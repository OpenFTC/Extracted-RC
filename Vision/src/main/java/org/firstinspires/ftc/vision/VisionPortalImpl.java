/*
 * Copyright (c) 2023 FIRST
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.vision;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.CameraControl;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibrationHelper;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibrationIdentity;
import org.firstinspires.ftc.robotcore.internal.camera.delegating.SwitchableCameraName;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvSwitchableWebcam;
import org.openftc.easyopencv.OpenCvWebcam;
import org.openftc.easyopencv.TimestampedOpenCvPipeline;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class VisionPortalImpl extends VisionPortal
{
    protected OpenCvCamera camera;
    protected final int cameraMonitorViewId;
    protected volatile CameraState cameraState = CameraState.CAMERA_DEVICE_CLOSED;
    protected VisionProcessor[] processors;
    protected volatile boolean[] processorsEnabled;
    protected volatile CameraCalibration calibration;
    protected final boolean autoPauseCameraMonitor;
    protected final boolean autoStartStream;
    protected final boolean showStats;
    protected final Semaphore userStateSemaphore = new Semaphore(1);
    protected final Size cameraResolution;
    protected final StreamFormat webcamStreamFormat;
    protected static final OpenCvCameraRotation CAMERA_ROTATION = OpenCvCameraRotation.SENSOR_NATIVE;
    protected String captureNextFrame;
    protected final Object captureFrameMtx = new Object();
    protected OpModeNotificationsListener opModeNotificationsListener = new OpModeNotificationsListener();

    // !! STATIC !! MUST PERSIST ACROSS INSTANCES
    protected static final Object viewUseMtx = new Object();
    protected static ArrayList<Integer> viewsInUse = new ArrayList<>();

    protected class OpModeNotificationsListener implements OpModeManagerNotifier.Notifications
    {
        @Override public void onOpModePreInit(OpMode opMode) {}
        @Override public void onOpModePreStart(OpMode opMode) {}

        @Override
        public void onOpModePostStop(OpMode opMode)
        {
            synchronized (viewUseMtx)
            {
                if (cameraMonitorViewId != 0 && viewsInUse.contains(cameraMonitorViewId))
                {
                    viewsInUse.remove(Integer.valueOf(cameraMonitorViewId));
                }
            }
        }
    }

    public VisionPortalImpl(CameraName camera, int cameraMonitorViewId, boolean autoPauseCameraMonitor,
                            Size cameraResolution, StreamFormat webcamStreamFormat, boolean autoStartStream, boolean showStats,
                            VisionProcessor[] processors)
    {
        synchronized (viewUseMtx)
        {
            if (cameraMonitorViewId != 0)
            {
                if (viewsInUse.contains(cameraMonitorViewId))
                {
                    if (cameraMonitorViewId == VisionPortal.DEFAULT_VIEW_CONTAINER_ID)
                    {
                        throw new IllegalStateException("When using multiple vision portals, you MUST use setLiveViewContainerId(int), NOT enableLiveView(bool)");
                    }
                    else
                    {
                        throw new IllegalStateException("User code attempted to set up multiple LiveViews with the same View ID. If you are using makeMultiPortalView(...), make sure you are using different index values on the returned array for each portal.");
                    }
                }
                else
                {
                    if (viewsInUse.contains(DEFAULT_VIEW_CONTAINER_ID) || (viewsInUse.size() > 0 && cameraMonitorViewId == DEFAULT_VIEW_CONTAINER_ID))
                    {
                        throw new IllegalStateException("Misconfigured MultiPortal View IDs for LiveView. Make sure you are using setLiveViewContainerId(int) for ALL portals");
                    }

                    viewsInUse.add(cameraMonitorViewId);
                    OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().getActivity()).registerListener(opModeNotificationsListener);
                }
            }
        }

        this.processors = processors;
        this.cameraMonitorViewId = cameraMonitorViewId;
        this.cameraResolution = cameraResolution;
        this.webcamStreamFormat = webcamStreamFormat;
        processorsEnabled = new boolean[processors.length];

        for (int i = 0; i < processors.length; i++)
        {
            processorsEnabled[i] = true;
        }

        this.autoPauseCameraMonitor = autoPauseCameraMonitor;
        this.showStats = showStats;
        this.autoStartStream = autoStartStream;

        createCamera(camera, cameraMonitorViewId);
        startCamera();
    }

    protected void startCamera()
    {
        if (camera == null)
        {
            throw new IllegalStateException("This should never happen");
        }

        if (cameraResolution == null) // was the user a silly silly
        {
            throw new IllegalArgumentException("parameters.cameraResolution == null");
        }

        camera.showFpsMeterOnViewport(showStats);
        camera.setViewportRenderer(OpenCvCamera.ViewportRenderer.NATIVE_VIEW);

        if(!(camera instanceof OpenCvWebcam))
        {
            camera.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
        }

        userStateSemaphore.acquireUninterruptibly();
        cameraState = CameraState.OPENING_CAMERA_DEVICE;

        try
        {
            camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
            {
                @Override
                public void onOpened()
                {
                    try
                    {
                        cameraState = CameraState.CAMERA_DEVICE_READY;
                        camera.setPipeline(new ProcessingPipeline());

                        if (camera instanceof OpenCvWebcam)
                        {
                            CameraCalibrationIdentity identity = ((OpenCvWebcam) camera).getCalibrationIdentity();

                            if (identity != null)
                            {
                                calibration = CameraCalibrationHelper.getInstance().getCalibration(identity, cameraResolution.getWidth(), cameraResolution.getHeight());
                            }
                        }

                        if (autoStartStream)
                        {
                            cameraState = CameraState.STARTING_STREAM;

                            if (camera instanceof OpenCvWebcam)
                            {
                                ((OpenCvWebcam)camera).startStreaming(cameraResolution.getWidth(), cameraResolution.getHeight(), CAMERA_ROTATION, webcamStreamFormat == null ? null : webcamStreamFormat.eocvStreamFormat);
                            }
                            else
                            {
                                camera.startStreaming(cameraResolution.getWidth(), cameraResolution.getHeight(), CAMERA_ROTATION);
                            }

                            cameraState = CameraState.STREAMING;
                        }
                    }
                    finally
                    {
                        userStateSemaphore.release();
                    }
                }

                @Override
                public void onError(int errorCode)
                {
                    cameraState = CameraState.ERROR;
                    RobotLog.ee("VisionPortalImpl", "Camera opening failed.");
                    userStateSemaphore.release();
                }
            });
        }
        catch (Exception e)
        {
            userStateSemaphore.release();
            throw e;
        }
    }

    protected void createCamera(CameraName cameraName, int cameraMonitorViewId)
    {
        if (cameraName == null) // was the user a silly silly
        {
            throw new IllegalArgumentException("parameters.camera == null");
        }
        else if (cameraName.isWebcam()) // Webcams
        {
            if (cameraMonitorViewId != 0)
            {
                camera = OpenCvCameraFactory.getInstance().createWebcam((WebcamName) cameraName, cameraMonitorViewId);
            }
            else
            {
                camera = OpenCvCameraFactory.getInstance().createWebcam((WebcamName) cameraName);
            }
        }
        else if (cameraName.isCameraDirection()) // Internal cameras
        {
            if (cameraMonitorViewId != 0)
            {
                camera = OpenCvCameraFactory.getInstance().createInternalCamera(
                        ((BuiltinCameraName) cameraName).getCameraDirection() == BuiltinCameraDirection.BACK ? OpenCvInternalCamera.CameraDirection.BACK : OpenCvInternalCamera.CameraDirection.FRONT, cameraMonitorViewId);
            }
            else
            {
                camera = OpenCvCameraFactory.getInstance().createInternalCamera(
                        ((BuiltinCameraName) cameraName).getCameraDirection() == BuiltinCameraDirection.BACK ? OpenCvInternalCamera.CameraDirection.BACK : OpenCvInternalCamera.CameraDirection.FRONT);
            }
        }
        else if (cameraName.isSwitchable())
        {
            SwitchableCameraName switchableCameraName = (SwitchableCameraName) cameraName;
            if (switchableCameraName.allMembersAreWebcams()) {
                CameraName[] members = switchableCameraName.getMembers();
                WebcamName[] webcamNames = new WebcamName[members.length];
                for (int i = 0; i < members.length; i++)
                {
                    webcamNames[i] = (WebcamName) members[i];
                }

                if (cameraMonitorViewId != 0)
                {
                    camera = OpenCvCameraFactory.getInstance().createSwitchableWebcam(cameraMonitorViewId, webcamNames);
                }
                else
                {
                    camera = OpenCvCameraFactory.getInstance().createSwitchableWebcam(webcamNames);
                }
            }
            else
            {
                throw new IllegalArgumentException("All members of a switchable camera name must be webcam names");
            }
        }
        else // ¯\_(ツ)_/¯
        {
            throw new IllegalArgumentException("Unknown camera name");
        }
    }

    @Override
    public void setProcessorEnabled(VisionProcessor processor, boolean enabled)
    {
        int numProcessorsEnabled = 0;
        boolean ok = false;

        for (int i = 0; i < processors.length; i++)
        {
            if (processor == processors[i])
            {
                processorsEnabled[i] = enabled;
                ok = true;
            }

            if (processorsEnabled[i])
            {
                numProcessorsEnabled++;
            }
        }

        if (ok)
        {
            if (autoPauseCameraMonitor)
            {
                if (numProcessorsEnabled == 0)
                {
                    camera.pauseViewport();
                }
                else
                {
                    camera.resumeViewport();
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("Processor not attached to this helper!");
        }
    }

    @Override
    public boolean getProcessorEnabled(VisionProcessor processor)
    {
        for (int i = 0; i < processors.length; i++)
        {
            if (processor == processors[i])
            {
                return processorsEnabled[i];
            }
        }

        throw new IllegalArgumentException("Processor not attached to this helper!");
    }

    @Override
    public CameraState getCameraState()
    {
        return cameraState;
    }

    @Override
    public void setActiveCamera(WebcamName webcamName)
    {
        if (camera instanceof OpenCvSwitchableWebcam)
        {
            ((OpenCvSwitchableWebcam) camera).setActiveCamera(webcamName);
        }
        else
        {
          throw new UnsupportedOperationException("setActiveCamera is only supported for switchable webcams");
        }
    }

    @Override
    public WebcamName getActiveCamera()
    {
        if (camera instanceof OpenCvSwitchableWebcam)
        {
            return ((OpenCvSwitchableWebcam) camera).getActiveCamera();
        }
        else
        {
            throw new UnsupportedOperationException("getActiveCamera is only supported for switchable webcams");
        }
    }

    @Override
    public <T extends CameraControl> T getCameraControl(Class<T> controlType)
    {
        if (cameraState == CameraState.STREAMING)
        {
            if (camera instanceof OpenCvWebcam)
            {
                return ((OpenCvWebcam) camera).getControl(controlType);
            }
            else
            {
                throw new UnsupportedOperationException("Getting controls is only supported for webcams");
            }
        }
        else
        {
            throw new IllegalStateException("You cannot use camera controls until the camera is streaming");
        }
    }

    @Override
    public void getFrameBitmap(Continuation<? extends Consumer<Bitmap>> continuation)
    {
        OpenCvCamera cameraSafe = camera;

        if (cameraSafe != null)
        {
            cameraSafe.getFrameBitmap(continuation);
        }
    }

    class ProcessingPipeline extends TimestampedOpenCvPipeline
    {
        public ProcessingPipeline()
        {
            MEMLEAK_DETECTION_ENABLED = false;
        }

        @Override
        public void init(Mat firstFrame)
        {
            for (VisionProcessor processor : processors)
            {
                processor.init(firstFrame.width(), firstFrame.height(), calibration);
            }
        }

        @Override
        public Mat processFrame(Mat input, long captureTimeNanos)
        {
            synchronized (captureFrameMtx)
            {
                if (captureNextFrame != null)
                {
                    saveMatToDiskFullPath(input, "/sdcard/VisionPortal-" + captureNextFrame + ".png");
                }

                captureNextFrame = null;
            }

            Object[] processorDrawCtxes = new Object[processors.length]; // cannot re-use frome to frame

            for (int i = 0; i < processors.length; i++)
            {
                if (processorsEnabled[i])
                {
                    processorDrawCtxes[i] = processors[i].processFrame(input, captureTimeNanos);
                }
            }

            requestViewportDrawHook(processorDrawCtxes);

            return input;
        }

        @Override
        public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext)
        {
            Object[] ctx = (Object[]) userContext;

            for (int i = 0; i < processors.length; i++)
            {
                if (processorsEnabled[i])
                {
                    processors[i].onDrawFrame(canvas, onscreenWidth, onscreenHeight, scaleBmpPxToCanvasPx, scaleCanvasDensity, ctx[i]);
                }
            }
        }
    }

    @Override
    public void saveNextFrameRaw(String filepath)
    {
        synchronized (captureFrameMtx)
        {
            captureNextFrame = filepath;
        }
    }

    @Override
    public void stopStreaming()
    {
        userStateSemaphore.acquireUninterruptibly();
        boolean handedOffSemaphore = false;

        try
        {
            if (cameraState == CameraState.STREAMING || cameraState == CameraState.STARTING_STREAM)
            {
                cameraState = CameraState.STOPPING_STREAM;
                new Thread(() ->
                {
                    try
                    {
                        camera.stopStreaming();
                        cameraState = CameraState.CAMERA_DEVICE_READY;
                    }
                    finally
                    {
                        userStateSemaphore.release();
                    }
                }).start();
                handedOffSemaphore = true;
            }
            else if (cameraState == CameraState.STOPPING_STREAM
                    || cameraState == CameraState.CAMERA_DEVICE_READY
                    || cameraState == CameraState.CLOSING_CAMERA_DEVICE)
            {
                // NO-OP: be idempotent
            }
            else
            {
                throw new RuntimeException(String.format("Illegal CameraState %s when calling stopStreaming()", cameraState.toString()));
            }
        }
        finally
        {
            if (!handedOffSemaphore)
            {
                userStateSemaphore.release();
            }
        }
    }

    @Override
    public void resumeStreaming()
    {
        userStateSemaphore.acquireUninterruptibly();
        boolean handedOffSemaphore = false;

        try
        {
            if (cameraState == CameraState.CAMERA_DEVICE_READY || cameraState == CameraState.STOPPING_STREAM)
            {
                cameraState = CameraState.STARTING_STREAM;
                new Thread(() ->
                {
                    try
                    {
                        if (camera instanceof OpenCvWebcam)
                        {
                            ((OpenCvWebcam)camera).startStreaming(cameraResolution.getWidth(), cameraResolution.getHeight(), CAMERA_ROTATION, webcamStreamFormat.eocvStreamFormat);
                        }
                        else
                        {
                            camera.startStreaming(cameraResolution.getWidth(), cameraResolution.getHeight(), CAMERA_ROTATION);
                        }
                        cameraState = CameraState.STREAMING;
                    }
                    finally
                    {
                        userStateSemaphore.release();
                    }
                }).start();
                handedOffSemaphore = true;
            }
            else if (cameraState == CameraState.STREAMING
                    || cameraState == CameraState.STARTING_STREAM)
            {
                // NO-OP: be idempotent
            }
            else
            {
                throw new RuntimeException(String.format("Illegal CameraState %s when calling resumeStreaming()", cameraState.toString()));
            }
        }
        finally
        {
            if (!handedOffSemaphore)
            {
                userStateSemaphore.release();
            }
        }
    }

    @Override
    public void stopLiveView()
    {
        OpenCvCamera cameraSafe = camera;

        if (cameraSafe != null)
        {
            camera.pauseViewport();
        }
    }

    @Override
    public void resumeLiveView()
    {
        OpenCvCamera cameraSafe = camera;

        if (cameraSafe != null)
        {
            camera.resumeViewport();
        }
    }

    @Override
    public float getFps()
    {
        OpenCvCamera cameraSafe = camera;

        if (cameraSafe != null)
        {
           return cameraSafe.getFps();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public void close()
    {
        userStateSemaphore.acquireUninterruptibly();
        boolean handedOffSemaphore = false;

        try
        {
            if (camera != null)
            {
                cameraState = CameraState.CLOSING_CAMERA_DEVICE;

                new Thread(() ->
                {
                    try
                    {
                        camera.closeCameraDevice();
                        cameraState = CameraState.CAMERA_DEVICE_CLOSED;
                        camera = null;
                    }
                    finally
                    {
                        userStateSemaphore.release();
                    }
                }).start();

                handedOffSemaphore = true;
            }
        }
        finally
        {
            if (!handedOffSemaphore)
            {
                userStateSemaphore.release();
            }

            synchronized (viewUseMtx)
            {
                if (cameraMonitorViewId != 0 && viewsInUse.contains(cameraMonitorViewId))
                {
                    viewsInUse.remove(Integer.valueOf(cameraMonitorViewId));
                }
            }
        }
    }
}

/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.firstinspires.ftc.robotcore.internal.tfod;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CloseableFrame;
import org.firstinspires.ftc.robotcore.external.tfod.CameraInformation;
import org.firstinspires.ftc.robotcore.external.tfod.FrameConsumer;
import org.firstinspires.ftc.robotcore.external.tfod.FrameGenerator;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

/**
 * An implementation of FrameGenerator where the frames are retrieved from the Vuforia frame queue.
 *
 * @author Vasu Agrawal
 * @author lizlooney@google.com (Liz Looney)
 */
public class VuforiaFrameGenerator implements FrameGenerator, Runnable {

  private final VuforiaLocalizer vuforiaLocalizer;
  private final BlockingQueue<CloseableFrame> frameQueue;
  private final CameraInformation cameraInformation;
  private final Thread frameGeneratorThread;
  private final AtomicReference<FrameConsumer> frameConsumerHolder = new AtomicReference<>();

  public VuforiaFrameGenerator(VuforiaLocalizer vuforiaLocalizer) {
    this.vuforiaLocalizer = vuforiaLocalizer;
    vuforiaLocalizer.enableConvertFrameToBitmap();
    vuforiaLocalizer.setFrameQueueCapacity(1);
    frameQueue = vuforiaLocalizer.getFrameQueue();
    cameraInformation = createCameraInformation(vuforiaLocalizer);
    frameGeneratorThread = new Thread(this, "VuforiaFrameGenerator");
  }

  private static CameraInformation createCameraInformation(VuforiaLocalizer vuforiaLocalizer) {
    int rotation = 0;

    CameraName cameraName = vuforiaLocalizer.getCameraName();
    if (cameraName instanceof BuiltinCameraName) {
      int displayRotation = 90 * AppUtil.getInstance().getRootActivity().getWindowManager().getDefaultDisplay().getRotation();

      BuiltinCameraDirection cameraDirection = ((BuiltinCameraName) cameraName).getCameraDirection();

      for (int cameraId = 0; cameraId < Camera.getNumberOfCameras(); cameraId++) {
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(cameraId, cameraInfo);

        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT && cameraDirection == BuiltinCameraDirection.FRONT) {
          rotation = - displayRotation - cameraInfo.orientation;
          break;
        }
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK && cameraDirection == BuiltinCameraDirection.BACK) {
          rotation = displayRotation - cameraInfo.orientation;
          break;
        }
      }
    }

    while (rotation < 0) {
      rotation += 360;
    }
    rotation %= 360;

    CameraCalibration camCal = vuforiaLocalizer.getCameraCalibration();
    // Vuforia returns the focal length in pixels, which is exactly what we need!
    return new CameraInformation(camCal.getSize().getWidth(), camCal.getSize().getHeight(),
        rotation, camCal.focalLengthX, camCal.focalLengthY);
  }

  // FrameGenerator

  @Override
  public CameraInformation getCameraInformation() {
    return cameraInformation;
  }

  @Override
  public void setFrameConsumer(FrameConsumer frameConsumer) {
    frameConsumerHolder.set(frameConsumer);
    if (frameConsumer != null) {
      frameGeneratorThread.start();
    } else {
      frameGeneratorThread.interrupt();
      try {
        frameGeneratorThread.join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  // Runnable

  @Override
  public void run() {
    Bitmap bitmap = null;

    while (true) {
      CloseableFrame frame;
      try {
        frame = frameQueue.take();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      }

      if (bitmap == null) {
        bitmap = createBitmap(frame, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
          bitmap = createBitmap(frame, Bitmap.Config.RGB_565);
          if (bitmap == null) {
            RobotLog.e("Error: Didn't find an RGBA8888 or RGB565 image from Vuforia!");
            return;
          }
        }
        FrameConsumer frameConsumer = frameConsumerHolder.get();
        if (frameConsumer == null) {
          return;
        }
        frameConsumer.init(bitmap);
      }

      boolean success = copyFrameToBitmap(frame, bitmap);
      frame.close(); frame = null;
      if (success) {
        FrameConsumer frameConsumer = frameConsumerHolder.get();
        if (frameConsumer == null) {
          return;
        }
        frameConsumer.processFrame();
      }
    }
  }

  private Bitmap createBitmap(CloseableFrame frame, Bitmap.Config bitmapConfig) {
    int pixelFormat = getPixelFormat(bitmapConfig);
    for (int i = 0; i < frame.getNumImages(); i++) {
      Image image = frame.getImage(i);
      if (image.getFormat() == pixelFormat) {
        return Bitmap.createBitmap(image.getWidth(), image.getHeight(), bitmapConfig);
      }
    }
    return null;
  }

  private boolean copyFrameToBitmap(CloseableFrame frame, Bitmap bitmap) {
    int pixelFormat = getPixelFormat(bitmap.getConfig());
    for (int i = 0; i < frame.getNumImages(); i++) {
      Image image = frame.getImage(i);
      if (image.getFormat() == pixelFormat) {
        bitmap.copyPixelsFromBuffer(image.getPixels());
        return true;
      }
    }
    RobotLog.e("Error: Didn't find a " + getPixelFormatName(bitmap.getConfig()) + " image from Vuforia!");
    return false;
  }

  private static int getPixelFormat(Bitmap.Config bitmapConfig) {
    switch (bitmapConfig) {
      case ARGB_8888:
        return PIXEL_FORMAT.RGBA8888;
      case RGB_565:
        return PIXEL_FORMAT.RGB565;
    }
    throw new IllegalArgumentException("Unexpected Bitmap.Config " + bitmapConfig);
  }

  private static String getPixelFormatName(Bitmap.Config bitmapConfig) {
    switch (bitmapConfig) {
      case ARGB_8888:
        return "RGBA8888";
      case RGB_565:
        return "RGB565";
    }
    throw new IllegalArgumentException("Unexpected Bitmap.Config " + bitmapConfig);
  }
}

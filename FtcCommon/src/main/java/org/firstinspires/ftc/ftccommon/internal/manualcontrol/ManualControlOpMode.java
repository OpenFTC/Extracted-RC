/*
Copyright (c) 2023 REV Robotics

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of REV Robotics nor the names of its contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.ftccommon.internal.manualcontrol;

import android.content.Context;

import androidx.annotation.Nullable;

import com.qualcomm.ftccommon.FtcEventLoop;
import com.qualcomm.ftccommon.R;
import com.qualcomm.hardware.HardwareDeviceManager;
import com.qualcomm.hardware.HardwareManualControlOpMode;
import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.bosch.BNO055Util;
import com.qualcomm.hardware.lynx.LynxEmbeddedBNO055IMUNew;
import com.qualcomm.hardware.lynx.LynxI2cDeviceSynch;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxNackException;
import com.qualcomm.hardware.lynx.LynxUsbDevice;
import com.qualcomm.hardware.lynx.LynxUsbDevice.SystemOperationHandle;
import com.qualcomm.hardware.lynx.LynxUsbDeviceImpl;
import com.qualcomm.hardware.lynx.commands.core.LynxFirmwareVersionManager;
import com.qualcomm.robotcore.eventloop.EventLoopManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.EmbeddedControlHubModule;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.usb.RobotArmingStateNotifier;
import com.qualcomm.robotcore.util.Device;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;

import com.qualcomm.robotcore.util.ThreadPool;
import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.DeviceNotControlHubException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.FailedToOpenHubException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.ImuNotFoundException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidDeviceIdException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidParameterException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.UserOpModeRunningException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.WebSocketNotAuthorizedForManualControlException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.HandleIdParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.OpenHubParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.HubAddressChangedNotification;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.HubStatusChangedNotification;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.FtcWebSocketMessage;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketCommandException;

import java.util.HashMap;
import java.util.Locale;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ManualControlOpMode extends HardwareManualControlOpMode {
    public static final String MANUAL_CONTROL_OP_MODE_NAME = "$Manual$Control$";

    private static final String TAG = "ManualControl";

    public static ManualControlOpMode getInstance() { return (ManualControlOpMode) HardwareManualControlOpMode.getInstance(); }

    @OpModeRegistrar
    public static void register(OpModeManager opModeManager) {
        opModeManager.register(new OpModeMeta.Builder()
                        .setFlavor(OpModeMeta.Flavor.SYSTEM)
                        .setName(ManualControlOpMode.MANUAL_CONTROL_OP_MODE_NAME)
                        .setSystemOpModeBaseDisplayName(AppUtil.getDefContext().getString(R.string.manual_control_op_mode_display_name))
                        .build(),
                ManualControlOpMode.class);
    }

    @Nullable private static volatile EventLoopManager eventLoopManager;
    public static void setEventLoopManager(@Nullable EventLoopManager eventLoopManager) {
        ManualControlOpMode.eventLoopManager = eventLoopManager;
    }

    //----------------------------------------------------------------------------------------------
    // Static OpMode tracking code
    //----------------------------------------------------------------------------------------------

    private static final Object currentOpModeLock = new Object();
    @Nullable private static OpModeManagerImpl opModeManager;
    @Nullable private static CurrentOpMode currentOpMode = CurrentOpMode.DEFAULT;
    private static final OpModeManagerNotifier.Notifications opModeListener = new OpModeManagerNotifier.Notifications() {
        @Override public void onOpModePreInit(OpMode opMode) {
            synchronized (currentOpModeLock) {
                updateCurrentOpMode(opMode);

                // Notify waiting threads that the condition they're waiting for may have arrived
                currentOpModeLock.notifyAll();
            }
        }

        @Override public void onOpModePreStart(OpMode opMode) { }
        @Override public void onOpModePostStop(OpMode opMode) { }
    };
    private enum CurrentOpMode { PENDING, DEFAULT, MANUAL_CONTROL, OTHER }

    @OnCreateEventLoop
    @SuppressWarnings("unused")
    public static void updateOpModeManager(Context context, FtcEventLoop eventLoop) {
        synchronized (currentOpModeLock) {
            opModeManager = eventLoop.getOpModeManager();
            updateCurrentOpMode(opModeManager.registerListener(opModeListener));

            // Notify waiting threads that the condition they're waiting for may have arrived
            currentOpModeLock.notifyAll();
        }
    }

    // Callers MUST hold currentOpModeLock, and call currentOpModeLock.notify() after this finishes
    private static void updateCurrentOpMode(@Nullable OpMode opMode) {
        if (opMode == null) {
            // Indicates that this is a brand new OpModeManagerImpl
            currentOpMode = CurrentOpMode.DEFAULT;
        } else if (opMode instanceof OpModeManagerImpl.DefaultOpMode) {
            currentOpMode = CurrentOpMode.DEFAULT;
        } else if (opMode instanceof ManualControlOpMode) {
            // When the Manual Control OpMode _actually_ starts running, it will
            // set currentOpMode to MANUAL_CONTROL itself.
            currentOpMode = CurrentOpMode.PENDING;
        } else {
            currentOpMode = CurrentOpMode.OTHER;
        }
    }

    public static void startOpModeAndWait() throws UserOpModeRunningException, InterruptedException {
        synchronized (currentOpModeLock) {
            // Wait for opModeManager to get set
            while (opModeManager == null) {
                currentOpModeLock.wait();
            }

            // Wait for currentOpMode to not be PENDING
            while (currentOpMode == CurrentOpMode.PENDING) {
                currentOpModeLock.wait();
            }

            if (currentOpMode == CurrentOpMode.MANUAL_CONTROL) {
                return;
            }

            // By checking this in a loop and calling wait() on a lock that gets notified when
            // either a new event loop starts up or the current OpMode changes, we should be
            // fully resilient to Robot Restarts.
            while (currentOpMode == CurrentOpMode.DEFAULT) {
                // Tell opModeManager to start the Manual Control OpMode, but only if the default
                // OpMode is still the actively running one
                opModeManager.initOpMode(MANUAL_CONTROL_OP_MODE_NAME, true);

                // Wait for a new OpMode to start
                currentOpMode = CurrentOpMode.PENDING;
                while (currentOpMode == CurrentOpMode.PENDING) {
                    currentOpModeLock.wait();
                }
            }

            if (currentOpMode == CurrentOpMode.OTHER) {
                throw new UserOpModeRunningException();
            } else if (currentOpMode == CurrentOpMode.MANUAL_CONTROL) {
                // Success!
                return;
            } else {
                throw new RuntimeException("Unexpected currentOpMode state " + currentOpMode);
            }
        }
    }

    public static boolean isRunning() {
        try {
            synchronized (currentOpModeLock) {
                while (currentOpMode == CurrentOpMode.PENDING) {
                    currentOpModeLock.wait();
                }

                return currentOpMode == CurrentOpMode.MANUAL_CONTROL;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    //----------------------------------------------------------------------------------------------
    // Instance variables and methods
    //----------------------------------------------------------------------------------------------
    private final BlockingDeque<FutureTask<?>> taskQueue = new LinkedBlockingDeque<>();

    // These will ONLY be accessed from the main OpMode thread
    private final Map<Integer, SystemOperationHandle> handleIdToHandleMap = new HashMap<>();
    private final List<LynxUsbDevice> usbDevices = new LinkedList<>();
    private int nextHandleId = 0;
    private IMU controlHubImu;

    public ManualControlOpMode() {
        HardwareManualControlOpMode.setInstance(this);
    }

    public void initializeImu(IMU.Parameters parameters) throws ImuNotFoundException, DeviceNotControlHubException {
        LynxModule controlHub = (LynxModule) EmbeddedControlHubModule.get();
        if (!Device.isRevControlHub()) {
            throw new DeviceNotControlHubException();
        }

        if (controlHub == null) {
            throw new RuntimeException("Unable to access the Control Hub's internal module");
        }
        // The IMU is on I2C bus 0.
        LynxI2cDeviceSynch i2cDevice = LynxFirmwareVersionManager.createLynxI2cDeviceSynch(AppUtil.getDefContext(), controlHub, 0);
        controlHubImu = createImu(controlHub, i2cDevice);
        boolean successful = controlHubImu.initialize(parameters);
        if(!successful) {
            controlHubImu = null;
            throw new ImuNotFoundException("Unable to initialize IMU");
        }
    }

    public IMU getImu() {
        return controlHubImu;
    }

    /**
     * Do NOT call this directly
     */
    @Override public void runOpMode() throws InterruptedException {
        try {
            synchronized (currentOpModeLock) {
                currentOpMode = CurrentOpMode.MANUAL_CONTROL;
                currentOpModeLock.notifyAll();
            }

            RobotLog.vv(TAG, "Manual Control mode has started");
            telemetry.addData("Status", "Robot can be controlled via external software");
            telemetry.update();

            while (!isStopRequested()) {
                FutureTask<?> task = taskQueue.take();
                task.run();
            }
        } finally {
            // The Manual Control OpMode is stopping

            // Unmark ourselves as the currently-running OpMode
            synchronized (currentOpModeLock) {
                currentOpMode = CurrentOpMode.PENDING;
            }

            ManualControlWebSocketHandler handler = ManualControlWebSocketHandler.getInstance();
            if (handler != null) {
                // Clean up the current Manual Control session
                handler.stopManualControl();
            }

            // Cancel any tasks that were queued to run on this OpMode's thread
            FutureTask<?> nextTask = taskQueue.poll();
            while (nextTask != null) {
                // The task is running on this thread, which doesn't need to get interrupted
                nextTask.cancel(false);
                nextTask = taskQueue.poll();
            }

            // Close all system operation handles that this OpMode still has open
            for (int handleId: handleIdToHandleMap.keySet()) {
                closeSystemOperationHandle(handleId);
            }
            handleIdToHandleMap.clear();

            // Close the current IMU
            controlHubImu.close();
            controlHubImu = null;

            // Close all USB device delegates that this OpMode opened
            for (LynxUsbDevice usbDevice: usbDevices) {
                usbDevice.close();
            }
        }
    }

    public <T> T runFromOpMode(Callable<T> callable) throws WebSocketCommandException {
        return runFromOpMode(callable, false);
    }

    public <T> T runFromOpMode(Callable<T> callable, boolean mustRunNext) throws WebSocketCommandException {
        FutureTask<T> task = new FutureTask<T>(callable);

        // Ensure that we don't end up in a situation where we add a task, but it never gets
        // executed or cancelled.
        synchronized (currentOpModeLock) {
            if (!isRunning()) throw new WebSocketNotAuthorizedForManualControlException();
            if (mustRunNext) {
                taskQueue.addFirst(task);
            } else {
                taskQueue.addLast(task);
            }
        }

        try {
            return task.get();
        } catch (CancellationException e) {
            // This indicates that the Manual Control OpMode was stopped before our task ran
            throw new WebSocketNotAuthorizedForManualControlException();
        } catch (ExecutionException exceptionWrapper) {
            // This indicates that the callable threw an Exception.
            Throwable e = exceptionWrapper.getCause();

            if (e instanceof InterruptedException) {
                // This indicates that the Manual Control OpMode was stopped mid-task, in which
                // case we can throw a more helpful exception than just RuntimeException.
                throw new WebSocketNotAuthorizedForManualControlException();
            } else if (e instanceof WebSocketCommandException) {
                // This exception is ready to be sent to the WebSocket, pass it along as-is
                throw (WebSocketCommandException) e;
            } else {
                throw new RuntimeException(e);
            }
        } catch (InterruptedException e) {
            // This indicates that the thread this method was called on was interrupted
            throw new RuntimeException(e);
        }
    }

    /**
     * ONLY call this from the Manual Control OpMode thread
     */
    public int openHubAndGetHandleId(OpenHubParameters parameters) throws InterruptedException, FailedToOpenHubException, InvalidParameterException {
        // We always open a new handle when this method is called, so that handles can be closed
        // individually without affecting duplicate handles that the client may have opened. This
        // also simplifies the amount of state we have to keep track of, especially when you factor
        // in module address changes.
        SerialNumber parentSerialNumber = SerialNumber.fromString(parameters.getParentSerialNumber());

        try {
            LynxUsbDevice lynxUsb = LynxUsbDeviceImpl.findOrCreateAndArm(
                    AppUtil.getDefContext(),
                    parentSerialNumber,
                    eventLoopManager,
                    HardwareDeviceManager.createUsbManager());

            if (lynxUsb.getArmingState() != RobotArmingStateNotifier.ARMINGSTATE.ARMED) {
                lynxUsb.close();
                throw new RobotCoreException("The specified LynxUsbDevice is not armed");
            }

            usbDevices.add(lynxUsb);

            SystemOperationHandle handle = lynxUsb.keepConnectedModuleAliveForSystemOperations(
                    parameters.getHubAddress(), parameters.getParentHubAddress());

            int handleId = nextHandleId++;
            handleIdToHandleMap.put(handleId, handle);
            return handleId;
        } catch (RobotCoreException | RuntimeException e) {
            String message = String.format(Locale.US, "Failed to open REV Hub. parentSerialNumber=%s address=%d", parentSerialNumber, parameters.getHubAddress());
            RobotLog.ee(TAG, e, message);
            throw new FailedToOpenHubException(message);
        }
    }

    /**
     * ONLY call this from the Manual Control OpMode thread
     */
    public SystemOperationHandle getSystemOperationHandle(HandleIdParameters parameters) throws InvalidDeviceIdException, InvalidParameterException {
        SystemOperationHandle handle = handleIdToHandleMap.get(parameters.getHandleId());
        if (handle == null) { throw new InvalidDeviceIdException(); }
        else { return handle; }
    }

    /**
     * ONLY call this from the Manual Control OpMode thread.
     * <p>
     * Idempotent.
     */
    public void closeSystemOperationHandle(int handleId) {
        SystemOperationHandle handleToClose = handleIdToHandleMap.get(handleId);
        if (handleToClose != null) {
            // Temporarily de-interrupt the OpMode thread so that we can talk to the hardware in peace
            boolean threadWasInterrupted = Thread.interrupted();
            try {
                handleIdToHandleMap.remove(handleId);

                boolean thisWasTheOnlyHandleToThisHub = true;
                for (SystemOperationHandle openHandle: handleIdToHandleMap.values()) {
                    if (openHandle.wrapsSameModule(handleToClose)) {
                        thisWasTheOnlyHandleToThisHub = false;
                        break;
                    }
                }

                if (thisWasTheOnlyHandleToThisHub) {
                    handleToClose.performSystemOperation(module -> {
                        try {
                            module.failSafe();
                        } catch (RobotCoreException | InterruptedException | LynxNackException e) {
                            RobotLog.ww(TAG, "Failed to put hub in failsafe mode");
                        }
                        module.setPattern(LynxModule.blinkerPolicy.getIdlePattern(module));
                    }, 25, TimeUnit.MILLISECONDS);
                }
            } catch (RobotCoreException | TimeoutException e) {
                RobotLog.ww(TAG, e, "Failed to put hub in failsafe mode and restore default pattern");
            } catch (InterruptedException e) {
                RobotLog.ww(TAG, e, "Failed to put hub in failsafe mode and restore default pattern");
                Thread.currentThread().interrupt();
            } finally {
                handleToClose.close();
                if (threadWasInterrupted) {
                    // Re-interrupt the OpMode thread
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public void onLynxModuleStatusChanged(LynxModule module, int statusWord, int motorAlerts) {
        try {
            if (!isRunning()) {
                return;
            }
            runFromOpMode(() -> {
                ManualControlWebSocketHandler handler = ManualControlWebSocketHandler.getInstance();
                int[] matchingHandleIds = findHandleIdsMatchingLynxModule(module);
                if (handler != null && matchingHandleIds.length > 0) {
                    String namespace = ManualControlWebSocketHandler.WS_NAMESPACE;
                    String payload = new HubStatusChangedNotification(statusWord, motorAlerts, matchingHandleIds).toJson();
                    handler.sendMessageToAllowedWebSocket(
                            new FtcWebSocketMessage(namespace,
                                    ManualControlWebSocketHandler.HUB_STATUS_CHANGED_NOTIFICATION,
                                    payload));
                }
                return null;
            }, true); // We pass true to the mustRunNext param to prioritize sending the address change notification
        } catch (RuntimeException | WebSocketCommandException e) {
            RobotLog.ee(TAG, e, "Exception thrown in onLynxModuleAddressChanged()");
        }
    }

    @Override
    public void onLynxModuleAddressChanged(LynxModule module, int oldAddress, int newAddress) {
        // We must run this on a background thread, because when we're the ones changing the address, the OpMode thread
        // will end up waiting for this function to exit, which also needs to run code on the OpMode thread. If we
        // block the address changing process in that way, we get a deadlock.
        ThreadPool.getDefault().execute(() -> {
            try {
                if (!isRunning()) {
                    return;
                }
                runFromOpMode(() -> {
                    ManualControlWebSocketHandler handler = ManualControlWebSocketHandler.getInstance();
                    int[] matchingHandleIds = findHandleIdsMatchingLynxModule(module);
                    if (handler != null && matchingHandleIds.length > 0) {
                        String namespace = ManualControlWebSocketHandler.WS_NAMESPACE;
                        String payload = new HubAddressChangedNotification(oldAddress, newAddress, matchingHandleIds).toJson();
                        handler.sendMessageToAllowedWebSocket(
                                new FtcWebSocketMessage(namespace,
                                        ManualControlWebSocketHandler.HUB_ADDRESS_CHANGED_NOTIFICATION,
                                        payload));
                    }
                    return null;
                });
            } catch (RuntimeException | WebSocketCommandException e) {
                RobotLog.ee(TAG, e, "Exception thrown in onLynxModuleAddressChanged()");
            }
        });
    }

    private int[] findHandleIdsMatchingLynxModule(LynxModule module) {
        return handleIdToHandleMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().wrapsModule(module))
                .mapToInt(Map.Entry::getKey)
                .toArray();
    }

    private IMU createImu(LynxModule controlHub, LynxI2cDeviceSynch i2cDevice) throws ImuNotFoundException {
        switch (controlHub.getImuType()) {
            case BNO055:
                return new LynxEmbeddedBNO055IMUNew(i2cDevice, true);
            case BHI260:
                return new BHI260IMU(i2cDevice, true);
            default:
                throw new ImuNotFoundException("Failed to communicate with BHI260 or BNO055 IMU.");
        }
    }
}

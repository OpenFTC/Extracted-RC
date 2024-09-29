/*
 * Copyright 2016 Google LLC
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

package com.google.blocks.ftcrobotcontroller.runtime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.blocks.ftcrobotcontroller.hardware.HardwareItem;
import com.google.blocks.ftcrobotcontroller.hardware.HardwareItemMap;
import com.google.blocks.ftcrobotcontroller.hardware.HardwareType;
import com.google.blocks.ftcrobotcontroller.hardware.HardwareUtil;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.TensorFlowAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.TfodAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.TfodCurrentGameAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.TfodCustomModelAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.TfodRoverRuckusAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.TfodSkyStoneAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.VuforiaCurrentGameAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.VuforiaLocalizerAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.VuforiaLocalizerParametersAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.VuforiaRelicRecoveryAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.VuforiaRoverRuckusAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.VuforiaSkyStoneAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.VuforiaTrackableAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.VuforiaTrackableDefaultListenerAccess;
import com.google.blocks.ftcrobotcontroller.runtime.obsolete.VuforiaTrackablesAccess;
import com.google.blocks.ftcrobotcontroller.util.FileUtil;
import com.google.blocks.ftcrobotcontroller.util.Identifier;
import com.google.blocks.ftcrobotcontroller.util.ProjectsUtil;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.hardware.EmbeddedControlHubModule;
import com.qualcomm.robotcore.hardware.LynxModuleImuType;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.internal.opmode.InstanceOpModeManager;
import org.firstinspires.ftc.robotcore.internal.opmode.InstanceOpModeRegistrar;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.robotcore.internal.opmode.RegisteredOpModes;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A subclass of {@link LinearOpMode} that loads JavaScript from a file and executes it.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public final class BlocksOpMode extends LinearOpMode {
  private static final String BLOCK_EXECUTION_ERROR = "Error: Error calling method on NPObject.";
  private static final String LOG_PREFIX = "BlocksOpMode - ";
  private static final boolean DEBUG_BLOCKS_EXECUTION = false;

  private static final AtomicReference<RuntimeException> fatalExceptionHolder = new AtomicReference<RuntimeException>();
  private static final AtomicReference<String> fatalErrorMessageHolder = new AtomicReference<String>();

  @SuppressLint("StaticFieldLeak")
  private static Activity activity;
  @SuppressLint("StaticFieldLeak")
  private static WebView webView;
  private static final AtomicReference<String> nameOfOpModeLoadedIntoWebView = new AtomicReference<String>();
  // Visible for testing.
  static final Map<String, Access> javascriptInterfaces = new ConcurrentHashMap<String, Access>();
  private final String project;
  private final String logPrefix;
  private final AtomicLong interruptedTime = new AtomicLong();

  private volatile BlockType currentBlockType;
  private volatile boolean currentBlockFinished;
  private volatile String currentBlockFirstName;
  private volatile String currentBlockLastName;
  private volatile Thread javaBridgeThread;

  private volatile boolean forceStopped = false;
  private volatile boolean wasTerminated = false;

  /**
   * The BlocksOpMode constructor loads JavaScript from a file and executes it when a Blocks OpMode
   * is run.
   *
   * @param project the name of the project.
   */
  // Visible for testing
  BlocksOpMode(String project) {
    super();
    this.project = project;
    logPrefix = LOG_PREFIX + "\"" + project + "\" - ";
  }

  private String getLogPrefix() {
    Thread thread = Thread.currentThread();
    return logPrefix + thread.getThreadGroup().getName() + "/" + thread.getName() + " - ";
  }

  void startBlockExecution(BlockType blockType, String blockFirstName, String blockLastName) {
    currentBlockType = blockType;
    currentBlockFirstName = blockFirstName;
    currentBlockLastName = blockLastName;
    currentBlockFinished = false;
    checkIfStopRequested();
    if (DEBUG_BLOCKS_EXECUTION) {
      RobotLog.i(getLogPrefix() + "startBlockExecution - " + getFullBlockLabel());
    }
  }

  void endBlockExecution() {
    if (fatalExceptionHolder.get() == null) {
      currentBlockFinished = true;
    }
  }

  String getFullBlockLabel() {
    switch (currentBlockType) {
      default:
        return "to runOpmode";
      case SPECIAL:
        return currentBlockFirstName + currentBlockLastName;
      case EVENT:
        return "to " +  currentBlockFirstName + currentBlockLastName;
      case CREATE:
        return "new " + currentBlockFirstName;
      case SETTER:
        return "set " + currentBlockFirstName + currentBlockLastName + " to";
      case GETTER:
        return currentBlockFirstName + currentBlockLastName;
      case FUNCTION:
        return "call " + currentBlockFirstName + currentBlockLastName;
    }
  }

  public void handleFatalException(Throwable e) {
    String errorMessage = e.getClass().getSimpleName() + (e.getMessage() != null ? " - " + e.getMessage() : "");
    RuntimeException re = new RuntimeException(
        "Fatal error occurred while executing the block labeled \"" + getFullBlockLabel() + "\". " +
        errorMessage, e);
    fatalExceptionHolder.set(re);
    throw re; // This will cause the OpMode to stop.
  }

  private void checkIfStopRequested() {
    if (interruptedTime.get() != 0L &&
        isStopRequested() &&
        System.currentTimeMillis() - interruptedTime.get() >= /* bite BEFORE main watchdog*/MS_BEFORE_FORCE_STOP_AFTER_STOP_REQUESTED-100) {
      RobotLog.i(getLogPrefix() + "checkIfStopRequested - about to stop OpMode by throwing RuntimeException");
      forceStopped = true;
      throw new RuntimeException("Stopping OpMode " + project + " by force.");
    }
  }

  void waitForStartForBlocks() {
    // Because this method is executed on the Java Bridge thread, it is not interrupted when stop
    // is called. To fix this, we repeatedly wait 100ms and check isStarted.
    RobotLog.i(getLogPrefix() + "waitForStartForBlocks - start");
    try {
      while (!isStartedForBlocks()) {
        synchronized (this) {
          try {
            this.wait(100);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
          }
        }
      }
    } finally {
      RobotLog.i(getLogPrefix() + "waitForStartForBlocks - end");
    }
  }

  void sleepForBlocks(long millis) {
    // Because this method is executed on the Java Bridge thread, it is not interrupted when stop
    // is called. To fix this, we repeatedly sleep 100ms and check isInterrupted.
    RobotLog.i(getLogPrefix() + "sleepForBlocks - start");
    try {
      long endTime = System.currentTimeMillis() + millis;
      while (!isInterrupted()) {
        long chunk = Math.min(100L, endTime - System.currentTimeMillis());
        if (chunk <= 0) {
          break;
        }
        sleep(chunk);
      }
    } finally {
      RobotLog.i(getLogPrefix() + "sleepForBlocks - end");
    }
  }

  private boolean isInterrupted() {
    return interruptedTime.get() != 0L;
  }

  boolean isStartedForBlocks() {
    return super.isStarted() || isInterrupted();
  }

  boolean isStopRequestedForBlocks() {
    return super.isStopRequested() || isInterrupted();
  }

  void terminateOpModeNowForBlocks() {
    wasTerminated = true;
    super.terminateOpModeNow();
  }

  @Override
  public void runOpMode() {
    RobotLog.i(getLogPrefix() + "runOpMode - start");
    cleanUpPreviousBlocksOpMode();

    BlocksOpModeCompanion.opMode = this;
    BlocksOpModeCompanion.linearOpMode = this;
    BlocksOpModeCompanion.hardwareMap = hardwareMap;
    BlocksOpModeCompanion.telemetry = telemetry;
    BlocksOpModeCompanion.gamepad1 = gamepad1;
    BlocksOpModeCompanion.gamepad2 = gamepad2;

    try {
      fatalExceptionHolder.set(null);
      fatalErrorMessageHolder.set(null);

      currentBlockType = BlockType.EVENT;
      currentBlockFirstName = "";
      currentBlockLastName = "runOpMode";

      boolean interrupted = false;
      interruptedTime.set(0L);

      final AtomicBoolean scriptFinished = new AtomicBoolean();
      final Object scriptFinishedLock = new Object();

      final BlocksOpModeAccess blocksOpModeAccess = new BlocksOpModeAccess(
          Identifier.BLOCKS_OP_MODE.identifierForJavaScript, scriptFinishedLock, scriptFinished);

      javascriptInterfaces.put(
          Identifier.BLOCKS_OP_MODE.identifierForJavaScript, blocksOpModeAccess);

      // Start running the user's OpMode blocks by calling loadScript on the UI thread.
      // Execution of the script is done by the WebView component, which uses the Java Bridge
      // thread to call into java.

      AppUtil appUtil = AppUtil.getInstance();

      synchronized (scriptFinishedLock) {
        appUtil.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            try {
              RobotLog.i(getLogPrefix() + "run1 - before loadScript");
              loadScript();
              RobotLog.i(getLogPrefix() + "run1 - after loadScript");
            } catch (Exception e) {
              RobotLog.e(getLogPrefix() + "run1 - caught " + e);
              // The exception may not have a stacktrace, so we check before calling
              // RobotLog.logStackTrace.
              if (e.getStackTrace() != null) {
                RobotLog.logStackTrace(e);
              }
            }
          }
        });

        // This thread (the thread executing BlocksOpMode.runOpMode) waits for the script to finish
        // When the script finishes, it calls BlocksOpModeAccess.scriptFinished() (on the Java
        // Bridge thread), which will set scriptFinished to true and call
        // scriptFinishedLock.notifyAll(). At that point, the scriptFinished.wait() call below
        // finish, allowing this thread to continue running.

        // If the stop button is pressed, the scriptFinished.wait() call below will be interrrupted
        // and this thread will catch InterruptedException. The script will continue to run and
        // this thread will continue to wait until scriptFinished is set. However, all calls from
        // javascript into java call startBlockExecution. startBlockExecution calls
        // checkIfStopRequested, which will throw an exception if the elapsed time since catching
        // the InterruptedException exceeds msStuckDetectStop. The exception will cause the script
        // to stop immediately, set scriptFinished to true and call scriptFinished.notifyAll().

        RobotLog.i(getLogPrefix() + "runOpMode - before while !scriptFinished loop");
        while (!scriptFinished.get()) {
          try {
            scriptFinishedLock.wait();
          } catch (InterruptedException e) {
            RobotLog.e(getLogPrefix() + "runOpMode - caught InterruptedException during scriptFinishedLock.wait");
            interrupted = true;
            interruptedTime.set(System.currentTimeMillis());
            /* Non-blocks specific code running on the Java bridge thread is unable to call isStopRequestedForBlocks.
               For that code, it's important to interrupt the thread, so that the normal interrupt handling code runs. */
            if (javaBridgeThread != null) {
              javaBridgeThread.interrupt();
            }
          }
        }
        RobotLog.i(getLogPrefix() + "runOpMode - after while !scriptFinished loop");
      }

      // Clean up the WebView component by calling clearScript on the UI thread.
      appUtil.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          try {
            RobotLog.i(getLogPrefix() + "run2 - before clearScript");
            clearScript();
            RobotLog.i(getLogPrefix() + "run2 - after clearScript");
          } catch (Exception e) {
            RobotLog.e(getLogPrefix() + "run2 - caught " + e);
            // The exception may not have a stacktrace, so we check before calling
            // RobotLog.logStackTrace.
            if (e.getStackTrace() != null) {
              RobotLog.logStackTrace(e);
            }
          }
        }
      });

      // If an InterruptedException was caught, call Thread.currentThread().interrupt() to set
      // the interrupted status.

      if (interrupted) {
        Thread.currentThread().interrupt();
      }

      // If there was an exception, throw it now.
      RuntimeException fatalException = fatalExceptionHolder.getAndSet(null);
      if (fatalException != null) {
        throw fatalException;
      }

      // If there was a fatal error in the WebView component, set the global error message.
      String fatalErrorMessage = fatalErrorMessageHolder.getAndSet(null);
      if (fatalErrorMessage != null) {
        RobotLog.setGlobalErrorMsg(fatalErrorMessage);
      }
    } finally {
      long interruptedTime = this.interruptedTime.get();
      if (interruptedTime != 0L) {
        RobotLog.i(getLogPrefix() + "runOpMode - end - " +
            (System.currentTimeMillis() - interruptedTime) + "ms after InterruptedException");
      } else {
        RobotLog.i(getLogPrefix() + "runOpMode - end - no InterruptedException");
      }
      BlocksOpModeCompanion.opMode = null;
      BlocksOpModeCompanion.linearOpMode = null;
    }
  }

  private void cleanUpPreviousBlocksOpMode() {
    String name = nameOfOpModeLoadedIntoWebView.get();
    if (name != null) {
      RobotLog.w(getLogPrefix() + "cleanUpPreviousBlocksOpMode - Warning: The Blocks runtime system is still loaded " +
          "with the Blocks OpMode named " + name + ".");
      RobotLog.w(getLogPrefix() + "cleanUpPreviousBlocksOpMode - Trying to clean up now.");
      AppUtil.getInstance().synchronousRunOnUiThread(new Runnable() {
        @Override
        public void run() {
          try {
            RobotLog.w(getLogPrefix() + "cleanUpPreviousBlocksOpMode run - before clearScript");
            clearScript();
            RobotLog.w(getLogPrefix() + "cleanUpPreviousBlocksOpMode run - after clearScript");
          } catch (Exception e) {
            RobotLog.e(getLogPrefix() + "cleanUpPreviousBlocksOpMode run - caught " + e);
            // The exception may not have a stacktrace, so we check before calling
            // RobotLog.logStackTrace.
            if (e.getStackTrace() != null) {
              RobotLog.logStackTrace(e);
            }
          }
        }
      });
      if (nameOfOpModeLoadedIntoWebView.get() != null) {
        RobotLog.w(getLogPrefix() + "cleanUpPreviousBlocksOpMode - Clean up was successful.");
      } else {
        RobotLog.e(getLogPrefix() + "cleanUpPreviousBlocksOpMode - Error: Clean up failed.");
        throw new RuntimeException(
            "Unable to start running the Blocks OpMode named " + project + ". The Blocks runtime " +
            "system is still loaded with the previous Blocks OpMode named " + name + ". " +
            "Please restart the Robot Controller app.");
      }
    }
  }

  @SuppressLint("JavascriptInterface")
  private void addJavascriptInterfaces(HardwareItemMap hardwareItemMap, Set<String> identifiersUsed) {
    addJavascriptInterfacesForIdentifiers();
    addObsoleteJavascriptInterfaces();
    // Make sure that all identifiers have been added.
    for (Identifier identifier : Identifier.values()) {
      if (!javascriptInterfaces.containsKey(identifier.identifierForJavaScript)) {
        throw new RuntimeException("There is no javascript interface for Identifier." + identifier);
      }
    }

    addJavascriptInterfacesForHardware(hardwareItemMap, identifiersUsed);

    for (Map.Entry<String, Access> entry : javascriptInterfaces.entrySet()) {
      String identifier = entry.getKey();
      Access access = entry.getValue();
      webView.addJavascriptInterface(access, identifier);
    }
  }

  private void addJavascriptInterface(String identifier, Access access) {
    if (javascriptInterfaces.containsKey(identifier)) {
      throw new RuntimeException("Duplicate identifier: " + identifier);
    }
    javascriptInterfaces.put(identifier, access);
  }

  // Visible for testing.
  void addJavascriptInterfacesForIdentifiers() {
    addJavascriptInterface(Identifier.ACCELERATION.identifierForJavaScript,
        new AccelerationAccess(this, Identifier.ACCELERATION.identifierForJavaScript));
    addJavascriptInterface(Identifier.ANDROID_ACCELEROMETER.identifierForJavaScript,
        new AndroidAccelerometerAccess(this, Identifier.ANDROID_ACCELEROMETER.identifierForJavaScript));
    addJavascriptInterface(Identifier.ANDROID_GYROSCOPE.identifierForJavaScript,
        new AndroidGyroscopeAccess(this, Identifier.ANDROID_GYROSCOPE.identifierForJavaScript));
    addJavascriptInterface(Identifier.ANDROID_ORIENTATION.identifierForJavaScript,
        new AndroidOrientationAccess(this, Identifier.ANDROID_ORIENTATION.identifierForJavaScript));
    addJavascriptInterface(Identifier.ANDROID_SOUND_POOL.identifierForJavaScript,
        new AndroidSoundPoolAccess(this, Identifier.ANDROID_SOUND_POOL.identifierForJavaScript));
    addJavascriptInterface(Identifier.ANDROID_TEXT_TO_SPEECH.identifierForJavaScript,
        new AndroidTextToSpeechAccess(this, Identifier.ANDROID_TEXT_TO_SPEECH.identifierForJavaScript));
    addJavascriptInterface(Identifier.ANGULAR_VELOCITY.identifierForJavaScript,
        new AngularVelocityAccess(this, Identifier.ANGULAR_VELOCITY.identifierForJavaScript));
    addJavascriptInterface(Identifier.APRIL_TAG.identifierForJavaScript,
        new AprilTagAccess(this, Identifier.APRIL_TAG.identifierForJavaScript));
    addJavascriptInterface(Identifier.BLINKIN_PATTERN.identifierForJavaScript,
        new BlinkinPatternAccess(this, Identifier.BLINKIN_PATTERN.identifierForJavaScript));
    addJavascriptInterface(Identifier.BNO055IMU_PARAMETERS.identifierForJavaScript,
        new BNO055IMUParametersAccess(this, Identifier.BNO055IMU_PARAMETERS.identifierForJavaScript));
    addJavascriptInterface(Identifier.COLOR.identifierForJavaScript,
        new ColorAccess(this, Identifier.COLOR.identifierForJavaScript, activity));
    addJavascriptInterface(Identifier.COLOR_BLOB_LOCATOR.identifierForJavaScript,
        new ColorBlobLocatorAccess(this, Identifier.COLOR_BLOB_LOCATOR.identifierForJavaScript));
    addJavascriptInterface(Identifier.DBG_LOG.identifierForJavaScript,
        new DbgLogAccess(this, Identifier.DBG_LOG.identifierForJavaScript));
    addJavascriptInterface(Identifier.ELAPSED_TIME.identifierForJavaScript,
        new ElapsedTimeAccess(this, Identifier.ELAPSED_TIME.identifierForJavaScript));
    addJavascriptInterface(Identifier.EXPOSURE_CONTROL.identifierForJavaScript,
        new ExposureControlAccess(this, Identifier.EXPOSURE_CONTROL.identifierForJavaScript));
    addJavascriptInterface(Identifier.FOCUS_CONTROL.identifierForJavaScript,
        new FocusControlAccess(this, Identifier.FOCUS_CONTROL.identifierForJavaScript));
    addJavascriptInterface(Identifier.GAIN_CONTROL.identifierForJavaScript,
        new GainControlAccess(this, Identifier.GAIN_CONTROL.identifierForJavaScript));
    addJavascriptInterface(Identifier.GAMEPAD_1.identifierForJavaScript,
        new GamepadAccess(this, Identifier.GAMEPAD_1.identifierForJavaScript, gamepad1));
    addJavascriptInterface(Identifier.GAMEPAD_2.identifierForJavaScript,
        new GamepadAccess(this, Identifier.GAMEPAD_2.identifierForJavaScript, gamepad2));
    addJavascriptInterface(Identifier.IMU_PARAMETERS.identifierForJavaScript,
        new ImuParametersAccess(this, Identifier.IMU_PARAMETERS.identifierForJavaScript));
    addJavascriptInterface(Identifier.LED_EFFECT.identifierForJavaScript,
        new LedEffectAccess(this, Identifier.LED_EFFECT.identifierForJavaScript));
    addJavascriptInterface(Identifier.LL_RESULT.identifierForJavaScript,
        new LLResultAccess(this, Identifier.LL_RESULT.identifierForJavaScript));
    addJavascriptInterface(Identifier.LL_STATUS.identifierForJavaScript,
        new LLStatusAccess(this, Identifier.LL_STATUS.identifierForJavaScript));
    addJavascriptInterface(Identifier.LINEAR_OP_MODE.identifierForJavaScript,
        new LinearOpModeAccess(this, Identifier.LINEAR_OP_MODE.identifierForJavaScript, project));
    addJavascriptInterface(Identifier.MAGNETIC_FLUX.identifierForJavaScript,
        new MagneticFluxAccess(this, Identifier.MAGNETIC_FLUX.identifierForJavaScript));
    addJavascriptInterface(Identifier.MATRIX_F.identifierForJavaScript,
        new MatrixFAccess(this, Identifier.MATRIX_F.identifierForJavaScript));
    addJavascriptInterface(Identifier.MISC.identifierForJavaScript,
        new MiscAccess(this, Identifier.MISC.identifierForJavaScript, hardwareMap));
    addJavascriptInterface(Identifier.NAVIGATION.identifierForJavaScript,
        new NavigationAccess(this, Identifier.NAVIGATION.identifierForJavaScript, hardwareMap));
    addJavascriptInterface(Identifier.OPENCV.identifierForJavaScript,
        new OpencvAccess(this, Identifier.OPENCV.identifierForJavaScript));
    addJavascriptInterface(Identifier.OPEN_GL_MATRIX.identifierForJavaScript,
        new OpenGLMatrixAccess(this, Identifier.OPEN_GL_MATRIX.identifierForJavaScript));
    addJavascriptInterface(Identifier.ORIENTATION.identifierForJavaScript,
        new OrientationAccess(this, Identifier.ORIENTATION.identifierForJavaScript));
    addJavascriptInterface(Identifier.PIDF_COEFFICIENTS.identifierForJavaScript,
        new PIDFCoefficientsAccess(this, Identifier.PIDF_COEFFICIENTS.identifierForJavaScript));
    addJavascriptInterface(Identifier.POSITION.identifierForJavaScript,
        new PositionAccess(this, Identifier.POSITION.identifierForJavaScript));
    addJavascriptInterface(Identifier.PREDOMINANT_COLOR.identifierForJavaScript,
        new PredominantColorAccess(this, Identifier.PREDOMINANT_COLOR.identifierForJavaScript));
    addJavascriptInterface(Identifier.PTZ_CONTROL.identifierForJavaScript,
        new PtzControlAccess(this, Identifier.PTZ_CONTROL.identifierForJavaScript));
    addJavascriptInterface(Identifier.QUATERNION.identifierForJavaScript,
        new QuaternionAccess(this, Identifier.QUATERNION.identifierForJavaScript));
    addJavascriptInterface(Identifier.RANGE.identifierForJavaScript,
        new RangeAccess(this, Identifier.RANGE.identifierForJavaScript));
    addJavascriptInterface(Identifier.REV_HUB_ORIENTATION_ON_ROBOT.identifierForJavaScript,
        new RevHubOrientationOnRobotAccess(this, Identifier.REV_HUB_ORIENTATION_ON_ROBOT.identifierForJavaScript));
    addJavascriptInterface(Identifier.REV_9AXIS_IMU_ORIENTATION_ON_ROBOT.identifierForJavaScript,
        new Rev9AxisImuOrientationOnRobotAccess(this, Identifier.REV_9AXIS_IMU_ORIENTATION_ON_ROBOT.identifierForJavaScript));
    addJavascriptInterface(Identifier.RUMBLE_EFFECT.identifierForJavaScript,
        new RumbleEffectAccess(this, Identifier.RUMBLE_EFFECT.identifierForJavaScript));
    addJavascriptInterface(Identifier.SYSTEM.identifierForJavaScript,
        new SystemAccess(this, Identifier.SYSTEM.identifierForJavaScript));
    addJavascriptInterface(Identifier.TELEMETRY.identifierForJavaScript,
        new TelemetryAccess(this, Identifier.TELEMETRY.identifierForJavaScript, telemetry));
    addJavascriptInterface(Identifier.TEMPERATURE.identifierForJavaScript,
        new TemperatureAccess(this, Identifier.TEMPERATURE.identifierForJavaScript));
    addJavascriptInterface(Identifier.VECTOR_F.identifierForJavaScript,
        new VectorFAccess(this, Identifier.VECTOR_F.identifierForJavaScript));
    addJavascriptInterface(Identifier.VELOCITY.identifierForJavaScript,
        new VelocityAccess(this, Identifier.VELOCITY.identifierForJavaScript));
    addJavascriptInterface(Identifier.VISION_PORTAL.identifierForJavaScript,
        new VisionPortalAccess(this, Identifier.VISION_PORTAL.identifierForJavaScript, hardwareMap));
    addJavascriptInterface(Identifier.WHITE_BALANCE_CONTROL.identifierForJavaScript,
        new WhiteBalanceControlAccess(this, Identifier.WHITE_BALANCE_CONTROL.identifierForJavaScript));
    addJavascriptInterface(Identifier.YAW_PITCH_ROLL_ANGLES.identifierForJavaScript,
        new YawPitchRollAnglesAccess(this, Identifier.YAW_PITCH_ROLL_ANGLES.identifierForJavaScript));
  }

  // Visible for testing.
  void addObsoleteJavascriptInterfaces() {
    addJavascriptInterface(Identifier.OBSOLETE_TENSOR_FLOW.identifierForJavaScript,
        new TensorFlowAccess(this, Identifier.OBSOLETE_TENSOR_FLOW.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_TFOD.identifierForJavaScript,
        new TfodAccess(this, Identifier.OBSOLETE_TFOD.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_TFOD_CURRENT_GAME.identifierForJavaScript,
        new TfodCurrentGameAccess(this, Identifier.OBSOLETE_TFOD_CURRENT_GAME.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_TFOD_CUSTOM_MODEL.identifierForJavaScript,
        new TfodCustomModelAccess(this, Identifier.OBSOLETE_TFOD_CUSTOM_MODEL.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_TFOD_ROVER_RUCKUS.identifierForJavaScript,
        new TfodRoverRuckusAccess(this, Identifier.OBSOLETE_TFOD_ROVER_RUCKUS.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_TFOD_SKY_STONE.identifierForJavaScript,
        new TfodSkyStoneAccess(this, Identifier.OBSOLETE_TFOD_SKY_STONE.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_VUFORIA_CURRENT_GAME.identifierForJavaScript,
        new VuforiaCurrentGameAccess(this, Identifier.OBSOLETE_VUFORIA_CURRENT_GAME.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_VUFORIA_RELIC_RECOVERY.identifierForJavaScript,
        new VuforiaRelicRecoveryAccess(this, Identifier.OBSOLETE_VUFORIA_RELIC_RECOVERY.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_VUFORIA_ROVER_RUCKUS.identifierForJavaScript,
        new VuforiaRoverRuckusAccess(this, Identifier.OBSOLETE_VUFORIA_ROVER_RUCKUS.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_VUFORIA_SKY_STONE.identifierForJavaScript,
        new VuforiaSkyStoneAccess(this, Identifier.OBSOLETE_VUFORIA_SKY_STONE.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_VUFORIA_LOCALIZER.identifierForJavaScript,
        new VuforiaLocalizerAccess(this, Identifier.OBSOLETE_VUFORIA_LOCALIZER.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_VUFORIA_LOCALIZER_PARAMETERS.identifierForJavaScript,
        new VuforiaLocalizerParametersAccess(this, Identifier.OBSOLETE_VUFORIA_LOCALIZER_PARAMETERS.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_VUFORIA_TRACKABLE.identifierForJavaScript,
        new VuforiaTrackableAccess(this, Identifier.OBSOLETE_VUFORIA_TRACKABLE.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_VUFORIA_TRACKABLE_DEFAULT_LISTENER.identifierForJavaScript,
        new VuforiaTrackableDefaultListenerAccess(this, Identifier.OBSOLETE_VUFORIA_TRACKABLE_DEFAULT_LISTENER.identifierForJavaScript));
    addJavascriptInterface(Identifier.OBSOLETE_VUFORIA_TRACKABLES.identifierForJavaScript,
        new VuforiaTrackablesAccess(this, Identifier.OBSOLETE_VUFORIA_TRACKABLES.identifierForJavaScript));
  }

  private void addJavascriptInterfacesForHardware(HardwareItemMap hardwareItemMap, Set<String> identifiersUsed) {
    for (HardwareType hardwareType : HardwareType.values()) {
      if (hardwareItemMap.contains(hardwareType)) {
        for (HardwareItem hardwareItem : hardwareItemMap.getHardwareItems(hardwareType)) {
          // Don't instantiate the HardwareAccess instance if the identifier isn't used in the
          // Blocks OpMode.
          if (identifiersUsed != null && !identifiersUsed.contains(hardwareItem.identifier)) {
            RobotLog.i(getLogPrefix() + "Skipping hardware device named \"" +
                hardwareItem.deviceName + "\". It isn't used in this Blocks OpMode.");
            continue;
          }
          if (javascriptInterfaces.containsKey(hardwareItem.identifier)) {
            RobotLog.w(getLogPrefix() + "There is already a JavascriptInterface for identifier \"" +
                hardwareItem.identifier + "\". Ignoring hardware type " + hardwareType + ".");
            continue;
          }
          Access access =
              HardwareAccess.newHardwareAccess(this, hardwareType, hardwareMap, hardwareItem);
          if (access != null) {
            javascriptInterfaces.put(hardwareItem.identifier, access);
          }
        }
      }
    }
  }

  private void removeJavascriptInterfaces() {
    Iterator<Map.Entry<String, Access>> it = javascriptInterfaces.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, Access> entry = it.next();
      String identifier = entry.getKey();
      Access access = entry.getValue();
      webView.removeJavascriptInterface(identifier);
      access.close();
      it.remove();
    }
  }

  private class BlocksOpModeAccess extends Access {
    private final Object scriptFinishedLock;
    private final AtomicBoolean scriptFinished;

    private BlocksOpModeAccess(String identifier, Object scriptFinishedLock, AtomicBoolean scriptFinished) {
      super(BlocksOpMode.this, identifier, "");
      this.scriptFinishedLock = scriptFinishedLock;
      this.scriptFinished = scriptFinished;
    }

    @SuppressWarnings("unused")
    @JavascriptInterface
    public void scriptStarting() {
      RobotLog.i(getLogPrefix() + "scriptStarting");
      /* Clear the interrupt flag on this thread (the JavaBridge thread), which may have been set
         during a previous Blocks OpMode run.

         Note that this thread is tied to the WebView, which is why it is reused for all Blocks
         OpModes.

         We clear the interrupt flag by calling the (normally undesirable) method Thread.interrupted().
      */

      //noinspection ResultOfMethodCallIgnored
      Thread.interrupted();
      javaBridgeThread = Thread.currentThread();
    }

    @SuppressWarnings("unused")
    @JavascriptInterface
    public void caughtException(String message, String currentBlockLabel) {
      if (wasTerminated) {
        return;
      }

      if (message != null) {
        // If a hardware device is used in blocks, but has been removed (or renamed) in the
        // configuration, the message is like "ReferenceError: left_drive is not defined".
        if (message.startsWith("ReferenceError: ") && message.endsWith(" is not defined")) {
          String missingIdentifier = message.substring(16, message.length() - 15);

          String errorMessage = "Could not find identifier: " + missingIdentifier;

          // See if we can improve the error message.
          String missingHardwareDeviceName = missingIdentifierToHardwareDeviceName(missingIdentifier);
          if (missingHardwareDeviceName != null) {
            errorMessage = "Could not find hardware device: " + missingHardwareDeviceName;

            if (missingIdentifier.endsWith(HardwareType.BNO055IMU.identifierSuffixForJavaScript)) {
              if (hardwareMap.getAllNames(BNO055IMUImpl.class).isEmpty()) {
                // There is no BNO055 configured under a different name. Check if the user is using
                // the wrong blocks for their IMU.
                String wrongImuErrorMessage = getWrongImuErrorMessage();
                if (wrongImuErrorMessage != null) {
                  errorMessage += "\n\n" + wrongImuErrorMessage;
                }
              }
            }
          }

          fatalErrorMessageHolder.compareAndSet(null, errorMessage);
          return;
        }

        if (forceStopped)
        {
           AppUtil.getInstance().showAlertDialog(UILocation.BOTH, "OpMode Force-Stopped",
               "User OpMode was stuck in stop(), but was able to be force stopped without " +
               "restarting the app. Please make sure you are calling opModeInInit() or " +
               "opModeIsActive() in any loops!");

           // Get out of dodge so we don't force a restart by setting a global error
           return;
        }

        // An exception occured while the Blocks OpMode was executing.
        // If the currentBlockLabel parameter is not empty, it is the label of the block that caused the exception.
        if (currentBlockLabel != null && !currentBlockLabel.isEmpty()) {
          fatalErrorMessageHolder.compareAndSet(null,
              "Fatal error occurred while executing the block labeled \"" +
              currentBlockLabel + "\". " + message);
        } else {
          // Otherwise, we use the label of the last block whose java code called
          // startBlockExecution.
          if (currentBlockFinished) {
            fatalErrorMessageHolder.compareAndSet(null,
                "Fatal error occurred after executing the block labeled \"" +
                getFullBlockLabel() + "\". " + message);
          } else {
            fatalErrorMessageHolder.compareAndSet(null,
                "Fatal error occurred while executing the block labeled \"" +
                getFullBlockLabel() + "\". " + message);
          }
        }
      }

      RobotLog.e(getLogPrefix() + "caughtException - message is " + message);
    }

    private String getWrongImuErrorMessage() {
      LynxModule controlHub = (LynxModule) EmbeddedControlHubModule.get();
      if (controlHub != null) {
        LynxModuleImuType controlHubImuType = controlHub.getImuType();
        if (controlHubImuType == LynxModuleImuType.BHI260) {
          return "You attempted to use a BNO055 IMU on a Control Hub that contains a BHI260AP IMU. " +
                  "You need to update your OpMode to use the IMU blocks instead of the IMU-BNO055 blocks.";
        }
      }
      return null;
    }

    @SuppressWarnings("unused")
    @JavascriptInterface
    public void scriptFinished() {
      RobotLog.i(getLogPrefix() + "scriptFinished");
      synchronized (scriptFinishedLock) {
        scriptFinished.set(true);
        scriptFinishedLock.notifyAll();
      }
    }
  }

  private static String missingIdentifierToHardwareDeviceName(String identifier) {
    for (HardwareType hardwareType : HardwareType.values()) {
      if (identifier.endsWith(hardwareType.identifierSuffixForJavaScript)) {
        return identifier.substring(0, identifier.length() - hardwareType.identifierSuffixForJavaScript.length());
      }
    }
    return null;
  }

  private void loadScript() throws IOException {
    RobotLog.i(getLogPrefix() + "loadScript - WebView user agent is \"" + webView.getSettings().getUserAgentString() + "\"");
    nameOfOpModeLoadedIntoWebView.set(project);
    HardwareItemMap hardwareItemMap = HardwareItemMap.newHardwareItemMap(hardwareMap);

    // Check if the javascript begins with a comment telling us what the identifiers are.
    Set<String> identifiersUsed = null;
    String jsFileContent = ProjectsUtil.fetchJsFileContent(project);
    if (jsFileContent.startsWith(HardwareUtil.IDENTIFIERS_USED_PREFIX)) {
      int eol = jsFileContent.indexOf("\n");
      identifiersUsed = new HashSet<>(Arrays.asList(
          jsFileContent.substring(HardwareUtil.IDENTIFIERS_USED_PREFIX.length(), eol).split(",")));
      jsFileContent = jsFileContent.substring(eol);
    }

    addJavascriptInterfaces(hardwareItemMap, identifiersUsed);

    String jsContent = HardwareUtil.upgradeJs(jsFileContent, hardwareItemMap);

    StringBuilder html = new StringBuilder()
        .append("<html><body onload='callRunOpMode()'><script type='text/javascript'>\n");
    FileUtil.readAsset(html, activity.getAssets(), "blocks/runtime.js");
    html.append("\n")
        .append(jsContent)
        .append("\n</script></body></html>\n");
    webView.loadDataWithBaseURL(
        null /* baseUrl */, html.toString(), "text/html", "UTF-8", null /* historyUrl */);
  }

  private void clearScript() {
    removeJavascriptInterfaces();
    if (!javascriptInterfaces.isEmpty()) {
      RobotLog.w(getLogPrefix() + "clearScript - Warning: javascriptInterfaces is not empty.");
    }
    javascriptInterfaces.clear();

    webView.loadDataWithBaseURL(
        null /* baseUrl */, "", "text/html", "UTF-8", null /* historyUrl */);
    nameOfOpModeLoadedIntoWebView.set(null);
  }

  /**
   * Sets the {@link WebView} so that all BlocksOpModes can access it.
   */
  @SuppressLint("setJavaScriptEnabled")
  public static void setActivityAndWebView(Activity a, WebView wv) {
    if (activity == null && webView == null) {
      addOpModeRegistrar();
    }

    activity = a;
    webView = wv;
    webView.getSettings().setJavaScriptEnabled(true);

    webView.setWebChromeClient(new WebChromeClient() {
      @Override
      public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return false; // continue with console logging.
      }
    });
  }

  private static void addOpModeRegistrar() {
    RegisteredOpModes.getInstance().addInstanceOpModeRegistrar(new InstanceOpModeRegistrar() {
      @Override public void register(InstanceOpModeManager manager) {
        try {
          // fetchEnabledProjectsWithJavaScript is thread-safe wrt concurrent saves from the browswer
          List<OpModeMeta> projects = ProjectsUtil.fetchEnabledProjectsWithJavaScript();
          for (OpModeMeta opModeMeta : projects) {
            manager.register(opModeMeta, new BlocksOpMode(opModeMeta.name));
          }
        } catch (Exception e) {
          RobotLog.logStackTrace(e);
        }
      }
    });
  }

  /**
   * @deprecated functionality now automatically called by the system
   */
  @Deprecated
  public static void registerAll(OpModeManager manager) {
    RobotLog.w(BlocksOpMode.class.getSimpleName(), "registerAll(OpModeManager) is deprecated and will be removed soon, as calling it is unnecessary in this and future API version");
  }
}

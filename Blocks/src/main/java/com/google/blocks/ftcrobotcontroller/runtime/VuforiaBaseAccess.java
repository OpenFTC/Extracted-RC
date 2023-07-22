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

package com.google.blocks.ftcrobotcontroller.runtime;

import static com.google.blocks.ftcrobotcontroller.hardware.HardwareUtil.SWITCHABLE_CAMERA_NAME;

import android.util.Pair;
import android.webkit.JavascriptInterface;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.Parameters.CameraMonitorFeedback;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaBase;

/**
 * An abstract class for classes that provides JavaScript access to a {@link VuforiaBase}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
abstract class VuforiaBaseAccess<T extends VuforiaBase> extends Access {
  private final HardwareMap hardwareMap;
  private T vuforiaBase;

  VuforiaBaseAccess(BlocksOpMode blocksOpMode, String identifier, HardwareMap hardwareMap,
      String blockFirstName) {
    super(blocksOpMode, identifier, blockFirstName);
    this.hardwareMap = hardwareMap;
  }

  private boolean checkAndSetVuforiaBase() {
    if (vuforiaBase != null) {
      reportWarning(blockFirstName + ".initialize has already been called!");
      return false;
    }
    vuforiaBase = createVuforia();
    return true;
  }

  protected abstract T createVuforia();

  T getVuforiaBase() {
    if (vuforiaBase == null) {
      reportWarning("You forgot to call " + blockFirstName + ".initialize!");
      return null;
    }
    return vuforiaBase;
  }

  // Access methods

  @Override
  void close() {
    if (vuforiaBase != null) {
      vuforiaBase.close();
      vuforiaBase = null;
    }
  }

  // Javascript methods

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VuforiaBase.class, methodName = "initialize")
  public void initialize_withCameraDirection(String vuforiaLicenseKey, String cameraDirectionString,
      boolean useExtendedTracking, boolean enableCameraMonitoring, String cameraMonitorFeedbackString,
      float dx, float dy, float dz, float xAngle, float yAngle, float zAngle,
      boolean useCompetitionFieldTargetLocations) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".initialize");
      internalInitializeWithCameraDirection(cameraDirectionString,
          useExtendedTracking, enableCameraMonitoring, cameraMonitorFeedbackString,
          dx, dy, dz, "XYZ", xAngle, yAngle, zAngle,
          useCompetitionFieldTargetLocations);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VuforiaBase.class, methodName = "initialize")
  public void initialize_withCameraDirection_2(String cameraDirectionString,
      boolean useExtendedTracking, boolean enableCameraMonitoring, String cameraMonitorFeedbackString,
      float dx, float dy, float dz,
      String axesOrderString, float firstAngle, float secondAngle, float thirdAngle,
      boolean useCompetitionFieldTargetLocations) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".initialize");
      internalInitializeWithCameraDirection(cameraDirectionString,
          useExtendedTracking, enableCameraMonitoring, cameraMonitorFeedbackString,
          dx, dy, dz, axesOrderString, firstAngle, secondAngle, thirdAngle,
          useCompetitionFieldTargetLocations);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  protected void internalInitializeWithCameraDirection(String cameraDirectionString,
      boolean useExtendedTracking, boolean enableCameraMonitoring, String cameraMonitorFeedbackString,
      float dx, float dy, float dz,
      String axesOrderString, float firstAngle, float secondAngle, float thirdAngle,
      boolean useCompetitionFieldTargetLocations) {
    CameraDirection cameraDirection = checkVuforiaLocalizerCameraDirection(cameraDirectionString);
    AxesOrder axesOrder = checkAxesOrder(axesOrderString);
    Pair<Boolean, CameraMonitorFeedback> cameraMonitorFeedback =
        checkCameraMonitorFeedback(cameraMonitorFeedbackString);
    if (cameraDirection != null && axesOrder != null && cameraMonitorFeedback.first && checkAndSetVuforiaBase()) {
      String vuforiaLicenseKey = "";
      vuforiaBase.initialize(vuforiaLicenseKey, cameraDirection,
          useExtendedTracking, enableCameraMonitoring, cameraMonitorFeedback.second,
          dx, dy, dz, axesOrder, firstAngle, secondAngle, thirdAngle, useCompetitionFieldTargetLocations);
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VuforiaBase.class, methodName = "initialize")
  public void initialize_withWebcam(String cameraNameString, String webcamCalibrationFilename,
      boolean useExtendedTracking, boolean enableCameraMonitoring, String cameraMonitorFeedbackString,
      float dx, float dy, float dz, float xAngle, float yAngle, float zAngle,
      boolean useCompetitionFieldTargetLocations) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".initialize");
      internalInitializeWithWebcam(cameraNameString, webcamCalibrationFilename,
          useExtendedTracking, enableCameraMonitoring, cameraMonitorFeedbackString,
          dx, dy, dz, "XYZ", xAngle, yAngle, zAngle,
          useCompetitionFieldTargetLocations);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VuforiaBase.class, methodName = "initialize")
  public void initialize_withWebcam_2(String cameraNameString, String webcamCalibrationFilename,
      boolean useExtendedTracking, boolean enableCameraMonitoring, String cameraMonitorFeedbackString,
      float dx, float dy, float dz,
      String axesOrderString, float firstAngle, float secondAngle, float thirdAngle,
      boolean useCompetitionFieldTargetLocations) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".initialize");
      internalInitializeWithWebcam(cameraNameString, webcamCalibrationFilename,
          useExtendedTracking, enableCameraMonitoring, cameraMonitorFeedbackString,
          dx, dy, dz, axesOrderString, firstAngle, secondAngle, thirdAngle,
          useCompetitionFieldTargetLocations);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  protected void internalInitializeWithWebcam(String cameraNameString, String webcamCalibrationFilename,
      boolean useExtendedTracking, boolean enableCameraMonitoring, String cameraMonitorFeedbackString,
      float dx, float dy, float dz,
      String axesOrderString, float firstAngle, float secondAngle, float thirdAngle,
      boolean useCompetitionFieldTargetLocations) {
    CameraName cameraName;
    if (cameraNameString.equals(SWITCHABLE_CAMERA_NAME)) {
      cameraName = blocksOpMode.getSwitchableCamera();
    } else {
      cameraName = checkCameraNameFromString(hardwareMap, cameraNameString);
    }
    AxesOrder axesOrder = checkAxesOrder(axesOrderString);
    Pair<Boolean, CameraMonitorFeedback> cameraMonitorFeedback =
        checkCameraMonitorFeedback(cameraMonitorFeedbackString);
    if (cameraName != null && axesOrder != null && cameraMonitorFeedback.first && checkAndSetVuforiaBase()) {
      String vuforiaLicenseKey = "";
      vuforiaBase.initialize(vuforiaLicenseKey, cameraName, webcamCalibrationFilename,
          useExtendedTracking, enableCameraMonitoring, cameraMonitorFeedback.second,
          dx, dy, dz, axesOrder, firstAngle, secondAngle, thirdAngle, useCompetitionFieldTargetLocations);
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VuforiaBase.class, methodName = "activate")
  public void activate() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".activate");
      try {
        vuforiaBase.activate();
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VuforiaBase.class, methodName = "deactivate")
  public void deactivate() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".deactivate");
      try {
        vuforiaBase.deactivate();
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VuforiaBase.class, methodName = "setActiveCamera")
  public void setActiveCamera(String cameraNameString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".deactivate");
      CameraName cameraName = checkCameraNameFromString(hardwareMap, cameraNameString);
      if (cameraName != null) {
        try {
          vuforiaBase.setActiveCamera(cameraName);
        } catch (IllegalStateException e) {
          reportWarning(e.getMessage());
        }
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VuforiaBase.class, methodName = "track")
  public String track(String name) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".track");
      try {
        return vuforiaBase.track(name).toJson();
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      } catch (IllegalArgumentException e) {
        reportInvalidArg("name", vuforiaBase.printTrackableNames());
      }
      return vuforiaBase.emptyTrackingResults(name).toJson();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VuforiaBase.class, methodName = "trackPose")
  public String trackPose(String name) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".trackPose");
      try {
        return vuforiaBase.trackPose(name).toJson();
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      } catch (IllegalArgumentException e) {
        reportInvalidArg("name", vuforiaBase.printTrackableNames());
      }
      return vuforiaBase.emptyTrackingResults(name).toJson();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VuforiaBase.class, methodName = "getVuforiaLocalizer")
  public VuforiaLocalizer getVuforiaLocalizer() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getVuforiaLocalizer");
      try {
        return vuforiaBase.getVuforiaLocalizer();
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      }
      return null;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

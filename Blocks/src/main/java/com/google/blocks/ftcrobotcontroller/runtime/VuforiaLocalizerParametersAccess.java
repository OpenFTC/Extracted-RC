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

import android.content.Context;
import android.util.Pair;
import android.webkit.JavascriptInterface;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaBase;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.Parameters;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.Parameters.CameraMonitorFeedback;

/**
 * A class that provides JavaScript access to {@link VuforiaLocalizer#Parameters}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class VuforiaLocalizerParametersAccess extends Access {
  private final Context context;
  private final HardwareMap hardwareMap;

  VuforiaLocalizerParametersAccess(BlocksOpMode blocksOpMode, String identifier, Context context, HardwareMap hardwareMap) {
    super(blocksOpMode, identifier, "VuforiaLocalizer.Parameters");
    this.context = context;
    this.hardwareMap = hardwareMap;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Parameters.class, constructor = true)
  public Parameters create() {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return VuforiaBase.createParameters();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Parameters.class, fieldName = "vuforiaLicenseKey")
  public void setVuforiaLicenseKey(Object parametersArg, String vuforiaLicenseKey) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setVuforiaLicenseKey");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      if (parameters != null) {
        parameters.vuforiaLicenseKey = vuforiaLicenseKey;
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
  @Block(classes = Parameters.class, fieldName = "vuforiaLicenseKey")
  public String getVuforiaLicenseKey(Object parametersArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".VuforiaLicenseKey");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      if (parameters != null) {
        return parameters.vuforiaLicenseKey;
      }
      return null;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Parameters.class, fieldName = "cameraDirection")
  public void setCameraDirection(Object parametersArg, String cameraDirectionString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setCameraDirection");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      CameraDirection cameraDirection = checkVuforiaLocalizerCameraDirection(cameraDirectionString);
      if (parameters != null || cameraDirection != null) {
        parameters.cameraDirection = cameraDirection;
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
  @Block(classes = Parameters.class, fieldName = "cameraDirection")
  public String getCameraDirection(Object parametersArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".CameraDirection");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      if (parameters != null) {
        CameraDirection cameraDirection = parameters.cameraDirection;
        if (cameraDirection != null) {
          return cameraDirection.toString();
        }
      }
      return "";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Parameters.class, fieldName = "cameraName")
  public void setCameraName(Object parametersArg, String cameraNameString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setCameraName");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      if (parameters != null) {
        parameters.cameraName = cameraNameFromString(hardwareMap, cameraNameString);
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
  @Block(classes = Parameters.class, fieldName = "cameraName")
  public String getCameraName(Object parametersArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".CameraName");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      if (parameters != null) {
        return cameraNameToString(hardwareMap, parameters.cameraName);
      }
      return null;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Parameters.class, methodName = "addWebcamCalibrationFile")
  public void addWebcamCalibrationFile(Object parametersArg, String webcamCalibrationFilename) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".addWebcamCalibrationFile");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      if (parameters != null) {
        parameters.addWebcamCalibrationFile(webcamCalibrationFilename);
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
  @Block(classes = Parameters.class, fieldName = "useExtendedTracking")
  public void setUseExtendedTracking(Object parametersArg, boolean useExtendedTracking) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setUseExtendedTracking");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      if (parameters != null) {
        parameters.useExtendedTracking = useExtendedTracking;
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
  @Block(classes = Parameters.class, fieldName = "useExtendedTracking")
  public boolean getUseExtendedTracking(Object parametersArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".UseExtendedTracking");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      if (parameters != null) {
        return parameters.useExtendedTracking;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Parameters.class, fieldName = "cameraMonitorViewIdParent")
  public boolean getEnableCameraMonitoring(Object parametersArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".EnableCameraMonitoring");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      if (parameters != null) {
        return parameters.cameraMonitorViewIdParent != 0;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Parameters.class, fieldName = "cameraMonitorFeedback")
  public void setCameraMonitorFeedback(Object parametersArg, String cameraMonitorFeedbackString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setCameraMonitorFeedback");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      Pair<Boolean, CameraMonitorFeedback> cameraMonitorFeedback =
          checkCameraMonitorFeedback(cameraMonitorFeedbackString);
      if (parameters != null && cameraMonitorFeedback.first) {
        parameters.cameraMonitorFeedback = cameraMonitorFeedback.second;
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
  @Block(classes = Parameters.class, fieldName = "cameraMonitorFeedback")
  public String getCameraMonitorFeedback(Object parametersArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".CameraMonitorFeedback");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      if (parameters != null) {
        CameraMonitorFeedback cameraMonitorFeedback = parameters.cameraMonitorFeedback;
        return (cameraMonitorFeedback == null)
            ? DEFAULT_CAMERA_MONTIOR_FEEDBACK_STRING : cameraMonitorFeedback.toString();
      }
      return "";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Parameters.class, fieldName = "fillCameraMonitorViewParent")
  public void setFillCameraMonitorViewParent(Object parametersArg, boolean fillCameraMonitorViewParent) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setFillCameraMonitorViewParent");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      if (parameters != null) {
        parameters.fillCameraMonitorViewParent = fillCameraMonitorViewParent;
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
  @Block(classes = Parameters.class, fieldName = "fillCameraMonitorViewParent")
  public boolean getFillCameraMonitorViewParent(Object parametersArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".FillCameraMonitorViewParent");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      if (parameters != null) {
        return parameters.fillCameraMonitorViewParent;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Parameters.class, fieldName = "cameraMonitorViewIdParent")
  public void setEnableCameraMonitoring(Object parametersArg, boolean enableCameraMonitoring) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setEnableCameraMonitoring");
      Parameters parameters = checkVuforiaLocalizerParameters(parametersArg);
      if (parameters != null) {
        if (enableCameraMonitoring) {
          parameters.cameraMonitorViewIdParent = context.getResources().getIdentifier(
              "cameraMonitorViewId", "id", context.getPackageName());
        } else {
          parameters.cameraMonitorViewIdParent = 0;
        }
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

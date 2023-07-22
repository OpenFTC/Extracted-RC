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

import android.webkit.JavascriptInterface;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

/**
 * A class that provides JavaScript access to {@link VuforiaTrackableDefaultListener}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class VuforiaTrackableDefaultListenerAccess extends Access {
  private final HardwareMap hardwareMap;

  VuforiaTrackableDefaultListenerAccess(BlocksOpMode blocksOpMode, String identifier, HardwareMap hardwareMap) {
    super(blocksOpMode, identifier, "VuforiaTrackableDefaultListener");
    this.hardwareMap = hardwareMap;
  }

  private VuforiaTrackableDefaultListener checkVuforiaTrackableDefaultListener(
      Object vuforiaTrackableDefaultListenerArg) {
    return checkArg(vuforiaTrackableDefaultListenerArg, VuforiaTrackableDefaultListener.class,
        "vuforiaTrackableDefaultListener");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VuforiaTrackableDefaultListener.class, methodName = "setPhoneInformation")
  public void setPhoneInformation(
      Object vuforiaTrackableDefaultListenerArg, Object phoneLocationOnRobotArg,
      String cameraDirectionString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setPhoneInformation");
      VuforiaTrackableDefaultListener vuforiaTrackableDefaultListener = checkVuforiaTrackableDefaultListener(
          vuforiaTrackableDefaultListenerArg);
      OpenGLMatrix phoneLocationOnRobot = checkOpenGLMatrix(phoneLocationOnRobotArg);
      CameraDirection cameraDirection = checkVuforiaLocalizerCameraDirection(cameraDirectionString);
      if (vuforiaTrackableDefaultListener != null && phoneLocationOnRobot != null && cameraDirection != null) {
        vuforiaTrackableDefaultListener.setPhoneInformation(phoneLocationOnRobot, cameraDirection);
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
  @Block(classes = VuforiaTrackableDefaultListener.class, methodName = "setCameraLocationOnRobot")
  public void setCameraLocationOnRobot(
      Object vuforiaTrackableDefaultListenerArg, String cameraNameString, Object cameraLocationOnRobotArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setCameraLocationOnRobot");
      VuforiaTrackableDefaultListener vuforiaTrackableDefaultListener = checkVuforiaTrackableDefaultListener(
          vuforiaTrackableDefaultListenerArg);
      CameraName cameraName = checkCameraNameFromString(hardwareMap, cameraNameString);
      OpenGLMatrix cameraLocationOnRobot = checkOpenGLMatrix(cameraLocationOnRobotArg);
      if (vuforiaTrackableDefaultListener != null && cameraName != null && cameraLocationOnRobot != null) {
        vuforiaTrackableDefaultListener.setCameraLocationOnRobot(cameraName, cameraLocationOnRobot);
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
  @Block(classes = VuforiaTrackableDefaultListener.class, methodName = "isVisible")
  public boolean isVisible(Object vuforiaTrackableDefaultListenerArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".isVisible");
      VuforiaTrackableDefaultListener vuforiaTrackableDefaultListener = checkVuforiaTrackableDefaultListener(
          vuforiaTrackableDefaultListenerArg);
      if (vuforiaTrackableDefaultListener != null) {
        return vuforiaTrackableDefaultListener.isVisible();
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
  @Block(classes = VuforiaTrackableDefaultListener.class, methodName = "getUpdatedRobotLocation")
  public OpenGLMatrix getUpdatedRobotLocation(Object vuforiaTrackableDefaultListenerArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getUpdatedRobotLocation");
      VuforiaTrackableDefaultListener vuforiaTrackableDefaultListener = checkVuforiaTrackableDefaultListener(
          vuforiaTrackableDefaultListenerArg);
      if (vuforiaTrackableDefaultListener != null) {
        return vuforiaTrackableDefaultListener.getUpdatedRobotLocation();
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
  @Block(classes = VuforiaTrackableDefaultListener.class, methodName = "getPose")
  public OpenGLMatrix getPose(Object vuforiaTrackableDefaultListenerArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getPose");
      VuforiaTrackableDefaultListener vuforiaTrackableDefaultListener = checkVuforiaTrackableDefaultListener(
          vuforiaTrackableDefaultListenerArg);
      if (vuforiaTrackableDefaultListener != null) {
        return vuforiaTrackableDefaultListener.getPose();
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
  @Block(classes = RelicRecoveryVuMark.class, methodName = "from")
  public String getRelicRecoveryVuMark(Object vuforiaTrackableDefaultListenerArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getRelicRecoveryVuMark");
      VuforiaTrackableDefaultListener vuforiaTrackableDefaultListener = checkVuforiaTrackableDefaultListener(
          vuforiaTrackableDefaultListenerArg);
      if (vuforiaTrackableDefaultListener != null) {
        return RelicRecoveryVuMark.from(vuforiaTrackableDefaultListener).toString();
      }
      return RelicRecoveryVuMark.UNKNOWN.toString();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

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
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;

/**
 * A class that provides JavaScript access to various navigation enum methods.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class NavigationAccess extends Access {
  private final HardwareMap hardwareMap;

  NavigationAccess(BlocksOpMode blocksOpMode, String identifier, HardwareMap hardwareMap) {
    super(blocksOpMode, identifier, "");
    this.hardwareMap = hardwareMap;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AngleUnit.class, methodName = "normalize")
  public double angleUnit_normalize(double angle, String angleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AngleUnit", ".normalize");
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (angleUnit != null) {
        return angleUnit.normalize(angle);
      }
      return 0;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AngleUnit.class, methodName = "fromUnit")
  public double angleUnit_convert(double angle, String fromAngleUnitString, String toAngleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AngleUnit", ".convert");
      AngleUnit fromAngleUnit = checkArg(fromAngleUnitString, AngleUnit.class, "from");
      AngleUnit toAngleUnit = checkArg(toAngleUnitString, AngleUnit.class, "to");
      if (fromAngleUnit != null && toAngleUnit != null) {
        return toAngleUnit.fromUnit(fromAngleUnit, angle);
      }
      return 0;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = UnnormalizedAngleUnit.class, methodName = "fromUnit")
  public double unnormalizedAngleUnit_convert(double angle, String fromAngleUnitString, String toAngleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, "UnnormalizedAngleUnit", ".convert");
      AngleUnit fromAngleUnit = checkArg(fromAngleUnitString, AngleUnit.class, "from");
      AngleUnit toAngleUnit = checkArg(toAngleUnitString, AngleUnit.class, "to");
      if (fromAngleUnit != null && toAngleUnit != null) {
        return toAngleUnit.getUnnormalized().fromUnit(fromAngleUnit.getUnnormalized(), angle);
      }
      return 0;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  private BuiltinCameraDirection checkBuiltinCameraDirection(String cameraDirectionString) {
    return checkArg(cameraDirectionString, BuiltinCameraDirection.class, "cameraDirection");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(exclusiveToBlocks = true)
  public BuiltinCameraDirection getBuiltinCameraDirection(String cameraDirectionString) {
    return checkBuiltinCameraDirection(cameraDirectionString);
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(exclusiveToBlocks = true)
  public WebcamName getWebcamName(String webcamNameString) {
    return hardwareMap.tryGet(WebcamName.class, webcamNameString);
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(exclusiveToBlocks = true)
  public CameraName createSwitchableCameraNameForAllWebcams() {
    return ClassFactory.createSwitchableCameraNameForAllWebcams(hardwareMap);
  }
}

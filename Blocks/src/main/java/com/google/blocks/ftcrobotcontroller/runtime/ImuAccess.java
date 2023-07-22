/**
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.blocks.ftcrobotcontroller.runtime;

import android.webkit.JavascriptInterface;
import com.google.blocks.ftcrobotcontroller.hardware.HardwareItem;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.IMU.Parameters;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

/**
 * A class that provides JavaScript access to {@link IMU}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class ImuAccess extends HardwareAccess<IMU> {
  private final IMU imu;

  ImuAccess(BlocksOpMode blocksOpMode, HardwareItem hardwareItem, HardwareMap hardwareMap) {
    super(blocksOpMode, hardwareItem, hardwareMap, IMU.class);
    this.imu = hardwareDevice;
  }

  private IMU.Parameters checkImuParameters(Object parametersArg) {
    return checkArg(parametersArg, IMU.Parameters.class, "parameters");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {IMU.class}, methodName = "initialize")
  public void initialize(Object parametersArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".initialize");
      Parameters parameters = checkImuParameters(parametersArg);
      if (parameters != null) {
        if (!imu.initialize(parameters)) {
          reportWarning("IMU initialization failed");
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
  @Block(classes = {IMU.class}, methodName = "getRobotAngularVelocity")
  public AngularVelocity getRobotAngularVelocity(String angleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getRobotAngularVelocity");
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (angleUnit != null) {
        return imu.getRobotAngularVelocity(angleUnit);
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
  @Block(classes = {IMU.class}, methodName = "getRobotOrientation")
  public Orientation getRobotOrientation(String axesReferenceString, String axesOrderString, String angleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getRobotOrientation");
      AxesReference axesReference = checkAxesReference(axesReferenceString);
      AxesOrder axesOrder = checkAxesOrder(axesOrderString);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (axesReference != null && axesOrder != null && angleUnit != null) {
        return imu.getRobotOrientation(axesReference, axesOrder, angleUnit);
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
  @Block(classes = {IMU.class}, methodName = "getRobotOrientationAsQuaternion")
  public Quaternion getRobotOrientationAsQuaternion() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getRobotOrientationAsQuaternion");
      return imu.getRobotOrientationAsQuaternion();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {IMU.class}, methodName = "getRobotYawPitchRollAngles")
  public YawPitchRollAngles getRobotYawPitchRollAngles() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getRobotYawPitchRollAngles");
      return imu.getRobotYawPitchRollAngles();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {IMU.class}, methodName = "resetYaw")
  public void resetYaw() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".resetYaw");
      imu.resetYaw();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

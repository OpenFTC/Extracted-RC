/**
 * Copyright 2024 Google LLC
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
import com.qualcomm.hardware.rev.Rev9AxisImuOrientationOnRobot;
import com.qualcomm.hardware.rev.Rev9AxisImuOrientationOnRobot.I2cPortFacingDirection;
import com.qualcomm.hardware.rev.Rev9AxisImuOrientationOnRobot.LogoFacingDirection;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;

/**
 * A class that provides JavaScript access to {@link IMU#Parameters}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class Rev9AxisImuOrientationOnRobotAccess extends Access {

  Rev9AxisImuOrientationOnRobotAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "Rev9AxisImuOrientationOnRobot");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Rev9AxisImuOrientationOnRobot.class, constructor = true)
  public Rev9AxisImuOrientationOnRobot create1(String logoFacingDirectionString, String i2cPortFacingDirectionString) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      LogoFacingDirection logoFacingDirection = checkArg(logoFacingDirectionString, LogoFacingDirection.class, "logoFacingDirection");
      I2cPortFacingDirection i2cPortFacingDirection = checkArg(i2cPortFacingDirectionString, I2cPortFacingDirection.class, "i2cPortFacingDirection");
      if (logoFacingDirection != null && i2cPortFacingDirection != null) {
        return new Rev9AxisImuOrientationOnRobot(logoFacingDirection, i2cPortFacingDirection);
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
  @Block(classes = Rev9AxisImuOrientationOnRobot.class, constructor = true)
  public Rev9AxisImuOrientationOnRobot create2(Object orientationArg) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      Orientation orientation = checkOrientation(orientationArg, "rotation");
      if (orientation != null) {
        return new Rev9AxisImuOrientationOnRobot(orientation);
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
  @Block(classes = Rev9AxisImuOrientationOnRobot.class, constructor = true)
  public Rev9AxisImuOrientationOnRobot create3(Object quaternionArg) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      Quaternion quaternion = checkQuaternion(quaternionArg, "rotation");
      if (quaternion != null) {
        return new Rev9AxisImuOrientationOnRobot(quaternion);
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
  @Block(classes = Rev9AxisImuOrientationOnRobot.class, methodName = "zyxOrientation")
  public Orientation zyxOrientation(double z, double y, double x) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".zyxOrientation");
      return Rev9AxisImuOrientationOnRobot.zyxOrientation(z, y, x);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Rev9AxisImuOrientationOnRobot.class, methodName = "xyzOrientation")
  public Orientation xyzOrientation(double x, double y, double z) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".zyxOrientation");
      return Rev9AxisImuOrientationOnRobot.xyzOrientation(x, y, z);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

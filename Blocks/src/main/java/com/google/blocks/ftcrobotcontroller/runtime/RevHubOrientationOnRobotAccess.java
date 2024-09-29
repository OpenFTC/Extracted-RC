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
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;

/**
 * A class that provides JavaScript access to {@link IMU#Parameters}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class RevHubOrientationOnRobotAccess extends Access {

  RevHubOrientationOnRobotAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "RevHubOrientationOnRobot");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = RevHubOrientationOnRobot.class, constructor = true)
  public RevHubOrientationOnRobot create1(String logoFacingDirectionString, String usbFacingDirectionString) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      LogoFacingDirection logoFacingDirection = checkArg(logoFacingDirectionString, LogoFacingDirection.class, "logoFacingDirection");
      UsbFacingDirection usbFacingDirection = checkArg(usbFacingDirectionString, UsbFacingDirection.class, "usbFacingDirection");
      if (logoFacingDirection != null && usbFacingDirection != null) {
        return new RevHubOrientationOnRobot(logoFacingDirection, usbFacingDirection);
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
  @Block(classes = RevHubOrientationOnRobot.class, constructor = true)
  public RevHubOrientationOnRobot create2(Object orientationArg) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      Orientation orientation = checkOrientation(orientationArg, "rotation");
      if (orientation != null) {
        return new RevHubOrientationOnRobot(orientation);
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
  @Block(classes = RevHubOrientationOnRobot.class, constructor = true)
  public RevHubOrientationOnRobot create3(Object quaternionArg) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      Quaternion quaternion = checkQuaternion(quaternionArg, "rotation");
      if (quaternion != null) {
        return new RevHubOrientationOnRobot(quaternion);
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
  @Block(classes = RevHubOrientationOnRobot.class, methodName = "zyxOrientation")
  public Orientation zyxOrientation(double z, double y, double x) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".zyxOrientation");
      return RevHubOrientationOnRobot.zyxOrientation(z, y, x);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = RevHubOrientationOnRobot.class, methodName = "xyzOrientation")
  public Orientation xyzOrientation(double x, double y, double z) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".zyxOrientation");
      return RevHubOrientationOnRobot.xyzOrientation(x, y, z);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

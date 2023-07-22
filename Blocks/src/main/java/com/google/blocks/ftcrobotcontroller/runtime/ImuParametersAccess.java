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
import com.qualcomm.robotcore.hardware.IMU.Parameters;
import com.qualcomm.robotcore.hardware.ImuOrientationOnRobot;

/**
 * A class that provides JavaScript access to {@link IMU#Parameters}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class ImuParametersAccess extends Access {

  ImuParametersAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "IMU.Parameters");
  }

  private ImuOrientationOnRobot checkImuOrientationOnRobot(Object imuOrientationOnRobotArg) {
    return checkArg(imuOrientationOnRobotArg, ImuOrientationOnRobot.class, "imuOrientationOnRobot");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Parameters.class, constructor = true)
  public Parameters create(Object imuOrientationOnRobotArg) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      ImuOrientationOnRobot imuOrientationOnRobot =
          checkImuOrientationOnRobot(imuOrientationOnRobotArg);
      if (imuOrientationOnRobotArg != null) {
        return new Parameters(imuOrientationOnRobot);
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

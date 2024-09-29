/*
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
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

/**
 * A class that provides JavaScript access to {@link YawPitchRollAngles}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class YawPitchRollAnglesAccess extends Access {

  YawPitchRollAnglesAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "YawPitchRollAngles");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {YawPitchRollAngles.class}, methodName = "getYaw")
  public double getYaw(Object yawPitchRollAnglesArg, String angleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getYaw");
      YawPitchRollAngles yawPitchRollAngles = checkYawPitchRollAngles(yawPitchRollAnglesArg);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (yawPitchRollAngles != null && angleUnit != null) {
        return yawPitchRollAngles.getYaw(angleUnit);
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
  @Block(classes = {YawPitchRollAngles.class}, methodName = "getPitch")
  public double getPitch(Object yawPitchRollAnglesArg, String angleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getPitch");
      YawPitchRollAngles yawPitchRollAngles = checkYawPitchRollAngles(yawPitchRollAnglesArg);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (yawPitchRollAngles != null && angleUnit != null) {
        return yawPitchRollAngles.getPitch(angleUnit);
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
  @Block(classes = {YawPitchRollAngles.class}, methodName = "getRoll")
  public double getRoll(Object yawPitchRollAnglesArg, String angleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getRoll");
      YawPitchRollAngles yawPitchRollAngles = checkYawPitchRollAngles(yawPitchRollAnglesArg);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (yawPitchRollAngles != null && angleUnit != null) {
        return yawPitchRollAngles.getRoll(angleUnit);
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
  @Block(classes = YawPitchRollAngles.class, constructor = true)
  public YawPitchRollAngles create_withArgs(String angleUnitString,
      double yaw, double pitch, double roll, long acquisitionTime) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (angleUnit != null) {
        return new YawPitchRollAngles(angleUnit, yaw, pitch, roll, acquisitionTime);
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
  @Block(classes = YawPitchRollAngles.class, constructor = true)
  public YawPitchRollAngles create_withArgs2(String angleUnitString, double yaw, double pitch, double roll) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (angleUnit != null) {
        return new YawPitchRollAngles(angleUnit, yaw, pitch, roll, 0);
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
  @Block(classes = {YawPitchRollAngles.class}, methodName = "getAcquisitionTime")
  public long getAcquisitionTime(Object yawPitchRollAnglesArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".AcquisitionTime");
      YawPitchRollAngles yawPitchRollAngles = checkYawPitchRollAngles(yawPitchRollAnglesArg);
      if (yawPitchRollAngles != null) {
        return yawPitchRollAngles.getAcquisitionTime();
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
  @Block(classes = {YawPitchRollAngles.class}, methodName = "toString")
  public String toText(Object yawPitchRollAnglesArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      YawPitchRollAngles yawPitchRollAngles = checkYawPitchRollAngles(yawPitchRollAnglesArg);
      if (yawPitchRollAngles != null) {
        return yawPitchRollAngles.toString();
      }
      return "";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

/*
 * Copyright 2024 Google LLC
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
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

/**
 * A class that provides JavaScript access to {@link Pose2D}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class Pose2DAccess extends Access {

  Pose2DAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "Pose2D");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Pose2D.class, constructor = true)
  public Pose2D create(
      String distanceUnitString, double x, double y, String angleUnitString, double heading) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (distanceUnit != null && angleUnit != null) {
        return new Pose2D(distanceUnit, x, y, angleUnit, heading);
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
  @Block(classes = Pose2D.class, methodName = "getX")
  public double getX(Object pose2DArg, String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getX");
      Pose2D pose2D = checkPose2D(pose2DArg);
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (pose2D != null && distanceUnit != null) {
        return pose2D.getX(distanceUnit);
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
  @Block(classes = Pose2D.class, methodName = "getY")
  public double getY(Object pose2DArg, String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getY");
      Pose2D pose2D = checkPose2D(pose2DArg);
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (pose2D != null && distanceUnit != null) {
        return pose2D.getY(distanceUnit);
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
  @Block(classes = Pose2D.class, methodName = "getHeading")
  public double getHeading(Object pose2DArg, String angleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getHeading");
      Pose2D pose2D = checkPose2D(pose2DArg);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (pose2D != null && angleUnit != null) {
        return pose2D.getHeading(angleUnit);
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
  @Block(classes = Pose2D.class, methodName = "toString")
  public String toText(Object pose2DArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      Pose2D pose2D = checkPose2D(pose2DArg);
      if (pose2D != null) {
        return pose2D.toString();
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

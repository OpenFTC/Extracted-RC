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
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.Axis;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;

/**
 * A class that provides JavaScript access to {@link AngularVelocity}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class AngularVelocityAccess extends Access {

  AngularVelocityAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "AngularVelocity");
  }

  private AngularVelocity checkAngularVelocity(Object angularVelocityArg) {
    return checkArg(angularVelocityArg, AngularVelocity.class, "angularVelocity");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AngularVelocity.class, fieldName = "angleUnit")
  public String getAngleUnit(Object angularVelocityArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".AngleUnit");
      AngularVelocity angularVelocity = checkAngularVelocity(angularVelocityArg);
      if (angularVelocity != null) {
        // AngleUnit and UnnormalizedAngleUnit have the same string value (DEGREES and RADIANS)
        UnnormalizedAngleUnit angleUnit = angularVelocity.angleUnit;
        if (angleUnit != null) {
          return angleUnit.toString();
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
  @Block(classes = AngularVelocity.class, fieldName = "xRotationRate")
  public float getXRotationRate(Object angularVelocityArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".XRotationRate");
      AngularVelocity angularVelocity = checkAngularVelocity(angularVelocityArg);
      if (angularVelocity != null) {
        return angularVelocity.xRotationRate;
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
  @Block(classes = AngularVelocity.class, fieldName = "yRotationRate")
  public float getYRotationRate(Object angularVelocityArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".YRotationRate");
      AngularVelocity angularVelocity = checkAngularVelocity(angularVelocityArg);
      if (angularVelocity != null) {
        return angularVelocity.yRotationRate;
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
  @Block(classes = AngularVelocity.class, fieldName = "zRotationRate")
  public float getZRotationRate(Object angularVelocityArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".ZRotationRate");
      AngularVelocity angularVelocity = checkAngularVelocity(angularVelocityArg);
      if (angularVelocity != null) {
        return angularVelocity.zRotationRate;
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
  @Block(classes = AngularVelocity.class, fieldName = "acquisitionTime")
  public long getAcquisitionTime(Object angularVelocityArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".AcquisitionTime");
      AngularVelocity angularVelocity = checkAngularVelocity(angularVelocityArg);
      if (angularVelocity != null) {
        return angularVelocity.acquisitionTime;
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
  @Block(classes = AngularVelocity.class, constructor = true)
  public AngularVelocity create() {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new AngularVelocity();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AngularVelocity.class, constructor = true)
  public AngularVelocity create_withArgs(
      String angleUnitString, float xRotationRate, float yRotationRate,
      float zRotationRate, long acquisitionTime) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (angleUnit != null) {
        return new AngularVelocity(
            angleUnit, xRotationRate, yRotationRate, zRotationRate, acquisitionTime);
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
  @Block(classes = AngularVelocity.class, methodName = "toAngleUnit")
  public AngularVelocity toAngleUnit(Object angularVelocityArg, String angleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toAngleUnit");
      AngularVelocity angularVelocity = checkAngularVelocity(angularVelocityArg);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (angularVelocity != null && angleUnit != null) {
        return angularVelocity.toAngleUnit(angleUnit);
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
  @Block(classes = AngularVelocity.class, fieldName = {"xRotationRate", "yRotationRate", "zRotationRate"})
  public float getRotationRate(Object angularVelocityArg, String axisString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getRotationRate");
      AngularVelocity angularVelocity = checkAngularVelocity(angularVelocityArg);
      Axis axis = checkArg(axisString, Axis.class, "axis");
      if (angularVelocity != null && axis != null) {
        switch (axis) {
          case X:
            return angularVelocity.xRotationRate;
          case Y:
            return angularVelocity.yRotationRate;
          case Z:
            return angularVelocity.zRotationRate;
          case UNKNOWN:
            reportInvalidArg("axis", "Axis.X, Axis.Y, or Axis.Z");
        }
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
  @Block(classes = AngularVelocity.class, methodName = "toString")
  public String toText(Object angularVelocityArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      AngularVelocity angularVelocity = checkAngularVelocity(angularVelocityArg);
      if (angularVelocity != null) {
        return angularVelocity.toString();
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

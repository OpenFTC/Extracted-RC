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
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

/**
 * A class that provides JavaScript access to {@link Velocity}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class VelocityAccess extends Access {

  VelocityAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "Velocity");
  }

  private Velocity checkVelocity(Object velocityArg) {
    return checkArg(velocityArg, Velocity.class, "velocity");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Velocity.class, fieldName = "unit")
  public String getDistanceUnit(Object velocityArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".DistanceUnit");
      Velocity velocity = checkVelocity(velocityArg);
      if (velocity != null) {
        DistanceUnit distanceUnit = velocity.unit;
        if (distanceUnit != null) {
          return distanceUnit.toString();
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
  @Block(classes = Velocity.class, fieldName = "xVeloc")
  public double getXVeloc(Object velocityArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".XVeloc");
      Velocity velocity = checkVelocity(velocityArg);
      if (velocity != null) {
        return velocity.xVeloc;
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
  @Block(classes = Velocity.class, fieldName = "yVeloc")
  public double getYVeloc(Object velocityArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".YVeloc");
      Velocity velocity = checkVelocity(velocityArg);
      if (velocity != null) {
        return velocity.yVeloc;
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
  @Block(classes = Velocity.class, fieldName = "zVeloc")
  public double getZVeloc(Object velocityArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".ZVeloc");
      Velocity velocity = checkVelocity(velocityArg);
      if (velocity != null) {
        return velocity.zVeloc;
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
  @Block(classes = Velocity.class, fieldName = "acquisitionTime")
  public long getAcquisitionTime(Object velocityArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".AcquisitionTime");
      Velocity velocity = checkVelocity(velocityArg);
      if (velocity != null) {
        return velocity.acquisitionTime;
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
  @Block(classes = Velocity.class, constructor = true)
  public Velocity create() {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new Velocity();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Velocity.class, constructor = true)
  public Velocity create_withArgs(
      String distanceUnitString, double xVeloc, double yVeloc, double zVeloc,
      long acquisitionTime) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (distanceUnit != null) {
        return new Velocity(distanceUnit, xVeloc, yVeloc, zVeloc, acquisitionTime);
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
  @Block(classes = Velocity.class, methodName = "toUnit")
  public Velocity toDistanceUnit(Object velocityArg, String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toDistanceUnit");
      Velocity velocity = checkVelocity(velocityArg);
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (velocity != null && distanceUnit != null) {
        return velocity.toUnit(distanceUnit);
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
  @Block(classes = Velocity.class, methodName = "toString")
  public String toText(Object velocityArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      Velocity velocity = checkVelocity(velocityArg);
      if (velocity != null) {
        return velocity.toString();
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

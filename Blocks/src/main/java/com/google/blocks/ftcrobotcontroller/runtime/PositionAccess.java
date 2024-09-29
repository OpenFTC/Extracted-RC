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
import org.firstinspires.ftc.robotcore.external.navigation.Position;

/**
 * A class that provides JavaScript access to {@link Position}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class PositionAccess extends Access {

  PositionAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "Position");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Position.class, fieldName = "unit")
  public String getDistanceUnit(Object positionArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".DistanceUnit");
      Position position = checkPosition(positionArg);
      if (position != null) {
        DistanceUnit distanceUnit = position.unit;
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
  @Block(classes = Position.class, fieldName = "x")
  public double getX(Object positionArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".X");
      Position position = checkPosition(positionArg);
      if (position != null) {
        return position.x;
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
  @Block(classes = Position.class, fieldName = "y")
  public double getY(Object positionArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Y");
      Position position = checkPosition(positionArg);
      if (position != null) {
        return position.y;
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
  @Block(classes = Position.class, fieldName = "z")
  public double getZ(Object positionArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Z");
      Position position = checkPosition(positionArg);
      if (position != null) {
        return position.z;
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
  @Block(classes = Position.class, fieldName = "acquisitionTime")
  public long getAcquisitionTime(Object positionArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".AcquisitionTime");
      Position position = checkPosition(positionArg);
      if (position != null) {
        return position.acquisitionTime;
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
  @Block(classes = Position.class, constructor = true)
  public Position create() {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new Position();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Position.class, constructor = true)
  public Position create_withArgs(
      String distanceUnitString, double x, double y, double z, long acquisitionTime) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (distanceUnit != null) {
        return new Position(distanceUnit, x, y, z, acquisitionTime);
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
  @Block(classes = Position.class, methodName = "toUnit")
  public Position toDistanceUnit(Object positionArg, String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toDistanceUnit");
      Position position = checkPosition(positionArg);
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (position != null && distanceUnit != null) {
        return position.toUnit(distanceUnit);
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
  @Block(classes = Position.class, methodName = "toString")
  public String toText(Object positionArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      Position position = checkPosition(positionArg);
      if (position != null) {
        return position.toString();
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

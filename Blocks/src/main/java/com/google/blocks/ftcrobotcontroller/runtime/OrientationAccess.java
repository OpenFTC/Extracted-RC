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
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * A class that provides JavaScript access to {@link Orientation}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class OrientationAccess extends Access {

  OrientationAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "Orientation");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Orientation.class, fieldName = "axesReference")
  public String getAxesReference(Object orientationArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".AxesReference");
      Orientation orientation = checkOrientation(orientationArg);
      if (orientation != null) {
        AxesReference axesReference = orientation.axesReference;
        if (axesReference != null) {
          return axesReference.toString();
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
  @Block(classes = Orientation.class, fieldName = "axesOrder")
  public String getAxesOrder(Object orientationArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".AxesOrder");
      Orientation orientation = checkOrientation(orientationArg);
      if (orientation != null) {
        AxesOrder axesOrder = orientation.axesOrder;
        if (axesOrder != null) {
          return axesOrder.toString();
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
  @Block(classes = Orientation.class, fieldName = "angleUnit")
  public String getAngleUnit(Object orientationArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".AngleUnit");
      Orientation orientation = checkOrientation(orientationArg);
      if (orientation != null) {
        AngleUnit angleUnit = orientation.angleUnit;
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
  @Block(classes = Orientation.class, fieldName = "firstAngle")
  public float getFirstAngle(Object orientationArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".FirstAngle");
      Orientation orientation = checkOrientation(orientationArg);
      if (orientation != null) {
        return orientation.firstAngle;
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
  @Block(classes = Orientation.class, fieldName = "secondAngle")
  public float getSecondAngle(Object orientationArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".SecondAngle");
      Orientation orientation = checkOrientation(orientationArg);
      if (orientation != null) {
        return orientation.secondAngle;
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
  @Block(classes = Orientation.class, fieldName = "thirdAngle")
  public float getThirdAngle(Object orientationArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".ThirdAngle");
      Orientation orientation = checkOrientation(orientationArg);
      if (orientation != null) {
        return orientation.thirdAngle;
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
  @Block(classes = Orientation.class, fieldName = "acquisitionTime")
  public long getAcquisitionTime(Object orientationArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".AcquisitionTime");
      Orientation orientation = checkOrientation(orientationArg);
      if (orientation != null) {
        return orientation.acquisitionTime;
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
  @Block(classes = Orientation.class, constructor = true)
  public Orientation create() {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new Orientation();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Orientation.class, constructor = true)
  public Orientation create_withArgs(
      String axesReferenceString, String axesOrderString, String angleUnitString, float firstAngle,
      float secondAngle, float thirdAngle, long acquisitionTime) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      AxesReference axesReference = checkAxesReference(axesReferenceString);
      AxesOrder axesOrder = checkAxesOrder(axesOrderString);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (axesReference != null && axesOrder != null && angleUnit != null) {
        return new Orientation(
            axesReference, axesOrder, angleUnit, firstAngle, secondAngle, thirdAngle,
            acquisitionTime);
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
  @Block(classes = Orientation.class, constructor = true)
  public Orientation create_withArgs2(
      String axesReferenceString, String axesOrderString, String angleUnitString,
      float firstAngle, float secondAngle, float thirdAngle) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      AxesReference axesReference = checkAxesReference(axesReferenceString);
      AxesOrder axesOrder = checkAxesOrder(axesOrderString);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (axesReference != null && axesOrder != null && angleUnit != null) {
        return new Orientation(
            axesReference, axesOrder, angleUnit, firstAngle, secondAngle, thirdAngle, 0);
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
  @Block(classes = Orientation.class, methodName = "toAngleUnit")
  public Orientation toAngleUnit(Object orientationArg, String angleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toAngleUnit");
      Orientation orientation = checkOrientation(orientationArg);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (orientation != null && angleUnit != null) {
        return orientation.toAngleUnit(angleUnit);
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
  @Block(classes = Orientation.class, methodName = "toAxesReference")
  public Orientation toAxesReference(Object orientationArg, String axesReferenceString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toAxesReference");
      Orientation orientation = checkOrientation(orientationArg);
      AxesReference axesReference = checkAxesReference(axesReferenceString);
      if (orientation != null && axesReference != null) {
        return orientation.toAxesReference(axesReference);
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
  @Block(classes = Orientation.class, methodName = "toAxesOrder")
  public Orientation toAxesOrder(Object orientationArg, String axesOrderString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toAxesOrder");
      Orientation orientation = checkOrientation(orientationArg);
      AxesOrder axesOrder = checkAxesOrder(axesOrderString);
      if (orientation != null && axesOrder != null) {
        return orientation.toAxesOrder(axesOrder);
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
  @Block(classes = Orientation.class, methodName = "toString")
  public String toText(Object orientationArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      Orientation orientation = checkOrientation(orientationArg);
      if (orientation != null) {
        return orientation.toString();
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
  @Block(classes = Orientation.class, methodName = "getRotationMatrix")
  public OpenGLMatrix getRotationMatrix(Object orientationArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getRotationMatrix");
      Orientation orientation = checkOrientation(orientationArg);
      if (orientation != null) {
        return orientation.getRotationMatrix();
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
  @Block(classes = Orientation.class, methodName = "getRotationMatrix")
  public OpenGLMatrix getRotationMatrix_withArgs(
      String axesReferenceString, String axesOrderString, String angleUnitString, float firstAngle,
      float secondAngle, float thirdAngle) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getRotationMatrix");
      AxesReference axesReference = checkAxesReference(axesReferenceString);
      AxesOrder axesOrder = checkAxesOrder(axesOrderString);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (axesReference != null && axesOrder != null && angleUnit != null) {
        return Orientation.getRotationMatrix(
            axesReference, axesOrder, angleUnit, firstAngle, secondAngle, thirdAngle);
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
  @Block(classes = Orientation.class, methodName = "getOrientation")
  public Orientation getOrientation(
      Object matrixArg, String axesReferenceString, String axesOrderString, String angleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getOrientation");
      MatrixF matrix = checkMatrixF(matrixArg);
      AxesReference axesReference = checkAxesReference(axesReferenceString);
      AxesOrder axesOrder = checkAxesOrder(axesOrderString);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (matrix != null && axesReference != null && axesOrder != null && angleUnit != null) {
        return Orientation.getOrientation(matrix, axesReference, axesOrder, angleUnit);
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

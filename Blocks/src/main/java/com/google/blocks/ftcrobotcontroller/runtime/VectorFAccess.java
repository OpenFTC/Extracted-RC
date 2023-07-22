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
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

/**
 * A class that provides JavaScript access to {@link VectorF}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class VectorFAccess extends Access {

  VectorFAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "VectorF");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VectorF.class, methodName = "length")
  public int getLength(Object vectorArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Length");
      VectorF vector = checkVectorF(vectorArg);
      if (vector != null) {
        return vector.length();
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
  @Block(classes = VectorF.class, methodName = "magnitude")
  public float getMagnitude(Object vectorArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Magnitude");
      VectorF vector = checkVectorF(vectorArg);
      if (vector != null) {
        return vector.magnitude();
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
  @Block(classes = VectorF.class, methodName = "length")
  public VectorF create(int length) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return VectorF.length(length);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VectorF.class, methodName = "get")
  public float get(Object vectorArg, int index) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".get");
      VectorF vector = checkVectorF(vectorArg);
      if (vector != null) {
        return vector.get(index);
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
  @Block(classes = VectorF.class, methodName = "put")
  public void put(Object vectorArg, int index, float value) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".put");
      VectorF vector = checkVectorF(vectorArg);
      if (vector != null) {
        vector.put(index, value);
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
  @Block(classes = VectorF.class, methodName = "toString")
  public String toText(Object vectorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      VectorF vector = checkVectorF(vectorArg);
      if (vector != null) {
        return vector.toString();
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
  @Block(classes = VectorF.class, methodName = "normalized3D")
  public VectorF normalized3D(Object vectorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".normalized3D");
      VectorF vector = checkVectorF(vectorArg);
      if (vector != null) {
        return vector.normalized3D();
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
  @Block(classes = VectorF.class, methodName = "dotProduct")
  public float dotProduct(Object vector1Arg, Object vector2Arg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".dotProduct");
      VectorF vector1 = checkArg(vector1Arg, VectorF.class, "vector1");
      VectorF vector2 = checkArg(vector2Arg, VectorF.class, "vector2");
      if (vector1 != null && vector2 != null) {
        return vector1.dotProduct(vector2);
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
  @Block(classes = VectorF.class, methodName = "multiplied")
  public MatrixF multiplied(Object vectorArg, Object matrixArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".multiplied");
      VectorF vector = checkVectorF(vectorArg);
      MatrixF matrix = checkMatrixF(matrixArg);
      if (vector != null && matrix != null) {
        return vector.multiplied(matrix);
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
  @Block(classes = VectorF.class, methodName = "added")
  public MatrixF added_withMatrix(Object vectorArg, Object matrixArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".added");
      VectorF vector = checkVectorF(vectorArg);
      MatrixF matrix = checkMatrixF(matrixArg);
      if (vector != null && matrix != null) {
        return vector.added(matrix);
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
  @Block(classes = VectorF.class, methodName = "added")
  public VectorF added_withVector(Object vector1Arg, Object vector2Arg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".added");
      VectorF vector1 = checkArg(vector1Arg, VectorF.class, "vector1");
      VectorF vector2 = checkArg(vector2Arg, VectorF.class, "vector2");
      if (vector1 != null && vector2 != null) {
        return vector1.added(vector2);
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
  @Block(classes = VectorF.class, methodName = "add")
  public void add_withVector(Object vector1Arg, Object vector2Arg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".add");
      VectorF vector1 = checkArg(vector1Arg, VectorF.class, "vector1");
      VectorF vector2 = checkArg(vector2Arg, VectorF.class, "vector2");
      if (vector1 != null && vector2 != null) {
        vector1.add(vector2);
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
  @Block(classes = VectorF.class, methodName = "subtracted")
  public MatrixF subtracted_withMatrix(Object vectorArg, Object matrixArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".subtracted");
      VectorF vector = checkVectorF(vectorArg);
      MatrixF matrix = checkMatrixF(matrixArg);
      if (vector != null && matrix != null) {
        return vector.subtracted(matrix);
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
  @Block(classes = VectorF.class, methodName = "subtracted")
  public VectorF subtracted_withVector(Object vector1Arg, Object vector2Arg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".subtracted");
      VectorF vector1 = checkArg(vector1Arg, VectorF.class, "vector1");
      VectorF vector2 = checkArg(vector2Arg, VectorF.class, "vector2");
      if (vector1 != null && vector2 != null) {
        return vector1.subtracted(vector2);
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
  @Block(classes = VectorF.class, methodName = "subtract")
  public void subtract_withVector(Object vector1Arg, Object vector2Arg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".subtract");
      VectorF vector1 = checkArg(vector1Arg, VectorF.class, "vector1");
      VectorF vector2 = checkArg(vector2Arg, VectorF.class, "vector2");
      if (vector1 != null && vector2 != null) {
        vector1.subtract(vector2);
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
  @Block(classes = VectorF.class, methodName = "multiplied")
  public VectorF multiplied_withScale(Object vectorArg, float scale) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".multiplied");
      VectorF vector = checkVectorF(vectorArg);
      if (vector != null) {
        return vector.multiplied(scale);
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
  @Block(classes = VectorF.class, methodName = "multiply")
  public void multiply_withScale(Object vectorArg, float scale) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".multiply");
      VectorF vector = checkVectorF(vectorArg);
      if (vector != null) {
        vector.multiply(scale);
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}
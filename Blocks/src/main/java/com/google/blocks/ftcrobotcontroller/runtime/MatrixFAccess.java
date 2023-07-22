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
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

/**
 * A class that provides JavaScript access to {@link MatrixF}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class MatrixFAccess extends Access {

  MatrixFAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "MatrixF");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = MatrixF.class, methodName = "numRows")
  public int getNumRows(Object matrixArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".NumRows");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        return matrix.numRows();
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
  @Block(classes = MatrixF.class, methodName = "numCols")
  public int getNumCols(Object matrixArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".NumCols");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        return matrix.numCols();
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
  @Block(classes = MatrixF.class, methodName = "slice")
  public MatrixF slice(Object matrixArg, int row, int col, int numRows, int numCols) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".slice");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        return matrix.slice(row, col, numRows, numCols);
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
  @Block(classes = MatrixF.class, methodName = "identityMatrix")
  public MatrixF identityMatrix(int dim) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".identityMatrix");
      return MatrixF.identityMatrix(dim);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = MatrixF.class, methodName = "diagonalMatrix")
  public MatrixF diagonalMatrix(int dim, int scale) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".diagonalMatrix");
      return MatrixF.diagonalMatrix(dim, scale);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = MatrixF.class, methodName = "diagonalMatrix")
  public MatrixF diagonalMatrix_withVector(Object vectorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".diagonalMatrix");
      VectorF vector = checkVectorF(vectorArg);
      if (vector != null) {
        return MatrixF.diagonalMatrix(vector);
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
  @Block(classes = MatrixF.class, methodName = "get")
  public float get(Object matrixArg, int row, int col) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".get");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        return matrix.get(row, col);
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
  @Block(classes = MatrixF.class, methodName = "put")
  public void put(Object matrixArg, int row, int col, float value) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".put");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        matrix.put(row, col, value);
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
  @Block(classes = MatrixF.class, methodName = "getRow")
  public VectorF getRow(Object matrixArg, int row) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getRow");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        return matrix.getRow(row);
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
  @Block(classes = MatrixF.class, methodName = "getColumn")
  public VectorF getColumn(Object matrixArg, int col) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getColumn");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        return matrix.getColumn(col);
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
  @Block(classes = MatrixF.class, methodName = "toString")
  public String toText(Object matrixArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        return matrix.toString();
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
  @Block(classes = MatrixF.class, methodName = "transform")
  public VectorF transform(Object matrixArg, Object vectorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".transform");
      MatrixF matrix = checkMatrixF(matrixArg);
      VectorF vector = checkVectorF(vectorArg);
      if (matrix != null && vector != null) {
        return matrix.transform(vector);
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
  @Block(classes = MatrixF.class, methodName = "formatAsTransform")
  public String formatAsTransform(Object matrixArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".formatAsTransform");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        return matrix.formatAsTransform();
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
  @Block(classes = MatrixF.class, methodName = "formatAsTransform")
  public String formatAsTransform_withArgs(Object matrixArg, String axesReferenceString, String axesOrderString, String angleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".formatAsTransform");
      MatrixF matrix = checkMatrixF(matrixArg);
      AxesReference axesReference = checkAxesReference(axesReferenceString);
      AxesOrder axesOrder = checkAxesOrder(axesOrderString);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (matrix != null && axesReference != null && axesOrder != null && angleUnit != null) {
        return matrix.formatAsTransform(axesReference, axesOrder, angleUnit);
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
  @Block(classes = MatrixF.class, methodName = "transposed")
  public MatrixF transposed(Object matrixArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".transposed");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        return matrix.transposed();
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
  @Block(classes = MatrixF.class, methodName = "multiplied")
  public MatrixF multiplied_withMatrix(Object matrix1Arg, Object matrix2Arg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".multiplied");
      MatrixF matrix1 = checkArg(matrix1Arg, MatrixF.class, "matrix1");
      MatrixF matrix2 = checkArg(matrix2Arg, MatrixF.class, "matrix2");
      if (matrix1 != null && matrix2 != null) {
        return matrix1.multiplied(matrix2);
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
  @Block(classes = MatrixF.class, methodName = "multiplied")
  public MatrixF multiplied_withScale(Object matrixArg, float scale) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".multiplied");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        return matrix.multiplied(scale);
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
  @Block(classes = MatrixF.class, methodName = "multiplied")
  public VectorF multiplied_withVector(Object matrixArg, Object vectorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".multiplied");
      MatrixF matrix = checkMatrixF(matrixArg);
      VectorF vector = checkVectorF(vectorArg);
      if (matrix != null && vector != null) {
        return matrix.multiplied(vector);
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
  @Block(classes = MatrixF.class, methodName = "multiply")
  public void multiply_withMatrix(Object matrix1Arg, Object matrix2Arg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".multiply");
      MatrixF matrix1 = checkArg(matrix1Arg, MatrixF.class, "matrix1");
      MatrixF matrix2 = checkArg(matrix2Arg, MatrixF.class, "matrix2");
      if (matrix1 != null && matrix2 != null) {
        matrix1.multiply(matrix2);
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
  @Block(classes = MatrixF.class, methodName = "multiply")
  public void multiply_withScale(Object matrixArg, float scale) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".multiply");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        matrix.multiply(scale);
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
  @Block(classes = MatrixF.class, methodName = "multiply")
  public void multiply_withVector(Object matrixArg, Object vectorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".multiply");
      MatrixF matrix = checkMatrixF(matrixArg);
      VectorF vector = checkVectorF(vectorArg);
      if (matrix != null && vector != null) {
        matrix.multiply(vector);
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
  @Block(classes = MatrixF.class, methodName = "toVector")
  public VectorF toVector(Object matrixArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toVector");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        return matrix.toVector();
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
  @Block(classes = MatrixF.class, methodName = "added")
  public MatrixF added_withMatrix(Object matrix1Arg, Object matrix2Arg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".added");
      MatrixF matrix1 = checkArg(matrix1Arg, MatrixF.class, "matrix1");
      MatrixF matrix2 = checkArg(matrix2Arg, MatrixF.class, "matrix2");
      if (matrix1 != null && matrix2 != null) {
        return matrix1.added(matrix2);
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
  @Block(classes = MatrixF.class, methodName = "added")
  public MatrixF added_withVector(Object matrixArg, Object vectorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".added");
      MatrixF matrix = checkMatrixF(matrixArg);
      VectorF vector = checkVectorF(vectorArg);
      if (matrix != null && vector != null) {
        return matrix.added(vector);
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
  @Block(classes = MatrixF.class, methodName = "add")
  public void add_withMatrix(Object matrix1Arg, Object matrix2Arg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".add");
      MatrixF matrix1 = checkArg(matrix1Arg, MatrixF.class, "matrix1");
      MatrixF matrix2 = checkArg(matrix2Arg, MatrixF.class, "matrix2");
      if (matrix1 != null && matrix2 != null) {
        matrix1.add(matrix2);
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
  @Block(classes = MatrixF.class, methodName = "add")
  public void add_withVector(Object matrixArg, Object vectorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".add");
      MatrixF matrix = checkMatrixF(matrixArg);
      VectorF vector = checkVectorF(vectorArg);
      if (matrix != null && vector != null) {
         matrix.add(vector);
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
  @Block(classes = MatrixF.class, methodName = "subtracted")
  public MatrixF subtracted_withMatrix(Object matrix1Arg, Object matrix2Arg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".subtracted");
      MatrixF matrix1 = checkArg(matrix1Arg, MatrixF.class, "matrix1");
      MatrixF matrix2 = checkArg(matrix2Arg, MatrixF.class, "matrix2");
      if (matrix1 != null && matrix2 != null) {
        return matrix1.subtracted(matrix2);
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
  @Block(classes = MatrixF.class, methodName = "subtracted")
  public MatrixF subtracted_withVector(Object matrixArg, Object vectorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".subtracted");
      MatrixF matrix = checkMatrixF(matrixArg);
      VectorF vector = checkVectorF(vectorArg);
      if (matrix != null && vector != null) {
        return matrix.subtracted(vector);
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
  @Block(classes = MatrixF.class, methodName = "subtract")
  public void subtract_withMatrix(Object matrix1Arg, Object matrix2Arg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".subtract");
      MatrixF matrix1 = checkArg(matrix1Arg, MatrixF.class, "matrix1");
      MatrixF matrix2 = checkArg(matrix2Arg, MatrixF.class, "matrix2");
      if (matrix1 != null && matrix2 != null) {
        matrix1.subtract(matrix2);
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
  @Block(classes = MatrixF.class, methodName = "subtract")
  public void subtract_withVector(Object matrixArg, Object vectorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".subtract");
      MatrixF matrix = checkMatrixF(matrixArg);
      VectorF vector = checkVectorF(vectorArg);
      if (matrix != null && vector != null) {
        matrix.subtract(vector);
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
  @Block(classes = MatrixF.class, methodName = "getTranslation")
  public VectorF getTranslation(Object matrixArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getTranslation");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        return matrix.getTranslation();
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
  @Block(classes = MatrixF.class, methodName = "inverted")
  public MatrixF inverted(Object matrixArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".inverted");
      MatrixF matrix = checkMatrixF(matrixArg);
      if (matrix != null) {
        return matrix.inverted();
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

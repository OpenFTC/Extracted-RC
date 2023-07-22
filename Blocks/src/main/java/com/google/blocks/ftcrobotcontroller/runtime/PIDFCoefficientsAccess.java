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
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

/**
 * A class that provides JavaScript access to {@link PIDFCoefficients}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class PIDFCoefficientsAccess extends Access {

  PIDFCoefficientsAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "PIDFCoefficients");
  }

  private PIDFCoefficients checkPIDFCoefficients(Object pidfCoefficientsArg) {
    return checkArg(pidfCoefficientsArg, PIDFCoefficients.class, "pidfCoefficients");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = PIDFCoefficients.class, constructor = true)
  public PIDFCoefficients create() {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new PIDFCoefficients();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = PIDFCoefficients.class, constructor = true)
  public PIDFCoefficients create_withPIDFAlgorithm(double p, double i, double d, double f, String algorithmString) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      MotorControlAlgorithm algorithm = checkArg(algorithmString, MotorControlAlgorithm.class, "algorithm");
      if (algorithm != null) {
        return new PIDFCoefficients(p, i, d, f, algorithm);
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
  @Block(classes = PIDFCoefficients.class, constructor = true)
  public PIDFCoefficients create_withPIDF(double p, double i, double d, double f) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new PIDFCoefficients(p, i, d, f);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = PIDFCoefficients.class, constructor = true)
  public PIDFCoefficients create_withPIDFCoefficients(Object pidfCoefficientsArg) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      PIDFCoefficients pidfCoefficients = checkPIDFCoefficients(pidfCoefficientsArg);
      if (pidfCoefficients != null) {
        return new PIDFCoefficients(pidfCoefficients);
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
  @Block(classes = PIDFCoefficients.class, fieldName = "p")
  public void setP(Object pidfCoefficientsArg, double p) {
    try {
      startBlockExecution(BlockType.SETTER, ".P");
      PIDFCoefficients pidfCoefficients = checkPIDFCoefficients(pidfCoefficientsArg);
      if (pidfCoefficients != null) {
        pidfCoefficients.p = p;
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
  @Block(classes = PIDFCoefficients.class, fieldName = "p")
  public double getP(Object pidfCoefficientsArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".P");
      PIDFCoefficients pidfCoefficients = checkPIDFCoefficients(pidfCoefficientsArg);
      if (pidfCoefficients != null) {
        return pidfCoefficients.p;
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
  @Block(classes = PIDFCoefficients.class, fieldName = "i")
  public void setI(Object pidfCoefficientsArg, double i) {
    try {
      startBlockExecution(BlockType.SETTER, ".I");
      PIDFCoefficients pidfCoefficients = checkPIDFCoefficients(pidfCoefficientsArg);
      if (pidfCoefficients != null) {
        pidfCoefficients.i = i;
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
  @Block(classes = PIDFCoefficients.class, fieldName = "i")
  public double getI(Object pidfCoefficientsArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".I");
      PIDFCoefficients pidfCoefficients = checkPIDFCoefficients(pidfCoefficientsArg);
      if (pidfCoefficients != null) {
        return pidfCoefficients.i;
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
  @Block(classes = PIDFCoefficients.class, fieldName = "d")
  public void setD(Object pidfCoefficientsArg, double d) {
    try {
      startBlockExecution(BlockType.SETTER, ".D");
      PIDFCoefficients pidfCoefficients = checkPIDFCoefficients(pidfCoefficientsArg);
      if (pidfCoefficients != null) {
        pidfCoefficients.d = d;
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
  @Block(classes = PIDFCoefficients.class, fieldName = "d")
  public double getD(Object pidfCoefficientsArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".D");
      PIDFCoefficients pidfCoefficients = checkPIDFCoefficients(pidfCoefficientsArg);
      if (pidfCoefficients != null) {
        return pidfCoefficients.d;
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
  @Block(classes = PIDFCoefficients.class, fieldName = "f")
  public void setF(Object pidfCoefficientsArg, double f) {
    try {
      startBlockExecution(BlockType.SETTER, ".F");
      PIDFCoefficients pidfCoefficients = checkPIDFCoefficients(pidfCoefficientsArg);
      if (pidfCoefficients != null) {
        pidfCoefficients.f = f;
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
  @Block(classes = PIDFCoefficients.class, fieldName = "f")
  public double getF(Object pidfCoefficientsArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".F");
      PIDFCoefficients pidfCoefficients = checkPIDFCoefficients(pidfCoefficientsArg);
      if (pidfCoefficients != null) {
        return pidfCoefficients.f;
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
  @Block(classes = PIDFCoefficients.class, fieldName = "algorithm")
  public void setAlgorithm(Object pidfCoefficientsArg, String algorithmString) {
    try {
      startBlockExecution(BlockType.SETTER, ".Algorithm");
      PIDFCoefficients pidfCoefficients = checkPIDFCoefficients(pidfCoefficientsArg);
      MotorControlAlgorithm algorithm = checkArg(algorithmString, MotorControlAlgorithm.class, "");
      if (pidfCoefficients != null && algorithm != null) {
        pidfCoefficients.algorithm = algorithm;
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
  @Block(classes = PIDFCoefficients.class, fieldName = "algorithm")
  public String getAlgorithm(Object pidfCoefficientsArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Algorithm");
      PIDFCoefficients pidfCoefficients = checkPIDFCoefficients(pidfCoefficientsArg);
      if (pidfCoefficients != null && pidfCoefficients.algorithm != null) {
        return pidfCoefficients.algorithm.toString();
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
  @Block(classes = PIDFCoefficients.class, methodName = "toString")
  public String toText(Object pidfCoefficientsArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      PIDFCoefficients pidfCoefficients = checkPIDFCoefficients(pidfCoefficientsArg);
      if (pidfCoefficients != null) {
        return pidfCoefficients.toString();
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

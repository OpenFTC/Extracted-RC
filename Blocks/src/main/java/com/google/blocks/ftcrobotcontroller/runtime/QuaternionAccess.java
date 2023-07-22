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
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;

/**
 * A class that provides JavaScript access to {@link Quaternion}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class QuaternionAccess extends Access {

  QuaternionAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "Quaternion");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Quaternion.class, fieldName = "w")
  public float getW(Object quaternionArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".W");
      Quaternion quaternion = checkQuaternion(quaternionArg);
      if (quaternion != null) {
        return quaternion.w;
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
  @Block(classes = Quaternion.class, fieldName = "x")
  public float getX(Object quaternionArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".X");
      Quaternion quaternion = checkQuaternion(quaternionArg);
      if (quaternion != null) {
        return quaternion.x;
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
  @Block(classes = Quaternion.class, fieldName = "y")
  public float getY(Object quaternionArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Y");
      Quaternion quaternion = checkQuaternion(quaternionArg);
      if (quaternion != null) {
        return quaternion.y;
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
  @Block(classes = Quaternion.class, fieldName = "z")
  public float getZ(Object quaternionArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Z");
      Quaternion quaternion = checkQuaternion(quaternionArg);
      if (quaternion != null) {
        return quaternion.z;
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
  @Block(classes = Quaternion.class, fieldName = "acquisitionTime")
  public long getAcquisitionTime(Object quaternionArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".AcquisitionTime");
      Quaternion quaternion = checkQuaternion(quaternionArg);
      if (quaternion != null) {
        return quaternion.acquisitionTime;
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
  @Block(classes = Quaternion.class, methodName = "magnitude")
  public float getMagnitude(Object quaternionArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Magnitude");
      Quaternion quaternion = checkQuaternion(quaternionArg);
      if (quaternion != null) {
        return quaternion.magnitude();
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
  @Block(classes = Quaternion.class, constructor = true)
  public Quaternion create() {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new Quaternion();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Quaternion.class, constructor = true)
  public Quaternion create_withArgs(float w, float x, float y, float z, long acquisitionTime) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new Quaternion(w, x, y, z, acquisitionTime);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Quaternion.class, constructor = true)
  public Quaternion create_withArgs2(float w, float x, float y, float z) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new Quaternion(w, x, y, z, 0);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Quaternion.class, methodName = "normalized")
  public Quaternion normalized(Object quaternionArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".normalized");
      Quaternion quaternion = checkQuaternion(quaternionArg);
      if (quaternion != null) {
        return quaternion.normalized();
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
  @Block(classes = Quaternion.class, methodName = "congugate")
  public Quaternion congugate(Object quaternionArg) {
    return conjugate(quaternionArg);
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Quaternion.class, methodName = "conjugate")
  public Quaternion conjugate(Object quaternionArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".conjugate");
      Quaternion quaternion = checkQuaternion(quaternionArg);
      if (quaternion != null) {
        return quaternion.conjugate();
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
  @Block(classes = Quaternion.class, methodName = "toString")
  public String toText(Object quaternionArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      Quaternion quaternion = checkQuaternion(quaternionArg);
      if (quaternion != null) {
        return quaternion.toString();
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

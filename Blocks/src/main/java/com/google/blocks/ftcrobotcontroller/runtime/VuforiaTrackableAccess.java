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
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * A class that provides JavaScript access to {@link VuforiaTrackable}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class VuforiaTrackableAccess extends Access {

  VuforiaTrackableAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "VuforiaTrackable");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = VuforiaTrackable.class, methodName = "setLocation")
  public void setLocation(Object vuforiaTrackableArg, Object matrixArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setLocation");
      VuforiaTrackable vuforiaTrackable = checkVuforiaTrackable(vuforiaTrackableArg);
      OpenGLMatrix matrix = checkOpenGLMatrix(matrixArg);
      if (vuforiaTrackable != null && matrix != null) {
        vuforiaTrackable.setLocation(matrix);
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
  @Block(classes = VuforiaTrackable.class, methodName = "getLocation")
  public OpenGLMatrix getLocation(Object vuforiaTrackableArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Location");
      VuforiaTrackable vuforiaTrackable = checkVuforiaTrackable(vuforiaTrackableArg);
      if (vuforiaTrackable != null) {
        return vuforiaTrackable.getLocation();
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
  @Block(classes = VuforiaTrackable.class, methodName = "setUserData")
  public void setUserData(Object vuforiaTrackableArg, Object userData) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setUserData");
      VuforiaTrackable vuforiaTrackable = checkVuforiaTrackable(vuforiaTrackableArg);
      if (vuforiaTrackable != null) {
        vuforiaTrackable.setUserData(userData);
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
  @Block(classes = VuforiaTrackable.class, methodName = "getUserData")
  public Object getUserData(Object vuforiaTrackableArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".UserData");
      VuforiaTrackable vuforiaTrackable = checkVuforiaTrackable(vuforiaTrackableArg);
      if (vuforiaTrackable != null) {
        return vuforiaTrackable.getUserData();
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
  @Block(classes = VuforiaTrackable.class, methodName = "getTrackables")
  public VuforiaTrackables getTrackables(Object vuforiaTrackableArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Trackables");
      VuforiaTrackable vuforiaTrackable = checkVuforiaTrackable(vuforiaTrackableArg);
      if (vuforiaTrackable != null) {
        return vuforiaTrackable.getTrackables();
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
  @Block(classes = VuforiaTrackable.class, methodName = "setName")
  public void setName(Object vuforiaTrackableArg, String name) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setName");
      VuforiaTrackable vuforiaTrackable = checkVuforiaTrackable(vuforiaTrackableArg);
      if (vuforiaTrackable != null) {
        vuforiaTrackable.setName(name);
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
  @Block(classes = VuforiaTrackable.class, methodName = "getName")
  public String getName(Object vuforiaTrackableArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Name");
      VuforiaTrackable vuforiaTrackable = checkVuforiaTrackable(vuforiaTrackableArg);
      if (vuforiaTrackable != null) {
        String name = vuforiaTrackable.getName();
        if (name != null) {
          return name;
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
  @Block(classes = VuforiaTrackable.class, methodName = "getListener")
  public VuforiaTrackable.Listener getListener(Object vuforiaTrackableArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Listener");
      VuforiaTrackable vuforiaTrackable = checkVuforiaTrackable(vuforiaTrackableArg);
      if (vuforiaTrackable != null) {
        return vuforiaTrackable.getListener();
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

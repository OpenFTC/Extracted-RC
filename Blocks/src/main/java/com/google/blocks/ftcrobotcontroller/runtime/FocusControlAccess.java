// Copyright 2023 FIRST

package com.google.blocks.ftcrobotcontroller.runtime;

import android.webkit.JavascriptInterface;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.FocusControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.FocusControl.Mode;

/**
 * A class that provides JavaScript access to {@link FocusControl}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class FocusControlAccess extends Access {

  FocusControlAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "FocusControl");
  }

  private FocusControl checkFocusControl(Object focusControlArg) {
    return checkArg(focusControlArg, FocusControl.class, "focusControl");
  }

  private Mode checkMode(String modeString) {
    return checkArg(modeString, Mode.class, "Mode");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = FocusControl.class, methodName = "getMode")
  public String getMode(Object focusControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "FocusControl", ".getMode");
      FocusControl focusControl = checkFocusControl(focusControlArg);
      if (focusControl != null) {
        return focusControl.getMode().toString();
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
  @Block(classes = FocusControl.class, methodName = "setMode")
  public boolean setMode(Object focusControlArg, String modeString) {
    try {
      startBlockExecution(BlockType.FUNCTION, "FocusControl", ".setMode");
      FocusControl focusControl = checkFocusControl(focusControlArg);
      Mode mode = checkMode(modeString);
      if (focusControl != null && mode != null) {
        return focusControl.setMode(mode);
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = FocusControl.class, methodName = "isModeSupported")
  public boolean isModeSupported(Object focusControlArg, String modeString) {
    try {
      startBlockExecution(BlockType.FUNCTION, "FocusControl", ".isModeSupported");
      FocusControl focusControl = checkFocusControl(focusControlArg);
      Mode mode = checkMode(modeString);
      if (focusControl != null && mode != null) {
        return focusControl.isModeSupported(mode);
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = FocusControl.class, methodName = "getMinFocusLength")
  public double getMinFocusLength(Object focusControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "FocusControl", ".getMinFocusLength");
      FocusControl focusControl = checkFocusControl(focusControlArg);
      if (focusControl != null) {
        return focusControl.getMinFocusLength();
      }
      return -1;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = FocusControl.class, methodName = "getMaxFocusLength")
  public double getMaxFocusLength(Object focusControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "FocusControl", ".getMaxFocusLength");
      FocusControl focusControl = checkFocusControl(focusControlArg);
      if (focusControl != null) {
        return focusControl.getMaxFocusLength();
      }
      return -1;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = FocusControl.class, methodName = "getFocusLength")
  public double getFocusLength(Object focusControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "FocusControl", ".getFocusLength");
      FocusControl focusControl = checkFocusControl(focusControlArg);
      if (focusControl != null) {
        return focusControl.getFocusLength();
      }
      return -1;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = FocusControl.class, methodName = "setFocusLength")
  public boolean setFocusLength(Object focusControlArg, double focusLength) {
    try {
      startBlockExecution(BlockType.FUNCTION, "FocusControl", ".setFocusLength");
      FocusControl focusControl = checkFocusControl(focusControlArg);
      if (focusControl != null) {
        return focusControl.setFocusLength(focusLength);
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = FocusControl.class, methodName = "isFocusLengthSupported")
  public boolean isFocusLengthSupported(Object focusControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "FocusControl", ".isFocusLengthSupported");
      FocusControl focusControl = checkFocusControl(focusControlArg);
      if (focusControl != null) {
        return focusControl.isFocusLengthSupported();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

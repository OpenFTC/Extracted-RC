// Copyright 2023 FIRST

package com.google.blocks.ftcrobotcontroller.runtime;

import android.webkit.JavascriptInterface;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.WhiteBalanceControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.WhiteBalanceControl.Mode;

/**
 * A class that provides JavaScript access to {@link WhiteBalanceControl}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class WhiteBalanceControlAccess extends Access {

  WhiteBalanceControlAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "WhiteBalanceControl");
  }

  private WhiteBalanceControl checkWhiteBalanceControl(Object whiteBalanceControlArg) {
    return checkArg(whiteBalanceControlArg, WhiteBalanceControl.class, "whiteBalanceControl");
  }

  private Mode checkMode(String modeString) {
    return checkArg(modeString, Mode.class, "Mode");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = WhiteBalanceControl.class, methodName = "getMode")
  public String getMode(Object whiteBalanceControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "WhiteBalanceControl", ".getMode");
      WhiteBalanceControl whiteBalanceControl = checkWhiteBalanceControl(whiteBalanceControlArg);
      if (whiteBalanceControl != null) {
        return whiteBalanceControl.getMode().toString();
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
  @Block(classes = WhiteBalanceControl.class, methodName = "setMode")
  public boolean setMode(Object whiteBalanceControlArg, String modeString) {
    try {
      startBlockExecution(BlockType.FUNCTION, "WhiteBalanceControl", ".setMode");
      WhiteBalanceControl whiteBalanceControl = checkWhiteBalanceControl(whiteBalanceControlArg);
      Mode mode = checkMode(modeString);
      if (whiteBalanceControl != null && mode != null) {
        return whiteBalanceControl.setMode(mode);
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
  @Block(classes = WhiteBalanceControl.class, methodName = "getMinWhiteBalanceTemperature")
  public int getMinWhiteBalanceTemperature(Object whiteBalanceControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "WhiteBalanceControl", ".getMinWhiteBalanceTemperature");
      WhiteBalanceControl whiteBalanceControl = checkWhiteBalanceControl(whiteBalanceControlArg);
      if (whiteBalanceControl != null) {
        return whiteBalanceControl.getMinWhiteBalanceTemperature();
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
  @Block(classes = WhiteBalanceControl.class, methodName = "getMaxWhiteBalanceTemperature")
  public int getMaxWhiteBalanceTemperature(Object whiteBalanceControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "WhiteBalanceControl", ".getMaxWhiteBalanceTemperature");
      WhiteBalanceControl whiteBalanceControl = checkWhiteBalanceControl(whiteBalanceControlArg);
      if (whiteBalanceControl != null) {
        return whiteBalanceControl.getMaxWhiteBalanceTemperature();
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
  @Block(classes = WhiteBalanceControl.class, methodName = "getWhiteBalanceTemperature")
  public int getWhiteBalanceTemperature(Object whiteBalanceControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "WhiteBalanceControl", ".getWhiteBalanceTemperature");
      WhiteBalanceControl whiteBalanceControl = checkWhiteBalanceControl(whiteBalanceControlArg);
      if (whiteBalanceControl != null) {
        return whiteBalanceControl.getWhiteBalanceTemperature();
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
  @Block(classes = WhiteBalanceControl.class, methodName = "setWhiteBalanceTemperature")
  public boolean setWhiteBalanceTemperature(Object whiteBalanceControlArg, int whiteBalanceTemperature) {
    try {
      startBlockExecution(BlockType.FUNCTION, "WhiteBalanceControl", ".setWhiteBalanceTemperature");
      WhiteBalanceControl whiteBalanceControl = checkWhiteBalanceControl(whiteBalanceControlArg);
      if (whiteBalanceControl != null) {
        return whiteBalanceControl.setWhiteBalanceTemperature(whiteBalanceTemperature);
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

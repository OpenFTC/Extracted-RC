// Copyright 2023 FIRST

package com.google.blocks.ftcrobotcontroller.runtime;

import android.webkit.JavascriptInterface;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;

/**
 * A class that provides JavaScript access to {@link GainControl}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class GainControlAccess extends Access {

  GainControlAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "GainControl");
  }

  private GainControl checkGainControl(Object gainControlArg) {
    return checkArg(gainControlArg, GainControl.class, "gainControl");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = GainControl.class, methodName = "getMinGain")
  public int getMinGain(Object gainControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "GainControl", ".getMinGain");
      GainControl gainControl = checkGainControl(gainControlArg);
      if (gainControl != null) {
        return gainControl.getMinGain();
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
  @Block(classes = GainControl.class, methodName = "getMaxGain")
  public int getMaxGain(Object gainControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "GainControl", ".getMaxGain");
      GainControl gainControl = checkGainControl(gainControlArg);
      if (gainControl != null) {
        return gainControl.getMaxGain();
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
  @Block(classes = GainControl.class, methodName = "getGain")
  public int getGain(Object gainControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "GainControl", ".getGain");
      GainControl gainControl = checkGainControl(gainControlArg);
      if (gainControl != null) {
        return gainControl.getGain();
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
  @Block(classes = GainControl.class, methodName = "setGain")
  public boolean setGain(Object gainControlArg, int gain) {
    try {
      startBlockExecution(BlockType.FUNCTION, "GainControl", ".setGain");
      GainControl gainControl = checkGainControl(gainControlArg);
      if (gainControl != null) {
        return gainControl.setGain(gain);
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

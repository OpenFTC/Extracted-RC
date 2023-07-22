// Copyright 2023 FIRST

package com.google.blocks.ftcrobotcontroller.runtime;

import android.webkit.JavascriptInterface;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.PtzControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.PtzControl.PanTiltHolder;
import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;

/**
 * A class that provides JavaScript access to {@link PtzControl}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class PtzControlAccess extends Access {

  PtzControlAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "PtzControl");
  }

  private PtzControl checkPtzControl(Object ptzControlArg) {
    return checkArg(ptzControlArg, PtzControl.class, "ptzControl");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = PtzControl.class, methodName = "getMinPanTilt")
  public String getMinPanTilt(Object ptzControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "PtzControl", ".getMinPanTilt");
      PtzControl ptzControl = checkPtzControl(ptzControlArg);
      if (ptzControl != null) {
        return SimpleGson.getInstance().toJson(ptzControl.getMinPanTilt());
      }
      return "{\"pan\": 0, \"tilt\": 0}";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = PtzControl.class, methodName = "getMaxPanTilt")
  public String getMaxPanTilt(Object ptzControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "PtzControl", ".getMaxPanTilt");
      PtzControl ptzControl = checkPtzControl(ptzControlArg);
      if (ptzControl != null) {
        return SimpleGson.getInstance().toJson(ptzControl.getMaxPanTilt());
      }
      return "{\"pan\": 0, \"tilt\": 0}";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = PtzControl.class, methodName = "getPanTilt")
  public String getPanTilt(Object ptzControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "PtzControl", ".getPanTilt");
      PtzControl ptzControl = checkPtzControl(ptzControlArg);
      if (ptzControl != null) {
        return SimpleGson.getInstance().toJson(ptzControl.getPanTilt());
      }
      return "{\"pan\": 0, \"tilt\": 0}";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = PtzControl.class, methodName = "setPanTilt")
  public boolean setPanTilt(Object ptzControlArg, String json) {
    try {
      startBlockExecution(BlockType.FUNCTION, "PtzControl", ".setPanTilt");
      PtzControl ptzControl = checkPtzControl(ptzControlArg);
      if (ptzControl != null) {
        PanTiltHolder panTiltHolder = SimpleGson.getInstance().fromJson(json, PanTiltHolder.class);
        return ptzControl.setPanTilt(panTiltHolder);
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
  @Block(classes = PtzControl.class, methodName = "getMinZoom")
  public int getMinZoom(Object ptzControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "PtzControl", ".getMinZoom");
      PtzControl ptzControl = checkPtzControl(ptzControlArg);
      if (ptzControl != null) {
        return ptzControl.getMinZoom();
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
  @Block(classes = PtzControl.class, methodName = "getMaxZoom")
  public int getMaxZoom(Object ptzControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "PtzControl", ".getMaxZoom");
      PtzControl ptzControl = checkPtzControl(ptzControlArg);
      if (ptzControl != null) {
        return ptzControl.getMaxZoom();
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
  @Block(classes = PtzControl.class, methodName = "getZoom")
  public int getZoom(Object ptzControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "PtzControl", ".getZoom");
      PtzControl ptzControl = checkPtzControl(ptzControlArg);
      if (ptzControl != null) {
        return ptzControl.getZoom();
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
  @Block(classes = PtzControl.class, methodName = "setZoom")
  public boolean setZoom(Object ptzControlArg, int zoom) {
    try {
      startBlockExecution(BlockType.FUNCTION, "PtzControl", ".setZoom");
      PtzControl ptzControl = checkPtzControl(ptzControlArg);
      if (ptzControl != null) {
        return ptzControl.setZoom(zoom);
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

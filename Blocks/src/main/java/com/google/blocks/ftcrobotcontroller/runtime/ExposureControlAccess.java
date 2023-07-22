// Copyright 2023 FIRST

package com.google.blocks.ftcrobotcontroller.runtime;

import android.webkit.JavascriptInterface;
import java.util.concurrent.TimeUnit;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl.Mode;

/**
 * A class that provides JavaScript access to {@link ExposureControl}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class ExposureControlAccess extends Access {

  ExposureControlAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "ExposureControl");
  }

  private ExposureControl checkExposureControl(Object exposureControlArg) {
    return checkArg(exposureControlArg, ExposureControl.class, "exposureControl");
  }

  private Mode checkMode(String modeString) {
    return checkArg(modeString, Mode.class, "Mode");
  }

  private TimeUnit checkTimeUnit(String timeUnitString) {
    return checkArg(timeUnitString, TimeUnit.class, "timeUnit");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = ExposureControl.class, methodName = "getMode")
  public String getMode(Object exposureControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getMode");
      ExposureControl exposureControl = checkExposureControl(exposureControlArg);
      if (exposureControl != null) {
        return exposureControl.getMode().toString();
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
  @Block(classes = ExposureControl.class, methodName = "setMode")
  public boolean setMode(Object exposureControlArg, String modeString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setMode");
      ExposureControl exposureControl = checkExposureControl(exposureControlArg);
      Mode mode = checkMode(modeString);
      if (exposureControl != null && mode != null) {
        return exposureControl.setMode(mode);
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
  @Block(classes = ExposureControl.class, methodName = "isModeSupported")
  public boolean isModeSupported(Object exposureControlArg, String modeString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".isModeSupported");
      ExposureControl exposureControl = checkExposureControl(exposureControlArg);
      Mode mode = checkMode(modeString);
      if (exposureControl != null && mode != null) {
        return exposureControl.isModeSupported(mode);
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
  @Block(classes = ExposureControl.class, methodName = "getMinExposure")
  public long getMinExposure(Object exposureControlArg, String timeUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getMinExposure");
      ExposureControl exposureControl = checkExposureControl(exposureControlArg);
      TimeUnit timeUnit = checkTimeUnit(timeUnitString);
      if (exposureControl != null && timeUnit != null) {
        return exposureControl.getMinExposure(timeUnit);
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
  @Block(classes = ExposureControl.class, methodName = "getMaxExposure")
  public long getMaxExposure(Object exposureControlArg, String timeUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getMaxExposure");
      ExposureControl exposureControl = checkExposureControl(exposureControlArg);
      TimeUnit timeUnit = checkTimeUnit(timeUnitString);
      if (exposureControl != null && timeUnit != null) {
        return exposureControl.getMaxExposure(timeUnit);
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
  @Block(classes = ExposureControl.class, methodName = "getExposure")
  public long getExposure(Object exposureControlArg, String timeUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getExposure");
      ExposureControl exposureControl = checkExposureControl(exposureControlArg);
      TimeUnit timeUnit = checkTimeUnit(timeUnitString);
      if (exposureControl != null && timeUnit != null) {
        return exposureControl.getExposure(timeUnit);
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
  @Block(classes = ExposureControl.class, methodName = "setExposure")
  public boolean setExposure(Object exposureControlArg, long duration, String timeUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setExposure");
      ExposureControl exposureControl = checkExposureControl(exposureControlArg);
      TimeUnit timeUnit = checkTimeUnit(timeUnitString);
      if (exposureControl != null && timeUnit != null) {
        return exposureControl.setExposure(duration, timeUnit);
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
  @Block(classes = ExposureControl.class, methodName = "isExposureSupported")
  public boolean isExposureSupported(Object exposureControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".isExposureSupported");
      ExposureControl exposureControl = checkExposureControl(exposureControlArg);
      if (exposureControl != null) {
        return exposureControl.isExposureSupported();
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
  @Block(classes = ExposureControl.class, methodName = "getAePriority")
  public boolean getAePriority(Object exposureControlArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getAePriority");
      ExposureControl exposureControl = checkExposureControl(exposureControlArg);
      if (exposureControl != null) {
        return exposureControl.getAePriority();
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
  @Block(classes = ExposureControl.class, methodName = "setAePriority")
  public boolean setAePriority(Object exposureControlArg, boolean priority) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setAePriority");
      ExposureControl exposureControl = checkExposureControl(exposureControlArg);
      if (exposureControl != null) {
        return exposureControl.setAePriority(priority);
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

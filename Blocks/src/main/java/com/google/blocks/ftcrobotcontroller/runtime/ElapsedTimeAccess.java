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
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ElapsedTime.Resolution;

/**
 * A class that provides JavaScript access to {@link ElapsedTime}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class ElapsedTimeAccess extends Access {

  ElapsedTimeAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "ElapsedTime");
  }

  private ElapsedTime checkElapsedTime(Object elapsedTimeArg) {
    return checkArg(elapsedTimeArg, ElapsedTime.class, "timer");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = ElapsedTime.class, constructor = true)
  public ElapsedTime create() {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new ElapsedTime();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = ElapsedTime.class, constructor = true)
  public ElapsedTime create_withStartTime(long startTime) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new ElapsedTime(startTime);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = ElapsedTime.class, constructor = true)
  public ElapsedTime create_withResolution(String resolutionString) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      Resolution resolution = checkArg(resolutionString, Resolution.class, "resolution");
      if (resolution != null) {
        return new ElapsedTime(resolution);
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
  @Block(classes = ElapsedTime.class, methodName = "startTime")
  public double getStartTime(Object elapsedTimeArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".StartTime");
      ElapsedTime elapsedTime = checkElapsedTime(elapsedTimeArg);
      if (elapsedTime != null) {
        return elapsedTime.startTime();
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
  @Block(classes = ElapsedTime.class, methodName = "time")
  public double getTime(Object elapsedTimeArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Time");
      ElapsedTime elapsedTime = checkElapsedTime(elapsedTimeArg);
      if (elapsedTime != null) {
        return elapsedTime.time();
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
  @Block(classes = ElapsedTime.class, methodName = "seconds")
  public double getSeconds(Object elapsedTimeArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Seconds");
      ElapsedTime elapsedTime = checkElapsedTime(elapsedTimeArg);
      if (elapsedTime != null) {
        return elapsedTime.seconds();
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
  @Block(classes = ElapsedTime.class, methodName = "milliseconds")
  public double getMilliseconds(Object elapsedTimeArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Milliseconds");
      ElapsedTime elapsedTime = checkElapsedTime(elapsedTimeArg);
      if (elapsedTime != null) {
        return elapsedTime.milliseconds();
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
  @Block(classes = ElapsedTime.class, methodName = "getResolution")
  public String getResolution(Object elapsedTimeArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Resolution");
      ElapsedTime elapsedTime = checkElapsedTime(elapsedTimeArg);
      if (elapsedTime != null) {
        Resolution resolution = elapsedTime.getResolution();
        if (resolution != null) {
          return resolution.toString();
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
  @Block(classes = ElapsedTime.class, methodName = "toString")
  public String getAsText(Object elapsedTimeArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".AsText");
      ElapsedTime elapsedTime = checkElapsedTime(elapsedTimeArg);
      if (elapsedTime != null) {
        return elapsedTime.toString();
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
  @Block(classes = ElapsedTime.class, methodName = "reset")
  public void reset(Object elapsedTimeArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".reset");
      ElapsedTime elapsedTime = checkElapsedTime(elapsedTimeArg);
      if (elapsedTime != null) {
        elapsedTime.reset();
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
  @Block(classes = ElapsedTime.class, methodName = "log")
  public void log(Object elapsedTimeArg, String label) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".log");
      ElapsedTime elapsedTime = checkElapsedTime(elapsedTimeArg);
      if (elapsedTime != null) {
        elapsedTime.log(label);
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
  @Block(classes = ElapsedTime.class, methodName = "toString")
  public String toText(Object elapsedTimeArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      ElapsedTime elapsedTime = checkElapsedTime(elapsedTimeArg);
      if (elapsedTime != null) {
        return elapsedTime.toString();
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

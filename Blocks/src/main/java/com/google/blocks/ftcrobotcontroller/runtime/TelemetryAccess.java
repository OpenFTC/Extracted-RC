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
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * A class that provides JavaScript access to {@link Telemetry}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class TelemetryAccess extends Access {
  private final Telemetry telemetry;

  TelemetryAccess(BlocksOpMode blocksOpMode, String identifier, Telemetry telemetry) {
    super(blocksOpMode, identifier, "Telemetry");
    this.telemetry = telemetry;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Telemetry.class, methodName = "addData")
  public void addNumericData(String key, double data) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".addData");
      telemetry.addData(key, data);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Telemetry.class, methodName = "addData")
  public void addTextData(String key, String data) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".addData");
      if (data != null) {
        telemetry.addData(key, data);
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
  @Block(classes = Telemetry.class, methodName = "addData")
  public void addObjectData(String key, Object data) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".addData");
      // Avoid calling data.toString() in case data is null.
      telemetry.addData(key, "" + data);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Telemetry.class, methodName = "addLine")
  public void addLine(String text) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".addLine");
      telemetry.addLine(text);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Telemetry.class, methodName = "update")
  public void update() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".update");
      telemetry.update();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Telemetry.class, methodName = "speak")
  public void speakTextData(String data, String languageCode, String countryCode) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".speak");
      if (data != null) {
        telemetry.speak(data, languageCode, countryCode);
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
  @Block(classes = Telemetry.class, methodName = "speak")
  public void speakObjectData(Object data, String languageCode, String countryCode) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".speak");
      // Avoid calling data.toString() in case data is null.
      telemetry.speak("" + data, languageCode, countryCode);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Telemetry.class, methodName = "setDisplayFormat")
  public void setDisplayFormat(String displayFormatString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setDisplayFormat");
      Telemetry.DisplayFormat displayFormat =
          checkArg(displayFormatString, Telemetry.DisplayFormat.class, "displayFormat");
      if (displayFormat != null) {
        telemetry.setDisplayFormat(displayFormat);
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
  @Block(classes = Telemetry.class, methodName = "setNumDecimalPlaces")
  public void setNumDecimalPlaces(int minDecimalPlaces, int maxDecimalPlaces) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setNumDecimalPlaces");

      telemetry.setNumDecimalPlaces(minDecimalPlaces, maxDecimalPlaces);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Telemetry.class, methodName = "setMsTransmissionInterval")
  public void setMsTransmissionInterval(int interval) {
    try {
      startBlockExecution(BlockType.SETTER, ".MsTransmissionInterval");
      telemetry.setMsTransmissionInterval(interval);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Telemetry.class, methodName = "getMsTransmissionInterval")
  public int getMsTransmissionInterval() {
    try {
      startBlockExecution(BlockType.GETTER, ".MsTransmissionInterval");
      return telemetry.getMsTransmissionInterval();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

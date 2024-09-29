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
import com.google.blocks.ftcrobotcontroller.hardware.HardwareItem;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LED;

/**
 * A class that provides JavaScript access to a {@link LED}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class LedAccess extends HardwareAccess<LED> {
  private final LED led;

  LedAccess(BlocksOpMode blocksOpMode, HardwareItem hardwareItem, HardwareMap hardwareMap) {
    super(blocksOpMode, hardwareItem, hardwareMap, LED.class);
    this.led = hardwareDevice;
  }

  // from com.qualcomm.robotcore.hardware.LED

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {LED.class}, methodName = "enable")
  public void enableLed(boolean enable) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".enableLed");
      led.enable(enable);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {LED.class}, methodName = "isLightOn")
  public boolean isLightOn() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".isLightOn");
      return led.isLightOn();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {LED.class}, methodName = "on")
  public void on() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".on");
      led.on();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {LED.class}, methodName = "off")
  public void off() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".off");
      led.off();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

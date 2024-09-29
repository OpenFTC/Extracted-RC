/*
 * Copyright 2024 Google LLC
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
import com.qualcomm.hardware.sparkfun.SparkFunLEDStick;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;

/**
 * A class that provides JavaScript access to {@link SparkFunLEDStick}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class SparkFunLEDStickAccess extends HardwareAccess<SparkFunLEDStick> {
  private final SparkFunLEDStick sparkFunLEDStick;

  SparkFunLEDStickAccess(BlocksOpMode blocksOpMode, HardwareItem hardwareItem, HardwareMap hardwareMap) {
    super(blocksOpMode, hardwareItem, hardwareMap, SparkFunLEDStick.class);
    this.sparkFunLEDStick = hardwareDevice;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunLEDStick.class}, methodName = "setColor")
  public void setColor_withPosition(int position, long longColor) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setColor");
      int color = (int) (longColor & 0xFFFFFFFF);
      sparkFunLEDStick.setColor(position, color);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunLEDStick.class}, methodName = "setColor")
  public void setColor(long longColor) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setColor");
      int color = (int) (longColor & 0xFFFFFFFF);
      sparkFunLEDStick.setColor(color);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunLEDStick.class}, methodName = "setColors")
  public void setColors(String jsonColors) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setColors");
      long[] longColors = SimpleGson.getInstance().fromJson(jsonColors, long[].class);
      int[] colors = new int[longColors.length];
      for (int i = 0; i < colors.length; i++){
        colors[i] = (int)(longColors[i] & 0xFFFFFFFF);
      }
      sparkFunLEDStick.setColors(colors);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunLEDStick.class}, methodName = "setBrightness")
  public void setBrightness_withPosition(int position, int brightness) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setBrightness");
      sparkFunLEDStick.setBrightness(position, brightness);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunLEDStick.class}, methodName = "setBrightness")
  public void setBrightness(int brightness) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setBrightness");
      sparkFunLEDStick.setBrightness(brightness);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunLEDStick.class}, methodName = "turnAllOff")
  public void turnAllOff() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".turnAllOff");
      sparkFunLEDStick.turnAllOff();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

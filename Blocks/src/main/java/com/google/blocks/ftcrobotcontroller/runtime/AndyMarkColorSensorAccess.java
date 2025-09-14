/*
 * Copyright 2025 Google LLC
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
import com.qualcomm.hardware.andymark.AndyMarkColorSensor;
import com.qualcomm.hardware.andymark.AndyMarkColorSensor.ProximityGain;
import com.qualcomm.hardware.andymark.AndyMarkColorSensor.ProximityPulseLength;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * A class that provides JavaScript access to {@link AndyMarkColorSensor}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class AndyMarkColorSensorAccess extends HardwareAccess<AndyMarkColorSensor> {
  private final AndyMarkColorSensor andyMarkColorSensor;

  AndyMarkColorSensorAccess(BlocksOpMode blocksOpMode, HardwareItem hardwareItem, HardwareMap hardwareMap) {
    super(blocksOpMode, hardwareItem, hardwareMap, AndyMarkColorSensor.class);
    this.andyMarkColorSensor = hardwareDevice;
  }

  // Properties

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "alpha")
  public int getAlpha() {
    try {
      startBlockExecution(BlockType.GETTER, ".Alpha");
      return andyMarkColorSensor.alpha();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "argb")
  public int getArgb() {
    try {
      startBlockExecution(BlockType.GETTER, ".Argb");
      return andyMarkColorSensor.argb();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "red")
  public int getRed() {
    try {
      startBlockExecution(BlockType.GETTER, ".Red");
      return andyMarkColorSensor.red();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "green")
  public int getGreen() {
    try {
      startBlockExecution(BlockType.GETTER, ".Green");
      return andyMarkColorSensor.green();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "blue")
  public int getBlue() {
    try {
      startBlockExecution(BlockType.GETTER, ".Blue");
      return andyMarkColorSensor.blue();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "getLightDetected")
  public double getLightDetected() {
    try {
      startBlockExecution(BlockType.GETTER, ".LightDetected");
      return andyMarkColorSensor.getLightDetected();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "setI2cAddress")
  public void setI2cAddress7Bit(int i2cAddr7Bit) {
    try {
      startBlockExecution(BlockType.SETTER, ".I2cAddress7Bit");
      andyMarkColorSensor.setI2cAddress(I2cAddr.create7bit(i2cAddr7Bit));
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "getI2cAddress")
  public int getI2cAddress7Bit() {
    try {
      startBlockExecution(BlockType.GETTER, ".I2cAddress7Bit");
      I2cAddr i2cAddr = andyMarkColorSensor.getI2cAddress();
      if (i2cAddr != null) {
        return i2cAddr.get7Bit();
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
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "setI2cAddress")
  public void setI2cAddress8Bit(int i2cAddr8Bit) {
    try {
      startBlockExecution(BlockType.SETTER, ".I2cAddress8Bit");
      andyMarkColorSensor.setI2cAddress(I2cAddr.create8bit(i2cAddr8Bit));
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "getI2cAddress")
  public int getI2cAddress8Bit() {
    try {
      startBlockExecution(BlockType.GETTER, ".I2cAddress8Bit");
      I2cAddr i2cAddr = andyMarkColorSensor.getI2cAddress();
      if (i2cAddr != null) {
        return i2cAddr.get8Bit();
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
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "getDistance")
  public double getDistance(String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getDistance");
      DistanceUnit distanceUnit = checkArg(distanceUnitString, DistanceUnit.class, "unit");
      if (distanceUnit != null) {
        return andyMarkColorSensor.getDistance(distanceUnit);
      }
      return 0.0;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "getNormalizedColors")
  public String getNormalizedColors() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getNormalizedColors");
      NormalizedRGBA color = andyMarkColorSensor.getNormalizedColors();
      return "{ \"Red\":" + color.red +
          ", \"Green\":" + color.green +
          ", \"Blue\":" + color.blue +
          ", \"Alpha\":" + color.alpha +
          ", \"Color\":" + color.toColor() + " }";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "setProximityGain")
  public void setProximityGain(String gainString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setProximityGain");
      ProximityGain gain = checkArg(gainString, ProximityGain.class, "gain");
      if (gain != null) {
        andyMarkColorSensor.setProximityGain(gain);
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
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "setProximityLedPulses")
  public void setProximityLedPulses(int pulses) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setProximityLedPulses");
      andyMarkColorSensor.setProximityLedPulses(pulses);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "setProximityLedPulseLength")
  public void setProximityLedPulseLength(String pulseLengthString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setProximityLedPulseLength");
      ProximityPulseLength pulseLength = checkArg(pulseLengthString, ProximityPulseLength.class, "pulseLength");
      if (pulseLength != null) {
        andyMarkColorSensor.setProximityLedPulseLength(pulseLength);
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
  @Block(classes = {AndyMarkColorSensor.class}, methodName = "configureProximitySettings")
  public void configureProximitySettings(String gainString, int pulses, String pulseLengthString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".configureProximitySettings");
      ProximityGain gain = checkArg(gainString, ProximityGain.class, "gain");
      ProximityPulseLength pulseLength = checkArg(pulseLengthString, ProximityPulseLength.class, "pulseLength");
      if (gain != null && pulseLength != null) {
        andyMarkColorSensor.configureProximitySettings(gain, pulses, pulseLength);
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

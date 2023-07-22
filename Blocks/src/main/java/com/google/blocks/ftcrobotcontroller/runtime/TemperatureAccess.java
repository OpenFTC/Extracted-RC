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
import org.firstinspires.ftc.robotcore.external.navigation.TempUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Temperature;

/**
 * A class that provides JavaScript access to {@link Temperature}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class TemperatureAccess extends Access {

  TemperatureAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "Temperature");
  }

  private Temperature checkTemperature(Object temperatureArg) {
    return checkArg(temperatureArg, Temperature.class, "temperature");
  }

  private TempUnit checkTempUnit(String tempUnitString) {
    return checkArg(tempUnitString, TempUnit.class, "tempUnit");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Temperature.class, fieldName = "unit")
  public String getTempUnit(Object temperatureArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".TempUnit");
      Temperature temperature = checkTemperature(temperatureArg);
      if (temperature != null) {
        TempUnit tempUnit = temperature.unit;
        if (tempUnit != null) {
          return tempUnit.toString();
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
  @Block(classes = Temperature.class, fieldName = "temperature")
  public double getTemperature(Object temperatureArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".Temperature");
      Temperature temperature = checkTemperature(temperatureArg);
      if (temperature != null) {
        return temperature.temperature;
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
  @Block(classes = Temperature.class, fieldName = "acquisitionTime")
  public long getAcquisitionTime(Object temperatureArg) {
    try {
      startBlockExecution(BlockType.GETTER, ".AcquisitionTime");
      Temperature temperature = checkTemperature(temperatureArg);
      if (temperature != null) {
        return temperature.acquisitionTime;
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
  @Block(classes = Temperature.class, constructor = true)
  public Temperature create() {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new Temperature();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Temperature.class, constructor = true)
  public Temperature create_withArgs(
      String tempUnitString, double temperature, long acquisitionTime) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      TempUnit tempUnit = checkTempUnit(tempUnitString);
      if (tempUnit != null) {
        return new Temperature(tempUnit, temperature, acquisitionTime);
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
  @Block(classes = Temperature.class, methodName = "toUnit")
  public Temperature toTempUnit(Object temperatureArg, String tempUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toTempUnit");
      Temperature temperature = checkTemperature(temperatureArg);
      TempUnit tempUnit = checkTempUnit(tempUnitString);
      if (temperature != null && tempUnit != null) {
        return temperature.toUnit(tempUnit);
      }
      return null;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

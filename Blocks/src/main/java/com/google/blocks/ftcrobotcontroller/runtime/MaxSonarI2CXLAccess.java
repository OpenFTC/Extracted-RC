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
import com.qualcomm.hardware.maxbotix.MaxSonarI2CXL;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * A class that provides JavaScript access to {@link MaxSonarI2CXL}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class MaxSonarI2CXLAccess extends HardwareAccess<MaxSonarI2CXL> {
  private final MaxSonarI2CXL maxSonarI2CXL;

  MaxSonarI2CXLAccess(BlocksOpMode blocksOpMode, HardwareItem hardwareItem, HardwareMap hardwareMap) {
    super(blocksOpMode, hardwareItem, hardwareMap, MaxSonarI2CXL.class);
    this.maxSonarI2CXL = hardwareDevice;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = MaxSonarI2CXL.class, methodName = "getDistanceSync")
  public double getDistanceSync(String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getDistanceSync");
      DistanceUnit distanceUnit = checkArg(distanceUnitString, DistanceUnit.class, "distanceUnit");
      if (distanceUnit != null) {
        return maxSonarI2CXL.getDistanceSync(distanceUnit);
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
  @Block(classes = MaxSonarI2CXL.class, methodName = "getDistanceSync")
  public double getDistanceSync_withDelay(int sonarPropagationDelayMs, String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getDistanceSync");
      DistanceUnit distanceUnit = checkArg(distanceUnitString, DistanceUnit.class, "distanceUnit");
      if (distanceUnit != null) {
        return maxSonarI2CXL.getDistanceSync(sonarPropagationDelayMs, distanceUnit);
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
  @Block(classes = MaxSonarI2CXL.class, methodName = "getDistanceAsync")
  public double getDistanceAsync(String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getDistanceAsync");
      DistanceUnit distanceUnit = checkArg(distanceUnitString, DistanceUnit.class, "distanceUnit");
      if (distanceUnit != null) {
        return maxSonarI2CXL.getDistanceAsync(distanceUnit);
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
  @Block(classes = MaxSonarI2CXL.class, methodName = "getDistanceAsync")
  public double getDistanceAsync_withDelay(int sonarPropagationDelayMs, String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getDistanceAsync");
      DistanceUnit distanceUnit = checkArg(distanceUnitString, DistanceUnit.class, "distanceUnit");
      if (distanceUnit != null) {
        return maxSonarI2CXL.getDistanceAsync(sonarPropagationDelayMs, distanceUnit);
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
  @Block(classes = MaxSonarI2CXL.class, methodName = "setI2cAddress")
  public void setI2cAddress7Bit(int i2cAddr7Bit) {
    try {
      startBlockExecution(BlockType.SETTER, ".I2cAddress7Bit");
      maxSonarI2CXL.setI2cAddress(I2cAddr.create7bit(i2cAddr7Bit));
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = MaxSonarI2CXL.class, methodName = "getI2cAddress")
  public int getI2cAddress7Bit() {
    try {
      startBlockExecution(BlockType.GETTER, ".I2cAddress7Bit");
      I2cAddr i2cAddr = maxSonarI2CXL.getI2cAddress();
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
  @Block(classes = MaxSonarI2CXL.class, methodName = "setI2cAddress")
  public void setI2cAddress8Bit(int i2cAddr8Bit) {
    try {
      startBlockExecution(BlockType.SETTER, ".I2cAddress8Bit");
      maxSonarI2CXL.setI2cAddress(I2cAddr.create8bit(i2cAddr8Bit));
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = MaxSonarI2CXL.class, methodName = "getI2cAddress")
  public int getI2cAddress8Bit() {
    try {
      startBlockExecution(BlockType.GETTER, ".I2cAddress8Bit");
      I2cAddr i2cAddr = maxSonarI2CXL.getI2cAddress();
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
  @Block(classes = MaxSonarI2CXL.class, methodName = "writeI2cAddrToSensorEEPROM")
  public void writeI2cAddrToSensorEEPROM(byte addr) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".writeI2cAddrToSensorEEPROM");
      maxSonarI2CXL.writeI2cAddrToSensorEEPROM(addr);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

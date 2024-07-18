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
import com.qualcomm.hardware.digitalchickenlabs.CachingOctoQuad;
import com.qualcomm.hardware.digitalchickenlabs.CachingOctoQuad.CachingMode;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuadBase.ChannelBankConfig;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuadBase.EncoderDirection;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuadBase.I2cRecoveryMode;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuadImpl;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * A class that provides JavaScript access to {@link CachingOctoQuad}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class OctoQuadAccess extends HardwareAccess<OctoQuadImpl> {
  private final CachingOctoQuad octoQuad;

  OctoQuadAccess(BlocksOpMode blocksOpMode, HardwareItem hardwareItem, HardwareMap hardwareMap) {
    super(blocksOpMode, hardwareItem, hardwareMap, OctoQuadImpl.class);
    this.octoQuad = hardwareDevice;
  }

  // From com.qualcomm.hardware.digitalchickenlabs.OctoQuadBase.

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {CachingOctoQuad.class}, methodName = "getChipId")
  public int getChipId() {
    try {
      startBlockExecution(BlockType.GETTER, ".ChipId");
      return octoQuad.getChipId();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {CachingOctoQuad.class}, methodName = "getFirmwareVersionString")
  public String getFirmwareVersionString() {
    try {
      startBlockExecution(BlockType.GETTER, ".FirmwareVersionString");
      return octoQuad.getFirmwareVersionString();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {CachingOctoQuad.class}, methodName = "resetAllPositions")
  public void resetAllPositions() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".resetAllPositions");
      octoQuad.resetAllPositions();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {CachingOctoQuad.class}, methodName = "resetSinglePosition")
  public void resetSinglePosition(int index) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".resetSinglePosition");
      octoQuad.resetSinglePosition(index);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {CachingOctoQuad.class}, methodName = "setSingleEncoderDirection")
  public void setSingleEncoderDirection(int index, String encoderDirectionString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setEncoderDirection");
      EncoderDirection encoderDirection = checkArg(encoderDirectionString, EncoderDirection.class, "");
      if (encoderDirection != null) {
        octoQuad.setSingleEncoderDirection(index, encoderDirection);
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
  @Block(classes = {CachingOctoQuad.class}, methodName = "getSingleEncoderDirection")
  public String getSingleEncoderDirection(int index) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".SingleEncoderDirection");
      EncoderDirection encoderDirection = octoQuad.getSingleEncoderDirection(index);
      if (encoderDirection != null) {
        return encoderDirection.toString();
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
  @Block(classes = {CachingOctoQuad.class}, methodName = "setAllVelocitySampleIntervals")
  public void setAllVelocitySampleIntervals(int intervalMillis) {
    try {
      startBlockExecution(BlockType.SETTER, ".AllVelocitySampleIntervals");
      octoQuad.setAllVelocitySampleIntervals(intervalMillis);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {CachingOctoQuad.class}, methodName = "setSingleVelocitySampleInterval")
  public void setSingleVelocitySampleInterval(int index, int intervalMillis) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".SingleVelocitySampleInterval");
      octoQuad.setSingleVelocitySampleInterval(index, intervalMillis);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {CachingOctoQuad.class}, methodName = "getSingleVelocitySampleInterval")
  public int getSingleVelocitySampleInterval(int index) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getSingleVelocitySampleInterval");
      return octoQuad.getSingleVelocitySampleInterval(index);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {CachingOctoQuad.class}, methodName = "setSingleChannelPulseWidthParams")
  public void setSingleChannelPulseWidthParams(int index, int min_length_us, int max_length_us) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setSingleChannelPulseWidthParams");
      octoQuad.setSingleChannelPulseWidthParams(index, min_length_us, max_length_us);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {CachingOctoQuad.class}, methodName = "resetEverything")
  public void resetEverything() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".resetEverything");
      octoQuad.resetEverything();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {CachingOctoQuad.class}, methodName = "setChannelBankConfig")
  public void setChannelBankConfig(String channelBankConfigString) {
    try {
      startBlockExecution(BlockType.SETTER, ".ChannelBankConfig");
      ChannelBankConfig channelBankConfig = checkArg(channelBankConfigString, ChannelBankConfig.class, "");
      if (channelBankConfig != null) {
        octoQuad.setChannelBankConfig(channelBankConfig);
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
  @Block(classes = {CachingOctoQuad.class}, methodName = "getChannelBankConfig")
  public String getChannelBankConfig() {
    try {
      startBlockExecution(BlockType.GETTER, ".ChannelBankConfig");
      ChannelBankConfig channelBankConfig = octoQuad.getChannelBankConfig();
      if (channelBankConfig != null) {
        return channelBankConfig.toString();
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
  @Block(classes = {CachingOctoQuad.class}, methodName = "setI2cRecoveryMode")
  public void setI2cRecoveryMode(String i2cRecoveryModeString) {
    try {
      startBlockExecution(BlockType.SETTER, ".I2cRecoveryMode");
      I2cRecoveryMode i2cRecoveryMode = checkArg(i2cRecoveryModeString, I2cRecoveryMode.class, "");
      if (i2cRecoveryMode != null) {
        octoQuad.setI2cRecoveryMode(i2cRecoveryMode);
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
  @Block(classes = {CachingOctoQuad.class}, methodName = "getI2cRecoveryMode")
  public String getI2cRecoveryMode() {
    try {
      startBlockExecution(BlockType.GETTER, ".I2cRecoveryMode");
      I2cRecoveryMode i2cRecoveryMode = octoQuad.getI2cRecoveryMode();
      if (i2cRecoveryMode != null) {
        return i2cRecoveryMode.toString();
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
  @Block(classes = {CachingOctoQuad.class}, methodName = "saveParametersToFlash")
  public void saveParametersToFlash() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".saveParametersToFlash");
      octoQuad.saveParametersToFlash();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  // From com.qualcomm.hardware.digitalchickenlabs.CachingOctoQuad.

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {CachingOctoQuad.class}, methodName = "setCachingMode")
  public void setCachingMode(String cachingModeString) {
    try {
      startBlockExecution(BlockType.SETTER, ".CachingMode");
      CachingMode cachingMode = checkArg(cachingModeString, CachingMode.class, "");
      if (cachingMode != null) {
        octoQuad.setCachingMode(cachingMode);
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
  @Block(classes = {CachingOctoQuad.class}, methodName = "refreshCache")
  public void refreshCache() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".refreshCache");
      octoQuad.refreshCache();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {CachingOctoQuad.class}, methodName = "readSinglePosition_Caching")
  public int readSinglePosition_Caching(int index) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".readSinglePosition_Caching");
      return octoQuad.readSinglePosition_Caching(index);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {CachingOctoQuad.class}, methodName = "readSingleVelocity_Caching")
  public int readSingleVelocity_Caching(int index) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".readSingleVelocity_Caching");
      return octoQuad.readSingleVelocity_Caching(index);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

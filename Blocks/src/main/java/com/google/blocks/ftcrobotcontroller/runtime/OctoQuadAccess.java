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
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad.CachingMode;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad.ChannelBankConfig;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad.EncoderDirection;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad.I2cRecoveryMode;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad.LocalizerDataBlock;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad.LocalizerStatus;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad.LocalizerYawAxis;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

/**
 * A class that provides JavaScript access to {@link OctoQuad}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class OctoQuadAccess extends HardwareAccess<OctoQuad> {
  private final OctoQuad octoQuad;

  OctoQuadAccess(BlocksOpMode blocksOpMode, HardwareItem hardwareItem, HardwareMap hardwareMap) {
    super(blocksOpMode, hardwareItem, hardwareMap, OctoQuad.class);
    this.octoQuad = hardwareDevice;
  }

  // From com.qualcomm.hardware.digitalchickenlabs.OctoQuadBase.

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {OctoQuad.class}, methodName = "getChipId")
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
  @Block(classes = {OctoQuad.class}, methodName = "getFirmwareVersionString")
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
  @Block(classes = {OctoQuad.class}, methodName = "resetAllPositions")
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
  @Block(classes = {OctoQuad.class}, methodName = "resetSinglePosition")
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
  @Block(classes = {OctoQuad.class}, methodName = "setSingleEncoderDirection")
  public void setSingleEncoderDirection(int index, String encoderDirectionString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setEncoderDirection");
      EncoderDirection encoderDirection = checkArg(encoderDirectionString, EncoderDirection.class, "direction");
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
  @Block(classes = {OctoQuad.class}, methodName = "getSingleEncoderDirection")
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
  @Block(classes = {OctoQuad.class}, methodName = "setAllVelocitySampleIntervals")
  public void setAllVelocitySampleIntervals(int intervalMillis) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setAllVelocitySampleIntervals");
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
  @Block(classes = {OctoQuad.class}, methodName = "setSingleVelocitySampleInterval")
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
  @Block(classes = {OctoQuad.class}, methodName = "getSingleVelocitySampleInterval")
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
  @Block(classes = {OctoQuad.class}, methodName = "setSingleChannelPulseWidthParams")
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
  @Block(classes = {OctoQuad.class}, methodName = "resetEverything")
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
  @Block(classes = {OctoQuad.class}, methodName = "setChannelBankConfig")
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
  @Block(classes = {OctoQuad.class}, methodName = "getChannelBankConfig")
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
  @Block(classes = {OctoQuad.class}, methodName = "setI2cRecoveryMode")
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
  @Block(classes = {OctoQuad.class}, methodName = "getI2cRecoveryMode")
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
  @Block(classes = {OctoQuad.class}, methodName = "saveParametersToFlash")
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

  // From com.qualcomm.hardware.digitalchickenlabs.OctoQuad.

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {OctoQuad.class}, methodName = "setCachingMode")
  public void setCachingMode(String cachingModeString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setCachingMode");
      if (cachingModeString.equals("NONE")) {
        reportWarning("OctoQuad.CachingMode NONE is obsolete.");
      } else {
        CachingMode cachingMode = checkArg(cachingModeString, CachingMode.class, "mode");
        if (cachingMode != null) {
          octoQuad.setCachingMode(cachingMode);
        }
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
  @Block(classes = {OctoQuad.class}, methodName = "refreshCache")
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
  @Block(classes = {OctoQuad.class}, methodName = "readSinglePosition_Caching")
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
  @Block(classes = {OctoQuad.class}, methodName = "readSingleVelocity_Caching")
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

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {OctoQuad.class}, methodName = "setSingleChannelPulseWidthTracksWrap")
  public void setSingleChannelPulseWidthTracksWrap(int index, boolean trackWrap) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setSingleChannelPulseWidthTracksWrap");
      octoQuad.setSingleChannelPulseWidthTracksWrap(index, trackWrap);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {OctoQuad.class}, methodName = "getSingleChannelPulseWidthTracksWrap")
  public boolean getSingleChannelPulseWidthTracksWrap(int index) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getSingleChannelPulseWidthTracksWrap");
      return octoQuad.getSingleChannelPulseWidthTracksWrap(index);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {OctoQuad.class}, methodName = "getLocalizerHeadingAxisChoice")
  public String getLocalizerHeadingAxisChoice() {
    try {
      startBlockExecution(BlockType.GETTER, ".LocalizerYawAxis");
      LocalizerYawAxis localizerYawAxis = octoQuad.getLocalizerHeadingAxisChoice();
      if (localizerYawAxis != null) {
        return localizerYawAxis.toString();
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
  @Block(classes = {OctoQuad.class}, methodName = "getLocalizerStatus")
  public String getLocalizerStatus() {
    try {
      startBlockExecution(BlockType.GETTER, ".LocalizerStatus");
      LocalizerStatus localizerStatus = octoQuad.getLocalizerStatus();
      if (localizerStatus != null) {
        return localizerStatus.toString();
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
  @Block(classes = {OctoQuad.class}, methodName = "setAllLocalizerParameters")
  public void setAllLocalizerParameters(
      int portX, int portY,
      float ticksPerMM_x, float ticksPerMM_y,
      float tcpOffsetMM_X, float tcpOffsetMM_Y,
      float headingScalar, int velocityIntervalMs) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setAllLocalizerParameters");
      octoQuad.setAllLocalizerParameters(
          portX, portY, ticksPerMM_x, ticksPerMM_y,
          tcpOffsetMM_X, tcpOffsetMM_Y, headingScalar, velocityIntervalMs);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {OctoQuad.class}, methodName = "readLocalizerData")
  public String readLocalizerData() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".readLocalizerData");
      LocalizerDataBlock localizerDataBlock = octoQuad.readLocalizerData();
      return localizerDataBlockToJson(localizerDataBlock);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  private static String localizerDataBlockToJson(LocalizerDataBlock localizerDataBlock) {
    String originalJson = toJson(localizerDataBlock);
    if (!originalJson.endsWith("}")) {
      RobotLog.ww("OctoQuadAccess", "Unexpected: result from toJson(LocalizerDataBlock) does not end with '}'!");
      return originalJson;
    }
    String json = new StringBuilder()
        .append(originalJson.substring(0, originalJson.length() - 1))
        .append(",")
        .append("\"isDataValid\":").append(toJson(localizerDataBlock.isDataValid()))
        .append("}")
        .toString();
    return json;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {OctoQuad.class}, methodName = "setLocalizerPose")
  public void setLocalizerPose(int posX_mm, int posY_mm, float heading_rad) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setLocalizerPose");
      octoQuad.setLocalizerPose(posX_mm, posY_mm, heading_rad);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {OctoQuad.class}, methodName = "setLocalizerHeading")
  public void setLocalizerHeading(float heading_rad) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setLocalizerHeading");
      octoQuad.setLocalizerHeading(heading_rad);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {OctoQuad.class}, methodName = "resetLocalizerAndCalibrateIMU")
  public void resetLocalizerAndCalibrateIMU() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".resetLocalizerAndCalibrateIMU");
      octoQuad.resetLocalizerAndCalibrateIMU();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

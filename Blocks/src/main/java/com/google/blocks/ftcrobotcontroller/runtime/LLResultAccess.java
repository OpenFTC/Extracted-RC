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
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.ColorResult;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

/**
 * A class that provides JavaScript access to LLResult.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class LLResultAccess extends Access {

  LLResultAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "LLResult");
  }

  private LLResult checkLLResult(Object llResultArg) {
    return checkArg(llResultArg, LLResult.class, "llResult");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = LLResult.class, methodName = "getStaleness")
  public long getStaleness(Object llResultArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getStaleness");
      LLResult llResult = checkLLResult(llResultArg);
      if (llResultArg != null) {
        return llResult.getStaleness();
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
  @Block(classes = LLResult.class, methodName = "toString")
  public String toText(Object llResultArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      LLResult llResult = checkLLResult(llResultArg);
      if (llResultArg != null) {
        return llResult.toString();
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
  @Block(exclusiveToBlocks = true)
  public String pose3DToText(String json) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      Pose3D pose3D = fromJson(json, Pose3D.class);
      return pose3D.toString();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  // llResultToJson is called from MiscAccess.objectToJson.
  static String llResultToJson(LLResult llResult) {
      StringBuilder json = new StringBuilder();
      json.append("{")
          .append("\"PythonOutput\":").append(toJson(llResult.getPythonOutput()))
          .append(",")
          .append("\"FiducialResults\":").append(toJson(llResult.getFiducialResults()))
          .append(",")
          .append("\"ColorResults\":").append(toJson(llResult.getColorResults()))
          .append(",")
          .append("\"ControlHubTimeStamp\":").append(llResult.getControlHubTimeStamp())
          .append(",")
          .append("\"ControlHubTimeStampNanos\":").append(llResult.getControlHubTimeStampNanos())
          .append(",")
          .append("\"FocusMetric\":").append(llResult.getFocusMetric())
          .append(",")
          .append("\"Botpose\":").append(toJson(llResult.getBotpose()))
          .append(",")
          .append("\"Botpose_MT2\":").append(toJson(llResult.getBotpose_MT2()))
          .append(",")
          .append("\"BotposeTagCount\":").append(llResult.getBotposeTagCount())
          .append(",")
          .append("\"BotposeSpan\":").append(llResult.getBotposeSpan())
          .append(",")
          .append("\"BotposeAvgDist\":").append(llResult.getBotposeAvgDist())
          .append(",")
          .append("\"BotposeAvgArea\":").append(llResult.getBotposeAvgArea())
          .append(",")
          .append("\"CaptureLatency\":").append(llResult.getCaptureLatency())
          .append(",")
          .append("\"Tx\":").append(llResult.getTx())
          .append(",")
          .append("\"Ty\":").append(llResult.getTy())
          .append(",")
          .append("\"TxNC\":").append(llResult.getTxNC())
          .append(",")
          .append("\"TyNC\":").append(llResult.getTyNC())
          .append(",")
          .append("\"Ta\":").append(llResult.getTa())
          .append(",")
          .append("\"PipelineIndex\":").append(llResult.getPipelineIndex())
          .append(",")
          .append("\"TargetingLatency\":").append(llResult.getTargetingLatency())
          .append(",")
          .append("\"Timestamp\":").append(llResult.getTimestamp())
          .append(",")
          .append("\"PipelineType\":\"").append(llResult.getPipelineType()).append("\"")
          .append(",")
          .append("\"IsValid\":\"").append(llResult.isValid()).append("\"")
          .append("}");
      return json.toString();
  }
}

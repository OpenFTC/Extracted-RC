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
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;

/**
 * A class that provides JavaScript access to {@link Limelight3A}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class Limelight3AAccess extends HardwareAccess<Limelight3A> {
  private final Limelight3A limelight3a;

  Limelight3AAccess(BlocksOpMode blocksOpMode, HardwareItem hardwareItem, HardwareMap hardwareMap) {
    super(blocksOpMode, hardwareItem, hardwareMap, Limelight3A.class);
    this.limelight3a = hardwareDevice;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "start")
  public void start() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".start");
      limelight3a.start();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "pause")
  public void pause() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".pause");
      limelight3a.pause();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "stop")
  public void stop() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".stop");
      limelight3a.stop();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "isRunning")
  public boolean isRunning() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".isRunning");
      return limelight3a.isRunning();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "setPollRateHz")
  public void setPollRateHz(int rateHz) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setPollRateHz");
      limelight3a.setPollRateHz(rateHz);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "getTimeSinceLastUpdate")
  public long getTimeSinceLastUpdate() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getTimeSinceLastUpdate");
      return limelight3a.getTimeSinceLastUpdate();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "isConnected")
  public boolean isConnected() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".isConnected");
      return limelight3a.isConnected();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "getLatestResult")
  public Object getLatestResult() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getLatestResult");
      return limelight3a.getLatestResult();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "getStatus")
  public Object getStatus() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getStatus");
      return limelight3a.getStatus();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "reloadPipeline")
  public boolean reloadPipeline() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".reloadPipeline");
      return limelight3a.reloadPipeline();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "pipelineSwitch")
  public boolean pipelineSwitch(int index) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".pipelineSwitch");
      return limelight3a.pipelineSwitch(index);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "updatePythonInputs")
  public boolean updatePythonInputs_with8Doubles(double input1, double input2, double input3, double input4,
                                                 double input5, double input6, double input7, double input8) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".updatePythonInputs");
      return limelight3a.updatePythonInputs(input1, input2, input3, input4,
                                            input5, input6, input7, input8);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "updatePythonInputs")
  public boolean updatePythonInputs_withArray(String jsonInputs) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".updatePythonInputs");
      double[] inputs = SimpleGson.getInstance().fromJson(jsonInputs, double[].class);
      return limelight3a.updatePythonInputs(inputs);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "updateRobotOrientation")
  public boolean updateRobotOrientation(double yaw) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".updateRobotOrientation");
      return limelight3a.updateRobotOrientation(yaw);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Limelight3A.class}, methodName = "shutdown")
  public void shutdown() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".shutdown");
      limelight3a.shutdown();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

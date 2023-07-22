/**
 * Copyright 2021 Google LLC
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

package com.google.blocks.ftcrobotcontroller.runtime.obsolete;

import android.webkit.JavascriptInterface;
import com.google.blocks.ftcrobotcontroller.runtime.Access;
import com.google.blocks.ftcrobotcontroller.runtime.BlockType;
import com.google.blocks.ftcrobotcontroller.runtime.BlocksOpMode;

/**
 * A class that provides JavaScript access to TensorFlow Object Detection.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public final class TfodAccess extends Access {

  public TfodAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "TensorFlowObjectDetection");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void useDefaultModel() {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".useDefaultModelForPOWERPLAY");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void useModelFromAsset(String assetName, String jsonLabels,
      boolean isModelTensorFlow2, boolean isModelQuantized, int inputSize) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".useModelFromAsset");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void useModelFromFile(String fileName, String jsonLabels,
      boolean isModelTensorFlow2, boolean isModelQuantized, int inputSize) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".useModelFromFile");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void initialize(Object vuforiaBaseAccess,
      float minimumConfidence, boolean useObjectTracker, boolean enableCameraMonitoring) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".initialize");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void initializeWithMoreArgs(Object vuforiaBaseAccess,
      float minimumConfidence, boolean useObjectTracker, boolean enableCameraMonitoring,
      int numInterpreterThreads, int numExecutorThreads,
      int maxNumDetections, int timingBufferSize, double maxFrameRate,
      float trackerMaxOverlap, float trackerMinSize,
      float trackerMarginalCorrelation, float trackerMinCorrelation) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".initialize");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void activate() {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".activate");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void deactivate() {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".deactivate");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setClippingMargins(int left, int top, int right, int bottom) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setClippingMargins");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setZoom(double magnification, double aspectRatio) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setZoom");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String getRecognitions() {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".getRecognitions");
    return "[]";
  }

  // legacy blocks

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setModelFromAssetLegacy(String assetName, String jsonLabels) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".useModelFromAsset");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setModelFromFileLegacy(String fileName, String jsonLabels) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".useModelFromFile");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void initializeWithIsModelTensorFlow2Legacy(
      Object vuforiaBaseAccess,
      float minimumConfidence, boolean useObjectTracker, boolean enableCameraMonitoring,
      boolean isModelTensorFlow2) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".initialize");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void initializeWithAllArgsLegacy(Object vuforiaBaseAccess,
      float minimumConfidence, boolean useObjectTracker, boolean enableCameraMonitoring,
      boolean isModelTensorFlow2, boolean isModelQuantized, int inputSize,
      int numInterpreterThreads, int numExecutorThreads,
      int maxNumDetections, int timingBufferSize, double maxFrameRate,
      float trackerMaxOverlap, float trackerMinSize,
      float trackerMarginalCorrelation, float trackerMinCorrelation) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".initialize");
  }
}

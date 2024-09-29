/*
 * Copyright (c) 2023 FIRST
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.google.blocks.ftcrobotcontroller.runtime.obsolete;

import android.webkit.JavascriptInterface;
import com.google.blocks.ftcrobotcontroller.runtime.Access;
import com.google.blocks.ftcrobotcontroller.runtime.BlockType;
import com.google.blocks.ftcrobotcontroller.runtime.BlocksOpMode;

/**
 * A class that provides JavaScript access to TfodProcessor.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public final class TensorFlowAccess extends Access {

  public TensorFlowAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, ""); // blocks have various first names.
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object createBuilder() {
    handleObsoleteBlockExecution(BlockType.CREATE, "TfodProcessor.Builder", "");
    return null;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setModelAssetName(Object tfodProcessorBuilderArg, String modelAssetName) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setModelAssetName");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setModelFileName(Object tfodProcessorBuilderArg, String modelFileName) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setModelFileName");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setModelLabels(Object tfodProcessorBuilderArg, String jsonLabels) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setModelLabels");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setIsModelTensorFlow2(Object tfodProcessorBuilderArg, boolean isModelTensorFlow2) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setIsModelTensorFlow2");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setIsModelQuantized(Object tfodProcessorBuilderArg, boolean isModelQuantized) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setIsModelQuantized");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setModelInputSize(Object tfodProcessorBuilderArg, int modelInputSize) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setModelInputSize");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setModelAspectRatio(Object tfodProcessorBuilderArg, double modelAspectRatio) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setModelAspectRatio");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setNumDetectorThreads(Object tfodProcessorBuilderArg, int numDetectorThreads) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setNumDetectorThreads");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setNumExecutorThreads(Object tfodProcessorBuilderArg, int numExecutorThreads) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setNumExecutorThreads");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setMaxNumRecognitions(Object tfodProcessorBuilderArg, int maxNumRecognitions) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setMaxNumRecognitions");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setUseObjectTracker(Object tfodProcessorBuilderArg, boolean useObjectTracker) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setUseObjectTracker");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setTrackerMaxOverlap(Object tfodProcessorBuilderArg, float trackerMaxOverlap) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setTrackerMaxOverlap");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setTrackerMinSize(Object tfodProcessorBuilderArg, float trackerMinSize) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setTrackerMinSize");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setTrackerMarginalCorrelation(Object tfodProcessorBuilderArg, float trackerMarginalCorrelation) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setTrackerMarginalCorrelation");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setTrackerMinCorrelation(Object tfodProcessorBuilderArg, float trackerMinCorrelation) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setTrackerMinCorrelation");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object build(Object tfodProcessorBuilderArg) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".build");
    return null;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object easyCreateWithDefaults() {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor", ".easyCreateWithDefaults");
    return null;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setMinResultConfidence(Object tfodProcessorArg, float minResultConfidence) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor", ".setMinResultConfidence");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setClippingMargins(Object tfodProcessorArg, int left, int top, int right, int bottom) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor", ".setClippingMargins");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setZoom(Object tfodProcessorArg, double magnification) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor", ".setZoom");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String getRecognitions(Object tfodProcessorArg) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor", ".getRecognitions");
    return "[]";
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String getFreshRecognitions(Object tfodProcessorArg) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, "TfodProcessor", ".getFreshRecognitions");
    return "[]";
  }
}

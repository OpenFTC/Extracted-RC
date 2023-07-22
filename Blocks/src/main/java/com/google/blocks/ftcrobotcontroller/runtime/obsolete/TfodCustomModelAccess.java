/**
 * Copyright 2020 Google LLC
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
import com.google.blocks.ftcrobotcontroller.runtime.BlockType;
import com.google.blocks.ftcrobotcontroller.runtime.BlocksOpMode;

/**
 * A class that provides JavaScript access to TensorFlow Object Detection for a custom model.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public final class TfodCustomModelAccess extends TfodBaseAccess {

  public TfodCustomModelAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "TensorFlowObjectDetectionCustomModel");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setModelFromAsset(String assetName, String jsonLabels) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setModelFromAsset");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setModelFromFile(String tfliteModelFilename, String jsonLabels) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setModelFromFile");
  }
}

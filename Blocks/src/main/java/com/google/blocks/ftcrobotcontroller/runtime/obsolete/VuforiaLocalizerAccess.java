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

package com.google.blocks.ftcrobotcontroller.runtime.obsolete;

import android.webkit.JavascriptInterface;
import com.google.blocks.ftcrobotcontroller.runtime.Access;
import com.google.blocks.ftcrobotcontroller.runtime.BlockType;
import com.google.blocks.ftcrobotcontroller.runtime.BlocksOpMode;

/**
 * A class that provides JavaScript access to VuforiaLocalizer.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public class VuforiaLocalizerAccess extends Access {

  public VuforiaLocalizerAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "VuforiaLocalizer");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object create(Object vuforiaLocalizerParameters) {
    handleObsoleteBlockExecution(BlockType.CREATE, "");
    return null;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object loadTrackablesFromAsset(Object vuforiaLocalizerArg, String assetName) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".loadTrackablesFromAsset");
    return null;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object loadTrackablesFromFile(Object vuforiaLocalizerArg, String absoluteFileName) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".loadTrackablesFromFile");
    return null;
  }
}

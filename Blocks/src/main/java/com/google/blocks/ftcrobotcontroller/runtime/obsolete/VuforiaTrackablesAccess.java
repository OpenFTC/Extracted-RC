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
 * A class that provides JavaScript access to VuforiaTrackables.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public class VuforiaTrackablesAccess extends Access {

  public VuforiaTrackablesAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "VuforiaTrackables");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public int getSize(Object vuforiaTrackablesArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".Size");
    return 0;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String getName(Object vuforiaTrackablesArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".Name");
    return "";
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object getLocalizer(Object vuforiaTrackablesArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".Localizer");
    return null;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object get(Object vuforiaTrackablesArg, int index) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".get");
    return null;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setName(Object vuforiaTrackablesArg, String name) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setName");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void activate(Object vuforiaTrackablesArg) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".activate");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void deactivate(Object vuforiaTrackablesArg) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".deactivate");
  }
}

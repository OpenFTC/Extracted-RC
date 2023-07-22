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
 * A class that provides JavaScript access to VuforiaTrackable.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public class VuforiaTrackableAccess extends Access {

  public VuforiaTrackableAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "VuforiaTrackable");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setLocation(Object vuforiaTrackableArg, Object matrixArg) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setLocation");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object getLocation(Object vuforiaTrackableArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".Location");
    return null;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setUserData(Object vuforiaTrackableArg, Object userData) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setUserData");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object getUserData(Object vuforiaTrackableArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".UserData");
    return null;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object getTrackables(Object vuforiaTrackableArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".Trackables");
    return null;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setName(Object vuforiaTrackableArg, String name) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setName");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String getName(Object vuforiaTrackableArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".Name");
    return "";
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object getListener(Object vuforiaTrackableArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".Listener");
    return null;
  }
}

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
 * A class that provides JavaScript access to VuforiaTrackableDefaultListener.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public class VuforiaTrackableDefaultListenerAccess extends Access {
  public VuforiaTrackableDefaultListenerAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "VuforiaTrackableDefaultListener");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setPhoneInformation(
      Object vuforiaTrackableDefaultListenerArg, Object phoneLocationOnRobotArg,
      String cameraDirectionString) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setPhoneInformation");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setCameraLocationOnRobot(
      Object vuforiaTrackableDefaultListenerArg, String cameraNameString, Object cameraLocationOnRobotArg) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setCameraLocationOnRobot");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public boolean isVisible(Object vuforiaTrackableDefaultListenerArg) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".isVisible");
    return false;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object getUpdatedRobotLocation(Object vuforiaTrackableDefaultListenerArg) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".getUpdatedRobotLocation");
    return null;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object getPose(Object vuforiaTrackableDefaultListenerArg) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".getPose");
    return null;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String getRelicRecoveryVuMark(Object vuforiaTrackableDefaultListenerArg) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".getRelicRecoveryVuMark");
    return "UNKNOWN";
  }
}

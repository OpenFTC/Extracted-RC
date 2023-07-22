/*
 * Copyright 2017 Google LLC
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
 * A class that provides JavaScript access to Vuforia for Relic Recovery (2017-2018).
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public final class VuforiaRelicRecoveryAccess extends VuforiaBaseAccess {
  public VuforiaRelicRecoveryAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "VuforiaRelicRecovery");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void initialize(String vuforiaLicenseKey, String cameraDirectionString,
      boolean enableCameraMonitoring, String cameraMonitorFeedbackString,
      float dx, float dy, float dz, float xAngle, float yAngle, float zAngle) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".initialize");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void initializeExtended(String vuforiaLicenseKey, String cameraDirectionString,
      boolean useExtendedTracking,
      boolean enableCameraMonitoring, String cameraMonitorFeedbackString,
      float dx, float dy, float dz, float xAngle, float yAngle, float zAngle,
      boolean useCompetitionFieldTargetLocations) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".initialize");
  }
}

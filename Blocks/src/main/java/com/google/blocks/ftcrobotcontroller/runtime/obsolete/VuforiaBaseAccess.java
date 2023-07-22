/*
 * Copyright 2018 Google LLC
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
 * An abstract class for classes that provides JavaScript access to VuforiaBase.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
abstract class VuforiaBaseAccess extends Access {

  VuforiaBaseAccess(BlocksOpMode blocksOpMode, String identifier, String blockFirstName) {
    super(blocksOpMode, identifier, blockFirstName);
  }

  // Javascript methods

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void initialize_withCameraDirection(String vuforiaLicenseKey, String cameraDirectionString,
      boolean useExtendedTracking, boolean enableCameraMonitoring, String cameraMonitorFeedbackString,
      float dx, float dy, float dz, float xAngle, float yAngle, float zAngle,
      boolean useCompetitionFieldTargetLocations) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".initialize");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void initialize_withCameraDirection_2(String cameraDirectionString,
      boolean useExtendedTracking, boolean enableCameraMonitoring, String cameraMonitorFeedbackString,
      float dx, float dy, float dz,
      String axesOrderString, float firstAngle, float secondAngle, float thirdAngle,
      boolean useCompetitionFieldTargetLocations) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".initialize");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void initialize_withWebcam(String cameraNameString, String webcamCalibrationFilename,
      boolean useExtendedTracking, boolean enableCameraMonitoring, String cameraMonitorFeedbackString,
      float dx, float dy, float dz, float xAngle, float yAngle, float zAngle,
      boolean useCompetitionFieldTargetLocations) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".initialize");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void initialize_withWebcam_2(String cameraNameString, String webcamCalibrationFilename,
      boolean useExtendedTracking, boolean enableCameraMonitoring, String cameraMonitorFeedbackString,
      float dx, float dy, float dz,
      String axesOrderString, float firstAngle, float secondAngle, float thirdAngle,
      boolean useCompetitionFieldTargetLocations) {
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
  public void setActiveCamera(String cameraNameString) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setActiveCamera");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String track(String name) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".track");
    return "[]";
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String trackPose(String name) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".trackPose");
    return "[]";
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object getVuforiaLocalizer() {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".getVuforiaLocalizer");
    return null;
  }
}

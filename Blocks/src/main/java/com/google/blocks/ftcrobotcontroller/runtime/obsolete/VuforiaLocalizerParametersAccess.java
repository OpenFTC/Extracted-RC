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
 * A class that provides JavaScript access to VuforiaLocalizer.Parameters.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public class VuforiaLocalizerParametersAccess extends Access {
  public VuforiaLocalizerParametersAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "VuforiaLocalizer.Parameters");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object create() {
    handleObsoleteBlockExecution(BlockType.CREATE, "");
    return null;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setVuforiaLicenseKey(Object parametersArg, String vuforiaLicenseKey) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setVuforiaLicenseKey");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String getVuforiaLicenseKey(Object parametersArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".VuforiaLicenseKey");
    return "";
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setCameraDirection(Object parametersArg, String cameraDirectionString) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setCameraDirection");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String getCameraDirection(Object parametersArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".CameraDirection");
    return "";
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setCameraName(Object parametersArg, String cameraNameString) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setCameraName");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String getCameraName(Object parametersArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".CameraName");
    return "";
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void addWebcamCalibrationFile(Object parametersArg, String webcamCalibrationFilename) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".addWebcamCalibrationFile");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setUseExtendedTracking(Object parametersArg, boolean useExtendedTracking) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setUseExtendedTracking");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public boolean getUseExtendedTracking(Object parametersArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".UseExtendedTracking");
    return false;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public boolean getEnableCameraMonitoring(Object parametersArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".EnableCameraMonitoring");
    return false;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setCameraMonitorFeedback(Object parametersArg, String cameraMonitorFeedbackString) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setCameraMonitorFeedback");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String getCameraMonitorFeedback(Object parametersArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".CameraMonitorFeedback");
    return "";
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setFillCameraMonitorViewParent(Object parametersArg, boolean fillCameraMonitorViewParent) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setFillCameraMonitorViewParent");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public boolean getFillCameraMonitorViewParent(Object parametersArg) {
    handleObsoleteBlockExecution(BlockType.GETTER, ".FillCameraMonitorViewParent");
    return false;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public void setEnableCameraMonitoring(Object parametersArg, boolean enableCameraMonitoring) {
    handleObsoleteBlockExecution(BlockType.FUNCTION, ".setEnableCameraMonitoring");
  }
}

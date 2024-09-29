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

package com.google.blocks.ftcrobotcontroller.util;

import androidx.annotation.Nullable;

/**
 * An enum to represent the various identifiers that are used in the generate javascript code.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public enum Identifier {
  ACCELERATION("accelerationAccess", "accelerationIdentifierForJavaScript",
      null, null),
  ANDROID_ACCELEROMETER("androidAccelerometerAccess", "androidAccelerometerIdentifierForJavaScript",
      "androidAccelerometer", "androidAccelerometerIdentifierForFtcJava"),
  ANDROID_GYROSCOPE("androidGyroscopeAccess", "androidGyroscopeIdentifierForJavaScript",
      "androidGyroscope", "androidGyroscopeIdentifierForFtcJava"),
  ANDROID_ORIENTATION("androidOrientationAccess", "androidOrientationIdentifierForJavaScript",
      "androidOrientation", "androidOrientationIdentifierForFtcJava"),
  ANDROID_SOUND_POOL("androidSoundPoolAccess", "androidSoundPoolIdentifierForJavaScript",
      "androidSoundPool", "androidSoundPoolIdentifierForFtcJava"),
  ANDROID_TEXT_TO_SPEECH("androidTextToSpeechAccess", "androidTextToSpeechIdentifierForJavaScript",
      "androidTextToSpeech", "androidTextToSpeechIdentifierForFtcJava"),
  ANGULAR_VELOCITY("angularVelocityAccess", "angularVelocityIdentifierForJavaScript",
      null, null),
  APRIL_TAG("aprilTagAccess", "aprilTagIdentifierForJavaScript",
      null, null),
  BLINKIN_PATTERN("blinkinPatternAccess", "blinkinPatternIdentifierForJavaScript",
      null, null),
  BLOCKS_OP_MODE("blocksOpMode", null,
      null, null),
  BNO055IMU_PARAMETERS("bno055imuParametersAccess", "bno055imuParametersIdentifierForJavaScript",
      null, null),
  COLOR("colorAccess", "colorIdentifierForJavaScript",
      null, null),
  COLOR_BLOB_LOCATOR("colorBlobLocatorAccess", "colorBlobLocatorIdentifierForJavaScript",
      null, null),
  DBG_LOG("dbgLogAccess", "dbgLogIdentifierForJavaScript",
      null, null),
  ELAPSED_TIME("elapsedTimeAccess", "elapsedTimeIdentifierForJavaScript",
      null, null),
  EXPOSURE_CONTROL("exposureControlAccess", "exposureControlIdentifierForJavaScript",
      null, null),
  FOCUS_CONTROL("focusControlAccess", "focusControlIdentifierForJavaScript",
      null, null),
  GAIN_CONTROL("gainControlAccess", "gainControlIdentifierForJavaScript",
      null, null),
  GAMEPAD_1("gamepad1", null,
      "gamepad1", null),
  GAMEPAD_2("gamepad2", null,
      "gamepad2", null),
  IMU_PARAMETERS("imuParametersAccess", "imuParametersIdentifierForJavaScript",
      null, null),
  LED_EFFECT("ledEffectAccess", "ledEffectIdentifierForJavaScript",
      null, null),
  LL_RESULT("llResultAccess", "llResultIdentifierForJavaScript",
      null, null),
  LL_STATUS("llStatusAccess", "llStatusIdentifierForJavaScript",
      null, null),
  LINEAR_OP_MODE("linearOpMode", "linearOpModeIdentifierForJavaScript",
      null, null),
  MAGNETIC_FLUX("magneticFluxAccess", "magneticFluxIdentifierForJavaScript",
      null, null),
  MATRIX_F("matrixFAccess", "matrixFIdentifierForJavaScript",
      null, null),
  MISC("miscAccess", "miscIdentifierForJavaScript",
      null, null),
  NAVIGATION("navigationAccess", "navigationIdentifierForJavaScript",
      null, null),
  OPENCV("opencvAccess", "opencvIdentifierForJavaScript",
      null, null),
  OPEN_GL_MATRIX("openGLMatrixAccess", "openGLMatrixIdentifierForJavaScript",
      null, null),
  ORIENTATION("orientationAccess", "orientationIdentifierForJavaScript",
      null, null),
  PIDF_COEFFICIENTS("pidfCoefficientsAccess", "pidfCoefficientsIdentifierForJavaScript",
      null, null),
  POSITION("positionAccess", "positionIdentifierForJavaScript",
      null, null),
  PREDOMINANT_COLOR("predominantColorAccess", "predominantColorIdentifierForJavaScript",
      null, null),
  PTZ_CONTROL("ptzControlAccess", "ptzControlIdentifierForJavaScript",
      null, null),
  QUATERNION("quaternionAccess", "quaternionIdentifierForJavaScript",
      null, null),
  RANGE("rangeAccess", "rangeIdentifierForJavaScript",
      null, null),
  REV_HUB_ORIENTATION_ON_ROBOT("revHubOrientationOnRobotAccess", "revHubOrientationOnRobotIdentifierForJavaScript",
      null, null),
  REV_9AXIS_IMU_ORIENTATION_ON_ROBOT("rev9AxisImuOrientationOnRobotAccess", "rev9AxisImuOrientationOnRobotIdentifierForJavaScript",
      null, null),
  RUMBLE_EFFECT("rumbleEffectAccess", "rumbleEffectIdentifierForJavaScript",
      null, null),
  SYSTEM("systemAccess", "systemIdentifierForJavaScript",
      null, null),
  TELEMETRY("telemetry", "telemetryIdentifierForJavaScript",
      null, null),
  TEMPERATURE("temperatureAccess", "temperatureIdentifierForJavaScript",
      null, null),
  VECTOR_F("vectorFAccess", "vectorFIdentifierForJavaScript",
      null, null),
  VELOCITY("velocityAccess", "velocityIdentifierForJavaScript",
      null, null),
  VISION_PORTAL("visionPortalAccess", "visionPortalIdentifierForJavaScript",
      null, null),
  WHITE_BALANCE_CONTROL("whiteBalanceControlAccess", "whiteBalanceControlIdentifierForJavaScript",
      null, null),
  YAW_PITCH_ROLL_ANGLES("yawPitchRollAnglesAccess", "yawPitchRollAnglesIdentifierForJavaScript",
      null, null),

  OBSOLETE_TENSOR_FLOW("tensorFlowAccess", "tensorFlowIdentifierForJavaScript", null, null),
  OBSOLETE_TFOD("tfodAccess", "tfodIdentifierForJavaScript",
      "tfod", "tfodIdentifierForFtcJava"),
  OBSOLETE_TFOD_CUSTOM_MODEL("tfodCustomModelAccess", "tfodCustomModelIdentifierForJavaScript",
      "tfodCustomModel", "tfodCustomModelIdentifierForFtcJava"),
  OBSOLETE_TFOD_CURRENT_GAME("tfodCurrentGameAccess", "tfodCurrentGameIdentifierForJavaScript",
      "tfodPOWERPLAY", "tfodCurrentGameIdentifierForFtcJava"),
  OBSOLETE_TFOD_ROVER_RUCKUS("tfodRoverRuckusAccess", "tfodRoverRuckusIdentifierForJavaScript",
      "tfodRoverRuckus", "tfodRoverRuckusIdentifierForFtcJava"),
  OBSOLETE_TFOD_SKY_STONE("tfodSkyStoneAccess", "tfodSkyStoneIdentifierForJavaScript",
      "tfodSkyStone", "tfodSkyStoneIdentifierForFtcJava"),
  OBSOLETE_VUFORIA_CURRENT_GAME("vuforiaCurrentGameAccess", "vuforiaCurrentGameIdentifierForJavaScript",
      "vuforiaPOWERPLAY", "vuforiaCurrentGameIdentifierForFtcJava"),
  OBSOLETE_VUFORIA_RELIC_RECOVERY("vuforiaAccess", "vuforiaIdentifierForJavaScript", // For backwards compatibility
      "vuforiaRelicRecovery", "vuforiaRelicRecoveryIdentifierForFtcJava"),
  OBSOLETE_VUFORIA_ROVER_RUCKUS("vuforiaRoverRuckusAccess", "vuforiaRoverRuckusIdentifierForJavaScript",
      "vuforiaRoverRuckus", "vuforiaRoverRuckusIdentifierForFtcJava"),
  OBSOLETE_VUFORIA_SKY_STONE("vuforiaSkyStoneAccess", "vuforiaSkyStoneIdentifierForJavaScript",
      "vuforiaSkyStone", "vuforiaSkyStoneIdentifierForFtcJava"),
  OBSOLETE_VUFORIA_LOCALIZER("vuforiaLocalizerAccess", "vuforiaLocalizerIdentifierForJavaScript",
      null, null),
  OBSOLETE_VUFORIA_LOCALIZER_PARAMETERS("vuforiaLocalizerParametersAccess", "vuforiaLocalizerParametersIdentifierForJavaScript",
      null, null),
  OBSOLETE_VUFORIA_TRACKABLE("vuforiaTrackableAccess", "vuforiaTrackableIdentifierForJavaScript",
      null, null),
  OBSOLETE_VUFORIA_TRACKABLE_DEFAULT_LISTENER("vuforiaTrackableDefaultListenerAccess", "vuforiaTrackableDefaultListenerIdentifierForJavaScript",
      null, null),
  OBSOLETE_VUFORIA_TRACKABLES("vuforiaTrackablesAccess", "vuforiaTrackablesIdentifierForJavaScript",
      null, null);


  /**
   * The identifier used in the generated javascript code.
   */
  public final String identifierForJavaScript;
  /**
   * The identifier used in the generated java code.
   */
  public final String identifierForFtcJava;
  /**
   * The name of the variable used in the JavaScript generators.
   */
  @Nullable
  public final String variableForJavaScript;
  /**
   * The name of the variable used in the FtcJava generators.
   */
  @Nullable
  public final String variableForFtcJava;

  Identifier(String identifierForJavaScript, String variableForJavaScript,
      String identifierForFtcJava, String variableForFtcJava) {
    this.identifierForJavaScript = identifierForJavaScript;
    this.variableForJavaScript = variableForJavaScript;
    this.identifierForFtcJava = identifierForFtcJava;
    this.variableForFtcJava = variableForFtcJava;
  }
}

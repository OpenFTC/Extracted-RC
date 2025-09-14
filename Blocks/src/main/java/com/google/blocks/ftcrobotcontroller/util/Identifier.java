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
  ACCELERATION("accelerationAccess", "accelerationIdentifierForJavaScript"),
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
  ANGULAR_VELOCITY("angularVelocityAccess", "angularVelocityIdentifierForJavaScript"),
  APRIL_TAG("aprilTagAccess", "aprilTagIdentifierForJavaScript"),
  BLINKIN_PATTERN("blinkinPatternAccess", "blinkinPatternIdentifierForJavaScript"),
  BLOCKS_OP_MODE("blocksOpMode", null),
  BNO055IMU_PARAMETERS("bno055imuParametersAccess", "bno055imuParametersIdentifierForJavaScript"),
  COLOR("colorAccess", "colorIdentifierForJavaScript"),
  COLOR_BLOB_LOCATOR("colorBlobLocatorAccess", "colorBlobLocatorIdentifierForJavaScript"),
  DBG_LOG("dbgLogAccess", "dbgLogIdentifierForJavaScript"),
  ELAPSED_TIME("elapsedTimeAccess", "elapsedTimeIdentifierForJavaScript"),
  EXPOSURE_CONTROL("exposureControlAccess", "exposureControlIdentifierForJavaScript"),
  FOCUS_CONTROL("focusControlAccess", "focusControlIdentifierForJavaScript"),
  GAIN_CONTROL("gainControlAccess", "gainControlIdentifierForJavaScript"),
  GAMEPAD_1("gamepad1", null,
      "gamepad1", null),
  GAMEPAD_2("gamepad2", null,
      "gamepad2", null),
  IMU_PARAMETERS("imuParametersAccess", "imuParametersIdentifierForJavaScript"),
  LED_EFFECT("ledEffectAccess", "ledEffectIdentifierForJavaScript"),
  LL_RESULT("llResultAccess", "llResultIdentifierForJavaScript"),
  LL_STATUS("llStatusAccess", "llStatusIdentifierForJavaScript"),
  LINEAR_OP_MODE("linearOpMode", "linearOpModeIdentifierForJavaScript"),
  MAGNETIC_FLUX("magneticFluxAccess", "magneticFluxIdentifierForJavaScript"),
  MATRIX_F("matrixFAccess", "matrixFIdentifierForJavaScript"),
  MISC("miscAccess", "miscIdentifierForJavaScript"),
  NAVIGATION("navigationAccess", "navigationIdentifierForJavaScript"),
  OPENCV("opencvAccess", "opencvIdentifierForJavaScript"),
  OPEN_GL_MATRIX("openGLMatrixAccess", "openGLMatrixIdentifierForJavaScript"),
  ORIENTATION("orientationAccess", "orientationIdentifierForJavaScript"),
  PIDF_COEFFICIENTS("pidfCoefficientsAccess", "pidfCoefficientsIdentifierForJavaScript"),
  POSE2D("pose2DAccess", "pose2DIdentifierForJavaScript"),
  POSITION("positionAccess", "positionIdentifierForJavaScript"),
  PREDOMINANT_COLOR("predominantColorAccess", "predominantColorIdentifierForJavaScript"),
  PTZ_CONTROL("ptzControlAccess", "ptzControlIdentifierForJavaScript"),
  QUATERNION("quaternionAccess", "quaternionIdentifierForJavaScript"),
  RANGE("rangeAccess", "rangeIdentifierForJavaScript"),
  REV_HUB_ORIENTATION_ON_ROBOT("revHubOrientationOnRobotAccess", "revHubOrientationOnRobotIdentifierForJavaScript"),
  REV_9AXIS_IMU_ORIENTATION_ON_ROBOT("rev9AxisImuOrientationOnRobotAccess", "rev9AxisImuOrientationOnRobotIdentifierForJavaScript"),
  RUMBLE_EFFECT("rumbleEffectAccess", "rumbleEffectIdentifierForJavaScript"),
  SYSTEM("systemAccess", "systemIdentifierForJavaScript"),
  TELEMETRY("telemetry", "telemetryIdentifierForJavaScript"),
  TEMPERATURE("temperatureAccess", "temperatureIdentifierForJavaScript"),
  VECTOR_F("vectorFAccess", "vectorFIdentifierForJavaScript"),
  VELOCITY("velocityAccess", "velocityIdentifierForJavaScript"),
  VISION_PORTAL("visionPortalAccess", "visionPortalIdentifierForJavaScript"),
  WHITE_BALANCE_CONTROL("whiteBalanceControlAccess", "whiteBalanceControlIdentifierForJavaScript"),
  YAW_PITCH_ROLL_ANGLES("yawPitchRollAnglesAccess", "yawPitchRollAnglesIdentifierForJavaScript"),

  OBSOLETE_TENSOR_FLOW("tensorFlowAccess", "tensorFlowIdentifierForJavaScript"),
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
  OBSOLETE_VUFORIA_LOCALIZER("vuforiaLocalizerAccess", "vuforiaLocalizerIdentifierForJavaScript"),
  OBSOLETE_VUFORIA_LOCALIZER_PARAMETERS("vuforiaLocalizerParametersAccess", "vuforiaLocalizerParametersIdentifierForJavaScript"),
  OBSOLETE_VUFORIA_TRACKABLE("vuforiaTrackableAccess", "vuforiaTrackableIdentifierForJavaScript"),
  OBSOLETE_VUFORIA_TRACKABLE_DEFAULT_LISTENER("vuforiaTrackableDefaultListenerAccess", "vuforiaTrackableDefaultListenerIdentifierForJavaScript"),
  OBSOLETE_VUFORIA_TRACKABLES("vuforiaTrackablesAccess", "vuforiaTrackablesIdentifierForJavaScript");


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

  Identifier(String identifierForJavaScript, String variableForJavaScript) {
    this(identifierForJavaScript, variableForJavaScript, null, null);
  }

  Identifier(String identifierForJavaScript, String variableForJavaScript,
      String identifierForFtcJava, String variableForFtcJava) {
    this.identifierForJavaScript = identifierForJavaScript;
    this.variableForJavaScript = variableForJavaScript;
    this.identifierForFtcJava = identifierForFtcJava;
    this.variableForFtcJava = variableForFtcJava;
  }
}

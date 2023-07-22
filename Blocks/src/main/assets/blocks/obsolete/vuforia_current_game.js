/**
 * @license
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

/**
 * @fileoverview FTC robot blocks related to Vuforia for the current game.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// createVuforiaCurrentGameTrackableNameDropdown
// vuforiaCurrentGameBlocksFirstName
// vuforiaCurrentGameIdentifierForJavaScript
// VUFORIA_CURRENT_GAME_TRACKABLE_NAME_TOOLTIPS
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor

Blockly.Blocks['vuforiaCurrentGame_initialize_withCameraDirection_2'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(vuforiaCurrentGameBlocksFirstName))
        .appendField('.')
        .appendField(createNonEditableField('initialize'));
    this.appendValueInput('CAMERA_DIRECTION').setCheck('VuforiaLocalizer.CameraDirection')
        .appendField('cameraDirection')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('USE_EXTENDED_TRACKING').setCheck('Boolean')
        .appendField('useExtendedTracking')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ENABLE_CAMERA_MONITORING').setCheck('Boolean')
        .appendField('enableCameraMonitoring')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('CAMERA_MONITOR_FEEDBACK').setCheck('VuforiaLocalizer.Parameters.CameraMonitorFeedback')
        .appendField('cameraMonitorFeedback')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DX').setCheck('Number')
        .appendField('phoneLocationOnRobot forward displacement')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DY').setCheck('Number')
        .appendField('phoneLocationOnRobot left displacement')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DZ').setCheck('Number')
        .appendField('phoneLocationOnRobot vertical displacement')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('AXES_ORDER').setCheck('AxesOrder')
        .appendField('phoneLocationOnRobot axesOrder')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('FIRST_ANGLE').setCheck('Number')
        .appendField('phoneLocationOnRobot firstAngle')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('SECOND_ANGLE').setCheck('Number')
        .appendField('phoneLocationOnRobot secondAngle')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('THIRD_ANGLE').setCheck('Number')
        .appendField('phoneLocationOnRobot thirdAngle')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('USE_COMPETITION_FIELD_TARGET_LOCATIONS').setCheck('Boolean')
        .appendField('useCompetitionFieldTargetLocations')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Initialize Vuforia for ' + vuforiaCurrentGameName + '.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'DX':
        case 'DY':
        case 'DZ':
        case 'FIRST_ANGLE':
        case 'SECOND_ANGLE':
        case 'THIRD_ANGLE':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['vuforiaCurrentGame_initialize_withCameraDirection_2'] = function(block) {
  var identifier = vuforiaCurrentGameIdentifierForJavaScript;
  var cameraDirection = Blockly.JavaScript.valueToCode(
      block, 'CAMERA_DIRECTION', Blockly.JavaScript.ORDER_COMMA);
  var useExtendedTracking = Blockly.JavaScript.valueToCode(
      block, 'USE_EXTENDED_TRACKING', Blockly.JavaScript.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.JavaScript.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.JavaScript.ORDER_COMMA);
  var cameraMonitorFeedback = Blockly.JavaScript.valueToCode(
      block, 'CAMERA_MONITOR_FEEDBACK', Blockly.JavaScript.ORDER_COMMA);
  var dx = Blockly.JavaScript.valueToCode(
      block, 'DX', Blockly.JavaScript.ORDER_COMMA);
  var dy = Blockly.JavaScript.valueToCode(
      block, 'DY', Blockly.JavaScript.ORDER_COMMA);
  var dz = Blockly.JavaScript.valueToCode(
      block, 'DZ', Blockly.JavaScript.ORDER_COMMA);
  var axesOrder = Blockly.JavaScript.valueToCode(
      block, 'AXES_ORDER', Blockly.JavaScript.ORDER_COMMA);
  var firstAngle = Blockly.JavaScript.valueToCode(
      block, 'FIRST_ANGLE', Blockly.JavaScript.ORDER_COMMA);
  var secondAngle = Blockly.JavaScript.valueToCode(
      block, 'SECOND_ANGLE', Blockly.JavaScript.ORDER_COMMA);
  var thirdAngle = Blockly.JavaScript.valueToCode(
      block, 'THIRD_ANGLE', Blockly.JavaScript.ORDER_COMMA);
  var useCompetitionFieldTargetLocations = Blockly.JavaScript.valueToCode(
      block, 'USE_COMPETITION_FIELD_TARGET_LOCATIONS', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.initialize_withCameraDirection_2(' +
      cameraDirection + ', ' + useExtendedTracking + ', ' +
      enableCameraMonitoring + ', ' + cameraMonitorFeedback + ', ' +
      dx + ', ' + dy + ', ' + dz + ', ' +
      axesOrder + ', ' + firstAngle + ', ' + secondAngle + ', ' + thirdAngle + ', ' +
      useCompetitionFieldTargetLocations + ');\n';
};

Blockly.FtcJava['vuforiaCurrentGame_initialize_withCameraDirection_2'] = function(block) {
  var className = 'VuforiaCurrentGame';
  var identifier = importDeclareAssignObsolete(block, null, className);
  var cameraDirection = Blockly.FtcJava.valueToCode(
      block, 'CAMERA_DIRECTION', Blockly.FtcJava.ORDER_COMMA);
  var useExtendedTracking = Blockly.FtcJava.valueToCode(
      block, 'USE_EXTENDED_TRACKING', Blockly.FtcJava.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.FtcJava.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.FtcJava.ORDER_COMMA);
  var cameraMonitorFeedback = Blockly.FtcJava.valueToCode(
      block, 'CAMERA_MONITOR_FEEDBACK', Blockly.FtcJava.ORDER_COMMA);
  var dx = Blockly.FtcJava.valueToCode(
      block, 'DX', Blockly.FtcJava.ORDER_COMMA);
  var dy = Blockly.FtcJava.valueToCode(
      block, 'DY', Blockly.FtcJava.ORDER_COMMA);
  var dz = Blockly.FtcJava.valueToCode(
      block, 'DZ', Blockly.FtcJava.ORDER_COMMA);
  var axesOrder = Blockly.FtcJava.valueToCode(
      block, 'AXES_ORDER', Blockly.FtcJava.ORDER_COMMA);
  var firstAngle = Blockly.FtcJava.valueToCode(
      block, 'FIRST_ANGLE', Blockly.FtcJava.ORDER_COMMA);
  var secondAngle = Blockly.FtcJava.valueToCode(
      block, 'SECOND_ANGLE', Blockly.FtcJava.ORDER_COMMA);
  var thirdAngle = Blockly.FtcJava.valueToCode(
      block, 'THIRD_ANGLE', Blockly.FtcJava.ORDER_COMMA);
  var useCompetitionFieldTargetLocations = Blockly.FtcJava.valueToCode(
      block, 'USE_COMPETITION_FIELD_TARGET_LOCATIONS', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.initialize(\n' +
      Blockly.FtcJava.INDENT_CONTINUE + '"", // vuforiaLicenseKey\n' +
      Blockly.FtcJava.INDENT_CONTINUE + cameraDirection + ', // cameraDirection\n' +
      Blockly.FtcJava.INDENT_CONTINUE + useExtendedTracking + ', // useExtendedTracking\n' +
      Blockly.FtcJava.INDENT_CONTINUE + enableCameraMonitoring + ', // enableCameraMonitoring\n' +
      Blockly.FtcJava.INDENT_CONTINUE + cameraMonitorFeedback + ', // cameraMonitorFeedback\n' +
      Blockly.FtcJava.INDENT_CONTINUE + dx + ', // dx\n' +
      Blockly.FtcJava.INDENT_CONTINUE + dy + ', // dy\n' +
      Blockly.FtcJava.INDENT_CONTINUE + dz + ', // dz\n' +
      Blockly.FtcJava.INDENT_CONTINUE + axesOrder + ', // axesOrder\n' +
      Blockly.FtcJava.INDENT_CONTINUE + firstAngle + ', // firstAngle\n' +
      Blockly.FtcJava.INDENT_CONTINUE + secondAngle + ', // secondAngle\n' +
      Blockly.FtcJava.INDENT_CONTINUE + thirdAngle + ', // thirdAngle\n' +
      Blockly.FtcJava.INDENT_CONTINUE + useCompetitionFieldTargetLocations + '); // useCompetitionFieldTargetLocations\n';
};

Blockly.Blocks['vuforiaCurrentGame_initialize_withWebcam_2'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(vuforiaCurrentGameBlocksFirstName))
        .appendField('.')
        .appendField(createNonEditableField('initialize'));
    this.appendValueInput('CAMERA_NAME').setCheck(['CameraName', 'WebcamName', 'SwitchableCamera'])
        .appendField('cameraName')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('WEBCAM_CALIBRATION_FILE').setCheck('String')
        .appendField('Webcam Calibration Filename')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('USE_EXTENDED_TRACKING').setCheck('Boolean')
        .appendField('useExtendedTracking')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ENABLE_CAMERA_MONITORING').setCheck('Boolean')
        .appendField('enableCameraMonitoring')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('CAMERA_MONITOR_FEEDBACK').setCheck('VuforiaLocalizer.Parameters.CameraMonitorFeedback')
        .appendField('cameraMonitorFeedback')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DX').setCheck('Number')
        .appendField('cameraLocationOnRobot forward displacement')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DY').setCheck('Number')
        .appendField('cameraLocationOnRobot left displacement')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DZ').setCheck('Number')
        .appendField('cameraLocationOnRobot vertical displacement')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('AXES_ORDER').setCheck('AxesOrder')
        .appendField('cameraLocationOnRobot axesOrder')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('FIRST_ANGLE').setCheck('Number')
        .appendField('cameraLocationOnRobot firstAngle')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('SECOND_ANGLE').setCheck('Number')
        .appendField('cameraLocationOnRobot secondAngle')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('THIRD_ANGLE').setCheck('Number')
        .appendField('cameraLocationOnRobot thirdAngle')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('USE_COMPETITION_FIELD_TARGET_LOCATIONS').setCheck('Boolean')
        .appendField('useCompetitionFieldTargetLocations')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Initialize Vuforia for ' + vuforiaCurrentGameName + '.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'DX':
        case 'DY':
        case 'DZ':
        case 'FIRST_ANGLE':
        case 'SECOND_ANGLE':
        case 'THIRD_ANGLE':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['vuforiaCurrentGame_initialize_withWebcam_2'] = function(block) {
  var identifier = vuforiaCurrentGameIdentifierForJavaScript;
  var cameraName = Blockly.JavaScript.valueToCode(
      block, 'CAMERA_NAME', Blockly.JavaScript.ORDER_COMMA);
  var webcamCalibrationFilename = Blockly.JavaScript.valueToCode(
      block, 'WEBCAM_CALIBRATION_FILE', Blockly.JavaScript.ORDER_COMMA);
  var useExtendedTracking = Blockly.JavaScript.valueToCode(
      block, 'USE_EXTENDED_TRACKING', Blockly.JavaScript.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.JavaScript.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.JavaScript.ORDER_COMMA);
  var cameraMonitorFeedback = Blockly.JavaScript.valueToCode(
      block, 'CAMERA_MONITOR_FEEDBACK', Blockly.JavaScript.ORDER_COMMA);
  var dx = Blockly.JavaScript.valueToCode(
      block, 'DX', Blockly.JavaScript.ORDER_COMMA);
  var dy = Blockly.JavaScript.valueToCode(
      block, 'DY', Blockly.JavaScript.ORDER_COMMA);
  var dz = Blockly.JavaScript.valueToCode(
      block, 'DZ', Blockly.JavaScript.ORDER_COMMA);
  var axesOrder = Blockly.JavaScript.valueToCode(
      block, 'AXES_ORDER', Blockly.JavaScript.ORDER_COMMA);
  var firstAngle = Blockly.JavaScript.valueToCode(
      block, 'FIRST_ANGLE', Blockly.JavaScript.ORDER_COMMA);
  var secondAngle = Blockly.JavaScript.valueToCode(
      block, 'SECOND_ANGLE', Blockly.JavaScript.ORDER_COMMA);
  var thirdAngle = Blockly.JavaScript.valueToCode(
      block, 'THIRD_ANGLE', Blockly.JavaScript.ORDER_COMMA);
  var useCompetitionFieldTargetLocations = Blockly.JavaScript.valueToCode(
      block, 'USE_COMPETITION_FIELD_TARGET_LOCATIONS', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.initialize_withWebcam_2(' +
      cameraName + ', ' + webcamCalibrationFilename + ', ' + useExtendedTracking + ', ' +
      enableCameraMonitoring + ', ' + cameraMonitorFeedback + ', ' +
      dx + ', ' + dy + ', ' + dz + ', ' +
      axesOrder + ', ' + firstAngle + ', ' + secondAngle + ', ' + thirdAngle + ', ' +
      useCompetitionFieldTargetLocations + ');\n';
};

Blockly.FtcJava['vuforiaCurrentGame_initialize_withWebcam_2'] = function(block) {
  var className = 'VuforiaCurrentGame';
  var identifier = importDeclareAssignObsolete(block, null, className);
  var cameraName = Blockly.FtcJava.valueToCode(
      block, 'CAMERA_NAME', Blockly.FtcJava.ORDER_COMMA);
  var webcamCalibrationFilename = Blockly.FtcJava.valueToCode(
      block, 'WEBCAM_CALIBRATION_FILE', Blockly.FtcJava.ORDER_COMMA);
  var useExtendedTracking = Blockly.FtcJava.valueToCode(
      block, 'USE_EXTENDED_TRACKING', Blockly.FtcJava.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.FtcJava.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.FtcJava.ORDER_COMMA);
  var cameraMonitorFeedback = Blockly.FtcJava.valueToCode(
      block, 'CAMERA_MONITOR_FEEDBACK', Blockly.FtcJava.ORDER_COMMA);
  var dx = Blockly.FtcJava.valueToCode(
      block, 'DX', Blockly.FtcJava.ORDER_COMMA);
  var dy = Blockly.FtcJava.valueToCode(
      block, 'DY', Blockly.FtcJava.ORDER_COMMA);
  var dz = Blockly.FtcJava.valueToCode(
      block, 'DZ', Blockly.FtcJava.ORDER_COMMA);
  var axesOrder = Blockly.FtcJava.valueToCode(
      block, 'AXES_ORDER', Blockly.FtcJava.ORDER_COMMA);
  var firstAngle = Blockly.FtcJava.valueToCode(
      block, 'FIRST_ANGLE', Blockly.FtcJava.ORDER_COMMA);
  var secondAngle = Blockly.FtcJava.valueToCode(
      block, 'SECOND_ANGLE', Blockly.FtcJava.ORDER_COMMA);
  var thirdAngle = Blockly.FtcJava.valueToCode(
      block, 'THIRD_ANGLE', Blockly.FtcJava.ORDER_COMMA);
  var useCompetitionFieldTargetLocations = Blockly.FtcJava.valueToCode(
      block, 'USE_COMPETITION_FIELD_TARGET_LOCATIONS', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.initialize(\n' +
      Blockly.FtcJava.INDENT_CONTINUE + '"", // vuforiaLicenseKey\n' +
      Blockly.FtcJava.INDENT_CONTINUE + cameraName + ', // cameraName\n' +
      Blockly.FtcJava.INDENT_CONTINUE + webcamCalibrationFilename + ', // webcamCalibrationFilename\n' +
      Blockly.FtcJava.INDENT_CONTINUE + useExtendedTracking + ', // useExtendedTracking\n' +
      Blockly.FtcJava.INDENT_CONTINUE + enableCameraMonitoring + ', // enableCameraMonitoring\n' +
      Blockly.FtcJava.INDENT_CONTINUE + cameraMonitorFeedback + ', // cameraMonitorFeedback\n' +
      Blockly.FtcJava.INDENT_CONTINUE + dx + ', // dx\n' +
      Blockly.FtcJava.INDENT_CONTINUE + dy + ', // dy\n' +
      Blockly.FtcJava.INDENT_CONTINUE + dz + ', // dz\n' +
      Blockly.FtcJava.INDENT_CONTINUE + axesOrder + ', // axesOrder\n' +
      Blockly.FtcJava.INDENT_CONTINUE + firstAngle + ', // firstAngle\n' +
      Blockly.FtcJava.INDENT_CONTINUE + secondAngle + ', // secondAngle\n' +
      Blockly.FtcJava.INDENT_CONTINUE + thirdAngle + ', // thirdAngle\n' +
      Blockly.FtcJava.INDENT_CONTINUE + useCompetitionFieldTargetLocations + '); // useCompetitionFieldTargetLocations\n';
};

// Deprecated - use vuforiaCurrentGame_initialize_withCameraDirection_2
Blockly.Blocks['vuforiaCurrentGame_initialize_withCameraDirection'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(vuforiaCurrentGameBlocksFirstName))
        .appendField('.')
        .appendField(createNonEditableField('initialize'));
    this.appendValueInput('CAMERA_DIRECTION').setCheck('VuforiaLocalizer.CameraDirection')
        .appendField('cameraDirection')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('USE_EXTENDED_TRACKING').setCheck('Boolean')
        .appendField('useExtendedTracking')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ENABLE_CAMERA_MONITORING').setCheck('Boolean')
        .appendField('enableCameraMonitoring')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('CAMERA_MONITOR_FEEDBACK').setCheck('VuforiaLocalizer.Parameters.CameraMonitorFeedback')
        .appendField('cameraMonitorFeedback')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PHONE_LOCATION_ON_ROBOT_DX').setCheck('Number')
        .appendField('phoneLocationOnRobot translation dx')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PHONE_LOCATION_ON_ROBOT_DY').setCheck('Number')
        .appendField('phoneLocationOnRobot translation dy')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PHONE_LOCATION_ON_ROBOT_DZ').setCheck('Number')
        .appendField('phoneLocationOnRobot translation dz')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PHONE_LOCATION_ON_ROBOT_X_ANGLE').setCheck('Number')
        .appendField('phoneLocationOnRobot rotation x')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PHONE_LOCATION_ON_ROBOT_Y_ANGLE').setCheck('Number')
        .appendField('phoneLocationOnRobot rotation y')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PHONE_LOCATION_ON_ROBOT_Z_ANGLE').setCheck('Number')
        .appendField('phoneLocationOnRobot rotation z')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('USE_COMPETITION_FIELD_TARGET_LOCATIONS').setCheck('Boolean')
        .appendField('useCompetitionFieldTargetLocations')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Initialize Vuforia for ' + vuforiaCurrentGameName + '.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'PHONE_LOCATION_ON_ROBOT_DX':
        case 'PHONE_LOCATION_ON_ROBOT_DY':
        case 'PHONE_LOCATION_ON_ROBOT_DZ':
        case 'PHONE_LOCATION_ON_ROBOT_X_ANGLE':
        case 'PHONE_LOCATION_ON_ROBOT_Y_ANGLE':
        case 'PHONE_LOCATION_ON_ROBOT_Z_ANGLE':
          return 'float';
      }
      return '';
    };
  }
};

// Deprecated - use vuforiaCurrentGame_initialize_withCameraDirection_2
Blockly.JavaScript['vuforiaCurrentGame_initialize_withCameraDirection'] = function(block) {
  return vuforia_initialize_withCameraDirection_JavaScript(block, '""', vuforiaCurrentGameIdentifierForJavaScript);
};

// Deprecated - use vuforiaCurrentGame_initialize_withCameraDirection_2
Blockly.FtcJava['vuforiaCurrentGame_initialize_withCameraDirection'] = function(block) {
  return vuforia_initialize_withCameraDirection_FtcJava(block, '""', 'VuforiaCurrentGame');
};

// Deprecated - use vuforiaCurrentGame_initialize_withWebcam_2
Blockly.Blocks['vuforiaCurrentGame_initialize_withWebcam'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(vuforiaCurrentGameBlocksFirstName))
        .appendField('.')
        .appendField(createNonEditableField('initialize'));
    this.appendValueInput('CAMERA_NAME').setCheck(['CameraName', 'WebcamName', 'SwitchableCamera'])
        .appendField('cameraName')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('WEBCAM_CALIBRATION_FILE').setCheck('String')
        .appendField('Webcam Calibration Filename')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('USE_EXTENDED_TRACKING').setCheck('Boolean')
        .appendField('useExtendedTracking')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ENABLE_CAMERA_MONITORING').setCheck('Boolean')
        .appendField('enableCameraMonitoring')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('CAMERA_MONITOR_FEEDBACK').setCheck('VuforiaLocalizer.Parameters.CameraMonitorFeedback')
        .appendField('cameraMonitorFeedback')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('CAMERA_LOCATION_ON_ROBOT_DX').setCheck('Number')
        .appendField('cameraLocationOnRobot translation dx')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('CAMERA_LOCATION_ON_ROBOT_DY').setCheck('Number')
        .appendField('cameraLocationOnRobot translation dy')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('CAMERA_LOCATION_ON_ROBOT_DZ').setCheck('Number')
        .appendField('cameraLocationOnRobot translation dz')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('CAMERA_LOCATION_ON_ROBOT_X_ANGLE').setCheck('Number')
        .appendField('cameraLocationOnRobot rotation x')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('CAMERA_LOCATION_ON_ROBOT_Y_ANGLE').setCheck('Number')
        .appendField('cameraLocationOnRobot rotation y')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('CAMERA_LOCATION_ON_ROBOT_Z_ANGLE').setCheck('Number')
        .appendField('cameraLocationOnRobot rotation z')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('USE_COMPETITION_FIELD_TARGET_LOCATIONS').setCheck('Boolean')
        .appendField('useCompetitionFieldTargetLocations')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Initialize Vuforia for ' + vuforiaCurrentGameName + '.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'CAMERA_LOCATION_ON_ROBOT_DX':
        case 'CAMERA_LOCATION_ON_ROBOT_DY':
        case 'CAMERA_LOCATION_ON_ROBOT_DZ':
        case 'CAMERA_LOCATION_ON_ROBOT_X_ANGLE':
        case 'CAMERA_LOCATION_ON_ROBOT_Y_ANGLE':
        case 'CAMERA_LOCATION_ON_ROBOT_Z_ANGLE':
          return 'float';
      }
      return '';
    };
  }
};

// Deprecated - use vuforiaCurrentGame_initialize_withWebcam_2
Blockly.JavaScript['vuforiaCurrentGame_initialize_withWebcam'] = function(block) {
  return vuforia_initialize_withWebcam_JavaScript(block, '""', vuforiaCurrentGameIdentifierForJavaScript);
};

// Deprecated - use vuforiaCurrentGame_initialize_withWebcam_2
Blockly.FtcJava['vuforiaCurrentGame_initialize_withWebcam'] = function(block) {
  return vuforia_initialize_withWebcam_FtcJava(block, 'VuforiaCurrentGame');
};

Blockly.Blocks['vuforiaCurrentGame_activate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(vuforiaCurrentGameBlocksFirstName))
        .appendField('.')
        .appendField(createNonEditableField('activate'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Activates all trackables, so that it is actively seeking their presence.');
  }
};

Blockly.JavaScript['vuforiaCurrentGame_activate'] = function(block) {
  return vuforia_activate_JavaScript(block, vuforiaCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['vuforiaCurrentGame_activate'] = function(block) {
  return vuforia_activate_FtcJava(block, 'VuforiaCurrentGame');
};

Blockly.Blocks['vuforiaCurrentGame_deactivate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(vuforiaCurrentGameBlocksFirstName))
        .appendField('.')
        .appendField(createNonEditableField('deactivate'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Deactivates all trackables, causing it to no longer see their presence.');
  }
};

Blockly.JavaScript['vuforiaCurrentGame_deactivate'] = function(block) {
  return vuforia_deactivate_JavaScript(block, vuforiaCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['vuforiaCurrentGame_deactivate'] = function(block) {
  return vuforia_deactivate_FtcJava(block, 'VuforiaCurrentGame');
};

Blockly.Blocks['vuforiaCurrentGame_setActiveCamera'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(vuforiaCurrentGameBlocksFirstName))
        .appendField('.')
        .appendField(createNonEditableField('setActiveCamera'));
    this.appendValueInput('CAMERA_NAME').setCheck(['CameraName', 'WebcamName'])
        .appendField('camera')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Switches the active camera. Does nothing if the camera specified in ' +
        vuforiaCurrentGameBlocksFirstName + '.initialize is not a switchable camera.');
  }
};

Blockly.JavaScript['vuforiaCurrentGame_setActiveCamera'] = function(block) {
  return vuforia_setActiveCamera_JavaScript(block, vuforiaCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['vuforiaCurrentGame_setActiveCamera'] = function(block) {
  return vuforia_setActiveCamera_FtcJava(block, 'VuforiaCurrentGame');
};

Blockly.Blocks['vuforiaCurrentGame_track'] = {
  init: function() {
    this.setOutput(true, 'VuforiaBase.TrackingResults');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(vuforiaCurrentGameBlocksFirstName))
        .appendField('.')
        .appendField(createNonEditableField('track'));
    this.appendValueInput('TRACKABLE_NAME').setCheck('String')
        .appendField('trackableName')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the VuforiaTrackingResults of the trackable with the given name.');
  }
};

Blockly.JavaScript['vuforiaCurrentGame_track'] = function(block) {
  return vuforia_track_JavaScript(block, vuforiaCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['vuforiaCurrentGame_track'] = function(block) {
  return vuforia_track_FtcJava(block, 'VuforiaCurrentGame');
};

Blockly.Blocks['vuforiaCurrentGame_trackPose'] = {
  init: function() {
    this.setOutput(true, 'VuforiaBase.TrackingResults');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(vuforiaCurrentGameBlocksFirstName))
        .appendField('.')
        .appendField(createNonEditableField('trackPose'));
    this.appendValueInput('TRACKABLE_NAME').setCheck('String')
        .appendField('trackableName')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the VuforiaTrackingResults of the pose of the trackable with the given name. ' +
        'The pose is the location of the trackable in the phone\'s coordinate system.');
  }
};

Blockly.JavaScript['vuforiaCurrentGame_trackPose'] = function(block) {
  return vuforia_trackPose_JavaScript(block, vuforiaCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['vuforiaCurrentGame_trackPose'] = function(block) {
  return vuforia_trackPose_FtcJava(block, 'VuforiaCurrentGame');
};

Blockly.Blocks['vuforiaCurrentGame_getVuforiaLocalizer'] = {
  init: function() {
    this.setOutput(true, 'VuforiaLocalizer');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(vuforiaCurrentGameBlocksFirstName))
        .appendField('.')
        .appendField(createNonEditableField('getVuforiaLocalizer'));
    this.setColour(functionColor);
    this.setTooltip('Returns the VuforiaLocalizer.');
  }
};

Blockly.JavaScript['vuforiaCurrentGame_getVuforiaLocalizer'] = function(block) {
  var code = vuforiaCurrentGameIdentifierForJavaScript + '.getVuforiaLocalizer()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['vuforiaCurrentGame_getVuforiaLocalizer'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'VuforiaCurrentGame');
  var code = identifier + '.getVuforiaLocalizer()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['vuforiaCurrentGame_typedEnum_trackableName'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField(createNonEditableField('TrackableName'))
        .appendField('.')
        .appendField(createVuforiaCurrentGameTrackableNameDropdown(), 'TRACKABLE_NAME');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = VUFORIA_CURRENT_GAME_TRACKABLE_NAME_TOOLTIPS;
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('TRACKABLE_NAME');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['vuforiaCurrentGame_typedEnum_trackableName'] = function(block) {
  return vuforia_typedEnum_trackableName_JavaScript(block);
};

Blockly.FtcJava['vuforiaCurrentGame_typedEnum_trackableName'] = function(block) {
  return vuforia_typedEnum_trackableName_FtcJava(block);
};

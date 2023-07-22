/**
 * @license
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @fileoverview FTC robot blocks related to IMU.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// createImuDropdown
// The following are defined in vars.js:
// createNonEditableField
// createPropertyDropdown
// getPropertyColor
// functionColor

// Functions

Blockly.Blocks['imu_initialize'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createImuDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('initialize'));
    this.appendValueInput('PARAMETERS').setCheck('IMU.Parameters')
        .appendField('parameters')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Initializes the IMU with the given parameters.');
  }
};

Blockly.JavaScript['imu_initialize'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var parameters = Blockly.JavaScript.valueToCode(
      block, 'PARAMETERS', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.initialize(' + parameters + ');\n';
};

Blockly.FtcJava['imu_initialize'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'IMU');
  var parameters = Blockly.FtcJava.valueToCode(
      block, 'PARAMETERS', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.initialize(' + parameters + ');\n';
};


imu_getProperty_JavaScript = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

imu_getProperty_FtcJava = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'IMU');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};


Blockly.Blocks['imu_getRobotAngularVelocity'] = {
  init: function() {
    this.setOutput(true, 'AngularVelocity');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createImuDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getRobotAngularVelocity'));
    this.appendValueInput('ANGLE_UNIT').setCheck('AngleUnit')
        .appendField('angleUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns an AngularVelocity object representing the angular velocity of the ' +
          'robot (how fast it\'s turning around the three axes).');
  }
};

Blockly.JavaScript['imu_getRobotAngularVelocity'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var angleUnit = Blockly.JavaScript.valueToCode(
      block, 'ANGLE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.getRobotAngularVelocity(' + angleUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['imu_getRobotAngularVelocity'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'IMU');
  var angleUnit = Blockly.FtcJava.valueToCode(
      block, 'ANGLE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.getRobotAngularVelocity(' + angleUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};


Blockly.Blocks['imu_getRobotOrientation'] = {
  init: function() {
    this.setOutput(true, 'Orientation');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createImuDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getRobotOrientation'));
    this.appendValueInput('AXES_REFERENCE').setCheck('AxesReference')
        .appendField('axesReference')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('AXES_ORDER').setCheck('AxesOrder')
        .appendField('axesOrder')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ANGLE_UNIT').setCheck('AngleUnit')
        .appendField('angleUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns an Orientation object representing the current orientation of the ' +
        'robot relative to the robot\'s position the last time that resetYaw was called, as if ' +
        'the robot was perfectly level at that time. The Orientation provides many ways to ' +
        'represent the robot\'s orientation, which is helpful for advanced use cases. Most teams ' +
        'should use getRobotYawPitchRollAngles');
  }
};

Blockly.JavaScript['imu_getRobotOrientation'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var axesReference = Blockly.JavaScript.valueToCode(
      block, 'AXES_REFERENCE', Blockly.JavaScript.ORDER_COMMA);
  var axesOrder = Blockly.JavaScript.valueToCode(
      block, 'AXES_ORDER', Blockly.JavaScript.ORDER_COMMA);
  var angleUnit = Blockly.JavaScript.valueToCode(
      block, 'ANGLE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.getRobotOrientation(' + axesReference + ', ' + axesOrder + ', ' + angleUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['imu_getRobotOrientation'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'IMU');
  var axesReference = Blockly.FtcJava.valueToCode(
      block, 'AXES_REFERENCE', Blockly.FtcJava.ORDER_COMMA);
  var axesOrder = Blockly.FtcJava.valueToCode(
      block, 'AXES_ORDER', Blockly.FtcJava.ORDER_COMMA);
  var angleUnit = Blockly.FtcJava.valueToCode(
      block, 'ANGLE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.getRobotOrientation(' + axesReference + ', ' + axesOrder + ', ' + angleUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};


Blockly.Blocks['imu_getProperty_Quaternion'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['RobotOrientationAsQuaternion', 'RobotOrientationAsQuaternion'],
    ];
    this.setOutput(true, 'Quaternion');
    this.appendDummyInput()
        .appendField(createImuDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['RobotOrientationAsQuaternion', 'Returns a Quaternion object representing the current ' +
            'orientation of the robot relative to the robot\'s position the last time that ' +
            'resetYaw was called, as if the robot was perfectly level at that time. Quaternions ' +
            'provide an advanced way to access orientation data that will work well for any ' +
            'orientation of the robot, even where other types of orientation data would encounter ' +
            'issues such as gimbal lock.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('PROP');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['imu_getProperty_Quaternion'] = imu_getProperty_JavaScript;

Blockly.FtcJava['imu_getProperty_Quaternion'] = imu_getProperty_FtcJava;


Blockly.Blocks['imu_getProperty_YawPitchRollAngles'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['RobotYawPitchRollAngles', 'RobotYawPitchRollAngles'],
    ];
    this.setOutput(true, 'YawPitchRollAngles');
    this.appendDummyInput()
        .appendField(createImuDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['RobotYawPitchRollAngles', 'Returns a YawPitchRollAngles object representing the ' +
            'current orientation of the robot relative to the robot\'s position the last time ' +
            'that resetYaw was called, as if the robot was perfectly level at that time.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('PROP');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['imu_getProperty_YawPitchRollAngles'] = imu_getProperty_JavaScript;

Blockly.FtcJava['imu_getProperty_YawPitchRollAngles'] = imu_getProperty_FtcJava;


Blockly.Blocks['imu_resetYaw'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createImuDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('resetYaw'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Resets the robot\'s yaw angle to 0. After calling this method, the reported ' +
        'orientation will be relative to the robot\'s position when this method was called, as ' +
        'if the robot was perfectly level right then. That is to say, the pitch and yaw will be ' +
        'ignored when this method is called.');
  }
};

Blockly.JavaScript['imu_resetYaw'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.resetYaw();\n';
};

Blockly.FtcJava['imu_resetYaw'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'IMU');
  return identifier + '.resetYaw();\n';
};

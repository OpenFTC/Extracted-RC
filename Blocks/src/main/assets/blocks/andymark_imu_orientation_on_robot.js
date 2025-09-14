/**
 * @license
 * Copyright 2025 Google LLC
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
 * @fileoverview FTC robot blocks related to AndyMarkIMUOrientationOnRobot.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// andyMarkImuOrientationOnRobotIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor

// AndyMarkIMUOrientationOnRobot Constructors

Blockly.Blocks['andyMarkImuOrientationOnRobot_create1'] = {
  init: function() {
    this.setOutput(true, 'ImuOrientationOnRobot');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('AndyMarkIMUOrientationOnRobot'));
    this.appendValueInput('LOGO_FACING_DIRECTION').setCheck('AndyMarkIMUOrientationOnRobot.LogoFacingDirection')
        .appendField('logoFacingDirection')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('I2C_PORT_FACING_DIRECTION').setCheck('AndyMarkIMUOrientationOnRobot.I2cPortFacingDirection')
        .appendField('i2cPortFacingDirection')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Creates a new AndyMarkIMUOrientationOnRobot object for an AndyMark IMU that is mounted ' +
        'orthogonally to a robot. ' +
        'This is the easiest constructor to use. ' +
        'Simply specify which direction on the robot that the AndyMark logo on the IMU is ' +
        'facing, and the direction that the I2C port on the IMU is facing. ' +
        'This block should be plugged into the imuOrientationOnRobot socket on the ' +
        '"new IMU.Parameters" block.');
  }
};

Blockly.JavaScript['andyMarkImuOrientationOnRobot_create1'] = function(block) {
  var logoFacingDirection = Blockly.JavaScript.valueToCode(
      block, 'LOGO_FACING_DIRECTION', Blockly.JavaScript.ORDER_COMMA);
  var i2cPortFacingDirection = Blockly.JavaScript.valueToCode(
      block, 'I2C_PORT_FACING_DIRECTION', Blockly.JavaScript.ORDER_COMMA);
  var code = andyMarkImuOrientationOnRobotIdentifierForJavaScript + '.create1(' + logoFacingDirection + ', ' + i2cPortFacingDirection + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['andyMarkImuOrientationOnRobot_create1'] = function(block) {
  var logoFacingDirection = Blockly.FtcJava.valueToCode(
      block, 'LOGO_FACING_DIRECTION', Blockly.FtcJava.ORDER_NONE);
  var i2cPortFacingDirection = Blockly.FtcJava.valueToCode(
      block, 'I2C_PORT_FACING_DIRECTION', Blockly.FtcJava.ORDER_NONE);
  var code = 'new AndyMarkIMUOrientationOnRobot(' + logoFacingDirection + ', ' + i2cPortFacingDirection + ')';
  Blockly.FtcJava.generateImport_('AndyMarkIMUOrientationOnRobot');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

Blockly.Blocks['andyMarkImuOrientationOnRobot_create2'] = {
  init: function() {
    this.setOutput(true, 'ImuOrientationOnRobot');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('AndyMarkIMUOrientationOnRobot'));
    this.appendValueInput('ROTATION').setCheck('Orientation')
        .appendField('rotation')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Creates a new AndyMarkIMUOrientationOnRobot object for an AndyMark IMU that is mounted ' +
        'at any arbitrary angle on a robot using an Orientation object. ' +
        'The Orientation block specifies the rotation (defined within the Robot Coordinate ' +
        'System) that would need to be applied in order to rotate an AndyMark IMU from having ' +
        'its logo facing up and the I2C port facing forward, to its actual orientation on the ' +
        'robot. ' +
        'This block should be plugged into the imuOrientationOnRobot socket on the ' +
        '"new IMU.Parameters" block.');
  }
};

Blockly.JavaScript['andyMarkImuOrientationOnRobot_create2'] = function(block) {
  var rotation = Blockly.JavaScript.valueToCode(
      block, 'ROTATION', Blockly.JavaScript.ORDER_NONE);
  var code = andyMarkImuOrientationOnRobotIdentifierForJavaScript + '.create2(' + rotation + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['andyMarkImuOrientationOnRobot_create2'] = function(block) {
  var rotation = Blockly.FtcJava.valueToCode(
      block, 'ROTATION', Blockly.FtcJava.ORDER_NONE);
  var code = 'new AndyMarkIMUOrientationOnRobot(' + rotation + ')';
  Blockly.FtcJava.generateImport_('AndyMarkIMUOrientationOnRobot');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

Blockly.Blocks['andyMarkImuOrientationOnRobot_create3'] = {
  init: function() {
    this.setOutput(true, 'ImuOrientationOnRobot');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('AndyMarkIMUOrientationOnRobot'));
    this.appendValueInput('ROTATION').setCheck('Quaternion')
        .appendField('rotation')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Creates a new AndyMarkIMUOrientationOnRobot object for an AndyMark IMU that is mounted ' +
        'at any arbitrary angle on a robot using a Quaternion object. ' +
        'The Quaternion block specifies the rotation (defined within the Robot Coordinate ' +
        'System) that would need to be applied in order to rotate an AndyMark IMU from having ' +
        'its logo facing up and the I2C port facing forward, to its actual orientation on the ' +
        'robot. ' +
        'This block should be plugged into the imuOrientationOnRobot socket on the ' +
        '"new IMU.Parameters" block.');
  }
};

Blockly.JavaScript['andyMarkImuOrientationOnRobot_create3'] = function(block) {
  var rotation = Blockly.JavaScript.valueToCode(
      block, 'ROTATION', Blockly.JavaScript.ORDER_NONE);
  var code = andyMarkImuOrientationOnRobotIdentifierForJavaScript + '.create3(' + rotation + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['andyMarkImuOrientationOnRobot_create3'] = function(block) {
  var rotation = Blockly.FtcJava.valueToCode(
      block, 'ROTATION', Blockly.FtcJava.ORDER_NONE);
  var code = 'new AndyMarkIMUOrientationOnRobot(' + rotation + ')';
  Blockly.FtcJava.generateImport_('AndyMarkIMUOrientationOnRobot');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

// AndyMarkIMUOrientationOnRobot Functions

Blockly.Blocks['andyMarkImuOrientationOnRobot_zyxOrientation'] = {
  init: function() {
    this.setOutput(true, 'Orientation');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('AndyMarkIMUOrientationOnRobot'))
        .appendField('.')
        .appendField(createNonEditableField('zyxOrientation'));
    this.appendValueInput('Z').setCheck('Number')
        .appendField('z')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('Y').setCheck('Number')
        .appendField('y')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('X').setCheck('Number')
        .appendField('x')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new Orientation object with angles z, y, and x, specified in degrees.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'Z':
        case 'Y':
        case 'X':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['andyMarkImuOrientationOnRobot_zyxOrientation'] = function(block) {
  var z = Blockly.JavaScript.valueToCode(
      block, 'Z', Blockly.JavaScript.ORDER_COMMA);
  var y = Blockly.JavaScript.valueToCode(
      block, 'Y', Blockly.JavaScript.ORDER_COMMA);
  var x = Blockly.JavaScript.valueToCode(
      block, 'X', Blockly.JavaScript.ORDER_COMMA);
  var code = andyMarkImuOrientationOnRobotIdentifierForJavaScript + '.zyxOrientation(' + z + ', ' + y + ', ' + x + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['andyMarkImuOrientationOnRobot_zyxOrientation'] = function(block) {
  var z = Blockly.FtcJava.valueToCode(
      block, 'Z', Blockly.FtcJava.ORDER_COMMA);
  var y = Blockly.FtcJava.valueToCode(
      block, 'Y', Blockly.FtcJava.ORDER_COMMA);
  var x = Blockly.FtcJava.valueToCode(
      block, 'X', Blockly.FtcJava.ORDER_COMMA);
  var code = 'AndyMarkIMUOrientationOnRobot.zyxOrientation(' + z + ', ' + y + ', ' + x + ')';
  Blockly.FtcJava.generateImport_('AndyMarkIMUOrientationOnRobot');
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['andyMarkImuOrientationOnRobot_xyzOrientation'] = {
  init: function() {
    this.setOutput(true, 'Orientation');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('AndyMarkIMUOrientationOnRobot'))
        .appendField('.')
        .appendField(createNonEditableField('xyzOrientation'));
    this.appendValueInput('X').setCheck('Number')
        .appendField('x')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('Y').setCheck('Number')
        .appendField('y')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('Z').setCheck('Number')
        .appendField('z')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new Orientation object with angles x, y, and z, specified in degrees.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'X':
        case 'Y':
        case 'Z':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['andyMarkImuOrientationOnRobot_xyzOrientation'] = function(block) {
  var x = Blockly.JavaScript.valueToCode(
      block, 'X', Blockly.JavaScript.ORDER_COMMA);
  var y = Blockly.JavaScript.valueToCode(
      block, 'Y', Blockly.JavaScript.ORDER_COMMA);
  var z = Blockly.JavaScript.valueToCode(
      block, 'Z', Blockly.JavaScript.ORDER_COMMA);
  var code = andyMarkImuOrientationOnRobotIdentifierForJavaScript + '.xyzOrientation(' + x + ', ' + y + ', ' + z + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['andyMarkImuOrientationOnRobot_xyzOrientation'] = function(block) {
  var x = Blockly.FtcJava.valueToCode(
      block, 'X', Blockly.FtcJava.ORDER_COMMA);
  var y = Blockly.FtcJava.valueToCode(
      block, 'Y', Blockly.FtcJava.ORDER_COMMA);
  var z = Blockly.FtcJava.valueToCode(
      block, 'Z', Blockly.FtcJava.ORDER_COMMA);
  var code = 'AndyMarkIMUOrientationOnRobot.xyzOrientation(' + x + ', ' + y + ', ' + z + ')';
  Blockly.FtcJava.generateImport_('AndyMarkIMUOrientationOnRobot');
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// AndyMarkIMUOrientationOnRobot Enums

Blockly.Blocks['andyMarkImuOrientationOnRobot_typedEnum_logoFacingDirection'] = {
  init: function() {
    var LOGO_FACING_DIRECTION_CHOICES = [
        ['UP', 'UP'],
        ['DOWN', 'DOWN'],
        ['FORWARD', 'FORWARD'],
        ['BACKWARD', 'BACKWARD'],
        ['LEFT', 'LEFT'],
        ['RIGHT', 'RIGHT'],
        ];
    this.setOutput(true, 'AndyMarkIMUOrientationOnRobot.LogoFacingDirection');
    this.appendDummyInput()
        .appendField(createNonEditableField('LogoFacingDirection'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(LOGO_FACING_DIRECTION_CHOICES), 'LOGO_FACING_DIRECTION');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['UP', 'The LogoFacingDirection value UP.'],
        ['DOWN', 'The LogoFacingDirection value DOWN.'],
        ['FORWARD', 'The LogoFacingDirection value FORWARD.'],
        ['BACKWARD', 'The LogoFacingDirection value BACKWARD.'],
        ['LEFT', 'The LogoFacingDirection value LEFT.'],
        ['RIGHT', 'The LogoFacingDirection value RIGHT.'],
        ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('LOGO_FACING_DIRECTION');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['andyMarkImuOrientationOnRobot_typedEnum_logoFacingDirection'] = function(block) {
  var code = '"' + block.getFieldValue('LOGO_FACING_DIRECTION') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['andyMarkImuOrientationOnRobot_typedEnum_logoFacingDirection'] = function(block) {
  var code = 'AndyMarkIMUOrientationOnRobot.LogoFacingDirection.' + block.getFieldValue('LOGO_FACING_DIRECTION');
  Blockly.FtcJava.generateImport_('AndyMarkIMUOrientationOnRobot');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['andyMarkImuOrientationOnRobot_typedEnum_i2cPortFacingDirection'] = {
  init: function() {
    var I2C_PORT_FACING_DIRECTION_CHOICES = [
        ['UP', 'UP'],
        ['DOWN', 'DOWN'],
        ['FORWARD', 'FORWARD'],
        ['BACKWARD', 'BACKWARD'],
        ['LEFT', 'LEFT'],
        ['RIGHT', 'RIGHT'],
        ];
    this.setOutput(true, 'AndyMarkIMUOrientationOnRobot.I2cPortFacingDirection');
    this.appendDummyInput()
        .appendField(createNonEditableField('I2cPortFacingDirection'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(I2C_PORT_FACING_DIRECTION_CHOICES), 'I2C_PORT_FACING_DIRECTION');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['UP', 'The I2cPortFacingDirection value UP.'],
        ['DOWN', 'The I2cPortFacingDirection value DOWN.'],
        ['FORWARD', 'The I2cPortFacingDirection value FORWARD.'],
        ['BACKWARD', 'The I2cPortFacingDirection value BACKWARD.'],
        ['LEFT', 'The I2cPortFacingDirection value LEFT.'],
        ['RIGHT', 'The I2cPortFacingDirection value RIGHT.'],
        ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('I2C_PORT_FACING_DIRECTION');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['andyMarkImuOrientationOnRobot_typedEnum_i2cPortFacingDirection'] = function(block) {
  var code = '"' + block.getFieldValue('I2C_PORT_FACING_DIRECTION') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['andyMarkImuOrientationOnRobot_typedEnum_i2cPortFacingDirection'] = function(block) {
  var code = 'AndyMarkIMUOrientationOnRobot.I2cPortFacingDirection.' + block.getFieldValue('I2C_PORT_FACING_DIRECTION');
  Blockly.FtcJava.generateImport_('AndyMarkIMUOrientationOnRobot');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};


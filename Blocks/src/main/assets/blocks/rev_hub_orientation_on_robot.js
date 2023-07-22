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
 * @fileoverview FTC robot blocks related to RevHubOrientationOnRobot.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// revHubOrientationOnRobotForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor

// Functions

Blockly.Blocks['revHubOrientationOnRobot_create1'] = {
  init: function() {
    this.setOutput(true, 'ImuOrientationOnRobot');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('RevHubOrientationOnRobot'));
    this.appendValueInput('LOGO_FACING_DIRECTION').setCheck('RevHubOrientationOnRobot.LogoFacingDirection')
        .appendField('logoFacingDirection')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('USB_FACING_DIRECTION').setCheck('RevHubOrientationOnRobot.UsbFacingDirection')
        .appendField('usbFacingDirection')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new RevHubOrientationOnRobot object for a REV Hub that is mounted ' +
        'orthogonally to a robot. This is the easiest constructor to use. Simply specify which ' +
        'direction on the robot that the REV Robotics logo on the Hub is facing, and the ' +
        'direction that the USB port(s) on the Hub are facing. This block should be plugged ' +
        'into the imuOrientationOnRobot socket on the "new IMU.Parameters" block.');
  }
};

Blockly.JavaScript['revHubOrientationOnRobot_create1'] = function(block) {
  var logoFacingDirection = Blockly.JavaScript.valueToCode(
      block, 'LOGO_FACING_DIRECTION', Blockly.JavaScript.ORDER_COMMA);
  var usbFacingDirection = Blockly.JavaScript.valueToCode(
      block, 'USB_FACING_DIRECTION', Blockly.JavaScript.ORDER_COMMA);
  var code = revHubOrientationOnRobotIdentifierForJavaScript + '.create1(' + logoFacingDirection + ', ' + usbFacingDirection + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['revHubOrientationOnRobot_create1'] = function(block) {
  var logoFacingDirection = Blockly.FtcJava.valueToCode(
      block, 'LOGO_FACING_DIRECTION', Blockly.FtcJava.ORDER_NONE);
  var usbFacingDirection = Blockly.FtcJava.valueToCode(
      block, 'USB_FACING_DIRECTION', Blockly.FtcJava.ORDER_NONE);
  var code = 'new RevHubOrientationOnRobot(' + logoFacingDirection + ', ' + usbFacingDirection + ')';
  Blockly.FtcJava.generateImport_('RevHubOrientationOnRobot');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

Blockly.Blocks['revHubOrientationOnRobot_create2'] = {
  init: function() {
    this.setOutput(true, 'ImuOrientationOnRobot');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('RevHubOrientationOnRobot'));
    this.appendValueInput('ROTATION').setCheck('Orientation')
        .appendField('rotation')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new RevHubOrientationOnRobot object for a REV Hub that is ' +
        'mounted at any arbitrary angle on a robot using an Orientation object. The Orientation ' +
        'block specifies the rotation (defined within the Robot Coordinate System) that would ' +
        'need to be applied in order to rotate a REV Control or Expansion Hub from having its ' +
        'logo facing up and the USB ports facing forward, to its actual orientation on the ' +
        'robot. This block should be plugged into the imuOrientationOnRobot socket on the ' +
        '"new IMU.Parameters" block.');
  }
};

Blockly.JavaScript['revHubOrientationOnRobot_create2'] = function(block) {
  var rotation = Blockly.JavaScript.valueToCode(
      block, 'ROTATION', Blockly.JavaScript.ORDER_NONE);
  var code = revHubOrientationOnRobotIdentifierForJavaScript + '.create2(' + rotation + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['revHubOrientationOnRobot_create2'] = function(block) {
  var rotation = Blockly.FtcJava.valueToCode(
      block, 'ROTATION', Blockly.FtcJava.ORDER_NONE);
  var code = 'new RevHubOrientationOnRobot(' + rotation + ')';
  Blockly.FtcJava.generateImport_('RevHubOrientationOnRobot');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

Blockly.Blocks['revHubOrientationOnRobot_create3'] = {
  init: function() {
    this.setOutput(true, 'ImuOrientationOnRobot');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('RevHubOrientationOnRobot'));
    this.appendValueInput('ROTATION').setCheck('Quaternion')
        .appendField('rotation')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new RevHubOrientationOnRobot object for a REV Hub that is ' +
        'mounted at any arbitrary angle on a robot using a Quaternion object. The Quaternion ' +
        'block specifies the rotation (defined within the Robot Coordinate System) that would ' +
        'need to be applied in order to rotate a REV Control or Expansion Hub from having its ' +
        'logo facing up and the USB ports facing forward, to its actual orientation on the ' +
        'robot. This block should be plugged into the imuOrientationOnRobot socket on the ' +
        '"new IMU.Parameters" block.');
  }
};

Blockly.JavaScript['revHubOrientationOnRobot_create3'] = function(block) {
  var rotation = Blockly.JavaScript.valueToCode(
      block, 'ROTATION', Blockly.JavaScript.ORDER_NONE);
  var code = revHubOrientationOnRobotIdentifierForJavaScript + '.create3(' + rotation + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['revHubOrientationOnRobot_create3'] = function(block) {
  var rotation = Blockly.FtcJava.valueToCode(
      block, 'ROTATION', Blockly.FtcJava.ORDER_NONE);
  var code = 'new RevHubOrientationOnRobot(' + rotation + ')';
  Blockly.FtcJava.generateImport_('RevHubOrientationOnRobot');
  return [code, Blockly.FtcJava.ORDER_NEW];
};


// Enums

Blockly.Blocks['revHubOrientationOnRobot_typedEnum_logoFacingDirection'] = {
  init: function() {
    var LOGO_FACING_DIRECTION_CHOICES = [
        ['UP', 'UP'],
        ['DOWN', 'DOWN'],
        ['FORWARD', 'FORWARD'],
        ['BACKWARD', 'BACKWARD'],
        ['LEFT', 'LEFT'],
        ['RIGHT', 'RIGHT'],
        ];
    this.setOutput(true, 'RevHubOrientationOnRobot.LogoFacingDirection');
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

Blockly.JavaScript['revHubOrientationOnRobot_typedEnum_logoFacingDirection'] = function(block) {
  var code = '"' + block.getFieldValue('LOGO_FACING_DIRECTION') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['revHubOrientationOnRobot_typedEnum_logoFacingDirection'] = function(block) {
  var code = 'RevHubOrientationOnRobot.LogoFacingDirection.' + block.getFieldValue('LOGO_FACING_DIRECTION');
  Blockly.FtcJava.generateImport_('RevHubOrientationOnRobot');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['revHubOrientationOnRobot_typedEnum_usbFacingDirection'] = {
  init: function() {
    var USB_FACING_DIRECTION_CHOICES = [
        ['UP', 'UP'],
        ['DOWN', 'DOWN'],
        ['FORWARD', 'FORWARD'],
        ['BACKWARD', 'BACKWARD'],
        ['LEFT', 'LEFT'],
        ['RIGHT', 'RIGHT'],
        ];
    this.setOutput(true, 'RevHubOrientationOnRobot.UsbFacingDirection');
    this.appendDummyInput()
        .appendField(createNonEditableField('UsbFacingDirection'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(USB_FACING_DIRECTION_CHOICES), 'USB_FACING_DIRECTION');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['UP', 'The UsbFacingDirection value UP.'],
        ['DOWN', 'The UsbFacingDirection value DOWN.'],
        ['FORWARD', 'The UsbFacingDirection value FORWARD.'],
        ['BACKWARD', 'The UsbFacingDirection value BACKWARD.'],
        ['LEFT', 'The UsbFacingDirection value LEFT.'],
        ['RIGHT', 'The UsbFacingDirection value RIGHT.'],
        ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('USB_FACING_DIRECTION');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['revHubOrientationOnRobot_typedEnum_usbFacingDirection'] = function(block) {
  var code = '"' + block.getFieldValue('USB_FACING_DIRECTION') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['revHubOrientationOnRobot_typedEnum_usbFacingDirection'] = function(block) {
  var code = 'RevHubOrientationOnRobot.UsbFacingDirection.' + block.getFieldValue('USB_FACING_DIRECTION');
  Blockly.FtcJava.generateImport_('RevHubOrientationOnRobot');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};


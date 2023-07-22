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
 * @fileoverview FTC robot blocks related to YawPitchRollAngles.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// yawPitchRollAnglesIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// CreatePropertyDropdown
// functionColor
// getPropertyColor


Blockly.Blocks['yawPitchRollAngles_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['AcquisitionTime', 'AcquisitionTime'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('YawPitchRollAngles'))
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('YAW_PITCH_ROLL_ANGLES').setCheck('YawPitchRollAngles')
        .appendField('yawPitchRollAngles')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['AcquisitionTime', 'Returns the AcquisitionTime of the given YawPitchRollAngles object.'],
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
    this.getFtcJavaOutputType = function() {
      var property = thisBlock.getFieldValue('PROP');
      switch (property) {
        case 'AcquisitionTime':
          return 'long';
        default:
          throw 'Unexpected property ' + property + ' (yawPitchRollAngles_getProperty_Number getOutputType).';
      }
    };
  }
};
Blockly.JavaScript['yawPitchRollAngles_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var yawPitchRollAngles = Blockly.JavaScript.valueToCode(
      block, 'YAW_PITCH_ROLL_ANGLES', Blockly.JavaScript.ORDER_COMMA);
  var code = yawPitchRollAnglesIdentifierForJavaScript + '.get' + property + '(' + yawPitchRollAngles + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['yawPitchRollAngles_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var yawPitchRollAngles = Blockly.FtcJava.valueToCode(
      block, 'YAW_PITCH_ROLL_ANGLES', Blockly.FtcJava.ORDER_MEMBER);
  var code = yawPitchRollAngles + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['yawPitchRollAngles_getPropertyWithAngleUnit_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Yaw', 'Yaw'],
        ['Pitch', 'Pitch'],
        ['Roll', 'Roll'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('YawPitchRollAngles'))
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('YAW_PITCH_ROLL_ANGLES').setCheck('YawPitchRollAngles')
        .appendField('yawPitchRollAngles')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ANGLE_UNIT').setCheck('AngleUnit')
        .appendField('angleUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Yaw', 'Returns the side-to-side lateral rotation of the robot ' +
            '(rotation around the Z axis), normalized to the range of [-180,+180) degrees.'],
        ['Pitch', 'Returns the front-to-back rotation of the robot ' +
            '(rotation around the X axis), normalized to the range of [-180,+180) degrees.'],
        ['Roll', 'Returns the side-to-side tilt of the robot ' +
            '(rotation around the Y axis), normalized to the range of [-180,+180) degrees.'],
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
    this.getFtcJavaOutputType = function() {
      var property = thisBlock.getFieldValue('PROP');
      switch (property) {
        case 'Yaw':
        case 'Pitch':
        case 'Roll':
          return 'double';
        default:
          throw 'Unexpected property ' + property + ' (yawPitchRollAngles_getPropertyWithAngleUnit_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['yawPitchRollAngles_getPropertyWithAngleUnit_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var yawPitchRollAngles = Blockly.JavaScript.valueToCode(
      block, 'YAW_PITCH_ROLL_ANGLES', Blockly.JavaScript.ORDER_COMMA);
  var angleUnit = Blockly.JavaScript.valueToCode(
      block, 'ANGLE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = yawPitchRollAnglesIdentifierForJavaScript + '.get' + property + '(' + yawPitchRollAngles + ', ' + angleUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['yawPitchRollAngles_getPropertyWithAngleUnit_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var yawPitchRollAngles = Blockly.FtcJava.valueToCode(
      block, 'YAW_PITCH_ROLL_ANGLES', Blockly.FtcJava.ORDER_MEMBER);
  var angleUnit = Blockly.FtcJava.valueToCode(
      block, 'ANGLE_UNIT', Blockly.FtcJava.ORDER_NONE);
  var code = yawPitchRollAngles + '.get' + property + '(' +  angleUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['yawPitchRollAngles_create_withArgs'] = {
  init: function() {
    this.setOutput(true, 'YawPitchRollAngles');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('YawPitchRollAngles'));
    this.appendValueInput('ANGLE_UNIT').setCheck('AngleUnit')
        .appendField('angleUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('YAW').setCheck('Number')
        .appendField('yaw')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PITCH').setCheck('Number')
        .appendField('pitch')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ROLL').setCheck('Number')
        .appendField('roll')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ACQUISITION_TIME').setCheck('Number')
        .appendField('acquisitionTime')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new YawPitchRollAngles object.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'YAW':
        case 'PITCH':
        case 'ROLL':
          return 'double';
        case 'ACQUISITION_TIME':
          return 'long';
      }
      return '';
    };
  }
};

Blockly.JavaScript['yawPitchRollAngles_create_withArgs'] = function(block) {
  var angleUnit = Blockly.JavaScript.valueToCode(
      block, 'ANGLE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var yaw = Blockly.JavaScript.valueToCode(
      block, 'YAW', Blockly.JavaScript.ORDER_COMMA);
  var pitch = Blockly.JavaScript.valueToCode(
      block, 'PITCH', Blockly.JavaScript.ORDER_COMMA);
  var roll = Blockly.JavaScript.valueToCode(
      block, 'ROLL', Blockly.JavaScript.ORDER_COMMA);
  var acquisitionTime = Blockly.JavaScript.valueToCode(
      block, 'ACQUISITION_TIME', Blockly.JavaScript.ORDER_COMMA);
  var code = yawPitchRollAnglesIdentifierForJavaScript + '.create_withArgs(' + angleUnit + ', ' +
      yaw + ', ' + pitch + ', ' + roll + ', ' + acquisitionTime + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['yawPitchRollAngles_create_withArgs'] = function(block) {
  var angleUnit = Blockly.FtcJava.valueToCode(
      block, 'ANGLE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var yaw = Blockly.FtcJava.valueToCode(
      block, 'YAW', Blockly.FtcJava.ORDER_COMMA);
  var pitch = Blockly.FtcJava.valueToCode(
      block, 'PITCH', Blockly.FtcJava.ORDER_COMMA);
  var roll = Blockly.FtcJava.valueToCode(
      block, 'ROLL', Blockly.FtcJava.ORDER_COMMA);
  var acquisitionTime = Blockly.FtcJava.valueToCode(
      block, 'ACQUISITION_TIME', Blockly.FtcJava.ORDER_COMMA);
  var code = 'new YawPitchRollAngles(' + angleUnit + ', ' +
      yaw + ', ' + pitch + ', ' + roll + ', ' + acquisitionTime + ')';
  Blockly.FtcJava.generateImport_('YawPitchRollAngles');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

Blockly.Blocks['yawPitchRollAngles_create_withArgs2'] = {
  init: function() {
    this.setOutput(true, 'YawPitchRollAngles');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('YawPitchRollAngles'));
    this.appendValueInput('ANGLE_UNIT').setCheck('AngleUnit')
        .appendField('angleUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('YAW').setCheck('Number')
        .appendField('yaw')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PITCH').setCheck('Number')
        .appendField('pitch')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ROLL').setCheck('Number')
        .appendField('roll')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new YawPitchRollAngles object.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'YAW':
        case 'PITCH':
        case 'ROLL':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['yawPitchRollAngles_create_withArgs2'] = function(block) {
  var angleUnit = Blockly.JavaScript.valueToCode(
      block, 'ANGLE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var yaw = Blockly.JavaScript.valueToCode(
      block, 'YAW', Blockly.JavaScript.ORDER_COMMA);
  var pitch = Blockly.JavaScript.valueToCode(
      block, 'PITCH', Blockly.JavaScript.ORDER_COMMA);
  var roll = Blockly.JavaScript.valueToCode(
      block, 'ROLL', Blockly.JavaScript.ORDER_COMMA);
  var code = yawPitchRollAnglesIdentifierForJavaScript + '.create_withArgs2(' + angleUnit + ', ' +
      yaw + ', ' + pitch + ', ' + roll + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['yawPitchRollAngles_create_withArgs2'] = function(block) {
  var angleUnit = Blockly.FtcJava.valueToCode(
      block, 'ANGLE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var yaw = Blockly.FtcJava.valueToCode(
      block, 'YAW', Blockly.FtcJava.ORDER_COMMA);
  var pitch = Blockly.FtcJava.valueToCode(
      block, 'PITCH', Blockly.FtcJava.ORDER_COMMA);
  var roll = Blockly.FtcJava.valueToCode(
      block, 'ROLL', Blockly.FtcJava.ORDER_COMMA);
  var acquisitionTime = '0';
  var code = 'new YawPitchRollAngles(' + angleUnit + ', ' +
      yaw + ', ' + pitch + ', ' + roll + ', ' + acquisitionTime + ')';
  Blockly.FtcJava.generateImport_('YawPitchRollAngles');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

Blockly.Blocks['yawPitchRollAngles_toText'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('YawPitchRollAngles'))
        .appendField('.')
        .appendField(createNonEditableField('toText'));
    this.appendValueInput('YAW_PITCH_ROLL_ANGLES').setCheck('YawPitchRollAngles')
        .appendField('yawPitchRollAngles')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a text representation of the given YawPitchRollAngles object.');
  }
};

Blockly.JavaScript['yawPitchRollAngles_toText'] = function(block) {
  var yawPitchRollAngles = Blockly.JavaScript.valueToCode(
      block, 'YAW_PITCH_ROLL_ANGLES', Blockly.JavaScript.ORDER_NONE);
  var code = yawPitchRollAnglesIdentifierForJavaScript + '.toText(' + yawPitchRollAngles + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['yawPitchRollAngles_toText'] = function(block) {
  var yawPitchRollAngles = Blockly.FtcJava.valueToCode(
      block, 'YAW_PITCH_ROLL_ANGLES', Blockly.FtcJava.ORDER_MEMBER);
  var code = yawPitchRollAngles + '.toString()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

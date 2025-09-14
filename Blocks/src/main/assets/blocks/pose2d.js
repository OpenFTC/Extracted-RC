/**
 * @license
 * Copyright 2024 Google LLC
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
 * @fileoverview FTC robot blocks related to Pose2D.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// pose2DIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// getPropertyColor
// functionColor

Blockly.Blocks['pose2D_create'] = {
  init: function() {
    this.setOutput(true, 'Pose2D');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('Pose2D'));
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('X').setCheck('Number')
        .appendField('x')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('Y').setCheck('Number')
        .appendField('y')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('HEADING_UNIT').setCheck('AngleUnit')
        .appendField('headingUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('HEADING').setCheck('Number')
        .appendField('heading')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new Pose2D object.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'X':
        case 'Y':
        case 'HEADING':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['pose2D_create'] = function(block) {
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var x = Blockly.JavaScript.valueToCode(
      block, 'X', Blockly.JavaScript.ORDER_COMMA);
  var y = Blockly.JavaScript.valueToCode(
      block, 'Y', Blockly.JavaScript.ORDER_COMMA);
  var heading = Blockly.JavaScript.valueToCode(
      block, 'HEADING', Blockly.JavaScript.ORDER_COMMA);
  var headingUnit = Blockly.JavaScript.valueToCode(
      block, 'HEADING_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = pose2DIdentifierForJavaScript + '.create(' + distanceUnit + ', ' + x + ', ' +
      y + ', ' + headingUnit + ', ' + heading + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['pose2D_create'] = function(block) {
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var x = Blockly.FtcJava.valueToCode(
      block, 'X', Blockly.FtcJava.ORDER_COMMA);
  var y = Blockly.FtcJava.valueToCode(
      block, 'Y', Blockly.FtcJava.ORDER_COMMA);
  var heading = Blockly.FtcJava.valueToCode(
      block, 'HEADING', Blockly.FtcJava.ORDER_COMMA);
  var headingUnit = Blockly.FtcJava.valueToCode(
      block, 'HEADING_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = 'new Pose2D(' + distanceUnit + ', ' + x + ', ' + y + ', ' + headingUnit + ', ' +
      heading + ')';
  Blockly.FtcJava.generateImport_('Pose2D');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

Blockly.Blocks['pose2D_getPropertyWithDistanceUnit_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['X', 'X'],
        ['Y', 'Y'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('Pose2D'))
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('POSE2D').setCheck('Pose2D')
        .appendField('pose2D')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('UNIT').setCheck('DistanceUnit')
        .appendField('unit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['X', 'Returns the X in the desired distance unit.'],
        ['Y', 'Returns the Y in the desired distance unit.'],
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
        case 'X':
        case 'Y':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['pose2D_getPropertyWithDistanceUnit_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var pose2D = Blockly.JavaScript.valueToCode(
      block, 'POSE2D', Blockly.JavaScript.ORDER_COMMA);
  var unit = Blockly.JavaScript.valueToCode(
      block, 'UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = pose2DIdentifierForJavaScript + '.get' + property + '(' + pose2D + ', ' + unit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['pose2D_getPropertyWithDistanceUnit_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var pose2D = Blockly.FtcJava.valueToCode(
      block, 'POSE2D', Blockly.FtcJava.ORDER_MEMBER);
  var unit = Blockly.FtcJava.valueToCode(
      block, 'UNIT', Blockly.FtcJava.ORDER_NONE);
  var code = pose2D + '.get' + property + '(' +  unit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['pose2D_getPropertyWithAngleUnit_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Heading', 'Heading'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('Pose2D'))
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('POSE2D').setCheck('Pose2D')
        .appendField('pose2D')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('UNIT').setCheck('AngleUnit')
        .appendField('unit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
      ['Heading', 'Returns the heading in the desired distance unit. ' +
                  'Be aware that this normalizes the angle to be between -PI and PI for RADIANS or ' +
                  'between -180 and 180 for DEGREES.'],
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
        case 'Heading':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['pose2D_getPropertyWithAngleUnit_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var pose2D = Blockly.JavaScript.valueToCode(
      block, 'POSE2D', Blockly.JavaScript.ORDER_COMMA);
  var unit = Blockly.JavaScript.valueToCode(
      block, 'UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = pose2DIdentifierForJavaScript + '.get' + property + '(' + pose2D + ', ' + unit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['pose2D_getPropertyWithAngleUnit_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var pose2D = Blockly.FtcJava.valueToCode(
      block, 'POSE2D', Blockly.FtcJava.ORDER_MEMBER);
  var unit = Blockly.FtcJava.valueToCode(
      block, 'UNIT', Blockly.FtcJava.ORDER_NONE);
  var code = pose2D + '.get' + property + '(' +  unit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['pose2D_toText'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Pose2D'))
        .appendField('.')
        .appendField(createNonEditableField('toText'));
    this.appendValueInput('POSE2D').setCheck('Pose2D')
        .appendField('pose2D')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a text representation of the given Pose2D object.');
  }
};

Blockly.JavaScript['pose2D_toText'] = function(block) {
  var pose2D = Blockly.JavaScript.valueToCode(
      block, 'POSE2D', Blockly.JavaScript.ORDER_NONE);
  var code = pose2DIdentifierForJavaScript + '.toText(' + pose2D + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['pose2D_toText'] = function(block) {
  var pose2D = Blockly.FtcJava.valueToCode(
      block, 'POSE2D', Blockly.FtcJava.ORDER_MEMBER);
  var code = pose2D + '.toString()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

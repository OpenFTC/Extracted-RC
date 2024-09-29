/**
 * @license
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

/**
 * @fileoverview FTC robot blocks related to Recognitions from TensorFlow Object Detection.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// navigationIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// getPropertyColor

Blockly.Blocks['tfodRecognition_getProperty_String'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Label', 'Label'],
    ];
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField(createNonEditableField('TfodRecognition'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('TFOD_RECOGNITION').setCheck('Recognition')
        .appendField('tfodRecognition')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Label', 'Returns the label of the recognized object.'],
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

Blockly.JavaScript['tfodRecognition_getProperty_String'] = function(block) {
  var property = block.getFieldValue('PROP');
  var tfodRecognition = Blockly.JavaScript.valueToCode(
      block, 'TFOD_RECOGNITION', Blockly.JavaScript.ORDER_MEMBER);
  var code = tfodRecognition + '.' + property;
  var blockLabel = 'TfodRecognition.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['tfodRecognition_getProperty_String'] = function(block) {
  var property = block.getFieldValue('PROP');
  var tfodRecognition = Blockly.FtcJava.valueToCode(
      block, 'TFOD_RECOGNITION', Blockly.FtcJava.ORDER_NONE);
  var code = tfodRecognition + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['tfodRecognition_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Confidence', 'Confidence'],
        ['Left', 'Left'],
        ['Right', 'Right'],
        ['Top', 'Top'],
        ['Bottom', 'Bottom'],
        ['Width', 'Width'],
        ['Height', 'Height'],
        ['ImageWidth', 'ImageWidth'],
        ['ImageHeight', 'ImageHeight'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('TfodRecognition'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('TFOD_RECOGNITION').setCheck('Recognition')
        .appendField('tfodRecognition')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Confidence', 'Returns the confidence of the recognition.'],
        ['Left', 'Returns the left coordinate, in pixels, of the recognized object.'],
        ['Right', 'Returns the right coordinate, in pixels, of the recognized object.'],
        ['Top', 'Returns the top coordinate, in pixels, of the recognized object.'],
        ['Bottom', 'Returns the bottom coordinate, in pixels, of the recognized object.'],
        ['Width', 'Returns the width, in pixels, of the recoginzed object.'],
        ['Height', 'Returns the height, in pixels, of the recoginzed object.'],
        ['ImageWidth', 'Returns the width, in pixels, of the entire image.'],
        ['ImageHeight', 'Returns the height, in pixels, of the entire image.'],
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
        case 'Confidence':
        case 'Left':
        case 'Right':
        case 'Top':
        case 'Bottom':
        case 'Width':
        case 'Height':
          return 'float';
        case 'ImageWidth':
        case 'ImageHeight':
          return 'int';
        default:
          throw 'Unexpected property ' + property + ' (tfodRecognition_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['tfodRecognition_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var tfodRecognition = Blockly.JavaScript.valueToCode(
      block, 'TFOD_RECOGNITION', Blockly.JavaScript.ORDER_MEMBER);
  var code = tfodRecognition + '.' + property;
  var blockLabel = 'TfodRecognition.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['tfodRecognition_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var tfodRecognition = Blockly.FtcJava.valueToCode(
      block, 'TFOD_RECOGNITION', Blockly.FtcJava.ORDER_NONE);
  var code = tfodRecognition + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['tfodRecognition_toText'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TfodRecognition'))
        .appendField('.')
        .appendField(createNonEditableField('toText'));
    this.appendValueInput('TFOD_RECOGNITION').setCheck('Recognition')
        .appendField('tfodRecognition')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a text representation of the given TfodRecognition.');
  }
};

Blockly.JavaScript['tfodRecognition_toText'] = function(block) {
  var tfodRecognition = Blockly.JavaScript.valueToCode(
      block, 'TFOD_RECOGNITION', Blockly.JavaScript.ORDER_NONE);
  var code = 'JSON.stringify(' + tfodRecognition + ')';
  var blockLabel = 'TfodRecognition.toText';
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['tfodRecognition_toText'] = function(block) {
  var tfodRecognition = Blockly.FtcJava.valueToCode(
      block, 'TFOD_RECOGNITION', Blockly.FtcJava.ORDER_MEMBER);
  var code = tfodRecognition + '.toString()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['tfodRecognition_estimateAngleToObject'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TfodRecognition'))
        .appendField('.')
        .appendField(createNonEditableField('estimateAngleToObject'));
    this.appendValueInput('TFOD_RECOGNITION').setCheck('Recognition')
        .appendField('tfodRecognition')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ANGLE_UNIT').setCheck('AngleUnit')
        .appendField('angleUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns an estimation of the horizontal angle to the detected object.');
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['tfodRecognition_estimateAngleToObject'] = function(block) {
  var tfodRecognition = Blockly.JavaScript.valueToCode(
      block, 'TFOD_RECOGNITION', Blockly.JavaScript.ORDER_MEMBER);
  var angleUnit = Blockly.JavaScript.valueToCode(
      block, 'ANGLE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = tfodRecognition + '.estimateAngleToObject';
  var blockLabel = 'TfodRecognition.estimateAngleToObject';
  var wrapped = wrapJavaScriptCode(code, blockLabel);
  code = navigationIdentifierForJavaScript + '.angleUnit_convert((' +
      wrapped[0] + '), "RADIANS", ' + angleUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['tfodRecognition_estimateAngleToObject'] = function(block) {
  var tfodRecognition = Blockly.FtcJava.valueToCode(
      block, 'TFOD_RECOGNITION', Blockly.FtcJava.ORDER_MEMBER);
  var angleUnit = Blockly.FtcJava.valueToCode(
      block, 'ANGLE_UNIT', Blockly.FtcJava.ORDER_NONE);
  var code = tfodRecognition + '.estimateAngleToObject(' + angleUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// Enums

Blockly.Blocks['tfod_typedEnum_label'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField(createNonEditableField('Label'))
        .appendField('.')
        .appendField(createTfodCurrentGameLabelDropdown(), 'LABEL');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = TFOD_CURRENT_GAME_LABEL_TOOLTIPS;
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('LABEL');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['tfod_typedEnum_label'] = function(block) {
  var code = '"' + block.getFieldValue('LABEL') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['tfod_typedEnum_label'] = function(block) {
  // Even in Java, a label is actually just a string, not an enum.
  var code = '"' + block.getFieldValue('LABEL') + '"';
  return [code, Blockly.FtcJava.ORDER_ATOMIC];
};


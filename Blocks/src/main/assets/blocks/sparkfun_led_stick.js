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
 * @fileoverview FTC robot blocks related to the SparkFun Qwiic LED Stick.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// createSparkFunLEDStickDropdown
// getSparkFunLEDStickConstant
// The following are defined in vars.js:
// createNonEditableField
// functionColor

Blockly.Blocks['sparkFunLEDStick_setColor_withPosition'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunLEDStickDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setColor'));
    this.appendValueInput('POSITION').setCheck('Number')
        .appendField('position')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('COLOR').setCheck('Number')
        .appendField('color')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Changes the color of a specific LED specified by a one-based position.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'POSITION':
        case 'COLOR':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['sparkFunLEDStick_setColor_withPosition'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var position = Blockly.JavaScript.valueToCode(
      block, 'POSITION', Blockly.JavaScript.ORDER_COMMA);
  var color = Blockly.JavaScript.valueToCode(
      block, 'COLOR', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setColor_withPosition(' + position + ', ' + color + ');\n';
};

Blockly.FtcJava['sparkFunLEDStick_setColor_withPosition'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunLEDStick');
  var position = Blockly.FtcJava.valueToCode(
      block, 'POSITION', Blockly.FtcJava.ORDER_COMMA);
  var color = Blockly.FtcJava.valueToCode(
      block, 'COLOR', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setColor(' + position + ', ' + color + ');\n';
};

Blockly.Blocks['sparkFunLEDStick_setColor'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunLEDStickDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setColor'));
    this.appendValueInput('COLOR').setCheck('Number')
        .appendField('color')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Change the color of all LEDs to a single color.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'COLOR':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['sparkFunLEDStick_setColor'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var color = Blockly.JavaScript.valueToCode(
      block, 'COLOR', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.setColor(' + color + ');\n';
};

Blockly.FtcJava['sparkFunLEDStick_setColor'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunLEDStick');
  var color = Blockly.FtcJava.valueToCode(
      block, 'COLOR', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.setColor(' + color + ');\n';
};

Blockly.Blocks['sparkFunLEDStick_setColors'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunLEDStickDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setColors'));
    this.appendValueInput('COLORS').setCheck('Array')
        .appendField('colors')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Change the color of multiple LEDs using a list.');
  }
};

Blockly.JavaScript['sparkFunLEDStick_setColors'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var colors = Blockly.JavaScript.valueToCode(
      block, 'COLORS', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.setColors(JSON.stringify(' + colors + '));\n';
};

Blockly.FtcJava['sparkFunLEDStick_setColors'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunLEDStick');
  var colors = Blockly.FtcJava.valueToCode(
      block, 'COLORS', Blockly.FtcJava.ORDER_NONE);
  Blockly.FtcJava.generateImport_('JavaUtil');
  return identifier + '.setColors(JavaUtil.makeIntArray(' + colors + '));\n';
};

Blockly.Blocks['sparkFunLEDStick_setBrightness_withPosition'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunLEDStickDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setBrightness'));
    this.appendValueInput('POSITION').setCheck('Number')
        .appendField('position')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('BRIGHTNESS').setCheck('Number')
        .appendField('brightness')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the brightness of an individual LED specified by a one-based position.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'POSITION':
        case 'BRIGHTNESS':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['sparkFunLEDStick_setBrightness_withPosition'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var position = Blockly.JavaScript.valueToCode(
      block, 'POSITION', Blockly.JavaScript.ORDER_COMMA);
  var brightness = Blockly.JavaScript.valueToCode(
      block, 'BRIGHTNESS', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setBrightness_withPosition(' + position + ', ' + brightness + ');\n';
};

Blockly.FtcJava['sparkFunLEDStick_setBrightness_withPosition'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunLEDStick');
  var position = Blockly.FtcJava.valueToCode(
      block, 'POSITION', Blockly.FtcJava.ORDER_COMMA);
  var brightness = Blockly.FtcJava.valueToCode(
      block, 'BRIGHTNESS', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setBrightness(' + position + ', ' + brightness + ');\n';
};

Blockly.Blocks['sparkFunLEDStick_setBrightness'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunLEDStickDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setBrightness'));
    this.appendValueInput('BRIGHTNESS').setCheck('Number')
        .appendField('brightness')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the brightness of all LEDs.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'BRIGHTNESS':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['sparkFunLEDStick_setBrightness'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var brightness = Blockly.JavaScript.valueToCode(
      block, 'BRIGHTNESS', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.setBrightness(' + brightness + ');\n';
};

Blockly.FtcJava['sparkFunLEDStick_setBrightness'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunLEDStick');
  var brightness = Blockly.FtcJava.valueToCode(
      block, 'BRIGHTNESS', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.setBrightness(' + brightness + ');\n';
};

Blockly.Blocks['sparkFunLEDStick_turnAllOff'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunLEDStickDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('turnAllOff'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Turns all LEDS off.');
  }
};

Blockly.JavaScript['sparkFunLEDStick_turnAllOff'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.turnAllOff();\n';
};

Blockly.FtcJava['sparkFunLEDStick_turnAllOff'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunLEDStick');
  return identifier + '.turnAllOff();\n';
};

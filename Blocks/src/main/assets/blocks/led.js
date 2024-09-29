/**
 * @license
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

/**
 * @fileoverview FTC robot blocks related to LED.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// createLedDropdown
// The following are defined in vars.js:
// createNonEditableField
// functionColor

// Functions

Blockly.Blocks['led_enableLed'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLedDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('enableLed'));
    this.appendValueInput('ENABLE') // no type, for compatibility
        .appendField('enable')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Enable the LED light.');
  }
};

Blockly.JavaScript['led_enableLed'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var enable = Blockly.JavaScript.valueToCode(
      block, 'ENABLE', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.enableLed(' + enable + ');\n';
};

Blockly.FtcJava['led_enableLed'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'LED');
  var enable = Blockly.FtcJava.valueToCode(
      block, 'ENABLE', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.enable(' + enable + ');\n';
};

Blockly.Blocks['led_enableLed_Boolean'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLedDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('enableLed'));
    this.appendValueInput('ENABLE').setCheck('Boolean')
        .appendField('enable')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Enable the LED light.');
  }
};

Blockly.JavaScript['led_enableLed_Boolean'] =
    Blockly.JavaScript['led_enableLed'];

Blockly.FtcJava['led_enableLed_Boolean'] =
    Blockly.FtcJava['led_enableLed'];

Blockly.Blocks['led_isLightOn'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLedDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('isLightOn'));
    this.setColour(functionColor);
    this.setTooltip('Returns true if the light is on.');
  }
};

Blockly.JavaScript['led_isLightOn'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.isLightOn()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['led_isLightOn'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'LED');
  var code = identifier + '.isLightOn()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['led_on'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLedDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('on'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Turn on the LED light.');
  }
};

Blockly.JavaScript['led_on'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.on();\n';
};

Blockly.FtcJava['led_on'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'LED');
  return identifier + '.on();\n';
};

Blockly.Blocks['led_off'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLedDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('off'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Turn off the LED light.');
  }
};

Blockly.JavaScript['led_off'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.off();\n';
};

Blockly.FtcJava['led_off'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'LED');
  return identifier + '.off();\n';
};
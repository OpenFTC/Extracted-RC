/**
 * @license
 * Copyright 2025 Google LLC
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
 * @fileoverview FTC robot blocks related to the blackboard.
 * @author Liz Looney
 */
// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// blackboardIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor


// Functions

Blockly.Blocks['blackboard_clear'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Blackboard'))
        .appendField('.')
        .appendField(createNonEditableField('clear'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Removes all of the mappings from the blackboard.');
  }
};

Blockly.JavaScript['blackboard_clear'] = function(block) {
  return blackboardIdentifierForJavaScript + '.clear();\n';
};

Blockly.FtcJava['blackboard_clear'] = function(block) {
  return 'blackboard.clear();\n';
};

Blockly.Blocks['blackboard_containsKey'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Blackboard'))
        .appendField('.')
        .appendField(createNonEditableField('containsKey'));
    this.appendValueInput('KEY').setCheck('String')
        .appendField('key')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns true if the blackboard contains a mapping for the specified key.');
  }
};

Blockly.JavaScript['blackboard_containsKey'] = function(block) {
  var key = Blockly.JavaScript.valueToCode(
      block, 'KEY', Blockly.JavaScript.ORDER_COMMA);
  var code = blackboardIdentifierForJavaScript + '.containsKey(' + key + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['blackboard_containsKey'] = function(block) {
  var key = Blockly.FtcJava.valueToCode(
      block, 'KEY', Blockly.FtcJava.ORDER_COMMA);
  var code = 'blackboard.containsKey(' + key + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['blackboard_get'] = {
  init: function() {
    this.setOutput(true);
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Blackboard'))
        .appendField('.')
        .appendField(createNonEditableField('get'));
    this.appendValueInput('KEY').setCheck('String')
        .appendField('key')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the value to which the specified key is mapped, or null if the blackboard contains no mapping for the key.');
  }
};

Blockly.JavaScript['blackboard_get'] = function(block) {
  var key = Blockly.JavaScript.valueToCode(
      block, 'KEY', Blockly.JavaScript.ORDER_COMMA);
  var code = 'blackboardGet(' + key + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['blackboard_get'] = function(block) {
  var key = Blockly.FtcJava.valueToCode(
      block, 'KEY', Blockly.FtcJava.ORDER_COMMA);
  var code = 'blackboard.get(' + key + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['blackboard_getOrDefault'] = {
  init: function() {
    this.setOutput(true);
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Blackboard'))
        .appendField('.')
        .appendField(createNonEditableField('getOrDefault'));
    this.appendValueInput('KEY').setCheck('String')
        .appendField('key')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DEFAULT_VALUE') // all types allowed
        .appendField('defaultValue')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the value to which the specified key is mapped, or defaultValue if the blackboard contains no mapping for the key.');
  }
};

Blockly.JavaScript['blackboard_getOrDefault'] = function(block) {
  var key = Blockly.JavaScript.valueToCode(
      block, 'KEY', Blockly.JavaScript.ORDER_COMMA);
  var defaultValue = Blockly.JavaScript.valueToCode(
      block, 'DEFAULT_VALUE', Blockly.JavaScript.ORDER_COMMA);
  var code = 'blackboardGetOrDefault("getOrDefault", ' + key + ', ' + defaultValue + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['blackboard_getOrDefault'] = function(block) {
  var key = Blockly.FtcJava.valueToCode(
      block, 'KEY', Blockly.FtcJava.ORDER_COMMA);
  var defaultValue = Blockly.FtcJava.valueToCode(
      block, 'DEFAULT_VALUE', Blockly.FtcJava.ORDER_COMMA);
  // Check if we should cast the result, based on the block plugged into the defaultValue socket.
  var cast = '';
  var input = block.getInput('DEFAULT_VALUE');
  if (input && input.connection) {
    var defaultValueBlock = input.connection.targetBlock();
    if (defaultValueBlock) {
      if (defaultValueBlock.type == 'math_number') {
        // If the defaultValue is a number block, add a cast for double.
        cast = '(double) ';
      } else if (defaultValueBlock.type == 'logic_boolean') {
        // If the defaultValue is a boolean block, add a cast for boolean.
        cast = '(boolean) ';
      }
    }
  }
  var code = cast + 'blackboard.getOrDefault(' + key + ', ' + defaultValue + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['blackboard_isEmpty'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Blackboard'))
        .appendField('.')
        .appendField(createNonEditableField('isEmpty'));
    this.setColour(functionColor);
    this.setTooltip('Returns true if the blackboard contains no key-value mappings.');
  }
};

Blockly.JavaScript['blackboard_isEmpty'] = function(block) {
  var code = blackboardIdentifierForJavaScript + '.isEmpty()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['blackboard_isEmpty'] = function(block) {
  var code = 'blackboard.isEmpty()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['blackboard_put'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Blackboard'))
        .appendField('.')
        .appendField(createNonEditableField('put'));
    this.appendValueInput('KEY').setCheck('String')
        .appendField('key')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VALUE') // all types allowed
        .appendField('value')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Associates the specified value with the specified key in the blackboard. ' +
                    'If the blackboard previously contained a mapping for the key, the old value is replaced by the specified value.');
  }
};

Blockly.JavaScript['blackboard_put'] = function(block) {
  var key = Blockly.JavaScript.valueToCode(
      block, 'KEY', Blockly.JavaScript.ORDER_COMMA);
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_COMMA);
  return 'blackboardPut(' + key + ', ' + value + ');\n';
};

Blockly.FtcJava['blackboard_put'] = function(block) {
  var key = Blockly.FtcJava.valueToCode(
      block, 'KEY', Blockly.FtcJava.ORDER_COMMA);
  var value = Blockly.FtcJava.valueToCode(
      block, 'VALUE', Blockly.FtcJava.ORDER_COMMA);
  return 'blackboard.put(' + key + ', ' + value + ');\n';
};

Blockly.Blocks['blackboard_remove'] = {
  init: function() {
    this.setOutput(true);
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Blackboard'))
        .appendField('.')
        .appendField(createNonEditableField('remove'));
    this.appendValueInput('KEY').setCheck('String')
        .appendField('key')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Removes the mapping for a key from the blackboard if it is present. ' +
                    'Returns the value to which the blackboard previously associated the key, or null if the map contained no mapping for the key.');
  }
};

Blockly.JavaScript['blackboard_remove'] = function(block) {
  var key = Blockly.JavaScript.valueToCode(
      block, 'KEY', Blockly.JavaScript.ORDER_COMMA);
  var code = 'blackboardRemove(' + key + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['blackboard_remove'] = function(block) {
  var key = Blockly.FtcJava.valueToCode(
      block, 'KEY', Blockly.FtcJava.ORDER_COMMA);
  var code = 'blackboard.remove(' + key + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['blackboard_size'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Blackboard'))
        .appendField('.')
        .appendField(createNonEditableField('size'));
    this.setColour(functionColor);
    this.setTooltip('Returns the number of key-value mappings in the blackboard.');
  }
};

Blockly.JavaScript['blackboard_size'] = function(block) {
  var code = blackboardIdentifierForJavaScript + '.size()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['blackboard_size'] = function(block) {
  var code = 'blackboard.size()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

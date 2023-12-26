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
 * @fileoverview FTC robot blocks related to System.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// systemIdentifierForJavaScript
// The following are defined in vars.js:
// functionColor

Blockly.Blocks['system_nanoTime'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('System'))
        .appendField('.')
        .appendField(createNonEditableField('nanoTime'));
    this.setColour(functionColor);
    this.setTooltip('Returns the current value of a high-resolution time source, in nanoseconds.');
    this.getFtcJavaOutputType = function() {
      return 'long';
    };
  }
};

Blockly.JavaScript['system_nanoTime'] = function(block) {
  var code = systemIdentifierForJavaScript + '.nanoTime()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['system_nanoTime'] = function(block) {
  var code = 'System.nanoTime()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['system_currentTimeMillis'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
    .appendField('call')
    .appendField(createNonEditableField('System'))
    .appendField('.')
    .appendField(createNonEditableField('currentTimeMillis'));
    this.setColour(functionColor);
    this.setTooltip('Returns the current time in milliseconds.');
    this.getFtcJavaOutputType = function() {
      return 'long';
    };
  }
};

Blockly.JavaScript['system_currentTimeMillis'] = function(block) {
  var code = systemIdentifierForJavaScript + '.currentTimeMillis()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['system_currentTimeMillis'] = function(block) {
  var code = 'System.currentTimeMillis()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

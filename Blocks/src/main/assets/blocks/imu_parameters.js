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
 * @fileoverview FTC robot blocks related to IMU.Parameters.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// imuParametersIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor

// Functions

Blockly.Blocks['imuParameters_create'] = {
  init: function() {
    this.setOutput(true, 'IMU.Parameters');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('IMU.Parameters'));
    this.appendValueInput('IMU_ORIENTATION_ON_ROBOT').setCheck('ImuOrientationOnRobot')
        .appendField('imuOrientationOnRobot')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new IMU.Parameters object. This block should be plugged into ' +
        'the parameters socket on the "IMU.initialize" block.');
  }
};

Blockly.JavaScript['imuParameters_create'] = function(block) {
  var imuOrientationOnRobot = Blockly.JavaScript.valueToCode(
      block, 'IMU_ORIENTATION_ON_ROBOT', Blockly.JavaScript.ORDER_NONE);
  var code = imuParametersIdentifierForJavaScript + '.create(' + imuOrientationOnRobot + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['imuParameters_create'] = function(block) {
  var imuOrientationOnRobot = Blockly.FtcJava.valueToCode(
      block, 'IMU_ORIENTATION_ON_ROBOT', Blockly.FtcJava.ORDER_NONE);
  var code = 'new IMU.Parameters(' + imuOrientationOnRobot + ')';
  Blockly.FtcJava.generateImport_('IMU');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

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
 * @fileoverview FTC robot blocks related to the Limelight3A Vision Sensor.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// createLimelight3ADropdown
// miscIdentifierForJavaScript
// llStatusIdentifierForJavaScript
// llResultIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor

Blockly.Blocks['limelight3A_start'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('start'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Starts or resumes periodic polling of Limelight data.');
  }
};

Blockly.JavaScript['limelight3A_start'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.start();\n';
};

Blockly.FtcJava['limelight3A_start'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  return identifier + '.start();\n';
};

Blockly.Blocks['limelight3A_pause'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('pause'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Pauses polling of Limelight data.');
  }
};

Blockly.JavaScript['limelight3A_pause'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.pause();\n';
};

Blockly.FtcJava['limelight3A_pause'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  return identifier + '.pause();\n';
};

Blockly.Blocks['limelight3A_stop'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('stop'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Stops polling of Limelight data.');
  }
};

Blockly.JavaScript['limelight3A_stop'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.stop();\n';
};

Blockly.FtcJava['limelight3A_stop'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  return identifier + '.stop();\n';
};

Blockly.Blocks['limelight3A_isRunning'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('isRunning'));
    this.setColour(functionColor);
    this.setTooltip('Checks if the polling is enabled. ' +
                    'Returns true if the polling is enabled, false otherwise.');
  }
};

Blockly.JavaScript['limelight3A_isRunning'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.isRunning()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['limelight3A_isRunning'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  var code = identifier + '.isRunning()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['limelight3A_setPollRateHz'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setPollRateHz'));
    this.appendValueInput('RATE_HZ').setCheck('Number')
        .appendField('rateHz')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the poll rate in Hertz (Hz). Must be called before start. The rate is clamped between 1 and 250 Hz.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'RATE_HZ':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['limelight3A_setPollRateHz'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var rateHz = Blockly.JavaScript.valueToCode(
      block, 'RATE_HZ', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.setPollRateHz(' + rateHz + ');\n';
};

Blockly.FtcJava['limelight3A_setPollRateHz'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  var rateHz = Blockly.FtcJava.valueToCode(
      block, 'RATE_HZ', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.setPollRateHz(' + rateHz + ');\n';
};

Blockly.Blocks['limelight3A_getTimeSinceLastUpdate'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getTimeSinceLastUpdate'));
    this.setColour(functionColor);
    this.setTooltip('Gets the time elapsed, in milliseconds, since the last update.');
    this.getFtcJavaOutputType = function() {
      return 'long';
    };
  }
};

Blockly.JavaScript['limelight3A_getTimeSinceLastUpdate'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.getTimeSinceLastUpdate()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['limelight3A_getTimeSinceLastUpdate'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  var code = identifier + '.getTimeSinceLastUpdate()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['limelight3A_isConnected'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('isConnected'));
    this.setColour(functionColor);
    this.setTooltip('Checks if the Limelight is currently connected. ' +
                    'Returns true if connected, false otherwise.');
  }
};

Blockly.JavaScript['limelight3A_isConnected'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.isConnected()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['limelight3A_isConnected'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  var code = identifier + '.isConnected()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['limelight3A_getLatestResult'] = {
  init: function() {
    this.setOutput(true, 'LLResult');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getLatestResult'));
    this.setColour(functionColor);
    this.setTooltip('Gets the latest result from the Limelight.');
  }
};

Blockly.JavaScript['limelight3A_getLatestResult'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.getLatestResult()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['limelight3A_getLatestResult'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  var code = identifier + '.getLatestResult()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['limelight3A_getStatus'] = {
  init: function() {
    this.setOutput(true, 'LLStatus');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getStatus'));
    this.setColour(functionColor);
    this.setTooltip('Gets the current status of the Limelight.');
  }
};

Blockly.JavaScript['limelight3A_getStatus'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.getStatus()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['limelight3A_getStatus'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  var code = identifier + '.getStatus()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};


Blockly.Blocks['limelight3A_reloadPipeline'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('reloadPipeline'));
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    this.setTooltip('Reloads the current Limelight pipeline. ' +
                    'Returns true if successful, false otherwise.');
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['limelight3A_reloadPipeline'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.reloadPipeline()';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['limelight3A_reloadPipeline'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  var code = identifier + '.reloadPipeline()';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['limelight3A_pipelineSwitch'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('pipelineSwitch'));
    this.appendValueInput('INDEX').setCheck('Number')
        .appendField('index')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    this.setTooltip('Switches to a pipeline at the specified index. ' +
                    'Returns true if successful, false otherwise.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INDEX':
          return 'int';
      }
      return '';
    };
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['limelight3A_pipelineSwitch'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var index = Blockly.JavaScript.valueToCode(
      block, 'INDEX', Blockly.JavaScript.ORDER_NONE);
  var code = identifier + '.pipelineSwitch(' + index + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['limelight3A_pipelineSwitch'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  var index = Blockly.FtcJava.valueToCode(
      block, 'INDEX', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.pipelineSwitch(' + index + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['limelight3A_updatePythonInputs_with8Doubles'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('updatePythonInputs'));
    this.appendValueInput('INPUT1').setCheck('Number')
        .appendField('input1')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('INPUT2').setCheck('Number')
        .appendField('input2')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('INPUT3').setCheck('Number')
        .appendField('input3')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('INPUT4').setCheck('Number')
        .appendField('input4')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('INPUT5').setCheck('Number')
        .appendField('input5')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('INPUT6').setCheck('Number')
        .appendField('input6')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('INPUT7').setCheck('Number')
        .appendField('input7')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('INPUT8').setCheck('Number')
        .appendField('input8')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    this.setTooltip('Updates the Python SnapScript inputs with 8 numeric values' +
                    'Returns true if successful, false otherwise.');
    this.getFtcJavaInputType = function(inputName) {
      return 'double';
    };
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['limelight3A_updatePythonInputs_with8Doubles'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var input1 = Blockly.JavaScript.valueToCode(
      block, 'INPUT1', Blockly.JavaScript.ORDER_COMMA);
  var input2 = Blockly.JavaScript.valueToCode(
      block, 'INPUT2', Blockly.JavaScript.ORDER_COMMA);
  var input3 = Blockly.JavaScript.valueToCode(
      block, 'INPUT3', Blockly.JavaScript.ORDER_COMMA);
  var input4 = Blockly.JavaScript.valueToCode(
      block, 'INPUT4', Blockly.JavaScript.ORDER_COMMA);
  var input5 = Blockly.JavaScript.valueToCode(
      block, 'INPUT5', Blockly.JavaScript.ORDER_COMMA);
  var input6 = Blockly.JavaScript.valueToCode(
      block, 'INPUT6', Blockly.JavaScript.ORDER_COMMA);
  var input7 = Blockly.JavaScript.valueToCode(
      block, 'INPUT7', Blockly.JavaScript.ORDER_COMMA);
  var input8 = Blockly.JavaScript.valueToCode(
      block, 'INPUT8', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.updatePythonInputs_with8Doubles(' +
      input1 + ', ' + input2 + ', ' + input3 + ', ' + input4 + ', ' +
      input5 + ', ' + input6 + ', ' + input7 + ', ' + input8 + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['limelight3A_updatePythonInputs_with8Doubles'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  var input1 = Blockly.FtcJava.valueToCode(
      block, 'INPUT1', Blockly.FtcJava.ORDER_COMMA);
  var input2 = Blockly.FtcJava.valueToCode(
      block, 'INPUT2', Blockly.FtcJava.ORDER_COMMA);
  var input3 = Blockly.FtcJava.valueToCode(
      block, 'INPUT3', Blockly.FtcJava.ORDER_COMMA);
  var input4 = Blockly.FtcJava.valueToCode(
      block, 'INPUT4', Blockly.FtcJava.ORDER_COMMA);
  var input5 = Blockly.FtcJava.valueToCode(
      block, 'INPUT5', Blockly.FtcJava.ORDER_COMMA);
  var input6 = Blockly.FtcJava.valueToCode(
      block, 'INPUT6', Blockly.FtcJava.ORDER_COMMA);
  var input7 = Blockly.FtcJava.valueToCode(
      block, 'INPUT7', Blockly.FtcJava.ORDER_COMMA);
  var input8 = Blockly.FtcJava.valueToCode(
      block, 'INPUT8', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.updatePythonInputs(' +
      input1 + ', ' + input2 + ', ' + input3 + ', ' + input4 + ', ' +
      input5 + ', ' + input6 + ', ' + input7 + ', ' + input8 + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['limelight3A_updatePythonInputs_withArray'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('updatePythonInputs'));
    this.appendValueInput('INPUTS').setCheck('Array')
        .appendField('inputs')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    this.setTooltip('Updates the Python SnapScript inputs with a list of numeric values.' +
                    'Returns true if successful, false otherwise.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INPUTS':
          return 'List<Double>';
      }
      return '';
    };
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['limelight3A_updatePythonInputs_withArray'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var inputs = Blockly.JavaScript.valueToCode(
      block, 'INPUTS', Blockly.JavaScript.ORDER_NONE);
  var code = identifier + '.updatePythonInputs_withArray(JSON.stringify(' + inputs + '))';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['limelight3A_updatePythonInputs_withArray'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  var inputs = Blockly.FtcJava.valueToCode(
      block, 'INPUTS', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.updatePythonInputs(JavaUtil.makeDoubleArray(' + inputs + '))';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['limelight3A_updateRobotOrientation'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('updateRobotOrientation'));
    this.appendValueInput('YAW').setCheck('Number')
        .appendField('yaw')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    this.setTooltip('Updates the robot orientation for MegaTag2. ' +
                    'Returns true if successful, false otherwise.');
    this.getFtcJavaInputType = function(inputName) {
      return 'double';
    };
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['limelight3A_updateRobotOrientation'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var yaw = Blockly.JavaScript.valueToCode(
      block, 'YAW', Blockly.JavaScript.ORDER_NONE);
  var code = identifier + '.updateRobotOrientation(' + yaw + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['limelight3A_updateRobotOrientation'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  var yaw = Blockly.FtcJava.valueToCode(
      block, 'YAW', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.updateRobotOrientation(' + yaw + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['limelight3A_shutdown'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createLimelight3ADropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('shutdown'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Shuts down the Limelight connection and stops all ongoing processes.');
  }
};

Blockly.JavaScript['limelight3A_shutdown'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.shutdown();\n';
};

Blockly.FtcJava['limelight3A_shutdown'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'Limelight3A');
  return identifier + '.shutdown();\n';
};

// LLStatus

Blockly.Blocks['llStatus_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Cid', 'Cid'],
        ['Cpu', 'Cpu'],
        ['FinalYaw', 'FinalYaw'],
        ['Fps', 'Fps'],
        ['HwType', 'HwType'],
        ['PipeImgCount', 'PipeImgCount'],
        ['PipelineIndex', 'PipelineIndex'],
        ['Ram', 'Ram'],
        ['Temp', 'Temp'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('LLStatus'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('LL_STATUS').setCheck('LLStatus')
        .appendField('llStatus')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Cid', 'Returns the camera ID.'],
        ['Cpu', 'Returns the CPU usage as a percentage.'],
        ['FinalYaw', 'Returns the final yaw angle in degrees.'],
        ['Fps', 'Returns the frames per second being processed.'],
        ['HwType', 'Returns the hardware type identifier.'],
        ['PipeImgCount', 'Returns the pipeline image count.'],
        ['PipelineIndex', 'Returns the current pipeline index.'],
        ['Ram', 'Returns the RAM usage as a percentage.'],
        ['Temp', 'Returns the temperature of the Limelight in degrees Celsius'],
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
        case 'Cid':
        case 'HwType':
        case 'PipeImgCount':
        case 'PipelineIndex':
          return 'int';
        default:
          return 'double';
      }
    };
  }
};

Blockly.JavaScript['llStatus_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var jsonFieldName = Blockly.FtcJava.makeFirstLetterLowerCase_(property);
  var llStatus = Blockly.JavaScript.valueToCode(
      block, 'LL_STATUS', Blockly.JavaScript.ORDER_COMMA);
  var code = 'getObjectViaJson(' + miscIdentifierForJavaScript + ',' + llStatus + ').' + jsonFieldName;
  var blockLabel = 'LLStatus.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['llStatus_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var llStatus = Blockly.FtcJava.valueToCode(
      block, 'LL_STATUS', Blockly.FtcJava.ORDER_MEMBER);
  var code = llStatus + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['llStatus_getProperty_String'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Name', 'Name'],
        ['PipelineType', 'PipelineType'],
    ];
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField(createNonEditableField('LLStatus'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('LL_STATUS').setCheck('LLStatus')
        .appendField('llStatus')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Name', 'Returns the name of the Limelight.'],
        ['PipelineType', 'Returns the type of pipeline being used.'],
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

Blockly.JavaScript['llStatus_getProperty_String'] = Blockly.JavaScript['llStatus_getProperty_Number'];
Blockly.FtcJava['llStatus_getProperty_String'] = Blockly.FtcJava['llStatus_getProperty_Number'];

Blockly.Blocks['llStatus_getProperty_Quaternion'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['CameraQuat', 'CameraQuat'],
    ];
    this.setOutput(true, 'Quaternion');
    this.appendDummyInput()
        .appendField(createNonEditableField('LLStatus'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('LL_STATUS').setCheck('LLStatus')
        .appendField('llStatus')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['CameraQuat', 'Returns the camera quaternion of the LLStatus.'],
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

Blockly.JavaScript['llStatus_getProperty_Quaternion'] = function(block) {
  var property = block.getFieldValue('PROP');
  var llStatus = Blockly.JavaScript.valueToCode(
      block, 'LL_STATUS', Blockly.JavaScript.ORDER_NONE);
  var code = llStatusIdentifierForJavaScript + '.get' + property + '(' + llStatus + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['llStatus_getProperty_Quaternion'] = Blockly.FtcJava['llStatus_getProperty_Number'];

Blockly.Blocks['llStatus_toText'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('LLStatus'))
        .appendField('.')
        .appendField(createNonEditableField('toText'));
    this.appendValueInput('LL_STATUS').setCheck('LLStatus')
        .appendField('llStatus')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a text representation of the given LLStatus.');
  }
};

Blockly.JavaScript['llStatus_toText'] = function(block) {
  var llStatus = Blockly.JavaScript.valueToCode(
      block, 'LL_STATUS', Blockly.JavaScript.ORDER_NONE);
  var code = llStatusIdentifierForJavaScript + '.toText(' + llStatus + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['llStatus_toText'] = function(block) {
  var llStatus = Blockly.FtcJava.valueToCode(
      block, 'LL_STATUS', Blockly.FtcJava.ORDER_MEMBER);
  var code = llStatus + '.toString()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// LLResult

Blockly.Blocks['llResult_getProperty_Array'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['PythonOutput', 'PythonOutput'],
        ['FiducialResults', 'FiducialResults'],
        ['ColorResults', 'ColorResults'],
    ];
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField(createNonEditableField('LLResult'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('LL_RESULT').setCheck('LLResult')
        .appendField('llResult')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
      ['PythonOutput', 'Returns the user-specified python snapscript output data, as a list of numeric values.'],
      ['FiducialResults', 'Returns the list of fiducial/apriltag results.'],
      ['ColorResults', 'Returns the list of color results.'],
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
        case 'PythonOutput':
          return 'List<Double>';
        case 'FiducialResults':
          return 'List<LLResultTypes.FiducialResult>';
        case 'ColorResults':
          return 'List<LLResultTypes.ColorResult>';
        default:
          throw 'Unexpected property ' + property + ' (llStatus_getProperty_Array getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['llResult_getProperty_Array'] = function(block) {
  var property = block.getFieldValue('PROP');
  var llResult = Blockly.JavaScript.valueToCode(
      block, 'LL_RESULT', Blockly.JavaScript.ORDER_COMMA);
  var code = 'getObjectViaJson(' + miscIdentifierForJavaScript + ',' + llResult + ').' + property;
  var blockLabel = 'LLResult.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['llResult_getProperty_Array'] = function(block) {
  var property = block.getFieldValue('PROP');
  var llResult = Blockly.FtcJava.valueToCode(
      block, 'LL_RESULT', Blockly.FtcJava.ORDER_MEMBER);
  var code = llResult + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['llResult_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['ControlHubTimeStamp', 'ControlHubTimeStamp'],
        ['ControlHubTimeStampNanos', 'ControlHubTimeStampNanos'],
        ['Staleness', 'Staleness'],
        ['FocusMetric', 'FocusMetric'],
        ['BotposeTagCount', 'BotposeTagCount'],
        ['BotposeSpan', 'BotposeSpan'],
        ['BotposeAvgDist', 'BotposeAvgDist'],
        ['BotposeAvgArea', 'BotposeAvgArea'],
        ['CaptureLatency', 'CaptureLatency'],
        ['Tx', 'Tx'],
        ['Ty', 'Ty'],
        ['TxNC', 'TxNC'],
        ['TyNC', 'TyNC'],
        ['Ta', 'Ta'],
        ['PipelineIndex', 'PipelineIndex'],
        ['TargetingLatency', 'TargetingLatency'],
        ['Timestamp', 'Timestamp'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('LLResult'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('LL_RESULT').setCheck('LLResult')
        .appendField('llResult')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['ControlHubTimeStamp', 'Returns the control hub timestamp in milliseconds.'],
        ['ControlHubTimeStampNanos', 'Returns the control hub timestamp in nanoseconds.'],
        ['Staleness', 'Returns the staleness of the data in milliseconds.'],
        ['FocusMetric', 'Returns the focus metric of the image. This is only valid if the focus pipeline is enabled.'],
        ['BotposeTagCount', 'Returns the number of tags used in the botpose calculation.'],
        ['BotposeSpan', 'Returns the span of tags used in the botpose calculation in meters.'],
        ['BotposeAvgDist', 'Returns the average distance of tags used in the botpose calculation in meters.'],
        ['BotposeAvgArea', 'Returns the average area of tags used in the botpose calculation.'],
        ['CaptureLatency', 'Returns the current capture latency in milliseconds.'],
        ['Tx', 'Returns the horizontal angular offset of the primary target in degrees from the crosshair. '],
        ['Ty', 'Returns the vertical angular offset of the primary target in degrees from the crosshair.'],
        ['TxNC', 'Returns the horizontal angular offset of the primary target in degrees from the principal pixel instead of the crosshair.'],
        ['TyNC', 'Returns the vertical angular offset of the primary target in degrees from the principal pixel instead of the crosshair.'],
        ['Ta', 'Returns the area of the target as a percentage of the image area.'],
        ['PipelineIndex', 'Returns the index of the currently active pipeline.'],
        ['TargetingLatency', 'Returns the targeting/pipeline latency in milliseconds.'],
        ['Timestamp', 'Returns the Limelight-local monotonic timestamp of the result.'],
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
        case 'BotposeTagCount':
        case 'PipelineIndex':
          return 'int';
        case 'ControlHubTimeStamp':
        case 'ControlHubTimeStampNanos':
        case 'Staleness':
          return 'long';
        default:
          return 'double';
      }
    };
  }
};

Blockly.JavaScript['llResult_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  // Staleness should always call back into java because the implementation calls System.currentTimeMillis().
  if (property == 'Staleness') {
    var llResult = Blockly.JavaScript.valueToCode(
        block, 'LL_RESULT', Blockly.JavaScript.ORDER_NONE);
    var code = llResultIdentifierForJavaScript + '.getStaleness(' + llResult + ')';
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    var llResult = Blockly.JavaScript.valueToCode(
        block, 'LL_RESULT', Blockly.JavaScript.ORDER_COMMA);
    var code = 'getObjectViaJson(' + miscIdentifierForJavaScript + ',' + llResult + ').' + property;
    var blockLabel = 'LLResult.' + block.getField('PROP').getText();
    return wrapJavaScriptCode(code, blockLabel);
  }
};

Blockly.FtcJava['llResult_getProperty_Number'] = Blockly.FtcJava['llResult_getProperty_Array'];

Blockly.Blocks['llResult_getProperty_Pose3D'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Botpose', 'Botpose'],
        ['Botpose_MT2','Botpose_MT2'],
    ];
    this.setOutput(true, 'Pose3D');
    this.appendDummyInput()
        .appendField(createNonEditableField('LLResult'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('LL_RESULT').setCheck('LLResult')
        .appendField('llResult')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Botpose', 'Returns the 3D bot pose.'],
        ['Botpose_MT2', 'Returns the 3D bot pose using MegaTag2. ' +
            'You must set the orientation of the robot with your imu for this to work.'],
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

Blockly.JavaScript['llResult_getProperty_Pose3D'] = Blockly.JavaScript['llResult_getProperty_Array'];

Blockly.FtcJava['llResult_getProperty_Pose3D'] = Blockly.FtcJava['llResult_getProperty_Array'];

Blockly.Blocks['llResult_getProperty_String'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['PipelineType', 'PipelineType'],
    ];
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField(createNonEditableField('LLResult'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('LL_RESULT').setCheck('LLResult')
        .appendField('llResult')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['PipelineType', 'Returns the type of the current pipeline.'],
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

Blockly.JavaScript['llResult_getProperty_String'] = Blockly.JavaScript['llResult_getProperty_Array'];

Blockly.FtcJava['llResult_getProperty_String'] = Blockly.FtcJava['llResult_getProperty_Array'];

Blockly.Blocks['llResult_getProperty_Boolean'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['IsValid', 'IsValid'],
    ];
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField(createNonEditableField('LLResult'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('LL_RESULT').setCheck('LLResult')
        .appendField('llResult')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['IsValid', 'Returns true if the given LLResult is valid.'],
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

Blockly.JavaScript['llResult_getProperty_Boolean'] = Blockly.JavaScript['llResult_getProperty_Array'];

Blockly.FtcJava['llResult_getProperty_Boolean'] = function(block) {
  var property = block.getFieldValue('PROP');
  var llResult = Blockly.FtcJava.valueToCode(
      block, 'LL_RESULT', Blockly.FtcJava.ORDER_MEMBER);
  var code;
  if (property == 'IsValid') {
    code= llResult + '.isValid()';
  } else {
    code = llResult + '.get' + property + '()';
  }
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['llResult_toText'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('LLResult'))
        .appendField('.')
        .appendField(createNonEditableField('toText'));
    this.appendValueInput('LL_RESULT').setCheck('LLResult')
        .appendField('llResult')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a text representation of the given LLResult.');
  }
};

Blockly.JavaScript['llResult_toText'] = function(block) {
  var llResult = Blockly.JavaScript.valueToCode(
      block, 'LL_RESULT', Blockly.JavaScript.ORDER_NONE);
  var code = llResultIdentifierForJavaScript + '.toText(' + llResult + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['llResult_toText'] = function(block) {
  var llResult = Blockly.FtcJava.valueToCode(
      block, 'LL_RESULT', Blockly.FtcJava.ORDER_MEMBER);
  var code = llResult + '.toString()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// LLResultTypes.FiducialResult

Blockly.Blocks['llResultTypesFiducialResult_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['FiducialId', 'FiducialId'],
        ['TargetArea', 'TargetArea'],
        ['TargetXPixels', 'TargetXPixels'],
        ['TargetYPixels', 'TargetYPixels'],
        ['TargetXDegrees', 'TargetXDegrees'],
        ['TargetYDegrees', 'TargetYDegrees'],
        ['TargetXDegreesNoCrosshair', 'TargetXDegreesNoCrosshair'],
        ['TargetYDegreesNoCrosshair', 'TargetYDegreesNoCrosshair'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('FiducialResult'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('FIDUCIAL_RESULT').setCheck('LLResultTypes.FiducialResult')
        .appendField('fiducialResult')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['FiducialId', 'Returns the ID of the fiducial.'],
        ['TargetArea', 'Returns the area of the detected fiducial as a percentage of the image.'],
        ['TargetXPixels', 'Returns the current tx in pixels from the crosshair.'],
        ['TargetYPixels', 'Returns the current ty in pixels from the crosshair.'],
        ['TargetXDegrees', 'Returns the current tx in degrees from the crosshair.'],
        ['TargetYDegrees', 'Returns the current ty in degrees from the crosshair.'],
        ['TargetXDegreesNoCrosshair', 'Returns the current tx in degrees from the principal pixel.'],
        ['TargetYDegreesNoCrosshair', 'Returns the current ty in degrees from the principal pixel.'],
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
        case 'FiducialId':
          return 'int';
        default:
          return 'double';
      }
    };
  }
};

Blockly.JavaScript['llResultTypesFiducialResult_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var jsonFieldName = Blockly.FtcJava.makeFirstLetterLowerCase_(property);
  var fiducialResult = Blockly.JavaScript.valueToCode(
      block, 'FIDUCIAL_RESULT', Blockly.JavaScript.ORDER_MEMBER);
  var code = fiducialResult + '.' + jsonFieldName;
  var blockLabel = 'FiducialResult.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['llResultTypesFiducialResult_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var fiducialResult = Blockly.FtcJava.valueToCode(
      block, 'FIDUCIAL_RESULT', Blockly.FtcJava.ORDER_MEMBER);
  var code = fiducialResult + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['llResultTypesFiducialResult_getProperty_Pose3D'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['CameraPoseTargetSpace', 'CameraPoseTargetSpace'],
        ['RobotPoseFieldSpace', 'RobotPoseFieldSpace'],
        ['RobotPoseTargetSpace', 'RobotPoseTargetSpace'],
        ['TargetPoseCameraSpace', 'TargetPoseCameraSpace'],
        ['TargetPoseRobotSpace', 'TargetPoseRobotSpace'],
    ];
    this.setOutput(true, 'Pose3D');
    this.appendDummyInput()
        .appendField(createNonEditableField('FiducialResult'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('FIDUCIAL_RESULT').setCheck('LLResultTypes.FiducialResult')
        .appendField('fiducialResult')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['CameraPoseTargetSpace', 'Returns the camera pose in target space.'],
        ['RobotPoseFieldSpace', 'Returns the robot pose in field space based on this fiducial/apriltag alone.'],
        ['RobotPoseTargetSpace', 'Returns the robot pose in target space.'],
        ['TargetPoseCameraSpace', 'Returns the target pose in camera space.'],
        ['TargetPoseRobotSpace', 'Returns the target pose in robot space.'],
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

Blockly.JavaScript['llResultTypesFiducialResult_getProperty_Pose3D'] = Blockly.JavaScript['llResultTypesFiducialResult_getProperty_Number'];

Blockly.FtcJava['llResultTypesFiducialResult_getProperty_Pose3D'] = Blockly.FtcJava['llResultTypesFiducialResult_getProperty_Number'];

Blockly.Blocks['llResultTypesFiducialResult_getProperty_String'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Family', 'Family'],
    ];
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField(createNonEditableField('FiducialResult'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('FIDUCIAL_RESULT').setCheck('LLResultTypes.FiducialResult')
        .appendField('fiducialResult')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Family', 'Returns the family of the fiducial (eg 36h11).'],
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

Blockly.JavaScript['llResultTypesFiducialResult_getProperty_String'] = Blockly.JavaScript['llResultTypesFiducialResult_getProperty_Number'];

Blockly.FtcJava['llResultTypesFiducialResult_getProperty_String'] = Blockly.FtcJava['llResultTypesFiducialResult_getProperty_Number'];

Blockly.Blocks['llResultTypesFiducialResult_getProperty_Array'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['TargetCorners', 'TargetCorners'],
    ];
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField(createNonEditableField('FiducialResult'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('FIDUCIAL_RESULT').setCheck('LLResultTypes.FiducialResult')
        .appendField('fiducialResult')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
      ['TargetCorners', 'Returns the four corner points of the detected fiducial/apriltag. ' +
                        'Each point is represented as a list of two doubles [x, y]'],
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
        case 'TargetCorners':
          return 'List<List<Double>>';
        default:
          throw 'Unexpected property ' + property + ' (llResultTypesFiducialResult_getProperty_Array getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['llResultTypesFiducialResult_getProperty_Array'] = Blockly.JavaScript['llResultTypesFiducialResult_getProperty_Number'];

Blockly.FtcJava['llResultTypesFiducialResult_getProperty_Array'] = Blockly.FtcJava['llResultTypesFiducialResult_getProperty_Number'];

// LLResultTypes.ColorResult

Blockly.Blocks['llResultTypesColorResult_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['TargetArea', 'TargetArea'],
        ['TargetXPixels', 'TargetXPixels'],
        ['TargetYPixels', 'TargetYPixels'],
        ['TargetXDegrees', 'TargetXDegrees'],
        ['TargetYDegrees', 'TargetYDegrees'],
        ['TargetXDegreesNoCrosshair', 'TargetXDegreesNoCrosshair'],
        ['TargetYDegreesNoCrosshair', 'TargetYDegreesNoCrosshair'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('ColorResult'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('COLOR_RESULT').setCheck('LLResultTypes.ColorResult')
        .appendField('colorResult')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['TargetArea', 'Returns the area of the detected color target as a percentage of the image.'],
        ['TargetXPixels', 'Returns the current tx in pixels from the crosshair.'],
        ['TargetYPixels', 'Returns the current ty in pixels from the crosshair.'],
        ['TargetXDegrees', 'Returns the current tx in degrees from the crosshair.'],
        ['TargetYDegrees', 'Returns the current ty in degrees from the crosshair.'],
        ['TargetXDegreesNoCrosshair', 'Returns the current tx in degrees from the principal pixel.'],
        ['TargetYDegreesNoCrosshair', 'Returns the current ty in degrees from the principal pixel.'],
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
      return 'double';
    };
  }
};

Blockly.JavaScript['llResultTypesColorResult_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var jsonFieldName = Blockly.FtcJava.makeFirstLetterLowerCase_(property);
  var colorResult = Blockly.JavaScript.valueToCode(
      block, 'COLOR_RESULT', Blockly.JavaScript.ORDER_MEMBER);
  var code = colorResult + '.' + jsonFieldName;
  var blockLabel = 'ColorResult.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['llResultTypesColorResult_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var colorResult = Blockly.FtcJava.valueToCode(
      block, 'COLOR_RESULT', Blockly.FtcJava.ORDER_MEMBER);
  var code = colorResult + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['llResultTypesColorResult_getProperty_Pose3D'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['CameraPoseTargetSpace', 'CameraPoseTargetSpace'],
        ['RobotPoseFieldSpace', 'RobotPoseFieldSpace'],
        ['RobotPoseTargetSpace', 'RobotPoseTargetSpace'],
        ['TargetPoseCameraSpace', 'TargetPoseCameraSpace'],
        ['TargetPoseRobotSpace', 'TargetPoseRobotSpace'],
    ];
    this.setOutput(true, 'Pose3D');
    this.appendDummyInput()
        .appendField(createNonEditableField('ColorResult'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('COLOR_RESULT').setCheck('LLResultTypes.ColorResult')
        .appendField('colorResult')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['CameraPoseTargetSpace', 'Returns the camera pose in target space.'],
        ['RobotPoseFieldSpace', 'Returns the robot pose in field space based on this color target.'],
        ['RobotPoseTargetSpace', 'Returns the robot pose in target space.'],
        ['TargetPoseCameraSpace', 'Returns the target pose in camera space.'],
        ['TargetPoseRobotSpace', 'Returns the target pose in robot space.'],
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

Blockly.JavaScript['llResultTypesColorResult_getProperty_Pose3D'] = Blockly.JavaScript['llResultTypesColorResult_getProperty_Number'];

Blockly.FtcJava['llResultTypesColorResult_getProperty_Pose3D'] = Blockly.FtcJava['llResultTypesColorResult_getProperty_Number'];

Blockly.Blocks['llResultTypesColorResult_getProperty_Array'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['TargetCorners', 'TargetCorners'],
    ];
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField(createNonEditableField('ColorResult'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('COLOR_RESULT').setCheck('LLResultTypes.ColorResult')
        .appendField('colorResult')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
      ['TargetCorners', 'Returns the corner points of the detected target. The number of corners is not fixed. ' +
                        'Each point is represented as a list of two doubles [x, y]'],
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
        case 'TargetCorners':
          return 'List<List<Double>>';
        default:
          throw 'Unexpected property ' + property + ' (llResultTypesColorResult_getProperty_Array getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['llResultTypesColorResult_getProperty_Array'] = Blockly.JavaScript['llResultTypesColorResult_getProperty_Number'];

Blockly.FtcJava['llResultTypesColorResult_getProperty_Array'] = Blockly.FtcJava['llResultTypesColorResult_getProperty_Number'];

// Pose3D

Blockly.Blocks['pose3D_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['position.x', 'position.x'],
        ['position.y', 'position.y'],
        ['position.z', 'position.z'],
        ['orientation.yaw', 'orientation.yaw'],
        ['orientation.pitch', 'orientation.pitch'],
        ['orientation.roll', 'orientation.roll'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('Pose3D'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('POSE_3D').setCheck('Pose3D')
        .appendField('pose')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['position.x', 'Returns the x value of the pose.'],
        ['position.y', 'Returns the y value of the pose.'],
        ['position.z', 'Returns the z value of the pose.'],
        ['orientation.yaw', 'Returns the yaw value of the pose.'],
        ['orientation.pitch', 'Returns the pitch value of the pose.'],
        ['orientation.roll', 'Returns the roll value of the pose.'],
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
      return 'double';
    };
  }
};

Blockly.JavaScript['pose3D_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var pose3D = Blockly.JavaScript.valueToCode(
      block, 'POSE_3D', Blockly.JavaScript.ORDER_MEMBER);
  var code = pose3D + '.' + property;
  var blockLabel = 'Pose3D.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['pose3D_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var pose3D = Blockly.FtcJava.valueToCode(
      block, 'POSE_3D', Blockly.FtcJava.ORDER_MEMBER);
  var parts = property.split('.');
  var code = pose3D + '.get' + Blockly.FtcJava.makeFirstLetterUpperCase_(parts[0]) + '()';
  var order;
  switch (parts[0]) {
    case 'position':
      code += '.' + parts[1];
      order = Blockly.FtcJava.ORDER_MEMBER;
      break;
    case 'orientation':
      code += '.get' + Blockly.FtcJava.makeFirstLetterUpperCase_(parts[1]) + '()';
      order = Blockly.FtcJava.ORDER_FUNCTION_CALL;
      break;
  }
  return [code, order];
};

Blockly.Blocks['pose3D_toText'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Pose3D'))
        .appendField('.')
        .appendField(createNonEditableField('toText'));
    this.appendValueInput('POSE_3D').setCheck('Pose3D')
        .appendField('pose3D')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a text representation of the given Pose3D.');
  }
};

Blockly.JavaScript['pose3D_toText'] = function(block) {
  var pose3D = Blockly.JavaScript.valueToCode(
      block, 'POSE_3D', Blockly.JavaScript.ORDER_NONE);
  var code = llResultIdentifierForJavaScript + '.pose3DToText(JSON.stringify(' + pose3D + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['pose3D_toText'] = function(block) {
  var pose3D = Blockly.FtcJava.valueToCode(
      block, 'POSE_3D', Blockly.FtcJava.ORDER_MEMBER);
  var code = pose3D + '.toString()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

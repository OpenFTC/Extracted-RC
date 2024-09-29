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
 * @fileoverview FTC robot blocks related to the MaxBotix MaxSonarI2CXL.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// createMaxSonarI2CXLDropdown
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor
// setPropertyColor

Blockly.Blocks['maxSonarI2CXL_setProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['I2cAddress7Bit', 'I2cAddress7Bit'],
        ['I2cAddress8Bit', 'I2cAddress8Bit'],
    ];
    this.appendValueInput('VALUE').setCheck('Number')
        .appendField('set')
        .appendField(createMaxSonarI2CXLDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP')
        .appendField('to');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['I2cAddress7Bit', 'Sets the 7 bit I2C address of the MaxSonar I2CXL sensor.'],
        ['I2cAddress8Bit', 'Sets the 8 bit I2C address of the MaxSonar I2CXL sensor.'],
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
    this.getFtcJavaInputType = function(inputName) {
      if (inputName == 'VALUE') {
        var property = thisBlock.getFieldValue('PROP');
        switch (property) {
          case 'I2cAddress7Bit':
          case 'I2cAddress8Bit':
            return 'int';
          default:
            throw 'Unexpected property ' + property + ' (maxSonarI2CXL_setProperty_Number getArgumentType).';
        }
      }
      return '';
    };
  }
};

Blockly.JavaScript['maxSonarI2CXL_setProperty_Number'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.set' + property + '(' + value + ');\n';
};

Blockly.FtcJava['maxSonarI2CXL_setProperty_Number'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'MaxSonarI2CXL');
  var property = block.getFieldValue('PROP');
  var value = Blockly.FtcJava.valueToCode(
      block, 'VALUE', Blockly.FtcJava.ORDER_NONE);
  var code;
  switch (property) {
    case 'I2cAddress7Bit':
      Blockly.FtcJava.generateImport_('I2cAddr');
      code = identifier + '.setI2cAddress(I2cAddr.create7bit(' + value + '));\n';
      break;
    case 'I2cAddress8Bit':
      Blockly.FtcJava.generateImport_('I2cAddr');
      code = identifier + '.setI2cAddress(I2cAddr.create8bit(' + value + '));\n';
      break;
    default:
      throw 'Unexpected property ' + property + ' (maxSonarI2CXL_setProperty_Number).';
  }
  return code;
};

Blockly.Blocks['maxSonarI2CXL_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['I2cAddress7Bit', 'I2cAddress7Bit'],
        ['I2cAddress8Bit', 'I2cAddress8Bit'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createMaxSonarI2CXLDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['I2cAddress7Bit', 'Returns the 7 bit I2C address of the MaxSonar I2CXL sensor.'],
        ['I2cAddress8Bit', 'Returns the 8 bit I2C address of the MaxSonar I2CXL sensor.'],
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
        case 'I2cAddress7Bit':
        case 'I2cAddress8Bit':
          return 'int';
        default:
          throw 'Unexpected property ' + property + ' (maxSonarI2CXL_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['maxSonarI2CXL_getProperty_Number'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['maxSonarI2CXL_getProperty_Number'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'MaxSonarI2CXL');
  var property = block.getFieldValue('PROP');
  var code;
  switch (property) {
    case 'I2cAddress7Bit':
      code = identifier + '.getI2cAddress().get7Bit()';
      break;
    case 'I2cAddress8Bit':
      code = identifier + '.getI2cAddress().get8Bit()';
      break;
    default:
      throw 'Unexpected property ' + property + ' (maxSonarI2CXL_getProperty_Number).';
  }
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['maxSonarI2CXL_getDistanceSync'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createMaxSonarI2CXLDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getDistanceSync'));
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Commands the sensor to send out a ping, sleeps for the default sonar propagation ' +
        'delay, and then reads the result of the measurement just commanded. Returns the result of ' +
        'the range measurement that was performed.');
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['maxSonarI2CXL_getDistanceSync'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_NONE);
  var code = identifier + '.getDistanceSync(' + distanceUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['maxSonarI2CXL_getDistanceSync'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'MaxSonarI2CXL');
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.getDistanceSync(' + distanceUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};


Blockly.Blocks['maxSonarI2CXL_getDistanceSync_withDelay'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createMaxSonarI2CXLDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getDistanceSync_withDelay'));
    this.appendValueInput('DELAY').setCheck('Number')
        .appendField('sonarPropagationDelayMs')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Commands the sensor to send out a ping, sleeps for the time specified by ' +
        'sonarPropagationDelay, and then reads the result of the measurement just commanded. ' +
        'Returns the result of the range measurement that was performed.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'DELAY':
          return 'int';
      }
      return '';
    };
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['maxSonarI2CXL_getDistanceSync_withDelay'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var delay = Blockly.JavaScript.valueToCode(
      block, 'DELAY', Blockly.JavaScript.ORDER_COMMA);
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.getDistanceSync_withDelay(' + delay + ', ' + distanceUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['maxSonarI2CXL_getDistanceSync_withDelay'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'MaxSonarI2CXL');
  var delay = Blockly.FtcJava.valueToCode(
      block, 'DELAY', Blockly.FtcJava.ORDER_COMMA);
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.getDistanceSync_withDelay(' + delay + ', ' + distanceUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['maxSonarI2CXL_getDistanceAsync'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createMaxSonarI2CXLDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getDistanceAsync'));
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Commands the sensor to send out a ping if the default delay has expired, and ' +
        'then reads the result of the measurement just commanded. If the delay has NOT expired, ' +
        'then the last value read from the sensor will be returned instead. Returns the result ' +
        'of the range measurement that was performed, or, if the default delay has not yet ' +
        'expired, the last value that was read from the sensor.');
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['maxSonarI2CXL_getDistanceAsync'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_NONE);
  var code = identifier + '.getDistanceAsync(' + distanceUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['maxSonarI2CXL_getDistanceAsync'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'MaxSonarI2CXL');
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.getDistanceAsync(' + distanceUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['maxSonarI2CXL_getDistanceAsync_withDelay'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createMaxSonarI2CXLDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getDistanceAsync_withDelay'));
    this.appendValueInput('DELAY').setCheck('Number')
        .appendField('sonarPropagationDelayMs')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Commands the sensor to send out a ping if the delay specified has expired, and ' +
        'then reads the result of the measurement just commanded. If the delay has NOT ' +
        'expired, then the last value read from the sensor will be returned instead. ' +
        'Returns the result of the range measurement that was performed, or, if the delay ' +
        'specified by sonarPropagationDelay has not yet expired, the last value that was ' +
        'read from the sensor.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'DELAY':
          return 'int';
      }
      return '';
    };
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['maxSonarI2CXL_getDistanceAsync_withDelay'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var delay = Blockly.JavaScript.valueToCode(
      block, 'DELAY', Blockly.JavaScript.ORDER_COMMA);
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.getDistanceAsync_withDelay(' + delay + ', ' + distanceUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['maxSonarI2CXL_getDistanceAsync_withDelay'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'MaxSonarI2CXL');
  var delay = Blockly.FtcJava.valueToCode(
      block, 'DELAY', Blockly.FtcJava.ORDER_COMMA);
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.getDistanceAsync_withDelay(' + delay + ', ' + distanceUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['maxSonarI2CXL_writeI2cAddrToSensorEEPROM'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createMaxSonarI2CXLDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('writeI2cAddrToSensorEEPROM'));
    this.appendValueInput('ADDRESS').setCheck('Number')
        .appendField('address')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Tells the sensor that it should operate on an I2C address other than the ' +
		    'default, and then writes that address to the internal EEPROM so that it is retained ' +
		    'across a power cycle. ' +
		    'You only need to call this method ONE TIME, EVER. (Unless you want to change the address ' +
		    'again, that is.) ' +
		    'Note that the sensor will only accept even numbered address values. If an odd numbered ' +
		    'address is sent, then the address will be set to the next lowest even number. Also, the ' +
		    'following addresses are invalid, and if sent, the command will be ignored: 0x00 (0), ' +
		    '0x50 (80), 0xA4 (164), 0xAA (170).');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'ADDRESS':
          return 'byte';
      }
      return '';
    };
  }
};

Blockly.JavaScript['maxSonarI2CXL_writeI2cAddrToSensorEEPROM'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var address = Blockly.JavaScript.valueToCode(
      block, 'ADDRESS', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.writeI2cAddrToSensorEEPROM(' + address + ');\n';
};

Blockly.FtcJava['maxSonarI2CXL_writeI2cAddrToSensorEEPROM'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'MaxSonarI2CXL');
  var address = Blockly.FtcJava.valueToCode(
      block, 'ADDRESS', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.writeI2cAddrToSensorEEPROM(' + address + ');\n';
};

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
 * @fileoverview FTC robot blocks related to the AndyMark Color Sensor.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// createAndyMarkColorSensorDropdown
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor
// setPropertyColor


// Properties

Blockly.Blocks['andyMarkColorSensor_setProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['I2cAddress7Bit', 'I2cAddress7Bit'],
        ['I2cAddress8Bit', 'I2cAddress8Bit'],
    ];
    this.appendValueInput('VALUE').setCheck('Number')
        .appendField('set')
        .appendField(createAndyMarkColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP')
        .appendField('to');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['I2cAddress7Bit', 'Sets the 7 bit I2C address for the sensor.'],
        ['I2cAddress8Bit', 'Sets the 8 bit I2C address for the sensor.'],
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
            throw 'Unexpected property ' + property + ' (andyMarkColorSensor_setProperty_Number getArgumentType).';
        }
      }
      return '';
    };
  }
};

Blockly.JavaScript['andyMarkColorSensor_setProperty_Number'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.set' + property + '(' + value + ');\n';
};

Blockly.FtcJava['andyMarkColorSensor_setProperty_Number'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'ColorSensor');
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
      throw 'Unexpected property ' + property + ' (andyMarkColorSensor_setProperty_Number).';
  }
  return code;
};

Blockly.Blocks['andyMarkColorSensor_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Alpha', 'Alpha'],
        ['Argb', 'Argb'],
        ['Red', 'Red'],
        ['Green', 'Green'],
        ['Blue', 'Blue'],
        ['LightDetected', 'LightDetected'],
        ['I2cAddress7Bit', 'I2cAddress7Bit'],
        ['I2cAddress8Bit', 'I2cAddress8Bit'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createAndyMarkColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Alpha', 'Returns the ambient (clear) light level from the sensor, in the range 0-65535.'],
        ['Argb', 'Returns an ARGB value representing the color detected by the sensor.'],
        ['Red', 'Returns the red color component from the sensor, in the range 0-65535.'],
        ['Green', 'Returns the green color component from the sensor, in the range 0-65535.'],
        ['Blue', 'Returns the blue color component from the sensor, in the range 0-65535.'],
        ['LightDetected', 'Returns the light detected by the sensor, normalized to a [0.0, 1.0] range.'],
        ['I2cAddress7Bit', 'Returns the 7 bit I2C address currently assigned to the sensor.'],
        ['I2cAddress8Bit', 'Returns the 8 bit I2C address currently assigned to the sensor.'],
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
        case 'Alpha':
        case 'Argb':
        case 'Blue':
        case 'Green':
        case 'Red':
        case 'I2cAddress7Bit':
        case 'I2cAddress8Bit':
          return 'int';
        case 'LightDetected':
          return 'double';
        default:
          throw 'Unexpected property ' + property + ' (andyMarkColorSensor_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['andyMarkColorSensor_getProperty_Number'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['andyMarkColorSensor_getProperty_Number'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'ColorSensor');
  var property = block.getFieldValue('PROP');
  var code;
  switch (property) {
    case 'Alpha':
    case 'Argb':
    case 'Red':
    case 'Green':
    case 'Blue':
      code = identifier + '.' + Blockly.FtcJava.makeFirstLetterLowerCase_(property) + '()';
      break;
    case 'LightDetected':
      code = identifier + '.get' + property + '()';
      break;
    case 'I2cAddress7Bit':
      code = identifier + '.getI2cAddress().get7Bit()';
      break;
    default:
      throw 'Unexpected property ' + property + ' (andyMarkColorSensor_getProperty_Number).';
  }
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};


// Functions

Blockly.Blocks['andyMarkColorSensor_getDistance'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createAndyMarkColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getDistance'));
    this.appendValueInput('UNIT').setCheck('DistanceUnit')
        .appendField('unit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Returns a calibrated, linear sense of distance as read by the sensor. Distance is ' +
        'measured to the plastic housing at the front of the sensor.');
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['andyMarkColorSensor_getDistance'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var unit = Blockly.JavaScript.valueToCode(
      block, 'UNIT', Blockly.JavaScript.ORDER_NONE);
  var code = identifier + '.getDistance(' + unit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['andyMarkColorSensor_getDistance'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'ColorSensor');
  var unit = Blockly.FtcJava.valueToCode(
      block, 'UNIT', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.getDistance(' + unit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['andyMarkColorSensor_getNormalizedColors'] = {
  init: function() {
    this.setOutput(true, 'NormalizedRGBA');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createAndyMarkColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getNormalizedColors'));
    this.setColour(functionColor);
    this.setTooltip('Returns a NormalizedRGBA object representing the color detected by the sensor.');
  }
};

Blockly.JavaScript['andyMarkColorSensor_getNormalizedColors'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = 'JSON.parse(' + identifier + '.getNormalizedColors())';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['andyMarkColorSensor_getNormalizedColors'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'ColorSensor');
  var code = identifier + '.getNormalizedColors()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['andyMarkColorSensor_setProximityGain'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createAndyMarkColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setProximityGain'));
    this.appendValueInput('GAIN').setCheck('AndyMarkColorSensor.ProximityGain')
        .appendField('gain')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Sets the proximity gain used by the TMD3725 sensor during proximity measurements. ' +
        'Proximity gain controls the signal amplification applied to the proximity photodiode ' +
        'before analog-to-digital conversion. Higher gain values increase sensitivity to distant ' +
        'or low-reflectivity targets, but may also saturate more easily at close range.');
  }
};

Blockly.JavaScript['andyMarkColorSensor_setProximityGain'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var gain = Blockly.JavaScript.valueToCode(
      block, 'GAIN', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setProximityGain(' + gain + ');\n';
};

Blockly.FtcJava['andyMarkColorSensor_setProximityGain'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'AndyMarkColorSensor');
  var gain = Blockly.FtcJava.valueToCode(
      block, 'GAIN', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setProximityGain(' + gain + ');\n';
};

Blockly.Blocks['andyMarkColorSensor_setProximityLedPulses'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createAndyMarkColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setProximityLedPulses'));
    this.appendValueInput('PULSES').setCheck('Number')
        .appendField('pulses')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Sets the number of IR LED pulses emitted during a single proximity measurement. ' +
        'Valid pulse counts are from 1 to 64. ' +
        'Increasing the number of pulses increases the total emitted IR energy per cycle, ' +
        'improving signal-to-noise ratio and extending effective measurement range. However, ' +
        'using more pulses increases measurement time and power consumption.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'PULSES':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['andyMarkColorSensor_setProximityLedPulses'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var pulses = Blockly.JavaScript.valueToCode(
      block, 'PULSES', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setProximityLedPulses(' + pulses + ');\n';
};

Blockly.FtcJava['andyMarkColorSensor_setProximityLedPulses'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'AndyMarkColorSensor');
  var pulses = Blockly.FtcJava.valueToCode(
      block, 'PULSES', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setProximityLedPulses(' + pulses + ');\n';
};

Blockly.Blocks['andyMarkColorSensor_setProximityLedPulseLength'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createAndyMarkColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setProximityLedPulseLength'));
    this.appendValueInput('PULSE_LENGTH').setCheck('AndyMarkColorSensor.ProximityPulseLength')
        .appendField('pulseLength')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Sets the IR LED pulse duration used during proximity measurements. ' +
        'The pulse duration controls how long each IR pulse lasts. Longer pulse durations ' +
        'increase proximity detection range and reduce noise at the cost of higher power ' +
        'consumption. Shorter pulse durations allow faster sampling and lower power usage.');
  }
};

Blockly.JavaScript['andyMarkColorSensor_setProximityLedPulseLength'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var pulseLength = Blockly.JavaScript.valueToCode(
      block, 'PULSE_LENGTH', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setProximityLedPulseLength(' + pulseLength + ');\n';
};

Blockly.FtcJava['andyMarkColorSensor_setProximityLedPulseLength'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'AndyMarkColorSensor');
  var pulseLength = Blockly.FtcJava.valueToCode(
      block, 'PULSE_LENGTH', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setProximityLedPulseLength(' + pulseLength + ');\n';
};

Blockly.Blocks['andyMarkColorSensor_configureProximitySettings'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createAndyMarkColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('configureProximitySettings'));
    this.appendValueInput('GAIN').setCheck('AndyMarkColorSensor.ProximityGain')
        .appendField('gain')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PULSES').setCheck('Number')
        .appendField('pulses')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PULSE_LENGTH').setCheck('AndyMarkColorSensor.ProximityPulseLength')
        .appendField('pulseLength')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
      'Configures proximity sensor settings in one call: gain, number of pulses, and pulse length.'
    );
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'PULSES':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['andyMarkColorSensor_configureProximitySettings'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var gain = Blockly.JavaScript.valueToCode(
      block, 'GAIN', Blockly.JavaScript.ORDER_COMMA);
  var pulses = Blockly.JavaScript.valueToCode(
      block, 'PULSES', Blockly.JavaScript.ORDER_COMMA);
  var pulseLength = Blockly.JavaScript.valueToCode(
      block, 'PULSE_LENGTH', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.configureProximitySettings(' + gain + ', ' + pulses + ', ' + pulseLength + ');\n';
};

Blockly.FtcJava['andyMarkColorSensor_configureProximitySettings'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'AndyMarkColorSensor');
  var gain = Blockly.FtcJava.valueToCode(
      block, 'GAIN', Blockly.FtcJava.ORDER_COMMA);
  var pulses = Blockly.FtcJava.valueToCode(
      block, 'PULSES', Blockly.FtcJava.ORDER_COMMA);
  var pulseLength = Blockly.FtcJava.valueToCode(
      block, 'PULSE_LENGTH', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.configureProximitySettings(' + gain + ', ' + pulses + ', ' + pulseLength + ');\n';
};


// Enums

Blockly.Blocks['andyMarkColorSensor_typedEnum_proximityGain'] = {
  init: function() {
    var PROXIMITY_GAIN_CHOICES = [
        ['GAIN_1X', 'GAIN_1X'],
        ['GAIN_2X', 'GAIN_2X'],
        ['GAIN_4X', 'GAIN_4X'],
        ['GAIN_8X', 'GAIN_8X'],
    ];
    this.setOutput(true, 'AndyMarkColorSensor.ProximityGain');
    this.appendDummyInput()
        .appendField(createNonEditableField('ProximityGain'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROXIMITY_GAIN_CHOICES), 'PROXIMITY_GAIN');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['GAIN_1X', '1x gain (lowest sensitivity, widest dynamic range)'],
        ['GAIN_2X', '2x gain (medium-low sensitivity)'],
        ['GAIN_4X', '4x gain (medium-high sensitivity, default setting)'],
        ['GAIN_8X', '8x gain (highest sensitivity, for detecting far or dark targets)'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('PROXIMITY_GAIN');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['andyMarkColorSensor_typedEnum_proximityGain'] = function(block) {
  var code = '"' + block.getFieldValue('PROXIMITY_GAIN') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['andyMarkColorSensor_typedEnum_proximityGain'] = function(block) {
  var code = 'AndyMarkColorSensor.ProximityGain.' + block.getFieldValue('PROXIMITY_GAIN');
  Blockly.FtcJava.generateImport_('AndyMarkColorSensor');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['andyMarkColorSensor_typedEnum_proximityPulseLength'] = {
  init: function() {
    var PROXIMITY_PULSE_LENGTH_CHOICES = [
        ['LENGTH_4US', 'LENGTH_4US'],
        ['LENGTH_8US', 'LENGTH_8US'],
        ['LENGTH_16US', 'LENGTH_16US'],
        ['LENGTH_32US', 'LENGTH_32US'],
    ];
    this.setOutput(true, 'AndyMarkColorSensor.ProximityPulseLength');
    this.appendDummyInput()
        .appendField(createNonEditableField('ProximityPulseLength'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROXIMITY_PULSE_LENGTH_CHOICES), 'PROXIMITY_PULSE_LENGTH');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['LENGTH_4US', '4μs (recommended for very short range, low noise)'],
        ['LENGTH_8US', '8μs (default and typical setting, good balance)'],
        ['LENGTH_16US', '16μs (for mid-range targets, increased signal strength)'],
        ['LENGTH_32US', '32μs (for maximum range, highest power usage)'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('PROXIMITY_PULSE_LENGTH');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['andyMarkColorSensor_typedEnum_proximityPulseLength'] = function(block) {
  var code = '"' + block.getFieldValue('PROXIMITY_PULSE_LENGTH') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['andyMarkColorSensor_typedEnum_proximityPulseLength'] = function(block) {
  var code = 'AndyMarkColorSensor.ProximityPulseLength.' + block.getFieldValue('PROXIMITY_PULSE_LENGTH');
  Blockly.FtcJava.generateImport_('AndyMarkColorSensor');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};


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
 * @fileoverview FTC robot blocks related to color sensor.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// createColorSensorDropdown
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor
// setPropertyColor

/**
 * Deprecated. See colorSensor_setProperty_Number.
 */
Blockly.Blocks['colorSensor_setProperty'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Gain', 'Gain'],
        ['I2cAddress7Bit', 'I2cAddress7Bit'],
        ['I2cAddress8Bit', 'I2cAddress8Bit'],
    ];
    this.appendValueInput('VALUE') // no type, for compatibility
        .appendField('set')
        .appendField(createColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP')
        .appendField('to');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Gain', 'Sets the gain of the color sensor. ' +
            'Not all color sensors support this feature.'],
        ['I2cAddress7Bit', 'Sets the 7 bit I2C address of the color sensor. ' +
            'Not all color sensors support this feature.'],
        ['I2cAddress8Bit', 'Sets the 8 bit I2C address of the color sensor. ' +
            'Not all color sensors support this feature.'],
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

Blockly.JavaScript['colorSensor_setProperty'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.set' + property + '(' + value + ');\n';
};

Blockly.FtcJava['colorSensor_setProperty'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'ColorSensor');
  var property = block.getFieldValue('PROP');
  var value = Blockly.FtcJava.valueToCode(
      block, 'VALUE', Blockly.FtcJava.ORDER_NONE);
  var code;
  switch (property) {
    case 'Gain':
      Blockly.FtcJava.generateImport_('NormalizedColorSensor');
      code = '((NormalizedColorSensor) ' + identifier + ').set' + property + '(' + value + ');\n';
      break;
    case 'I2cAddress7Bit':
      Blockly.FtcJava.generateImport_('I2cAddr');
      code = identifier + '.setI2cAddress(I2cAddr.create7bit(' + value + '));\n';
      break;
    case 'I2cAddress8Bit':
      Blockly.FtcJava.generateImport_('I2cAddr');
      code = identifier + '.setI2cAddress(I2cAddr.create8bit(' + value + '));\n';
      break;
    default:
      throw 'Unexpected property ' + property + ' (colorSensor_setProperty).';
  }
  return code;
};

Blockly.Blocks['colorSensor_setProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Gain', 'Gain'],
        ['I2cAddress7Bit', 'I2cAddress7Bit'],
        ['I2cAddress8Bit', 'I2cAddress8Bit'],
    ];
    this.appendValueInput('VALUE').setCheck('Number')
        .appendField('set')
        .appendField(createColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP')
        .appendField('to');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Gain', 'Sets the gain of the color sensor. ' +
            'Not all color sensors support this feature.'],
        ['I2cAddress7Bit', 'Sets the 7 bit I2C address of the color sensor. ' +
            'Not all color sensors support this feature.'],
        ['I2cAddress8Bit', 'Sets the 8 bit I2C address of the color sensor. ' +
            'Not all color sensors support this feature.'],
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
          case 'Gain':
            return 'float';
          case 'I2cAddress7Bit':
          case 'I2cAddress8Bit':
            return 'int';
          default:
            throw 'Unexpected property ' + property + ' (colorSensor_setProperty_Number getArgumentType).';
        }
      }
      return '';
    };
  }
};

Blockly.JavaScript['colorSensor_setProperty_Number'] =
    Blockly.JavaScript['colorSensor_setProperty'];

Blockly.FtcJava['colorSensor_setProperty_Number'] =
    Blockly.FtcJava['colorSensor_setProperty'];

/**
 * Deprecated. See colorSensor_getProperty_Number.
 */
Blockly.Blocks['colorSensor_getProperty'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Alpha', 'Alpha'],
        ['Argb', 'Argb'],
        ['Blue', 'Blue'],
        ['Gain', 'Gain'],
        ['Green', 'Green'],
        ['I2cAddress7Bit', 'I2cAddress7Bit'],
        ['I2cAddress8Bit', 'I2cAddress8Bit'],
        ['Red', 'Red'],
    ];
    this.setOutput(true); // no type, for compatibility
    this.appendDummyInput()
        .appendField(createColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Alpha', 'Returns the alpha component of the color detected by the color sensor.'],
        ['Argb', 'Returns the color detected by the sensor, as an ARGB value.'],
        ['Blue', 'Returns the blue component of the color detected by the color sensor.'],
        ['Gain', 'Returns the gain of the color sensor. ' +
            'Not all color sensors support this feature.'],
        ['Green', 'Returns the green component of the color detected by the color sensor.'],
        ['I2cAddress7Bit', 'Returns the 7 bit I2C address of the color sensor. ' +
            'Not all color sensors support this feature.'],
        ['I2cAddress8Bit', 'Returns the 8 bit I2C address of the color sensor. ' +
            'Not all color sensors support this feature.'],
        ['Red', 'Returns the red component of the color detected by the color sensor.'],
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

Blockly.JavaScript['colorSensor_getProperty'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['colorSensor_getProperty'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'ColorSensor');
  var property = block.getFieldValue('PROP');
  var code;
  switch (property) {
    case 'Alpha':
    case 'Argb':
    case 'Blue':
    case 'Green':
    case 'Red':
      code = identifier + '.' + Blockly.FtcJava.makeFirstLetterLowerCase_(property) + '()';
      break;
    case 'Gain':
      Blockly.FtcJava.generateImport_('NormalizedColorSensor');
      code = '((NormalizedColorSensor) ' + identifier + ').get' + property + '()';
      break;
    case 'I2cAddress7Bit':
      code = identifier + '.getI2cAddress().get7Bit()';
      break;
    case 'I2cAddress8Bit':
      code = identifier + '.getI2cAddress().get8Bit()';
      break;
    default:
      throw 'Unexpected property ' + property + ' (colorSensor_getProperty).';
  }
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['colorSensor_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Alpha', 'Alpha'],
        ['Argb', 'Argb'],
        ['Blue', 'Blue'],
        ['Gain', 'Gain'],
        ['Green', 'Green'],
        ['I2cAddress7Bit', 'I2cAddress7Bit'],
        ['I2cAddress8Bit', 'I2cAddress8Bit'],
        ['Red', 'Red'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Alpha', 'Returns the alpha component of the color detected by the color sensor.'],
        ['Argb', 'Returns the color detected by the sensor, as an ARGB value.'],
        ['Blue', 'Returns the blue component of the color detected by the color sensor.'],
        ['Gain', 'Returns the gain of the color sensor. ' +
            'Not all color sensors support this feature.'],
        ['Green', 'Returns the green component of the color detected by the color sensor.'],
        ['I2cAddress7Bit', 'Returns the 7 bit I2C address of the color sensor. ' +
            'Not all color sensors support this feature.'],
        ['I2cAddress8Bit', 'Returns the 8 bit I2C address of the color sensor. ' +
            'Not all color sensors support this feature.'],
        ['Red', 'Returns the red component of the color detected by the color sensor.'],
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
        case 'I2cAddress7Bit':
        case 'I2cAddress8Bit':
        case 'Red':
          return 'int';
        case 'Gain':
          return 'float';
        default:
          throw 'Unexpected property ' + property + ' (colorSensor_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['colorSensor_getProperty_Number'] =
    Blockly.JavaScript['colorSensor_getProperty'];

Blockly.FtcJava['colorSensor_getProperty_Number'] =
    Blockly.FtcJava['colorSensor_getProperty'];

// Functions

/*
 * Deprecated. See colorSensor_enableLed_boolean
 */
Blockly.Blocks['colorSensor_enableLed'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('enableLed'));
    this.appendValueInput('ENABLE') // no type, for compatibility
        .appendField('enable')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Enable the LED light. Not all color sensors support this feature.');
  }
};

Blockly.JavaScript['colorSensor_enableLed'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var enable = Blockly.JavaScript.valueToCode(
      block, 'ENABLE', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.enableLed(' + enable + ');\n';
};

Blockly.FtcJava['colorSensor_enableLed'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'ColorSensor');
  var enable = Blockly.FtcJava.valueToCode(
      block, 'ENABLE', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.enableLed(' + enable + ');\n';
};

Blockly.Blocks['colorSensor_enableLed_Boolean'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('enableLed'));
    this.appendValueInput('ENABLE').setCheck('Boolean')
        .appendField('enable')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Enable the LED light. Not all color sensors support this feature.');
  }
};

Blockly.JavaScript['colorSensor_enableLed_Boolean'] =
    Blockly.JavaScript['colorSensor_enableLed'];

Blockly.FtcJava['colorSensor_enableLed_Boolean'] =
    Blockly.FtcJava['colorSensor_enableLed'];

Blockly.Blocks['colorSensor_enableLight'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('enableLight'));
    this.appendValueInput('ENABLE').setCheck('Boolean')
        .appendField('enable')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Enable the light. Not all color sensors support this feature.');
  }
};

Blockly.JavaScript['colorSensor_enableLight'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var enable = Blockly.JavaScript.valueToCode(
      block, 'ENABLE', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.enableLight(' + enable + ');\n';
};

Blockly.FtcJava['colorSensor_enableLight'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'ColorSensor');
  var enable = Blockly.FtcJava.valueToCode(
      block, 'ENABLE', Blockly.FtcJava.ORDER_NONE);
  // This java code will throw ClassCastException if the ColorSensor is not a SwitchableLight.
  Blockly.FtcJava.generateImport_('SwitchableLight');
  return '((SwitchableLight) ' + identifier + ').enableLight(' + enable + ');\n';
};

Blockly.Blocks['colorSensor_isLightOn'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('isLightOn'));
    this.setColour(functionColor);
    this.setTooltip('Returns true if the light is on.');
  }
};

Blockly.JavaScript['colorSensor_isLightOn'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.isLightOn()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['colorSensor_isLightOn'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'ColorSensor');
  // This java code will throw ClassCastException if the ColorSensor is not a Light.
  Blockly.FtcJava.generateImport_('Light');
  var code = '((Light) ' + identifier + ').isLightOn()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['colorSensor_toText'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('toText'));
    this.setColour(functionColor);
    this.setTooltip('Returns a text representation of the color detected by the sensor.');
  }
};

Blockly.JavaScript['colorSensor_toText'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.toText()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['colorSensor_toText'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'ColorSensor');
  var code = identifier + '.toString()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['colorSensor_getNormalizedColors'] = {
  init: function() {
    this.setOutput(true, 'NormalizedRGBA');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createColorSensorDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getNormalizedColors'));
    this.setColour(functionColor);
    this.setTooltip('Returns a NormalizedRGBA representing the color detected by the sensor.');
  }
};

Blockly.JavaScript['colorSensor_getNormalizedColors'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = 'JSON.parse(' + identifier + '.getNormalizedColors())';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['colorSensor_getNormalizedColors'] = function(block) {
  // This java code will throw ClassCastException if the ColorSensor is not a NormalizedColorSensor.
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'ColorSensor');
  Blockly.FtcJava.generateImport_('NormalizedColorSensor');
  var code = '((NormalizedColorSensor) ' + identifier + ').getNormalizedColors()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

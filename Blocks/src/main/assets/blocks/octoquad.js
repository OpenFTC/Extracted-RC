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
 * @fileoverview FTC robot blocks related to the OctoQuad device.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// createOctoQuadDropdown
// getOctoQuadConstant
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor
// setPropertyColor

Blockly.Blocks['octoquad_constant_Number'] = {
  init: function() {
    var CONSTANT_CHOICES = [
        ['OCTOQUAD_CHIP_ID', 'OCTOQUAD_CHIP_ID'],
        ['SUPPORTED_FW_VERSION_MAJ', 'SUPPORTED_FW_VERSION_MAJ'],
        ['ENCODER_FIRST', 'ENCODER_FIRST'],
        ['ENCODER_LAST', 'ENCODER_LAST'],
        ['NUM_ENCODERS', 'NUM_ENCODERS'],
        ['MIN_VELOCITY_MEASUREMENT_INTERVAL_MS', 'MIN_VELOCITY_MEASUREMENT_INTERVAL_MS'],
        ['MAX_VELOCITY_MEASUREMENT_INTERVAL_MS', 'MAX_VELOCITY_MEASUREMENT_INTERVAL_MS'],
        ['MIN_PULSE_WIDTH_US', 'MIN_PULSE_WIDTH_US'],
        ['MAX_PULSE_WIDTH_US', 'MAX_PULSE_WIDTH_US'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('OctoQuad'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(CONSTANT_CHOICES), 'CONSTANT');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['OCTOQUAD_CHIP_ID', 'The constant OctoQuad.OCTOQUAD_CHIP_ID, whose value is ' + getOctoQuadConstant('OCTOQUAD_CHIP_ID') + '.'],
        ['SUPPORTED_FW_VERSION_MAJ', 'The constant OctoQuad.SUPPORTED_FW_VERSION_MAJ, whose value is ' + getOctoQuadConstant('SUPPORTED_FW_VERSION_MAJ') + '.'],
        ['ENCODER_FIRST', 'The constant OctoQuad.ENCODER_FIRST, whose value is ' + getOctoQuadConstant('ENCODER_FIRST') + '.'],
        ['ENCODER_LAST', 'The constant OctoQuad.ENCODER_LAST, whose value is ' + getOctoQuadConstant('ENCODER_LAST') + '.'],
        ['NUM_ENCODERS', 'The constant OctoQuad.NUM_ENCODERS, whose value is ' + getOctoQuadConstant('NUM_ENCODERS') + '.'],
        ['MIN_VELOCITY_MEASUREMENT_INTERVAL_MS', 'The constant OctoQuad.MIN_VELOCITY_MEASUREMENT_INTERVAL_MS, whose value is ' +
            getOctoQuadConstant('MIN_VELOCITY_MEASUREMENT_INTERVAL_MS') + ' milliseconds.'],
        ['MAX_VELOCITY_MEASUREMENT_INTERVAL_MS', 'The constant OctoQuad.MAX_VELOCITY_MEASUREMENT_INTERVAL_MS, whose value is ' +
            getOctoQuadConstant('MAX_VELOCITY_MEASUREMENT_INTERVAL_MS') + 'milliseconds.'],
        ['MIN_PULSE_WIDTH_US', 'The constant OctoQuad.MIN_PULSE_WIDTH_US, whose value is ' +
            getOctoQuadConstant('MIN_PULSE_WIDTH_US') + ' microseconds. The symbol for microseconds is μs, but is sometimes simplified to us.'],
        ['MAX_PULSE_WIDTH_US', 'The constant OctoQuad.MAX_PULSE_WIDTH_US, whose value is ' +
            getOctoQuadConstant('MAX_PULSE_WIDTH_US') + ' microseconds. The symbol for microseconds is μs, but is sometimes simplified to us.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('CONSTANT');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
    this.getFtcJavaOutputType = function() {
      var constant = thisBlock.getFieldValue('CONSTANT');
      switch (constant) {
        case 'OCTOQUAD_CHIP_ID':
          return 'byte';
        case 'SUPPORTED_FW_VERSION_MAJ':
        case 'ENCODER_FIRST':
        case 'ENCODER_LAST':
        case 'NUM_ENCODERS':
        case 'MIN_VELOCITY_MEASUREMENT_INTERVAL_MS':
        case 'MAX_VELOCITY_MEASUREMENT_INTERVAL_MS':
        case 'MIN_PULSE_WIDTH_US':
        case 'MAX_PULSE_WIDTH_US':
        return 'int';
      }
    };
  }
};

Blockly.JavaScript['octoquad_constant_Number'] = function(block) {
  var constant = block.getFieldValue('CONSTANT');
  var code = getOctoQuadConstant(constant);
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['octoquad_constant_Number'] = function(block) {
  var constant = block.getFieldValue('CONSTANT');
  var code = 'OctoQuad.' + constant;
  Blockly.FtcJava.generateImport_('OctoQuad');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['octoquad_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['ChipId', 'ChipId'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['ChipId', 'Returns the chip id of the OctoQuad.'],
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
        case 'ChipId':
          return 'byte';
        default:
          throw 'Unexpected property ' + property + ' (octoquad_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['octoquad_getProperty_Number'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['octoquad_getProperty_Number'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['octoquad_getProperty_String'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['FirmwareVersionString', 'FirmwareVersionString'],
    ];
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['FirmwareVersionString', 'Returns the firmware version running on the OctoQuad.'],
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

Blockly.JavaScript['octoquad_getProperty_String'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['octoquad_getProperty_String'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['octoquad_resetAllPositions'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('resetAllPositions'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Resets all encoder counts in the OctoQuad firmware.');
  }
};

Blockly.JavaScript['octoquad_resetAllPositions'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.resetAllPositions();\n';
};

Blockly.FtcJava['octoquad_resetAllPositions'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  return identifier + '.resetAllPositions();\n';
};

Blockly.Blocks['octoquad_resetSinglePosition'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('resetSinglePosition'));
    this.appendValueInput('INDEX').setCheck('Number')
        .appendField('index')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Resets a single encoder in the OctoQuad firmware.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INDEX':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['octoquad_resetSinglePosition'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var index = Blockly.JavaScript.valueToCode(
      block, 'INDEX', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.resetSinglePosition(' + index + ');\n';
};

Blockly.FtcJava['octoquad_resetSinglePosition'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var index = Blockly.FtcJava.valueToCode(
      block, 'INDEX', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.resetSinglePosition(' + index + ');\n';
};

Blockly.Blocks['octoquad_typedEnum_encoderDirection'] = {
  init: function() {
    var ENCODER_DIRECTION_CHOICES = [
        ['FORWARD', 'FORWARD'],
        ['REVERSE', 'REVERSE'],
    ];
    this.setOutput(true, 'OctoQuad.EncoderDirection');
    this.appendDummyInput()
        .appendField(createNonEditableField('EncoderDirection'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(ENCODER_DIRECTION_CHOICES), 'ENCODER_DIRECTION');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['FORWARD', 'The OctoQuad.EncoderDirection value FORWARD'],
        ['REVERSE', 'The OctoQuad.EncoderDirection value REVERSE'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('ENCODER_DIRECTION');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['octoquad_typedEnum_encoderDirection'] = function(block) {
  var code = '"' + block.getFieldValue('ENCODER_DIRECTION') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['octoquad_typedEnum_encoderDirection'] = function(block) {
  var code = 'OctoQuad.EncoderDirection.' + block.getFieldValue('ENCODER_DIRECTION');
  Blockly.FtcJava.generateImport_('OctoQuad');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['octoquad_setSingleEncoderDirection'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setSingleEncoderDirection'));
    this.appendValueInput('INDEX').setCheck('Number')
        .appendField('index')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DIRECTION').setCheck('OctoQuad.EncoderDirection')
        .appendField('direction')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the direction for a single encoder. This parameter will ' +
         'NOT be retained across power cycles, unless you call saveParametersToFlash.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INDEX':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['octoquad_setSingleEncoderDirection'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var index = Blockly.JavaScript.valueToCode(
      block, 'INDEX', Blockly.JavaScript.ORDER_COMMA);
  var direction = Blockly.JavaScript.valueToCode(
      block, 'DIRECTION', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setSingleEncoderDirection(' + index + ', ' + direction + ');\n';
};

Blockly.FtcJava['octoquad_setSingleEncoderDirection'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var index = Blockly.FtcJava.valueToCode(
    block, 'INDEX', Blockly.FtcJava.ORDER_COMMA);
  var direction = Blockly.FtcJava.valueToCode(
      block, 'DIRECTION', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setSingleEncoderDirection(' + index + ', ' + direction + ');\n';
};

Blockly.Blocks['octoquad_getSingleEncoderDirection'] = {
  init: function() {
    this.setOutput(true, 'OctoQuad.EncoderDirection');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getSingleEncoderDirection'));
    this.appendValueInput('INDEX').setCheck('Number')
        .appendField('index')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the direction for a single encoder.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INDEX':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['octoquad_getSingleEncoderDirection'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var index = Blockly.JavaScript.valueToCode(
      block, 'INDEX', Blockly.JavaScript.ORDER_NONE);
  var code = identifier + '.getSingleEncoderDirection(' + index + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['octoquad_getSingleEncoderDirection'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var index = Blockly.FtcJava.valueToCode(
      block, 'INDEX', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.getSingleEncoderDirection(' + index + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['octoquad_setAllVelocitySampleIntervals'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setAllVelocitySampleIntervals'));
    this.appendValueInput('INTERVAL_MS').setCheck('Number')
        .appendField('intervalMillis')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the velocity sample interval for all encoders. This parameter will ' +
         'NOT be retained across power cycles, unless you call saveParametersToFlash.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INTERVAL_MS':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['octoquad_setAllVelocitySampleIntervals'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var intervalMillis = Blockly.JavaScript.valueToCode(
      block, 'INTERVAL_MS', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setAllVelocitySampleIntervals(' + intervalMillis + ');\n';
};

Blockly.FtcJava['octoquad_setAllVelocitySampleIntervals'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var intervalMillis = Blockly.FtcJava.valueToCode(
      block, 'INTERVAL_MS', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setAllVelocitySampleIntervals(' + intervalMillis + ');\n';
};

Blockly.Blocks['octoquad_setSingleVelocitySampleInterval'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setSingleVelocitySampleInterval'));
    this.appendValueInput('INDEX').setCheck('Number')
        .appendField('index')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('INTERVAL_MS').setCheck('Number')
        .appendField('intervalMillis')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Set the velocity sample interval for a single encoder. This parameter will ' +
         'NOT be retained across power cycles, unless you call saveParametersToFlash.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INDEX':
          return 'int';
        case 'INTERVAL_MS':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['octoquad_setSingleVelocitySampleInterval'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var index = Blockly.JavaScript.valueToCode(
      block, 'INDEX', Blockly.JavaScript.ORDER_COMMA);
  var intervalMillis = Blockly.JavaScript.valueToCode(
      block, 'INTERVAL_MS', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setSingleVelocitySampleInterval(' + index + ', ' + intervalMillis + ');\n';
};

Blockly.FtcJava['octoquad_setSingleVelocitySampleInterval'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var index = Blockly.FtcJava.valueToCode(
      block, 'INDEX', Blockly.FtcJava.ORDER_COMMA);
  var intervalMillis = Blockly.FtcJava.valueToCode(
      block, 'INTERVAL_MS', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setSingleVelocitySampleInterval(' + index + ', ' + intervalMillis + ');\n';
};

Blockly.Blocks['octoquad_getSingleVelocitySampleInterval'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getSingleVelocitySampleInterval'));
    this.appendValueInput('INDEX').setCheck('Number')
        .appendField('index')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Read a single velocity sample interval.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INDEX':
          return 'int';
      }
      return '';
    };
    this.getFtcJavaOutputType = function() {
      return 'int';
    };
  }
};

Blockly.JavaScript['octoquad_getSingleVelocitySampleInterval'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var index = Blockly.JavaScript.valueToCode(
      block, 'INDEX', Blockly.JavaScript.ORDER_NONE);
  var code = identifier + '.getSingleVelocitySampleInterval(' + index + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['octoquad_getSingleVelocitySampleInterval'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var index = Blockly.FtcJava.valueToCode(
      block, 'INDEX', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.getSingleVelocitySampleInterval(' + index + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['octoquad_setSingleChannelPulseWidthParams'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setSingleChannelPulseWidthParams'));
    this.appendValueInput('INDEX').setCheck('Number')
        .appendField('index')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MIN_WIDTH_US').setCheck('Number')
        .appendField('minLengthMicros')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MAX_WIDTH_US').setCheck('Number')
        .appendField('maxLengthMicros')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the velocity sample interval for all encoders. This parameter will ' +
         'NOT be retained across power cycles, unless you call saveParametersToFlash.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INDEX':
        case 'MIN_WIDTH_US':
        case 'MAX_WIDTH_US':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['octoquad_setSingleChannelPulseWidthParams'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var index = Blockly.JavaScript.valueToCode(
      block, 'INDEX', Blockly.JavaScript.ORDER_COMMA);
  var minLengthMicros = Blockly.JavaScript.valueToCode(
      block, 'MIN_WIDTH_US', Blockly.JavaScript.ORDER_COMMA);
  var maxLengthMicros = Blockly.JavaScript.valueToCode(
      block, 'MAX_WIDTH_US', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setSingleChannelPulseWidthParams(' + index + ', ' + minLengthMicros + ', ' + maxLengthMicros + ');\n';
};

Blockly.FtcJava['octoquad_setSingleChannelPulseWidthParams'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var index = Blockly.FtcJava.valueToCode(
      block, 'INDEX', Blockly.FtcJava.ORDER_COMMA);
  var minLengthMicros = Blockly.FtcJava.valueToCode(
      block, 'MIN_WIDTH_US', Blockly.FtcJava.ORDER_COMMA);
  var maxLengthMicros = Blockly.FtcJava.valueToCode(
      block, 'MAX_WIDTH_US', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setSingleChannelPulseWidthParams(' + index + ', ' + minLengthMicros + ', ' + maxLengthMicros + ');\n';
};

Blockly.Blocks['octoquad_resetEverything'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('resetEverything'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Run the firmware\'s internal reset routine.');
  }
};

Blockly.JavaScript['octoquad_resetEverything'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.resetEverything();\n';
};

Blockly.FtcJava['octoquad_resetEverything'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  return identifier + '.resetEverything();\n';
};

Blockly.Blocks['octoquad_typedEnum_channelBankConfig'] = {
  init: function() {
    var CHANNEL_BANK_CONFIG_CHOICES = [
        ['ALL_QUADRATURE', 'ALL_QUADRATURE'],
        ['ALL_PULSE_WIDTH', 'ALL_PULSE_WIDTH'],
        ['BANK1_QUADRATURE_BANK2_PULSE_WIDTH', 'BANK1_QUADRATURE_BANK2_PULSE_WIDTH'],
    ];
    this.setOutput(true, 'OctoQuad.ChannelBankConfig');
    this.appendDummyInput()
        .appendField(createNonEditableField('ChannelBankConfig'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(CHANNEL_BANK_CONFIG_CHOICES), 'CHANNEL_BANK_CONFIG');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['ALL_QUADRATURE', 'The OctoQuad.ChannelBankConfig value ALL_QUADRATURE'],
        ['ALL_PULSE_WIDTH', 'The OctoQuad.ChannelBankConfig value ALL_PULSE_WIDTH'],
        ['BANK1_QUADRATURE_BANK2_PULSE_WIDTH', 'The OctoQuad.ChannelBankConfig value BANK1_QUADRATURE_BANK2_PULSE_WIDTH'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('CHANNEL_BANK_CONFIG');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['octoquad_typedEnum_channelBankConfig'] = function(block) {
  var code = '"' + block.getFieldValue('CHANNEL_BANK_CONFIG') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['octoquad_typedEnum_channelBankConfig'] = function(block) {
  var code = 'OctoQuad.ChannelBankConfig.' + block.getFieldValue('CHANNEL_BANK_CONFIG');
  Blockly.FtcJava.generateImport_('OctoQuad');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['octoquad_setProperty_ChannelBankConfig'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['ChannelBankConfig', 'ChannelBankConfig'],
    ];
    this.appendValueInput('VALUE').setCheck('OctoQuad.ChannelBankConfig')
        .appendField('set')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP')
        .appendField('to');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['ChannelBankConfig', 'Configures the OctoQuad\'s channel banks. This parameter will ' +
            'NOT be retained across power cycles, unless you call saveParametersToFlash.'],
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

Blockly.JavaScript['octoquad_setProperty_ChannelBankConfig'] =function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.set' + property + '(' + value + ');\n';
};

Blockly.FtcJava['octoquad_setProperty_ChannelBankConfig'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var property = block.getFieldValue('PROP');
  var value = Blockly.FtcJava.valueToCode(
    block, 'VALUE', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.set' + property + '(' + value + ');\n';
};

Blockly.Blocks['octoquad_getProperty_ChannelBankConfig'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['ChannelBankConfig', 'ChannelBankConfig'],
    ];
    this.setOutput(true, 'OctoQuad.ChannelBankConfig');
    this.appendDummyInput()
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['ChannelBankConfig', 'Returns the current channel bank configuration.'],
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

Blockly.JavaScript['octoquad_getProperty_ChannelBankConfig'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['octoquad_getProperty_ChannelBankConfig'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['octoquad_typedEnum_i2cRecoveryMode'] = {
  init: function() {
    var I2C_RECOVERY_MODE_CHOICES = [
        ['NONE', 'NONE'],
        ['MODE_1_PERIPH_RST_ON_FRAME_ERR', 'MODE_1_PERIPH_RST_ON_FRAME_ERR'],
        ['MODE_2_M1_PLUS_SCL_IDLE_ONESHOT_TGL', 'MODE_2_M1_PLUS_SCL_IDLE_ONESHOT_TGL'],
    ];
    this.setOutput(true, 'OctoQuad.I2cRecoveryMode');
    this.appendDummyInput()
        .appendField(createNonEditableField('I2cRecoveryMode'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(I2C_RECOVERY_MODE_CHOICES), 'I2C_RECOVERY_MODE');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['NONE', 'The OctoQuad.I2cRecoveryMode value NONE'],
        ['MODE_1_PERIPH_RST_ON_FRAME_ERR', 'The OctoQuad.I2cRecoveryMode value MODE_1_PERIPH_RST_ON_FRAME_ERR'],
        ['MODE_2_M1_PLUS_SCL_IDLE_ONESHOT_TGL', 'The OctoQuad.I2cRecoveryMode value MODE_2_M1_PLUS_SCL_IDLE_ONESHOT_TGL'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('I2C_RECOVERY_MODE');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['octoquad_typedEnum_i2cRecoveryMode'] = function(block) {
  var code = '"' + block.getFieldValue('I2C_RECOVERY_MODE') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['octoquad_typedEnum_i2cRecoveryMode'] = function(block) {
  var code = 'OctoQuad.I2cRecoveryMode.' + block.getFieldValue('I2C_RECOVERY_MODE');
  Blockly.FtcJava.generateImport_('OctoQuad');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['octoquad_setProperty_I2cRecoveryMode'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['I2cRecoveryMode', 'I2cRecoveryMode'],
    ];
    this.appendValueInput('VALUE').setCheck('OctoQuad.I2cRecoveryMode')
        .appendField('set')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP')
        .appendField('to');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['I2cRecoveryMode', 'Configures the OctoQuad to use the specified I2C recovery mode. This parameter will ' +
            'NOT be retained across power cycles, unless you call saveParametersToFlash.'],
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

Blockly.JavaScript['octoquad_setProperty_I2cRecoveryMode'] =function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.set' + property + '(' + value + ');\n';
};

Blockly.FtcJava['octoquad_setProperty_I2cRecoveryMode'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var property = block.getFieldValue('PROP');
  var value = Blockly.FtcJava.valueToCode(
    block, 'VALUE', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.set' + property + '(' + value + ');\n';
};

Blockly.Blocks['octoquad_getProperty_I2cRecoveryMode'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['I2cRecoveryMode', 'I2cRecoveryMode'],
    ];
    this.setOutput(true, 'OctoQuad.I2cRecoveryMode');
    this.appendDummyInput()
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['I2cRecoveryMode', 'Returns the currently configured I2C recovery mode.'],
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

Blockly.JavaScript['octoquad_getProperty_I2cRecoveryMode'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['octoquad_getProperty_I2cRecoveryMode'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['octoquad_saveParametersToFlash'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('saveParametersToFlash'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Stores the current state of parameters to flash, to be applied at next boot.');
  }
};

Blockly.JavaScript['octoquad_saveParametersToFlash'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.saveParametersToFlash();\n';
};

Blockly.FtcJava['octoquad_saveParametersToFlash'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  return identifier + '.saveParametersToFlash();\n';
};

Blockly.Blocks['octoquad_typedEnum_cachingMode'] = {
  init: function() {
    var CACHING_MODE_CHOICES = [
        ['MANUAL', 'MANUAL'],
        ['AUTO', 'AUTO'],
        ['NONE', 'NONE'],
    ];
    this.setOutput(true, 'OctoQuad.CachingMode');
    this.appendDummyInput()
        .appendField(createNonEditableField('CachingMode'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(CACHING_MODE_CHOICES), 'CACHING_MODE');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['MANUAL', 'The OctoQuad.CachingMode value MANUAL'],
        ['AUTO', 'The OctoQuad.CachingMode value AUTO'],
        ['NONE', 'The OctoQuad.CachingMode value NONE is obsolete.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('CACHING_MODE');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['octoquad_typedEnum_cachingMode'] = function(block) {
  var code = '"' + block.getFieldValue('CACHING_MODE') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['octoquad_typedEnum_cachingMode'] = function(block) {
  var code = 'OctoQuad.CachingMode.' + block.getFieldValue('CACHING_MODE');
  Blockly.FtcJava.generateImport_('OctoQuad');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['octoquad_setCachingMode'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setCachingMode'));
    this.appendValueInput('MODE').setCheck('OctoQuad.CachingMode')
        .appendField('mode')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the data caching mode for the OctoQuad.');
  }
};

Blockly.JavaScript['octoquad_setCachingMode'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var mode = Blockly.JavaScript.valueToCode(
      block, 'MODE', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setCachingMode(' + mode + ');\n';
};

Blockly.FtcJava['octoquad_setCachingMode'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var mode = Blockly.FtcJava.valueToCode(
      block, 'MODE', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setCachingMode(' + mode + ');\n';
};

Blockly.Blocks['octoquad_refreshCache'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('refreshCache'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Refreshes the position and velocity data cache.');
  }
};

Blockly.JavaScript['octoquad_refreshCache'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.refreshCache();\n';
};

Blockly.FtcJava['octoquad_refreshCache'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  return identifier + '.refreshCache();\n';
};

Blockly.Blocks['octoquad_readSinglePosition_Caching'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('readSinglePosition_Caching'));
    this.appendValueInput('INDEX').setCheck('Number')
        .appendField('index')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Read a single position from the OctoQuad. ' +
        'Note this call may return cached data based on the CachingMode you selected! ' +
        'Depending on the channel bank configuration, this may either be quadrature step count, or pulse width.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INDEX':
          return 'int';
      }
      return '';
    };
    this.getFtcJavaOutputType = function() {
      return 'int';
    };
  }
};

Blockly.JavaScript['octoquad_readSinglePosition_Caching'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var index = Blockly.JavaScript.valueToCode(
      block, 'INDEX', Blockly.JavaScript.ORDER_NONE);
  var code = identifier + '.readSinglePosition_Caching(' + index + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['octoquad_readSinglePosition_Caching'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var index = Blockly.FtcJava.valueToCode(
      block, 'INDEX', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.readSinglePosition_Caching(' + index + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['octoquad_readSingleVelocity_Caching'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('readSingleVelocity_Caching'));
    this.appendValueInput('INDEX').setCheck('Number')
        .appendField('index')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Read a single velocity from the OctoQuad. ' +
        'Note this call may return cached data based on the CachingMode you selected! ' +
        'NOTE: if using an absolute pulse width encoder, in order to get sane velocity data, ' +
        'you must set the channel min/max pulse width parameter.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INDEX':
          return 'int';
      }
      return '';
    };
    this.getFtcJavaOutputType = function() {
      return 'int';
    };
  }
};

Blockly.JavaScript['octoquad_readSingleVelocity_Caching'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var index = Blockly.JavaScript.valueToCode(
      block, 'INDEX', Blockly.JavaScript.ORDER_NONE);
  var code = identifier + '.readSingleVelocity_Caching(' + index + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['octoquad_readSingleVelocity_Caching'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var index = Blockly.FtcJava.valueToCode(
      block, 'INDEX', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.readSingleVelocity_Caching(' + index + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['octoquad_setSingleChannelPulseWidthTracksWrap'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setSingleChannelPulseWidthTracksWrap'));
    this.appendValueInput('INDEX').setCheck('Number')
        .appendField('index')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TRACK_WRAP').setCheck('Boolean')
        .appendField('trackWrap')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Configure whether in PWM mode, a channel will report the raw PWM length in ' +
                    'microseconds, or whether it will perform "wrap tracking" for use with an ' +
                    'absolute encoder to turn the absolute position in a continuous value.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INDEX':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['octoquad_setSingleChannelPulseWidthTracksWrap'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var index = Blockly.JavaScript.valueToCode(
      block, 'INDEX', Blockly.JavaScript.ORDER_COMMA);
  var trackWrap = Blockly.JavaScript.valueToCode(
      block, 'TRACK_WRAP', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setSingleChannelPulseWidthTracksWrap(' + index + ', ' + trackWrap + ');\n';
};

Blockly.FtcJava['octoquad_setSingleChannelPulseWidthTracksWrap'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var index = Blockly.FtcJava.valueToCode(
      block, 'INDEX', Blockly.FtcJava.ORDER_COMMA);
  var trackWrap = Blockly.FtcJava.valueToCode(
      block, 'TRACK_WRAP', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setSingleChannelPulseWidthTracksWrap(' + index + ', ' + trackWrap + ');\n';
};

Blockly.Blocks['octoquad_getSingleChannelPulseWidthTracksWrap'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getSingleChannelPulseWidthTracksWrap'));
    this.appendValueInput('INDEX').setCheck('Number')
        .appendField('index')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Get whether PWM wrap tracking is enabled for a single channel.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INDEX':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['octoquad_getSingleChannelPulseWidthTracksWrap'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var index = Blockly.JavaScript.valueToCode(
      block, 'INDEX', Blockly.JavaScript.ORDER_NONE);
  var code = identifier + '.getSingleChannelPulseWidthTracksWrap(' + index + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['octoquad_getSingleChannelPulseWidthTracksWrap'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var index = Blockly.FtcJava.valueToCode(
      block, 'INDEX', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.getSingleChannelPulseWidthTracksWrap(' + index + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['octoquad_typedEnum_localizerYawAxis'] = {
  init: function() {
    var LOCALIZER_YAW_AXIS_CHOICES = [
        ['UNDECIDED', 'UNDECIDED'],
        ['X', 'X'],
        ['X_INV', 'X_INV'],
        ['Y', 'Y'],
        ['Y_INV', 'Y_INV'],
        ['Z', 'Z'],
        ['Z_INV', 'Z_INV'],
    ];
    this.setOutput(true, 'OctoQuad.LocalizerYawAxis');
    this.appendDummyInput()
        .appendField(createNonEditableField('LocalizerYawAxis'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(LOCALIZER_YAW_AXIS_CHOICES), 'LOCALIZER_YAW_AXIS');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['UNDECIDED', 'The OctoQuad.LocalizerYawAxis value UNDECIDED'],
        ['X', 'The OctoQuad.LocalizerYawAxis value X'],
        ['X_INV', 'The OctoQuad.LocalizerYawAxis value X_INV'],
        ['Y', 'The OctoQuad.LocalizerYawAxis value Y'],
        ['Y_INV', 'The OctoQuad.LocalizerYawAxis value Y_INV'],
        ['Z', 'The OctoQuad.LocalizerYawAxis value Z'],
        ['Z_INV', 'The OctoQuad.LocalizerYawAxis value Z_INV'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('LOCALIZER_YAW_AXIS');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['octoquad_typedEnum_localizerYawAxis'] = function(block) {
  var code = '"' + block.getFieldValue('LOCALIZER_YAW_AXIS') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['octoquad_typedEnum_localizerYawAxis'] = function(block) {
  var code = 'OctoQuad.LocalizerYawAxis.' + block.getFieldValue('LOCALIZER_YAW_AXIS');
  Blockly.FtcJava.generateImport_('OctoQuad');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['octoquad_getProperty_LocalizerYawAxis'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['LocalizerHeadingAxisChoice', 'LocalizerHeadingAxisChoice'],
    ];
    this.setOutput(true, 'OctoQuad.LocalizerYawAxis');
    this.appendDummyInput()
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
      ['LocalizerHeadingAxisChoice', 'Returns the IMU axis the localizer decided to use for heading.'],
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

Blockly.JavaScript['octoquad_getProperty_LocalizerYawAxis'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['octoquad_getProperty_LocalizerYawAxis'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['octoquad_typedEnum_localizerStatus'] = {
  init: function() {
    var LOCALIZER_STATUS_CHOICES = [
        ['INVALID', 'INVALID'],
        ['NOT_INITIALIZED', 'NOT_INITIALIZED'],
        ['WARMING_UP_IMU', 'WARMING_UP_IMU'],
        ['CALIBRATING_IMU', 'CALIBRATING_IMU'],
        ['RUNNING', 'RUNNING'],
        ['FAULT_NO_IMU', 'FAULT_NO_IMU'],
    ];
    this.setOutput(true, 'OctoQuad.LocalizerStatus');
    this.appendDummyInput()
        .appendField(createNonEditableField('LocalizerStatus'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(LOCALIZER_STATUS_CHOICES), 'LOCALIZER_STATUS');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['INVALID', 'The OctoQuad.LocalizerStatus value INVALID'],
        ['NOT_INITIALIZED', 'The OctoQuad.LocalizerStatus value NOT_INITIALIZED'],
        ['WARMING_UP_IMU', 'The OctoQuad.LocalizerStatus value WARMING_UP_IMU'],
        ['CALIBRATING_IMU', 'The OctoQuad.LocalizerStatus value CALIBRATING_IMU'],
        ['RUNNING', 'The OctoQuad.LocalizerStatus value RUNNING'],
        ['FAULT_NO_IMU', 'The OctoQuad.LocalizerStatus value FAULT_NO_IMU'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('LOCALIZER_STATUS');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['octoquad_typedEnum_localizerStatus'] = function(block) {
  var code = '"' + block.getFieldValue('LOCALIZER_STATUS') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['octoquad_typedEnum_localizerStatus'] = function(block) {
  var code = 'OctoQuad.LocalizerStatus.' + block.getFieldValue('LOCALIZER_STATUS');
  Blockly.FtcJava.generateImport_('OctoQuad');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['octoquad_getProperty_LocalizerStatus'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['LocalizerStatus', 'LocalizerStatus'],
    ];
    this.setOutput(true, 'OctoQuad.LocalizerStatus');
    this.appendDummyInput()
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['LocalizerStatus', 'Returns the current status of the localizer algorithm.'],
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

Blockly.JavaScript['octoquad_getProperty_LocalizerStatus'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['octoquad_getProperty_LocalizerStatus'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['octoquad_setAllLocalizerParameters'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setAllLocalizerParameters'));
    this.appendValueInput('PORT_X').setCheck('Number')
        .appendField('portX')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PORT_Y').setCheck('Number')
        .appendField('portY')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TICKS_PER_MM_X').setCheck('Number')
        .appendField('ticksPerMM_x')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TICKS_PER_MM_Y').setCheck('Number')
        .appendField('ticksPerMM_y')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TCP_OFFSET_MM_X').setCheck('Number')
        .appendField('tcpOffsetMM_X')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TCP_OFFSET_MM_Y').setCheck('Number')
        .appendField('tcpOffsetMM_Y')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('HEADING_SCALAR').setCheck('Number')
        .appendField('headingScalar')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VELOCITY_INTERVAL_MS').setCheck('Number')
        .appendField('velocityIntervalMs')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Sets all localizer parameters. ' +
        'NOTE: this will not take effect until a call to resetLocalizerAndCalibrateIMU.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'PORT_X':
        case 'PORT_Y':
        case 'VELOCITY_INTERVAL_MS':
          return 'int';
        case 'TICKS_PER_MM_X':
        case 'TICKS_PER_MM_Y':
        case 'TCP_OFFSET_MM_X':
        case 'TCP_OFFSET_MM_Y':
        case 'HEADING_SCALAR':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['octoquad_setAllLocalizerParameters'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var portX = Blockly.JavaScript.valueToCode(
      block, 'PORT_X', Blockly.JavaScript.ORDER_COMMA);
  var portY = Blockly.JavaScript.valueToCode(
      block, 'PORT_Y', Blockly.JavaScript.ORDER_COMMA);
  var ticksPerMM_x = Blockly.JavaScript.valueToCode(
      block, 'TICKS_PER_MM_X', Blockly.JavaScript.ORDER_COMMA);
  var ticksPerMM_y = Blockly.JavaScript.valueToCode(
      block, 'TICKS_PER_MM_Y', Blockly.JavaScript.ORDER_COMMA);
  var tcpOffsetMM_X = Blockly.JavaScript.valueToCode(
      block, 'TCP_OFFSET_MM_X', Blockly.JavaScript.ORDER_COMMA);
  var tcpOffsetMM_Y = Blockly.JavaScript.valueToCode(
      block, 'TCP_OFFSET_MM_Y', Blockly.JavaScript.ORDER_COMMA);
  var headingScalar = Blockly.JavaScript.valueToCode(
      block, 'HEADING_SCALAR', Blockly.JavaScript.ORDER_COMMA);
  var velocityIntervalMs = Blockly.JavaScript.valueToCode(
      block, 'VELOCITY_INTERVAL_MS', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setAllLocalizerParameters(' +
    portX + ', ' + portY + ', ' +
    ticksPerMM_x + ', ' + ticksPerMM_y + ', ' +
    tcpOffsetMM_X + ', ' + tcpOffsetMM_Y + ', ' +
    headingScalar + ', ' + velocityIntervalMs + ');\n';
};

Blockly.FtcJava['octoquad_setAllLocalizerParameters'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var portX = Blockly.FtcJava.valueToCode(
      block, 'PORT_X', Blockly.FtcJava.ORDER_COMMA);
  var portY = Blockly.FtcJava.valueToCode(
      block, 'PORT_Y', Blockly.FtcJava.ORDER_COMMA);
  var ticksPerMM_x = Blockly.FtcJava.valueToCode(
      block, 'TICKS_PER_MM_X', Blockly.FtcJava.ORDER_COMMA);
  var ticksPerMM_y = Blockly.FtcJava.valueToCode(
      block, 'TICKS_PER_MM_Y', Blockly.FtcJava.ORDER_COMMA);
  var tcpOffsetMM_X = Blockly.FtcJava.valueToCode(
      block, 'TCP_OFFSET_MM_X', Blockly.FtcJava.ORDER_COMMA);
  var tcpOffsetMM_Y = Blockly.FtcJava.valueToCode(
      block, 'TCP_OFFSET_MM_Y', Blockly.FtcJava.ORDER_COMMA);
  var headingScalar = Blockly.FtcJava.valueToCode(
      block, 'HEADING_SCALAR', Blockly.FtcJava.ORDER_COMMA);
  var velocityIntervalMs = Blockly.FtcJava.valueToCode(
      block, 'VELOCITY_INTERVAL_MS', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setAllLocalizerParameters(' +
    portX + ', ' + portY + ', ' +
    ticksPerMM_x + ', ' + ticksPerMM_y + ', ' +
    tcpOffsetMM_X + ', ' + tcpOffsetMM_Y + ', ' +
    headingScalar + ', ' + velocityIntervalMs + ');\n';
};

Blockly.Blocks['octoquad_readLocalizerData'] = {
  init: function() {
    this.setOutput(true, 'OctoQuad.LocalizerDataBlock');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('readLocalizerData'));
    this.setColour(functionColor);
    this.setTooltip(
        'Bulk read all localizer data in one operation for maximum efficiency.');
  }
};

Blockly.JavaScript['octoquad_readLocalizerData'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = 'JSON.parse(' + identifier + '.readLocalizerData())';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['octoquad_readLocalizerData'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var code = identifier + '.readLocalizerData()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['octoquad_setLocalizerPose'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setLocalizerPose'));
    this.appendValueInput('POS_X_MM').setCheck('Number')
        .appendField('posX_mm')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('POS_Y_MM').setCheck('Number')
        .appendField('posY_mm')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('HEADING_RAD').setCheck('Number')
        .appendField('heading_rad')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        '"Teleport" the localizer to a new location. This may be useful, for instance, for updating ' +
        'your position based on vision targeting.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'POS_X_MM':
        case 'POS_Y_MM':
          return 'int';
        case 'HEADING_RAD':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['octoquad_setLocalizerPose'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var posX_mm = Blockly.JavaScript.valueToCode(
      block, 'POS_X_MM', Blockly.JavaScript.ORDER_COMMA);
  var posY_mm = Blockly.JavaScript.valueToCode(
      block, 'POS_Y_MM', Blockly.JavaScript.ORDER_COMMA);
  var heading_rad = Blockly.JavaScript.valueToCode(
      block, 'HEADING_RAD', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setLocalizerPose(' +
    posX_mm + ', ' + posY_mm + ', ' + heading_rad + ');\n';
};

Blockly.FtcJava['octoquad_setLocalizerPose'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var posX_mm = Blockly.FtcJava.valueToCode(
      block, 'POS_X_MM', Blockly.FtcJava.ORDER_COMMA);
  var posY_mm = Blockly.FtcJava.valueToCode(
      block, 'POS_Y_MM', Blockly.FtcJava.ORDER_COMMA);
  var heading_rad = Blockly.FtcJava.valueToCode(
      block, 'HEADING_RAD', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setLocalizerPose(' +
    posX_mm + ', ' + posY_mm + ', ' + heading_rad + ');\n';
};

Blockly.Blocks['octoquad_setLocalizerHeading'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setLocalizerHeading'));
    this.appendValueInput('HEADING_RAD').setCheck('Number')
        .appendField('heading_rad')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        '"Teleport" the localizer heading to a new orientation. This may be useful, ' +
        'for instance, for updating heading based on vision targeting.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'HEADING_RAD':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['octoquad_setLocalizerHeading'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var heading_rad = Blockly.JavaScript.valueToCode(
      block, 'HEADING_RAD', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setLocalizerHeading(' + heading_rad + ');\n';
};

Blockly.FtcJava['octoquad_setLocalizerHeading'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  var heading_rad = Blockly.FtcJava.valueToCode(
      block, 'HEADING_RAD', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setLocalizerHeading(' + heading_rad + ');\n';
};

Blockly.Blocks['octoquad_resetLocalizerAndCalibrateIMU'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createOctoQuadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('resetLocalizerAndCalibrateIMU'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Reset the localizer pose to (0,0,0) and recalibrate the IMU. ' +
        'To determine when the reset is complete, call getLocalizerStatus and compare with ' +
        'LocalizerStatus.RUNNING.');
  }
};

Blockly.JavaScript['octoquad_resetLocalizerAndCalibrateIMU'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.resetLocalizerAndCalibrateIMU();\n';
};

Blockly.FtcJava['octoquad_resetLocalizerAndCalibrateIMU'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'OctoQuad');
  return identifier + '.resetLocalizerAndCalibrateIMU();\n';
};

Blockly.Blocks['octoquadLocalizerDataBlock_getProperty_LocalizerStatus'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['localizerStatus', 'localizerStatus'],
    ];
    this.setOutput(true, 'OctoQuad.LocalizerStatus');
    this.appendDummyInput()
        .appendField(createNonEditableField('OctoQuad.LocalizerDataBlock'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('LOCALIZER_DATA_BLOCK').setCheck('OctoQuad.LocalizerDataBlock')
        .appendField('localizerDataBlock')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['localizerStatus', 'Returns the localizerStatus field of the OctoQuad.LocalizerDataBlock.'],
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

Blockly.JavaScript['octoquadLocalizerDataBlock_getProperty_LocalizerStatus'] = function(block) {
  var property = block.getFieldValue('PROP');
  var localizerDataBlock = Blockly.JavaScript.valueToCode(
      block, 'LOCALIZER_DATA_BLOCK', Blockly.JavaScript.ORDER_MEMBER);
  var code = localizerDataBlock + '.' + property;
  var blockLabel = 'OctoQuad.LocalizerDataBlock.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['octoquadLocalizerDataBlock_getProperty_LocalizerStatus'] = function(block) {
  var property = block.getFieldValue('PROP');
  var localizerDataBlock = Blockly.FtcJava.valueToCode(
      block, 'LOCALIZER_DATA_BLOCK', Blockly.FtcJava.ORDER_MEMBER);
  var code = localizerDataBlock + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['octoquadLocalizerDataBlock_getProperty_Boolean'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['crcOk', 'crcOk'],
    ];
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField(createNonEditableField('OctoQuad.LocalizerDataBlock'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('LOCALIZER_DATA_BLOCK').setCheck('OctoQuad.LocalizerDataBlock')
        .appendField('localizerDataBlock')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['crcOk', 'Returns the crcOk field of the OctoQuad.LocalizerDataBlock.'],
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

Blockly.JavaScript['octoquadLocalizerDataBlock_getProperty_Boolean'] = function(block) {
  var property = block.getFieldValue('PROP');
  var localizerDataBlock = Blockly.JavaScript.valueToCode(
      block, 'LOCALIZER_DATA_BLOCK', Blockly.JavaScript.ORDER_MEMBER);
  var code = localizerDataBlock + '.' + property;
  var blockLabel = 'OctoQuad.LocalizerDataBlock.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['octoquadLocalizerDataBlock_getProperty_Boolean'] = function(block) {
  var property = block.getFieldValue('PROP');
  var localizerDataBlock = Blockly.FtcJava.valueToCode(
      block, 'LOCALIZER_DATA_BLOCK', Blockly.FtcJava.ORDER_MEMBER);
  var code = localizerDataBlock + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['octoquadLocalizerDataBlock_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['heading_rad', 'heading_rad'],
        ['posX_mm', 'posX_mm'],
        ['posY_mm', 'posY_mm'],
        ['velX_mmS', 'velX_mmS'],
        ['velY_mmS', 'velY_mmS'],
        ['velHeading_radS', 'velHeading_radS'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('OctoQuad.LocalizerDataBlock'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('LOCALIZER_DATA_BLOCK').setCheck('OctoQuad.LocalizerDataBlock')
        .appendField('localizerDataBlock')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['heading_rad', 'Returns the heading_rad field of the OctoQuad.LocalizerDataBlock.'],
        ['posX_mm', 'Returns the posX_mm field of the OctoQuad.LocalizerDataBlock.'],
        ['posY_mm', 'Returns the posY_mm field of the OctoQuad.LocalizerDataBlock.'],
        ['velX_mmS', 'Returns the velX_mmS field of the OctoQuad.LocalizerDataBlock.'],
        ['velY_mmS', 'Returns the velY_mmS field of the OctoQuad.LocalizerDataBlock.'],
        ['velHeading_radS', 'Returns the velHeading_radS field of the OctoQuad.LocalizerDataBlock.'],
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
        case 'posX_mm':
        case 'posY_mm':
        case 'velX_mmS':
        case 'velY_mmS':
          return 'short';
        case 'heading_rad':
        case 'velHeading_radS':
          return 'float';
        default:
          throw 'Unexpected property ' + property + ' (octoquadLocalizerDataBlock_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['octoquadLocalizerDataBlock_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var localizerDataBlock = Blockly.JavaScript.valueToCode(
      block, 'LOCALIZER_DATA_BLOCK', Blockly.JavaScript.ORDER_MEMBER);
  var code = localizerDataBlock + '.' + property;
  var blockLabel = 'OctoQuad.LocalizerDataBlock.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['octoquadLocalizerDataBlock_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var localizerDataBlock = Blockly.FtcJava.valueToCode(
      block, 'LOCALIZER_DATA_BLOCK', Blockly.FtcJava.ORDER_MEMBER);
  var code = localizerDataBlock + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['octoquadLocalizerDataBlock_isDataValid'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('OctoQuad.LocalizerDataBlock'))
        .appendField('.')
        .appendField(createNonEditableField('isDataValid'));
    this.appendValueInput('LOCALIZER_DATA_BLOCK').setCheck('OctoQuad.LocalizerDataBlock')
        .appendField('localizerDataBlock')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Checks whether it is likely that this data is valid. The localizer status is read ' +
        'along with the data, and if the status is not RUNNING, then the data invalid. ' +
        'Additionally, if the CRC on the returned is bad, (e.g. if there is an I2C bus stall ' +
        'or bit flip), you could avoid acting on that corrupted data. ' +
        'Returns whether it is likely that this data is valid.');
  }
};

Blockly.JavaScript['octoquadLocalizerDataBlock_isDataValid'] = function(block) {
  var localizerDataBlock = Blockly.JavaScript.valueToCode(
      block, 'LOCALIZER_DATA_BLOCK', Blockly.JavaScript.ORDER_MEMBER);
  var code = localizerDataBlock + '.isDataValid';
  var blockLabel = 'call OctoQuad.LocalizerDataBlock.isDataValid';
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['octoquadLocalizerDataBlock_isDataValid'] = function(block) {
  var localizerDataBlock = Blockly.FtcJava.valueToCode(
      block, 'LOCALIZER_DATA_BLOCK', Blockly.FtcJava.ORDER_MEMBER);
  var code = localizerDataBlock + '.isDataValid()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};


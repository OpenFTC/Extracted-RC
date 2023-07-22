// Copyright 2023 FIRST

/**
 * @fileoverview FTC robot blocks related to WhiteBalanceControl.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// whiteBalanceControlIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor


// functions

Blockly.Blocks['whiteBalanceControl_getMode'] = {
  init: function() {
    this.setOutput(true, 'WhiteBalanceControl.Mode');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('WhiteBalanceControl'))
        .appendField('.')
        .appendField(createNonEditableField('getMode'));
    this.appendValueInput('WHITE_BALANCE_CONTROL').setCheck('WhiteBalanceControl')
        .appendField('whiteBalanceControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the current white balance mode value.');
  }
};

Blockly.JavaScript['whiteBalanceControl_getMode'] = function(block) {
  var whiteBalanceControl = Blockly.JavaScript.valueToCode(
      block, 'WHITE_BALANCE_CONTROL', Blockly.JavaScript.ORDER_NONE);
  var code = whiteBalanceControlIdentifierForJavaScript + '.getMode(' + whiteBalanceControl + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['whiteBalanceControl_getMode'] = function(block) {
  var whiteBalanceControl = Blockly.FtcJava.valueToCode(
      block, 'WHITE_BALANCE_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var code = whiteBalanceControl + '.getMode()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['whiteBalanceControl_setMode'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('WhiteBalanceControl'))
        .appendField('.')
        .appendField(createNonEditableField('setMode'));
    this.appendValueInput('WHITE_BALANCE_CONTROL').setCheck('WhiteBalanceControl')
        .appendField('whiteBalanceControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MODE').setCheck('WhiteBalanceControl.Mode')
        .appendField('mode')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    this.setTooltip(function() {
      return 'Sets the white balance mode value.' + thisBlock.getTooltipSuffix();
    });
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['whiteBalanceControl_setMode'] = function(block) {
  var whiteBalanceControl = Blockly.JavaScript.valueToCode(
      block, 'WHITE_BALANCE_CONTROL', Blockly.JavaScript.ORDER_COMMA);
  var mode = Blockly.JavaScript.valueToCode(
      block, 'MODE', Blockly.JavaScript.ORDER_COMMA);
  var code = whiteBalanceControlIdentifierForJavaScript + '.setMode(' +
      whiteBalanceControl + ', ' + mode + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['whiteBalanceControl_setMode'] = function(block) {
  var whiteBalanceControl = Blockly.FtcJava.valueToCode(
      block, 'WHITE_BALANCE_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var mode = Blockly.FtcJava.valueToCode(
      block, 'MODE', Blockly.FtcJava.ORDER_NONE);
  var code = whiteBalanceControl + '.setMode(' + mode + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['whiteBalanceControl_getWhiteBalanceTemperature'] = {
  init: function() {
    var FUNCTION_CHOICES = [
        ['getWhiteBalanceTemperature', 'getWhiteBalanceTemperature'],
        ['getMinWhiteBalanceTemperature', 'getMinWhiteBalanceTemperature'],
        ['getMaxWhiteBalanceTemperature', 'getMaxWhiteBalanceTemperature'],
        ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('WhiteBalanceControl'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(FUNCTION_CHOICES), 'FUNC');
    this.appendValueInput('WHITE_BALANCE_CONTROL').setCheck('WhiteBalanceControl')
        .appendField('whiteBalanceControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['getWhiteBalanceTemperature', 'Returns the current white balance temperature, in degrees Kelvin.'],
        ['getMinWhiteBalanceTemperature', 'Returns the minimum white balance temperature, in degrees Kelvin.'],
        ['getMaxWhiteBalanceTemperature', 'Returns the maximum white balance temperature, in degrees Kelvin.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('FUNC');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
    this.getFtcJavaOutputType = function() {
      return 'int';
    };
  }
};

Blockly.JavaScript['whiteBalanceControl_getWhiteBalanceTemperature'] = function(block) {
  var func = block.getFieldValue('FUNC');
  var whiteBalanceControl = Blockly.JavaScript.valueToCode(
      block, 'WHITE_BALANCE_CONTROL', Blockly.JavaScript.ORDER_NONE);
  var code = whiteBalanceControlIdentifierForJavaScript + '.' + func + '(' +
      whiteBalanceControl + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['whiteBalanceControl_getWhiteBalanceTemperature'] = function(block) {
  var func = block.getFieldValue('FUNC');
  var whiteBalanceControl = Blockly.FtcJava.valueToCode(
      block, 'WHITE_BALANCE_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var code = whiteBalanceControl + '.' + func + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['whiteBalanceControl_setWhiteBalanceTemperature'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('WhiteBalanceControl'))
        .appendField('.')
        .appendField(createNonEditableField('setWhiteBalanceTemperature'));
    this.appendValueInput('WHITE_BALANCE_CONTROL').setCheck('WhiteBalanceControl')
        .appendField('whiteBalanceControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('WHITE_BALANCE_TEMPERATURE').setCheck('Number')
        .appendField('whiteBalanceTemperature')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    this.setTooltip(function() {
      return 'Sets the white balance temperature, in degrees Kelvin.' + thisBlock.getTooltipSuffix();
    });
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'WHITE_BALANCE_TEMPERATURE':
          return 'int';
      }
      return '';
    };
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['whiteBalanceControl_setWhiteBalanceTemperature'] = function(block) {
  var whiteBalanceControl = Blockly.JavaScript.valueToCode(
      block, 'WHITE_BALANCE_CONTROL', Blockly.JavaScript.ORDER_COMMA);
  var whiteBalanceTemperature = Blockly.JavaScript.valueToCode(
      block, 'WHITE_BALANCE_TEMPERATURE', Blockly.JavaScript.ORDER_COMMA);
  var code = whiteBalanceControlIdentifierForJavaScript + '.setWhiteBalanceTemperature(' +
      whiteBalanceControl + ', ' + whiteBalanceTemperature + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['whiteBalanceControl_setWhiteBalanceTemperature'] = function(block) {
  var whiteBalanceControl = Blockly.FtcJava.valueToCode(
      block, 'WHITE_BALANCE_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var whiteBalanceTemperature = Blockly.FtcJava.valueToCode(
      block, 'WHITE_BALANCE_TEMPERATURE', Blockly.FtcJava.ORDER_COMMA);
  var code = whiteBalanceControl + '.setWhiteBalanceTemperature(' + whiteBalanceTemperature + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

// Enums

Blockly.Blocks['whiteBalanceControl_typedEnum_mode'] = {
  init: function() {
    var MODE_CHOICES = [
        ['AUTO', 'AUTO'],
        ['MANUAL', 'MANUAL'],
        ['UNKNOWN', 'UNKNOWN'],
    ];
    this.setOutput(true, 'WhiteBalanceControl.Mode');
    this.appendDummyInput()
        .appendField(createNonEditableField('WhiteBalanceControl.Mode'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(MODE_CHOICES), 'MODE');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['AUTO', 'The WhiteBalanceControl.Mode value AUTO.'],
        ['MANUAL', 'The WhiteBalanceControl.Mode value MANUAL.'],
        ['UNKNOWN', 'The WhiteBalanceControl.Mode value UNKNOWN.'],
        ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('MODE');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['whiteBalanceControl_typedEnum_mode'] = function(block) {
  var code = '"' + block.getFieldValue('MODE') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['whiteBalanceControl_typedEnum_mode'] = function(block) {
  var code = 'WhiteBalanceControl.Mode.' + block.getFieldValue('MODE');
  Blockly.FtcJava.generateImport_('WhiteBalanceControl');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

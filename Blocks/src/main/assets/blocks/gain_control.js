// Copyright 2023 FIRST

/**
 * @fileoverview FTC robot blocks related to GainControl.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// gainControlIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor


// functions

Blockly.Blocks['gainControl_getGain'] = {
  init: function() {
    var FUNCTION_CHOICES = [
        ['getGain', 'getGain'],
        ['getMinGain', 'getMinGain'],
        ['getMaxGain', 'getMaxGain'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('GainControl'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(FUNCTION_CHOICES), 'FUNC');
    this.appendValueInput('GAIN_CONTROL').setCheck('GainControl')
        .appendField('gainControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['getGain', 'Returns the current gain.'],
        ['getMinGain', 'Returns the minimum gain.'],
        ['getMaxGain', 'Returns the maximum gain.'],
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

Blockly.JavaScript['gainControl_getGain'] = function(block) {
  var func = block.getFieldValue('FUNC');
  var gainControl = Blockly.JavaScript.valueToCode(
      block, 'GAIN_CONTROL', Blockly.JavaScript.ORDER_NONE);
  var code = gainControlIdentifierForJavaScript + '.' + func + '(' +
      gainControl + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['gainControl_getGain'] = function(block) {
  var func = block.getFieldValue('FUNC');
  var gainControl = Blockly.FtcJava.valueToCode(
      block, 'GAIN_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var code = gainControl + '.' + func + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['gainControl_setGain'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('GainControl'))
        .appendField('.')
        .appendField(createNonEditableField('setGain'));
    this.appendValueInput('GAIN_CONTROL').setCheck('GainControl')
        .appendField('gainControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('GAIN').setCheck('Number')
        .appendField('gain')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    this.setTooltip(function() {
      return 'Sets the gain.' + thisBlock.getTooltipSuffix();
    });
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'GAIN':
          return 'int';
      }
      return '';
    };
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['gainControl_setGain'] = function(block) {
  var gainControl = Blockly.JavaScript.valueToCode(
      block, 'GAIN_CONTROL', Blockly.JavaScript.ORDER_COMMA);
  var gain = Blockly.JavaScript.valueToCode(
      block, 'GAIN', Blockly.JavaScript.ORDER_COMMA);
  var code = gainControlIdentifierForJavaScript + '.setGain(' +
      gainControl + ', ' + gain + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['gainControl_setGain'] = function(block) {
  var gainControl = Blockly.FtcJava.valueToCode(
      block, 'GAIN_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var gain = Blockly.FtcJava.valueToCode(
      block, 'GAIN', Blockly.FtcJava.ORDER_NONE);
  var code = gainControl + '.setGain(' + gain + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

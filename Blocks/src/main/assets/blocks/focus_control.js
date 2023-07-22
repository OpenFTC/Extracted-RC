// Copyright 2023 FIRST

/**
 * @fileoverview FTC robot blocks related to FocusControl.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// focusControlIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor


// functions

Blockly.Blocks['focusControl_getMode'] = {
  init: function() {
    this.setOutput(true, 'FocusControl.Mode');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('FocusControl'))
        .appendField('.')
        .appendField(createNonEditableField('getMode'));
    this.appendValueInput('FOCUS_CONTROL').setCheck('FocusControl')
        .appendField('focusControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the current focus mode value.');
  }
};

Blockly.JavaScript['focusControl_getMode'] = function(block) {
  var focusControl = Blockly.JavaScript.valueToCode(
      block, 'FOCUS_CONTROL', Blockly.JavaScript.ORDER_NONE);
  var code = focusControlIdentifierForJavaScript + '.getMode(' + focusControl + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['focusControl_getMode'] = function(block) {
  var focusControl = Blockly.FtcJava.valueToCode(
      block, 'FOCUS_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var code = focusControl + '.getMode()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['focusControl_setMode'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('FocusControl'))
        .appendField('.')
        .appendField(createNonEditableField('setMode'));
    this.appendValueInput('FOCUS_CONTROL').setCheck('FocusControl')
        .appendField('focusControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MODE').setCheck('FocusControl.Mode')
        .appendField('mode')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    this.setTooltip(function() {
      return 'Sets the focus mode value.' + thisBlock.getTooltipSuffix();
    });
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['focusControl_setMode'] = function(block) {
  var focusControl = Blockly.JavaScript.valueToCode(
      block, 'FOCUS_CONTROL', Blockly.JavaScript.ORDER_COMMA);
  var mode = Blockly.JavaScript.valueToCode(
      block, 'MODE', Blockly.JavaScript.ORDER_COMMA);
  var code = focusControlIdentifierForJavaScript + '.setMode(' +
      focusControl + ', ' + mode + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['focusControl_setMode'] = function(block) {
  var focusControl = Blockly.FtcJava.valueToCode(
      block, 'FOCUS_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var mode = Blockly.FtcJava.valueToCode(
      block, 'MODE', Blockly.FtcJava.ORDER_NONE);
  var code = focusControl + '.setMode(' + mode + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['focusControl_isModeSupported'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('FocusControl'))
        .appendField('.')
        .appendField(createNonEditableField('isModeSupported'));
    this.appendValueInput('FOCUS_CONTROL').setCheck('FocusControl')
        .appendField('focusControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MODE').setCheck('FocusControl.Mode')
        .appendField('mode')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns whether the given FocusControl.Mode is supported.');
  }
};

Blockly.JavaScript['focusControl_isModeSupported'] = function(block) {
  var focusControl = Blockly.JavaScript.valueToCode(
      block, 'FOCUS_CONTROL', Blockly.JavaScript.ORDER_COMMA);
  var mode = Blockly.JavaScript.valueToCode(
      block, 'MODE', Blockly.JavaScript.ORDER_COMMA);
  var code = focusControlIdentifierForJavaScript + '.isModeSupported(' +
      focusControl + ', ' + mode + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['focusControl_isModeSupported'] = function(block) {
  var focusControl = Blockly.FtcJava.valueToCode(
      block, 'FOCUS_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var mode = Blockly.FtcJava.valueToCode(
      block, 'MODE', Blockly.FtcJava.ORDER_NONE);
  var code = focusControl + '.isModeSupported(' + mode + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['focusControl_getFocusLength'] = {
  init: function() {
    var FUNCTION_CHOICES = [
        ['getFocusLength', 'getFocusLength'],
        ['getMinFocusLength', 'getMinFocusLength'],
        ['getMaxFocusLength', 'getMaxFocusLength'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('FocusControl'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(FUNCTION_CHOICES), 'FUNC');
    this.appendValueInput('FOCUS_CONTROL').setCheck('FocusControl')
        .appendField('focusControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['getFocusLength', 'Returns the current focus length, or < 0 if not available.'],
        ['getMinFocusLength', 'Returns the minimum focus length, or < 0 if not available.'],
        ['getMaxFocusLength', 'Returns the maximum focus length, or < 0 if not available.'],
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
      return 'double';
    };
  }
};

Blockly.JavaScript['focusControl_getFocusLength'] = function(block) {
  var func = block.getFieldValue('FUNC');
  var focusControl = Blockly.JavaScript.valueToCode(
      block, 'FOCUS_CONTROL', Blockly.JavaScript.ORDER_NONE);
  var code = focusControlIdentifierForJavaScript + '.' + func + '(' +
      focusControl + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['focusControl_getFocusLength'] = function(block) {
  var func = block.getFieldValue('FUNC');
  var focusControl = Blockly.FtcJava.valueToCode(
      block, 'FOCUS_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var code = focusControl + '.' + func + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['focusControl_setFocusLength'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('FocusControl'))
        .appendField('.')
        .appendField(createNonEditableField('setFocusLength'));
    this.appendValueInput('FOCUS_CONTROL').setCheck('FocusControl')
        .appendField('focusControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('FOCUS_LENGTH').setCheck('Number')
        .appendField('focusLength')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    this.setTooltip(function() {
      return 'Sets the focus length.' + thisBlock.getTooltipSuffix();
    });
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'FOCUS_LENGTH':
          return 'double';
      }
      return '';
    };
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['focusControl_setFocusLength'] = function(block) {
  var focusControl = Blockly.JavaScript.valueToCode(
      block, 'FOCUS_CONTROL', Blockly.JavaScript.ORDER_COMMA);
  var focusLength = Blockly.JavaScript.valueToCode(
      block, 'FOCUS_LENGTH', Blockly.JavaScript.ORDER_COMMA);
  var code = focusControlIdentifierForJavaScript + '.setFocusLength(' +
      focusControl + ', ' + focusLength + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['focusControl_setFocusLength'] = function(block) {
  var focusControl = Blockly.FtcJava.valueToCode(
      block, 'FOCUS_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var focusLength = Blockly.FtcJava.valueToCode(
      block, 'FOCUS_LENGTH', Blockly.FtcJava.ORDER_COMMA);
  var code = focusControl + '.setFocusLength(' + focusLength + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['focusControl_isFocusLengthSupported'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('FocusControl'))
        .appendField('.')
        .appendField(createNonEditableField('isFocusLengthSupported'));
    this.appendValueInput('FOCUS_CONTROL').setCheck('FocusControl')
        .appendField('focusControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns true if focus is supported.');
  }
};

Blockly.JavaScript['focusControl_isFocusLengthSupported'] = function(block) {
  var focusControl = Blockly.JavaScript.valueToCode(
      block, 'FOCUS_CONTROL', Blockly.JavaScript.ORDER_NONE);
  var code = focusControlIdentifierForJavaScript + '.isFocusLengthSupported(' +
      focusControl + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['focusControl_isFocusLengthSupported'] = function(block) {
  var focusControl = Blockly.FtcJava.valueToCode(
      block, 'FOCUS_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var code = focusControl + '.isFocusLengthSupported()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// Enums

Blockly.Blocks['focusControl_typedEnum_mode'] = {
  init: function() {
    var MODE_CHOICES = [
        ['Auto', 'Auto'],
        ['ContinuousAuto', 'ContinuousAuto'],
        ['Macro', 'Macro'],
        ['Infinity', 'Infinity'],
        ['Fixed', 'Fixed'],
        ['Unknown', 'Unknown'],
    ];
    this.setOutput(true, 'FocusControl.Mode');
    this.appendDummyInput()
        .appendField(createNonEditableField('FocusControl.Mode'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(MODE_CHOICES), 'MODE');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Auto', 'The FocusControl.Mode value Auto.'],
        ['ContinuousAuto', 'The FocusControl.Mode value ContinuousAuto.'],
        ['Macro', 'The FocusControl.Mode value Macro.'],
        ['Infinity', 'The FocusControl.Mode value Infinity.'],
        ['Fixed', 'The FocusControl.Mode value Fixed.'],
        ['Unknown', 'The FocusControl.Mode value Unknown.'],
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

Blockly.JavaScript['focusControl_typedEnum_mode'] = function(block) {
  var code = '"' + block.getFieldValue('MODE') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['focusControl_typedEnum_mode'] = function(block) {
  var code = 'FocusControl.Mode.' + block.getFieldValue('MODE');
  Blockly.FtcJava.generateImport_('FocusControl');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

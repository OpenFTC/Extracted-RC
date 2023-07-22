// Copyright 2023 FIRST

/**
 * @fileoverview FTC robot blocks related to ExposureControl.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// exposureControlIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor

// Functions

Blockly.Blocks['exposureControl_getMode'] = {
  init: function() {
    this.setOutput(true, 'ExposureControl.Mode');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ExposureControl'))
        .appendField('.')
        .appendField(createNonEditableField('getMode'));
    this.appendValueInput('EXPOSURE_CONTROL').setCheck('ExposureControl')
        .appendField('exposureControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the current exposure mode value.');
  }
};

Blockly.JavaScript['exposureControl_getMode'] = function(block) {
  var exposureControl = Blockly.JavaScript.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.JavaScript.ORDER_NONE);
  var code = exposureControlIdentifierForJavaScript + '.getMode(' + exposureControl + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['exposureControl_getMode'] = function(block) {
  var exposureControl = Blockly.FtcJava.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var code = exposureControl + '.getMode()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['exposureControl_setMode'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ExposureControl'))
        .appendField('.')
        .appendField(createNonEditableField('setMode'));
    this.appendValueInput('EXPOSURE_CONTROL').setCheck('ExposureControl')
        .appendField('exposureControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MODE').setCheck('ExposureControl.Mode')
        .appendField('mode')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    this.setTooltip(function() {
      return 'Sets the exposure mode value.' + thisBlock.getTooltipSuffix();
    });
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['exposureControl_setMode'] = function(block) {
  var exposureControl = Blockly.JavaScript.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.JavaScript.ORDER_COMMA);
  var mode = Blockly.JavaScript.valueToCode(
      block, 'MODE', Blockly.JavaScript.ORDER_COMMA);
  var code = exposureControlIdentifierForJavaScript + '.setMode(' +
      exposureControl + ', ' + mode + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['exposureControl_setMode'] = function(block) {
  var exposureControl = Blockly.FtcJava.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var mode = Blockly.FtcJava.valueToCode(
      block, 'MODE', Blockly.FtcJava.ORDER_NONE);
  var code = exposureControl + '.setMode(' + mode + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['exposureControl_isModeSupported'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ExposureControl'))
        .appendField('.')
        .appendField(createNonEditableField('isModeSupported'));
    this.appendValueInput('EXPOSURE_CONTROL').setCheck('ExposureControl')
        .appendField('exposureControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MODE').setCheck('ExposureControl.Mode')
        .appendField('mode')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns true if the given ExposureControl.Mode is supported.');
  }
};

Blockly.JavaScript['exposureControl_isModeSupported'] = function(block) {
  var exposureControl = Blockly.JavaScript.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.JavaScript.ORDER_COMMA);
  var mode = Blockly.JavaScript.valueToCode(
      block, 'MODE', Blockly.JavaScript.ORDER_COMMA);
  var code = exposureControlIdentifierForJavaScript + '.isModeSupported(' +
      exposureControl + ', ' + mode + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['exposureControl_isModeSupported'] = function(block) {
  var exposureControl = Blockly.FtcJava.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var mode = Blockly.FtcJava.valueToCode(
      block, 'MODE', Blockly.FtcJava.ORDER_NONE);
  var code = exposureControl + '.isModeSupported(' + mode + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['exposureControl_getExposure'] = {
  init: function() {
    var FUNCTION_CHOICES = [
        ['getExposure', 'getExposure'],
        ['getMinExposure', 'getMinExposure'],
        ['getMaxExposure', 'getMaxExposure'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ExposureControl'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(FUNCTION_CHOICES), 'FUNC');
    this.appendValueInput('EXPOSURE_CONTROL').setCheck('ExposureControl')
        .appendField('exposureControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TIME_UNIT').setCheck('TimeUnit')
        .appendField('timeUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['getExposure', 'Returns the current exposure, or 0 if not available.'],
        ['getMinExposure', 'Returns the minimum exposure, or 0 if not available.'],
        ['getMaxExposure', 'Returns the maximum exposure, or 0 if not available.'],
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
      return 'long';
    };
  }
};

Blockly.JavaScript['exposureControl_getExposure'] = function(block) {
  var func = block.getFieldValue('FUNC');
  var exposureControl = Blockly.JavaScript.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.JavaScript.ORDER_COMMA);
  var timeUnit = Blockly.JavaScript.valueToCode(
      block, 'TIME_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = exposureControlIdentifierForJavaScript + '.' + func + '(' +
      exposureControl + ', ' + timeUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['exposureControl_getExposure'] = function(block) {
  var func = block.getFieldValue('FUNC');
  var exposureControl = Blockly.FtcJava.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var timeUnit = Blockly.FtcJava.valueToCode(
      block, 'TIME_UNIT', Blockly.FtcJava.ORDER_NONE);
  var code = exposureControl + '.' + func + '(' + timeUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['exposureControl_setExposure'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ExposureControl'))
        .appendField('.')
        .appendField(createNonEditableField('setExposure'));
    this.appendValueInput('EXPOSURE_CONTROL').setCheck('ExposureControl')
        .appendField('exposureControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DURATION').setCheck('Number')
        .appendField('duration')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TIME_UNIT').setCheck('TimeUnit')
        .appendField('timeUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    this.setTooltip(function() {
      return 'Sets the exposure.' + thisBlock.getTooltipSuffix();
    });
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'DURATION':
          return 'long';
      }
      return '';
    };
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['exposureControl_setExposure'] = function(block) {
  var exposureControl = Blockly.JavaScript.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.JavaScript.ORDER_COMMA);
  var duration = Blockly.JavaScript.valueToCode(
      block, 'DURATION', Blockly.JavaScript.ORDER_COMMA);
  var timeUnit = Blockly.JavaScript.valueToCode(
      block, 'TIME_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = exposureControlIdentifierForJavaScript + '.setExposure(' +
      exposureControl + ', ' + duration + ', ' + timeUnit + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['exposureControl_setExposure'] = function(block) {
  var exposureControl = Blockly.FtcJava.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var duration = Blockly.FtcJava.valueToCode(
      block, 'DURATION', Blockly.FtcJava.ORDER_COMMA);
  var timeUnit = Blockly.FtcJava.valueToCode(
      block, 'TIME_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = exposureControl + '.setExposure(' + duration + ', ' + timeUnit + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['exposureControl_isExposureSupported'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ExposureControl'))
        .appendField('.')
        .appendField(createNonEditableField('isExposureSupported'));
    this.appendValueInput('EXPOSURE_CONTROL').setCheck('ExposureControl')
        .appendField('exposureControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns true if exposure is supported.');
  }
};

Blockly.JavaScript['exposureControl_isExposureSupported'] = function(block) {
  var exposureControl = Blockly.JavaScript.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.JavaScript.ORDER_NONE);
  var code = exposureControlIdentifierForJavaScript + '.isExposureSupported(' +
      exposureControl + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['exposureControl_isExposureSupported'] = function(block) {
  var exposureControl = Blockly.FtcJava.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var code = exposureControl + '.isExposureSupported()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['exposureControl_getAePriority'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ExposureControl'))
        .appendField('.')
        .appendField(createNonEditableField('getAePriority'));
    this.appendValueInput('EXPOSURE_CONTROL').setCheck('ExposureControl')
        .appendField('exposureControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the AE priority.');
  }
};

Blockly.JavaScript['exposureControl_getAePriority'] = function(block) {
  var exposureControl = Blockly.JavaScript.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.JavaScript.ORDER_NONE);
  var code = exposureControlIdentifierForJavaScript + '.getAePriority(' +
      exposureControl + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['exposureControl_getAePriority'] = function(block) {
  var exposureControl = Blockly.FtcJava.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var code = exposureControl + '.getAePriority()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['exposureControl_setAePriority'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ExposureControl'))
        .appendField('.')
        .appendField(createNonEditableField('setAePriority'));
    this.appendValueInput('EXPOSURE_CONTROL').setCheck('ExposureControl')
        .appendField('exposureControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PRIORITY').setCheck('Boolean')
        .appendField('priority')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    this.setTooltip(function() {
      return 'Sets the AE priority.' + thisBlock.getTooltipSuffix();
    });
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['exposureControl_setAePriority'] = function(block) {
  var exposureControl = Blockly.JavaScript.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.JavaScript.ORDER_COMMA);
  var priority = Blockly.JavaScript.valueToCode(
      block, 'PRIORITY', Blockly.JavaScript.ORDER_COMMA);
  var code = exposureControlIdentifierForJavaScript + '.setAePriority(' +
      exposureControl + ', ' + priority + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['exposureControl_setAePriority'] = function(block) {
  var exposureControl = Blockly.FtcJava.valueToCode(
      block, 'EXPOSURE_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var priority = Blockly.FtcJava.valueToCode(
      block, 'PRIORITY', Blockly.FtcJava.ORDER_NONE);
  var code = exposureControl + '.setAePriority(' + priority + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

// Enums

Blockly.Blocks['exposureControl_typedEnum_mode'] = {
  init: function() {
    var MODE_CHOICES = [
        ['Auto', 'Auto'],
        ['ContinuousAuto', 'ContinuousAuto'],
        ['Manual', 'Manual'],
        ['ShutterPriority', 'ShutterPriority'],
        ['AperturePriority', 'AperturePriority'],
        ['Unknown', 'Unknown'],
    ];
    this.setOutput(true, 'ExposureControl.Mode');
    this.appendDummyInput()
        .appendField(createNonEditableField('ExposureControl.Mode'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(MODE_CHOICES), 'MODE');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Auto', 'The ExposureControl.Mode value Auto.'],
        ['ContinuousAuto', 'The ExposureControl.Mode value ContinuousAuto.'],
        ['Manual', 'The ExposureControl.Mode value Manual.'],
        ['ShutterPriority', 'The ExposureControl.Mode value ShutterPriority.'],
        ['AperturePriority', 'The ExposureControl.Mode value AperturePriority.'],
        ['Unknown', 'The ExposureControl.Mode value Unknown.'],
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

Blockly.JavaScript['exposureControl_typedEnum_mode'] = function(block) {
  var code = '"' + block.getFieldValue('MODE') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['exposureControl_typedEnum_mode'] = function(block) {
  var code = 'ExposureControl.Mode.' + block.getFieldValue('MODE');
  Blockly.FtcJava.generateImport_('ExposureControl');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

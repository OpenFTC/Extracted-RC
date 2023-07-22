// Copyright 2023 FIRST

/**
 * @fileoverview FTC robot blocks related to PtzControl.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// ptzControlIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor
// setPropertyColor


// functions

Blockly.Blocks['ptzControl_getPanTilt'] = {
  init: function() {
    var FUNCTION_CHOICES = [
        ['getPanTilt', 'getPanTilt'],
        ['getMinPanTilt', 'getMinPanTilt'],
        ['getMaxPanTilt', 'getMaxPanTilt'],
        ];
    this.setOutput(true, 'PtzControl.PanTiltHolder');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('PtzControl'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(FUNCTION_CHOICES), 'FUNC');
    this.appendValueInput('PTZ_CONTROL').setCheck('PtzControl')
        .appendField('ptzControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['getPanTilt', 'Returns the current virtual pan/tilt.'],
        ['getMinPanTilt', 'Returns the minimum virtual pan/tilt.'],
        ['getMaxPanTilt', 'Returns the maximum virtual pan/tilt.'],
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
  }
};

Blockly.JavaScript['ptzControl_getPanTilt'] = function(block) {
  var func = block.getFieldValue('FUNC');
  var ptzControl = Blockly.JavaScript.valueToCode(
      block, 'PTZ_CONTROL', Blockly.JavaScript.ORDER_NONE);
  var code = 'JSON.parse(' + ptzControlIdentifierForJavaScript + '.' + func + '(' +
      ptzControl + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['ptzControl_getPanTilt'] = function(block) {
  var func = block.getFieldValue('FUNC');
  var ptzControl = Blockly.FtcJava.valueToCode(
      block, 'PTZ_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var code = ptzControl + '.' + func + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['ptzControl_setPanTilt'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('PtzControl'))
        .appendField('.')
        .appendField(createNonEditableField('setPanTilt'));
    this.appendValueInput('PTZ_CONTROL').setCheck('PtzControl')
        .appendField('ptzControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PAN_TILT_HOLDER').setCheck('PtzControl.PanTiltHolder')
        .appendField('panTiltHolder')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    this.setTooltip(function() {
      return 'Sets the virtual pan/tilt.' + thisBlock.getTooltipSuffix();
    });
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['ptzControl_setPanTilt'] = function(block) {
  var ptzControl = Blockly.JavaScript.valueToCode(
      block, 'PTZ_CONTROL', Blockly.JavaScript.ORDER_COMMA);
  var panTiltHolder = Blockly.JavaScript.valueToCode(
      block, 'PAN_TILT_HOLDER', Blockly.JavaScript.ORDER_COMMA);
  var code = ptzControlIdentifierForJavaScript + '.setPanTilt(' +
      ptzControl + ', JSON.stringify(' + panTiltHolder + '))';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['ptzControl_setPanTilt'] = function(block) {
  var ptzControl = Blockly.FtcJava.valueToCode(
      block, 'PTZ_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var panTiltHolder = Blockly.FtcJava.valueToCode(
      block, 'PAN_TILT_HOLDER', Blockly.FtcJava.ORDER_NONE);
  var code = ptzControl + '.setPanTilt(' + panTiltHolder + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['ptzControl_getZoom'] = {
  init: function() {
    var FUNCTION_CHOICES = [
        ['getZoom', 'getZoom'],
        ['getMinZoom', 'getMinZoom'],
        ['getMaxZoom', 'getMaxZoom'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('PtzControl'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(FUNCTION_CHOICES), 'FUNC');
    this.appendValueInput('PTZ_CONTROL').setCheck('PtzControl')
        .appendField('ptzControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['getZoom', 'Returns the current zoom.'],
        ['getMinZoom', 'Returns the minimum zoom.'],
        ['getMaxZoom', 'Returns the maximum zoom.'],
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

Blockly.JavaScript['ptzControl_getZoom'] = function(block) {
  var func = block.getFieldValue('FUNC');
  var ptzControl = Blockly.JavaScript.valueToCode(
      block, 'PTZ_CONTROL', Blockly.JavaScript.ORDER_NONE);
  var code = ptzControlIdentifierForJavaScript + '.' + func + '(' +
      ptzControl + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['ptzControl_getZoom'] = function(block) {
  var func = block.getFieldValue('FUNC');
  var ptzControl = Blockly.FtcJava.valueToCode(
      block, 'PTZ_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var code = ptzControl + '.' + func + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['ptzControl_setZoom'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('PtzControl'))
        .appendField('.')
        .appendField(createNonEditableField('setZoom'));
    this.appendValueInput('PTZ_CONTROL').setCheck('PtzControl')
        .appendField('ptzControl')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ZOOM').setCheck('Number')
        .appendField('zoom')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    this.setTooltip(function() {
      return 'Sets the zoom.' + thisBlock.getTooltipSuffix();
    });
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'ZOOM':
          return 'int';
      }
      return '';
    };
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['ptzControl_setZoom'] = function(block) {
  var ptzControl = Blockly.JavaScript.valueToCode(
      block, 'PTZ_CONTROL', Blockly.JavaScript.ORDER_COMMA);
  var zoom = Blockly.JavaScript.valueToCode(
      block, 'ZOOM', Blockly.JavaScript.ORDER_COMMA);
  var code = ptzControlIdentifierForJavaScript + '.setZoom(' +
      ptzControl + ', ' + zoom + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['ptzControl_setZoom'] = function(block) {
  var ptzControl = Blockly.FtcJava.valueToCode(
      block, 'PTZ_CONTROL', Blockly.FtcJava.ORDER_MEMBER);
  var zoom = Blockly.FtcJava.valueToCode(
      block, 'ZOOM', Blockly.FtcJava.ORDER_NONE);
  var code = ptzControl + '.setZoom(' + zoom + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

// constructor

Blockly.Blocks['panTiltHolder_create'] = {
  init: function() {
    this.setOutput(true, 'PtzControl.PanTiltHolder');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('PanTiltHolder'));
    this.setColour(functionColor);
    this.setTooltip('Creates a new PanTiltHolder object.');
  }
};

Blockly.JavaScript['panTiltHolder_create'] = function(block) {
  var code = '{"pan": 0, "tilt": 0}';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['panTiltHolder_create'] = function(block) {
  var code = 'new PtzControl.PanTiltHolder()';
  Blockly.FtcJava.generateImport_('PtzControl');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

// properties

Blockly.Blocks['panTiltHolder_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['pan', 'pan'],
        ['tilt', 'tilt'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('PanTiltHolder'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('PAN_TILT_HOLDER').setCheck('PtzControl.PanTiltHolder')
        .appendField('panTiltHolder')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['pan', 'Returns the pan field of the PanTiltHolder.'],
        ['tilt', 'Returns the tilt field of the PanTiltHolder.'],
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
        case 'pan':
        case 'tilt':
          return 'int';
        default:
          throw 'Unexpected property ' + property + ' (panTiltHolder_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['panTiltHolder_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var panTiltHolder = Blockly.JavaScript.valueToCode(
      block, 'PAN_TILT_HOLDER', Blockly.JavaScript.ORDER_MEMBER);
  var code = panTiltHolder + '.' + property;
  var blockLabel = 'PanTiltHolder.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['panTiltHolder_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var panTiltHolder = Blockly.FtcJava.valueToCode(
      block, 'PAN_TILT_HOLDER', Blockly.FtcJava.ORDER_MEMBER);
  var code = panTiltHolder + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['panTiltHolder_setProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['pan', 'pan'],
        ['tilt', 'tilt'],
    ];
    this.appendDummyInput()
        .appendField('set')
        .appendField(createNonEditableField('PanTiltHolder'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('PAN_TILT_HOLDER').setCheck('PtzControl.PanTiltHolder')
        .appendField('panTiltHolder')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VALUE').setCheck('Number')
        .appendField('to')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['pan', 'Sets the pan field of the given PanTiltHolder object.'],
        ['tilt', 'Sets the tilt of the given PanTiltHolder object.'],
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
          case 'pan':
          case 'tilt':
            return 'int';
          default:
            throw 'Unexpected property ' + property + ' (panTiltHolder_setProperty_Number getArgumentType).';
        }
      }
      return '';
    };
  }
};

Blockly.JavaScript['panTiltHolder_setProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var panTiltHolder = Blockly.JavaScript.valueToCode(
      block, 'PAN_TILT_HOLDER', Blockly.JavaScript.ORDER_MEMBER);
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_ASSIGNMENT);
  return panTiltHolder + '.' + property + ' = ' + value + ';\n';
};

Blockly.FtcJava['panTiltHolder_setProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var panTiltHolder = Blockly.FtcJava.valueToCode(
      block, 'PAN_TILT_HOLDER', Blockly.FtcJava.ORDER_MEMBER);
  var value = Blockly.FtcJava.valueToCode(
      block, 'VALUE', Blockly.FtcJava.ORDER_ASSIGNMENT);
  return panTiltHolder + '.' + property + ' = ' + value + ';\n';
};


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
 * @fileoverview FTC robot blocks related to PredominantColorProcessor and it's inner classes.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// predominantColorIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor
// wrapJavaScriptCode

// PredominantColorProcessor.Builder

Blockly.Blocks['predominantColorProcessorBuilder_create_assign'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('set')
        .appendField(new Blockly.FieldVariable('myPredominantColorProcessorBuilder', null,
                                               ['PredominantColorProcessor.Builder'], 'PredominantColorProcessor.Builder'),
                     'PREDOMINANT_COLOR_PROCESSOR_BUILDER')
        .appendField('to')
        .appendField('new')
        .appendField(createNonEditableField('PredominantColorProcessor.Builder'));
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(builderColor);
    this.setTooltip('Creates a new PredominantColorProcessor.Builder object and assigns it to a variable.');
  }
};

Blockly.JavaScript['predominantColorProcessorBuilder_create_assign'] = function(block) {
  var varName = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('PREDOMINANT_COLOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  return varName + ' = ' + predominantColorIdentifierForJavaScript + '.createPredominantColorProcessorBuilder();\n';
};

Blockly.FtcJava['predominantColorProcessorBuilder_create_assign'] = function(block) {
  var varName = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('PREDOMINANT_COLOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  Blockly.FtcJava.generateImport_('PredominantColorProcessor');
  return varName + ' = new PredominantColorProcessor.Builder();\n';
};

Blockly.Blocks['predominantColorProcessorBuilder_setRoi'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myPredominantColorProcessorBuilder', null,
                                               ['PredominantColorProcessor.Builder'], 'PredominantColorProcessor.Builder'),
                     'PREDOMINANT_COLOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setRoi'));
    this.appendValueInput('ROI').setCheck('ImageRegion');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the Region of Interest on which to perform color analysis.');
  }
};

Blockly.JavaScript['predominantColorProcessorBuilder_setRoi'] = function(block) {
  var predominantColorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('PREDOMINANT_COLOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var roi = Blockly.JavaScript.valueToCode(
      block, 'ROI', Blockly.JavaScript.ORDER_COMMA);
  return predominantColorIdentifierForJavaScript + '.setRoi(' +
      predominantColorProcessorBuilder + ', ' + roi + ');\n';
};

Blockly.FtcJava['predominantColorProcessorBuilder_setRoi'] = function(block) {
  var predominantColorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('PREDOMINANT_COLOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var roi = Blockly.FtcJava.valueToCode(
      block, 'ROI', Blockly.FtcJava.ORDER_NONE);
  return predominantColorProcessorBuilder + '.setRoi(' + roi + ');\n';
};

Blockly.Blocks['predominantColorProcessorBuilder_setSwatches'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myPredominantColorProcessorBuilder', null,
                                               ['PredominantColorProcessor.Builder'], 'PredominantColorProcessor.Builder'),
                     'PREDOMINANT_COLOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setSwatches'));
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the Swatches from which a "best guess" at the shade of the ' +
                    'predominant color will be made.');
    this.itemCount_ = 3;
    this.updateShape_();
    this.setMutator(new Blockly.Mutator(['predominantColorProcessorBuilder_setSwatches_item']));
  },
  mutationToDom: function() {
    var container = Blockly.utils.xml.createElement('mutation');
    container.setAttribute('items', this.itemCount_);
    return container;
  },
  domToMutation: function(xmlElement) {
    this.itemCount_ = parseInt(xmlElement.getAttribute('items'), 10);
    this.updateShape_();
  },
  decompose: function(workspace) {
    var containerBlock = workspace.newBlock('predominantColorProcessorBuilder_setSwatches_container');
    containerBlock.initSvg();
    var connection = containerBlock.getInput('STACK').connection;
    for (var i = 0; i < this.itemCount_; i++) {
      var itemBlock = workspace.newBlock('predominantColorProcessorBuilder_setSwatches_item');
      itemBlock.initSvg();
      connection.connect(itemBlock.previousConnection);
      connection = itemBlock.nextConnection;
    }
    return containerBlock;
  },
  compose: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    // Count number of inputs.
    var connections = [];
    while (itemBlock) {
      connections.push(itemBlock.valueConnection_);
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
    // Disconnect any children that don't belong.
    for (var i = 0; i < this.itemCount_; i++) {
      var connection = this.getInput('SWATCH' + i).connection.targetConnection;
      if (connection && connections.indexOf(connection) == -1) {
        connection.disconnect();
      }
    }
    this.itemCount_ = connections.length;
    this.updateShape_();
    // Reconnect any child blocks.
    for (var i = 0; i < this.itemCount_; i++) {
      Blockly.Mutator.reconnect(connections[i], this, 'SWATCH' + i);
    }
  },
  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var i = 0;
    while (itemBlock) {
      var input = this.getInput('SWATCH' + i);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      i++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  },
  updateShape_: function() {
    /*
    if (this.itemCount_ && this.getInput('EMPTY')) {
      this.removeInput('EMPTY');
    } else if (!this.itemCount_ && !this.getInput('EMPTY')) {
      this.appendDummyInput('EMPTY')
          .appendField(Blockly.Msg['LISTS_CREATE_EMPTY_TITLE']);
    }
    */
    // Add new inputs.
    for (var i = 0; i < this.itemCount_; i++) {
      if (!this.getInput('SWATCH' + i)) {
        var input = this.appendValueInput('SWATCH' + i);
        /*
        if (i == 0) {
          input.appendField(Blockly.Msg['LISTS_CREATE_WITH_INPUT_WITH']);
        }
        */
      }
    }
    // Remove deleted inputs.
    while (this.getInput('SWATCH' + i)) {
      this.removeInput('SWATCH' + i);
      i++;
    }
  }
};

Blockly.Blocks['predominantColorProcessorBuilder_setSwatches_container'] = {
  /**
   * Mutator block for list container.
   * @this Blockly.Block
   */
  init: function() {
    this.setStyle('list_blocks');
    this.appendDummyInput()
        .appendField('');
    this.appendStatementInput('STACK');
    this.setTooltip('Add, remove, or reorder swatches.');
    this.contextMenu = false;
  }
};

Blockly.Blocks['predominantColorProcessorBuilder_setSwatches_item'] = {
  /**
   * Mutator block for adding items.
   * @this Blockly.Block
   */
  init: function() {
    this.setStyle('list_blocks');
    this.appendDummyInput()
        .appendField('swatch');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setTooltip('Add a swatch.');
    this.contextMenu = false;
  }
};

Blockly.JavaScript['predominantColorProcessorBuilder_setSwatches'] = function(block) {
  var predominantColorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('PREDOMINANT_COLOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var elements = new Array(block.itemCount_);
  for (var i = 0; i < block.itemCount_; i++) {
    elements[i] = Blockly.JavaScript.valueToCode(block, 'SWATCH' + i,
        Blockly.JavaScript.ORDER_COMMA) || 'null';
  }
  var swatches = '[' + elements.join(', ') + ']';
  return predominantColorIdentifierForJavaScript + '.setSwatches(' +
      predominantColorProcessorBuilder + ', JSON.stringify(' + swatches + '));\n';
};

Blockly.FtcJava['predominantColorProcessorBuilder_setSwatches'] = function(block) {
  var predominantColorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('PREDOMINANT_COLOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var elements = new Array(block.itemCount_);
  for (var i = 0; i < block.itemCount_; i++) {
    elements[i] = Blockly.FtcJava.valueToCode(block, 'SWATCH' + i,
        Blockly.FtcJava.ORDER_COMMA) || 'null';
  }
  var swatches = elements.join(',\n' + Blockly.FtcJava.INDENT_CONTINUE);
  return predominantColorProcessorBuilder + '.setSwatches(\n' +
    Blockly.FtcJava.INDENT_CONTINUE + swatches + ');\n';
};

Blockly.Blocks['predominantColorProcessorBuilder_build'] = {
  init: function() {
    this.setOutput(true, 'PredominantColorProcessor');
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myPredominantColorProcessorBuilder', null,
                                               ['PredominantColorProcessor.Builder'], 'PredominantColorProcessor.Builder'),
                     'PREDOMINANT_COLOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('build'));
    this.setInputsInline(true);
    this.setColour(builderColor);
    this.setTooltip('Builds an PredominantColorProcessor from the given PredominantColorProcessor.Builder.');
  }
};

Blockly.JavaScript['predominantColorProcessorBuilder_build'] = function(block) {
  var predominantColorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('PREDOMINANT_COLOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var code = predominantColorIdentifierForJavaScript + '.buildPredominantColorProcessor(' +
      predominantColorProcessorBuilder + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['predominantColorProcessorBuilder_build'] = function(block) {
  var predominantColorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('PREDOMINANT_COLOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var code = predominantColorProcessorBuilder + '.build()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// PredominantColorProcessor

Blockly.Blocks['predominantColorProcessor_getAnalysis'] = {
  init: function() {
    this.setOutput(true, 'PredominantColorProcessor.Result');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('PredominantColorProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('getAnalysis'));
    this.appendValueInput('PREDOMINANT_COLOR_PROCESSOR').setCheck('PredominantColorProcessor')
        .appendField('predominantColorProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the result of the most recent color analysis.');
  }
};

Blockly.JavaScript['predominantColorProcessor_getAnalysis'] = function(block) {
  var predominantColorProcessor = Blockly.JavaScript.valueToCode(
      block, 'PREDOMINANT_COLOR_PROCESSOR', Blockly.JavaScript.ORDER_NONE);
  var code = 'JSON.parse(' + predominantColorIdentifierForJavaScript + '.getAnalysis(' +
      predominantColorProcessor + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['predominantColorProcessor_getAnalysis'] = function(block) {
  var predominantColorProcessor = Blockly.FtcJava.valueToCode(
      block, 'PREDOMINANT_COLOR_PROCESSOR', Blockly.FtcJava.ORDER_MEMBER);
  var code = predominantColorProcessor + '.getAnalysis()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// PredominantColorProcessor.Result

Blockly.Blocks['predominantColorProcessorResult_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['rgb', 'rgb'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('PredominantColorProcessor.Result'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('RESULT').setCheck('PredominantColorProcessor.Result')
        .appendField('result')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['rgb', 'Returns the Exact numerical value of the dominant color in the ROI.'],
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
        case 'rgb':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['predominantColorProcessorResult_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var result = Blockly.JavaScript.valueToCode(
      block, 'RESULT', Blockly.JavaScript.ORDER_MEMBER);
  var code = result + '.' + property;
  var blockLabel = 'PredominantColorProcessor.Result.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['predominantColorProcessorResult_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var result = Blockly.FtcJava.valueToCode(
      block, 'RESULT', Blockly.FtcJava.ORDER_MEMBER);
  var code = result + '.' + property;
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['predominantColorProcessorResult_getProperty_Swatch'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['closestSwatch', 'closestSwatch'],
    ];
    this.setOutput(true, 'PredominantColorProcessor.Swatch');
    this.appendDummyInput()
        .appendField(createNonEditableField('PredominantColorProcessor.Result'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('RESULT').setCheck('PredominantColorProcessor.Result')
        .appendField('result')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['closestSwatch', 'Returns a "best guess" at the general shade of the dominant color in the ROI.'],
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

Blockly.JavaScript['predominantColorProcessorResult_getProperty_Swatch'] =
  Blockly.JavaScript['predominantColorProcessorResult_getProperty_Number'];

Blockly.FtcJava['predominantColorProcessorResult_getProperty_Swatch'] =
  Blockly.FtcJava['predominantColorProcessorResult_getProperty_Number'];

// PredominantColorProcessor.Swatch

Blockly.Blocks['predominantColor_typedEnum_swatch'] = {
  init: function() {
    var SWATCH_CHOICES = [
        ['RED', 'RED'],
        ['ORANGE', 'ORANGE'],
        ['YELLOW', 'YELLOW'],
        ['GREEN', 'GREEN'],
        ['CYAN', 'CYAN'],
        ['BLUE', 'BLUE'],
        ['PURPLE', 'PURPLE'],
        ['MAGENTA', 'MAGENTA'],
        ['BLACK', 'BLACK'],
        ['WHITE', 'WHITE'],
    ];
    this.setOutput(true, 'PredominantColorProcessor.Swatch');
    this.appendDummyInput()
        .appendField(createNonEditableField('Swatch'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(SWATCH_CHOICES), 'SWATCH');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['RED', 'The Swatch value RED.'],
        ['ORANGE', 'The Swatch value ORANGE.'],
        ['YELLOW', 'The Swatch value YELLOW.'],
        ['GREEN', 'The Swatch value GREEN.'],
        ['CYAN', 'The Swatch value CYAN.'],
        ['BLUE', 'The Swatch value BLUE.'],
        ['PURPLE', 'The Swatch value PURPLE.'],
        ['MAGENTA', 'The Swatch value MAGENTA.'],
        ['BLACK', 'The Swatch value BLACK.'],
        ['WHITE', 'The Swatch value WHITE.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('SWATCH');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['predominantColor_typedEnum_swatch'] = function(block) {
  var code = '"' + block.getFieldValue('SWATCH') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['predominantColor_typedEnum_swatch'] = function(block) {
  var code = 'PredominantColorProcessor.Swatch.' + block.getFieldValue('SWATCH');
  Blockly.FtcJava.generateImport_('PredominantColorProcessor');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

/*
 * Copyright (c) 2023 FIRST
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * @fileoverview FTC robot blocks related to HuskyLens.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// createHuskyLensDropdown
// The following are defined in vars.js:
// createNonEditableField
// createPropertyDropdown
// functionColor
// getPropertyColor

Blockly.Blocks['huskyLens_knock'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createHuskyLensDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('knock'));
    this.setColour(functionColor);
    this.setTooltip('Returns true if the HuskyLens is communicating.');
  }
};

Blockly.JavaScript['huskyLens_knock'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.knock()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['huskyLens_knock'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'HuskyLens');
  var code = identifier + '.knock()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['huskyLens_selectAlgorithm'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createHuskyLensDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('selectAlgorithm'));
    this.appendValueInput('ALGORITHM').setCheck('HuskyLens.Algorithm')
        .appendField('algorithm')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Selects the algorithm that the HuskyLens should use. This should be called ' +
        'upon startup to ensure that the device is returning what you expect it to return.');
  }
};

Blockly.JavaScript['huskyLens_selectAlgorithm'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var algorithm = Blockly.JavaScript.valueToCode(
      block, 'ALGORITHM', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.selectAlgorithm(' + algorithm + ');\n';
};

Blockly.FtcJava['huskyLens_selectAlgorithm'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'HuskyLens');
  var algorithm = Blockly.FtcJava.valueToCode(
      block, 'algorithm', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.selectAlgorithm(' + algorithm + ');\n';
};

Blockly.Blocks['huskyLens_typedEnum_algorithm'] = {
  init: function() {
    var ALGORITHM_CHOICES = [
        ['FACE_RECOGNITION', 'FACE_RECOGNITION'],
        ['OBJECT_TRACKING', 'OBJECT_TRACKING'],
        ['OBJECT_RECOGNITION', 'OBJECT_RECOGNITION'],
        ['LINE_TRACKING', 'LINE_TRACKING'],
        ['COLOR_RECOGNITION', 'COLOR_RECOGNITION'],
        ['TAG_RECOGNITION', 'TAG_RECOGNITION'],
        ['OBJECT_CLASSIFICATION', 'OBJECT_CLASSIFICATION'],
        ['NONE', 'NONE'],
    ];
    this.setOutput(true, 'HuskyLens.Algorithm');
    this.appendDummyInput()
        .appendField(createNonEditableField('Algorithm'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(ALGORITHM_CHOICES), 'ALGORITHM');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['FACE_RECOGNITION', 'The HuskyLens.Algorithm value FACE_RECOGNITION'],
        ['OBJECT_TRACKING', 'The HuskyLens.Algorithm value OBJECT_TRACKING'],
        ['OBJECT_RECOGNITION', 'The HuskyLens.Algorithm value OBJECT_RECOGNITION'],
        ['LINE_TRACKING', 'The HuskyLens.Algorithm value LINE_TRACKING'],
        ['COLOR_RECOGNITION', 'The HuskyLens.Algorithm value COLOR_RECOGNITION'],
        ['TAG_RECOGNITION', 'The HuskyLens.Algorithm value TAG_RECOGNITION'],
        ['OBJECT_CLASSIFICATION', 'The HuskyLens.Algorithm value OBJECT_CLASSIFICATION'],
        ['NONE', 'The HuskyLens.Algorithm value NONE'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('ALGORITHM');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['huskyLens_typedEnum_algorithm'] = function(block) {
  var code = '"' + block.getFieldValue('ALGORITHM') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['huskyLens_typedEnum_algorithm'] = function(block) {
  var code = 'HuskyLens.Algorithm.' + block.getFieldValue('ALGORITHM');
  Blockly.FtcJava.generateImport_('HuskyLens');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['huskyLens_blocks'] = {
  init: function() {
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createHuskyLensDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('blocks'));
    this.setColour(functionColor);
    this.setTooltip('Returns a list of blocks.');
    this.getFtcJavaOutputType = function() {
      return 'List<HuskyLens.Block>';
    };
  }
};

Blockly.JavaScript['huskyLens_blocks'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = 'JSON.parse(' + identifier + '.blocks())';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['huskyLens_blocks'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'HuskyLens');
  var code = identifier + '.blocks()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['huskyLens_blocks_withId'] = {
  init: function() {
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createHuskyLensDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('blocks'));
    this.appendValueInput('ID').setCheck('Number')
        .appendField('id')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a list of blocks with the given id.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'ID':
          return 'int';
      }
      return '';
    };
    this.getFtcJavaOutputType = function() {
      return 'List<HuskyLens.Block>';
    };
  }
};

Blockly.JavaScript['huskyLens_blocks_withId'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var id = Blockly.JavaScript.valueToCode(
      block, 'ID', Blockly.JavaScript.ORDER_NONE);
  var code = 'JSON.parse(' + identifier + '.blocks_withId(' + id + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['huskyLens_blocks_withId'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'HuskyLens');
  var id = Blockly.FtcJava.valueToCode(
      block, 'id', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.blocks(' + id + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['huskyLens_arrows'] = {
  init: function() {
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createHuskyLensDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('arrows'));
    this.setColour(functionColor);
    this.setTooltip('Returns a list of arrows.');
    this.getFtcJavaOutputType = function() {
      return 'List<HuskyLens.Arrow>';
    };
  }
};

Blockly.JavaScript['huskyLens_arrows'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = 'JSON.parse(' + identifier + '.arrows())';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['huskyLens_arrows'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'HuskyLens');
  var code = identifier + '.arrows()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['huskyLens_arrows_withId'] = {
  init: function() {
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createHuskyLensDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('arrows'));
    this.appendValueInput('ID').setCheck('Number')
        .appendField('id')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a list of arrows with the given id.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'ID':
          return 'int';
      }
      return '';
    };
    this.getFtcJavaOutputType = function() {
      return 'List<HuskyLens.Arrow>';
    };
  }
};

Blockly.JavaScript['huskyLens_arrows_withId'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var id = Blockly.JavaScript.valueToCode(
      block, 'ID', Blockly.JavaScript.ORDER_NONE);
  var code = 'JSON.parse(' + identifier + '.arrows_withId(' + id + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['huskyLens_arrows_withId'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'HuskyLens');
  var id = Blockly.FtcJava.valueToCode(
      block, 'id', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.arrows(' + id + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['huskyLensBlock_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['x', 'x'],
        ['y', 'y'],
        ['width', 'width'],
        ['height', 'height'],
        ['top', 'top'],
        ['left', 'left'],
        ['id', 'id'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('HuskyLens.Block'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('HUSKY_LENS_BLOCK').setCheck('HuskyLens.Block')
        .appendField('huskyLensBlock')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['x', 'Returns the x field of the HuskyLens.Block.'],
        ['y', 'Returns the y field of the HuskyLens.Block.'],
        ['width', 'Returns the width field of the HuskyLens.Block.'],
        ['height', 'Returns the height field of the HuskyLens.Block.'],
        ['top', 'Returns the top field of the HuskyLens.Block.'],
        ['left', 'Returns the left field of the HuskyLens.Block.'],
        ['id', 'Returns the id field of the HuskyLens.Block.'],
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
        case 'x':
        case 'y':
        case 'width':
        case 'height':
        case 'top':
        case 'left':
        case 'id':
          return 'int';
        default:
          throw 'Unexpected property ' + property + ' (huskyLensBlock_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['huskyLensBlock_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var huskyLensBlock = Blockly.JavaScript.valueToCode(
      block, 'HUSKY_LENS_BLOCK', Blockly.JavaScript.ORDER_MEMBER);
  var code = huskyLensBlock + '.' + property;
  var blockLabel = 'HuskyLens.Block.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['huskyLensBlock_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var huskyLensBlock = Blockly.FtcJava.valueToCode(
      block, 'HUSKY_LENS_BLOCK', Blockly.FtcJava.ORDER_MEMBER);
  var code = huskyLensBlock + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['huskyLensBlock_toText'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('HuskyLens.Block'))
        .appendField('.')
        .appendField(createNonEditableField('toText'));
    this.appendValueInput('HUSKY_LENS_BLOCK').setCheck('HuskyLens.Block')
        .appendField('huskyLensBlock')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a text representation of the given HuskyLens.Block.');
  }
};

Blockly.JavaScript['huskyLensBlock_toText'] = function(block) {
  var huskyLensBlock = Blockly.JavaScript.valueToCode(
      block, 'HUSKY_LENS_BLOCK', Blockly.JavaScript.ORDER_MEMBER);
  var code = miscIdentifierForJavaScript + '.huskyLensBlockToText(JSON.stringify(' + huskyLensBlock + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['huskyLensBlock_toText'] = function(block) {
  var huskyLensBlock = Blockly.FtcJava.valueToCode(
      block, 'HUSKY_LENS_BLOCK', Blockly.FtcJava.ORDER_MEMBER);
  var code = huskyLensBlock + '.toString()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['huskyLensArrow_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['x_origin', 'x_origin'],
        ['y_origin', 'y_origin'],
        ['x_target', 'x_target'],
        ['y_target', 'y_target'],
        ['id', 'id'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('HuskyLens.Arrow'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('HUSKY_LENS_ARROW').setCheck('HuskyLens.Arrow')
        .appendField('huskyLensArrow')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['x_origin', 'Returns the x_origin field of the HuskyLens.Arrow.'],
        ['y_origin', 'Returns the y_origin field of the HuskyLens.Arrow.'],
        ['x_target', 'Returns the x_target field of the HuskyLens.Arrow.'],
        ['y_target', 'Returns the y_target field of the HuskyLens.Arrow.'],
        ['id', 'Returns the id field of the HuskyLens.Arrow.'],
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
        case 'x':
        case 'y':
        case 'width':
        case 'height':
        case 'top':
        case 'left':
        case 'id':
          return 'int';
        default:
          throw 'Unexpected property ' + property + ' (huskyLensArrow_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['huskyLensArrow_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var huskyLensArrow = Blockly.JavaScript.valueToCode(
      block, 'HUSKY_LENS_ARROW', Blockly.JavaScript.ORDER_MEMBER);
  var code = huskyLensArrow + '.' + property;
  var blockLabel = 'HuskyLens.Arrow.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['huskyLensArrow_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var huskyLensArrow = Blockly.FtcJava.valueToCode(
      block, 'HUSKY_LENS_ARROW', Blockly.FtcJava.ORDER_MEMBER);
  var code = huskyLensArrow + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['huskyLensArrow_toText'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField('call')
    .appendField(createNonEditableField('HuskyLens.Arrow'))
        .appendField('.')
        .appendField(createNonEditableField('toText'));
    this.appendValueInput('HUSKY_LENS_ARROW').setCheck('HuskyLens.Arrow')
        .appendField('huskyLensArrow')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a text representation of the given HuskyLens.Arrow.');
  }
};

Blockly.JavaScript['huskyLensArrow_toText'] = function(block) {
  var huskyLensArrow = Blockly.JavaScript.valueToCode(
      block, 'HUSKY_LENS_ARROW', Blockly.JavaScript.ORDER_MEMBER);
  var code = miscIdentifierForJavaScript + '.huskyLensArrowToText(JSON.stringify(' + huskyLensArrow + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['huskyLensArrow_toText'] = function(block) {
  var huskyLensArrow = Blockly.FtcJava.valueToCode(
      block, 'HUSKY_LENS_ARROW', Blockly.FtcJava.ORDER_MEMBER);
  var code = huskyLensArrow + '.toString()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

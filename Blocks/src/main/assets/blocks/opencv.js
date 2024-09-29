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
 * @fileoverview FTC robot blocks related to some classes in the
 * org.firstinspires.ftc.vision.opencv and org.opencv.core packages.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// opencvIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor
// setPropertyColor
// wrapJavaScriptCode

// ColorRange

Blockly.Blocks['colorRange_constant_ColorRange'] = {
  init: function() {
    var CONSTANT_CHOICES = [
        ['BLUE', 'BLUE'],
        ['RED', 'RED'],
        ['YELLOW', 'YELLOW'],
        ['GREEN', 'GREEN'],
    ];
    this.setOutput(true, 'ColorRange');
    this.appendDummyInput()
        .appendField(createNonEditableField('ColorRange'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(CONSTANT_CHOICES), 'CONSTANT');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['BLUE', 'The BLUE ColorRange object.'],
        ['RED', 'The RED ColorRange object.'],
        ['YELLOW', 'The YELLOW ColorRange object.'],
        ['GREEN', 'The GREEN ColorRange object.'],
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
  }
};

Blockly.JavaScript['colorRange_constant_ColorRange'] = function(block) {
  var code = opencvIdentifierForJavaScript + '.colorRange("' + block.getFieldValue('CONSTANT') + '")';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['colorRange_constant_ColorRange'] = function(block) {
  var constant = block.getFieldValue('CONSTANT');
  var code = 'ColorRange.' + constant;
  Blockly.FtcJava.generateImport_('ColorRange');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['colorRange_create'] = {
  init: function() {
    this.setOutput(true, 'ColorRange');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('ColorRange'));
    this.appendValueInput('COLOR_SPACE').setCheck('ColorSpace')
        .appendField('colorSpace')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MIN').setCheck('Scalar')
        .appendField('min')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MAX').setCheck('Scalar')
        .appendField('max')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new ColorRange object.');
  }
};

Blockly.JavaScript['colorRange_create'] = function(block) {
  var colorSpace = Blockly.JavaScript.valueToCode(
      block, 'COLOR_SPACE', Blockly.JavaScript.ORDER_COMMA);
  var min = Blockly.JavaScript.valueToCode(
      block, 'MIN', Blockly.JavaScript.ORDER_COMMA);
  var max = Blockly.JavaScript.valueToCode(
      block, 'MAX', Blockly.JavaScript.ORDER_COMMA);
  var code = opencvIdentifierForJavaScript + '.createColorRange(' + colorSpace + ', ' + min + ', ' + max + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['colorRange_create'] = function(block) {
  var colorSpace = Blockly.FtcJava.valueToCode(
      block, 'COLOR_SPACE', Blockly.FtcJava.ORDER_COMMA);
  var min = Blockly.FtcJava.valueToCode(
      block, 'MIN', Blockly.FtcJava.ORDER_COMMA);
  var max = Blockly.FtcJava.valueToCode(
      block, 'MAX', Blockly.FtcJava.ORDER_COMMA);
  var code = 'new ColorRange(' + colorSpace + ', ' + min + ', ' + max + ')';
  Blockly.FtcJava.generateImport_('ColorRange');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

// ColorSpace

Blockly.Blocks['colorSpace_typedEnum_colorSpace'] = {
  init: function() {
    var COLOR_SPACE_CHOICES = [
        ['YCrCb', 'YCrCb'],
        ['HSV', 'HSV'],
        ['RGB', 'RGB'],
    ];
    this.setOutput(true, 'ColorSpace');
    this.appendDummyInput()
        .appendField(createNonEditableField('ColorSpace'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(COLOR_SPACE_CHOICES), 'COLOR_SPACE');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['YCrCb', 'The ColorSpace value YCrCb.'],
        ['HSV', 'The ColorSpace value HSV.'],
        ['RGB', 'The ColorSpace value RGB.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('COLOR_SPACE');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['colorSpace_typedEnum_colorSpace'] = function(block) {
  var code = '"' + block.getFieldValue('COLOR_SPACE') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['colorSpace_typedEnum_colorSpace'] = function(block) {
  var code = 'ColorSpace.' + block.getFieldValue('COLOR_SPACE');
  Blockly.FtcJava.generateImport_('ColorSpace');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

// ImageRegion

Blockly.Blocks['imageRegion_asImageCoordinates'] = {
  init: function() {
    this.setOutput(true, 'ImageRegion');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ImageRegion'))
        .appendField('.')
        .appendField(createNonEditableField('asImageCoordinates'));
    this.appendValueInput('LEFT').setCheck('Number')
        .appendField('left')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TOP').setCheck('Number')
        .appendField('top')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('RIGHT').setCheck('Number')
        .appendField('right')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('BOTTOM').setCheck('Number')
        .appendField('bottom')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a new ImageRegion, constructed using typical image processing coordinates.');
    this.getFtcJavaInputType = function(inputName) {
      return 'int';
    };
  }
};

Blockly.JavaScript['imageRegion_asImageCoordinates'] = function(block) {
  var left = Blockly.JavaScript.valueToCode(
      block, 'LEFT', Blockly.JavaScript.ORDER_COMMA);
  var top = Blockly.JavaScript.valueToCode(
      block, 'TOP', Blockly.JavaScript.ORDER_COMMA);
  var right = Blockly.JavaScript.valueToCode(
      block, 'RIGHT', Blockly.JavaScript.ORDER_COMMA);
  var bottom = Blockly.JavaScript.valueToCode(
      block, 'BOTTOM', Blockly.JavaScript.ORDER_COMMA);
  var code = opencvIdentifierForJavaScript + '.asImageCoordinates(' + left + ', ' + top + ', ' + right + ', ' + bottom + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['imageRegion_asImageCoordinates'] = function(block) {
  var left = Blockly.FtcJava.valueToCode(
      block, 'LEFT', Blockly.FtcJava.ORDER_COMMA);
  var top = Blockly.FtcJava.valueToCode(
      block, 'TOP', Blockly.FtcJava.ORDER_COMMA);
  var right = Blockly.FtcJava.valueToCode(
      block, 'RIGHT', Blockly.FtcJava.ORDER_COMMA);
  var bottom = Blockly.FtcJava.valueToCode(
      block, 'BOTTOM', Blockly.FtcJava.ORDER_COMMA);
  var code = 'ImageRegion.asImageCoordinates(' + left + ', ' + top + ', ' + right + ', ' + bottom + ')';
  Blockly.FtcJava.generateImport_('ImageRegion');
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['imageRegion_asUnityCenterCoordinates'] = {
  init: function() {
    this.setOutput(true, 'ImageRegion');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ImageRegion'))
        .appendField('.')
        .appendField(createNonEditableField('asUnityCenterCoordinates'));
    this.appendValueInput('LEFT').setCheck('Number')
        .appendField('left')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TOP').setCheck('Number')
        .appendField('top')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('RIGHT').setCheck('Number')
        .appendField('right')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('BOTTOM').setCheck('Number')
        .appendField('bottom')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a new ImageRegion, constructed using "Unity Center" coordinates.');
    this.getFtcJavaInputType = function(inputName) {
      return 'double';
    };
  }
};

Blockly.JavaScript['imageRegion_asUnityCenterCoordinates'] = function(block) {
  var left = Blockly.JavaScript.valueToCode(
      block, 'LEFT', Blockly.JavaScript.ORDER_COMMA);
  var top = Blockly.JavaScript.valueToCode(
      block, 'TOP', Blockly.JavaScript.ORDER_COMMA);
  var right = Blockly.JavaScript.valueToCode(
      block, 'RIGHT', Blockly.JavaScript.ORDER_COMMA);
  var bottom = Blockly.JavaScript.valueToCode(
      block, 'BOTTOM', Blockly.JavaScript.ORDER_COMMA);
  var code = opencvIdentifierForJavaScript + '.asUnityCenterCoordinates(' + left + ', ' + top + ', ' + right + ', ' + bottom + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['imageRegion_asUnityCenterCoordinates'] = function(block) {
  var left = Blockly.FtcJava.valueToCode(
      block, 'LEFT', Blockly.FtcJava.ORDER_COMMA);
  var top = Blockly.FtcJava.valueToCode(
      block, 'TOP', Blockly.FtcJava.ORDER_COMMA);
  var right = Blockly.FtcJava.valueToCode(
      block, 'RIGHT', Blockly.FtcJava.ORDER_COMMA);
  var bottom = Blockly.FtcJava.valueToCode(
      block, 'BOTTOM', Blockly.FtcJava.ORDER_COMMA);
  var code = 'ImageRegion.asUnityCenterCoordinates(' + left + ', ' + top + ', ' + right + ', ' + bottom + ')';
  Blockly.FtcJava.generateImport_('ImageRegion');
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['imageRegion_entireFrame'] = {
  init: function() {
    this.setOutput(true, 'ImageRegion');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ImageRegion'))
        .appendField('.')
        .appendField(createNonEditableField('entireFrame'));
    this.setColour(functionColor);
    this.setTooltip('Returns a new ImageRegion, representing the entire frame.');
  }
};

Blockly.JavaScript['imageRegion_entireFrame'] = function(block) {
  var code = opencvIdentifierForJavaScript + '.entireFrame()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['imageRegion_entireFrame'] = function(block) {
  var code = 'ImageRegion.entireFrame()';
  Blockly.FtcJava.generateImport_('ImageRegion');
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// org.opencv.core.Point

Blockly.Blocks['point_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['x', 'x'],
        ['y', 'y'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('Point'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('POINT').setCheck('org.opencv.core.Point')
        .appendField('point')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['x', 'Returns the x value of the Point.'],
        ['y', 'Returns the y value of the Point.'],
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
      return 'double';
    };
  }
};

Blockly.JavaScript['point_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var point = Blockly.JavaScript.valueToCode(
      block, 'POINT', Blockly.JavaScript.ORDER_MEMBER);
  var code = point + '.' + property;
  var blockLabel = 'Point.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['point_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var point = Blockly.FtcJava.valueToCode(
      block, 'POINT', Blockly.FtcJava.ORDER_MEMBER);
  var code = point + '.' + property;
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// org.opencv.core.Rect

Blockly.Blocks['rect_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['x', 'x'],
        ['y', 'y'],
        ['width', 'width'],
        ['height', 'height'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('Rect'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('RECT').setCheck('org.opencv.core.Rect')
        .appendField('rect')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
      ['x', 'Returns the x value of the Rect (top left corner).'],
      ['y', 'Returns the y value of the Rect (top left corner).'],
      ['width', 'Returns the width value of the Rect.'],
      ['height', 'Returns the height value of the Rect.'],
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
      return 'double';
    };
  }
};

Blockly.JavaScript['rect_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var rect = Blockly.JavaScript.valueToCode(
      block, 'RECT', Blockly.JavaScript.ORDER_MEMBER);
  var code = rect + '.' + property;
  var blockLabel = 'Rect.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['rect_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var rect = Blockly.FtcJava.valueToCode(
      block, 'RECT', Blockly.FtcJava.ORDER_MEMBER);
  var code = rect + '.' + property;
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// org.opencv.core.RotatedRect

Blockly.Blocks['rotatedRect_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['angle', 'angle'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('RotatedRect'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('ROTATED_RECT').setCheck('RotatedRect')
        .appendField('rotatedRect')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['angle', 'Returns the angle value of the RotatedRect.'],
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
      return 'double';
    };
  }
};

Blockly.JavaScript['rotatedRect_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var rotatedRect = Blockly.JavaScript.valueToCode(
      block, 'ROTATED_RECT', Blockly.JavaScript.ORDER_MEMBER);
  var code = rotatedRect + '.' + property;
  var blockLabel = 'RotatedRect.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['rotatedRect_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var rotatedRect = Blockly.FtcJava.valueToCode(
      block, 'ROTATED_RECT', Blockly.FtcJava.ORDER_MEMBER);
  var code = rotatedRect + '.' + property;
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['rotatedRect_getProperty_Point'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['center', 'center'],
    ];
    this.setOutput(true, 'org.opencv.core.Point');
    this.appendDummyInput()
        .appendField(createNonEditableField('RotatedRect'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('ROTATED_RECT').setCheck('RotatedRect')
        .appendField('rotatedRect')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['center', 'Returns the center Point of the RotatedRect.'],
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

Blockly.JavaScript['rotatedRect_getProperty_Point'] = Blockly.JavaScript['rotatedRect_getProperty_Number'];

Blockly.FtcJava['rotatedRect_getProperty_Point'] = Blockly.FtcJava['rotatedRect_getProperty_Number'];

Blockly.Blocks['rotatedRect_getProperty_Size'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['size', 'size'],
    ];
    this.setOutput(true, 'org.opencv.core.Size');
    this.appendDummyInput()
        .appendField(createNonEditableField('RotatedRect'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('ROTATED_RECT').setCheck('RotatedRect')
        .appendField('rotatedRect')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['size', 'Returns the Size of the RotatedRect.'],
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

Blockly.JavaScript['rotatedRect_getProperty_Size'] = Blockly.JavaScript['rotatedRect_getProperty_Number'];

Blockly.FtcJava['rotatedRect_getProperty_Size'] = Blockly.FtcJava['rotatedRect_getProperty_Number'];

Blockly.Blocks['rotatedRect_boundingRect'] = {
  init: function() {
    this.setOutput(true, 'org.opencv.core.Rect');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('RotatedRect'))
        .appendField('.')
        .appendField(createNonEditableField('boundingRect'));
    this.appendValueInput('ROTATED_RECT').setCheck('RotatedRect')
        .appendField('rotatedRect')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the horizontal bounding rectangle (of type Rect) of the RotatedRect.');
  }
};

Blockly.JavaScript['rotatedRect_boundingRect'] = function(block) {
  var rotatedRect = Blockly.JavaScript.valueToCode(
      block, 'ROTATED_RECT', Blockly.JavaScript.ORDER_MEMBER);
  var code = rotatedRect + '.boundingRect';
  var blockLabel = 'call RotatedRect.boundingRect'
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['rotatedRect_boundingRect'] = function(block) {
  var rotatedRect = Blockly.FtcJava.valueToCode(
      block, 'ROTATED_RECT', Blockly.FtcJava.ORDER_MEMBER);
  var code = rotatedRect + '.boundingRect()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['rotatedRect_points'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('RotatedRect'))
        .appendField('.')
        .appendField(createNonEditableField('points'));
    this.appendValueInput('ROTATED_RECT').setCheck('RotatedRect')
        .appendField('rotatedRect')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('POINTS').setCheck('Array')
        .appendField('points')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip("Stores the 4 vertices of the 'rotatedRect' as a List of Points in the " +
                    "variable plugged into the 'points' socket.");
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'POINTS':
          return 'org.opencv.core.Point[]';
      }
      return '';
    };
  }
};

Blockly.JavaScript['rotatedRect_points'] = function(block) {
  var blockLabel = 'call RotatedRect.getPosVelAcc';

  // The blocks plugged into POINTS must be a variables_get block.
  var pointsBlock = block.getInputTargetBlock('POINTS');
  if (!pointsBlock || pointsBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the points socket of the block labeled \"' +
        blockLabel + '\". Expected a variable block.";\n';
  }
  var points = Blockly.JavaScript.valueToCode(
    block, 'POINTS', Blockly.JavaScript.ORDER_MEMBER);
  var rotatedRect = Blockly.JavaScript.valueToCode(
      block, 'ROTATED_RECT', Blockly.JavaScript.ORDER_MEMBER);

  return '{\n' + Blockly.JavaScript.INDENT +
    'startBlockExecution("' + blockLabel + '");\n' + Blockly.JavaScript.INDENT +
    points + ' = ' + rotatedRect + '.points;\n' + Blockly.JavaScript.INDENT +
    'endBlockExecution(0);\n' +
    '}\n';
};

Blockly.FtcJava['rotatedRect_points'] = function(block) {
  // The blocks plugged into POINTS must be a variables_get block.
  var pointsBlock = block.getInputTargetBlock('POINTS');
  if (!pointsBlock || pointsBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the points socket of the block labeled \"' +
        blockLabel + '\". Expected a variable block.";\n';
  }
  var points = Blockly.FtcJava.valueToCode(
      block, 'POINTS', Blockly.FtcJava.ORDER_MEMBER);
  var rotatedRect = Blockly.FtcJava.valueToCode(
      block, 'ROTATED_RECT', Blockly.FtcJava.ORDER_MEMBER);
  return '{\n' + Blockly.FtcJava.INDENT +
      points + ' = new org.opencv.core.Point[4];\n' + Blockly.FtcJava.INDENT +
      rotatedRect + '.points(' + points + ');\n' +
      '}\n';
};

// org.opencv.core.Scalar

Blockly.Blocks['scalar_create_with3'] = {
  init: function() {
    this.setOutput(true, 'Scalar');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('Scalar'));
    this.appendValueInput('V0').setCheck('Number')
        .appendField('v0')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('V1').setCheck('Number')
        .appendField('v1')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('V2').setCheck('Number')
        .appendField('v2')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new Scalar object.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'V0':
        case 'V1':
        case 'V2':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['scalar_create_with3'] = function(block) {
  var v0 = Blockly.JavaScript.valueToCode(
      block, 'V0', Blockly.JavaScript.ORDER_COMMA);
  var v1 = Blockly.JavaScript.valueToCode(
      block, 'V1', Blockly.JavaScript.ORDER_COMMA);
  var v2 = Blockly.JavaScript.valueToCode(
      block, 'V2', Blockly.JavaScript.ORDER_COMMA);
  var code = opencvIdentifierForJavaScript + '.createScalar_with3(' + v0 + ', ' + v1 + ', ' + v2 + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['scalar_create_with3'] = function(block) {
  var v0 = Blockly.FtcJava.valueToCode(
      block, 'V0', Blockly.FtcJava.ORDER_COMMA);
  var v1 = Blockly.FtcJava.valueToCode(
      block, 'V1', Blockly.FtcJava.ORDER_COMMA);
  var v2 = Blockly.FtcJava.valueToCode(
      block, 'V2', Blockly.FtcJava.ORDER_COMMA);
  var code = 'new Scalar(' + v0 + ', ' + v1 + ', ' + v2 + ')';
  Blockly.FtcJava.generateImport_('Scalar');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

// org.opencv.core.Size

Blockly.Blocks['size_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['width', 'width'],
        ['height', 'height'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('Size'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('SIZE').setCheck('org.opencv.core.Size')
        .appendField('size')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['width', 'Returns the width value of the Size.'],
        ['height', 'Returns the height value of the Size.'],
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
      return 'double';
    };
  }
};

Blockly.JavaScript['size_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var size = Blockly.JavaScript.valueToCode(
      block, 'SIZE', Blockly.JavaScript.ORDER_MEMBER);
  var code = size + '.' + property;
  var blockLabel = 'Size.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['size_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var size = Blockly.FtcJava.valueToCode(
      block, 'SIZE', Blockly.FtcJava.ORDER_MEMBER);
  var code = size + '.' + property;
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

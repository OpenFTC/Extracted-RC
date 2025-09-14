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
 * @fileoverview FTC robot blocks related to ColorBlobLocatorProcessor and it's inner classes.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// colorBlobLocatorIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor
// wrapJavaScriptCode

// ColorBlobLocatorProcessor.Builder

Blockly.Blocks['colorBlobLocatorProcessorBuilder_create_assign'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('set')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('to')
        .appendField('new')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.Builder'));
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(builderColor);
    this.setTooltip('Creates a new ColorBlobLocatorProcessor.Builder object and assigns it to a variable.');
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_create_assign'] = function(block) {
  var varName = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  return varName + ' = ' + colorBlobLocatorIdentifierForJavaScript + '.createColorBlobLocatorProcessorBuilder();\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_create_assign'] = function(block) {
  var varName = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return varName + ' = new ColorBlobLocatorProcessor.Builder();\n';
};

Blockly.Blocks['colorBlobLocatorProcessorBuilder_setDrawContours'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setDrawContours'));
    this.appendValueInput('DRAW_CONTOURS').setCheck('Boolean');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets whether to draw the contour outline for the detected ' +
                    'blobs on the camera preview. This can be helpful for debugging ' +
                    'thresholding.');
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_setDrawContours'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var drawContours = Blockly.JavaScript.valueToCode(
      block, 'DRAW_CONTOURS', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.setDrawContours(' +
      colorBlobLocatorProcessorBuilder + ', ' + drawContours + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_setDrawContours'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var drawContours = Blockly.FtcJava.valueToCode(
      block, 'DRAW_CONTOURS', Blockly.FtcJava.ORDER_NONE);
  return colorBlobLocatorProcessorBuilder + '.setDrawContours(' + drawContours + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorBuilder_setBoxFitColor'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setBoxFitColor'));
    this.appendValueInput('COLOR').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the color used to draw the "best fit" bounding boxes for blobs.');
    this.getFtcJavaInputType = function(inputName) {
        return 'int';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_setBoxFitColor'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var color = Blockly.JavaScript.valueToCode(
      block, 'COLOR', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.setBoxFitColor(' +
      colorBlobLocatorProcessorBuilder + ', ' + color + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_setBoxFitColor'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var color = Blockly.FtcJava.valueToCode(
      block, 'COLOR', Blockly.FtcJava.ORDER_NONE);
  return colorBlobLocatorProcessorBuilder + '.setBoxFitColor(' + color + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorBuilder_setCircleFitColor'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setCircleFitColor'));
    this.appendValueInput('COLOR').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the color used to draw the enclosing circle around blobs.');
    this.getFtcJavaInputType = function(inputName) {
        return 'int';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_setCircleFitColor'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var color = Blockly.JavaScript.valueToCode(
      block, 'COLOR', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.setCircleFitColor(' +
      colorBlobLocatorProcessorBuilder + ', ' + color + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_setCircleFitColor'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var color = Blockly.FtcJava.valueToCode(
      block, 'COLOR', Blockly.FtcJava.ORDER_NONE);
  return colorBlobLocatorProcessorBuilder + '.setCircleFitColor(' + color + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorBuilder_setRoiColor'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setRoiColor'));
    this.appendValueInput('COLOR').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the color used to draw the ROI on the camera preview.');
    this.getFtcJavaInputType = function(inputName) {
        return 'int';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_setRoiColor'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var color = Blockly.JavaScript.valueToCode(
      block, 'COLOR', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.setRoiColor(' +
      colorBlobLocatorProcessorBuilder + ', ' + color + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_setRoiColor'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var color = Blockly.FtcJava.valueToCode(
      block, 'COLOR', Blockly.FtcJava.ORDER_NONE);
  return colorBlobLocatorProcessorBuilder + '.setRoiColor(' + color + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorBuilder_setContourColor'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setContourColor'));
    this.appendValueInput('COLOR').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the color used to draw blob contours on the camera preview.');
    this.getFtcJavaInputType = function(inputName) {
        return 'int';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_setContourColor'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var color = Blockly.JavaScript.valueToCode(
      block, 'COLOR', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.setContourColor(' +
      colorBlobLocatorProcessorBuilder + ', ' + color + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_setContourColor'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var color = Blockly.FtcJava.valueToCode(
      block, 'COLOR', Blockly.FtcJava.ORDER_NONE);
  return colorBlobLocatorProcessorBuilder + '.setContourColor(' + color + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorBuilder_setTargetColorRange'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setTargetColorRange'));
    this.appendValueInput('COLOR_RANGE').setCheck('ColorRange');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the color range used to find blobs.');
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_setTargetColorRange'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var colorRange = Blockly.JavaScript.valueToCode(
      block, 'COLOR_RANGE', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.setTargetColorRange(' +
      colorBlobLocatorProcessorBuilder + ', ' + colorRange + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_setTargetColorRange'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var colorRange = Blockly.FtcJava.valueToCode(
      block, 'COLOR_RANGE', Blockly.FtcJava.ORDER_NONE);
  return colorBlobLocatorProcessorBuilder + '.setTargetColorRange(' + colorRange + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorBuilder_setContourMode'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setContourMode'));
    this.appendValueInput('CONTOUR_MODE').setCheck('ColorBlobLocatorProcessor.ContourMode');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the contour mode which will be used when generating the results provided by getBlobs.');
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_setContourMode'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var contourMode = Blockly.JavaScript.valueToCode(
      block, 'CONTOUR_MODE', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.setContourMode(' +
      colorBlobLocatorProcessorBuilder + ', ' + contourMode + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_setContourMode'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var contourMode = Blockly.FtcJava.valueToCode(
      block, 'CONTOUR_MODE', Blockly.FtcJava.ORDER_NONE);
  return colorBlobLocatorProcessorBuilder + '.setContourMode(' + contourMode + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorBuilder_setRoi'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setRoi'));
    this.appendValueInput('ROI').setCheck('ImageRegion');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the Region of Interest on which to perform blob detection.');
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_setRoi'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var roi = Blockly.JavaScript.valueToCode(
      block, 'ROI', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.setRoi(' +
      colorBlobLocatorProcessorBuilder + ', ' + roi + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_setRoi'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var roi = Blockly.FtcJava.valueToCode(
      block, 'ROI', Blockly.FtcJava.ORDER_NONE);
  return colorBlobLocatorProcessorBuilder + '.setRoi(' + roi + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorBuilder_setBlurSize'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setBlurSize'))
        .appendField('pixels');
    this.appendValueInput('BLUR_SIZE').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the size of the blur kernel. Blurring can improve ' +
                    'color thresholding results by smoothing color variation. ' +
                    'Use 0 to disable.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'BLUR_SIZE':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_setBlurSize'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var blurSize = Blockly.JavaScript.valueToCode(
      block, 'BLUR_SIZE', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.setBlurSize(' +
      colorBlobLocatorProcessorBuilder + ', ' + blurSize + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_setBlurSize'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var blurSize = Blockly.FtcJava.valueToCode(
      block, 'BLUR_SIZE', Blockly.FtcJava.ORDER_NONE);
  return colorBlobLocatorProcessorBuilder + '.setBlurSize(' + blurSize + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorBuilder_setMorphOperationType'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setMorphOperationType'));
    this.appendValueInput('MORPH_OPERATION_TYPE').setCheck('ColorBlobLocatorProcessor.MorphOperationType');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip(
        'Sets the type of morph operation to perform. Only relevant if using ' +
        'both erosion and dilation.');
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_setMorphOperationType'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var morphOperationType = Blockly.JavaScript.valueToCode(
      block, 'MORPH_OPERATION_TYPE', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.setMorphOperationType(' +
      colorBlobLocatorProcessorBuilder + ', ' + morphOperationType + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_setMorphOperationType'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var morphOperationType = Blockly.FtcJava.valueToCode(
      block, 'MORPH_OPERATION_TYPE', Blockly.FtcJava.ORDER_NONE);
  return colorBlobLocatorProcessorBuilder + '.setMorphOperationType(' + morphOperationType + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorBuilder_setErodeSize'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setErodeSize'))
        .appendField('pixels');
    this.appendValueInput('ERODE_SIZE').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the size of the Erosion operation performed after applying ' +
                    'the color threshold. Erosion eats away at the mask, reducing ' +
                    'noise by eliminating super small areas, but also reduces the ' +
                    'contour areas of everything a little bit. ' +
                    'Use 0 to disable.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'ERODE_SIZE':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_setErodeSize'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var erodeSize = Blockly.JavaScript.valueToCode(
      block, 'ERODE_SIZE', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.setErodeSize(' +
      colorBlobLocatorProcessorBuilder + ', ' + erodeSize + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_setErodeSize'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var erodeSize = Blockly.FtcJava.valueToCode(
      block, 'ERODE_SIZE', Blockly.FtcJava.ORDER_NONE);
  return colorBlobLocatorProcessorBuilder + '.setErodeSize(' + erodeSize + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorBuilder_setDilateSize'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setDilateSize'))
        .appendField('pixels');
    this.appendValueInput('DILATE_SIZE').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the size of the Dilation operation performed after applying ' +
                    'the Erosion operation. Dilation expands mask areas, making up  ' +
                    'for shrinkage caused during erosion, and can also clean up results ' +
                    'by closing small interior gaps in the mask. ' +
                    'Use 0 to disable.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'DILATE_SIZE':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_setDilateSize'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var dilateSize = Blockly.JavaScript.valueToCode(
      block, 'DILATE_SIZE', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.setDilateSize(' +
      colorBlobLocatorProcessorBuilder + ', ' + dilateSize + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_setDilateSize'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var dilateSize = Blockly.FtcJava.valueToCode(
      block, 'DILATE_SIZE', Blockly.FtcJava.ORDER_NONE);
  return colorBlobLocatorProcessorBuilder + '.setDilateSize(' + dilateSize + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorBuilder_build'] = {
  init: function() {
    this.setOutput(true, 'ColorBlobLocatorProcessor');
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myColorBlobLocatorProcessorBuilder', null,
                                               ['ColorBlobLocatorProcessor.Builder'], 'ColorBlobLocatorProcessor.Builder'),
                     'COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('build'));
    this.setInputsInline(true);
    this.setColour(builderColor);
    this.setTooltip('Builds an ColorBlobLocatorProcessor from the given ColorBlobLocatorProcessor.Builder.');
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBuilder_build'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var code = colorBlobLocatorIdentifierForJavaScript + '.buildColorBlobLocatorProcessor(' +
      colorBlobLocatorProcessorBuilder + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['colorBlobLocatorProcessorBuilder_build'] = function(block) {
  var colorBlobLocatorProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('COLOR_BLOB_LOCATOR_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var code = colorBlobLocatorProcessorBuilder + '.build()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// ColorBlobLocatorProcessor

Blockly.Blocks['colorBlobLocatorProcessor_addFilter'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('addFilter'));
    this.appendValueInput('COLOR_BLOB_LOCATOR_PROCESSOR').setCheck('ColorBlobLocatorProcessor')
        .appendField('colorBlobLocatorProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('FILTER').setCheck('ColorBlobLocatorProcessor.BlobFilter')
        .appendField('blobFilter')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Adds a blob filter.');
  }
};

Blockly.JavaScript['colorBlobLocatorProcessor_addFilter'] = function(block) {
  var colorBlobLocatorProcessor = Blockly.JavaScript.valueToCode(
      block, 'COLOR_BLOB_LOCATOR_PROCESSOR', Blockly.JavaScript.ORDER_COMMA);
  var filter = Blockly.JavaScript.valueToCode(
      block, 'FILTER', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.addFilter(' + colorBlobLocatorProcessor + ', ' + filter + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessor_addFilter'] = function(block) {
  var colorBlobLocatorProcessor = Blockly.FtcJava.valueToCode(
      block, 'COLOR_BLOB_LOCATOR_PROCESSOR', Blockly.FtcJava.ORDER_COMMA);
  var filter = Blockly.FtcJava.valueToCode(
      block, 'FILTER', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return colorBlobLocatorProcessor + '.addFilter(' + filter + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessor_removeFilter'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('removeFilter'));
    this.appendValueInput('COLOR_BLOB_LOCATOR_PROCESSOR').setCheck('ColorBlobLocatorProcessor')
        .appendField('colorBlobLocatorProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('FILTER').setCheck('ColorBlobLocatorProcessor.BlobFilter')
        .appendField('blobFilter')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Removes a previously added blob filter.');
  }
};

Blockly.JavaScript['colorBlobLocatorProcessor_removeFilter'] = function(block) {
  var colorBlobLocatorProcessor = Blockly.JavaScript.valueToCode(
      block, 'COLOR_BLOB_LOCATOR_PROCESSOR', Blockly.JavaScript.ORDER_COMMA);
  var filter = Blockly.JavaScript.valueToCode(
      block, 'FILTER', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.removeFilter(' + colorBlobLocatorProcessor + ', ' + filter + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessor_removeFilter'] = function(block) {
  var colorBlobLocatorProcessor = Blockly.FtcJava.valueToCode(
      block, 'COLOR_BLOB_LOCATOR_PROCESSOR', Blockly.FtcJava.ORDER_COMMA);
  var filter = Blockly.FtcJava.valueToCode(
      block, 'FILTER', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return colorBlobLocatorProcessor + '.removeFilter(' + filter + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessor_removeAllFilters'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('removeAllFilters'));
    this.appendValueInput('COLOR_BLOB_LOCATOR_PROCESSOR').setCheck('ColorBlobLocatorProcessor')
        .appendField('colorBlobLocatorProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Removes all previously added filters.');
  }
};

Blockly.JavaScript['colorBlobLocatorProcessor_removeAllFilters'] = function(block) {
  var colorBlobLocatorProcessor = Blockly.JavaScript.valueToCode(
      block, 'COLOR_BLOB_LOCATOR_PROCESSOR', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.removeAllFilters(' + colorBlobLocatorProcessor + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessor_removeAllFilters'] = function(block) {
  var colorBlobLocatorProcessor = Blockly.FtcJava.valueToCode(
      block, 'COLOR_BLOB_LOCATOR_PROCESSOR', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return colorBlobLocatorProcessor + '.removeAllFilters();\n';
};

Blockly.Blocks['colorBlobLocatorProcessor_setSort'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('setSort'));
    this.appendValueInput('COLOR_BLOB_LOCATOR_PROCESSOR').setCheck('ColorBlobLocatorProcessor')
        .appendField('colorBlobLocatorProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('SORT').setCheck('ColorBlobLocatorProcessor.BlobSort')
        .appendField('blobSort')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the blob sort.');
  }
};

Blockly.JavaScript['colorBlobLocatorProcessor_setSort'] = function(block) {
  var colorBlobLocatorProcessor = Blockly.JavaScript.valueToCode(
      block, 'COLOR_BLOB_LOCATOR_PROCESSOR', Blockly.JavaScript.ORDER_COMMA);
  var sort = Blockly.JavaScript.valueToCode(
      block, 'SORT', Blockly.JavaScript.ORDER_COMMA);
  return colorBlobLocatorIdentifierForJavaScript + '.setSort(' + colorBlobLocatorProcessor + ', ' + sort + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessor_setSort'] = function(block) {
  var colorBlobLocatorProcessor = Blockly.FtcJava.valueToCode(
      block, 'COLOR_BLOB_LOCATOR_PROCESSOR', Blockly.FtcJava.ORDER_COMMA);
  var sort = Blockly.FtcJava.valueToCode(
      block, 'SORT', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return colorBlobLocatorProcessor + '.setSort(' + sort + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessor_getBlobs'] = {
  init: function() {
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('getBlobs'));
    this.appendValueInput('COLOR_BLOB_LOCATOR_PROCESSOR').setCheck('ColorBlobLocatorProcessor')
        .appendField('colorBlobLocatorProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a list containing the latest blobs, which may be stale.');
    this.getFtcJavaOutputType = function() {
      return 'List<ColorBlobLocatorProcessor.Blob>';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessor_getBlobs'] = function(block) {
  var colorBlobLocatorProcessor = Blockly.JavaScript.valueToCode(
      block, 'COLOR_BLOB_LOCATOR_PROCESSOR', Blockly.JavaScript.ORDER_NONE);
  var code = 'JSON.parse(' + colorBlobLocatorIdentifierForJavaScript + '.getBlobs(' +
      colorBlobLocatorProcessor + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['colorBlobLocatorProcessor_getBlobs'] = function(block) {
  var colorBlobLocatorProcessor = Blockly.FtcJava.valueToCode(
      block, 'COLOR_BLOB_LOCATOR_PROCESSOR', Blockly.FtcJava.ORDER_MEMBER);
  var code = colorBlobLocatorProcessor + '.getBlobs()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// ColorBlobLocatorProcessor.ContourMode

Blockly.Blocks['colorBlobLocator_typedEnum_contourMode'] = {
  init: function() {
    var CONTOUR_MODE_CHOICES = [
        ['EXTERNAL_ONLY', 'EXTERNAL_ONLY'],
        ['ALL_FLATTENED_HIERARCHY', 'ALL_FLATTENED_HIERARCHY'],
    ];
    this.setOutput(true, 'ColorBlobLocatorProcessor.ContourMode');
    this.appendDummyInput()
        .appendField(createNonEditableField('ContourMode'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(CONTOUR_MODE_CHOICES), 'CONTOUR_MODE');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['EXTERNAL_ONLY', 'The ContourMode value EXTERNAL_ONLY.'],
        ['ALL_FLATTENED_HIERARCHY', 'The ContourMode value ALL_FLATTENED_HIERARCHY.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('CONTOUR_MODE');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['colorBlobLocator_typedEnum_contourMode'] = function(block) {
  var code = '"' + block.getFieldValue('CONTOUR_MODE') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['colorBlobLocator_typedEnum_contourMode'] = function(block) {
  var code = 'ColorBlobLocatorProcessor.ContourMode.' + block.getFieldValue('CONTOUR_MODE');
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

// ColorBlobLocatorProcessor.MorphOperationType

Blockly.Blocks['colorBlobLocator_typedEnum_morphOperationType'] = {
  init: function() {
    var MORPH_OPERATION_TYPE_CHOICES = [
        ['OPENING', 'OPENING'],
        ['CLOSING', 'CLOSING'],
    ];
    this.setOutput(true, 'ColorBlobLocatorProcessor.MorphOperationType');
    this.appendDummyInput()
        .appendField(createNonEditableField('MorphOperationType'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(MORPH_OPERATION_TYPE_CHOICES), 'MORPH_OPERATION_TYPE');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['OPENING', 'The MorphOperationType value OPENING. Performs erosion followed by dilation.'],
        ['CLOSING', 'The MorphOperationType value CLOSING. Performs dilation followed by erosion.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('MORPH_OPERATION_TYPE');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['colorBlobLocator_typedEnum_morphOperationType'] = function(block) {
  var code = '"' + block.getFieldValue('MORPH_OPERATION_TYPE') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['colorBlobLocator_typedEnum_morphOperationType'] = function(block) {
  var code = 'ColorBlobLocatorProcessor.MorphOperationType.' + block.getFieldValue('MORPH_OPERATION_TYPE');
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

// Blob

Blockly.Blocks['colorBlobLocatorProcessorBlob_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['ContourArea', 'ContourArea'],
        ['Density', 'Density'],
        ['AspectRatio', 'AspectRatio'],
        ['ArcLength', 'ArcLength'],
        ['Circularity', 'Circularity'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.Blob'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('BLOB').setCheck('ColorBlobLocatorProcessor.Blob')
        .appendField('blob')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['ContourArea', 'Returns the area enclosed by this blob\'s contour.'],
        ['Density', 'Returns the density of this blob, i.e. the ratio of contour area to convex hull area'],
        ['AspectRatio', 'Returns the aspect ratio of this blob, i.e. the ratio of longer side of the bounding box to the shorter side'],
        ['ArcLength', 'Returns the arc length of this blob.'],
        ['Circularity', 'Returns the circularity of this blob.'],
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
        case 'ContourArea':
          return 'int';
        default:
          return 'double';
      }
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBlob_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var blob = Blockly.JavaScript.valueToCode(
      block, 'BLOB', Blockly.JavaScript.ORDER_MEMBER);
  var code = blob + '.' + property;
  var blockLabel = 'ColorBlobLocatorProcessor.Blob.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['colorBlobLocatorProcessorBlob_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var blob = Blockly.FtcJava.valueToCode(
      block, 'BLOB', Blockly.FtcJava.ORDER_MEMBER);
  var code = blob + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['colorBlobLocatorProcessorBlob_getProperty_Array'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['ContourPoints', 'ContourPoints'],
    ];
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.Blob'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('BLOB').setCheck('ColorBlobLocatorProcessor.Blob')
        .appendField('blob')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['ContourPoints', "Returns a List of the contour's Points for this blob."],
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
        case 'ContourPoints':
          return 'org.opencv.core.Point[]';
      }
      return '';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBlob_getProperty_Array'] =
  Blockly.JavaScript['colorBlobLocatorProcessorBlob_getProperty_Number'];

Blockly.FtcJava['colorBlobLocatorProcessorBlob_getProperty_Array'] =
  Blockly.FtcJava['colorBlobLocatorProcessorBlob_getProperty_Number'];

Blockly.Blocks['colorBlobLocatorProcessorBlob_getProperty_RotatedRect'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['BoxFit', 'BoxFit'],
    ];
    this.setOutput(true, 'RotatedRect');
    this.appendDummyInput()
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.Blob'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('BLOB').setCheck('ColorBlobLocatorProcessor.Blob')
        .appendField('blob')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['BoxFit', 'Returns a "best fit" bounding box for this blob.'],
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

Blockly.JavaScript['colorBlobLocatorProcessorBlob_getProperty_RotatedRect'] =
  Blockly.JavaScript['colorBlobLocatorProcessorBlob_getProperty_Number'];

Blockly.FtcJava['colorBlobLocatorProcessorBlob_getProperty_RotatedRect'] =
  Blockly.FtcJava['colorBlobLocatorProcessorBlob_getProperty_Number'];

Blockly.Blocks['colorBlobLocatorProcessorBlob_getProperty_Circle'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Circle', 'Circle'],
    ];
    this.setOutput(true, 'Circle');
    this.appendDummyInput()
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.Blob'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('BLOB').setCheck('ColorBlobLocatorProcessor.Blob')
        .appendField('blob')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Circle', 'Returns the circle enclosing this blob.'],
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

Blockly.JavaScript['colorBlobLocatorProcessorBlob_getProperty_Circle'] =
  Blockly.JavaScript['colorBlobLocatorProcessorBlob_getProperty_Number'];

Blockly.FtcJava['colorBlobLocatorProcessorBlob_getProperty_Circle'] =
  Blockly.FtcJava['colorBlobLocatorProcessorBlob_getProperty_Number'];

// ColorBlobLocatorProcessor.BlobCriteria

Blockly.Blocks['colorBlobLocator_typedEnum_blobCriteria'] = {
  init: function() {
    var CRITERIA_CHOICES = [
        ['BY_CONTOUR_AREA', 'BY_CONTOUR_AREA'],
        ['BY_DENSITY', 'BY_DENSITY'],
        ['BY_ASPECT_RATIO', 'BY_ASPECT_RATIO'],
        ['BY_ARC_LENGTH', 'BY_ARC_LENGTH'],
        ['BY_CIRCULARITY', 'BY_CIRCULARITY'],
    ];
    this.setOutput(true, 'ColorBlobLocatorProcessor.BlobCriteria');
    this.appendDummyInput()
        .appendField(createNonEditableField('BlobCriteria'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(CRITERIA_CHOICES), 'CRITERIA');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['BY_CONTOUR_AREA', 'The BlobCriteria value BY_CONTOUR_AREA.'],
        ['BY_DENSITY', 'The BlobCriteria value BY_DENSITY.'],
        ['BY_ASPECT_RATIO', 'The BlobCriteria value BY_ASPECT_RATIO.'],
        ['BY_ARC_LENGTH', 'The BlobCriteria value BY_ARC_LENGTH.'],
        ['BY_CIRCULARITY', 'The BlobCriteria value BY_CIRCULARITY.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('CRITERIA');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['colorBlobLocator_typedEnum_blobCriteria'] = function(block) {
  var code = '"' + block.getFieldValue('CRITERIA') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['colorBlobLocator_typedEnum_blobCriteria'] = function(block) {
  var code = 'ColorBlobLocatorProcessor.BlobCriteria.' + block.getFieldValue('CRITERIA');
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

// ColorBlobLocatorProcessor.BlobFilter

Blockly.Blocks['colorBlobLocatorProcessorBlobFilter_create'] = {
  init: function() {
    this.setOutput(true, 'ColorBlobLocatorProcessor.BlobFilter');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.BlobFilter'));
    this.appendValueInput('CRITERIA').setCheck('ColorBlobLocatorProcessor.BlobCriteria')
        .appendField('blobCriteria')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MIN_VALUE').setCheck('Number')
        .appendField('minValue')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MAX_VALUE').setCheck('Number')
        .appendField('maxValue')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new ColorBlobLocatorProcessor.BlobFilter object.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MIN_VALUE':
        case 'MAX_VALUE':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBlobFilter_create'] = function(block) {
  var criteria = Blockly.JavaScript.valueToCode(
      block, 'CRITERIA', Blockly.JavaScript.ORDER_COMMA);
  var minValue = Blockly.JavaScript.valueToCode(
      block, 'MIN_VALUE', Blockly.JavaScript.ORDER_COMMA);
  var maxValue = Blockly.JavaScript.valueToCode(
      block, 'MAX_VALUE', Blockly.JavaScript.ORDER_COMMA);
  var code = colorBlobLocatorIdentifierForJavaScript + '.createColorBlobLocatorProcessorBlobFilter(' +
      criteria + ', ' + minValue + ', ' + maxValue + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['colorBlobLocatorProcessorBlobFilter_create'] = function(block) {
  var criteria = Blockly.FtcJava.valueToCode(
      block, 'CRITERIA', Blockly.FtcJava.ORDER_COMMA);
  var minValue = Blockly.FtcJava.valueToCode(
      block, 'MIN_VALUE', Blockly.FtcJava.ORDER_COMMA);
  var maxValue = Blockly.FtcJava.valueToCode(
      block, 'MAX_VALUE', Blockly.FtcJava.ORDER_COMMA);
  var code = 'new ColorBlobLocatorProcessor.BlobFilter(' + criteria + ', ' + minValue + ', ' + maxValue + ')';
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

Blockly.Blocks['colorBlobLocatorProcessorBlobFilter_getProperty_BlobCriteria'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['criteria', 'criteria'],
    ];
    this.setOutput(true, 'ColorBlobLocatorProcessor.BlobCriteria');
    this.appendDummyInput()
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.BlobFilter'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('FILTER').setCheck('ColorBlobLocatorProcessor.BlobFilter')
        .appendField('blobFilter')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['criteria', 'Returns the criteria of the blob filter.'],
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

Blockly.JavaScript['colorBlobLocatorProcessorBlobFilter_getProperty_BlobCriteria'] = function(block) {
  var property = block.getFieldValue('PROP');
  var filter = Blockly.JavaScript.valueToCode(
      block, 'FILTER', Blockly.JavaScript.ORDER_COMMA);
  var code = 'getObjectViaJson(' + miscIdentifierForJavaScript + ',' + filter + ').' + property;
  var blockLabel = 'ColorBlobLocatorProcessor.BlobFilter.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['colorBlobLocatorProcessorBlobFilter_getProperty_BlobCriteria'] = function(block) {
  var property = block.getFieldValue('PROP');
  var filter = Blockly.FtcJava.valueToCode(
      block, 'FILTER', Blockly.FtcJava.ORDER_MEMBER);
  var code = filter + '.' + property;
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['colorBlobLocatorProcessorBlobFilter_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['minValue', 'minValue'],
        ['maxValue', 'maxValue'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.BlobFilter'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('FILTER').setCheck('ColorBlobLocatorProcessor.BlobFilter')
        .appendField('blobFilter')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['minValue', 'Returns the minimum value of the blob filter.'],
        ['maxValue', 'Returns the maximum value of the blob filter.'],
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
        case 'minValue':
        case 'maxValue':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBlobFilter_getProperty_Number'] =
  Blockly.JavaScript['colorBlobLocatorProcessorBlobFilter_getProperty_BlobCriteria'];

Blockly.FtcJava['colorBlobLocatorProcessorBlobFilter_getProperty_Number'] =
  Blockly.FtcJava['colorBlobLocatorProcessorBlobFilter_getProperty_BlobCriteria'];

// ColorBlobLocatorProcessor.BlobSort

Blockly.Blocks['colorBlobLocatorProcessorBlobSort_create'] = {
  init: function() {
    this.setOutput(true, 'ColorBlobLocatorProcessor.BlobSort');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.BlobSort'));
    this.appendValueInput('CRITERIA').setCheck('ColorBlobLocatorProcessor.BlobCriteria')
        .appendField('criteria')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('SORT_ORDER').setCheck('SortOrder')
        .appendField('sortOrder')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new ColorBlobLocatorProcessor.BlobSort object.');
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorBlobSort_create'] = function(block) {
  var criteria = Blockly.JavaScript.valueToCode(
      block, 'CRITERIA', Blockly.JavaScript.ORDER_COMMA);
  var sortOrder = Blockly.JavaScript.valueToCode(
      block, 'SORT_ORDER', Blockly.JavaScript.ORDER_COMMA);
  var code = colorBlobLocatorIdentifierForJavaScript + '.createColorBlobLocatorProcessorBlobSort(' + criteria + ', ' + sortOrder + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['colorBlobLocatorProcessorBlobSort_create'] = function(block) {
  var criteria = Blockly.FtcJava.valueToCode(
      block, 'CRITERIA', Blockly.FtcJava.ORDER_COMMA);
  var sortOrder = Blockly.FtcJava.valueToCode(
      block, 'SORT_ORDER', Blockly.FtcJava.ORDER_COMMA);
  var code = 'new ColorBlobLocatorProcessor.BlobSort(' + criteria + ', ' + sortOrder + ')';
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

Blockly.Blocks['colorBlobLocatorProcessorBlobSort_getProperty_BlobCriteria'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['criteria', 'criteria'],
    ];
    this.setOutput(true, 'ColorBlobLocatorProcessor.BlobCriteria');
    this.appendDummyInput()
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.BlobSort'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('SORT').setCheck('ColorBlobLocatorProcessor.BlobSort')
        .appendField('blobSort')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['criteria', 'Returns the criteria of the blob sort.'],
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

Blockly.JavaScript['colorBlobLocatorProcessorBlobSort_getProperty_BlobCriteria'] = function(block) {
  var property = block.getFieldValue('PROP');
  var sort = Blockly.JavaScript.valueToCode(
      block, 'SORT', Blockly.JavaScript.ORDER_COMMA);
  var code = 'getObjectViaJson(' + miscIdentifierForJavaScript + ',' + sort + ').' + property;
  var blockLabel = 'ColorBlobLocatorProcessor.BlobSort.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['colorBlobLocatorProcessorBlobSort_getProperty_BlobCriteria'] = function(block) {
  var property = block.getFieldValue('PROP');
  var sort = Blockly.FtcJava.valueToCode(
      block, 'SORT', Blockly.FtcJava.ORDER_MEMBER);
  var code = sort + '.' + property;
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['colorBlobLocatorProcessorBlobSort_getProperty_SortOrder'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['sortOrder', 'sortOrder'],
    ];
    this.setOutput(true, 'SortOrder');
    this.appendDummyInput()
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.BlobSort'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('SORT').setCheck('ColorBlobLocatorProcessor.BlobSort')
        .appendField('blobSort')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['sortOrder', 'Returns the sortOrder of the blob sort.'],
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

Blockly.JavaScript['colorBlobLocatorProcessorBlobSort_getProperty_SortOrder'] =
  Blockly.JavaScript['colorBlobLocatorProcessorBlobSort_getProperty_BlobCriteria']

Blockly.FtcJava['colorBlobLocatorProcessorBlobSort_getProperty_SortOrder'] =
  Blockly.FtcJava['colorBlobLocatorProcessorBlobSort_getProperty_BlobCriteria'];

// Util

Blockly.Blocks['colorBlobLocatorProcessorUtil_filterByCriteria'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.Util'))
        .appendField('.')
        .appendField(createNonEditableField('filterByCriteria'));
    this.appendValueInput('CRITERIA').setCheck('ColorBlobLocatorProcessor.BlobCriteria')
        .appendField('criteria')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MIN_VALUE').setCheck('Number')
        .appendField('minValue')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MAX_VALUE').setCheck('Number')
        .appendField('maxValue')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('BLOBS').setCheck('Array')
        .appendField('blobs')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Removes from a List of Blobs those which fail to meet a given criteria.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MIN_VALUE':
        case 'MAX_VALUE':
          return 'double';
        case 'BLOBS':
          return 'List<ColorBlobLocatorProcessor.Blob>';
      }
      return '';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorUtil_filterByCriteria'] = function(block) {
  var criteria = Blockly.JavaScript.valueToCode(
      block, 'CRITERIA', Blockly.JavaScript.ORDER_COMMA);
  var minValue = Blockly.JavaScript.valueToCode(
      block, 'MIN_VALUE', Blockly.JavaScript.ORDER_COMMA);
  var maxValue = Blockly.JavaScript.valueToCode(
      block, 'MAX_VALUE', Blockly.JavaScript.ORDER_COMMA);
  var blobs = Blockly.JavaScript.valueToCode(
      block, 'BLOBS', Blockly.JavaScript.ORDER_COMMA);
  return 'colorBlobsFilterByCriteria(' + criteria + ', ' + minValue + ', ' + maxValue + ', ' + blobs + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorUtil_filterByCriteria'] = function(block) {
  var criteria = Blockly.FtcJava.valueToCode(
      block, 'CRITERIA', Blockly.FtcJava.ORDER_COMMA);
  var minValue = Blockly.FtcJava.valueToCode(
      block, 'MIN_VALUE', Blockly.FtcJava.ORDER_COMMA);
  var maxValue = Blockly.FtcJava.valueToCode(
      block, 'MAX_VALUE', Blockly.FtcJava.ORDER_COMMA);
  var blobs = Blockly.FtcJava.valueToCode(
      block, 'BLOBS', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return 'ColorBlobLocatorProcessor.Util.filterByCriteria(' + criteria + ', ' + minValue + ', ' + maxValue + ', ' + blobs + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorUtil_sortByCriteria'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.Util'))
        .appendField('.')
        .appendField(createNonEditableField('sortByCriteria'));
    this.appendValueInput('CRITERIA').setCheck('ColorBlobLocatorProcessor.BlobCriteria')
        .appendField('criteria')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('SORT_ORDER').setCheck('SortOrder')
        .appendField('sortOrder')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('BLOBS').setCheck('Array')
        .appendField('blobs')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sorts a list of Blobs based on a given criteria.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'BLOBS':
          return 'List<ColorBlobLocatorProcessor.Blob>';
      }
      return '';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorUtil_sortByCriteria'] = function(block) {
  var criteria = Blockly.JavaScript.valueToCode(
      block, 'CRITERIA', Blockly.JavaScript.ORDER_COMMA);
  var sortOrder = Blockly.JavaScript.valueToCode(
      block, 'SORT_ORDER', Blockly.JavaScript.ORDER_COMMA);
  var blobs = Blockly.JavaScript.valueToCode(
      block, 'BLOBS', Blockly.JavaScript.ORDER_COMMA);
  return 'colorBlobsSortByCriteria(' + criteria + ', ' + sortOrder + ', ' + blobs + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorUtil_sortByCriteria'] = function(block) {
  var criteria = Blockly.FtcJava.valueToCode(
      block, 'CRITERIA', Blockly.FtcJava.ORDER_COMMA);
  var sortOrder = Blockly.FtcJava.valueToCode(
      block, 'SORT_ORDER', Blockly.FtcJava.ORDER_COMMA);
  var blobs = Blockly.FtcJava.valueToCode(
      block, 'BLOBS', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return 'ColorBlobLocatorProcessor.Util.sortByCriteria(' + criteria + ', ' + sortOrder + ', ' + blobs + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorUtil_filterByArea'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.Util'))
        .appendField('.')
        .appendField(createNonEditableField('filterByArea'));
    this.appendValueInput('MIN_AREA').setCheck('Number')
        .appendField('minArea')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MAX_AREA').setCheck('Number')
        .appendField('maxArea')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('BLOBS').setCheck('Array')
        .appendField('blobs')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Removes from a List of Blobs those which fail to meet an area criteria.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MIN_AREA':
        case 'MAX_AREA':
          return 'double';
        case 'BLOBS':
          return 'List<ColorBlobLocatorProcessor.Blob>';
      }
      return '';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorUtil_filterByArea'] = function(block) {
  var minArea = Blockly.JavaScript.valueToCode(
      block, 'MIN_AREA', Blockly.JavaScript.ORDER_COMMA);
  var maxArea = Blockly.JavaScript.valueToCode(
      block, 'MAX_AREA', Blockly.JavaScript.ORDER_COMMA);
  var blobs = Blockly.JavaScript.valueToCode(
      block, 'BLOBS', Blockly.JavaScript.ORDER_COMMA);
  return 'colorBlobsFilterByArea(' + minArea + ', ' + maxArea + ', ' + blobs + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorUtil_filterByArea'] = function(block) {
  var minArea = Blockly.FtcJava.valueToCode(
      block, 'MIN_AREA', Blockly.FtcJava.ORDER_COMMA);
  var maxArea = Blockly.FtcJava.valueToCode(
      block, 'MAX_AREA', Blockly.FtcJava.ORDER_COMMA);
  var blobs = Blockly.FtcJava.valueToCode(
      block, 'BLOBS', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return 'ColorBlobLocatorProcessor.Util.filterByArea(' + minArea + ', ' + maxArea + ', ' + blobs + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorUtil_sortByArea'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.Util'))
        .appendField('.')
        .appendField(createNonEditableField('sortByArea'));
    this.appendValueInput('SORT_ORDER').setCheck('SortOrder')
        .appendField('sortOrder')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('BLOBS').setCheck('Array')
        .appendField('blobs')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sorts a list of Blobs based on area.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'BLOBS':
          return 'List<ColorBlobLocatorProcessor.Blob>';
      }
      return '';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorUtil_sortByArea'] = function(block) {
  var sortOrder = Blockly.JavaScript.valueToCode(
      block, 'SORT_ORDER', Blockly.JavaScript.ORDER_COMMA);
  var blobs = Blockly.JavaScript.valueToCode(
      block, 'BLOBS', Blockly.JavaScript.ORDER_COMMA);
  return 'colorBlobsSortByArea(' + sortOrder + ', ' + blobs + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorUtil_sortByArea'] = function(block) {
  var sortOrder = Blockly.FtcJava.valueToCode(
      block, 'SORT_ORDER', Blockly.FtcJava.ORDER_COMMA);
  var blobs = Blockly.FtcJava.valueToCode(
      block, 'BLOBS', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return 'ColorBlobLocatorProcessor.Util.sortByArea(' + sortOrder + ', ' + blobs + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorUtil_filterByDensity'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.Util'))
        .appendField('.')
        .appendField(createNonEditableField('filterByDensity'));
    this.appendValueInput('MIN_DENSITY').setCheck('Number')
        .appendField('minDensity')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MAX_DENSITY').setCheck('Number')
        .appendField('maxDensity')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('BLOBS').setCheck('Array')
        .appendField('blobs')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Removes from a List of Blobs those which fail to meet a density criteria.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MIN_DENSITY':
        case 'MAX_DENSITY':
          return 'double';
        case 'BLOBS':
          return 'List<ColorBlobLocatorProcessor.Blob>';
        default:
          return 'double';
      }
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorUtil_filterByDensity'] = function(block) {
  var minDensity = Blockly.JavaScript.valueToCode(
      block, 'MIN_DENSITY', Blockly.JavaScript.ORDER_COMMA);
  var maxDensity = Blockly.JavaScript.valueToCode(
      block, 'MAX_DENSITY', Blockly.JavaScript.ORDER_COMMA);
  var blobs = Blockly.JavaScript.valueToCode(
      block, 'BLOBS', Blockly.JavaScript.ORDER_COMMA);
  return 'colorBlobsFilterByDensity(' + minDensity + ', ' + maxDensity + ', ' + blobs + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorUtil_filterByDensity'] = function(block) {
  var minDensity = Blockly.FtcJava.valueToCode(
      block, 'MIN_DENSITY', Blockly.FtcJava.ORDER_COMMA);
  var maxDensity = Blockly.FtcJava.valueToCode(
      block, 'MAX_DENSITY', Blockly.FtcJava.ORDER_COMMA);
  var blobs = Blockly.FtcJava.valueToCode(
      block, 'BLOBS', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return 'ColorBlobLocatorProcessor.Util.filterByDensity(' + minDensity + ', ' + maxDensity + ', ' + blobs + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorUtil_sortByDensity'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.Util'))
        .appendField('.')
        .appendField(createNonEditableField('sortByDensity'));
    this.appendValueInput('SORT_ORDER').setCheck('SortOrder')
        .appendField('sortOrder')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('BLOBS').setCheck('Array')
        .appendField('blobs')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sort a list of Blobs based on density.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'BLOBS':
          return 'List<ColorBlobLocatorProcessor.Blob>';
      }
      return '';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorUtil_sortByDensity'] = function(block) {
  var sortOrder = Blockly.JavaScript.valueToCode(
      block, 'SORT_ORDER', Blockly.JavaScript.ORDER_COMMA);
  var blobs = Blockly.JavaScript.valueToCode(
      block, 'BLOBS', Blockly.JavaScript.ORDER_COMMA);
  return 'colorBlobsSortByDensity(' + sortOrder + ', ' + blobs + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorUtil_sortByDensity'] = function(block) {
  var sortOrder = Blockly.FtcJava.valueToCode(
      block, 'SORT_ORDER', Blockly.FtcJava.ORDER_COMMA);
  var blobs = Blockly.FtcJava.valueToCode(
      block, 'BLOBS', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return 'ColorBlobLocatorProcessor.Util.sortByDensity(' + sortOrder + ', ' + blobs + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorUtil_filterByAspectRatio'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.Util'))
        .appendField('.')
        .appendField(createNonEditableField('filterByAspectRatio'));
    this.appendValueInput('MIN_ASPECT_RATIO').setCheck('Number')
        .appendField('minAspectRatio')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MAX_ASPECT_RATIO').setCheck('Number')
        .appendField('maxAspectRatio')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('BLOBS').setCheck('Array')
        .appendField('blobs')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Removes from a List of Blobs those which fail to meet an aspect ratio criteria.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MIN_ASPECT_RATIO':
        case 'MAX_ASPECT_RATIO':
          return 'double';
        case 'BLOBS':
          return 'List<ColorBlobLocatorProcessor.Blob>';
        default:
          return 'double';
      }
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorUtil_filterByAspectRatio'] = function(block) {
  var minAspectRatio = Blockly.JavaScript.valueToCode(
      block, 'MIN_ASPECT_RATIO', Blockly.JavaScript.ORDER_COMMA);
  var maxAspectRatio = Blockly.JavaScript.valueToCode(
      block, 'MAX_ASPECT_RATIO', Blockly.JavaScript.ORDER_COMMA);
  var blobs = Blockly.JavaScript.valueToCode(
      block, 'BLOBS', Blockly.JavaScript.ORDER_COMMA);
  return 'colorBlobsFilterByAspectRatio(' + minAspectRatio + ', ' + maxAspectRatio + ', ' + blobs + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorUtil_filterByAspectRatio'] = function(block) {
  var minAspectRatio = Blockly.FtcJava.valueToCode(
      block, 'MIN_ASPECT_RATIO', Blockly.FtcJava.ORDER_COMMA);
  var maxAspectRatio = Blockly.FtcJava.valueToCode(
      block, 'MAX_ASPECT_RATIO', Blockly.FtcJava.ORDER_COMMA);
  var blobs = Blockly.FtcJava.valueToCode(
      block, 'BLOBS', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return 'ColorBlobLocatorProcessor.Util.filterByAspectRatio(' + minAspectRatio + ', ' + maxAspectRatio + ', ' + blobs + ');\n';
};

Blockly.Blocks['colorBlobLocatorProcessorUtil_sortByAspectRatio'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('ColorBlobLocatorProcessor.Util'))
        .appendField('.')
        .appendField(createNonEditableField('sortByAspectRatio'));
    this.appendValueInput('SORT_ORDER').setCheck('SortOrder')
        .appendField('sortOrder')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('BLOBS').setCheck('Array')
        .appendField('blobs')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sorts a list of Blobs based on aspect ratio.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'BLOBS':
          return 'List<ColorBlobLocatorProcessor.Blob>';
      }
      return '';
    };
  }
};

Blockly.JavaScript['colorBlobLocatorProcessorUtil_sortByAspectRatio'] = function(block) {
  var sortOrder = Blockly.JavaScript.valueToCode(
      block, 'SORT_ORDER', Blockly.JavaScript.ORDER_COMMA);
  var blobs = Blockly.JavaScript.valueToCode(
      block, 'BLOBS', Blockly.JavaScript.ORDER_COMMA);
  return 'colorBlobsSortByAspectRatio(' + sortOrder + ', ' + blobs + ');\n';
};

Blockly.FtcJava['colorBlobLocatorProcessorUtil_sortByAspectRatio'] = function(block) {
  var sortOrder = Blockly.FtcJava.valueToCode(
      block, 'SORT_ORDER', Blockly.FtcJava.ORDER_COMMA);
  var blobs = Blockly.FtcJava.valueToCode(
      block, 'BLOBS', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('ColorBlobLocatorProcessor');
  return 'ColorBlobLocatorProcessor.Util.sortByAspectRatio(' + sortOrder + ', ' + blobs + ');\n';
};

// Circle

Blockly.Blocks['circle_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Radius', 'Radius'],
        ['X', 'X'],
        ['Y', 'Y'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('Circle'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('CIRCLE').setCheck('Circle')
        .appendField('circle')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Radius', 'Returns the radius of the Circle.'],
        ['X', 'Returns the x position of the Circle'],
        ['Y', 'Returns the y position of the Circle'],
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
      return 'float';
    };
  }
};

Blockly.JavaScript['circle_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var circle = Blockly.JavaScript.valueToCode(
      block, 'CIRCLE', Blockly.JavaScript.ORDER_MEMBER);
  var code = circle + '.' + property;
  var blockLabel = 'Circle.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['circle_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var circle = Blockly.FtcJava.valueToCode(
      block, 'CIRCLE', Blockly.FtcJava.ORDER_MEMBER);
  var code = circle + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['circle_getProperty_Point'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Center', 'Center'],
    ];
    this.setOutput(true, 'org.opencv.core.Point');
    this.appendDummyInput()
        .appendField(createNonEditableField('Circle'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('CIRCLE').setCheck('Circle')
        .appendField('circle')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Center', 'Returns the center Point of the Circle.'],
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

Blockly.JavaScript['circle_getProperty_Point'] = Blockly.JavaScript['circle_getProperty_Number'];

Blockly.FtcJava['circle_getProperty_Point'] = Blockly.FtcJava['circle_getProperty_Number'];

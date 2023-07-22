/**
 * @license
 * Copyright 2023 Google LLC
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
 * @fileoverview FTC robot blocks related to AprilTag detection.
 * @author Liz Looney
 */


// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// aprilTagIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// builderColor
// functionColor
// getPropertyColor

// AprilTagProcessor.Builder

Blockly.Blocks['aprilTagProcessorBuilder_create_assign'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('set')
        .appendField(new Blockly.FieldVariable('myAprilTagProcessorBuilder', null, ['AprilTagProcessor.Builder'], 'AprilTagProcessor.Builder'),
            'APRIL_TAG_PROCESSOR_BUILDER')
        .appendField('to')
        .appendField('new')
        .appendField(createNonEditableField('AprilTagProcessor.Builder'));
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(builderColor);
    this.setTooltip('Creates a new AprilTagProcessor.Builder object and assigns it to a variable.');
  }
};

Blockly.JavaScript['aprilTagProcessorBuilder_create_assign'] = function(block) {
  var varName = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  return varName + ' = ' + aprilTagIdentifierForJavaScript + '.createAprilTagProcessorBuilder();\n';
};

Blockly.FtcJava['aprilTagProcessorBuilder_create_assign'] = function(block) {
  var varName = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  Blockly.FtcJava.generateImport_('AprilTagProcessor');
  return varName + ' = new AprilTagProcessor.Builder();\n';
};

Blockly.Blocks['aprilTagProcessorBuilder_setDrawAxes'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagProcessorBuilder', null, ['AprilTagProcessor.Builder'], 'AprilTagProcessor.Builder'),
            'APRIL_TAG_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setDrawAxes'));
    this.appendValueInput('DRAW_AXES').setCheck('Boolean');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets whether or not to draw the axes on detections.');
  }
};

Blockly.JavaScript['aprilTagProcessorBuilder_setDrawAxes'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var drawAxes = Blockly.JavaScript.valueToCode(
      block, 'DRAW_AXES', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.setDrawAxes(' +
      aprilTagProcessorBuilder + ', ' + drawAxes + ');\n';
};

Blockly.FtcJava['aprilTagProcessorBuilder_setDrawAxes'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var drawAxes = Blockly.FtcJava.valueToCode(
      block, 'DRAW_AXES', Blockly.FtcJava.ORDER_NONE);
  return aprilTagProcessorBuilder + '.setDrawAxes(' + drawAxes + ');\n';
};

Blockly.Blocks['aprilTagProcessorBuilder_setDrawCubeProjection'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagProcessorBuilder', null, ['AprilTagProcessor.Builder'], 'AprilTagProcessor.Builder'),
            'APRIL_TAG_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setDrawCubeProjection'));
    this.appendValueInput('DRAW_CUBE').setCheck('Boolean');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets whether or not to draw the cube projection on detections.');
  }
};

Blockly.JavaScript['aprilTagProcessorBuilder_setDrawCubeProjection'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var drawCube = Blockly.JavaScript.valueToCode(
      block, 'DRAW_CUBE', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.setDrawCubeProjection(' +
      aprilTagProcessorBuilder + ', ' + drawCube + ');\n';
};

Blockly.FtcJava['aprilTagProcessorBuilder_setDrawCubeProjection'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var drawCube = Blockly.FtcJava.valueToCode(
      block, 'DRAW_CUBE', Blockly.FtcJava.ORDER_NONE);
  return aprilTagProcessorBuilder + '.setDrawCubeProjection(' + drawCube + ');\n';
};

Blockly.Blocks['aprilTagProcessorBuilder_setDrawTagOutline'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagProcessorBuilder', null, ['AprilTagProcessor.Builder'], 'AprilTagProcessor.Builder'),
            'APRIL_TAG_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setDrawTagOutline'));
    this.appendValueInput('DRAW_OUTLINE').setCheck('Boolean');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets whether or not to draw the tag outline on detections.');
  }
};

Blockly.JavaScript['aprilTagProcessorBuilder_setDrawTagOutline'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var drawOutline = Blockly.JavaScript.valueToCode(
      block, 'DRAW_OUTLINE', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.setDrawTagOutline(' +
      aprilTagProcessorBuilder + ', ' + drawOutline + ');\n';
};

Blockly.FtcJava['aprilTagProcessorBuilder_setDrawTagOutline'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var drawOutline = Blockly.FtcJava.valueToCode(
      block, 'DRAW_OUTLINE', Blockly.FtcJava.ORDER_NONE);
  return aprilTagProcessorBuilder + '.setDrawTagOutline(' + drawOutline + ');\n';
};

Blockly.Blocks['aprilTagProcessorBuilder_setDrawTagID'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagProcessorBuilder', null, ['AprilTagProcessor.Builder'], 'AprilTagProcessor.Builder'),
            'APRIL_TAG_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setDrawTagID'));
    this.appendValueInput('DRAW_TAG_ID').setCheck('Boolean');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets whether or not to draw the tag id on detections.');
  }
};

Blockly.JavaScript['aprilTagProcessorBuilder_setDrawTagID'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var drawTagID = Blockly.JavaScript.valueToCode(
      block, 'DRAW_TAG_ID', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.setDrawTagID(' +
      aprilTagProcessorBuilder + ', ' + drawTagID + ');\n';
};

Blockly.FtcJava['aprilTagProcessorBuilder_setDrawTagID'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var drawTagID = Blockly.FtcJava.valueToCode(
      block, 'DRAW_TAG_ID', Blockly.FtcJava.ORDER_NONE);
  return aprilTagProcessorBuilder + '.setDrawTagID(' + drawTagID + ');\n';
};

Blockly.Blocks['aprilTagProcessorBuilder_setLensIntrinsics'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagProcessorBuilder', null, ['AprilTagProcessor.Builder'], 'AprilTagProcessor.Builder'),
            'APRIL_TAG_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setLensIntrinsics'));
    this.appendValueInput('FX').setCheck('Number')
        .appendField('fx').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('FY').setCheck('Number')
        .appendField('fy').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('CX').setCheck('Number')
        .appendField('cx').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('CY').setCheck('Number')
        .appendField('cy').setAlign(Blockly.ALIGN_RIGHT);
    this.setInputsInline(false);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the lens intrinsics. The values used here should be obtained from a camera calibration.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'FX':
        case 'FY':
        case 'CX':
        case 'CY':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['aprilTagProcessorBuilder_setLensIntrinsics'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var fx = Blockly.JavaScript.valueToCode(
      block, 'FX', Blockly.JavaScript.ORDER_COMMA);
  var fy = Blockly.JavaScript.valueToCode(
      block, 'FY', Blockly.JavaScript.ORDER_COMMA);
  var cx = Blockly.JavaScript.valueToCode(
      block, 'CX', Blockly.JavaScript.ORDER_COMMA);
  var cy = Blockly.JavaScript.valueToCode(
      block, 'CY', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.setLensIntrinsics(' +
      aprilTagProcessorBuilder + ', ' + fx + ', ' + fy + ', ' + cx + ', ' + cy + ');\n';
};

Blockly.FtcJava['aprilTagProcessorBuilder_setLensIntrinsics'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var fx = Blockly.FtcJava.valueToCode(
      block, 'FX', Blockly.FtcJava.ORDER_COMMA);
  var fy = Blockly.FtcJava.valueToCode(
      block, 'FY', Blockly.FtcJava.ORDER_COMMA);
  var cx = Blockly.FtcJava.valueToCode(
      block, 'CX', Blockly.FtcJava.ORDER_COMMA);
  var cy = Blockly.FtcJava.valueToCode(
      block, 'CY', Blockly.FtcJava.ORDER_COMMA);
  return aprilTagProcessorBuilder + '.setLensIntrinsics(' + fx + ', ' + fy + ', ' + cx + ', ' + cy + ');\n';
};

Blockly.Blocks['aprilTagProcessorBuilder_setNumThreads'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagProcessorBuilder', null, ['AprilTagProcessor.Builder'], 'AprilTagProcessor.Builder'),
            'APRIL_TAG_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setNumThreads'));
    this.appendValueInput('NUM_THREADS').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the number of threads to use for detection.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'NUM_THREADS':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['aprilTagProcessorBuilder_setNumThreads'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var numThreads = Blockly.JavaScript.valueToCode(
      block, 'NUM_THREADS', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.setNumThreads(' +
      aprilTagProcessorBuilder + ', ' + numThreads + ');\n';
};

Blockly.FtcJava['aprilTagProcessorBuilder_setNumThreads'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var numThreads = Blockly.FtcJava.valueToCode(
      block, 'NUM_THREADS', Blockly.FtcJava.ORDER_NONE);
  return aprilTagProcessorBuilder + '.setNumThreads(' + numThreads + ');\n';
};

Blockly.Blocks['aprilTagProcessorBuilder_setOutputUnits'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagProcessorBuilder', null, ['AprilTagProcessor.Builder'], 'AprilTagProcessor.Builder'),
            'APRIL_TAG_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setOutputUnits'));
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ANGLE_UNIT').setCheck('AngleUnit')
        .appendField('angleUnit').setAlign(Blockly.ALIGN_RIGHT);
    this.setInputsInline(false);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the output units.');
  }
};

Blockly.JavaScript['aprilTagProcessorBuilder_setOutputUnits'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var angleUnit = Blockly.JavaScript.valueToCode(
      block, 'ANGLE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.setOutputUnits(' +
      aprilTagProcessorBuilder + ', ' + distanceUnit + ', ' + angleUnit + ');\n';
};

Blockly.FtcJava['aprilTagProcessorBuilder_setOutputUnits'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var angleUnit = Blockly.FtcJava.valueToCode(
      block, 'ANGLE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  return aprilTagProcessorBuilder + '.setOutputUnits(' + distanceUnit + ', ' + angleUnit + ');\n';
};

Blockly.Blocks['aprilTagProcessorBuilder_setTagFamily'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagProcessorBuilder', null, ['AprilTagProcessor.Builder'], 'AprilTagProcessor.Builder'),
            'APRIL_TAG_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setTagFamily'));
    this.appendValueInput('TAG_FAMILY').setCheck('AprilTagProcessor.TagFamily');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the tag family you want to detect.');
  }
};

Blockly.JavaScript['aprilTagProcessorBuilder_setTagFamily'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var tagFamily = Blockly.JavaScript.valueToCode(
      block, 'TAG_FAMILY', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.setTagFamily(' +
      aprilTagProcessorBuilder + ', ' + tagFamily + ');\n';
};

Blockly.FtcJava['aprilTagProcessorBuilder_setTagFamily'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var tagFamily = Blockly.FtcJava.valueToCode(
      block, 'TAG_FAMILY', Blockly.FtcJava.ORDER_NONE);
  return aprilTagProcessorBuilder + '.setTagFamily(' + tagFamily + ');\n';
};

Blockly.Blocks['aprilTagProcessorBuilder_setTagLibrary'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagProcessorBuilder', null, ['AprilTagProcessor.Builder'], 'AprilTagProcessor.Builder'),
            'APRIL_TAG_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setTagLibrary'));
    this.appendValueInput('APRIL_TAG_LIBRARY').setCheck('AprilTagLibrary');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the tag library.');
  }
};

Blockly.JavaScript['aprilTagProcessorBuilder_setTagLibrary'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var aprilTagLibrary = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_LIBRARY', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.setTagLibrary(' +
      aprilTagProcessorBuilder + ', ' + aprilTagLibrary + ');\n';
};

Blockly.FtcJava['aprilTagProcessorBuilder_setTagLibrary'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var aprilTagLibrary = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_LIBRARY', Blockly.FtcJava.ORDER_NONE);
  return aprilTagProcessorBuilder + '.setTagLibrary(' + aprilTagLibrary + ');\n';
};

Blockly.Blocks['aprilTagProcessorBuilder_build'] = {
  init: function() {
    this.setOutput(true, 'AprilTagProcessor');
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagProcessorBuilder', null, ['AprilTagProcessor.Builder'], 'AprilTagProcessor.Builder'),
            'APRIL_TAG_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('build'));
    this.setInputsInline(true);
    this.setColour(builderColor);
    this.setTooltip('Builds an AprilTagProcessor from the given AprilTagProcessor.Builder.');
  }
};

Blockly.JavaScript['aprilTagProcessorBuilder_build'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var code = aprilTagIdentifierForJavaScript + '.buildAprilTagProcessor(' +
      aprilTagProcessorBuilder + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagProcessorBuilder_build'] = function(block) {
  var aprilTagProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var code = aprilTagProcessorBuilder + '.build()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// AprilTagProcessor

Blockly.Blocks['aprilTagProcessor_easyCreateWithDefaults'] = {
  init: function() {
    this.setOutput(true, 'AprilTagProcessor');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('AprilTagProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('easyCreateWithDefaults'));
    this.setColour(functionColor);
    this.setTooltip('Creates a new AprilTagProcessor object, with default values for all settings.');
  }
};

Blockly.JavaScript['aprilTagProcessor_easyCreateWithDefaults'] = function(block) {
  var code = aprilTagIdentifierForJavaScript + '.easyCreateWithDefaults()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagProcessor_easyCreateWithDefaults'] = function(block) {
  Blockly.FtcJava.generateImport_('AprilTagProcessor');
  var code = 'AprilTagProcessor.easyCreateWithDefaults()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['aprilTagProcessor_setDecimation'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('AprilTagProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('setDecimation'));
    this.appendValueInput('APRIL_TAG_PROCESSOR').setCheck('AprilTagProcessor')
        .appendField('aprilTagProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DECIMATION').setCheck('Number')
        .appendField('decimation')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the detector decimation.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'DECIMATION':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['aprilTagProcessor_setDecimation'] = function(block) {
  var aprilTagProcessor = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_PROCESSOR', Blockly.JavaScript.ORDER_COMMA);
  var decimation = Blockly.JavaScript.valueToCode(
      block, 'DECIMATION', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.setDecimation(' +
      aprilTagProcessor + ', ' + decimation + ');\n';
};

Blockly.FtcJava['aprilTagProcessor_setDecimation'] = function(block) {
  var aprilTagProcessor = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_PROCESSOR', Blockly.FtcJava.ORDER_MEMBER);
  var decimation = Blockly.FtcJava.valueToCode(
      block, 'DECIMATION', Blockly.FtcJava.ORDER_NONE);
  return aprilTagProcessor + '.setDecimation(' + decimation + ');\n';
};

Blockly.Blocks['aprilTagProcessor_setPoseSolver'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('AprilTagProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('setPoseSolver'));
    this.appendValueInput('APRIL_TAG_PROCESSOR').setCheck('AprilTagProcessor')
        .appendField('aprilTagProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('POSE_SOLVER').setCheck('AprilTagProcessor.PoseSolver')
        .appendField('poseSolver')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the pose solver.');
  }
};

Blockly.JavaScript['aprilTagProcessor_setPoseSolver'] = function(block) {
  var aprilTagProcessor = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_PROCESSOR', Blockly.JavaScript.ORDER_COMMA);
  var poseSolver = Blockly.JavaScript.valueToCode(
      block, 'POSE_SOLVER', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.setPoseSolver(' +
      aprilTagProcessor + ', ' + poseSolver + ');\n';
};

Blockly.FtcJava['aprilTagProcessor_setPoseSolver'] = function(block) {
  var aprilTagProcessor = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_PROCESSOR', Blockly.FtcJava.ORDER_MEMBER);
  var poseSolver = Blockly.FtcJava.valueToCode(
      block, 'POSE_SOLVER', Blockly.FtcJava.ORDER_NONE);
  return aprilTagProcessor + '.setPoseSolver(' + poseSolver + ');\n';
};

Blockly.Blocks['aprilTagProcessor_getPerTagAvgPoseSolveTime'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('AprilTagProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('getPerTagAvgPoseSolveTime'));
    this.appendValueInput('APRIL_TAG_PROCESSOR').setCheck('AprilTagProcessor')
        .appendField('aprilTagProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the per tag average pose solve time.');
    this.getFtcJavaOutputType = function() {
      return 'int';
    };
  }
};

Blockly.JavaScript['aprilTagProcessor_getPerTagAvgPoseSolveTime'] = function(block) {
  var aprilTagProcessor = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_PROCESSOR', Blockly.JavaScript.ORDER_NONE);
  var code = aprilTagIdentifierForJavaScript + '.getPerTagAvgPoseSolveTime(' +
      aprilTagProcessor + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagProcessor_getPerTagAvgPoseSolveTime'] = function(block) {
  var aprilTagProcessor = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_PROCESSOR', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagProcessor + '.getPerTagAvgPoseSolveTime()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['aprilTagProcessor_getDetections'] = {
  init: function() {
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('AprilTagProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('getDetections'));
    this.appendValueInput('APRIL_TAG_PROCESSOR').setCheck('AprilTagProcessor')
        .appendField('aprilTagProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a list containing the latest detections, which may be stale.');
    this.getFtcJavaOutputType = function() {
      return 'List<AprilTagDetection>';
    };
  }
};

Blockly.JavaScript['aprilTagProcessor_getDetections'] = function(block) {
  var aprilTagProcessor = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_PROCESSOR', Blockly.JavaScript.ORDER_NONE);
  var code = 'JSON.parse(' + aprilTagIdentifierForJavaScript + '.getDetections(' +
      aprilTagProcessor + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagProcessor_getDetections'] = function(block) {
  var aprilTagProcessor = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_PROCESSOR', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagProcessor + '.getDetections()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['aprilTagProcessor_getLatestDetections'] = Blockly.Blocks['aprilTagProcessor_getDetections'];
Blockly.JavaScript['aprilTagProcessor_getLatestDetections'] = Blockly.JavaScript['aprilTagProcessor_getDetections'];
Blockly.FtcJava['aprilTagProcessor_getLatestDetections'] = Blockly.FtcJava['aprilTagProcessor_getDetections'];


Blockly.Blocks['aprilTagProcessor_getFreshDetections'] = {
  init: function() {
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('AprilTagProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('getFreshDetections'));
    this.appendValueInput('APRIL_TAG_PROCESSOR').setCheck('AprilTagProcessor')
        .appendField('aprilTagProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a list containing detections that were detected since the last call to this method, ' +
        'or null if no new detections are available. This is useful to avoid re-processing the same ' +
        'detections multiple times.');
    this.getFtcJavaOutputType = function() {
      return 'List<AprilTagDetection>';
    };
  }
};

Blockly.JavaScript['aprilTagProcessor_getFreshDetections'] = function(block) {
  var aprilTagProcessor = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_PROCESSOR', Blockly.JavaScript.ORDER_NONE);
  var code = 'nullOrJson(' + aprilTagIdentifierForJavaScript + '.getFreshDetections(' +
      aprilTagProcessor + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagProcessor_getFreshDetections'] = function(block) {
  var aprilTagProcessor = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_PROCESSOR', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagProcessor + '.getFreshDetections()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['aprilTagProcessor_getDetectionsUpdate'] = Blockly.Blocks['aprilTagProcessor_getFreshDetections'];
Blockly.JavaScript['aprilTagProcessor_getDetectionsUpdate'] = Blockly.JavaScript['aprilTagProcessor_getFreshDetections'];
Blockly.FtcJava['aprilTagProcessor_getDetectionsUpdate'] = Blockly.FtcJava['aprilTagProcessor_getFreshDetections'];


Blockly.Blocks['aprilTagDetection_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['id', 'id'],
        ['hamming', 'hamming'],
        ['decisionMargin', 'decisionMargin'],
        ['center.x', 'center.x'],
        ['center.y', 'center.y'],
        ['corners[0].x', 'corners[0].x'],
        ['corners[0].y', 'corners[0].y'],
        ['corners[1].x', 'corners[1].x'],
        ['corners[1].y', 'corners[1].y'],
        ['corners[2].x', 'corners[2].x'],
        ['corners[2].y', 'corners[2].y'],
        ['corners[3].x', 'corners[3].x'],
        ['corners[3].y', 'corners[3].y'],
        ['ftcPose.x', 'ftcPose.x'],
        ['ftcPose.y', 'ftcPose.y'],
        ['ftcPose.z', 'ftcPose.z'],
        ['ftcPose.yaw', 'ftcPose.yaw'],
        ['ftcPose.pitch', 'ftcPose.pitch'],
        ['ftcPose.roll', 'ftcPose.roll'],
        ['ftcPose.range', 'ftcPose.range'],
        ['ftcPose.bearing', 'ftcPose.bearing'],
        ['ftcPose.elevation', 'ftcPose.elevation'],
        ['rawPose.x', 'rawPose.x'],
        ['rawPose.y', 'rawPose.y'],
        ['rawPose.z', 'rawPose.z'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('AprilTagDetection'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('APRIL_TAG_DETECTION').setCheck('AprilTagDetection')
        .appendField('aprilTagDetection')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['id', 'Returns the id field of the AprilTagDetection.'],
        ['hamming', 'Returns the hamming field of the AprilTagDetection.'],
        ['decisionMargin', 'Returns the decisionMargin field of the AprilTagDetection.'],
        ['center.x', 'Returns the center.x field of the AprilTagDetection.'],
        ['center.y', 'Returns the center.y field of the AprilTagDetection.'],
        ['corners[0].x', 'Returns the corners[0].x field of the AprilTagDetection.'],
        ['corners[0].y', 'Returns the corners[0].y field of the AprilTagDetection.'],
        ['corners[1].x', 'Returns the corners[1].x field of the AprilTagDetection.'],
        ['corners[1].y', 'Returns the corners[1].y field of the AprilTagDetection.'],
        ['corners[2].x', 'Returns the corners[2].x field of the AprilTagDetection.'],
        ['corners[2].y', 'Returns the corners[2].y field of the AprilTagDetection.'],
        ['corners[3].x', 'Returns the corners[3].x field of the AprilTagDetection.'],
        ['corners[3].y', 'Returns the corners[3].y field of the AprilTagDetection.'],
        ['ftcPose.x', 'Returns the ftcPose.x field of the AprilTagDetection.'],
        ['ftcPose.y', 'Returns the ftcPose.y field of the AprilTagDetection.'],
        ['ftcPose.z', 'Returns the ftcPose.z field of the AprilTagDetection.'],
        ['ftcPose.yaw', 'Returns the ftcPose.yaw field of the AprilTagDetection.'],
        ['ftcPose.pitch', 'Returns the ftcPose.pitch field of the AprilTagDetection.'],
        ['ftcPose.roll', 'Returns the ftcPose.roll field of the AprilTagDetection.'],
        ['ftcPose.range', 'Returns the ftcPose.range field of the AprilTagDetection.'],
        ['ftcPose.bearing', 'Returns the ftcPose.bearing field of the AprilTagDetection.'],
        ['ftcPose.elevation', 'Returns the ftcPose.elevation field of the AprilTagDetection.'],
        ['rawPose.x', 'Returns the rawPose.x field of the AprilTagDetection.'],
        ['rawPose.y', 'Returns the rawPose.y field of the AprilTagDetection.'],
        ['rawPose.z', 'Returns the rawPose.z field of the AprilTagDetection.'],
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
        case 'id':
        case 'hamming':
          return 'int';
        case 'decisionMargin':
          return 'float';
        case 'center.x':
        case 'center.y':
        case 'corners[0].x':
        case 'corners[0].y':
        case 'corners[1].x':
        case 'corners[1].y':
        case 'corners[2].x':
        case 'corners[2].y':
        case 'corners[3].x':
        case 'corners[3].y':
        case 'ftcPose.x':
        case 'ftcPose.y':
        case 'ftcPose.z':
        case 'ftcPose.yaw':
        case 'ftcPose.pitch':
        case 'ftcPose.roll':
        case 'ftcPose.range':
        case 'ftcPose.bearing':
        case 'ftcPose.elevation':
        case 'rawPose.x':
        case 'rawPose.y':
        case 'rawPose.z':
          return 'double';
        default:
          throw 'Unexpected property ' + property + ' (aprilTagDetection_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['aprilTagDetection_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.JavaScript.ORDER_MEMBER);
  var code = aprilTagDetection + '.' + property;
  var blockLabel = 'AprilTagDetection.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['aprilTagDetection_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagDetection + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['aprilTagDetection_getProperty_String'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['metadata.name', 'metadata.name'],
    ];
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField(createNonEditableField('AprilTagDetection'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('APRIL_TAG_DETECTION').setCheck('AprilTagDetection')
        .appendField('aprilTagDetection')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['metadata.name', 'Returns the metadata.name field of the AprilTagDetection.'],
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

Blockly.JavaScript['aprilTagDetection_getProperty_String'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.JavaScript.ORDER_MEMBER);
  var code = aprilTagDetection + '.' + property;
  var blockLabel = 'AprilTagDetection.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['aprilTagDetection_getProperty_String'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagDetection + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['aprilTagDetection_getProperty_IsNotNull'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['metadata', 'metadata'],
        ['ftcPose', 'ftcPose'],
        ['rawPose', 'rawPose'],
    ];
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField(createNonEditableField('AprilTagDetection'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP')
        .appendField('!=')
        .appendField(createNonEditableField('null'));
    this.appendValueInput('APRIL_TAG_DETECTION').setCheck('AprilTagDetection')
        .appendField('aprilTagDetection')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['metadata', 'Returns true if the metadata field of the AprilTagDetection is not null.'],
        ['ftcPose', 'Returns true if the ftcPose field of the AprilTagDetection is not null.'],
        ['rawPose', 'Returns true if the rawPose field of the AprilTagDetection is not null.'],
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

Blockly.JavaScript['aprilTagDetection_getProperty_IsNotNull'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.JavaScript.ORDER_MEMBER);
  var code = aprilTagDetection + '.' + property + ' != null';
  var blockLabel = 'AprilTagDetection.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['aprilTagDetection_getProperty_IsNotNull'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagDetection + '.' + property + ' != null';
  return [code, Blockly.FtcJava.ORDER_EQUALITY];
};

Blockly.Blocks['aprilTagDetection_getProperty_AprilTagMetadata'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['metadata', 'metadata'],
    ];
    this.setOutput(true, 'AprilTagMetadata');
    this.appendDummyInput()
        .appendField(createNonEditableField('AprilTagDetection'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('APRIL_TAG_DETECTION').setCheck('AprilTagDetection')
        .appendField('aprilTagDetection')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['metadata', 'Returns the metadata field of the AprilTagDetection.'],
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

Blockly.JavaScript['aprilTagDetection_getProperty_AprilTagMetadata'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.JavaScript.ORDER_MEMBER);
  var code = aprilTagDetection + '.' + property;
  var blockLabel = 'AprilTagDetection.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['aprilTagDetection_getProperty_AprilTagMetadata'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagDetection + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['aprilTagDetection_getProperty_AprilTagPoseFtc'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['ftcPose', 'ftcPose'],
    ];
    this.setOutput(true, 'AprilTagPoseFtc');
    this.appendDummyInput()
        .appendField(createNonEditableField('AprilTagDetection'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('APRIL_TAG_DETECTION').setCheck('AprilTagDetection')
        .appendField('aprilTagDetection')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['ftcPose', 'Returns the ftcPose field of the AprilTagDetection.'],
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

Blockly.JavaScript['aprilTagDetection_getProperty_AprilTagPoseFtc'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.JavaScript.ORDER_MEMBER);
  var o = aprilTagDetection + '.' + property;
  var code = aprilTagIdentifierForJavaScript + '.createAprilTagPoseFtc(' +
      '"GETTER", "AprilTagDetection", "' + property + '", ' +
      'JSON.stringify(' + o + '))';
  var wrappedCode = 'evalIfTruthy(' + o + ', \'' + code + '\', null)';
  return [wrappedCode, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagDetection_getProperty_AprilTagPoseFtc'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagDetection + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['aprilTagDetection_getProperty_AprilTagPoseRaw'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['rawPose', 'rawPose'],
    ];
    this.setOutput(true, 'AprilTagPoseRaw');
    this.appendDummyInput()
        .appendField(createNonEditableField('AprilTagDetection'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('APRIL_TAG_DETECTION').setCheck('AprilTagDetection')
        .appendField('aprilTagDetection')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['rawPose', 'Returns the rawPose field of the AprilTagDetection.'],
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

Blockly.JavaScript['aprilTagDetection_getProperty_AprilTagPoseRaw'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.JavaScript.ORDER_MEMBER);
  var o = aprilTagDetection + '.' + property;
  var code = aprilTagIdentifierForJavaScript + '.createAprilTagPoseRaw(' +
      '"GETTER", "AprilTagDetection", "' + property + '", ' +
      'JSON.stringify(' + o + '))';
  var wrappedCode = 'evalIfTruthy(' + o + ', \'' + code + '\', null)';
  return [wrappedCode, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagDetection_getProperty_AprilTagPoseRaw'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagDetection + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['aprilTagDetection_getProperty_MatrixF'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['rawPose.R', 'rawPose.R'],
    ];
    this.setOutput(true, 'MatrixF');
    this.appendDummyInput()
        .appendField(createNonEditableField('AprilTagDetection'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('APRIL_TAG_DETECTION').setCheck('AprilTagDetection')
        .appendField('aprilTagDetection')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['rawPose.R', 'Returns the rawPose.R field of the AprilTagDetection.'],
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

Blockly.JavaScript['aprilTagDetection_getProperty_MatrixF'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.JavaScript.ORDER_MEMBER);
  var o = aprilTagDetection + '.' + property;
  var code = aprilTagIdentifierForJavaScript + '.createMatrixF(' +
      '"GETTER", "AprilTagDetection", "' + property + '", ' +
      'JSON.stringify(' + o + '))';
  var wrappedCode = 'evalIfTruthy(' + o + ', \'' + code + '\', null)';
  return [wrappedCode, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagDetection_getProperty_MatrixF'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagDetection = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_DETECTION', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagDetection + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

// AprilTagGameDatabase

Blockly.Blocks['aprilTagGameDatabase_getCurrentGameTagLibrary'] = {
  init: function() {
    this.setOutput(true, 'AprilTagLibrary');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('AprilTagGameDatabase'))
        .appendField('.')
        .appendField(createNonEditableField('getCurrentGameTagLibrary'));
    this.setColour(functionColor);
    this.setTooltip('Returns the tag library for the current game.');
  }
};

Blockly.JavaScript['aprilTagGameDatabase_getCurrentGameTagLibrary'] = function(block) {
  var code = aprilTagIdentifierForJavaScript + '.getCurrentGameTagLibrary()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagGameDatabase_getCurrentGameTagLibrary'] = function(block) {
  Blockly.FtcJava.generateImport_('AprilTagGameDatabase');
  var code = 'AprilTagGameDatabase.getCurrentGameTagLibrary()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['aprilTagGameDatabase_getCenterStageTagLibrary'] = {
  init: function() {
    this.setOutput(true, 'AprilTagLibrary');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('AprilTagGameDatabase'))
        .appendField('.')
        .appendField(createNonEditableField('getCenterStageTagLibrary'));
    this.setColour(functionColor);
    this.setTooltip('Returns the tag library for the Center Stage FTC game.');
  }
};

Blockly.JavaScript['aprilTagGameDatabase_getCenterStageTagLibrary'] = function(block) {
  var code = aprilTagIdentifierForJavaScript + '.getCenterStageTagLibrary()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagGameDatabase_getCenterStageTagLibrary'] = function(block) {
  Blockly.FtcJava.generateImport_('AprilTagGameDatabase');
  var code = 'AprilTagGameDatabase.getCenterStageTagLibrary()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['aprilTagGameDatabase_getSampleTagLibrary'] = {
  init: function() {
    this.setOutput(true, 'AprilTagLibrary');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('AprilTagGameDatabase'))
        .appendField('.')
        .appendField(createNonEditableField('getSampleTagLibrary'));
    this.setColour(functionColor);
    this.setTooltip('Returns the tag library for the tags used in the sample OpModes.');
  }
};

Blockly.JavaScript['aprilTagGameDatabase_getSampleTagLibrary'] = function(block) {
  var code = aprilTagIdentifierForJavaScript + '.getSampleTagLibrary()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagGameDatabase_getSampleTagLibrary'] = function(block) {
  Blockly.FtcJava.generateImport_('AprilTagGameDatabase');
  var code = 'AprilTagGameDatabase.getSampleTagLibrary()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// AprilTagLibrary.Builder

Blockly.Blocks['aprilTagLibraryBuilder_create_assign'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('set')
        .appendField(new Blockly.FieldVariable('myAprilTagLibraryBuilder', null, ['AprilTagLibrary.Builder'], 'AprilTagLibrary.Builder'),
            'APRIL_TAG_LIBRARY_BUILDER')
        .appendField('to')
        .appendField('new')
        .appendField(createNonEditableField('AprilTagLibrary.Builder'));
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(builderColor);
    this.setTooltip('Creates a new AprilTagLibrary.Builder object and assigns it to a variable.');
  }
};

Blockly.JavaScript['aprilTagLibraryBuilder_create_assign'] = function(block) {
  var varName = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  return varName + ' = ' + aprilTagIdentifierForJavaScript + '.createAprilTagLibraryBuilder();\n';
};

Blockly.FtcJava['aprilTagLibraryBuilder_create_assign'] = function(block) {
  var varName = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  Blockly.FtcJava.generateImport_('AprilTagLibrary');
  return varName + ' = new AprilTagLibrary.Builder();\n';
};

Blockly.Blocks['aprilTagLibraryBuilder_setAllowOverwrite'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagLibraryBuilder', null, ['AprilTagLibrary.Builder'], 'AprilTagLibrary.Builder'),
            'APRIL_TAG_LIBRARY_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setAllowOverwrite'));
    this.appendValueInput('ALLOW_OVERWRITE').setCheck('Boolean');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets whether to allow a new tag to be added when the library already contains a tag with the same ID.');
  }
};

Blockly.JavaScript['aprilTagLibraryBuilder_setAllowOverwrite'] = function(block) {
  var aprilTagLibraryBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  var allowOverwrite = Blockly.JavaScript.valueToCode(
      block, 'ALLOW_OVERWRITE', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.setAllowOverwrite(' +
      aprilTagLibraryBuilder + ', ' + allowOverwrite + ');\n';
};

Blockly.FtcJava['aprilTagLibraryBuilder_setAllowOverwrite'] = function(block) {
  var aprilTagLibraryBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  var allowOverwrite = Blockly.FtcJava.valueToCode(
      block, 'ALLOW_OVERWRITE', Blockly.FtcJava.ORDER_NONE);
  return aprilTagLibraryBuilder + '.setAllowOverwrite(' + allowOverwrite + ');\n';
};

Blockly.Blocks['aprilTagLibraryBuilder_addTag_withMetadata'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagLibraryBuilder', null, ['AprilTagLibrary.Builder'], 'AprilTagLibrary.Builder'),
            'APRIL_TAG_LIBRARY_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('addTag'));
    this.appendValueInput('APRIL_TAG_METADATA').setCheck('AprilTagMetadata');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Adds a tag to the AprilTagLibrary.Builder.');
  }
};

Blockly.JavaScript['aprilTagLibraryBuilder_addTag_withMetadata'] = function(block) {
  var aprilTagLibraryBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  var aprilTagMetadata = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_METADATA', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.addTag_WithMetadata(' +
      aprilTagLibraryBuilder + ', JSON.stringify(' + aprilTagMetadata + '));\n';
};

Blockly.FtcJava['aprilTagLibraryBuilder_addTag_withMetadata'] = function(block) {
  var aprilTagLibraryBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  var aprilTagMetadata = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_METADATA', Blockly.FtcJava.ORDER_NONE);
  return aprilTagLibraryBuilder + '.addTag(' + aprilTagMetadata + ');\n';
};

Blockly.Blocks['aprilTagLibraryBuilder_addTag'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagLibraryBuilder', null, ['AprilTagLibrary.Builder'], 'AprilTagLibrary.Builder'),
            'APRIL_TAG_LIBRARY_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('addTag'));
    this.appendValueInput('ID').setCheck('Number')
        .appendField('id').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('NAME').setCheck('String')
        .appendField('name').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TAG_SIZE').setCheck('Number')
        .appendField('tagSize').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('FIELD_POSITION').setCheck('VectorF')
        .appendField('fieldPosition').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('FIELD_ORIENTATION').setCheck('Quaternion')
        .appendField('fieldOrientation').setAlign(Blockly.ALIGN_RIGHT);
    this.setInputsInline(false);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Adds a tag to the AprilTagLibrary.Builder.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'ID':
          return 'int';
        case 'TAG_SIZE':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['aprilTagLibraryBuilder_addTag'] = function(block) {
  var aprilTagLibraryBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  var id = Blockly.JavaScript.valueToCode(
      block, 'ID', Blockly.JavaScript.ORDER_COMMA);
  var name = Blockly.JavaScript.valueToCode(
      block, 'NAME', Blockly.JavaScript.ORDER_COMMA);
  var tagSize = Blockly.JavaScript.valueToCode(
      block, 'TAG_SIZE', Blockly.JavaScript.ORDER_COMMA);
  var fieldPosition = Blockly.JavaScript.valueToCode(
      block, 'FIELD_POSITION', Blockly.JavaScript.ORDER_COMMA);
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var fieldOrientation = Blockly.JavaScript.valueToCode(
      block, 'FIELD_ORIENTATION', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.addTag(' + aprilTagLibraryBuilder + ', ' +
      id + ', ' + name + ', ' + tagSize + ', ' + fieldPosition + ', ' +
      distanceUnit + ', ' + fieldOrientation + ');\n';
};

Blockly.FtcJava['aprilTagLibraryBuilder_addTag'] = function(block) {
  var aprilTagLibraryBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  var id = Blockly.FtcJava.valueToCode(
      block, 'ID', Blockly.FtcJava.ORDER_COMMA);
  var name = Blockly.FtcJava.valueToCode(
      block, 'NAME', Blockly.FtcJava.ORDER_COMMA);
  var tagSize = Blockly.FtcJava.valueToCode(
      block, 'TAG_SIZE', Blockly.FtcJava.ORDER_COMMA);
  var fieldPosition = Blockly.FtcJava.valueToCode(
      block, 'FIELD_POSITION', Blockly.FtcJava.ORDER_COMMA);
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var fieldOrientation = Blockly.FtcJava.valueToCode(
      block, 'FIELD_ORIENTATION', Blockly.FtcJava.ORDER_COMMA);
  return aprilTagLibraryBuilder + '.addTag(' +
      id + ', ' + name + ', ' + tagSize + ', ' + fieldPosition + ', ' +
      distanceUnit + ', ' + fieldOrientation + ');\n';
};

Blockly.Blocks['aprilTagLibraryBuilder_addTag_withoutPoseInfo'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagLibraryBuilder', null, ['AprilTagLibrary.Builder'], 'AprilTagLibrary.Builder'),
            'APRIL_TAG_LIBRARY_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('addTag'));
    this.appendValueInput('ID').setCheck('Number')
        .appendField('id').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('NAME').setCheck('String')
        .appendField('name').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TAG_SIZE').setCheck('Number')
        .appendField('tagSize').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit').setAlign(Blockly.ALIGN_RIGHT);
    this.setInputsInline(false);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Adds a tag, without pose information, to the AprilTagLibrary.Builder.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'ID':
          return 'int';
        case 'TAG_SIZE':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['aprilTagLibraryBuilder_addTag_withoutPoseInfo'] = function(block) {
  var aprilTagLibraryBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  var id = Blockly.JavaScript.valueToCode(
      block, 'ID', Blockly.JavaScript.ORDER_COMMA);
  var name = Blockly.JavaScript.valueToCode(
      block, 'NAME', Blockly.JavaScript.ORDER_COMMA);
  var tagSize = Blockly.JavaScript.valueToCode(
      block, 'TAG_SIZE', Blockly.JavaScript.ORDER_COMMA);
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.addTag_withoutPoseInfo(' + aprilTagLibraryBuilder + ', ' +
      id + ', ' + name + ', ' + tagSize + ', ' + distanceUnit + ');\n';
};

Blockly.FtcJava['aprilTagLibraryBuilder_addTag_withoutPoseInfo'] = function(block) {
  var aprilTagLibraryBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  var id = Blockly.FtcJava.valueToCode(
      block, 'ID', Blockly.FtcJava.ORDER_COMMA);
  var name = Blockly.FtcJava.valueToCode(
      block, 'NAME', Blockly.FtcJava.ORDER_COMMA);
  var tagSize = Blockly.FtcJava.valueToCode(
      block, 'TAG_SIZE', Blockly.FtcJava.ORDER_COMMA);
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  return aprilTagLibraryBuilder + '.addTag(' +
      id + ', ' + name + ', ' + tagSize + ', ' + distanceUnit + ');\n';
};

Blockly.Blocks['aprilTagLibraryBuilder_addTags'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagLibraryBuilder', null, ['AprilTagLibrary.Builder'], 'AprilTagLibrary.Builder'),
            'APRIL_TAG_LIBRARY_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('addTags'));
    this.appendValueInput('APRIL_TAG_LIBRARY').setCheck('AprilTagLibrary');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Adds all the tags from the given AprilTagLibrary to the AprilTagLibrary.Builder.');
  }
};

Blockly.JavaScript['aprilTagLibraryBuilder_addTags'] = function(block) {
  var aprilTagLibraryBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  var aprilTagLibrary = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_LIBRARY', Blockly.JavaScript.ORDER_COMMA);
  return aprilTagIdentifierForJavaScript + '.addTags(' +
      aprilTagLibraryBuilder + ', ' + aprilTagLibrary + ');\n';
};

Blockly.FtcJava['aprilTagLibraryBuilder_addTags'] = function(block) {
  var aprilTagLibraryBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  var aprilTagLibrary = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_LIBRARY', Blockly.FtcJava.ORDER_NONE);
  return aprilTagLibraryBuilder + '.addTags(' + aprilTagLibrary + ');\n';
};

Blockly.Blocks['aprilTagLibraryBuilder_build'] = {
  init: function() {
    this.setOutput(true, 'AprilTagLibrary');
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myAprilTagLibraryBuilder', null, ['AprilTagLibrary.Builder'], 'AprilTagLibrary.Builder'),
            'APRIL_TAG_LIBRARY_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('build'));
    this.setInputsInline(true);
    this.setColour(builderColor);
    this.setTooltip('Builds an AprilTagLibrary from the given AprilTagLibrary.Builder.');
  }
};

Blockly.JavaScript['aprilTagLibraryBuilder_build'] = function(block) {
  var aprilTagLibraryBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  var code = aprilTagIdentifierForJavaScript + '.buildAprilTagLibrary(' + aprilTagLibraryBuilder + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagLibraryBuilder_build'] = function(block) {
  var aprilTagLibraryBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('APRIL_TAG_LIBRARY_BUILDER'), Blockly.Variables.NAME_TYPE);
  var code = aprilTagLibraryBuilder + '.build()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// AprilTagLibrary

Blockly.Blocks['aprilTagLibrary_lookupTag'] = {
  init: function() {
    this.setOutput(true, 'AprilTagMetadata');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('AprilTagLibrary'))
        .appendField('.')
        .appendField(createNonEditableField('lookupTag'));
    this.appendValueInput('APRIL_TAG_LIBRARY').setCheck('AprilTagLibrary')
        .appendField('aprilTagLibrary')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ID').setCheck('Number')
        .appendField('id')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Looks up a tag id in the given AprilTagLibrary.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'ID':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['aprilTagLibrary_lookupTag'] = function(block) {
  var aprilTagLibrary = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_LIBRARY', Blockly.JavaScript.ORDER_NONE);
  var id = Blockly.JavaScript.valueToCode(
      block, 'ID', Blockly.JavaScript.ORDER_COMMA);
  var code = 'JSON.parse(' + aprilTagIdentifierForJavaScript + '.lookupTag(' +
      aprilTagLibrary + ', ' + id + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagLibrary_lookupTag'] = function(block) {
  var aprilTagLibrary = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_LIBRARY', Blockly.FtcJava.ORDER_MEMBER);
  var id = Blockly.FtcJava.valueToCode(
      block, 'ID', Blockly.FtcJava.ORDER_NONE);
  var code = aprilTagLibrary + '.lookupTag(' + id + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// AprilTagMetadata

Blockly.Blocks['aprilTagMetadata_create'] = {
  init: function() {
    this.setOutput(true, 'AprilTagMetadata');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('AprilTagMetadata'));
    this.appendValueInput('ID').setCheck('Number')
        .appendField('id')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('NAME').setCheck('String')
        .appendField('name')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TAG_SIZE').setCheck('Number')
        .appendField('tagSize')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('FIELD_POSITION').setCheck('VectorF')
        .appendField('fieldPosition')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('FIELD_ORIENTATION').setCheck('Quaternion')
        .appendField('fieldOrientation')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new AprilTagMetadata object.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'ID':
          return 'int';
        case 'TAG_SIZE':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['aprilTagMetadata_create'] = function(block) {
  var id = Blockly.JavaScript.valueToCode(
      block, 'ID', Blockly.JavaScript.ORDER_COMMA);
  var name = Blockly.JavaScript.valueToCode(
      block, 'NAME', Blockly.JavaScript.ORDER_COMMA);
  var tagSize = Blockly.JavaScript.valueToCode(
      block, 'TAG_SIZE', Blockly.JavaScript.ORDER_COMMA);
  var fieldPosition = Blockly.JavaScript.valueToCode(
      block, 'FIELD_POSITION', Blockly.JavaScript.ORDER_COMMA);
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var fieldOrientation = Blockly.JavaScript.valueToCode(
      block, 'FIELD_ORIENTATION', Blockly.JavaScript.ORDER_COMMA);
  var code = 'JSON.parse(' + aprilTagIdentifierForJavaScript + '.createMetadata(' +
      id + ', ' + name + ', ' + tagSize + ', ' + fieldPosition + ', ' +
      distanceUnit + ', ' + fieldOrientation + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagMetadata_create'] = function(block) {
  Blockly.FtcJava.generateImport_('AprilTagMetadata');
  var id = Blockly.FtcJava.valueToCode(
      block, 'ID', Blockly.FtcJava.ORDER_COMMA);
  var name = Blockly.FtcJava.valueToCode(
      block, 'NAME', Blockly.FtcJava.ORDER_COMMA);
  var tagSize = Blockly.FtcJava.valueToCode(
      block, 'TAG_SIZE', Blockly.FtcJava.ORDER_COMMA);
  var fieldPosition = Blockly.FtcJava.valueToCode(
      block, 'FIELD_POSITION', Blockly.FtcJava.ORDER_COMMA);
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var fieldOrientation = Blockly.FtcJava.valueToCode(
      block, 'FIELD_ORIENTATION', Blockly.FtcJava.ORDER_COMMA);
  var code = 'new AprilTagMetdata(' +
      id + ', ' + name + ', ' + tagSize + ', ' + fieldPosition + ', ' +
      distanceUnit + ', ' + fieldOrientation + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['aprilTagMetadata_create_withoutPoseInfo'] = {
  init: function() {
    this.setOutput(true, 'AprilTagMetadata');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('AprilTagMetadata'));
    this.appendValueInput('ID').setCheck('Number')
        .appendField('id')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('NAME').setCheck('String')
        .appendField('name')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TAG_SIZE').setCheck('Number')
        .appendField('tagSize')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new AprilTagMetadata object, without pose information.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'ID':
          return 'int';
        case 'TAG_SIZE':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['aprilTagMetadata_create_withoutPoseInfo'] = function(block) {
  var id = Blockly.JavaScript.valueToCode(
      block, 'ID', Blockly.JavaScript.ORDER_COMMA);
  var name = Blockly.JavaScript.valueToCode(
      block, 'NAME', Blockly.JavaScript.ORDER_COMMA);
  var tagSize = Blockly.JavaScript.valueToCode(
      block, 'TAG_SIZE', Blockly.JavaScript.ORDER_COMMA);
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = 'JSON.parse(' + aprilTagIdentifierForJavaScript + '.createMetadata_withoutPoseInfo(' +
      id + ', ' + name + ', ' + tagSize + ', ' + distanceUnit + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagMetadata_create_withoutPoseInfo'] = function(block) {
  Blockly.FtcJava.generateImport_('AprilTagMetadata');
  var id = Blockly.FtcJava.valueToCode(
      block, 'ID', Blockly.FtcJava.ORDER_COMMA);
  var name = Blockly.FtcJava.valueToCode(
      block, 'NAME', Blockly.FtcJava.ORDER_COMMA);
  var tagSize = Blockly.FtcJava.valueToCode(
      block, 'TAG_SIZE', Blockly.FtcJava.ORDER_COMMA);
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = 'new AprilTagMetdata(' +
      id + ', ' + name + ', ' + tagSize + ', ' + distanceUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['aprilTagMetadata_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['id', 'id'],
        ['tagsize', 'tagsize'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('AprilTagMetadata'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('APRIL_TAG_METADATA').setCheck('AprilTagMetadata')
        .appendField('aprilTagMetadata')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['id', 'Returns the id field of the AprilTagMetadata.'],
        ['tagsize', 'Returns the tagsize field of the AprilTagMetadata.'],
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
        case 'id':
          return 'int';
        case 'tagsize':
          return 'double';
        default:
          throw 'Unexpected property ' + property + ' (aprilTagMetadata_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['aprilTagMetadata_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagMetadata = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_METADATA', Blockly.JavaScript.ORDER_MEMBER);
  var code = aprilTagMetadata + '.' + property;
  var blockLabel = 'AprilTagMetadata.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['aprilTagMetadata_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagMetadata = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_METADATA', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagMetadata + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['aprilTagMetadata_getProperty_String'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['name', 'name'],
    ];
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField(createNonEditableField('AprilTagMetadata'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('APRIL_TAG_METADATA').setCheck('AprilTagMetadata')
        .appendField('aprilTagMetadata')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['name', 'Returns the name field of the AprilTagMetadata.'],
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

Blockly.JavaScript['aprilTagMetadata_getProperty_String'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagMetadata = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_METADATA', Blockly.JavaScript.ORDER_MEMBER);
  var code = aprilTagMetadata + '.' + property;
  var blockLabel = 'AprilTagMetadata.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['aprilTagMetadata_getProperty_String'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagMetadata = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_METADATA', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagMetadata + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['aprilTagMetadata_getProperty_DistanceUnit'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['distanceUnit', 'distanceUnit'],
    ];
    this.setOutput(true, 'DistanceUnit');
    this.appendDummyInput()
        .appendField(createNonEditableField('AprilTagMetadata'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('APRIL_TAG_METADATA').setCheck('AprilTagMetadata')
        .appendField('aprilTagMetadata')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['distanceUnit', 'Returns the distanceUnit field of the AprilTagMetadata.'],
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

Blockly.JavaScript['aprilTagMetadata_getProperty_DistanceUnit'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagMetadata = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_METADATA', Blockly.JavaScript.ORDER_MEMBER);
  var code = aprilTagMetadata + '.' + property;
  var blockLabel = 'AprilTagMetadata.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['aprilTagMetadata_getProperty_DistanceUnit'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagMetadata = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_METADATA', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagMetadata + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['aprilTagMetadata_getProperty_VectorF'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['fieldPosition', 'fieldPosition'],
    ];
    this.setOutput(true, 'VectorF');
    this.appendDummyInput()
        .appendField(createNonEditableField('AprilTagMetadata'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('APRIL_TAG_METADATA').setCheck('AprilTagMetadata')
        .appendField('aprilTagMetadata')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['fieldPosition', 'Returns the fieldPosition field of the AprilTagMetadata.'],
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

Blockly.JavaScript['aprilTagMetadata_getProperty_VectorF'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagMetadata = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_METADATA', Blockly.JavaScript.ORDER_MEMBER);
  var o = aprilTagMetadata + '.' + property;
  var code = aprilTagIdentifierForJavaScript + '.createVectorF(' +
      '"GETTER", "AprilTagMetadata", "' + property + '", ' +
      'JSON.stringify(' + o + '))';
  var wrappedCode = 'evalIfTruthy(' + o + ', \'' + code + '\', null)';
  return [wrappedCode, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagMetadata_getProperty_VectorF'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagMetadata = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_METADATA', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagMetadata + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['aprilTagMetadata_getProperty_Quaternion'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['fieldOrientation', 'fieldOrientation'],
    ];
    this.setOutput(true, 'Quaternion');
    this.appendDummyInput()
        .appendField(createNonEditableField('AprilTagMetadata'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('APRIL_TAG_METADATA').setCheck('AprilTagMetadata')
        .appendField('aprilTagMetadata')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['fieldOrientation', 'Returns the fieldOrientation field of the AprilTagMetadata.'],
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

Blockly.JavaScript['aprilTagMetadata_getProperty_Quaternion'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagMetadata = Blockly.JavaScript.valueToCode(
      block, 'APRIL_TAG_METADATA', Blockly.JavaScript.ORDER_MEMBER);
  var o = aprilTagMetadata + '.' + property;
  var code = aprilTagIdentifierForJavaScript + '.createQuaternion(' +
      '"GETTER", "AprilTagMetadata", "' + property + '", ' +
      'JSON.stringify(' + o + '))';
  var wrappedCode = 'evalIfTruthy(' + o + ', \'' + code + '\', null)';
  return [wrappedCode, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['aprilTagMetadata_getProperty_Quaternion'] = function(block) {
  var property = block.getFieldValue('PROP');
  var aprilTagMetadata = Blockly.FtcJava.valueToCode(
      block, 'APRIL_TAG_METADATA', Blockly.FtcJava.ORDER_MEMBER);
  var code = aprilTagMetadata + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

// TagFamily

Blockly.Blocks['aprilTag_typedEnum_tagFamily'] = {
  init: function() {
    var TAG_FAMILY_CHOICES = [
        ['TAG_36h11', 'TAG_36h11'],
        ['TAG_25h9', 'TAG_25h9'],
        ['TAG_16h5', 'TAG_16h5'],
        ['TAG_standard41h12', 'TAG_standard41h12'],
    ];
    this.setOutput(true, 'AprilTagProcessor.TagFamily');
    this.appendDummyInput()
        .appendField(createNonEditableField('TagFamily'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(TAG_FAMILY_CHOICES), 'TAG_FAMILY');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['TAG_36h11', 'The TagFamily value TAG_36h11.'],
        ['TAG_25h9', 'The TagFamily value TAG_25h9.'],
        ['TAG_16h5', 'The TagFamily value TAG_16h5.'],
        ['TAG_standard41h12', 'The TagFamily value TAG_standard41h12.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('TAG_FAMILY');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['aprilTag_typedEnum_tagFamily'] = function(block) {
  var code = '"' + block.getFieldValue('TAG_FAMILY') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['aprilTag_typedEnum_tagFamily'] = function(block) {
  var code = 'AprilTagProcessor.TagFamily.' + block.getFieldValue('TAG_FAMILY');
  Blockly.FtcJava.generateImport_('AprilTagProcessor');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

// PoseSolver

Blockly.Blocks['aprilTag_typedEnum_poseSolver'] = {
  init: function() {
    var POSE_SOLVER_CHOICES = [
        ['APRILTAG_BUILTIN', 'APRILTAG_BUILTIN'],
        ['OPENCV_ITERATIVE', 'OPENCV_ITERATIVE'],
        ['OPENCV_SOLVEPNP_EPNP', 'OPENCV_SOLVEPNP_EPNP'],
        ['OPENCV_IPPE', 'OPENCV_IPPE'],
        ['OPENCV_IPPE_SQUARE', 'OPENCV_IPPE_SQUARE'],
        ['OPENCV_SQPNP', 'OPENCV_SQPNP'],
    ];
    this.setOutput(true, 'AprilTagProcessor.PoseSolver');
    this.appendDummyInput()
        .appendField(createNonEditableField('PoseSolver'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(POSE_SOLVER_CHOICES), 'POSE_SOLVER');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['APRILTAG_BUILTIN', 'The PoseSolver value APRILTAG_BUILTIN.'],
        ['OPENCV_ITERATIVE', 'The PoseSolver value OPENCV_ITERATIVE.'],
        ['OPENCV_SOLVEPNP_EPNP', 'The PoseSolver value OPENCV_SOLVEPNP_EPNP.'],
        ['OPENCV_IPPE', 'The PoseSolver value OPENCV_IPPE.'],
        ['OPENCV_IPPE_SQUARE', 'The PoseSolver value OPENCV_IPPE_SQUARE.'],
        ['OPENCV_SQPNP', 'The PoseSolver value OPENCV_SQPNP.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('POSE_SOLVER');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['aprilTag_typedEnum_poseSolver'] = function(block) {
  var code = '"' + block.getFieldValue('POSE_SOLVER') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['aprilTag_typedEnum_poseSolver'] = function(block) {
  var code = 'AprilTagProcessor.PoseSolver.' + block.getFieldValue('POSE_SOLVER');
  Blockly.FtcJava.generateImport_('AprilTagProcessor');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

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
 * @fileoverview FTC robot blocks related to TfodProcessor
 * @author Liz Looney
 */


// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// tensorFlowIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// builderColor
// functionColor
// getPropertyColor


Blockly.Blocks['tfodProcessorBuilder_create_assign'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('set')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('to')
        .appendField('new')
        .appendField(createNonEditableField('TfodProcessor.Builder'));
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(builderColor);
    this.setTooltip('Creates a new TfodProcessor.Builder object and assigns it to a variable.');
  }
};

Blockly.JavaScript['tfodProcessorBuilder_create_assign'] = function(block) {
  var varName = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  return varName + ' = ' + tensorFlowIdentifierForJavaScript + '.createBuilder();\n';
};

Blockly.FtcJava['tfodProcessorBuilder_create_assign'] = function(block) {
  var varName = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  Blockly.FtcJava.generateImport_('TfodProcessor');
  return varName + ' = new TfodProcessor.Builder();\n';
};

Blockly.Blocks['tfodProcessorBuilder_setModelAssetName'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setModelAssetName'));
    this.appendValueInput('MODEL_ASSET_NAME').setCheck('String');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the name of the asset where the model can be found.');
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setModelAssetName'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var modelAssetName = Blockly.JavaScript.valueToCode(
      block, 'MODEL_ASSET_NAME', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setModelAssetName(' +
      tfodProcessorBuilder + ', ' + modelAssetName + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setModelAssetName'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var modelAssetName = Blockly.FtcJava.valueToCode(
      block, 'MODEL_ASSET_NAME', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setModelAssetName(' + modelAssetName + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setModelFileName'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setModelFileName'));
    this.appendValueInput('MODEL_FILE_NAME').setCheck('String');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the name of the file where the model can be found.');
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setModelFileName'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var modelFileName = Blockly.JavaScript.valueToCode(
      block, 'MODEL_FILE_NAME', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setModelFileName(' +
      tfodProcessorBuilder + ', ' + modelFileName + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setModelFileName'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var modelFileName = Blockly.FtcJava.valueToCode(
      block, 'MODEL_FILE_NAME', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setModelFileName(' + modelFileName + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setModelLabels'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setModelLabels'));
    this.appendValueInput('LABELS').setCheck('Array');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the full ordered list of labels the model is trained to recognize.');
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setModelLabels'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var labels = Blockly.JavaScript.valueToCode(
      block, 'LABELS', Blockly.JavaScript.ORDER_NONE);
  return tensorFlowIdentifierForJavaScript + '.setModelLabels(' +
      tfodProcessorBuilder + ', JSON.stringify(' + labels + '));\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setModelLabels'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var labels = Blockly.FtcJava.valueToCode(
      block, 'LABELS', Blockly.FtcJava.ORDER_COMMA);
  return tfodProcessorBuilder + '.setModelLabels(' + labels + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setIsModelTensorFlow2'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setIsModelTensorFlow2'));
    this.appendValueInput('IS_MODEL_TENSORFLOW_2').setCheck('Boolean');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets whether the model is a TensorFlow2 model.');
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setIsModelTensorFlow2'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var isModelTensorFlow2 = Blockly.JavaScript.valueToCode(
      block, 'IS_MODEL_TENSORFLOW_2', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setIsModelTensorFlow2(' +
      tfodProcessorBuilder + ', ' + isModelTensorFlow2 + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setIsModelTensorFlow2'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var isModelTensorFlow2 = Blockly.FtcJava.valueToCode(
      block, 'IS_MODEL_TENSORFLOW_2', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setIsModelTensorFlow2(' + isModelTensorFlow2 + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setIsModelQuantized'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setIsModelQuantized'));
    this.appendValueInput('IS_MODEL_QUANTIZED').setCheck('Boolean');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets whether the model is quantized.');
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setIsModelQuantized'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var isModelQuantized = Blockly.JavaScript.valueToCode(
      block, 'IS_MODEL_QUANTIZED', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setIsModelQuantized(' +
      tfodProcessorBuilder + ', ' + isModelQuantized + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setIsModelQuantized'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var isModelQuantized = Blockly.FtcJava.valueToCode(
      block, 'IS_MODEL_QUANTIZED', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setIsModelQuantized(' + isModelQuantized + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setModelInputSize'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setModelInputSize'));
    this.appendValueInput('MODEL_INPUT_SIZE').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the size, in pixels, of images input to the network.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MODEL_INPUT_SIZE':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setModelInputSize'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var modelInputSize = Blockly.JavaScript.valueToCode(
      block, 'MODEL_INPUT_SIZE', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setModelInputSize(' +
      tfodProcessorBuilder + ', ' + modelInputSize + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setModelInputSize'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var modelInputSize = Blockly.FtcJava.valueToCode(
      block, 'MODEL_INPUT_SIZE', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setModelInputSize(' + modelInputSize + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setModelAspectRatio'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setModelAspectRatio'));
    this.appendValueInput('MODEL_ASPECT_RATIO').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the aspect ratio for the images used when the model was created.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MODEL_ASPECT_RATIO':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setModelAspectRatio'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var modelAspectRatio = Blockly.JavaScript.valueToCode(
      block, 'MODEL_ASPECT_RATIO', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setModelAspectRatio(' +
      tfodProcessorBuilder + ', ' + modelAspectRatio + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setModelAspectRatio'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var modelAspectRatio = Blockly.FtcJava.valueToCode(
      block, 'MODEL_ASPECT_RATIO', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setModelAspectRatio(' + modelAspectRatio + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setNumExecutorThreads'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setNumExecutorThreads'));
    this.appendValueInput('NUM_EXECUTOR_THREADS').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Set the number of executor threads to use. Each executor corresponds ' +
        'to one TensorFlow Object Detector.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'NUM_EXECUTOR_THREADS':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setNumExecutorThreads'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var numExecutorThreads = Blockly.JavaScript.valueToCode(
      block, 'NUM_EXECUTOR_THREADS', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setNumExecutorThreads(' +
      tfodProcessorBuilder + ', ' + numExecutorThreads + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setNumExecutorThreads'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var numExecutorThreads = Blockly.FtcJava.valueToCode(
      block, 'NUM_EXECUTOR_THREADS', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setNumExecutorThreads(' + numExecutorThreads + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setNumDetectorThreads'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setNumDetectorThreads'));
    this.appendValueInput('NUM_DETECTOR_THREADS').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the number of threads that each individual TensorFlow object detector is allowed to use.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'NUM_DETECTOR_THREADS':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setNumDetectorThreads'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var numDetectorThreads = Blockly.JavaScript.valueToCode(
      block, 'NUM_DETECTOR_THREADS', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setNumDetectorThreads(' +
      tfodProcessorBuilder + ', ' + numDetectorThreads + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setNumDetectorThreads'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var numDetectorThreads = Blockly.FtcJava.valueToCode(
      block, 'NUM_DETECTOR_THREADS', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setNumDetectorThreads(' + numDetectorThreads + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setMaxNumRecognitions'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setMaxNumRecognitions'));
    this.appendValueInput('MAX_NUM_RECOGNITIONS').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the maximum number of recognitions the network will return.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MAX_NUM_RECOGNITIONS':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setMaxNumRecognitions'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var maxNumRecognitions = Blockly.JavaScript.valueToCode(
      block, 'MAX_NUM_RECOGNITIONS', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setMaxNumRecognitions(' +
      tfodProcessorBuilder + ', ' + maxNumRecognitions + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setMaxNumRecognitions'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var maxNumRecognitions = Blockly.FtcJava.valueToCode(
      block, 'MAX_NUM_RECOGNITIONS', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setMaxNumRecognitions(' + maxNumRecognitions + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setUseObjectTracker'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setUseObjectTracker'));
    this.appendValueInput('USE_OBJECT_TRACKER').setCheck('Boolean');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets whether to use the object tracker.');
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setUseObjectTracker'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var useObjectTracker = Blockly.JavaScript.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setUseObjectTracker(' +
      tfodProcessorBuilder + ', ' + useObjectTracker + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setUseObjectTracker'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var useObjectTracker = Blockly.FtcJava.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setUseObjectTracker(' + useObjectTracker + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setTrackerMaxOverlap'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setTrackerMaxOverlap'));
    this.appendValueInput('TRACKER_MAX_OVERLAP').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the maximum percentage of a box that can be overlapped by another box ' +
        'at recognition time.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'TRACKER_MAX_OVERLAP':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setTrackerMaxOverlap'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var trackerMaxOverlap = Blockly.JavaScript.valueToCode(
      block, 'TRACKER_MAX_OVERLAP', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setTrackerMaxOverlap(' +
      tfodProcessorBuilder + ', ' + trackerMaxOverlap + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setTrackerMaxOverlap'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var trackerMaxOverlap = Blockly.FtcJava.valueToCode(
      block, 'TRACKER_MAX_OVERLAP', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setTrackerMaxOverlap(' + trackerMaxOverlap + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setTrackerMinSize'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setTrackerMinSize'));
    this.appendValueInput('TRACKER_MIN_SIZE').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the minimum size of an object that the object tracker will track.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'TRACKER_MIN_SIZE':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setTrackerMinSize'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var trackerMinSize = Blockly.JavaScript.valueToCode(
      block, 'TRACKER_MIN_SIZE', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setTrackerMinSize(' +
      tfodProcessorBuilder + ', ' + trackerMinSize + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setTrackerMinSize'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var trackerMinSize = Blockly.FtcJava.valueToCode(
      block, 'TRACKER_MIN_SIZE', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setTrackerMinSize(' + trackerMinSize + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setTrackerMarginalCorrelation'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setTrackerMarginalCorrelation'));
    this.appendValueInput('TRACKER_MARGINAL_CORRELATION').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Allow replacement of the tracked box with new results if correlation has ' +
        'dropped below trackerMarginalCorrelation.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'TRACKER_MARGINAL_CORRELATION':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setTrackerMarginalCorrelation'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var trackerMarginalCorrelation = Blockly.JavaScript.valueToCode(
      block, 'TRACKER_MARGINAL_CORRELATION', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setTrackerMarginalCorrelation(' +
      tfodProcessorBuilder + ', ' + trackerMarginalCorrelation + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setTrackerMarginalCorrelation'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var trackerMarginalCorrelation = Blockly.FtcJava.valueToCode(
      block, 'TRACKER_MARGINAL_CORRELATION', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setTrackerMarginalCorrelation(' + trackerMarginalCorrelation + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_setTrackerMinCorrelation'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setTrackerMinCorrelation'));
    this.appendValueInput('TRACKER_MIN_CORRELATION').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Consider an object to be lost if correlation falls below trackerMinCorrelation.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'TRACKER_MIN_CORRELATION':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodProcessorBuilder_setTrackerMinCorrelation'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var trackerMinCorrelation = Blockly.JavaScript.valueToCode(
      block, 'TRACKER_MIN_CORRELATION', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setTrackerMinCorrelation(' +
      tfodProcessorBuilder + ', ' + trackerMinCorrelation + ');\n';
};

Blockly.FtcJava['tfodProcessorBuilder_setTrackerMinCorrelation'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var trackerMinCorrelation = Blockly.FtcJava.valueToCode(
      block, 'TRACKER_MIN_CORRELATION', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessorBuilder + '.setTrackerMinCorrelation(' + trackerMinCorrelation + ');\n';
};

Blockly.Blocks['tfodProcessorBuilder_build'] = {
  init: function() {
    this.setOutput(true, 'TfodProcessor');
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myTfodProcessorBuilder', null, ['TfodProcessor.Builder'], 'TfodProcessor.Builder'),
            'TFOD_PROCESSOR_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('build'));
    this.setInputsInline(true);
    this.setColour(builderColor);
    this.setTooltip('Builds a TfodProcessor from the given TfodProcessor.Builder.');
  }
};

Blockly.JavaScript['tfodProcessorBuilder_build'] = function(block) {
  var tfodProcessorBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var code = tensorFlowIdentifierForJavaScript + '.build(' + tfodProcessorBuilder + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['tfodProcessorBuilder_build'] = function(block) {
  var tfodProcessorBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('TFOD_PROCESSOR_BUILDER'), Blockly.Variables.NAME_TYPE);
  var code = tfodProcessorBuilder + '.build()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['tfodProcessor_easyCreateWithDefaults'] = {
  init: function() {
    this.setOutput(true, 'TfodProcessor');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TfodProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('easyCreateWithDefaults'));
    this.setColour(functionColor);
    this.setTooltip('Creates a new TfodProcessor object, with default values for all settings.');
  }
};

Blockly.JavaScript['tfodProcessor_easyCreateWithDefaults'] = function(block) {
  var code = tensorFlowIdentifierForJavaScript + '.easyCreateWithDefaults()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['tfodProcessor_easyCreateWithDefaults'] = function(block) {
  Blockly.FtcJava.generateImport_('TfodProcessor');
  var code = 'TfodProcessor.easyCreateWithDefaults()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['tfodProcessor_setMinResultConfidence'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TfodProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('setMinResultConfidence'));
    this.appendValueInput('TFOD_PROCESSOR').setCheck('TfodProcessor')
        .appendField('tfodProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MIN_RESULT_CONFIDENCE').setCheck('Number')
        .appendField('minResultConfidence')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the minimum confidence at which to keep recognitions.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MIN_RESULT_CONFIDENCE':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodProcessor_setMinResultConfidence'] = function(block) {
  var tfodProcessor = Blockly.JavaScript.valueToCode(
      block, 'TFOD_PROCESSOR', Blockly.JavaScript.ORDER_COMMA);
  var minResultConfidence = Blockly.JavaScript.valueToCode(
      block, 'MIN_RESULT_CONFIDENCE', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setMinResultConfidence(' +
      tfodProcessor + ', ' + minResultConfidence + ');\n';
};

Blockly.FtcJava['tfodProcessor_setMinResultConfidence'] = function(block) {
  var tfodProcessor = Blockly.FtcJava.valueToCode(
      block, 'TFOD_PROCESSOR', Blockly.FtcJava.ORDER_MEMBER);
  var minResultConfidence = Blockly.FtcJava.valueToCode(
      block, 'MIN_RESULT_CONFIDENCE', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessor + '.setMinResultConfidence(' + minResultConfidence + ');\n';
};

Blockly.Blocks['tfodProcessor_setClippingMargins'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TfodProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('setClippingMargins'));
    this.appendValueInput('TFOD_PROCESSOR').setCheck('TfodProcessor')
        .appendField('tfodProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
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
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the number of pixels to obscure on the left, top, right, and bottom ' +
        'edges of each image passed to the TensorFlow object detector. The size of the images ' +
        'are not changed, but the pixels in the margins are colored black.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'LEFT':
        case 'TOP':
        case 'BOTTOM':
        case 'RIGHT':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodProcessor_setClippingMargins'] = function(block) {
  var tfodProcessor = Blockly.JavaScript.valueToCode(
      block, 'TFOD_PROCESSOR', Blockly.JavaScript.ORDER_COMMA);
  var left = Blockly.JavaScript.valueToCode(
      block, 'LEFT', Blockly.JavaScript.ORDER_COMMA);
  var top = Blockly.JavaScript.valueToCode(
      block, 'TOP', Blockly.JavaScript.ORDER_COMMA);
  var right = Blockly.JavaScript.valueToCode(
      block, 'RIGHT', Blockly.JavaScript.ORDER_COMMA);
  var bottom = Blockly.JavaScript.valueToCode(
      block, 'BOTTOM', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setClippingMargins(' +
      tfodProcessor + ', ' + left + ', ' + top + ', ' + right + ', ' + bottom + ');\n';
};

Blockly.FtcJava['tfodProcessor_setClippingMargins'] = function(block) {
  var tfodProcessor = Blockly.FtcJava.valueToCode(
      block, 'TFOD_PROCESSOR', Blockly.FtcJava.ORDER_MEMBER);
  var left = Blockly.FtcJava.valueToCode(
      block, 'LEFT', Blockly.FtcJava.ORDER_COMMA);
  var top = Blockly.FtcJava.valueToCode(
      block, 'TOP', Blockly.FtcJava.ORDER_COMMA);
  var right = Blockly.FtcJava.valueToCode(
      block, 'RIGHT', Blockly.FtcJava.ORDER_COMMA);
  var bottom = Blockly.FtcJava.valueToCode(
      block, 'BOTTOM', Blockly.FtcJava.ORDER_COMMA);
  return tfodProcessor + '.setClippingMargins(' +
      left + ', ' + top + ', ' + right + ', ' + bottom + ');\n';
};

Blockly.Blocks['tfodProcessor_setZoom'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TfodProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('setZoom'));
    this.appendValueInput('TFOD_PROCESSOR').setCheck('TfodProcessor')
        .appendField('tfodProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MAGNIFICATION').setCheck('Number')
        .appendField('magnification')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Indicates that only the zoomed center area of each image will be ' +
        'passed to the TensorFlow object detector. For no zooming, set magnification to 1.0.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MAGNIFICATION':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodProcessor_setZoom'] = function(block) {
  var tfodProcessor = Blockly.JavaScript.valueToCode(
      block, 'TFOD_PROCESSOR', Blockly.JavaScript.ORDER_COMMA);
  var magnification = Blockly.JavaScript.valueToCode(
      block, 'MAGNIFICATION', Blockly.JavaScript.ORDER_COMMA);
  return tensorFlowIdentifierForJavaScript + '.setZoom(' +
      tfodProcessor + ', ' + magnification + ');\n';
};

Blockly.FtcJava['tfodProcessor_setZoom'] = function(block) {
  var tfodProcessor = Blockly.FtcJava.valueToCode(
      block, 'TFOD_PROCESSOR', Blockly.FtcJava.ORDER_MEMBER);
  var magnification = Blockly.FtcJava.valueToCode(
      block, 'MAGNIFICATION', Blockly.FtcJava.ORDER_NONE);
  return tfodProcessor + '.setZoom(' + magnification + ');\n';
};

Blockly.Blocks['tfodProcessor_getRecognitions'] = {
  init: function() {
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TfodProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('getRecognitions'));
    this.appendValueInput('TFOD_PROCESSOR').setCheck('TfodProcessor')
        .appendField('tfodProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a list containing the latest recognitions, which may be stale.');
    this.getFtcJavaOutputType = function() {
      return 'List<Recognition>';
    };
  }
};

Blockly.JavaScript['tfodProcessor_getRecognitions'] = function(block) {
  var tfodProcessor = Blockly.JavaScript.valueToCode(
      block, 'TFOD_PROCESSOR', Blockly.JavaScript.ORDER_NONE);
  var code = 'JSON.parse(' + tensorFlowIdentifierForJavaScript + '.getRecognitions(' + tfodProcessor + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['tfodProcessor_getRecognitions'] = function(block) {
  var tfodProcessor = Blockly.FtcJava.valueToCode(
      block, 'TFOD_PROCESSOR', Blockly.FtcJava.ORDER_MEMBER);
  var code = tfodProcessor + '.getRecognitions()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['tfodProcessor_getLatestRecognitions'] = Blockly.Blocks['tfodProcessor_getRecognitions'];
Blockly.JavaScript['tfodProcessor_getLatestRecognitions'] = Blockly.JavaScript['tfodProcessor_getRecognitions'];
Blockly.FtcJava['tfodProcessor_getLatestRecognitions'] = Blockly.FtcJava['tfodProcessor_getRecognitions'];

Blockly.Blocks['tfodProcessor_getFreshRecognitions'] = {
  init: function() {
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TfodProcessor'))
        .appendField('.')
        .appendField(createNonEditableField('getFreshRecognitions'));
    this.appendValueInput('TFOD_PROCESSOR').setCheck('TfodProcessor')
        .appendField('tfodProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(' a list containing recognitions that were detected since the last call to this method, ' +
        'or null if no new recognitions are available. This is useful to avoid re-processing the same ' +
        'recognitions multiple times.');
    this.getFtcJavaOutputType = function() {
      return 'List<Recognition>';
    };
  }
};

Blockly.JavaScript['tfodProcessor_getFreshRecognitions'] = function(block) {
  var tfodProcessor = Blockly.JavaScript.valueToCode(
      block, 'TFOD_PROCESSOR', Blockly.JavaScript.ORDER_NONE);
  var code = 'nullOrJson(' + tensorFlowIdentifierForJavaScript + '.getFreshRecognitions(' + tfodProcessor + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['tfodProcessor_getFreshRecognitions'] = function(block) {
  var tfodProcessor = Blockly.FtcJava.valueToCode(
      block, 'TFOD_PROCESSOR', Blockly.FtcJava.ORDER_MEMBER);
  var code = tfodProcessor + '.getFreshRecognitions()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['tfodProcessor_getRecognitionsUpdate'] = Blockly.Blocks['tfodProcessor_getFreshRecognitions'];
Blockly.JavaScript['tfodProcessor_getRecognitionsUpdate'] = Blockly.JavaScript['tfodProcessor_getFreshRecognitions'];
Blockly.FtcJava['tfodProcessor_getRecognitionsUpdate'] = Blockly.FtcJava['tfodProcessor_getFreshRecognitions'];

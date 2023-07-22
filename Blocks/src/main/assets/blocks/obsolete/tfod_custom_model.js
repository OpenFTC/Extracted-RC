/**
 * Copyright 2020 Google LLC
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
 * @fileoverview FTC robot blocks related to TensorFlow Object Detection for a custom model.
 * @author lizlooney@google.com (Liz Looney)
 */

// These blocks are deprecated.

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// vuforiaCurrentGameIdentifierForJavaScript
// tfodCustomModelIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor

Blockly.Blocks['tfodCustomModel_setModelFromFile'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetectionCustomModel'))
        .appendField('.')
        .appendField(createNonEditableField('setModelFromFile'));
    this.appendValueInput('TFLITE_MODEL_FILENAME').setCheck('String')
        .appendField('tfliteModelFilename')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('LABELS').setCheck('Array')
        .appendField('Labels')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the filename of the custom TensorFlowLite model and ' +
        'the list of labels the model is trained to recognize.');
  }
};

Blockly.JavaScript['tfodCustomModel_setModelFromFile'] = function(block) {
  var tfliteModelFilename = Blockly.JavaScript.valueToCode(
      block, 'TFLITE_MODEL_FILENAME', Blockly.JavaScript.ORDER_COMMA);
  var labels = Blockly.JavaScript.valueToCode(
      block, 'LABELS', Blockly.JavaScript.ORDER_COMMA);
  return tfodCustomModelIdentifierForJavaScript + '.setModelFromFile(' +
      tfliteModelFilename + ', JSON.stringify(' + labels + '));\n';
};

Blockly.FtcJava['tfodCustomModel_setModelFromFile'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'TfodCustomModel');
  var tfliteModelFilename = Blockly.FtcJava.valueToCode(
      block, 'TFLITE_MODEL_FILENAME', Blockly.FtcJava.ORDER_COMMA);
  var labels = Blockly.FtcJava.valueToCode(
      block, 'LABELS', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setModelFromFile(' +
      tfliteModelFilename + ', ' + labels + ');\n';
};

Blockly.Blocks['tfodCustomModel_setModelFromAsset'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetectionCustomModel'))
        .appendField('.')
        .appendField(createNonEditableField('setModelFromAsset'));
    this.appendValueInput('ASSET_NAME').setCheck('String')
        .appendField('tfliteAssetName')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('LABELS').setCheck('Array')
        .appendField('Labels')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the asset name of the custom TensorFlowLite model and ' +
        'the list of labels the model is trained to recognize.');
  }
};

Blockly.JavaScript['tfodCustomModel_setModelFromAsset'] = function(block) {
  var tfliteModelAssetName = Blockly.JavaScript.valueToCode(
      block, 'ASSET_NAME', Blockly.JavaScript.ORDER_COMMA);
  var labels = Blockly.JavaScript.valueToCode(
      block, 'LABELS', Blockly.JavaScript.ORDER_COMMA);
  return tfodCustomModelIdentifierForJavaScript + '.setModelFromAsset(' +
      tfliteModelAssetName + ', JSON.stringify(' + labels + '));\n';
};

Blockly.FtcJava['tfodCustomModel_setModelFromAsset'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'TfodCustomModel');
  var tfliteModelAssetName = Blockly.FtcJava.valueToCode(
      block, 'ASSET_NAME', Blockly.FtcJava.ORDER_COMMA);
  var labels = Blockly.FtcJava.valueToCode(
      block, 'LABELS', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setModelFromAsset(' +
      tfliteModelAssetName + ', ' + labels + ');\n';
};

Blockly.Blocks['tfodCustomModel_initialize'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetectionCustomModel'))
        .appendField('.')
        .appendField(createNonEditableField('initialize'));
    this.appendValueInput('MINIMUM_CONFIDENCE').setCheck('Number')
        .appendField('minimumConfidence')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('USE_OBJECT_TRACKER').setCheck('Boolean')
        .appendField('useObjectTracker')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ENABLE_CAMERA_MONITORING').setCheck('Boolean')
        .appendField('enableCameraMonitoring')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Initialize TensorFlow Object Detection for a custom model.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MINIMUM_CONFIDENCE':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodCustomModel_initialize'] = function(block) {
  return tfod_initialize_JavaScript(block, tfodCustomModelIdentifierForJavaScript,
      vuforiaCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCustomModel_initialize'] = function(block) {
  return tfod_initialize_FtcJava(block, 'TfodCustomModel', 'VuforiaCurrentGame');
};

Blockly.Blocks['tfodCustomModel_initialize_withIsModelTensorFlow2'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetectionCustomModel'))
        .appendField('.')
        .appendField(createNonEditableField('initialize'));
    this.appendValueInput('MINIMUM_CONFIDENCE').setCheck('Number')
        .appendField('minimumConfidence')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('USE_OBJECT_TRACKER').setCheck('Boolean')
        .appendField('useObjectTracker')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ENABLE_CAMERA_MONITORING').setCheck('Boolean')
        .appendField('enableCameraMonitoring')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('IS_MODEL_TENSORFLOW_2').setCheck('Boolean')
        .appendField('isModelTensorFlow2')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Initialize TensorFlow Object Detection for a custom model.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MINIMUM_CONFIDENCE':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodCustomModel_initialize_withIsModelTensorFlow2'] = function(block) {
  return tfodCustomModel_initialize_withIsModelTensorFlow2_JavaScript(block, tfodCustomModelIdentifierForJavaScript,
      vuforiaCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCustomModel_initialize_withIsModelTensorFlow2'] = function(block) {
  return tfodCustomModel_initialize_withIsModelTensorFlow2_FtcJava(block, 'TfodCustomModel', 'VuforiaCurrentGame');
};

Blockly.Blocks['tfodCustomModel_initialize_withAllArgs'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetectionCustomModel'))
        .appendField('.')
        .appendField(createNonEditableField('initialize'));
    this.appendValueInput('MINIMUM_CONFIDENCE').setCheck('Number')
        .appendField('minimumConfidence')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('USE_OBJECT_TRACKER').setCheck('Boolean')
        .appendField('useObjectTracker')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ENABLE_CAMERA_MONITORING').setCheck('Boolean')
        .appendField('enableCameraMonitoring')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('IS_MODEL_TENSORFLOW_2').setCheck('Boolean')
        .appendField('isModelTensorFlow2')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('IS_MODEL_QUANTIZED').setCheck('Boolean')
        .appendField('isModelQuantized')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('INPUT_SIZE').setCheck('Number')
        .appendField('inputSize')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('NUM_INTERPRETER_THREADS').setCheck('Number')
        .appendField('numInterpreterThreads')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('NUM_EXECUTOR_THREADS').setCheck('Number')
        .appendField('numExecutorThreads')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MAX_NUM_DETECTIONS').setCheck('Number')
        .appendField('maxNumDetections')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TIMING_BUFFER_SIZE').setCheck('Number')
        .appendField('timingBufferSize')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MAX_FRAME_RATE').setCheck('Number')
        .appendField('maxFrameRate')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TRACKER_MAX_OVERLAP').setCheck('Number')
        .appendField('trackerMaxOverlap')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TRACKER_MIN_SIZE').setCheck('Number')
        .appendField('trackerMinSize')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TRACKER_MARGINAL_CORRELATION').setCheck('Number')
        .appendField('trackerMarginalCorrelation')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TRACKER_MIN_CORRELATION').setCheck('Number')
        .appendField('trackerMinCorrelation')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Initialize TensorFlow Object Detection for a custom model.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MAX_FRAME_RATE':
          return 'double';
        case 'INPUT_SIZE':
        case 'NUM_INTERPRETER_THREADS':
        case 'NUM_EXECUTOR_THREADS':
        case 'MAX_NUM_DETECTIONS':
        case 'TIMING_BUFFER_SIZE':
          return 'int';
        case 'MINIMUM_CONFIDENCE':
        case 'TRACKER_MAX_OVERLAP':
        case 'TRACKER_MIN_SIZE':
        case 'TRACKER_MARGINAL_CORRELATION':
        case 'TRACKER_MIN_CORRELATION':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodCustomModel_initialize_withAllArgs'] = function(block) {
  return tfodCustomModel_initialize_withAllArgs_JavaScript(block, tfodCustomModelIdentifierForJavaScript,
      vuforiaCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCustomModel_initialize_withAllArgs'] = function(block) {
  return tfodCustomModel_initialize_withAllArgs_FtcJava(block, 'TfodCustomModel', 'VuforiaCurrentGame');
};

Blockly.Blocks['tfodCustomModel_activate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetectionCustomModel'))
        .appendField('.')
        .appendField(createNonEditableField('activate'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Activates object detection.');
  }
};

Blockly.JavaScript['tfodCustomModel_activate'] = function(block) {
  return tfod_activate_JavaScript(block, tfodCustomModelIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCustomModel_activate'] = function(block) {
  return tfod_activate_FtcJava(block, 'TfodCustomModel');
};

Blockly.Blocks['tfodCustomModel_deactivate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetectionCustomModel'))
        .appendField('.')
        .appendField(createNonEditableField('deactivate'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Deactivates object detection.');
  }
};

Blockly.JavaScript['tfodCustomModel_deactivate'] = function(block) {
  return tfod_deactivate_JavaScript(block, tfodCustomModelIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCustomModel_deactivate'] = function(block) {
  return tfod_deactivate_FtcJava(block, 'TfodCustomModel');
};

Blockly.Blocks['tfodCustomModel_setClippingMargins'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetectionCustomModel'))
        .appendField('.')
        .appendField(createNonEditableField('setClippingMargins'));
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

Blockly.JavaScript['tfodCustomModel_setClippingMargins'] = function(block) {
  return tfod_setClippingMargins_JavaScript(block, tfodCustomModelIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCustomModel_setClippingMargins'] = function(block) {
  return tfod_setClippingMargins_FtcJava(block, 'TfodCustomModel');
};

Blockly.Blocks['tfodCustomModel_setZoom'] = {
  init: function() {
          this.appendDummyInput()
              .appendField('call')
              .appendField(createNonEditableField('TensorFlowObjectDetectionCustomModel'))
              .appendField('.')
              .appendField(createNonEditableField('setZoom'));
          this.appendValueInput('MAGNIFICATION').setCheck('Number')
              .appendField('magnification')
              .setAlign(Blockly.ALIGN_RIGHT);
          this.appendValueInput('ASPECT_RATIO').setCheck('Number')
              .appendField('aspectRatio')
              .setAlign(Blockly.ALIGN_RIGHT);
          this.setPreviousStatement(true);
          this.setNextStatement(true);
          this.setColour(functionColor);
          this.setTooltip('Indicates that only the zoomed center area of each image will be ' +
              'passed to the TensorFlow object detector. For no zooming, set magnification to 1.0. ' +
              'For best results, the aspect ratio should match the aspect ratio of the images that ' +
              'were used to train the TensorFlow model.');
          this.getFtcJavaInputType = function(inputName) {
            switch (inputName) {
              case 'MAGNIFICATION':
              case 'ASPECT_RATIO':
                return 'double';
            }
            return '';
          };
        }
};

Blockly.JavaScript['tfodCustomModel_setZoom'] = function(block) {
  return tfod_setZoom_JavaScript(block, tfodCustomModelIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCustomModel_setZoom'] = function(block) {
  return tfod_setZoom_FtcJava(block, 'TfodCustomModel');
};

Blockly.Blocks['tfodCustomModel_getRecognitions'] = {
  init: function() {
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetectionCustomModel'))
        .appendField('.')
        .appendField(createNonEditableField('getRecognitions'));
    this.setColour(functionColor);
    this.setTooltip('Returns a List of the current recognitions.');
    this.getFtcJavaOutputType = function() {
      return 'List<Recognition>';
    };
  }
};

Blockly.JavaScript['tfodCustomModel_getRecognitions'] = function(block) {
  return tfod_getRecognitions_JavaScript(block, tfodCustomModelIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCustomModel_getRecognitions'] = function(block) {
  return tfod_getRecognitions_FtcJava(block, 'TfodCustomModel');
};

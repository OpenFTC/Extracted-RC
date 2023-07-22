/**
 * Copyright 2021 Google LLC
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
 * @fileoverview FTC robot blocks related to TensorFlow Object Detection.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// vuforiaCurrentGameIdentifierForJavaScript
// tfodIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor

Blockly.Blocks['tfod_useDefaultModel'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
        .appendField('.')
        .appendField(createNonEditableField('useDefaultModelFor' + tfodCurrentGameNameNoSpaces));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Specifies that the default model for ' + tfodCurrentGameName +
        ' will be used.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INPUT_SIZE':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfod_useDefaultModel'] = function(block) {
  return tfodIdentifierForJavaScript + '.useDefaultModel();\n';
};

Blockly.FtcJava['tfod_useDefaultModel'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  return identifier + '.useDefaultModel();\n';
};

Blockly.Blocks['tfod_useModelFromAsset'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
        .appendField('.')
        .appendField(createNonEditableField('useModelFromAsset'));
    this.appendValueInput('ASSET_NAME').setCheck('String')
        .appendField('tfliteAssetName')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('LABELS').setCheck('Array')
        .appendField('Labels')
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
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    this.setTooltip(function() {
      var tooltip = 'Specifies the asset name of the custom TensorFlowLite model and ' +
          'the full ordered list of labels the model is trained to recognize.';
      if (thisBlock.getCommentText()) {
        tooltip += ' Click on the blue question mark for more information.';
      }
      return tooltip;
    });
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INPUT_SIZE':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfod_useModelFromAsset'] = function(block) {
  var assetName = Blockly.JavaScript.valueToCode(
      block, 'ASSET_NAME', Blockly.JavaScript.ORDER_COMMA);
  var labels = Blockly.JavaScript.valueToCode(
      block, 'LABELS', Blockly.JavaScript.ORDER_COMMA);
  var isModelTensorFlow2 = Blockly.JavaScript.valueToCode(
      block, 'IS_MODEL_TENSORFLOW_2', Blockly.JavaScript.ORDER_COMMA);
  var isModelQuantized = Blockly.JavaScript.valueToCode(
      block, 'IS_MODEL_QUANTIZED', Blockly.JavaScript.ORDER_COMMA);
  var inputSize = Blockly.JavaScript.valueToCode(
      block, 'INPUT_SIZE', Blockly.JavaScript.ORDER_COMMA);
  return tfodIdentifierForJavaScript + '.useModelFromAsset(' +
      assetName + ', JSON.stringify(' + labels + '), ' +
      isModelTensorFlow2 + ', ' + isModelQuantized + ', ' + inputSize + ');\n';
};

Blockly.FtcJava['tfod_useModelFromAsset'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  var assetName = Blockly.FtcJava.valueToCode(
      block, 'ASSET_NAME', Blockly.FtcJava.ORDER_COMMA);
  var labels = Blockly.FtcJava.valueToCode(
      block, 'LABELS', Blockly.FtcJava.ORDER_COMMA);
  var isModelTensorFlow2 = Blockly.FtcJava.valueToCode(
      block, 'IS_MODEL_TENSORFLOW_2', Blockly.FtcJava.ORDER_COMMA);
  var isModelQuantized = Blockly.FtcJava.valueToCode(
      block, 'IS_MODEL_QUANTIZED', Blockly.FtcJava.ORDER_COMMA);
  var inputSize = Blockly.FtcJava.valueToCode(
      block, 'INPUT_SIZE', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.useModelFromAsset(' +
      assetName + ', ' + labels + ', ' +
      isModelTensorFlow2 + ', ' + isModelQuantized + ', ' + inputSize + ');\n';
};

Blockly.Blocks['tfod_useModelFromFile'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
        .appendField('.')
        .appendField(createNonEditableField('useModelFromFile'));
    this.appendValueInput('FILE_NAME').setCheck('String')
        .appendField('tfliteFileName')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('LABELS').setCheck('Array')
        .appendField('Labels')
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
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    this.setTooltip(function() {
      var tooltip = 'Specifies the file name of the custom TensorFlowLite model and ' +
          'the full ordered list of labels the model is trained to recognize.';
      if (thisBlock.getCommentText()) {
        tooltip += ' Click on the blue question mark for more information.';
      }
      return tooltip;
    });
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'INPUT_SIZE':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfod_useModelFromFile'] = function(block) {
  var fileName = Blockly.JavaScript.valueToCode(
      block, 'FILE_NAME', Blockly.JavaScript.ORDER_COMMA);
  var labels = Blockly.JavaScript.valueToCode(
      block, 'LABELS', Blockly.JavaScript.ORDER_COMMA);
  var isModelTensorFlow2 = Blockly.JavaScript.valueToCode(
      block, 'IS_MODEL_TENSORFLOW_2', Blockly.JavaScript.ORDER_COMMA);
  var isModelQuantized = Blockly.JavaScript.valueToCode(
      block, 'IS_MODEL_QUANTIZED', Blockly.JavaScript.ORDER_COMMA);
  var inputSize = Blockly.JavaScript.valueToCode(
      block, 'INPUT_SIZE', Blockly.JavaScript.ORDER_COMMA);
  return tfodIdentifierForJavaScript + '.useModelFromFile(' +
      fileName + ', JSON.stringify(' + labels + '), ' +
      isModelTensorFlow2 + ', ' + isModelQuantized + ', ' + inputSize + ');\n';
};

Blockly.FtcJava['tfod_useModelFromFile'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  var fileName = Blockly.FtcJava.valueToCode(
      block, 'FILE_NAME', Blockly.FtcJava.ORDER_COMMA);
  var labels = Blockly.FtcJava.valueToCode(
      block, 'LABELS', Blockly.FtcJava.ORDER_COMMA);
  var isModelTensorFlow2 = Blockly.FtcJava.valueToCode(
      block, 'IS_MODEL_TENSORFLOW_2', Blockly.FtcJava.ORDER_COMMA);
  var isModelQuantized = Blockly.FtcJava.valueToCode(
      block, 'IS_MODEL_QUANTIZED', Blockly.FtcJava.ORDER_COMMA);
  var inputSize = Blockly.FtcJava.valueToCode(
      block, 'INPUT_SIZE', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.useModelFromFile(' +
      fileName + ', ' + labels + ', ' +
      isModelTensorFlow2 + ', ' + isModelQuantized + ', ' + inputSize + ');\n';
};

Blockly.Blocks['tfod_initialize'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
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
    this.setTooltip('Initialize TensorFlow Object Detection with the default model for ' +
        'the current game or a custom model. This blocks should be preceded by either ' +
        'TensorFlowObjectDetection.useDefaultModel or ' +
        'TensorFlowObjectDetection.useModelFromFile or ' +
        'TensorFlowObjectDetection.useModelFromAsset.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MINIMUM_CONFIDENCE':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfod_initialize'] = function(block) {
  var minimumConfidence = Blockly.JavaScript.valueToCode(
      block, 'MINIMUM_CONFIDENCE', Blockly.JavaScript.ORDER_COMMA);
  var useObjectTracker = Blockly.JavaScript.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.JavaScript.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.JavaScript.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.JavaScript.ORDER_COMMA);
  return tfodIdentifierForJavaScript + '.initialize(' +
      vuforiaCurrentGameIdentifierForJavaScript + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ');\n';
};

Blockly.FtcJava['tfod_initialize'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  var vuforiaIdentifier = importDeclareAssignObsolete(block, null, 'VuforiaCurrentGame');
  var minimumConfidence = Blockly.FtcJava.valueToCode(
      block, 'MINIMUM_CONFIDENCE', Blockly.FtcJava.ORDER_COMMA);
  var useObjectTracker = Blockly.FtcJava.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.FtcJava.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.FtcJava.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.initialize(' + vuforiaIdentifier + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ');\n';
};

Blockly.Blocks['tfod_initialize_withMoreArgs'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
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
    this.setTooltip('Initialize TensorFlow Object Detection with the default model for ' +
        'the current game or a custom model. This blocks should be preceded by either ' +
        'TensorFlowObjectDetection.useDefaultModel or ' +
        'TensorFlowObjectDetection.useModelFromFile or ' +
        'TensorFlowObjectDetection.useModelFromAsset.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MAX_FRAME_RATE':
          return 'double';
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

Blockly.JavaScript['tfod_initialize_withMoreArgs'] = function(block) {
  var minimumConfidence = Blockly.JavaScript.valueToCode(
      block, 'MINIMUM_CONFIDENCE', Blockly.JavaScript.ORDER_COMMA);
  var useObjectTracker = Blockly.JavaScript.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.JavaScript.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.JavaScript.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.JavaScript.ORDER_COMMA);
  var numInterpreterThreads = Blockly.JavaScript.valueToCode(
      block, 'NUM_INTERPRETER_THREADS', Blockly.JavaScript.ORDER_COMMA);
  var numExecutorThreads = Blockly.JavaScript.valueToCode(
      block, 'NUM_EXECUTOR_THREADS', Blockly.JavaScript.ORDER_COMMA);
  var maxNumDetections = Blockly.JavaScript.valueToCode(
      block, 'MAX_NUM_DETECTIONS', Blockly.JavaScript.ORDER_COMMA);
  var timingBufferSize = Blockly.JavaScript.valueToCode(
      block, 'TIMING_BUFFER_SIZE', Blockly.JavaScript.ORDER_COMMA);
  var maxFrameRate = Blockly.JavaScript.valueToCode(
      block, 'MAX_FRAME_RATE', Blockly.JavaScript.ORDER_COMMA);
  var trackerMaxOverlap = Blockly.JavaScript.valueToCode(
      block, 'TRACKER_MAX_OVERLAP', Blockly.JavaScript.ORDER_COMMA);
  var trackerMinSize = Blockly.JavaScript.valueToCode(
      block, 'TRACKER_MIN_SIZE', Blockly.JavaScript.ORDER_COMMA);
  var trackerMarginalCorrelation = Blockly.JavaScript.valueToCode(
      block, 'TRACKER_MARGINAL_CORRELATION', Blockly.JavaScript.ORDER_COMMA);
  var trackerMinCorrelation = Blockly.JavaScript.valueToCode(
      block, 'TRACKER_MIN_CORRELATION', Blockly.JavaScript.ORDER_COMMA);
  return tfodIdentifierForJavaScript + '.initializeWithMoreArgs(' +
      vuforiaCurrentGameIdentifierForJavaScript + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ', ' +
      numInterpreterThreads + ', ' + numExecutorThreads + ', ' +
      maxNumDetections + ', ' + timingBufferSize + ', ' + maxFrameRate + ', ' +
      trackerMaxOverlap + ', ' + trackerMinSize + ', ' +
      trackerMarginalCorrelation + ', ' + trackerMinCorrelation + ');\n';
};

Blockly.FtcJava['tfod_initialize_withMoreArgs'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  var vuforiaIdentifier = importDeclareAssignObsolete(block, null, 'VuforiaCurrentGame');
  var minimumConfidence = Blockly.FtcJava.valueToCode(
      block, 'MINIMUM_CONFIDENCE', Blockly.FtcJava.ORDER_COMMA);
  var useObjectTracker = Blockly.FtcJava.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.FtcJava.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.FtcJava.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.FtcJava.ORDER_COMMA);
  var numInterpreterThreads = Blockly.FtcJava.valueToCode(
      block, 'NUM_INTERPRETER_THREADS', Blockly.FtcJava.ORDER_COMMA);
  var numExecutorThreads = Blockly.FtcJava.valueToCode(
      block, 'NUM_EXECUTOR_THREADS', Blockly.FtcJava.ORDER_COMMA);
  var maxNumDetections = Blockly.FtcJava.valueToCode(
      block, 'MAX_NUM_DETECTIONS', Blockly.FtcJava.ORDER_COMMA);
  var timingBufferSize = Blockly.FtcJava.valueToCode(
      block, 'TIMING_BUFFER_SIZE', Blockly.FtcJava.ORDER_COMMA);
  var maxFrameRate = Blockly.FtcJava.valueToCode(
      block, 'MAX_FRAME_RATE', Blockly.FtcJava.ORDER_COMMA);
  var trackerMaxOverlap = Blockly.FtcJava.valueToCode(
      block, 'TRACKER_MAX_OVERLAP', Blockly.FtcJava.ORDER_COMMA);
  var trackerMinSize = Blockly.FtcJava.valueToCode(
      block, 'TRACKER_MIN_SIZE', Blockly.FtcJava.ORDER_COMMA);
  var trackerMarginalCorrelation = Blockly.FtcJava.valueToCode(
      block, 'TRACKER_MARGINAL_CORRELATION', Blockly.FtcJava.ORDER_COMMA);
  var trackerMinCorrelation = Blockly.FtcJava.valueToCode(
      block, 'TRACKER_MIN_CORRELATION', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.initialize(' + vuforiaIdentifier + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ', ' +
      numInterpreterThreads + ', ' + numExecutorThreads + ', ' +
      maxNumDetections + ', ' + timingBufferSize + ', ' + maxFrameRate + ', ' +
      trackerMaxOverlap + ', ' + trackerMinSize + ', ' +
      trackerMarginalCorrelation + ', ' + trackerMinCorrelation + ');\n';
};

Blockly.Blocks['tfod_activate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
        .appendField('.')
        .appendField(createNonEditableField('activate'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Activates object detection.');
  }
};

Blockly.JavaScript['tfod_activate'] = function(block) {
  return tfodIdentifierForJavaScript + '.activate();\n';
};

Blockly.FtcJava['tfod_activate'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  return identifier + '.activate();\n';
};

Blockly.Blocks['tfod_deactivate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
        .appendField('.')
        .appendField(createNonEditableField('deactivate'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Deactivates object detection.');
  }
};

Blockly.JavaScript['tfod_deactivate'] = function(block) {
  return tfodIdentifierForJavaScript + '.deactivate();\n';
};

Blockly.FtcJava['tfod_deactivate'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  return identifier + '.deactivate();\n';
};

Blockly.Blocks['tfod_setClippingMargins'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
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

Blockly.JavaScript['tfod_setClippingMargins'] = function(block) {
  var left = Blockly.JavaScript.valueToCode(block, 'LEFT', Blockly.JavaScript.ORDER_COMMA);
  var top = Blockly.JavaScript.valueToCode(block, 'TOP', Blockly.JavaScript.ORDER_COMMA);
  var right = Blockly.JavaScript.valueToCode(block, 'RIGHT', Blockly.JavaScript.ORDER_COMMA);
  var bottom = Blockly.JavaScript.valueToCode(block, 'BOTTOM', Blockly.JavaScript.ORDER_COMMA);
  return tfodIdentifierForJavaScript + '.setClippingMargins(' +
      left + ', ' + top + ', ' + right + ', ' + bottom + ');\n';
};

Blockly.FtcJava['tfod_setClippingMargins'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  var left = Blockly.FtcJava.valueToCode(block, 'LEFT', Blockly.FtcJava.ORDER_COMMA);
  var top = Blockly.FtcJava.valueToCode(block, 'TOP', Blockly.FtcJava.ORDER_COMMA);
  var right = Blockly.FtcJava.valueToCode(block, 'RIGHT', Blockly.FtcJava.ORDER_COMMA);
  var bottom = Blockly.FtcJava.valueToCode(block, 'BOTTOM', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setClippingMargins(' +
      left + ', ' + top + ', ' + right + ', ' + bottom + ');\n';
};

Blockly.Blocks['tfod_setZoom'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
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

Blockly.JavaScript['tfod_setZoom'] = function(block) {
  var magnification = Blockly.JavaScript.valueToCode(block, 'MAGNIFICATION', Blockly.JavaScript.ORDER_COMMA);
  var aspectRatio = Blockly.JavaScript.valueToCode(block, 'ASPECT_RATIO', Blockly.JavaScript.ORDER_COMMA);
  return tfodIdentifierForJavaScript + '.setZoom(' + magnification + ', ' + aspectRatio + ');\n';
};

Blockly.FtcJava['tfod_setZoom'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  var magnification = Blockly.FtcJava.valueToCode(block, 'MAGNIFICATION', Blockly.FtcJava.ORDER_COMMA);
  var aspectRatio = Blockly.FtcJava.valueToCode(block, 'ASPECT_RATIO', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setZoom(' + magnification + ', ' + aspectRatio + ');\n';
};

Blockly.Blocks['tfod_getRecognitions'] = {
  init: function() {
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
        .appendField('.')
        .appendField(createNonEditableField('getRecognitions'));
    this.setColour(functionColor);
    this.setTooltip('Returns a List of the current recognitions.');
    this.getFtcJavaOutputType = function() {
      return 'List<Recognition>';
    };
  }
};

Blockly.JavaScript['tfod_getRecognitions'] = function(block) {
  var code = 'JSON.parse(' + tfodIdentifierForJavaScript + '.getRecognitions())';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['tfod_getRecognitions'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  var code = identifier + '.getRecognitions()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// legacy blocks

Blockly.Blocks['tfodLegacy_setModelFromAsset'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
        .appendField('.')
        .appendField(createNonEditableField('useModelFromAsset'));
    this.appendValueInput('ASSET_NAME').setCheck('String')
        .appendField('tfliteAssetName')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('LABELS').setCheck('Array')
        .appendField('Labels')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Specifies the asset name of the custom TensorFlowLite model and ' +
        'the full ordered list of labels the model is trained to recognize.');
  }
};

Blockly.JavaScript['tfodLegacy_setModelFromAsset'] = function(block) {
  var assetName = Blockly.JavaScript.valueToCode(
      block, 'ASSET_NAME', Blockly.JavaScript.ORDER_COMMA);
  var labels = Blockly.JavaScript.valueToCode(
      block, 'LABELS', Blockly.JavaScript.ORDER_COMMA);
  return tfodIdentifierForJavaScript + '.setModelFromAssetLegacy(' +
      assetName + ', JSON.stringify(' + labels + '));\n';
};

Blockly.FtcJava['tfodLegacy_setModelFromAsset'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  var assetName = Blockly.FtcJava.valueToCode(
      block, 'ASSET_NAME', Blockly.FtcJava.ORDER_COMMA);
  var labels = Blockly.FtcJava.valueToCode(
      block, 'LABELS', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.useModelFromAsset(' +
      assetName + ', ' + labels + ');\n';
};

Blockly.Blocks['tfodLegacy_setModelFromFile'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
        .appendField('.')
        .appendField(createNonEditableField('useModelFromFile'));
    this.appendValueInput('TFLITE_MODEL_FILENAME').setCheck('String')
        .appendField('tfliteFileName')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('LABELS').setCheck('Array')
        .appendField('Labels')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Specifies the file name of the custom TensorFlowLite model and ' +
        'the full ordered list of labels the model is trained to recognize.');
  }
};

Blockly.JavaScript['tfodLegacy_setModelFromFile'] = function(block) {
  var fileName = Blockly.JavaScript.valueToCode(
      block, 'TFLITE_MODEL_FILENAME', Blockly.JavaScript.ORDER_COMMA);
  var labels = Blockly.JavaScript.valueToCode(
      block, 'LABELS', Blockly.JavaScript.ORDER_COMMA);
  return tfodIdentifierForJavaScript + '.setModelFromFileLegacy(' +
      fileName + ', JSON.stringify(' + labels + '));\n';
};

Blockly.FtcJava['tfodLegacy_setModelFromFile'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  var fileName = Blockly.FtcJava.valueToCode(
      block, 'TFLITE_MODEL_FILENAME', Blockly.FtcJava.ORDER_COMMA);
  var labels = Blockly.FtcJava.valueToCode(
      block, 'LABELS', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.useModelFromFile(' +
      fileName + ', ' + labels + ');\n';
};

Blockly.Blocks['tfodLegacy_initialize_withIsModelTensorFlow2'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
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
    this.setTooltip('Initialize TensorFlow Object Detection with the default model for ' +
        'the current game or a custom model. This blocks should be preceded by either ' +
        'TensorFlowObjectDetection.useDefaultModel or ' +
        'TensorFlowObjectDetection.useModelFromFile or ' +
        'TensorFlowObjectDetection.useModelFromAsset.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MINIMUM_CONFIDENCE':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodLegacy_initialize_withIsModelTensorFlow2'] = function(block) {
  var minimumConfidence = Blockly.JavaScript.valueToCode(
      block, 'MINIMUM_CONFIDENCE', Blockly.JavaScript.ORDER_COMMA);
  var useObjectTracker = Blockly.JavaScript.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.JavaScript.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.JavaScript.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.JavaScript.ORDER_COMMA);
  var isModelTensorFlow2 = Blockly.JavaScript.valueToCode(
      block, 'IS_MODEL_TENSORFLOW_2', Blockly.JavaScript.ORDER_COMMA);
  return tfodIdentifierForJavaScript + '.initializeWithIsModelTensorFlow2Legacy(' +
      vuforiaCurrentGameIdentifierForJavaScript + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ', ' +
      isModelTensorFlow2 + ');\n';
};

Blockly.FtcJava['tfodLegacy_initialize_withIsModelTensorFlow2'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  var vuforiaIdentifier = importDeclareAssignObsolete(block, null, 'VuforiaCurrentGame');
  var minimumConfidence = Blockly.FtcJava.valueToCode(
      block, 'MINIMUM_CONFIDENCE', Blockly.FtcJava.ORDER_COMMA);
  var useObjectTracker = Blockly.FtcJava.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.FtcJava.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.FtcJava.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.FtcJava.ORDER_COMMA);
  var isModelTensorFlow2 = Blockly.FtcJava.valueToCode(
      block, 'IS_MODEL_TENSORFLOW_2', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.initialize(' + vuforiaIdentifier + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ', ' +
      isModelTensorFlow2 + ');\n';
};

Blockly.Blocks['tfodLegacy_initialize_withAllArgs'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('TensorFlowObjectDetection'))
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
    this.setTooltip('Initialize TensorFlow Object Detection with the default model for ' +
        'the current game or a custom model. This blocks should be preceded by either ' +
        'TensorFlowObjectDetection.useDefaultModel or ' +
        'TensorFlowObjectDetection.useModelFromFile or ' +
        'TensorFlowObjectDetection.useModelFromAsset.');
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

Blockly.JavaScript['tfodLegacy_initialize_withAllArgs'] = function(block) {
  var minimumConfidence = Blockly.JavaScript.valueToCode(
      block, 'MINIMUM_CONFIDENCE', Blockly.JavaScript.ORDER_COMMA);
  var useObjectTracker = Blockly.JavaScript.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.JavaScript.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.JavaScript.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.JavaScript.ORDER_COMMA);
  var isModelTensorFlow2 = Blockly.JavaScript.valueToCode(
      block, 'IS_MODEL_TENSORFLOW_2', Blockly.JavaScript.ORDER_COMMA);
  var isModelQuantized = Blockly.JavaScript.valueToCode(
      block, 'IS_MODEL_QUANTIZED', Blockly.JavaScript.ORDER_COMMA);
  var inputSize = Blockly.JavaScript.valueToCode(
      block, 'INPUT_SIZE', Blockly.JavaScript.ORDER_COMMA);
  var numInterpreterThreads = Blockly.JavaScript.valueToCode(
      block, 'NUM_INTERPRETER_THREADS', Blockly.JavaScript.ORDER_COMMA);
  var numExecutorThreads = Blockly.JavaScript.valueToCode(
      block, 'NUM_EXECUTOR_THREADS', Blockly.JavaScript.ORDER_COMMA);
  var maxNumDetections = Blockly.JavaScript.valueToCode(
      block, 'MAX_NUM_DETECTIONS', Blockly.JavaScript.ORDER_COMMA);
  var timingBufferSize = Blockly.JavaScript.valueToCode(
      block, 'TIMING_BUFFER_SIZE', Blockly.JavaScript.ORDER_COMMA);
  var maxFrameRate = Blockly.JavaScript.valueToCode(
      block, 'MAX_FRAME_RATE', Blockly.JavaScript.ORDER_COMMA);
  var trackerMaxOverlap = Blockly.JavaScript.valueToCode(
      block, 'TRACKER_MAX_OVERLAP', Blockly.JavaScript.ORDER_COMMA);
  var trackerMinSize = Blockly.JavaScript.valueToCode(
      block, 'TRACKER_MIN_SIZE', Blockly.JavaScript.ORDER_COMMA);
  var trackerMarginalCorrelation = Blockly.JavaScript.valueToCode(
      block, 'TRACKER_MARGINAL_CORRELATION', Blockly.JavaScript.ORDER_COMMA);
  var trackerMinCorrelation = Blockly.JavaScript.valueToCode(
      block, 'TRACKER_MIN_CORRELATION', Blockly.JavaScript.ORDER_COMMA);
  return tfodIdentifierForJavaScript + '.initializeWithAllArgsLegacy(' +
      vuforiaCurrentGameIdentifierForJavaScript + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ', ' +
      isModelTensorFlow2 + ', ' + isModelQuantized + ', ' + inputSize + ', ' +
      numInterpreterThreads + ', ' + numExecutorThreads + ', ' +
      maxNumDetections + ', ' + timingBufferSize + ', ' + maxFrameRate + ', ' +
      trackerMaxOverlap + ', ' + trackerMinSize + ', ' +
      trackerMarginalCorrelation + ', ' + trackerMinCorrelation + ');\n';
};

Blockly.FtcJava['tfodLegacy_initialize_withAllArgs'] = function(block) {
  var identifier = importDeclareAssignObsolete(block, null, 'Tfod');
  var vuforiaIdentifier = importDeclareAssignObsolete(block, null, 'VuforiaCurrentGame');
  var minimumConfidence = Blockly.FtcJava.valueToCode(
      block, 'MINIMUM_CONFIDENCE', Blockly.FtcJava.ORDER_COMMA);
  var useObjectTracker = Blockly.FtcJava.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.FtcJava.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.FtcJava.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.FtcJava.ORDER_COMMA);
  var isModelTensorFlow2 = Blockly.FtcJava.valueToCode(
      block, 'IS_MODEL_TENSORFLOW_2', Blockly.FtcJava.ORDER_COMMA);
  var isModelQuantized = Blockly.FtcJava.valueToCode(
      block, 'IS_MODEL_QUANTIZED', Blockly.FtcJava.ORDER_COMMA);
  var inputSize = Blockly.FtcJava.valueToCode(
      block, 'INPUT_SIZE', Blockly.FtcJava.ORDER_COMMA);
  var numInterpreterThreads = Blockly.FtcJava.valueToCode(
      block, 'NUM_INTERPRETER_THREADS', Blockly.FtcJava.ORDER_COMMA);
  var numExecutorThreads = Blockly.FtcJava.valueToCode(
      block, 'NUM_EXECUTOR_THREADS', Blockly.FtcJava.ORDER_COMMA);
  var maxNumDetections = Blockly.FtcJava.valueToCode(
      block, 'MAX_NUM_DETECTIONS', Blockly.FtcJava.ORDER_COMMA);
  var timingBufferSize = Blockly.FtcJava.valueToCode(
      block, 'TIMING_BUFFER_SIZE', Blockly.FtcJava.ORDER_COMMA);
  var maxFrameRate = Blockly.FtcJava.valueToCode(
      block, 'MAX_FRAME_RATE', Blockly.FtcJava.ORDER_COMMA);
  var trackerMaxOverlap = Blockly.FtcJava.valueToCode(
      block, 'TRACKER_MAX_OVERLAP', Blockly.FtcJava.ORDER_COMMA);
  var trackerMinSize = Blockly.FtcJava.valueToCode(
      block, 'TRACKER_MIN_SIZE', Blockly.FtcJava.ORDER_COMMA);
  var trackerMarginalCorrelation = Blockly.FtcJava.valueToCode(
      block, 'TRACKER_MARGINAL_CORRELATION', Blockly.FtcJava.ORDER_COMMA);
  var trackerMinCorrelation = Blockly.FtcJava.valueToCode(
      block, 'TRACKER_MIN_CORRELATION', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.initialize(' + vuforiaIdentifier + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ', ' +
      isModelTensorFlow2 + ', ' + isModelQuantized + ', ' + inputSize + ', ' +
      numInterpreterThreads + ', ' + numExecutorThreads + ', ' + maxNumDetections + ', ' +
      timingBufferSize + ', ' + maxFrameRate + ', ' + trackerMaxOverlap + ', ' + trackerMinSize + ', ' +
      trackerMarginalCorrelation + ', ' + trackerMinCorrelation + ');\n';
};

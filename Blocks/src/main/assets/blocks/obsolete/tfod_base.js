/**
 * @license
 * Copyright 2018 Google LLC
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
 * @fileoverview Functions to generate code for the initialize method call for TensorFlow Object Detection
 * @author lizlooney@google.com (Liz Looney)
 */


function tfod_initialize_JavaScript(block, identifier, vuforiaIdentifier) {
  var minimumConfidence = Blockly.JavaScript.valueToCode(
      block, 'MINIMUM_CONFIDENCE', Blockly.JavaScript.ORDER_COMMA);
  var useObjectTracker = Blockly.JavaScript.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.JavaScript.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.JavaScript.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.initialize(' + vuforiaIdentifier + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ');\n';
}

function tfod_initialize_FtcJava(block, className, vuforiaClassName) {
  var identifier = importDeclareAssignObsolete(block, null, className);
  var vuforiaIdentifier = importDeclareAssignObsolete(block, null, vuforiaClassName);
  var minimumConfidence = Blockly.FtcJava.valueToCode(
      block, 'MINIMUM_CONFIDENCE', Blockly.FtcJava.ORDER_COMMA);
  var useObjectTracker = Blockly.FtcJava.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.FtcJava.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.FtcJava.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.initialize(' + vuforiaIdentifier + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ');\n';
}


function tfodCustomModel_initialize_withIsModelTensorFlow2_JavaScript(block, identifier, vuforiaIdentifier) {
  var minimumConfidence = Blockly.JavaScript.valueToCode(
      block, 'MINIMUM_CONFIDENCE', Blockly.JavaScript.ORDER_COMMA);
  var useObjectTracker = Blockly.JavaScript.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.JavaScript.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.JavaScript.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.JavaScript.ORDER_COMMA);
  var isModelTensorFlow2 = Blockly.JavaScript.valueToCode(
      block, 'IS_MODEL_TENSORFLOW_2', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.initializeWithIsModelTensorFlow2(' + vuforiaIdentifier + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ', ' + isModelTensorFlow2 + ');\n';
}

function tfodCustomModel_initialize_withIsModelTensorFlow2_FtcJava(block, className, vuforiaClassName) {
  var identifier = importDeclareAssignObsolete(block, null, className);
  var vuforiaIdentifier = importDeclareAssignObsolete(block, null, vuforiaClassName);
  var minimumConfidence = Blockly.FtcJava.valueToCode(
      block, 'MINIMUM_CONFIDENCE', Blockly.FtcJava.ORDER_COMMA);
  var useObjectTracker = Blockly.FtcJava.valueToCode(
      block, 'USE_OBJECT_TRACKER', Blockly.FtcJava.ORDER_COMMA);
  var enableCameraMonitoring = Blockly.FtcJava.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.FtcJava.ORDER_COMMA);
  var isModelTensorFlow2 = Blockly.FtcJava.valueToCode(
      block, 'IS_MODEL_TENSORFLOW_2', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.initializeWithIsModelTensorFlow2(' + vuforiaIdentifier + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ', ' + isModelTensorFlow2 + ');\n';
}

function tfodCustomModel_initialize_withAllArgs_JavaScript(block, identifier, vuforiaIdentifier) {
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
  return identifier + '.initializeWithAllArgs(' + vuforiaIdentifier + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ', ' +
      isModelTensorFlow2 + ', ' + isModelQuantized + ', ' + inputSize + ', ' +
      numInterpreterThreads + ', ' + numExecutorThreads + ', ' +
      maxNumDetections + ', ' + timingBufferSize + ', ' + maxFrameRate + ', ' +
      trackerMaxOverlap + ', ' + trackerMinSize + ', ' +
      trackerMarginalCorrelation + ', ' + trackerMinCorrelation + ');\n';
}

function tfodCustomModel_initialize_withAllArgs_FtcJava(block, className, vuforiaClassName) {
  var identifier = importDeclareAssignObsolete(block, null, className);
  var vuforiaIdentifier = importDeclareAssignObsolete(block, null, vuforiaClassName);
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
  return identifier + '.initializeWithAllArgs(' + vuforiaIdentifier + ', ' +
      minimumConfidence + ', ' + useObjectTracker + ', ' + enableCameraMonitoring + ', ' +
      isModelTensorFlow2 + ', ' + isModelQuantized + ', ' + inputSize + ', ' +
      numInterpreterThreads + ', ' + numExecutorThreads + ', ' +
      maxNumDetections + ', ' + timingBufferSize + ', ' + maxFrameRate + ', ' +
      trackerMaxOverlap + ', ' + trackerMinSize + ', ' +
      trackerMarginalCorrelation + ', ' + trackerMinCorrelation + ');\n';
}

function tfod_activate_JavaScript(block, identifier) {
  return identifier + '.activate();\n';
}

function tfod_activate_FtcJava(block, className) {
  var identifier = importDeclareAssignObsolete(block, null, className);
  return identifier + '.activate();\n';
}

function tfod_deactivate_JavaScript(block, identifier) {
  return identifier + '.deactivate();\n';
}

function tfod_deactivate_FtcJava(block, className) {
  var identifier = importDeclareAssignObsolete(block, null, className);
  return identifier + '.deactivate();\n';
}

function tfod_setClippingMargins_JavaScript(block, identifier) {
  var left = Blockly.JavaScript.valueToCode(block, 'LEFT', Blockly.JavaScript.ORDER_COMMA);
  var top = Blockly.JavaScript.valueToCode(block, 'TOP', Blockly.JavaScript.ORDER_COMMA);
  var right = Blockly.JavaScript.valueToCode(block, 'RIGHT', Blockly.JavaScript.ORDER_COMMA);
  var bottom = Blockly.JavaScript.valueToCode(block, 'BOTTOM', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setClippingMargins(' +
      left + ', ' + top + ', ' + right + ', ' + bottom + ');\n';
}

function tfod_setClippingMargins_FtcJava(block, className) {
  var identifier = importDeclareAssignObsolete(block, null, className);
  var left = Blockly.FtcJava.valueToCode(block, 'LEFT', Blockly.FtcJava.ORDER_COMMA);
  var top = Blockly.FtcJava.valueToCode(block, 'TOP', Blockly.FtcJava.ORDER_COMMA);
  var right = Blockly.FtcJava.valueToCode(block, 'RIGHT', Blockly.FtcJava.ORDER_COMMA);
  var bottom = Blockly.FtcJava.valueToCode(block, 'BOTTOM', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setClippingMargins(' +
      left + ', ' + top + ', ' + right + ', ' + bottom + ');\n';
}

function tfod_setZoom_JavaScript(block, identifier) {
  var magnification = Blockly.JavaScript.valueToCode(block, 'MAGNIFICATION', Blockly.JavaScript.ORDER_COMMA);
  var aspectRatio = Blockly.JavaScript.valueToCode(block, 'ASPECT_RATIO', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setZoom(' + magnification + ', ' + aspectRatio + ');\n';
}

function tfod_setZoom_FtcJava(block, className) {
  var identifier = importDeclareAssignObsolete(block, null, className);
  var magnification = Blockly.FtcJava.valueToCode(block, 'MAGNIFICATION', Blockly.FtcJava.ORDER_COMMA);
  var aspectRatio = Blockly.FtcJava.valueToCode(block, 'ASPECT_RATIO', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setZoom(' + magnification + ', ' + aspectRatio + ');\n';
}

function tfod_getRecognitions_JavaScript(block, identifier) {
  var code = 'JSON.parse(' + identifier + '.getRecognitions())';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
}

function tfod_getRecognitions_FtcJava(block, className) {
  var identifier = importDeclareAssignObsolete(block, null, className);
  var code = identifier + '.getRecognitions()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
}

function tfod_typedEnum_label_JavaScript(block) {
  var code = '"' + block.getFieldValue('LABEL') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
}

function tfod_typedEnum_label_FtcJava(block) {
  // Even in Java, a label is actually just a string, not an enum.
  var code = '"' + block.getFieldValue('LABEL') + '"';
  return [code, Blockly.FtcJava.ORDER_ATOMIC];
}

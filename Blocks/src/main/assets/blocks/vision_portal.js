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
 * @fileoverview FTC robot blocks related to VisionPortal.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// visionPortalIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// builderColor
// functionColor
// getPropertyColor


Blockly.Blocks['visionPortalBuilder_create_assign'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('set')
        .appendField(new Blockly.FieldVariable('myVisionPortalBuilder', null, ['VisionPortal.Builder'], 'VisionPortal.Builder'),
            'VISION_PORTAL_BUILDER')
        .appendField('to')
        .appendField('new')
        .appendField(createNonEditableField('VisionPortal.Builder'));
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(builderColor);
    this.setTooltip('Creates a new VisionPortal.Builder object and assigns it to a variable.');
  }
};

Blockly.JavaScript['visionPortalBuilder_create_assign'] = function(block) {
  var varName = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  return varName + ' = ' + visionPortalIdentifierForJavaScript + '.createBuilder();\n';
};

Blockly.FtcJava['visionPortalBuilder_create_assign'] = function(block) {
  var varName = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  Blockly.FtcJava.generateImport_('VisionPortal');
  return varName + ' = new VisionPortal.Builder();\n';
};

Blockly.Blocks['visionPortalBuilder_setCamera'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myVisionPortalBuilder', null, ['VisionPortal.Builder'], 'VisionPortal.Builder'),
            'VISION_PORTAL_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setCamera'));
    this.appendValueInput('CAMERA').setCheck(['BuiltinCameraDirection', 'WebcamName', 'SwitchableCameraName']);
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the camera to the specified builtin camera direction, webcam name, or switchable camera name.');
  }
};

Blockly.JavaScript['visionPortalBuilder_setCamera'] = function(block) {
  var visionPortalBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var camera = Blockly.JavaScript.valueToCode(
      block, 'CAMERA', Blockly.JavaScript.ORDER_COMMA);
  return visionPortalIdentifierForJavaScript + '.setCamera(' +
      visionPortalBuilder + ', ' + camera + ');\n';
};

Blockly.FtcJava['visionPortalBuilder_setCamera'] = function(block) {
  var visionPortalBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var camera = Blockly.FtcJava.valueToCode(
      block, 'CAMERA', Blockly.FtcJava.ORDER_NONE);
  return visionPortalBuilder + '.setCamera(' + camera + ');\n';
};

Blockly.Blocks['visionPortalBuilder_setStreamFormat'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myVisionPortalBuilder', null, ['VisionPortal.Builder'], 'VisionPortal.Builder'),
            'VISION_PORTAL_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setStreamFormat'));
    this.appendValueInput('STREAM_FORMAT').setCheck('VisionPortal.StreamFormat');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the stream format.');
  }
};

Blockly.JavaScript['visionPortalBuilder_setStreamFormat'] = function(block) {
  var visionPortalBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var streamFormat = Blockly.JavaScript.valueToCode(
      block, 'STREAM_FORMAT', Blockly.JavaScript.ORDER_COMMA);
  return visionPortalIdentifierForJavaScript + '.setStreamFormat(' +
      visionPortalBuilder + ', ' + streamFormat + ');\n';
};

Blockly.FtcJava['visionPortalBuilder_setStreamFormat'] = function(block) {
  var visionPortalBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var streamFormat = Blockly.FtcJava.valueToCode(
      block, 'STREAM_FORMAT', Blockly.FtcJava.ORDER_NONE);
  return visionPortalBuilder + '.setStreamFormat(' + streamFormat + ');\n';
};

Blockly.Blocks['visionPortalBuilder_enableLiveView'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myVisionPortalBuilder', null, ['VisionPortal.Builder'], 'VisionPortal.Builder'),
            'VISION_PORTAL_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('enableLiveView'));
    // For historical reasons, the input is named ENABLE_CAMERA_MONITORING.
    this.appendValueInput('ENABLE_CAMERA_MONITORING').setCheck('Boolean');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Enables the live camera preview.');
  }
};

Blockly.JavaScript['visionPortalBuilder_enableLiveView'] = function(block) {
  var visionPortalBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  // For historical reasons, the input is named ENABLE_CAMERA_MONITORING.
  var enableLiveView = Blockly.JavaScript.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.JavaScript.ORDER_COMMA);
  return visionPortalIdentifierForJavaScript + '.enableLiveView(' +
      visionPortalBuilder + ', ' + enableLiveView + ');\n';
};

Blockly.FtcJava['visionPortalBuilder_enableLiveView'] = function(block) {
  var visionPortalBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  // For historical reasons, the input is named ENABLE_CAMERA_MONITORING.
  var enableLiveView = Blockly.FtcJava.valueToCode(
      block, 'ENABLE_CAMERA_MONITORING', Blockly.FtcJava.ORDER_NONE);
  return visionPortalBuilder + '.enableLiveView(' + enableLiveView + ');\n';
};

// For the 8.2 release, the block type was visionPortalBuilder_enableCameraMonitoring.
Blockly.Blocks['visionPortalBuilder_enableCameraMonitoring'] = Blockly.Blocks['visionPortalBuilder_enableLiveView'];
Blockly.JavaScript['visionPortalBuilder_enableCameraMonitoring'] = Blockly.JavaScript['visionPortalBuilder_enableLiveView'];
Blockly.FtcJava['visionPortalBuilder_enableCameraMonitoring'] = Blockly.FtcJava['visionPortalBuilder_enableLiveView'];

Blockly.Blocks['visionPortalBuilder_setAutoStopLiveView'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myVisionPortalBuilder', null, ['VisionPortal.Builder'], 'VisionPortal.Builder'),
            'VISION_PORTAL_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setAutoStopLiveView'));
    this.appendValueInput('AUTO_STOP_LIVE_VIEW').setCheck('Boolean');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets whether to automatically stop the live view (RC preview) when all vision processors are disabled.');
  }
};

Blockly.JavaScript['visionPortalBuilder_setAutoStopLiveView'] = function(block) {
  var visionPortalBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var autoStopLiveView = Blockly.JavaScript.valueToCode(
      block, 'AUTO_STOP_LIVE_VIEW', Blockly.JavaScript.ORDER_COMMA);
  return visionPortalIdentifierForJavaScript + '.setAutoStopLiveView(' +
      visionPortalBuilder + ', ' + autoStopLiveView + ');\n';
};

Blockly.FtcJava['visionPortalBuilder_setAutoStopLiveView'] = function(block) {
  var visionPortalBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var autoStopLiveView = Blockly.FtcJava.valueToCode(
      block, 'AUTO_STOP_LIVE_VIEW', Blockly.FtcJava.ORDER_NONE);
  return visionPortalBuilder + '.setAutoStopLiveView(' + autoStopLiveView + ');\n';
};

Blockly.Blocks['visionPortalBuilder_setLiveViewContainerId'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myVisionPortalBuilder', null, ['VisionPortal.Builder'], 'VisionPortal.Builder'),
            'VISION_PORTAL_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setLiveViewContainerId'));
    this.appendValueInput('VIEW_ID').setCheck('Number');
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the live view container id.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'VIEW_ID':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['visionPortalBuilder_setLiveViewContainerId'] = function(block) {
  var visionPortalBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var viewId = Blockly.JavaScript.valueToCode(
      block, 'VIEW_ID', Blockly.JavaScript.ORDER_COMMA);
  return visionPortalIdentifierForJavaScript + '.setLiveViewContainerId(' +
      visionPortalBuilder + ', ' + viewId + ');\n';
};

Blockly.FtcJava['visionPortalBuilder_setLiveViewContainerId'] = function(block) {
  var visionPortalBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var viewId = Blockly.FtcJava.valueToCode(
      block, 'VIEW_ID', Blockly.FtcJava.ORDER_NONE);
  return visionPortalBuilder + '.setLiveViewContainerId(' + viewId + ');\n';
};

// For the 8.2 release, the block type was visionPortalBuilder_setCameraMonitorViewId.
Blockly.Blocks['visionPortalBuilder_setCameraMonitorViewId'] = Blockly.Blocks['visionPortalBuilder_setLiveViewContainerId'];
Blockly.JavaScript['visionPortalBuilder_setCameraMonitorViewId'] = Blockly.JavaScript['visionPortalBuilder_setLiveViewContainerId'];
Blockly.FtcJava['visionPortalBuilder_setCameraMonitorViewId'] = Blockly.FtcJava['visionPortalBuilder_setLiveViewContainerId'];

Blockly.Blocks['visionPortalBuilder_setCameraResolution'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myVisionPortalBuilder', null, ['VisionPortal.Builder'], 'VisionPortal.Builder'),
            'VISION_PORTAL_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('setCameraResolution'));
    this.appendValueInput('CAMERA_WIDTH').setCheck('Number')
        .appendField('width').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('CAMERA_HEIGHT').setCheck('Number')
        .appendField('height').setAlign(Blockly.ALIGN_RIGHT);
    this.setInputsInline(false);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Sets the camera resolution.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'CAMERA_WIDTH':
        case 'CAMERA_HEIGHT':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['visionPortalBuilder_setCameraResolution'] = function(block) {
  var visionPortalBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var width = Blockly.JavaScript.valueToCode(
      block, 'CAMERA_WIDTH', Blockly.JavaScript.ORDER_COMMA);
  var height = Blockly.JavaScript.valueToCode(
      block, 'CAMERA_HEIGHT', Blockly.JavaScript.ORDER_COMMA);
  return visionPortalIdentifierForJavaScript + '.setCameraResolution(' +
      visionPortalBuilder + ', ' + width + ', ' + height + ');\n';
};

Blockly.FtcJava['visionPortalBuilder_setCameraResolution'] = function(block) {
  var visionPortalBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var width = Blockly.FtcJava.valueToCode(
      block, 'CAMERA_WIDTH', Blockly.FtcJava.ORDER_COMMA);
  var height = Blockly.FtcJava.valueToCode(
      block, 'CAMERA_HEIGHT', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('Size');
  return visionPortalBuilder + '.setCameraResolution(new Size(' + width + ', ' + height + '));\n';
};

Blockly.Blocks['visionPortalBuilder_addProcessor'] = {
  init: function() {
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myVisionPortalBuilder', null, ['VisionPortal.Builder'], 'VisionPortal.Builder'),
            'VISION_PORTAL_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('addProcessor'));
    this.appendValueInput('VISION_PROCESSOR').setCheck(['AprilTagProcessor', 'TfodProcessor']);
    this.setInputsInline(true);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(builderColor);
    this.setTooltip('Adds a vision processor.');
  }
};

Blockly.JavaScript['visionPortalBuilder_addProcessor'] = function(block) {
  var visionPortalBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var processor = Blockly.JavaScript.valueToCode(
      block, 'VISION_PROCESSOR', Blockly.JavaScript.ORDER_COMMA);
  return visionPortalIdentifierForJavaScript + '.addProcessor(' +
      visionPortalBuilder + ', ' + processor + ');\n';
};

Blockly.FtcJava['visionPortalBuilder_addProcessor'] = function(block) {
  var visionPortalBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var processor = Blockly.FtcJava.valueToCode(
      block, 'VISION_PROCESSOR', Blockly.FtcJava.ORDER_NONE);
  return visionPortalBuilder + '.addProcessor(' + processor + ');\n';
};

Blockly.Blocks['visionPortalBuilder_build'] = {
  init: function() {
    this.setOutput(true, 'VisionPortal');
    this.appendDummyInput('FIELD_VARIABLE')
        .appendField('call')
        .appendField(new Blockly.FieldVariable('myVisionPortalBuilder', null, ['VisionPortal.Builder'], 'VisionPortal.Builder'),
            'VISION_PORTAL_BUILDER')
        .appendField('.')
        .appendField(createNonEditableField('build'));
    this.setInputsInline(true);
    this.setColour(builderColor);
    this.setTooltip('Builds a VisionPortal object from the given VisionPortal.Builder.');
  }
};

Blockly.JavaScript['visionPortalBuilder_build'] = function(block) {
  var visionPortalBuilder = Blockly.JavaScript.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var code = visionPortalIdentifierForJavaScript + '.build(' + visionPortalBuilder + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['visionPortalBuilder_build'] = function(block) {
  var visionPortalBuilder = Blockly.FtcJava.variableDB_.getName(
      block.getFieldValue('VISION_PORTAL_BUILDER'), Blockly.Variables.NAME_TYPE);
  var code = visionPortalBuilder + '.build()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['visionPortal_easyCreateWithDefaults_oneProcessor'] = {
  init: function() {
    this.setOutput(true, 'VisionPortal');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('easyCreateWithDefaults'));
    this.appendValueInput('CAMERA').setCheck(['BuiltinCameraDirection', 'WebcamName', 'SwitchableCameraName'])
        .appendField('camera').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VISION_PROCESSOR').setCheck(['AprilTagProcessor', 'TfodProcessor'])
        .appendField('visionProcessor').setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new VisionPortal object, with the given camera and vision ' +
        'processor, and default values for all other settings.');
  }
};

Blockly.JavaScript['visionPortal_easyCreateWithDefaults_oneProcessor'] = function(block) {
  var camera = Blockly.JavaScript.valueToCode(
      block, 'CAMERA', Blockly.JavaScript.ORDER_COMMA);
  var processor = Blockly.JavaScript.valueToCode(
      block, 'VISION_PROCESSOR', Blockly.JavaScript.ORDER_COMMA);
  var code = visionPortalIdentifierForJavaScript + '.easyCreateWithDefaults_oneProcessor(' +
      camera + ', ' + processor + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['visionPortal_easyCreateWithDefaults_oneProcessor'] = function(block) {
  var camera = Blockly.FtcJava.valueToCode(
      block, 'CAMERA', Blockly.FtcJava.ORDER_COMMA);
  var processor = Blockly.FtcJava.valueToCode(
      block, 'VISION_PROCESSOR', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('VisionPortal');
  var code = 'VisionPortal.easyCreateWithDefaults(' +
      camera + ', ' + processor + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['visionPortal_easyCreateWithDefaults_twoProcessors'] = {
  init: function() {
    this.setOutput(true, 'VisionPortal');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('easyCreateWithDefaults'));
    this.appendValueInput('CAMERA').setCheck(['BuiltinCameraDirection', 'WebcamName', 'SwitchableCameraName'])
        .appendField('camera').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VISION_PROCESSOR_1').setCheck(['AprilTagProcessor', 'TfodProcessor'])
        .appendField('visionProcessor1').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VISION_PROCESSOR_2').setCheck(['AprilTagProcessor', 'TfodProcessor'])
        .appendField('visionProcessor2').setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new VisionPortal object, with the given camera and vision ' +
        'processors, and default values for all other settings.');
  }
};

Blockly.JavaScript['visionPortal_easyCreateWithDefaults_twoProcessors'] = function(block) {
  var camera = Blockly.JavaScript.valueToCode(
      block, 'CAMERA', Blockly.JavaScript.ORDER_COMMA);
  var processor1 = Blockly.JavaScript.valueToCode(
      block, 'VISION_PROCESSOR_1', Blockly.JavaScript.ORDER_COMMA);
  var processor2 = Blockly.JavaScript.valueToCode(
      block, 'VISION_PROCESSOR_2', Blockly.JavaScript.ORDER_COMMA);
  var code = visionPortalIdentifierForJavaScript + '.easyCreateWithDefaults_twoProcessors(' +
      camera + ', ' + processor1 + ', ' + processor2 + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['visionPortal_easyCreateWithDefaults_twoProcessors'] = function(block) {
  var camera = Blockly.FtcJava.valueToCode(
      block, 'CAMERA', Blockly.FtcJava.ORDER_COMMA);
  var processor1 = Blockly.FtcJava.valueToCode(
      block, 'VISION_PROCESSOR_1', Blockly.FtcJava.ORDER_COMMA);
  var processor2 = Blockly.FtcJava.valueToCode(
      block, 'VISION_PROCESSOR_2', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('VisionPortal');
  var code = 'VisionPortal.easyCreateWithDefaults(' +
      camera + ', ' + processor1 + ', ' + processor2 + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['visionPortal_setProcessorEnabled'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('setProcessorEnabled'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VISION_PROCESSOR').setCheck(['AprilTagProcessor', 'TfodProcessor'])
        .appendField('visionProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ENABLED').setCheck('Boolean')
        .appendField('enabled')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Enables or disables a vision processor.');
  }
};

Blockly.JavaScript['visionPortal_setProcessorEnabled'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_COMMA);
  var processor = Blockly.JavaScript.valueToCode(
      block, 'VISION_PROCESSOR', Blockly.JavaScript.ORDER_COMMA);
  var enabled = Blockly.JavaScript.valueToCode(
      block, 'ENABLED', Blockly.JavaScript.ORDER_COMMA);
  return visionPortalIdentifierForJavaScript + '.setProcessorEnabled(' +
      visionPortal + ', ' + processor + ', ' + enabled + ');\n';
};

Blockly.FtcJava['visionPortal_setProcessorEnabled'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  var processor = Blockly.FtcJava.valueToCode(
      block, 'VISION_PROCESSOR', Blockly.FtcJava.ORDER_COMMA);
  var enabled = Blockly.FtcJava.valueToCode(
      block, 'ENABLED', Blockly.FtcJava.ORDER_COMMA);
  return visionPortal + '.setProcessorEnabled(' + processor + ', ' + enabled + ');\n';
};

Blockly.Blocks['visionPortal_getProcessorEnabled'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('getProcessorEnabled'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VISION_PROCESSOR').setCheck(['AprilTagProcessor', 'TfodProcessor'])
        .appendField('visionProcessor')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns true if the given vision processor is enabled.');
  }
};

Blockly.JavaScript['visionPortal_getProcessorEnabled'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_COMMA);
  var processor = Blockly.JavaScript.valueToCode(
      block, 'VISION_PROCESSOR', Blockly.JavaScript.ORDER_COMMA);
  var code = visionPortalIdentifierForJavaScript + '.getProcessorEnabled(' +
      visionPortal + ', ' + processor + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['visionPortal_getProcessorEnabled'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  var processor = Blockly.FtcJava.valueToCode(
      block, 'VISION_PROCESSOR', Blockly.FtcJava.ORDER_NONE);
  var code = visionPortal + '.getProcessorEnabled(' + processor + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['visionPortal_getCameraState'] = {
  init: function() {
    this.setOutput(true, 'VisionPortal.CameraState');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('getCameraState'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the state of the camera.');
  }
};

Blockly.JavaScript['visionPortal_getCameraState'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_NONE);
  var code = visionPortalIdentifierForJavaScript + '.getCameraState(' +
      visionPortal + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['visionPortal_getCameraState'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  var code = visionPortal + '.getCameraState()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['visionPortal_saveNextFrameRaw'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('saveNextFrameRaw'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('FILENAME').setCheck('String')
        .appendField('filename')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Saves the next frame to the given file.');
  }
};

Blockly.JavaScript['visionPortal_saveNextFrameRaw'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_COMMA);
  var filename = Blockly.JavaScript.valueToCode(
      block, 'FILENAME', Blockly.JavaScript.ORDER_COMMA);
  return visionPortalIdentifierForJavaScript + '.saveNextFrameRaw(' +
      visionPortal + ', ' + filename + ');\n';
};

Blockly.FtcJava['visionPortal_saveNextFrameRaw'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  var filename = Blockly.FtcJava.valueToCode(
      block, 'FILENAME', Blockly.FtcJava.ORDER_NONE);
  return visionPortal + '.saveNextFrameRaw(' + filename + ');\n';
};

Blockly.Blocks['visionPortal_stopStreaming'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('stopStreaming'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Temporarily stops the streaming session. This can save CPU resources, with the ability to resume quickly when needed.');
  }
};

Blockly.JavaScript['visionPortal_stopStreaming'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_NONE);
  return visionPortalIdentifierForJavaScript + '.stopStreaming(' +
      visionPortal + ');\n';
};

Blockly.FtcJava['visionPortal_stopStreaming'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  return visionPortal + '.stopStreaming();\n';
};

Blockly.Blocks['visionPortal_resumeStreaming'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('resumeStreaming'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Resumes the streaming session if previously stopped.');
  }
};

Blockly.JavaScript['visionPortal_resumeStreaming'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_NONE);
  return visionPortalIdentifierForJavaScript + '.resumeStreaming(' +
      visionPortal + ');\n';
};

Blockly.FtcJava['visionPortal_resumeStreaming'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  return visionPortal + '.resumeStreaming();\n';
};

Blockly.Blocks['visionPortal_stopLiveView'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('stopLiveView'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Temporarily stops the live view (RC preview).');
  }
};

Blockly.JavaScript['visionPortal_stopLiveView'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_NONE);
  return visionPortalIdentifierForJavaScript + '.stopLiveView(' +
      visionPortal + ');\n';
};

Blockly.FtcJava['visionPortal_stopLiveView'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  return visionPortal + '.stopLiveView();\n';
};

Blockly.Blocks['visionPortal_resumeLiveView'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('resumeLiveView'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Starts the live view (RC preview) again.');
  }
};

Blockly.JavaScript['visionPortal_resumeLiveView'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_NONE);
  return visionPortalIdentifierForJavaScript + '.resumeLiveView(' +
      visionPortal + ');\n';
};

Blockly.FtcJava['visionPortal_resumeLiveView'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  return visionPortal + '.resumeLiveView();\n';
};

Blockly.Blocks['visionPortal_getFps'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('getFps'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal').setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the current vision frame rate.');
    this.getFtcJavaOutputType = function() {
      return 'float';
    };
  }
};

Blockly.JavaScript['visionPortal_getFps'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_NONE);
  var code = visionPortalIdentifierForJavaScript + '.getFps(' +
      visionPortal + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['visionPortal_getFps'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  var code = visionPortal + '.getFps()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['visionPortal_getExposureControl'] = {
  init: function() {
    this.setOutput(true, 'ExposureControl');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('getExposureControl'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Gets the ExposureControl object.');
  }
};

Blockly.JavaScript['visionPortal_getExposureControl'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_NONE);
  var code = visionPortalIdentifierForJavaScript + '.getExposureControl(' +
      visionPortal + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['visionPortal_getExposureControl'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  Blockly.FtcJava.generateImport_('ExposureControl');
  var code = visionPortal + '.getCameraControl(ExposureControl.class)';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['visionPortal_getFocusControl'] = {
  init: function() {
    this.setOutput(true, 'FocusControl');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('getFocusControl'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Gets the FocusControl object.');
  }
};

Blockly.JavaScript['visionPortal_getFocusControl'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_NONE);
  var code = visionPortalIdentifierForJavaScript + '.getFocusControl(' +
      visionPortal + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['visionPortal_getFocusControl'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  Blockly.FtcJava.generateImport_('FocusControl');
  var code = visionPortal + '.getCameraControl(FocusControl.class)';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['visionPortal_getGainControl'] = {
  init: function() {
    this.setOutput(true, 'GainControl');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('getGainControl'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Gets the GainControl object.');
  }
};

Blockly.JavaScript['visionPortal_getGainControl'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_NONE);
  var code = visionPortalIdentifierForJavaScript + '.getGainControl(' +
      visionPortal + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['visionPortal_getGainControl'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  Blockly.FtcJava.generateImport_('GainControl');
  var code = visionPortal + '.getCameraControl(GainControl.class)';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['visionPortal_getPtzControl'] = {
  init: function() {
    this.setOutput(true, 'PtzControl');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('getPtzControl'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Gets the PtzControl object.');
  }
};

Blockly.JavaScript['visionPortal_getPtzControl'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_NONE);
  var code = visionPortalIdentifierForJavaScript + '.getPtzControl(' +
      visionPortal + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['visionPortal_getPtzControl'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  Blockly.FtcJava.generateImport_('PtzControl');
  var code = visionPortal + '.getCameraControl(PtzControl.class)';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['visionPortal_getWhiteBalanceControl'] = {
  init: function() {
    this.setOutput(true, 'WhiteBalanceControl');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('getWhiteBalanceControl'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Gets the WhiteBalanceControl object.');
  }
};

Blockly.JavaScript['visionPortal_getWhiteBalanceControl'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_NONE);
  var code = visionPortalIdentifierForJavaScript + '.getWhiteBalanceControl(' +
      visionPortal + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['visionPortal_getWhiteBalanceControl'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  Blockly.FtcJava.generateImport_('WhiteBalanceControl');
  var code = visionPortal + '.getCameraControl(WhiteBalanceControl.class)';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['visionPortal_setActiveCamera'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('setActiveCamera'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('WEBCAM_NAME').setCheck('WebcamName')
        .appendField('webcamName')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Switches the active camera. Use this block only for Switchable Cameras.');
  }
};

Blockly.JavaScript['visionPortal_setActiveCamera'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_COMMA);
  var webcamName = Blockly.JavaScript.valueToCode(
      block, 'WEBCAM_NAME', Blockly.JavaScript.ORDER_COMMA);
  return visionPortalIdentifierForJavaScript + '.setActiveCamera(' +
      visionPortal + ', ' + webcamName + ');\n';
};

Blockly.FtcJava['visionPortal_setActiveCamera'] = function(block) {
  var switchableCamera = Blockly.FtcJava.valueToCode(
      block, 'SWITCHABLE_CAMERA', Blockly.FtcJava.ORDER_MEMBER);
  var webcamName = Blockly.FtcJava.valueToCode(
      block, 'WEBCAM_NAME', Blockly.FtcJava.ORDER_NONE);
  return switchableCamera + '.setActiveCamera(' + webcamName + ');\n';
};

Blockly.Blocks['visionPortal_getActiveCamera'] = {
  init: function() {
    this.setOutput(true, 'WebcamName');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('getActiveCamera'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns the active camera, to be compared with a "webcam named" block. Use this block only for Switchable Cameras.');
  }
};

Blockly.JavaScript['visionPortal_getActiveCamera'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_NONE);
  var code = visionPortalIdentifierForJavaScript + '.getActiveCamera(' +
      visionPortal + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['visionPortal_getActiveCamera'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  var code = visionPortal + '.getActiveCamera()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['visionPortal_close'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('close'));
    this.appendValueInput('VISION_PORTAL').setCheck('VisionPortal')
        .appendField('visionPortal')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Close the camera stream.');
  }
};

Blockly.JavaScript['visionPortal_close'] = function(block) {
  var visionPortal = Blockly.JavaScript.valueToCode(
      block, 'VISION_PORTAL', Blockly.JavaScript.ORDER_COMMA);
  return visionPortalIdentifierForJavaScript + '.close(' +
      visionPortal + ');\n';
};

Blockly.FtcJava['visionPortal_close'] = function(block) {
  var visionPortal = Blockly.FtcJava.valueToCode(
      block, 'VISION_PORTAL', Blockly.FtcJava.ORDER_MEMBER);
  return visionPortal + '.close();\n';
};

Blockly.Blocks['visionPortal_makeMultiPortalView'] = {
  init: function() {
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('VisionPortal'))
        .appendField('.')
        .appendField(createNonEditableField('makeMultiPortalView'));
    this.appendValueInput('NUM_PORTALS').setCheck('Number')
        .appendField('numPortals').setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MULTI_PORTAL_LAYOUT').setCheck('VisionPortal.MultiPortalLayout')
        .appendField('multiPortalLayout').setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Makes a multi-portal view and returns the list of view ids.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'NUM_PORTALS':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['visionPortal_makeMultiPortalView'] = function(block) {
  var numPortals = Blockly.JavaScript.valueToCode(
      block, 'NUM_PORTALS', Blockly.JavaScript.ORDER_COMMA);
  var multiPortalLayout = Blockly.JavaScript.valueToCode(
      block, 'MULTI_PORTAL_LAYOUT', Blockly.JavaScript.ORDER_COMMA);
  var code = 'JSON.parse(' + visionPortalIdentifierForJavaScript + '.makeMultiPortalView(' +
      numPortals + ', ' + multiPortalLayout + '))';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['visionPortal_makeMultiPortalView'] = function(block) {
  var numPortals = Blockly.FtcJava.valueToCode(
      block, 'NUM_PORTALS', Blockly.FtcJava.ORDER_COMMA);
  var multiPortalLayout = Blockly.FtcJava.valueToCode(
      block, 'MULTI_PORTAL_LAYOUT', Blockly.FtcJava.ORDER_COMMA);
  Blockly.FtcJava.generateImport_('JavaUtil');
  Blockly.FtcJava.generateImport_('VisionPortal');
  var code = 'JavaUtil.makeIntegerList(VisionPortal.makeMultiPortalView(' +
      numPortals + ', ' + multiPortalLayout + '))';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// Enums

Blockly.Blocks['visionPortal_typedEnum_streamFormat'] = {
  init: function() {
    var STREAM_FORMAT_CHOICES = [
        ['YUY2', 'YUY2'],
        ['MJPEG', 'MJPEG'],
    ];
    this.setOutput(true, 'VisionPortal.StreamFormat');
    this.appendDummyInput()
        .appendField(createNonEditableField('StreamFormat'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(STREAM_FORMAT_CHOICES), 'STREAM_FORMAT');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['YUY2', 'The StreamFormat value YUY2.'],
        ['MJPEG', 'The StreamFormat value MJPEG.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('STREAM_FORMAT');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['visionPortal_typedEnum_streamFormat'] = function(block) {
  var code = '"' + block.getFieldValue('STREAM_FORMAT') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['visionPortal_typedEnum_streamFormat'] = function(block) {
  var code = 'VisionPortal.StreamFormat.' + block.getFieldValue('STREAM_FORMAT');
  Blockly.FtcJava.generateImport_('VisionPortal');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['visionPortal_typedEnum_cameraState'] = {
  init: function() {
    var CAMERA_STATE_CHOICES = [
        ['OPENING_CAMERA_DEVICE', 'OPENING_CAMERA_DEVICE'],
        ['CAMERA_DEVICE_READY', 'CAMERA_DEVICE_READY'],
        ['STARTING_STREAM', 'STARTING_STREAM'],
        ['STREAMING', 'STREAMING'],
        ['STOPPING_STREAM', 'STOPPING_STREAM'],
        ['CLOSING_CAMERA_DEVICE', 'CLOSING_CAMERA_DEVICE'],
        ['CAMERA_DEVICE_CLOSED', 'CAMERA_DEVICE_CLOSED'],
        ['ERROR','ERROR'],
    ];
    this.setOutput(true, 'VisionPortal.CameraState');
    this.appendDummyInput()
        .appendField(createNonEditableField('CameraState'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(CAMERA_STATE_CHOICES), 'CAMERA_STATE');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['OPENING_CAMERA_DEVICE', 'The CameraState value OPENING_CAMERA_DEVICE'],
        ['CAMERA_DEVICE_READY', 'The CameraState value CAMERA_DEVICE_READY'],
        ['STARTING_STREAM', 'The CameraState value STARTING_STREAM'],
        ['STREAMING', 'The CameraState value STREAMING'],
        ['STOPPING_STREAM', 'The CameraState value STOPPING_STREAM'],
        ['CLOSING_CAMERA_DEVICE', 'The CameraState value CLOSING_CAMERA_DEVICE'],
        ['CAMERA_DEVICE_CLOSED', 'The CameraState value CAMERA_DEVICE_CLOSED'],
        ['ERROR','The CameraState value ERROR'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('CAMERA_STATE');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['visionPortal_typedEnum_cameraState'] = function(block) {
  var code = '"' + block.getFieldValue('CAMERA_STATE') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['visionPortal_typedEnum_cameraState'] = function(block) {
  var code = 'VisionPortal.CameraState.' + block.getFieldValue('CAMERA_STATE');
  Blockly.FtcJava.generateImport_('VisionPortal');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['visionPortal_typedEnum_multiPortalLayout'] = {
  init: function() {
    var MULTI_PORTAL_LAYOUT_CHOICES = [
        ['VERTICAL', 'VERTICAL'],
        ['HORIZONTAL', 'HORIZONTAL'],
    ];
    this.setOutput(true, 'VisionPortal.MultiPortalLayout');
    this.appendDummyInput()
        .appendField(createNonEditableField('MultiPortalLayout'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(MULTI_PORTAL_LAYOUT_CHOICES), 'MULTI_PORTAL_LAYOUT');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['VERTICAL', 'The MultiPortalLayout value VERTICAL.'],
        ['HORIZONTAL', 'The MultiPortalLayout value HORIZONTAL.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('MULTI_PORTAL_LAYOUT');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['visionPortal_typedEnum_multiPortalLayout'] = function(block) {
  var code = '"' + block.getFieldValue('MULTI_PORTAL_LAYOUT') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['visionPortal_typedEnum_multiPortalLayout'] = function(block) {
  var code = 'VisionPortal.MultiPortalLayout.' + block.getFieldValue('MULTI_PORTAL_LAYOUT');
  Blockly.FtcJava.generateImport_('VisionPortal');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

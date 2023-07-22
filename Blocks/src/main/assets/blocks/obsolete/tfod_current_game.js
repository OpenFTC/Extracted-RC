/**
 * @license
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
 * @fileoverview FTC robot blocks related to TensorFlow Object Detection for the current game.
 * @author lizlooney@google.com (Liz Looney)
 */

// These blocks are deprecated.

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// createTfodCurrentGameLabelDropdown
// tfodCurrentGameBlocksFirstName
// tfodCurrentGameIdentifierForJavaScript
// vuforiaCurrentGameIdentifierForJavaScript
// TFOD_CURRENT_GAME_LABEL_TOOLTIPS
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor

Blockly.Blocks['tfodCurrentGame_initialize'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(tfodCurrentGameBlocksFirstName))
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
    this.setTooltip('Initialize TensorFlow Object Detection for ' + tfodCurrentGameName + '.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MINIMUM_CONFIDENCE':
          return 'float';
      }
      return '';
    };
  }
};

Blockly.JavaScript['tfodCurrentGame_initialize'] = function(block) {
  return tfod_initialize_JavaScript(block, tfodCurrentGameIdentifierForJavaScript,
      vuforiaCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCurrentGame_initialize'] = function(block) {
  return tfod_initialize_FtcJava(block, 'TfodCurrentGame', 'VuforiaCurrentGame');
};

Blockly.Blocks['tfodCurrentGame_activate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(tfodCurrentGameBlocksFirstName))
        .appendField('.')
        .appendField(createNonEditableField('activate'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Activates object detection.');
  }
};

Blockly.JavaScript['tfodCurrentGame_activate'] = function(block) {
  return tfod_activate_JavaScript(block, tfodCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCurrentGame_activate'] = function(block) {
  return tfod_activate_FtcJava(block, 'TfodCurrentGame');
};

Blockly.Blocks['tfodCurrentGame_deactivate'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(tfodCurrentGameBlocksFirstName))
        .appendField('.')
        .appendField(createNonEditableField('deactivate'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Deactivates object detection.');
  }
};

Blockly.JavaScript['tfodCurrentGame_deactivate'] = function(block) {
  return tfod_deactivate_JavaScript(block, tfodCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCurrentGame_deactivate'] = function(block) {
  return tfod_deactivate_FtcJava(block, 'TfodCurrentGame');
};

Blockly.Blocks['tfodCurrentGame_setClippingMargins'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(tfodCurrentGameBlocksFirstName))
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

Blockly.JavaScript['tfodCurrentGame_setClippingMargins'] = function(block) {
  return tfod_setClippingMargins_JavaScript(block, tfodCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCurrentGame_setClippingMargins'] = function(block) {
  return tfod_setClippingMargins_FtcJava(block, 'TfodCurrentGame');
};

Blockly.Blocks['tfodCurrentGame_setZoom'] = {
  init: function() {
          this.appendDummyInput()
              .appendField('call')
              .appendField(createNonEditableField(tfodCurrentGameBlocksFirstName))
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
              'were used to train the TensorFlow model (1.7777 for 16/9).');
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

Blockly.JavaScript['tfodCurrentGame_setZoom'] = function(block) {
  return tfod_setZoom_JavaScript(block, tfodCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCurrentGame_setZoom'] = function(block) {
  return tfod_setZoom_FtcJava(block, 'TfodCurrentGame');
};

Blockly.Blocks['tfodCurrentGame_getRecognitions'] = {
  init: function() {
    this.setOutput(true, 'Array');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField(tfodCurrentGameBlocksFirstName))
        .appendField('.')
        .appendField(createNonEditableField('getRecognitions'));
    this.setColour(functionColor);
    this.setTooltip('Returns a List of the current recognitions.');
    this.getFtcJavaOutputType = function() {
      return 'List<Recognition>';
    };
  }
};

Blockly.JavaScript['tfodCurrentGame_getRecognitions'] = function(block) {
  return tfod_getRecognitions_JavaScript(block, tfodCurrentGameIdentifierForJavaScript);
};

Blockly.FtcJava['tfodCurrentGame_getRecognitions'] = function(block) {
  return tfod_getRecognitions_FtcJava(block, 'TfodCurrentGame');
};

Blockly.Blocks['tfodCurrentGame_typedEnum_label'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField(createNonEditableField('Label'))
        .appendField('.')
        .appendField(createTfodCurrentGameLabelDropdown(), 'LABEL');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = TFOD_CURRENT_GAME_LABEL_TOOLTIPS;
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('LABEL');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['tfodCurrentGame_typedEnum_label'] = function(block) {
  return tfod_typedEnum_label_JavaScript(block);
};

Blockly.FtcJava['tfodCurrentGame_typedEnum_label'] = function(block) {
  return tfod_typedEnum_label_FtcJava(block);
};

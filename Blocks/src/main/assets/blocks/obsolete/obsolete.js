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

function isObsolete(block) {
  if (block.type == 'navigation_enum_cameraDirection' ||
      block.type == 'navigation_typedEnum_cameraDirection' ||
      block.type == 'navigation_enum_cameraMonitorFeedback' ||
      block.type == 'navigation_typedEnum_cameraMonitorFeedback' ||
      block.type == 'tfod_useDefaultModel' ||
      block.type == 'tfod_useModelFromAsset' ||
      block.type == 'tfod_useModelFromFile' ||
      block.type == 'tfod_initialize' ||
      block.type == 'tfod_initialize_withMoreArgs' ||
      block.type == 'tfod_activate' ||
      block.type == 'tfod_deactivate' ||
      block.type == 'tfod_setClippingMargins' ||
      block.type == 'tfod_setZoom' ||
      block.type == 'tfod_getRecognitions' ||
      block.type.startsWith('tfodLegacy_') ||
      block.type.startsWith('tfodCurrentGame_') ||
      block.type.startsWith('tfodCustomModel_') ||
      block.type.startsWith('vuforia') ||
      block.type.startsWith('vuMarks') ||
      block.type.startsWith('elapsedTime_')) {
    return true;
  }
  return false;
}

function wasForRelicRecovery(block) {
  if (block.type == 'vuforia_initialize' ||
      block.type == 'vuforia_initializeExtended' ||
      block.type == 'vuforia_initializeExtendedNoKey' ||
      block.type == 'vuforia_initialize_withWebcam' ||
      block.type == 'vuforia_activate' ||
      block.type == 'vuforia_deactivate' ||
      block.type == 'vuforia_track' ||
      block.type == 'vuforia_trackPose' ||
      block.type == 'vuforia_typedEnum_trackableName' ||
      block.type == 'vuforiaTrackingResults_getProperty_RelicRecoveryVuMark' ||
      block.type == 'vuMarks_typedEnum_relicRecoveryVuMark') {
    return true;
  }
  return false;
}

function wasForRoverRuckus(block) {
  if (block.type == 'tfodRoverRuckus_initialize' ||
      block.type == 'tfodRoverRuckus_activate' ||
      block.type == 'tfodRoverRuckus_deactivate' ||
      block.type == 'tfodRoverRuckus_setClippingMargins' ||
      block.type == 'tfodRoverRuckus_getRecognitions' ||
      block.type == 'tfodRoverRuckus_typedEnum_label' ||
      block.type == 'vuforiaRoverRuckus_initialize_withCameraDirection' ||
      block.type == 'vuforiaRoverRuckus_initialize_withWebcam' ||
      block.type == 'vuforiaRoverRuckus_activate' ||
      block.type == 'vuforiaRoverRuckus_deactivate' ||
      block.type == 'vuforiaRoverRuckus_track' ||
      block.type == 'vuforiaRoverRuckus_trackPose' ||
      block.type == 'vuforiaRoverRuckus_typedEnum_trackableName') {
    return true;
  }

  return false;
}

function wasForSkyStone(block) {
  if (block.type == 'tfodSkyStone_initialize' ||
      block.type == 'tfodSkyStone_activate' ||
      block.type == 'tfodSkyStone_deactivate' ||
      block.type == 'tfodSkyStone_setClippingMargins' ||
      block.type == 'tfodSkyStone_setZoom' ||
      block.type == 'tfodSkyStone_getRecognitions' ||
      block.type == 'tfodSkyStone_typedEnum_label' ||
      block.type == 'vuforiaSkyStone_initialize_withCameraDirection' ||
      block.type == 'vuforiaSkyStone_initialize_withWebcam' ||
      block.type == 'vuforiaSkyStone_activate' ||
      block.type == 'vuforiaSkyStone_deactivate' ||
      block.type == 'vuforiaSkyStone_setActiveCamera' ||
      block.type == 'vuforiaSkyStone_track' ||
      block.type == 'vuforiaSkyStone_trackPose' ||
      block.type == 'vuforiaSkyStone_typedEnum_trackableName') {
    return true;
  }

  return false;
}

function addReservedWordsForFtcJavaObsolete() {
  Blockly.FtcJava.addReservedWords('VuforiaBase');
  Blockly.FtcJava.addReservedWords('VuforiaLocalizer');
  Blockly.FtcJava.addReservedWords('VuforiaRelicRecovery');
  Blockly.FtcJava.addReservedWords('VuforiaRoverRuckus');
  Blockly.FtcJava.addReservedWords('VuforiaTrackable');
  Blockly.FtcJava.addReservedWords('VuforiaTrackableDefaultListener');
  Blockly.FtcJava.addReservedWords('VuforiaTrackables');
  Blockly.FtcJava.addReservedWords('TfodBase');
  Blockly.FtcJava.addReservedWords('TfodRoverRuckus');
}

function knownTypeToClassNameObsolete(type) {
  switch (type) {
    case 'VuforiaBase':
    case 'VuforiaBase.TrackingResults':
    case 'VuforiaCurrentGame':
    case 'VuforiaLocalizer':
    case 'VuforiaLocalizer.CameraDirection':
    case 'VuforiaLocalizer.Parameters':
    case 'VuforiaLocalizer.Parameters.CameraMonitorFeedback':
    case 'VuforiaRelicRecovery':
    case 'VuforiaRoverRuckus':
    case 'VuforiaSkyStone':
    case 'VuforiaTrackable':
    case 'VuforiaTrackableDefaultListener':
    case 'VuforiaTrackables':
      return 'org.firstinspires.ftc.robotcore.external.navigation.' + type;
    case 'Tfod':
    case 'TfodBase':
    case 'TfodCurrentGame':
    case 'TfodCustomModel':
    case 'TfodRoverRuckus':
    case 'TfodSkyStone':
      return 'org.firstinspires.ftc.robotcore.external.tfod.' + type;
  }
  return null;
}

function importDeclareAssignObsolete(block, identifierFieldName, javaType) {
  var identifier = null;
  var identifierForFtcJava = null;
  var rvalue = null;
  var needsToBeClosed = false;
  switch (javaType) {
    case 'Tfod':
      // tfodIdentifierForFtcJava is defined dynamically in
      // HardwareUtil.fetchJavaScriptForHardware().
      identifierForFtcJava = identifier = tfodIdentifierForFtcJava;
      rvalue = 'new ' + javaType + '()';
      needsToBeClosed = true;
      break;
    case 'TfodCurrentGame':
      // tfodCurrentGameIdentifierForFtcJava is defined dynamically in
      // HardwareUtil.fetchJavaScriptForHardware().
      identifierForFtcJava = identifier = tfodCurrentGameIdentifierForFtcJava;
      rvalue = 'new ' + javaType + '()';
      needsToBeClosed = true;
      break;
    case 'TfodCustomModel':
      // tfodCustomModelIdentifierForFtcJava is defined dynamically in
      // HardwareUtil.fetchJavaScriptForHardware().
      identifierForFtcJava = identifier = tfodCustomModelIdentifierForFtcJava;
      rvalue = 'new ' + javaType + '()';
      needsToBeClosed = true;
      break;
    case 'TfodRoverRuckus':
      // tfodRoverRuckusIdentifierForFtcJava is defined dynamically in
      // HardwareUtil.fetchJavaScriptForHardware().
      identifierForFtcJava = identifier = tfodRoverRuckusIdentifierForFtcJava;
      rvalue = 'new ' + javaType + '()';
      needsToBeClosed = true;
      break;
    case 'TfodSkyStone':
      // tfodSkyStoneIdentifierForFtcJava is defined dynamically in
      // HardwareUtil.fetchJavaScriptForHardware().
      identifierForFtcJava = identifier = tfodSkyStoneIdentifierForFtcJava;
      rvalue = 'new ' + javaType + '()';
      needsToBeClosed = true;
      break;
    case 'VuforiaCurrentGame':
      // vuforiaCurrentGameIdentifierForFtcJava is defined dynamically in
      // HardwareUtil.fetchJavaScriptForHardware().
      identifierForFtcJava = identifier = vuforiaCurrentGameIdentifierForFtcJava;
      rvalue = 'new ' + javaType + '()';
      needsToBeClosed = true;
      break;
    case 'VuforiaRelicRecovery':
      // vuforiaRelicRecoveryIdentifierForFtcJava is defined dynamically in
      // HardwareUtil.fetchJavaScriptForHardware().
      identifierForFtcJava = identifier = vuforiaRelicRecoveryIdentifierForFtcJava;
      rvalue = 'new ' + javaType + '()';
      needsToBeClosed = true;
      break;
    case 'VuforiaRoverRuckus':
      // vuforiaRoverRuckusIdentifierForFtcJava is defined dynamically in
      // HardwareUtil.fetchJavaScriptForHardware().
      identifierForFtcJava = identifier = vuforiaRoverRuckusIdentifierForFtcJava;
      rvalue = 'new ' + javaType + '()';
      needsToBeClosed = true;
      break;
    case 'VuforiaSkyStone':
      // vuforiaSkyStoneIdentifierForFtcJava is defined dynamically in
      // HardwareUtil.fetchJavaScriptForHardware().
      identifierForFtcJava = identifier = vuforiaSkyStoneIdentifierForFtcJava;
      rvalue = 'new ' + javaType + '()';
      needsToBeClosed = true;
      break;
    default:
      throw 'Unexpected situation (javaType is \'' + javaType + '\').';
  }

  Blockly.FtcJava.generateImport_(javaType);
  Blockly.FtcJava.definitions_['declare_field_' + identifier] =
      'private ' + javaType + ' ' + identifierForFtcJava + ';';

  Blockly.FtcJava.definitions_['assign_field_' + identifier] =
      identifierForFtcJava + ' = ' + rvalue + ';';

  if (needsToBeClosed) {
    Blockly.FtcJava.definitions_['closing_code_' + identifier] = identifierForFtcJava + '.close();';
  }

  return identifierForFtcJava;
};


//....................................................................................
// The following code used to be generated by HardwareUtil.fetchJavaScriptForHardware.

var tfodCurrentGameName = 'POWERPLAY';
var tfodCurrentGameNameNoSpaces = 'POWERPLAY';
var tfodCurrentGameBlocksFirstName = 'TensorFlowObjectDetectionPOWERPLAY';
var vuforiaCurrentGameName = 'POWERPLAY';
var vuforiaCurrentGameBlocksFirstName = 'VuforiaPOWERPLAY';

var switchableCameraName = 'Switchable Camera';

function createTfodRoverRuckusLabelDropdown() {
  var CHOICES = [
      ['Gold Mineral', 'Gold Mineral'],
      ['Silver Mineral', 'Silver Mineral'],
  ];
  return createFieldDropdown(CHOICES);
}

var TFOD_ROVER_RUCKUS_LABEL_TOOLTIPS = [
  ['Gold Mineral', 'The Label value Gold Mineral.'],
  ['Silver Mineral', 'The Label value Silver Mineral.'],
];

function createVuforiaRoverRuckusTrackableNameDropdown() {
  var CHOICES = [
      ['BluePerimeter', 'BluePerimeter'],
      ['RedPerimeter', 'RedPerimeter'],
      ['FrontPerimeter', 'FrontPerimeter'],
      ['BackPerimeter', 'BackPerimeter'],
  ];
  return createFieldDropdown(CHOICES);
}

var VUFORIA_ROVER_RUCKUS_TRACKABLE_NAME_TOOLTIPS = [
  ['BluePerimeter', 'The TrackableName value BluePerimeter.'],
  ['RedPerimeter', 'The TrackableName value RedPerimeter.'],
  ['FrontPerimeter', 'The TrackableName value FrontPerimeter.'],
  ['BackPerimeter', 'The TrackableName value BackPerimeter.'],
];

function createTfodSkyStoneLabelDropdown() {
  var CHOICES = [
      ['Stone', 'Stone'],
      ['Skystone', 'Skystone'],
      ];
  return createFieldDropdown(CHOICES);
}

var TFOD_SKY_STONE_LABEL_TOOLTIPS = [
   ['Stone', 'The Label value Stone.'],
   ['Skystone', 'The Label value Skystone.'],
   ];

function createVuforiaSkyStoneTrackableNameDropdown() {
  var CHOICES = [
      ['Stone Target', 'Stone Target'],
      ['Blue Rear Bridge', 'Blue Rear Bridge'],
      ['Red Rear Bridge', 'Red Rear Bridge'],
      ['Red Front Bridge', 'Red Front Bridge'],
      ['Blue Front Bridge', 'Blue Front Bridge'],
      ['Red Perimeter 1', 'Red Perimeter 1'],
      ['Red Perimeter 2', 'Red Perimeter 2'],
      ['Front Perimeter 1', 'Front Perimeter 1'],
      ['Front Perimeter 2', 'Front Perimeter 2'],
      ['Blue Perimeter 1', 'Blue Perimeter 1'],
      ['Blue Perimeter 2', 'Blue Perimeter 2'],
      ['Rear Perimeter 1', 'Rear Perimeter 1'],
      ['Rear Perimeter 2', 'Rear Perimeter 2'],
  ];
  return createFieldDropdown(CHOICES);
}

var VUFORIA_SKY_STONE_TRACKABLE_NAME_TOOLTIPS = [
  ['Stone Target', 'The TrackableName value Stone Target.'],
  ['Blue Rear Bridge', 'The TrackableName value Blue Rear Bridge.'],
  ['Red Rear Bridge', 'The TrackableName value Red Rear Bridge.'],
  ['Red Front Bridge', 'The TrackableName value Red Front Bridge.'],
  ['Blue Front Bridge', 'The TrackableName value Blue Front Bridge.'],
  ['Red Perimeter 1', 'The TrackableName value Red Perimeter 1.'],
  ['Red Perimeter 2', 'The TrackableName value Red Perimeter 2.'],
  ['Front Perimeter 1', 'The TrackableName value Front Perimeter 1.'],
  ['Front Perimeter 2', 'The TrackableName value Front Perimeter 2.'],
  ['Blue Perimeter 1', 'The TrackableName value Blue Perimeter 1.'],
  ['Blue Perimeter 2', 'The TrackableName value Blue Perimeter 2.'],
  ['Rear Perimeter 1', 'The TrackableName value Rear Perimeter 1.'],
  ['Rear Perimeter 2', 'The TrackableName value Rear Perimeter 2.'],
];

function createVuforiaCurrentGameTrackableNameDropdown() {
  var CHOICES = [
      ['Red Audience Wall', 'Red Audience Wall'],
      ['Red Rear Wall', 'Red Rear Wall'],
      ['Blue Audience Wall', 'Blue Audience Wall'],
      ['Blue Rear Wall', 'Blue Rear Wall'],
  ];
  return createFieldDropdown(CHOICES);
}

var VUFORIA_CURRENT_GAME_TRACKABLE_NAME_TOOLTIPS = [
  ['Red Audience Wall', 'The TrackableName value Red Audience Wall.'],
  ['Red Rear Wall', 'The TrackableName value Red Rear Wall.'],
  ['Blue Audience Wall', 'The TrackableName value Blue Audience Wall.'],
  ['Blue Rear Wall', 'The TrackableName value Blue Rear Wall.'],
];

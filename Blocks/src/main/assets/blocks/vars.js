/**
 * @license
 * Copyright 2016 Google LLC
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

var titlePrefix = 'FTC';
var currentProjectName;
var currentClassName = '';
var savedBlkFileContent;
var missingHardware = [];
var blockIdsWithMissingHardware = [];
var WarningBits = {
  NONE: 0,
  MISSING_HARDWARE: 1 << 0,
  RELIC_RECOVERY: 1 << 1,
  ROVER_RUCKUS: 1 << 2,
  MISSING_METHOD: 1 << 3,
};
var mouseX, mouseY;
var previousClipboardXml;
var savedClipboardContent;

var blocksFinishedLoading = false;
var showJavaCheckbox;
var javaArea;
var javaContent;
var parentArea;
var blocksAndBannerArea;
var blocklyArea;
var blocklyDiv;
var banner;
var bannerText;
var bannerButton;
var split;
var workspace;

var projectEnabled = true;

var setPropertyColor = 147;
var getPropertyColor = 151;
var functionColor = 289;
var commentColor = 200;

var identifierFieldNames = ['IDENTIFIER', 'IDENTIFIER1', 'IDENTIFIER2'];

function createNonEditableField(label) {
  var field = new Blockly.FieldTextInput(label);
  field.CURSOR = '';
  field.showEditor_ = function(opt_quietInput) {};
  return field;
}

function createFieldDropdown(choices) {
  if (choices.length == 0) {
    return createNonEditableField('');
  }
  // Disable validation. We'll show a warning if the newValue is not in the choices. This can
  // happen if the hardware configuration has changed.
  var field = new Blockly.FieldDropdown(choices);
  field.doClassValidation_ = function(newValue) {
    return newValue;
  };
  return field;
}

// TODO(lizlooney): Use "createPropertyDropdown(PROPERTY_CHOICES)" in place of "new Blockly.FieldDropdown(PROPERTY_CHOICES)"
function createPropertyDropdown(propertyChoices) {
  if (propertyChoices.length == 1 && propertyChoices[0].length == 2 && propertyChoices[0][0] == propertyChoices[0][1]) {
    return createNonEditableField(propertyChoices[0][0]);
  }
  return new Blockly.FieldDropdown(propertyChoices)
}

function isJavaIdentifierStart(c) {
  return /[a-zA-Z$_]/.test(c);
}

function isJavaIdentifierPart(c) {
  return /[a-zA-Z0-9$_]/.test(c);
}

function containsAtLeastOneAlphanumeric(s) {
  return /[a-zA-Z0-9]+/.test(s);
}

function containsAmpersand(s) {
  return /&+/.test(s);
}

function makeIdentifier(deviceName) {
  var identifier = '';

  var c = deviceName.charAt(0);
  if (isJavaIdentifierStart(c)) {
    identifier += c;
  } else if (isJavaIdentifierPart(c)) {
    identifier += ('_' + c);
  }

  for (var i = 1; i < deviceName.length; i++) {
    c = deviceName.charAt(i);
    if (isJavaIdentifierPart(c)) {
      identifier += c;
    }
  }
  return identifier;
}

function escapeHtml(text) {
  var out = '';
  for (var i = 0; i < text.length; i++) {
    var c = text.charAt(i);
    if (c == ' ') {
      out += '&nbsp;';
    } else if (c == '<') {
      out += '&lt;'
    } else if (c == '>') {
      out += '&gt;';
    } else if (c == '&') {
      out += '&amp;';
    } else if (c > 0x7E || c < ' ') {
      out += ('&#' + text.charCodeAt(i) + ';');
    } else {
      out += c;
    }
  }
  return out;
}

function formatExtraXml(flavor, group, autoTransition, enabled) {
  return '<?xml version=\'1.0\' encoding=\'UTF-8\' standalone=\'yes\' ?>' +
      '<Extra>' +
      '<OpModeMeta flavor="' + flavor + '" group="' + group + '" autoTransition="' + autoTransition + '" />' +
      '<Enabled value="' + enabled + '" />' +
      '</Extra> ';
}

function parseExtraXml(blkFileContent) {
  var extra = Object.create(null);
  extra['flavor'] = 'TELEOP';
  extra['group'] = '';
  extra['autoTransition'] = '';
  extra['enabled'] = true;

  // The blocks content is up to and including the first </xml>.
  var i = blkFileContent.indexOf('</xml>');
  // The extra xml content is after the first </xml>.
  // Set OpModeMeta and Enabled UI components.
  var extraXml = blkFileContent.substring(i + 6); // 6 is length of </xml>
  if (extraXml.length > 0) {
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(extraXml.trim(), 'text/xml');
    var opModeMetaElements = xmlDoc.getElementsByTagName('OpModeMeta');
    if (opModeMetaElements.length >= 1) {
      extra['flavor'] = opModeMetaElements[0].getAttribute('flavor');
      extra['group'] = opModeMetaElements[0].getAttribute('group');
      extra['autoTransition'] = opModeMetaElements[0].getAttribute('autoTransition');
    }
    var enabledElements = xmlDoc.getElementsByTagName('Enabled');
    if (enabledElements.length >= 1) {
      var enabledString = enabledElements[0].getAttribute('value');
      if (enabledString) {
        extra['enabled'] = (enabledString == 'true');
      }
    }
  }
  return extra;
}

function knownTypeToClassName(type) {
  // NOTE(lizlooney): If you add a case to this switch, you should also add that type to
  // HardwareUtil.buildReservedWordsForFtcJava.
  switch (type) {
    case 'Color':
      return 'android.graphics.' + type;
    case 'SoundPlayer':
      return 'com.qualcomm.ftccommon.' + type;
    case 'BNO055IMU':
    case 'BNO055IMU.AccelerationIntegrator':
    case 'BNO055IMU.AccelUnit':
    case 'BNO055IMU.Parameters':
    case 'BNO055IMU.SensorMode':
    case 'BNO055IMU.SystemStatus':
    case 'JustLoggingAccelerationIntegrator':
      return 'com.qualcomm.hardware.bosch.' + type;
    case 'ModernRoboticsI2cCompassSensor':
    case 'ModernRoboticsI2cGyro':
    case 'ModernRoboticsI2cGyro.HeadingMode':
    case 'ModernRoboticsI2cRangeSensor':
      return 'com.qualcomm.hardware.modernrobotics.' + type;
    case 'RevBlinkinLedDriver':
    case 'RevBlinkinLedDriver.BlinkinPattern':
    case 'RevHubOrientationOnRobot':
    case 'RevHubOrientationOnRobot.LogoFacingDirection':
    case 'RevHubOrientationOnRobot.UsbFacingDirection':
      return 'com.qualcomm.hardware.rev.' + type;
    case 'Autonomous':
    case 'Disabled':
    case 'LinearOpMode':
    case 'TeleOp':
      return 'com.qualcomm.robotcore.eventloop.opmode.' + type;
    case 'AccelerationSensor':
    case 'AnalogInput':
    case 'CRServo':
    case 'ColorSensor':
    case 'CompassSensor':
    case 'CompassSensor.CompassMode':
    case 'DcMotor':
    case 'DcMotor.RunMode':
    case 'DcMotor.ZeroPowerBehavior':
    case 'DcMotorEx':
    case 'DcMotorSimple':
    case 'DcMotorSimple.Direction':
    case 'DigitalChannel':
    case 'DigitalChannel.Mode':
    case 'DistanceSensor':
    case 'Gamepad':
    case 'Gamepad.LedEffect':
    case 'Gamepad.LedEffect.Builder':
    case 'Gamepad.RumbleEffect':
    case 'Gamepad.RumbleEffect.Builder':
    case 'GyroSensor':
    case 'Gyroscope':
    case 'I2cAddr':
    case 'I2cAddrConfig':
    case 'I2cAddressableDevice':
    case 'IrSeekerSensor':
    case 'IrSeekerSensor.Mode':
    case 'IMU':
    case 'IMU.Parameters':
    case 'ImuOrientationOnRobot':
    case 'LED':
    case 'Light':
    case 'LightSensor':
    case 'MotorControlAlgorithm':
    case 'NormalizedColorSensor':
    case 'NormalizedRGBA':
    case 'OpticalDistanceSensor':
    case 'OrientationSensor':
    case 'PIDCoefficients':
    case 'PIDFCoefficients':
    case 'PWMOutput':
    case 'Servo':
    case 'Servo.Direction':
    case 'ServoController':
    case 'ServoController.PwmStatus':
    case 'SwitchableLight':
    case 'TouchSensor':
    case 'UltrasonicSensor':
    case 'VoltageSensor':
      return 'com.qualcomm.robotcore.hardware.' + type;
    case 'ElapsedTime':
    case 'ElapsedTime.Resolution':
    case 'Range':
    case 'ReadWriteFile':
    case 'RobotLog':
      return 'com.qualcomm.robotcore.util.' + type;
    case 'Boolean':
    case 'Byte':
    case 'Character':
    case 'Double':
    case 'Float':
    case 'Integer':
    case 'Long':
    case 'Number':
    case 'Object':
    case 'Short':
    case 'String':
      return 'java.lang.' + type;
    case 'ArrayList':
    case 'Collections':
    case 'List':
      return 'java.util.' + type;
    case 'ClassFactory':
    case 'JavaUtil':
    case 'Telemetry':
    case 'Telemetry.DisplayFormat':
      return 'org.firstinspires.ftc.robotcore.external.' + type;
    case 'AndroidAccelerometer':
    case 'AndroidGyroscope':
    case 'AndroidOrientation':
    case 'AndroidSoundPool':
    case 'AndroidTextToSpeech':
      return 'org.firstinspires.ftc.robotcore.external.android.' + type;
    case 'CameraName':
    case 'WebcamName':
      return 'org.firstinspires.ftc.robotcore.external.hardware.camera.' + type;
    case 'MatrixF':
    case 'OpenGLMatrix':
    case 'VectorF':
      return 'org.firstinspires.ftc.robotcore.external.matrices.' + type;
    case 'Acceleration':
    case 'AngleUnit':
    case 'AngularVelocity':
    case 'AxesOrder':
    case 'AxesReference':
    case 'Axis':
    case 'CurrentUnit':
    case 'DistanceUnit':
    case 'MagneticFlux':
    case 'Orientation':
    case 'Position':
    case 'Quaternion':
    case 'RelicRecoveryVuMark':
    case 'Temperature':
    case 'TempUnit':
    case 'UnnormalizedAngleUnit':
    case 'Velocity':
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
    case 'YawPitchRollAngles':
      return 'org.firstinspires.ftc.robotcore.external.navigation.' + type;
    case 'AppUtil':
      return 'org.firstinspires.ftc.robotcore.internal.system.' + type;
    case 'Recognition':
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

function wrapJavaScriptCode(originalCode, blockLabel) {
  // startBlockExecution always returns true, but using the conditional operator here allows us to
  // call startBlockExecution, then execute the originalCode, then execute endBlockExecution, then
  // return the result from the original code.
  var code = 'startBlockExecution("' + blockLabel + '") ' +
      '? endBlockExecution(' + originalCode + ') : 0';
  return [code, Blockly.JavaScript.ORDER_CONDITIONAL];
}

function generateJavaScriptCode() {
  const disabled = disableOrphans();
  let jsFileContent = Blockly.JavaScript.workspaceToCode(workspace);
  const identifiersUsed = collectIdentifiersUsed();
  reenableOrphans(disabled);

  let comment = IDENTIFIERS_USED_PREFIX;
  let delimiter = '';
  for (var i = 0; i < identifiersUsed.length; i++) {
    comment += delimiter + identifiersUsed[i];
    delimiter = ',';
  }
  jsFileContent = comment + '\n\n' + jsFileContent;

  return jsFileContent;
}

function disableOrphans() {
  Blockly.Events.disable();
  var disabled = [];
  var blocks = workspace.getTopBlocks(true);
  for (var x = 0, block; block = blocks[x]; x++) {
    if (block.type != 'procedures_defnoreturn' &&
        block.type != 'procedures_defreturn' &&
        block.isEnabled()) {
      do {
        block.setEnabled(false);
        disabled.push(block);
        block = block.getNextBlock();
      } while (block);
    }
  }
  Blockly.Events.enable();
  return disabled;
}

function reenableOrphans(disabled) {
  Blockly.Events.disable();
  for (var x = 0, block; block = disabled[x]; x++) {
    block.setEnabled(true);
  }
  Blockly.Events.enable();
}

function collectIdentifiersUsed() {
  const identifiersUsed = new Set();
  const allBlocks = workspace.getAllBlocks();
  for (let iBlock = 0, block; block = allBlocks[iBlock]; iBlock++) {
    if (block.isEnabled() && !block.getInheritedDisabled()) {
      for (var iFieldName = 0; iFieldName < identifierFieldNames.length; iFieldName++) {
        const identifierFieldName = identifierFieldNames[iFieldName];
        const field = block.getField(identifierFieldName);
        if (field) {
          const identifier = field.getValue();
          identifiersUsed.add(identifier);
        }
      }
    }
  }
  return Array.from(identifiersUsed).sort(function(id1, id2) {
    return id1.localeCompare(id2);
  });
}

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
 * @fileoverview FTC robot blocks related to the goBILDA® Pinpoint Odometry Computer.
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// createGoBildaPinpointDropdown
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor


// Enums

Blockly.Blocks['goBildaPinpoint_typedEnum_deviceStatus'] = {
  init: function() {
    var DEVICE_STATUS_CHOICES = [
      ['NOT_READY', 'NOT_READY'],
      ['READY', 'READY'],
      ['CALIBRATING', 'CALIBRATING'],
      ['FAULT_X_POD_NOT_DETECTED', 'FAULT_X_POD_NOT_DETECTED'],
      ['FAULT_Y_POD_NOT_DETECTED', 'FAULT_Y_POD_NOT_DETECTED'],
      ['FAULT_NO_PODS_DETECTED', 'FAULT_NO_PODS_DETECTED'],
      ['FAULT_IMU_RUNAWAY', 'FAULT_IMU_RUNAWAY'],
      ['FAULT_BAD_READ', 'FAULT_BAD_READ'],
    ];
    this.setOutput(true, 'GoBildaPinpointDriver.DeviceStatus');
    this.appendDummyInput()
        .appendField(createNonEditableField('DeviceStatus'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(DEVICE_STATUS_CHOICES), 'DEVICE_STATUS');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
      ['NOT_READY', 'The device is currently powering up and has not initialized yet. RED LED'],
      ['READY', 'The device is currently functioning as normal. GREEN LED'],
      ['CALIBRATING', 'The device is currently recalibrating the gyro. RED LED'],
      ['FAULT_X_POD_NOT_DETECTED', 'The device does not detect an X pod plugged in. BLUE LED'],
      ['FAULT_Y_POD_NOT_DETECTED', 'The device does not detect a Y pod plugged in. ORANGE LED'],
      ['FAULT_NO_PODS_DETECTED', 'The device does not detect any pods plugged in. PURPLE LED'],
      ['FAULT_IMU_RUNAWAY', 'The device detects a bad IMU calibration.'],
      ['FAULT_BAD_READ', 'The Java code has detected a bad I²C read, the result reported is a ' +
          'duplicate of the last good read.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('DEVICE_STATUS');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['goBildaPinpoint_typedEnum_deviceStatus'] = function(block) {
  var code = '"' + block.getFieldValue('DEVICE_STATUS') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['goBildaPinpoint_typedEnum_deviceStatus'] = function(block) {
  var code = 'GoBildaPinpointDriver.DeviceStatus.' + block.getFieldValue('DEVICE_STATUS');
  Blockly.FtcJava.generateImport_('GoBildaPinpointDriver');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['goBildaPinpoint_typedEnum_encoderDirection'] = {
  init: function() {
    var ENCODER_DIRECTION_CHOICES = [
        ['FORWARD', 'FORWARD'],
        ['REVERSED', 'REVERSED'],
    ];
    this.setOutput(true, 'GoBildaPinpointDriver.EncoderDirection');
    this.appendDummyInput()
        .appendField(createNonEditableField('EncoderDirection'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(ENCODER_DIRECTION_CHOICES), 'ENCODER_DIRECTION');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['FORWARD', 'The GoBildaPinpointDriver.EncoderDirection value FORWARD'],
        ['REVERSED', 'The GoBildaPinpointDriver.EncoderDirection value REVERSED'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('ENCODER_DIRECTION');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['goBildaPinpoint_typedEnum_encoderDirection'] = function(block) {
  var code = '"' + block.getFieldValue('ENCODER_DIRECTION') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['goBildaPinpoint_typedEnum_encoderDirection'] = function(block) {
  var code = 'GoBildaPinpointDriver.EncoderDirection.' + block.getFieldValue('ENCODER_DIRECTION');
  Blockly.FtcJava.generateImport_('GoBildaPinpointDriver');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['goBildaPinpoint_typedEnum_goBildaOdometryPods'] = {
  init: function() {
    var ODOMETRY_PODS_CHOICES = [
        ['goBILDA_SWINGARM_POD', 'goBILDA_SWINGARM_POD'],
        ['goBILDA_4_BAR_POD', 'goBILDA_4_BAR_POD'],
    ];
    this.setOutput(true, 'GoBildaPinpointDriver.GoBildaOdometryPods');
    this.appendDummyInput()
        .appendField(createNonEditableField('GoBildaOdometryPods'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(ODOMETRY_PODS_CHOICES), 'ODOMETRY_PODS');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['goBILDA_SWINGARM_POD', 'The GoBildaPinpointDriver.GoBildaOdometryPods value goBILDA_SWINGARM_POD'],
        ['goBILDA_4_BAR_POD', 'The GoBildaPinpointDriver.GoBildaOdometryPods value goBILDA_4_BAR_POD'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('ODOMETRY_PODS');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['goBildaPinpoint_typedEnum_goBildaOdometryPods'] = function(block) {
  var code = '"' + block.getFieldValue('ODOMETRY_PODS') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['goBildaPinpoint_typedEnum_goBildaOdometryPods'] = function(block) {
  var code = 'GoBildaPinpointDriver.GoBildaOdometryPods.' + block.getFieldValue('ODOMETRY_PODS');
  Blockly.FtcJava.generateImport_('GoBildaPinpointDriver');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['goBildaPinpoint_typedEnum_readData'] = {
  init: function() {
    var READ_DATA_CHOICES = [
        ['ONLY_UPDATE_HEADING', 'ONLY_UPDATE_HEADING'],
    ];
    this.setOutput(true, 'GoBildaPinpointDriver.ReadData');
    this.appendDummyInput()
        .appendField(createNonEditableField('ReadData'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(READ_DATA_CHOICES), 'READ_DATA');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['ONLY_UPDATE_HEADING', 'The GoBildaPinpointDriver.ReadData value ONLY_UPDATE_HEADING'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('READ_DATA');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['goBildaPinpoint_typedEnum_readData'] = function(block) {
  var code = '"' + block.getFieldValue('READ_DATA') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['goBildaPinpoint_typedEnum_readData'] = function(block) {
  var code = 'GoBildaPinpointDriver.ReadData.' + block.getFieldValue('READ_DATA');
  Blockly.FtcJava.generateImport_('GoBildaPinpointDriver');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

// Properties

Blockly.Blocks['goBildaPinpoint_setProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['YawScalar', 'YawScalar'],
    ];
    this.appendValueInput('VALUE').setCheck('Number')
        .appendField('set')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP')
        .appendField('to');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['YawScalar', 'Sets the scalar for the robot\'s heading. ' +
            'Tuning this value should be unnecessary. ' +
            'The goBILDA Odometry Computer has a per-device tuned yaw offset already applied ' +
            'when you receive it. ' +
            'This is a scalar that is applied to the gyro\'s yaw value. Increasing it will mean ' +
            'it will report more than one degree for every degree the sensor fusion algorithm ' +
            'measures. ' +
            'You can tune this variable by rotating the robot a large amount (10 full turns is a ' +
            'good starting place) and comparing the amount that the robot rotated to the amount ' +
            'measured. ' +
            'Rotating the robot exactly 10 times should measure 3600°. If it measures more or ' +
            'less, divide moved amount by the measured amount and apply that value to the Yaw ' +
            'Offset. ' +
            'If you find that to get an accurate heading number you need to apply a scalar of ' +
            'more than 1.05, or less than 0.95, your device may be bad. Please reach out to ' +
            'tech@gobilda.com'],
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
    this.getFtcJavaInputType = function(inputName) {
      if (inputName == 'VALUE') {
        var property = thisBlock.getFieldValue('PROP');
        switch (property) {
          case 'YawScalar':
            return 'double';
          default:
            throw 'Unexpected property ' + property + ' (goBildaPinpoint_setProperty_Number).';
        }
      }
      return '';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_setProperty_Number'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.set' + property + '(' + value + ');\n';
};

Blockly.FtcJava['goBildaPinpoint_setProperty_Number'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var property = block.getFieldValue('PROP');
  var value = Blockly.FtcJava.valueToCode(
      block, 'VALUE', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.set' + property + '(' + value + ');\n';
};

Blockly.Blocks['goBildaPinpoint_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
      ['YawScalar', 'YawScalar'],
      ['DeviceID', 'DeviceID'],
      ['DeviceVersion', 'DeviceVersion'],
      ['LoopTime', 'LoopTime'],
      ['EncoderX', 'EncoderX'],
      ['EncoderY', 'EncoderY'],
      ['Frequency', 'Frequency'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['DeviceID', 'Returns the deviceID of the Odometry Computer. Should return 1.'],
        ['DeviceVersion', 'Returns the firmware version of the Odometry Computer.'],
        ['YawScalar', 'Returns the scalar that the IMU measured heading is multiplied by.'],
        ['LoopTime', 'Returns the Odometry Computer\'s most recent loop time. ' +
            'If values less than 500, or more than 1100 are commonly seen here, there may be ' +
            'something wrong with your device. Please reach out to tech@gobilda.com'],
        ['Frequency', 'Returns the Odometry Computer\'s most recent loop frequency. ' + 
            'If values less than 900, or more than 2000 are commonly seen here, there may be ' +
            'something wrong with your device. Please reach out to tech@gobilda.com'],
        ['EncoderX', 'Returns the raw value of the X (forward) encoder in ticks.'],
        ['EncoderY', 'Returns the raw value of the Y (strafe) encoder in ticks.'],
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
        case 'YawScalar':
          return 'float';
        case 'DeviceID':
        case 'DeviceVersion':
        case 'LoopTime':
        case 'EncoderX':
        case 'EncoderY':
          return 'int';
        case 'Frequency':
          return 'double';
        default:
          throw 'Unexpected property ' + property + ' (goBildaPinpoint_getProperty_Number).';
      }
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_getProperty_Number'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['goBildaPinpoint_getProperty_Number'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['goBildaPinpoint_getProperty_DeviceStatus'] = {
  init: function() {
    var PROPERTY_CHOICES = [
      ['DeviceStatus', 'DeviceStatus'],
    ];
    this.setOutput(true, 'GoBildaPinpointDriver.DeviceStatus');
    this.appendDummyInput()
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
      ['DeviceStatus', 'Returns the device status, indicating any faults the Odometry Computer may be experiencing.'],
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

Blockly.JavaScript['goBildaPinpoint_getProperty_DeviceStatus'] =
  Blockly.JavaScript['goBildaPinpoint_getProperty_Number'];

Blockly.FtcJava['goBildaPinpoint_getProperty_DeviceStatus'] =
  Blockly.FtcJava['goBildaPinpoint_getProperty_Number'];

Blockly.Blocks['goBildaPinpoint_getProperty_Pose2D'] = {
  init: function() {
    var PROPERTY_CHOICES = [
      ['Position', 'Position'],
    ];
    this.setOutput(true, 'Pose2D');
    this.appendDummyInput()
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
      ['Position', 'Returns a Pose2D object containing the estimated position of the robot.'],
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

Blockly.JavaScript['goBildaPinpoint_getProperty_Pose2D'] = Blockly.JavaScript['goBildaPinpoint_getProperty_Number'];

Blockly.FtcJava['goBildaPinpoint_getProperty_Pose2D'] = Blockly.FtcJava['goBildaPinpoint_getProperty_Number'];

// Functions

Blockly.Blocks['goBildaPinpoint_update'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('update'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Call this once per loop to read new data from the Odometry Computer. ' +
                    'Data will only update once this is called.');
  }
};

Blockly.JavaScript['goBildaPinpoint_update'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.update();\n';
};

Blockly.FtcJava['goBildaPinpoint_update'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  return identifier + '.update();\n';
};

Blockly.Blocks['goBildaPinpoint_update_withReadData'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('update'));
    this.appendValueInput('READ_DATA').setCheck('GoBildaPinpointDriver.ReadData')
        .appendField('readData')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Call this once per loop to read new data from the Odometry Computer. ' +
        'This update block allows a narrower range of data to be read from the device for faster ' +
        'read times.');
  }
};

Blockly.JavaScript['goBildaPinpoint_update_withReadData'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var readData = Blockly.JavaScript.valueToCode(
      block, 'READ_DATA', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.update_withReadData(' + readData + ');\n';
};

Blockly.FtcJava['goBildaPinpoint_update_withReadData'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var readData = Blockly.FtcJava.valueToCode(
      block, 'READ_DATA', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.update(' + readData + ');\n';
};

Blockly.Blocks['goBildaPinpoint_setOffsets'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setOffsets'));
    this.appendValueInput('X_OFFSET').setCheck('Number')
        .appendField('xOffset')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('Y_OFFSET').setCheck('Number')
        .appendField('yOffset')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Sets the odometry pod positions relative to the point that the odometry computer tracks ' +
        'around. The most common tracking position is the center of the robot. The X pod offset ' +
        'refers to how far sideways from the tracking point the X (forward) odometry pod is. ' +
        'Left of the center is a positive number, right of center is a negative number. The Y ' +
        'pod offset refers to how far forwards from the tracking point the Y (strafe) odometry ' +
        'pod is. Forward of center is a positive number, backwards is a negative number.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'X_OFFSET':
        case 'Y_OFFSET':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_setOffsets'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var xOffset = Blockly.JavaScript.valueToCode(
      block, 'X_OFFSET', Blockly.JavaScript.ORDER_COMMA);
  var yOffset = Blockly.JavaScript.valueToCode(
      block, 'Y_OFFSET', Blockly.JavaScript.ORDER_COMMA);
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setOffsets(' + xOffset + ', ' + yOffset + ', ' + distanceUnit + ');\n';
};

Blockly.FtcJava['goBildaPinpoint_setOffsets'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var xOffset = Blockly.FtcJava.valueToCode(
      block, 'X_OFFSET', Blockly.FtcJava.ORDER_COMMA);
  var yOffset = Blockly.FtcJava.valueToCode(
      block, 'Y_OFFSET', Blockly.FtcJava.ORDER_COMMA);
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setOffsets(' + xOffset + ', ' + yOffset + ', ' + distanceUnit + ');\n';
};

Blockly.Blocks['goBildaPinpoint_recalibrateIMU'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('recalibrateIMU'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Recalibrates the Odometry Computer\'s internal IMU. Robot MUST be stationary. Device ' +
        'takes a large number of samples, and uses those as the gyroscope zero-offset. This ' +
        'takes approximately 0.25 seconds.');
  }
};

Blockly.JavaScript['goBildaPinpoint_recalibrateIMU'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.recalibrateIMU();\n';
};

Blockly.FtcJava['goBildaPinpoint_recalibrateIMU'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  return identifier + '.recalibrateIMU();\n';
};

Blockly.Blocks['goBildaPinpoint_resetPosAndIMU'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('resetPosAndIMU'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Resets the current position to 0,0,0 and recalibrates the Odometry Computer\'s internal ' +
        'IMU. Robot MUST be stationary. Device takes a large number of samples, and uses those as ' +
        'the gyroscope zero-offset. This takes approximately 0.25 seconds.');
  }
};

Blockly.JavaScript['goBildaPinpoint_resetPosAndIMU'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.resetPosAndIMU();\n';
};

Blockly.FtcJava['goBildaPinpoint_resetPosAndIMU'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  return identifier + '.resetPosAndIMU();\n';
};

Blockly.Blocks['goBildaPinpoint_setEncoderDirections'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setEncoderDirections'));
    this.appendValueInput('X_ENCODER').setCheck('GoBildaPinpointDriver.EncoderDirection')
        .appendField('xEncoder')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('Y_ENCODER').setCheck('GoBildaPinpointDriver.EncoderDirection')
        .appendField('yEncoder')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the direction of each encoder.');
  }
};

Blockly.JavaScript['goBildaPinpoint_setEncoderDirections'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var xEncoder = Blockly.JavaScript.valueToCode(
      block, 'X_ENCODER', Blockly.JavaScript.ORDER_COMMA);
  var yEncoder = Blockly.JavaScript.valueToCode(
      block, 'Y_ENCODER', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setEncoderDirections(' + xEncoder + ', ' + yEncoder + ');\n';
};

Blockly.FtcJava['goBildaPinpoint_setEncoderDirections'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var xEncoder = Blockly.FtcJava.valueToCode(
      block, 'X_ENCODER', Blockly.FtcJava.ORDER_COMMA);
  var yEncoder = Blockly.FtcJava.valueToCode(
      block, 'Y_ENCODER', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setEncoderDirections(' + xEncoder + ', ' + yEncoder + ');\n';
};

Blockly.Blocks['goBildaPinpoint_setEncoderResolution_withPods'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setEncoderResolution'));
    this.appendValueInput('PODS').setCheck('GoBildaPinpointDriver.GoBildaOdometryPods')
        .appendField('pods')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Sets the encoder resolution by the type of GoBilda pod you are using. ' +
        'If you aren\'t using a GoBilda pod, use the setEncoderResolution block ' +
        'that takes ticksPerUnit instead.');
  }
};

Blockly.JavaScript['goBildaPinpoint_setEncoderResolution_withPods'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var pods = Blockly.JavaScript.valueToCode(
      block, 'PODS', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.setEncoderResolution_withPods(' + pods + ');\n';
};

Blockly.FtcJava['goBildaPinpoint_setEncoderResolution_withPods'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var pods = Blockly.FtcJava.valueToCode(
      block, 'PODS', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.setEncoderResolution(' + pods + ');\n';
};

Blockly.Blocks['goBildaPinpoint_setEncoderResolution_withTicks'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setEncoderResolution'));
    this.appendValueInput('TICKS_PER_UNIT').setCheck('Number')
        .appendField('ticksPerUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the encoder resolution.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'TICKS_PER_UNIT':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_setEncoderResolution_withTicks'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var ticksPerUnit = Blockly.JavaScript.valueToCode(
      block, 'TICKS_PER_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setEncoderResolution_withTicks(' + ticksPerUnit + ', ' + distanceUnit + ');\n';
};

Blockly.FtcJava['goBildaPinpoint_setEncoderResolution_withTicks'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var ticksPerUnit = Blockly.FtcJava.valueToCode(
      block, 'TICKS_PER_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setEncoderResolution(' + ticksPerUnit + ', ' + distanceUnit + ');\n';
};

Blockly.Blocks['goBildaPinpoint_setPosition'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setPosition'));
    this.appendValueInput('POSITION').setCheck('Pose2D')
        .appendField('position')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Sets the position that the Pinpoint should use to track your robot relative to. You ' +
        'can use this to update the estimated position of your robot with new external sensor ' +
        'data, or to run a robot in field coordinates. This overrides the current position.');
  }
};

Blockly.JavaScript['goBildaPinpoint_setPosition'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var position = Blockly.JavaScript.valueToCode(
      block, 'POSITION', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setPosition(' + position + ');\n';
};

Blockly.FtcJava['goBildaPinpoint_setPosition'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var position = Blockly.FtcJava.valueToCode(
      block, 'POSITION', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setPosition(' + position + ');\n';
};

Blockly.Blocks['goBildaPinpoint_setPosX'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setPosX'));
    this.appendValueInput('POS_X').setCheck('Number')
        .appendField('posX')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Sets the X position that the Pinpoint should use to track your robot relative to. You ' +
        'can use this to update the estimated position of your robot with new external sensor ' +
        'data, or to run a robot in field coordinates.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'POS_X':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_setPosX'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var posX = Blockly.JavaScript.valueToCode(
      block, 'POS_X', Blockly.JavaScript.ORDER_COMMA);
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setPosX(' + posX + ', ' + distanceUnit + ');\n';
};

Blockly.FtcJava['goBildaPinpoint_setPosX'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var posX = Blockly.FtcJava.valueToCode(
      block, 'POS_X', Blockly.FtcJava.ORDER_COMMA);
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setPosX(' + posX + ', ' + distanceUnit + ');\n';
};

Blockly.Blocks['goBildaPinpoint_setPosY'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setPosY'));
    this.appendValueInput('POS_Y').setCheck('Number')
        .appendField('posY')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Sets the Y position that the Pinpoint should use to track your robot relative to. You ' +
        'can use this to update the estimated position of your robot with new external sensor ' +
        'data, or to run a robot in field coordinates.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'POS_Y':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_setPosY'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var posY = Blockly.JavaScript.valueToCode(
      block, 'POS_Y', Blockly.JavaScript.ORDER_COMMA);
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setPosY(' + posY + ', ' + distanceUnit + ');\n';
};

Blockly.FtcJava['goBildaPinpoint_setPosY'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var posY = Blockly.FtcJava.valueToCode(
      block, 'POS_Y', Blockly.FtcJava.ORDER_COMMA);
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setPosY(' + posY + ', ' + distanceUnit + ');\n';
};

Blockly.Blocks['goBildaPinpoint_setHeading'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setHeading'));
    this.appendValueInput('HEADING').setCheck('Number')
        .appendField('heading')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ANGLE_UNIT').setCheck('AngleUnit')
        .appendField('angleUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip(
        'Sets the heading that the Pinpoint should use to track your robot relative to. You ' +
        'can use this to update the estimated position of your robot with new external sensor ' +
        'data, or to run a robot in field coordinates.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'HEADING':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_setHeading'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var heading = Blockly.JavaScript.valueToCode(
      block, 'HEADING', Blockly.JavaScript.ORDER_COMMA);
  var angleUnit = Blockly.JavaScript.valueToCode(
      block, 'ANGLE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setHeading(' + heading + ', ' + angleUnit + ');\n';
};

Blockly.FtcJava['goBildaPinpoint_setHeading'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var heading = Blockly.FtcJava.valueToCode(
      block, 'HEADING', Blockly.FtcJava.ORDER_COMMA);
  var angleUnit = Blockly.FtcJava.valueToCode(
      block, 'ANGLE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setHeading(' + heading + ', ' + angleUnit + ');\n';
};

Blockly.Blocks['goBildaPinpoint_getPosX'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getPosX'));
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Returns the estimated X (forward) position of the robot in the specified units.');
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_getPosX'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.getPosX(' + distanceUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['goBildaPinpoint_getPosX'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.getPosX(' + distanceUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['goBildaPinpoint_getPosY'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getPosY'));
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Returns the estimated Y (Strafe) position of the robot in the specified units.');
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_getPosY'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.getPosY(' + distanceUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['goBildaPinpoint_getPosY'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.getPosY(' + distanceUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['goBildaPinpoint_getHeading_withAngleUnit'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getHeading_withAngleUnit'));
    this.appendValueInput('ANGLE_UNIT').setCheck('AngleUnit')
        .appendField('angleUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Return the normalized estimated H (heading) position of the robot in the specified ' +
        'units. Normalized heading is wrapped from -180°, to 180°.');
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_getHeading_withAngleUnit'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var angleUnit = Blockly.JavaScript.valueToCode(
      block, 'ANGLE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.getHeading_withAngleUnit(' + angleUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['goBildaPinpoint_getHeading_withAngleUnit'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var angleUnit = Blockly.FtcJava.valueToCode(
      block, 'ANGLE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.getHeading(' + angleUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['goBildaPinpoint_getHeading_withUnnormalizedAngleUnit'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getHeading_withUnnormalizedAngleUnit'));
    this.appendValueInput('UNNORMALIZED_ANGLE_UNIT').setCheck('UnnormalizedAngleUnit')
        .appendField('unnormalizedAngleUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Returns the unnormalized estimated H (heading) position of the robot in the specified ' +
        'units. Unnormalized heading is not constrained from -180° to 180°. It will continue ' +
        'counting multiple rotations.');
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_getHeading_withUnnormalizedAngleUnit'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var unnormalizedAngleUnit = Blockly.JavaScript.valueToCode(
      block, 'UNNORMALIZED_ANGLE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.getHeading_withUnnormalizedAngleUnit(' + unnormalizedAngleUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['goBildaPinpoint_getHeading_withUnnormalizedAngleUnit'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var unnormalizedAngleUnit = Blockly.FtcJava.valueToCode(
      block, 'UNNORMALIZED_ANGLE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.getHeading(' + unnormalizedAngleUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['goBildaPinpoint_getVelX'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getVelX'));
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Return the estimated X (forward) velocity of the robot in the specified units/sec.');
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_getVelX'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.getVelX(' + distanceUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['goBildaPinpoint_getVelX'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.getVelX(' + distanceUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['goBildaPinpoint_getVelY'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getVelY'));
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Return the estimated Y (strafe) velocity of the robot in the specified units/sec.');
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_getVelY'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.getVelY(' + distanceUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['goBildaPinpoint_getVelY'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.getVelY(' + distanceUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['goBildaPinpoint_getHeadingVelocity_withUnnormalizedAngleUnit'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getHeadingVelocity_withUnnormalizedAngleUnit'));
    this.appendValueInput('UNNORMALIZED_ANGLE_UNIT').setCheck('UnnormalizedAngleUnit')
        .appendField('unnormalizedAngleUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Returns the estimated H (heading) velocity of the robot in the specified units/sec');
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_getHeadingVelocity_withUnnormalizedAngleUnit'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var unnormalizedAngleUnit = Blockly.JavaScript.valueToCode(
      block, 'UNNORMALIZED_ANGLE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.getHeadingVelocity_withUnnormalizedAngleUnit(' + unnormalizedAngleUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['goBildaPinpoint_getHeadingVelocity_withUnnormalizedAngleUnit'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var unnormalizedAngleUnit = Blockly.FtcJava.valueToCode(
      block, 'UNNORMALIZED_ANGLE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.getHeadingVelocity(' + unnormalizedAngleUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['goBildaPinpoint_getXOffset'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getXOffset'));
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Return the user-set offset for the X (forward) pod in the specified units. This uses ' +
        'its own I2C read, avoid calling this every loop.');
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_getXOffset'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.getXOffset(' + distanceUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['goBildaPinpoint_getXOffset'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.getXOffset(' + distanceUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['goBildaPinpoint_getYOffset'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGoBildaPinpointDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getYOffset'));
    this.appendValueInput('DISTANCE_UNIT').setCheck('DistanceUnit')
        .appendField('distanceUnit')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip(
        'Return the user-set offset for the Y (strafe) pod in the specified units. This uses ' +
        'its own I2C read, avoid calling this every loop.');
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['goBildaPinpoint_getYOffset'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var distanceUnit = Blockly.JavaScript.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.getYOffset(' + distanceUnit + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['goBildaPinpoint_getYOffset'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'GoBildaPinpointDriver');
  var distanceUnit = Blockly.FtcJava.valueToCode(
      block, 'DISTANCE_UNIT', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.getYOffset(' + distanceUnit + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

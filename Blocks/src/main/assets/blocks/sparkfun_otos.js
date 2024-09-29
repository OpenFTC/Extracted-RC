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
 * @fileoverview FTC robot blocks related to the SparkFun Qwiic Optical Tracking
 * Odometry Sensor (OTOS).
 * @author Liz Looney
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// createSparkFunOTOSDropdown
// getSparkFunOTOSConstant
// The following are defined in vars.js:
// createNonEditableField
// functionColor
// getPropertyColor
// setPropertyColor
// wrapJavaScriptCode

Blockly.Blocks['sparkFunOTOS_constant_Number'] = {
  init: function() {
    var CONSTANT_CHOICES = [
        ['MIN_SCALAR', 'MIN_SCALAR'],
        ['MAX_SCALAR', 'MAX_SCALAR'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('SparkFunOTOS'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(CONSTANT_CHOICES), 'CONSTANT');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['MIN_SCALAR', 'The minimum scalar value for the linear and angular scalars.'],
        ['MAX_SCALAR', 'The maximum scalar value for the linear and angular scalars.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('CONSTANT');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
    this.getFtcJavaOutputType = function() {
      var constant = thisBlock.getFieldValue('CONSTANT');
      switch (constant) {
        case 'MIN_SCALAR':
        case 'MAX_SCALAR':
          return 'double';
      }
    };
  }
};

Blockly.JavaScript['sparkFunOTOS_constant_Number'] = function(block) {
  var constant = block.getFieldValue('CONSTANT');
  var code = getSparkFunOTOSConstant(constant);
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['sparkFunOTOS_constant_Number'] = function(block) {
  var constant = block.getFieldValue('CONSTANT');
  var code = 'SparkFunOTOS.' + constant;
  Blockly.FtcJava.generateImport_('SparkFunOTOS');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

sparkFunOTOS_getProperty_JavaScript = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

sparkFunOTOS_getProperty_JSON_JavaScript = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = 'JSON.parse(' + identifier + '.get' + property + '())';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

sparkFunOTOS_getProperty_FtcJava = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunOTOS');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

sparkFunOTOS_setProperty_JavaScript = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.set' + property + '(' + value + ');\n';
};

sparkFunOTOS_setProperty_JSON_JavaScript = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.set' + property + '(JSON.stringify(' + value + '));\n';
};

sparkFunOTOS_setProperty_FtcJava = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunOTOS');
  var property = block.getFieldValue('PROP');
  var value = Blockly.FtcJava.valueToCode(
      block, 'VALUE', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.set' + property + '(' + value + ');\n';
};


Blockly.Blocks['sparkFunOTOS_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['ImuCalibrationProgress', 'ImuCalibrationProgress'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
      ['ImuCalibrationProgress', 'Gets the progress of the IMU calibration. Used for asynchronous ' +
          'calibration with calibrateImu. Returns the number of samples remaining for calibration.'],
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
        case 'ImuCalibrationProgress':
          return 'int';
        default:
          throw 'Unexpected property ' + property + ' (sparkFunOTOS_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['sparkFunOTOS_getProperty_Number'] = sparkFunOTOS_getProperty_JavaScript;
Blockly.FtcJava['sparkFunOTOS_getProperty_Number'] = sparkFunOTOS_getProperty_FtcJava;

Blockly.Blocks['sparkFunOTOS_getProperty_Number_ReturnBoolean'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['LinearScalar', 'LinearScalar'],
        ['AngularScalar', 'AngularScalar'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['LinearScalar', 'Gets the linear scalar used by the OTOS. The linear scalar is used to ' +
            'compensate for scaling issues with the sensor measurements.'],
        ['AngularScalar', 'Gets the angular scalar used by the OTOS. The angular scalar is used to ' +
            'compensate for scaling issues with the sensor measurements.'],
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
        case 'LinearScalar':
        case 'AngularScalar':
          return 'double';
        default:
          throw 'Unexpected property ' + property + ' (sparkFunOTOS_getProperty_Number_ReturnBoolean getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['sparkFunOTOS_getProperty_Number_ReturnBoolean'] = sparkFunOTOS_getProperty_JavaScript;
Blockly.FtcJava['sparkFunOTOS_getProperty_Number_ReturnBoolean'] = sparkFunOTOS_getProperty_FtcJava;

Blockly.Blocks['sparkFunOTOS_setProperty_Number_ReturnBoolean'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['LinearScalar', 'LinearScalar'],
        ['AngularScalar', 'AngularScalar'],
    ];
    this.appendValueInput('VALUE').setCheck('Number')
        .appendField('set')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP')
        .appendField('to');
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['LinearScalar', 'Sets the linear scalar used by the OTOS. Can be used to ' +
         'compensate for scaling issues with the sensor measurements. ' +
         'The value must be between 0.872 and 1.127.'],
        ['AngularScalar', 'Sets the angular scalar used by the OTOS. Can be used to ' +
         'compensate for scaling issues with the sensor measurements. ' +
         'The value must be between 0.872 and 1.127.'],
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
          case 'LinearScalar':
          case 'AngularScalar':
            return 'double';
          default:
            throw 'Unexpected property ' + property + ' (sparkFunOTOS_setProperty_Number_ReturnBoolean getArgumentType).';
        }
      }
      return '';
    };
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['sparkFunOTOS_setProperty_Number_ReturnBoolean'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_NONE);
  var code = identifier + '.set' + property + '(' + value + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['sparkFunOTOS_setProperty_Number_ReturnBoolean'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunOTOS');
  var property = block.getFieldValue('PROP');
  var value = Blockly.FtcJava.valueToCode(
      block, 'VALUE', Blockly.FtcJava.ORDER_NONE);
  var code = identifier + '.set' + property + '(' + value + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['sparkFunOTOS_getProperty_DistanceUnit'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['LinearUnit', 'LinearUnit'],
    ];
    this.setOutput(true, 'DistanceUnit');
    this.appendDummyInput()
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['LinearUnit', 'Gets the linear unit used by all methods using a pose.'],
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

Blockly.JavaScript['sparkFunOTOS_getProperty_DistanceUnit'] = sparkFunOTOS_getProperty_JavaScript;
Blockly.FtcJava['sparkFunOTOS_getProperty_DistanceUnit'] = sparkFunOTOS_getProperty_FtcJava;

Blockly.Blocks['sparkFunOTOS_setProperty_DistanceUnit'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['LinearUnit', 'LinearUnit'],
    ];
    this.appendValueInput('VALUE').setCheck('DistanceUnit')
        .appendField('set')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP')
        .appendField('to');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['LinearUnit', 'Sets the linear unit used by all methods using a pose.'],
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

Blockly.JavaScript['sparkFunOTOS_setProperty_DistanceUnit'] = sparkFunOTOS_setProperty_JavaScript;
Blockly.FtcJava['sparkFunOTOS_setProperty_DistanceUnit'] = sparkFunOTOS_setProperty_FtcJava;

Blockly.Blocks['sparkFunOTOS_getProperty_AngleUnit'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['AngularUnit', 'AngularUnit'],
    ];
    this.setOutput(true, 'AngleUnit');
    this.appendDummyInput()
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['AngularUnit', 'Gets the angular unit used by all methods using a pose.'],
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

Blockly.JavaScript['sparkFunOTOS_getProperty_AngleUnit'] = sparkFunOTOS_getProperty_JavaScript;
Blockly.FtcJava['sparkFunOTOS_getProperty_AngleUnit'] = sparkFunOTOS_getProperty_FtcJava;

Blockly.Blocks['sparkFunOTOS_setProperty_AngleUnit'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['AngularUnit', 'AngularUnit'],
    ];
    this.appendValueInput('VALUE').setCheck('AngleUnit')
        .appendField('set')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP')
        .appendField('to');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['AngularUnit', 'Sets the angular unit used by all methods using a pose.'],
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

Blockly.JavaScript['sparkFunOTOS_setProperty_AngleUnit'] = sparkFunOTOS_setProperty_JavaScript;
Blockly.FtcJava['sparkFunOTOS_setProperty_AngleUnit'] = sparkFunOTOS_setProperty_FtcJava;

Blockly.Blocks['sparkFunOTOS_getProperty_Status'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Status', 'Status'],
    ];
    this.setOutput(true, 'SparkFunOTOS.Status');
    this.appendDummyInput()
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Status', 'Gets the status register from the OTOS, which includes warnings and errors reported by the sensor.'],
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

Blockly.JavaScript['sparkFunOTOS_getProperty_Status'] = sparkFunOTOS_getProperty_JSON_JavaScript;

Blockly.FtcJava['sparkFunOTOS_getProperty_Status'] = sparkFunOTOS_getProperty_FtcJava;

Blockly.Blocks['sparkFunOTOS_getProperty_Pose2D'] = {
  init: function() {
    var PROPERTY_CHOICES = [
      ['Offset', 'Offset'],
      ['Position', 'Position'],
      ['Acceleration', 'Acceleration'],
      ['Velocity', 'Velocity'],
      ['PositionStdDev', 'PositionStdDev'],
      ['AccelerationStdDev', 'AccelerationStdDev'],
      ['VelocityStdDev', 'VelocityStdDev'],
    ];
    this.setOutput(true, 'SparkFunOTOS.Pose2D');
    this.appendDummyInput()
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
      ['Offset', 'Gets the offset of the OTOS relative to the center of the robot.'],
      ['Position', 'Gets the position measured by the OTOS.'],
      ['Acceleration', 'Gets the acceleration measured by the OTOS.'],
      ['Velocity', 'Gets the velocity measured by the OTOS.'],
      ['PositionStdDev', 'Gets the standard deviation of the measured position. ' +
       'These values are just the square root of the diagonal elements ' +
       'of the covariance matrices of the Kalman filters used in the firmware, so ' +
       'they are just statistical quantities and do not represent actual error!'],
      ['AccelerationStdDev', 'Gets the standard deviation of the measured acceleration. ' +
       'These values are just the square root of the diagonal elements ' +
       'of the covariance matrices of the Kalman filters used in the firmware, so ' +
       'they are just statistical quantities and do not represent actual error!'],
      ['VelocityStdDev', 'Gets the standard deviation of the measured velocity. ' +
       'These values are just the square root of the diagonal elements ' +
       'of the covariance matrices of the Kalman filters used in the firmware, so ' +
       'they are just statistical quantities and do not represent actual error!'],
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

Blockly.JavaScript['sparkFunOTOS_getProperty_Pose2D'] = sparkFunOTOS_getProperty_JSON_JavaScript;
Blockly.FtcJava['sparkFunOTOS_getProperty_Pose2D'] = sparkFunOTOS_getProperty_FtcJava;

Blockly.Blocks['sparkFunOTOS_setProperty_Pose2D'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['Offset', 'Offset'],
        ['Position', 'Position'],
    ];
    this.appendValueInput('VALUE').setCheck('SparkFunOTOS.Pose2D')
        .appendField('set')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP')
        .appendField('to');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['Offset', 'Sets the offset of the OTOS. This is useful if your sensor is ' +
            'mounted off-center from a robot. Rather than returning the position of ' +
            'the sensor, the OTOS will return the position of the robot.'],
        ['Position', 'Sets the position measured by the OTOS. This is useful if your ' +
            'robot does not start at the origin, or you have another source of ' +
            'location information (eg. vision odometry); the OTOS will continue ' +
            'tracking from this position.'],
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

Blockly.JavaScript['sparkFunOTOS_setProperty_Pose2D'] = sparkFunOTOS_setProperty_JSON_JavaScript;
Blockly.FtcJava['sparkFunOTOS_setProperty_Pose2D'] = sparkFunOTOS_setProperty_FtcJava;

Blockly.Blocks['sparkFunOTOS_getProperty_SignalProcessConfig'] = {
  init: function() {
    var PROPERTY_CHOICES = [
      ['SignalProcessConfig', 'SignalProcessConfig'],
    ];
    this.setOutput(true, 'SparkFunOTOS.SignalProcessConfig');
    this.appendDummyInput()
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createPropertyDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
      ['SignalProcessConfig', 'Gets the signal processing configuration from the OTOS'],
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

Blockly.JavaScript['sparkFunOTOS_getProperty_SignalProcessConfig'] = sparkFunOTOS_getProperty_JSON_JavaScript;
Blockly.FtcJava['sparkFunOTOS_getProperty_SignalProcessConfig'] = sparkFunOTOS_getProperty_FtcJava;

Blockly.Blocks['sparkFunOTOS_setProperty_SignalProcessConfig'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['SignalProcessConfig', 'SignalProcessConfig'],
    ];
    this.appendValueInput('VALUE').setCheck('SparkFunOTOS.SignalProcessConfig')
        .appendField('set')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP')
        .appendField('to');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['SignalProcessConfig', 'Sets the signal processing configuration on the OTOS. This is ' +
            'primarily useful for creating and testing a new lookup table calibration.'],
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

Blockly.JavaScript['sparkFunOTOS_setProperty_SignalProcessConfig'] = sparkFunOTOS_setProperty_JSON_JavaScript;
Blockly.FtcJava['sparkFunOTOS_setProperty_SignalProcessConfig'] = sparkFunOTOS_setProperty_FtcJava;

Blockly.Blocks['sparkFunOTOS_getPosVelAcc'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getPosVelAcc'));
    this.appendValueInput('POS').setCheck('SparkFunOTOS.Pose2D')
        .appendField('pos')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VEL').setCheck('SparkFunOTOS.Pose2D')
        .appendField('vel')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ACC').setCheck('SparkFunOTOS.Pose2D')
        .appendField('acc')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Gets the position, velocity, and acceleration measured by the ' +
                    'OTOS in a single burst read.');
  }
};

Blockly.JavaScript['sparkFunOTOS_getPosVelAcc'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');

  // The blocks plugged into POS, VEL, and ACC must be variables_get blocks.
  var blockLabel = 'call ' + identifier + '.getPosVelAcc';
  var posBlock = block.getInputTargetBlock('POS');
  if (!posBlock || posBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the pos socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  var velBlock = block.getInputTargetBlock('VEL');
  if (!velBlock || velBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the vel socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  var accBlock = block.getInputTargetBlock('ACC');
  if (!accBlock || accBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the acc socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  
  var pos = Blockly.JavaScript.valueToCode(
    block, 'POS', Blockly.JavaScript.ORDER_MEMBER);
  var vel = Blockly.JavaScript.valueToCode(
    block, 'VEL', Blockly.JavaScript.ORDER_MEMBER);
  var acc = Blockly.JavaScript.valueToCode(
    block, 'ACC', Blockly.JavaScript.ORDER_MEMBER);
  var listVar = Blockly.JavaScript.variableDB_.getDistinctName(
    'tmpList', Blockly.Variables.NAME_TYPE);
  return '{\n' +
      // Send stringified values of the args so they can be validated as legal SparkFunOTOS.Pose2D objects.
      '  var ' + listVar + ' = JSON.parse(' + identifier + '.getPosVelAcc(\n' +
      '      JSON.stringify(' + pos + '), JSON.stringify(' + vel + '), JSON.stringify(' + acc + ')));\n' +
      '  ' + pos + '.x = ' + listVar + '[0].x;\n' +
      '  ' + pos + '.y = ' + listVar + '[0].y;\n' +
      '  ' + pos + '.h = ' + listVar + '[0].h;\n' +
      '  ' + vel + '.x = ' + listVar + '[1].x;\n' +
      '  ' + vel + '.y = ' + listVar + '[1].y;\n' +
      '  ' + vel + '.h = ' + listVar + '[1].h;\n' +
      '  ' + acc + '.x = ' + listVar + '[2].x;\n' +
      '  ' + acc + '.y = ' + listVar + '[2].y;\n' +
      '  ' + acc + '.h = ' + listVar + '[2].h;\n' +
      '}\n';
};

Blockly.FtcJava['sparkFunOTOS_getPosVelAcc'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunOTOS');
  var pos = Blockly.FtcJava.valueToCode(
      block, 'POS', Blockly.FtcJava.ORDER_COMMA);
  var vel = Blockly.FtcJava.valueToCode(
      block, 'VEL', Blockly.FtcJava.ORDER_COMMA);
  var acc = Blockly.FtcJava.valueToCode(
      block, 'ACC', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.getPosVelAcc(' + pos + ', ' + vel + ', ' + acc + ');\n';
};

Blockly.Blocks['sparkFunOTOS_getPosVelAccStdDev'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getPosVelAccStdDev'));
    this.appendValueInput('POS_STD_DEV').setCheck('SparkFunOTOS.Pose2D')
        .appendField('posStdDev')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VEL_STD_DEV').setCheck('SparkFunOTOS.Pose2D')
        .appendField('velStdDev')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ACC_STD_DEV').setCheck('SparkFunOTOS.Pose2D')
        .appendField('accStdDev')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Gets the standard deviation of the measured position, velocity, ' +
                    'and acceleration in a single burst read.');
  }
};

Blockly.JavaScript['sparkFunOTOS_getPosVelAccStdDev'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');

  // The blocks plugged into POS_STD_DEV, VEL_STD_DEV, and ACC_STD_DEV must be variables_get blocks.
  var blockLabel = 'call ' + identifier + '.getPosVelAccStdDev';
  var posStdDevBlock = block.getInputTargetBlock('POS_STD_DEV');
  if (!posStdDevBlock || posStdDevBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the posStdDev socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  var velStdDevBlock = block.getInputTargetBlock('VEL_STD_DEV');
  if (!velStdDevBlock || velStdDevBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the velStdDev socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  var accStdDevBlock = block.getInputTargetBlock('ACC_STD_DEV');
  if (!accStdDevBlock || accStdDevBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the accStdDev socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  
  var posStdDev = Blockly.JavaScript.valueToCode(
    block, 'POS_STD_DEV', Blockly.JavaScript.ORDER_MEMBER);
  var velStdDev = Blockly.JavaScript.valueToCode(
    block, 'VEL_STD_DEV', Blockly.JavaScript.ORDER_MEMBER);
  var accStdDev = Blockly.JavaScript.valueToCode(
    block, 'ACC_STD_DEV', Blockly.JavaScript.ORDER_MEMBER);
  var listVar = Blockly.JavaScript.variableDB_.getDistinctName(
    'tmpList', Blockly.Variables.NAME_TYPE);
  return '{\n' +
      // Send stringified values of the args so they can be validated as legal SparkFunOTOS.Pose2D objects.
      '  var ' + listVar + ' = JSON.parse(' + identifier + '.getPosVelAccStdDev(\n' +
      '      JSON.stringify(' + posStdDev + '), JSON.stringify(' + velStdDev + '), JSON.stringify(' + accStdDev + ')));\n' +
      '  ' + posStdDev + '.x = ' + listVar + '[0].x;\n' +
      '  ' + posStdDev + '.y = ' + listVar + '[0].y;\n' +
      '  ' + posStdDev + '.h = ' + listVar + '[0].h;\n' +
      '  ' + velStdDev + '.x = ' + listVar + '[1].x;\n' +
      '  ' + velStdDev + '.y = ' + listVar + '[1].y;\n' +
      '  ' + velStdDev + '.h = ' + listVar + '[1].h;\n' +
      '  ' + accStdDev + '.x = ' + listVar + '[2].x;\n' +
      '  ' + accStdDev + '.y = ' + listVar + '[2].y;\n' +
      '  ' + accStdDev + '.h = ' + listVar + '[2].h;\n' +
      '}\n';
};

Blockly.FtcJava['sparkFunOTOS_getPosVelAccStdDev'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunOTOS');
  var posStdDev = Blockly.FtcJava.valueToCode(
      block, 'POS_STD_DEV', Blockly.FtcJava.ORDER_COMMA);
  var velStdDev = Blockly.FtcJava.valueToCode(
      block, 'VEL_STD_DEV', Blockly.FtcJava.ORDER_COMMA);
  var accStdDev = Blockly.FtcJava.valueToCode(
      block, 'ACC_STD_DEV', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.getPosVelAccStdDev(' + posStdDev + ', ' + velStdDev + ', ' + accStdDev + ');\n';
};

Blockly.Blocks['sparkFunOTOS_getPosVelAccAndStdDev'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getPosVelAccAndStdDev'));
    this.appendValueInput('POS').setCheck('SparkFunOTOS.Pose2D')
        .appendField('pos')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VEL').setCheck('SparkFunOTOS.Pose2D')
        .appendField('vel')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ACC').setCheck('SparkFunOTOS.Pose2D')
        .appendField('acc')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('POS_STD_DEV').setCheck('SparkFunOTOS.Pose2D')
        .appendField('posStdDev')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VEL_STD_DEV').setCheck('SparkFunOTOS.Pose2D')
        .appendField('velStdDev')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('ACC_STD_DEV').setCheck('SparkFunOTOS.Pose2D')
        .appendField('accStdDev')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Gets the position, velocity, acceleration, and standard deviation ' +
                    'of each in a single burst read.');
  }
};

Blockly.JavaScript['sparkFunOTOS_getPosVelAccAndStdDev'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');

  // The blocks plugged into POS, VEL, ACC, POS_STD_DEV, VEL_STD_DEV, and ACC_STD_DEV must be variables_get blocks.
  var blockLabel = 'call ' + identifier + '.getPosVelAccAndStdDev';
  var posBlock = block.getInputTargetBlock('POS');
  if (!posBlock || posBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the pos socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  var velBlock = block.getInputTargetBlock('VEL');
  if (!velBlock || velBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the vel socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  var accBlock = block.getInputTargetBlock('ACC');
  if (!accBlock || accBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the acc socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  var posStdDevBlock = block.getInputTargetBlock('POS_STD_DEV');
  if (!posStdDevBlock || posStdDevBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the posStdDev socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  var velStdDevBlock = block.getInputTargetBlock('VEL_STD_DEV');
  if (!velStdDevBlock || velStdDevBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the velStdDev socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  var accStdDevBlock = block.getInputTargetBlock('ACC_STD_DEV');
  if (!accStdDevBlock || accStdDevBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the accStdDev socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  
  var pos = Blockly.JavaScript.valueToCode(
    block, 'POS', Blockly.JavaScript.ORDER_MEMBER);
  var vel = Blockly.JavaScript.valueToCode(
    block, 'VEL', Blockly.JavaScript.ORDER_MEMBER);
  var acc = Blockly.JavaScript.valueToCode(
    block, 'ACC', Blockly.JavaScript.ORDER_MEMBER);
  var posStdDev = Blockly.JavaScript.valueToCode(
    block, 'POS_STD_DEV', Blockly.JavaScript.ORDER_MEMBER);
  var velStdDev = Blockly.JavaScript.valueToCode(
    block, 'VEL_STD_DEV', Blockly.JavaScript.ORDER_MEMBER);
  var accStdDev = Blockly.JavaScript.valueToCode(
    block, 'ACC_STD_DEV', Blockly.JavaScript.ORDER_MEMBER);
  var listVar = Blockly.JavaScript.variableDB_.getDistinctName(
    'tmpList', Blockly.Variables.NAME_TYPE);
  return '{\n' +
      // Send stringified values of the args so they can be validated as legal SparkFunOTOS.Pose2D objects.
      '  var ' + listVar + ' = JSON.parse(' + identifier + '.getPosVelAccAndStdDev(\n' +
      '      JSON.stringify(' + pos + '), JSON.stringify(' + vel + '), JSON.stringify(' + acc + '),\n' +
      '      JSON.stringify(' + posStdDev + '), JSON.stringify(' + velStdDev + '), JSON.stringify(' + accStdDev + ')));\n' +
      '  ' + pos + '.x = ' + listVar + '[0].x;\n' +
      '  ' + pos + '.y = ' + listVar + '[0].y;\n' +
      '  ' + pos + '.h = ' + listVar + '[0].h;\n' +
      '  ' + vel + '.x = ' + listVar + '[1].x;\n' +
      '  ' + vel + '.y = ' + listVar + '[1].y;\n' +
      '  ' + vel + '.h = ' + listVar + '[1].h;\n' +
      '  ' + acc + '.x = ' + listVar + '[2].x;\n' +
      '  ' + acc + '.y = ' + listVar + '[2].y;\n' +
      '  ' + acc + '.h = ' + listVar + '[2].h;\n' +
      '  ' + posStdDev + '.x = ' + listVar + '[3].x;\n' +
      '  ' + posStdDev + '.y = ' + listVar + '[3].y;\n' +
      '  ' + posStdDev + '.h = ' + listVar + '[3].h;\n' +
      '  ' + velStdDev + '.x = ' + listVar + '[4].x;\n' +
      '  ' + velStdDev + '.y = ' + listVar + '[4].y;\n' +
      '  ' + velStdDev + '.h = ' + listVar + '[4].h;\n' +
      '  ' + accStdDev + '.x = ' + listVar + '[5].x;\n' +
      '  ' + accStdDev + '.y = ' + listVar + '[5].y;\n' +
      '  ' + accStdDev + '.h = ' + listVar + '[5].h;\n' +
      '}\n';
};

Blockly.FtcJava['sparkFunOTOS_getPosVelAccAndStdDev'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunOTOS');
  var pos = Blockly.FtcJava.valueToCode(
      block, 'POS', Blockly.FtcJava.ORDER_COMMA);
  var vel = Blockly.FtcJava.valueToCode(
      block, 'VEL', Blockly.FtcJava.ORDER_COMMA);
  var acc = Blockly.FtcJava.valueToCode(
      block, 'ACC', Blockly.FtcJava.ORDER_COMMA);
  var posStdDev = Blockly.FtcJava.valueToCode(
      block, 'POS_STD_DEV', Blockly.FtcJava.ORDER_COMMA);
  var velStdDev = Blockly.FtcJava.valueToCode(
      block, 'VEL_STD_DEV', Blockly.FtcJava.ORDER_COMMA);
  var accStdDev = Blockly.FtcJava.valueToCode(
      block, 'ACC_STD_DEV', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.getPosVelAccAndStdDev(' + pos + ', ' + vel + ', ' + acc + ', ' + posStdDev + ', ' + velStdDev + ', ' + accStdDev + ');\n';
};

Blockly.Blocks['sparkFunOTOS_getVersionInfo'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('getVersionInfo'));
    this.appendValueInput('HW_VERSION').setCheck('SparkFunOTOS.Version')
        .appendField('hwVersion')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('FW_VERSION').setCheck('SparkFunOTOS.Version')
        .appendField('fwVersion')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Gets the hardware and firmware version of the OTOS.');
  }
};

Blockly.JavaScript['sparkFunOTOS_getVersionInfo'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');

  // The blocks plugged into HW_VERSION and FW_VERSION must be variables_get blocks.
  var blockLabel = 'call ' + identifier + '.getVersionInfo';
  var hwVersionBlock = block.getInputTargetBlock('HW_VERSION');
  if (!hwVersionBlock || hwVersionBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the hwVersion socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  var fwVersionBlock = block.getInputTargetBlock('FW_VERSION');
  if (!fwVersionBlock || fwVersionBlock.type != 'variables_get') {
    return 'throw "Incorrect block plugged into the fwVersion socket of the block labeled \"' + blockLabel + '\". Expected a variable block.";\n';
  }
  
  var hwVersion = Blockly.JavaScript.valueToCode(
    block, 'HW_VERSION', Blockly.JavaScript.ORDER_MEMBER);
  var fwVersion = Blockly.JavaScript.valueToCode(
    block, 'FW_VERSION', Blockly.JavaScript.ORDER_MEMBER);
  var listVar = Blockly.JavaScript.variableDB_.getDistinctName(
    'tmpList', Blockly.Variables.NAME_TYPE);
  return '{\n' +
      // Send stringified values of the args so they can be validated as legal SparkFunOTOS.Version objects.
      '  var ' + listVar + ' = JSON.parse(' + identifier + '.getVersionInfo(\n' +
      '      JSON.stringify(' + hwVersion + '), JSON.stringify(' + fwVersion + ')));\n' +
      // Copy the field values into the hwVersion and fwVersion objects.
      '  ' + hwVersion + '.major = ' + listVar + '[0].major;\n' +
      '  ' + hwVersion + '.minor = ' + listVar + '[0].minor;\n' +
      '  ' + fwVersion + '.major = ' + listVar + '[1].major;\n' +
      '  ' + fwVersion + '.minor = ' + listVar + '[1].minor;\n' +
      '}\n';
};

Blockly.FtcJava['sparkFunOTOS_getVersionInfo'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunOTOS');
  var hwVersion = Blockly.FtcJava.valueToCode(
      block, 'HW_VERSION', Blockly.FtcJava.ORDER_COMMA);
  var fwVersion = Blockly.FtcJava.valueToCode(
      block, 'FW_VERSION', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.getVersionInfo(' + hwVersion + ', ' + fwVersion + ');\n';
};

Blockly.Blocks['sparkFunOTOS_resetTracking'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('resetTracking'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Resets the tracking algorithm, which resets the position to the ' +
                    'origin, but can also be used to recover from some rare tracking errors.');
  }
};

Blockly.JavaScript['sparkFunOTOS_resetTracking'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.resetTracking();\n';
};

Blockly.FtcJava['sparkFunOTOS_resetTracking'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunOTOS');
  return identifier + '.resetTracking();\n';
};

Blockly.Blocks['sparkFunOTOS_begin'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('begin'));
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    this.setTooltip('Begins the Qwiic OTOS and verifies it is connected. ' +
                    'Returns true if successful, false otherwise.');
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['sparkFunOTOS_begin'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.begin()';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['sparkFunOTOS_begin'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunOTOS');
  var code = identifier + '.begin()';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['sparkFunOTOS_calibrateImu'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('calibrateImu'));
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    this.setTooltip('Calibrates the IMU on the OTOS, which removes the accelerometer and ' +
                    'gyroscope offsets. This will do the full 255 samples and wait until ' +
                    'the calibration is done, which takes about 612ms as of firmware v1.0. ' +
                    'Returns true if the calibration was successful, false otherwise.');
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['sparkFunOTOS_calibrateImu'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.calibrateImu()';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['sparkFunOTOS_calibrateImu'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunOTOS');
  var code = identifier + '.calibrateImu()';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['sparkFunOTOS_calibrateImu_withArgs'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('calibrateImu'));
    this.appendValueInput('NUM_SAMPLES').setCheck('Number')
        .appendField('numSamples')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('WAIT_UNTIL_DONE').setCheck('Boolean')
        .appendField('waitUntilDone')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true, null);
    this.setNextStatement(true, null);
    this.setColour(functionColor);
    this.setTooltip('Calibrates the IMU on the OTOS, which removes the accelerometer and ' +
                    'gyroscope offsets. The maximum number of samples is 255. Each sample ' +
                    'takes about 2.4ms, so fewer samples can be taken for faster calibration. ' +
                    'Returns true if the calibration was successful, false otherwise.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'NUM_SAMPLES':
          return 'int';
      }
      return '';
    };
    Blockly.Extensions.apply('toggle_output_boolean', this, true);
  }
};

Blockly.JavaScript['sparkFunOTOS_calibrateImu_withArgs'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var numSamples = Blockly.JavaScript.valueToCode(
      block, 'NUM_SAMPLES', Blockly.JavaScript.ORDER_COMMA);
  var waitUntilDone = Blockly.JavaScript.valueToCode(
      block, 'WAIT_UNTIL_DONE', Blockly.JavaScript.ORDER_COMMA);
  var code = identifier + '.calibrateImu_withArgs(' + numSamples + ', ' + waitUntilDone + ')';
  if (block.outputConnection) {
    return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.FtcJava['sparkFunOTOS_calibrateImu_withArgs'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunOTOS');
  var numSamples = Blockly.FtcJava.valueToCode(
      block, 'NUM_SAMPLES', Blockly.FtcJava.ORDER_COMMA);
  var waitUntilDone = Blockly.FtcJava.valueToCode(
      block, 'WAIT_UNTIL_DONE', Blockly.FtcJava.ORDER_COMMA);
  var code = identifier + '.calibrateImu(' + numSamples + ', ' + waitUntilDone + ')';
  if (block.outputConnection) {
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  } else {
    return code + ';\n';
  }
};

Blockly.Blocks['sparkFunOTOS_isConnected'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('isConnected'));
    this.setColour(functionColor);
    this.setTooltip('Checks if the OTOS is connected to the I2C bus. ' +
                    'Returns true if the OTOS is connected, false otherwise.');
  }
};

Blockly.JavaScript['sparkFunOTOS_isConnected'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.isConnected()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['sparkFunOTOS_isConnected'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunOTOS');
  var code = identifier + '.isConnected()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['sparkFunOTOS_selfTest'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createSparkFunOTOSDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('selfTest'));
    this.setColour(functionColor);
    this.setTooltip('Performs a self-test on the OTOS. ' +
                    'Returns true if the self-test passed, false otherwise.');
  }
};

Blockly.JavaScript['sparkFunOTOS_selfTest'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.selfTest()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['sparkFunOTOS_selfTest'] = function(block) {
  var identifier = Blockly.FtcJava.importDeclareAssign_(block, 'IDENTIFIER', 'SparkFunOTOS');
  var code = identifier + '.selfTest()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

// SparkFunOTOS.Pose2D blocks

Blockly.Blocks['sparkFunOTOS_Pose2D_create'] = {
  init: function() {
    this.setOutput(true, 'SparkFunOTOS.Pose2D');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('SparkFunOTOS.Pose2D'));
    this.setColour(functionColor);
    this.setTooltip('Creates a new SparkFunOTOS.Pose2D object.');
  }
};

Blockly.JavaScript['sparkFunOTOS_Pose2D_create'] = function(block) {
  var code = '{"x": 0.0, "y": 0.0, "h": 0.0}';
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.FtcJava['sparkFunOTOS_Pose2D_create'] = function(block) {
  var code = 'new SparkFunOTOS.Pose2D()';
  Blockly.FtcJava.generateImport_('SparkFunOTOS');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

Blockly.Blocks['sparkFunOTOS_Pose2D_create_withArgs'] = {
  init: function() {
    this.setOutput(true, 'SparkFunOTOS.Pose2D');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('SparkFunOTOS.Pose2D'));
    this.appendValueInput('X').setCheck('Number')
        .appendField('x')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('Y').setCheck('Number')
        .appendField('y')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('H').setCheck('Number')
        .appendField('h')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Creates a new SparkFunOTOS.Pose2D object.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'X':
        case 'Y':
        case 'H':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['sparkFunOTOS_Pose2D_create_withArgs'] = function(block) {
  var x = Blockly.JavaScript.valueToCode(
      block, 'X', Blockly.JavaScript.ORDER_COMMA);
  var y = Blockly.JavaScript.valueToCode(
      block, 'Y', Blockly.JavaScript.ORDER_COMMA);
  var h = Blockly.JavaScript.valueToCode(
      block, 'H', Blockly.JavaScript.ORDER_COMMA);
  var code = '{"x": ' + x + ', "y": ' + y + ', "h": ' + h + '}';
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.FtcJava['sparkFunOTOS_Pose2D_create_withArgs'] = function(block) {
  var x = Blockly.FtcJava.valueToCode(
      block, 'X', Blockly.FtcJava.ORDER_COMMA);
  var y = Blockly.FtcJava.valueToCode(
      block, 'Y', Blockly.FtcJava.ORDER_COMMA);
  var h = Blockly.FtcJava.valueToCode(
      block, 'H', Blockly.FtcJava.ORDER_COMMA);
  var code = 'new SparkFunOTOS.Pose2D(' + x + ', ' + y + ', ' + h + ')';
  Blockly.FtcJava.generateImport_('SparkFunOTOS');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

Blockly.Blocks['sparkFunOTOS_Pose2D_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['x', 'x'],
        ['y', 'y'],
        ['h', 'h'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('SparkFunOTOS.Pose2D'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('POSE').setCheck('SparkFunOTOS.Pose2D')
        .appendField('pose')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['x', 'Returns the x field of the given SparkFunOTOS.Pose2D object.'],
        ['y', 'Returns the y field of the given SparkFunOTOS.Pose2D object.'],
        ['h', 'Returns the h field of the given SparkFunOTOS.Pose2D object.'],
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
        case 'x':
        case 'y':
        case 'h':
          return 'double';
        default:
          throw 'Unexpected property ' + property + ' (sparkFunOTOS_Pose2D_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['sparkFunOTOS_Pose2D_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var pose = Blockly.JavaScript.valueToCode(
      block, 'POSE', Blockly.JavaScript.ORDER_MEMBER);
  var code = pose + '.' + property;
  var blockLabel = 'SparkFunOTOS.Pose2D.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['sparkFunOTOS_Pose2D_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var pose = Blockly.FtcJava.valueToCode(
      block, 'POSE', Blockly.FtcJava.ORDER_MEMBER);
  var code = pose + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['sparkFunOTOS_Pose2D_setProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['x', 'x'],
        ['y', 'y'],
        ['h', 'h'],
    ];
    this.appendDummyInput()
        .appendField('set')
        .appendField(createNonEditableField('SparkFunOTOS.Pose2D'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('POSE').setCheck('SparkFunOTOS.Pose2D')
        .appendField('pose')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VALUE').setCheck('Number')
        .appendField('to')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['x', 'Sets the x field of the given SparkFunOTOS.Pose2D object.'],
        ['y', 'Sets the y field of the given SparkFunOTOS.Pose2D object.'],
        ['h', 'Sets the h field of the given SparkFunOTOS.Pose2D object.'],
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
          case 'x':
          case 'y':
          case 'h':
            return 'double';
          default:
            throw 'Unexpected property ' + property + ' (sparkFunOTOS_Pose2D_setProperty_Number getArgumentType).';
        }
      }
      return '';
    };
  }
};

Blockly.JavaScript['sparkFunOTOS_Pose2D_setProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var pose = Blockly.JavaScript.valueToCode(
      block, 'POSE', Blockly.JavaScript.ORDER_MEMBER);
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_ASSIGNMENT);
  return pose + '.' + property + ' = ' + value + ';\n';
};

Blockly.FtcJava['sparkFunOTOS_Pose2D_setProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var pose = Blockly.FtcJava.valueToCode(
      block, 'POSE', Blockly.FtcJava.ORDER_MEMBER);
  var value = Blockly.FtcJava.valueToCode(
      block, 'VALUE', Blockly.FtcJava.ORDER_ASSIGNMENT);
  return pose + '.' + property + ' = ' + value + ';\n';
};

// SparkFunOTOS.SignalProcessConfig blocks

Blockly.Blocks['sparkFunOTOS_SignalProcessConfig_create'] = {
  init: function() {
    this.setOutput(true, 'SparkFunOTOS.SignalProcessConfig');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('SparkFunOTOS.SignalProcessConfig'));
    this.setColour(functionColor);
    this.setTooltip('Creates a new SparkFunOTOS.SignalProcessConfig object.');
  }
};

Blockly.JavaScript['sparkFunOTOS_SignalProcessConfig_create'] = function(block) {
  var code = '{"enLut": false, "enAcc": false, "enRot": false, "enVar": false}';
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.FtcJava['sparkFunOTOS_SignalProcessConfig_create'] = function(block) {
  var code = 'new SparkFunOTOS.SignalProcessConfig()';
  Blockly.FtcJava.generateImport_('SparkFunOTOS');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

Blockly.Blocks['sparkFunOTOS_SignalProcessConfig_getProperty_Boolean'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['enLut', 'enLut'],
        ['enAcc', 'enAcc'],
        ['enRot', 'enRot'],
        ['enVar', 'enVar'],
    ];
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField(createNonEditableField('SparkFunOTOS.SignalProcessConfig'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('SIGNAL_PROCESS_CONFIG').setCheck('SparkFunOTOS.SignalProcessConfig')
        .appendField('signalProcessConfig')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['enLut', 'Returns the enLut field of the given SparkFunOTOS.SignalProcessConfig object.'],
        ['enAcc', 'Returns the enAcc field of the given SparkFunOTOS.SignalProcessConfig object.'],
        ['enRot', 'Returns the enRot field of the given SparkFunOTOS.SignalProcessConfig object.'],
        ['enVar', 'Returns the enVar field of the given SparkFunOTOS.SignalProcessConfig object.'],
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

Blockly.JavaScript['sparkFunOTOS_SignalProcessConfig_getProperty_Boolean'] = function(block) {
  var property = block.getFieldValue('PROP');
  var signalProcessConfig = Blockly.JavaScript.valueToCode(
      block, 'SIGNAL_PROCESS_CONFIG', Blockly.JavaScript.ORDER_MEMBER);
  var code = signalProcessConfig + '.' + property;
  var blockLabel = 'SparkFunOTOS.SignalProcessConfig.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['sparkFunOTOS_SignalProcessConfig_getProperty_Boolean'] = function(block) {
  var property = block.getFieldValue('PROP');
  var signalProcessConfig = Blockly.FtcJava.valueToCode(
      block, 'SIGNAL_PROCESS_CONFIG', Blockly.FtcJava.ORDER_MEMBER);
  var code = signalProcessConfig + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['sparkFunOTOS_SignalProcessConfig_setProperty_Boolean'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['enLut', 'enLut'],
        ['enAcc', 'enAcc'],
        ['enRot', 'enRot'],
        ['enVar', 'enVar'],
    ];
    this.appendDummyInput()
        .appendField('set')
        .appendField(createNonEditableField('SparkFunOTOS.SignalProcessConfig'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('SIGNAL_PROCESS_CONFIG').setCheck('SparkFunOTOS.SignalProcessConfig')
        .appendField('signalProcessConfig')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('VALUE').setCheck('Boolean')
        .appendField('to')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(setPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['enLut', 'Sets the enLut field of the given SparkFunOTOS.SignalProcessConfig object.'],
        ['enAcc', 'Sets the enAcc field of the given SparkFunOTOS.SignalProcessConfig object.'],
        ['enRot', 'Sets the enRot field of the given SparkFunOTOS.SignalProcessConfig object.'],
        ['enVar', 'Sets the enVar field of the given SparkFunOTOS.SignalProcessConfig object.'],
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

Blockly.JavaScript['sparkFunOTOS_SignalProcessConfig_setProperty_Boolean'] = function(block) {
  var property = block.getFieldValue('PROP');
  var signalProcessConfig = Blockly.JavaScript.valueToCode(
      block, 'SIGNAL_PROCESS_CONFIG', Blockly.JavaScript.ORDER_MEMBER);
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_ASSIGNMENT);
  return signalProcessConfig + '.' + property + ' = ' + value + ';\n';
};

Blockly.FtcJava['sparkFunOTOS_SignalProcessConfig_setProperty_Boolean'] = function(block) {
  var property = block.getFieldValue('PROP');
  var signalProcessConfig = Blockly.FtcJava.valueToCode(
      block, 'SIGNAL_PROCESS_CONFIG', Blockly.FtcJava.ORDER_MEMBER);
  var value = Blockly.FtcJava.valueToCode(
      block, 'VALUE', Blockly.FtcJava.ORDER_ASSIGNMENT);
  return signalProcessConfig + '.' + property + ' = ' + value + ';\n';
};

// SparkFunOTOS.Status blocks

Blockly.Blocks['sparkFunOTOS_Status_getProperty_Boolean'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['warnTiltAngle', 'warnTiltAngle'],
        ['warnOpticalTracking', 'warnOpticalTracking'],
        ['errorPaa', 'errorPaa'],
        ['errorLsm', 'errorLsm'],
    ];
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField(createNonEditableField('SparkFunOTOS.Status'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('STATUS').setCheck('SparkFunOTOS.Status')
        .appendField('status')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['warnTiltAngle', 'Returns the warnTiltAngle field of the given SparkFunOTOS.Status object.'],
        ['warnOpticalTracking', 'Returns the warnOpticalTracking field of the given SparkFunOTOS.Status object.'],
        ['errorPaa', 'Returns the errorPaa field of the given SparkFunOTOS.Status object.'],
        ['errorLsm', 'Returns the errorLsm field of the given SparkFunOTOS.Status object.'],
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

Blockly.JavaScript['sparkFunOTOS_Status_getProperty_Boolean'] = function(block) {
  var property = block.getFieldValue('PROP');
  var status = Blockly.JavaScript.valueToCode(
      block, 'STATUS', Blockly.JavaScript.ORDER_MEMBER);
  var code = status + '.' + property;
  var blockLabel = 'SparkFunOTOS.Status.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['sparkFunOTOS_Status_getProperty_Boolean'] = function(block) {
  var property = block.getFieldValue('PROP');
  var status = Blockly.FtcJava.valueToCode(
      block, 'STATUS', Blockly.FtcJava.ORDER_MEMBER);
  var code = status + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

// SparkFunOTOS.Version blocks

Blockly.Blocks['sparkFunOTOS_Version_create'] = {
  init: function() {
    this.setOutput(true, 'SparkFunOTOS.Version');
    this.appendDummyInput()
        .appendField('new')
        .appendField(createNonEditableField('SparkFunOTOS.Version'));
    this.setColour(functionColor);
    this.setTooltip('Creates a new SparkFunOTOS.Version object.');
  }
};

Blockly.JavaScript['sparkFunOTOS_Version_create'] = function(block) {
  var code = '{"major": 0, "minor": 0}';
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.FtcJava['sparkFunOTOS_Version_create'] = function(block) {
  var code = 'new SparkFunOTOS.Version()';
  Blockly.FtcJava.generateImport_('SparkFunOTOS');
  return [code, Blockly.FtcJava.ORDER_NEW];
};

Blockly.Blocks['sparkFunOTOS_Version_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['major', 'major'],
        ['minor', 'minor'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('SparkFunOTOS.Version'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.appendValueInput('VERSION').setCheck('SparkFunOTOS.Version')
        .appendField('version')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['major', 'Returns the major field of the given SparkFunOTOS.Version object.'],
        ['minor', 'Returns the minor field of the given SparkFunOTOS.Version object.'],
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
        case 'major':
        case 'minor':
          return 'byte';
        default:
          throw 'Unexpected property ' + property + ' (sparkFunOTOS_Version_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['sparkFunOTOS_Version_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var version = Blockly.JavaScript.valueToCode(
      block, 'VERSION', Blockly.JavaScript.ORDER_MEMBER);
  var code = version + '.' + property;
  var blockLabel = 'SparkFunOTOS.Version.' + block.getField('PROP').getText();
  return wrapJavaScriptCode(code, blockLabel);
};

Blockly.FtcJava['sparkFunOTOS_Version_getProperty_Number'] = function(block) {
  var property = block.getFieldValue('PROP');
  var version = Blockly.FtcJava.valueToCode(
      block, 'VERSION', Blockly.FtcJava.ORDER_MEMBER);
  var code = version + '.' + property;
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

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

/**
 * @fileoverview Miscellaneous blocks.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// miscIdentifierForJavaScript
// The following are defined in vars.js:
// commentColor
// functionColor
// knownTypeToClassName

Blockly.Blocks['comment'] = {
  init: function() {
    this.appendDummyInput()
        .appendField(new Blockly.FieldTextInput(''), 'COMMENT');
    this.getField('COMMENT').maxDisplayLength = 140;
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(commentColor);
  }
};

Blockly.JavaScript['comment'] = function(block) {
  return '';
};

Blockly.FtcJava['comment'] = function(block) {
  return '// ' + block.getFieldValue('COMMENT') + '\n';
};

Blockly.Blocks['misc_null'] = {
  init: function() {
    this.setOutput(true); // no type for null
    this.appendDummyInput()
        .appendField(createNonEditableField('null'));
    this.setColour(functionColor);
    this.setTooltip('null');
  }
};

Blockly.JavaScript['misc_null'] = function(block) {
  var code = miscIdentifierForJavaScript + '.getNull()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['misc_null'] = function(block) {
  return ['null', Blockly.FtcJava.ORDER_ATOMIC];
};

Blockly.Blocks['misc_isNull'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('isNull'));
    this.appendValueInput('VALUE') // all types allowed
        .appendField('value')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns true if the given value is null, false otherwise.');
  }
};

Blockly.JavaScript['misc_isNull'] = function(block) {
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_NONE);
  var code = value + ' == null';
  return [code, Blockly.JavaScript.ORDER_EQUALITY];
};

Blockly.FtcJava['misc_isNull'] = function(block) {
  var value = Blockly.FtcJava.valueToCode(
      block, 'VALUE', Blockly.FtcJava.ORDER_EQUALITY);
  var code = value + ' == null';
  return [code, Blockly.FtcJava.ORDER_EQUALITY];
};

Blockly.Blocks['misc_isNotNull'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('isNotNull'));
    this.appendValueInput('VALUE') // all types allowed
        .appendField('value')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns true if the given value is not null, false otherwise.');
  }
};

Blockly.JavaScript['misc_isNotNull'] = function(block) {
  var value = Blockly.JavaScript.valueToCode(
      block, 'VALUE', Blockly.JavaScript.ORDER_NONE);
  var code = value + ' != null';
  return [code, Blockly.JavaScript.ORDER_EQUALITY];
};

Blockly.FtcJava['misc_isNotNull'] = function(block) {
  var value = Blockly.FtcJava.valueToCode(
      block, 'VALUE', Blockly.FtcJava.ORDER_EQUALITY);
  var code = value + ' != null';
  return [code, Blockly.FtcJava.ORDER_EQUALITY];
};

Blockly.Blocks['misc_atan2'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('atan2');
    this.appendValueInput('Y').setCheck('Number')
        .appendField('y')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('X').setCheck('Number')
        .appendField('x')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(Blockly.Msg.MATH_HUE);
    this.setTooltip('Returns a numerical value between -180 and +180 degrees, representing ' +
        'the counterclockwise angle between the positive X axis, and the point (x, y).');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'Y':
        case 'X':
          return 'double';
      }
      return '';
    };
    this.getFtcJavaOutputType = function() {
      return 'double';
    };
  }
};

Blockly.JavaScript['misc_atan2'] = function(block) {
  var y = Blockly.JavaScript.valueToCode(
      block, 'Y', Blockly.JavaScript.ORDER_COMMA);
  var x = Blockly.JavaScript.valueToCode(
      block, 'X', Blockly.JavaScript.ORDER_COMMA);
  var code = 'Math.atan2(' + y + ', ' + x + ') / Math.PI * 180';
  return [code, Blockly.JavaScript.ORDER_DIVISION];
};

Blockly.FtcJava['misc_atan2'] = function(block) {
  var y = Blockly.FtcJava.valueToCode(
      block, 'Y', Blockly.FtcJava.ORDER_COMMA);
  var x = Blockly.FtcJava.valueToCode(
      block, 'X', Blockly.FtcJava.ORDER_COMMA);
  var code = 'Math.atan2(' + y + ', ' + x + ') / Math.PI * 180';
  return [code, Blockly.FtcJava.ORDER_MULTIPLICATION];
};

Blockly.Blocks['misc_formatNumber'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('formatNumber'));
    this.appendValueInput('NUMBER').setCheck('Number')
        .appendField('number')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PRECISION').setCheck('Number')
        .appendField('precision')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a text value of the given number formatted with the given ' +
        'precision, padded with zeros if necessary.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'NUMBER':
          return 'double';
        case 'PRECISION':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['misc_formatNumber'] = function(block) {
  var number = Blockly.JavaScript.valueToCode(
      block, 'NUMBER', Blockly.JavaScript.ORDER_COMMA);
  var precision = Blockly.JavaScript.valueToCode(
      block, 'PRECISION', Blockly.JavaScript.ORDER_COMMA);
  var code = miscIdentifierForJavaScript + '.formatNumber(' + number + ', ' + precision + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['misc_formatNumber'] = function(block) {
  var number = Blockly.FtcJava.valueToCode(
      block, 'NUMBER', Blockly.FtcJava.ORDER_COMMA);
  var precision = Blockly.FtcJava.valueToCode(
      block, 'PRECISION', Blockly.FtcJava.ORDER_COMMA);
  // Due to issues with floating point precision, we always call the JavaUtil method.
  Blockly.FtcJava.generateImport_('JavaUtil');
  var code = 'JavaUtil.formatNumber(' + number + ', ' + precision + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['misc_formatNumber_withWidth'] = {
  init: function() {
    this.setOutput(true, 'String');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('formatNumber'));
    this.appendValueInput('NUMBER').setCheck('Number')
        .appendField('number')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('WIDTH').setCheck('Number')
        .appendField('width')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PRECISION').setCheck('Number')
        .appendField('precision')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a text value of the given number formatted with the given width ' +
        'and precision, padded if necessary.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'NUMBER':
          return 'double';
        case 'WIDTH':
        case 'PRECISION':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['misc_formatNumber_withWidth'] = function(block) {
  var number = Blockly.JavaScript.valueToCode(
      block, 'NUMBER', Blockly.JavaScript.ORDER_COMMA);
  var width = Blockly.JavaScript.valueToCode(
      block, 'WIDTH', Blockly.JavaScript.ORDER_COMMA);
  var precision = Blockly.JavaScript.valueToCode(
      block, 'PRECISION', Blockly.JavaScript.ORDER_COMMA);
  var code = miscIdentifierForJavaScript + '.formatNumber_withWidth(' +
      number + ', ' + width + ', ' + precision + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['misc_formatNumber_withWidth'] = function(block) {
  var number = Blockly.FtcJava.valueToCode(
      block, 'NUMBER', Blockly.FtcJava.ORDER_COMMA);
  var width = Blockly.FtcJava.valueToCode(
      block, 'WIDTH', Blockly.FtcJava.ORDER_COMMA);
  var precision = Blockly.FtcJava.valueToCode(
      block, 'PRECISION', Blockly.FtcJava.ORDER_COMMA);
  // Due to issues with floating point precision, we always call the JavaUtil method.
  Blockly.FtcJava.generateImport_('JavaUtil');
  var code = 'JavaUtil.formatNumber(' + number + ', ' + width + ', ' + precision + ')';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['misc_roundDecimal'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('roundDecimal'));
    this.appendValueInput('NUMBER').setCheck('Number')
        .appendField('number')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('PRECISION').setCheck('Number')
        .appendField('precision')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setColour(functionColor);
    this.setTooltip('Returns a numeric value of the given number rounded to the given precision.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'NUMBER':
          return 'double';
        case 'PRECISION':
          return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['misc_roundDecimal'] = function(block) {
  var number = Blockly.JavaScript.valueToCode(
      block, 'NUMBER', Blockly.JavaScript.ORDER_COMMA);
  var precision = Blockly.JavaScript.valueToCode(
      block, 'PRECISION', Blockly.JavaScript.ORDER_COMMA);
  var code = miscIdentifierForJavaScript + '.roundDecimal(' + number + ', ' + precision + ')';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['misc_roundDecimal'] = function(block) {
  var number = Blockly.FtcJava.valueToCode(
      block, 'NUMBER', Blockly.FtcJava.ORDER_COMMA);
  var precision = Blockly.FtcJava.valueToCode(
      block, 'PRECISION', Blockly.FtcJava.ORDER_COMMA);
  // Due to issues with floating point precision, we always call the JavaUtil method.
  Blockly.FtcJava.generateImport_('JavaUtil');
  var code = 'Double.parseDouble(JavaUtil.formatNumber(' + number + ', ' + precision + '))';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['misc_addItemToList'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('add item');
    this.appendValueInput('ITEM');
    this.appendDummyInput()
        .appendField('to list');
    this.appendValueInput('LIST')
        .setCheck('Array');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(Blockly.Msg.LISTS_HUE);
    this.setTooltip('Add the item to the end of the list.');
  }
};

Blockly.JavaScript['misc_addItemToList'] = function(block) {
  var item = Blockly.JavaScript.valueToCode(
      block, 'ITEM', Blockly.JavaScript.ORDER_NONE);
  var list = Blockly.JavaScript.valueToCode(
      block, 'LIST', Blockly.JavaScript.ORDER_MEMBER);
  return list + '.push(' + item + ');\n';
};

Blockly.FtcJava['misc_addItemToList'] = function(block) {
  var item = Blockly.FtcJava.valueToCode(
      block, 'ITEM', Blockly.FtcJava.ORDER_NONE);
  var list = Blockly.FtcJava.valueToCode(
      block, 'LIST', Blockly.FtcJava.ORDER_MEMBER);
  return list + '.add(' + item + ');\n';
};

Blockly.Blocks['misc_typedEnum_TimeUnit'] = {
  init: function() {
    var TIME_UNIT_CHOICES = [
        ['DAYS', 'DAYS'],
        ['HOURS', 'HOURS'],
        ['MICROSECONDS', 'MICROSECONDS'],
        ['MILLISECONDS', 'MILLISECONDS'],
        ['MINUTES', 'MINUTES'],
        ['NANOSECONDS', 'NANOSECONDS'],
        ['SECONDS', 'SECONDS'],
    ];
    this.setOutput(true, 'TimeUnit');
    this.appendDummyInput()
        .appendField(createNonEditableField('TimeUnit'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(TIME_UNIT_CHOICES), 'TIME_UNIT');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['DAYS', 'The TimeUnit value DAYS.'],
        ['HOURS', 'The TimeUnit value HOURS.'],
        ['MICROSECONDS', 'The TimeUnit value MICROSECONDS.'],
        ['MILLISECONDS', 'The TimeUnit value MILLISECONDS.'],
        ['MINUTES', 'The TimeUnit value MINUTES.'],
        ['NANOSECONDS', 'The TimeUnit value NANOSECONDS.'],
        ['SECONDS', 'The TimeUnit value SECONDS.'],
        ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('TIME_UNIT');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['misc_typedEnum_TimeUnit'] = function(block) {
  var code = '"' + block.getFieldValue('TIME_UNIT') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['misc_typedEnum_TimeUnit'] = function(block) {
  var code = 'TimeUnit.' + block.getFieldValue('TIME_UNIT');
  Blockly.FtcJava.generateImport_('TimeUnit');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

//................................................................................
// MyBlocks

var MY_BLOCKS_DEFAULT_HEADING = 'call Java method';
var MY_BLOCKS_DEFAULT_COLOR = 289;

function misc_call_mutationToDom(block) {
  var xmlElement = Blockly.utils.xml.createElement('mutation');
  xmlElement.setAttribute('createDropdownFunctionName', block.ftcAttributes_.createDropdownFunctionName);
  xmlElement.setAttribute('methodLookupString', block.ftcAttributes_.methodLookupString);
  xmlElement.setAttribute('fullClassName', block.ftcAttributes_.fullClassName);
  xmlElement.setAttribute('simpleName', block.ftcAttributes_.simpleName);
  xmlElement.setAttribute('parameterCount', block.ftcAttributes_.parameterCount);
  xmlElement.setAttribute('returnType', block.ftcAttributes_.returnType);
  xmlElement.setAttribute('color', String(block.ftcAttributes_.color));
  xmlElement.setAttribute('heading', block.ftcAttributes_.heading);
  xmlElement.setAttribute('comment', block.ftcAttributes_.comment);
  xmlElement.setAttribute('tooltip', block.ftcAttributes_.tooltip);
  xmlElement.setAttribute('accessMethod', block.ftcAttributes_.accessMethod);
  xmlElement.setAttribute('convertReturnValue', block.ftcAttributes_.convertReturnValue);
  for (var i = 0; i < block.ftcAttributes_.parameterCount; i++) {
    xmlElement.setAttribute('argLabel' + i, block.ftcAttributes_.argLabels[i]);
    xmlElement.setAttribute('argType' + i, block.ftcAttributes_.argTypes[i]);
    xmlElement.setAttribute('argAuto' + i, block.ftcAttributes_.argAutos[i]);
  }
  return xmlElement;
}

function misc_call_domToMutation(block, xmlElement) {
  if (!block.ftcAttributes_) {
    block.ftcAttributes_ = Object.create(null);
  }
  block.ftcAttributes_.methodLookupString = xmlElement.getAttribute('methodLookupString');
  block.ftcAttributes_.parameterCount = parseInt(xmlElement.getAttribute('parameterCount'), 10);
  block.ftcAttributes_.returnType = xmlElement.getAttribute('returnType');
  block.ftcAttributes_.comment = xmlElement.getAttribute('comment');
  block.ftcAttributes_.tooltip = xmlElement.getAttribute('tooltip');
  block.ftcAttributes_.accessMethod = xmlElement.getAttribute('accessMethod');
  block.ftcAttributes_.convertReturnValue = xmlElement.getAttribute('convertReturnValue');

  // Attributes added for 2021-2022 season.
  block.ftcAttributes_.heading = xmlElement.hasAttribute('heading')
      ? xmlElement.getAttribute('heading') : MY_BLOCKS_DEFAULT_HEADING;
  block.ftcAttributes_.color = xmlElement.hasAttribute('color')
      ? Number(xmlElement.getAttribute('color')) : MY_BLOCKS_DEFAULT_COLOR;
  block.ftcAttributes_.createDropdownFunctionName = xmlElement.hasAttribute('createDropdownFunctionName')
      ? xmlElement.getAttribute('createDropdownFunctionName') : '';
  block.ftcAttributes_.fullClassName = xmlElement.hasAttribute('fullClassName')
      ? xmlElement.getAttribute('fullClassName') : ('org.firstinspires.ftc.teamcode.' + block.getFieldValue('CLASS_NAME'));
  block.ftcAttributes_.simpleName = xmlElement.hasAttribute('simpleName')
      ? xmlElement.getAttribute('simpleName') : block.getFieldValue('CLASS_NAME');

  // When the feature was first created, accessMethod and convertReturnValue were not saved in the block.
  if (!block.ftcAttributes_.accessMethod) {
    figureOutAccessMethodAndConvertReturnValue(block);
  }

  // Update the block.
  if (block.ftcAttributes_.returnType != 'void') {
    var outputCheck = classTypeToCheck(block.ftcAttributes_.returnType, false);
    if (outputCheck) {
      if (block.outputConnection) {
        block.outputConnection.setCheck(outputCheck)
      }
    }
    block.outputType_ = classTypeToFtcJavaInputOutputType(block.ftcAttributes_.returnType, outputCheck, false);
    block.getFtcJavaOutputType = function() {
      return block.outputType_;
    }
  }
  // Add argument sockets.
  block.ftcAttributes_.argLabels = [];
  block.ftcAttributes_.argTypes = [];
  block.ftcAttributes_.argAutos = [];
  block.inputTypes_ = [];
  for (var i = 0; i < block.ftcAttributes_.parameterCount; i++) {
    block.ftcAttributes_.argLabels[i] = xmlElement.getAttribute('argLabel' + i);
    block.ftcAttributes_.argTypes[i] = xmlElement.getAttribute('argType' + i);
    block.ftcAttributes_.argAutos[i] = xmlElement.getAttribute('argAuto' + i);
    var inputCheck = classTypeToCheck(block.ftcAttributes_.argTypes[i], true);
    block.inputTypes_[i] = classTypeToFtcJavaInputOutputType(block.ftcAttributes_.argTypes[i], inputCheck, true);
    if (block.ftcAttributes_.argAutos[i]) {
      // No socket if parameter is provided automatically.
    } else {
      var input = block.appendValueInput('ARG' + i);
      if (inputCheck) {
        input.setCheck(inputCheck);
      }
      var label = block.ftcAttributes_.argLabels[i]
          ? block.ftcAttributes_.argLabels[i]
          : classTypeToLabel(block.ftcAttributes_.argTypes[i], true);
      input.appendField(label)
          .setAlign(Blockly.ALIGN_RIGHT);
    }
  }
  // Set the block's color.
  block.setColour(block.ftcAttributes_.color);
  // Set the block's HEADING (only misc_callJava_* blocks have HEADING)
  var field = block.getField('HEADING');
  if (field) {
    var value = block.ftcAttributes_.heading || '';
    if (value) {
      field.setValue(value);
    } else {
      block.removeInput('HEADING'); // Remove the heading row.
    }
  }
  // Set the block's DEVICE_NAME dropdown (only misc_callHardware_* blocks have DEVICE_NAME)
  var input = block.getInput('DEVICE_NAME');
  if (input) {
    var fn = window[block.ftcAttributes_.createDropdownFunctionName];
    if (typeof fn === 'function') {
      input.removeField('DEVICE_NAME');
      input.insertFieldAt(1, fn(), 'DEVICE_NAME');
    }
  }
  // Add a comment for the method's javadoc comment.
  if (block.ftcAttributes_.comment) {
    block.setCommentText(block.ftcAttributes_.comment);
  }
  block.getFtcJavaInputType = function(inputName) {
    if (inputName.startsWith('ARG')) {
      var i = parseInt(inputName.substr(3), 10);
      return block.inputTypes_[i];
    }
    return ''; // This shouldn't happen.
  };
}

function figureOutAccessMethodAndConvertReturnValue(block) {
  block.ftcAttributes_.accessMethod = 'callJava';
  block.ftcAttributes_.convertReturnValue = '';
  switch (block.ftcAttributes_.returnType) {
    case 'boolean':
    case 'java.lang.Boolean':
      block.ftcAttributes_.accessMethod += '_boolean';
      break;
    case 'char':
    case 'java.lang.Character':
    case 'java.lang.String':
      block.ftcAttributes_.accessMethod += '_String';
      break;
    case 'byte':
    case 'java.lang.Byte':
    case 'short':
    case 'java.lang.Short':
    case 'int':
    case 'java.lang.Integer':
    case 'long':
    case 'java.lang.Long':
    case 'float':
    case 'java.lang.Float':
    case 'double':
    case 'java.lang.Double':
      block.ftcAttributes_.accessMethod += '_String';
      block.ftcAttributes_.convertReturnValue = 'Number';
      break;
  }
}

function classTypeToCheck(classType, input) {
  if (classType.startsWith('[')) {
    return classType;
  }

  switch (classType) {
    // No checking for java.lang.Object.
    case 'java.lang.Object':
      return false;

    // Types that are provided automatically.
    case 'com.qualcomm.robotcore.eventloop.opmode.LinearOpMode':
    case 'com.qualcomm.robotcore.eventloop.opmode.OpMode':
    case 'com.qualcomm.robotcore.hardware.HardwareMap':
    case 'org.firstinspires.ftc.robotcore.external.Telemetry':
      return 'ProvidedAutomatically';

    // Java primitives and wrappers
    case 'boolean':
    case 'java.lang.Boolean':
      return 'Boolean';
    case 'char':
    case 'java.lang.Character':
    case 'java.lang.String':
      return 'String';
    case 'byte':
    case 'java.lang.Byte':
    case 'short':
    case 'java.lang.Short':
    case 'int':
    case 'java.lang.Integer':
    case 'long':
    case 'java.lang.Long':
    case 'float':
    case 'java.lang.Float':
    case 'double':
    case 'java.lang.Double':
    case 'java.lang.Number':
      return 'Number';
  }

  // If it's a type that's already used in blocks, use that type.
  var type = classType;
  var lastDot = type.lastIndexOf('.');
  if (lastDot != -1) {
    type = type.substr(lastDot + 1);
  }
  type = type.replace(/\$/g, '.'); // Change any $ to . (for inner classes).
  var knownJavaClassName = knownTypeToClassName(type);
  if (knownJavaClassName) {
    if (type == 'MatrixF' && input) {
      // Special case: A MatrixF parameter can accept a MatrixF or an OpenGLMatrix.
      return ['MatrixF', 'OpenGLMatrix'];
    }
    return type;
  }

  return classType;
}

function classTypeToFtcJavaInputOutputType(classType, check, input) {
  if (check == 'Number') {
    switch (classType) {
      case 'byte':
      case 'short':
      case 'int':
      case 'long':
      case 'float':
      case 'double':
        return classType;
    }
  }
  if (input && Array.isArray(check) &&
      check.length == 2 &&
      check.includes('MatrixF') &&
      check.includes('OpenGLMatrix')) {
    return 'MatrixF';
  }

  return classTypeToLabel(classType, true);
}

function classTypeToLabel(classType, removePackage) {
  if (classType.charAt(0) == '[') {
    return arrayTypeToLabel(classType, removePackage);
  }

  var label = classType;
  if (removePackage) {
    var lastDot = label.lastIndexOf('.');
    if (lastDot != -1) {
      label = label.substr(lastDot + 1);
    }
  }
  return label.replace(/\$/g, '.'); // Change any $ to . (for inner classes).
}

function arrayTypeToLabel(arrayType, removePackage) {
  // if arrayType is [I, label is int[]
  // if arrayType is [[J, label is long[][]
  // if arrayType is [Ljava.lang.Object;, label is java.lang.Object[]
  var elementType;
  switch (arrayType.charAt(1)) {
    case '[':
      elementType = arrayTypeToLabel(arrayType.substring(1), removePackage)
      break;
    case 'L':
      elementType = arrayType.substring(2);
      if (elementType.endsWith(';')) {
        elementType = elementType.substring(0, elementType.length - 1);
      }
      elementType = classTypeToLabel(elementType, removePackage);
      break;
    case 'Z':
      elementType = 'boolean';
      break;
    case 'B':
      elementType = 'byte';
      break;
    case 'C':
      elementType = 'char';
      break;
    case 'D':
      elementType = 'double';
      break;
    case 'F':
      elementType = 'float';
      break;
    case 'I':
      elementType = 'int';
      break;
    case 'J':
      elementType = 'long';
      break;
    case 'S':
      elementType = 'short';
      break;
  }
  return elementType + '[]';
}

function generateFtcImport(type) {
  if (type.includes('.')) {
    // Check for array of class.
    if (type.match(/^\[*L.*\;$/)) {
      var lastBracket = type.indexOf('[L');
      var semiColon = type.indexOf(';');
      generateFtcImport(type.substring(lastBracket + 2, semiColon));
    } else {
      var classToImport = type;
      // For inner classes, import the outer class.
      if (classToImport.includes('$')) {
        classToImport = classToImport.substring(0, classToImport.indexOf('$'));
      }
      Blockly.FtcJava.generateImportStatement_(classToImport);
    }
  }
}

Blockly.Blocks['misc_callJava_return'] = {
  init: function() {
    this.setOutput(true);
    this.appendDummyInput('HEADING')
        .appendField(MY_BLOCKS_DEFAULT_HEADING, 'HEADING');
    this.appendDummyInput()
        .appendField(createNonEditableField(''), 'CLASS_NAME')
        .appendField('.')
        .appendField(createNonEditableField(''), 'METHOD_NAME');
    this.setColour(MY_BLOCKS_DEFAULT_COLOR);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    this.setTooltip(function() {
      if (thisBlock.ftcAttributes_.tooltip) {
        return thisBlock.ftcAttributes_.tooltip;
      } else {
        var methodName = thisBlock.getFieldValue('METHOD_NAME');
        return 'Calls the Java method named ' + methodName +
            ' from the class named ' + thisBlock.ftcAttributes_.fullClassName + '.' +
            ' The Java method\'s return type is ' + classTypeToLabel(thisBlock.ftcAttributes_.returnType, false) + '.';
      }
    });
  },
  mutationToDom: function() {
    return misc_call_mutationToDom(this);
  },
  domToMutation: function(xmlElement) {
    misc_call_domToMutation(this, xmlElement);
  },
};

function generateJavaScriptCallJava(block) {
  var delimiter = ',\n' + Blockly.JavaScript.INDENT + Blockly.JavaScript.INDENT;
  // callJava is in runtime.js
  var code = 'callJava(' + miscIdentifierForJavaScript + delimiter +
      '"' + block.ftcAttributes_.returnType + '"' + delimiter +
      '"' + block.ftcAttributes_.accessMethod + '"' + delimiter +
      '"' + block.ftcAttributes_.convertReturnValue + '"' + delimiter +
      '"' + block.ftcAttributes_.methodLookupString + '"';
  for (var i = 0; i < block.ftcAttributes_.parameterCount; i++) {
    code += delimiter;
    code += (block.ftcAttributes_.argAutos[i]
        ? 'null'
        : (Blockly.JavaScript.valueToCode(block, 'ARG' + i, Blockly.JavaScript.ORDER_COMMA) || 'null'));
  }
  code += ')';
  return code;
}

Blockly.JavaScript['misc_callJava_return'] = function(block) {
  var code = generateJavaScriptCallJava(block);
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

function generateFtcJavaCallJava(block) {
  generateFtcImport(block.ftcAttributes_.returnType);
  for (var i = 0; i < block.ftcAttributes_.parameterCount; i++) {
    generateFtcImport(block.ftcAttributes_.argTypes[i]);
  }

  if (block.ftcAttributes_.fullClassName.startsWith("org.firstinspires.ftc.teamcode.") &&
      block.ftcAttributes_.fullClassName.lastIndexOf('.') == 30) {
  } else {
    generateFtcImport(block.ftcAttributes_.fullClassName);
  }
  var className = block.ftcAttributes_.simpleName;
  var methodName = block.getFieldValue('METHOD_NAME');
  var code = className + '.' + methodName + '(';
  var delimiter = '';
  var order = (block.ftcAttributes_.parameterCount == 1) ? Blockly.FtcJava.ORDER_NONE : Blockly.FtcJava.ORDER_COMMA;
  for (var i = 0; i < block.ftcAttributes_.parameterCount; i++) {
    code += delimiter;
    if (block.ftcAttributes_.argAutos[i]) {
      code += block.ftcAttributes_.argAutos[i];
    } else {
      var argCode = (Blockly.FtcJava.valueToCode(block, 'ARG' + i, order) || 'null');

      // Special case for char and java.lang.Character.
      if (block.ftcAttributes_.argTypes[i] == 'char' ||
          block.ftcAttributes_.argTypes[i] == 'java.lang.Character') {
        if (argCode.length == 3 && argCode.charAt(0) == '"' && argCode.charAt(2) == '"') {
          argCode = "'" + argCode.charAt(1) + "'";
        }
      }
      code += argCode;
    }
    delimiter = ', ';
  }
  code += ')';
  return code;
}

Blockly.FtcJava['misc_callJava_return'] = function(block) {
  var code = generateFtcJavaCallJava(block);
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['misc_callJava_noReturn'] = {
  init: function() {
    this.appendDummyInput('HEADING')
        .appendField(MY_BLOCKS_DEFAULT_HEADING, 'HEADING');
    this.appendDummyInput()
        .appendField(createNonEditableField(''), 'CLASS_NAME')
        .appendField('.')
        .appendField(createNonEditableField(''), 'METHOD_NAME');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(MY_BLOCKS_DEFAULT_COLOR);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    this.setTooltip(function() {
      if (thisBlock.ftcAttributes_.tooltip) {
        return thisBlock.ftcAttributes_.tooltip;
      } else {
        var methodName = thisBlock.getFieldValue('METHOD_NAME');
        return 'Calls the Java method named ' + methodName +
            ' from the class named ' + thisBlock.ftcAttributes_.fullClassName + '.';
      }
    });
  },
  mutationToDom: function() {
    return misc_call_mutationToDom(this);
  },
  domToMutation: function(xmlElement) {
    misc_call_domToMutation(this, xmlElement);
  },
};

Blockly.JavaScript['misc_callJava_noReturn'] = function(block) {
  return generateJavaScriptCallJava(block) + ';\n';
};

Blockly.FtcJava['misc_callJava_noReturn'] = function(block) {
  return generateFtcJavaCallJava(block) + ';\n';
};

//................................................................................
// MyBlocks for hardware

Blockly.Blocks['misc_callHardware_return'] = {
  init: function() {
    this.setOutput(true);
    this.appendDummyInput('DEVICE_NAME')
        .appendField('call')
        .appendField(createNonEditableField(''), 'DEVICE_NAME')
        .appendField('.')
        .appendField(createNonEditableField(''), 'METHOD_NAME');
    this.setColour(MY_BLOCKS_DEFAULT_COLOR);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    this.setTooltip(function() {
      if (thisBlock.ftcAttributes_.tooltip) {
        return thisBlock.ftcAttributes_.tooltip;
      } else {
        var methodName = thisBlock.getFieldValue('METHOD_NAME');
        return 'Calls the Java method named ' + methodName +
            ' from the class named ' + thisBlock.ftcAttributes_.fullClassName + '.' +
            ' The Java method\'s return type is ' + classTypeToLabel(thisBlock.ftcAttributes_.returnType, false) + '.';
      }
    });
  },
  mutationToDom: function() {
    return misc_call_mutationToDom(this);
  },
  domToMutation: function(xmlElement) {
    misc_call_domToMutation(this, xmlElement);
  },
};

function generateJavaScriptCallHardware(block) {
  var deviceName = block.getFieldValue('DEVICE_NAME');
  var delimiter = ',\n' + Blockly.JavaScript.INDENT + Blockly.JavaScript.INDENT;
  // callHardware is in runtime.js
  var code = 'callHardware(' + miscIdentifierForJavaScript + delimiter +
      '"' + block.ftcAttributes_.returnType + '"' + delimiter +
      '"' + block.ftcAttributes_.accessMethod + '"' + delimiter +
      '"' + block.ftcAttributes_.convertReturnValue + '"' + delimiter +
      '"' + deviceName + '"' + delimiter +
      '"' + block.ftcAttributes_.methodLookupString + '"';
  for (var i = 0; i < block.ftcAttributes_.parameterCount; i++) {
    code += delimiter;
    code += (block.ftcAttributes_.argAutos[i]
        ? 'null'
        : (Blockly.JavaScript.valueToCode(block, 'ARG' + i, Blockly.JavaScript.ORDER_COMMA) || 'null'));
  }
  code += ')';
  return code;
}

Blockly.JavaScript['misc_callHardware_return'] = function(block) {
  var code = generateJavaScriptCallHardware(block);
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

function generateFtcJavaCallHardware(block) {
  generateFtcImport(block.ftcAttributes_.returnType);
  for (var i = 0; i < block.ftcAttributes_.parameterCount; i++) {
    generateFtcImport(block.ftcAttributes_.argTypes[i]);
  }

  var deviceName = block.getFieldValue('DEVICE_NAME');
  var identifier = makeIdentifier(deviceName);

  generateFtcImport(block.ftcAttributes_.fullClassName);
  Blockly.FtcJava.definitions_['declare_field_' + identifier] =
      'private ' + block.ftcAttributes_.simpleName + ' ' + identifier + ';';
  var rvalue = 'hardwareMap.get(' + block.ftcAttributes_.simpleName + '.class, ' +
      Blockly.FtcJava.quote_(deviceName) + ')';
  Blockly.FtcJava.definitions_['assign_field_' + identifier] =
      identifier + ' = ' + rvalue + ';';

  var methodName = block.getFieldValue('METHOD_NAME');
  var code = identifier + '.' + methodName + '(';
  var delimiter = '';
  var order = (block.ftcAttributes_.parameterCount == 1) ? Blockly.FtcJava.ORDER_NONE : Blockly.FtcJava.ORDER_COMMA;
  for (var i = 0; i < block.ftcAttributes_.parameterCount; i++) {
    code += delimiter;
    if (block.ftcAttributes_.argAutos[i]) {
      code += block.ftcAttributes_.argAutos[i];
    } else {
      var argCode = (Blockly.FtcJava.valueToCode(block, 'ARG' + i, order) || 'null');

      // Special case for char and java.lang.Character.
      if (block.ftcAttributes_.argTypes[i] == 'char' ||
          block.ftcAttributes_.argTypes[i] == 'java.lang.Character') {
        if (argCode.length == 3 && argCode.charAt(0) == '"' && argCode.charAt(2) == '"') {
          argCode = "'" + argCode.charAt(1) + "'";
        }
      }
      code += argCode;
    }
    delimiter = ', ';
  }
  code += ')';
  return code;
}

Blockly.FtcJava['misc_callHardware_return'] = function(block) {
  var code = generateFtcJavaCallHardware(block);
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['misc_callHardware_noReturn'] = {
  init: function() {
    this.appendDummyInput('DEVICE_NAME')
        .appendField('call')
        .appendField(createNonEditableField(''), 'DEVICE_NAME')
        .appendField('.')
        .appendField(createNonEditableField(''), 'METHOD_NAME');
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(MY_BLOCKS_DEFAULT_COLOR);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    this.setTooltip(function() {
      if (thisBlock.ftcAttributes_.tooltip) {
        return thisBlock.ftcAttributes_.tooltip;
      } else {
        var methodName = thisBlock.getFieldValue('METHOD_NAME');
        return 'Calls the Java method named ' + methodName +
            ' from the class named ' + thisBlock.ftcAttributes_.fullClassName + '.';
      }
    });
  },
  mutationToDom: function() {
    return misc_call_mutationToDom(this);
  },
  domToMutation: function(xmlElement) {
    misc_call_domToMutation(this, xmlElement);
  },
};

Blockly.JavaScript['misc_callHardware_noReturn'] = function(block) {
  return generateJavaScriptCallHardware(block) + ';\n';
};

Blockly.FtcJava['misc_callHardware_noReturn'] = function(block) {
  return generateFtcJavaCallHardware(block) + ';\n';
};


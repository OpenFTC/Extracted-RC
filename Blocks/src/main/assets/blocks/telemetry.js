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
 * @fileoverview FTC robot blocks related to telemetry.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are generated dynamically in HardwareUtil.fetchJavaScriptForHardware():
// telemetryIdentifierForJavaScript
// The following are defined in vars.js:
// createNonEditableField
// functionColor

// Functions

Blockly.Blocks['telemetry_addNumericData'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Telemetry'))
        .appendField('.')
        .appendField(createNonEditableField('addData'));
    this.appendValueInput('KEY') // no type, for compatibility
        .appendField('key')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('NUMBER') // no type, for compatibility
        .appendField('number')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Add a numeric data point.');
  }
};

Blockly.JavaScript['telemetry_addNumericData'] = function(block) {
  var key = Blockly.JavaScript.valueToCode(
      block, 'KEY', Blockly.JavaScript.ORDER_COMMA);
  var number = Blockly.JavaScript.valueToCode(
      block, 'NUMBER', Blockly.JavaScript.ORDER_COMMA);
  // Convert the number to a String in JavaScript so a number like 123 doesn't show up as 123.0.
  return telemetryIdentifierForJavaScript + '.addTextData(' + key + ', String(' + number + '));\n';
};

Blockly.FtcJava['telemetry_addNumericData'] = function(block) {
  var key = Blockly.FtcJava.valueToCode(
      block, 'KEY', Blockly.FtcJava.ORDER_COMMA);
  var number = Blockly.FtcJava.valueToCode(
      block, 'NUMBER', Blockly.FtcJava.ORDER_COMMA);
  return 'telemetry.addData(' + key + ', ' + number + ');\n';
};

Blockly.Blocks['telemetry_addNumericData_Number'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Telemetry'))
        .appendField('.')
        .appendField(createNonEditableField('addData'));
    this.appendValueInput('KEY').setCheck('String')
        .appendField('key')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('NUMBER').setCheck('Number')
        .appendField('number')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Add a numeric data point.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'NUMBER':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['telemetry_addNumericData_Number'] =
    Blockly.JavaScript['telemetry_addNumericData'];

Blockly.FtcJava['telemetry_addNumericData_Number'] =
    Blockly.FtcJava['telemetry_addNumericData'];

Blockly.Blocks['telemetry_addTextData'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Telemetry'))
        .appendField('.')
        .appendField(createNonEditableField('addData'));
    this.appendValueInput('KEY') // no type, for compatibility
        .appendField('key')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TEXT') // no type, for compatibility
        .appendField('text')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Add a data point.');
  }
};

Blockly.JavaScript['telemetry_addTextData'] = function(block) {
  var key = Blockly.JavaScript.valueToCode(
      block, 'KEY', Blockly.JavaScript.ORDER_COMMA);
  var text = Blockly.JavaScript.valueToCode(
      block, 'TEXT', Blockly.JavaScript.ORDER_COMMA);
  return 'telemetryAddTextData(' + key + ', ' + text + ');\n';
};

Blockly.FtcJava['telemetry_addTextData'] = function(block) {
  var key = Blockly.FtcJava.valueToCode(
      block, 'KEY', Blockly.FtcJava.ORDER_COMMA);
  var text = Blockly.FtcJava.valueToCode(
      block, 'TEXT', Blockly.FtcJava.ORDER_COMMA);
  return 'telemetry.addData(' + key + ', ' + text + ');\n';
};

Blockly.Blocks['telemetry_addTextData_All'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Telemetry'))
        .appendField('.')
        .appendField(createNonEditableField('addData'));
    this.appendValueInput('KEY').setCheck('String')
        .appendField('key')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('TEXT') // all types allowed
        .appendField('text')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Add a data point.');
  }
};

Blockly.JavaScript['telemetry_addTextData_All'] =
    Blockly.JavaScript['telemetry_addTextData'];

Blockly.FtcJava['telemetry_addTextData_All'] =
    Blockly.FtcJava['telemetry_addTextData'];

Blockly.Blocks['telemetry_addLine'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Telemetry'))
        .appendField('.')
        .appendField(createNonEditableField('addLine'));
    this.appendValueInput('TEXT').setCheck('String')
        .appendField('text')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Add a line of text to the telemetry output on the driver station.');
  }
};

Blockly.JavaScript['telemetry_addLine'] = function(block) {
  var text = Blockly.JavaScript.valueToCode(
      block, 'TEXT', Blockly.JavaScript.ORDER_NONE);
  return telemetryIdentifierForJavaScript + '.addLine(' + text + ');\n';
};

Blockly.FtcJava['telemetry_addLine'] = function(block) {
  var text = Blockly.FtcJava.valueToCode(
      block, 'TEXT', Blockly.FtcJava.ORDER_NONE);
  return 'telemetry.addLine(' + text + ');\n';
};

Blockly.Blocks['telemetry_update'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Telemetry'))
        .appendField('.')
        .appendField(createNonEditableField('update'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Send data to the driver station.');
  }
};

Blockly.JavaScript['telemetry_update'] = function(block) {
  return telemetryIdentifierForJavaScript + '.update();\n';
};

Blockly.FtcJava['telemetry_update'] = function(block) {
  return 'telemetry.update();\n';
};

Blockly.Blocks['telemetry_speak'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Telemetry'))
        .appendField('.')
        .appendField(createNonEditableField('speak'));
    this.appendValueInput('TEXT') // all types allowed
        .appendField('text')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Directs the Driver Station device to speak the given text using ' +
        'TextToSpeech functionality, with the same language and country codes that ' +
        'were previously used, or the default language and country.');
  }
};

Blockly.JavaScript['telemetry_speak'] = function(block) {
  var text = Blockly.JavaScript.valueToCode(
      block, 'TEXT', Blockly.JavaScript.ORDER_COMMA);
  var languageCode = "''";
  var countryCode = "''";
  return 'telemetrySpeak(' + text + ', ' + languageCode + ', ' + countryCode + ');\n';
};

Blockly.FtcJava['telemetry_speak'] = function(block) {
  var text = Blockly.FtcJava.valueToCode(
      block, 'TEXT', Blockly.FtcJava.ORDER_COMMA);
  return 'telemetry.speak(' + text + ', null, null);\n';
};

Blockly.Blocks['telemetry_speak_withLanguage'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Telemetry'))
        .appendField('.')
        .appendField(createNonEditableField('speak'));
    this.appendValueInput('TEXT') // all types allowed
        .appendField('text')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('LANGUAGE_CODE').setCheck('String')
        .appendField('languageCode')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('COUNTRY_CODE').setCheck('String')
        .appendField('countryCode')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Directs the Driver Station device to speak the given text using ' +
        'TextToSpeech functionality, with the given language and country codes.');
  }
};

Blockly.JavaScript['telemetry_speak_withLanguage'] = function(block) {
  var text = Blockly.JavaScript.valueToCode(
      block, 'TEXT', Blockly.JavaScript.ORDER_COMMA);
  var languageCode = Blockly.JavaScript.valueToCode(
      block, 'LANGUAGE_CODE', Blockly.JavaScript.ORDER_COMMA);
  var countryCode = Blockly.JavaScript.valueToCode(
      block, 'COUNTRY_CODE', Blockly.JavaScript.ORDER_COMMA);
  return 'telemetrySpeak(' + text + ', ' + languageCode + ', ' + countryCode + ');\n';
};

Blockly.FtcJava['telemetry_speak_withLanguage'] = function(block) {
  var text = Blockly.FtcJava.valueToCode(
      block, 'TEXT', Blockly.FtcJava.ORDER_COMMA);
  var languageCode = Blockly.FtcJava.valueToCode(
      block, 'LANGUAGE_CODE', Blockly.FtcJava.ORDER_COMMA);
  var countryCode = Blockly.FtcJava.valueToCode(
      block, 'COUNTRY_CODE', Blockly.FtcJava.ORDER_COMMA);
  return 'telemetry.speak(' + text + ', ' + languageCode + ', ' + countryCode + ');\n';
};

Blockly.Blocks['telemetry_setDisplayFormat'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createNonEditableField('Telemetry'))
        .appendField('.')
        .appendField(createNonEditableField('setDisplayFormat'));
    this.appendValueInput('DISPLAY_FORMAT').setCheck('Telemetry.DisplayFormat')
        .appendField('displayFormat')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Sets the telemetry display format on the Driver Station.');
  }
};

Blockly.JavaScript['telemetry_setDisplayFormat'] = function(block) {
  var displayFormat = Blockly.JavaScript.valueToCode(
      block, 'DISPLAY_FORMAT', Blockly.JavaScript.ORDER_NONE);
  return telemetryIdentifierForJavaScript + '.setDisplayFormat(' + displayFormat + ');\n';
};

Blockly.FtcJava['telemetry_setDisplayFormat'] = function(block) {
  var displayFormat = Blockly.FtcJava.valueToCode(
      block, 'DISPLAY_FORMAT', Blockly.FtcJava.ORDER_NONE);
  return 'telemetry.setDisplayFormat(' + displayFormat + ');\n';
};

// Enums

Blockly.Blocks['telemetry_typedEnum_displayFormat'] = {
  init: function() {
    var DISPLAY_FORMAT_CHOICES = [
        ['CLASSIC', 'CLASSIC'],
        ['MONOSPACE', 'MONOSPACE'],
        ['HTML', 'HTML'],
    ];
    this.setOutput(true, 'Telemetry.DisplayFormat');
    this.appendDummyInput()
        .appendField(createNonEditableField('DisplayFormat'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(DISPLAY_FORMAT_CHOICES), 'DISPLAY_FORMAT');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['CLASSIC', 'The DisplayFormat value CLASSIC.'],
        ['MONOSPACE', 'The DisplayFormat value MONOSPACE.'],
        ['HTML', 'The DisplayFormat value HTML.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('DISPLAY_FORMAT');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['telemetry_typedEnum_displayFormat'] = function(block) {
  var code = '"' + block.getFieldValue('DISPLAY_FORMAT') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['telemetry_typedEnum_displayFormat'] = function(block) {
  var code = 'Telemetry.DisplayFormat.' + block.getFieldValue('DISPLAY_FORMAT');
  Blockly.FtcJava.generateImport_('Telemetry');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

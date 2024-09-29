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
 * @fileoverview FTC robot blocks for SortOrder.
 * @author Liz Looney
 */

// The following are defined in vars.js:
// createNonEditableField
// getPropertyColor

// SortOrder

Blockly.Blocks['util_typedEnum_sortOrder'] = {
  init: function() {
    var SORT_ORDER_CHOICES = [
        ['ASCENDING', 'ASCENDING'],
        ['DESCENDING', 'DESCENDING'],
    ];
    this.setOutput(true, 'SortOrder');
    this.appendDummyInput()
        .appendField(createNonEditableField('SortOrder'))
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(SORT_ORDER_CHOICES), 'SORT_ORDER');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['ASCENDING', 'The SortOrder value ASCENDING.'],
        ['DESCENDING', 'The SortOrder value DESCENDING.'],
    ];
    this.setTooltip(function() {
      var key = thisBlock.getFieldValue('SORT_ORDER');
      for (var i = 0; i < TOOLTIPS.length; i++) {
        if (TOOLTIPS[i][0] == key) {
          return TOOLTIPS[i][1];
        }
      }
      return '';
    });
  }
};

Blockly.JavaScript['util_typedEnum_sortOrder'] = function(block) {
  var code = '"' + block.getFieldValue('SORT_ORDER') + '"';
  return [code, Blockly.JavaScript.ORDER_ATOMIC];
};

Blockly.FtcJava['util_typedEnum_sortOrder'] = function(block) {
  var code = 'SortOrder.' + block.getFieldValue('SORT_ORDER');
  Blockly.FtcJava.generateImport_('SortOrder');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

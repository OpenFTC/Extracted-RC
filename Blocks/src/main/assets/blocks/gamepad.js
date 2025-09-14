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
 * @fileoverview FTC robot blocks related to game pads.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are defined in vars.js:
// getPropertyColor
// functionColor

function createGamepadDropdown() {
  var CHOICES = [
      ['gamepad1', 'gamepad1'],
      ['gamepad2', 'gamepad2'],
      ];
  return new Blockly.FieldDropdown(CHOICES);
}

Blockly.Blocks['gamepad_getProperty'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['A', 'A'],
        ['AWasPressed', 'AWasPressed'],
        ['AWasReleased', 'AWasReleased'],
        ['AtRest', 'AtRest'],
        ['B', 'B'],
        ['BWasPressed', 'BWasPressed'],
        ['BWasReleased', 'BWasReleased'],
        ['Back', 'Back'],
        ['BackWasPressed', 'BackWasPressed'],
        ['BackWasReleased', 'BackWasReleased'],
        ['Circle', 'Circle'],
        ['CircleWasPressed', 'CircleWasPressed'],
        ['CircleWasReleased', 'CircleWasReleased'],
        ['Cross', 'Cross'],
        ['CrossWasPressed', 'CrossWasPressed'],
        ['CrossWasReleased', 'CrossWasReleased'],
        ['DpadDown', 'DpadDown'],
        ['DpadDownWasPressed', 'DpadDownWasPressed'],
        ['DpadDownWasReleased', 'DpadDownWasReleased'],
        ['DpadLeft', 'DpadLeft'],
        ['DpadLeftWasPressed', 'DpadLeftWasPressed'],
        ['DpadLeftWasReleased', 'DpadLeftWasReleased'],
        ['DpadRight', 'DpadRight'],
        ['DpadRightWasPressed', 'DpadRightWasPressed'],
        ['DpadRightWasReleased', 'DpadRightWasReleased'],
        ['DpadUp', 'DpadUp'],
        ['DpadUpWasPressed', 'DpadUpWasPressed'],
        ['DpadUpWasReleased', 'DpadUpWasReleased'],
        ['Guide', 'Guide'],
        ['GuideWasPressed', 'GuideWasPressed'],
        ['GuideWasReleased', 'GuideWasReleased'],
        ['LeftBumper', 'LeftBumper'],
        ['LeftBumperWasPressed', 'LeftBumperWasPressed'],
        ['LeftBumperWasReleased', 'LeftBumperWasReleased'],
        ['LeftStickButton', 'LeftStickButton'],
        ['LeftStickButtonWasPressed', 'LeftStickButtonWasPressed'],
        ['LeftStickButtonWasReleased', 'LeftStickButtonWasReleased'],
        ['LeftStickX', 'LeftStickX'],
        ['LeftStickY', 'LeftStickY'],
        ['LeftTrigger', 'LeftTrigger'],
        ['Options', 'Options'],
        ['OptionsWasPressed', 'OptionsWasPressed'],
        ['OptionsWasReleased', 'OptionsWasReleased'],
        ['PS', 'PS'],
        ['PSWasPressed', 'PSWasPressed'],
        ['PSWasReleased', 'PSWasReleased'],
        ['RightBumper', 'RightBumper'],
        ['RightBumperWasPressed', 'RightBumperWasPressed'],
        ['RightBumperWasReleased', 'RightBumperWasReleased'],
        ['RightStickButton', 'RightStickButton'],
        ['RightStickButtonWasPressed', 'RightStickButtonWasPressed'],
        ['RightStickButtonWasReleased', 'RightStickButtonWasReleased'],
        ['RightStickX', 'RightStickX'],
        ['RightStickY', 'RightStickY'],
        ['RightTrigger', 'RightTrigger'],
        ['Share', 'Share'],
        ['ShareWasPressed', 'ShareWasPressed'],
        ['ShareWasReleased', 'ShareWasReleased'],
        ['Square', 'Square'],
        ['SquareWasPressed', 'SquareWasPressed'],
        ['SquareWasReleased', 'SquareWasReleased'],
        ['Start', 'Start'],
        ['StartWasPressed', 'StartWasPressed'],
        ['StartWasReleased', 'StartWasReleased'],
        ['Touchpad', 'Touchpad'],
        ['TouchpadWasPressed', 'TouchpadWasPressed'],
        ['TouchpadWasReleased', 'TouchpadWasReleased'],
        ['TouchpadFinger1', 'TouchpadFinger1'],
        ['TouchpadFinger2', 'TouchpadFinger2'],
        ['TouchpadFinger1X', 'TouchpadFinger1X'],
        ['TouchpadFinger1Y', 'TouchpadFinger1Y'],
        ['TouchpadFinger2X', 'TouchpadFinger2X'],
        ['TouchpadFinger2Y', 'TouchpadFinger2Y'],
        ['Triangle', 'Triangle'],
        ['TriangleWasPressed', 'TriangleWasPressed'],
        ['TriangleWasReleased', 'TriangleWasReleased'],
        ['X', 'X'],
        ['XWasPressed', 'XWasPressed'],
        ['XWasReleased', 'XWasReleased'],
        ['Y', 'Y'],
        ['YWasPressed', 'YWasPressed'],
        ['YWasReleased', 'YWasReleased'],
    ];
    this.setOutput(true); // no type, for compatibility
    this.appendDummyInput()
        .appendField(createGamepadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['A', 'Returns true if the A button is pressed.'],
        ['AWasPressed', 'Returns true if the A button was pressed since the last call of this block.'],
        ['AWasReleased', 'Returns true if the A button was released since the last call of this block.'],
        ['AtRest', 'Returns true if all analog sticks and triggers are in their rest position.'],
        ['B', 'Returns true if the B button is pressed.'],
        ['BWasPressed', 'Returns true if the B button was pressed since the last call of this block.'],
        ['BWasReleased', 'Returns true if the B button was released since the last call of this block.'],
        ['Back', 'Returns true if the Back button is pressed.'],
        ['BackWasPressed', 'Returns true if the Back button was pressed since the last call of this block.'],
        ['BackWasReleased', 'Returns true if the Back button was released since the last call of this block.'],
        ['Circle', 'Returns true if the Circle button is pressed.'],
        ['CircleWasPressed', 'Returns true if the Circle button was pressed since the last call of this block.'],
        ['CircleWasReleased', 'Returns true if the Circle button was released since the last call of this block.'],
        ['Cross', 'Returns true if the Cross button is pressed.'],
        ['CrossWasPressed', 'Returns true if the Cross button was pressed since the last call of this block.'],
        ['CrossWasReleased', 'Returns true if the Cross button was released since the last call of this block.'],
        ['DpadDown', 'Returns true if the dpad down button is pressed.'],
        ['DpadDownWasPressed', 'Returns true if the dpad down button was pressed since the last call of this block.'],
        ['DpadDownWasReleased', 'Returns true if the dpad down button was released since the last call of this block.'],
        ['DpadLeft', 'Returns true if the dpad left button is pressed.'],
        ['DpadLeftWasPressed', 'Returns true if the dpad left button was pressed since the last call of this block.'],
        ['DpadLeftWasReleased', 'Returns true if the dpad left button was released since the last call of this block.'],
        ['DpadRight', 'Returns true if the dpad right button is pressed.'],
        ['DpadRightWasPressed', 'Returns true if the dpad right button was pressed since the last call of this block.'],
        ['DpadRightWasReleased', 'Returns true if the dpad right button was released since the last call of this block.'],
        ['DpadUp', 'Returns true if the dpad up button is pressed.'],
        ['DpadUpWasPressed', 'Returns true if the dpad up button was pressed since the last call of this block.'],
        ['DpadUpWasReleased', 'Returns true if the dpad up button was released since the last call of this block.'],
        ['Guide', 'Returns true if the Guide button is pressed. ' +
            'The Guide button is often the large button in the middle of the controller.'],
        ['GuideWasPressed', 'Returns true if the Guide button was pressed since the last call of this block. ' +
            'The Guide button is often the large button in the middle of the controller.'],
        ['GuideWasReleased', 'Returns true if the Guide button was released since the last call of this block. ' +
            'The Guide button is often the large button in the middle of the controller.'],
        ['LeftBumper', 'Returns true if the left bumper is pressed.'],
        ['LeftBumperWasPressed', 'Returns true if the left bumper was pressed since the last call of this block.'],
        ['LeftBumperWasReleased', 'Returns true if the left bumper was released since the last call of this block.'],
        ['LeftStickButton', 'Returns true if the left stick button is pressed.'],
        ['LeftStickButtonWasPressed', 'Returns true if the left stick button was pressed since the last call of this block.'],
        ['LeftStickButtonWasReleased', 'Returns true if the left stick button was released since the last call of this block.'],
        ['LeftStickX', 'Returns a numeric value between -1.0 and +1.0 representing the left analog stick horizontal axis value.'],
        ['LeftStickY', 'Returns a numeric value between -1.0 and +1.0 representing the left analog stick vertical axis value.'],
        ['LeftTrigger', 'Returns a numeric value between 0.0 and +1.0 representing the left trigger value.'],
        ['Options', 'Returns true if the Options button is pressed.'],
        ['OptionsWasPressed', 'Returns true if the Options button was pressed since the last call of this block.'],
        ['OptionsWasReleased', 'Returns true if the Options button was released since the last call of this block.'],
        ['PS', 'Returns true if the PS button is pressed.'],
        ['PSWasPressed', 'Returns true if the PS button was pressed since the last call of this block.'],
        ['PSWasReleased', 'Returns true if the PS button was released since the last call of this block.'],
        ['RightBumper', 'Returns true if the right bumper is pressed.'],
        ['RightBumperWasPressed', 'Returns true if the right bumper was pressed since the last call of this block.'],
        ['RightBumperWasReleased', 'Returns true if the right bumper was released since the last call of this block.'],
        ['RightStickButton', 'Returns true if the right stick button is pressed.'],
        ['RightStickButtonWasPressed', 'Returns true if the right stick button was pressed since the last call of this block.'],
        ['RightStickButtonWasReleased', 'Returns true if the right stick button was released since the last call of this block.'],
        ['RightStickX', 'Returns a numeric value between -1.0 and +1.0 representing the right analog stick horizontal axis value.'],
        ['RightStickY', 'Returns a numeric value between -1.0 and +1.0 representing the right analog stick vertical axis value .'],
        ['RightTrigger', 'Returns a numeric value between 0.0 and +1.0 representing the right trigger value.'],
        ['Share', 'Returns true if the Share button is pressed.'],
        ['ShareWasPressed', 'Returns true if the Share button was pressed since the last call of this block.'],
        ['ShareWasReleased', 'Returns true if the Share button was released since the last call of this block.'],
        ['Square', 'Returns true if the Square button is pressed.'],
        ['SquareWasPressed', 'Returns true if the Square button was pressed since the last call of this block.'],
        ['SquareWasReleased', 'Returns true if the Square button was released since the last call of this block.'],
        ['Start', 'Returns true if the Start button is pressed.'],
        ['StartWasPressed', 'Returns true if the Start button was pressed since the last call of this block.'],
        ['StartWasReleased', 'Returns true if the Start button was released since the last call of this block.'],
        ['Touchpad', 'Returns true if the Touchpad button is pressed.'],
        ['TouchpadWasPressed', 'Returns true if the Touchpad button was pressed since the last call of this block.'],
        ['TouchpadWasReleased', 'Returns true if the Touchpad button was released since the last call of this block.'],
        ['TouchpadFinger1', 'Returns true if the Touchpad is tracking finger ID # 1.'],
        ['TouchpadFinger2', 'Returns true if the Touchpad is tracking finger ID # 2.'],
        ['TouchpadFinger1X', 'Returns  a numeric value between -1.0 and +1.0 representing the horizontal axis value.'],
        ['TouchpadFinger1Y', 'Returns  a numeric value between -1.0 and +1.0 representing the vertical axis value.'],
        ['TouchpadFinger2X', 'Returns  a numeric value between -1.0 and +1.0 representing the horizontal axis value.'],
        ['TouchpadFinger2Y', 'Returns  a numeric value between -1.0 and +1.0 representing the vertical axis value.'],
        ['Triangle', 'Returns true if the Triangle button is pressed.'],
        ['TriangleWasPressed', 'Returns true if the Triangle button was pressed since the last call of this block.'],
        ['TriangleWasReleased', 'Returns true if the Triangle button was released since the last call of this block.'],
        ['X', 'Returns true if the X button is pressed.'],
        ['XWasPressed', 'Returns true if the X button was pressed since the last call of this block.'],
        ['XWasReleased', 'Returns true if the X button was released since the last call of this block.'],
        ['Y', 'Returns true if the Y button is pressed.'],
        ['YWasPressed', 'Returns true if the Y button was pressed since the last call of this block.'],
        ['YWasReleased', 'Returns true if the Y button was released since the last call of this block.'],
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

Blockly.JavaScript['gamepad_getProperty'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code = identifier + '.get' + property + '()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['gamepad_getProperty'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var property = block.getFieldValue('PROP');
  var code;
  switch (property) {
    case 'A':
      code = 'a';
      break;
    case 'AWasPressed':
      code = 'a_was_pressed()';
      break;
    case 'AWasReleased':
      code = 'a_was_released()';
      break;
    case 'AtRest':
      code = 'atRest()';
      break;
    case 'B':
      code = 'b';
      break;
    case 'BWasPressed':
      code = 'b_was_pressed()';
      break;
    case 'BWasReleased':
      code = 'b_was_released()';
      break;
    case 'Back':
      code = 'back';
      break;
    case 'BackWasPressed':
      code = 'back_was_pressed()';
      break;
    case 'BackWasReleased':
      code = 'back_was_released()';
      break;
    case 'Circle':
      code = 'circle';
      break;
    case 'CircleWasPressed':
      code = 'circle_was_pressed()';
      break;
    case 'CircleWasReleased':
      code = 'circle_was_released()';
      break;
    case 'Cross':
      code = 'cross';
      break;
    case 'CrossWasPressed':
      code = 'cross_was_pressed()';
      break;
    case 'CrossWasReleased':
      code = 'cross_was_released()';
      break;
    case 'DpadDown':
      code = 'dpad_down';
      break;
    case 'DpadDownWasPressed':
      code = 'dpad_down_was_pressed()';
      break;
    case 'DpadDownWasReleased':
      code = 'dpad_down_was_released()';
      break;
    case 'DpadLeft':
      code = 'dpad_left';
      break;
    case 'DpadLeftWasPressed':
      code = 'dpad_left_was_pressed()';
      break;
    case 'DpadLeftWasReleased':
      code = 'dpad_left_was_released()';
      break;
    case 'DpadRight':
      code = 'dpad_right';
      break;
    case 'DpadRightWasPressed':
      code = 'dpad_right_was_pressed()';
      break;
    case 'DpadRightWasReleased':
      code = 'dpad_right_was_released()';
      break;
    case 'DpadUp':
      code = 'dpad_up';
      break;
    case 'DpadUpWasPressed':
      code = 'dpad_up_was_pressed()';
      break;
    case 'DpadUpWasReleased':
      code = 'dpad_up_was_released()';
      break;
    case 'Guide':
      code = 'guide';
      break;
    case 'GuideWasPressed':
      code = 'guide_was_pressed()';
      break;
    case 'GuideWasReleased':
      code = 'guide_was_released()';
      break;
    case 'LeftBumper':
      code = 'left_bumper';
      break;
    case 'LeftBumperWasPressed':
      code = 'left_bumper_was_pressed()';
      break;
    case 'LeftBumperWasReleased':
      code = 'left_bumper_was_released()';
      break;
    case 'LeftStickButton':
      code = 'left_stick_button';
      break;
    case 'LeftStickButtonWasPressed':
      code = 'left_stick_button_was_pressed()';
      break;
    case 'LeftStickButtonWasReleased':
      code = 'left_stick_button_was_released()';
      break;
    case 'LeftStickX':
      code = 'left_stick_x';
      break;
    case 'LeftStickXWasPressed':
      code = 'left_stick_x_was_pressed()';
      break;
    case 'LeftStickXWasReleased':
      code = 'left_stick_x_was_released()';
      break;
    case 'LeftStickY':
      code = 'left_stick_y';
      break;
    case 'LeftStickYWasPressed':
      code = 'left_stick_y_was_pressed()';
      break;
    case 'LeftStickYWasReleased':
      code = 'left_stick_y_was_released()';
      break;
    case 'LeftTrigger':
      code = 'left_trigger';
      break;
    case 'LeftTriggerWasPressed':
      code = 'left_trigger_was_pressed()';
      break;
    case 'LeftTriggerWasReleased':
      code = 'left_trigger_was_released()';
      break;
    case 'Options':
      code = 'options';
      break;
    case 'OptionsWasPressed':
      code = 'options_was_pressed()';
      break;
    case 'OptionsWasReleased':
      code = 'options_was_released()';
      break;
    case 'PS':
      code = 'ps';
      break;
    case 'PSWasPressed':
      code = 'ps_was_pressed()';
      break;
    case 'PSWasReleased':
      code = 'ps_was_released()';
      break;
    case 'RightBumper':
      code = 'right_bumper';
      break;
    case 'RightBumperWasPressed':
      code = 'right_bumper_was_pressed()';
      break;
    case 'RightBumperWasReleased':
      code = 'right_bumper_was_released()';
      break;
    case 'RightStickButton':
      code = 'right_stick_button';
      break;
    case 'RightStickButtonWasPressed':
      code = 'right_stick_button_was_pressed()';
      break;
    case 'RightStickButtonWasReleased':
      code = 'right_stick_button_was_released()';
      break;
    case 'RightStickX':
      code = 'right_stick_x';
      break;
    case 'RightStickXWasPressed':
      code = 'right_stick_x_was_pressed()';
      break;
    case 'RightStickXWasReleased':
      code = 'right_stick_x_was_released()';
      break;
    case 'RightStickY':
      code = 'right_stick_y';
      break;
    case 'RightStickYWasPressed':
      code = 'right_stick_y_was_pressed()';
      break;
    case 'RightStickYWasReleased':
      code = 'right_stick_y_was_released()';
      break;
    case 'RightTrigger':
      code = 'right_trigger';
      break;
    case 'RightTriggerWasPressed':
      code = 'right_trigger_was_pressed()';
      break;
    case 'RightTriggerWasReleased':
      code = 'right_trigger_was_released()';
      break;
    case 'Share':
      code = 'share';
      break;
    case 'ShareWasPressed':
      code = 'share_was_pressed()';
      break;
    case 'ShareWasReleased':
      code = 'share_was_released()';
      break;
    case 'Square':
      code = 'square';
      break;
    case 'SquareWasPressed':
      code = 'square_was_pressed()';
      break;
    case 'SquareWasReleased':
      code = 'square_was_released()';
      break;
    case 'Start':
      code = 'start';
      break;
    case 'StartWasPressed':
      code = 'start_was_pressed()';
      break;
    case 'StartWasReleased':
      code = 'start_was_released()';
      break;
    case 'Touchpad':
      code = 'touchpad';
      break;
    case 'TouchpadWasPressed':
      code = 'touchpad_was_pressed()';
      break;
    case 'TouchpadWasReleased':
      code = 'touchpad_was_released()';
      break;
    case 'TouchpadFinger1':
      code = 'touchpad_finger_1';
      break;
    case 'TouchpadFinger2':
      code = 'touchpad_finger_2';
      break;
    case 'TouchpadFinger1X':
      code = 'touchpad_finger_1_x';
      break;
    case 'TouchpadFinger1Y':
      code = 'touchpad_finger_1_y';
      break;
    case 'TouchpadFinger2X':
      code = 'touchpad_finger_2_x';
      break;
    case 'TouchpadFinger2Y':
      code = 'touchpad_finger_2_y';
      break;
    case 'Triangle':
      code = 'triangle';
      break;
    case 'TriangleWasPressed':
      code = 'triangle_was_pressed()';
      break;
    case 'TriangleWasReleased':
      code = 'triangle_was_released()';
      break;
    case 'X':
      code = 'x';
      break;
    case 'XWasPressed':
      code = 'x_was_pressed()';
      break;
    case 'XWasReleased':
      code = 'x_was_released()';
      break;
    case 'Y':
      code = 'y';
      break;
    case 'YWasPressed':
      code = 'y_was_pressed()';
      break;
    case 'YWasReleased':
      code = 'y_was_released()';
      break;
    default:
      throw 'Unexpected property ' + property + ' (gamepad_getProperty).';
  }
  var code = identifier + '.' + code;
  if (code.endsWith(')')) { // atRest() is a method.
    return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
  }
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['gamepad_getProperty_Boolean'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['A', 'A'],
        ['AWasPressed', 'AWasPressed'],
        ['AWasReleased', 'AWasReleased'],
        ['AtRest', 'AtRest'],
        ['B', 'B'],
        ['BWasPressed', 'BWasPressed'],
        ['BWasReleased', 'BWasReleased'],
        ['Back', 'Back'],
        ['BackWasPressed', 'BackWasPressed'],
        ['BackWasReleased', 'BackWasReleased'],
        ['Circle', 'Circle'],
        ['CircleWasPressed', 'CircleWasPressed'],
        ['CircleWasReleased', 'CircleWasReleased'],
        ['Cross', 'Cross'],
        ['CrossWasPressed', 'CrossWasPressed'],
        ['CrossWasReleased', 'CrossWasReleased'],
        ['DpadDown', 'DpadDown'],
        ['DpadDownWasPressed', 'DpadDownWasPressed'],
        ['DpadDownWasReleased', 'DpadDownWasReleased'],
        ['DpadLeft', 'DpadLeft'],
        ['DpadLeftWasPressed', 'DpadLeftWasPressed'],
        ['DpadLeftWasReleased', 'DpadLeftWasReleased'],
        ['DpadRight', 'DpadRight'],
        ['DpadRightWasPressed', 'DpadRightWasPressed'],
        ['DpadRightWasReleased', 'DpadRightWasReleased'],
        ['DpadUp', 'DpadUp'],
        ['DpadUpWasPressed', 'DpadUpWasPressed'],
        ['DpadUpWasReleased', 'DpadUpWasReleased'],
        ['Guide', 'Guide'],
        ['GuideWasPressed', 'GuideWasPressed'],
        ['GuideWasReleased', 'GuideWasReleased'],
        ['LeftBumper', 'LeftBumper'],
        ['LeftBumperWasPressed', 'LeftBumperWasPressed'],
        ['LeftBumperWasReleased', 'LeftBumperWasReleased'],
        ['LeftStickButton', 'LeftStickButton'],
        ['LeftStickButtonWasPressed', 'LeftStickButtonWasPressed'],
        ['LeftStickButtonWasReleased', 'LeftStickButtonWasReleased'],
        ['Options', 'Options'],
        ['OptionsWasPressed', 'OptionsWasPressed'],
        ['OptionsWasReleased', 'OptionsWasReleased'],
        ['PS', 'PS'],
        ['PSWasPressed', 'PSWasPressed'],
        ['PSWasReleased', 'PSWasReleased'],
        ['RightBumper', 'RightBumper'],
        ['RightBumperWasPressed', 'RightBumperWasPressed'],
        ['RightBumperWasReleased', 'RightBumperWasReleased'],
        ['RightStickButton', 'RightStickButton'],
        ['RightStickButtonWasPressed', 'RightStickButtonWasPressed'],
        ['RightStickButtonWasReleased', 'RightStickButtonWasReleased'],
        ['Share', 'Share'],
        ['ShareWasPressed', 'ShareWasPressed'],
        ['ShareWasReleased', 'ShareWasReleased'],
        ['Square', 'Square'],
        ['SquareWasPressed', 'SquareWasPressed'],
        ['SquareWasReleased', 'SquareWasReleased'],
        ['Start', 'Start'],
        ['StartWasPressed', 'StartWasPressed'],
        ['StartWasReleased', 'StartWasReleased'],
        ['Touchpad', 'Touchpad'],
        ['TouchpadWasPressed', 'TouchpadWasPressed'],
        ['TouchpadWasReleased', 'TouchpadWasReleased'],
        ['TouchpadFinger1', 'TouchpadFinger1'],
        ['TouchpadFinger2', 'TouchpadFinger2'],
        ['Triangle', 'Triangle'],
        ['TriangleWasPressed', 'TriangleWasPressed'],
        ['TriangleWasReleased', 'TriangleWasReleased'],
        ['X', 'X'],
        ['XWasPressed', 'XWasPressed'],
        ['XWasReleased', 'XWasReleased'],
        ['Y', 'Y'],
        ['YWasPressed', 'YWasPressed'],
        ['YWasReleased', 'YWasReleased'],
    ];
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField(createGamepadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the tooltip closure below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['A', 'Returns true if the A button is pressed.'],
        ['AWasPressed', 'Returns true if the A button was pressed since the last call of this block.'],
        ['AWasReleased', 'Returns true if the A button was released since the last call of this block.'],
        ['AtRest', 'Returns true if all analog sticks and triggers are in their rest position.'],
        ['B', 'Returns true if the B button is pressed.'],
        ['BWasPressed', 'Returns true if the B button was pressed since the last call of this block.'],
        ['BWasReleased', 'Returns true if the B button was released since the last call of this block.'],
        ['Back', 'Returns true if the Back button is pressed.'],
        ['BackWasPressed', 'Returns true if the Back button was pressed since the last call of this block.'],
        ['BackWasReleased', 'Returns true if the Back button was released since the last call of this block.'],
        ['Circle', 'Returns true if the Circle button is pressed.'],
        ['CircleWasPressed', 'Returns true if the Circle button was pressed since the last call of this block.'],
        ['CircleWasReleased', 'Returns true if the Circle button was released since the last call of this block.'],
        ['Cross', 'Returns true if the Cross button is pressed.'],
        ['CrossWasPressed', 'Returns true if the Cross button was pressed since the last call of this block.'],
        ['CrossWasReleased', 'Returns true if the Cross button was released since the last call of this block.'],
        ['DpadDown', 'Returns true if the dpad down button is pressed.'],
        ['DpadDownWasPressed', 'Returns true if the dpad down button was pressed since the last call of this block.'],
        ['DpadDownWasReleased', 'Returns true if the dpad down button was released since the last call of this block.'],
        ['DpadLeft', 'Returns true if the dpad left button is pressed.'],
        ['DpadLeftWasPressed', 'Returns true if the dpad left button was pressed since the last call of this block.'],
        ['DpadLeftWasReleased', 'Returns true if the dpad left button was released since the last call of this block.'],
        ['DpadRight', 'Returns true if the dpad right button is pressed.'],
        ['DpadRightWasPressed', 'Returns true if the dpad right button was pressed since the last call of this block.'],
        ['DpadRightWasReleased', 'Returns true if the dpad right button was released since the last call of this block.'],
        ['DpadUp', 'Returns true if the dpad up button is pressed.'],
        ['DpadUpWasPressed', 'Returns true if the dpad up button was pressed since the last call of this block.'],
        ['DpadUpWasReleased', 'Returns true if the dpad up button was released since the last call of this block.'],
        ['Guide', 'Returns true if the Guide button is pressed. ' +
            'The Guide button is often the large button in the middle of the controller.'],
        ['GuideWasPressed', 'Returns true if the Guide button was pressed since the last call of this block. ' +
            'The Guide button is often the large button in the middle of the controller.'],
        ['GuideWasReleased', 'Returns true if the Guide button was released since the last call of this block. ' +
            'The Guide button is often the large button in the middle of the controller.'],
        ['LeftBumper', 'Returns true if the left bumper is pressed.'],
        ['LeftBumperWasPressed', 'Returns true if the left bumper was pressed since the last call of this block.'],
        ['LeftBumperWasReleased', 'Returns true if the left bumper was released since the last call of this block.'],
        ['LeftStickButton', 'Returns true if the left stick button is pressed.'],
        ['LeftStickButtonWasPressed', 'Returns true if the left stick button was pressed since the last call of this block.'],
        ['LeftStickButtonWasReleased', 'Returns true if the left stick button was released since the last call of this block.'],
        ['Options', 'Returns true if the Options button is pressed.'],
        ['OptionsWasPressed', 'Returns true if the Options button was pressed since the last call of this block.'],
        ['OptionsWasReleased', 'Returns true if the Options button was released since the last call of this block.'],
        ['PS', 'Returns true if the PS button is pressed.'],
        ['PSWasPressed', 'Returns true if the PS button was pressed since the last call of this block.'],
        ['PSWasReleased', 'Returns true if the PS button was released since the last call of this block.'],
        ['RightBumper', 'Returns true if the right bumper is pressed.'],
        ['RightBumperWasPressed', 'Returns true if the right bumper was pressed since the last call of this block.'],
        ['RightBumperWasReleased', 'Returns true if the right bumper was released since the last call of this block.'],
        ['RightStickButton', 'Returns true if the right stick button is pressed.'],
        ['RightStickButtonWasPressed', 'Returns true if the right stick button was pressed since the last call of this block.'],
        ['RightStickButtonWasReleased', 'Returns true if the right stick button was released since the last call of this block.'],
        ['Share', 'Returns true if the Share button is pressed.'],
        ['ShareWasPressed', 'Returns true if the Share button was pressed since the last call of this block.'],
        ['ShareWasReleased', 'Returns true if the Share button was released since the last call of this block.'],
        ['Square', 'Returns true if the Square button is pressed.'],
        ['SquareWasPressed', 'Returns true if the Square button was pressed since the last call of this block.'],
        ['SquareWasReleased', 'Returns true if the Square button was released since the last call of this block.'],
        ['Start', 'Returns true if the Start button is pressed.'],
        ['StartWasPressed', 'Returns true if the Start button was pressed since the last call of this block.'],
        ['StartWasReleased', 'Returns true if the Start button was released since the last call of this block.'],
        ['Touchpad', 'Returns true if the Touchpad button is pressed.'],
        ['TouchpadWasPressed', 'Returns true if the Touchpad button was pressed since the last call of this block.'],
        ['TouchpadWasReleased', 'Returns true if the Touchpad button was released since the last call of this block.'],
        ['TouchpadFinger1', 'Returns true if the Touchpad is tracking finger ID # 1.'],
        ['TouchpadFinger2', 'Returns true if the Touchpad is tracking finger ID # 2.'],
        ['Triangle', 'Returns true if the Triangle button is pressed.'],
        ['TriangleWasPressed', 'Returns true if the Triangle button was pressed since the last call of this block.'],
        ['TriangleWasReleased', 'Returns true if the Triangle button was released since the last call of this block.'],
        ['X', 'Returns true if the X button is pressed.'],
        ['XWasPressed', 'Returns true if the X button was pressed since the last call of this block.'],
        ['XWasReleased', 'Returns true if the X button was released since the last call of this block.'],
        ['Y', 'Returns true if the Y button is pressed.'],
        ['YWasPressed', 'Returns true if the Y button was pressed since the last call of this block.'],
        ['YWasReleased', 'Returns true if the Y button was released since the last call of this block.'],
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

Blockly.JavaScript['gamepad_getProperty_Boolean'] =
    Blockly.JavaScript['gamepad_getProperty'];

Blockly.FtcJava['gamepad_getProperty_Boolean'] =
    Blockly.FtcJava['gamepad_getProperty'];


Blockly.Blocks['gamepad_getProperty_Number'] = {
  init: function() {
    var PROPERTY_CHOICES = [
        ['LeftStickX', 'LeftStickX'],
        ['LeftStickY', 'LeftStickY'],
        ['LeftTrigger', 'LeftTrigger'],
        ['RightStickX', 'RightStickX'],
        ['RightStickY', 'RightStickY'],
        ['RightTrigger', 'RightTrigger'],
        ['TouchpadFinger1X', 'TouchpadFinger1X'],
        ['TouchpadFinger1Y', 'TouchpadFinger1Y'],
        ['TouchpadFinger2X', 'TouchpadFinger2X'],
        ['TouchpadFinger2Y', 'TouchpadFinger2Y'],
    ];
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createGamepadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(new Blockly.FieldDropdown(PROPERTY_CHOICES), 'PROP');
    this.setColour(getPropertyColor);
    // Assign 'this' to a variable for use in the closures below.
    var thisBlock = this;
    var TOOLTIPS = [
        ['LeftStickX', 'Returns a numeric value between -1.0 and +1.0 representing the left analog stick horizontal axis value.'],
        ['LeftStickY', 'Returns a numeric value between -1.0 and +1.0 representing the left analog stick vertical axis value.'],
        ['LeftTrigger', 'Returns a numeric value between 0.0 and +1.0 representing the left trigger value.'],
        ['RightStickX', 'Returns a numeric value between -1.0 and +1.0 representing the right analog stick horizontal axis value.'],
        ['RightStickY', 'Returns a numeric value between -1.0 and +1.0 representing the right analog stick vertical axis value .'],
        ['RightTrigger', 'Returns a numeric value between 0.0 and +1.0 representing the right trigger value.'],
        ['TouchpadFinger1X', 'Returns  a numeric value between -1.0 and +1.0 representing the horizontal axis value.'],
        ['TouchpadFinger1Y', 'Returns  a numeric value between -1.0 and +1.0 representing the vertical axis value.'],
        ['TouchpadFinger2X', 'Returns  a numeric value between -1.0 and +1.0 representing the horizontal axis value.'],
        ['TouchpadFinger2Y', 'Returns  a numeric value between -1.0 and +1.0 representing the vertical axis value.'],
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
        case 'LeftStickX':
        case 'LeftStickY':
        case 'LeftTrigger':
        case 'RightStickX':
        case 'RightStickY':
        case 'RightTrigger':
        case 'TouchpadFinger1X':
        case 'TouchpadFinger1Y':
        case 'TouchpadFinger2X':
        case 'TouchpadFinger2Y':
          return 'float';
        default:
          throw 'Unexpected property ' + property + ' (gamepad_getProperty_Number getOutputType).';
      }
    };
  }
};

Blockly.JavaScript['gamepad_getProperty_Number'] =
    Blockly.JavaScript['gamepad_getProperty'];

Blockly.FtcJava['gamepad_getProperty_Number'] =
    Blockly.FtcJava['gamepad_getProperty'];

Blockly.Blocks['gamepad_rumble_with1'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGamepadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('rumble'));
    this.appendValueInput('MILLISECONDS').setCheck('Number')
        .appendField('duration')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Rumble the gamepad\'s first rumble motor at maximum power for the given amount of milliseconds.');
    this.getFtcJavaInputType = function(inputName) {
      if (inputName == 'MILLISECONDS') {
        return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['gamepad_rumble_with1'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var millis = Blockly.JavaScript.valueToCode(
      block, 'MILLISECONDS', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.rumble_with1(' + millis + ');\n';
};

Blockly.FtcJava['gamepad_rumble_with1'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var millis = Blockly.FtcJava.valueToCode(
      block, 'MILLISECONDS', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.rumble(' + millis + ');\n';
};

Blockly.Blocks['gamepad_rumble_with3'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGamepadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('rumble'));
    this.appendValueInput('RUMBLE_1').setCheck('Number')
        .appendField('rumble1')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('RUMBLE_2').setCheck('Number')
        .appendField('rumble2')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MILLISECONDS').setCheck('Number')
        .appendField('duration')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Rumble the gamepad at a fixed rumble power for the given amount of milliseconds.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MILLISECONDS':
          return 'int';
        case 'RUMBLE_1':
        case 'RUMBLE_2':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['gamepad_rumble_with3'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var rumble1 = Blockly.JavaScript.valueToCode(
      block, 'RUMBLE_1', Blockly.JavaScript.ORDER_COMMA);
  var rumble2 = Blockly.JavaScript.valueToCode(
      block, 'RUMBLE_2', Blockly.JavaScript.ORDER_COMMA);
  var millis = Blockly.JavaScript.valueToCode(
      block, 'MILLISECONDS', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.rumble_with3(' + rumble1 + ', ' + rumble2 + ', ' + millis + ');\n';
};

Blockly.FtcJava['gamepad_rumble_with3'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var rumble1 = Blockly.FtcJava.valueToCode(
      block, 'RUMBLE_1', Blockly.FtcJava.ORDER_COMMA);
  var rumble2 = Blockly.FtcJava.valueToCode(
      block, 'RUMBLE_2', Blockly.FtcJava.ORDER_COMMA);
  var millis = Blockly.FtcJava.valueToCode(
      block, 'MILLISECONDS', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.rumble(' + rumble1 + ', ' + rumble2 + ', ' + millis + ');\n';
};

Blockly.Blocks['gamepad_stopRumble'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGamepadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('stopRumble'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Cancel the currently running rumble effect, if any.');
  }
};

Blockly.JavaScript['gamepad_stopRumble'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.stopRumble();\n';
};

Blockly.FtcJava['gamepad_stopRumble'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.stopRumble();\n';
};

Blockly.Blocks['gamepad_rumbleBlips'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGamepadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('rumbleBlips'));
    this.appendValueInput('COUNT').setCheck('Number')
        .appendField('count')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Rumble the gamepad for a certain number of "blips" using predetermined blip timing.');
    this.getFtcJavaInputType = function(inputName) {
      if (inputName == 'COUNT') {
        return 'int';
      }
      return '';
    };
  }
};

Blockly.JavaScript['gamepad_rumbleBlips'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var count = Blockly.JavaScript.valueToCode(
      block, 'COUNT', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.rumbleBlips(' + count + ');\n';
};

Blockly.FtcJava['gamepad_rumbleBlips'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var count = Blockly.FtcJava.valueToCode(
      block, 'COUNT', Blockly.FtcJava.ORDER_NONE);
  return identifier + '.rumbleBlips(' + count + ');\n';
};

Blockly.Blocks['gamepad_isRumbling'] = {
  init: function() {
    this.setOutput(true, 'Boolean');
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGamepadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('isRumbling'));
    this.setColour(functionColor);
    this.setTooltip('Returns an educated guess about whether there is a rumble ' +
        'action ongoing on this gamepad.');
  }
};

Blockly.JavaScript['gamepad_isRumbling'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.isRumbling()';
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.FtcJava['gamepad_isRumbling'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var code = identifier + '.isRumbling()';
  return [code, Blockly.FtcJava.ORDER_FUNCTION_CALL];
};

Blockly.Blocks['gamepad_runRumbleEffect'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGamepadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('runRumbleEffect'));
    this.appendValueInput('RUMBLE_EFFECT').setCheck('Gamepad.RumbleEffect')
        .appendField('rumbleEffect')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Run a rumble effect.');
  }
};

Blockly.JavaScript['gamepad_runRumbleEffect'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var rumbleEffect = Blockly.JavaScript.valueToCode(
      block, 'RUMBLE_EFFECT', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.runRumbleEffect(' + rumbleEffect + ');\n';
};

Blockly.FtcJava['gamepad_runRumbleEffect'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var rumbleEffect = Blockly.FtcJava.valueToCode(
      block, 'RUMBLE_EFFECT', Blockly.FtcJava.ORDER_MEMBER);
  return identifier + '.runRumbleEffect(' + rumbleEffect + ');\n';
};

Blockly.Blocks['gamepad_RUMBLE_DURATION_CONTINUOUS'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('Gamepad'))
        .appendField('.')
        .appendField(createNonEditableField('RUMBLE_DURATION_CONTINUOUS'));
    this.setColour(getPropertyColor);
    this.setTooltip('Duration indicating continuous rumbling.');
    this.getFtcJavaOutputType = function() {
      return 'int';
    };
  }
};

Blockly.JavaScript['gamepad_RUMBLE_DURATION_CONTINUOUS'] = function(block) {
  return ['-1', Blockly.JavaScript.ORDER_UNARY_NEGATION];
};

Blockly.FtcJava['gamepad_RUMBLE_DURATION_CONTINUOUS'] = function(block) {
  var code = 'Gamepad.RUMBLE_DURATION_CONTINUOUS';
  Blockly.FtcJava.generateImport_('Gamepad');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

// LED Effect

Blockly.Blocks['gamepad_setLedColor'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGamepadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('setLedColor'));
    this.appendValueInput('RED').setCheck('Number')
        .appendField('red')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('GREEN').setCheck('Number')
        .appendField('green')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('BLUE').setCheck('Number')
        .appendField('blue')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.appendValueInput('MILLISECONDS').setCheck('Number')
        .appendField('duration')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Set the LED to a certain color (r,g,b) for a certain duration.');
    this.getFtcJavaInputType = function(inputName) {
      switch (inputName) {
        case 'MILLISECONDS':
          return 'int';
        case 'RED':
        case 'GREEN':
        case 'BLUE':
          return 'double';
      }
      return '';
    };
  }
};

Blockly.JavaScript['gamepad_setLedColor'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var red = Blockly.JavaScript.valueToCode(
      block, 'RED', Blockly.JavaScript.ORDER_COMMA);
  var green = Blockly.JavaScript.valueToCode(
      block, 'GREEN', Blockly.JavaScript.ORDER_COMMA);
  var blue = Blockly.JavaScript.valueToCode(
      block, 'BLUE', Blockly.JavaScript.ORDER_COMMA);
  var millis = Blockly.JavaScript.valueToCode(
      block, 'MILLISECONDS', Blockly.JavaScript.ORDER_COMMA);
  return identifier + '.setLedColor(' +
      red + ', ' + green + ', ' + blue + ', ' + millis + ');\n';
};

Blockly.FtcJava['gamepad_setLedColor'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var red = Blockly.FtcJava.valueToCode(
      block, 'RED', Blockly.FtcJava.ORDER_COMMA);
  var green = Blockly.FtcJava.valueToCode(
      block, 'GREEN', Blockly.FtcJava.ORDER_COMMA);
  var blue = Blockly.FtcJava.valueToCode(
      block, 'BLUE', Blockly.FtcJava.ORDER_COMMA);
  var millis = Blockly.FtcJava.valueToCode(
      block, 'MILLISECONDS', Blockly.FtcJava.ORDER_COMMA);
  return identifier + '.setLedColor(' + red + ', ' + green + ', ' + blue + ', ' + millis + ');\n';
};

Blockly.Blocks['gamepad_runLedEffect'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGamepadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('runLedEffect'));
    this.appendValueInput('LED_EFFECT').setCheck('Gamepad.LedEffect')
        .appendField('ledEffect')
        .setAlign(Blockly.ALIGN_RIGHT);
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Run an led effect.');
  }
};

Blockly.JavaScript['gamepad_runLedEffect'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var ledEffect = Blockly.JavaScript.valueToCode(
      block, 'LED_EFFECT', Blockly.JavaScript.ORDER_NONE);
  return identifier + '.runLedEffect(' + ledEffect + ');\n';
};

Blockly.FtcJava['gamepad_runLedEffect'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  var ledEffect = Blockly.FtcJava.valueToCode(
      block, 'LED_EFFECT', Blockly.FtcJava.ORDER_MEMBER);
  return identifier + '.runLedEffect(' + ledEffect + ');\n';
};

Blockly.Blocks['gamepad_LED_DURATION_CONTINUOUS'] = {
  init: function() {
    this.setOutput(true, 'Number');
    this.appendDummyInput()
        .appendField(createNonEditableField('Gamepad'))
        .appendField('.')
        .appendField(createNonEditableField('LED_DURATION_CONTINUOUS'));
    this.setColour(getPropertyColor);
    this.setTooltip('Duration indicating continuous LED effect.');
    this.getFtcJavaOutputType = function() {
      return 'int';
    };
  }
};

Blockly.JavaScript['gamepad_LED_DURATION_CONTINUOUS'] = function(block) {
  return ['-1', Blockly.JavaScript.ORDER_UNARY_NEGATION];
};

Blockly.FtcJava['gamepad_LED_DURATION_CONTINUOUS'] = function(block) {
  var code = 'Gamepad.LED_DURATION_CONTINUOUS';
  Blockly.FtcJava.generateImport_('Gamepad');
  return [code, Blockly.FtcJava.ORDER_MEMBER];
};

Blockly.Blocks['gamepad_resetEdgeDetection'] = {
  init: function() {
    this.appendDummyInput()
        .appendField('call')
        .appendField(createGamepadDropdown(), 'IDENTIFIER')
        .appendField('.')
        .appendField(createNonEditableField('resetEdgeDetection'));
    this.setPreviousStatement(true);
    this.setNextStatement(true);
    this.setColour(functionColor);
    this.setTooltip('Clears any remembered presses and releases of buttons.');
  }
};

Blockly.JavaScript['gamepad_resetEdgeDetection'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.resetEdgeDetection();\n';
};

Blockly.FtcJava['gamepad_resetEdgeDetection'] = function(block) {
  var identifier = block.getFieldValue('IDENTIFIER');
  return identifier + '.resetEdgeDetection();\n';
};

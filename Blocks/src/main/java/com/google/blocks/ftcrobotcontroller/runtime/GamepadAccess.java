/*
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

package com.google.blocks.ftcrobotcontroller.runtime;

import android.webkit.JavascriptInterface;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Gamepad.LedEffect;
import com.qualcomm.robotcore.hardware.Gamepad.RumbleEffect;

/**
 * A class that provides JavaScript access to a {@link Gamepad}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class GamepadAccess extends Access {
  private final Gamepad gamepad;

  GamepadAccess(BlocksOpMode blocksOpMode, String identifier, Gamepad gamepad) {
    super(blocksOpMode, identifier, identifier);
    this.gamepad = gamepad;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "left_stick_x")
  public float getLeftStickX() {
    try {
      startBlockExecution(BlockType.GETTER, ".LeftStickX");
      if (gamepad != null) {
        return gamepad.left_stick_x;
      }
      return 0f;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "left_stick_y")
  public float getLeftStickY() {
    try {
      startBlockExecution(BlockType.GETTER, ".LeftStickY");
      if (gamepad != null) {
        return gamepad.left_stick_y;
      }
      return 0f;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "right_stick_x")
  public float getRightStickX() {
    try {
      startBlockExecution(BlockType.GETTER, ".RightStickX");
      if (gamepad != null) {
        return gamepad.right_stick_x;
      }
      return 0f;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "right_stick_y")
  public float getRightStickY() {
    try {
      startBlockExecution(BlockType.GETTER, ".RightStickY");
      if (gamepad != null) {
        return gamepad.right_stick_y;
      }
      return 0f;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "dpad_up")
  public boolean getDpadUp() {
    try {
      startBlockExecution(BlockType.GETTER, ".DpadUp");
      if (gamepad != null) {
        return gamepad.dpad_up;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "dpadUpWasPressed")
  public boolean getDpadUpWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".DpadUpWasPressed");
      if (gamepad != null) {
        return gamepad.dpadUpWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "dpadUpWasReleased")
  public boolean getDpadUpWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".DpadUpWasReleased");
      if (gamepad != null) {
        return gamepad.dpadUpWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "dpad_down")
  public boolean getDpadDown() {
    try {
      startBlockExecution(BlockType.GETTER, ".DpadDown");
      if (gamepad != null) {
        return gamepad.dpad_down;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "dpadDownWasPressed")
  public boolean getDpadDownWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".DpadDownWasPressed");
      if (gamepad != null) {
        return gamepad.dpadDownWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "dpadDownWasReleased")
  public boolean getDpadDownWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".DpadDownWasReleased");
      if (gamepad != null) {
        return gamepad.dpadDownWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "dpad_left")
  public boolean getDpadLeft() {
    try {
      startBlockExecution(BlockType.GETTER, ".DpadLeft");
      if (gamepad != null) {
        return gamepad.dpad_left;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "dpadLeftWasPressed")
  public boolean getDpadLeftWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".DpadLeftWasPressed");
      if (gamepad != null) {
        return gamepad.dpadLeftWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "dpadLeftWasReleased")
  public boolean getDpadLeftWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".DpadLeftWasReleased");
      if (gamepad != null) {
        return gamepad.dpadLeftWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "dpad_right")
  public boolean getDpadRight() {
    try {
      startBlockExecution(BlockType.GETTER, ".DpadRight");
      if (gamepad != null) {
        return gamepad.dpad_right;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "dpadRightWasPressed")
  public boolean getDpadRightWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".DpadRightWasPressed");
      if (gamepad != null) {
        return gamepad.dpadRightWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "dpadRightWasReleased")
  public boolean getDpadRightWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".DpadRightWasReleased");
      if (gamepad != null) {
        return gamepad.dpadRightWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "a")
  public boolean getA() {
    try {
      startBlockExecution(BlockType.GETTER, ".A");
      if (gamepad != null) {
        return gamepad.a;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "aWasPressed")
  public boolean getAWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".AWasPressed");
      if (gamepad != null) {
        return gamepad.aWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "aWasReleased")
  public boolean getAWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".AWasReleased");
      if (gamepad != null) {
        return gamepad.aWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "b")
  public boolean getB() {
    try {
      startBlockExecution(BlockType.GETTER, ".B");
      if (gamepad != null) {
        return gamepad.b;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "bWasPressed")
  public boolean getBWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".BWasPressed");
      if (gamepad != null) {
        return gamepad.bWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "bWasReleased")
  public boolean getBWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".BWasReleased");
      if (gamepad != null) {
        return gamepad.bWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "x")
  public boolean getX() {
    try {
      startBlockExecution(BlockType.GETTER, ".X");
      if (gamepad != null) {
        return gamepad.x;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "xWasPressed")
  public boolean getXWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".XWasPressed");
      if (gamepad != null) {
        return gamepad.xWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "xWasReleased")
  public boolean getXWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".XWasReleased");
      if (gamepad != null) {
        return gamepad.xWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "y")
  public boolean getY() {
    try {
      startBlockExecution(BlockType.GETTER, ".Y");
      if (gamepad != null) {
        return gamepad.y;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "yWasPressed")
  public boolean getYWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".YWasPressed");
      if (gamepad != null) {
        return gamepad.yWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "yWasReleased")
  public boolean getYWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".YWasReleased");
      if (gamepad != null) {
        return gamepad.yWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "guide")
  public boolean getGuide() {
    try {
      startBlockExecution(BlockType.GETTER, ".Guide");
      if (gamepad != null) {
        return gamepad.guide;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "guideWasPressed")
  public boolean getGuideWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".GuideWasPressed");
      if (gamepad != null) {
        return gamepad.guideWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "guideWasReleased")
  public boolean getGuideWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".GuideWasReleased");
      if (gamepad != null) {
        return gamepad.guideWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "start")
  public boolean getStart() {
    try {
      startBlockExecution(BlockType.GETTER, ".Start");
      if (gamepad != null) {
        return gamepad.start;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "startWasPressed")
  public boolean getStartWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".StartWasPressed");
      if (gamepad != null) {
        return gamepad.startWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "startWasReleased")
  public boolean getStartWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".StartWasReleased");
      if (gamepad != null) {
        return gamepad.startWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "back")
  public boolean getBack() {
    try {
      startBlockExecution(BlockType.GETTER, ".Back");
      if (gamepad != null) {
        return gamepad.back;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "backWasPressed")
  public boolean getBackWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".BackWasPressed");
      if (gamepad != null) {
        return gamepad.backWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "backWasReleased")
  public boolean getBackWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".BackWasReleased");
      if (gamepad != null) {
        return gamepad.backWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "left_bumper")
  public boolean getLeftBumper() {
    try {
      startBlockExecution(BlockType.GETTER, ".LeftBumper");
      if (gamepad != null) {
        return gamepad.left_bumper;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "leftBumperWasPressed")
  public boolean getLeftBumperWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".LeftBumperWasPressed");
      if (gamepad != null) {
        return gamepad.leftBumperWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "leftBumperWasReleased")
  public boolean getLeftBumperWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".LeftBumperWasReleased");
      if (gamepad != null) {
        return gamepad.leftBumperWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "right_bumper")
  public boolean getRightBumper() {
    try {
      startBlockExecution(BlockType.GETTER, ".RightBumper");
      if (gamepad != null) {
        return gamepad.right_bumper;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "rightBumperWasPressed")
  public boolean getRightBumperWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".RightBumperWasPressed");
      if (gamepad != null) {
        return gamepad.rightBumperWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "rightBumperWasReleased")
  public boolean getRightBumperWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".RightBumperWasReleased");
      if (gamepad != null) {
        return gamepad.rightBumperWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "left_stick_button")
  public boolean getLeftStickButton() {
    try {
      startBlockExecution(BlockType.GETTER, ".LeftStickButton");
      if (gamepad != null) {
        return gamepad.left_stick_button;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "leftStickButtonWasPressed")
  public boolean getLeftStickButtonWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".LeftStickButtonWasPressed");
      if (gamepad != null) {
        return gamepad.leftStickButtonWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "leftStickButtonWasReleased")
  public boolean getLeftStickButtonWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".LeftStickButtonWasReleased");
      if (gamepad != null) {
        return gamepad.leftStickButtonWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "right_stick_button")
  public boolean getRightStickButton() {
    try {
      startBlockExecution(BlockType.GETTER, ".RightStickButton");
      if (gamepad != null) {
        return gamepad.right_stick_button;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "rightStickButtonWasPressed")
  public boolean getRightStickButtonWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".RightStickButtonWasPressed");
      if (gamepad != null) {
        return gamepad.rightStickButtonWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "rightStickButtonWasReleased")
  public boolean getRightStickButtonWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".RightStickButtonWasReleased");
      if (gamepad != null) {
        return gamepad.rightStickButtonWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "left_trigger")
  public float getLeftTrigger() {
    try {
      startBlockExecution(BlockType.GETTER, ".LeftTrigger");
      if (gamepad != null) {
        return gamepad.left_trigger;
      }
      return 0f;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "right_trigger")
  public float getRightTrigger() {
    try {
      startBlockExecution(BlockType.GETTER, ".RightTrigger");
      if (gamepad != null) {
        return gamepad.right_trigger;
      }
      return 0f;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "atRest")
  public boolean getAtRest() {
    try {
      startBlockExecution(BlockType.GETTER, ".AtRest");
      if (gamepad != null) {
        return gamepad.atRest();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "circle")
  public boolean getCircle() {
    try {
      startBlockExecution(BlockType.GETTER, ".Circle");
      if (gamepad != null) {
        return gamepad.circle;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "circleWasPressed")
  public boolean getCircleWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".CircleWasPressed");
      if (gamepad != null) {
        return gamepad.circleWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "circleWasReleased")
  public boolean getCircleWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".CircleWasReleased");
      if (gamepad != null) {
        return gamepad.circleWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "cross")
  public boolean getCross() {
    try {
      startBlockExecution(BlockType.GETTER, ".Cross");
      if (gamepad != null) {
        return gamepad.cross;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "crossWasPressed")
  public boolean getCrossWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".CrossWasPressed");
      if (gamepad != null) {
        return gamepad.crossWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "crossWasReleased")
  public boolean getCrossWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".CrossWasReleased");
      if (gamepad != null) {
        return gamepad.crossWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "options")
  public boolean getOptions() {
    try {
      startBlockExecution(BlockType.GETTER, ".Options");
      if (gamepad != null) {
        return gamepad.options;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "optionsWasPressed")
  public boolean getOptionsWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".OptionsWasPressed");
      if (gamepad != null) {
        return gamepad.optionsWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "optionsWasReleased")
  public boolean getOptionsWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".OptionsWasReleased");
      if (gamepad != null) {
        return gamepad.optionsWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "ps")
  public boolean getPS() {
    try {
      startBlockExecution(BlockType.GETTER, ".PS");
      if (gamepad != null) {
        return gamepad.ps;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "psWasPressed")
  public boolean getPSWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".PSWasPressed");
      if (gamepad != null) {
        return gamepad.psWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "psWasReleased")
  public boolean getPSWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".PSWasReleased");
      if (gamepad != null) {
        return gamepad.psWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "share")
  public boolean getShare() {
    try {
      startBlockExecution(BlockType.GETTER, ".Share");
      if (gamepad != null) {
        return gamepad.share;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "shareWasPressed")
  public boolean getShareWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".ShareWasPressed");
      if (gamepad != null) {
        return gamepad.shareWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "shareWasReleased")
  public boolean getShareWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".ShareWasReleased");
      if (gamepad != null) {
        return gamepad.shareWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "square")
  public boolean getSquare() {
    try {
      startBlockExecution(BlockType.GETTER, ".Square");
      if (gamepad != null) {
        return gamepad.square;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "squareWasPressed")
  public boolean getSquareWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".SquareWasPressed");
      if (gamepad != null) {
        return gamepad.squareWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "squareWasReleased")
  public boolean getSquareWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".SquareWasReleased");
      if (gamepad != null) {
        return gamepad.squareWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "touchpad")
  public boolean getTouchpad() {
    try {
      startBlockExecution(BlockType.GETTER, ".Touchpad");
      if (gamepad != null) {
        return gamepad.touchpad;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "touchpadWasPressed")
  public boolean getTouchpadWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".TouchpadWasPressed");
      if (gamepad != null) {
        return gamepad.touchpadWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "touchpadWasReleased")
  public boolean getTouchpadWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".TouchpadWasReleased");
      if (gamepad != null) {
        return gamepad.touchpadWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "touchpad_finger_1")
  public boolean getTouchpadFinger1() {
    try {
      startBlockExecution(BlockType.GETTER, ".TouchpadFinger1");
      if (gamepad != null) {
        return gamepad.touchpad_finger_1;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "touchpad_finger_1_x")
  public float getTouchpadFinger1X() {
    try {
      startBlockExecution(BlockType.GETTER, ".TouchpadFinger1X");
      if (gamepad != null) {
        return gamepad.touchpad_finger_1_x;
      }
      return 0f;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "touchpad_finger_1_y")
  public float getTouchpadFinger1Y() {
    try {
      startBlockExecution(BlockType.GETTER, ".TouchpadFinger1Y");
      if (gamepad != null) {
        return gamepad.touchpad_finger_1_y;
      }
      return 0f;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "touchpad_finger_2")
  public boolean getTouchpadFinger2() {
    try {
      startBlockExecution(BlockType.GETTER, ".TouchpadFinger2");
      if (gamepad != null) {
        return gamepad.touchpad_finger_2;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "touchpad_finger_2_x")
  public float getTouchpadFinger2X() {
    try {
      startBlockExecution(BlockType.GETTER, ".TouchpadFinger2X");
      if (gamepad != null) {
        return gamepad.touchpad_finger_2_x;
      }
      return 0f;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "touchpad_finger_2_y")
  public float getTouchpadFinger2Y() {
    try {
      startBlockExecution(BlockType.GETTER, ".TouchpadFinger2Y");
      if (gamepad != null) {
        return gamepad.touchpad_finger_2_y;
      }
      return 0f;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, fieldName = "triangle")
  public boolean getTriangle() {
    try {
      startBlockExecution(BlockType.GETTER, ".Triangle");
      if (gamepad != null) {
        return gamepad.triangle;
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "triangleWasPressed")
  public boolean getTriangleWasPressed() {
    try {
      startBlockExecution(BlockType.GETTER, ".TriangleWasPressed");
      if (gamepad != null) {
        return gamepad.triangleWasPressed();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "triangleWasReleased")
  public boolean getTriangleWasReleased() {
    try {
      startBlockExecution(BlockType.GETTER, ".TriangleWasReleased");
      if (gamepad != null) {
        return gamepad.triangleWasReleased();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "resetEdgeDetection")
  public void resetEdgeDetection() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".resetEdgeDetection");
      if (gamepad != null) {
        gamepad.resetEdgeDetection();
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "rumble")
  public void rumble_with1(int millis) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".rumble");
      if (gamepad != null) {
        gamepad.rumble(millis);
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "rumble")
  public void rumble_with3(double rumble1, double rumble2, int millis) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".rumble");
      if (gamepad != null) {
        gamepad.rumble(rumble1, rumble2, millis);
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "stopRumble")
  public void stopRumble() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".stopRumble");
      if (gamepad != null) {
        gamepad.stopRumble();
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "rumbleBlips")
  public void rumbleBlips(int count) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".rumbleBlips");
      if (gamepad != null) {
        gamepad.rumbleBlips(count);
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "isRumbling")
  public boolean isRumbling() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".isRumbling");
      if (gamepad != null) {
        return gamepad.isRumbling();
      }
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  private RumbleEffect checkRumbleEffect(Object rumbleEffectArg) {
    return checkArg(rumbleEffectArg, RumbleEffect.class, "rumbleEffect");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "runRumbleEffect")
  public void runRumbleEffect(Object rumbleEffectArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".runRumbleEffect");
      RumbleEffect rumbleEffect = checkRumbleEffect(rumbleEffectArg);
      if (gamepad != null && rumbleEffect != null) {
        gamepad.runRumbleEffect(rumbleEffect);
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "setLedColor")
  public void setLedColor(double r, double g, double b, int millis) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setLedColor");
      if (gamepad != null) {
        gamepad.setLedColor(r, g, b, millis);
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  private LedEffect checkLedEffect(Object ledEffectArg) {
    return checkArg(ledEffectArg, LedEffect.class, "ledEffect");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {Gamepad.class}, methodName = "runLedEffect")
  public void runLedEffect(Object ledEffectArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".runLedEffect");
      LedEffect ledEffect = checkLedEffect(ledEffectArg);
      if (gamepad != null && ledEffect != null) {
        gamepad.runLedEffect(ledEffect);
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

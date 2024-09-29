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
import com.google.blocks.ftcrobotcontroller.hardware.HardwareItem;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Servo.Direction;
import com.qualcomm.robotcore.hardware.ServoImplEx;

/**
 * A class that provides JavaScript access to a {@link Servo}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class ServoAccess extends HardwareAccess<ServoImplEx> {
  private final ServoImplEx servo;

  ServoAccess(BlocksOpMode blocksOpMode, HardwareItem hardwareItem, HardwareMap hardwareMap) {
    super(blocksOpMode, hardwareItem, hardwareMap, ServoImplEx.class);
    this.servo = hardwareDevice;
  }

  // From com.qualcomm.robotcore.hardware.Servo

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Servo.class, methodName = "setDirection")
  public void setDirection(String directionString) {
    try {
      startBlockExecution(BlockType.SETTER, ".Direction");
      Direction direction = checkArg(directionString, Direction.class, "");
      if (direction != null) {
        servo.setDirection(direction);
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
  @Block(classes = Servo.class, methodName = "getDirection")
  public String getDirection() {
    try {
      startBlockExecution(BlockType.GETTER, ".Direction");
      Direction direction = servo.getDirection();
      if (direction != null) {
        return direction.toString();
      }
      return "";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Servo.class, methodName = "setPosition")
  public void setPosition(double position) {
    try {
      startBlockExecution(BlockType.SETTER, ".Position");
      servo.setPosition(position);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Servo.class, methodName = "getPosition")
  public double getPosition() {
    try {
      startBlockExecution(BlockType.GETTER, ".Position");
      return servo.getPosition();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Servo.class, methodName = "scaleRange")
  public void scaleRange(double min, double max) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".scaleRange");
      servo.scaleRange(min, max);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {PwmControl.class}, methodName = "setPwmEnable")
  public void setPwmEnable() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setPwmEnable");
      servo.setPwmEnable();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {PwmControl.class}, methodName = "setPwmDisable")
  public void setPwmDisable() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setPwmDisable");
      servo.setPwmDisable();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {PwmControl.class}, methodName = "isPwmEnabled")
  public boolean isPwmEnabled() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".isPwmEnabled");
      return servo.isPwmEnabled();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

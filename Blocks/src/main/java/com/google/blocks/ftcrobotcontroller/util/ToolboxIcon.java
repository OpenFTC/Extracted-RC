/*
 * Copyright 2017 Google LLC
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

package com.google.blocks.ftcrobotcontroller.util;

/**
 * An enum to represent a toolbox icon.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public enum ToolboxIcon {
  ACCELERATION_SENSOR("AccelerationSensor-icon"),
  ANALOG_INPUT("AnalogInput-icon"),
  COLOR_SENSOR("ColorSensor-icon"),
  COMPASS_SENSOR("CompassSensor-icon"),
  CR_SERVO("CRServo-icon"),
  DC_MOTOR("DcMotor-icon"),
  DIGITAL_CHANNEL("DigitalChannel-icon"),
  ELAPSED_TIME("ElapsedTime-icon"),
  GAMEPAD("Gamepad-icon"),
  GYRO_SENSOR("GyroSensor-icon"),
  IR_SEEKER_SENSOR("IrSeekerSensor-icon"),
  LED("LED-icon"),
  LIGHT_SENSOR("LightSensor-icon"),
  LINEAR_OPMODE("LinearOpMode-icon"),
  OCTOQUAD("OctoQuad-icon"),
  OPTICAL_DISTANCE_SENSOR("OpticalDistanceSensor-icon"),
  SERVO("Servo-icon"),
  SERVO_CONTROLLER("ServoController-icon"),
  TOUCH_SENSOR("TouchSensor-icon"),
  ULTRASONIC_SENSOR("UltrasonicSensor-icon"),
  VOLTAGE_SENSOR("VoltageSensor-icon");

  public final String cssClass;

  ToolboxIcon(String cssClass) {
    this.cssClass = cssClass;
  }
}

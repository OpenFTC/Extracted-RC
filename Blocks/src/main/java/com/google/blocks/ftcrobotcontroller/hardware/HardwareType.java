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

package com.google.blocks.ftcrobotcontroller.hardware;

import com.google.blocks.ftcrobotcontroller.util.ToolboxFolder;
import com.google.blocks.ftcrobotcontroller.util.ToolboxIcon;
import com.google.blocks.ftcrobotcontroller.util.ToolboxUtil;
import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuadImpl;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxEmbeddedIMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.maxbotix.MaxSonarI2CXL;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsAnalogOpticalDistanceSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsTouchSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cCompassSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.hardware.sparkfun.SparkFunLEDStick;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.AccelerationSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DigitalChannelImpl;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.configuration.BuiltInConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.ConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.ConfigurationTypeManager;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.hardware.configuration.ServoFlavor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.ServoConfigurationType;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * An enum to represent a specific type of hardware.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public enum HardwareType {
  ACCELERATION_SENSOR( // See acceleration_sensor.js
      "createAccelerationSensorDropdown", "accelerationSensor", "AsAccelerationSensor", "_AccelerationSensor",
      ToolboxFolder.SENSORS, "AccelerationSensor", ToolboxIcon.ACCELERATION_SENSOR,
      AccelerationSensor.class,
      BuiltInConfigurationType.ACCELEROMETER.getXmlTag()),
  ANALOG_INPUT( // See analog_input.js
      "createAnalogInputDropdown", "analogInput", "AsAnalogInput", "_AnalogInput",
      ToolboxFolder.OTHER, "AnalogInput", ToolboxIcon.ANALOG_INPUT,
      AnalogInput.class,
      ConfigurationTypeManager.getXmlTag(AnalogInput.class)),
  BNO055IMU( // see bno055imu.js
      "createBNO055IMUDropdown", "bno055imu", "AsBNO055IMU", "_IMU_BNO055",
      ToolboxFolder.SENSORS, "IMU-BNO055 (legacy)", null, // No toolbox icon yet.
      BNO055IMUImpl.class,
      ConfigurationTypeManager.getXmlTag(AdafruitBNO055IMU.class),
      ConfigurationTypeManager.getXmlTag(LynxEmbeddedIMU.class)),
  COLOR_RANGE_SENSOR( // see color_range_sensor.js
      // The blockTypePRefix is lynxI2cColorRangeSensor for backwards compatibility.
      "createColorRangeSensorDropdown", "lynxI2cColorRangeSensor", "AsREVColorRangeSensor", "_REV_ColorRangeSensor",
      ToolboxFolder.SENSORS, "REV Color/Range Sensor", ToolboxIcon.COLOR_SENSOR,
      ColorRangeSensor.class,
      BuiltInConfigurationType.LYNX_COLOR_SENSOR.getXmlTag(),
      ConfigurationTypeManager.getXmlTag(RevColorSensorV3.class)),
  COLOR_SENSOR( // see color_sensor.js
      "createColorSensorDropdown", "colorSensor", "AsColorSensor", "_ColorSensor",
      ToolboxFolder.SENSORS, "ColorSensor", ToolboxIcon.COLOR_SENSOR,
      ColorSensor.class,
      BuiltInConfigurationType.COLOR_SENSOR.getXmlTag(),
      BuiltInConfigurationType.ADAFRUIT_COLOR_SENSOR.getXmlTag(),
      BuiltInConfigurationType.LYNX_COLOR_SENSOR.getXmlTag(),
      ConfigurationTypeManager.getXmlTag(RevColorSensorV3.class)),
  COMPASS_SENSOR( // see compass_sensor.js
      "createCompassSensorDropdown", "compassSensor", "AsCompassSensor", "_CompassSensor",
      ToolboxFolder.SENSORS, "CompassSensor", ToolboxIcon.COMPASS_SENSOR,
      CompassSensor.class,
      BuiltInConfigurationType.COMPASS.getXmlTag()),
  CR_SERVO( // see cr_servo.js
      "createCRServoDropdown", "crServo", "AsCRServo", "_CRServo",
      ToolboxFolder.ACTUATORS, "CRServo", ToolboxIcon.CR_SERVO,
      CRServo.class,
      getContinuousServoXmlTags()),
  DC_MOTOR( // see dc_motor.js
      "createDcMotorDropdown", "dcMotor", "AsDcMotor", "_DcMotor",
      ToolboxFolder.ACTUATORS, "DcMotor", ToolboxIcon.DC_MOTOR,
      DcMotor.class,
      getMotorXmlTags()),
  DIGITAL_CHANNEL( // see digital_channel.js
      "createDigitalChannelDropdown", "digitalChannel", "AsDigitalChannel", "_DigitalChannel",
      ToolboxFolder.OTHER, "DigitalChannel", ToolboxIcon.DIGITAL_CHANNEL,
      DigitalChannel.class,
      ConfigurationTypeManager.getXmlTag(DigitalChannelImpl.class)),
  DISTANCE_SENSOR( // see distance_sensor.js
      "createDistanceSensorDropdown", "distanceSensor", "AsDistanceSensor", "_DistanceSensor",
      ToolboxFolder.SENSORS, "DistanceSensor", ToolboxIcon.ULTRASONIC_SENSOR, // Need to make artwork but the ultrasonic sensor is close to what we want.
      DistanceSensor.class,
      BuiltInConfigurationType.LYNX_COLOR_SENSOR.getXmlTag(),
      ConfigurationTypeManager.getXmlTag(Rev2mDistanceSensor.class),
      ConfigurationTypeManager.getXmlTag(RevColorSensorV3.class)),
  GYRO_SENSOR( // see gyro_sensor.js
      "createGyroSensorDropdown", "gyroSensor", "AsGyroSensor", "_GyroSensor",
      ToolboxFolder.SENSORS, "GyroSensor", ToolboxIcon.GYRO_SENSOR,
      GyroSensor.class,
      BuiltInConfigurationType.GYRO.getXmlTag()),
  HUSKY_LENS( // see husky_lens.js
      "createHuskyLensDropdown", "huskyLens", "AsHuskyLens", "_HuskyLens",
      ToolboxFolder.SENSORS, "HuskyLens", null, // No toolbox icon.
      HuskyLens.class,
      ConfigurationTypeManager.getXmlTag(HuskyLens.class)),
  IMU( // see imu.js
      "createImuDropdown", "imu", "AsIMU", "_IMU",
      ToolboxFolder.SENSORS, "IMU", null, // No toolbox icon yet.
      IMU.class,
      LynxConstants.EMBEDDED_BNO055_IMU_XML_TAG,
      LynxConstants.EMBEDDED_BHI260AP_IMU_XML_TAG,
      ConfigurationTypeManager.getXmlTag(AdafruitBNO055IMU.class)),
  IR_SEEKER_SENSOR( // see ir_seeker_sensor.js
      "createIrSeekerSensorDropdown", "irSeekerSensor", "AsIrSeekerSensor", "_IrSeekerSensor",
      ToolboxFolder.SENSORS, "IrSeekerSensor", ToolboxIcon.IR_SEEKER_SENSOR,
      IrSeekerSensor.class,
      BuiltInConfigurationType.IR_SEEKER.getXmlTag(),
      BuiltInConfigurationType.IR_SEEKER_V3.getXmlTag()),
  LED( // see led.js
      "createLedDropdown", "led", "AsLED", "_LED",
      ToolboxFolder.OTHER, "LED", ToolboxIcon.LED,
      LED.class,
      ConfigurationTypeManager.getXmlTag(LED.class)),
  LIGHT_SENSOR( // see light_sensor.js
      "createLightSensorDropdown", "lightSensor", "AsLightSensor", "_LightSensor",
      ToolboxFolder.SENSORS, "LightSensor", ToolboxIcon.LIGHT_SENSOR,
      LightSensor.class,
      BuiltInConfigurationType.LIGHT_SENSOR.getXmlTag()),
  LIMELIGHT_3A( // see limelight_3a.js
      "createLimelight3ADropdown", "limelight3A", "AsLimelight3A", "_Limelight3A",
      ToolboxFolder.SENSORS, "Limelight3A", null, // No toolbox icon.
      Limelight3A.class,
      BuiltInConfigurationType.ETHERNET_OVER_USB_DEVICE.getXmlTag()),
  LYNX_MODULE( // No blocks provided.
      null, null, "AsREVModule", "_REV_Module",
      null, null, null,
      LynxModule.class,
      BuiltInConfigurationType.LYNX_MODULE.getXmlTag()),
  MAX_SONAR_I2CXL( // see max_sonar_i2cxl.js
      "createMaxSonarI2CXLDropdown", "maxSonarI2CXL", "AsMaxSonarI2CXL", "_MaxSonarI2CXL",
      ToolboxFolder.SENSORS, "MaxSonarI2CXL", null, // No toolbox icon.
      MaxSonarI2CXL.class,
      ConfigurationTypeManager.getXmlTag(MaxSonarI2CXL.class)),
  MR_I2C_COMPASS_SENSOR( // see mr_i2c_compass_sensor.js
      "createMrI2cCompassSensorDropdown", "mrI2cCompassSensor", "AsMrI2cCompassSensor", "_MR_I2cCompassSensor",
      ToolboxFolder.SENSORS, "MrI2cCompassSensor", ToolboxIcon.COMPASS_SENSOR,
      ModernRoboticsI2cCompassSensor.class,
      ConfigurationTypeManager.getXmlTag(ModernRoboticsI2cCompassSensor.class)),
  MR_I2C_RANGE_SENSOR( // see mr_i2c_range_sensor.js
      "createMrI2cRangeSensorDropdown", "mrI2cRangeSensor", "AsMrI2cRangeSensor", "_MR_I2cRangeSensor",
      ToolboxFolder.SENSORS, "MrI2cRangeSensor", ToolboxIcon.OPTICAL_DISTANCE_SENSOR,
      ModernRoboticsI2cRangeSensor.class,
      ConfigurationTypeManager.getXmlTag(ModernRoboticsI2cRangeSensor.class)),
  OCTOQUAD( // see octoquad.js
      "createOctoQuadDropdown", "octoquad", "AsOctoQuad", "_OctoQuad",
      ToolboxFolder.SENSORS, "OctoQuad", ToolboxIcon.OCTOQUAD,
      OctoQuadImpl.class,
      ConfigurationTypeManager.getXmlTag(OctoQuadImpl.class)),
  OPTICAL_DISTANCE_SENSOR( // see optical_distance_sensor.js
      "createOpticalDistanceSensorDropdown", "opticalDistanceSensor", "AsOpticalDistanceSensor", "_OpticalDistanceSensor",
      ToolboxFolder.SENSORS, "OpticalDistanceSensor", ToolboxIcon.OPTICAL_DISTANCE_SENSOR,
      OpticalDistanceSensor.class,
      ConfigurationTypeManager.getXmlTag(ModernRoboticsAnalogOpticalDistanceSensor.class),
      BuiltInConfigurationType.LYNX_COLOR_SENSOR.getXmlTag(),
      ConfigurationTypeManager.getXmlTag(RevColorSensorV3.class)),
  REV_BLINKIN_LED_DRIVER( // see rev_blinkin_led_driver.js
      "createRevBlinkinLedDriverDropdown", "revBlinkinLedDriver", "AsRevBlinkinLedDriver", "_RevBlinkinLedDriver",
      ToolboxFolder.OTHER, "RevBlinkinLedDriver", ToolboxIcon.LED,
      RevBlinkinLedDriver.class,
      ConfigurationTypeManager.getXmlTag(RevBlinkinLedDriver.class)),
  SERVO( // see servo.js
      "createServoDropdown", "servo", "AsServo", "_Servo",
      ToolboxFolder.ACTUATORS, "Servo", ToolboxIcon.SERVO,
      Servo.class,
      getStandardServoXmlTags()),
  SERVO_CONTROLLER( // see servo_controller.js
      "createServoControllerDropdown", "servoController", "AsServoController", "_ServoController",
      ToolboxFolder.ACTUATORS, "ServoController", ToolboxIcon.SERVO_CONTROLLER,
      ServoController.class,
      BuiltInConfigurationType.LYNX_MODULE.getXmlTag()),
  SPARKFUN_LED_STICK( // see sparkfun_led_stick.js
      "createSparkFunLEDStickDropdown", "sparkFunLEDStick", "AsSparkFunLEDStick", "_SparkFunLEDStick",
      ToolboxFolder.OTHER, "SparkFunLEDStick", null, // No toolbox icon.
      SparkFunLEDStick.class,
      ConfigurationTypeManager.getXmlTag(SparkFunLEDStick.class)),
  SPARKFUN_OTOS( // see sparkfun_otos.js
      "createSparkFunOTOSDropdown", "sparkFunOTOS", "AsSparkFunOTOS", "_SparkFunOTOS",
      ToolboxFolder.SENSORS, "SparkFunOTOS", null, // No toolbox icon.
      SparkFunOTOS.class,
      ConfigurationTypeManager.getXmlTag(SparkFunOTOS.class)),
  TOUCH_SENSOR( // see touch_sensor.js
      "createTouchSensorDropdown", "touchSensor", "AsTouchSensor", "_TouchSensor",
      ToolboxFolder.SENSORS, "TouchSensor", ToolboxIcon.TOUCH_SENSOR,
      TouchSensor.class,
      ConfigurationTypeManager.getXmlTag(ModernRoboticsTouchSensor.class),
      ConfigurationTypeManager.getXmlTag(RevTouchSensor.class)),
  ULTRASONIC_SENSOR( // see ultrasonic_sensor.js
      "createUltrasonicSensorDropdown", "ultrasonicSensor", "AsUltrasonicSensor", "_UltrasonicSensor",
      ToolboxFolder.SENSORS, "UltrasonicSensor", ToolboxIcon.ULTRASONIC_SENSOR,
      UltrasonicSensor.class,
      BuiltInConfigurationType.ULTRASONIC_SENSOR.getXmlTag()),
  VOLTAGE_SENSOR( // see voltage_sensor.js
      "createVoltageSensorDropdown", "voltageSensor", "AsVoltageSensor", "_VoltageSensor",
      ToolboxFolder.SENSORS, "VoltageSensor", ToolboxIcon.VOLTAGE_SENSOR,
      VoltageSensor.class,
      BuiltInConfigurationType.LYNX_MODULE.getXmlTag()),
  WEBCAM_NAME( // No blocks provided.
      null, null, "AsWebcamName", "_WebcamName",
      null, null, null,
      WebcamName.class,
      BuiltInConfigurationType.WEBCAM.getXmlTag());

  private static String[] getMotorXmlTags() {
    List<String> tags = new LinkedList<>();
    for (ConfigurationType type : ConfigurationTypeManager.getInstance().getApplicableConfigTypes(ConfigurationType.DeviceFlavor.MOTOR, null, false)) {
      if (type == BuiltInConfigurationType.NOTHING) continue;
      tags.add(type.getXmlTag());
    }
    tags.add(ConfigurationTypeManager.LEGACY_HD_HEX_MOTOR_TAG);
    String[] result = new String[tags.size()];
    return tags.toArray(result);
  }

  private static String[] getStandardServoXmlTags() {
    List<String> tags = new LinkedList<>();
    for (ConfigurationType type : ConfigurationTypeManager.getInstance().getApplicableConfigTypes(ConfigurationType.DeviceFlavor.SERVO, null, false)) {
      if (type == BuiltInConfigurationType.NOTHING || ((ServoConfigurationType)type).getServoFlavor() != ServoFlavor.STANDARD) continue;
      tags.add(type.getXmlTag());
    }
    String[] result = new String[tags.size()];
    return tags.toArray(result);
  }

  private static String[] getContinuousServoXmlTags() {
    List<String> tags = new LinkedList<>();
    for (ConfigurationType type : ConfigurationTypeManager.getInstance().getApplicableConfigTypes(ConfigurationType.DeviceFlavor.SERVO, null, false)) {
      if (type == BuiltInConfigurationType.NOTHING || ((ServoConfigurationType)type).getServoFlavor() != ServoFlavor.CONTINUOUS) continue;
      tags.add(type.getXmlTag());
    }
    String[] result = new String[tags.size()];
    return tags.toArray(result);
  }

  static final Comparator<HardwareType> BY_TOOLBOX_CATEGORY_NAME = new Comparator<HardwareType>() {
    @Override public int compare(HardwareType h1, HardwareType h2) {
      String s1 = (h1.toolboxCategoryName == null) ? "" : h1.toolboxCategoryName;
      String s2 = (h2.toolboxCategoryName == null) ? "" : h2.toolboxCategoryName;
      return s1.compareToIgnoreCase(s2);
    }
  };

  /**
   * The name of the javascript function which creates a block dropdown showing the names of all
   * hardware items of this HardwareType. The javascript code is produced dynamically in
   * {@link HardwareUtil#fetchJavaScriptForHardware}. This must match the function name used in the
   * appropriate js file.
   */
  public final String createDropdownFunctionName;
  /**
   * The prefix of all block types associated with this HardwareType. The toolbox xml is produced
   * dynamically in {@link ToolboxUtil}. This must match the prefix used in the appropriate js file.
   */
  public final String blockTypePrefix;
  /**
   * The suffix of all JavaScript identifiers for devices of this HardwareType.
   */
  public final String identifierSuffixForJavaScript;
  /**
   * The suffix appended, only if necessary to make them unique, to FtcJava identifiers for devices of this HardwareType.
   */
  public final String identifierSuffixForFtcJava;
  /**
   * The toolbox folder that will contain the toolbox category associated with this HardwareType.
   */
  public final ToolboxFolder toolboxFolder;
  /**
   * The name of the toolbox category associated with this HardwareType.
   */
  public final String toolboxCategoryName;
  /**
   * The toolbox icon enum associated with this HardwareType.
   */
  public final ToolboxIcon toolboxIcon;
  /**
   * The common type shared by all instances of this HardwareType.
   */
  public final Class<? extends HardwareDevice> deviceType;
  /**
   * The xmlTags corresponding to this HardwareType.
   */
  public final String[] xmlTags;

  HardwareType(
      String createDropdownFunctionName, String blockTypePrefix, String identifierSuffixForJavaScript,
      String identifierSuffixForFtcJava,
      ToolboxFolder toolboxFolder, String toolboxCategoryName, ToolboxIcon toolboxIcon,
      Class<? extends HardwareDevice> deviceType,
      String... xmlTags) {
    if (identifierSuffixForJavaScript == null || identifierSuffixForJavaScript.isEmpty()) {
      throw new IllegalArgumentException("identifierSuffixForJavaScript");
    }
    if (identifierSuffixForFtcJava == null || identifierSuffixForFtcJava.isEmpty()) {
      throw new IllegalArgumentException("identifierSuffixForFtcJava");
    }
    this.createDropdownFunctionName = createDropdownFunctionName;
    this.blockTypePrefix = blockTypePrefix;
    this.identifierSuffixForJavaScript = identifierSuffixForJavaScript;
    this.identifierSuffixForFtcJava = identifierSuffixForFtcJava;
    this.toolboxFolder = toolboxFolder;
    this.toolboxCategoryName = toolboxCategoryName;
    this.toolboxIcon = toolboxIcon;
    this.deviceType = deviceType;
    this.xmlTags = xmlTags;
  }

  boolean isContainer() {
    // TODO(lizlooney): if we add more controllers, add them here.
    return deviceType == LynxModule.class ||
        deviceType == ServoController.class;
  }

  public String makeIdentifier(String deviceName) {
    return HardwareItem.makeIdentifier(deviceName) + identifierSuffixForJavaScript;
  }

  public static HardwareType fromIdentifierSuffixForJavaScript(String identifierSuffixForJavaScript) {
    for (HardwareType hardwareType : HardwareType.values()) {
      if (hardwareType.identifierSuffixForJavaScript.equals(identifierSuffixForJavaScript)) {
        return hardwareType;
      }
    }
    return null;
  }
}

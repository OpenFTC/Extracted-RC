/*
 * Copyright (c) 2014, 2015 Qualcomm Technologies Inc
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * (subject to the limitations in the disclaimer below) provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of Qualcomm Technologies Inc nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.qualcomm.robotcore.hardware;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.AnalogSensorConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.DigitalIoDeviceConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.ServoConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.I2cDeviceConfigurationType;
import com.qualcomm.robotcore.util.SerialNumber;

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraManager;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import java.net.InetAddress;
import java.util.List;

@SuppressWarnings("javadoc")
public interface DeviceManager {

  /**
   * Enum of known USB Device Types
   */
  enum UsbDeviceType {
    FTDI_USB_UNKNOWN_DEVICE,
    LYNX_USB_DEVICE,
    /** a camera managed by {@link CameraManager}. See {@link WebcamName} */
    WEBCAM,
    ETHERNET_DEVICE,
    UNKNOWN_DEVICE;

    public static UsbDeviceType from(String string) {
      for (UsbDeviceType type : values()) {
        if (type.toString().equals(string)) {
          return type;
        }
      }
    return UNKNOWN_DEVICE;
    }
  }

  /**
   * Get a listing of currently connected USB devices
   * <p>
   * This method will attempt to open all USB devices that are using an FTDI USB chipset. It will
   * then probe the device to determine if it is a Modern Robotics device. Finally, it will close the
   * device.
   * <p>
   * Because of the opening and closing of devices, it is recommended that this method is not called
   * while any FTDI devices are in use.
   *
   * @return a map of serial numbers to Modern Robotics device types
   * @throws RobotCoreException if unable to open a device
   */
  ScannedDevices scanForUsbDevices() throws RobotCoreException;

  /**
   * Create an instance of a DcMotor
   *
   * @param controller DC Motor controller this motor is attached to
   * @param portNumber physical port number on the controller
   * @param motorType the optional type we know of for this motor
   * @return an instance of a DcMotor
   */
  DcMotor createDcMotor(DcMotorController controller, int portNumber, @NonNull MotorConfigurationType motorType, String name);
  DcMotor createDcMotorEx(DcMotorController controller, int portNumber, @NonNull MotorConfigurationType motorType, String name);

  /**
   * Create an instance of a Servo
   *
   * @param controller Servo controller this servo is attached to
   * @param portNumber physical port number on the controller
   * @return an instance of a Servo
   */
  Servo createServoEx(ServoControllerEx controller, int portNumber, String name, ServoConfigurationType servoType);
  CRServo createCRServoEx(ServoControllerEx controller, int portNumber, String name, ServoConfigurationType servoType);
  List<HardwareDevice> createCustomServoDeviceInstances(ServoControllerEx controller, int portNumber, ServoConfigurationType servoConfigurationType);

  /**
   * @param controller Analog Input Controller Module this device is connected to
   * @return Instances of an Analog Input device
   */
  List<HardwareDevice> createAnalogSensorInstances(AnalogInputController controller, int channel, AnalogSensorConfigurationType type);

  /**
   * @param controller Device Interface Module this device is connected to
   * @param type
   * @return Instances of an Digital Channel device
   */
  List<HardwareDevice> createDigitalDeviceInstances(DigitalChannelController controller, int channel, DigitalIoDeviceConfigurationType type);

  /**
   *
   * @param controller PWM Output Controller Module this device is connected to
   * @return - an instance of an Digital Channel device
   */
  PWMOutput createPwmOutputDevice(PWMOutputController controller, int channel, String name);

  /**
   *
   * @param module I2c Controller Module this device is connected to
   * @param channel the channel it's connected to on the Controller
   * @return an instance of an I2c Channel device
   */
  I2cDeviceSynch createI2cDeviceSynch(RobotCoreLynxModule module, DeviceConfiguration.I2cChannel channel, String name);

  /**
   * @return Instances of a user-defined sensor type.
   */
  List<HardwareDevice> createI2cDeviceInstances(RobotCoreLynxModule lynxModule, DeviceConfiguration.I2cChannel channel, I2cDeviceConfigurationType type, String name);

  /**
   * Creates a Limelight3A.
   */
  HardwareDevice createLimelight3A(SerialNumber serialNumber, @Nullable String name, InetAddress ipAddress);

  /**
   * Creates an instance of a Lynx USB device
   * @param serialNumber
   * @return
   * @throws RobotCoreException
   * @throws InterruptedException
   */
  RobotCoreLynxUsbDevice createLynxUsbDevice(SerialNumber serialNumber, @Nullable String name) throws RobotCoreException, InterruptedException;

  /**
   * Creates a {@link WebcamName} from the indicated serialized contents
   */
  @Nullable WebcamName createWebcamName(SerialNumber serialNumber, String name) throws RobotCoreException, InterruptedException;

  /**
   * Create an instance of a Modern Robotics TouchSensor on a digital controller
   * @param digitalController       controller this device is connected to
   * @param physicalPort            the port number of the device on that controller
   * @param name                    the name of this device in the hardware map
   * @return a TouchSensor
   */
  TouchSensor createMRDigitalTouchSensor(DigitalChannelController digitalController, int physicalPort, String name);

  /**
   * Create an instance of a IrSeekerSensorV3
   * @param module the device is connected to
   * @param channel port number on the Device Interface Module this device is connected to
   * @return an IrSeekerSensor
   */
  IrSeekerSensor createMRI2cIrSeekerSensorV3(RobotCoreLynxModule module, DeviceConfiguration.I2cChannel channel, String name);

  /**
   * Create an instance of a GyroSensor
   * @param module this device is connected to
   * @param channel         i2c connection channel
   * @return a GyroSensor
   */
  GyroSensor createModernRoboticsI2cGyroSensor(RobotCoreLynxModule module, DeviceConfiguration.I2cChannel channel, String name);

  /**
   * Create an instance of a ColorSensor
   * @param module the sensor is connected to
   * @param channel the I2C port on the Device Interface this module is connected to
   * @return a ColorSensor
   */
  ColorSensor createAdafruitI2cColorSensor(RobotCoreLynxModule module, DeviceConfiguration.I2cChannel channel, String name);
  ColorSensor createLynxColorRangeSensor(RobotCoreLynxModule module, DeviceConfiguration.I2cChannel channel, String name);

  /**
   * Create an instance of a ColorSensor
   * @param module this sensor is attached to
   * @param channel the I2C port it's connected to
   * @return a ColorSensor
   */
  ColorSensor createModernRoboticsI2cColorSensor(RobotCoreLynxModule module, DeviceConfiguration.I2cChannel channel, String name);

  /**
   * Create an instance of an LED
   * @param controller Digital Channel Controller this LED is connected to
   * @param channel the digital port it's connected to
   * @return an LED
   */
  LED createLED(DigitalChannelController controller, int channel, String name);
}

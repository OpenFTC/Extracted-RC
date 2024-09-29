/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.qualcomm.robotcore.hardware.configuration;

import android.os.Build;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Device;
import com.qualcomm.robotcore.util.SerialNumber;

import org.firstinspires.ftc.robotcore.internal.system.SystemProperties;

/**
 * LynxConstants contains information regarding the configured size of various aspects of
 * a Lynx module.
 */
@SuppressWarnings("WeakerAccess")
public class LynxConstants
    {
    public static final String TAG = "LynxConstants";

    public static final int DRAGONBOARD_CH_VERSION = 0;

    public static final int MINIMUM_LEGAL_CH_OS_VERSION_CODE = 6; // Corresponds to Control Hub OS 1.1.2
    public static final String MINIMUM_LEGAL_CH_OS_VERSION_STRING = "1.1.2"; // ONLY for display. Do not use in comparisons.

    public static final int MINIMUM_LEGAL_DH_OS_VERSION_CODE = 21; // Corresponds to Driver Hub OS 1.2.0
    public static final String MINIMUM_LEGAL_DH_OS_VERSION_STRING = "1.1.0"; // ONLY for display. Do not use in comparisons.

    private static final String DRAGONBOARD_MODEL = "FIRST Control Hub";
    private static final String EMPTY_STRING = "";
    private static final int ORIGINAL_CH_OS_VERSION_CODE = 1;

    public static final int EXPANSION_HUB_PRODUCT_NUMBER = 0x311152;
    public static final int SERVO_HUB_PRODUCT_NUMBER = 0x111855;

    public static final String SERVO_HUB_INTERFACE_NAME = "SERVO_HUB";

    public static boolean revHubTypeCanHaveImu(int productNumber)
        {
        return productNumber == EXPANSION_HUB_PRODUCT_NUMBER;
        }

    /** Are we running on an Android / Lynx combo device */
    private static Boolean isControlHub;
    public static boolean isRevControlHub()
        {
        if (isControlHub == null)
            {
            isControlHub = SystemProperties.getBoolean("persist.ftcandroid.serialasusb", false);
            }
        return isControlHub;
        }

    /** Returns the version of the Control Hub for the purpose of identifying software compatibility.
     *
     *  Increasing the CH version number in the OS means that new versions of the SDK and AP service
     *  will need to be released. Specifically, AndroidBoard.getInstance must be modified to support
     *  the new version number, and the value of the `org.firstinspires.latestSupportedControlHubVersion`
     *  meta-data tag in the manifests must be increased. */
    public static int getControlHubVersion()
        {
        int version = SystemProperties.getInt("ro.ftcandroid.controlhubversion", -1);
        if (version == -1)
            {
            if (Build.MODEL.equalsIgnoreCase(DRAGONBOARD_MODEL))
                {
                version = DRAGONBOARD_CH_VERSION; // The Dragonboard doesn't have ro.ftcandroid.controlhubversion set.
                }
            }
        return version;
        }

    /** Get the Control Hub OS version. Returns null if OS version property is not set.
     *
     * Value is human-readable, do not attempt to parse.
     * Use {@link #getControlHubOsVersionCode()} ()} to differentiate between OS versions programmatically. */
    @Nullable public static String getControlHubOsVersion()
        {
        String chOsVersion = SystemProperties.get("ro.controlhub.os.version", EMPTY_STRING);
        if(EMPTY_STRING.equals(chOsVersion)) chOsVersion = null;
        return chOsVersion;
        }

    /** Gets the machine-parsable version number of the Control Hub OS */
    public static int getControlHubOsVersionCode()
        {
        // ro.controlhub.os.versionnum was added in the second OS version (1.0.1), so if we don't
        // find it, it's safe to assume that we are looking at the original version of the Control
        // Hub OS.

        // It could also be a Dragonboard, but differences between Control Hub hardware revisions
        // should be handled within the different implementations of AndroidBoard.
        return SystemProperties.getInt("ro.controlhub.os.versionnum", ORIGINAL_CH_OS_VERSION_CODE);
        }

    public static boolean controlHubOsVersionIsObsolete()
        {
        return Device.isRevControlHub() && getControlHubOsVersionCode() < MINIMUM_LEGAL_CH_OS_VERSION_CODE;
        }

    /** Get the Driver Hub OS version. Returns null if OS version property is not set.
     *
     * Value is human-readable, do not attempt to parse.
     * Use {@link #getDriverHubOsVersionCode()} ()} to differentiate between OS versions programmatically. */
    @Nullable public static String getDriverHubOsVersion()
        {
        String dhOsVersion = SystemProperties.get("ro.driverhub.os.version", EMPTY_STRING);
        if(EMPTY_STRING.equals(dhOsVersion)) dhOsVersion = null;
        return dhOsVersion;
        }

    /** Gets the machine-parsable version number of the Driver Hub OS */
    public static int getDriverHubOsVersionCode()
        {
        return SystemProperties.getInt("ro.driverhub.os.versionnum", 0);
        }

    public static boolean isEmbeddedSerialNumber(SerialNumber serialNumber)
        {
        return SERIAL_NUMBER_EMBEDDED.equals(serialNumber);
        }

    public static boolean autorunRobotController()
        {
        return SystemProperties.getBoolean("persist.ftcandroid.autorunrc", false);
        }

    /** Whether to use the tiny little indicator LEDs on the Dragonboard itself for signalling */
    public static boolean useIndicatorLEDS()
        {
        return SystemProperties.getBoolean("persist.ftcandroid.rcuseleds", false);
        }
    public final static int INDICATOR_LED_ROBOT_CONTROLLER_ALIVE = 1;
    public final static int INDICATOR_LED_INVITE_DIALOG_ACTIVE = 2;
    public final static int INDICATOR_LED_BOOT = 4;

    public static final int SERIAL_MODULE_BAUD_RATE = 460800;
    public static final SerialNumber SERIAL_NUMBER_EMBEDDED = SerialNumber.createEmbedded();

    public final static int USB_BAUD_RATE = 460800;
    public final static int LATENCY_TIMER = 1;

    public final static int INITIAL_MOTOR_PORT = 0;
    public final static int INITIAL_SERVO_PORT = 0;

    public final static int NUMBER_OF_MOTORS = 4;
    public final static int NUMBER_OF_SERVO_CHANNELS = 6;
    public final static int NUMBER_OF_PWM_CHANNELS = 4;
    public final static int NUMBER_OF_ANALOG_INPUTS = 4;
    public final static int NUMBER_OF_DIGITAL_IOS = 8;
    public final static int NUMBER_OF_I2C_BUSSES = 4;
    public final static int EMBEDDED_IMU_BUS = 0;

    public final static int DEFAULT_TARGET_POSITION_TOLERANCE = 5;

    public final static int MAX_NUMBER_OF_MODULES = 254;
    public final static int MAX_MODULES_DISCOVER = MAX_NUMBER_OF_MODULES;   // see LynxDiscoveryCommand: parent uses this count, so we must mirror
    public static final int CH_EMBEDDED_MODULE_ADDRESS = 173;
    public final static int MAX_UNRESERVED_MODULE_ADDRESS = 10;
    public final static int DEFAULT_PARENT_MODULE_ADDRESS = 1;              // try to avoid using: query the device instead

    public static final String EMBEDDED_BNO055_IMU_XML_TAG = "LynxEmbeddedIMU";
    public static final String EMBEDDED_BHI260AP_IMU_XML_TAG = "ControlHubImuBHI260AP";

    public static void validateMotorZ(int motorZ)
        {
        if (motorZ < 0 || motorZ >= NUMBER_OF_MOTORS)
            throw new IllegalArgumentException(String.format("invalid motor: %d", motorZ));
        }
    public static void validatePwmChannelZ(int channelZ)
        {
        if (channelZ < 0 || channelZ >= NUMBER_OF_PWM_CHANNELS)
            throw new IllegalArgumentException(String.format("invalid pwm channel: %d", channelZ));
        }
    public static void validateServoChannelZ(int channelZ)
        {
        if (channelZ < 0 || channelZ >= NUMBER_OF_SERVO_CHANNELS)
            throw new IllegalArgumentException(String.format("invalid servo channel: %d", channelZ));
        }
    public static void validateI2cBusZ(int busZ)
        {
        if (busZ < 0 || busZ >= NUMBER_OF_I2C_BUSSES)
            throw new IllegalArgumentException(String.format("invalid i2c bus: %d", busZ));
        }
    public static void validateAnalogInputZ(int analogInputZ)
        {
        if (analogInputZ < 0 || analogInputZ >= NUMBER_OF_ANALOG_INPUTS)
            throw new IllegalArgumentException(String.format("invalid analog input: %d", analogInputZ));
        }
    public static void validateDigitalIOZ(int digitalIOZ)
        {
        if (digitalIOZ < 0 || digitalIOZ >= NUMBER_OF_DIGITAL_IOS)
            throw new IllegalArgumentException(String.format("invalid digital pin: %d", digitalIOZ));
        }

    public static DcMotor.RunMode lynxMotorModeToRunMode(byte lynxMotorMode)
        {
        switch (lynxMotorMode)
            {
            case 0: return DcMotor.RunMode.RUN_WITHOUT_ENCODER;
            case 1: return DcMotor.RunMode.RUN_USING_ENCODER;
            case 2: return DcMotor.RunMode.RUN_TO_POSITION;
            default: throw new IllegalStateException(String.format("illegal mode byte: 0x%02x", lynxMotorMode));
            }
        }
    public static byte runModeToLynxMotorMode(DcMotor.RunMode runMode)
        {
        switch (runMode)
            {
            case RUN_WITHOUT_ENCODER:  return 0;
            case RUN_USING_ENCODER:    return 1;
            case RUN_TO_POSITION:      return 2;
            default: throw new IllegalArgumentException(String.format("illegal mode %s", runMode));
            }
        }

    }

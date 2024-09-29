/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
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

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.R;
import com.qualcomm.robotcore.hardware.ControlSystem;
import com.qualcomm.robotcore.hardware.DeviceManager;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * {@link BuiltInConfigurationType} is an enum representing all the various types of hardware
 * whose semantics and supporting logic is built into the SDK.
 */
public enum BuiltInConfigurationType implements ConfigurationType
    {
        GYRO("Gyro", DeviceFlavor.I2C),
        COMPASS("Compass", null),
        IR_SEEKER("IrSeeker", null),
        LIGHT_SENSOR("LightSensor", null),
        ACCELEROMETER("Accelerometer", null),
        PULSE_WIDTH_DEVICE("PulseWidthDevice", null),
        IR_SEEKER_V3("IrSeekerV3", DeviceFlavor.I2C),
        ULTRASONIC_SENSOR("UltrasonicSensor", null),
        ADAFRUIT_COLOR_SENSOR("AdafruitColorSensor", DeviceFlavor.I2C),
        COLOR_SENSOR("ColorSensor", DeviceFlavor.I2C),    // this is a modern robotics or an NXT color sensor
        LYNX_COLOR_SENSOR("LynxColorSensor", DeviceFlavor.I2C),
        LYNX_USB_DEVICE("LynxUsbDevice", null),
        LYNX_MODULE("LynxModule", null),
        SERVO_HUB("ServoHub", null),
        WEBCAM("Webcam", null),
        ETHERNET_OVER_USB_DEVICE("EthernetDevice", null),
        ROBOT("Robot", null),                 // not an actual config type, but is an XML tag we know about
        NOTHING("Nothing", null),             // in the config UI, NOTHING means no device is attached
        UNKNOWN("<unknown>", null);           // UNKNOWN is never actually used in XML

    private final String xmlTag;
    private final DeviceFlavor deviceFlavor;

    private final Context context = AppUtil.getDefContext();

    private static final List<BuiltInConfigurationType> valuesCache = Collections.unmodifiableList(Arrays.asList(values()));

    /**
     * Pass deviceFlavor if one of the types besides BUILT_IN applies, otherwise pass null.
     */
    BuiltInConfigurationType(String xmlTag, @Nullable DeviceFlavor deviceFlavor)
        {
        this.xmlTag = xmlTag;
        this.deviceFlavor = deviceFlavor;
        }

    public static BuiltInConfigurationType fromXmlTag(String xmlTag)
        {
        for (BuiltInConfigurationType type : valuesCache)
            {
            if (xmlTag.equalsIgnoreCase(type.xmlTag)) return type;
            }
        return UNKNOWN;
        }

    public static ConfigurationType fromString(String toString)
        {
        for (ConfigurationType configType : valuesCache)
            {
            if (toString.equalsIgnoreCase(configType.toString()))
                {
                return configType;
                }
            }
        return BuiltInConfigurationType.UNKNOWN;
        }

    public static ConfigurationType fromUSBDeviceType(DeviceManager.UsbDeviceType type)
        {
        switch (type)
            {
            case LYNX_USB_DEVICE:                               return LYNX_USB_DEVICE;
            case WEBCAM:                                        return WEBCAM;
            default:                                            return UNKNOWN;
            }
        }

    @Override public boolean isDeviceFlavor(DeviceFlavor flavor)
        {
        if (flavor == DeviceFlavor.BUILT_IN)
            {
            return true;
            }
        return flavor == this.deviceFlavor;
        }

    @Override
    public boolean isCompatibleWith(ControlSystem controlSystem)
        {
        return true;
        }

    @NonNull
    @Override public DeviceFlavor getDeviceFlavor()
        {
        if (deviceFlavor != null)
            {
            return deviceFlavor;
            }
        return DeviceFlavor.BUILT_IN;
        }

    @Override @NonNull
    public DeviceManager.UsbDeviceType toUSBDeviceType()
        {
        switch (this)
            {
            case LYNX_USB_DEVICE:           return DeviceManager.UsbDeviceType.LYNX_USB_DEVICE;
            case WEBCAM:                    return DeviceManager.UsbDeviceType.WEBCAM;
            default:                        return DeviceManager.UsbDeviceType.FTDI_USB_UNKNOWN_DEVICE;
            }
        }

    @Override @NonNull
    public String getName()
        {
        switch (this)
            {
            case PULSE_WIDTH_DEVICE:        return context.getString(R.string.configTypePulseWidthDevice);
            case IR_SEEKER_V3:              return context.getString(R.string.configTypeIrSeekerV3);
            case ADAFRUIT_COLOR_SENSOR:     return context.getString(R.string.configTypeAdafruitColorSensor);
            case LYNX_COLOR_SENSOR:         return context.getString(R.string.configTypeLynxColorSensor);
            case LYNX_USB_DEVICE:           return context.getString(R.string.configTypeLynxUSBDevice);
            case LYNX_MODULE:               return context.getString(R.string.configTypeLynxModule);
            case NOTHING:                   return context.getString(R.string.configTypeNothing);
            case WEBCAM:                    return context.getString(R.string.configTypeWebcam);
            case GYRO:                      return context.getString(R.string.configTypeMRGyro);
            case COLOR_SENSOR:              return context.getString(R.string.configTypeMRColorSensor);
            case UNKNOWN:
            default:
                return context.getString(R.string.configTypeUnknown);
            }
        }

    @Override
    public boolean isDeprecated()
        {
        try
            {
            return BuiltInConfigurationType.class.getField(toString()).isAnnotationPresent(Deprecated.class);
            }
        catch (NoSuchFieldException e)
            {
            RobotLog.logStackTrace(e); // This should not be able to happen, ever.
            return false;
            }
        }

    @Override
    public ConfigurationTypeManager.ClassSource getClassSource()
        {
        return ConfigurationTypeManager.ClassSource.APK;
        }

    @Override
    public boolean annotatedClassIsInstantiable()
        {
        return false;
        }

    @Override @NonNull public String getXmlTag()
        {
        return this.xmlTag;
        }
    }

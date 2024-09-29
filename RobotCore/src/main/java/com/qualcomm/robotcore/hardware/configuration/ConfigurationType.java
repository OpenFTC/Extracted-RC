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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.ControlSystem;
import com.qualcomm.robotcore.hardware.DeviceManager;

import java.util.EnumSet;

/**
 * {@link ConfigurationType} instances represent the type of various kinds of hardware
 * device configurations that might exist within the SDK.
 */
public interface ConfigurationType
    {
    enum DeviceFlavor
        {
            BUILT_IN,
            I2C,
            MOTOR,
            ANALOG_SENSOR,
            SERVO,
            DIGITAL_IO,
            ANALOG_OUTPUT,
            ETHERNET_OVER_USB
        }

    /**
     * @return A user-understandable string form of this configuration type
     */
    @NonNull String getName();

    /**
     * @return Whether the type should be presented as deprecated in the user interface
     */
    boolean isDeprecated();

    /**
     * @return The source of the class that this type came from
     */
    @NonNull ConfigurationTypeManager.ClassSource getClassSource();

    boolean annotatedClassIsInstantiable();

    /**
     * @return The XML element tag to be used when serializing configurations of this type
     */
    @NonNull String getXmlTag();

    /**
     * If this configuration type has a corresponding USB device configuration type, returns same;
     * otherwise, returns {@link DeviceManager.UsbDeviceType#FTDI_USB_UNKNOWN_DEVICE FTDI_USB_UNKNOWN_DEVICE}.
     * @return The USB device type that corresponds to this configuration type, if any
     */
    @NonNull DeviceManager.UsbDeviceType toUSBDeviceType();

    /**
     * @return Whether this configuration type is of the indicated flavor
     */
    boolean isDeviceFlavor(DeviceFlavor flavor);

    boolean isCompatibleWith(ControlSystem controlSystem);

    /**
     * Returns the configuration type's most specific flavor.
     * <p>
     * Types defined in BuiltInConfigurationType will only return DeviceFlavor.BUILT_IN
     * if none of the other types apply. If you need to know if this type is defined in the
     * BuiltInConfigurationType enum, use isDeviceFlavor(BUILT_IN).
     * <p>
     * For BuiltInConfigurationType instances that are defined both as a legacy device and as a modern
     * device, this will assume you're asking about the modern variant.
     */
    @NonNull DeviceFlavor getDeviceFlavor();
    }

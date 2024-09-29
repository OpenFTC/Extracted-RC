/*
Copyright (c) 2024 REV Robotics

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of REV Robotics nor the names of its contributors may be used to
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
import com.qualcomm.robotcore.util.SerialNumber;
import org.firstinspires.ftc.robotcore.internal.usb.LynxModuleSerialNumber;

import java.util.List;

public abstract class RhspModuleConfiguration extends ControllerConfiguration<DeviceConfiguration>
    {
    private int parentModuleAddress;

    /** not persisted in XML */
    private @NonNull SerialNumber usbDeviceSerialNumber = SerialNumber.createFake();

    public RhspModuleConfiguration(String name, List<DeviceConfiguration> devices, @NonNull SerialNumber serialNumber, ConfigurationType type)
        {
        super(name, devices, serialNumber, type);
        }

    /**
     * A Control Hub or a USB-connected Expansion Hub is considered a "Parent",
     * and an RS485-connected Expansion Hub is a "Child".
     */
    public abstract boolean isParent();


    public void setModuleAddress(int moduleAddress)
        {
        this.setPort(moduleAddress);
        }
    public int getModuleAddress()
        {
        return this.getPort();
        }

    /**
     * Set the address of the parent module that this module is connected through.
     * <p>
     * If this module *is* this parent module, indicate that by providing this module's own address.
     */
    public void setParentModuleAddress(int parentModuleAddress)
        {
        this.parentModuleAddress = parentModuleAddress;
        }

    /**
     * If this module is a parent, this will return its own address
     */
    public int getParentModuleAddress()
        {
        return this.parentModuleAddress;
        }

    @Override
    public void setPort(int port)
        {
        super.setPort(port);
        setSerialNumber(new LynxModuleSerialNumber(usbDeviceSerialNumber, port));
        }


    public void setUsbDeviceSerialNumber(@NonNull SerialNumber usbDeviceSerialNumber)
        {
        this.usbDeviceSerialNumber = usbDeviceSerialNumber;
        setSerialNumber(new LynxModuleSerialNumber(usbDeviceSerialNumber, getModuleAddress()));
        }

    @Override public void setSerialNumber(@NonNull SerialNumber serialNumber)
        {
        super.setSerialNumber(serialNumber);
        // RobotLog.vv(TAG, "setSerialNumber(%s)", serialNumber);
        }

    public @NonNull SerialNumber getUsbDeviceSerialNumber()
        {
        return usbDeviceSerialNumber;
        }

    /** separate method just to reinforce whose serial number we're retreiving */
    public @NonNull SerialNumber getModuleSerialNumber()
        {
        return getSerialNumber();
        }

    }

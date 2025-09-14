/*
Copyright (c) 2025 AndyMark Inc.

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list
    of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this
    list of conditions and the following disclaimer in the documentation and/or
    other materials provided with the distribution.

3. Neither the name of AndyMark Inc. nor the names of its contributors may be used to
    endorse or promote products derived from this software without specific prior
    written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
DAMAGE.
*/

package com.qualcomm.hardware.andymark;

import org.firstinspires.ftc.robotcore.external.navigation.Axis;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMUNew;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

/**
* AndyMark 9-Axis IMU Driver
*
* Author: Jonathan Lane  
* Organization: AndyMark, Inc.
*
* Description:
* This class provides a hardware abstraction layer for the AndyMark 9-Axis IMU,
* which is based on the Bosch BNO055 sensor. It extends the `BNO055IMUNew` class
* from the FTC SDK and registers as a built-in I2C device for automatic configuration.
*/

@I2cDeviceType
@DeviceProperties(  name = "AndyMark 9-Axis IMU", 
                    description = "AndyMark External IMU", 
                    xmlTag = "AndyMarkIMU",
                    builtIn = true)
public class AndyMarkIMU extends BNO055IMUNew 
{
    /**
    * This constructor is called internally by the FTC SDK.
    */
    public AndyMarkIMU(I2cDeviceSynchSimple deviceClient, boolean deviceClientIsOwned) 
    {
        super(deviceClient, deviceClientIsOwned, BNO055IMU.I2CADDR_DEFAULT);
    }

    /**
    * Returns the name of this device for display in the Driver Station and SDK internals.
    * 
    * @return A user-visible name for the IMU
    */
    @Override public String getDeviceName() 
    {
        return "AndyMark 9-Axis IMU";
    }

    /**
    * Returns the manufacturer of this device.
    *
    * Although AndyMark distributes the hardware, the internal BNO055 chip
    * is manufactured by Bosch and this device is represented internally as a Lynx component.
    * 
    * @return The manufacturer enum representing this device's source
    */
    @Override public Manufacturer getManufacturer() 
    {
        return Manufacturer.Lynx;
    }
}

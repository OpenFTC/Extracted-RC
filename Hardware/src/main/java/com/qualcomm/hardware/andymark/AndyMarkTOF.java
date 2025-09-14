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

import com.qualcomm.hardware.stmicroelectronics.VL53L0X;
import com.qualcomm.robotcore.hardware.ControlSystem;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;

/**
* AndyMark 2m TOF Lidar Driver
*
* Author: Jonathan Lane  
* Organization: AndyMark, Inc.
*
* Description:
* This class provides a hardware abstraction layer for the AndyMark 2-meter Time-of-Flight (TOF) Lidar sensor,
* which is based on the STMicroelectronics VL53L0X. It extends the `VL53L0X` class from the FTC SDK
* and registers as a built-in I2C device for automatic recognition in configuration files.
*
* Features:
* - Provides distance measurements up to 2 meters using laser-based time-of-flight technology.
* - Fully integrates with FTC SDK I2C device handling.
*
* Note:
* Ensure the I2C address and wiring match your configuration. Performance may vary depending
* on lighting conditions and surface reflectivity.
*/

@I2cDeviceType
@DeviceProperties(  name = "AndyMark TOF Lidar", 
                    description = "AndyMark 2m TOF Lidar", 
                    xmlTag = "AndyMarkTOF",
                    builtIn = true)
                    
public class AndyMarkTOF extends VL53L0X 
{
    public AndyMarkTOF(I2cDeviceSynch deviceClient, boolean deviceClientIsOwned)
    {
        super(deviceClient, deviceClientIsOwned);
    }

    /**
    * Returns the name of this device for display in the Driver Station and SDK internals.
    * 
    * @return A user-visible name for the TOF Lidar
    */
    @Override
    public String getDeviceName() 
    {
        return "AndyMark TOF Lidar";
    }
}

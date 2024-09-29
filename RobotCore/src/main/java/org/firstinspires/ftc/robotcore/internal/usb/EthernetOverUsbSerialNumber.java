/*
Copyright (c) 2024 FIRST

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of FIRST nor the names of their contributors may be used to
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
package org.firstinspires.ftc.robotcore.internal.usb;

import com.qualcomm.robotcore.util.SerialNumber;

@SuppressWarnings("WeakerAccess")
public class EthernetOverUsbSerialNumber extends SerialNumber
{
    protected final String ipAddress;
    protected final String iface;
    private final static String SERIAL_NUMBER_PREFIX = "EthernetOverUsb";

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    public static EthernetOverUsbSerialNumber fromIpAddress(String ipAddress, String iface)
    {
        String serialNumber = SERIAL_NUMBER_PREFIX + ":" + iface + ":" + ipAddress;
        return new EthernetOverUsbSerialNumber(serialNumber);
    }

    public static EthernetOverUsbSerialNumber fromSerialNumber(String serialNumber)
    {
        return new EthernetOverUsbSerialNumber(serialNumber);
    }

    private EthernetOverUsbSerialNumber(String serialNumber)
    {
        super(serialNumber.trim());
        this.iface = serialNumber.substring(serialNumber.indexOf(':') + 1, serialNumber.lastIndexOf(':'));
        this.ipAddress = serialNumber.substring(serialNumber.lastIndexOf(':') + 1);
    }

    //----------------------------------------------------------------------------------------------
    // Accessing
    //----------------------------------------------------------------------------------------------

    public String getIpAddress()
    {
        return ipAddress;
    }

    @Override
    public String toString()
    {
        return iface + ':' + ipAddress;
    }

    @Override
    public boolean isFake()
    {
        return false;
    }

    /**
     * Returns whether the indicated serial number initialization string is one of the legacy
     * fake serial number forms or not.
     *
     * @param initializer the serial number initialization string to test
     * @return whether the serial number initialization string is a legacy fake form of serial number
     */
    public static boolean isLegacyFake(String initializer)
        {
        return false;
        }

    }

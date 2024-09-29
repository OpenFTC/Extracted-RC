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

import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;

/*
 * Configuration for a device that implements Ethernet over USB.
 *
 * Assumptions for vendors that implement this class of device:
 *
 * Devices that implement this should also implement a DHCP server.  When connected to a USB
 * port on the Control Hub, Android will create an ethernet interface, typically eth0, but maybe eth1,
 * and the device should, via DHCP, assign the Control Hub an ip address.
 *
 * The "serial number" in the configuration is the ip address assigned to the Control Hub.  For this
 * reason, devices should take care that the ip address allocated via DHCP is persistent between reconnects.
 *
 * An example of how this may be accomplished would be for the DHCP server to allocate an ip address that is
 * a fixed offset from the device's own ip address.
 */
@SuppressWarnings("WeakerAccess")
public class EthernetOverUsbConfiguration extends ControllerConfiguration<DeviceConfiguration>
    {
    public static final String TAG = "EthernetOverUsbConfiguration";
    public static final String XMLATTR_IPADDRESS = "ipAddress";
    private static final int LIMELIGHT_DEFAULT_INDEX = 1;

    protected InetAddress ipAddress;

    public EthernetOverUsbConfiguration()
    {
        this("", SerialNumber.createFake(), "172.0.0.1");
    }

    public EthernetOverUsbConfiguration(String name, SerialNumber serialNumber, String ipAddress)
    {
        super(name, new LinkedList<DeviceConfiguration>(), serialNumber, BuiltInConfigurationType.ETHERNET_OVER_USB_DEVICE);

        this.setKnownToBeAttached(true);
        try {
            /*
             * If this is being constructed for existing consumption by a config file, then when
             * the xml is parsed the ip address will be set to the value in the config file.
             * If this is being constructed for a new config file, then we want the default to be
             * the value passed in, but with the fourth octet swapped out to the default for the limelight.
             * So go ahead do the swap, and if this is being pulled from a file it will get fixed up later.
             */
            this.ipAddress = swapFourthOctet(InetAddress.getByName(ipAddress), LIMELIGHT_DEFAULT_INDEX);
        } catch (UnknownHostException e) {
            RobotLog.ee(TAG, "Invalid IP address: ", e.getMessage());
        }
    }

    private static InetAddress swapFourthOctet(InetAddress ipAddress, int x) throws UnknownHostException
    {
        if (x < 1 || x > 16) {
            throw new IllegalArgumentException("X must be between 1 and 16.");
        }

        byte[] ipBytes = ipAddress.getAddress();
        if (ipBytes.length != 4) {
            throw new IllegalArgumentException("Invalid IP address format.");
        }

        ipBytes[3] = (byte) x;
        return InetAddress.getByAddress(ipBytes);
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    @Override public void serializeXmlAttributes(XmlSerializer serializer)
    {
        try {
            super.serializeXmlAttributes(serializer);
            serializer.attribute("", XMLATTR_IPADDRESS, this.getIpAddress().getHostAddress());
        } catch (Exception e) {
            RobotLog.ee(TAG, e, "exception serializing");
            throw new RuntimeException(e);
        }
    }

    @Override public void deserializeAttributes(XmlPullParser parser) {
        super.deserializeAttributes(parser);

        // Read the bus. If there is no bus (the legacy case), then we use the port
        String ipAddressStr = parser.getAttributeValue(null, EthernetOverUsbConfiguration.XMLATTR_IPADDRESS);
        try {
            InetAddress ipAddress = InetAddress.getByName(ipAddressStr);
            this.setIpAddress(ipAddress);
        } catch (UnknownHostException e){
            RobotLog.ee(TAG, "Invalid IP address: ", e.getMessage());
        }
    }
}

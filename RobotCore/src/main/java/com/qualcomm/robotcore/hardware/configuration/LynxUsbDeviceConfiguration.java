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

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * A Lynx USB Device contains one or more Lynx modules linked together over an
 * RS485 bus. The one of these which is connected externally to USB is termed
 * the 'parent'; the others are called 'children'.
 */
@SuppressWarnings("WeakerAccess")
public class LynxUsbDeviceConfiguration extends ControllerConfiguration<RhspModuleConfiguration>
    {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public static final String XMLATTR_PARENT_MODULE_ADDRESS = "parentModuleAddress";
    // Any sort of automated configuration tweaking should occur on the Robot Controller only,
    // so that we don't break configurations when the DS and RC are not running the same version.
    // The RC will re-process XML that it sends to and receives from the DS, so we don't need to do it on the DS.
    private static final boolean ASSUME_EMBEDDED_MODULE_ADDRESS = AppUtil.getInstance().isRobotController();

    int parentModuleAddress = LynxConstants.DEFAULT_PARENT_MODULE_ADDRESS; // Contains the canonical parent module address (adjusted as necessary)
    private int recordedParentModuleAddress = LynxConstants.DEFAULT_PARENT_MODULE_ADDRESS; // Contains the parent module address as recorded in the XML

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    // For use in deserializing from XML
    public LynxUsbDeviceConfiguration()
        {
        super("", new LinkedList<RhspModuleConfiguration>(), SerialNumber.createFake(), BuiltInConfigurationType.LYNX_USB_DEVICE);
        }

    public LynxUsbDeviceConfiguration(String name, List<RhspModuleConfiguration> modules, SerialNumber serialNumber)
        {
        super(name, new LinkedList<RhspModuleConfiguration>(modules), serialNumber, BuiltInConfigurationType.LYNX_USB_DEVICE);
        finishInitialization();
        }

    @Override public void setSerialNumber(@NonNull SerialNumber serialNumber)
        {
        super.setSerialNumber(serialNumber);
        for (RhspModuleConfiguration module : getModules())
            {
            module.setUsbDeviceSerialNumber(serialNumber);
            }
        }

    //----------------------------------------------------------------------------------------------
    // Operations
    //----------------------------------------------------------------------------------------------

    /**
     * Returns the module address of the Lynx module which is directly USB connected
     */
    public int getParentModuleAddress()
        {
        return this.parentModuleAddress;
        }

    public void setParentModuleAddress(int moduleAddress)
        {
        this.parentModuleAddress = moduleAddress;
        }

    public List<RhspModuleConfiguration> getModules()
        {
        return this.getDevices();
        }

    @Override
    protected void deserializeAttributes(XmlPullParser parser)
        {
        super.deserializeAttributes(parser); // Parses serial number
        String recordedParentAddressString = parser.getAttributeValue(null, XMLATTR_PARENT_MODULE_ADDRESS);
        if (recordedParentAddressString != null && !recordedParentAddressString.isEmpty())
            {
            recordedParentModuleAddress = Integer.parseInt(recordedParentAddressString);
            }
        if (ASSUME_EMBEDDED_MODULE_ADDRESS && getSerialNumber().isEmbedded())
            {
            // For a Control Hub, the parent module address is always 173.
            setParentModuleAddress(LynxConstants.CH_EMBEDDED_MODULE_ADDRESS);
            }
        else
            {
            setParentModuleAddress(recordedParentModuleAddress);
            }
        }

    @Override
    protected void deserializeChildElement(ConfigurationType configurationType, XmlPullParser parser, ReadXMLFileHandler xmlReader) throws IOException, XmlPullParserException, RobotCoreException
        {
        super.deserializeChildElement(configurationType, parser, xmlReader);
        // deserializeAttributes is called before deserializeChildElement
        if (configurationType == BuiltInConfigurationType.LYNX_MODULE)
            {
            LynxModuleConfiguration moduleConfiguration = new LynxModuleConfiguration();
            moduleConfiguration.deserialize(parser, xmlReader);

            if (ASSUME_EMBEDDED_MODULE_ADDRESS &&
                getSerialNumber().isEmbedded() &&
                moduleConfiguration.getModuleAddress() == recordedParentModuleAddress)
                {
                // This particular module is the one inside of the Control Hub, assume that it has
                // an address of 173 in the real world, regardless of what the configuration says.
                moduleConfiguration.setModuleAddress(LynxConstants.CH_EMBEDDED_MODULE_ADDRESS);
                }
            moduleConfiguration.setParentModuleAddress(parentModuleAddress);
            getModules().add(moduleConfiguration);
            }

        else if (configurationType == BuiltInConfigurationType.SERVO_HUB)
            {
            ServoHubConfiguration moduleConfiguration = new ServoHubConfiguration();
            moduleConfiguration.deserialize(parser, xmlReader);

            moduleConfiguration.setParentModuleAddress(parentModuleAddress);
            getModules().add(moduleConfiguration);
            }
        }

    @Override
    protected void onDeserializationComplete(ReadXMLFileHandler xmlReader)
        {
        finishInitialization();
        super.onDeserializationComplete(xmlReader);
        }

    private void finishInitialization()
        {
        // Sort in increasing order by module address
        // TODO(Noah): Find more robust place to do this
        Collections.sort(this.getModules(), new Comparator<DeviceConfiguration>()
            {
            @Override public int compare(DeviceConfiguration lhs, DeviceConfiguration rhs)
                {
                // Always put the embedded module at the top of the list
                if (lhs.getPort() == LynxConstants.CH_EMBEDDED_MODULE_ADDRESS)
                    {
                    return -1;
                    }
                return lhs.getPort() - rhs.getPort();
                }
            });

        // Checking how many modules have the reserved address is a robust way to detect non-parent modules with that address
        int numberOfModulesWithControlHubAddress = 0;
        for (RhspModuleConfiguration module : getModules())
            {
            module.setUsbDeviceSerialNumber(getSerialNumber());
            if (module.isParent())
                {
                this.setParentModuleAddress(module.getModuleAddress());
                }
            if (module.getModuleAddress() == LynxConstants.CH_EMBEDDED_MODULE_ADDRESS)
                {
                numberOfModulesWithControlHubAddress++;
                // The module has the reserved Control Hub address; verify that it's supposed to
                if (!getSerialNumber().isEmbedded() || numberOfModulesWithControlHubAddress > 1)
                    {
                    RobotLog.setGlobalErrorMsg("An Expansion Hub is configured with address 173, which is reserved for the Control Hub. You need to change the Expansion Hub's address, and make a new configuration file");
                    }
                }
            }
        }
    }

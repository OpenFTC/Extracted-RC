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

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.util.SerialNumber;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ServoHubConfiguration extends RhspModuleConfiguration
    {
    public static final String TAG = "ServoHubConfiguration";

    private List<DeviceConfiguration> servos = new LinkedList<>();

    public ServoHubConfiguration()
        {
        this("");
        }

    public ServoHubConfiguration(String name)
        {
        super(name, new ArrayList<>(), SerialNumber.createFake(), BuiltInConfigurationType.SERVO_HUB);

        servos = ConfigurationUtility.buildEmptyServos(LynxConstants.INITIAL_SERVO_PORT, LynxConstants.NUMBER_OF_SERVO_CHANNELS);
        }

    @Override
    public boolean isParent()
        {
        // a servo hub can never be a parent
        return false;
        }

    public List<DeviceConfiguration> getServos()
        {
        return servos;
        }
    public void setServos(List<DeviceConfiguration> servos)
        {
        this.servos = servos;
        }

    @Override
    protected void deserializeChildElement(ConfigurationType configurationType, XmlPullParser parser, ReadXMLFileHandler xmlReader) throws IOException, XmlPullParserException, RobotCoreException
        {
        super.deserializeChildElement(configurationType, parser, xmlReader);

        if (configurationType.isDeviceFlavor(ConfigurationType.DeviceFlavor.SERVO))
            {
            DeviceConfiguration servo = new DeviceConfiguration();
            servo.deserialize(parser, xmlReader);
            getServos().set(servo.getPort() - LynxConstants.INITIAL_SERVO_PORT, servo);
            }
        }

    @Override
    protected void deserializeAttributes(XmlPullParser parser)
        {
        super.deserializeAttributes(parser);
        setModuleAddress(getPort());
        }

    }

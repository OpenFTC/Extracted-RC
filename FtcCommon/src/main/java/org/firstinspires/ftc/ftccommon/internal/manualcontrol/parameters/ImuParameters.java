/*
Copyright (c) 2023 REV Robotics

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
package org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters;

import androidx.annotation.Nullable;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidParameterException;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;

import java.util.Arrays;
import java.util.Optional;

public class ImuParameters {
    @Nullable private Float w;
    @Nullable private Float x;
    @Nullable private Float y;
    @Nullable private Float z;

    @Nullable private String logoFacing;
    @Nullable private String usbFacing;

    /**
     * If the quaternion parameters (w, x, y, z) are provided, we will use them. Otherwise, if the simple parameters
     * (logoFacing, usbFacing) are provided, we use them instead. If neither are provided, we default to logoFacing: UP
     * and usbFacing: BACKWARD
     * @return orientation matching the parameters
     */
    public RevHubOrientationOnRobot getOrientation() throws InvalidParameterException {
        if(w != null && x != null & y != null && z != null) {
            return new RevHubOrientationOnRobot(new Quaternion(w, x, y, z, 0));
        }
        if(logoFacing != null && usbFacing != null) {
            return new RevHubOrientationOnRobot(getLogoDirection(logoFacing), getUsbDirection(usbFacing));
        }

        return new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
    }

    private RevHubOrientationOnRobot.LogoFacingDirection getLogoDirection(String s) throws InvalidParameterException {
        Optional<RevHubOrientationOnRobot.LogoFacingDirection> direction = Arrays
                .stream(RevHubOrientationOnRobot.LogoFacingDirection.values())
                .filter((RevHubOrientationOnRobot.LogoFacingDirection d) ->
                        d.name().equalsIgnoreCase(s)
                ).findFirst();

        if(!direction.isPresent()) throw new InvalidParameterException(s + " is not a valid Logo Direction");
        return direction.get();
    }

    private RevHubOrientationOnRobot.UsbFacingDirection getUsbDirection(String s) throws InvalidParameterException {
        Optional<RevHubOrientationOnRobot.UsbFacingDirection> direction = Arrays
                .stream(RevHubOrientationOnRobot.UsbFacingDirection.values())
                .filter((RevHubOrientationOnRobot.UsbFacingDirection d) ->
                        d.name().equalsIgnoreCase(s)
                ).findFirst();

        if(!direction.isPresent()) throw new InvalidParameterException(s + " is not a valid Logo Direction");
        return direction.get();
    }
}

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

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidParameterException;

@SuppressWarnings("unused") // Used by GSON
public class DebugLogLevelParameters extends HandleIdParameters {
    /**
     * Group to set verbosity level for. One of
     * <ul>
     *     <li>Main = 1</li>
     *     <li>TransmitterToHost = 2</li>
     *     <li>ReceiverFromHost = 3</li>
     *     <li>ADC = 4</li>
     *     <li>PWMAndServo = 5</li>
     *     <li>ModuleLED = 6</li>
     *     <li>DigitalIO = 7</li>
     *     <li>I2C = 8</li>
     *     <li>Motor0 = 9</li>
     *     <li>Motor1 = 10</li>
     *     <li>Motor2 = 11</li>
     *     <li>Motor3 = 12</li>
     * </ul>
     */
    @Nullable private Integer debugGroup;

    /**
     * Verbosity Level. One of:
     * <ul>
     *     <li>Off = 0,</li>
     *     <li>Level1 = 1</li>
     *     <li>Level2 = 2</li>
     *     <li>Level3 = 3</li>
     * </ul>
     */
    @Nullable private Integer verbosityLevel;

    public int getDebugGroup() throws InvalidParameterException {
        if (debugGroup == null) {
            throw new InvalidParameterException("debugGroup not specified");
        }
        return debugGroup;
    }

    public int getVerbosityLevel() throws InvalidParameterException {
        if (verbosityLevel == null) {
            throw new InvalidParameterException("verbosityLevel not specified");
        }
        return verbosityLevel;
    }
}

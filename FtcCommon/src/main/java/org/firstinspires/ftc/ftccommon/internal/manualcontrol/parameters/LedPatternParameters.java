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
public class LedPatternParameters extends HandleIdParameters {
    @Nullable private Integer s0;
    @Nullable private Integer s1;
    @Nullable private Integer s2;
    @Nullable private Integer s3;
    @Nullable private Integer s4;
    @Nullable private Integer s5;
    @Nullable private Integer s6;
    @Nullable private Integer s7;
    @Nullable private Integer s8;
    @Nullable private Integer s9;
    @Nullable private Integer s10;
    @Nullable private Integer s11;
    @Nullable private Integer s12;
    @Nullable private Integer s13;
    @Nullable private Integer s14;
    @Nullable private Integer s15;

    public int getRgbtPatternStep0() throws InvalidParameterException {
        if (s0 == null) {
            throw new InvalidParameterException("s0 (step 0, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s0;
    }

    public int getRgbtPatternStep1() throws InvalidParameterException {
        if (s1 == null) {
            throw new InvalidParameterException("s1 (step 1, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s1;
    }

    public int getRgbtPatternStep2() throws InvalidParameterException {
        if (s2 == null) {
            throw new InvalidParameterException("s2 (step 2, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s2;
    }

    public int getRgbtPatternStep3() throws InvalidParameterException {
        if (s3 == null) {
            throw new InvalidParameterException("s3 (step 3, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s3;
    }

    public int getRgbtPatternStep4() throws InvalidParameterException {
        if (s4 == null) {
            throw new InvalidParameterException("s4 (step 4, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s4;
    }

    public int getRgbtPatternStep5() throws InvalidParameterException {
        if (s5 == null) {
            throw new InvalidParameterException("s5 (step 5, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s5;
    }

    public int getRgbtPatternStep6() throws InvalidParameterException {
        if (s6 == null) {
            throw new InvalidParameterException("s6 (step 6, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s6;
    }

    public int getRgbtPatternStep7() throws InvalidParameterException {
        if (s7 == null) {
            throw new InvalidParameterException("s7 (step 7, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s7;
    }

    public int getRgbtPatternStep8() throws InvalidParameterException {
        if (s8 == null) {
            throw new InvalidParameterException("s8 (step 8, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s8;
    }

    public int getRgbtPatternStep9() throws InvalidParameterException {
        if (s9 == null) {
            throw new InvalidParameterException("s9 (step 9, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s9;
    }

    public int getRgbtPatternStep10() throws InvalidParameterException {
        if (s10 == null) {
            throw new InvalidParameterException("s10 (step 10, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s10;
    }

    public int getRgbtPatternStep11() throws InvalidParameterException {
        if (s11 == null) {
            throw new InvalidParameterException("s11 (step 11, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s11;
    }

    public int getRgbtPatternStep12() throws InvalidParameterException {
        if (s12 == null) {
            throw new InvalidParameterException("s12 (step 12, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s12;
    }

    public int getRgbtPatternStep13() throws InvalidParameterException {
        if (s13 == null) {
            throw new InvalidParameterException("s13 (step 13, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s13;
    }

    public int getRgbtPatternStep14() throws InvalidParameterException {
        if (s14 == null) {
            throw new InvalidParameterException("s14 (step 14, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s14;
    }

    public int getRgbtPatternStep15() throws InvalidParameterException {
        if (s15 == null) {
            throw new InvalidParameterException("s15 (step 15, rgbt integer with one byte per value, t is deci-seconds) not specified");
        }
        return s15;
    }
}

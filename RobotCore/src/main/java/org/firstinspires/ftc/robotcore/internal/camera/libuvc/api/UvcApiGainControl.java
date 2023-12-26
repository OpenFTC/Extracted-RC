/*
 * Copyright (c) 2020 Michael Hoogasian
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of Michael Hoogasian nor the names of his contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.robotcore.internal.camera.libuvc.api;

import org.firstinspires.ftc.robotcore.external.function.Supplier;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.internal.camera.libuvc.nativeobject.UvcDeviceHandle;
import org.firstinspires.ftc.robotcore.internal.system.Tracer;

@SuppressWarnings("WeakerAccess")
public class UvcApiGainControl implements GainControl
{
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public static final String TAG = "UvcApiGainControl";
    public String getTag() { return TAG; }
    public static boolean TRACE = true;
    protected Tracer tracer = Tracer.create(getTag(), TRACE);

    protected UvcDeviceHandle uvcDeviceHandle; // no ref

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    public UvcApiGainControl(UvcDeviceHandle uvcDeviceHandle)
    {
        this.uvcDeviceHandle = uvcDeviceHandle;
    }

    //----------------------------------------------------------------------------------------------
    // GainControl
    //----------------------------------------------------------------------------------------------


    @Override
    public boolean setGain(final int gain)
    {
        return tracer.traceResult(tracer.format("setGain(%d)", gain), new Supplier<Boolean>()
        {
            @Override
            public Boolean get()
            {
                return uvcDeviceHandle.setGain(gain);
            }
        });
    }

    @Override
    public int getGain()
    {
        return tracer.traceResult("getGain()", new Supplier<Integer>()
        {
            @Override
            public Integer get()
            {
                return uvcDeviceHandle.getGain();
            }
        });
    }

    @Override
    public int getMinGain()
    {
        return tracer.traceResult("getMinGain()", new Supplier<Integer>()
        {
            @Override
            public Integer get()
            {
                return uvcDeviceHandle.getMinGain();
            }
        });
    }

    @Override
    public int getMaxGain()
    {
        return tracer.traceResult("getMaxGain()", new Supplier<Integer>()
        {
            @Override
            public Integer get()
            {
                return uvcDeviceHandle.getMaxGain();
            }
        });
    }
}

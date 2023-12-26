/*
 * Copyright (c) 2021 Michael Hoogasian
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
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.WhiteBalanceControl;
import org.firstinspires.ftc.robotcore.internal.camera.libuvc.nativeobject.UvcDeviceHandle;
import org.firstinspires.ftc.robotcore.internal.system.Tracer;

@SuppressWarnings("WeakerAccess")
public class UvcApiWhiteBalanceControl implements WhiteBalanceControl
{
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public static final String TAG = "UvcApiWhiteBalanceControl";
    public String getTag() { return TAG; }
    public static boolean TRACE = true;
    protected Tracer tracer = Tracer.create(getTag(), TRACE);

    protected UvcDeviceHandle uvcDeviceHandle; // no ref

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    public UvcApiWhiteBalanceControl(UvcDeviceHandle uvcDeviceHandle)
    {
        this.uvcDeviceHandle = uvcDeviceHandle;
    }

    //----------------------------------------------------------------------------------------------
    // WhiteBalanceControl
    //----------------------------------------------------------------------------------------------


    @Override
    public boolean setWhiteBalanceTemperature(final int temperature)
    {
        return tracer.traceResult(tracer.format("setWhiteBalanceTemperature(%d)", temperature), new Supplier<Boolean>()
        {
            @Override
            public Boolean get()
            {
                return uvcDeviceHandle.setWhiteBalanceTemperature(temperature);
            }
        });
    }

    @Override
    public int getWhiteBalanceTemperature()
    {
        return tracer.traceResult("getWhiteBalanceTemperature()", new Supplier<Integer>()
        {
            @Override
            public Integer get()
            {
                return uvcDeviceHandle.getWhiteBalanceTemperature();
            }
        });
    }

    @Override
    public int getMinWhiteBalanceTemperature()
    {
        return tracer.traceResult("getMinWhiteBalanceTemperature()", new Supplier<Integer>()
        {
            @Override
            public Integer get()
            {
                return uvcDeviceHandle.getMinWhiteBalanceTemperature();
            }
        });
    }

    @Override
    public int getMaxWhiteBalanceTemperature()
    {
        return tracer.traceResult("getMaxWhiteBalanceTemperature()", new Supplier<Integer>()
        {
            @Override
            public Integer get()
            {
                return uvcDeviceHandle.getMaxWhiteBalanceTemperature();
            }
        });
    }

    @Override
    public Mode getMode()
    {
        return tracer.traceResult("getMode()", new Supplier<Mode>()
        {
            @Override
            public Mode get()
            {
                return Mode.values()[uvcDeviceHandle.getWhiteBalanceMode()];
            }
        });
    }

    @Override
    public boolean setMode(final Mode mode)
    {
        return tracer.traceResult("setMode()", new Supplier<Boolean>()
        {
            @Override
            public Boolean get()
            {
                return uvcDeviceHandle.setWhiteBalanceMode(mode.ordinal());
            }
        });
    }
}

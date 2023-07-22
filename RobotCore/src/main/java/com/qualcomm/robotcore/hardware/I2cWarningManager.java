/*
Copyright (c) 2022 REV Robotics, Michael Hoogasian

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
package com.qualcomm.robotcore.hardware;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.R;
import com.qualcomm.robotcore.util.GlobalWarningSource;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.util.HashSet;
import java.util.Iterator;

public class I2cWarningManager implements GlobalWarningSource
{
    // We don't have a getInstance() static method so that callers can't mix up suppressing new
    // device warnings, and suppressing already-present device warnings.
    // This way we can more carefully control the API we present.
    private static final I2cWarningManager instance = new I2cWarningManager();
    static
    {
        RobotLog.registerGlobalWarningSource(instance);
    }

    // We use an explicit, static lock to prevent confusing when locking from a static method vs an instance method
    private static final Object lock = new Object();

    // We use static state for anything that the GlobalWarningSource method overrides don't need
    private static int newProblemDeviceSuppressionCount = 0;

    public static void notifyProblemI2cDevice(@NonNull I2cDeviceSynchSimple dev)
    {
        if (dev instanceof I2cDeviceSynchImplOnSimple)
        {
            // Mark the wrapped I2cDeviceSynchSimple class as the one that's experiencing a problem,
            // so that if another driver is sharing the wrapped class, they are being tracked
            // equivalently here. We use recursion to keep the behavior correct even if a wrapper is
            // wrapping a wrapper.
            notifyProblemI2cDevice(((I2cDeviceSynchImplOnSimple) dev).i2cDeviceSynchSimple);
            return;
        }
        synchronized (lock)
        {
            if (newProblemDeviceSuppressionCount == 0)
            {
                instance.problemDevices.add(dev);
            }
        }
    }

    public static void removeProblemI2cDevice(I2cDeviceSynchSimple dev)
    {
        if (dev instanceof I2cDeviceSynchImplOnSimple)
        {
            // Mark the wrapped I2cDeviceSynchSimple class as the one that's experiencing a problem,
            // so that if another driver is sharing the wrapped class, they are being tracked
            // equivalently here. We use recursion to keep the behavior correct even if a wrapper is
            // wrapping a wrapper.
            removeProblemI2cDevice(((I2cDeviceSynchImplOnSimple) dev).i2cDeviceSynchSimple);
            return;
        }
        synchronized (lock)
        {
            if (instance.problemDevices.isEmpty())
            {
                return;
            }
            instance.problemDevices.remove(dev);
        }
    }

    public static void suppressNewProblemDeviceWarningsWhile(Runnable runnable)
    {
        synchronized (lock)
        {
            newProblemDeviceSuppressionCount++;
        }
        try
        {
            runnable.run();
        }
        finally
        {
            synchronized (lock) {
                newProblemDeviceSuppressionCount--;
            }
        }
    }

    public static void suppressNewProblemDeviceWarnings(boolean suppress)
    {
        synchronized (lock)
        {
            if (suppress)
            {
                newProblemDeviceSuppressionCount++;
            }
            else
            {
                newProblemDeviceSuppressionCount--;
            }
        }
    }

    public static void clearI2cWarnings()
    {
        instance.clearGlobalWarning();
    }

    // Instance state
    private final HashSet<I2cDeviceSynchSimple> problemDevices = new HashSet<>();
    private int warningSourceSuppressionCount = 0;

    @Nullable
    @Override
    public String getGlobalWarning()
    {
        synchronized (lock) {
            if (problemDevices.isEmpty() || warningSourceSuppressionCount > 0)
            {
                return null;
            }

            StringBuilder builder = new StringBuilder();
            builder.append(AppUtil.getDefContext().getString(R.string.warningI2cCommError));
            builder.append(" ");

            Iterator<I2cDeviceSynchSimple> iterator = problemDevices.iterator();

            while (iterator.hasNext())
            {
                HardwareDevice dev = iterator.next();

                String name = ((RobotConfigNameable)dev).getUserConfiguredName();
                if (name != null)
                {
                    builder.append("'");
                    builder.append(name);
                    builder.append("'");
                }

                if (iterator.hasNext())
                {
                    builder.append(", ");
                }
                else
                {
                    builder.append(". ");
                }
            }

            builder.append("Check your wiring and configuration.");

            return builder.toString();
        }
    }

    @Override
    public boolean shouldTriggerWarningSound()
    {
        return true;
    }

    @Override
    public void suppressGlobalWarning(boolean suppress) {
        synchronized (lock)
        {
            if (suppress)
            {
                warningSourceSuppressionCount++;
            }
            else
            {
                warningSourceSuppressionCount--;
            }
        }
    }

    @Override
    public void setGlobalWarning(String warning)
    {
    }

    @Override
    public void clearGlobalWarning()
    {
        synchronized (lock)
        {
            problemDevices.clear();
        }
    }
}

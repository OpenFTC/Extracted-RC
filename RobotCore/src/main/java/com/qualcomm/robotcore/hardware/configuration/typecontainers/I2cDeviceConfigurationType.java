/*
Copyright (c) 2016 Robert Atkinson, Noah Andrews

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
package com.qualcomm.robotcore.hardware.configuration.typecontainers;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImplOnSimple;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
import com.qualcomm.robotcore.hardware.configuration.ConfigurationTypeManager;
import com.qualcomm.robotcore.hardware.configuration.ConfigurationTypeManager.ClassSource;
import com.qualcomm.robotcore.hardware.configuration.ConstructorPrototype;
import com.qualcomm.robotcore.hardware.configuration.I2cSensor;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.util.ClassUtil;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Func;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * {@link I2cDeviceConfigurationType} contains the meta-data for a user-defined I2c sensor driver.
 */
public final class I2cDeviceConfigurationType extends InstantiableUserConfigurationType
    {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------
    private static final ConstructorPrototype ctorI2cDeviceSynchSimple            = new ConstructorPrototype(I2cDeviceSynchSimple.class, boolean.class);
    private static final ConstructorPrototype ctorI2cDeviceSynch                  = new ConstructorPrototype(I2cDeviceSynch.class, boolean.class);
    private static final ConstructorPrototype ctorI2cDeviceSynchSimpleDeprecated  = new ConstructorPrototype(I2cDeviceSynchSimple.class);
    private static final ConstructorPrototype ctorI2cDeviceSynchDeprecated        = new ConstructorPrototype(I2cDeviceSynch.class);

    private static final ConstructorPrototype[] allowableConstructorPrototypes =
            {
            ctorI2cDeviceSynchSimple,
            ctorI2cDeviceSynch,
            ctorI2cDeviceSynchSimpleDeprecated,
            ctorI2cDeviceSynchDeprecated,
            };

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    public I2cDeviceConfigurationType(Class<? extends HardwareDevice> clazz, String xmlTag, ClassSource classSource)
        {
        super(clazz, DeviceFlavor.I2C, xmlTag, allowableConstructorPrototypes, classSource);
        }

    public static I2cDeviceConfigurationType getLynxEmbeddedBNO055ImuType()
        {
        return (I2cDeviceConfigurationType) ConfigurationTypeManager.getInstance().configurationTypeFromTag(LynxConstants.EMBEDDED_BNO055_IMU_XML_TAG);
        }

    public static I2cDeviceConfigurationType getLynxEmbeddedBHI260APImuType()
        {
        return (I2cDeviceConfigurationType) ConfigurationTypeManager.getInstance().configurationTypeFromTag(LynxConstants.EMBEDDED_BHI260AP_IMU_XML_TAG);
        }

    // Used by gson deserialization
    @SuppressWarnings("unused")
    public I2cDeviceConfigurationType()
        {
        super(DeviceFlavor.I2C);
        }

    public void processAnnotation(@Nullable I2cSensor i2cSensor)
        {
        if (i2cSensor != null)
            {
            if (name.isEmpty())
                {
                name = ClassUtil.decodeStringRes(i2cSensor.name().trim());
                }
            this.description = ClassUtil.decodeStringRes(i2cSensor.description());
            }
        }

    //----------------------------------------------------------------------------------------------
    // Instance creation
    //----------------------------------------------------------------------------------------------

    public List<HardwareDevice> createInstances(Func<I2cDeviceSynchSimple> simpleSynchFunc)
        {
        final List<HardwareDevice> result = new ArrayList<>(additionalTypesToInstantiate.size() + 1);

        // All instances should (under ideal circumstances where all instances use a non-deprecated
        // constructor) share a single deviceClient, so that if an I2C error occurs during an OpMode
        // using one driver, and then another OpMode is run that uses a different driver for the
        // same configuration entry, the second OpMode is able to clear the warning caused by the
        // first OpMode.
        final AtomicReference<I2cDeviceSynchSimple> sharedDeviceClient = new AtomicReference<>();
        boolean onlyOneInstance = additionalTypesToInstantiate.size() == 0;

        forThisAndAllAdditionalTypes(type ->
            {
            try
                {
                Constructor<HardwareDevice> ctor;

                ctor = type.findMatch(ctorI2cDeviceSynchSimple);
                if (ctor != null)
                    {
                    // Classes that use a non-deprecated constructor use a shared deviceClient.
                    sharedDeviceClient.compareAndSet(null, simpleSynchFunc.value());
                    result.add(ctor.newInstance(sharedDeviceClient.get(), onlyOneInstance));
                    return; // Exits the forThisAndAllAdditionalTypes() lambda, NOT createInstances()
                    }

                ctor = type.findMatch(ctorI2cDeviceSynch);
                if (ctor != null)
                    {
                    // Classes that use a non-deprecated constructor use a shared deviceClient.
                    sharedDeviceClient.compareAndSet(null, simpleSynchFunc.value());
                    I2cDeviceSynch i2cDeviceSynch = getI2cDeviceSynchFromSimple(sharedDeviceClient.get(), onlyOneInstance);
                    result.add(ctor.newInstance(i2cDeviceSynch, onlyOneInstance));
                    return; // Exits the forThisAndAllAdditionalTypes() lambda, NOT createInstances()
                    }

                ctor = type.findMatch(ctorI2cDeviceSynchSimpleDeprecated);
                if (ctor != null)
                    {
                    warnAboutDeprecatedConstructor(type.getClazz());
                    // Classes that use a deprecated constructor get a unique deviceClient just
                    // for them, since they probably assume that they own it.
                    result.add(ctor.newInstance(simpleSynchFunc.value()));
                    return; // Exits the forThisAndAllAdditionalTypes() lambda, NOT createInstances()
                    }

                ctor = type.findMatch(ctorI2cDeviceSynchDeprecated);
                if (ctor != null)
                    {
                    warnAboutDeprecatedConstructor(type.getClazz());
                    // Classes that use a deprecated constructor get a unique deviceClient just
                    // for them, since they probably assume that they own it.
                    I2cDeviceSynch i2cDeviceSynch = getI2cDeviceSynchFromSimple(simpleSynchFunc.value(), true);
                    result.add(ctor.newInstance(i2cDeviceSynch));
                    return; // Exits the forThisAndAllAdditionalTypes() lambda, NOT createInstances()
                    }

                RobotLog.e("unable to locate constructor for device class " + type.getClazz().getName());
                }
            catch (IllegalAccessException | InstantiationException | InvocationTargetException e)
                {
                handleConstructorExceptions(e, type.getClazz());
                }
            });
        return result;
        }

    private static I2cDeviceSynch getI2cDeviceSynchFromSimple(I2cDeviceSynchSimple simple, boolean isSimpleOwned)
        {
        if (simple instanceof I2cDeviceSynch) { return (I2cDeviceSynch) simple; }
        else { return new I2cDeviceSynchImplOnSimple(simple, isSimpleOwned); }
        }

    private static void warnAboutDeprecatedConstructor(Class<?> driverClass)
        {
        // Only print warning to RobotLog, this isn't critical for the end user to know about. 99%
        // of the time, it's not going to even make a difference whether a third-party driver has
        // been updated with one of the new constructor signatures.
        RobotLog.ww(I2cDeviceType.class.getSimpleName(), "%s only has deprecated constructors. " +
                "For best results, add an additional constructor that accepts an additional " +
                "\"deviceClientIsOwned\" boolean parameter and passes it to the I2cDeviceSynchDevice " +
                "or I2cDeviceSynchDeviceWithParameters constructor.",
                driverClass.getName());
        }

    //----------------------------------------------------------------------------------------------
    // Serialization (used in local marshalling during configuration editing)
    //----------------------------------------------------------------------------------------------

    private Object writeReplace()
        {
        return new SerializationProxy(this);
        }
    }

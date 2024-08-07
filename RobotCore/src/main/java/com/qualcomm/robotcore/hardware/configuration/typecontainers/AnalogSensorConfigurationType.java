/*
Copyright (c) 2018 Noah Andrews

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Noah Andrews nor the names of his contributors may be used to
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

import com.qualcomm.robotcore.hardware.AnalogInputController;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.configuration.ConfigurationTypeManager.ClassSource;
import com.qualcomm.robotcore.hardware.configuration.ConstructorPrototype;
import com.qualcomm.robotcore.util.RobotLog;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link AnalogSensorConfigurationType} contains the meta-data for a user-defined analog sensor driver.
 */
public final class AnalogSensorConfigurationType extends InstantiableUserConfigurationType {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    private static final ConstructorPrototype ctorAnalogSensor = new ConstructorPrototype(AnalogInputController.class, int.class);

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    public AnalogSensorConfigurationType(Class<? extends HardwareDevice> clazz, String xmlTag, ClassSource classSource) {
        super(clazz, DeviceFlavor.ANALOG_SENSOR, xmlTag, new ConstructorPrototype[]{ctorAnalogSensor}, classSource);
    }

    // Used by gson deserialization
    @SuppressWarnings("unused")
    public AnalogSensorConfigurationType() {
        super(DeviceFlavor.ANALOG_SENSOR);
    }

    //----------------------------------------------------------------------------------------------
    // Instance creation
    //----------------------------------------------------------------------------------------------

    public List<HardwareDevice> createInstances(AnalogInputController controller, int port) {
        final List<HardwareDevice> result = new ArrayList<>(additionalTypesToInstantiate.size() + 1);
        forThisAndAllAdditionalTypes(type -> {
            try {
                Constructor<HardwareDevice> ctor;

                ctor = type.findMatch(ctorAnalogSensor);
                if (ctor == null) {
                    RobotLog.e("unable to locate constructor for device class " + type.getClazz().getName());
                } else {
                    result.add(ctor.newInstance(controller, port));
                }
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                handleConstructorExceptions(e, type.getClazz());
            }
        });
        return result;
    }

    //----------------------------------------------------------------------------------------------
    // Serialization (used in local marshalling during configuration editing)
    //----------------------------------------------------------------------------------------------

    private Object writeReplace() {
        return new SerializationProxy(this);
    }
}

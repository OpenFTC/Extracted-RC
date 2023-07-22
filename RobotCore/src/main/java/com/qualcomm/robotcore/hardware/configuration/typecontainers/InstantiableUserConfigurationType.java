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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.configuration.ConfigurationTypeManager;
import com.qualcomm.robotcore.hardware.configuration.ConfigurationTypeManager.ClassSource;
import com.qualcomm.robotcore.hardware.configuration.ConstructorPrototype;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.util.ClassUtil;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Function;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class InstantiableUserConfigurationType extends UserConfigurationType {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    protected final Set<InstantiableUserConfigurationType> additionalTypesToInstantiate = new HashSet<>(); // Empty when running on the DS
    private Class<? extends HardwareDevice> clazz; // Null when running on the DS
    private List<Constructor> constructors; // Null when running on the DS
    private String originalName; // Null when running on the DS

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    protected InstantiableUserConfigurationType(Class clazz, @NonNull DeviceFlavor flavor, @NonNull String xmlTag, ConstructorPrototype[] allowableConstructorPrototypes, @NonNull ClassSource classSource) {
        super(clazz, flavor, xmlTag, classSource);
        this.clazz = clazz;
        this.constructors = findUsableConstructors(allowableConstructorPrototypes);
    }

    // used by gson
    protected InstantiableUserConfigurationType(@NonNull DeviceFlavor flavor) {
        super(flavor);
    }

    @Override
    public void processAnnotation(@NonNull DeviceProperties deviceProperties) {
        super.processAnnotation(deviceProperties);
        originalName = getName();
    }

    //----------------------------------------------------------------------------------------------
    // General methods
    //----------------------------------------------------------------------------------------------

    /**
     * Find the usable constructors of the underlying class, given a list of allowed prototypes
     */
    private List<Constructor> findUsableConstructors(ConstructorPrototype[] allowedPrototypes) {
        List<Constructor> result = new LinkedList<>();
        List<Constructor> constructors = ClassUtil.getDeclaredConstructors(getClazz());
        for (Constructor<?> ctor : constructors) {
            int requiredModifiers = Modifier.PUBLIC;
            if (!((ctor.getModifiers() & requiredModifiers) == requiredModifiers))
                continue;

            for (ConstructorPrototype allowedSignature : allowedPrototypes) {
                if (allowedSignature.matches(ctor)) {
                    result.add(ctor);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Finds a constructor of the underlying class that matches a given prototype
     */
    protected final Constructor<HardwareDevice> findMatch(ConstructorPrototype prototype) {
        // This method isn't being called until after ConfigurationTypeManager verifies that the class in question is a HardwareDevice.
        for (Constructor<HardwareDevice> ctor : constructors) {
            if (prototype.matches(ctor)) {
                return ctor;
            }
        }
        return null;
    }

    protected void forThisAndAllAdditionalTypes(Consumer<InstantiableUserConfigurationType> consumer) {
        consumer.accept(this);
        for (InstantiableUserConfigurationType additionalType: additionalTypesToInstantiate) {
            consumer.accept(additionalType);
        }
    }

    /** @return {@code true} if check returns true for any of the configuration types */
    protected boolean checkThisAndAllAdditionalTypes(Function<InstantiableUserConfigurationType, Boolean> checker) {
        AtomicBoolean result = new AtomicBoolean(false);
        forThisAndAllAdditionalTypes(type -> {
            if (checker.apply(type)) { result.set(true); }
        });
        return result.get();
    }

    //----------------------------------------------------------------------------------------------
    // Accessing
    //----------------------------------------------------------------------------------------------

    public final boolean hasConstructors() {
        return this.constructors.size() > 0;
    }

    public final Class<? extends HardwareDevice> getClazz() {
        return this.clazz;
    }

    // Override this if the corresponding annotation can also be applied to noninstantiable classes
    @Override
    public boolean annotatedClassIsInstantiable() {
        return true;
    }

    public void addAdditionalTypeToInstantiate(InstantiableUserConfigurationType newAdditionalType) {
        // Start by figuring out whether to replace our name with the additional type's name, BEFORE
        // we pollute our state with their state (which would impact the results).

        // Device types that share an XML tag do not have to share a display name. Which display
        // name is used is chosen based on the class source and whether the device type is
        // built-in to the SDK. If the existing type has an equal or higher priority display name,
        // this new type's display name gets ignored.

        if (newAdditionalType.getTopLevelDisplayNamePriority().compareTo(this.getDisplayNamePriorityIncludingAdditionalTypes()) < 0) {
            this.name = newAdditionalType.originalName;
        }

        // Add the new type to our list
        additionalTypesToInstantiate.add(newAdditionalType);
    }

    public static class ClearTypesFromSourceResult {
        @Nullable public final InstantiableUserConfigurationType newTopLevelType;
        public final Set<String> freedDisplayNames;

        public ClearTypesFromSourceResult(@Nullable InstantiableUserConfigurationType newTopLevelType,
                                          Set<String> freedDisplayNames) {
            this.newTopLevelType = newTopLevelType;
            this.freedDisplayNames = freedDisplayNames;
        }
    }

    public ClearTypesFromSourceResult clearTypesFromSource(ClassSource classSource) {
        InstantiableUserConfigurationType newTopLevelType = this;
        Set<String> potentiallyFreedDisplayNames = new HashSet<>();

        // capture to avoid deleting while iterating
        List<InstantiableUserConfigurationType> extantAdditionalTypes = new ArrayList<>(additionalTypesToInstantiate);

        // Clear additional types from the given class source and mark their display names as potentially freed
        for (InstantiableUserConfigurationType additionalType: extantAdditionalTypes) {
            if (additionalType.getClassSource() == classSource) {
                potentiallyFreedDisplayNames.add(additionalType.originalName);
                additionalTypesToInstantiate.remove(additionalType);
            }
        }

        if (getClassSource() == classSource) {
            // We need to replace ourselves as the top-level source.

            potentiallyFreedDisplayNames.add(getName());

            // Pick one of the remaining additional types (if there are any) to be the new top-level type
            if (additionalTypesToInstantiate.size() > 0) {
                newTopLevelType = additionalTypesToInstantiate.iterator().next();
                additionalTypesToInstantiate.remove(newTopLevelType);

                // Migrate the other additional types to the new top-level type
                for (InstantiableUserConfigurationType additionalType: additionalTypesToInstantiate) {
                    // Calling addAdditionalTypeToInstantiate() makes it so that the new display name
                    // will work itself out.
                    newTopLevelType.addAdditionalTypeToInstantiate(additionalType);
                }
            } else {
                // This XML tag is getting cleared out entirely.
                newTopLevelType = null;
            }
        }

        Set<String> freedDisplayNames = new HashSet<>();
        if (newTopLevelType == null) {
            // This XML tag is getting cleared entirely. All associated display names are free.
            freedDisplayNames = potentiallyFreedDisplayNames;
        } else {
            for (String potentiallyFreedDisplayName: potentiallyFreedDisplayNames) {
                if (!newTopLevelType.checkThisAndAllAdditionalTypes(type -> type.originalName.equals(potentiallyFreedDisplayName))) {
                    // None of the remaining types have this display name, so it can definitely be freed.
                    freedDisplayNames.add(potentiallyFreedDisplayName);
                }
            }

            if (freedDisplayNames.contains(newTopLevelType.getName())) {
                // We were using the name of a type that was only provided by classes that have now
                // been cleared, so we need to select the highest-priority remaining name. If the
                // top-level type was swapped out, that process should have handled selecting a new
                // display name, so this is for the case where the top-level type was not swapped
                // out.

                newTopLevelType.name = newTopLevelType.originalName;
                for (InstantiableUserConfigurationType additionalType: newTopLevelType.additionalTypesToInstantiate) {
                    if (additionalType.getTopLevelDisplayNamePriority().compareTo(newTopLevelType.getTopLevelDisplayNamePriority()) < 0) {
                        newTopLevelType.name = additionalType.originalName;
                    }
                }
            }
        }

        return new ClearTypesFromSourceResult(newTopLevelType, Collections.unmodifiableSet(freedDisplayNames));
    }


    //----------------------------------------------------------------------------------------------
    // Utility
    //----------------------------------------------------------------------------------------------

    protected final void handleConstructorExceptions(Exception e, Class<?> clazz) {
        RobotLog.e("Creating instance of user device %s failed: ", clazz.getName());
        RobotLog.logStackTrace(e);

        if (e instanceof InvocationTargetException) {
            Throwable targetException = ((InvocationTargetException) e).getTargetException();
            if (!isBuiltIn()) {
                RobotLog.setGlobalErrorMsg("Constructor of device class " + clazz.getName() + " threw %s. See log.", targetException.getClass().getSimpleName());
            }
        }
        if (!isBuiltIn()) {
            RobotLog.setGlobalErrorMsg("Internal error while creating instance of device class " + clazz.getName() + ". See log.");
        }
    }

    //----------------------------------------------------------------------------------------------
    // Serialization (used in local marshalling during configuration editing)
    //----------------------------------------------------------------------------------------------

    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    //----------------------------------------------------------------------------------------------
    // Display name prioritization
    //----------------------------------------------------------------------------------------------

    // Enums are naturally comparable according to their defined order.
    // The priority order was chosen to maximize documentation consistency.
    // If you change this priority order, you also need to update the following two methods to
    // reflect the new order.
    enum DisplayNamePriority {
        // If possible, use the built-in display name, so that the official documentation is consistent.
        BUILT_IN,
        // Next, use display names from classes KNOWN to be from third-party libraries, so that
        // their documentation is consistent.
        EXTERNAL_LIB,
        // Classes that are in the APK but don't come with the SDK could either be from a
        // third-party library or user code. In case they're from a third-party library, prioritize
        // this over OnBotJava classes
        APK,
        // We pretty much know OnBotJava classes are just user code.
        ONBOTJAVA
    }

    private DisplayNamePriority getDisplayNamePriorityIncludingAdditionalTypes() {
        if (checkThisAndAllAdditionalTypes(UserConfigurationType::isBuiltIn)) {
            return DisplayNamePriority.BUILT_IN;
        } else if (checkThisAndAllAdditionalTypes(type -> type.getClassSource() == ClassSource.EXTERNAL_LIB)) {
            return DisplayNamePriority.EXTERNAL_LIB;
        } else if (checkThisAndAllAdditionalTypes(type -> type.getClassSource() == ClassSource.APK)) {
            return DisplayNamePriority.APK;
        } else if (checkThisAndAllAdditionalTypes(type -> type.getClassSource() == ClassSource.ONBOTJAVA)) {
            return DisplayNamePriority.ONBOTJAVA;
        } else {
            RobotLog.ww(ConfigurationTypeManager.TAG, "Unable to determine display name priority for %s", clazz.getName());
            return DisplayNamePriority.ONBOTJAVA;
        }
    }

    private DisplayNamePriority getTopLevelDisplayNamePriority() {
        if (isBuiltIn()) { return DisplayNamePriority.BUILT_IN; }
        else if (getClassSource() == ClassSource.EXTERNAL_LIB) {
            return DisplayNamePriority.EXTERNAL_LIB;
        } else if (getClassSource() == ClassSource.APK) {
            return DisplayNamePriority.APK;
        } else if (getClassSource() == ClassSource.ONBOTJAVA) {
            return DisplayNamePriority.ONBOTJAVA;
        } else {
            RobotLog.ww(ConfigurationTypeManager.TAG, "Unable to determine display name priority for %s", clazz.getName());
            return DisplayNamePriority.ONBOTJAVA;
        }
    }
}

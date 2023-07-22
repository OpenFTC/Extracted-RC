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
package com.qualcomm.robotcore.hardware.configuration;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import com.qualcomm.robotcore.hardware.ControlSystem;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.annotations.AnalogSensorType;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.DigitalIoDeviceType;
import com.qualcomm.robotcore.hardware.configuration.annotations.ExpansionHubPIDFPositionParams;
import com.qualcomm.robotcore.hardware.configuration.annotations.ExpansionHubPIDFVelocityParams;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.hardware.configuration.annotations.ServoType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.AnalogSensorConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.DigitalIoDeviceConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.I2cDeviceConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.InstantiableUserConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.ServoConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.UserConfigurationType;
import com.qualcomm.robotcore.robocol.Command;
import com.qualcomm.robotcore.util.ClassUtil;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.Util;

import org.firstinspires.ftc.robotcore.external.Predicate;
import org.firstinspires.ftc.robotcore.internal.network.NetworkConnectionHandler;
import org.firstinspires.ftc.robotcore.internal.network.RobotCoreCommandList;
import org.firstinspires.ftc.robotcore.internal.opmode.ClassFilter;
import org.firstinspires.ftc.robotcore.internal.opmode.OnBotJavaDeterminer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * {@link ConfigurationTypeManager} is responsible for managing configuration types.
 *
 * @see I2cDeviceType
 * @see AnalogSensorType
 * @see DigitalIoDeviceType
 * @see ServoType
 * @see com.qualcomm.robotcore.hardware.configuration.annotations.MotorType
 * @see I2cDeviceType
 */
@SuppressLint("StaticFieldLeak")
public final class ConfigurationTypeManager implements ClassFilter
    {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public enum ClassSource
        {
        /**
         * Classes that are baked into the APK file. These may have come from the FTC SDK,
         * libraries that were pulled in via Gradle, or user code.
         */
        APK,
        /**
         * Classes from OnBotJava user code.
         */
        ONBOTJAVA,
        /**
         * Classes from external JAR/AAR files uploaded through the OnBotJava web interface.
         */
        EXTERNAL_LIB
        }

    public static final String TAG = "UserDeviceTypeManager";
    public static boolean DEBUG = true;

    // TODO(Noah): Delete these fields and any code that uses them in an off-season or major release,
    //             and note in the release notes that REV Robotics 40:1 HD Hex Motors
    //             from configurations made in versions prior to 4.0 will no longer work.
    public static String LEGACY_HD_HEX_MOTOR_TAG = "RevRoboticsHDHexMotor";
    public static String NEW_HD_HEX_MOTOR_40_TAG = "RevRobotics40HDHexMotor";

    public static ConfigurationTypeManager getInstance()
        {
        return theInstance;
        }

    private static final ConfigurationTypeManager theInstance = new ConfigurationTypeManager();
    private static final String unspecifiedMotorTypeXmlTag = getXmlTag(UnspecifiedMotor.class);
    private static final String standardServoTypeXmlTag = getXmlTag(Servo.class);
    private static final Class[] typeAnnotationsArray = { ServoType.class, AnalogSensorType.class, DigitalIoDeviceType.class, I2cDeviceType.class, com.qualcomm.robotcore.hardware.configuration.annotations.MotorType.class};
    private static final List<Class> typeAnnotationsList = Arrays.asList(typeAnnotationsArray);

    private final Gson gson = newGson();
    private final Map<String, ConfigurationType> mapTagToConfigurationType = new HashMap<>();
    /** Maps DeviceFlavors (creating a unique display namespace for each one) to display names to
        the XML tag each one is associated with */
    private final Map<ConfigurationType.DeviceFlavor, Map<String, String>> displayNamesMap = new HashMap<>();

    private final Comparator<? super ConfigurationType> configTypeComparator = new Comparator<ConfigurationType>()
        {
        @Override
        public int compare(ConfigurationType lhs, ConfigurationType rhs)
            {
            if (lhs == BuiltInConfigurationType.NOTHING || rhs == BuiltInConfigurationType.NOTHING)
                {
                // NOTHING always comes first in the list, which means that it's the "smallest" value.
                if (lhs == rhs) { return 0; }
                else if (lhs == BuiltInConfigurationType.NOTHING) { return -1; }
                else { return 1; }
                }
            else
                {
                return lhs.getName().compareTo(rhs.getName());
                }
            }
        };

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    public ConfigurationTypeManager()
        {
        for (ConfigurationType.DeviceFlavor flavor : ConfigurationType.DeviceFlavor.values())
            {
            displayNamesMap.put(flavor, new HashMap<>());
            }
        addBuiltinConfigurationTypes();
        }

    //----------------------------------------------------------------------------------------------
    // Retrieval
    //----------------------------------------------------------------------------------------------

    public MotorConfigurationType getUnspecifiedMotorType()
        {
        return (MotorConfigurationType)configurationTypeFromTag(unspecifiedMotorTypeXmlTag);
        }

    public ServoConfigurationType getStandardServoType()
        {
        return (ServoConfigurationType)configurationTypeFromTag(standardServoTypeXmlTag);
        }

    public ConfigurationType configurationTypeFromTag(String xmlTag)
        {
        ConfigurationType result = mapTagToConfigurationType.get(xmlTag);
        if (result == null)
            {
            result = BuiltInConfigurationType.UNKNOWN;
            }
        return result;
        }

    // TODO(Noah): Remove flavor parameter after I2cSensor and the original MotorType have been removed
    public @Nullable UserConfigurationType userTypeFromClass(ConfigurationType.DeviceFlavor flavor, Class<?> clazz)
        {
        String xmlTag = null;

        DeviceProperties deviceProperties = clazz.getAnnotation(DeviceProperties.class);
        if (deviceProperties != null)
            {
            xmlTag = getXmlTag(deviceProperties);
            }

        if (xmlTag == null)
            {
            switch (flavor)
                {
                case I2C:
                    I2cSensor i2cSensor = clazz.getAnnotation(I2cSensor.class);
                    if (i2cSensor != null)
                        {
                        xmlTag = getXmlTag(i2cSensor);
                        }
                    break;
                case MOTOR:
                    MotorType motorType = clazz.getAnnotation(MotorType.class);
                    if (motorType != null)
                        {
                        xmlTag = getXmlTag(motorType);
                        }
                    break;
                }
            }
        return xmlTag==null ? null : (UserConfigurationType) configurationTypeFromTag(xmlTag);
        }

    /**
     * Get the applicable configuration types to populate dropdowns with
     *
     * @param deviceFlavor What type of device is being configured
     * @param controlSystem What type of control system the device is connected to. If null, we err on the side of including types.
     * @param configuringControlHubParent Whether the device being configured is a Control Hub parent.
     *                                    Ignored if you pass anything other than DeviceFlavor.I2C.
     * @param i2cBus Which I2C bus on the REV hub the device is connected to.
     *               Ignored if you pass anything other than DeviceFlavor.I2C and ControlSystem.REV_HUB.
     * @return The list of types that can be selected from
     */
    public @NonNull SortedSet<ConfigurationType> getApplicableConfigTypes(ConfigurationType.DeviceFlavor deviceFlavor, @Nullable ControlSystem controlSystem, boolean configuringControlHubParent, int i2cBus)
        {
        SortedSet<ConfigurationType> result = new TreeSet<>(configTypeComparator);
        for (ConfigurationType type : mapTagToConfigurationType.values())
            {
            if (type.getDeviceFlavor() == deviceFlavor && (controlSystem == null || type.isCompatibleWith(controlSystem)))
                {
                // Filter out the embedded BNO055 IMU from incompatible devices
                if (type == I2cDeviceConfigurationType.getLynxEmbeddedBNO055ImuType() && controlSystem != null &&
                        (controlSystem != ControlSystem.REV_HUB || i2cBus != LynxConstants.EMBEDDED_IMU_BUS))
                    {
                    continue;
                    }

                // Filter out the embedded BHI260AP IMU from incompatible devices
                if (type == I2cDeviceConfigurationType.getLynxEmbeddedBHI260APImuType() &&
                        (!configuringControlHubParent || i2cBus != LynxConstants.EMBEDDED_IMU_BUS))
                    {
                    continue;
                    }
                result.add(type);
                }
            }

        result.add(BuiltInConfigurationType.NOTHING);
        return result;
        }

    /**
     * Get the applicable configuration types to populate dropdowns with (don't use this variant for REV I2C)
     *
     * @param deviceFlavor What type of device is being configured
     * @param controlSystem What type of control system the device is connected to. If null, we err on the side of including types.
     * @param configuringControlHubParent Whether the device being configured is a Control Hub parent.
     *                                    Ignored if you pass anything other than DeviceFlavor.I2C.
     * @return The list of types that can be selected from
     */
    public @NonNull SortedSet<ConfigurationType> getApplicableConfigTypes(ConfigurationType.DeviceFlavor deviceFlavor, @Nullable ControlSystem controlSystem, boolean configuringControlHubParent)
        {
        return getApplicableConfigTypes(deviceFlavor, controlSystem, configuringControlHubParent, 0);
        }

    //----------------------------------------------------------------------------------------------
    // Serialization
    //----------------------------------------------------------------------------------------------

    public Gson getGson()
        {
        return gson;
        }

    public void sendUserDeviceTypes()
        {
        String userDeviceTypes = this.serializeUserDeviceTypes();
        NetworkConnectionHandler.getInstance().sendCommand(new Command(RobotCoreCommandList.CMD_NOTIFY_USER_DEVICE_LIST, userDeviceTypes));
        }


    // Replace the current user device types with the ones contained in the serialization
    public void deserializeUserDeviceTypes(String serialization) // Used on the DS
        {
        clearAllUserTypes();

        ConfigurationType[] newTypesArray = gson.fromJson(serialization, ConfigurationType[].class);

        // RCs prior to 8.1.1 send a duplicate entry for the RevRobotics40HDHexMotor XML tag, that
        // we need to de-duplicate, or else the add() method will end up throwing an exception.
        // Later releases of the RC remove the duplicate entry before sending.
        // GSON deserializes the duplicate entries as separate instances, so we can't de-duplicate
        // using a HashSet (unless an appropriate .equals() implementation is added in the future,
        // which we may or may not want to do).
        boolean alreadySawHdHex40Entry = false;
        List<ConfigurationType> newTypes = new ArrayList<>(Arrays.asList(newTypesArray));
        Iterator<ConfigurationType> newTypesIterator = newTypes.iterator();
        while (newTypesIterator.hasNext())
            {
            if (NEW_HD_HEX_MOTOR_40_TAG.equals(newTypesIterator.next().getXmlTag()))
                {
                if (alreadySawHdHex40Entry)
                    {
                    newTypesIterator.remove();
                    }
                alreadySawHdHex40Entry = true;
                }
            }

        for (ConfigurationType deviceType : newTypes)
            {
            // Ignore built-in types from the RC. We'll use our own instead.
            if (deviceType.isDeviceFlavor(ConfigurationType.DeviceFlavor.BUILT_IN)) continue;
            add((UserConfigurationType) deviceType);
            }

        if (DEBUG)
            {
            for (Map.Entry<String, ConfigurationType> pair : mapTagToConfigurationType.entrySet())
                {
                if (!(pair.getValue() instanceof BuiltInConfigurationType))
                    {
                    RobotLog.vv(TAG, "deserialized: xmltag=%s name=%s class=%s", pair.getValue().getXmlTag(), pair.getValue().getName(), pair.getValue().getClass().getSimpleName());
                    }
                }
            }
        }

    private Gson newGson()
        {
        RuntimeTypeAdapterFactory<ConfigurationType> userDeviceTypeAdapterFactory
                = RuntimeTypeAdapterFactory.of(ConfigurationType.class, "flavor")
                .registerSubtype(BuiltInConfigurationType.class, ConfigurationType.DeviceFlavor.BUILT_IN.toString())
                .registerSubtype(I2cDeviceConfigurationType.class, ConfigurationType.DeviceFlavor.I2C.toString())
                .registerSubtype(MotorConfigurationType.class, ConfigurationType.DeviceFlavor.MOTOR.toString())
                .registerSubtype(ServoConfigurationType.class, ConfigurationType.DeviceFlavor.SERVO.toString())
                .registerSubtype(AnalogSensorConfigurationType.class, ConfigurationType.DeviceFlavor.ANALOG_SENSOR.toString())
                .registerSubtype(DigitalIoDeviceConfigurationType.class, ConfigurationType.DeviceFlavor.DIGITAL_IO.toString());

        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(BuiltInConfigurationType.class, new BuiltInConfigurationTypeJsonAdapter())
                .registerTypeAdapterFactory(userDeviceTypeAdapterFactory)
                .create();
        }


    private String serializeUserDeviceTypes()
        {
        Collection<ConfigurationType> configTypes = mapTagToConfigurationType.values();

        // Filter out duplicate entry for the REV HD Hex 40 motor by converting to a Set.
        // The duplicate entry is there because of the temporary workaround to keep the legacy
        // "RevRoboticsHDHexMotor" XML tag working.
        configTypes = new HashSet<>(configTypes);

        return gson.toJson(configTypes);
        }


    //----------------------------------------------------------------------------------------------
    // Type list management
    //----------------------------------------------------------------------------------------------

    private void addBuiltinConfigurationTypes()
        {
        for (BuiltInConfigurationType type : BuiltInConfigurationType.values())
            {
            mapTagToConfigurationType.put(type.getXmlTag(), type);
            displayNamesMap.get(type.getDeviceFlavor()).put(type.getName(), type.getXmlTag());
            }
        }

    private void add(UserConfigurationType deviceType)
        {
        String xmlTag = deviceType.getXmlTag();

        ConfigurationType existingConfigurationType = mapTagToConfigurationType.get(xmlTag);
        if (existingConfigurationType == null)
            {
            // This XML tag is not currently mapped to anything
            mapTagToConfigurationType.put(deviceType.getXmlTag(), deviceType);
            displayNamesMap.get(deviceType.getDeviceFlavor()).put(deviceType.getName(), deviceType.getXmlTag());
            }
        else
            {
            // This needs to execute BEFORE the call to addAdditionalTypeToInstantiate()
            String originalDisplayName = existingConfigurationType.getName();
            boolean nonMatchingDisplayName = !originalDisplayName.equals(deviceType.getName());

            // This XML tag is already mapped to another configuration type. Add this new type to
            // the list of additional types to instantiate on the existing type.

            // It should be safe to cast both the existing and new configuration types to
            // InstantiableUserConfigurationType because checkAnnotationParameterConstraints() would
            // have rejected the type if they weren't both instantiable.
            ((InstantiableUserConfigurationType) existingConfigurationType)
                    .addAdditionalTypeToInstantiate((InstantiableUserConfigurationType) deviceType);

            // This needs to execute AFTER the call to addAdditionalTypeToInstantiate()
            if (nonMatchingDisplayName)
                {
                boolean newDisplayNameIgnored = originalDisplayName.equals(existingConfigurationType.getName());
                RobotLog.addGlobalWarningMessage("XML tag %s has multiple display names associated with it: \"%s\" and \"%s\". Ignoring \"%s\".",
                        deviceType.getXmlTag(),
                        originalDisplayName,
                        deviceType.getName(),
                        newDisplayNameIgnored ? deviceType.getName() : originalDisplayName);
                }

            // Calling addAdditionalTypeToInstantiate() may have changed existingConfigurationType's
            // display name, but we do NOT want to update displayNamesMap to remove the old display
            // name, so that there can never be ambiguity about which XML tag a given display name
            // is associated with. Instead, we just make sure that the name of the newly-added type
            // is represented (even if it's not actually used by the configuration UI).
            displayNamesMap.get(deviceType.getDeviceFlavor()).put(deviceType.getName(), deviceType.getXmlTag());
            }

        if (deviceType.getXmlTag().equals(NEW_HD_HEX_MOTOR_40_TAG))
            {
            mapTagToConfigurationType.put(LEGACY_HD_HEX_MOTOR_TAG, deviceType);
            }
        }

    private void clearAllUserTypes()
        {
        mapTagToConfigurationType.clear();
        for (Map<String, String> namespace: displayNamesMap.values())
            {
            namespace.clear();
            }

        addBuiltinConfigurationTypes();
        }

    private void clearUserTypesFromSource(ClassSource classSource)
        {
        List<ConfigurationType> extantTypes = new ArrayList<>(mapTagToConfigurationType.values());  // capture to avoid deleting while iterating

        for (ConfigurationType configurationType : extantTypes)
            {
            if (configurationType instanceof InstantiableUserConfigurationType)
                {
                InstantiableUserConfigurationType.ClearTypesFromSourceResult result
                        = ((InstantiableUserConfigurationType) configurationType).clearTypesFromSource(classSource);

                // Free any newly-unused display names
                for (String freedDisplayName: result.freedDisplayNames)
                    {
                    displayNamesMap.get(configurationType.getDeviceFlavor()).remove(freedDisplayName);
                    }

                if (result.newTopLevelType == null)
                    {
                    // This XML tag was entirely removed.
                    mapTagToConfigurationType.remove(configurationType.getXmlTag());
                    }
                else
                    {
                    // Update the XML tag map with the new top-level type
                    // (which may or may not be different from before)
                    mapTagToConfigurationType.put(configurationType.getXmlTag(), result.newTopLevelType);
                    }
                }
            else // configurationType is not an instance of InstantiableUserConfigurationType
                {
                if (configurationType.getClassSource() == classSource)
                    {
                    if (configurationType instanceof BuiltInConfigurationType)
                        {
                        continue; // This method only clears user types
                        }
                    mapTagToConfigurationType.remove(configurationType.getXmlTag());
                    displayNamesMap.get(configurationType.getDeviceFlavor()).remove(configurationType.getName());
                    }
                }
            }
        }

    //----------------------------------------------------------------------------------------------
    // Annotation parsing
    //----------------------------------------------------------------------------------------------

    @Override public void filterAllClassesStart()
        {
        clearAllUserTypes();
        }

    @Override public void filterOnBotJavaClassesStart()
        {
        clearUserTypesFromSource(ClassSource.ONBOTJAVA);
        }

    @Override public void filterExternalLibrariesClassesStart()
        {
        clearUserTypesFromSource(ClassSource.EXTERNAL_LIB);
        }

    @Override public void filterClass(Class clazz)
        {
        // Unlike filterOnBotJavaClass() and filterExternalLibrariesClass(), filterClass() can be
        // called for a class from any source.
        ClassSource classSource = ClassSource.APK;
        if (OnBotJavaDeterminer.isOnBotJava(clazz))
            {
            classSource = ClassSource.ONBOTJAVA;
            }
        else if (OnBotJavaDeterminer.isExternalLibraries(clazz))
            {
            classSource = ClassSource.EXTERNAL_LIB;
            }
        filterClass(clazz, classSource);
        }

    @Override public void filterOnBotJavaClass(Class clazz)
        {
        filterClass(clazz, ClassSource.ONBOTJAVA);
        }

    @Override public void filterExternalLibrariesClass(Class clazz)
        {
        filterClass(clazz, ClassSource.EXTERNAL_LIB);
        }

    private void filterClass(Class<?> clazz, ClassSource classSource)
        {
        if (addMotorTypeFromDeprecatedAnnotation(clazz, classSource)) return;

        if (addI2cTypeFromDeprecatedAnnotation(clazz, classSource)) return;

        Annotation specificTypeAnnotation = getTypeAnnotation(clazz);
        if (specificTypeAnnotation == null) return;

        DeviceProperties devicePropertiesAnnotation = clazz.getAnnotation(DeviceProperties.class);
        if (devicePropertiesAnnotation == null)
            {
            reportConfigurationError("Class " + clazz.getSimpleName() + " annotated with " + specificTypeAnnotation + " is missing @DeviceProperties annotation.");
            return;
            }

        UserConfigurationType configurationType = createAppropriateConfigurationType(specificTypeAnnotation, devicePropertiesAnnotation, clazz, classSource);
        configurationType.processAnnotation(devicePropertiesAnnotation);
        configurationType.finishedAnnotations(clazz);
        if (configurationType.annotatedClassIsInstantiable())
            {
            if (checkInstantiableTypeConstraints((InstantiableUserConfigurationType)configurationType))
                {
                add(configurationType);
                }
            }
        else
            {
            if (checkAnnotationParameterConstraints(configurationType))
                {
                add(configurationType);
                }
            }
        }


    @Override public void filterAllClassesComplete()
        {
        // Nothing to do
        }

    @Override public void filterOnBotJavaClassesComplete()
        {
        // Nothing to do
        }

    @Override public void filterExternalLibrariesClassesComplete()
        {
        // Nothing to do
        }

    @SuppressWarnings("unchecked")
    private UserConfigurationType createAppropriateConfigurationType(Annotation specificTypeAnnotation, DeviceProperties devicePropertiesAnnotation, Class clazz, ClassSource classSource)
        {
        UserConfigurationType configurationType = null;
        if (specificTypeAnnotation instanceof ServoType)
            {
            configurationType = new ServoConfigurationType(clazz, getXmlTag(devicePropertiesAnnotation), classSource);
            ((ServoConfigurationType) configurationType).processAnnotation((ServoType) specificTypeAnnotation);
            }
        else if (specificTypeAnnotation instanceof com.qualcomm.robotcore.hardware.configuration.annotations.MotorType)
            {
            configurationType = new MotorConfigurationType(clazz, getXmlTag(devicePropertiesAnnotation), classSource);
            processMotorSupportAnnotations(clazz, (MotorConfigurationType) configurationType);
            ((MotorConfigurationType) configurationType).processAnnotation((com.qualcomm.robotcore.hardware.configuration.annotations.MotorType) specificTypeAnnotation);
            }
        else if (specificTypeAnnotation instanceof AnalogSensorType)
            {
            configurationType = new AnalogSensorConfigurationType(clazz, getXmlTag(devicePropertiesAnnotation), classSource);
            }
        else if (specificTypeAnnotation instanceof DigitalIoDeviceType)
            {
            configurationType = new DigitalIoDeviceConfigurationType(clazz, getXmlTag(devicePropertiesAnnotation), classSource);
            }
        else if (specificTypeAnnotation instanceof I2cDeviceType)
            {
            configurationType = new I2cDeviceConfigurationType(clazz, getXmlTag(devicePropertiesAnnotation), classSource);
            }
        return configurationType;
        }

    /**
     * @return true if a new MotorConfigurationType was added
     */
    @SuppressWarnings("deprecation")
    private boolean addMotorTypeFromDeprecatedAnnotation(Class<?> clazz, ClassSource classSource)
        {
        if (clazz.isAnnotationPresent(MotorType.class))
            {
            MotorType motorTypeAnnotation = clazz.getAnnotation(MotorType.class);
            //noinspection ConstantConditions
            MotorConfigurationType motorType = new MotorConfigurationType(clazz, getXmlTag(motorTypeAnnotation), classSource);
            motorType.processAnnotation(motorTypeAnnotation);
            processMotorSupportAnnotations(clazz, motorType);
            motorType.finishedAnnotations(clazz);
            // There's some things we need to check about the actual class
            if (!checkAnnotationParameterConstraints(motorType))
                return false;

            add(motorType);
            return true;
            }
        return false;
        }

    /**
     * @return true if a new MotorConfigurationType was added
     */
    @SuppressWarnings("deprecation")
    private boolean addI2cTypeFromDeprecatedAnnotation(Class<?> clazz, ClassSource classSource)
        {
        if (isHardwareDevice(clazz))
            {
            if (clazz.isAnnotationPresent(I2cSensor.class))
                {
                I2cSensor i2cSensorAnnotation = clazz.getAnnotation(I2cSensor.class);
                @SuppressWarnings({ "unchecked", "ConstantConditions" })
                I2cDeviceConfigurationType sensorType = new I2cDeviceConfigurationType((Class<? extends HardwareDevice>) clazz, getXmlTag(i2cSensorAnnotation), classSource);
                sensorType.processAnnotation(i2cSensorAnnotation);
                sensorType.finishedAnnotations(clazz);

                if (!checkInstantiableTypeConstraints(sensorType))
                    return false;

                add(sensorType);
                return true;
                }
            }
        return false;
        }

    private void processMotorSupportAnnotations(Class<?> clazz, MotorConfigurationType motorType)
        {
        motorType.processAnnotation(findAnnotation(clazz, DistributorInfo.class));

        // Can't have both old and new local declarations (pick your horse!), but local definitions
        // override inherited ones
        processNewOldAnnotations(motorType, clazz, ExpansionHubPIDFVelocityParams.class, ExpansionHubMotorControllerVelocityParams.class);
        processNewOldAnnotations(motorType, clazz, ExpansionHubPIDFPositionParams.class, ExpansionHubMotorControllerPositionParams.class);
        }

    protected <NewType extends Annotation, OldType extends Annotation> void processNewOldAnnotations(
            final MotorConfigurationType motorConfigurationType,
            final Class<?> clazz,
            final Class<NewType> newType,
            final Class<OldType> oldType)
        {
        // newType is logical superset of oldType. Thus, there's no reason to ever have an oldType
        // annotation if you've already got a newType one on the same class. Thus, we prohibit same.
        // However, you might want to override an inherited value with either.
        if (!ClassUtil.searchInheritance(clazz, new Predicate<Class<?>>()
                {
                @Override public boolean test(Class<?> aClass)
                    {
                    return processAnnotationIfPresent(motorConfigurationType, clazz, newType);
                    }
                }))
            {
            ClassUtil.searchInheritance(clazz, new Predicate<Class<?>>()
                {
                @Override public boolean test(Class<?> aClass)
                    {
                    return processAnnotationIfPresent(motorConfigurationType, clazz, oldType);
                    }
                });
            }
        }

     protected <A extends Annotation> boolean processAnnotationIfPresent(MotorConfigurationType motorConfigurationType, Class<?> clazz, Class<A> annotationType)
        {
        A annotation = clazz.getAnnotation(annotationType);
        if (annotation != null)
            {
            motorConfigurationType.processAnnotation(annotation);
            return true;
            }
        return false;
        }

    private Annotation getTypeAnnotation(Class clazz)
        {
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations)
            {
            if (typeAnnotationsList.contains(annotation.annotationType())) return annotation;
            }

        return null;
        }

    /** Allow annotations to be inherited if we want them to. */
    private <A extends Annotation> A findAnnotation(Class<?> clazz, final Class<A> annotationType)
        {
        final ArrayList<A> result = new ArrayList<>(1);
        result.add(null);

        ClassUtil.searchInheritance(clazz, new Predicate<Class<?>>()
            {
            @Override public boolean test(Class<?> aClass)
                {
                A annotation = aClass.getAnnotation(annotationType);
                if (annotation != null)
                    {
                    result.set(0, annotation);
                    return true;
                    }
                else
                    return false;
                }
            });

        return result.get(0);
        }

    private boolean checkAnnotationParameterConstraints(UserConfigurationType deviceType)
        {
        String xmlTag = deviceType.getXmlTag();
        ConfigurationType.DeviceFlavor deviceFlavor = deviceType.getDeviceFlavor();

        // Check the user-visible form of the sensor name
        if (!isLegalDeviceTypeName(deviceType.getName()))
            {
            reportConfigurationError("\"%s\" is not a legal device type name", deviceType.getName());
            return false;
            }

        // Check the XML tag
        if (!isLegalXmlTag(xmlTag))
            {
            reportConfigurationError("\"%s\" is not a legal XML tag for the device type \"%s\"", xmlTag, deviceType.getName());
            return false;
            }

        if (deviceType.annotatedClassIsInstantiable())
            {
            // Instantiable configuration types can have duplicate XML tags and display names,
            // but there are rules.

            // For XML tags, any tags that this device type duplicates must also be for instantiable
            // types and have the same DeviceFlavor. Having duplicated XML tags means that for a
            // configuration that contains that XML tag, multiple different classes will be
            // instantiated. The user will be able to retrieve an instance of their desired class
            // from the HardwareMap. This allows for the user to switch which device driver they use
            // to access a device without having to select a different type in the configuration.

            ConfigurationType duplicatingType = mapTagToConfigurationType.get(xmlTag);
            if (duplicatingType != null)
                {
                if (!duplicatingType.annotatedClassIsInstantiable())
                    {
                    // XML tags for instantiable types can only duplicate other instantiable types
                    reportConfigurationError("the XML tag \"%s\" is already defined by a non-instantiable type", xmlTag);
                    return false;
                    }

                if (!duplicatingType.isDeviceFlavor(deviceFlavor))
                    {
                    // XML tags for instantiable types can only duplicate types with the same device flavor
                    reportConfigurationError("the XML tag \"%s\" cannot be registered as a %s device, because it is already registered as a %s device.", xmlTag, deviceFlavor, duplicatingType.getDeviceFlavor());
                    return false;
                    }
                }

            // For display names, we must never have ambiguity about which XML tag a given display
            // name corresponds to. Therefore, any already-processed device type that shares this
            // display name must also have the same XML tag.

            String tagAssociatedWithDuplicateDisplayName = displayNamesMap.get(deviceFlavor).get(deviceType.getName());
            if (tagAssociatedWithDuplicateDisplayName != null && !xmlTag.equals(tagAssociatedWithDuplicateDisplayName))
                {
                reportConfigurationError("the display name \"%s\" is already registered with the XML " +
                        "tag \"%s\", and cannot be registered with the XML tag \"%s\"");
                }
            }
        else
            {
            // Non-instantiable device types must have fully unique names and XML tags.
            if (displayNamesMap.get(deviceFlavor).containsKey(deviceType.getName()))
                {
                reportConfigurationError("the device type \"%s\" is already defined", deviceType.getName());
                return false;
                }
            if (mapTagToConfigurationType.containsKey(xmlTag))
                {
                reportConfigurationError("the XML tag \"%s\" is already defined", xmlTag);
                return false;
                }
            }

        return true;
        }

    private boolean checkInstantiableTypeConstraints(InstantiableUserConfigurationType deviceType)
        {
        if (!checkAnnotationParameterConstraints(deviceType))
            {
            return false;
            }
        // If the class doesn't extend HardwareDevice, that's an error, we'll ignore it
        if (!isHardwareDevice(deviceType.getClazz()))
            {
            reportConfigurationError("'%s' class doesn't inherit from the class 'HardwareDevice'", deviceType.getClazz().getSimpleName());
            return false;
            }

        // If it's not 'public', it can't be loaded by the system and won't work. We report
        // the error and ignore
        if (!Modifier.isPublic(deviceType.getClazz().getModifiers()))
            {
            reportConfigurationError("'%s' class is not declared 'public'", deviceType.getClazz().getSimpleName());
            return false;
            }

        // Can we instantiate?
        if (!deviceType.hasConstructors())
            {
            reportConfigurationError("'%s' class lacks necessary constructor", deviceType.getClazz().getSimpleName());
            return false;
            }
        return true;
        }

    private boolean isLegalDeviceTypeName(String name)
        {
        return Util.isGoodString(name);
        }

    //----------------------------------------------------------------------------------------------
    // Utility
    //----------------------------------------------------------------------------------------------
    public static String getXmlTag(Class clazz)
        {
        DeviceProperties devicePropertiesAnnotation = (DeviceProperties) clazz.getAnnotation(DeviceProperties.class);
        return getXmlTag(devicePropertiesAnnotation);
        }
    private void reportConfigurationError(String format, Object... args)
        {
        String message = String.format(format, args);
        RobotLog.ee(TAG, String.format("configuration error: %s", message));
        RobotLog.setGlobalErrorMsg(message);
        }

    private boolean isHardwareDevice(Class clazz)
        {
        return ClassUtil.inheritsFrom(clazz, HardwareDevice.class);
        }

    private boolean isLegalXmlTag(String xmlTag)
        {
        if (!Util.isGoodString(xmlTag))
            return false;

        // For simplicity, we only allow a restricted subset of what XML allows
        //  https://www.w3.org/TR/REC-xml/#NT-NameStartChar
        String nameStartChar = "\\p{Alpha}_:";
        String nameChar      = nameStartChar + "0-9\\-\\.";

        return xmlTag.matches("^[" + nameStartChar + "][" + nameChar + "]*$");
        }

    private static String getXmlTag(I2cSensor i2cSensor)
        {
        return ClassUtil.decodeStringRes(i2cSensor.xmlTag().trim());
        }
    private static String getXmlTag(MotorType motorType)
        {
        return ClassUtil.decodeStringRes(motorType.xmlTag().trim());
        }
    private static String getXmlTag(DeviceProperties deviceProperties)
        {
        return ClassUtil.decodeStringRes(deviceProperties.xmlTag().trim());
        }
    }

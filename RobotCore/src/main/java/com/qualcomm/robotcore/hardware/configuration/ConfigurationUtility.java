/*
Copyright (c) 2017 Robert Atkinson

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

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.qualcomm.robotcore.R;
import com.qualcomm.robotcore.hardware.DeviceManager;
import com.qualcomm.robotcore.hardware.EmbeddedControlHubModule;
import com.qualcomm.robotcore.hardware.LynxModuleImuType;
import com.qualcomm.robotcore.hardware.LynxModuleMeta;
import com.qualcomm.robotcore.hardware.LynxModuleMetaList;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.I2cDeviceConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.ServoConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.UserConfigurationType;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;

import org.firstinspires.ftc.robotcore.external.function.Supplier;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.Assert;
import org.firstinspires.ftc.robotcore.internal.system.Misc;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;
import org.firstinspires.ftc.robotcore.internal.usb.EthernetOverUsbSerialNumber;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.qualcomm.robotcore.hardware.configuration.LynxConstants.EXPANSION_HUB_PRODUCT_NUMBER;
import static com.qualcomm.robotcore.hardware.configuration.LynxConstants.SERVO_HUB_PRODUCT_NUMBER;

/**
 * {@link ConfigurationUtility} is a utility class containing methods for construction and
 * initializing various types of {@link DeviceConfiguration}. This is used in two contexts:
 * constructing entirely new XML configurations in the configuration UI, and during reading
 * of an existing XML configuration, where it's used to reconstitute fully flushed out
 * configurations, since we only *store* the parts of a configuration which are actually
 * populated.
 */
@SuppressWarnings("WeakerAccess")
public class ConfigurationUtility
    {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public static final String TAG = "ConfigurationUtility";
    public static final int firstNamedDeviceNumber = 1;

    protected Set<String> existingNames;

    public ConfigurationUtility()
        {
        resetNameUniquifiers();
        }

    //----------------------------------------------------------------------------------------------
    // Name management
    //----------------------------------------------------------------------------------------------

    public void resetNameUniquifiers()
        {
        existingNames = new HashSet<String>();
        }

    /** Historical policy is that all names are in a single flat name space. We could perhaps
     * change that, and it might be an improvement, but that hasn't yet been done. */
    public Set<String> getExistingNames(ConfigurationType configurationType)
        {
        return existingNames;
        }

    public void noteExistingName(ConfigurationType configurationType, String name)
        {
        getExistingNames(configurationType).add(name);
        }

    // TODO: the resId should be retreived from configurationType
    protected String createUniqueName(ConfigurationType configurationType, @StringRes int resId)
        {
        return createUniqueName(configurationType, AppUtil.getDefContext().getString(resId), firstNamedDeviceNumber);
        }

    protected String createUniqueName(ConfigurationType configurationType, @StringRes int resId, int preferredParam)
        {
        return createUniqueName(configurationType, AppUtil.getDefContext().getString(resId), preferredParam);
        }

    protected String createUniqueName(ConfigurationType configurationType, String format, int preferredParam)
        {
        return createUniqueName(configurationType, null, format, preferredParam);
        }

    protected String createUniqueName(ConfigurationType configurationType, @StringRes int firstChoiceId, @StringRes int formatId, int preferredParam)
        {
        return createUniqueName(configurationType, AppUtil.getDefContext().getString(firstChoiceId), AppUtil.getDefContext().getString(formatId), preferredParam);
        }

    private String createUniqueName(ConfigurationType configurationType, @Nullable String firstChoice, @NonNull String format, int preferredParam)
        {
        Set<String> existing = getExistingNames(configurationType);
        if (firstChoice != null)
            {
            if (!existing.contains(firstChoice))
                {
                noteExistingName(configurationType, firstChoice);
                return firstChoice;
                }
            }
        String candidate = Misc.formatForUser(format, preferredParam);
        if (!existing.contains(candidate))
            {
            noteExistingName(configurationType, candidate);
            return candidate;
            }
        for (int i = firstNamedDeviceNumber; ; i++)
            {
            candidate = Misc.formatForUser(format, i);
            if (!existing.contains(candidate))
                {
                noteExistingName(configurationType, candidate);
                return candidate;
                }
            }
        }

    //----------------------------------------------------------------------------------------------
    // Building *new* configurations
    //----------------------------------------------------------------------------------------------

    public ControllerConfiguration buildNewControllerConfiguration(SerialNumber serialNumber, DeviceManager.UsbDeviceType deviceType, Supplier<LynxModuleMetaList> lynxModuleSupplier)
        {
        ControllerConfiguration result = null;
        switch (deviceType)
            {
            case WEBCAM:                                        result = buildNewWebcam(serialNumber); break;
            case LYNX_USB_DEVICE:                               result = buildNewLynxUsbDevice(serialNumber, lynxModuleSupplier); break;
        }
        return result;
        }

    public ControllerConfiguration buildNewEthernetOverUsbControllerConfiguration(SerialNumber serialNumber)
        {
            return new EthernetOverUsbConfiguration("Ethernet Device", serialNumber, ((EthernetOverUsbSerialNumber)serialNumber).getIpAddress());
        }

    public WebcamConfiguration buildNewWebcam(SerialNumber serialNumber)
        {
        String name = createUniqueName(BuiltInConfigurationType.WEBCAM, R.string.counted_camera_name);
        WebcamConfiguration result = new WebcamConfiguration(name, serialNumber);
        result.setEnabled(true);
        return result;
        }

    public LynxUsbDeviceConfiguration buildNewLynxUsbDevice(SerialNumber serialNumber, Supplier<LynxModuleMetaList> lynxModuleMetaListSupplier)
        {
        return buildNewLynxUsbDevice(serialNumber, lynxModuleMetaListSupplier.get());
        }
    public LynxUsbDeviceConfiguration buildNewLynxUsbDevice(SerialNumber serialNumber, LynxModuleMetaList metas)
        {
        RobotLog.vv(TAG, "buildNewLynxUsbDevice(%s)...", serialNumber);
        boolean isEmbeddedControlHubUsbDevice = serialNumber.isEmbedded();
        try {
            if (metas == null) metas = new LynxModuleMetaList(serialNumber);
            RobotLog.vv(TAG, "buildLynxUsbDevice(): lynx modules: %s", metas);

            LynxModuleMeta parentMeta = metas.getParent();
            LynxModuleImuType parentImuType;

            if (parentMeta == null)
                {
                parentImuType = LynxModuleImuType.NONE;
                }
            else
                {
                parentImuType = metas.getParent().imuType();
                if (parentImuType == LynxModuleImuType.UNKNOWN)
                    {
                    RobotLog.ww(TAG, "parent IMU type was UNKNOWN in buildNewLynxDevice()");
                    parentImuType = LynxModuleImuType.NONE;
                    }
                }

            List<RhspModuleConfiguration> modules = new LinkedList<>();
            for (LynxModuleMeta meta : metas)
                {
                boolean isEmbeddedControlHubModule =
                        isEmbeddedControlHubUsbDevice &&
                        meta.revProductNumber() == EXPANSION_HUB_PRODUCT_NUMBER &&
                        meta.isParent() &&
                        meta.getModuleAddress() == LynxConstants.CH_EMBEDDED_MODULE_ADDRESS;

                LynxModuleImuType syntheticImuType;
                if (parentImuType != LynxModuleImuType.NONE)
                    {
                    // When the parent has an IMU, that's the IMU that should be used for
                    // performance reasons, so we don't want to add the synthetic IMU on any other
                    // modules.

                    // Because we previously set parentHasImu to true if the RC is outdated,
                    // this branch includes that case.
                    if (meta.isParent()) { syntheticImuType = parentImuType; }
                    else { syntheticImuType = LynxModuleImuType.NONE; }
                    }
                else
                    {
                    // When the parent is known to not have an IMU, we want to add the synthetic IMU
                    // for any modules that are known to physically have one.

                    syntheticImuType = meta.imuType();
                    if (syntheticImuType == LynxModuleImuType.UNKNOWN)
                        {
                        RobotLog.ww(TAG, "module IMU type was UNKNOWN in buildNewLynxDevice()");
                        syntheticImuType = LynxModuleImuType.NONE;
                        }
                    }

                int parentModuleAddress = -1;
                if (parentMeta != null)
                    {
                    parentModuleAddress = parentMeta.getModuleAddress();
                    }

                if (meta.revProductNumber() == EXPANSION_HUB_PRODUCT_NUMBER)
                    {
                    modules.add(buildNewLynxModule(
                            meta.getModuleAddress(),
                            parentModuleAddress,
                            syntheticImuType,
                            true,
                            isEmbeddedControlHubModule));
                    }
                else if (meta.revProductNumber() == SERVO_HUB_PRODUCT_NUMBER)
                    {
                    modules.add(buildNewServoHub(
                            meta.getModuleAddress(),
                            parentModuleAddress,
                            true));
                    }
                else
                    {
                    AppUtil.getInstance().showToast(UILocation.BOTH, "Found unknown REV Hub device", Toast.LENGTH_LONG);
                    }
                }
            DeviceConfiguration.sortByName(modules);
            RobotLog.vv(TAG, "buildNewLynxUsbDevice(%s): %d modules", serialNumber, modules.size());

            String name;
            if (isEmbeddedControlHubUsbDevice) {
                name = createUniqueName(BuiltInConfigurationType.LYNX_USB_DEVICE, R.string.control_hub_usb_device_name, R.string.counted_lynx_usb_device_name, 0);
            } else {
                name = createUniqueName(BuiltInConfigurationType.LYNX_USB_DEVICE, R.string.counted_lynx_usb_device_name);
            }
            LynxUsbDeviceConfiguration result = new LynxUsbDeviceConfiguration(name, modules, serialNumber);
            result.setEnabled(true);
            return result;
            }
        finally
            {
            RobotLog.vv(TAG, "...buildNewLynxUsbDevice(%s): ", serialNumber);
            }
        }

    /**
     * To build a parent lynx module, pass the same number to both moduleAddress and parentModuleAddress
     */
    public LynxModuleConfiguration buildNewLynxModule(
            int moduleAddress,
            int parentModuleAddress,
            LynxModuleImuType syntheticImuType,
            boolean isEnabled,
            boolean isEmbeddedControlHubModule)
        {
        String name;
        if (isEmbeddedControlHubModule) {
            // If this is the Control Hub module, ideally the name will just be "Control Hub"
            name = createUniqueName(BuiltInConfigurationType.LYNX_MODULE, R.string.control_hub_module_name, R.string.counted_lynx_module_name, 0);
        } else {
            // Otherwise, the ideal name is "Expansion Hub X", where X is the module address
            name = createUniqueName(BuiltInConfigurationType.LYNX_MODULE, R.string.counted_lynx_module_name, moduleAddress);
        }

        LynxModuleConfiguration lynxModuleConfiguration = buildEmptyLynxModule(name, moduleAddress, parentModuleAddress, isEnabled);

        // Add the embedded IMU device to the newly created configuration, if applicable
        if (syntheticImuType != LynxModuleImuType.NONE && syntheticImuType != LynxModuleImuType.UNKNOWN)
            {
            UserConfigurationType embeddedIMUConfigurationType;
            if (syntheticImuType == LynxModuleImuType.BNO055)
                {
                embeddedIMUConfigurationType = I2cDeviceConfigurationType.getLynxEmbeddedBNO055ImuType();
                }
            else if (syntheticImuType == LynxModuleImuType.BHI260)
                {
                embeddedIMUConfigurationType = I2cDeviceConfigurationType.getLynxEmbeddedBHI260APImuType();
                }
            else throw new RuntimeException("Unrecognized embedded IMU type");

            Assert.assertTrue(embeddedIMUConfigurationType!=null && embeddedIMUConfigurationType.isDeviceFlavor(ConfigurationType.DeviceFlavor.I2C));

            String imuName = createUniqueName(embeddedIMUConfigurationType, R.string.preferred_imu_name, R.string.counted_imu_name, 1);
            LynxI2cDeviceConfiguration imuConfiguration = new LynxI2cDeviceConfiguration();
            imuConfiguration.setConfigurationType(embeddedIMUConfigurationType);
            imuConfiguration.setName(imuName);
            imuConfiguration.setEnabled(true);
            imuConfiguration.setBus(LynxConstants.EMBEDDED_IMU_BUS);
            lynxModuleConfiguration.getI2cDevices().add(imuConfiguration);
            }

        return lynxModuleConfiguration;
        }

    public ServoHubConfiguration buildNewServoHub(
            int moduleAddress,
            int parentModuleAddress,
            boolean isEnabled)
        {
        String name;

        name = createUniqueName(BuiltInConfigurationType.SERVO_HUB, R.string.counted_servo_hub_name, moduleAddress);

        ServoHubConfiguration servoHubConfiguration = buildEmptyServoHub(name, moduleAddress, parentModuleAddress, isEnabled);

        return servoHubConfiguration;
        }

    /** The distinguishing feature of the 'embedded' Lynx USB device is that it is labelled as
     * 'system synthetic', in that we make it up, making sure it's there independent of whether
     * it's been configured in the hardware map or not. We do this so that we can ALWAYS access
     * the functionality of that embedded module, such as its LEDs. */
    protected LynxUsbDeviceConfiguration buildNewEmbeddedLynxUsbDevice(final @NonNull DeviceManager deviceManager)
        {
        LynxModuleMeta embeddedMeta = new LynxModuleMeta(LynxConstants.CH_EMBEDDED_MODULE_ADDRESS, true);
        embeddedMeta.setRevProductNumber(EXPANSION_HUB_PRODUCT_NUMBER);
        embeddedMeta.setImuType(EmbeddedControlHubModule.getImuType());
        LynxModuleMetaList embeddedMetaList = new LynxModuleMetaList(
                LynxConstants.SERIAL_NUMBER_EMBEDDED,
                Collections.singletonList(embeddedMeta));
        LynxUsbDeviceConfiguration controllerConfiguration = buildNewLynxUsbDevice(LynxConstants.SERIAL_NUMBER_EMBEDDED, embeddedMetaList);
        controllerConfiguration.setEnabled(true);
        controllerConfiguration.setSystemSynthetic(true);
        for (RhspModuleConfiguration moduleConfiguration : controllerConfiguration.getModules())
            {
            moduleConfiguration.setSystemSynthetic(true);
            }

        return controllerConfiguration;
        }

    //----------------------------------------------------------------------------------------------
    // Supporting methods
    //----------------------------------------------------------------------------------------------

    protected static List<DeviceConfiguration> buildEmptyDevices(int initialPort, int size, ConfigurationType type)
        {
        ArrayList<DeviceConfiguration> list = new ArrayList<DeviceConfiguration>();
        for (int i = 0; i < size; i++)
            {
            list.add(new DeviceConfiguration(i + initialPort, type, DeviceConfiguration.DISABLED_DEVICE_NAME, false));
            }
        return list;
        }

    public static List<DeviceConfiguration> buildEmptyMotors(int initialPort, int size)
        {
        return buildEmptyDevices(initialPort, size, MotorConfigurationType.getUnspecifiedMotorType());
        }

    public static List<DeviceConfiguration> buildEmptyServos(int initialPort, int size)
        {
        return buildEmptyDevices(initialPort, size, ServoConfigurationType.getStandardServoType());
        }

    /**
     * To build a parent lynx module, pass the same number to both moduleAddress and parentModuleAddress
     */
    protected LynxModuleConfiguration buildEmptyLynxModule(String name, int moduleAddress, int parentModuleAddress, boolean isEnabled)
        {
        RobotLog.vv(TAG, "buildEmptyLynxModule() mod#=%d...", moduleAddress);
        //
        noteExistingName(BuiltInConfigurationType.LYNX_MODULE, name);
        //
        LynxModuleConfiguration moduleConfiguration = new LynxModuleConfiguration(name);
        moduleConfiguration.setModuleAddress(moduleAddress);
        moduleConfiguration.setParentModuleAddress(parentModuleAddress);
        moduleConfiguration.setEnabled(isEnabled);
        //
        RobotLog.vv(TAG, "...buildEmptyLynxModule() mod#=%d", moduleAddress);
        return moduleConfiguration;
        }

    protected ServoHubConfiguration buildEmptyServoHub(String name, int moduleAddress, int parentModuleAddress, boolean isEnabled)
        {
        RobotLog.vv(TAG, "buildEmptyServoHub() mod#=%d...", moduleAddress);
        //
        noteExistingName(BuiltInConfigurationType.SERVO_HUB, name);
        //
        ServoHubConfiguration moduleConfiguration = new ServoHubConfiguration(name);
        moduleConfiguration.setModuleAddress(moduleAddress);
        moduleConfiguration.setParentModuleAddress(parentModuleAddress);
        moduleConfiguration.setEnabled(isEnabled);
        //
        RobotLog.vv(TAG, "...buildEmptyServoHub() mod#=%d", moduleAddress);
        return moduleConfiguration;
        }
    }

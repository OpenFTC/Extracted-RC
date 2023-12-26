/*
Copyright (c) 2022 REV Robotics

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
package com.qualcomm.hardware.bosch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.hardware.R;
import com.qualcomm.hardware.lynx.LynxI2cDeviceSynch;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.commands.core.LynxFirmwareVersionManager;
import com.qualcomm.hardware.lynx.commands.core.LynxI2cWriteMultipleBytesCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxI2cWriteReadMultipleBytesCommand;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.EmbeddedControlHubModule;
import com.qualcomm.robotcore.hardware.HardwareDeviceHealth;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDeviceWithParameters;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
import com.qualcomm.robotcore.hardware.I2cWaitControl;
import com.qualcomm.robotcore.hardware.I2cWarningManager;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.QuaternionBasedImuHelper;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.TypeConversion;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.internal.hardware.android.AndroidBoard;
import org.firstinspires.ftc.robotcore.internal.hardware.android.GpioPin;
import org.firstinspires.ftc.robotcore.internal.system.AppAliveNotifier;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.SystemProperties;
import org.firstinspires.ftc.robotcore.internal.ui.ProgressParameters;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;

@I2cDeviceType
@DeviceProperties(name = "@string/lynx_embedded_bhi260ap_imu_name", xmlTag = LynxConstants.EMBEDDED_BHI260AP_IMU_XML_TAG, description = "@string/lynx_embedded_imu_description", builtIn = true)
public class BHI260IMU extends I2cDeviceSynchDeviceWithParameters<I2cDeviceSynchSimple, IMU.Parameters> implements IMU {
    // TODO(Noah): Document which virtual sensors are enabled
    // General constants
    private static final String TAG = "BHI260IMU";
    public static final boolean DIAGNOSTIC_MODE = false; // Diagnostic mode generates a lot of automated I2C traffic
    // Enabling FIFO parsing in diagnostic mode causes error code 0x77 because something's wrong with our Interrupt Status checking
    public static final boolean DIAGNOSTIC_MODE_FIFO_PARSING = false;
    private static final I2cAddr I2C_ADDR = I2cAddr.create7bit(0x28);
    private static final double QUATERNION_SCALE_FACTOR = Math.pow(2, -14);
    private static final int PRODUCT_ID = 0x89;
    // It takes less than a second to boot from flash with no descriptor, but let's allow 2
    private static final int BOOT_FROM_FLASH_TIMEOUT_MS = 2000;
    // See W25Q64JW flash datasheet. In the worst-case scenario, the IMU will erase the flash chip
    // 4KiB at a time. Each 4KiB erase can take up to 400ms. We are erasing around 110-120 KiB of
    // the chip (to be conservative, let's use a value of 140 KiB), so erasing could theoretically
    // take as long as  take 14,000ms (not including any overhead). Of course, the typical 4KiB
    // erase time is just 45ms, so it's extremely unlikely to ever take that long.
    private static final int ERASE_FLASH_TIMEOUT_MS = 14_000;
    private static final int MAX_ANGULAR_VELOCITY_DEG_PER_S = 2000;

    // Generic command constants
    private static final int COMMAND_HEADER_LENGTH = 4;
    private static final int MAX_SEND_I2C_BYTES_NO_REGISTER = LynxI2cWriteMultipleBytesCommand.cbPayloadLast;
    private static final int MAX_SEND_I2C_BYTES_WITH_REGISTER = MAX_SEND_I2C_BYTES_NO_REGISTER - 1;
    private static final int MAX_READ_I2C_BYTES = LynxI2cWriteReadMultipleBytesCommand.cbPayloadLast;

    // General firmware flashing constants
    private static final boolean DEBUG_FW_FLASHING = false;
    private static final int FW_START_ADDRESS = 0x1F84; // From the datasheet
    // ALWAYS update these two together
    private static final int FW_RESOURCE = R.raw.rev_bhi260_ap_fw_20;
    private static final int BUNDLED_FW_VERSION = 20;

    // Write Flash constants

    // For the Write Flash command, it's simpler if we think of the starting address as part of the
    // header instead of the payload, so that the "payload" only contains FW and padding bytes
    private static final int WRITE_FLASH_COMMAND_HEADER_LENGTH = 8;

    private static final int MAX_WRITE_FLASH_FIRMWARE_AND_PADDING_BYTES = MAX_SEND_I2C_BYTES_WITH_REGISTER - WRITE_FLASH_COMMAND_HEADER_LENGTH;
    private static final int MAX_WRITE_FLASH_FIRMWARE_BYTES = // Factors in the need for padding bytes
            MAX_WRITE_FLASH_FIRMWARE_AND_PADDING_BYTES - (MAX_WRITE_FLASH_FIRMWARE_AND_PADDING_BYTES % 4);
    // We'll likely need to increase this timeout if we gain the ability to write more I2C bytes at once
    private static final int WRITE_FLASH_RESPONSE_TIMEOUT_MS = 1_000;

    // Static state
    // Safely having static state is only possible because we KNOW that there will only be AT MOST a
    // single BHI260 IMU connected to the Robot Controller, which is not the case for any other type
    // of sensor. Only state that needs to be shared between firmware flashing and normal sensor
    // operation should be static.

    private static final StatusAndDebugFifoModeManager statusAndDebugFifoModeManager = new StatusAndDebugFifoModeManager();
    private static volatile Thread diagnosticModeThread;
    // We need a BHI-specific I2C lock because there are some communication sequences that need to
    // happen atomically.
    private static final Object i2cLock = new Object();
    // Requesting data from the general purpose register via a GPIO pin needs to be done atomically
    // with retrieving that data via I2C.
    private static final Object genPurposeRegisterDataRetrievalLock = new Object();

    // Static methods

    public static boolean imuIsPresent(I2cDeviceSynchSimple deviceClient) {
        deviceClient.setI2cAddress(I2C_ADDR);

        RobotLog.vv(TAG, "Suppressing I2C warnings while we check for a BHI260AP IMU");
        I2cWarningManager.suppressNewProblemDeviceWarnings(true);
        try {
            // Check product identifier
            int productId = read8(deviceClient, Register.PRODUCT_IDENTIFIER);
            if (productId == PRODUCT_ID) {
                RobotLog.vv(TAG, "Found BHI260AP IMU");
                return true;
            } else {
                RobotLog.vv(TAG, "No BHI260AP IMU found");
                return false;
            }
        } finally {
            I2cWarningManager.suppressNewProblemDeviceWarnings(false);
        }
    }

    public static void flashFirmwareIfNecessary(I2cDeviceSynchSimple deviceClient) {
        deviceClient.setI2cAddress(I2C_ADDR);

        try {
            waitForHostInterface(deviceClient);
            checkForFlashPresence(deviceClient);
            boolean alreadyFlashed = waitForFlashVerification(deviceClient);

            int firmwareVersion;
            if (alreadyFlashed) {
                firmwareVersion = read16(deviceClient, Register.USER_VERSION);
            } else {
                firmwareVersion = 0;
            }

            RobotLog.vv(TAG, "flashFirmwareIfNecessary() alreadyFlashed=%b firmwareVersion=%d", alreadyFlashed, firmwareVersion);

            if (firmwareVersion != BUNDLED_FW_VERSION) {
                try {
                    // When we are flashing the firmware at the factory, we know that there is no
                    // wire connected to port 0, so it's safe to flash at 400 KHz.
                    if (SystemProperties.getBoolean("persist.bhi260.flash400khz", false)) {
                        RobotLog.vv(TAG, "Setting I2C bus speed to 400KHz for firmware flashing");
                        ((LynxI2cDeviceSynch) deviceClient).setBusSpeed(LynxI2cDeviceSynch.BusSpeed.FAST_400K);
                    } else {
                        RobotLog.vv(TAG, "Setting I2C bus speed to 100KHz for firmware flashing");
                        ((LynxI2cDeviceSynch) deviceClient).setBusSpeed(LynxI2cDeviceSynch.BusSpeed.STANDARD_100K);
                    }

                    // See flashing instructions in section 18.2 of the datasheet
                    RobotLog.ii(TAG, "Flashing IMU firmware version %d", BUNDLED_FW_VERSION);
                    ElapsedTime fwFlashTimer = new ElapsedTime();

                    ByteBuffer fwBuffer;
                    try {
                        fwBuffer = ByteBuffer.wrap(ReadWriteFile.readRawResourceBytesOrThrow(FW_RESOURCE));
                    } catch (IOException e) {
                        RobotLog.ee(TAG, e, "Failed to read IMU firmware file");
                        throw new InitException();
                    }

                    int fwLength = fwBuffer.remaining();

                    // Reset the IMU (which puts it in Host Boot mode), and wait for the command to
                    // be written (which will result in us waiting more than the 4 uS required by
                    // the datasheet)
                    RobotLog.vv(TAG, "Resetting IMU");
                    write8(deviceClient, Register.RESET_REQUEST, 0x1, I2cWaitControl.WRITTEN);

                    waitForHostInterface(deviceClient);

                    // The read-only general purpose register is usually used for the custom REV
                    // data output, but at this point in the boot process it contains the JDEC
                    // manufacturer ID for the IMU's external flash chip.
                    RobotLog.dd(TAG, "Flash device's JDEC manufacturer ID: 0x%X", read8(deviceClient, Register.GEN_PURPOSE_READ));

                    if (DEBUG_FW_FLASHING) { RobotLog.dd(TAG, "FW length: %d bytes", fwLength); }

                    RobotLog.vv(TAG, "Wiping IMU flash memory");
                    // Show indeterminate progress bar while we wipe the flash memory
                    AppUtil.getInstance().showProgress(UILocation.BOTH, AppUtil.getDefContext().getString(R.string.flashingControlHubImu), new ProgressParameters(0, fwLength));
                    ByteBuffer eraseFlashPayload = ByteBuffer.allocate(8)
                            .order(ByteOrder.LITTLE_ENDIAN)
                            .putInt(FW_START_ADDRESS) // Start address
                            .putInt(FW_START_ADDRESS + fwLength); // End address
                    try {
                        sendCommandAndWaitForResponse(deviceClient, CommandType.ERASE_FLASH, eraseFlashPayload.array(), ERASE_FLASH_TIMEOUT_MS);
                    } catch (CommandFailureException e) {
                        RobotLog.ee(TAG, e, "IMU flash erase failed");
                        throw new InitException();
                    } finally {
                        // Erasing may have taken a long time; ensure the CH OS watchdog doesn't trip.
                        AppAliveNotifier.getInstance().notifyAppAlive(); // Make sure we don't trip the CH OS watchdog
                    }

                    // Send up to 64K in a single Write Flash command (will require multiple I2C writes)
                    int nextStartAddress = FW_START_ADDRESS;
                    int numBytesAlreadyWritten = 0;

                    RobotLog.ii(TAG, "Sending firmware data");
                    while (fwBuffer.hasRemaining()) {
                        int fwBytesToTransmitInCommand = Math.min(fwBuffer.remaining(), MAX_WRITE_FLASH_FIRMWARE_BYTES);

                        if (DEBUG_FW_FLASHING) { RobotLog.dd(TAG, "Transmitting Write Flash command with %d fw bytes", fwBytesToTransmitInCommand); }
                        try {
                            // This function will handle keeping the OS watchdog fed while it runs
                            sendWriteFlashCommandAndWaitForResponse(deviceClient, nextStartAddress, fwBytesToTransmitInCommand, fwBuffer);
                        } catch (CommandFailureException e) {
                            RobotLog.ee(TAG, e, "Write Flash command failed");
                            throw new InitException();
                        }
                        nextStartAddress += fwBytesToTransmitInCommand;
                        numBytesAlreadyWritten += fwBytesToTransmitInCommand;
                        AppUtil.getInstance().showProgress(UILocation.BOTH, AppUtil.getDefContext().getString(R.string.flashingControlHubImu), new ProgressParameters(numBytesAlreadyWritten, fwLength));
                    }

                    // We've finished writing the firmware to flash, now we tell the chip to boot from it
                    RobotLog.vv(TAG, "Booting into newly-flashed firmware");
                    sendCommand(deviceClient, CommandType.BOOT_FLASH, null);

                    waitForHostInterface(deviceClient);
                    checkForFlashPresence(deviceClient);
                    boolean flashSucceeded = waitForFlashVerification(deviceClient);
                    if (flashSucceeded) {
                        RobotLog.vv(TAG, "Successfully flashed Control Hub IMU firmware in %.2f seconds", fwFlashTimer.seconds());
                    } else {
                        RobotLog.ee(TAG, "IMU flash verification failed after flashing firmware");
                        throw new InitException();
                    }
                } finally {
                    AppUtil.getInstance().dismissProgress(UILocation.BOTH);
                }
            }

            if (DIAGNOSTIC_MODE) {
                RobotLog.vv(TAG, "Starting diagnostic mode");
                startDiagnosticMode();
            }
        } catch (InitException e) {
            RobotLog.addGlobalWarningMessage(AppUtil.getDefContext().getString(R.string.controlHubImuFwFlashFailed));
        }
    }

    // Instance state
    private int fwVersion = 0;
    private final QuaternionBasedImuHelper helper;

    // Do not assign values to these until initialization. Getting objects for the GPIO pins we need
    // will cause a warning on Control Hub OS versions prior to 1.1.3, which we don't want to do
    // unless the sensor actually gets initialized.
    private DigitalChannel gameRVRequestGpio;
    private DigitalChannel correctedGyroRequestGpio;

    // Constructor and instance methods

    public BHI260IMU(I2cDeviceSynchSimple i2cDeviceSynchSimple, boolean deviceClientIsOwned) {
        super(i2cDeviceSynchSimple, deviceClientIsOwned, new Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));

        helper = new QuaternionBasedImuHelper(parameters.imuOrientationOnRobot);

        if (imuIsPresent(deviceClient)) {
            // Reset the yaw to ensure predictable behavior on app launch and Robot Restart, which
            // is when hardware device objects get (re) created. On boot, it's clearer if yaw 0 is
            // set at the time when the rest of the system finishes booting, instead of within the
            // first couple of seconds of power being applied. On Robot Restart, it's _much_ clearer
            // if the yaw gets reset to 0, instead of being set to the current rotation relative to
            // power on.
            if (internalInitialize(parameters)) {
                // We call helper.resetYaw() directly instead of this.resetYaw() so that we can
                // specify a nice long timeout
                helper.resetYaw(TAG, this::getRawQuaternion, 500);
            }

        }

    }

    @Override
    protected boolean internalInitialize(@NonNull Parameters parameters) {
        // This driver does NOT perform a reset, so that we don't wipe out the yaw offset until the
        // user requests that we do so.
        if (!parameters.getClass().equals(Parameters.class)) {
            RobotLog.addGlobalWarningMessage(AppUtil.getDefContext().getString(R.string.parametersForOtherDeviceUsed));
        }
        parameters = parameters.copy();
        this.parameters = parameters;

        // We want these fields to get initialized even if initialization ends up failing
        gameRVRequestGpio = AndroidBoard.getInstance().getBhi260Gpio5();
        correctedGyroRequestGpio = AndroidBoard.getInstance().getBhi260Gpio6();
        gameRVRequestGpio.setMode(DigitalChannel.Mode.OUTPUT);
        correctedGyroRequestGpio.setMode(DigitalChannel.Mode.OUTPUT);

        helper.setImuOrientationOnRobot(parameters.imuOrientationOnRobot);

        deviceClient.setI2cAddress(I2C_ADDR);

        if (!imuIsPresent(deviceClient)) {
            RobotLog.ee(TAG, "Could not find Control Hub IMU (BHI260AP)");
            return false;
        }

        try {
            waitForHostInterface(deviceClient);
            checkForFlashPresence(deviceClient);
            boolean flashContentsVerified = waitForFlashVerification(deviceClient);

            if (!flashContentsVerified) {
                RobotLog.ee(TAG, "IMU flash contents were not verified");
                throw new InitException();
            }

            if (readBootStatusFlags(deviceClient).contains(BootStatusFlag.FIRMWARE_HALTED)) {
                RobotLog.ee(TAG, "IMU reports that its firmware is not running");
                return false;
            }

            disableFifoTimestamps(deviceClient);

            fwVersion = read16(deviceClient, Register.USER_VERSION);
            if (fwVersion == BUNDLED_FW_VERSION) {
                RobotLog.dd(TAG, "Firmware version: %d", fwVersion);
            } else {
                RobotLog.ee(TAG, "Firmware version is incorrect. expected=%d actual=%d", BUNDLED_FW_VERSION, fwVersion);
                throw new InitException();
            }

            // Disable the I2C watchdog
            write8(deviceClient, Register.HOST_CONTROL, 0x0, I2cWaitControl.ATOMIC);

            // TODO(Noah): Send self-test commands (check Error Register after both)
            // TODO(Noah): Look into using Fast Offset Compensation

            // Set the dynamic range of the Gyroscope Corrected data (you can't set the dynamic
            // range for virtual sensors that combine data from more than one physical sensor)
            setSensorDynamicRange(Sensor.GYROSCOPE_CORRECTED_DATA_HOLDER, MAX_ANGULAR_VELOCITY_DEG_PER_S);

            configureSensor(Sensor.GAME_ROTATION_VECTOR_DATA_HOLDER, 800, 0);
            configureSensor(Sensor.GAME_ROTATION_VECTOR_GPIO_HANDLER, 800, 0);
            configureSensor(Sensor.GYROSCOPE_CORRECTED_DATA_HOLDER, 800, 0);
            configureSensor(Sensor.GYROSCOPE_CORRECTED_GPIO_HANDLER, 800, 0);
        } catch (InitException e) {
            return false;
        }

        return true;
    }

    @Override public void resetYaw() {
        helper.resetYaw(TAG, this::getRawQuaternion, 50);
    }

    @Override public YawPitchRollAngles getRobotYawPitchRollAngles() {
        return helper.getRobotYawPitchRollAngles(TAG, this::getRawQuaternion);
    }

    @Override public Orientation getRobotOrientation(AxesReference reference, AxesOrder order, AngleUnit angleUnit) {
        return helper.getRobotOrientation(TAG, this::getRawQuaternion, reference, order, angleUnit);
    }

    @Override public Quaternion getRobotOrientationAsQuaternion() {
        return helper.getRobotOrientationAsQuaternion(TAG, this::getRawQuaternion, true);
    }

    @Override public AngularVelocity getRobotAngularVelocity(AngleUnit angleUnit) {
        return helper.getRobotAngularVelocity(getRawAngularVelocity(), angleUnit);
    }

    /**
     * @return The orientation of the IMU, defined in terms of the IMU's coordinate system, not the
     *         robot's.
     */
    protected Quaternion getRawQuaternion() throws QuaternionBasedImuHelper.FailedToRetrieveQuaternionException {
        if (!(gameRVRequestGpio instanceof GpioPin)) {
            // We must be running on a CH OS older than 1.1.3, there's no sense wasting time trying
            // to read a value.
            throw new QuaternionBasedImuHelper.FailedToRetrieveQuaternionException();
        }

        long timestamp;
        ByteBuffer data;
        synchronized (genPurposeRegisterDataRetrievalLock) {
            gameRVRequestGpio.setState(true);
            timestamp = System.nanoTime();
            // We need to wait at least 500 microseconds before performing the I2C read. Fortunately
            // for us, that amount of time has already passed by the time that the internal LynxModule
            // finishes receiving the I2C read command.
            data = readMultiple(deviceClient, Register.GEN_PURPOSE_READ, 8);
            gameRVRequestGpio.setState(false);
        }

        int xInt = data.getShort();
        int yInt = data.getShort();
        int zInt = data.getShort();
        int wInt = data.getShort();

        if (xInt == 0 && yInt == 0 && zInt == 0 && wInt == 0) {
            // All zeros is not a valid quaternion.
            throw new QuaternionBasedImuHelper.FailedToRetrieveQuaternionException();
        }

        float x = (float) (xInt * QUATERNION_SCALE_FACTOR);
        float y = (float) (yInt * QUATERNION_SCALE_FACTOR);
        float z = (float) (zInt * QUATERNION_SCALE_FACTOR);
        float w = (float) (wInt * QUATERNION_SCALE_FACTOR);
        return new Quaternion(w, x, y, z, timestamp);
    }

    /**
     * @return The angular velocity of the IMU, defined in terms of the IMU's coordinate system, not
     *         the robot's.
     */
    protected AngularVelocity getRawAngularVelocity() {
        if (!(correctedGyroRequestGpio instanceof GpioPin)) {
            // We must be running on a CH OS older than 1.1.3, there's no sense wasting time trying
            // to read a value.
            return new AngularVelocity(AngleUnit.DEGREES, 0, 0, 0,0);
        }

        long timestamp;
        ByteBuffer data;
        synchronized (genPurposeRegisterDataRetrievalLock) {
            correctedGyroRequestGpio.setState(true);
            timestamp = System.nanoTime();
            data = readMultiple(deviceClient, Register.GEN_PURPOSE_READ, 6);
            correctedGyroRequestGpio.setState(false);
        }

        int xInt = data.getShort();
        int yInt = data.getShort();
        int zInt = data.getShort();
        // TODO(Noah): Provide a way for the user to specify the desired Dynamic Range, set the
        //             dynamic range via the Change Sensor Dynamic Range command, and dynamically
        //             set the scale factor here based on the specified dynamic range.
        final double scaleFactor = MAX_ANGULAR_VELOCITY_DEG_PER_S / 23767.0;
        float x = (float) (xInt * scaleFactor);
        float y = (float) (yInt * scaleFactor);
        float z = (float) (zInt * scaleFactor);
        return new AngularVelocity(AngleUnit.DEGREES, x, y, z, timestamp);
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.Lynx;
    }

    @Override
    public String getDeviceName() {
        return AppUtil.getDefContext().getString(R.string.lynx_embedded_bhi260ap_imu_name);
    }

    @Override
    public String getConnectionInfo() {
        return String.format("BHI260 IMU on %s", deviceClient.getConnectionInfo());
    }

    @SuppressWarnings("unused")
    public int getFirmwareVersion() {
        return fwVersion;
    }

    private static int read8(I2cDeviceSynchSimple deviceClient, Register register) {
        synchronized (i2cLock) {
            return TypeConversion.unsignedByteToInt(deviceClient.read8(register.address));
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static int read16(I2cDeviceSynchSimple deviceClient, Register register) {
        synchronized (i2cLock) {
            return TypeConversion.byteArrayToShort(deviceClient.read(register.address, 2), ByteOrder.LITTLE_ENDIAN);
        }
    }

    private static ByteBuffer readMultiple(I2cDeviceSynchSimple deviceClient, Register register, int numBytes) {
        synchronized (i2cLock) {
            return ByteBuffer.wrap(deviceClient.read(register.address, numBytes)).order(ByteOrder.LITTLE_ENDIAN);
        }
    }

    private static ByteBuffer readMultiple(I2cDeviceSynchSimple deviceClient, int numBytes) {
        synchronized (i2cLock) {
            return ByteBuffer.wrap(deviceClient.read(numBytes)).order(ByteOrder.LITTLE_ENDIAN);
        }
    }

    private static <T extends Enum<T>> EnumSet<T> read8Flags(I2cDeviceSynchSimple deviceClient, Register register, Class<T> enumClass) {
        synchronized (i2cLock) {
            return convertIntToEnumSet(read8(deviceClient, register), enumClass);
        }
    }

    private static EnumSet<BootStatusFlag> readBootStatusFlags(I2cDeviceSynchSimple deviceClient) {
        synchronized (i2cLock) {
            return read8Flags(deviceClient, Register.BOOT_STATUS, BootStatusFlag.class);
        }
    }

    private static void write8(I2cDeviceSynchSimple deviceClient, Register register, int data, I2cWaitControl waitControl) {
        synchronized (i2cLock) {
            deviceClient.write8(register.address, data, waitControl);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Enum<T>> void write8Flags(I2cDeviceSynchSimple deviceClient, Register register, EnumSet<T> flags, I2cWaitControl waitControl) {
        synchronized (i2cLock) {
            write8(deviceClient, register, convertEnumSetToInt(flags), waitControl);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void writeMultiple(I2cDeviceSynchSimple deviceClient, Register register, byte[] data) {
        synchronized (i2cLock) {
            deviceClient.write(register.address, data);
        }
    }

    @SuppressWarnings("BusyWait")
    private static void waitForHostInterface(I2cDeviceSynchSimple deviceClient) throws InitException {
        ElapsedTime timeoutTimer = new ElapsedTime();
        EnumSet<BootStatusFlag> bootStatusFlags = readBootStatusFlags(deviceClient);

        try {
            while (!bootStatusFlags.contains(BootStatusFlag.HOST_INTERFACE_READY) && timeoutTimer.milliseconds() < BOOT_FROM_FLASH_TIMEOUT_MS) {
                Thread.sleep(10);
                bootStatusFlags = readBootStatusFlags(deviceClient);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InitException();
        }

        if (!bootStatusFlags.contains(BootStatusFlag.HOST_INTERFACE_READY)) {
            RobotLog.ee(TAG, "Timeout expired while waiting for IMU host interface to become ready. bootStatusFlags=%s", bootStatusFlags);
            throw new InitException();
        }
    }

    private static void checkForFlashPresence(I2cDeviceSynchSimple deviceClient) throws InitException {
        EnumSet<BootStatusFlag> bootStatusFlags = readBootStatusFlags(deviceClient);
        if (!bootStatusFlags.contains(BootStatusFlag.FLASH_DETECTED) || bootStatusFlags.contains(BootStatusFlag.NO_FLASH)) {
            RobotLog.ee(TAG, "IMU did not detect flash chip");
            throw new InitException();
        }
    }

    /**
     * @return true if the flash chip contains valid firmware
     */
    @SuppressWarnings("BusyWait")
    private static boolean waitForFlashVerification(I2cDeviceSynchSimple deviceClient) throws InitException {
        ElapsedTime timeoutTimer = new ElapsedTime();
        EnumSet<BootStatusFlag> bootStatusFlags = readBootStatusFlags(deviceClient);
        AppAliveNotifier.getInstance().notifyAppAlive(); // Make sure we don't trip the CH OS watchdog

        try {
            // Wait for flash verification to either complete or error out
            while (!bootStatusFlags.contains(BootStatusFlag.FLASH_VERIFY_DONE) && timeoutTimer.milliseconds() < 1500) {
                if (bootStatusFlags.contains(BootStatusFlag.FLASH_VERIFY_ERROR)) {
                    RobotLog.ee(TAG, "Error verifying IMU firmware");
                    return false;
                }
                Thread.sleep(10);
                bootStatusFlags = readBootStatusFlags(deviceClient);
            }

            if (!bootStatusFlags.contains(BootStatusFlag.FLASH_VERIFY_DONE)) {
                RobotLog.ww(TAG, "Timeout expired while waiting for IMU to load its firmware from flash");
                return false;
            } else if (bootStatusFlags.contains(BootStatusFlag.FLASH_VERIFY_ERROR)) {
                RobotLog.ee(TAG, "Error verifying IMU firmware");
                return false;
            }
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InitException();
        }
    }

    /** Disable full and delta timestamps to reduce needed I2C traffic for reading async FIFO data */
    private static void disableFifoTimestamps(I2cDeviceSynchSimple deviceClient) {
        sendCommand(deviceClient, CommandType.CONTROL_FIFO_FORMAT, new byte[]{ 0b11 });
    }

    private static void sendCommand(I2cDeviceSynchSimple deviceClient, CommandType commandType, @Nullable byte[] payload) {
        if (payload == null) { payload = new byte[0]; }
        int totalLength = COMMAND_HEADER_LENGTH + payload.length;
        int numPaddingBytes = 0;

        // Make sure that we send a multiple of 4 bytes
        if (totalLength % 4 != 0) {
            numPaddingBytes = 4 - (payload.length % 4);
            totalLength += numPaddingBytes;
        }

        if (totalLength > MAX_SEND_I2C_BYTES_WITH_REGISTER) {
            throw new IllegalArgumentException("sendCommand() called with too large of a payload. Update sendCommand() to break into multiple I2C writes");
        }

        ByteBuffer buffer = ByteBuffer.allocate(totalLength)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putShort((short) commandType.id)
                .putShort((short) (totalLength - COMMAND_HEADER_LENGTH))
                .put(payload);
        if (numPaddingBytes > 0) {
            buffer.put(new byte[numPaddingBytes]);
        }
        writeMultiple(deviceClient, Register.COMMAND_INPUT, buffer.array());
    }

    @SuppressWarnings("SameParameterValue")
    private static StatusPacket sendCommandAndWaitForResponse(
            I2cDeviceSynchSimple deviceClient,
            CommandType commandType,
            @Nullable byte[] payload,
            int responseTimeoutMs) throws CommandFailureException {
        statusAndDebugFifoModeManager.lockStatusAndDebugFifoMode(deviceClient, StatusAndDebugFifoMode.SYNCHRONOUS);
        try {
            sendCommand(deviceClient, commandType, payload);
            return waitForCommandResponse(deviceClient, commandType, responseTimeoutMs);
        } finally {
            statusAndDebugFifoModeManager.unlockStatusAndDebugFifoMode();
        }
    }

    /**
     * Also handles reporting flash progress and keeping the Control Hub OS watchdog fed
     */
    private static void sendWriteFlashCommandAndWaitForResponse(
            I2cDeviceSynchSimple deviceClient,
            int startAddress,
            int numFwBytesInCommand,
            ByteBuffer bytesSource) throws CommandFailureException {

        if (numFwBytesInCommand > MAX_WRITE_FLASH_FIRMWARE_BYTES) {
            throw new IllegalArgumentException("Tried to write too many bytes in a single Write Flash command");
        }

        AppAliveNotifier.getInstance().notifyAppAlive(); // Make sure we don't trip the CH OS watchdog

        // totalLengthOfCommand also needs to account for padding, if needed. We'll update it next.
        int totalLengthOfCommand = WRITE_FLASH_COMMAND_HEADER_LENGTH + numFwBytesInCommand;
        int numPaddingBytes = 0;

        // Make sure that we send a multiple of 4 bytes (required by the datasheet)
        if (totalLengthOfCommand % 4 != 0) {
            numPaddingBytes = 4 - (numFwBytesInCommand % 4);
            totalLengthOfCommand += numPaddingBytes;
        }
        int numFwPlusPaddingBytes = numFwBytesInCommand + numPaddingBytes;

        if (DEBUG_FW_FLASHING) { RobotLog.dd(TAG, "totalLengthOfCommand=%d numFwPlusPaddingBytes=%d numFwBytesInCommand=%d", totalLengthOfCommand, numFwPlusPaddingBytes, numFwBytesInCommand); }

        ByteBuffer buffer = ByteBuffer.allocate(totalLengthOfCommand)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putShort((short) CommandType.WRITE_FLASH.id);

        // While there is a WRITE_FLASH_COMMAND_HEADER_LENGTH constant, that is only used so that
        // we don't have to separately keep remembering to subtract out the starting address bytes,
        // in addition to the header. As far as the BHI260 is concerned, the Write Flash command has
        // the same size header as every other command.
        short writeFlashCommandLength = (short) (totalLengthOfCommand - COMMAND_HEADER_LENGTH);
        if (DEBUG_FW_FLASHING) { RobotLog.dd(TAG, "Write Flash command length: %d", TypeConversion.unsignedShortToInt(writeFlashCommandLength)); }
        buffer.putShort(writeFlashCommandLength);

        buffer.putInt(startAddress);
        int bufferPositionFirmwareBytes = buffer.position();

        if (DEBUG_FW_FLASHING) { RobotLog.dd(TAG, "Copying %d bytes from byte source (%d bytes remaining)", numFwBytesInCommand, bytesSource.remaining() - numFwBytesInCommand); }
        bytesSource.get(buffer.array(), bufferPositionFirmwareBytes, numFwBytesInCommand);

        buffer.position(WRITE_FLASH_COMMAND_HEADER_LENGTH + numFwBytesInCommand);

        if (numPaddingBytes > 0) {
            buffer.put(new byte[numPaddingBytes]);
        }

        if (DEBUG_FW_FLASHING) { printByteBuffer("Write Flash command", buffer); }

        statusAndDebugFifoModeManager.lockStatusAndDebugFifoMode(deviceClient, StatusAndDebugFifoMode.SYNCHRONOUS);
        try {
            writeMultiple(deviceClient, Register.COMMAND_INPUT, buffer.array());
            waitForCommandResponse(deviceClient, CommandType.WRITE_FLASH, WRITE_FLASH_RESPONSE_TIMEOUT_MS);
        } finally {
            statusAndDebugFifoModeManager.unlockStatusAndDebugFifoMode();
        }
    }

    /**
     * Because of the Expansion Hub firmware bug that causes an extra I2C byte to be read (without
     * telling us what that byte was), receiving the response can only work correctly when the
     * response payload has 100 bytes or less. We accomplish it working at all by ignoring the
     * second payload length byte when we read the header, so that we lose that instead of the first
     * payload byte.
     * <p>
     * Before sending the command that we wait for a response for, call
     * statusAndDebugFifoModeManager.lockStatusAndDebugFifoMode(StatusAndDebugFifoMode.SYNCHRONOUS)
     */
    private static StatusPacket waitForCommandResponse(I2cDeviceSynchSimple deviceClient, CommandType commandType, int timeoutMs) throws CommandFailureException {
        ElapsedTime timeoutTimer = new ElapsedTime();
        ElapsedTime notifyAppAliveTimer = new ElapsedTime();

        // Make sure we don't trip the CH OS watchdog
        AppAliveNotifier.getInstance().notifyAppAlive();

        // Loop until we the interrupt status register indicates that there is Status data available.
        // Ideally we'd use the interrupt pin, but the only place we currently wait for a command
        // response is when flashing the firmware, and it's not possible to use the interrupt pin
        // when the IMU is in bootloader mode.
        while (true) {
            // After 10 seconds, the Control Hub OS watchdog will trip
            if (notifyAppAliveTimer.seconds() > 8) {
                AppAliveNotifier.getInstance().notifyAppAlive();
                notifyAppAliveTimer.reset();
            }

            if (timeoutTimer.milliseconds() >= timeoutMs) {
                throw new CommandFailureException(String.format(Locale.ENGLISH, "%dms timeout expired while waiting for response", timeoutMs));
            }

            // TODO(Noah): Use the IMU interrupt pin instead of the interrupt register when possible
            //             (not during firmware flashing)
            EnumSet<InterruptStatusFlag> interruptStatusFlags = read8Flags(deviceClient, Register.INTERRUPT_STATUS, InterruptStatusFlag.class);

            // When we send an erase flash command, the reset flag has not yet been cleared.
            if (interruptStatusFlags.contains(InterruptStatusFlag.RESET_OR_FAULT) && commandType != CommandType.ERASE_FLASH) {
                RobotLog.ww(TAG, "Reset or Fault interrupt status was set while waiting for %s response", commandType);
                RobotLog.ww(TAG, "Interrupt status: %s", interruptStatusFlags);
                RobotLog.ww(TAG, "Error value: 0x%X", read8(deviceClient, Register.ERROR_VALUE));
            }

            if (interruptStatusFlags.contains(InterruptStatusFlag.STATUS_STATUS)) {
                break;
            }
        }

        // Loop until we successfully receive a response. If the IMU responds with a status code of
        // 0, we try again.
        while (true) {
            // After 10 seconds, the Control Hub OS watchdog will trip
            if (notifyAppAliveTimer.seconds() > 8) {
                AppAliveNotifier.getInstance().notifyAppAlive();
                notifyAppAliveTimer.reset();
            }

            if (timeoutTimer.milliseconds() >= timeoutMs) {
                throw new CommandFailureException(String.format(Locale.ENGLISH, "%dms timeout expired while waiting for response", timeoutMs));
            }

            // As per section 14.2.1 of the datasheet, the standard FIFO format does not apply to
            // the Status and Debug FIFO in synchronous mode (which we use when sending a command
            // that we expect a response to). This means that the first two bytes will be the status
            // code, not the amount of data in the FIFO.

            // Read the first 3 bytes of the 4-byte header (reading the 4th would corrupt the
            // payload because of the Expansion Hub FW bug)

            // Bytes 0 and 1 tell us the command response's status code
            // Byte 3 tells us the length of the command response's payload
            final int responseStatusCode;
            final byte[] responsePayload;
            synchronized (i2cLock) {
                ByteBuffer responseHeader = readMultiple(deviceClient, Register.STATUS_AND_DEBUG_FIFO_OUTPUT, 3);
                responseStatusCode = TypeConversion.unsignedShortToInt(responseHeader.getShort());
                final int responsePayloadLength = TypeConversion.unsignedByteToInt(responseHeader.get());

                if (responsePayloadLength > MAX_READ_I2C_BYTES) {
                    throw new RuntimeException(String.format(Locale.ENGLISH, "IMU sent payload that was too long (%d bytes)", responsePayloadLength));
                }

                responsePayload = responsePayloadLength == 0 ? new byte[0] : readMultiple(deviceClient, responsePayloadLength).array();
            }

            if (responseStatusCode != commandType.successStatusCode) {
                if (responseStatusCode == 0) {
                    RobotLog.ww(TAG, "Received status code 0, trying again");
                    continue; // Try again
                }

                String errorMessage = null;
                String erroredCommandDesc;

                if (responseStatusCode == COMMAND_ERROR_RESPONSE) {
                    CommandError commandError = null;
                    int commandErrorCode = -1;
                    int erroredCommandId = -1;

                    if (responsePayload.length >= 3) {
                        ByteBuffer errorPayload = ByteBuffer.wrap(responsePayload).order(ByteOrder.LITTLE_ENDIAN);
                        erroredCommandId = TypeConversion.unsignedShortToInt(errorPayload.getShort());
                        commandErrorCode = TypeConversion.unsignedByteToInt(errorPayload.get());
                        commandError = CommandError.fromInt(commandErrorCode);
                    }

                    if (erroredCommandId == commandType.id) {
                        erroredCommandDesc = commandType + " command";
                    } else {
                        // The command ID that this error is a response to does not match the command we just sent
                        CommandType erroredCommandType = CommandType.findById(erroredCommandId);

                        if (erroredCommandType == null) {
                            erroredCommandDesc = String.format(Locale.US, "unknown command 0x%4X (just sent %s command)", erroredCommandId, commandType);
                        } else {
                            erroredCommandDesc = String.format(Locale.US, "%s command (just sent %s command)", erroredCommandType, commandType);
                        }
                    }

                    if (commandError == null) {
                        errorMessage = String.format(Locale.US, "Received unknown Command Error code 0x%2X in response to %s", commandErrorCode, erroredCommandDesc);
                    } else {
                        errorMessage = String.format(Locale.US, "Received Command Error %s in response to %s", commandError, erroredCommandDesc);
                    }
                }

                // TODO(Noah): Add good support for receiving a non-error, non-matching response

                if (errorMessage == null) {
                    errorMessage = String.format(Locale.US, "Received unexpected response status 0x%X for %s command", responseStatusCode, commandType);
                }

                throw new CommandFailureException(errorMessage);
            }
            return new StatusPacket(responseStatusCode, responsePayload);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void configureSensor(Sensor sensor, float sampleRateHz, int latencyMs) {
        if (latencyMs > 1_6777_215) {
            // The latency is larger than can fit into 3 bytes (unsigned)
            throw new IllegalArgumentException("Sensor latency must be less than 1,6777,215 milliseconds");
        }

        ByteBuffer buffer = ByteBuffer.allocate(8)
                .order(ByteOrder.LITTLE_ENDIAN)
                .put((byte) sensor.id)
                .putFloat(sampleRateHz); // putFloat() adds 4 bytes
        byte[] latency4Bytes = TypeConversion.intToByteArray(latencyMs, ByteOrder.LITTLE_ENDIAN);
        buffer.put(latency4Bytes, 0, 3); // Add the first 3 bytes of the latency integer
        sendCommand(deviceClient, CommandType.CONFIGURE_SENSOR, buffer.array());
        // TODO(Noah): Check that the sensor is correctly configured
        // TODO(Noah): Check the Error Value register
    }

    /**
     * @param sensor The Virtual Sensor whose physical sensor we are changing the Dynamic Range of
     * @param maxSensorValue The maximum value that will be able to be represented by the sensor. In
     *                       the datasheet, this is referred to as the Dynamic Range. Smaller values
     *                       will reduce the needed scale factor and increase precision, at the cost
     *                       of the sensor not being able to express as large of a value.
     */
    @SuppressWarnings("SameParameterValue")
    private void setSensorDynamicRange(Sensor sensor, int maxSensorValue) {
        if (maxSensorValue > 65_535) {
            // The dynamic range is larger than can fit into 2 bytes (unsigned)
            throw new IllegalArgumentException("Sensor dynamic range must be less than 65,535");
        }
        ByteBuffer buffer = ByteBuffer.allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .put((byte) sensor.id)
                .putShort((short) maxSensorValue)
                .put((byte) 0);
        sendCommand(deviceClient, CommandType.CHANGE_SENSOR_DYNAMIC_RANGE, buffer.array());
        // TODO(Noah): Wait for the Dynamic Range Changed meta event
    }

    private static <T extends Enum<T>> EnumSet<T> convertIntToEnumSet(int value, Class<T> enumClass) {
        EnumSet<T> flags = EnumSet.noneOf(enumClass);
        for (T flag: enumClass.getEnumConstants()) {
            if (((1 << flag.ordinal()) & value) == (1 << flag.ordinal())) {
                flags.add(flag);
            }
        }
        return flags;
    }

    private static <T extends Enum<T>> int convertEnumSetToInt(EnumSet<T> enumSet) {
        int result = 0;
        for (T flag: enumSet) {
            result |= 1 << flag.ordinal();
        }
        return result;
    }

    @SuppressWarnings("SameParameterValue")
    private static void printByteBuffer(String tag, ByteBuffer buffer, boolean printFromBeginning) {
        int initialPosition = buffer.position();
        if (printFromBeginning) {
            buffer.position(0);
        }
        int length = buffer.remaining();

        String contentsString;
        if (length > 0) {
            StringBuilder stringBuilder = new StringBuilder(String.format("%X", buffer.get()));
            while (buffer.hasRemaining()) {
                stringBuilder.append(String.format("-%X", buffer.get()));
            }
            contentsString = stringBuilder.toString();
        } else {
            contentsString = "[]";
        }

        RobotLog.dd(TAG, "%s (%d bytes): %s", tag, length, contentsString);
        buffer.position(initialPosition);
    }

    private static void printByteBuffer(String tag, ByteBuffer buffer) {
        printByteBuffer(tag, buffer, true);
    }

    private static void readFifo(I2cDeviceSynchSimple deviceClient, Fifo fifo) {
        ByteBuffer fifoContents;
        // We synchronize on fifoLock here because we need to be sure another thread doesn't
        synchronized (i2cLock) {
            // Read the FIFO descriptor (because of a Control Hub firmware bug, 4 bytes will be read
            // even though we only requested 3, but we'll only have access to the first 3)
            ByteBuffer fifoDescriptor = readMultiple(deviceClient, fifo.register, 3);
            int fifoTransferLength = TypeConversion.unsignedShortToInt(fifoDescriptor.getShort());

            if (fifoTransferLength == 0) {
                RobotLog.ww(TAG, "Attempted to read empty %s FIFO", fifo);
                return;
            }

            int remainingBytes = fifoTransferLength - 4;
            if (remainingBytes <= MAX_READ_I2C_BYTES) {
                // If we request the full number of bytes, the firmware will read an extra byte
                // because of the bug, which will cause the IMU to set the Error Value register to
                // 0x77 (Host Download Channel Empty). So, we read one fewer byte, and make sure to
                // catch the resulting BufferUnderflowExceptions and deal with them reasonably.
                fifoContents = readMultiple(deviceClient, remainingBytes - 1);
            } else {
                fifoContents = readMultiple(deviceClient, MAX_READ_I2C_BYTES);
                RobotLog.ww(TAG, "FIFO was too large. Aborting transfer and discarding data.");

                HostInterfaceControlFlag abortFlag;
                byte discardFlushValue;
                switch (fifo) {
                    case WAKE_UP:
                        abortFlag = HostInterfaceControlFlag.ABORT_TRANSFER_CHANNEL_1;
                        discardFlushValue = (byte) 0xFB;
                        break;
                    case NON_WAKE_UP:
                        abortFlag = HostInterfaceControlFlag.ABORT_TRANSFER_CHANNEL_2;
                        discardFlushValue = (byte) 0xFA;
                        break;
                    case STATUS_AND_DEBUG:
                    default:
                        abortFlag = HostInterfaceControlFlag.ABORT_TRANSFER_CHANNEL_3;
                        discardFlushValue = (byte) 0xF9;
                        break;
                }

                EnumSet<HostInterfaceControlFlag> hostInterfaceControlFlags = read8Flags(deviceClient, Register.HOST_INTERFACE_CONTROL, HostInterfaceControlFlag.class);
                hostInterfaceControlFlags.add(abortFlag);
                write8Flags(deviceClient, Register.HOST_INTERFACE_CONTROL, hostInterfaceControlFlags, I2cWaitControl.ATOMIC);

                byte[] flushPayload = new byte[4];
                flushPayload[0] = discardFlushValue;

                sendCommand(deviceClient, CommandType.FIFO_FLUSH, flushPayload);
            }
        }
        while (fifoContents.hasRemaining()) {
            int eventId = TypeConversion.unsignedByteToInt(fifoContents.get());

            // Event IDs 224 and above indicate system events, and ID 0 indicates the Padding event.
            if (eventId != 0 && eventId < 224) {
                // This shouldn't happen because we haven't configured any sensors that put data in a FIFO
                RobotLog.ww(TAG, "%s FIFO contained sensor data", fifo);

                int sensorPayloadLength = -1;

                // TODO(Noah): Define payload lengths for various virtual sensors
                //noinspection ConstantConditions
                if (sensorPayloadLength > 0) {
                    try {
                        byte[] tmp = new byte[sensorPayloadLength];
                        fifoContents.get(tmp);
                    } catch (BufferUnderflowException e) {
                        byte[] tmp = new byte[sensorPayloadLength - 1];
                        fifoContents.get(tmp);
                    }
                    continue;
                }
            }

            NonSensorEventType eventType = NonSensorEventType.findById(eventId);
            if (eventType == null) {
                RobotLog.ee(TAG, "%s FIFO contained unknown event ID %d. Unable to process the rest of the FIFO's contents", fifo, eventId);
                return;
            }

            try {

                switch (eventType) {
                    case DEBUG_DATA:
                        printDebugData(fifoContents);
                        break;
                    case TIMESTAMP_SMALL_DELTA:
                        // Throw away the 1-byte payload
                        fifoContents.get();
                        break;
                    case TIMESTAMP_LARGE_DELTA:
                        // Throw away the 2-byte payload
                        fifoContents.getShort();
                        break;
                    case TIMESTAMP_FULL:
                        // Throw away the 5-byte payload
                        byte[] temp = new byte[5];
                        fifoContents.get(temp);
                        break;
                    case META_EVENT:
                        parseMetaEvent(fifoContents, fifo);
                        break;
                    case FILLER:
                        // ignore
                        break;
                    case PADDING:
                        // There will be no further data in the FIFO
                        return;
                    default:
                        RobotLog.ee(TAG, "No handler is defined for event type %s", eventType);
                        return;
                }
            } catch (BufferUnderflowException e) {
                // We had to request one fewer byte than we're supposed to because of the Expansion
                // Hub firmware bug. This means that there will be a BufferUnderflowException for
                // the final event in the buffer.

                // It's best if that exception is caught closer to where it occurred, but for safety
                // we make sure to catch at this level as well.
                RobotLog.ee(TAG, e, "BufferUnderflowException was caught at the top level of readFifo(). " +
                        "It should be caught closer to where it was thrown so that it can be handled more intelligently.");
                return;
            }
        }
    }

    private static void startDiagnosticMode() {
        // TODO: Configure meta events and such

        if (diagnosticModeThread == null) {
            diagnosticModeThread = new Thread(new Runnable() {
                private I2cDeviceSynchSimple deviceClient;

                @Override
                public void run() {
                    RobotLog.vv(TAG, "Diagnostic mode thread started");

                    boolean fifoTimestampsDisabled = false;
                    int errorValue;
                    int previousErrorValue = 0;
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            //noinspection BusyWait
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            return;
                        }

                        if (!refreshDeviceClient()) { continue; }

                        if (!fifoTimestampsDisabled) {
                            disableFifoTimestamps(deviceClient);
                            fifoTimestampsDisabled = true;
                        }

                        errorValue = read8(deviceClient, Register.ERROR_VALUE);
                        if (errorValue != previousErrorValue) {
                            if (errorValue == 0) {
                                RobotLog.vv(TAG, "Error cleared");
                            } else {
                                RobotLog.ww(TAG, "Error value: 0x%X", errorValue);
                            }
                        }
                        previousErrorValue = errorValue;

                        if (DIAGNOSTIC_MODE_FIFO_PARSING) {
                            statusAndDebugFifoModeManager.lockStatusAndDebugFifoMode(deviceClient, StatusAndDebugFifoMode.ASYNCHRONOUS);
                            try {
                                // We have to check the interrupt status while the status and debug FIFO
                                // is in ASYNC mode, or else the Debug Status won't be set.
                                final EnumSet<InterruptStatusFlag> interruptStatusFlags = read8Flags(deviceClient, Register.INTERRUPT_STATUS, InterruptStatusFlag.class);
                                if (interruptStatusFlags.contains(InterruptStatusFlag.STATUS_STATUS)) {
                                    RobotLog.ee(TAG, "Interrupt status Status was set in asynchronous mode");
                                }
                                if (interruptStatusFlags.size() > 0) {
                                    RobotLog.vv(TAG, "Interrupt status flags: %s", interruptStatusFlags);
                                }

                                if (interruptStatusFlags.contains(InterruptStatusFlag.DEBUG_STATUS)
                                        || interruptStatusFlags.contains(InterruptStatusFlag.RESET_OR_FAULT)) {
                                    readFifo(deviceClient, Fifo.STATUS_AND_DEBUG);
                                }

                                if (interruptStatusFlags.contains(InterruptStatusFlag.WAKE_UP_FIFO_STATUS_1)
                                        || interruptStatusFlags.contains(InterruptStatusFlag.WAKE_UP_FIFO_STATUS_2)) {
                                    readFifo(deviceClient, Fifo.WAKE_UP);
                                }

                                if (interruptStatusFlags.contains(InterruptStatusFlag.NON_WAKE_UP_FIFO_STATUS_1)
                                        || interruptStatusFlags.contains(InterruptStatusFlag.NON_WAKE_UP_FIFO_STATUS_2)) {
                                    readFifo(deviceClient, Fifo.NON_WAKE_UP);
                                }
                            } finally {
                                statusAndDebugFifoModeManager.unlockStatusAndDebugFifoMode();
                            }
                        }
                    }
                }

                // Returns true if deviceClient is valid
                private boolean refreshDeviceClient() {
                    if (deviceClient == null || deviceClient.getHealthStatus() == HardwareDeviceHealth.HealthStatus.CLOSED) {
                        LynxModule embeddedControlHubModule = (LynxModule) EmbeddedControlHubModule.get();
                        if (embeddedControlHubModule == null || !embeddedControlHubModule.isOpen()) {
                            deviceClient = null;
                            return false;
                        } else {
                            deviceClient = LynxFirmwareVersionManager.createLynxI2cDeviceSynch(AppUtil.getDefContext(), embeddedControlHubModule, 0);
                            deviceClient.setI2cAddress(I2C_ADDR);
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            });
            diagnosticModeThread.setName("Diagnostic mode thread");
            diagnosticModeThread.start();
        }
    }

    private static void printDebugData(ByteBuffer fifoContents) {
        final int sensorId = TypeConversion.unsignedByteToInt(fifoContents.get());
        final int flags = TypeConversion.unsignedByteToInt(fifoContents.get());
        final int validLength = flags & 0x3F;
        final boolean isBinary = (flags & 0x40) > 0;

        // It's important for us to read all 16 bytes from the buffer (even the invalid ones) so
        // that the buffer is in the correct place when parsing continues in another function.
        final byte[] data = new byte[16];
        fifoContents.get(data);

        final byte[] validData = Arrays.copyOfRange(data, 0, validLength);

        String tag = String.format(Locale.ENGLISH, "Debug data (sensor %d)", sensorId);
        if (isBinary) { printByteBuffer(tag, ByteBuffer.wrap(validData)); }
        else { RobotLog.dd(TAG, "%s: %s", tag, new String(validData, StandardCharsets.UTF_8)); }
    }

    private static void parseMetaEvent(ByteBuffer fifoContents, Fifo fifo) {
        int metaEventType = TypeConversion.unsignedByteToInt(fifoContents.get());

        byte[] payload = new byte[2];

        try {
            fifoContents.get(payload);
        } catch (BufferUnderflowException e) {
            // This is safe because we are only one byte short.
            byte firstPayloadByte = fifoContents.get();
            payload = new byte[] { firstPayloadByte, -1 };
        }

        int firstByteUnsigned = TypeConversion.unsignedByteToInt(payload[0]);
        int secondByteUnsigned = TypeConversion.unsignedByteToInt(payload[1]);

        switch (metaEventType) {
            case 1:
                RobotLog.vv(TAG, "Sensor %d flush complete", firstByteUnsigned);
                break;
            case 2:
                // The second byte does contain the new sample rate, but it maxes out at just 255,
                // which is not useful for us.
                RobotLog.vv(TAG, "Sensor %d sample rate changed", firstByteUnsigned);
                break;
            case 3:
                RobotLog.vv(TAG, "Sensor %d power mode is now %d", firstByteUnsigned, secondByteUnsigned);
                break;
            case 4:
                RobotLog.vv(TAG, "IMU system error. errorCode=0x%X interruptStatus=%s", firstByteUnsigned, convertIntToEnumSet(secondByteUnsigned, InterruptStatusFlag.class));
                break;
            case 6:
                String accuracy = "unknown";
                if (secondByteUnsigned == 0) { accuracy = "unreliable"; }
                else if (secondByteUnsigned == 1) { accuracy = "low"; }
                else if (secondByteUnsigned == 2) { accuracy = "medium"; }
                else if (secondByteUnsigned == 3) { accuracy = "high"; }
                RobotLog.vv(TAG, "Sensor %d accuracy is now \"%s\"", firstByteUnsigned, accuracy);
                break;
            case 11:
                RobotLog.vv(TAG, "Sensor %d experienced an error. errorCode=0x%X", firstByteUnsigned, secondByteUnsigned);
                break;
            case 12:
                RobotLog.vv(TAG, "FIFO %s overflowed", fifo);
                // Throw away the 5-byte Full Timestamp
                byte[] temp = new byte[5];
                fifoContents.get(temp);
                break;
            case 13:
                RobotLog.vv(TAG, "Sensor %d dynamic range changed", firstByteUnsigned);
                break;
            case 14:
                RobotLog.ww(TAG, "FIFO %s has reached its watermark level", fifo);
                break;
            case 16:
                RobotLog.vv(TAG, "Firmware has finished initializing");
                break;
            case 17:
                RobotLog.ww(TAG, "Unexpectedly received a Transfer Cause event for sensor %d", firstByteUnsigned);
                break;
            case 18:
                String error = "unknown";
                if (secondByteUnsigned == 1) { error = "Trigger was delayed"; }
                else if (secondByteUnsigned == 2) { error = "Trigger was dropped"; }
                else if (secondByteUnsigned == 3) { error = "Hang detection disabled"; }
                else if (secondByteUnsigned == 4) { error = "Parent is not enabled"; }
                RobotLog.ww(TAG, "Software Framework error for sensor %d: %s", firstByteUnsigned, error);
                break;
            case 19:
                String cause = "unknown";
                boolean warn = false;
                if (secondByteUnsigned == 0) { cause = "Power-on reset"; }
                else if (secondByteUnsigned == 1) { cause = "External reset"; }
                else if (secondByteUnsigned == 2) { cause = "Host commanded reset"; }
                else if (secondByteUnsigned == 4) { cause = "Watchdog reset"; warn = true; }
                String log = "IMU was reset: %s";
                if (warn) {
                    RobotLog.ww(TAG, log, cause);
                } else {
                    RobotLog.vv(TAG, log, cause);
                }
                break;
            case 20:
                // Spacer event; ignore
                break;
            default:
                RobotLog.ww(TAG, "Received unknown meta event type %d", metaEventType);
                break;
        }
    }

    private enum Register {
        COMMAND_INPUT(0x00),
        WAKE_UP_FIFO_OUTPUT(0x01),
        NON_WAKE_UP_FIFO_OUTPUT(0x02),
        STATUS_AND_DEBUG_FIFO_OUTPUT(0x03),
        CHIP_CONTROL(0x05),
        HOST_INTERFACE_CONTROL(0x06),
        HOST_INTERRUPT_CONTROL(0x07),
        RESET_REQUEST(0x14),
        TIMESTAMP_EVENT_REQUEST(0x15),
        HOST_CONTROL(0x16),
        HOST_STATUS(0x17),
        PRODUCT_IDENTIFIER(0x1C),
        REVISION_IDENTIFIER(0x1D),
        ROM_VERSION(0x1E),
        KERNEL_VERSION(0x20),
        USER_VERSION(0x22),
        FEATURE_STATUS(0x24),
        BOOT_STATUS(0x25),
        CHIP_ID(0x2B),
        INTERRUPT_STATUS(0x2D),
        ERROR_VALUE(0x2E),
        GEN_PURPOSE_READ(0x32);

        private final int address;

        Register(int address) {
            this.address = address;
        }
    }

    private enum Fifo {
        WAKE_UP(Register.WAKE_UP_FIFO_OUTPUT),
        NON_WAKE_UP(Register.NON_WAKE_UP_FIFO_OUTPUT),
        STATUS_AND_DEBUG(Register.STATUS_AND_DEBUG_FIFO_OUTPUT);

        private final Register register;
        Fifo(Register register) { this.register = register; }
    }

    private enum StatusAndDebugFifoMode { SYNCHRONOUS, ASYNCHRONOUS }

    private enum NonSensorEventType {
        DEBUG_DATA(250, -1),
        TIMESTAMP_SMALL_DELTA(251, 245),
        TIMESTAMP_LARGE_DELTA(252, 246),
        TIMESTAMP_FULL(253, 247),
        META_EVENT(254, 248),
        FILLER(255, 255),
        PADDING(0, 0);

        final int nonWakeUpId;
        final int wakeUpId;
        NonSensorEventType(int nonWakeUpId, int wakeUpId) {
            this.wakeUpId = wakeUpId;
            this.nonWakeUpId = nonWakeUpId;
        }

        public static @Nullable NonSensorEventType findById(int id) {
            for (NonSensorEventType event: NonSensorEventType.values()) {
                if (event.nonWakeUpId == id || event.wakeUpId == id) { return event; }
            }
            return null;
        }
    }

    private enum CommandType {
        ERASE_FLASH(0x0004, 0x000A),
        WRITE_FLASH(0x0005, 0x000B),
        BOOT_FLASH(0x0006, 0),
        FIFO_FLUSH(0x0009, 0),
        CONFIGURE_SENSOR(0x000D, 0),
        CHANGE_SENSOR_DYNAMIC_RANGE(0x000E, 0),
        CONTROL_FIFO_FORMAT(0x0015, 0);

        private final int id;
        private final int successStatusCode; // 0 indicates no response expected

        CommandType(int id, int successStatusCode) {
            this.id = id;
            this.successStatusCode = successStatusCode;
        }

        public static @Nullable CommandType findById(int id) {
            for (CommandType commandType: CommandType.values()) {
                if (commandType.id == id) { return commandType; }
            }
            return null;
        }
    }

    private static final int COMMAND_ERROR_RESPONSE = 0x0F;
    private enum CommandError {
        INCORRECT_LENGTH(0x01),
        TOO_LONG(0x02),
        PARAM_WRITE_ERROR(0x03),
        PARAM_READ_ERROR(0x04),
        INVALID_COMMAND(0x05),
        INVALID_PARAM(0x06),
        COMMAND_FAILED(0xFF);

        private final int value;
        CommandError(int value) {
            this.value = value;
        }

        public static @Nullable CommandError fromInt(int intValue) {
            for (CommandError value: values()) {
                if (intValue == value.value) { return value; }
            }
            return null;
        }
    }

    private enum Sensor {
        GAME_ROTATION_VECTOR_WAKE_UP(38),
        GAME_ROTATION_VECTOR_DATA_HOLDER(176),
        GAME_ROTATION_VECTOR_GPIO_HANDLER(177),
        GYROSCOPE_CORRECTED_DATA_HOLDER(178),
        GYROSCOPE_CORRECTED_GPIO_HANDLER(179);
        private final int id;

        Sensor(int id) {
            this.id = id;
        }
    }

    private enum BootStatusFlag {
        FLASH_DETECTED,
        FLASH_VERIFY_DONE,
        FLASH_VERIFY_ERROR,
        NO_FLASH,
        HOST_INTERFACE_READY,
        FIRMWARE_VERIFY_DONE,
        FIRMWARE_VERIFY_ERROR,
        FIRMWARE_HALTED
    }

    private enum InterruptStatusFlag {
        HOST_INTERRUPT_ASSERTED,
        WAKE_UP_FIFO_STATUS_1,
        WAKE_UP_FIFO_STATUS_2,
        NON_WAKE_UP_FIFO_STATUS_1,
        NON_WAKE_UP_FIFO_STATUS_2,
        STATUS_STATUS, // Indicates that a command result is ready
        DEBUG_STATUS,
        RESET_OR_FAULT
    }

    private enum HostInterfaceControlFlag {
        ABORT_TRANSFER_CHANNEL_0,
        ABORT_TRANSFER_CHANNEL_1,
        ABORT_TRANSFER_CHANNEL_2,
        ABORT_TRANSFER_CHANNEL_3,
        APPLICATION_PROCESSOR_SUSPENDED,
        RESERVED,
        TIMESTAMP_EVENT_REQUEST,
        ASYNC_STATUS_CHANNEL
    }

    /**
     * Public-facing methods should not throw this exception.
     * <p>
     * This exception indicates that initialization failed, and should be handled by returning
     * false from {@link BHI260IMU#internalInitialize(Parameters)}
     */
    private static class InitException extends Exception {}

    /**
     * Public-facing methods should not throw this exception.
     * <p>
     * This exception indicates that a command failed.
     */
    private static class CommandFailureException extends Exception {
        public CommandFailureException(String message) {
            super(message);
        }
    }

    private static class StatusPacket {
        public final int statusCode;
        public final byte[] payload;

        private StatusPacket(int statusCode, byte[] payload) {
            this.statusCode = statusCode;
            this.payload = payload;
        }
    }

    private static class StatusAndDebugFifoModeManager {
        private final boolean DEBUG = false;

        @Nullable private StatusAndDebugFifoMode statusAndDebugFifoMode = null;

        private final ReentrantLock operationInProgressLock = new ReentrantLock();

        /**
         * Set the Status and Debug FIFO mode, and prevent other threads from changing it until
         * this thread calls {@link #unlockStatusAndDebugFifoMode()}
         */
        public void lockStatusAndDebugFifoMode(I2cDeviceSynchSimple deviceClient, StatusAndDebugFifoMode mode) {
            if (DEBUG) {
                RobotLog.dd(TAG, "lockStatusAndDebugFifoMode(%s) called on thread \"%s\"", mode, Thread.currentThread().getName());
            }

            operationInProgressLock.lock();

            if (DEBUG) {
                RobotLog.dd(TAG, "lockStatusAndDebugFifoMode(%s) on thread \"%s\" acquired the lock", mode, Thread.currentThread().getName());
            }

            if (statusAndDebugFifoMode == null) {
                EnumSet<HostInterfaceControlFlag> hostInterfaceControlStatus = read8Flags(deviceClient, Register.HOST_INTERFACE_CONTROL, HostInterfaceControlFlag.class);
                if (hostInterfaceControlStatus.contains(HostInterfaceControlFlag.ASYNC_STATUS_CHANNEL)) {
                    statusAndDebugFifoMode = StatusAndDebugFifoMode.ASYNCHRONOUS;
                } else {
                    statusAndDebugFifoMode = StatusAndDebugFifoMode.SYNCHRONOUS;
                }
                RobotLog.vv(TAG, "Initial Status and Debug FIFO mode: %s", statusAndDebugFifoMode);
            }
            if (statusAndDebugFifoMode != mode) {
                statusAndDebugFifoMode = mode;

                final EnumSet<HostInterfaceControlFlag> hostInterfaceControlFlags = EnumSet.noneOf(HostInterfaceControlFlag.class);
                if (mode == StatusAndDebugFifoMode.ASYNCHRONOUS) { hostInterfaceControlFlags.add(HostInterfaceControlFlag.ASYNC_STATUS_CHANNEL); }

                write8Flags(deviceClient, Register.HOST_INTERFACE_CONTROL, hostInterfaceControlFlags, I2cWaitControl.ATOMIC);
                if (DEBUG) {
                    RobotLog.dd(TAG, "Set Status and Debug FIFO mode to %s (0x%X). New Host Interface Control value: %s", statusAndDebugFifoMode, convertEnumSetToInt(EnumSet.of(HostInterfaceControlFlag.ASYNC_STATUS_CHANNEL)), read8Flags(deviceClient, Register.HOST_INTERFACE_CONTROL, HostInterfaceControlFlag.class));
                }
            }
        }

        /**
         * Allow other threads to change the Status and Debug FIFO mode
         */
        public void unlockStatusAndDebugFifoMode() {
            if (DEBUG) {
                RobotLog.dd(TAG, "thread \"%s\" released the lock", Thread.currentThread().getName());
            }
            operationInProgressLock.unlock();
        }
    }
}

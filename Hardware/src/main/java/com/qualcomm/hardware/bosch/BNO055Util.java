/*
Copyright (c) 2022 REV Robotics, Robert Atkinson

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

import com.qualcomm.hardware.bosch.BNO055IMU.CalibrationData;
import com.qualcomm.hardware.bosch.BNO055IMU.Register;
import com.qualcomm.hardware.bosch.BNO055IMU.SensorMode;
import com.qualcomm.hardware.bosch.BNO055IMU.SystemStatus;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
import com.qualcomm.robotcore.hardware.I2cWaitControl;
import com.qualcomm.robotcore.hardware.I2cWarningManager;
import com.qualcomm.robotcore.hardware.QuaternionBasedImuHelper;
import com.qualcomm.robotcore.hardware.TimestampedData;
import com.qualcomm.robotcore.util.ReadWriteFile;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.TypeConversion;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;

import static com.qualcomm.hardware.bosch.BNO055IMUImpl.bCHIP_ID_VALUE;

public class BNO055Util {
    public static class InitException extends Exception {
        public InitException(String message) {
            super(message);
        }
    }

    /**
     * The deviceClient parameter needs to already have its I2C address set.
     */
    public static void sharedInit(I2cDeviceSynchSimple deviceClient, BNO055IMU.Parameters parameters) throws InitException {
        // Set to normal power mode
        write8(deviceClient, Register.PWR_MODE, BNO055IMUImpl.POWER_MODE.NORMAL.getValue(), I2cWaitControl.WRITTEN);

        // Make sure we're on page 0
        write8(deviceClient, Register.PAGE_ID, 0);

        // Set the output units. Section 3.6, p31
        //noinspection deprecation
        int unitsel = (parameters.pitchMode.bVal << 7) |       // pitch angle convention
                (parameters.temperatureUnit.bVal << 4) | // temperature
                (parameters.angleUnit.bVal << 2) |       // euler angle units
                (parameters.angleUnit.bVal << 1) |       // gyro units, per second
                (parameters.accelUnit.bVal /*<< 0*/);    // accelerometer units
        write8(deviceClient, Register.UNIT_SEL, unitsel);

        // Set the default axis map (the user may have changed it in a previous OpMode run)
        write8(deviceClient, Register.AXIS_MAP_CONFIG, 0x24);
        write8(deviceClient, Register.AXIS_MAP_SIGN, 0);

        // Switch to page 1 so we can write some more registers
        write8(deviceClient, Register.PAGE_ID, 1);

        // Configure selected page 1 registers
        write8(deviceClient, Register.ACC_CONFIG, parameters.accelPowerMode.bVal | parameters.accelBandwidth.bVal | parameters.accelRange.bVal);
        write8(deviceClient, Register.MAG_CONFIG, parameters.magPowerMode.bVal | parameters.magOpMode.bVal | parameters.magRate.bVal);
        write8(deviceClient, Register.GYR_CONFIG_0, parameters.gyroBandwidth.bVal | parameters.gyroRange.bVal);
        write8(deviceClient, Register.GYR_CONFIG_1, parameters.gyroPowerMode.bVal);

        // Switch back
        write8(deviceClient, Register.PAGE_ID, 0);

        write8(deviceClient, Register.SYS_TRIGGER, 0x00);

        if (parameters.calibrationData != null) {
            writeCalibrationData(deviceClient, parameters.calibrationData);
        } else if (parameters.calibrationDataFile != null) {
            try {
                File file = AppUtil.getInstance().getSettingsFile(parameters.calibrationDataFile);
                String serialized = ReadWriteFile.readFileOrThrow(file);
                CalibrationData data = CalibrationData.deserialize(serialized);
                writeCalibrationData(deviceClient, data);
            } catch (IOException e) {
                // Ignore the absence of the indicated file, etc
            }
        }

        // Finally, enter the requested operating mode (see section 3.3).
        setSensorMode(deviceClient, parameters.mode);
    }

    /**
     * The deviceClient parameter needs to already have its I2C address set.
     */
    // TODO(Noah): Migrate callers of BNO055IMUImpl.imuIsPresent() to this method
    public static boolean imuIsPresent(I2cDeviceSynchSimple deviceClient, boolean retryAfterWaiting) {
        final String TAG = "BNO055";
        RobotLog.vv(TAG, "Suppressing I2C warnings while we check for a BNO055 IMU");
        I2cWarningManager.suppressNewProblemDeviceWarnings(true);
        try {
            byte chipId = read8(deviceClient, Register.CHIP_ID);
            if (chipId != bCHIP_ID_VALUE && retryAfterWaiting) {
                try {
                    Thread.sleep(650); // delay value is from Table 0-2 in the BNO055 specification
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                chipId = read8(deviceClient, Register.CHIP_ID);
            }
            if (chipId == bCHIP_ID_VALUE) {
                RobotLog.vv(TAG, "Found BNO055 IMU");
                return true;
            } else {
                RobotLog.vv(TAG, "No BNO055 IMU found");
                return false;
            }
        } finally {
            I2cWarningManager.suppressNewProblemDeviceWarnings(false);
        }
    }

    public static SystemStatus getSystemStatus(I2cDeviceSynchSimple deviceClient, String tag) {
        byte bVal = read8(deviceClient, Register.SYS_STAT);
        SystemStatus status = SystemStatus.from(bVal);
        if (status == SystemStatus.UNKNOWN) {
            RobotLog.ww(tag, "unknown system status observed: 0x%08x", bVal);
        }
        return status;
    }

    public static void writeCalibrationData(I2cDeviceSynchSimple deviceClient, CalibrationData data) {
        // Section 3.11.4:
        //
        // It is important that the correct offsets and corresponding sensor radius are used.
        // Incorrect offsets may result in unreliable orientation data even at calibration
        // accuracy level 3. To set the calibration profile the following steps need to be taken
        //
        //    1. Select the operation mode to CONFIG_MODE
        //    2. Write the corresponding sensor offsets and radius data
        //    3. Change operation mode to fusion mode

        SensorMode prevMode = getSensorMode(deviceClient);

        if (prevMode != SensorMode.CONFIG)
            setSensorMode(deviceClient, SensorMode.CONFIG);

        // Make sure we're on page 0
        write8(deviceClient, Register.PAGE_ID, 0);

        writeShort(deviceClient, Register.ACC_OFFSET_X_LSB, data.dxAccel);
        writeShort(deviceClient, Register.ACC_OFFSET_Y_LSB, data.dyAccel);
        writeShort(deviceClient, Register.ACC_OFFSET_Z_LSB, data.dzAccel);
        writeShort(deviceClient, Register.MAG_OFFSET_X_LSB, data.dxMag);
        writeShort(deviceClient, Register.MAG_OFFSET_Y_LSB, data.dyMag);
        writeShort(deviceClient, Register.MAG_OFFSET_Z_LSB, data.dzMag);
        writeShort(deviceClient, Register.GYR_OFFSET_X_LSB, data.dxGyro);
        writeShort(deviceClient, Register.GYR_OFFSET_Y_LSB, data.dyGyro);
        writeShort(deviceClient, Register.GYR_OFFSET_Z_LSB, data.dzGyro);
        writeShort(deviceClient, Register.ACC_RADIUS_LSB, data.radiusAccel);
        writeShort(deviceClient, Register.MAG_RADIUS_LSB, data.radiusMag);

        // Restore the previous mode and return
        if (prevMode != SensorMode.CONFIG) {
            setSensorMode(deviceClient, prevMode);
        }
    }

    public static void setSensorMode(I2cDeviceSynchSimple deviceClient, SensorMode mode) {
        /* The default operation mode after power-on is CONFIGMODE. When the user changes to another
        operation mode, the sensors which are required in that particular sensor mode are powered,
        while the sensors whose signals are not required are set to suspend mode. */

        // Check if we are already in the desired mode
        if (getSensorMode(deviceClient) == mode) {
            return;
        }

        // Actually change the operation/sensor mode (waiting for the write to be completed)
        write8(deviceClient, Register.OPR_MODE, mode.bVal & 0x0F, I2cWaitControl.WRITTEN);

        try {
            // Delay per Table 3-6 of BNO055 Data sheet
            if (mode == SensorMode.CONFIG) {
                Thread.sleep(25); // Datasheet says 19ms
            } else {
                Thread.sleep(15); // Datasheet says 7ms
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static SensorMode getSensorMode(I2cDeviceSynchSimple deviceClient) {
        return SensorMode.fromByte(read8(deviceClient, Register.OPR_MODE));
    }

    public static Quaternion getRawQuaternion(I2cDeviceSynchSimple deviceClient) throws QuaternionBasedImuHelper.FailedToRetrieveQuaternionException {
        // Section 3.6.5.5 of BNO055 specification
        TimestampedData ts = deviceClient.readTimeStamped(Register.QUA_DATA_W_LSB.bVal, 8);

        boolean receivedAllZeros = true;
        for (byte b: ts.data) {
            if (b != 0) {
                receivedAllZeros = false;
                break;
            }
        }

        if (receivedAllZeros) {
            // All zeros is not a valid quaternion.
            throw new QuaternionBasedImuHelper.FailedToRetrieveQuaternionException();
        }

        BNO055IMUImpl.VectorData vector = new BNO055IMUImpl.VectorData(ts, (1 << 14));
        return new Quaternion(vector.next(), vector.next(), vector.next(), vector.next(), vector.data.nanoTime);
    }

    public static AngularVelocity getRawAngularVelocity(I2cDeviceSynchSimple deviceClient,
                                                        BNO055IMU.AngleUnit angleUnitConfiguredOnImu,
                                                        AngleUnit desiredAngleUnit) {

        BNO055IMUImpl.VectorData vector = new BNO055IMUImpl.VectorData(
                deviceClient.readTimeStamped(BNO055IMUImpl.VECTOR.GYROSCOPE.getValue(), 6),
                getAngularScale(angleUnitConfiguredOnImu));

        float xRotationRate = vector.next();
        float yRotationRate = vector.next();
        float zRotationRate = vector.next();

        return new AngularVelocity(angleUnitConfiguredOnImu.toAngleUnit(),
                xRotationRate, yRotationRate, zRotationRate, vector.data.nanoTime)
                .toAngleUnit(desiredAngleUnit);
    }

    public static float getAngularScale(BNO055IMU.AngleUnit angleUnit) {
        return angleUnit == BNO055IMU.AngleUnit.DEGREES ? 16.0f : 900.0f;
    }

    public static byte read8(I2cDeviceSynchSimple deviceClient, final Register reg) {
        return deviceClient.read8(reg.bVal);
    }

    public static byte[] read(I2cDeviceSynchSimple deviceClient, final Register reg, final int cb) {
        return deviceClient.read(reg.bVal, cb);
    }

    public static void write8(I2cDeviceSynchSimple deviceClient, Register reg, int data) {
        write8(deviceClient, reg, data, I2cWaitControl.ATOMIC);
    }

    public static void write8(I2cDeviceSynchSimple deviceClient, Register reg, int data, I2cWaitControl waitControl) {
        deviceClient.write8(reg.bVal, data, waitControl);
    }

    public static void write(I2cDeviceSynchSimple deviceClient, Register reg, byte[] data) {
        deviceClient.write(reg.bVal, data);
    }

    public static void writeShort(I2cDeviceSynchSimple deviceClient, final Register reg, short value) {
        byte[] data = TypeConversion.shortToByteArray(value, ByteOrder.LITTLE_ENDIAN);
        write(deviceClient, reg, data);
    }
}

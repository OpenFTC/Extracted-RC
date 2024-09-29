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
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDeviceWithParameters;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.ImuOrientationOnRobot;
import com.qualcomm.robotcore.hardware.QuaternionBasedImuHelper;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import static com.qualcomm.hardware.bosch.BNO055Util.getRawQuaternion;

/**
 * BNO055 IMU driver that implements the new {@link com.qualcomm.robotcore.hardware.IMU} interface
 */
public abstract class BNO055IMUNew extends I2cDeviceSynchDeviceWithParameters<I2cDeviceSynchSimple, IMU.Parameters> implements IMU {

    //----------------------------------------------------------------------------------------------
    // Constants
    //----------------------------------------------------------------------------------------------

    private static final String TAG = "BNO055IMU (new)";
    private static final BNO055IMU.AngleUnit INTERNAL_ANGLE_UNIT = BNO055IMU.AngleUnit.DEGREES;

    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    private final QuaternionBasedImuHelper helper;
    @Nullable private final I2cAddr guaranteedAddress;

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    /**
     * Use this constructor for BNO055-based sensors that allow the I2C address to be changed
     */
    public BNO055IMUNew(I2cDeviceSynchSimple deviceClient, boolean deviceClientIsOwned) {
        this(deviceClient, deviceClientIsOwned, null);
    }

    /**
     * Use this constructor for BNO055-based sensors that do NOT allow the I2C address to be changed
     */
    public BNO055IMUNew(I2cDeviceSynchSimple deviceClient, boolean deviceClientIsOwned, @Nullable I2cAddr guaranteedAddress) {
        super(deviceClient, deviceClientIsOwned, new Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));

        helper = new QuaternionBasedImuHelper(parameters.imuOrientationOnRobot);
        this.guaranteedAddress = guaranteedAddress;

        I2cAddr assumedAddress = guaranteedAddress;
        if (assumedAddress == null) {
            assumedAddress = BNO055IMU.I2CADDR_DEFAULT;
        }

        deviceClient.setI2cAddress(assumedAddress);

        if (BNO055Util.imuIsPresent(deviceClient, false)) {
            // Reset the yaw to ensure predictable behavior on app launch and Robot Restart, which
            // is when hardware device objects get (re) created. On boot, it's clearer if yaw 0 is
            // set at the time when the rest of the system finishes booting, instead of within the
            // first couple of seconds of power being applied. On Robot Restart, it's _much_ clearer
            // if the yaw gets reset to 0, instead of being set to the current rotation relative to
            // power on.
            if (initialize(parameters)) {
                // We call helper.resetYaw() directly instead of this.resetYaw() so that we can
                // specify a nice long timeout
                helper.resetYaw(TAG, () -> getRawQuaternion(deviceClient), 500);
            }
        }

    }

    @Override protected boolean internalInitialize(@NonNull IMU.Parameters genericParameters) {
        // This new BNO055 driver does NOT perform a reset, so that we don't wipe out the yaw offset
        // until the user requests that we do so.
        genericParameters = genericParameters.copy();

        Parameters parameters;
        if (genericParameters instanceof Parameters) {
            parameters = (Parameters) genericParameters;
        } else {
            if (!genericParameters.getClass().equals(IMU.Parameters.class)) {
                RobotLog.addGlobalWarningMessage(AppUtil.getDefContext().getString(R.string.parametersForOtherDeviceUsed));
            }
            parameters = new Parameters(genericParameters);
        }
        this.parameters = parameters;

        helper.setImuOrientationOnRobot(parameters.imuOrientationOnRobot);

        if (guaranteedAddress == null) {
            deviceClient.setI2cAddress(parameters.i2cAddr);
        } else {
            deviceClient.setI2cAddress(guaranteedAddress);
        }

        try {
            // Make sure we have the right device
            if (!BNO055Util.imuIsPresent(deviceClient, true)) {
                throw new BNO055Util.InitException("IMU appears to not be present");
            }

            // Get us into config mode, for sure
            BNO055Util.setSensorMode(deviceClient, BNO055IMU.SensorMode.CONFIG);

            BNO055Util.sharedInit(deviceClient, parameters.toOldParameters());
        } catch (BNO055Util.InitException e) {
            RobotLog.ee(TAG, e, "Failed to initialize BNO055 IMU");
            return false;
        }

        // Make sure the status is correct
        BNO055IMU.SystemStatus status = BNO055Util.getSystemStatus(deviceClient, TAG);
        return status == BNO055IMU.SystemStatus.RUNNING_FUSION;
    }

    //----------------------------------------------------------------------------------------------
    // IMU interface
    //----------------------------------------------------------------------------------------------

    @Override public void resetYaw() {
        // After initializing the BNO055 IMU, it can take about 50ms before it starts returning
        // valid data, so we want to leave some wiggle room.
        helper.resetYaw(TAG, () -> getRawQuaternion(deviceClient), 100);
    }

    @Override public YawPitchRollAngles getRobotYawPitchRollAngles() {
        return helper.getRobotYawPitchRollAngles(TAG, () -> getRawQuaternion(deviceClient));
    }

    @Override
    public Orientation getRobotOrientation(AxesReference reference, AxesOrder order, AngleUnit angleUnit) {
        return helper.getRobotOrientation(TAG, () -> getRawQuaternion(deviceClient), reference, order, angleUnit);
    }

    @Override public Quaternion getRobotOrientationAsQuaternion() {
        return helper.getRobotOrientationAsQuaternion(TAG, () -> getRawQuaternion(deviceClient), true);
    }

    @Override public AngularVelocity getRobotAngularVelocity(AngleUnit angleUnit) {
        return helper.getRobotAngularVelocity(
                BNO055Util.getRawAngularVelocity(deviceClient, INTERNAL_ANGLE_UNIT, INTERNAL_ANGLE_UNIT.toAngleUnit()),
                angleUnit);
    }

    //----------------------------------------------------------------------------------------------
    // HardwareDevice
    //----------------------------------------------------------------------------------------------

    @Override public abstract String getDeviceName();

    @Override public abstract Manufacturer getManufacturer();

    //----------------------------------------------------------------------------------------------
    // Parameters
    //----------------------------------------------------------------------------------------------

    /**
     * A version of the {@link IMU.Parameters} class that adds additional parameters specific to the
     * BNO055 IMU.
     */
    public static class Parameters extends IMU.Parameters {

        /** The I2C address of the BNO055 */
        public I2cAddr i2cAddr = BNO055IMU.I2CADDR_DEFAULT;

        /** Calibration data with which the BNO055 should be initialized.*/
        public BNO055IMU.CalibrationData calibrationData = null;

        /**
         * The path of a file containing calibration data with which the BNO055 should be
         * initialized. If {@link #calibrationData} is not null, that will be used instead.
         */
        public String calibrationDataFile = null;

        /**
         * @param imuOrientationOnRobot The orientation of the IMU relative to the robot. If the IMU
         *                              is in a REV Control or Expansion Hub, create an instance of
         *                              com.qualcomm.hardware.rev.RevHubOrientationOnRobot
         *                              (from the Hardware module).
         */
        public Parameters(ImuOrientationOnRobot imuOrientationOnRobot) {
            super(imuOrientationOnRobot);
        }

        private Parameters(IMU.Parameters genericParameters) {
            super(genericParameters.imuOrientationOnRobot);
        }

        @Override public Parameters copy() {
            Parameters copy = new Parameters(this.imuOrientationOnRobot);
            copy.i2cAddr = this.i2cAddr;
            copy.calibrationData = this.calibrationData;
            copy.calibrationDataFile = this.calibrationDataFile;
            return copy;
        }

        /**
         * @return A {@link BNO055IMU.Parameters} instance that represents the settings needed for
         *         this driver to work correctly, and respects the parameters set in this object by
         *         the user.
         */
        private BNO055IMU.Parameters toOldParameters() {
            BNO055IMU.Parameters result = new BNO055IMU.Parameters();
            result.angleUnit = INTERNAL_ANGLE_UNIT;
            result.calibrationData = this.calibrationData;
            result.calibrationDataFile = this.calibrationDataFile;
            return result;
        }
    }
}

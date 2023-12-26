/*
Copyright (c) 2016 Robert Atkinson

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
package com.qualcomm.hardware.bosch;

import androidx.annotation.NonNull;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cAddrConfig;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDeviceWithParameters;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
import com.qualcomm.robotcore.hardware.I2cWaitControl;
import com.qualcomm.robotcore.hardware.I2cWarningManager;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.QuaternionBasedImuHelper;
import com.qualcomm.robotcore.hardware.TimestampedData;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.ThreadPool;
import com.qualcomm.robotcore.util.TypeConversion;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Axis;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.MagneticFlux;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.external.navigation.Temperature;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@link BNO055IMUImpl} provides support for communicating with a BNO055 inertial motion
 * unit. Sensors using this integrated circuit are available from several manufacturers.
 */
public abstract class BNO055IMUImpl extends I2cDeviceSynchDeviceWithParameters<I2cDeviceSynch, BNO055IMU.Parameters>
        implements BNO055IMU, Gyroscope, IntegratingGyroscope, I2cAddrConfig, OpModeManagerNotifier.Notifications
    {

    public static class ImuNotInitializedException extends RuntimeException
        {
        public ImuNotInitializedException() { super("The IMU was not initialized"); }
        }

    /**
     * The deviceClient parameter needs to already have its I2C address set.
     */
    public static boolean imuIsPresent(I2cDeviceSynchSimple deviceClient, boolean retryAfterWaiting)
        {
        return BNO055Util.imuIsPresent(deviceClient, retryAfterWaiting);
        }

    //------------------------------------------------------------------------------------------
    // State
    //------------------------------------------------------------------------------------------

    protected final Object           dataLock = new Object();
    protected AccelerationIntegrator accelerationAlgorithm;

    protected final Object           startStopLock = new Object();
    protected ExecutorService        accelerationMananger;
    protected float                  delayScale             = 1;
    protected static final int       msAwaitChipId          = 2000;
    protected static final int       msAwaitSelfTest        = 2000;
    // The msAwaitSelfTest value is lore. We choose here to use the same value for awaiting chip id,
    // on the (not completely unreasonable) theory that similar things are happening in the chip in both
    // cases. A survey of other libraries is as follows:
    //  1000ms:     https://github.com/OpenROV/openrov-software-arduino/blob/master/OpenROV/BNO055.cpp
    //              https://github.com/alexstyl/Adafruit-BNO055-SparkCore-port/blob/master/Adafruit_BNO055.cpp

    // We always read as much as we can when we have nothing else to do
    protected static final I2cDeviceSynch.ReadMode readMode = I2cDeviceSynch.ReadMode.REPEAT;

    /**
     * One of two primary register windows we use for reading from the BNO055.
     *
     * Given the maximum allowable size of a register window, the set of registers on
     * a BNO055 can be usefully divided into two windows, which we here call lowerWindow
     * and upperWindow.
     *
     * When we find the need to change register windows depending on what data is being requested
     * from the sensor, we try to use these two windows so as to reduce the number of register
     * window switching that might be required as other data is read in the future.
     */
    protected static final I2cDeviceSynch.ReadWindow lowerWindow = newWindow(Register.CHIP_ID, Register.EUL_H_LSB);
    /**
     * A second of two primary register windows we use for reading from the BNO055.
     * We'd like to include the temperature register, too, but that would make a 27-byte window, and
     * those don't (currently) work in the CDIM.
     *
     * @see #lowerWindow
     */
    protected static final I2cDeviceSynch.ReadWindow upperWindow = newWindow(Register.EUL_H_LSB, Register.TEMP);

    protected static I2cDeviceSynch.ReadWindow newWindow(Register regFirst, Register regMax)
        {
        return new I2cDeviceSynch.ReadWindow(regFirst.bVal, regMax.bVal-regFirst.bVal, readMode);
        }

    protected void throwIfNotInitialized()
        {
        if (parameters.mode == SensorMode.DISABLED) throw new ImuNotInitializedException();
        }


    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    /**
     * This constructor is called internally by the FTC SDK.
     */
    public BNO055IMUImpl(I2cDeviceSynch deviceClient, boolean deviceClientIsOwned)
        {
        super(deviceClient, deviceClientIsOwned, disabledParameters());

        this.deviceClient.setReadWindow(lowerWindow);
        this.deviceClient.engage();

        this.accelerationAlgorithm = new NaiveAccelerationIntegrator();
        this.accelerationMananger  = null;

        this.registerArmingStateCallback(false);
        }

    // A concrete instance to use that allows us to avoid ever having NULL parameters
    protected static Parameters disabledParameters()
        {
        Parameters result = new Parameters();
        result.mode = SensorMode.DISABLED;
        return result;
        }

    //------------------------------------------------------------------------------------------
    // Notifications
    // This particular sensor wants to take action not only when the a user OpMode is started (for
    // which it gets a resetDeviceConfigurationForOpMode() as all HardwareDevices do) but also when
    // the OpMode ends. To that end, it implements OpModeManagerNotifier.Notifications.
    //------------------------------------------------------------------------------------------

    @Override public void resetDeviceConfigurationForOpMode()
        {
        // So that teams can maintain a 0-heading point across OpModes, we want to preserve the
        // parameters from run to run, and we do NOT want to mark the device as needing
        // initialization between OpModes.
        Parameters previousParameters = this.parameters;
        boolean previouslyInitialized = this.isInitialized;

        stopAccelerationIntegration();
        super.resetDeviceConfigurationForOpMode();

        this.parameters = previousParameters;
        this.isInitialized = previouslyInitialized;
        }

    @Override public void onOpModePreInit(OpMode opMode)
        {
        }

    @Override public void onOpModePreStart(OpMode opMode)
        {
        }

    @Override public void onOpModePostStop(OpMode opMode)
        {
        stopAccelerationIntegration();
        }

    //------------------------------------------------------------------------------------------
    // Initialization
    //------------------------------------------------------------------------------------------

    @Override public I2cAddr getI2cAddress()
        {
        return this.parameters.i2cAddr;
        }

    @Override public void setI2cAddress(I2cAddr newAddress)
        {
        this.parameters.i2cAddr = newAddress;
        this.deviceClient.setI2cAddress(newAddress);
        }

    /**
     * Initialize the device to be running in the indicated operation mode
     */
    @Override public boolean internalInitialize(@NonNull Parameters parameters)
        {
        if (parameters.mode==SensorMode.DISABLED) {
            // Semantically, we should probably return false here, because the sensor was not
            // successfully initialized. However, we now show a warning whenever initialization
            // fails (returns false). Since this class does not do anything with the
            // isInitialized field (and as the legacy IMU driver, it won't be receiving new
            // enhancements in the future), it doesn't really impact anything, so practicality wins.
            return true;
        }

        // Remember parameters so they're accessible starting during initialization.
        // Disconnect from user parameters so he won't interfere with us later.
        Parameters prevParameters = this.parameters;
        parameters = parameters.clone();
        this.parameters = parameters;

        // Configure logging as desired (we no longer log at the I2C level)
        // this.deviceClient.setLogging(this.parameters.loggingEnabled);
        // this.deviceClient.setLoggingTag(this.parameters.loggingTag);

        // Make sure we're talking to the correct I2c address
        this.deviceClient.setI2cAddress(parameters.i2cAddr);

        SystemStatus expectedStatus = parameters.mode.isFusionMode() ? SystemStatus.RUNNING_FUSION : SystemStatus.RUNNING_NO_FUSION;


        if (internalInitializeOnce(expectedStatus))
            {
            this.isInitialized = true;
            return true;
            }


        log_e("IMU initialization failed");
        this.parameters = prevParameters;
        return false;
        }

    /**
     * Do one attempt at initializing the device to be running in the indicated operation mode
     */
    protected boolean internalInitializeOnce(SystemStatus expectedStatus)
        {
        // Validate parameters
        if (SensorMode.CONFIG == parameters.mode)
            throw new IllegalArgumentException("SensorMode.CONFIG illegal for use as initialization mode");

        ElapsedTime elapsed = new ElapsedTime();
        if (parameters.accelerationIntegrationAlgorithm != null)
            {
            this.accelerationAlgorithm = parameters.accelerationIntegrationAlgorithm;
            }

        // Make sure we have the right device
        if (!imuIsPresent(deviceClient, true))
            {
            log_e("IMU appears to not be present");
            return false;
            }

        // Get us into config mode, for sure
        setSensorMode(SensorMode.CONFIG);

        // Reset the system, and wait for the chip id register to switch back from its reset state
        // to the chip id state. This can take a very long time, some 650ms (Table 0-2, p13)
        // perhaps. While in the reset state the chip id (and other registers) reads as 0xFF.
        I2cWarningManager.suppressNewProblemDeviceWarnings(true);
        try {
            elapsed.reset();
            write8(Register.SYS_TRIGGER, 0x20, I2cWaitControl.WRITTEN);
            delay(400);
            RobotLog.vv(getLoggingTag(), "Now polling until IMU comes out of reset. It is normal to see I2C failures below");
            byte chipId;
            while (!isStopRequested())
                {
                chipId = read8(Register.CHIP_ID);
                if (chipId == bCHIP_ID_VALUE)
                    break;
                delayExtra(10);
                if (elapsed.milliseconds() > msAwaitChipId)
                    {
                    log_e("failed to retrieve chip id");
                    return false;
                    }
                }
            delayLoreExtra(50);
            }
        finally
            {
                I2cWarningManager.suppressNewProblemDeviceWarnings(false);
            }

        RobotLog.vv(getLoggingTag(), "IMU has come out of reset. No more I2C failures should occur.");

        try
            {
            BNO055Util.sharedInit(deviceClient, parameters);
            }
        catch (BNO055Util.InitException e)
            {
            RobotLog.ee(getLoggingTag(), e, "Failed to initialize BNO055 IMU");
            return false;
            }

        // Make sure the status is correct before exiting
        SystemStatus status = getSystemStatus();
        if (status==expectedStatus)
            return true;
        else
            {
            log_w("IMU initialization failed: system status=%s expected=%s", status, expectedStatus);
            return false;
            }
        }

    protected void setSensorMode(SensorMode mode)
    /* The default operation mode after power-on is CONFIGMODE. When the user changes to another
    operation mode, the sensors which are required in that particular sensor mode are powered,
    while the sensors whose signals are not required are set to suspend mode. */
        {
        BNO055Util.setSensorMode(deviceClient, mode);
        }

    public synchronized SystemStatus getSystemStatus()
        {
        return BNO055Util.getSystemStatus(deviceClient, getLoggingTag());
        }

    public synchronized SystemError getSystemError()
        {
        byte bVal = read8(Register.SYS_ERR);
        SystemError error = SystemError.from(bVal);
        if (error==SystemError.UNKNOWN)
            {
            log_w("unknown system error observed: 0x%08x", bVal);
            }
        return error;
        }

    public synchronized CalibrationStatus getCalibrationStatus()
        {
        byte bVal = read8(Register.CALIB_STAT);
        return new CalibrationStatus(bVal);
        }

    //----------------------------------------------------------------------------------------------
    // HardwareDevice
    //----------------------------------------------------------------------------------------------

    @Override
    public void close()
        {
        stopAccelerationIntegration();
        super.close();
        }

    @Override public abstract String getDeviceName();

    @Override public abstract Manufacturer getManufacturer();

    //----------------------------------------------------------------------------------------------
    // Gyroscope
    //----------------------------------------------------------------------------------------------

    @Override public Set<Axis> getAngularVelocityAxes()
        {
        Set<Axis> result = new HashSet<Axis>();
        result.add(Axis.X);
        result.add(Axis.Y);
        result.add(Axis.Z);
        return result;
        }

    @Override public Set<Axis> getAngularOrientationAxes()
        {
        Set<Axis> result = new HashSet<Axis>();
        result.add(Axis.X);
        result.add(Axis.Y);
        result.add(Axis.Z);
        return result;
        }

    @Override
    public synchronized AngularVelocity getAngularVelocity(org.firstinspires.ftc.robotcore.external.navigation.AngleUnit unit)
        {
        throwIfNotInitialized();

        // Ensure that the 6 bytes for this vector are visible in the register window.
        ensureReadWindow(new I2cDeviceSynch.ReadWindow(VECTOR.GYROSCOPE.getValue(), 6, readMode));

        AngularVelocity rawAngularVelocity = BNO055Util.getRawAngularVelocity(deviceClient, parameters.angleUnit, unit);
        // Negate the X and Y axes (see the comment in getAngularOrientation())
        return new AngularVelocity(
                unit,
                -rawAngularVelocity.xRotationRate,
                -rawAngularVelocity.yRotationRate,
                rawAngularVelocity.zRotationRate,
                rawAngularVelocity.acquisitionTime);
        }

    @Override
    public Orientation getAngularOrientation(AxesReference reference, AxesOrder order, org.firstinspires.ftc.robotcore.external.navigation.AngleUnit angleUnit)
        {
        return getAngularOrientation().toAxesReference(reference).toAxesOrder(order).toAngleUnit(angleUnit);
        }

    //------------------------------------------------------------------------------------------
    // Calibration
    //------------------------------------------------------------------------------------------

    public synchronized boolean isSystemCalibrated()
        {
        byte b = this.read8(Register.CALIB_STAT);
        return ((b>>6) & 0x03) == 0x03;
        }

    public synchronized boolean isGyroCalibrated()
        {
        byte b = this.read8(Register.CALIB_STAT);
        return ((b>>4) & 0x03) == 0x03;
        }

    public synchronized boolean isAccelerometerCalibrated()
        {
        byte b = this.read8(Register.CALIB_STAT);
        return ((b>>2) & 0x03) == 0x03;
        }

    public synchronized boolean isMagnetometerCalibrated()
        {
        byte b = this.read8(Register.CALIB_STAT);
        return ((b/*>>0*/) & 0x03) == 0x03;
        }

    public CalibrationData readCalibrationData()
        {
        // From Section 3.11.4 of the datasheet:
        //
        // "The calibration profile includes sensor offsets and sensor radius. Host system can
        // read the offsets and radius only after a full calibration is achieved and the operation
        // mode is switched to CONFIG_MODE. Refer to sensor offsets and sensor radius registers."
        //
        // Other useful links:
        //      https://forums.adafruit.com/viewtopic.php?f=22&t=83965
        //      https://learn.adafruit.com/bno055-absolute-orientation-sensor-with-raspberry-pi-and-beaglebone-black/webgl-example#sensor-calibration
        //      http://iotdk.intel.com/docs/master/upm/classupm_1_1_b_n_o055.html

        SensorMode prevMode = BNO055Util.getSensorMode(deviceClient);
        if (prevMode != SensorMode.CONFIG) setSensorMode(SensorMode.CONFIG);

        CalibrationData result = new CalibrationData();
        result.dxAccel = readShort(Register.ACC_OFFSET_X_LSB);
        result.dyAccel = readShort(Register.ACC_OFFSET_Y_LSB);
        result.dzAccel = readShort(Register.ACC_OFFSET_Z_LSB);
        result.dxMag   = readShort(Register.MAG_OFFSET_X_LSB);
        result.dyMag   = readShort(Register.MAG_OFFSET_Y_LSB);
        result.dzMag   = readShort(Register.MAG_OFFSET_Z_LSB);
        result.dxGyro  = readShort(Register.GYR_OFFSET_X_LSB);
        result.dyGyro  = readShort(Register.GYR_OFFSET_Y_LSB);
        result.dzGyro  = readShort(Register.GYR_OFFSET_Z_LSB);
        result.radiusAccel = readShort(Register.ACC_RADIUS_LSB);
        result.radiusMag   = readShort(Register.MAG_RADIUS_LSB);

        // Restore the previous mode and return
        if (prevMode != SensorMode.CONFIG) setSensorMode(prevMode);
        return result;
        }

    public void writeCalibrationData(CalibrationData data)
        {
        BNO055Util.writeCalibrationData(deviceClient, data);
        }

    //------------------------------------------------------------------------------------------
    // BNO055IMU data retrieval
    //------------------------------------------------------------------------------------------

    public synchronized Temperature getTemperature()
        {
        throwIfNotInitialized();
        byte b = this.read8(Register.TEMP);
        return new Temperature(this.parameters.temperatureUnit.toTempUnit(), (double)b, System.nanoTime());
        }

    public synchronized MagneticFlux getMagneticFieldStrength()
        {
        throwIfNotInitialized();
        VectorData vector = getVector(VECTOR.MAGNETOMETER, getFluxScale());
        return new MagneticFlux(vector.next(), vector.next(), vector.next(), vector.data.nanoTime);
        }
    public synchronized Acceleration getOverallAcceleration()
        {
        throwIfNotInitialized();
        VectorData vector = getVector(VECTOR.ACCELEROMETER, getMetersAccelerationScale());
        return new Acceleration(DistanceUnit.METER, vector.next(), vector.next(), vector.next(), vector.data.nanoTime);
        }
    public synchronized Acceleration getLinearAcceleration()
        {
        throwIfNotInitialized();
        VectorData vector = getVector(VECTOR.LINEARACCEL, getMetersAccelerationScale());
        return new Acceleration(DistanceUnit.METER, vector.next(), vector.next(), vector.next(), vector.data.nanoTime);
        }
    public synchronized Acceleration getGravity()
        {
        throwIfNotInitialized();
        VectorData vector = getVector(VECTOR.GRAVITY, getMetersAccelerationScale());
        return new Acceleration(DistanceUnit.METER, vector.next(), vector.next(), vector.next(), vector.data.nanoTime);
        }
    public synchronized AngularVelocity getAngularVelocity()
        {
        throwIfNotInitialized();
        return getAngularVelocity(parameters.angleUnit.toAngleUnit());
        }
    public synchronized Orientation getAngularOrientation()
        {
        // Data returned from VECTOR.EULER is heading, roll, pitch, in that order as specified by
        // the Android convention (https://developer.android.com/guide/topics/sensors/sensors_position).
        //
        // Note that the coordinate system used by the BNO055 interface is rotated 180 degrees
        // around the Z axis from the coordinate system defined in the datasheet. This means that
        // the values for the X and Y axes need to be negated from what they would be normally.
        //
        // The IMU returns heading in what one might call 'compass' direction, with values
        // increasing CW. We need a geometric direction, with values increasing CCW. So we simply negate.
        //
        // The IMU returns roll and pitch with values increasing CW according to the coordinate axes
        // specified in section 3.4 of the datasheet given the Android pitch mode. Since we need
        // values that increase with CCW rotation (as per the right hand rule), the IMU effectively
        // performs the X and Y negations for us when retrieving orientation. However, the angular
        // velocity values are positive for CCW rotation, which means that we need to negate the x
        // and y rotation rates for angular velocity.
        //
        // The data returned from the IMU is in the units that we initialized the IMU to return.
        // However, the IMU has a different sense of angle normalization than we do, so we explicitly
        // normalize such that users aren't surprised by (e.g.) Z angles which always appear as negative
        // (in the range (-360, 0]).
        //
        throwIfNotInitialized();
        VectorData vector = getVector(VECTOR.EULER, getAngularScale());
        org.firstinspires.ftc.robotcore.external.navigation.AngleUnit angleUnit = parameters.angleUnit.toAngleUnit();
        return new Orientation(AxesReference.INTRINSIC, AxesOrder.ZYX, angleUnit,
                angleUnit.normalize(-vector.next()),
                angleUnit.normalize(vector.next()),
                angleUnit.normalize(vector.next()),
                vector.data.nanoTime);
        }

    public synchronized Quaternion getQuaternionOrientation()
        {
        throwIfNotInitialized();
        // Ensure we can see the registers we need
        deviceClient.ensureReadWindow(
                new I2cDeviceSynch.ReadWindow(Register.QUA_DATA_W_LSB.bVal, 8, readMode),
                upperWindow);
        try
            {
            return BNO055Util.getRawQuaternion(deviceClient);
            }
        catch (QuaternionBasedImuHelper.FailedToRetrieveQuaternionException e)
            {
            if (parameters.loggingEnabled)
                {
                RobotLog.ww(getLoggingTag(), "Failed to retrieve valid quaternion. Returning identity quaternion.");
                }
            return Quaternion.identityQuaternion();
            }
        }

    /**
     * Return the number by which we need to divide a raw angle as read from the device in order
     * to convert it to our current angular units. See Table 3-22 of the BNO055 spec
     */
    protected float getAngularScale()
        {
        return BNO055Util.getAngularScale(this.parameters.angleUnit);
        }

    /**
     * Return the number by which we need to divide a raw acceleration as read from the device in order
     * to convert it to our current acceleration units. See Table 3-17 of the BNO055 spec.
     */
    protected float getAccelerationScale()
        {
        return this.parameters.accelUnit == AccelUnit.METERS_PERSEC_PERSEC ? 100.0f : 1.0f;
        }

    protected float getMetersAccelerationScale()
        {
        // Logically, the difference in scale between m/s^2 and mg should be 1000 / gravity
        // == 1000 / 9.80665 == 101.97162. And that's not 100. But if we actually use the
        // logically correct scale factor, the magnitude of the reported gravity vector doesn't
        // come out correct when running in MILLI_EARTH_GRAVITY mode, and, presumably, the other
        // accelerations are equally incorrect. A value of 100 seems to make that work, so we use it.
        // Which is a bit of a mystery, as it's almost like the MILLI_EARTH_GRAVITY and
        // METERS_PERSEC_PERSEC modes are actually one and the same. For now, we go with what
        // works.

        float scaleConversionFactor = 100;

        return this.parameters.accelUnit == AccelUnit.METERS_PERSEC_PERSEC
                ? getAccelerationScale()
                : getAccelerationScale() /*in mg*/ * scaleConversionFactor;
        }

    /**
     * Return the number by which we need to divide a raw acceleration as read from the device in order
     * to convert it to our current angular units. See Table 3-19 of the BNO055 spec. Note that the
     * BNO055 natively uses micro Teslas; we instead use Teslas.
     */
    protected float getFluxScale()
        {
        return 16.0f * 1000000.0f;
        }

    protected VectorData getVector(final VECTOR vector, float scale)
        {
        // Ensure that the 6 bytes for this vector are visible in the register window.
        ensureReadWindow(new I2cDeviceSynch.ReadWindow(vector.getValue(), 6, readMode));

        // Read the data
        return new VectorData(deviceClient.readTimeStamped(vector.getValue(), 6), scale);
        }

    protected static class VectorData
        {
        public    TimestampedData   data;
        public    float             scale;
        protected ByteBuffer        buffer;

        public VectorData(TimestampedData data, float scale)
            {
            this.data = data;
            this.scale = scale;
            buffer = ByteBuffer.wrap(data.data).order(ByteOrder.LITTLE_ENDIAN);
            }

        public float next()
            {
            return buffer.getShort() / scale;
            }
        }

    //------------------------------------------------------------------------------------------
    // Position and velocity management
    //------------------------------------------------------------------------------------------

    public Acceleration getAcceleration()
        {
        synchronized (dataLock)
            {
            Acceleration result = this.accelerationAlgorithm.getAcceleration();
            if (result == null) result = new Acceleration();
            return result;
            }
        }
    public Velocity getVelocity()
        {
        synchronized (dataLock)
            {
            Velocity result = this.accelerationAlgorithm.getVelocity();
            if (result == null) result = new Velocity();
            return result;
            }
        }
    public Position getPosition()
        {
        synchronized (dataLock)
            {
            Position result = this.accelerationAlgorithm.getPosition();
            if (result == null) result = new Position();
            return result;
            }
        }

    public void startAccelerationIntegration(Position initalPosition, Velocity initialVelocity, int msPollInterval)
    // Start integrating acceleration to determine position and velocity by polling for acceleration every while
        {
        throwIfNotInitialized();
        synchronized (this.startStopLock)
            {
            // Stop doing this if we're already in flight
            this.stopAccelerationIntegration();

            // Set the current position and velocity
            this.accelerationAlgorithm.initialize(this.parameters, initalPosition, initialVelocity);

            // Make a new thread on which to do the integration
            this.accelerationMananger = ThreadPool.newSingleThreadExecutor("imu acceleration");

            // Start the whole schebang a rockin...
            this.accelerationMananger.execute(new AccelerationManager(msPollInterval));
            }
        }

    public void stopAccelerationIntegration() // needs a different lock than 'synchronized(this)'
        {
        synchronized (this.startStopLock)
            {
            // Stop the integration thread
            if (this.accelerationMananger != null)
                {
                this.accelerationMananger.shutdownNow();
                ThreadPool.awaitTerminationOrExitApplication(this.accelerationMananger, 10, TimeUnit.SECONDS, "IMU acceleration", "unresponsive user acceleration code");
                this.accelerationMananger = null;
                }
            }
        }

    /** Maintains current velocity and position by integrating acceleration */
    class AccelerationManager implements Runnable
        {
        protected final int msPollInterval;
        protected final static long nsPerMs = ElapsedTime.MILLIS_IN_NANO;

        AccelerationManager(int msPollInterval)
            {
            this.msPollInterval = msPollInterval;
            }

        @Override public void run()
            {
            // Don't let inappropriate exceptions sneak out
            try
                {
                // Loop until we're asked to stop
                while (!isStopRequested())
                    {
                    // Read the latest available acceleration
                    final Acceleration linearAcceleration = BNO055IMUImpl.this.getLinearAcceleration();

                    // Have the algorithm do its thing
                    synchronized (dataLock)
                        {
                        accelerationAlgorithm.update(linearAcceleration);
                        }

                    // Wait an appropriate interval before beginning again
                    if (msPollInterval > 0)
                        {
                        long msSoFar = (System.nanoTime() - linearAcceleration.acquisitionTime) / nsPerMs;
                        long msReadFudge = 5;   // very roughly accounts for delta from read request to acquisitionTime setting
                        Thread.sleep(Math.max(0,msPollInterval - msSoFar - msReadFudge));
                        }
                    else
                        Thread.yield(); // never do a hard spin
                    }
                }
            catch (InterruptedException|CancellationException e)
                {
                return;
                }
            }
        }

    boolean isStopRequested()
        {
        return Thread.currentThread().isInterrupted();
        }

    @Override public synchronized byte read8(final Register reg)
        {
        return deviceClient.read8(reg.bVal);
        }

    @Override public synchronized byte[] read(final Register reg, final int cb)
        {
        return deviceClient.read(reg.bVal, cb);
        }

    protected short readShort(final Register reg)
        {
        byte[] data = read(reg, 2);
        return TypeConversion.byteArrayToShort(data, ByteOrder.LITTLE_ENDIAN);
        }


    @Override public void write8(Register reg, int data)
        {
        write8(reg, data, I2cWaitControl.ATOMIC);
        }

    public void write8(Register reg, int data, I2cWaitControl waitControl)
        {
        this.deviceClient.write8(reg.bVal, data, waitControl);
        }

    @Override public void write(Register reg, byte[] data)
        {
        write(reg, data, I2cWaitControl.ATOMIC);
        }

    public void write(Register reg, byte[] data, I2cWaitControl waitControl)
        {
        this.deviceClient.write(reg.bVal, data, waitControl);
        }

    protected void writeShort(final Register reg, short value)
        {
        byte[] data = TypeConversion.shortToByteArray(value, ByteOrder.LITTLE_ENDIAN);
        write(reg, data);
        }
    protected void waitForWriteCompletions()
        {
        // We use ATOMIC for legacy reasons, but now that we have WRITTEN, that might
        // be a better choice.
        this.deviceClient.waitForWriteCompletions(I2cWaitControl.ATOMIC);
        }

    //------------------------------------------------------------------------------------------
    // Internal utility
    //------------------------------------------------------------------------------------------

    protected String getLoggingTag()
        {
        return parameters.loggingTag;
        }

    protected void log_v(String format, Object... args)
        {
        if (this.parameters.loggingEnabled)
            {
            String message = String.format(format, args);
            Log.v(getLoggingTag(), message);
            }
        }

    protected void log_d(String format, Object... args)
        {
        if (this.parameters.loggingEnabled)
            {
            String message = String.format(format, args);
            Log.d(getLoggingTag(), message);
            }
        }

    protected void log_w(String format, Object... args)
        {
        if (this.parameters.loggingEnabled)
            {
            String message = String.format(format, args);
            Log.w(getLoggingTag(), message);
            }
        }

    protected void log_e(String format, Object... args)
        {
        if (this.parameters.loggingEnabled)
            {
            String message = String.format(format, args);
            Log.e(getLoggingTag(), message);
            }
        }

    protected void ensureReadWindow(I2cDeviceSynch.ReadWindow needed)
    // We optimize small windows into larger ones if we can
        {
        I2cDeviceSynch.ReadWindow windowToSet = lowerWindow.containsWithSameMode(needed)
            ? lowerWindow
            : upperWindow.containsWithSameMode(needed)
                ? upperWindow
                : needed;           // just use what's needed if it's not within our two main windows
        this.deviceClient.ensureReadWindow(needed, windowToSet);
        }

    // Our write logic doesn't actually know when the I2C writes are issued. All it knows is
    // when the write has made it to the USB Core Device Interface Module. It's a pretty
    // deterministic interval after that that the I2C write occurs, we guess, but we don't
    // really know what that is. To account for this, we slop in some extra time to the
    // delays so that we're not cutting things too close to the edge. And given that this is
    // initialization logic and so not time critical, we err on being generous: the current
    // setting of this extra can undoubtedly be reduced.

    protected final static int msExtra = 50;

    protected void delayExtra(int ms)
        {
        delay(ms + msExtra);
        }
    protected void delayLoreExtra(int ms)
        {
        delayLore(ms + msExtra);
        }

    /**
     * delayLore() implements a delay that only known by lore and mythology to be necessary.
     *
     * @see #delay(int)
     */
    protected void delayLore(int ms)
        {
        delay(ms);
        }

    /**
     * delay() implements delays which are known to be necessary according to the BNO055 specification
     *
     * @see #delayLore(int)
     */
    protected void delay(int ms)
        {
        try
            {
            // delays are usually relative to preceding writes, so make sure they're all out to the controller
            this.waitForWriteCompletions();
            Thread.sleep((int)(ms * delayScale));
            }
        catch (InterruptedException e)
            {
            Thread.currentThread().interrupt();
            }
        }

    protected void enterConfigModeFor(Runnable action)
        {
        SensorMode modePrev = BNO055Util.getSensorMode(deviceClient);
        setSensorMode(SensorMode.CONFIG);
        delayLoreExtra(25);
        try
            {
            action.run();
            }
        finally
            {
            setSensorMode(modePrev);
            delayLoreExtra(20);
            }
        }

    protected <T> T enterConfigModeFor(Func<T> lambda)
        {
        T result;

        SensorMode modePrev = BNO055Util.getSensorMode(deviceClient);
        setSensorMode(SensorMode.CONFIG);
        delayLoreExtra(25);
        try
            {
            result = lambda.value();
            }
        finally
            {
            setSensorMode(modePrev);
            delayLoreExtra(20);
            }
        //
        return result;
        }

    //------------------------------------------------------------------------------------------
    // Constants
    //------------------------------------------------------------------------------------------

    public final static byte bCHIP_ID_VALUE = (byte)0xa0;

    enum VECTOR
        {
            ACCELEROMETER   (Register.ACC_DATA_X_LSB),
            MAGNETOMETER    (Register.MAG_DATA_X_LSB),
            GYROSCOPE       (Register.GYR_DATA_X_LSB),
            EULER           (Register.EUL_H_LSB),
            LINEARACCEL     (Register.LIA_DATA_X_LSB),
            GRAVITY         (Register.GRV_DATA_X_LSB);
        //------------------------------------------------------------------------------------------
        protected byte value;
        VECTOR(int value) { this.value = (byte)value; }
        VECTOR(Register register) { this(register.bVal); }
        public byte getValue() { return this.value; }
        }

    enum POWER_MODE
        {
            NORMAL(0X00),
            LOWPOWER(0X01),
            SUSPEND(0X02);
        //------------------------------------------------------------------------------------------
        protected byte value;
        POWER_MODE(int value) { this.value = (byte)value; }
        public byte getValue() { return this.value; }
        }

    }

// This code is in part modelled after https://github.com/adafruit/Adafruit_BNO055

/***************************************************************************
 This is a library for the BNO055 orientation sensor

 Designed specifically to work with the Adafruit BNO055 Breakout.

 Pick one up today in the adafruit shop!
 ------> http://www.adafruit.com/products

 These sensors use I2C to communicate, 2 pins are required to interface.

 Adafruit invests time and resources providing this open source code,
 please support Adafruit andopen-source hardware by purchasing products
 from Adafruit!

 Written by KTOWN for Adafruit Industries.

 MIT license, all text above must be included in any redistribution
 ***************************************************************************/

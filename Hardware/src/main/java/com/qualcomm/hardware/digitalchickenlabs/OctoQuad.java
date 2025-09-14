/*
 * Copyright (c) 2025 DigitalChickenLabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.qualcomm.hardware.digitalchickenlabs;

import androidx.annotation.NonNull;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import java.util.Locale;

public interface OctoQuad extends HardwareDevice
{
    byte OCTOQUAD_CHIP_ID = 0x51;
    int SUPPORTED_FW_VERSION_MAJ = 3;
    int ENCODER_FIRST = 0;
    int ENCODER_LAST = 7;
    int NUM_ENCODERS = 8;
    int MIN_VELOCITY_MEASUREMENT_INTERVAL_MS = 1;
    int MAX_VELOCITY_MEASUREMENT_INTERVAL_MS = 255;
    int MIN_PULSE_WIDTH_US = 0;  //  The symbol for microseconds is μs, but is sometimes simplified to us.
    int MAX_PULSE_WIDTH_US = 0xFFFF;

    /**
     * Reads the CHIP_ID register of the OctoQuad
     * @return the value in the CHIP_ID register of the OctoQuad
     */
    byte getChipId();

    /**
     * Class to represent an OctoQuad firmware version
     */
    class FirmwareVersion
    {
        public final int maj;
        public final int min;
        public final int eng;

        public FirmwareVersion(int maj, int min, int eng)
        {
            this.maj = maj;
            this.min = min;
            this.eng = eng;
        }

        @NonNull
        @Override
        public String toString()
        {
            return String.format(Locale.US, "%d.%d.%d", maj, min, eng);
        }
    }

    /**
     * Get the firmware version running on the OctoQuad
     * @return the firmware version running on the OctoQuad
     */
    FirmwareVersion getFirmwareVersion();

    /**
     * Get the firmware version running on the OctoQuad
     * @return the firmware version running on the OctoQuad
     */
    String getFirmwareVersionString();

    /**
     * Allows reversing an encoder channel internally on the OctoQuad.
     * This has basically the same effect as negating pos/vel in user
     * code, except for when using the absolute localizer, in which case
     * this must be used to inform the firmware of the tracking wheel
     * directions
     */
    enum EncoderDirection
    {
        FORWARD,
        REVERSE
    }

    /**
     * Set the direction for a single encoder
     * This has basically the same effect as negating pos/vel in user
     * code, except for when using the absolute localizer, in which case
     * this must be used to inform the firmware of the tracking wheel
     * directions.
     * This parameter will NOT be retained across power cycles, unless
     * you call {@link #saveParametersToFlash()} ()}
     * @param idx the index of the encoder
     * @param dir direction
     */
    void setSingleEncoderDirection(int idx, EncoderDirection dir);

    /**
     * Get the direction for a single encoder
     * @param idx the index of the encoder
     * @return direction of the encoder in question
     */
    EncoderDirection getSingleEncoderDirection(int idx);

    /**
     * Set the direction for all encoders
     * This parameter will NOT be retained across power cycles, unless
     * you call {@link #saveParametersToFlash()} ()}
     * See {@link #setSingleEncoderDirection(int, EncoderDirection)}
     * @param reverse 8-length direction array
     */
    void setAllEncoderDirections(boolean[] reverse);

    /**
     * Switch which mode a channel bank operates in
     */
    enum ChannelBankConfig
    {
        /**
         * Both channel banks are configured for Quadrature input
         */
        ALL_QUADRATURE(0),

        /**
         * Both channel banks are configured for pulse width input
         */
        ALL_PULSE_WIDTH(1),

        /**
         * Bank 1 (channels 0-3) is configured for Quadrature input;
         * Bank 2 (channels 4-7) is configured for pulse width input.
         */
        BANK1_QUADRATURE_BANK2_PULSE_WIDTH(2);

        final byte bVal;

        ChannelBankConfig(int bVal)
        {
            this.bVal = (byte) bVal;
        }
    }

    /**
     * Configures the OctoQuad's channel banks
     * This parameter will NOT be retained across power cycles, unless
     * you call {@link #saveParametersToFlash()} ()}
     * @param config the channel bank configuration to use
     */
    void setChannelBankConfig(ChannelBankConfig config);

    /**
     * Queries the OctoQuad to determine the current channel bank configuration
     * @return the current channel bank configuration
     */
    ChannelBankConfig getChannelBankConfig();

    /**
     * A data block holding all encoder data; this block may be bulk read
     * in one I2C operation. You should check if the CRC is OK before using the data.
     */
    class EncoderDataBlock
    {
        public int[] positions = new int[NUM_ENCODERS];
        public short[] velocities = new short[NUM_ENCODERS];
        public boolean crcOk;

        /**
         * Check whether it is likely that this data is valid by checking if the CRC
         * on the returned is bad. For example, if there is an I2C bus stall or bit flip,
         * you could avoid acting on corrupted data.
         * @return whether it is likely that this data is valid
         */
        public boolean isDataValid()
        {
            return crcOk;
        }

        public void copyTo(EncoderDataBlock target)
        {
            System.arraycopy(positions, 0, target.positions, 0, NUM_ENCODERS);
            System.arraycopy(velocities, 0, target.velocities, 0, NUM_ENCODERS);
            target.crcOk = crcOk;
        }
    }

    /**
     * Reads all encoder data from the OctoQuad, writing the data into
     * an existing {@link EncoderDataBlock} object. The previous values are destroyed.
     * @param out the {@link EncoderDataBlock} object to fill with new data
     */
    void readAllEncoderData(EncoderDataBlock out);

    /**
     * Reads all encoder data from the OctoQuad
     * @return a {@link EncoderDataBlock} object with the new data
     */
    EncoderDataBlock readAllEncoderData();

    /**
     * Controls how data is cached to reduce the number
     * of Lynx transactions needed to read the encoder data
     */
    enum CachingMode
    {
        /**
         * The cache is only updated when you call {@link #refreshCache()}
         */
        MANUAL,

        /**
         * The cache is updated the 2nd time you call {@link #readSinglePosition_Caching(int)} (int)}
         * or {@link #readSingleVelocity_Caching(int)} (int)} for the same encoder index.
         */
        AUTO
    }

    /**
     * Set the data caching mode for the OctoQuad.  Applies to xxx_Caching() calls.
     * @param mode mode to use. Choices:
     *  MANUAL:      The cache is only updated when you call {@link #refreshCache()}.
     *  AUTO:  (def) The cache is updated the 2nd time you call {@link #readSinglePosition_Caching(int)} (int)}
     *               or {@link #readSingleVelocity_Caching(int)} (int)} for the same encoder index.
     */
    void setCachingMode(CachingMode mode);

    /**
     * Manually refresh the position and velocity data cache
     */
    void refreshCache();

    /**
     * Read a single position from the data cache
     * Depending on the channel bank configuration, this may
     * either be quadrature step count, or pulse width.
     * See {@link #setCachingMode(CachingMode mode)} for more details about caching.
     * @param idx the index of the encoder to read
     * @return the position for the specified encoder
     */
    int readSinglePosition_Caching(int idx);

    /**
     * Read a single velocity from the data cache
     * NOTE: if using an absolute pulse width encoder, in order to get sane
     * velocity data, you must set the channel min/max pulse width parameter.
     * @param idx the index of the encoder to read
     * @return the velocity for the specified encoder
     */
    short readSingleVelocity_Caching(int idx);

    /**
     * Deprecated - delegates to {@link #readSingleVelocity_Caching(int)}
     */
    @Deprecated
    short readSingleVelocity(int idx);

    /**
     * Deprecated - delegates to {@link #readSinglePosition_Caching(int)}
     */
    @Deprecated
    int readSinglePosition(int idx);

    /**
     * Reset a single encoder in the OctoQuad firmware
     * @param idx the index of the encoder to reset
     */
    void resetSinglePosition(int idx);

    /**
     * Reset all encoder counts in the OctoQuad firmware
     */
    void resetAllPositions();

    /**
     * Reset multiple encoders in the OctoQuad firmware in one command
     * @param resets the encoders to be reset
     */
    void resetMultiplePositions(boolean[] resets);

    /**
     * Reset multiple encoders in the OctoQuad firmware in one command
     * @param indices the indices of the encoders to reset
     */
    void resetMultiplePositions(int... indices);

    /**
     * Set the velocity sample interval for a single encoder
     * This parameter will NOT be retained across power cycles, unless
     * you call {@link #saveParametersToFlash()} ()}
     * @param idx the index of the encoder in question
     * @param intvlms the sample interval in milliseconds
     */
    void setSingleVelocitySampleInterval(int idx, int intvlms);

    /**
     * Read a single velocity sample interval
     * @param idx the index of the encoder in question
     * @return the velocity sample interval
     */
    int getSingleVelocitySampleInterval(int idx);

    /**
     * Set the velocity sample intervals for all encoders
     * This parameter will NOT be retained across power cycles, unless
     * you call {@link #saveParametersToFlash()} ()}
     * @param intvlms the sample interval in milliseconds
     */
    void setAllVelocitySampleIntervals(int intvlms);

    /**
     * A data block containing minimum and maximum pulse widths to be
     * applied to a channel using {@link #setSingleChannelPulseWidthParams(int, ChannelPulseWidthParams)}
     */
    class ChannelPulseWidthParams
    {
        public int min_length_us;
        public int max_length_us;

        public ChannelPulseWidthParams() {}

        public ChannelPulseWidthParams(int min_length_us, int max_length_us)
        {
            this.min_length_us = min_length_us;
            this.max_length_us = max_length_us;
        }
    }

    /**
     * Configure the minimum/maximum pulse width reported by an absolute encoder
     * which is connected to a given channel, to allow the ability to provide
     * accurate velocity data.
     * These parameters will NOT be retained across power cycles, unless
     * you call {@link #saveParametersToFlash()} ()}
     * @param idx the channel in question
     * @param min_length_us minimum pulse width
     * @param max_length_us maximum pulse width
     */
    void setSingleChannelPulseWidthParams(int idx, int min_length_us, int max_length_us);

    /**
     * Configure the minimum/maximum pulse width reported by an absolute encoder
     * which is connected to a given channel, to allow the ability to provide
     * accurate velocity data.
     * These parameters will NOT be retained across power cycles, unless
     * you call {@link #saveParametersToFlash()} ()}
     * @param idx the channel in question
     * @param params minimum/maximum pulse width
     */
    void setSingleChannelPulseWidthParams(int idx, ChannelPulseWidthParams params);

    /**
     * Queries the OctoQuad to determine the currently set minimum/maxiumum pulse
     * width for an encoder channel, to allow sane velocity data.
     * @param idx the channel in question
     * @return minimum/maximum pulse width
     */
    ChannelPulseWidthParams getSingleChannelPulseWidthParams(int idx);

    /**
     * Configure whether in PWM mode, a channel will report the raw PWM length
     * in microseconds, or whether it will perform "wrap tracking" for use with
     * an absolute encoder to turn the absolute position into a continuous value.
     * <p>
     * This is useful if you want your absolute encoder to track position across
     * multiple rotations. NB: in order to get sane data, you MUST set the channel
     * min/max pulse width parameter. Do not assume these values are the same for each
     * encoder, even if they are from the same production run! REV Through Bore encoders
     * have been observed to vary +/- 10uS from the spec'd values. You need to
     * actually test each encoder manually by turning it very slowly until it gets to
     * the wrap around point, and making note of the max/min values that it flips between,
     * and then program those values into the respective channel's min/max pulse width parameter.
     * <p>
     * Note that when using a PWM channel in "wrap tracking" mode, issuing a reset to
     * that channel does NOT clear the position register to zero. Rather, it zeros the
     * "accumulator" (i.e. the number of "wraps") such that immediately after issuing a
     * reset command, the position register will contain the raw pulse width in microseconds.
     * This behavior is chosen because it maintains the ability of the absolute encoder to
     * actually report its absolute position. If resetting the channel were to really zero
     * out the reported position register, using an absolute encoder would not behave any
     * differently than using a relative encoder.
     *
     * @param idx the channel in question
     * @param trackWrap whether to track wrap around
     */
    void setSingleChannelPulseWidthTracksWrap(int idx, boolean trackWrap);

    /**
     * Get whether PWM wrap tracking is enabled for a single channel
     * @param idx the channel in question
     * @return whether PWM wrap tracking is enabled
     */
    boolean getSingleChannelPulseWidthTracksWrap(int idx);

    /**
     * Configure whether in PWM mode, a channel will report the raw PWM length
     * in microseconds, or whether it will perform "wrap tracking" for use with
     * an absolute encoder to turn the absolute position into a continuous value
     * <p>
     * This is useful if you want your absolute encoder to track position across
     * multiple rotations. NB: in order to get sane data, you MUST set the channel
     * min/max pulse width parameter. Do not assume these values are the same for each
     * encoder, even if they are from the same production run! REV Through Bore encoders
     * have been observed to vary +/- 10uS from the spec'd values. You need to
     * actually test each encoder manually by turning it very slowly until it gets to
     * the wrap around point, and making note of the max/min values that it flips between,
     * and then program those values into the respective channel's min/max pulse width parameter.
     * <p>
     * Note that when using a PWM channel in "wrap tracking" mode, issuing a reset to
     * that channel does NOT clear the position register to zero. Rather, it zeros the
     * "accumulator" (i.e. the number of "wraps") such that immediately after issuing a
     * reset command, the position register will contain the raw pulse width in microseconds.
     * This behavior is chosen because it maintains the ability of the absolute encoder to
     * actually report its absolute position. If resetting the channel were to really zero
     * out the reported position register, using an absolute encoder would not behave any
     * differently than using a relative encoder.
     *
     * @param trackWrap whether to track wrap around
     */
    void setAllChannelsPulseWidthTracksWrap(boolean[] trackWrap);

    // ---------------------------------------------------------------------------------------------
    // ABSOLUTE LOCALIZER API
    // ---------------------------------------------------------------------------------------------

    /**
     * A data block holding all localizer data needed for navigation; this block may be bulk
     * read in one I2C read operation.
     */
    class LocalizerDataBlock
    {
        public LocalizerStatus localizerStatus;
        public boolean crcOk;
        public float heading_rad;
        public short posX_mm;
        public short posY_mm;
        public short velX_mmS;
        public short velY_mmS;
        public float velHeading_radS;

        /**
         * Check whether it is likely that this data is valid. The localizer status is read
         * along with the data, and if the status is not RUNNING, then the data invalid.
         * Additionally, if the CRC on the returned is bad, (e.g. if there is an I2C bus stall
         * or bit flip), you could avoid acting on that corrupted data.
         * @return whether it is likely that this data is valid
         */
        public boolean isDataValid()
        {
            return crcOk && localizerStatus == LocalizerStatus.RUNNING;
        }
    }

    /**
     * Enum representing the status of the absolute localizer algorithm
     * You can poll this to determine when IMU calibration has finished.
     */
    enum LocalizerStatus
    {
        INVALID(0),
        NOT_INITIALIZED(1),
        WARMING_UP_IMU(2),
        CALIBRATING_IMU(3),
        RUNNING(4),
        FAULT_NO_IMU(5);

        final int code;

        LocalizerStatus(int code)
        {
            this.code = code;
        }
    }

    /**
     * The absolute localizer automagically selects the appropriate axis on the IMU to use
     * for yaw, regardless of physical mounting orientation. (However, you must mount in one
     * of the 6 axis-orthogonal orientations). You can poll this status to determine which
     * axis was chosen.
     */
    enum LocalizerYawAxis
    {
        UNDECIDED(0),
        X(1),
        X_INV(2),
        Y(3),
        Y_INV(4),
        Z(5),
        Z_INV(6);

        final int code;

        LocalizerYawAxis(int code)
        {
            this.code = code;
        }
    }

    /**
     * Set the port index to be used by the absolute localizer routine for measuring X movement
     * @param port the port index to be used by the absolute localizer routine for measuring X movement
     */
    void setLocalizerPortX(int port);

    /**
     * Set the port index to be used by the absolute localizer routine for measuring Y movement
     * @param port the port index to be used by the absolute localizer routine for measuring Y movement
     */
    void setLocalizerPortY(int port);

    /**
     * Set the scalar for converting encoder counts on the X port to millimeters of travel
     * You SHOULD NOT calculate this - this is a real world not a theoretical one - you should
     * measure this by pushing your robot N meters and dividing the counts by (N*1000)
     * @param ticksPerMM_x scalar for converting encoder counts on the X port to millimeters of travel
     */
    void setLocalizerCountsPerMM_X(float ticksPerMM_x);

    /**
     * Set the scalar for converting encoder counts on the Y port to millimeters of travel
     * You SHOULD NOT calculate this - this is a real world not a theoretical one - you should
     * measure this by pushing your robot N meters and dividing the counts by (N*1000)
     * @param ticksPerMM_y scalar for converting encoder counts on the X port to millimeters of travel
     */
    void setLocalizerCountsPerMM_Y(float ticksPerMM_y);

    /**
     * The real TCP (Tracking Center Point) of your robot is the point on the robot, where,
     * if the robot rotates about that point, neither the X nor Y tracking wheels will rotate.
     * This point can be determined by drawing imaginary lines parallel to, and through the middle
     * of, your tracking wheels, and finding where those lines intersect.
     * <p>
     * Without setting any TCP offset, if the robot rotates, the reported XY vales from the localizer
     * will change during the rotation, unless the rotation is about the real TCP. Most users will
     * find this behavior rather unhelpful, since usually a robot will robot about its geometric center,
     * and not about the real TCP.
     * <p>
     * You can move the location of the localize's "virtual" TCP away from the true location (e.g. to
     * the center of your robot) by applying an offset. This value does not need to be extremely accurate
     * since it will not affect the accuracy or repeatability of the localizer algorithm.
     * @param tcpOffsetMM_X offset to move the virtual TCP
     */
    void setLocalizerTcpOffsetMM_X(float tcpOffsetMM_X);

    /**
     * The real TCP (Tracking Center Point) of your robot is the point on the robot, where,
     * if the robot rotates about that point, neither the X nor Y tracking wheels will rotate.
     * This point can be determined by drawing imaginary lines parallel to, and through the middle
     * of, your tracking wheels, and finding where those lines intersect.
     * <p>
     * Without setting any TCP offset, if the robot rotates, the reported XY vales from the localizer
     * will change during the rotation, unless the rotation is about the real TCP. Most users will
     * find this behavior rather unhelpful, since usually a robot will robot about its geometric center,
     * and not about the real TCP.
     * <p>
     * You can move the location of the localize's "virtual" TCP away from the true location (e.g. to
     * the center of your robot) by applying an offset. This value does not need to be extremely accurate
     * since it will not affect the accuracy or repeatability of the localizer algorithm.
     * @param tcpOffsetMM_Y offset to move the virtual TCP
     */
    void setLocalizerTcpOffsetMM_Y(float tcpOffsetMM_Y);

    /**
     * Set a scale factor to apply to the IMU heading to improve accuracy
     * The recommended way to tune this is to set the scalar to 1.0, place the robot against a hard
     * surface, rotate the robot 10 times (3600 degrees) and see how far off the reported heading is
     * from what it should be.
     * @param headingScalar scale factor to apply to the IMU heading to improve accuracy
     */
    void setLocalizerImuHeadingScalar(float headingScalar);

    /**
     * Set the period of translational velocity calculation.
     * Longer periods give higher resolution with more latency,
     * shorter periods give lower resolution with less latency.
     * @param ms 1-255ms
     */
    void setLocalizerVelocityIntervalMS(int ms);

    /**
     * Set all localizer parameters with one function call
     * <p>
     * NOTE: this will not take effect until a call to {@link #resetLocalizerAndCalibrateIMU()}
     * @param portX see desc. for specific function call
     * @param portY see desc. for specific function call
     * @param ticksPerMM_x see desc. for specific function call
     * @param ticksPerMM_y see desc. for specific function call
     * @param tcpOffsetMM_X see desc. for specific function call
     * @param tcpOffsetMM_Y see desc. for specific function call
     * @param headingScalar see desc. for specific function call
     * @param velocityIntervalMs see desc. for specific function call
     */
    void setAllLocalizerParameters(
            int portX,
            int portY,
            float ticksPerMM_x,
            float ticksPerMM_y,
            float tcpOffsetMM_X,
            float tcpOffsetMM_Y,
            float headingScalar,
            int velocityIntervalMs
    );

    /**
     * Bulk read all localizer data in one operation for maximum efficiency, writing the data into
     * an existing {@link LocalizerDataBlock} object. The previous values are destroyed.
     * @param out the {@link LocalizerDataBlock} object to fill with new data
     */
    void readLocalizerData(LocalizerDataBlock out);

    /**
     * Bulk read all localizer data in one operation for maximum efficiency
     * @return newly read localizer data
     */
    LocalizerDataBlock readLocalizerData();

    /**
     * Bulk read all localizer data and encoder data in one operation for maximum efficiency,
     * writing the data into existing objects. The previous values are destroyed.
     * @param localizerOut the {@link LocalizerDataBlock} object to fill with new localizer data
     * @param encoderOut the {@link EncoderDataBlock} object to fill with new encoder data
     */
    void readLocalizerDataAndAllEncoderData(LocalizerDataBlock localizerOut, EncoderDataBlock encoderOut);

    /**
     * "Teleport" the localizer to a new location. This may be useful, for instance, for updating
     * your position based on vision targeting.
     * @param posX_mm x position in millimeters
     * @param posY_mm y position in millimeters
     * @param heading_rad heading in radians
     */
    void setLocalizerPose(int posX_mm, int posY_mm, float heading_rad);

    /**
     * Teleport the localizer heading to a new orientation. This may be useful,
     * for instance, for updating heading based on vision targeting.
     * @param heading_rad heading in radians
     */
    void setLocalizerHeading(float heading_rad);

    /**
     * Get the current status of the localizer algorithm
     * @return current status of the localizer algorithm
     */
    LocalizerStatus getLocalizerStatus();

    /**
     * Query which IMU axis the localizer decided to use for heading
     * @return which IMU axis the localizer decided to use for heading
     */
    LocalizerYawAxis getLocalizerHeadingAxisChoice();

    /**
     * Reset the localizer pose to (0,0,0) and recalibrate the IMU.
     * Poll {@link #getLocalizerStatus()} for {@link LocalizerStatus#RUNNING}
     * to determine when the reset is complete.
     */
    void resetLocalizerAndCalibrateIMU();

    // --------------------------------------------------------------------------------------------------------------------------------
    // MISC. APIs
    //---------------------------------------------------------------------------------------------------------------------------------

    enum I2cRecoveryMode
    {
        /**
         * Does not perform any active attempts to recover a wedged I2C bus
         */
        NONE(0),

        /**
         * The OctoQuad will reset its I2C peripheral if 50ms elapses between
         * byte transmissions or between bytes and start/stop conditions
         */
        MODE_1_PERIPH_RST_ON_FRAME_ERR(1),

        /**
         * Mode 1 actions + the OctoQuad will toggle the clock line briefly,
         * once, after 1500ms of no communications.
         */
        MODE_2_M1_PLUS_SCL_IDLE_ONESHOT_TGL(2);

        final byte bVal;

        I2cRecoveryMode(int bVal)
        {
            this.bVal = (byte) bVal;
        }
    }

    /**
     * Configures the OctoQuad to use the specified I2C recovery mode.
     * This parameter will NOT be retained across power cycles, unless
     * you call {@link #saveParametersToFlash()} ()}
     * @param mode the recovery mode to use
     */
    void setI2cRecoveryMode(I2cRecoveryMode mode);

    /**
     * Queries the OctoQuad to determine the currently configured I2C recovery mode
     * @return the currently configured I2C recovery mode
     */
    I2cRecoveryMode getI2cRecoveryMode();

    /**
     * Stores the current state of parameters to flash, to be applied at next boot
     */
    void saveParametersToFlash();

    /**
     * Run the firmware's internal reset routine
     */
    void resetEverything();
}

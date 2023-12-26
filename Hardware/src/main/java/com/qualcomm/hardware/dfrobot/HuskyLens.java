/*
Copyright (c) 2023 FIRST

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of FIRST nor the names of its contributors may be used to
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
package com.qualcomm.hardware.dfrobot;


import com.qualcomm.hardware.R;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.TypeConversion;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.qualcomm.robotcore.util.TypeConversion.byteArrayToShort;
import static com.qualcomm.robotcore.util.Util.concatenateByteArrays;

/*
 * Driver for HuskyLens Vision Sensor
 *
 * This device communicates using I2C in a command/response fashion, not the
 * typical register read method.  Responses may be variable in length and are not
 * preceded by a register write indicating which portion of the device to read.
 * Instead implementations issue a command, and the device responds with a sequence
 * of bytes.  Implementations are expected to read length bytes to know when to
 * finish reading off the i2c bus.
 *
 * For more information on the protocol see:
 *     https://github.com/HuskyLens/HUSKYLENSArduino/blob/master/HUSKYLENS%20Protocol.md
 *
 * NOTE: The Expansion Hub has a bug in the read N bytes operation that causes it to
 *       swallow an extra byte.  Tell it to read 8 bytes and it reads 9 bytes but
 *       returns the 8 you asked for.  The 9th byte goes to /dev/null and is never seen
 *       by the SDK.  If you have 16 bytes to read, and read them in two operations of
 *       8 bytes each, then you'll only ever see 15 bytes, the first byte in the second
 *       operation having been consumed, and never returned, by the first operation.
 *
 * In order to work around this bug in a sane fashion, this driver implements a subset of
 * the complete command set.  Since firmware always reads an extra byte, if we can ensure
 * that the extra byte is always at the end of the total sequence of bytes we want to read,
 * then the device should just nack that extra byte, the firmware ignores the nack and
 * everyone is happy.  The maximum number of bytes that can be read on an i2c bus in a
 * single transaction is firmware limited to 100.  Therefore HuskyLens commands that have
 * responses that are potentially greater than 100 bytes need to be capped to ensure they
 * do not overflow 100 bytes.
 *
 * To make this manageable, you can ask for Blocks or Arrows, but not both.  When
 * asking for Blocks, we cap the number of blocks at 6.  Similarly for Arrows, they are
 * capped at 6.  Assuming they come back in ID order.
 *
 * If you need a Block or Arrow whose ID is greater than the maximum ID returned by the
 * grouped command, then you may use the individual block(int id) method to return just
 * the blocks with that ID.  Still capped at 6, but for FTC game purposes that should be
 * sufficient.
 */


/**
 * Driver for HuskyLens Vision Sensor.
 *
 * {@link HuskyLens} provides support for the DFRobot HuskyLens Vision Sensor.
 *
 * @see <a href="https://wiki.dfrobot.com/HUSKYLENS_V1.0_SKU_SEN0305_SEN0336">HuskyLens</a>
 *
 * Limitations: Both Block and Arrow array sizes are capped at 6 elements.  The
 *   call to blocks() will return all Blocks irrespective of ID, capped at 6.  The
 *   call to blocks(int id) will return all Blocks with the given id, capped at 6.
 *   The latter may be useful if you have too many returned objects.  Similarly with
 *   arrows().
 */
@I2cDeviceType
@DeviceProperties(name = "@string/dfrobot_huskylens_name", description = "@string/dfrobot_huskylens_description", xmlTag = "HuskyLens")
public class HuskyLens extends I2cDeviceSynchDevice<I2cDeviceSynch> {

    private final String TAG = "HuskyLens";

    private final byte CMD_REQUEST_KNOCK = 0x2C;
    private final byte CMD_REQUEST_ALL = 0x20;       // unimplemented
    private final byte CMD_REQUEST_BLOCKS = 0x21;
    private final byte CMD_REQUEST_BLOCK_BY_ID = 0x27;
    private final byte CMD_REQUEST_ARROWS = 0x22;
    private final byte CMD_REQUEST_ARROW_BY_ID = 0x28;
    private final byte CMD_REQUEST_ALGORITHM = 0x2D;

    private final byte LEARNED = 0x23;               // unimplemented
    private final byte BLOCKS_LEARNED = 0x24;
    private final byte ARROWS_LEARNED = 0x25;
    private final byte BY_ID = 0x26;
    private final byte BLOCKS_BY_ID = 0x27;
    private final byte ARROWS_BY_ID = 0x28;

    private final byte CMD_RESP_OK = 0x2E;
    private final byte CMD_RESP_INFO = 0x29;
    private final byte CMD_RESP_BLOCK = 0x2A;
    private final byte CMD_RESP_ARROW = 0x2B;

    private final byte FACE_RECOGNITION = 0x00;
    private final byte OBJECT_TRACKING = 0x01;
    private final byte OBJECT_RECOGNITION = 0x02;
    private final byte LINE_TRACKING = 0x03;
    private final byte COLOR_RECOGNITION = 0x04;
    private final byte TAG_RECOGNITION = 0x05;
    private final byte OBJECT_CLASSIFICATION = 0x06;

    private final byte HEADER_BYTE_1 = 0x55;
    private final byte HEADER_BYTE_2 = (byte)0xAA;
    private final byte PROTOCOL_ADDR = 0x11;

    protected final int DEFAULT_I2C_ADDR = 0x32;

    private final byte CMD_RESP_INFO_LEN = 16;
    private final byte CMD_RESP_BLOCK_LEN = 16;
    private final byte CMD_RESP_ARROW_LEN = 16;
    private final byte CMD_RESP_OK_LEN = 6;

    private final byte MAX_BLOCKS_IN_RESPONSE = 6;
    private final byte MAX_ARROWS_IN_RESPONSE = 6;

    private static final Block[] NO_BLOCKS = {};
    private static final Arrow[] NO_ARROWS = {};

    private static final boolean debug = false;

    /**
     * Algorithms to be used with selectAlgorithm()
     */
    public enum Algorithm {
        FACE_RECOGNITION(0x00),
        OBJECT_TRACKING(0x01),
        OBJECT_RECOGNITION(0x02),
        LINE_TRACKING(0x03),
        COLOR_RECOGNITION(0x04),
        TAG_RECOGNITION(0x05),
        OBJECT_CLASSIFICATION(0x06),

        NONE(0xFF);

        public byte bVal;
        Algorithm(int val) { this.bVal = (byte)val; }
    }

    /**
     * A Block is a fundamental unit of recognition for all recognized kinds except Arrows and describes
     * the recognized entity's location center, width, height, top left corner, and id.
     *
     * Units are pixels, except for the id.  The device's resolution is 320x240.
     */
    public class Block
    {
        public final int x;
        public final int y;
        public final int width;
        public final int height;
        public final int top;
        public final int left;
        public final int id;

        public Block(byte[] buf)
        {
            if (buf == null) throw new IllegalArgumentException();
            if (buf.length < 16) throw new IllegalArgumentException();

            this.x = byteToShort(buf, 5);
            this.y = byteToShort(buf, 7);
            this.width = byteToShort(buf, 9);
            this.height = byteToShort(buf, 11);
            this.id = byteToShort(buf, 13);

            this.top = this.y - (this.height / 2);
            this.left = this.x - (this.width / 2);
        }

        public String toString()
        {
            return "id=" + id + " size: " + width + "x" + height + " position: " + x + "," + y;
        }
    }

    /**
     * An Arrow is returned when the algorithm is set to LINE_TRACKING and represents
     * the direction the line faces.
     *
     * Units are pixels, except for the id.  The device's resolution is 320x240.
     */
    public class Arrow
    {
        public final int x_origin;
        public final int y_origin;
        public final int x_target;
        public final int y_target;
        public final int id;

        public Arrow(byte[] buf)
        {
            if (buf == null) throw new IllegalArgumentException();
            if (buf.length < 16) throw new IllegalArgumentException();

            this.x_origin = byteToShort(buf, 5);
            this.y_origin = byteToShort(buf, 7);
            this.x_target = byteToShort(buf, 9);
            this.y_target = byteToShort(buf, 11);
            this.id = byteToShort(buf, 13);
        }

        public String toString()
        {
            return "id=" + id + " origin:" + x_origin + "," + y_origin + " target:" + x_target + "," + y_target;
        }
    }

    public HuskyLens(I2cDeviceSynch deviceClient)
    {
        super(deviceClient, true);
        this.deviceClient.setI2cAddress(I2cAddr.create7bit(DEFAULT_I2C_ADDR));
        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
    }

    @Override
    public Manufacturer getManufacturer()
    {
        return Manufacturer.DFRobot;
    }

    @Override
    protected synchronized boolean doInitialize()
    {
        /*
         * Not setting a default algorithm here because algorithms are selectable
         * on the device itself and we should not override an algorithm if the
         * user selected one externally.
         */
        return knock();
    }

    @Override
    public String getDeviceName()
    {
        return AppUtil.getDefContext().getString(R.string.dfrobot_huskylens_name);
    }
    
    /**
     * Proof of life check.
     *
     * @return true if the device responds, false otherwise.
     */
    public boolean knock()
    {
        sendCommand(CMD_REQUEST_KNOCK);
        byte[] buf = this.deviceClient.read(CMD_RESP_OK_LEN);
        if (debug) RobotLog.logBytes(TAG, "knock resp", buf, buf.length);
        if (buf == null) {
            RobotLog.ee(TAG, "I2C transaction failed");
            return false;
        } else if (!verifyChecksum(buf)) {
            RobotLog.ee(TAG, "Checksum failed for command KNOCK ");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Select the algorithm that the HuskyLens should use.  This should be called
     * upon startup to ensure that the device is returning what you expect it to return.
     */
    public void selectAlgorithm(Algorithm algorithm)
    {
        sendCommandWithData(CMD_REQUEST_ALGORITHM, algorithm.bVal, (byte)0x00);
    }

    /**
     * Returns an array of blocks or the empty array if no blocks are seen.
     */
    public Block[] blocks()
    {
        sendCommand(CMD_REQUEST_BLOCKS);
        return readBlocksResponse();
    }

    /**
     * Returns an array of blocks with the given id or the empty array if no blocks are seen.
     */
    public Block[] blocks(int id)
    {
        byte highByte = (byte)(id & 0xFF);
        byte lowByte = (byte)((id >> 8) & 0xFF);
        sendCommandWithData(CMD_REQUEST_BLOCK_BY_ID, highByte, lowByte);

        return readBlocksResponse();
    }

    /**
     * Returns an array of arrows or the empty array if no arrows are seen.
     */
    public Arrow[] arrows()
    {
        sendCommand(CMD_REQUEST_ARROWS);
        return readArrowsResponse();
    }

    /**
     * Returns an array of arrows with the given id or the empty array if no arrows are seen.
     */
    public Arrow[] arrows(int id)
    {
        byte highByte = (byte)(id & 0xFF);
        byte lowByte = (byte)((id >> 8) & 0xFF);
        sendCommandWithData(CMD_REQUEST_ARROW_BY_ID, highByte, lowByte);

        return readArrowsResponse();
    }

    protected byte[] readInfo()
    {
        return this.deviceClient.read(0, CMD_RESP_INFO_LEN);
    }

    protected Block[] readBlocksResponse()
    {
        byte[] info = readInfo();

        if (!verifyChecksum(info)) {
            return NO_BLOCKS;
        }

        if (debug) RobotLog.logBytes(TAG, "info resp", info, info.length);
        int count = byteToShort(info, 5);
        if (count == 0) {
            return NO_BLOCKS;
        }
        if (count > MAX_BLOCKS_IN_RESPONSE) {
            count = MAX_BLOCKS_IN_RESPONSE;
        }

        Block[] blocks = new Block[count];
        byte[] block_buf = new byte[CMD_RESP_BLOCK_LEN];

        /*
         * Fix the EH firmware bug.
         *
         * The idea is that instead of trying to work with missing data, given that the
         * way software is structured we also know what byte is missing, simply put that
         * byte back in the buffer where it belongs and pretend that we received a full,
         * complete, and correct message from here on out.
         */
        byte buf[] = this.deviceClient.read((CMD_RESP_BLOCK_LEN * count) - 1);
        byte[] prependByte = {0x55};
        ByteBuffer byteBuf = ByteBuffer.wrap(concatenateByteArrays(prependByte, buf));
        if (debug) RobotLog.logBytes(TAG, "blocks", byteBuf.array(), byteBuf.array().length);

        for (int i = 0; i < count; i++) {
            byteBuf.get(block_buf, 0, CMD_RESP_BLOCK_LEN);
            if (debug) RobotLog.logBytes(TAG, "single block", block_buf, block_buf.length);
            if (!verifyChecksum(block_buf)) {
                RobotLog.ee(TAG, "Checksum failed for block " + i);
                RobotLog.logBytes(TAG, "Checksum failure: ", block_buf, block_buf.length);
                continue;
            }
            blocks[i] = new Block(block_buf);
            RobotLog.ii(TAG, blocks[i].toString());
        }

        return blocks;
    }

    protected Arrow[] readArrowsResponse()
    {
        byte[] info = readInfo();

        if (!verifyChecksum(info)) {
            return NO_ARROWS;
        }

        if (debug) RobotLog.logBytes(TAG, "info resp", info, info.length);
        int count = byteToShort(info, 5);
        if (count == 0) {
            return NO_ARROWS;
        }
        if (count > MAX_ARROWS_IN_RESPONSE) {
            count = MAX_ARROWS_IN_RESPONSE;
        }

        Arrow[] arrows = new Arrow[count];
        byte[] arrow_buf = new byte[CMD_RESP_ARROW_LEN];

        /*
         * Fix the EH firmware bug.  See comment in readBlocksResponse()
         */
        byte buf[] = this.deviceClient.read((CMD_RESP_ARROW_LEN * count) - 1);
        byte[] prependByte = {0x55};
        ByteBuffer byteBuf = ByteBuffer.wrap(concatenateByteArrays(prependByte, buf));
        if (debug) RobotLog.logBytes(TAG, "arrows", byteBuf.array(), byteBuf.array().length);

        for (int i = 0; i < count; i++) {
            byteBuf.get(arrow_buf, 0, CMD_RESP_ARROW_LEN);
            if (debug) RobotLog.logBytes(TAG, "single arrow", arrow_buf, arrow_buf.length);
            if (!verifyChecksum(arrow_buf)) {
                RobotLog.ee(TAG, "Checksum failed for arrow " + i);
                RobotLog.logBytes(TAG, "Checksum failure: ", arrow_buf, arrow_buf.length);
                continue;
            }
            arrows[i] = new Arrow(arrow_buf);
            RobotLog.ii(TAG, arrows[i].toString());
        }

        return arrows;
    }

    /*
     * Send a basic command that takes no parameters
     */
    protected void sendCommand(byte cmd) {
        byte[] tmp = { HEADER_BYTE_1, HEADER_BYTE_2, PROTOCOL_ADDR, 0x00, cmd, 0x00};
        ByteBuffer buf = ByteBuffer.wrap(tmp);
        buf.put(5, calculateChecksum(tmp));

        this.deviceClient.write(buf.array());
    }

    protected void sendCommandWithData(byte cmd, byte d1, byte d2) {
        byte[] tmp = { HEADER_BYTE_1, HEADER_BYTE_2, PROTOCOL_ADDR, 0x02, cmd, d1, d2, 0x00};
        ByteBuffer buf = ByteBuffer.wrap(tmp);
        buf.put(7, calculateChecksum(tmp));

        this.deviceClient.write(buf.array());
    }

    private int byteToShort(byte[] m, int i)
    {
        if ((i + 1) > m.length) {
            throw new IllegalArgumentException();
        }
        return TypeConversion.unsignedShortToInt(TypeConversion.byteArrayToShort(m, i, ByteOrder.LITTLE_ENDIAN));
    }

    private byte calculateChecksum(byte[] buf)
    {
        int checksum = 0;

        for (int i = 0; i < buf.length - 1; i++) {
            checksum += (buf[i] & 0xff);
        }

        return (byte)checksum;
    }

    private boolean verifyChecksum(byte[] buf)
    {
        byte sum = calculateChecksum(buf);
        if (sum == buf[buf.length - 1]) {
            return true;
        } else {
            return true;
        }
    }
}

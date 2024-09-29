package com.qualcomm.hardware.sparkfun;

import android.graphics.Color;
import androidx.annotation.ColorInt;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;

/**
 * Support for the <A HREF="https://www.sparkfun.com/products/18354">Sparkfun QWIIC LED Stick</A>
 *   To connect it directly, you need this <A HREF="https://www.sparkfun.com/products/25596">cable</A>
 */
@I2cDeviceType()
@DeviceProperties(name = "@string/sparkfun_led_stick_name",
                  description = "@string/sparkfun_led_stick_description",
                  xmlTag = "QWIIC_LED_STICK")
public class SparkFunLEDStick extends I2cDeviceSynchDevice<I2cDeviceSynchSimple> {
    private final static boolean LIMIT_TO_ONE_STICK = true;
    private final static int MAX_SEGMENT_LENGTH = 12;
    private enum Commands {
        CHANGE_LED_LENGTH(0x70),
        WRITE_SINGLE_LED_COLOR(0x71),
        WRITE_ALL_LED_COLOR(0x72),
        WRITE_RED_ARRAY(0x73),
        WRITE_GREEN_ARRAY(0x74),
        WRITE_BLUE_ARRAY(0x75),
        WRITE_SINGLE_LED_BRIGHTNESS(0x76),
        WRITE_ALL_LED_BRIGHTNESS(0x77),
        WRITE_ALL_LED_OFF(0x78);
        final int bVal;

        Commands(int bVal) {
            this.bVal = bVal;
        }
    }

    /**
     * Change the color of a specific LED
     *
     * @param position which LED to change (1 - 255)
     * @param color    what color to set it to
     */
    public void setColor(int position, @ColorInt int color) {
        byte[] data = new byte[4];
        data[0] = (byte) position;
        data[1] = (byte) Color.red(color);
        data[2] = (byte) Color.green(color);
        data[3] = (byte) Color.blue(color);
        writeI2C(Commands.WRITE_SINGLE_LED_COLOR, data);
    }

    /**
     * Change the color of all LEDs to a single color
     *
     * @param color what the color should be
     */
    public void setColor(@ColorInt int color) {
        byte[] data = new byte[3];
        data[0] = (byte) Color.red(color);
        data[1] = (byte) Color.green(color);
        data[2] = (byte) Color.blue(color);
        writeI2C(Commands.WRITE_ALL_LED_COLOR, data);
    }

    /**
     * Send a segment of the LED array
     *
     * @param cmd    command to send
     * @param array  the values (limited from 0..255)
     * @param offset the starting value (LED only, array starts at 0)
     * @param length the length to send
     */
    private void sendSegment(Commands cmd, int[] array, int offset, int length) {
        byte[] data = new byte[length + 2];
        data[0] = (byte) length;
        data[1] = (byte) offset;

        for (int i = 0; i < length; i++) {
            data[2 + i] = (byte) array[i];
        }
        writeI2C(cmd, data);
    }

    /**
     * Change the color of an LED color segment
     *
     * @param colors what the colors should be
     * @param offset where in the array to start
     * @param length length to send (limited to 12)
     */
    private void setLEDColorSegment(@ColorInt int[] colors, int offset, int length) {
        // Copy a segment of elements from the colors array into separate arrays for red, green, and blue.
        int[] redArray = new int[length];
        int[] greenArray = new int[length];
        int[] blueArray = new int[length];

        // Here we iterate [0, length) because we are filling the red, green, and blue arrays.
        for (int i = 0; i < length; i++) {
            // Use the offset when indexing into the colors array so we get the appropriate segement of elements.
            redArray[i] = Color.red(colors[i + offset]);
            greenArray[i] = Color.green(colors[i + offset]);
            blueArray[i] = Color.blue(colors[i + offset]);
        }
        sendSegment(Commands.WRITE_RED_ARRAY, redArray, offset, length);
        sendSegment(Commands.WRITE_GREEN_ARRAY, greenArray, offset, length);
        sendSegment(Commands.WRITE_BLUE_ARRAY, blueArray, offset, length);
    }

    /**
     * Change the color of all LEDs using arrays
     *
     * @param colors array of colors to set lights to
     */
    public void setColors(@ColorInt int[] colors) {
        int length = colors.length;

        if (LIMIT_TO_ONE_STICK && length > 10) {
            length = 10;
        }

        int numInLastSegment = length % MAX_SEGMENT_LENGTH;
        int numSegments = length / MAX_SEGMENT_LENGTH;
        for (int i = 0; i < numSegments; i++) {
            setLEDColorSegment(colors, i * MAX_SEGMENT_LENGTH, MAX_SEGMENT_LENGTH);
        }
        if (numInLastSegment > 0) {
          setLEDColorSegment(colors, numSegments * MAX_SEGMENT_LENGTH, numInLastSegment);
        }
    }

    /**
     * Set the brightness of an individual LED
     *
     * @param position     which LED to change (1-255)
     * @param brightness brightness level (0-31)
     */
    public void setBrightness(int position, int brightness) {
        byte[] data = new byte[2];
        data[0] = (byte) position;
        data[1] = (byte) brightness;
        writeI2C(Commands.WRITE_SINGLE_LED_BRIGHTNESS, data);
    }

    /**
     * Set the brightness of all LEDs
     *
     * @param brightness brightness level (0-31)
     */
    public void setBrightness(int brightness) {
        byte[] data = new byte[1];
        data[0] = (byte) brightness;
        writeI2C(Commands.WRITE_ALL_LED_BRIGHTNESS, data);
    }

    /**
     * Turn all LEDS off...
     */
    public void turnAllOff() {
        setColor(0);
    }

    private void writeI2C(Commands cmd, byte[] data) {
        deviceClient.write(cmd.bVal, data);
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.SparkFun;
    }

    @Override
    protected synchronized boolean doInitialize() {
        return true;
    }

    @Override
    public String getDeviceName() {
        return "SparkFun Qwiic LED Strip";
    }

    private final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x23);

    public SparkFunLEDStick(I2cDeviceSynchSimple deviceClient, boolean deviceClientIsOwned) {
        super(deviceClient, deviceClientIsOwned);

        deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);
        super.registerArmingStateCallback(false);
    }

}

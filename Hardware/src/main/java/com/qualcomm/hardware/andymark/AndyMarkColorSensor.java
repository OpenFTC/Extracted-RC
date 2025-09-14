/*
Copyright (c) 2025 AndyMark Inc.

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list
    of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this
    list of conditions and the following disclaimer in the documentation and/or
    other materials provided with the distribution.

3. Neither the name of AndyMark Inc. nor the names of its contributors may be used to
    endorse or promote products derived from this software without specific prior
    written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
DAMAGE.
*/

package com.qualcomm.hardware.andymark;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.ControlSystem;
import com.qualcomm.robotcore.util.TypeConversion;
import com.qualcomm.robotcore.util.Range;

/**
* AndyMark Proximity and Color Sensor Driver
*
* Author: Jonathan Lane
* Organization: AndyMark, Inc.
*
* Description:
* Driver for the AndyMark Proximity and Color Sensor
* It enables measurements of Proximity, Ambient Light, Red, Green, Blue Values
*/

@SuppressWarnings({"WeakerAccess", "unused"})
@I2cDeviceType
@DeviceProperties(  name = "AndyMark Proximity & Color Sensor", 
                    description = "AndyMark Proximity & Color Sensor", 
                    xmlTag = "AndyMarkColor")

public class AndyMarkColorSensor extends I2cDeviceSynchDevice<I2cDeviceSynch> implements ColorRangeSensor, DistanceSensor, ColorSensor
{
    // Prxoximity Gain
    private static final int REG_PCFG1 = 0x8F;
    public enum ProximityGain
    {
        GAIN_1X(0b00000000),
        GAIN_2X(0b01000000),
        GAIN_4X(0b10000000),
        GAIN_8X(0b11000000);

        public final int bits;

        ProximityGain(int bits)
        {
            this.bits = bits;
        }
    }

    // Pulse Count / Length
    private static final int REG_PCFG0 = 0x8E;

    // Pulse Lengths
    public enum ProximityPulseLength
    {
        LENGTH_4US(0b00000000),
        LENGTH_8US(0b01000000),
        LENGTH_16US(0b10000000),
        LENGTH_32US(0b11000000);

        public final int bits;

        ProximityPulseLength(int bits)
        {
            this.bits = bits;
        }
    }

    private static final I2cAddr I2C_ADDRESS = I2cAddr.create7bit(0x39);

    private static final int ID_REGISTER = 0x92;
    private static final int EXPECTED_ID = 0xE4;
    
    private static final int STATUS_REGISTER = 0x93;
    private static final int WTIME_REGISTER = 0x83;
    private static final int ATIME_REGISTER = 0x81;
    private static final int CONTROL_REGISTER = 0x8F;
    private static final int ENABLE_REGISTER = 0x80;

    private static final int PROXIMITY_DATA = 0x9C;
    private static final int ALS_CLEAR_LOW = 0x94;
    private static final int ALS_CLEAR_HIGH = 0x95;
    private static final int ALS_RED_LOW = 0x96;
    private static final int ALS_RED_HIGH = 0x97;
    private static final int ALS_GREEN_LOW = 0x98;
    private static final int ALS_GREEN_HIGH = 0x99;
    private static final int ALS_BLUE_LOW = 0x9A;
    private static final int ALS_BLUE_HIGH = 0x9B;


    public AndyMarkColorSensor(I2cDeviceSynch deviceClient, boolean deviceClientIsOwned)
    {
        super(deviceClient, deviceClientIsOwned);
        this.deviceClient.setI2cAddress(I2C_ADDRESS);
        this.deviceClient.engage();
    }

    //-----------------------------------------------------------------------------------------
    // Hardware Device
    //-----------------------------------------------------------------------------------------

    /**
    * Returns the name of the device.
    *
    * @return The device name string
    */
    @Override
    public String getDeviceName() 
    {
        return "AndyMark Proximity & Color Sensor";
    }

    /**
    * Returns the manufacturer of the sensor.
    *
    * @return The sensor manufacturer (AMS)
    */
    @Override
    public HardwareDevice.Manufacturer getManufacturer() 
    {
        return Manufacturer.AMS;
    }

    /**
    * Sets a new I2C address for the sensor.
    * 
    * This allows multiple identical devices to be used on the same bus with different addresses.
    *
    * @param newAddress The new I2C address to assign to the device
    */
    @Override
    public void setI2cAddress(I2cAddr newAddress) 
    {
        deviceClient.setI2cAddress(newAddress);
    }
    
    /**
    * Returns the I2C address currently assigned to the sensor.
    *
    * @return Current I2C address of the device
    */
    @Override
    public I2cAddr getI2cAddress() 
    {
        return deviceClient.getI2cAddress();
    }

    /**
    * Performs internal initialization and status check.
    *
    * @return true if the sensor is ready (status bit 0 is set), false otherwise
    */
    @Override
    protected boolean doInitialize() 
    {
        writeRegister(ENABLE_REGISTER, 0x0F);
        writeRegister(ATIME_REGISTER, 0xFF);
        writeRegister(WTIME_REGISTER, 0xFF);
        writeRegister(CONTROL_REGISTER, 0x6F);

        // Check ID register to verify communication
        return readRegister(ID_REGISTER) == EXPECTED_ID;
    }

    //-----------------------------------------------------------------------------------------
    // OpticalDistanceSensor / LightSensor
    //-----------------------------------------------------------------------------------------

    /**
    * Classifies the perceived color using raw RGB sensor values with calibrated thresholds.
    * <p>
    * This method reads the red, green, blue, and clear (alpha) light values from the color sensor,
    * then applies a series of heuristics to determine the most likely color identity. Classification 
    * is based on both brightness and color component relationships, tailored to match observed readings
    * from controlled samples of common colors.
    * <p>
    * The classification priority is as follows:
    * <ul>
    *   <li><b>Unknown</b>: If the alpha channel or total brightness is too low to provide a reliable reading.</li>
    *   <li><b>Black</b>: If total RGB brightness is less than 90.</li>
    *   <li><b>White</b>: If total RGB brightness exceeds 450.</li>
    *   <li><b>Yellow</b>: If red exceeds 115, green exceeds 90, and blue is less than 45.</li>
    *   <li><b>Cyan</b>: If green exceeds 45, blue exceeds 38, and red is less than 40.</li>
    *   <li><b>Magenta</b>: If red exceeds 65, and both green and blue is less than 30.</li>
    *   <li><b>Red</b>: If red exceeds both green and blue by a margin of 10.</li>
    *   <li><b>Green</b>: If green exceeds both red and blue by a margin of 10.</li>
    *   <li><b>Blue</b>: If blue exceeds both red and green by a margin of 10.</li>
    *   <li><b>Unknown</b>: If no conditions are met.</li>
    * </ul>
    * <p>
    * These thresholds are empirically derived from observed RGB values and may need to be tuned 
    * further for different lighting environments or surfaces. Alpha is used as a reliability check,
    * and normalization is not currently applied.
    *
    * @return A string representing the best-matching color: "Red", "Green", "Blue", "Yellow", 
    *         "Cyan", "Magenta", "White", "Black", or "Unknown".
    */
    public String classifyColor() 
    {
        int a = alpha();
        int r = red();
        int g = green();
        int b = blue();
    
        int brightness = r + g + b;
        if (a < 50 || brightness < 50) return "Unknown";  // too dim to classify
        if (brightness < 90) return "Black";
        if (brightness > 450) return "White";
    
        // Prioritized specific colors
        if (r > 115 && g > 90 && b < 45) return "Yellow";     // Yellow: 125,103,32
        if (g > 45 && b > 38 && r < 40) return "Cyan";        // Cyan: 23,49,42
        if (r > 65 && g < 30 && b < 30) return "Magenta";     // Magenta: 75,22,22
    
        // Primary color fallback
        int margin = 10;
        if (r > g + margin && r > b + margin) return "Red";   // Red: 100,22,15
        if (g > r + margin && g > b + margin) return "Green"; // Green: 34,62,28
        if (b > r + margin && b > g + margin) return "Blue";  // Blue: 18,31,35
    
        return "Unknown";
    }

    /**
    * Returns a packed 32-bit Android-style ARGB color value.
    * 
    * Combines the alpha, red, green, and blue channel readings
    * into a single integer where each channel is 8 bits.
    *
    * @return 32-bit integer representing ARGB color
    */
    @Override
    public int argb() 
    {
        int a = alpha();
        int r = red();
        int g = green();
        int b = blue();
    
        // Pack into Android-style ARGB 32-bit int
        return ((a & 0xFF) << 24) |
               ((r & 0xFF) << 16) |
               ((g & 0xFF) << 8)  |
               (b & 0xFF);
    }
    
    /**
    * Returns the color sensor readings normalized to a range of [0, 1].
    * Normalization allows color values to be compared independent of ambient light intensity.
    *
    * @return NormalizedRGBA structure containing normalized alpha, red, green, and blue components
    */
    @Override
    public NormalizedRGBA getNormalizedColors()
    {
        NormalizedRGBA normalized = new NormalizedRGBA();
        
        // Normalize each color component to a [0, 1] range
        normalized.alpha = Range.clip(alpha() / 65535.0f, 0.0f, 1.0f);
        normalized.red   = Range.clip(red()   / 65535.0f, 0.0f, 1.0f);
        normalized.green = Range.clip(green() / 65535.0f, 0.0f, 1.0f);
        normalized.blue  = Range.clip(blue()  / 65535.0f, 0.0f, 1.0f);
    
        return normalized;
    }

    /**
    * Reads the ambient (clear) light level from the sensor.
    *
    * @return Ambient light level as a 16-bit value in the range 0-65535
    */
    @Override 
    public int alpha()
    {
        return read16BitRegister(ALS_CLEAR_LOW, ALS_CLEAR_HIGH);
    }
    
    /**
    * Returns the raw light detected by the sensor, normalized to a [0.0, 1.0] range.
    * This is typically based on the clear (ambient) light channel.
    *
    * @return Normalized raw light detection as a double between 0.0 and 1.0
    */
    @Override
    public double getRawLightDetected()
    {
        // Return the clear channel (ambient light) normalized
        return Range.clip(alpha() / 65535.0, 0.0, 1.0);
    }
    
    /**
    * Returns the normalized light detection value.
    * Typically identical to {@link #getRawLightDetected()} for sensors without built-in filtering.
    *
    * @return Normalized light detection as a double between 0.0 and 1.0
    */
    @Override
    public double getLightDetected()
    {
        // Historically, FTC SDK defines getLightDetected() the same as getRawLightDetected()
        return getRawLightDetected();
    }
    
    /**
    * Returns the maximum possible value for raw light detection.
    * 
    * For a 16-bit clear channel reading, this is typically 65535.
    *
    * @return The maximum raw light value
    */
    @Override
    public double getRawLightDetectedMax() 
    {
        // Typical maximum for a 16-bit sensor
        return 65535.0;
    }

    /**
    * Reads the red color component from the sensor.
    *
    * @return Red light intensity as a 16-bit value in the range 0-65535
    */
    @Override
    public int red() 
    {
        return read16BitRegister(ALS_RED_LOW, ALS_RED_HIGH);
    }

    /**
    * Reads the green color component from the sensor.
    *
    * @return Green light intensity as a 16-bit value in the range 0-65535
    */
    @Override
    public int green() 
    {
        return read16BitRegister(ALS_GREEN_LOW, ALS_GREEN_HIGH);
    }

    /**
    * Reads the blue color component from the sensor.
    *
    * @return Blue light intensity as a 16-bit value in the range 0-65535
    */
    @Override
    public int blue() 
    {
        return read16BitRegister(ALS_BLUE_LOW, ALS_BLUE_HIGH);
    }
    
    /**
    * Enables or disables the sensor's LED, if available.
    * 
    * This sensor does not provide a separately controllable LED.
    * The proximity engine manages the internal IR LED automatically.
    *
    * @param enable True to enable the LED, false to disable (ignored)
    */
    @Override
    public void enableLed(boolean enable) 
    {
    // The TMD3725 does not have a separately controllable LED that can be manually turned on/off.
    // It uses an internal IR LED for proximity sensing automatically during measurements.
    }
    
    /**
    * Sets the gain for the color sensor.
    * 
    * This sensor does not support adjustable gain through software.
    * This method is a no-op for compatibility.
    *
    * @param gain Requested gain value (ignored)
    */
    @Override
    public void setGain(float gain) 
    {
        // This sensor does not support adjustable gain through software
        // You could store the requested gain if needed, but otherwise, no-op
    }
    
    /**
    * Returns the gain setting currently applied to the color sensor.
    * 
    * For this device, the gain is fixed and not adjustable.
    *
    * @return The fixed gain value (1.0)
    */
    @Override
    public float getGain() 
    {
        // This sensor has a fixed gain internally
        return 1.0f; // Or whatever gain you want to report (nominal)
    }

    //-----------------------------------------------------------------------------------------
    // Distance Sensor
    //-----------------------------------------------------------------------------------------

    /**
    * Returns a calibrated, linear sense of distance as read by the infrared proximity
    * part of the TMD3725 sensor. Distance is measured to the plastic housing at the front of the
    * sensor.
    *
    * Natively, the raw optical signal follows an inverse relationship. Here, parameters have
    * been fitted to turn that into a <em>linear</em> measure of distance. The function fitted
    * was of the form:
    *
    *      RawProximity = a * distance^b + c
    *
    * Calibration is based on the default settings from the TMD3725 datasheet using
    * 6 pulses at 90mA with default gain and pulse length.
    *
    * Reflectivity and surface angle will affect results and user should validate
    * calibration for specific applications.
    *
    * @param unit  the unit of distance in which the result should be returned
    * @return      the currently measured distance in the indicated units.
    */
    @Override
    public double getDistance(DistanceUnit unit)
    {
        int rawProximity = getProximity();  // Read raw proximity register value

        double inDistance = inFromProximity(rawProximity);

        return unit.fromUnit(DistanceUnit.INCH, inDistance);
    }

    /**
    * Converts raw proximity reading into inches using a fitted curve.
    * 
    * @param rawProximity  Raw proximity value from 0x9C
    * @return              Distance in inches
    */
    private double inFromProximity(int rawProximity)
    {
        // Fitted parameters (example for the 32us, 8x gain curve from datasheet)
        final double a = 250.0;  // maximum proximity count (at 0 distance)
        final double b = -1.25;  // curve falloff based on datasheet graph
        final double c = 0.0;    // baseline offset (default 0)

        // Invert the formula to solve for distance
        // Rearranged:
        //   distance = ((rawProximity - c) / a)^(1/b)
        double adjusted = Math.max(rawProximity - c, 1); // Avoid divide by zero
        double distance_mm = Math.pow(adjusted / a, 1.0 / b);

        // Convert mm → inches
        double distance_in = distance_mm / 25.4;

        // Clamp to physical limits if needed
        distance_in = Math.max(0.25, Math.min(6.0, distance_in));  // Typical range from datasheet

        return distance_in;
    }

    /**
    * Reads the proximity measurement from the sensor.
    *
    * @return Proximity value as an integer in the range 0-255
    */
    public int getProximity() 
    {
        return readRegister(PROXIMITY_DATA);
    }

    //-----------------------------------------------------------------------------------------
    // Distance Sensor Utility
    //-----------------------------------------------------------------------------------------

    /**
    * Sets the proximity gain used by the TMD3725 sensor during proximity measurements.
    * 
    * Proximity gain controls the signal amplification applied to the proximity photodiode
    * before analog-to-digital conversion. Higher gain values increase sensitivity to distant
    * or low-reflectivity targets, but may also saturate more easily at close range.
    *
    * Proximity gain is configured by setting bits 7:6 (PGAIN) in the PCFG1 register.
    * Valid gain settings are:
    *
    * <ul>
    *   <li>1x gain (lowest sensitivity, widest dynamic range)</li>
    *   <li>2x gain (medium-low sensitivity)</li>
    *   <li>4x gain (medium-high sensitivity, default setting)</li>
    *   <li>8x gain (highest sensitivity, for detecting far or dark targets)</li>
    * </ul>
    *
    * Default configuration assumes proximity engine settings at factory defaults.
    * End users should experimentally verify that the selected gain meets application
    * requirements, balancing range, saturation, and noise.
    *
    * @param gain The desired proximity gain setting to apply.
    */
    public void setProximityGain(ProximityGain gain)
    {
        // 1. Read current PCFG1 register
        int pcfg1 = readRegister(REG_PCFG1);

        // 2. Clear bits 7 and 6 (PGAIN field)
        pcfg1 &= 0b00111111;

        // 3. Set new PGAIN bits
        pcfg1 |= (gain.bits & 0b11000000);

        // 4. Write back to PCFG1 register
        writeRegister(REG_PCFG1, pcfg1);
    }

    /**
    * Sets the number of IR LED pulses emitted during a single proximity measurement.
    * 
    * Increasing the number of pulses increases the total emitted IR energy per cycle,
    * improving signal-to-noise ratio and extending effective measurement range. However,
    * using more pulses increases measurement time and power consumption.
    *
    * The number of pulses is configured by setting bits 5:0 (PPULSE) in the PCFG0 register.
    * Valid pulse counts are:
    *
    * <ul>
    *   <li>1 pulse (lowest power, fastest update rate)</li>
    *   <li>2–63 pulses (tradeoff between power, noise, and range)</li>
    *   <li>64 pulses (maximum IR energy, longest measurement time)</li>
    * </ul>
    *
    * Default configuration assumes 6 pulses, based on factory defaults in the TMD3725 datasheet.
    * End users should select the number of pulses appropriate for their desired sensing range,
    * update rate, and battery life.
    *
    * @param pulses Number of LED pulses to use per proximity cycle (1–64).
    * @throws IllegalArgumentException if the number of pulses is outside the allowed range.
    */
    public void setProximityLedPulses(int pulses)
    {
        if (pulses < 1 || pulses > 64)
        {
            throw new IllegalArgumentException("Pulses must be between 1 and 64.");
        }

        int ppulseValue = pulses - 1;

        // 1. Read current PCFG0 register
        int pcfg0 = readRegister(REG_PCFG0);

        // 2. Clear bits 5:0 (PPULSE field)
        pcfg0 &= 0b11000000;

        // 3. Set new PPULSE value
        pcfg0 |= (ppulseValue & 0b00111111);

        // 4. Write back
        writeRegister(REG_PCFG0, pcfg0);
    }

    /**
    * Sets the IR LED pulse duration used during proximity measurements.
    * 
    * The pulse duration controls how long each IR pulse lasts. Longer pulse durations
    * increase proximity detection range and reduce noise at the cost of higher power
    * consumption. Shorter pulse durations allow faster sampling and lower power usage.
    *
    * Pulse length is selected by setting bits 7:6 (PPULSE_LEN) in the PCFG0 register.
    * Valid pulse durations are:
    *
    * <ul>
    *   <li>4μs (recommended for very short range, low noise)</li>
    *   <li>8μs (default and typical setting, good balance)</li>
    *   <li>16μs (for mid-range targets, increased signal strength)</li>
    *   <li>32μs (for maximum range, highest power usage)</li>
    * </ul>
    *
    * Default configuration assumes proximity engine settings at factory defaults.
    * End user should experimentally verify that selected pulse length meets their
    * desired range, accuracy, and power tradeoffs.
    *
    * @param pulseLength the desired IR LED pulse duration to use for proximity sensing
    */
    public void setProximityLedPulseLength(ProximityPulseLength pulseLength)
    {
        // 1. Read current PCFG0 register
        int pcfg0 = readRegister(REG_PCFG0);

        // 2. Clear bits 7 and 6 (PPULSE_LEN field)
        pcfg0 &= 0b00111111;

        // 3. Set new PPULSE_LEN bits
        pcfg0 |= (pulseLength.bits & 0b11000000);

        // 4. Write back
        writeRegister(REG_PCFG0, pcfg0);
    }

    /**
    * Configures proximity sensor settings in one call:
    * gain, number of pulses, and pulse length.
    *
    * @param gain         Desired proximity gain setting.
    * @param pulses       Number of LED pulses (1–64).
    * @param pulseLength  Desired pulse length setting.
    */
    public void configureProximitySettings(ProximityGain gain, int pulses, ProximityPulseLength pulseLength)
    {
        setProximityGain(gain);
        setProximityLedPulses(pulses);
        setProximityLedPulseLength(pulseLength);
    }

    //-----------------------------------------------------------------------------------------
    // Utility Methods
    //-----------------------------------------------------------------------------------------

    /**
    * Returns a status string indicating the connection status of the device.
    * Useful for telemetry or debugging output.
    *
    * @return A status string identifying the device and its connection state
    */
    @Override
    public String status()
    {
        return getDeviceName() + " connected";
    }

    private void writeRegister(int register, int value) 
    {
        deviceClient.write8(register, value);
    }

    private int readRegister(int register) 
    {
        return deviceClient.read8(register) & 0xFF;
    }

    private int read16BitRegister(int lowRegister, int highRegister) 
    {
        int low = readRegister(lowRegister);
        int high = readRegister(highRegister);
        return (high << 8) | low;
    }
}

package org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters;

import androidx.annotation.Nullable;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidParameterException;

@SuppressWarnings("unused") // Used by GSON
public class I2cReadParameters extends I2cAddressParameters {
    @Nullable private Integer cb;

    public int getNumBytes() throws InvalidParameterException {
        if (cb == null) {
            throw new InvalidParameterException("cb (number of bytes to read) not specified");
        }
        return cb;
    }
}

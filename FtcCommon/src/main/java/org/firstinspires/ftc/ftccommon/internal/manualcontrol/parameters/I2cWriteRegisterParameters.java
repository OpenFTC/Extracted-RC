package org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters;

import androidx.annotation.Nullable;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidParameterException;

@SuppressWarnings("unused") // Used by GSON
public class I2cWriteRegisterParameters extends I2cWriteParameters {
    @Nullable private Integer r;

    public int getRegister() throws InvalidParameterException {
        if (r == null) {
            throw new InvalidParameterException("r (I2C register) not specified");
        }
        return r;
    }
}

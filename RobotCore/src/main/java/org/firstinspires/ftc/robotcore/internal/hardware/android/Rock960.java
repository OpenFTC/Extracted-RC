/*
Copyright (c) 2018 Noah Andrews

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Noah Andrews nor the names of his contributors may be used to
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
package org.firstinspires.ftc.robotcore.internal.hardware.android;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.RobotLog;

import java.io.File;

/**
 * Rock960 support is incomplete (and the Rock960 will probably never be used in a production
 * Control Hub anyway). Nevertheless, This is here in case we ever do want to support the Rock960.
 *
 * As of 2019-05-15, this implementation is unused by {@link AndroidBoard#getInstance()}.
 */
public class Rock960 extends AndroidBoard {

    private static final String TAG = "Rock960";

    // Don't allow instantiation outside of our package
    protected Rock960() {}

    // GPIO pins
    private static final DigitalChannel ANDROID_BOARD_IS_PRESENT_PIN =
            GpioPin.createOutput(1102, true, GpioPin.Active.LOW, ANDROID_BOARD_IS_PRESENT_PIN_NAME);

    private static final DigitalChannel LYNX_MODULE_RESET_PIN =
            GpioPin.createOutput(1041, false, GpioPin.Active.LOW, LYNX_MODULE_RESET_PIN_NAME);

    private static final DigitalChannel PROGRAMMING_PIN =
            GpioPin.createOutput(1006, false, GpioPin.Active.LOW, PROGRAMMING_PIN_NAME);

    // This pin does not work correctly with the build of Android I have running today (Jun 20, 2018) --Noah
    private static final DigitalChannel USER_BUTTON_PIN = GpioPin.createInput(1100, GpioPin.Active.HIGH, USER_BUTTON_PIN_NAME);

    // UART file
    private static final File UART_FILE = new File("/dev/ttyS3");

    // Public Methods

    @Override @NonNull
    public String getDeviceType() {
        return "Rock960";
    }

    @Override @NonNull
    public DigitalChannel getAndroidBoardIsPresentPin() {
        return ANDROID_BOARD_IS_PRESENT_PIN;
    }

    @Override @NonNull
    public DigitalChannel getProgrammingPin() {
        return PROGRAMMING_PIN;
    }

    @Override @NonNull
    public DigitalChannel getLynxModuleResetPin() {
        return LYNX_MODULE_RESET_PIN;
    }

    @Override @NonNull
    public DigitalChannel getUserButtonPin() {
        return USER_BUTTON_PIN;
    }

    @Override @NonNull
    public DigitalChannel getBhi260ResetPin() {
        return new FakeAndroidBoard.FakeDigitalChannel(DigitalChannel.Mode.OUTPUT);
    }

    @Override @NonNull
    public DigitalChannel getBhi260InterruptPin() {
        return new FakeAndroidBoard.FakeDigitalChannel(DigitalChannel.Mode.INPUT);
    }

    @Override @NonNull
    public DigitalChannel getBhi260Gpio1() {
        return new FakeAndroidBoard.FakeDigitalChannel(DigitalChannel.Mode.OUTPUT);
    }

    @Override @NonNull
    public DigitalChannel getBhi260Gpio5() {
        return new FakeAndroidBoard.FakeDigitalChannel(DigitalChannel.Mode.OUTPUT);
    }

    @Override @NonNull
    public DigitalChannel getBhi260Gpio6() {
        return new FakeAndroidBoard.FakeDigitalChannel(DigitalChannel.Mode.OUTPUT);
    }

    @Override @NonNull
    public DigitalChannel getBhi260Gpio17() {
        return new FakeAndroidBoard.FakeDigitalChannel(DigitalChannel.Mode.OUTPUT);
    }

    @Override @NonNull
    public DigitalChannel getBhi260Gpio18() {
        return new FakeAndroidBoard.FakeDigitalChannel(DigitalChannel.Mode.OUTPUT);
    }

    @Override @NonNull
    public File getUartLocation() {
        return UART_FILE;
    }

    @Override public boolean supports5GhzAp() {
        return true;
    }

    @Override public boolean supports5GhzAutoSelection() {
        return false;
    }

    @Override public boolean supportsBulkNetworkSettings() {
        return false;
    }

    @Override public boolean supportsGetChannelInfoIntent() {
        return false;
    }

    @Override public boolean hasControlHubUpdater() {
        return false;
    }

    @Override public boolean hasRcAppWatchdog() {
        return false;
    }

    @Override @NonNull
    public WifiDataRate getWifiApBeaconRate() {
        return WifiDataRate.UNKNOWN;
    }

    @Override public void setWifiApBeaconRate(WifiDataRate beaconRate) {
        RobotLog.ww(TAG, "Unable to set the Wi-Fi AP beacon rate on a Rock960");
    }
}

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
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.R;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.util.GlobalWarningSource;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.RunShellCommand;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

public class Rev3328 extends AndroidBoard {
    private static final String TAG = "Rev3328";
    private static final int OS_1_1_0_VERSION_CODE = 3;
    private static final int OS_1_1_1_VERSION_CODE = 4;
    private static final int OS_1_1_2_BETA_VERSION_CODE = 5;
    private static final int OS_1_1_2_VERSION_CODE = 6;
    private static final int OS_1_1_3_VERSION_CODE = 7;

    private static final WarningSource warningSource;

    static {
        warningSource = new WarningSource();
        RobotLog.registerGlobalWarningSource(warningSource);
    }

    /**
     * To convert 96boards pin numbers to raw GPIO numbers for the REV3328 board
     *
     *  1. Navigate to the final page of the hardware schematic
     *  2. Find the desired pin on CON8600
     *  3. See what GPIO address it maps to (e.g. GPIO1_C2)
     *  4. Use this formula: X1*32 + X2 + A*8 where:
     *          X1 is the first number (1),
     *          X2 is the second number (2),
     *          A is the zero-indexed numerical representation of the letter (C becomes 2)
     *     So, the raw GPIO number for GPIO1_C2 is 50.
     */

    // GPIO pins
    private static final DigitalChannel ANDROID_BOARD_IS_PRESENT_PIN =
            GpioPin.createOutput(50, true, GpioPin.Active.LOW, ANDROID_BOARD_IS_PRESENT_PIN_NAME);

    private static final DigitalChannel USER_BUTTON_PIN = GpioPin.createInput(51, GpioPin.Active.HIGH, USER_BUTTON_PIN_NAME);

    private static final DigitalChannel PROGRAMMING_PIN =
            GpioPin.createOutput(66, false, GpioPin.Active.LOW, PROGRAMMING_PIN_NAME);

    private static final DigitalChannel LYNX_MODULE_RESET_PIN =
            GpioPin.createOutput(87, false, GpioPin.Active.LOW, LYNX_MODULE_RESET_PIN_NAME);

    // UART file
    private static final File UART_FILE = new File("/dev/ttyS1");

    // Don't allow instantiation outside of our package
    protected Rev3328() { }

    // The BHI260 pins are created on first use, so that we don't warn the user that their Control
    // Hub OS is too old unless the pins are actually accessed. Only access from synchronized
    // methods.
    @Nullable private DigitalChannel bhi260ResetPin;
    @Nullable private DigitalChannel bhi260InterruptPin;
    @Nullable private DigitalChannel bhi260Gpio1Pin;
    @Nullable private DigitalChannel bhi260Gpio5Pin;
    @Nullable private DigitalChannel bhi260Gpio6Pin;
    @Nullable private DigitalChannel bhi260Gpio17Pin;
    @Nullable private DigitalChannel bhi260Gpio18Pin;

    // Public Methods

    @Override @NonNull
    public String getDeviceType() {
        return "REV3328";
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
    public synchronized DigitalChannel getBhi260ResetPin() {
        if (bhi260ResetPin == null) {
            bhi260ResetPin = createOutputPinIfOsSupportsBhi260apImu(45, false, GpioPin.Active.LOW, BHI_260_RESET_PIN_NAME);
        }
        return bhi260ResetPin;
    }

    @Override @NonNull
    public synchronized DigitalChannel getBhi260InterruptPin() {
        if (bhi260InterruptPin == null) {
            bhi260InterruptPin = createInputPinIfOsSupportsBhi260apImu(84, GpioPin.Active.HIGH, BHI_260_INTERRUPT_PIN_NAME);
        }
        return bhi260InterruptPin;
    }

    @Override @NonNull
    public synchronized DigitalChannel getBhi260Gpio1() {
        if (bhi260Gpio1Pin == null) {
            bhi260Gpio1Pin = createInputPinIfOsSupportsBhi260apImu(46, GpioPin.Active.LOW, BHI_260_GPIO1_PIN_NAME);
        }
        return bhi260Gpio1Pin;
    }

    @Override @NonNull
    public synchronized DigitalChannel getBhi260Gpio5() {
        if (bhi260Gpio5Pin == null) {
            bhi260Gpio5Pin = createOutputPinIfOsSupportsBhi260apImu(85, false, GpioPin.Active.LOW, BHI_260_GPIO5_PIN_NAME);
        }
        return bhi260Gpio5Pin;
    }

    @Override @NonNull
    public synchronized DigitalChannel getBhi260Gpio6() {
        if (bhi260Gpio6Pin == null) {
            bhi260Gpio6Pin = createOutputPinIfOsSupportsBhi260apImu(47, false, GpioPin.Active.LOW, BHI_260_GPIO6_PIN_NAME);
        }
        return bhi260Gpio6Pin;
    }

    @Override @NonNull
    synchronized public DigitalChannel getBhi260Gpio17() {
        if (bhi260Gpio17Pin == null) {
            bhi260Gpio17Pin = createInputPinIfOsSupportsBhi260apImu(86, GpioPin.Active.LOW, BHI_260_GPIO17_PIN_NAME);
        }
        return bhi260Gpio17Pin;
    }

    @Override @NonNull
    synchronized public DigitalChannel getBhi260Gpio18() {
        if (bhi260Gpio18Pin == null) {
            bhi260Gpio18Pin = createInputPinIfOsSupportsBhi260apImu(83, GpioPin.Active.LOW, BHI_260_GPIO18_PIN_NAME);
        }
        return bhi260Gpio18Pin;
    }

    @Override @NonNull
    public File getUartLocation() {
        return UART_FILE;
    }

    @Override @NonNull
    public WifiDataRate getWifiApBeaconRate() {
        if (LynxConstants.getControlHubOsVersionCode() < OS_1_1_0_VERSION_CODE) {
            return WifiDataRate.CCK_1Mb; // OS versions prior to 1.1.0 used a 1Mb beacon rate
        }
        String rawBeaconRateString = new RunShellCommand().run("cat /sys/module/wlan/parameters/rev_beacon_rate").getOutput().trim();
        RealtekWifiDataRate rtkDataRate = null;
        try {
            rtkDataRate = RealtekWifiDataRate.fromRawValue(Integer.parseInt(rawBeaconRateString));
        } catch (RuntimeException e) {
            RobotLog.ee(TAG, e, "Error obtaining Wi-Fi AP beacon rate");
        }
        if (rtkDataRate == null) {
            return WifiDataRate.UNKNOWN;
        }
        return rtkDataRate.wifiDataRate;
    }

    @Override public void setWifiApBeaconRate(WifiDataRate beaconRate) {
        if (LynxConstants.getControlHubOsVersionCode() < OS_1_1_1_VERSION_CODE) {
            RobotLog.ww(TAG, "Unable to set the Wi-Fi AP beacon rate on Control Hub OS version" + LynxConstants.getControlHubOsVersion());
            RobotLog.ww(TAG, "Control Hub OS version 1.1.1 or higher is required for this feature.");
            return;
        }
        int rawBeaconRate = RealtekWifiDataRate.fromWifiDataRate(beaconRate).rawValue;
        new RunShellCommand().run("echo " + rawBeaconRate + " > /sys/module/wlan/parameters/rev_beacon_rate");
    }

    @Override public boolean supports5GhzAp() {
        return true;
    }

    @Override public boolean supports5GhzAutoSelection() {
        return LynxConstants.getControlHubOsVersionCode() >= OS_1_1_2_BETA_VERSION_CODE;
    }

    @Override public boolean supportsBulkNetworkSettings() {
        return LynxConstants.getControlHubOsVersionCode() >= OS_1_1_2_BETA_VERSION_CODE;
    }

    @Override public boolean supportsGetChannelInfoIntent() {
        return LynxConstants.getControlHubOsVersionCode() >= OS_1_1_2_BETA_VERSION_CODE;
    }

    @Override public boolean hasControlHubUpdater() {
        return true;
    }

    @Override public boolean hasRcAppWatchdog() {
        return LynxConstants.getControlHubOsVersionCode() >= OS_1_1_2_VERSION_CODE;
    }

    private static DigitalChannel createInputPinIfOsSupportsBhi260apImu(int rawGpioNumber, GpioPin.Active active, String name) {
        if (LynxConstants.getControlHubOsVersionCode() >= OS_1_1_3_VERSION_CODE) {
            return GpioPin.createInput(rawGpioNumber, active, name);
        } else {
            warningSource.bhi260ImuAccessedOnIncompatibleOs = true;
            return new FakeAndroidBoard.FakeDigitalChannel(DigitalChannel.Mode.INPUT);
        }
    }

    private static DigitalChannel createOutputPinIfOsSupportsBhi260apImu(int rawGpioNumber, boolean initialState, GpioPin.Active active, String name) {
        if (LynxConstants.getControlHubOsVersionCode() >= OS_1_1_3_VERSION_CODE) {
            return GpioPin.createOutput(rawGpioNumber, initialState, active, name);
        } else {
            warningSource.bhi260ImuAccessedOnIncompatibleOs = true;
            return new FakeAndroidBoard.FakeDigitalChannel(DigitalChannel.Mode.OUTPUT);
        }
    }

    private enum RealtekWifiDataRate {
        // Values taken from https://github.com/REVrobotics/kernel-controlhub-android/blob/806e038dadebd9fd95b9604446d2ea440c9f86a0/drivers/net/wireless/rockchip_wlan/rtl8821cu/include/ieee80211.h#L702-L714
        RTK_CCK_1Mb(0x02, WifiDataRate.CCK_1Mb),
        RTK_CCK_2Mb(0x04, WifiDataRate.CCK_2Mb),
        RTK_CCK_5Mb(0x0B, WifiDataRate.CCK_5Mb),
        RTK_CCK_11Mb(0x16, WifiDataRate.CCK_11Mb),
        RTK_OFDM_6Mb(0x0C, WifiDataRate.OFDM_6Mb),
        RTK_OFDM_9Mb(0x12, WifiDataRate.OFDM_9Mb),
        RTK_OFDM_12Mb(0x18, WifiDataRate.OFDM_12Mb),
        RTK_OFDM_18Mb(0x24, WifiDataRate.OFDM_18Mb),
        RTK_OFDM_24Mb(0x30, WifiDataRate.OFDM_24Mb),
        RTK_OFDM_36Mb(0x48, WifiDataRate.OFDM_36Mb),
        RTK_OFDM_48Mb(0x60, WifiDataRate.OFDM_48Mb),
        RTK_OFDM_54Mb(0x6C, WifiDataRate.OFDM_54Mb);

        private final int rawValue;
        private final AndroidBoard.WifiDataRate wifiDataRate;
        RealtekWifiDataRate(int rawValue, AndroidBoard.WifiDataRate wifiDataRate) {
            this.rawValue = rawValue;
            this.wifiDataRate = wifiDataRate;
        }

        public static RealtekWifiDataRate fromWifiDataRate(AndroidBoard.WifiDataRate wifiDataRate) {
            for (RealtekWifiDataRate rtkWifiDataRate: values()) {
                if (rtkWifiDataRate.wifiDataRate == wifiDataRate) return rtkWifiDataRate;
            }
            throw new IllegalArgumentException("Unsupported data rate for Realtek Wi-Fi: " + wifiDataRate);
        }

        public static @Nullable RealtekWifiDataRate fromRawValue(int rawValue) {
            for (RealtekWifiDataRate rtkWifiDataRate: values()) {
                if (rtkWifiDataRate.rawValue == rawValue) return rtkWifiDataRate;
            }
            return null;
        }
    }

    private static class WarningSource implements GlobalWarningSource {
        volatile boolean bhi260ImuAccessedOnIncompatibleOs = false;

        @Nullable @Override public String getGlobalWarning() {
            if (bhi260ImuAccessedOnIncompatibleOs) {
                return AppUtil.getDefContext().getString(R.string.warningAttemptedToUseBhi260OnOldOS);
            } else {
                return null;
            }
        }

        @Override public boolean shouldTriggerWarningSound() { return false; }
        @Override public void suppressGlobalWarning(boolean suppress) { }
        @Override public void setGlobalWarning(String warning) { }
        @Override public void clearGlobalWarning() { }
    }
}

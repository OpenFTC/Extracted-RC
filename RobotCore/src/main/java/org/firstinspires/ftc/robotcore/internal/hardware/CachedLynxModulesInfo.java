/*
 * Copyright (c) 2020 REV Robotics, Craig MacFarlane
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * (subject to the limitations in the disclaimer below) provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of REV Robotics nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.firstinspires.ftc.robotcore.internal.hardware;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.R;
import com.qualcomm.robotcore.hardware.LynxModuleImuType;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.util.List;

public class CachedLynxModulesInfo {
    private static volatile List<LynxModuleInfo> cachedModulesInfo;

    public static List<LynxModuleInfo> getLynxModulesInfo() {
        return cachedModulesInfo;
    }

    public static void setLynxModulesInfo(List<LynxModuleInfo> lynxModulesInfo) {
        cachedModulesInfo = lynxModulesInfo;
    }

    /**
     * Convert a raw Lynx firmware version string (e.g. "HW: 20, Maj: 1, Min: 8, Eng: 2")
     * into a user-friendly string (e.g. "1.8.2") by stripping the leading hardware revision
     * and all alphabetic characters.
     */
    public static String formatFirmwareVersion(@NonNull String version) {
        return version.substring(version.indexOf(',')+1).replaceAll("[a-zA-Z: ]*", "").replaceAll(",", ".");
    }

    /**
     * name: The name of the device as recorded in the configuration file
     * firmwareVersion: The formatted Lynx firmware string (e.g. 1.8.2), or the value of lynxUnavailableFWVersionString
     *
     * This API is exposed via HTTP
     */
    public static class LynxModuleInfo {
        public final String name;
        public final String firmwareVersion;
        public final String parentSerial;
        public final int moduleAddress;
        public final String imuType;
        public final int revProductNumber;

        public LynxModuleInfo(@NonNull String name, @Nullable String rawFirmwareVersion, @NonNull String parentSerial, int moduleAddress, LynxModuleImuType imuType, int revProductNumber) {
            this.name = name;
            this.moduleAddress = moduleAddress;
            this.parentSerial = parentSerial;
            this.imuType = imuType.toString();
            this.revProductNumber = revProductNumber;
            if (rawFirmwareVersion == null) {
                this.firmwareVersion = AppUtil.getDefContext().getString(R.string.lynxUnavailableFWVersionString);
            } else {
                this.firmwareVersion = formatFirmwareVersion(rawFirmwareVersion);
            }
        }
    }
}

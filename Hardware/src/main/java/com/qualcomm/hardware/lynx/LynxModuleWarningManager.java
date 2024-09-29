/*
Copyright (c) 2019 Noah Andrews
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
package com.qualcomm.hardware.lynx;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;

import com.qualcomm.hardware.R;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.GlobalWarningSource;
import com.qualcomm.robotcore.util.RobotLog;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.qualcomm.robotcore.hardware.configuration.LynxConstants.EXPANSION_HUB_PRODUCT_NUMBER;

/**
 * Composes system telemetry warnings related to the status of the lynx modules, and logs
 * persistent conditions with configurable throttling.
 */
public class LynxModuleWarningManager {
    private static final LynxModuleWarningManager instance = new LynxModuleWarningManager();

    public static LynxModuleWarningManager getInstance() {
        return instance;
    }

    // Constant fields
    private final static String TAG = "LynxModuleWarningManager";

    // When you update these values, make sure to update InspectionActivity.isValidFirmwareVersion() at the same time.
    private final static String MIN_FW_VERSION_HUMAN_STRING = "1.8.2"; // ONLY for display to the user
    private final static int MIN_FW_VERSION_MAJOR = 1;
    private final static int MIN_FW_VERSION_MINOR = 8;
    private final static int MIN_FW_VERSION_ENG = 2;

    private final static int LOW_BATTERY_STATUS_TIMEOUT_SECONDS = 2;
    private final static int LOW_BATTERY_LOG_FREQUENCY_SECONDS = 2;
    private final static int UNRESPONSIVE_LOG_FREQUENCY_SECONDS = 2;
    private final OpModeManagerNotifier.Notifications opModeNotificationListener = new WarningManagerOpModeListener();
    private final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(AppUtil.getDefContext());
    private final GlobalWarningSource warningSource = new LynxModuleWarningSource();
    private final Object warningMessageLock = new Object();

    // State
    private volatile boolean userOpModeRunning = false;
    private volatile HardwareMap hardwareMap = null;
    private final ConcurrentMap<Integer, UnresponsiveStatus> modulesReportedUnresponsive = new ConcurrentHashMap<>();
    private final ConcurrentMap<Integer, LowBatteryStatus> modulesReportedLowBattery = new ConcurrentHashMap<>();
    private final Set<String> modulesReportedReset = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    private final Set<String> outdatedModules = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    private String cachedWarningMessage = null; // Protected by warningMessageLock

    /**
     * Called on Robot startup
     */
    public void init(OpModeManagerImpl opModeManager, HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        opModeManager.registerListener(opModeNotificationListener);
        warningSource.clearGlobalWarning();
        RobotLog.registerGlobalWarningSource(warningSource);
        if (sharedPrefs.getBoolean(AppUtil.getDefContext().getString(R.string.pref_warn_about_obsolete_software), true)) {
            lookForOutdatedModules();
        } else {
            outdatedModules.clear();
        }
    }

    /**
     * Unlike {@link LynxModule#noteNotResponding()}, this method should be called repeatedly while
     * the module is unresponsive. This is so that we can keep track of if the module was ever
     * unresponsive while a user OpMode was running.
     */
    public void reportModuleUnresponsive(LynxModule module) {
        if (!module.isUserModule() || !module.isOpen) return;
        int moduleNumber = module.getModuleAddress();
        UnresponsiveStatus unresponsiveStatus = modulesReportedUnresponsive.get(moduleNumber);
        if (unresponsiveStatus == null) {
            String moduleName = getModuleName(module);
            unresponsiveStatus = new UnresponsiveStatus(module, moduleName);
            modulesReportedUnresponsive.put(moduleNumber, unresponsiveStatus);
        }
        unresponsiveStatus.reportConditionAndLogWithThrottle(userOpModeRunning);
    }

    public void reportModuleReset(LynxModule module) {
        if (!module.isUserModule()) return;
        String moduleName = getModuleName(module);
        String logMessage = "%s regained power after a complete power loss.";

        if (userOpModeRunning) { // Module resets while the user is not running an OpMode are normal
            logMessage += " A user OpMode was running, so unexpected behavior may occur.";
            boolean newlyReported = modulesReportedReset.add(moduleName);
            if (newlyReported) {
                synchronized (warningMessageLock) {
                    cachedWarningMessage = null;
                }
            }
        } else {
            logMessage += " No user OpMode was running.";
        }
        RobotLog.ww("HubPowerCycle", logMessage, moduleName);
    }

    public void reportModuleLowBattery(LynxModule module) {
        if (!module.isUserModule()) return;
        int moduleNumber = module.getModuleAddress();
        LowBatteryStatus lowBatteryStatus = modulesReportedLowBattery.get(moduleNumber);
        if (lowBatteryStatus == null) {
            String moduleName = getModuleName(module);
            lowBatteryStatus = new LowBatteryStatus(module, moduleName);
            modulesReportedLowBattery.put(moduleNumber, lowBatteryStatus);
        }
        lowBatteryStatus.reportConditionAndLogWithThrottle(userOpModeRunning);
    }

    private String getModuleName(LynxModule module) {
        String moduleName;
        try {
            moduleName = hardwareMap.getNamesOf(module).iterator().next();
        } catch (RuntimeException e) { // Protects against null hardwareMap and empty iterator
            moduleName = "Expansion Hub " + module.getModuleAddress();
        }
        return moduleName;
    }

    private void lookForOutdatedModules() {
        outdatedModules.clear();
        List<LynxModule> lynxModules = hardwareMap.getAll(LynxModule.class);
        for (LynxModule lynxModule : lynxModules) {
            try {
                String fwVersion = lynxModule.getNullableFirmwareVersionString();
                if (fwVersion == null) continue;
                String[] versionNums = fwVersion.split("(, )?\\w*: ");
                versionNums = Arrays.copyOfRange(versionNums, 2,5);
                int majorVersion = Integer.parseInt(versionNums[0]);
                int minorVersion = Integer.parseInt(versionNums[1]);
                int engVersion = Integer.parseInt(versionNums[2]);
                boolean isExpansionHub = lynxModule.getRevProductNumber() == EXPANSION_HUB_PRODUCT_NUMBER;
                if (isFwVersionOutdated(majorVersion, minorVersion, engVersion) && isExpansionHub) {
                    outdatedModules.add(getModuleName(lynxModule));
                }
            } catch (RuntimeException e) {
                RobotLog.ee(TAG, e, "Exception caught while checking if module is outdated");
            }
        }
    }

    private boolean isFwVersionOutdated(int majorVersion, int minorVersion, int engVersion) {
        if (majorVersion > MIN_FW_VERSION_MAJOR) return false;
        if (majorVersion < MIN_FW_VERSION_MAJOR) return true;
        if (minorVersion > MIN_FW_VERSION_MINOR) return false;
        if (minorVersion < MIN_FW_VERSION_MINOR) return true;
        return engVersion < MIN_FW_VERSION_ENG;
    }

    /**
     * Used for conditions that persist past a singular event
     */
    private static abstract class ConditionStatus {
        final ElapsedTime timeSinceConditionLastReported = new ElapsedTime();
        final ElapsedTime timeSinceConditionLogged = new ElapsedTime(0);
        final LynxModuleIntf lynxModule;
        final String moduleName;
        final int logFrequencySeconds;
        boolean conditionPreviouslyTrue = false;
        boolean conditionTrueDuringOpModeRun = false;

        ConditionStatus(LynxModuleIntf lynxModule, String moduleName, int logFrequencySeconds) {
            this.lynxModule = lynxModule;
            this.logFrequencySeconds = logFrequencySeconds;
            this.moduleName = moduleName;
        }

        abstract boolean conditionCurrentlyTrue();
        abstract void logCondition();

        void reportConditionAndLogWithThrottle(boolean userOpModeRunning) {
            if (userOpModeRunning) conditionTrueDuringOpModeRun = true; // Don't overwrite a true value with false.
            timeSinceConditionLastReported.reset();
            if (timeSinceConditionLogged.seconds() > logFrequencySeconds) {
                logCondition();
                timeSinceConditionLogged.reset();
            }
        }

        final boolean hasChangedSinceLastCheck() {
            boolean conditionCurrentlyTrue = conditionCurrentlyTrue();
            boolean changedSinceLastCheck = conditionCurrentlyTrue != conditionPreviouslyTrue;
            conditionPreviouslyTrue = conditionCurrentlyTrue;
            return changedSinceLastCheck;
        }
    }

    private static class UnresponsiveStatus extends ConditionStatus {
        private UnresponsiveStatus(LynxModuleIntf lynxModule, String moduleName) {
            super(lynxModule, moduleName, UNRESPONSIVE_LOG_FREQUENCY_SECONDS);
        }
        @Override boolean conditionCurrentlyTrue() {
            return lynxModule.isNotResponding();
        }
        @Override void logCondition() {
            RobotLog.w("%s is currently unresponsive.", moduleName);
        }
    }

    private static class LowBatteryStatus extends ConditionStatus {
        private LowBatteryStatus(LynxModule lynxModule, String moduleName) {
            super(lynxModule, moduleName, LOW_BATTERY_LOG_FREQUENCY_SECONDS);
        }

        @Override boolean conditionCurrentlyTrue() {
            return timeSinceConditionLastReported.seconds() < LOW_BATTERY_STATUS_TIMEOUT_SECONDS;
        }

        @Override void logCondition() {
            RobotLog.w("%s currently has a battery too low to run motors and servos.", moduleName);
        }
    }

    private class LynxModuleWarningSource implements GlobalWarningSource {
        private int warningMessageSuppressionCount = 0; // Protected by warningMessageLock

        @Override public String getGlobalWarning() {
            synchronized (warningMessageLock) {
                if (warningMessageSuppressionCount > 0) {
                    return "";
                }
                boolean cacheValid = cachedWarningMessage != null;

                if (cacheValid) {
                    // Check if there is new low battery information
                    for (LowBatteryStatus status : modulesReportedLowBattery.values()) {
                        if (status.hasChangedSinceLastCheck()) {
                            cacheValid = false;
                            break;
                        }
                    }

                    // Check if there is new information about disconnected modules
                    for (UnresponsiveStatus status : modulesReportedUnresponsive.values()) {
                        if (status.hasChangedSinceLastCheck() || !cacheValid) {
                            cacheValid = false;
                            break;
                        }
                    }
                }

                if (!cacheValid) {
                    cachedWarningMessage = composeWarning();
                }
                return cachedWarningMessage;
            }
        }

        @Override public boolean shouldTriggerWarningSound() {
            return true;
        }

        private String composeWarning() {
            @Nullable String currentlyUnresponsiveWarning = null;
            @Nullable String previouslyUnresponsiveWarning = null;
            @Nullable String powerIssuesWarning;
            @Nullable String outdatedHubsWarning;

            if (modulesReportedUnresponsive.size() > 0) {
                // Separate the currently unresponsive modules from the previously unresponsive ones
                List<String> modulesCurrentlyUnresponsive = new ArrayList<>();
                List<String> modulesUnresponsiveDuringOpMode = new ArrayList<>();
                for (UnresponsiveStatus status : modulesReportedUnresponsive.values()) {
                    if (status.lynxModule.isNotResponding()) {
                        modulesCurrentlyUnresponsive.add(status.moduleName);
                    } else if (status.conditionTrueDuringOpModeRun && !modulesReportedReset.contains(status.moduleName)) {
                        // If we know the module reset (lost power) entirely, we don't also need to report that it wasn't responding.
                        modulesUnresponsiveDuringOpMode.add(status.moduleName);
                    }
                }
                currentlyUnresponsiveWarning = composeCurrentlyUnresponsiveWarning(modulesCurrentlyUnresponsive);
                previouslyUnresponsiveWarning = composePreviouslyNotRespondingWarning(modulesUnresponsiveDuringOpMode);
            }

            powerIssuesWarning = composePowerIssuesWarning();
            outdatedHubsWarning= composeOutdatedHubsWarning();

            return RobotLog.combineGlobalWarnings(Arrays.asList(currentlyUnresponsiveWarning, previouslyUnresponsiveWarning, powerIssuesWarning, outdatedHubsWarning));
        }

        @Nullable private String composeCurrentlyUnresponsiveWarning(List<String> modulesCurrentlyUnresponsive) {
            if (modulesCurrentlyUnresponsive.size() > 0) {
                StringBuilder builder = new StringBuilder();
                composeModuleList(modulesCurrentlyUnresponsive, builder);
                builder.append(AppUtil.getDefContext().getString(R.string.lynxModuleCurrentlyNotResponding));
                return builder.toString();
            } else {
                return null;
            }
        }

        @Nullable private String composePreviouslyNotRespondingWarning(List<String> modulesUnresponsiveDuringOpMode) {
            if (modulesUnresponsiveDuringOpMode.size() > 0) {
                StringBuilder builder = new StringBuilder();
                composeModuleList(modulesUnresponsiveDuringOpMode, builder);
                builder.append(AppUtil.getDefContext().getString(R.string.lynxModulePreviouslyNotResponding));
                return builder.toString();
            } else {
                return null;
            }
        }

        @Nullable private String composePowerIssuesWarning() {
            if (modulesReportedReset.size() < 1 && modulesReportedLowBattery.size() < 1) return null;

            StringBuilder builder = new StringBuilder();

            boolean powerLossWarningAdded = composePowerLossWarning(builder);
            if (powerLossWarningAdded) {
                builder.append(" ");
            }

            boolean batteryLowWarningAdded = composeBatteryLowWarning(builder);
            if (batteryLowWarningAdded) {
                builder.append(" ");
            }

            if (powerLossWarningAdded || batteryLowWarningAdded) {
                composePowerIssueTip(userOpModeRunning, builder);
                return builder.toString();
            } else {
                return null;
            }
        }

        @Nullable private String composeOutdatedHubsWarning() {
            if (outdatedModules.size() < 1) return null;
            StringBuilder builder = new StringBuilder();
            composeModuleList(outdatedModules, builder);
            builder.append(AppUtil.getDefContext().getString(R.string.lynxModuleFirmwareOutdated, MIN_FW_VERSION_HUMAN_STRING));
            return builder.toString();
        }


        // Returns true if power loss warning was added
        private boolean composePowerLossWarning(final StringBuilder builder) {
            if (modulesReportedReset.size() < 1) return false;

            composeModuleList(modulesReportedReset, builder);
            builder.append(AppUtil.getDefContext().getString(R.string.lynxModulePowerLost));
            return true;
        }

        // Returns true if battery low warning was added
        private boolean composeBatteryLowWarning(final StringBuilder builder) {
            if (modulesReportedLowBattery.size() < 1) return false;

            boolean warningAdded = false;
            List<String> modulesCurrentlyReporting = new ArrayList<>();
            List<String> modulesReportedDuringOpMode = new ArrayList<>();

            for (LowBatteryStatus status : modulesReportedLowBattery.values()) {
                if (status.conditionCurrentlyTrue()) {
                    modulesCurrentlyReporting.add(status.moduleName);
                } else if (status.conditionTrueDuringOpModeRun) {
                    modulesReportedDuringOpMode.add(status.moduleName);
                }
            }

            if (modulesCurrentlyReporting.size() > 0) {
                composeModuleList(modulesCurrentlyReporting, builder);
                builder.append(AppUtil.getDefContext().getString(R.string.lynxModuleBatteryIsCurrentlyLow));
                warningAdded = true;
            }

            if (modulesReportedDuringOpMode.size() > 0) {
                if (warningAdded) {
                    builder.append(" ");
                }
                composeModuleList(modulesReportedDuringOpMode, builder);
                builder.append(AppUtil.getDefContext().getString(R.string.lynxModuleBatteryWasLow));
                warningAdded = true;
            }
            return warningAdded;
        }

        private void composePowerIssueTip(boolean runningUserOpMode, final StringBuilder builder) {
            if (runningUserOpMode) {
                builder.append(AppUtil.getDefContext().getString(R.string.powerIssueTip));
            } else {
                builder.append(AppUtil.getDefContext().getString(R.string.robotOffTip));
            }
        }

        void composeModuleList(Collection<String> moduleNames, final StringBuilder builder) {
            Iterator<String> iterator = moduleNames.iterator();
            while (iterator.hasNext()) {
                builder.append(iterator.next());
                if (iterator.hasNext()) builder.append(", ");
            }
            builder.append(" ");
        }

        @Override public void suppressGlobalWarning(boolean suppress) {
            synchronized (warningMessageLock) {
                if (suppress)
                    warningMessageSuppressionCount++;
                else
                    warningMessageSuppressionCount--;
            }
        }

        @Override public void clearGlobalWarning() {
            synchronized (warningMessageLock) {
                cachedWarningMessage = null;
                modulesReportedUnresponsive.clear();
                modulesReportedReset.clear();
                modulesReportedLowBattery.clear();
                outdatedModules.clear();
                warningMessageSuppressionCount = 0;
            }
        }

        @Override public void setGlobalWarning(String warning) {
            // Ignore, this class controls its own warning message
        }
    }

    private class WarningManagerOpModeListener implements OpModeManagerNotifier.Notifications {
        @Override public void onOpModePreInit(OpMode opMode) {
            userOpModeRunning = !(opMode instanceof OpModeManagerImpl.DefaultOpMode);
        }

        @Override public void onOpModePreStart(OpMode opMode) { }

        @Override public void onOpModePostStop(OpMode opMode) {
            userOpModeRunning = false;
        }
    }
}

/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
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
package org.firstinspires.inspection;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;

import com.google.gson.annotations.SerializedName;
import com.qualcomm.robotcore.BuildConfig;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.util.Device;
import com.qualcomm.robotcore.wifi.NetworkType;

import org.firstinspires.ftc.robotcore.internal.hardware.CachedLynxModulesInfo;
import org.firstinspires.ftc.robotcore.internal.network.ApChannelManagerFactory;
import org.firstinspires.ftc.robotcore.internal.network.DeviceNameManager;
import org.firstinspires.ftc.robotcore.internal.network.DeviceNameManagerFactory;
import org.firstinspires.ftc.robotcore.internal.network.NetworkConnectionHandler;
import org.firstinspires.ftc.robotcore.internal.network.PasswordManagerFactory;
import org.firstinspires.ftc.robotcore.internal.network.WifiUtil;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;
import org.firstinspires.ftc.robotcore.internal.network.StartResult;
import org.firstinspires.ftc.robotcore.internal.network.WifiDirectAgent;

import java.util.List;

/**
 * {@link InspectionState} contains the inspection state of either a RC or a DS
 */
@SuppressWarnings("WeakerAccess")
public class InspectionState
    {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public static final String robotControllerPackage = "com.qualcomm.ftcrobotcontroller";
    public static final String driverStationPackage = "com.qualcomm.ftcdriverstation";

    public static final String NO_VERSION = "";
    public static final int NO_VERSION_CODE = 0;

    //The serialized names save several hundred bytes in the QR code

    @SerializedName("mfr")
    public String manufacturer;
    @SerializedName("mdl")
    public String model;
    @SerializedName("dev")
    public String deviceCodename; // For scoring system to auto-select
    @SerializedName("os")
    public String osVersion; // Android version (e.g. 7.1.1)
    @SerializedName("chOs")
    public String controlHubOsVersion; // Control Hub OS version (e.g. 1.1.1)
    @SerializedName("dhOs")
    public String driverHubOsVersion;
    @SerializedName("chOsNum")
    public int controlHubOsVersionNum;
    @SerializedName("dhOsNum")
    public int driverHubOsVersionNum;
    @SerializedName("fw")
    public String firmwareVersion; // TODO(Noah): The next time we bump Robocol, send a list of firmware versions instead
    @SerializedName("sdk")
    public int sdkInt;
    @SerializedName("am")
    public boolean airplaneModeOn;
    @SerializedName("bt")
    public boolean bluetoothOn;
    @SerializedName("wfEn")
    public boolean wifiEnabled;
    @SerializedName("wfConn")
    public boolean wifiConnected;
    @SerializedName("wfDirEn")
    public boolean wifiDirectEnabled;
    @SerializedName("wfDirConn")
    public boolean wifiDirectConnected;
    @SerializedName("wfc")
    public int wifiChannel;
    @SerializedName("loc")
    public boolean locationEnabled;
    @SerializedName("name")
    public String deviceName;
    @SerializedName("bat")
    public double batteryFraction;
    @SerializedName("rcIn")
    public boolean robotControllerInstalled;
    @SerializedName("dsIn")
    public boolean driverStationInstalled;
    @SerializedName("aVmj")
    public int majorSdkVersion;
    @SerializedName("aVmn")
    public int minorSdkVersion;
    @SerializedName("aVpt")
    public int pointSdkVersion;
    @SerializedName("bldTs")
    public String appBuildTime;
    @QrExclude
    public long    rxDataCount;
    @QrExclude
    public long    txDataCount;
    @QrExclude
    public long    bytesPerSecond;
    @SerializedName("pw")
    public boolean isDefaultPassword;

    //----------------------------------------------------------------------------------------------
    // Construction and initialization
    //----------------------------------------------------------------------------------------------

    public InspectionState()
        {
        // For deserialization, initialize CH OS version to NO_VERSION value.
        // Otherwise, it will be null when the RC is running 5.x
        this.controlHubOsVersion = NO_VERSION;
        }

    public void initializeLocal()
        {
        DeviceNameManager nameManager = DeviceNameManagerFactory.getInstance();
        StartResult startResult = new StartResult();
        nameManager.start(startResult);
        initializeLocal(nameManager);
        nameManager.stop(startResult);
        }

    public void initializeLocal(DeviceNameManager nameManager)
        {
        this.manufacturer = Build.MANUFACTURER;
        this.model = Build.MODEL;
        this.deviceCodename = Build.DEVICE;
        this.osVersion = Build.VERSION.RELEASE;
        this.firmwareVersion = getFirmwareInspectionVersions();
        this.sdkInt = Build.VERSION.SDK_INT;
        this.airplaneModeOn = WifiUtil.isAirplaneModeOn();
        this.bluetoothOn = WifiUtil.isBluetoothOn();
        this.wifiEnabled = WifiUtil.isWifiEnabled();
        this.batteryFraction = getLocalBatteryFraction();

        this.majorSdkVersion = BuildConfig.SDK_MAJOR_VERSION;
        this.minorSdkVersion = BuildConfig.SDK_MINOR_VERSION;
        this.pointSdkVersion = BuildConfig.SDK_POINT_VERSION;
        this.appBuildTime = BuildConfig.SDK_BUILD_TIME;
        this.driverStationInstalled = !getPackageVersionString(driverStationPackage).equals(NO_VERSION);
        this.robotControllerInstalled = !getPackageVersionString(robotControllerPackage).equals(NO_VERSION);
        this.deviceName = nameManager.getDeviceName();

        if (Device.isRevDriverHub())
            {
            this.driverHubOsVersion = LynxConstants.getDriverHubOsVersion();
            this.driverHubOsVersionNum = LynxConstants.getDriverHubOsVersionCode();
            }
        else
            {
            this.driverHubOsVersion = NO_VERSION;
            this.driverHubOsVersionNum = NO_VERSION_CODE;
            }

        NetworkConnectionHandler networkConnectionHandler = NetworkConnectionHandler.getInstance();
        NetworkType networkType = networkConnectionHandler.getNetworkType();
        if (networkType == NetworkType.WIRELESSAP || networkType == NetworkType.RCWIRELESSAP)
            {
            if (Device.isRevControlHub())
                {
                this.controlHubOsVersion = LynxConstants.getControlHubOsVersion();
                if (this.controlHubOsVersion == null)
                    {
                    this.controlHubOsVersion = "unknown";
                    }
                this.controlHubOsVersionNum = LynxConstants.getControlHubOsVersionCode();
                this.isDefaultPassword = PasswordManagerFactory.getInstance().isDefault();
                this.wifiEnabled = WifiUtil.isWifiApEnabled();
                if (this.wifiEnabled) this.wifiConnected = true;
                }
            else
                {
                this.controlHubOsVersion = NO_VERSION;
                this.controlHubOsVersionNum = NO_VERSION_CODE;
                this.deviceName = WifiUtil.getConnectedSsid();
                this.wifiDirectEnabled = WifiUtil.isWifiEnabled();
                this.wifiConnected = WifiUtil.isWifiConnected();
                }
            this.wifiDirectConnected = false;  // Not shown on inspection activity.  Why does it exist?
            }
        else
            {
            this.wifiConnected = WifiDirectAgent.getInstance().isWifiConnected();
            this.wifiDirectEnabled = WifiDirectAgent.getInstance().isWifiDirectEnabled();
            this.wifiDirectConnected = WifiDirectAgent.getInstance().isWifiDirectConnected();
            }
        this.wifiChannel = ApChannelManagerFactory.getInstance().getCurrentChannel().channelNum;
        this.locationEnabled = WifiUtil.areLocationServicesEnabled();
        this.rxDataCount = networkConnectionHandler.getRxDataCount();
        this.txDataCount = networkConnectionHandler.getTxDataCount();
        this.bytesPerSecond = networkConnectionHandler.getBytesPerSecond();
        }

    protected double getLocalBatteryFraction()
        {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = AppUtil.getInstance().getApplication().registerReceiver(null, intentFilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        return level / (double) scale;
        }

    protected String getPackageVersionString(String packageName)
        {
        PackageManager pm = AppUtil.getDefContext().getPackageManager();
        try
            {
            return pm.getPackageInfo(packageName, PackageManager.GET_META_DATA).versionName;
            }
        catch (PackageManager.NameNotFoundException e)
            {
            return NO_VERSION;
            }
        }

    //----------------------------------------------------------------------------------------------
    // Serialization
    //----------------------------------------------------------------------------------------------

    public String serialize()
        {
        return SimpleGson.getInstance().toJson(this);
        }

    public static InspectionState deserialize(String serialized)
        {
        return SimpleGson.getInstance().fromJson(serialized, InspectionState.class);
        }

    //----------------------------------------------------------------------------------------------
    // Firmware determination
    //----------------------------------------------------------------------------------------------

    /*
     * getFirmwareDisplayVersion
     *
     * Returns displayable text that we can use for firmware versions on the Inspection activity.
     */
    private static String getFirmwareInspectionVersions()
        {
        List<CachedLynxModulesInfo.LynxModuleInfo> versions = CachedLynxModulesInfo.getLynxModulesInfo();

        if (versions ==  null || versions.isEmpty())
            {
            return "N/A";
            }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < versions.size(); i++)
            {
            stringBuilder.append(String.format("[%s] %s", versions.get(i).name, versions.get(i).firmwareVersion));

            // Append carriage returns, but not for the last item
            if(i < versions.size()-1)
                {
                stringBuilder.append("\n");
                }
            }

        return stringBuilder.toString();
        }
    }

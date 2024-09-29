/*
 * Copyright 2016 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.firstinspires.ftc.robotcore.internal.webserver;

import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qualcomm.robotcore.BuildConfig;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.util.Device;
import com.qualcomm.robotcore.util.IncludedFirmwareFileInfo;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.hardware.CachedLynxModulesInfo;
import org.firstinspires.ftc.robotcore.internal.hardware.CachedLynxModulesInfo.LynxModuleInfo;
import org.firstinspires.ftc.robotcore.internal.hardware.android.AndroidBoard;
import org.firstinspires.ftc.robotcore.internal.network.ApChannel;
import org.firstinspires.ftc.robotcore.internal.network.ApChannelManagerFactory;
import org.firstinspires.ftc.robotcore.internal.network.DeviceNameManagerFactory;
import org.firstinspires.ftc.robotcore.internal.network.WifiUtil;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * A class that contains various information about the robot controller's web server
 * that is useful to javascript.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
@SuppressWarnings("WeakerAccess")
public class RobotControllerWebInfo {

  public static final String TAG = RobotControllerWebInfo.class.getSimpleName();

  private static final Gson gson = new GsonBuilder()
          .registerTypeAdapter(ApChannel.class, new ApChannel.GsonTypeAdapter())
          .create();

  private static final String cachedChOsVersion = LynxConstants.getControlHubOsVersion();
  private static final String cachedSdkVersion = AppUtil.getSdkVersionString();

  private static String cachedRcVersion;
  static {
    try {
      cachedRcVersion = AppUtil.getDefContext().getPackageManager().getPackageInfo(AppUtil.getDefContext().getPackageName(), 0).versionName;
    } catch (PackageManager.NameNotFoundException e) {
      RobotLog.ee(TAG, e, "Unable to find the RC version name");
      cachedRcVersion = "unknown"; // Shouldn't happen
    }
  }

  // NOTE: these names are known to javascript
  private final String deviceName;
  private final String networkName;
  private final String passphrase;
  private final String serverUrl;
  private final boolean serverIsAlive;
  private final boolean isREVControlHub;
  private final boolean supports5GhzAp;
  private final boolean appUpdateRequiresReboot;
  private final boolean supportsOtaUpdate;
  private final Set<ApChannel> availableChannels;
  private final ApChannel currentChannel;
  private final int webSocketApiVersion;
  private final String sdkVersion = cachedSdkVersion;
  private final String rcVersion = cachedRcVersion;
  private final String serialNumber;
  private final String chOsVersion = cachedChOsVersion;
  private final List<LynxModuleInfo> revHubNamesAndVersions;
  private final String includedFirmwareFileVersion;
  private FtcUserAgentCategory ftcUserAgentCategory;

  private static String cachedConfigName;
  private String activeConfigName;

  public RobotControllerWebInfo(
      String networkName, String passphrase, String serverUrl,
      boolean serverIsAlive) {
    this.deviceName = DeviceNameManagerFactory.getInstance().getDeviceName();
    this.networkName = (networkName == null) ? this.deviceName : networkName;
    this.passphrase = passphrase;
    this.serverUrl = serverUrl;
    this.serverIsAlive = serverIsAlive;
    this.isREVControlHub = LynxConstants.isRevControlHub();
    this.ftcUserAgentCategory = FtcUserAgentCategory.OTHER;
    this.supports5GhzAp = WifiUtil.is5GHzAvailable();
    this.appUpdateRequiresReboot = !AndroidBoard.getInstance().hasControlHubUpdater(); // The Control Hub Updater replaces the old update method, which required a reboot
    this.supportsOtaUpdate = AndroidBoard.getInstance().hasControlHubUpdater();        // The Control Hub Updater enables the RC app to start OTA updates
    this.availableChannels = ApChannelManagerFactory.getInstance().getSupportedChannels();
    this.currentChannel = ApChannelManagerFactory.getInstance().getCurrentChannel();
    this.webSocketApiVersion = WebSocketManager.WEBSOCKET_API_VERSION;
    this.revHubNamesAndVersions = CachedLynxModulesInfo.getLynxModulesInfo();
    this.includedFirmwareFileVersion = IncludedFirmwareFileInfo.HUMAN_READABLE_FW_VERSION;
    this.serialNumber = Device.getSerialNumberOrUnknown();
  }

  public String getDeviceName() {
    return deviceName;
  }

  public String getNetworkName() {
    return networkName;
  }

  public String getPassphrase() {
    return passphrase;
  }

  public String getServerUrl() {
    return serverUrl;
  }

  public boolean isServerAlive() {
    return serverIsAlive;
  }

  public FtcUserAgentCategory getFtcUserAgentCategory() {
    return ftcUserAgentCategory;
  }

  public boolean isREVControlHub() {
    return isREVControlHub;
  }

  public boolean is5GhzApSupported() {
    return supports5GhzAp;
  }

  public boolean doesAppUpdateRequireReboot() {
    return appUpdateRequiresReboot;
  }

  public boolean isOtaUpdateSupported() {
    return supportsOtaUpdate;
  }

  public int getWebSocketApiVersion() {
    return webSocketApiVersion;
  }

  // todo: fix realign the types of the input
  public void setFtcUserAgentCategory(Map<String, String> session) {
    String userAgent = session.get("user-agent");
    this.ftcUserAgentCategory = FtcUserAgentCategory.fromUserAgent(userAgent);
  }

  public static void setActiveConfigName(String newActiveConfig) {
    cachedConfigName = newActiveConfig;
  }

  public String toJson() {
    activeConfigName = cachedConfigName;
    return gson.toJson(this);
  }

  public static RobotControllerWebInfo fromJson(String json) {
    return gson.fromJson(json, RobotControllerWebInfo.class);
  }
}

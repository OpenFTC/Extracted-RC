/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

/*
 * Copyright (c) 2016 Molly Nicholas
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
 * Neither the name of Molly Nicholas nor the names of its contributors may be used to
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
package com.qualcomm.ftccommon;

import android.app.Activity;
import android.graphics.Color;

import com.qualcomm.ftccommon.configuration.FtcConfigurationActivity;
import com.qualcomm.ftccommon.configuration.RobotConfigFile;
import com.qualcomm.ftccommon.configuration.RobotConfigFileManager;
import com.qualcomm.ftccommon.configuration.USBScanManager;
import com.qualcomm.hardware.HardwareFactory;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxUsbDevice;
import com.qualcomm.robotcore.eventloop.EventLoop;
import com.qualcomm.robotcore.eventloop.EventLoopManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.DeviceManager;
import com.qualcomm.robotcore.hardware.EmbeddedControlHubModule;
import com.qualcomm.robotcore.hardware.LynxModuleMeta;
import com.qualcomm.robotcore.hardware.LynxModuleMetaList;
import com.qualcomm.robotcore.hardware.ScannedDevices;
import com.qualcomm.robotcore.hardware.USBAccessibleLynxModule;
import com.qualcomm.robotcore.hardware.configuration.ConfigurationTypeManager;
import com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.hardware.configuration.ReadXMLFileHandler;
import com.qualcomm.robotcore.hardware.configuration.WriteXMLFileHandler;
import com.qualcomm.robotcore.robocol.Command;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;
import com.qualcomm.robotcore.util.ThreadPool;
import com.qualcomm.robotcore.util.WebServer;

import org.firstinspires.ftc.robotcore.external.Consumer;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamServer;
import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;
import org.firstinspires.ftc.robotcore.internal.network.ApChannel;
import org.firstinspires.ftc.robotcore.internal.network.CallbackResult;
import org.firstinspires.ftc.robotcore.internal.network.NetworkConnectionHandler;
import org.firstinspires.ftc.robotcore.internal.network.PreferenceRemoterRC;
import org.firstinspires.ftc.robotcore.internal.network.RobotCoreCommandList;
import org.firstinspires.ftc.robotcore.internal.network.RobotCoreCommandList.LynxFirmwareUpdateResp;
import org.firstinspires.ftc.robotcore.internal.network.WifiDirectAgent;
import org.firstinspires.ftc.robotcore.internal.network.WifiDirectGroupName;
import org.firstinspires.ftc.robotcore.internal.network.WifiDirectPersistentGroupManager;
import org.firstinspires.ftc.robotcore.internal.opmode.OnBotJavaBuildLocker;
import org.firstinspires.ftc.robotcore.internal.opmode.RegisteredOpModes;
import org.firstinspires.ftc.robotcore.internal.system.AppAliveNotifier;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.Assert;
import org.firstinspires.ftc.robotcore.internal.ui.ProgressParameters;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;
import org.firstinspires.inspection.InspectionState;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

import androidx.annotation.CallSuper;

/**
 * {@link FtcEventLoopBase} is an abstract base that handles defines core event processing
 * logic that's available whether a Robot is currently extant or not
 */
@SuppressWarnings("WeakerAccess")
public abstract class FtcEventLoopBase implements EventLoop
    {
    // TODO(Noah): Look into making FtcEventLoopIdle (and therefore FtcEventLoopBase) available
    //             before the Robot first starts up. This would allow many commands (including
    //             injected commands) to succeed much earlier.
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public static final String TAG = "FtcEventLoop";

    protected NetworkConnectionHandler networkConnectionHandler = NetworkConnectionHandler.getInstance();
    protected Activity activityContext;
    protected RobotConfigFileManager robotCfgFileMgr;
    protected FtcEventLoopHandler ftcEventLoopHandler;
    protected boolean runningOnDriverStation = false;
    protected final USBScanManager usbScanManager = USBScanManager.getInstance();
    protected final OpModeRegister userOpmodeRegister;

    protected final RegisteredOpModes registeredOpModes;

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    protected FtcEventLoopBase(HardwareFactory hardwareFactory, OpModeRegister userOpmodeRegister, UpdateUI.Callback callback, Activity activityContext)
        {
        this.userOpmodeRegister = userOpmodeRegister;
        this.registeredOpModes = RegisteredOpModes.getInstance();
        this.activityContext = activityContext;
        this.robotCfgFileMgr = new RobotConfigFileManager(activityContext);
        this.ftcEventLoopHandler = new FtcEventLoopHandler(hardwareFactory, callback, activityContext);
        }

    //----------------------------------------------------------------------------------------------
    // Scanning
    //----------------------------------------------------------------------------------------------

    @Override public void teardown() throws RobotCoreException, InterruptedException
        {
        ftcEventLoopHandler.close();
        }

    //----------------------------------------------------------------------------------------------
    // Commands
    //----------------------------------------------------------------------------------------------

    @Override
    public CallbackResult processCommand(Command command) throws InterruptedException, RobotCoreException
        {
        CallbackResult result = CallbackResult.HANDLED;

        String name = command.getName();
        String extra = command.getExtra();

        if (name.equals(CommandList.CMD_RESTART_ROBOT))
            {
            handleCommandRestartRobot();
            }
        else if (name.equals(CommandList.CMD_REQUEST_CONFIGURATIONS))
            {
            handleCommandRequestConfigurations();
            }
        else if (name.equals(CommandList.CMD_REQUEST_REMEMBERED_GROUPS))
            {
            handleCommandRequestRememberedGroups();
            }
        else if (name.equals(CommandList.CMD_CLEAR_REMEMBERED_GROUPS))
            {
            handleCommandClearRememberedGroups();
            }
        else if (name.equals(CommandList.CMD_SCAN))
            {
            handleCommandScan(extra);
            }
        else if (name.equals(CommandList.CMD_DISCOVER_LYNX_MODULES))
            {
            handleCommandDiscoverLynxModules(extra);
            }
        else if (name.equals(CommandList.CMD_LYNX_FIRMWARE_UPDATE))
            {
            handleCommandLynxFirmwareUpdate(command);
            }
        else if (name.equals(CommandList.CMD_GET_USB_ACCESSIBLE_LYNX_MODULES))
            {
            handleCommandGetUSBAccessibleLynxModules(command);
            }
        else if (name.equals(CommandList.CMD_LYNX_ADDRESS_CHANGE))
            {
            handleCommandLynxChangeModuleAddresses(command);
            }
        else if (name.equals(CommandList.CMD_GET_CANDIDATE_LYNX_FIRMWARE_IMAGES))
            {
            handleCommandGetCandidateLynxFirmwareImages(command);
            }
        else if (name.equals(CommandList.CMD_REQUEST_INSPECTION_REPORT))
            {
            handleCommandRequestInspectionReport();
            }
        else if (name.equals(CommandList.CMD_DISABLE_BLUETOOTH))
            {
            handleCommandDisableBluetooth();
            }
        else if (name.equals(CommandList.CMD_REQUEST_ABOUT_INFO))
            {
            handleCommandRequestAboutInfo(command);
            }
        else if (name.equals(CommandList.CMD_DISCONNECT_FROM_WIFI_DIRECT))
            {
            handleCommandDisconnectWifiDirect();
            }
        else if (name.equals(CommandList.CMD_REQUEST_CONFIGURATION_TEMPLATES))
            {
            handleCommandRequestConfigurationTemplates();
            }
        else if (name.equals(RobotCoreCommandList.CMD_REQUEST_PARTICULAR_CONFIGURATION))
            {
            handleCommandRequestParticularConfiguration(extra);
            }
        else if (name.equals(CommandList.CMD_ACTIVATE_CONFIGURATION))
            {
            handleCommandActivateConfiguration(extra);
            }
        else if (name.equals(CommandList.CMD_REQUEST_USER_DEVICE_TYPES))
            {
            ConfigurationTypeManager.getInstance().sendUserDeviceTypes();
            }
        else if (name.equals(RobotCoreCommandList.CMD_REQUEST_ACTIVE_CONFIG))
            {
            sendActiveConfig();
            }
        else if (name.equals(RobotCoreCommandList.CMD_REQUEST_OP_MODE_LIST))
            {
            sendOpModeList();
            }
        else if (name.equals(CommandList.CMD_SAVE_CONFIGURATION))
            {
            handleCommandSaveConfiguration(extra);
            }
        else if (name.equals(CommandList.CMD_DELETE_CONFIGURATION))
            {
            handleCommandDeleteConfiguration(extra);
            }
        else if (name.equals(CommandList.CMD_START_DS_PROGRAM_AND_MANAGE))
            {
            handleCommandStartDriverStationProgramAndManage();
            }
        else if (name.equals(CommandList.CMD_SHOW_TOAST))
            {
            handleCommandShowToast(command);
            }
        else if (name.equals(CommandList.CMD_SHOW_DIALOG))
            {
            handleCommandShowDialog(command);
            }
        else if (name.equals(CommandList.CMD_DISMISS_DIALOG))
            {
            handleCommandDismissDialog(command);
            }
        else if (name.equals(CommandList.CMD_DISMISS_ALL_DIALOGS))
            {
            handleCommandDismissAllDialogs(command);
            }
        else if (name.equals(CommandList.CMD_SHOW_PROGRESS))
            {
            handleCommandShowProgress(command);
            }
        else if (name.equals(CommandList.CMD_DISMISS_PROGRESS))
            {
            handleCommandDismissProgress(command);
            }
        else if (name.equals(CommandList.CMD_ROBOT_CONTROLLER_PREFERENCE))
            {
            result = PreferenceRemoterRC.getInstance().handleCommandRobotControllerPreference(extra);
            }
        else if (name.equals(CommandList.CmdPlaySound.Command))
            {
            result = SoundPlayer.getInstance().handleCommandPlaySound(extra);
            }
        else if (name.equals(CommandList.CmdRequestSound.Command))
            {
            result = SoundPlayer.getInstance().handleCommandRequestSound(command);
            }
        else if (name.equals(CommandList.CmdStopPlayingSounds.Command))
            {
            result = SoundPlayer.getInstance().handleCommandStopPlayingSounds(command);
            }
        else if (name.equals(CommandList.CMD_REQUEST_FRAME))
            {
            result = CameraStreamServer.getInstance().handleRequestFrame();
            }
        else if (name.equals(CommandList.CmdVisuallyIdentify.Command))
            {
            result = handleCommandVisuallyIdentify(command);
            }
        else if (name.equals(CommandList.CMD_VISUALLY_CONFIRM_WIFI_RESET))
            {
            result = handleCommandVisuallyConfirmWifiReset();
            }
        else if (name.equals(CommandList.CMD_VISUALLY_CONFIRM_WIFI_BAND_SWITCH))
            {
            result = handleCommandVisuallyConfirmWifiBandSwitch(command);
            }
        else
            {
            result = CallbackResult.NOT_HANDLED;
            }
        return result;
        }


    protected void handleCommandActivateConfiguration(String data)
        {
        RobotConfigFile cfgFile = robotCfgFileMgr.getConfigFromString(data);
        robotCfgFileMgr.setActiveConfigAndUpdateUI(runningOnDriverStation, cfgFile);
        }

    protected void sendActiveConfig()
        {
        RobotConfigFile configFile = robotCfgFileMgr.getActiveConfig();
        String serialized = configFile.toString();
        networkConnectionHandler.sendCommand(new Command(CommandList.CMD_NOTIFY_ACTIVE_CONFIGURATION, serialized));
        }

    protected void sendOpModeList()
        {
        // We might get a request in really soon, before we're fully together. Wait: the driver
        // station doesn't retry if we were to ignore (might not need any more, as we send this
        // state more frequently than we used to)
        this.registeredOpModes.waitOpModesRegistered();
        String opModeList = SimpleGson.getInstance().toJson(registeredOpModes.getOpModes());
        networkConnectionHandler.sendCommand(new Command(CommandList.CMD_NOTIFY_OP_MODE_LIST, opModeList));
        }

    protected void checkForChangedOpModes()
        {
        boolean needToSendUIState = false;
        if (registeredOpModes.getOnBotJavaChanged())
            {
            OnBotJavaBuildLocker.lockBuildExclusiveWhile(new Runnable()
                {
                    @Override public void run()
                        {
                        registeredOpModes.clearOnBotJavaChanged();
                        registeredOpModes.registerOnBotJavaOpModes();
                        }
                });
            needToSendUIState = true;
            }

        if (registeredOpModes.getExternalLibrariesChanged())
            {
            registeredOpModes.clearExternalLibrariesChanged(); // clear first so we err on side of registering too often rather than too infrequently
            registeredOpModes.registerExternalLibrariesOpModes();
            needToSendUIState = true;
            }

        if (registeredOpModes.getBlocksOpModesChanged())
            {
            registeredOpModes.clearBlocksOpModesChanged(); // clear first so we err on side of registering too often rather than too infrequently
            registeredOpModes.registerInstanceOpModes();
            needToSendUIState = true;
            }

        if (needToSendUIState)
            {
            sendOpModeList();
            ConfigurationTypeManager.getInstance().sendUserDeviceTypes();
            }
        }

    @Override @CallSuper
    public void init(EventLoopManager eventLoopManager) throws RobotCoreException, InterruptedException
        {
        }

    @Override @CallSuper
    public void loop()
        {
        AppAliveNotifier.getInstance().notifyAppAlive();
        }

    protected void handleCommandRestartRobot()
        {
        ftcEventLoopHandler.restartRobot();
        }

    /*
     * The driver station wants the contents of the configuration file.
     */
    protected void handleCommandRequestParticularConfiguration(String data)
        {
        RobotConfigFile file = robotCfgFileMgr.getConfigFromString(data);
        ReadXMLFileHandler parser = new ReadXMLFileHandler();

        if (file.isNoConfig())
            {
            // don't try to parse if there's no file
            return;
            }

        try
            {
            // It's important to parse and re-serialize the configuration file, so that we can apply any needed transformations prior to transmission.
            WriteXMLFileHandler writeXMLFileHandler = new WriteXMLFileHandler();
            ArrayList<ControllerConfiguration> deviceList = (ArrayList<ControllerConfiguration>) parser.parse(file.getXml());
            // This file is already saved, so we should allow duplicates in it rather than failing to load it at all
            String xmlData = writeXMLFileHandler.toXml(deviceList, true);
            RobotLog.vv(FtcConfigurationActivity.TAG, "FtcEventLoop: handleCommandRequestParticularConfigFile, data: " + xmlData);
            networkConnectionHandler.sendCommand(new Command(RobotCoreCommandList.CMD_REQUEST_PARTICULAR_CONFIGURATION_RESP, xmlData));
            }
        catch (RobotCoreException | FileNotFoundException | XmlPullParserException e)
            {
            RobotLog.ee(TAG, e, "Failed to get and/or parse the requested configuration file");
            }
        }

    protected void handleCommandDeleteConfiguration(String fileInfo)
        {
        RobotConfigFile cfgFile = robotCfgFileMgr.getConfigFromString(fileInfo);
        File file = RobotConfigFileManager.getFullPath(cfgFile.getName());
        if (file.delete())
            {
            /* all is well */
            }
        else
            {
            RobotLog.ee(TAG, "Tried to delete a file that does not exist: " + cfgFile.getName());
            }
        }

    protected void handleCommandSaveConfiguration(String fileInfo)
        {
        String[] fileInfoArray = fileInfo.split(RobotConfigFileManager.FILE_LIST_COMMAND_DELIMITER);
        try
            {
            RobotConfigFile cfgFile = robotCfgFileMgr.getConfigFromString(fileInfoArray[0]);
            robotCfgFileMgr.writeToFile(cfgFile, false, fileInfoArray[1]);
            robotCfgFileMgr.setActiveConfigAndUpdateUI(false, cfgFile);
            }
        catch (RobotCoreException | IOException | RuntimeException e)
            {
            RobotLog.ee(TAG, e, "Failed to save configuration file");
            }
        }

    /**
     * Serialize the entire list of config file metadata and send to the driver station
     */
    protected void handleCommandRequestConfigurations()
        {
        ArrayList<RobotConfigFile> fileList = robotCfgFileMgr.getXMLFiles();
        String objsSerialized = RobotConfigFileManager.serializeXMLConfigList(fileList);
        networkConnectionHandler.sendCommand(new Command(CommandList.CMD_REQUEST_CONFIGURATIONS_RESP, objsSerialized));
        }

    /**
     * Serialize the list of remembered Wi-Fi Direct groups and send it to the driver station
     */
    protected void handleCommandRequestRememberedGroups()
        {
        WifiDirectPersistentGroupManager manager = new WifiDirectPersistentGroupManager(WifiDirectAgent.getInstance());
        String serialized = WifiDirectGroupName.serializeNames(manager.getPersistentGroups());
        networkConnectionHandler.sendCommand(new Command(CommandList.CMD_REQUEST_REMEMBERED_GROUPS_RESP, serialized));
        }

    /**
     * Clear the list of remembered groups
     */
    protected void handleCommandClearRememberedGroups()
        {
        WifiDirectPersistentGroupManager manager = new WifiDirectPersistentGroupManager(WifiDirectAgent.getInstance());
        manager.deleteAllPersistentGroups();
        AppUtil.getInstance().showToast(UILocation.BOTH, AppUtil.getDefContext().getString(R.string.toastWifiP2pRememberedGroupsCleared));
        }

    /**
     * @see FtcConfigurationActivity#doUSBScanAndUpdateUI()
     */
    protected void handleCommandScan(String extra) throws RobotCoreException, InterruptedException {
        RobotLog.vv(FtcConfigurationActivity.TAG, "handling command SCAN");

        // Start a scan and wait for it to complete, but if a scan is already in progress, then just wait for that one to finish
        final ThreadPool.SingletonResult<ScannedDevices> future = usbScanManager.startDeviceScanIfNecessary();

        // Actually carry out the scan in a worker thread so that we don't hold up the receive loop for
        // half-second or so that carrying out the scan will take.
        ThreadPool.getDefault().execute(new Runnable()
            {
            @Override public void run()
                {
                try
                    {
                    ScannedDevices scannedDevices = future.await();
                    if (scannedDevices==null) scannedDevices = new ScannedDevices();

                    // Package up the raw scanned device info and send that back to the DS
                    String data = usbScanManager.packageCommandResponse(scannedDevices);
                    RobotLog.vv(FtcConfigurationActivity.TAG, "handleCommandScan data='%s'", data);
                    networkConnectionHandler.sendCommand(new Command(CommandList.CMD_SCAN_RESP, data));
                    }
                catch (InterruptedException e)
                    {
                    Thread.currentThread().interrupt();
                    }
                }
            });
        }

    protected void handleCommandDiscoverLynxModules(String extra) throws RobotCoreException
        {
        RobotLog.vv(FtcConfigurationActivity.TAG, "handling command DiscoverLynxModules");
        final SerialNumber serialNumber = SerialNumber.fromString(extra);

        // Start a scan and wait for it to complete, but if a scan is already in progress, then just wait for that one to finish
        final ThreadPool.SingletonResult<LynxModuleMetaList> future = this.usbScanManager.startLynxModuleEnumerationIfNecessary(serialNumber);

        // Actually carry out the scan in a worker thread so that we don't hold up the receive loop for
        // full second or more that carrying out the discovery will take.
        ThreadPool.getDefault().execute(new Runnable()
            {
            @Override public void run()
                {
                try
                    {
                    LynxModuleMetaList lynxModules = future.await();
                    if (lynxModules==null) lynxModules = new LynxModuleMetaList(serialNumber);

                    // Package up the raw module list and send that back to the DS
                    String data = usbScanManager.packageCommandResponse(lynxModules);
                    RobotLog.vv(FtcConfigurationActivity.TAG, "DiscoverLynxModules data='%s'", data);
                    networkConnectionHandler.sendCommand(new Command(CommandList.CMD_DISCOVER_LYNX_MODULES_RESP, data));
                    }
                catch (InterruptedException e)
                    {
                    Thread.currentThread().interrupt();
                    }
                }
            });
        }

    /**
     * Update the firmware of the device indicated in the command.
     *
     * Note: we need to run this in a worker thread so that our command processor here
     * remains responsive and able to process new messages.
     */
    protected void handleCommandLynxFirmwareUpdate(final Command commandRequest)
        {
        RobotLog.vv(TAG, "handleCommandLynxFirmwareUpdate received");
        final CommandList.LynxFirmwareUpdate params = CommandList.LynxFirmwareUpdate.deserialize(commandRequest.getExtra());

        ThreadPool.getDefault().submit(new Runnable()
            {
            @Override public void run()
                {
                LynxFirmwareUpdateResp result = updateLynxFirmware(params.serialNumber, params.firmwareImageFile, params.originatorId);
                networkConnectionHandler.sendReply(commandRequest, new Command(CommandList.CMD_LYNX_FIRMWARE_UPDATE_RESP, result.serialize()));
                }
            });
        }

    /**
     * Updates the firmware of the Expansion Hub in the indicated USB-attached device
     * to be the indicated firmware.
     */
    protected LynxFirmwareUpdateResp updateLynxFirmware(final SerialNumber serialNumber, final CommandList.FWImage image, String requestId)
        {
        LynxFirmwareUpdateResp result = new LynxFirmwareUpdateResp();
        result.success = false;
        result.originatorId = requestId;

        final boolean updatingControlHub = serialNumber.isEmbedded();
        Consumer<ProgressParameters> progressConsumer = new Consumer<ProgressParameters>()
            {
            Double prevPercentComplete = null;
            @Override public void accept(ProgressParameters parameters)
                {
                double percentComplete = Math.round(parameters.fractionComplete() * 100);
                if (prevPercentComplete==null || prevPercentComplete != percentComplete)
                    {
                    prevPercentComplete = percentComplete;
                    String message;
                    if (updatingControlHub)
                        {
                        message = String.format(activityContext.getString(R.string.controlHubFirmwareUpdateMessage), image.getName());
                        }
                    else
                        {
                        message = String.format(activityContext.getString(R.string.expansionHubFirmwareUpdateMessage), serialNumber, image.getName());
                        }
                    AppUtil.getInstance().showProgress(UILocation.BOTH,
                            message,
                            parameters.fractionComplete(),
                            100);
                    }
                }
            };

        try {
            // Show an indeterminate progress bar, in case we aren't able to start the flashing process right away
            progressConsumer.accept(new ProgressParameters(0, 1));
            final LynxUsbDevice lynxUsbDevice = getLynxUsbDeviceForFirmwareUpdate(serialNumber);
            if (lynxUsbDevice != null)
                {
                try {
                    result = lynxUsbDevice.updateFirmware(image, requestId, progressConsumer);
                    }
                finally
                    {
                    lynxUsbDevice.close();
                    }
                }
            else
                {
                RobotLog.ee(TAG, "unable to obtain lynx usb device for fw update: %s", serialNumber);
                }
            }
        finally
            {
            AppUtil.getInstance().dismissProgress(UILocation.BOTH);
            }
        RobotLog.vv(TAG, "updateLynxFirmware(%s, %s): result=%s", serialNumber, image.getName(), result.serialize());
        return result;
        }

    protected void handleCommandGetUSBAccessibleLynxModules(final Command commandRequest)
        {
        ThreadPool.getDefault().execute(new Runnable()
            {
            @Override public void run()
                {
                CommandList.USBAccessibleLynxModulesRequest request = CommandList.USBAccessibleLynxModulesRequest.deserialize(commandRequest.getExtra());
                ArrayList<USBAccessibleLynxModule> modules = new ArrayList<USBAccessibleLynxModule>();
                try {
                    modules.addAll(getUSBAccessibleLynxDevices(request.forFirmwareUpdate));
                    }
                catch (RobotCoreException ignored)
                    {
                    }
                Collections.sort(modules, new Comparator<USBAccessibleLynxModule>()
                    {
                    @Override public int compare(USBAccessibleLynxModule lhs, USBAccessibleLynxModule rhs)
                        {
                        return lhs.getSerialNumber().getString().compareTo(rhs.getSerialNumber().getString());
                        }
                    });
                CommandList.USBAccessibleLynxModulesResp resp = new CommandList.USBAccessibleLynxModulesResp();
                resp.modules = modules;
                networkConnectionHandler.sendReply(commandRequest, new Command(CommandList.CMD_GET_USB_ACCESSIBLE_LYNX_MODULES_RESP, resp.serialize()));
                }
            });
        }

    protected LynxUsbDevice getLynxUsbDeviceForFirmwareUpdate(SerialNumber serialNumber)
        {
        try
            {
            return (LynxUsbDevice) usbScanManager.getDeviceManager().createLynxUsbDevice(serialNumber, null);
            }
        catch (RobotCoreException e)
            {
            RobotLog.ee(TAG, e, "getLynxUsbDeviceForFirmwareUpdate(): exception opening lynx usb device: %s", serialNumber);
            }
        catch (InterruptedException e)
            {
            Thread.currentThread().interrupt();
            RobotLog.ee(TAG, "Thread interrupted in getLynxUsbDeviceForFirmwareUpdate");
            }
        return null;
        }

    protected List<USBAccessibleLynxModule> getUSBAccessibleLynxDevices(boolean forFirmwareUpdate) throws RobotCoreException
        {
        RobotLog.vv(TAG, "getUSBAccessibleLynxDevices(includeModuleAddresses=%s)...", forFirmwareUpdate);

        // We do a raw, low level scan, not caring what's in the current hardware map, if anything.
        // This is important: a module might, for example, be in a state where it previously had a
        // failed firmware update, and all that's running is its bootloader. Such a beast would be
        // unable to respond to a high level scan.
        USBScanManager scanManager = USBScanManager.getInstance();
        final ThreadPool.SingletonResult<ScannedDevices> future = scanManager.startDeviceScanIfNecessary();
        try {
            ScannedDevices scannedDevices = future.await();
            List<USBAccessibleLynxModule> result = new ArrayList<USBAccessibleLynxModule>();

            // Return everything returned by the scan
            for (Map.Entry<SerialNumber,DeviceManager.UsbDeviceType> entry : scannedDevices.entrySet())
                {
                if (entry.getValue() == DeviceManager.UsbDeviceType.LYNX_USB_DEVICE)
                    {
                    SerialNumber serialNumber = entry.getKey();
                    result.add(new USBAccessibleLynxModule(serialNumber));
                    }
                }

            // Return the embedded module if we're supposed to and if it wasn't already there (it might be absent if it's bricked)
            if (LynxConstants.isRevControlHub())
                {
                boolean found = false;
                for (USBAccessibleLynxModule module : result)
                    {
                    if (module.getSerialNumber().equals(LynxConstants.SERIAL_NUMBER_EMBEDDED))
                        {
                        found = true;
                        break;
                        }
                    }
                if (!found)
                    {
                    result.add(new USBAccessibleLynxModule(LynxConstants.SERIAL_NUMBER_EMBEDDED));
                    }
                }

            for (USBAccessibleLynxModule module : result)
                {
                RobotLog.vv(TAG, "getUSBAccessibleLynxDevices: found serial=%s", module.getSerialNumber());
                }

            // Add additional information if we're asked to
            if (forFirmwareUpdate)
                {
                RobotLog.vv(TAG, "finding module addresses and current firmware versions");
                for (int i = 0; i < result.size(); )
                    {
                    final USBAccessibleLynxModule usbModule = result.get(i);
                    RobotLog.vv(TAG, "getUSBAccessibleLynxDevices: finding module address for usbModule %s", usbModule.getSerialNumber());
                    LynxUsbDevice lynxUsbDevice = (LynxUsbDevice) scanManager.getDeviceManager().createLynxUsbDevice(usbModule.getSerialNumber(), null);
                    try {
                        // Discover all the modules on us, sure, but we're really only interested in
                        // *our* address. At the same time, we'd like to talk to the lynx module on
                        // lynxUsbDevice while it's already open, just for a moment. Finally, be aware
                        // that when we're talking to a bricked hub that discovery comes back empty.
                        LynxModuleMetaList lynxModuleMetas = lynxUsbDevice.discoverModules(false);

                        // Find *our* module address in the metadata, if we can. Further, if there
                        // were states or configurations that a module might be in which it was ineligible
                        // for firmware update, we should check for them here. Historically, we thought that
                        // that might include having a child module attached, but testing and a bit of more
                        // careful thought shows that having a child isn't a problem.
                        boolean foundParent = false;
                        usbModule.setModuleAddress(0);
                        for (LynxModuleMeta meta : lynxModuleMetas)
                            {
                            RobotLog.vv(TAG,"assessing %s", meta);
                            if (meta.getModuleAddress()==0) // paranoia
                                {
                                RobotLog.vv(TAG, "ignoring module with address zero");
                                continue;
                                }
                            if (meta.isParent())
                                {
                                // It's us!
                                foundParent = true;
                                usbModule.setModuleAddress(meta.getModuleAddress());
                                }
                            else
                                {
                                // We've got child modules connected
                                }
                            }

                        // As of this writing, there's no known reason we shouldn't try to update any
                        // module that we in fact happen to run across.
                        boolean okToUpdateFirmware = true;

                        // Find his *current* fw version if we can
                        usbModule.setFirmwareVersionString("");
                        if (okToUpdateFirmware && foundParent)
                            {
                            try {
                                lynxUsbDevice.performSystemOperationOnParentModule(usbModule.getModuleAddress(), lynxModule ->
                                    {
                                    String fw = lynxModule.getNullableFirmwareVersionString();
                                    if (fw != null)
                                        {
                                        usbModule.setFirmwareVersionString(fw);
                                        }
                                    else
                                        RobotLog.ee(TAG, "getUSBAccessibleLynxDevices(): fw returned null");
                                    }, 250, TimeUnit.MILLISECONDS);
                                }
                            catch (RobotCoreException | TimeoutException e)
                                {
                                RobotLog.ee(TAG, e, "exception retrieving fw version; ignoring");
                                }
                            }

                        if (okToUpdateFirmware)
                            {
                            i++;    // advance to next usb accessible module
                            }
                        else
                            {
                            RobotLog.vv(TAG, "getUSBAccessibleLynxDevices: culled serial=%s", usbModule.getSerialNumber());
                            result.remove(i);
                            }
                        }
                    finally
                        {
                        if (lynxUsbDevice != null) lynxUsbDevice.close();
                        }
                    }
                }

            RobotLog.vv(TAG, "getUSBAccessibleLynxDevices(): %d modules found", result.size());
            return result;
            }
        catch (InterruptedException e)
            {
            Thread.currentThread().interrupt();
            return new ArrayList<USBAccessibleLynxModule>();
            }
        finally
            {
            RobotLog.vv(TAG, "...getUSBAccessibleLynxDevices()");
            }
        }

    protected void handleCommandLynxChangeModuleAddresses(final Command commandRequest)
        {
        ThreadPool.getDefault().execute(new Runnable()
            {
            @Override public void run()
                {
                try {
                    boolean success = true;
                    try {
                        CommandList.LynxAddressChangeRequest changeRequest = CommandList.LynxAddressChangeRequest.deserialize(commandRequest.getExtra());
                        DeviceManager deviceManager = usbScanManager.getDeviceManager();
                        for (final CommandList.LynxAddressChangeRequest.AddressChange addressChange : changeRequest.modulesToChange)
                            {
                            RobotLog.vv(TAG, "lynx module connected to portal %s (parent address %d): change address %d -> %d", addressChange.serialNumber, addressChange.parentAddress, addressChange.oldAddress, addressChange.newAddress);
                            LynxUsbDevice lynxUsbDevice = (LynxUsbDevice)deviceManager.createLynxUsbDevice(addressChange.serialNumber, null);
                            try {
                                lynxUsbDevice.performSystemOperationOnConnectedModule(
                                        addressChange.oldAddress,
                                        addressChange.parentAddress,
                                        lynxModule -> lynxModule.setNewModuleAddress(addressChange.newAddress),
                                        250,
                                        TimeUnit.MILLISECONDS);
                                }
                            catch (RobotCoreException | TimeoutException e)
                                {
                                RobotLog.ee(TAG, e, "failure during module address change");
                                AppUtil.getInstance().showToast(UILocation.BOTH, activityContext.getString(R.string.toastLynxAddressChangeFailed, addressChange.serialNumber));
                                success = false;
                                if (e instanceof RobotCoreException)
                                    {
                                    throw (RobotCoreException) e;
                                    }
                                throw new RobotCoreException("Failed to change module address", e);
                                }
                            finally
                                {
                                if (lynxUsbDevice != null) lynxUsbDevice.close();
                                }
                            }
                        }
                    catch (RobotCoreException ignored)
                        {
                        }
                    finally
                        {
                        networkConnectionHandler.sendReply(commandRequest, new Command(CommandList.CMD_LYNX_ADDRESS_CHANGE_FINISHED));
                        if (success) AppUtil.getInstance().showToast(UILocation.BOTH, activityContext.getString(R.string.toastLynxAddressChangeComplete));
                        }
                    }
                catch (InterruptedException e)
                    {
                    Thread.currentThread().interrupt();
                    }
                }
            });
        }

    protected void handleCommandGetCandidateLynxFirmwareImages(Command commandRequest)
        {
        // We return the full path to files with a .bin extension, case insensitive
        final Pattern pattern = Pattern.compile("(?i).*\\.bin");

        // We look for files in the file system on the robot controller
        File firmwareDir = AppUtil.LYNX_FIRMWARE_UPDATE_DIR;
        File[] candidateFiles = firmwareDir.listFiles(new FileFilter()
            {
            @Override public boolean accept(File pathname)
                {
                Assert.assertTrue(pathname.isAbsolute());
                return pattern.matcher(pathname.getName()).matches();
                }
            });

        CommandList.LynxFirmwareImagesResp respParams = new CommandList.LynxFirmwareImagesResp();
        respParams.firstFolder = AppUtil.FIRST_FOLDER;
        for (File candidate : candidateFiles)
            {
            respParams.firmwareImages.add(new CommandList.FWImage(candidate, false));
            }

        // We also look for files in our assets
        try {
            File fromBase = new File(firmwareDir.getParentFile().getName(), firmwareDir.getName());
            String[] firmwareAssets = activityContext.getAssets().list(fromBase.getPath());
            for (String firmwareAsset : firmwareAssets)
                {
                if (pattern.matcher(firmwareAsset).matches())
                    {
                    File file = new File(fromBase, firmwareAsset);
                    Assert.assertTrue(!file.isAbsolute());
                    respParams.firmwareImages.add(new CommandList.FWImage(file, true));
                    }
                }
            }
        catch (IOException ignore)
            {
            }

        // Return all those, unsorted
        networkConnectionHandler.sendReply(commandRequest, new Command(CommandList.CMD_GET_CANDIDATE_LYNX_FIRMWARE_IMAGES_RESP, respParams.serialize()));
        }

    /**
     * Serialize the entire list of config file metadata and send to the driver station
     */
    protected void handleCommandRequestConfigurationTemplates()
        {
        ArrayList<RobotConfigFile> fileList = robotCfgFileMgr.getXMLTemplates();
        String objsSerialized = RobotConfigFileManager.serializeXMLConfigList(fileList);
        networkConnectionHandler.sendCommand(new Command(CommandList.CMD_REQUEST_CONFIGURATION_TEMPLATES_RESP, objsSerialized));
        }

    protected void handleCommandStartDriverStationProgramAndManage()
        {
        EventLoopManager eventLoopManager = ftcEventLoopHandler.getEventLoopManager();
        if (eventLoopManager != null)
            {
            WebServer webServer = eventLoopManager.getWebServer();
            String extra = webServer.getConnectionInformation().toJson();
            RobotLog.vv(TAG, "sending p&m resp: %s", extra);
            networkConnectionHandler.sendCommand(new Command(CommandList.CMD_START_DS_PROGRAM_AND_MANAGE_RESP, extra));
            }
        else
            {
            RobotLog.vv(TAG, "handleCommandStartDriverStationProgramAndManage() with null EventLoopManager; ignored");
            }
        }

    protected void handleCommandShowDialog(Command command)
        {
        RobotCoreCommandList.ShowDialog showDialog = RobotCoreCommandList.ShowDialog.deserialize(command.getExtra());
        AppUtil.DialogParams params = new AppUtil.DialogParams(UILocation.ONLY_LOCAL, showDialog.title, showDialog.message);
        params.uuidString = showDialog.uuidString;
        AppUtil.getInstance().showDialog(params);
        }

    protected void handleCommandDismissDialog(Command command)
        {
        AppUtil.getInstance().dismissDialog(UILocation.ONLY_LOCAL, RobotCoreCommandList.DismissDialog.deserialize(command.getExtra()));
        }
    protected void handleCommandDismissAllDialogs(Command command)
        {
        AppUtil.getInstance().dismissAllDialogs(UILocation.ONLY_LOCAL);
        }

    protected void handleCommandShowProgress(Command command)
        {
        RobotCoreCommandList.ShowProgress showProgress = RobotCoreCommandList.ShowProgress.deserialize(command.getExtra());
        AppUtil.getInstance().showProgress(UILocation.ONLY_LOCAL, showProgress.message, showProgress);
        }

    protected void handleCommandDismissProgress(Command command)
        {
        AppUtil.getInstance().dismissProgress(UILocation.ONLY_LOCAL);
        }

    protected void handleCommandShowToast(Command command)
        {
        RobotCoreCommandList.ShowToast showToast = RobotCoreCommandList.ShowToast.deserialize(command.getExtra());
        AppUtil.getInstance().showToast(UILocation.ONLY_LOCAL, showToast.message, showToast.duration);
        }

    /**
     * Return an inspection report of this (robot controller) device back to the caller
     */
    protected void handleCommandRequestInspectionReport()
        {
        InspectionState inspectionState = new InspectionState();
        inspectionState.initializeLocal();
        String serialized = inspectionState.serialize();
        networkConnectionHandler.sendCommand(new Command(CommandList.CMD_REQUEST_INSPECTION_REPORT_RESP, serialized));
        }

    protected void handleCommandDisableBluetooth()
        {
        AppUtil.getInstance().setBluetoothEnabled(false);
        }

    protected void handleCommandRequestAboutInfo(Command command)
        {
        RobotCoreCommandList.AboutInfo aboutInfo = FtcAboutActivity.getLocalAboutInfo();
        String serialized = aboutInfo.serialize();
        networkConnectionHandler.sendCommand(new Command(CommandList.CMD_REQUEST_ABOUT_INFO_RESP, serialized));
        }

    protected void handleCommandDisconnectWifiDirect()
        {
        if (WifiDirectAgent.getInstance().disconnectFromWifiDirect())
            {
            AppUtil.getInstance().showToast(UILocation.BOTH, AppUtil.getDefContext().getString(R.string.toastDisconnectedFromWifiDirect));
            }
        else
            {
            AppUtil.getInstance().showToast(UILocation.BOTH, AppUtil.getDefContext().getString(R.string.toastErrorDisconnectingFromWifiDirect));
            }
        }

    protected CallbackResult handleCommandVisuallyIdentify(Command command)
        {
        final CommandList.CmdVisuallyIdentify cmdVisuallyIdentify = CommandList.CmdVisuallyIdentify.deserialize(command.getExtra());
        ThreadPool.getDefaultSerial().execute(() -> // serial so that 'off' identifies don't re-order
                VisualIdentificationManager.getInstance().handleCommandVisuallyIdentify(cmdVisuallyIdentify));
        return CallbackResult.HANDLED;
        }

    protected CallbackResult handleCommandVisuallyConfirmWifiReset()
        {
        if (!LynxConstants.isRevControlHub()) return CallbackResult.HANDLED;
        ThreadPool.getDefaultSerial().execute(new Runnable() {
            @Override public void run() {
                LynxModule embeddedModule = (LynxModule) EmbeddedControlHubModule.get();
                if (embeddedModule != null) {
                    List<Blinker.Step> confirmButtonPressPattern = new ArrayList<>();
                    confirmButtonPressPattern.add(new Blinker.Step(Color.MAGENTA, 100, TimeUnit.MILLISECONDS));
                    confirmButtonPressPattern.add(new Blinker.Step(Color.YELLOW, 100, TimeUnit.MILLISECONDS));
                    confirmButtonPressPattern.add(new Blinker.Step(Color.CYAN, 100, TimeUnit.MILLISECONDS));
                    confirmButtonPressPattern.add(new Blinker.Step(Color.RED, 100, TimeUnit.MILLISECONDS));

                    embeddedModule.pushPattern(confirmButtonPressPattern);
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        RobotLog.ee(TAG, e, "Thread interrupted while visually confirming Wi-Fi reset");
                        Thread.currentThread().interrupt();
                    }
                    embeddedModule.popPattern();
                }
            }
        });
        return CallbackResult.HANDLED;
        }

    protected CallbackResult handleCommandVisuallyConfirmWifiBandSwitch(Command command)
        {
        if (!LynxConstants.isRevControlHub()) return CallbackResult.HANDLED;

        final int newBand = Integer.parseInt(command.getExtra());
        ThreadPool.getDefaultSerial().execute(new Runnable() {
            @Override public void run() {
                LynxModule embeddedModule = (LynxModule) EmbeddedControlHubModule.get();
                if (embeddedModule != null) {
                    int bandColor;
                    if (newBand == ApChannel.AP_BAND_5GHZ) {
                        bandColor = Color.MAGENTA;
                    } else {
                        bandColor = Color.YELLOW;
                    }

                    List<Blinker.Step> confirmBandSwitchPattern = new ArrayList<>();
                    confirmBandSwitchPattern.add(new Blinker.Step(bandColor, 200, TimeUnit.MILLISECONDS));
                    confirmBandSwitchPattern.add(new Blinker.Step(Color.BLACK, 100, TimeUnit.MILLISECONDS));

                    embeddedModule.pushPattern(confirmBandSwitchPattern);
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        RobotLog.ee(TAG, e, "Thread interrupted while visually confirming Wi-Fi band switch");
                        Thread.currentThread().interrupt();
                    }
                    embeddedModule.popPattern();
                }
            }
        });
        return CallbackResult.HANDLED;
        }
    }

/*
Copyright (c) 2023 REV Robotics

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of REV Robotics nor the names of its contributors may be used to
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
package org.firstinspires.ftc.ftccommon.internal.manualcontrol;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.ftccommon.configuration.USBScanManager;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxNackException;
import com.qualcomm.hardware.lynx.commands.core.LynxReadVersionStringCommand;
import com.qualcomm.hardware.lynx.commands.standard.LynxFailSafeCommand;
import com.qualcomm.hardware.lynx.commands.standard.LynxQueryInterfaceCommand;
import com.qualcomm.hardware.lynx.commands.standard.LynxQueryInterfaceResponse;
import com.qualcomm.robotcore.hardware.DeviceManager;
import com.qualcomm.robotcore.hardware.LynxModuleMeta;
import com.qualcomm.robotcore.hardware.LynxModuleMetaList;
import com.qualcomm.robotcore.hardware.ScannedDevices;
import com.qualcomm.robotcore.util.SerialNumber;
import com.qualcomm.robotcore.util.ThreadPool;
import com.qualcomm.robotcore.util.WebHandlerManager;

import org.firstinspires.ftc.ftccommon.external.WebHandlerRegistrar;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands.AnalogCommands;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands.BulkInputCommand;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands.DigitalCommands;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands.I2cCommands;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands.ImuCommands;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands.LedCommands;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands.LogCommands;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands.MotorCommands;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands.ServoCommands;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.FailedToOpenHubException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidDeviceIdException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidParameterException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.ManualControlLockedException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.OnlyLocalConnectionsAllowedException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.UserOpModeRunningException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.WebSocketNotAuthorizedForManualControlException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.WebSocketNotSubscribedException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.HandleIdParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.OpenHubParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.QueryInterfaceParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.SetHubAddressParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.HubType;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.ManualControlApiVersion;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.RhspInterface;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.ParentHub;
import org.firstinspires.ftc.robotcore.internal.hardware.CachedLynxModulesInfo;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.FtcWebSocket;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.FtcWebSocketMessage;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketManager;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketMessageTypeHandler;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketNamespaceHandler;
import org.firstinspires.ftc.robotserver.internal.webserver.websockets.command.WebSocketCommandHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ManualControlWebSocketHandler extends WebSocketNamespaceHandler {
    // TODO(Noah): Notify when a Hub stops responding

    //----------------------------------------------------------------------------------------------
    // Static state and methods
    //----------------------------------------------------------------------------------------------
    public static final String WS_NAMESPACE = "MC";
    private static final ManualControlApiVersion API_VERSION = new ManualControlApiVersion(1, 0);
    @Nullable private static volatile ManualControlWebSocketHandler instance;

    @WebHandlerRegistrar
    @SuppressWarnings("unused")
    public static void registerNamespace(Context context, WebHandlerManager webHandlerManager) {
        WebSocketManager webSocketManager = webHandlerManager.getWebServer().getWebSocketManager();
        ManualControlWebSocketHandler newInstance = new ManualControlWebSocketHandler(webSocketManager);
        instance = newInstance;
        webSocketManager.registerNamespaceHandler(newInstance);
    }

    @Nullable public static ManualControlWebSocketHandler getInstance() { return instance; }

    //----------------------------------------------------------------------------------------------
    // Instance state and methods
    //----------------------------------------------------------------------------------------------
    private final WebSocketManager webSocketManager;
    private final Object allowedWebSocketLock = new Object();
    @Nullable private FtcWebSocket allowedWebSocket = null;

    public ManualControlWebSocketHandler(WebSocketManager webSocketManager) {
        super(WS_NAMESPACE);
        this.webSocketManager = webSocketManager;
    }

    public void checkIfWebSocketIsPermittedToControlHardware(FtcWebSocket webSocket)
            throws WebSocketNotAuthorizedForManualControlException {
        synchronized (allowedWebSocketLock) {
            if (webSocket != allowedWebSocket) {
                throw new WebSocketNotAuthorizedForManualControlException();
            }
        }
    }

    public static final String HUB_STATUS_CHANGED_NOTIFICATION = "hubStatusChanged";
    public static final String HUB_ADDRESS_CHANGED_NOTIFICATION = "hubAddressChanged";
    public static final String SESSION_ENDED_NOTIFICATION = "sessionEnded";

    @Override
    protected void registerMessageTypeHandlers(Map<String, WebSocketMessageTypeHandler> messageTypeHandlerMap) {
        super.registerMessageTypeHandlers(messageTypeHandlerMap);

        messageTypeHandlerMap.put("start", new StartCommand());
        messageTypeHandlerMap.put("stop", new StopCommand(this));
        messageTypeHandlerMap.put("scanAndDiscover", new ScanAndDiscoverCommand(this));
        messageTypeHandlerMap.put("openHub", new OpenHubCommand(this));
        messageTypeHandlerMap.put("closeHub", new CloseHubCommand(this));
        messageTypeHandlerMap.put("getHubFwVersionString", new GetHubFirmwareVersionStringCommand(this));
        messageTypeHandlerMap.put("queryInterface", new QueryInterfaceCommand(this));
        messageTypeHandlerMap.put("sendFailSafe", new SendFailSafeCommand(this));
        messageTypeHandlerMap.put("setHubAddress", new SetHubAddressCommand(this));

        ImuCommands.register(messageTypeHandlerMap, this);
        MotorCommands.register(messageTypeHandlerMap, this);
        ServoCommands.register(messageTypeHandlerMap, this);
        AnalogCommands.register(messageTypeHandlerMap, this);
        DigitalCommands.register(messageTypeHandlerMap, this);
        I2cCommands.register(messageTypeHandlerMap, this);
        BulkInputCommand.register(messageTypeHandlerMap, this);
        LedCommands.register(messageTypeHandlerMap, this);
        LogCommands.register(messageTypeHandlerMap, this);
    }

    @Override public void onUnsubscribe(FtcWebSocket webSocket) {
        super.onUnsubscribe(webSocket);
        synchronized (allowedWebSocketLock) {
            if (webSocket == allowedWebSocket) {
                stopManualControl();
            }
        }
    }

    public void sendMessageToAllowedWebSocket(FtcWebSocketMessage message) {
        synchronized (allowedWebSocketLock) {
            if (allowedWebSocket != null) {
                allowedWebSocket.send(message);
            }
        }
    }

    // Idempotent
    public void stopManualControl() {
        sendMessageToAllowedWebSocket(new FtcWebSocketMessage(WS_NAMESPACE, SESSION_ENDED_NOTIFICATION));
        synchronized (allowedWebSocketLock) {
            allowedWebSocket = null;
        }
        ManualControlOpMode opModeInstance = ManualControlOpMode.getInstance();
        if (opModeInstance != null) {
            // This is safe to call even if a different OpMode is already running
            opModeInstance.requestOpModeStop();
        }
    }

    // Does NOT extend ManualControlCommandHandler, because allowedWebSocket gets handled differently here
    private class StartCommand extends WebSocketCommandHandler<Void, ManualControlApiVersion> {
        @NonNull @Override protected Class<Void> getPayloadClass() { return Void.class; }
        @NonNull @Override protected Class<ManualControlApiVersion> getResultClass() { return ManualControlApiVersion.class; }

        @Override
        public ManualControlApiVersion handleCommand(Void value, FtcWebSocket webSocket) throws
                UserOpModeRunningException,
                ManualControlLockedException,
                OnlyLocalConnectionsAllowedException,
                WebSocketNotSubscribedException {

            // At this time, only connections from ADB or localhost are permitted
            if (!webSocket.getRemoteIpAddress().isLoopbackAddress()) {
                throw new OnlyLocalConnectionsAllowedException();
            }

            // Only allow connections that have subscribed to this namespace, so that we can track
            // when they have unsubscribed/close
            if (!webSocketManager.webSocketIsSubscribedToNamespace(WS_NAMESPACE, webSocket)) {
                throw new WebSocketNotSubscribedException();
            }

            synchronized (allowedWebSocketLock) {
                if (allowedWebSocket == null) {
                    allowedWebSocket = webSocket;
                } else if (allowedWebSocket != webSocket) {
                    throw new ManualControlLockedException();
                }
            }

            try {
                ManualControlOpMode.startOpModeAndWait();
                return API_VERSION;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class StopCommand extends ManualControlCommandHandler<Void, Void> {
        public StopCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<Void> getPayloadClass() { return Void.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void handleManualControlCommand(Void value) {
            manualControlWebSocketHandler.stopManualControl();
            return null;
        }
    }

    private static class ScanAndDiscoverCommand extends ManualControlCommandHandler<Void, Map<String, ParentHub>> {
        public ScanAndDiscoverCommand(ManualControlWebSocketHandler handler) { super(handler); }

        @Override
        public Map<String, ParentHub> handleManualControlCommand(Void parameters) throws InterruptedException {
            final Map<String, ParentHub> result = new HashMap<>();
            final ScannedDevices scannedDevices = USBScanManager.getInstance().startDeviceScanIfNecessary().await();

            if (scannedDevices != null) {
                Map<SerialNumber, ThreadPool.SingletonResult<LynxModuleMetaList>> pendingDiscoveryResults = new HashMap<>();
                // Start discovery on all hubs
                for (Map.Entry<SerialNumber, DeviceManager.UsbDeviceType> entry : scannedDevices.entrySet()) {
                    SerialNumber serialNumber = entry.getKey();
                    if (entry.getValue() == DeviceManager.UsbDeviceType.LYNX_USB_DEVICE) {
                        pendingDiscoveryResults.put(serialNumber, USBScanManager.getInstance().startLynxModuleEnumerationIfNecessary(serialNumber));
                    }
                }
                // Wait for discovery to finish for all hubs
                for (Map.Entry<SerialNumber, ThreadPool.SingletonResult<LynxModuleMetaList>> entry: pendingDiscoveryResults.entrySet()) {
                    SerialNumber serialNumber = entry.getKey();
                    final LynxModuleMetaList modules = entry.getValue().await();
                    if (modules != null) {
                        final LynxModuleMeta parent = modules.getParent();
                        if (parent != null) {
                            final int parentAddress = parent.getModuleAddress();
                            final List<Integer> childAddresses = new LinkedList<>();
                            for (LynxModuleMeta module : modules.modules) {
                                if (!module.isParent()) {
                                    childAddresses.add(module.getModuleAddress());
                                }
                            }
                            HubType hubType = serialNumber.isEmbedded() ? HubType.CONTROL_HUB : HubType.EXPANSION_HUB;
                            String serialString = serialNumber.getString();
                            result.put(serialString, new ParentHub(serialString, hubType, parentAddress, childAddresses));
                        }
                    }
                }
            }
            return result;
        }

        @NonNull @Override protected Class<Void> getPayloadClass() { return Void.class; }
        @NonNull @Override protected Class<Map<String, ParentHub>> getResultClass() {
            // This is fairly gross, but it's what's required
            //noinspection unchecked
            return (Class<Map<String, ParentHub>>) (Class<?>) Map.class;
        }
    }

    private static class OpenHubCommand extends ManualControlCommandHandler<OpenHubParameters, Integer> {
        public OpenHubCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<OpenHubParameters> getPayloadClass() { return OpenHubParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        /**
         * Returns a unique-to-this-RC-app value that can be used to identify a given REV Hub.
         * <p>
         * Clients must NOT use the returned value in any way except to pass it to other commands.
         * The type of the returned value is not guaranteed to be stable.
         */
        @Override
        public Integer handleManualControlCommand(OpenHubParameters parameters) throws InterruptedException, FailedToOpenHubException, InvalidParameterException {
            return ManualControlOpMode.getInstance().openHubAndGetHandleId(parameters);
        }
    }

    private static class CloseHubCommand extends ManualControlCommandHandler<HandleIdParameters, Void> {
        public CloseHubCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override public Void handleManualControlCommand(HandleIdParameters parameters) throws InvalidParameterException, InvalidDeviceIdException {
            ManualControlOpMode.getInstance().closeSystemOperationHandle(parameters.getHandleId());
            return null;
        }
    }

    private static class GetHubFirmwareVersionStringCommand extends ManualControlDeviceCommandHandler<HandleIdParameters, String> {
        public GetHubFirmwareVersionStringCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
        @NonNull @Override protected Class<String> getResultClass() { return String.class; }

        @Override
        public String performOperationOnDevice(LynxModule module, HandleIdParameters parameters) throws LynxNackException, InterruptedException {
            LynxReadVersionStringCommand command = new LynxReadVersionStringCommand(module);
            String rawString = command.sendReceive().getNullableVersionString();
            if (rawString == null) { return "unknown"; }
            return CachedLynxModulesInfo.formatFirmwareVersion(rawString);
        }
    }

    private static class QueryInterfaceCommand extends ManualControlDeviceCommandHandler<QueryInterfaceParameters, RhspInterface> {
        public QueryInterfaceCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<QueryInterfaceParameters> getPayloadClass() { return QueryInterfaceParameters.class; }
        @NonNull @Override protected Class<RhspInterface> getResultClass() { return RhspInterface.class; }

        @Override
        public RhspInterface performOperationOnDevice(LynxModule module, QueryInterfaceParameters value) throws LynxNackException, InterruptedException, InvalidParameterException {
            String name = value.getInterfaceName();
            LynxQueryInterfaceCommand command = new LynxQueryInterfaceCommand(module, name);
            LynxQueryInterfaceResponse response = command.sendReceive();
            return new RhspInterface(name, response.getCommandNumberFirst(),
                    response.getNumberOfCommands());
        }
    }

    private static class SendFailSafeCommand extends ManualControlDeviceCommandHandler<HandleIdParameters, Void> {
        public SendFailSafeCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, HandleIdParameters parameters) throws LynxNackException, InterruptedException {
            LynxFailSafeCommand command = new LynxFailSafeCommand(module);
            command.send();
            return null;
        }
    }

    private static class SetHubAddressCommand extends ManualControlDeviceCommandHandler<SetHubAddressParameters, Void> {
        public SetHubAddressCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<SetHubAddressParameters> getPayloadClass() { return SetHubAddressParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, SetHubAddressParameters parameters) throws InvalidParameterException {
            if (module.getSerialNumber().isEmbedded() && module.isParent()) {
                throw new InvalidParameterException("The Control Hub's address cannot be changed");
            }
            module.setNewModuleAddress(parameters.getNewAddress());
            return null;
        }
    }
}

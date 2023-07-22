package org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands;

import androidx.annotation.NonNull;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxNackException;
import com.qualcomm.hardware.lynx.commands.core.LynxGetServoConfigurationCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetServoEnableCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetServoPulseWidthCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetServoConfigurationCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetServoEnableCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetServoPulseWidthCommand;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlDeviceCommandHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlWebSocketHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.ServoChannelParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.ServoConfigurationParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.ServoPulseWidthParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.SetServoEnableParameters;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketCommandException;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketMessageTypeHandler;

import java.util.Map;

public class ServoCommands {

    public static void register(Map<String, WebSocketMessageTypeHandler> handlerMap, ManualControlWebSocketHandler handler) {
        handlerMap.put("getServoConfiguration", new GetServoConfigurationCommand(handler));
        handlerMap.put("setServoConfiguration", new SetServoConfigurationCommand(handler));
        handlerMap.put("getServoPulseWidth", new GetServoPulseWidthCommand(handler));
        handlerMap.put("setServoPulseWidth", new SetServoPulseWidthCommand(handler));
        handlerMap.put("getServoEnable", new GetServoEnableCommand(handler));
        handlerMap.put("setServoEnable", new SetServoEnableCommand(handler));
    }

    private static class GetServoConfigurationCommand extends ManualControlDeviceCommandHandler<ServoChannelParameters, Integer> {
        public GetServoConfigurationCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<ServoChannelParameters> getPayloadClass() { return ServoChannelParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, ServoChannelParameters params) throws LynxNackException, InterruptedException, WebSocketCommandException {
            LynxGetServoConfigurationCommand command = new LynxGetServoConfigurationCommand(module, params.getServoChannel());
            return command.sendReceive().getFramePeriod();
        }
    }

    private static class SetServoPulseWidthCommand extends ManualControlDeviceCommandHandler<ServoPulseWidthParameters, Void> {
        public SetServoPulseWidthCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<ServoPulseWidthParameters> getPayloadClass() { return ServoPulseWidthParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, ServoPulseWidthParameters params) throws LynxNackException, InterruptedException, WebSocketCommandException {
            LynxSetServoPulseWidthCommand command = new LynxSetServoPulseWidthCommand(module, params.getServoChannel(), params.getPulseWidth());
            command.send();
            return null;
        }
    }

    private static class GetServoPulseWidthCommand extends ManualControlDeviceCommandHandler<ServoChannelParameters, Integer> {
        public GetServoPulseWidthCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<ServoChannelParameters> getPayloadClass() { return ServoChannelParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, ServoChannelParameters params) throws LynxNackException, InterruptedException, WebSocketCommandException {
            LynxGetServoPulseWidthCommand command = new LynxGetServoPulseWidthCommand(module, params.getServoChannel());
            return command.sendReceive().getPulseWidth();
        }
    }

    private static class SetServoEnableCommand extends ManualControlDeviceCommandHandler<SetServoEnableParameters, Void> {
        public SetServoEnableCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<SetServoEnableParameters> getPayloadClass() { return SetServoEnableParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, SetServoEnableParameters params) throws LynxNackException, InterruptedException, WebSocketCommandException {
            LynxSetServoEnableCommand command = new LynxSetServoEnableCommand(module, params.getServoChannel(), params.getEnable());
            command.send();
            return null;
        }
    }

    private static class GetServoEnableCommand extends ManualControlDeviceCommandHandler<ServoChannelParameters, Boolean> {
        public GetServoEnableCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<ServoChannelParameters> getPayloadClass() { return ServoChannelParameters.class; }
        @NonNull @Override protected Class<Boolean> getResultClass() { return Boolean.class; }

        @Override
        public Boolean performOperationOnDevice(LynxModule module, ServoChannelParameters params) throws LynxNackException, InterruptedException, WebSocketCommandException {
            LynxGetServoEnableCommand command = new LynxGetServoEnableCommand(module, params.getServoChannel());
            return command.sendReceive().isEnabled();
        }
    }

    private static class SetServoConfigurationCommand extends ManualControlDeviceCommandHandler<ServoConfigurationParameters, Void> {
        public SetServoConfigurationCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<ServoConfigurationParameters> getPayloadClass() { return ServoConfigurationParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, ServoConfigurationParameters params) throws LynxNackException, InterruptedException, WebSocketCommandException {
            LynxSetServoConfigurationCommand command = new LynxSetServoConfigurationCommand(module, params.getServoChannel(), params.getFramePeriod());
            command.send();
            return null;
        }
    }
}

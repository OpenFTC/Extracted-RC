package org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands;

import androidx.annotation.NonNull;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxNackException;
import com.qualcomm.hardware.lynx.commands.core.LynxInjectDataLogHintCommand;
import com.qualcomm.hardware.lynx.commands.standard.LynxSetDebugLogLevelCommand;
import com.qualcomm.robotcore.util.RobotLog;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlDeviceCommandHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlWebSocketHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidParameterException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.DebugHintParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.DebugLogLevelParameters;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketMessageTypeHandler;

import java.util.Map;

public class LogCommands {

    public static void register(Map<String, WebSocketMessageTypeHandler> handlerMap, ManualControlWebSocketHandler handler) {
        handlerMap.put("setDebugLogLevel", new SetDebugLogLevelCommand(handler));
        handlerMap.put("injectDebugLogHint", new InjectDebugLogHintCommand(handler));
    }

    private static class InjectDebugLogHintCommand extends ManualControlDeviceCommandHandler<DebugHintParameters, Void> {
        public InjectDebugLogHintCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }
        @NonNull @Override protected Class<DebugHintParameters> getPayloadClass() { return DebugHintParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, DebugHintParameters value) throws LynxNackException, InterruptedException, InvalidParameterException {
            RobotLog.dd("Injected Lynx Hint", value.getHint());
            LynxInjectDataLogHintCommand command = new LynxInjectDataLogHintCommand(module, value.getHint());
            command.send();
            return null;
        }
    }

    private static class SetDebugLogLevelCommand extends ManualControlDeviceCommandHandler<DebugLogLevelParameters, Void> {
        public SetDebugLogLevelCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }
        @NonNull @Override protected Class<DebugLogLevelParameters> getPayloadClass() { return DebugLogLevelParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, DebugLogLevelParameters value) throws LynxNackException, InterruptedException, InvalidParameterException {
            LynxModule.DebugGroup debugGroup = LynxModule.DebugGroup.fromInt(value.getDebugGroup());
            LynxModule.DebugVerbosity verbosity = LynxModule.DebugVerbosity.fromInt(value.getVerbosityLevel());

            LynxSetDebugLogLevelCommand command = new LynxSetDebugLogLevelCommand(module, debugGroup, verbosity);
            command.send();
            return null;
        }
    }
}

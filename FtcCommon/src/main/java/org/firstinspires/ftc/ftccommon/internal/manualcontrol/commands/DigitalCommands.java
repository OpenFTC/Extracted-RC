package org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands;

import androidx.annotation.NonNull;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxNackException;
import com.qualcomm.hardware.lynx.commands.core.LynxGetAllDIOInputsCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetDIODirectionCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetSingleDIOInputCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetAllDIOOutputsCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetDIODirectionCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetSingleDIOOutputCommand;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlDeviceCommandHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlWebSocketHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidParameterException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.HandleIdParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.DigitalAllPinsParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.DigitalPinDirectionParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.DigitalChannelValueParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.DigitalChannelParameters;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketCommandException;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketMessageTypeHandler;

import java.util.Map;

public class DigitalCommands {

    public static void register(Map<String, WebSocketMessageTypeHandler> handlerMap, ManualControlWebSocketHandler handler) {
        handlerMap.put("setDigitalOutput", new SetDigitalOutputCommand(handler));
        handlerMap.put("setAllDigitalOutputs", new SetAllDigitalOutputsCommand(handler));
        handlerMap.put("setDigitalDirection", new SetDigitalDirectionCommand(handler));
        handlerMap.put("isDigitalOutput", new GetDigitalDirectionIsOutputCommand(handler));
        handlerMap.put("getDigitalInput", new GetDigitalInputCommand(handler));
        handlerMap.put("getAllDigitalInputs", new GetAllDigitalInputsCommand(handler));
    }

    private static class SetDigitalOutputCommand extends ManualControlDeviceCommandHandler<DigitalChannelValueParameters, Void> {
        public SetDigitalOutputCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<DigitalChannelValueParameters> getPayloadClass() { return DigitalChannelValueParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, DigitalChannelValueParameters params) throws LynxNackException, InterruptedException, InvalidParameterException {
            LynxSetSingleDIOOutputCommand command = new LynxSetSingleDIOOutputCommand(module, params.getDigitalChannel(), params.getValue());
            command.send();
            return null;
        }
    }

    private static class SetAllDigitalOutputsCommand extends ManualControlDeviceCommandHandler<DigitalAllPinsParameters, Void> {
        public SetAllDigitalOutputsCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<DigitalAllPinsParameters> getPayloadClass() { return DigitalAllPinsParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, DigitalAllPinsParameters params) throws LynxNackException, InterruptedException, InvalidParameterException {
            LynxSetAllDIOOutputsCommand command = new LynxSetAllDIOOutputsCommand(module, params.getBitField());
            command.send();
            return null;
        }
    }

    private static class SetDigitalDirectionCommand extends ManualControlDeviceCommandHandler<DigitalPinDirectionParameters, Void> {
        public SetDigitalDirectionCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<DigitalPinDirectionParameters> getPayloadClass() { return DigitalPinDirectionParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, DigitalPinDirectionParameters params) throws LynxNackException, InterruptedException, InvalidParameterException {
            DigitalChannel.Mode mode = params.isOutput() ? DigitalChannel.Mode.OUTPUT : DigitalChannel.Mode.INPUT;
            LynxSetDIODirectionCommand command = new LynxSetDIODirectionCommand(module, params.getDigitalChannel(), mode);
            command.send();
            return null;
        }
    }

    private static class GetDigitalDirectionIsOutputCommand extends ManualControlDeviceCommandHandler<DigitalChannelParameters, Boolean> {
        public GetDigitalDirectionIsOutputCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<DigitalChannelParameters> getPayloadClass() { return DigitalChannelParameters.class; }
        @NonNull @Override protected Class<Boolean> getResultClass() { return Boolean.class; }

        @Override
        public Boolean performOperationOnDevice(LynxModule module, DigitalChannelParameters params) throws WebSocketCommandException, LynxNackException, InterruptedException {
            LynxGetDIODirectionCommand command = new LynxGetDIODirectionCommand(module, params.getDigitalChannel());
            return command.sendReceive().getMode() == DigitalChannel.Mode.OUTPUT;
        }
    }

    private static class GetDigitalInputCommand extends ManualControlDeviceCommandHandler<DigitalChannelParameters, Boolean> {
        public GetDigitalInputCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<DigitalChannelParameters> getPayloadClass() { return DigitalChannelParameters.class; }
        @NonNull @Override protected Class<Boolean> getResultClass() { return Boolean.class; }

        @Override
        public Boolean performOperationOnDevice(LynxModule module, DigitalChannelParameters params) throws LynxNackException, InterruptedException, InvalidParameterException {
            LynxGetSingleDIOInputCommand command = new LynxGetSingleDIOInputCommand(module, params.getDigitalChannel());
            return command.sendReceive().getValue();
        }
    }

    private static class GetAllDigitalInputsCommand extends ManualControlDeviceCommandHandler<HandleIdParameters, Integer> {
        public GetAllDigitalInputsCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, HandleIdParameters params) throws LynxNackException, InterruptedException {
            LynxGetAllDIOInputsCommand command = new LynxGetAllDIOInputsCommand(module);
            return command.sendReceive().getAllPins();
        }
    }
}

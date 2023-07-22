package org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands;

import androidx.annotation.NonNull;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxNackException;
import com.qualcomm.hardware.lynx.commands.core.LynxGetADCCommand;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlDeviceCommandHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlWebSocketHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidParameterException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.AnalogChannelParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.HandleIdParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.MotorChannelParameters;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketCommandException;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketMessageTypeHandler;

import java.util.Map;

public class AnalogCommands {

    public static void register(Map<String, WebSocketMessageTypeHandler> handlerMap, ManualControlWebSocketHandler handler) {
        handlerMap.put("getAnalogInput", new GetAnalogInputCommand(handler));
        handlerMap.put("getBatteryCurrent", new GetBatteryCurrentCommand(handler));
        handlerMap.put("getBatteryVoltage", new GetBatteryVoltageCommand(handler));
        handlerMap.put("getTemperature", new GetTemperatureCommand(handler));
        handlerMap.put("getI2cCurrent", new GetI2cCurrentCommand(handler));
        handlerMap.put("getServoCurrent", new GetServoCurrentCommand(handler));
        handlerMap.put("getDigitalBusCurrent", new GetDigitalBusCurrentCommand(handler));
        handlerMap.put("getMotorCurrent", new GetMotorCurrentCommand(handler));
        handlerMap.put("get5VBusVoltage", new Get5vBusVoltageCommand(handler));
    }

    public static class GetAnalogInputCommand extends ManualControlDeviceCommandHandler<AnalogChannelParameters, Integer> {
        public GetAnalogInputCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<AnalogChannelParameters> getPayloadClass() { return AnalogChannelParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, AnalogChannelParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxGetADCCommand.Channel channel;
            int channelNum = params.getAnalogChannel();
            switch(channelNum) {
                case 0:
                    channel = LynxGetADCCommand.Channel.USER0;
                    break;
                case 1:
                    channel = LynxGetADCCommand.Channel.USER1;
                    break;
                case 2:
                    channel = LynxGetADCCommand.Channel.USER2;
                    break;
                case 3:
                    channel = LynxGetADCCommand.Channel.USER3;
                    break;
                default:
                    throw new InvalidParameterException("invalid analog channel " + channelNum);
            }
            LynxGetADCCommand command = new LynxGetADCCommand(module, channel, LynxGetADCCommand.Mode.ENGINEERING);
            return command.sendReceive().getValue();
        }
    }

    public static class GetBatteryCurrentCommand extends ManualControlDeviceCommandHandler<HandleIdParameters, Integer> {
        public GetBatteryCurrentCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, HandleIdParameters params) throws LynxNackException, InterruptedException {
            LynxGetADCCommand command = new LynxGetADCCommand(module, LynxGetADCCommand.Channel.BATTERY_CURRENT, LynxGetADCCommand.Mode.ENGINEERING);
            return command.sendReceive().getValue();
        }
    }

    public static class GetTemperatureCommand extends ManualControlDeviceCommandHandler<HandleIdParameters, Double> {
        public GetTemperatureCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
        @NonNull @Override protected Class<Double> getResultClass() { return Double.class; }

        @Override
        public Double performOperationOnDevice(LynxModule module, HandleIdParameters params) throws LynxNackException, InterruptedException {
            LynxGetADCCommand command = new LynxGetADCCommand(module, LynxGetADCCommand.Channel.CONTROLLER_TEMPERATURE, LynxGetADCCommand.Mode.ENGINEERING);
            return command.sendReceive().getValue() / 10.0;
        }
    }

    public static class GetBatteryVoltageCommand extends ManualControlDeviceCommandHandler<HandleIdParameters, Integer> {
        public GetBatteryVoltageCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, HandleIdParameters params) throws LynxNackException, InterruptedException {
            LynxGetADCCommand command = new LynxGetADCCommand(module, LynxGetADCCommand.Channel.BATTERY_MONITOR, LynxGetADCCommand.Mode.ENGINEERING);
            return command.sendReceive().getValue();
        }
    }

    public static class GetDigitalBusCurrentCommand extends ManualControlDeviceCommandHandler<HandleIdParameters, Integer> {
        public GetDigitalBusCurrentCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, HandleIdParameters params) throws LynxNackException, InterruptedException {
            LynxGetADCCommand command = new LynxGetADCCommand(module, LynxGetADCCommand.Channel.GPIO_CURRENT, LynxGetADCCommand.Mode.ENGINEERING);
            return command.sendReceive().getValue();
        }
    }

    public static class GetI2cCurrentCommand extends ManualControlDeviceCommandHandler<HandleIdParameters, Integer> {
        public GetI2cCurrentCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, HandleIdParameters params) throws LynxNackException, InterruptedException {
            LynxGetADCCommand command = new LynxGetADCCommand(module, LynxGetADCCommand.Channel.I2C_BUS_CURRENT, LynxGetADCCommand.Mode.ENGINEERING);
            return command.sendReceive().getValue();
        }
    }

    public static class GetServoCurrentCommand extends ManualControlDeviceCommandHandler<HandleIdParameters, Integer> {
        public GetServoCurrentCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, HandleIdParameters params) throws LynxNackException, InterruptedException {
            LynxGetADCCommand command = new LynxGetADCCommand(module, LynxGetADCCommand.Channel.SERVO_CURRENT, LynxGetADCCommand.Mode.ENGINEERING);
            return command.sendReceive().getValue();
        }
    }

    public static class GetMotorCurrentCommand extends ManualControlDeviceCommandHandler<MotorChannelParameters, Integer> {
        public GetMotorCurrentCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorChannelParameters> getPayloadClass() { return MotorChannelParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, MotorChannelParameters parameters) throws WebSocketCommandException, LynxNackException, InterruptedException {
            LynxGetADCCommand.Channel channel;
            int channelNum = parameters.getMotorChannel();
            switch(channelNum) {
                case 0:
                    channel = LynxGetADCCommand.Channel.MOTOR0_CURRENT;
                    break;
                case 1:
                    channel = LynxGetADCCommand.Channel.MOTOR1_CURRENT;
                    break;
                case 2:
                    channel = LynxGetADCCommand.Channel.MOTOR2_CURRENT;
                    break;
                case 3:
                    channel = LynxGetADCCommand.Channel.MOTOR3_CURRENT;
                    break;
                default:
                    throw new InvalidParameterException("invalid motor channel " + channelNum);
            }
            LynxGetADCCommand command = new LynxGetADCCommand(module, channel, LynxGetADCCommand.Mode.ENGINEERING);
            return command.sendReceive().getValue();
        }
    }

    public static class Get5vBusVoltageCommand extends ManualControlDeviceCommandHandler<HandleIdParameters, Integer> {
        public Get5vBusVoltageCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, HandleIdParameters params) throws LynxNackException, InterruptedException {
            LynxGetADCCommand command = new LynxGetADCCommand(module, LynxGetADCCommand.Channel.FIVE_VOLT_MONITOR, LynxGetADCCommand.Mode.ENGINEERING);
            return command.sendReceive().getValue();
        }
    }
}

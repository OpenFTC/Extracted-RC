package org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands;

import androidx.annotation.NonNull;
import com.qualcomm.hardware.lynx.LynxI2cDeviceSynch;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxNackException;
import com.qualcomm.hardware.lynx.commands.core.LynxFirmwareVersionManager;
import com.qualcomm.hardware.lynx.commands.core.LynxI2cConfigureChannelCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxI2cConfigureQueryCommand;
import com.qualcomm.robotcore.util.TypeConversion;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlDeviceCommandHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlWebSocketHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidParameterException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.I2cChannelConfiguration;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.I2cChannelParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.I2cReadParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.I2cReadRegisterParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.I2cWriteParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.I2cWriteRegisterParameters;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketMessageTypeHandler;

import java.util.Map;

public class I2cCommands {

    public static void register(Map<String, WebSocketMessageTypeHandler> handlerMap, ManualControlWebSocketHandler handler) {
        handlerMap.put("setI2cChannelConfiguration", new SetI2cChannelConfigurationCommand(handler));
        handlerMap.put("getI2cChannelConfiguration", new GetI2cChannelConfigurationCommand(handler));
        handlerMap.put("readI2cData", new ReadI2cDataCommand(handler));
        handlerMap.put("readI2cRegister", new ReadI2cRegisterCommand(handler));
        handlerMap.put("writeI2cData", new WriteI2cDataCommand(handler));
        handlerMap.put("writeI2cRegister", new WriteI2cRegisterCommand(handler));
    }

    private static class SetI2cChannelConfigurationCommand extends ManualControlDeviceCommandHandler<I2cChannelConfiguration, Void> {
        public SetI2cChannelConfigurationCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }
        @NonNull @Override protected Class<I2cChannelConfiguration> getPayloadClass() { return I2cChannelConfiguration.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, I2cChannelConfiguration params) throws LynxNackException, InterruptedException, InvalidParameterException {
            LynxI2cConfigureChannelCommand command = new LynxI2cConfigureChannelCommand(module, params.getChannel(), params.getSpeedCode());
            command.send();
            return null;
        }
    }

    private static class GetI2cChannelConfigurationCommand extends ManualControlDeviceCommandHandler<I2cChannelParameters, Integer> {
        public GetI2cChannelConfigurationCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }
        @NonNull @Override protected Class<I2cChannelParameters> getPayloadClass() { return I2cChannelParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, I2cChannelParameters params) throws LynxNackException, InterruptedException, InvalidParameterException {
            LynxI2cConfigureQueryCommand command = new LynxI2cConfigureQueryCommand(module, params.getChannel());
            return TypeConversion.unsignedByteToInt(command.sendReceive().getSpeedCode().bVal);
        }
    }

    private static class ReadI2cDataCommand extends ManualControlDeviceCommandHandler<I2cReadParameters, int[]> {
        public ReadI2cDataCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }
        @NonNull @Override protected Class<I2cReadParameters> getPayloadClass() { return I2cReadParameters.class; }
        @NonNull @Override protected Class<int[]> getResultClass() { return int[].class; }

        @Override
        public int[] performOperationOnDevice(LynxModule module, I2cReadParameters params) throws InvalidParameterException {
            LynxI2cDeviceSynch device = LynxFirmwareVersionManager.createLynxI2cDeviceSynch(AppUtil.getDefContext(), module, params.getChannel());
            device.setI2cAddr(params.getAddress());

            byte[] data = device.read(params.getNumBytes());
            int[] result = new int[data.length];

            for (int i = 0; i < data.length; i++) {
                result[i] = TypeConversion.unsignedByteToInt(data[i]);
            }

            return result;
        }
    }

    private static class ReadI2cRegisterCommand extends ManualControlDeviceCommandHandler<I2cReadRegisterParameters, int[]> {
        public ReadI2cRegisterCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }
        @NonNull @Override protected Class<I2cReadRegisterParameters> getPayloadClass() { return I2cReadRegisterParameters.class; }
        @NonNull @Override protected Class<int[]> getResultClass() { return int[].class; }

        @Override
        public int[] performOperationOnDevice(LynxModule module, I2cReadRegisterParameters params) throws InvalidParameterException {
            LynxI2cDeviceSynch device = LynxFirmwareVersionManager.createLynxI2cDeviceSynch(AppUtil.getDefContext(), module, params.getChannel());
            device.setI2cAddr(params.getAddress());

            byte[] data = device.read(params.getRegister(), params.getNumBytes());
            int[] result = new int[data.length];

            for (int i = 0; i < data.length; i++) {
                result[i] = TypeConversion.unsignedByteToInt(data[i]);
            }

            return result;
        }
    }

    private static class WriteI2cDataCommand extends ManualControlDeviceCommandHandler<I2cWriteParameters, Void> {
        public WriteI2cDataCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }
        @NonNull @Override protected Class<I2cWriteParameters> getPayloadClass() { return I2cWriteParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, I2cWriteParameters params) throws InvalidParameterException {
            LynxI2cDeviceSynch device = LynxFirmwareVersionManager.createLynxI2cDeviceSynch(AppUtil.getDefContext(), module, params.getChannel());
            device.setI2cAddr(params.getAddress());
            device.write(params.getData());
            return null;
        }
    }

    private static class WriteI2cRegisterCommand extends ManualControlDeviceCommandHandler<I2cWriteRegisterParameters, Void> {
        public WriteI2cRegisterCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }

        @NonNull @Override protected Class<I2cWriteRegisterParameters> getPayloadClass() { return I2cWriteRegisterParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, I2cWriteRegisterParameters params) throws InvalidParameterException {
            LynxI2cDeviceSynch device = LynxFirmwareVersionManager.createLynxI2cDeviceSynch(AppUtil.getDefContext(), module, params.getChannel());
            device.setI2cAddr(params.getAddress());
            device.write(params.getRegister(), params.getData());
            return null;
        }
    }
}

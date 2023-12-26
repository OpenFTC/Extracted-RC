package org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands;

import androidx.annotation.NonNull;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxNackException;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorChannelCurrentAlertLevelCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorChannelEnableCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorChannelModeCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorChannelModeResponse;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorConstantPowerCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorEncoderPositionCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorPIDFControlLoopCoefficientsCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorPIDFControlLoopCoefficientsResponse;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorPIDControlLoopCoefficientsCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorPIDControlLoopCoefficientsResponse;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorTargetPositionCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorTargetPositionResponse;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorTargetVelocityCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxIsMotorAtTargetCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxResetMotorEncoderCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetMotorChannelCurrentAlertLevelCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetMotorChannelEnableCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetMotorChannelModeCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetMotorConstantPowerCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetMotorPIDControlLoopCoefficientsCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetMotorPIDFControlLoopCoefficientsCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetMotorTargetPositionCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetMotorTargetVelocityCommand;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.util.RobotLog;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlDeviceCommandHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlWebSocketHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidParameterException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.MotorAlertLevelParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.MotorChannelParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.MotorConstantPowerParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.SetMotorEnableParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.MotorModeParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.MotorPidfCoefficientsParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.SetMotorModeParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.MotorPidCoefficientsParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.MotorTargetPositionParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.MotorTargetVelocityParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.MotorModeResponse;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.MotorTargetPosition;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.ClosedLoopControlCoefficients;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketCommandException;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketMessageTypeHandler;

import java.util.Map;

public class MotorCommands {
    public static void register(Map<String, WebSocketMessageTypeHandler> handlerMap, ManualControlWebSocketHandler handler) {
        handlerMap.put("setMotorMode", new SetMotorModeCommand(handler));
        handlerMap.put("getMotorMode", new GetMotorModeCommand(handler));
        handlerMap.put("setMotorEnabled", new SetMotorEnabledCommand(handler));
        handlerMap.put("getMotorEnable", new GetMotorEnableCommand(handler));
        handlerMap.put("setMotorAlertLevel", new SetMotorAlertLevelCommand(handler));
        handlerMap.put("getMotorAlertLevel", new GetMotorAlertLevelCommand(handler));
        handlerMap.put("resetMotorEncoder", new ResetMotorEncoderCommand(handler));
        handlerMap.put("setMotorConstantPower", new SetMotorConstantPowerCommand(handler));
        handlerMap.put("getMotorConstantPower", new GetMotorConstantPowerCommand(handler));
        handlerMap.put("setMotorTargetVelocity", new SetMotorTargetVelocityCommand(handler));
        handlerMap.put("getMotorTargetVelocity", new GetMotorTargetVelocityCommand(handler));
        handlerMap.put("setMotorTargetPosition", new SetMotorTargetPositionCommand(handler));
        handlerMap.put("getMotorTargetPosition", new GetMotorTargetPositionCommand(handler));
        handlerMap.put("getMotorEncoder", new GetMotorEncoderCommand(handler));
        handlerMap.put("getIsMotorAtTarget", new GetIsMotorAtTargetCommand(handler));
        handlerMap.put("setMotorPidCoefficients", new SetMotorPidCoefficientsCommand(handler));
        handlerMap.put("getMotorClosedLoopControlCoefficients", new GetMotorClosedLoopControlCoefficientsCommand(handler));
        handlerMap.put("setMotorPidfCoefficients", new SetMotorPidfCoefficientsCommand(handler));
    }

    private static class SetMotorModeCommand extends ManualControlDeviceCommandHandler<SetMotorModeParameters, Void> {
        public SetMotorModeCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<SetMotorModeParameters> getPayloadClass() { return SetMotorModeParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, SetMotorModeParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxSetMotorChannelModeCommand command
                    = new LynxSetMotorChannelModeCommand(module, params.getMotorChannel(), params.getMotorMode(), params.getZeroPowerBehavior());
            command.send();
            return null;
        }
    }

    private static class GetMotorModeCommand extends ManualControlDeviceCommandHandler<MotorChannelParameters, MotorModeResponse> {
        public GetMotorModeCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorChannelParameters> getPayloadClass() { return MotorChannelParameters.class; }
        @NonNull @Override protected Class<MotorModeResponse> getResultClass() { return MotorModeResponse.class; }

        @Override
        public MotorModeResponse performOperationOnDevice(LynxModule module, MotorChannelParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxGetMotorChannelModeCommand command = new LynxGetMotorChannelModeCommand(module, params.getMotorChannel());
            LynxGetMotorChannelModeResponse response = command.sendReceive();
            int motorMode = LynxConstants.runModeToLynxMotorMode(response.getMode());
            return new MotorModeResponse(motorMode, response.getZeroPowerBehavior() == DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }

    private static class SetMotorEnabledCommand extends ManualControlDeviceCommandHandler<SetMotorEnableParameters, Void> {
        public SetMotorEnabledCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<SetMotorEnableParameters> getPayloadClass() { return SetMotorEnableParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, SetMotorEnableParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxSetMotorChannelEnableCommand command = new LynxSetMotorChannelEnableCommand(module, params.getMotorChannel(), params.getEnable());
            command.send();
            return null;
        }
    }

    private static class GetMotorEnableCommand extends ManualControlDeviceCommandHandler<MotorChannelParameters, Boolean> {
        public GetMotorEnableCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorChannelParameters> getPayloadClass() { return MotorChannelParameters.class; }
        @NonNull @Override protected Class<Boolean> getResultClass() { return Boolean.class; }

        @Override
        public Boolean performOperationOnDevice(LynxModule module, MotorChannelParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxGetMotorChannelEnableCommand command = new LynxGetMotorChannelEnableCommand(module, params.getMotorChannel());
            return command.sendReceive().isEnabled();
        }
    }

    private static class SetMotorAlertLevelCommand extends ManualControlDeviceCommandHandler<MotorAlertLevelParameters, Void> {
        public SetMotorAlertLevelCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorAlertLevelParameters> getPayloadClass() { return MotorAlertLevelParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, MotorAlertLevelParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxSetMotorChannelCurrentAlertLevelCommand command = new LynxSetMotorChannelCurrentAlertLevelCommand(module, params.getMotorChannel(), params.getCurrentLimit_mA());
            command.send();
            return null;
        }
    }

    private static class GetMotorAlertLevelCommand extends ManualControlDeviceCommandHandler<MotorChannelParameters, Integer> {
        public GetMotorAlertLevelCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorChannelParameters> getPayloadClass() { return MotorChannelParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, MotorChannelParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxGetMotorChannelCurrentAlertLevelCommand command = new LynxGetMotorChannelCurrentAlertLevelCommand(module, params.getMotorChannel());
            return command.sendReceive().getCurrentLimit();
        }
    }

    private static class ResetMotorEncoderCommand extends ManualControlDeviceCommandHandler<MotorChannelParameters, Void> {
        public ResetMotorEncoderCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorChannelParameters> getPayloadClass() { return MotorChannelParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, MotorChannelParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxResetMotorEncoderCommand command = new LynxResetMotorEncoderCommand(module, params.getMotorChannel());
            command.send();
            return null;
        }
    }

    private static class SetMotorConstantPowerCommand extends ManualControlDeviceCommandHandler<MotorConstantPowerParameters, Void> {
        public SetMotorConstantPowerCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorConstantPowerParameters> getPayloadClass() { return MotorConstantPowerParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, MotorConstantPowerParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            int power = (int) (params.getMotorPower() * LynxSetMotorConstantPowerCommand.apiPowerLast);
            LynxSetMotorConstantPowerCommand command = new LynxSetMotorConstantPowerCommand(module, params.getMotorChannel(), power);
            command.send();
            return null;
        }
    }

    private static class GetMotorConstantPowerCommand extends ManualControlDeviceCommandHandler<MotorChannelParameters, Integer> {
        public GetMotorConstantPowerCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorChannelParameters> getPayloadClass() { return MotorChannelParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, MotorChannelParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxGetMotorConstantPowerCommand command = new LynxGetMotorConstantPowerCommand(module, params.getMotorChannel());
            return command.sendReceive().getPower() / LynxSetMotorConstantPowerCommand.apiPowerLast;
        }
    }

    private static class SetMotorTargetVelocityCommand extends ManualControlDeviceCommandHandler<MotorTargetVelocityParameters, Void> {
        public SetMotorTargetVelocityCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorTargetVelocityParameters> getPayloadClass() { return MotorTargetVelocityParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, MotorTargetVelocityParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxSetMotorTargetVelocityCommand command = new LynxSetMotorTargetVelocityCommand(module, params.getMotorChannel(), params.getTargetVelocityCountsPerSecond());
            command.send();
            return null;
        }
    }

    private static class GetMotorTargetVelocityCommand extends ManualControlDeviceCommandHandler<MotorChannelParameters, Integer> {
        public GetMotorTargetVelocityCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorChannelParameters> getPayloadClass() { return MotorChannelParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, MotorChannelParameters params) throws WebSocketCommandException, LynxNackException, InterruptedException {
            LynxGetMotorTargetVelocityCommand command = new LynxGetMotorTargetVelocityCommand(module, params.getMotorChannel());
            return command.sendReceive().getVelocity();
        }
    }

    private static class SetMotorTargetPositionCommand extends ManualControlDeviceCommandHandler<MotorTargetPositionParameters, Void> {
        public SetMotorTargetPositionCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorTargetPositionParameters> getPayloadClass() { return MotorTargetPositionParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, MotorTargetPositionParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxSetMotorTargetPositionCommand command = new LynxSetMotorTargetPositionCommand(module, params.getMotorChannel(), params.getTargetPositionCounts(), params.getTargetToleranceCounts());
            command.send();
            return null;
        }
    }

    private static class GetMotorTargetPositionCommand extends ManualControlDeviceCommandHandler<MotorChannelParameters, MotorTargetPosition> {
        public GetMotorTargetPositionCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorChannelParameters> getPayloadClass() { return MotorChannelParameters.class; }
        @NonNull @Override protected Class<MotorTargetPosition> getResultClass() { return MotorTargetPosition.class; }

        @Override
        public MotorTargetPosition performOperationOnDevice(LynxModule module, MotorChannelParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxGetMotorTargetPositionCommand command = new LynxGetMotorTargetPositionCommand(module, params.getMotorChannel());
            LynxGetMotorTargetPositionResponse response = command.sendReceive();
            return new MotorTargetPosition(response.getTarget(), response.getTolerance());
        }
    }

    private static class GetIsMotorAtTargetCommand extends ManualControlDeviceCommandHandler<MotorChannelParameters, Boolean> {
        public GetIsMotorAtTargetCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorChannelParameters> getPayloadClass() { return MotorChannelParameters.class; }
        @NonNull @Override protected Class<Boolean> getResultClass() { return Boolean.class; }

        @Override
        public Boolean performOperationOnDevice(LynxModule module, MotorChannelParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxIsMotorAtTargetCommand command = new LynxIsMotorAtTargetCommand(module, params.getMotorChannel());
            return command.sendReceive().isAtTarget();
        }
    }

    private static class GetMotorEncoderCommand extends ManualControlDeviceCommandHandler<MotorChannelParameters, Integer> {
        public GetMotorEncoderCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorChannelParameters> getPayloadClass() { return MotorChannelParameters.class; }
        @NonNull @Override protected Class<Integer> getResultClass() { return Integer.class; }

        @Override
        public Integer performOperationOnDevice(LynxModule module, MotorChannelParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxGetMotorEncoderPositionCommand command = new LynxGetMotorEncoderPositionCommand(module, params.getMotorChannel());
            return command.sendReceive().getPosition();
        }
    }

    private static class SetMotorPidCoefficientsCommand extends ManualControlDeviceCommandHandler<MotorPidCoefficientsParameters, Void> {
        public SetMotorPidCoefficientsCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorPidCoefficientsParameters> getPayloadClass() { return MotorPidCoefficientsParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, MotorPidCoefficientsParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxSetMotorPIDControlLoopCoefficientsCommand command = new LynxSetMotorPIDControlLoopCoefficientsCommand(
                    module, params.getMotorChannel(), params.getMotorMode(), params.getInternalP(), params.getInternalI(), params.getInternalD());
            command.send();

            RobotLog.dd(TAG, "Set Motor PID Coefficients");
            return null;
        }
    }

    private static class GetMotorClosedLoopControlCoefficientsCommand extends ManualControlDeviceCommandHandler<MotorModeParameters, ClosedLoopControlCoefficients> {
        public GetMotorClosedLoopControlCoefficientsCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorModeParameters> getPayloadClass() { return MotorModeParameters.class; }
        @NonNull @Override protected Class<ClosedLoopControlCoefficients> getResultClass() { return ClosedLoopControlCoefficients.class; }

        @Override
        public ClosedLoopControlCoefficients performOperationOnDevice(LynxModule module, MotorModeParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            if(module.isCommandSupported(LynxGetMotorPIDFControlLoopCoefficientsCommand.class)) {
                LynxGetMotorPIDFControlLoopCoefficientsCommand command = new LynxGetMotorPIDFControlLoopCoefficientsCommand(
                        module, params.getMotorChannel(), params.getMotorMode());
                LynxGetMotorPIDFControlLoopCoefficientsResponse response = command.sendReceive();
                return new ClosedLoopControlCoefficients(response.getP(), response.getI(), response.getD(), response.getF(),
                        response.getInternalMotorControlAlgorithm().getValue());
            } else {
                LynxGetMotorPIDControlLoopCoefficientsCommand command = new LynxGetMotorPIDControlLoopCoefficientsCommand(
                        module, params.getMotorChannel(), params.getMotorMode());
                LynxGetMotorPIDControlLoopCoefficientsResponse response = command.sendReceive();
                return new ClosedLoopControlCoefficients(response.getP(), response.getI(), response.getD(), 0,
                        LynxSetMotorPIDFControlLoopCoefficientsCommand.InternalMotorControlAlgorithm.LegacyPID.getValue());
            }
        }
    }

    private static class SetMotorPidfCoefficientsCommand extends ManualControlDeviceCommandHandler<MotorPidfCoefficientsParameters, Void> {
        public SetMotorPidfCoefficientsCommand(ManualControlWebSocketHandler handler) { super(handler); }
        @NonNull @Override protected Class<MotorPidfCoefficientsParameters> getPayloadClass() { return MotorPidfCoefficientsParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, MotorPidfCoefficientsParameters params) throws InvalidParameterException, LynxNackException, InterruptedException {
            LynxSetMotorPIDFControlLoopCoefficientsCommand command = new LynxSetMotorPIDFControlLoopCoefficientsCommand(
                    module, params.getMotorChannel(), params.getMotorMode(), params.getInternalP(), params.getInternalI(), params.getInternalD(), params.getInternalF(), LynxSetMotorPIDFControlLoopCoefficientsCommand.InternalMotorControlAlgorithm.PIDF);
            command.send();

            RobotLog.dd(TAG, "Set Motor PIDF Coefficients");
            return null;
        }
    }
}

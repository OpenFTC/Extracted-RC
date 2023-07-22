package org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands;

import androidx.annotation.NonNull;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxNackException;
import com.qualcomm.hardware.lynx.commands.core.LynxGetBulkInputDataCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxGetBulkInputDataResponse;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlDeviceCommandHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlWebSocketHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.HandleIdParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.BulkInputResponse;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketMessageTypeHandler;

import java.util.Map;

public class BulkInputCommand extends ManualControlDeviceCommandHandler<HandleIdParameters, BulkInputResponse> {
    public static void register(Map<String, WebSocketMessageTypeHandler> handlerMap, ManualControlWebSocketHandler handler) {
        handlerMap.put("getBulkInputData", new BulkInputCommand(handler));
    }

    public BulkInputCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }
    @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
    @NonNull @Override protected Class<BulkInputResponse> getResultClass() { return BulkInputResponse.class; }

    @Override
    public BulkInputResponse performOperationOnDevice(LynxModule module, HandleIdParameters parameters) throws LynxNackException, InterruptedException {
        LynxGetBulkInputDataCommand command = new LynxGetBulkInputDataCommand(module);
        LynxGetBulkInputDataResponse response = command.sendReceive();

        return new BulkInputResponse(response.getAllDigitalInputs(),
                response.getEncoder(0), response.getEncoder(1),
                response.getEncoder(2), response.getEncoder(3),
                response.getMotorStatus(),
                response.getVelocity(0), response.getVelocity(1),
                response.getVelocity(2), response.getVelocity(3),
                response.getAnalogInput(0), response.getAnalogInput(1),
                response.getAnalogInput(2), response.getAnalogInput(3));
    }
}

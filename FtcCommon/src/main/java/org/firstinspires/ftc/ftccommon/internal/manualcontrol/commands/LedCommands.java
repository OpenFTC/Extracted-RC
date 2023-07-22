package org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands;

import android.graphics.Color;
import androidx.annotation.NonNull;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxNackException;
import com.qualcomm.hardware.lynx.commands.standard.LynxGetModuleLEDColorCommand;
import com.qualcomm.hardware.lynx.commands.standard.LynxGetModuleLEDColorResponse;
import com.qualcomm.hardware.lynx.commands.standard.LynxGetModuleLEDPatternCommand;
import com.qualcomm.hardware.lynx.commands.standard.LynxGetModuleLEDPatternResponse;
import com.qualcomm.hardware.lynx.commands.standard.LynxSetModuleLEDColorCommand;
import com.qualcomm.hardware.lynx.commands.standard.LynxSetModuleLEDPatternCommand;
import com.qualcomm.robotcore.hardware.Blinker;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlDeviceCommandHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlWebSocketHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidParameterException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.HandleIdParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.LedColorParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.LedPatternParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.LedColorResponse;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.LedPatternResponse;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketMessageTypeHandler;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LedCommands {

    public static void register(Map<String, WebSocketMessageTypeHandler> handlerMap, ManualControlWebSocketHandler handler) {
        handlerMap.put("setLedColor", new SetLedColorCommand(handler));
        handlerMap.put("getLedColor", new GetLedColorCommand(handler));
        handlerMap.put("setLedPattern", new SetLedPatternCommand(handler));
        handlerMap.put("getLedPattern", new GetLedPatternCommand(handler));
    }

    private static class SetLedColorCommand extends ManualControlDeviceCommandHandler<LedColorParameters, Void> {
        public SetLedColorCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }
        @NonNull @Override protected Class<LedColorParameters> getPayloadClass() { return LedColorParameters.class; }

        @Override
        public Void performOperationOnDevice(LynxModule module, LedColorParameters value) throws LynxNackException, InterruptedException, InvalidParameterException {
            LynxSetModuleLEDColorCommand command = new LynxSetModuleLEDColorCommand(module, (byte)value.getRed(), (byte)value.getGreen(), (byte)value.getBlue());
            command.send();
            return null;
        }
    }

    private static class GetLedColorCommand extends ManualControlDeviceCommandHandler<HandleIdParameters, LedColorResponse> {
        public GetLedColorCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }
        @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
        @NonNull @Override protected Class<LedColorResponse> getResultClass() { return LedColorResponse.class; }

        @Override
        public LedColorResponse performOperationOnDevice(LynxModule module, HandleIdParameters value) throws LynxNackException, InterruptedException {
            LynxGetModuleLEDColorCommand command = new LynxGetModuleLEDColorCommand(module);
            LynxGetModuleLEDColorResponse response = command.sendReceive();

            return new LedColorResponse(response.getRed(), response.getGreen(), response.getBlue());
        }
    }

    private static class SetLedPatternCommand extends ManualControlDeviceCommandHandler<LedPatternParameters, Void> {
        public SetLedPatternCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }
        @NonNull @Override protected Class<LedPatternParameters> getPayloadClass() { return LedPatternParameters.class; }
        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        private Blinker.Step toStep(int value) {
            int r = (value >> 24) & 0xFF;
            int g = (value >> 16) & 0xFF;
            int b = (value >> 8) & 0xFF;
            double t = ((value) & 0xFF)/10.0;
            int color = Color.rgb(r, g, b);
            return new Blinker.Step(color, (long)(t * 1000), TimeUnit.MILLISECONDS);
        }

        @Override
        public Void performOperationOnDevice(LynxModule module, LedPatternParameters value) throws LynxNackException, InterruptedException, InvalidParameterException {
            LynxSetModuleLEDPatternCommand.Steps steps = new LynxSetModuleLEDPatternCommand.Steps();
            steps.add(toStep(value.getRgbtPatternStep0()));
            steps.add(toStep(value.getRgbtPatternStep1()));
            steps.add(toStep(value.getRgbtPatternStep2()));
            steps.add(toStep(value.getRgbtPatternStep3()));
            steps.add(toStep(value.getRgbtPatternStep4()));
            steps.add(toStep(value.getRgbtPatternStep5()));
            steps.add(toStep(value.getRgbtPatternStep6()));
            steps.add(toStep(value.getRgbtPatternStep7()));
            steps.add(toStep(value.getRgbtPatternStep8()));
            steps.add(toStep(value.getRgbtPatternStep9()));
            steps.add(toStep(value.getRgbtPatternStep10()));
            steps.add(toStep(value.getRgbtPatternStep11()));
            steps.add(toStep(value.getRgbtPatternStep12()));
            steps.add(toStep(value.getRgbtPatternStep13()));
            steps.add(toStep(value.getRgbtPatternStep14()));
            steps.add(toStep(value.getRgbtPatternStep15()));
            LynxSetModuleLEDPatternCommand command = new LynxSetModuleLEDPatternCommand(module, steps);
            command.send();
            return null;
        }
    }

    private static class GetLedPatternCommand extends ManualControlDeviceCommandHandler<HandleIdParameters, LedPatternResponse> {
        public GetLedPatternCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }
        @NonNull @Override protected Class<HandleIdParameters> getPayloadClass() { return HandleIdParameters.class; }
        @NonNull @Override protected Class<LedPatternResponse> getResultClass() { return LedPatternResponse.class; }

        @Override
        public LedPatternResponse performOperationOnDevice(LynxModule module, HandleIdParameters value) throws LynxNackException, InterruptedException {
            LynxGetModuleLEDPatternCommand command = new LynxGetModuleLEDPatternCommand(module);
            LynxGetModuleLEDPatternResponse response = command.sendReceive();
            return new LedPatternResponse(
                    getValue(response.getStep(0)),
                    getValue(response.getStep(1)),
                    getValue(response.getStep(2)),
                    getValue(response.getStep(3)),
                    getValue(response.getStep(4)),
                    getValue(response.getStep(5)),
                    getValue(response.getStep(6)),
                    getValue(response.getStep(7)),
                    getValue(response.getStep(8)),
                    getValue(response.getStep(9)),
                    getValue(response.getStep(10)),
                    getValue(response.getStep(11)),
                    getValue(response.getStep(12)),
                    getValue(response.getStep(13)),
                    getValue(response.getStep(14)),
                    getValue(response.getStep(15))
            );
        }

        private int getValue(Blinker.Step step) {
            int t = step.getDurationMs() / 100; //convert to deci-seconds
            int color = step.getColor();
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);

            return (t & 0xFF) | ((blue & 0xFF) << 8) | ((green & 0xFF) << 16) | ((red & 0xFF) << 24);
        }
    }
}

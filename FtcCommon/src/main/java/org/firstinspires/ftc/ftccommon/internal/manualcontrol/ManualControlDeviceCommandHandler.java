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

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxNackException;
import com.qualcomm.hardware.lynx.commands.standard.LynxNack;
import com.qualcomm.robotcore.exception.RobotCoreException;

import com.qualcomm.robotcore.hardware.usb.RobotArmingStateNotifier;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.HubNotRespondingException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.InvalidParameterException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.ManualControlNackException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions.WebSocketNotAuthorizedForManualControlException;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.HandleIdParameters;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketCommandException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ManualControlDeviceCommandHandler<P extends HandleIdParameters, R> extends ManualControlCommandHandler<P, R> {
    private final int timeout;
    private final TimeUnit timeoutUnit;

    public ManualControlDeviceCommandHandler(ManualControlWebSocketHandler manualControlWebSocketHandler) {
        this(manualControlWebSocketHandler, 120, TimeUnit.MILLISECONDS);
    }

    public ManualControlDeviceCommandHandler(ManualControlWebSocketHandler manualControlWebSocketHandler, int timeout, TimeUnit timeoutUnit) {
        super(manualControlWebSocketHandler);
        this.timeout = timeout;
        this.timeoutUnit = timeoutUnit;
    }

    /**
     * This method is called on a background thread managed by the {@link com.qualcomm.hardware.lynx.LynxUsbDevice}
     * system operation infrastructure
     * <p>
     * Do NOT call .close() on the provided {@link LynxModule}
     */
    public abstract R performOperationOnDevice(LynxModule lynxModule, P parameters) throws LynxNackException, WebSocketCommandException, InterruptedException;

    @Override
    public final R handleManualControlCommand(P parameters) throws WebSocketCommandException, InterruptedException {
        AtomicReference<R> resultRef = new AtomicReference<>();
        AtomicReference<Exception> exceptionRef = new AtomicReference<>();
        try {
            ManualControlOpMode.getInstance().getSystemOperationHandle(parameters).performSystemOperation(
                    module -> {
                        if (module.getArmingState() == RobotArmingStateNotifier.ARMINGSTATE.ARMED) {
                            try { resultRef.set(performOperationOnDevice(module, parameters)); }
                            catch (LynxNackException | WebSocketCommandException | InterruptedException | RuntimeException e) { exceptionRef.set(e); }
                        } else {
                            exceptionRef.set(new HubNotRespondingException());
                        }
                    },
                    timeout,
                    timeoutUnit
            );
        } catch (TimeoutException e){
            throw new HubNotRespondingException();
        } catch (RobotCoreException e) {
            // This will eventually get converted to an InternalWebSocketCommandException
            throw new RuntimeException(e);
        }

        Exception exception = exceptionRef.get();
        if (exception == null) {
            return resultRef.get();
        } else {
            // The only checked exceptions that this should be able to contain are the ones that
            // can be thrown by performOperationOnDevice(), namely LynxNackException,
            // WebSocketCommandException, and InterruptedException. If it's an InterruptedException,
            // we must NOT just rethrow it, even though this method can throw InterruptedException,
            // because it was a different thread that got interrupted.
            if (exception instanceof LynxNackException) {
                LynxNack.ReasonCode nackReason = ((LynxNackException)exception).getNack().getNackReasonCode();

                if (nackReason.getValue() > 255) {
                    // This is a made-up NACK code used internally by the SDK, and we must not send it to the client as a NACK.
                    if (nackReason instanceof LynxNack.StandardReasonCode) {
                        switch ((LynxNack.StandardReasonCode) nackReason) {
                            // Fall-through
                            case ABANDONED_WAITING_FOR_RESPONSE:
                            case ABANDONED_WAITING_FOR_ACK:
                                throw new HubNotRespondingException();

                            case CANCELLED_FOR_SAFETY:
                                throw new WebSocketNotAuthorizedForManualControlException();

                            case UNRECOGNIZED_REASON_CODE:
                                // Should never happen, as we are not calling getNackReasonCodeAsEnum()
                                throw new RuntimeException("Unexpectedly got UNRECOGNIZED_REASON_CODE");
                            default:
                                // If this happens, it indicates that an additional fake NACK was added that is not
                                // being handled in this switch statement.
                                throw new RuntimeException("Got unrecognized fake NACK " + nackReason);
                        }
                    } else {
                        // Should never happen
                        throw new RuntimeException("Got unrecognized fake non-standard NACK " + nackReason);
                    }
                } else {
                    throw new ManualControlNackException((LynxNackException) exception);
                }
            } else if (exception instanceof IllegalArgumentException) {
                throw new InvalidParameterException(exception.getMessage());
            } else if (exception instanceof InterruptedException) {
                // Ultimately, this indicates that the Manual Control OpMode has been stopped.
                throw new WebSocketNotAuthorizedForManualControlException();
            } else if (exception instanceof WebSocketCommandException) {
                // Throw WebSocketCommandException as-is.
                throw (WebSocketCommandException) exception;
            } else if (exception instanceof RuntimeException) {
                throw (RuntimeException) exception;
            } else {
                throw new RuntimeException(exception);
            }
        }
    }
}

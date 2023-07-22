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

package org.firstinspires.ftc.robotserver.internal.webserver.websockets.command;

import androidx.annotation.NonNull;
import com.google.gson.JsonSyntaxException;
import com.qualcomm.robotcore.util.RobotLog;
import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.*;

public abstract class WebSocketCommandHandler<P, R> implements WebSocketMessageTypeHandler {
    public static String TAG = "WSCommandHandler";

    /**
     * @return the class to decode the command payload to.
     */
    @NonNull
    protected abstract Class<P> getPayloadClass();

    /**
     * @return the class you will be returning in the response.
     */
    @NonNull
    protected abstract Class<R> getResultClass();

    /**
     *
     * @param value the decoded value of the command's commandPayload
     * @param webSocket a FtcWebSocket instance that can give information about the connection
     * @return the response to send back
     * @throws WebSocketCommandException if the command cannot be completed successfully
     */
    public abstract R handleCommand(P value, FtcWebSocket webSocket) throws WebSocketCommandException;

    @Override
    public final void handleMessage(FtcWebSocketMessage message, FtcWebSocket webSocket) {
        String payloadString = message.getPayload();
        WebSocketCommand command;
        try {
            command = SimpleGson.getInstance().fromJson(payloadString, WebSocketCommand.class);
        } catch(JsonSyntaxException e) {
            RobotLog.ee(TAG, "Payload is not valid. Make sure it has a key field, a " +
                    "commandPayload field, and is valid JSON: " + payloadString);
            return;
        }

        P payload = null;
        if (command.commandPayload != null && !command.commandPayload.isEmpty()) {
            payload = SimpleGson.getInstance().fromJson(command.commandPayload, getPayloadClass());
        }

        try {
            Object result = handleCommand(payload, webSocket);
            String resultJson = "{}";
            if(result != null) resultJson = SimpleGson.getInstance().toJson(result, getResultClass());
            webSocket.sendCommandResponse(WebSocketCommandResponse.createSuccess(command.commandKey, resultJson));
        } catch (WebSocketCommandException e) {
            webSocket.sendCommandResponse(WebSocketCommandResponse.createError(command.commandKey, e));
        } catch (RuntimeException e) {
            InternalWebSocketCommandException internalException = new InternalWebSocketCommandException(e.getMessage(), e);
            webSocket.sendCommandResponse(WebSocketCommandResponse.createError(command.commandKey, internalException));
        }
    }
}

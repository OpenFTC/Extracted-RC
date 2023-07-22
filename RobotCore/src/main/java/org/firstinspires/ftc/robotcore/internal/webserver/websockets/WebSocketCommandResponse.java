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

package org.firstinspires.ftc.robotcore.internal.webserver.websockets;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;

public class WebSocketCommandResponse {

    /**
     * Create a success response to send
     * @param commandKey should match the commandKey of the command being responded to.
     * @param response response to send back. Should not be null.
     * @return a Command Response with the response field set and the error field null.
     */
    public static WebSocketCommandResponse createSuccess(Object commandKey, @NonNull String response) {
        return new WebSocketCommandResponse(commandKey, response, null);
    }

    /**
     * Create an error response to send
     * @param commandKey should match the commandKey of the command being responded to.
     * @param exception error to send back. Should not be null.
     * @return a Command Response with the error field set and the response field null.
     */
    public static WebSocketCommandResponse createError(Object commandKey, @NonNull WebSocketCommandException exception) {
        // Messages in Java exceptions are stored in a detailMessage field, but we want to send a message field instead.
        JsonObject jsonObject = SimpleGson.getInstance().toJsonTree(exception, exception.getClass()).getAsJsonObject();
        jsonObject.addProperty("message", exception.getMessage());
        jsonObject.remove("detailMessage");
        return new WebSocketCommandResponse(commandKey, null, SimpleGson.getInstance().toJson(jsonObject));
    }

    Object commandKey;
    String response;
    String error;

    private WebSocketCommandResponse(Object commandKey, String response, String error) {
        this.commandKey = commandKey;
        this.response = response;
        this.error = error;
    }

    public Object getCommandKey() {
        return commandKey;
    }

    public String getResponse() {
        return response;
    }

    public String getError() {
        return error;
    }
}

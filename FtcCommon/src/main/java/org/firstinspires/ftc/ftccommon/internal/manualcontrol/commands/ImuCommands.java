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
package org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands;

import androidx.annotation.NonNull;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlCommandHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlOpMode;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlWebSocketHandler;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.GetImuVelocityParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.ImuParameters;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.ImuAngularVelocityResponse;
import org.firstinspires.ftc.ftccommon.internal.manualcontrol.responses.ImuQuaternionResponse;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketCommandException;
import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketMessageTypeHandler;

import java.util.Map;

public class ImuCommands {

    public static void register(Map<String, WebSocketMessageTypeHandler> handlerMap, ManualControlWebSocketHandler handler) {
        handlerMap.put("initializeImu", new InitializeImuCommand(handler));
        handlerMap.put("getImuQuaternion", new GetImuQuaternionCommand(handler));
        handlerMap.put("getImuAngularVelocity", new GetImuAngularVelocityCommand(handler));
    }

    public static class InitializeImuCommand extends ManualControlCommandHandler<ImuParameters, Void> {
        public InitializeImuCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }

        @NonNull @Override protected Class<ImuParameters> getPayloadClass() { return ImuParameters.class; }

        @NonNull @Override protected Class<Void> getResultClass() { return Void.class; }

        @Override public Void handleManualControlCommand(ImuParameters parameters) throws WebSocketCommandException {
            RevHubOrientationOnRobot orientationOnRobot = parameters.getOrientation();
            ManualControlOpMode.getInstance().initializeImu(new IMU.Parameters(orientationOnRobot));
            return null;
        }
    }

    public static class GetImuQuaternionCommand extends ManualControlCommandHandler<GetImuVelocityParameters, ImuQuaternionResponse> {
        public GetImuQuaternionCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }

        @NonNull @Override protected Class<GetImuVelocityParameters> getPayloadClass() { return GetImuVelocityParameters.class; }

        @NonNull @Override protected Class<ImuQuaternionResponse> getResultClass() { return ImuQuaternionResponse.class; }

        @Override
        public ImuQuaternionResponse handleManualControlCommand(GetImuVelocityParameters parameters) throws WebSocketCommandException {
            IMU imu = ManualControlOpMode.getInstance().getImu();
            Quaternion quaternion = imu.getRobotOrientationAsQuaternion();
            return new ImuQuaternionResponse(quaternion.w, quaternion.x, quaternion.y, quaternion.z);
        }
    }

    public static class GetImuAngularVelocityCommand extends ManualControlCommandHandler<GetImuVelocityParameters, ImuAngularVelocityResponse> {
        public GetImuAngularVelocityCommand(ManualControlWebSocketHandler manualControlWebSocketHandler) { super(manualControlWebSocketHandler); }

        @NonNull @Override protected Class<GetImuVelocityParameters> getPayloadClass() { return GetImuVelocityParameters.class; }

        @NonNull @Override protected Class<ImuAngularVelocityResponse> getResultClass() { return ImuAngularVelocityResponse.class; }

        @Override
        public ImuAngularVelocityResponse handleManualControlCommand(GetImuVelocityParameters parameters) throws WebSocketCommandException {
            IMU imu = ManualControlOpMode.getInstance().getImu();
            AngularVelocity velocity = imu.getRobotAngularVelocity(parameters.getUnit());
            return new ImuAngularVelocityResponse(velocity.xRotationRate, velocity.yRotationRate, velocity.zRotationRate);
        }
    }
}

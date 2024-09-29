/*
Copyright (c) 2024 REV Robotics

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
package com.qualcomm.hardware.rev;

import com.qualcomm.robotcore.hardware.ImuOrientationOnRobot;
import com.qualcomm.robotcore.hardware.QuaternionBasedImuHelper;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;

// Intentionally left as package-private
abstract class RevImuOrientationOnRobot implements ImuOrientationOnRobot {
    private final Quaternion robotCoordinateSystemFromPerspectiveOfImu;
    private final Quaternion imuRotationOffset;
    private final Quaternion angularVelocityTransform;

    protected RevImuOrientationOnRobot(Quaternion rotation) {
        // When an REV device with an IMU has its logo facing up and its
        // USB (for an Expansion or Control Hub) / I2C (for a REV 6-Axis IMU) port facing
        // forward, the IMU's axes are rotated by -90 degrees around the Z axis compared to the
        // Robot Coordinate System's axes, so we need to apply that rotation to whatever the user
        // has specified.
        Quaternion imuRotationWithinHub = QuaternionBasedImuHelper.quaternionFromZAxisRotation(-90, AngleUnit.DEGREES);
        rotation = rotation.multiply(imuRotationWithinHub, 0).normalized();

        // A rotation that will take the hub from the "default" orientation to its actual
        // orientation when applied in the Robot Coordinate System, will take the hub from its
        // actual orientation to the "default" orientation when applied in the IMU's coordinate
        // system, which is what the angular velocity transform is!
        this.angularVelocityTransform = new Quaternion(rotation.w, rotation.x, rotation.y, rotation.z, 0);

        // For the BNO055 and BHI260, the Z axis always points upward, just like in the Robot
        // Coordinate System, regardless of the starting orientation (this is accomplished using
        // their internal accelerometers). This means that the only axes that ever need to be
        // remapped are the X and Y axes, which we can do with the Z axis component of the rotation.
        this.robotCoordinateSystemFromPerspectiveOfImu = new Quaternion(rotation.w, 0, 0, rotation.z, 0).normalized();

        // The Z axis rotation will always start out at zero, so we don't want to include that in
        // the rotation offset.
        rotation = rotation.multiply(robotCoordinateSystemFromPerspectiveOfImu.inverse(), 0).normalized();

        // The rotation was provided as the rotation that needs to be applied to a hub in the
        // "default" orientation to get it to its actual orientation. The offset that will be
        // applied needs to be the opposite (inverse) of that.
        this.imuRotationOffset = rotation.inverse().normalized();
    }

    @Override public Quaternion imuCoordinateSystemOrientationFromPerspectiveOfRobot() {
        return robotCoordinateSystemFromPerspectiveOfImu;
    }

    @Override public Quaternion imuRotationOffset() {
        return imuRotationOffset;
    }

    @Override public Quaternion angularVelocityTransform() {
        return angularVelocityTransform;
    }

    static Orientation zyxOrientation(double z, double y, double x) {
        return new Orientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES, (float) z, (float) y, (float) x, 0);
    }

    static Orientation xyzOrientation(double x, double y, double z) {
        return new Orientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, (float) x, (float) y, (float) z, 0);
    }
}

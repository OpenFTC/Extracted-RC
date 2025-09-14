/* Copyright (c) 2025 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.hardware.andymark.AndyMarkIMUOrientationOnRobot;
import com.qualcomm.hardware.andymark.AndyMarkIMUOrientationOnRobot.I2cPortFacingDirection;
import com.qualcomm.hardware.andymark.AndyMarkIMUOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

/*
 * This OpMode shows how to use the AndyMark IMU sensor. It assumes that the AndyMark IMU is
 * configured with the name "imu".
 *
 * The sample will display the current Yaw, Pitch and Roll of the robot.
 *
 * With the correct orientation parameters selected, pitch/roll/yaw should act as follows:
 *   Pitch value should INCREASE as the robot is tipped UP at the front. (Rotation about X)
 *   Roll value should INCREASE as the robot is tipped UP at the left side. (Rotation about Y)
 *   Yaw value should INCREASE as the robot is rotated Counter Clockwise. (Rotation about Z)
 *
 * The yaw can be reset (to zero) by pressing the Y button on the gamepad (Triangle on a PS4 controller).
 *
 * This specific sample assumes that the AndyMark IMU is mounted on one of the three orthogonal
 * planes (X/Y, X/Z or Y/Z) and that the AndyMark IMU has only been rotated in a range of 90 degree
 * increments.
 *
 * Note: if your AndyMark IMU is mounted on a surface angled at some non-90 Degree multiple (like
 * 30), then you should use the SensorAndyMarkIMUNonOrthogonal sample in this folder.
 *
 * This "Orthogonal" requirement means that:
 *
 * 1) The AndyMark logo printed on the top of the AndyMark IMU can ONLY be pointing in one of six directions:
 *    FORWARD, BACKWARD, UP, DOWN, LEFT and RIGHT.
 *
 * 2) The I2C port can only be pointing in one of the same six directions:
 *    FORWARD, BACKWARD, UP, DOWN, LEFT and RIGHT.
 *
 * So, to fully define how your AndyMark IMU is mounted to the robot, you must simply specify:
 *    LogoFacingDirection
 *    I2cPortFacingDirection
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 *
 * Finally, choose the two correct parameters to define how your AndyMark IMU is mounted and edit
 * this OpMode to use those parameters.
 */
@TeleOp(name = "Sensor: AndyMark IMU Orthogonal", group = "Sensor")
@Disabled   // Comment this out to add to the OpMode list
public class SensorAndyMarkIMUOrthogonal extends LinearOpMode
{
    // The AndyMark IMU sensor object
    private IMU imu;

    //----------------------------------------------------------------------------------------------
    // Main logic
    //----------------------------------------------------------------------------------------------

    @Override public void runOpMode() throws InterruptedException {

        // Retrieve and initialize the AndyMark IMU.
        // This sample expects the AndyMark IMU to be named "imu".
        imu = hardwareMap.get(IMU.class, "imu");

        /* Define how the AndyMark IMU is mounted to the robot to get the correct Yaw, Pitch, and
         * Roll values.
         *
         * Two input parameters are required to fully specify the Orientation.
         * The first parameter specifies the direction the AndyMark logo on the IMU is pointing.
         * The second parameter specifies the direction the I2C port on the IMU is pointing.
         * All directions are relative to the robot, and left/right is as-viewed from behind the robot.
         */

        /* The next two lines define the IMU orientation.
         * To Do:  EDIT these two lines to match YOUR mounting configuration.
         */
        LogoFacingDirection logoDirection = LogoFacingDirection.UP;
        I2cPortFacingDirection i2cDirection = I2cPortFacingDirection.FORWARD;

        AndyMarkIMUOrientationOnRobot orientationOnRobot = new AndyMarkIMUOrientationOnRobot(logoDirection, i2cDirection);

        // Now initialize the AndyMark IMU with this mounting orientation.
        // Note: if you choose two conflicting directions, this initialization will cause a code exception.
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        // Loop and update the dashboard.
        while (!isStopRequested()) {
            telemetry.addData("IMU orientation", "Logo=%s   I2C=%s\n ", logoDirection, i2cDirection);

            // Check to see if heading reset is requested.
            if (gamepad1.y) {
                telemetry.addData("Yaw", "Resetting\n");
                imu.resetYaw();
            } else {
                telemetry.addData("Yaw", "Press Y (triangle) on Gamepad to reset\n");
            }

            // Retrieve rotational angles and velocities.
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            AngularVelocity angularVelocity = imu.getRobotAngularVelocity(AngleUnit.DEGREES);

            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
            telemetry.addData("Pitch (X)", "%.2f Deg.", orientation.getPitch(AngleUnit.DEGREES));
            telemetry.addData("Roll (Y)", "%.2f Deg.\n", orientation.getRoll(AngleUnit.DEGREES));
            telemetry.addData("Yaw (Z) velocity", "%.2f Deg/Sec", angularVelocity.zRotationRate);
            telemetry.addData("Pitch (X) velocity", "%.2f Deg/Sec", angularVelocity.xRotationRate);
            telemetry.addData("Roll (Y) velocity", "%.2f Deg/Sec", angularVelocity.yRotationRate);
            telemetry.update();
        }
    }
}

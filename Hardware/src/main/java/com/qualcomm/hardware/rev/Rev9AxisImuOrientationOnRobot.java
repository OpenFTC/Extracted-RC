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

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;

/**
 * The orientation at which a given REV External IMU is mounted to a robot.
 * <p>
 * Pass to the {@link com.qualcomm.robotcore.hardware.IMU.Parameters} constructor.
 */
public class Rev9AxisImuOrientationOnRobot extends RevImuOrientationOnRobot {
    public enum LogoFacingDirection {
        UP,
        DOWN,
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }

    public enum I2cPortFacingDirection {
        UP,
        DOWN,
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }

    /**
     * Constructs a {@link Rev9AxisImuOrientationOnRobot} for a REV 9-Axis IMU that is mounted orthogonally to a
     * robot. This is the easiest constructor to use. Simply specify which direction on the robot that the REV
     * logo on the IMU is facing, and the direction that the I2C port on the IMU is facing.
     * @param logoFacingDirection The direction that the REV logo is facing on the robot.
     * @param i2cPortFacingDirection The direction that the I2C port is facing on the robot.
     */
    public Rev9AxisImuOrientationOnRobot(LogoFacingDirection logoFacingDirection, I2cPortFacingDirection i2cPortFacingDirection) {
        this(friendlyApiToOrientation(logoFacingDirection, i2cPortFacingDirection));
    }

    /**
     * Constructs a {@link Rev9AxisImuOrientationOnRobot} for a REV 9-Axis IMU that is mounted at any arbitrary
     * angle on a robot using an {@link Orientation} object.
     * @param rotation The rotation (defined within the Robot Coordinate System, and specified as an
     *                 {@link Orientation}) that would need to be applied in order to rotate a REV 9-Axis IMU from
     *                 having its logo facing up and the I2C port facing forward, to its actual orientation on the
     *                 robot.
     */
    public Rev9AxisImuOrientationOnRobot(Orientation rotation) {
        this(Quaternion.fromMatrix(rotation.getRotationMatrix(), 0));
    }

    /**
     * Constructs a {@link Rev9AxisImuOrientationOnRobot} for a REV 9-Axis IMU that is mounted at any arbitrary
     * angle on a robot using a {@link Quaternion} object.
     * @param rotation The rotation (defined within the Robot Coordinate System, and specified as a
     *                 {@link Quaternion}) that would need to be applied in order to rotate a REV 9-Axis IMU from
     *                 having its logo facing up and the I2C port facing forward, to its actual orientation on the robot.
     */
    public Rev9AxisImuOrientationOnRobot(Quaternion rotation) {
        super(rotation);
    }

    protected static Orientation friendlyApiToOrientation(LogoFacingDirection logoFacingDirection, I2cPortFacingDirection i2cPortFacingDirection) {
        if (logoFacingDirection == LogoFacingDirection.UP) {
            if (i2cPortFacingDirection == I2cPortFacingDirection.UP) {
                throwIllegalImuOrientationException();
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.DOWN) {
                throwIllegalImuOrientationException();
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.FORWARD) {
                return zyxOrientation(0, 0, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.BACKWARD) {
                return zyxOrientation(180, 0, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.LEFT) {
                return zyxOrientation(90, 0, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.RIGHT) {
                return zyxOrientation(-90, 0, 0);
            }
        } else if (logoFacingDirection == LogoFacingDirection.DOWN) {
            if (i2cPortFacingDirection == I2cPortFacingDirection.UP) {
                throwIllegalImuOrientationException();
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.DOWN) {
                throwIllegalImuOrientationException();
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.FORWARD) {
                return zyxOrientation(0, 180, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.BACKWARD) {
                return zyxOrientation(180, 180, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.LEFT) {
                return zyxOrientation(90, 180, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.RIGHT) {
                return zyxOrientation(-90, 180, 0);
            }
        } else if (logoFacingDirection == LogoFacingDirection.FORWARD) {
            if (i2cPortFacingDirection == I2cPortFacingDirection.UP) {
                return xyzOrientation(90, 180, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.DOWN) {
                return xyzOrientation(-90, 0, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.FORWARD) {
                throwIllegalImuOrientationException();
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.BACKWARD) {
                throwIllegalImuOrientationException();
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.LEFT) {
                return xyzOrientation(-90, 0, 90);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.RIGHT) {
                return xyzOrientation(-90, 0, -90);
            }
        } else if (logoFacingDirection == LogoFacingDirection.BACKWARD) {
            if (i2cPortFacingDirection == I2cPortFacingDirection.UP) {
                return xyzOrientation(90, 0, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.DOWN) {
                return xyzOrientation(90, 0, 180);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.FORWARD) {
                throwIllegalImuOrientationException();
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.BACKWARD) {
                throwIllegalImuOrientationException();
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.LEFT) {
                return xyzOrientation(90, 0, 90);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.RIGHT) {
                return xyzOrientation(90, 0, -90);
            }
        } else if (logoFacingDirection == LogoFacingDirection.LEFT) {
            if (i2cPortFacingDirection == I2cPortFacingDirection.UP) {
                return xyzOrientation(90, -90, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.DOWN) {
                return xyzOrientation(-90, -90, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.FORWARD) {
                return xyzOrientation(0, -90, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.BACKWARD) {
                return xyzOrientation(0, -90, 180);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.LEFT) {
                throwIllegalImuOrientationException();
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.RIGHT) {
                throwIllegalImuOrientationException();
            }
        } else if (logoFacingDirection == LogoFacingDirection.RIGHT) {
            if (i2cPortFacingDirection == I2cPortFacingDirection.UP) {
                return zyxOrientation(90, 0, 90);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.DOWN) {
                return zyxOrientation(-90, 0, -90);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.FORWARD) {
                return zyxOrientation(0, 90, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.BACKWARD) {
                return zyxOrientation(180, -90, 0);
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.LEFT) {
                throwIllegalImuOrientationException();
            } else if (i2cPortFacingDirection == I2cPortFacingDirection.RIGHT) {
                throwIllegalImuOrientationException();
            }
        }

        throw new RuntimeException("The FTC SDK developers forgot about this combination, please file a bug report.");
    }

    private static void throwIllegalImuOrientationException() {
        throw new IllegalArgumentException("The specified REV 9-Axis IMU orientation is physically impossible");
    }

    public static Orientation zyxOrientation(double z, double y, double x) {
        return RevImuOrientationOnRobot.zyxOrientation(z, y, x);
    }

    public static Orientation xyzOrientation(double x, double y, double z) {
        return RevImuOrientationOnRobot.xyzOrientation(x, y, z);
    }
}

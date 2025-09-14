/*
 * SPDX-License-Identifier: BSD-3-Clause-Clear
 * Copyright (c) 2024 REV Robotics
 * 
 * This file consists of code that was copied, with modifications, from
 * sdk/libs/Hardware/src/main/java/com/qualcomm/hardware/rev/Rev9AxisImuOrientationOnRobot.java and
 * sdk/libs/Hardware/src/main/java/com/qualcomm/hardware/rev/RevImuOrientationOnRobot.java
 */
package com.qualcomm.hardware.andymark;

import com.qualcomm.robotcore.hardware.ImuOrientationOnRobot;
import com.qualcomm.robotcore.hardware.QuaternionBasedImuHelper;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;

/**
 * The orientation at which an AndyMark IMU is mounted to a robot.
 * <p>
 * Pass to the {@link com.qualcomm.robotcore.hardware.IMU.Parameters} constructor.
 */
public class AndyMarkIMUOrientationOnRobot implements ImuOrientationOnRobot {
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

    private final Quaternion robotCoordinateSystemFromPerspectiveOfImu;
    private final Quaternion imuRotationOffset;
    private final Quaternion angularVelocityTransform;

    /**
     * Constructs an {@link AndyMarkIMUOrientationOnRobot} for an AndyMark IMU that is mounted orthogonally to a
     * robot. This is the easiest constructor to use. Simply specify which direction on the robot that the AndyMark
     * logo on the IMU is facing, and the direction that the I2C port on the IMU is facing.
     * @param logoFacingDirection The direction that the AndyMark logo is facing on the robot.
     * @param i2cPortFacingDirection The direction that the I2C port is facing on the robot.
     */
    public AndyMarkIMUOrientationOnRobot(LogoFacingDirection logoFacingDirection, I2cPortFacingDirection i2cPortFacingDirection) {
        this(friendlyApiToOrientation(logoFacingDirection, i2cPortFacingDirection));
    }

    /**
     * Constructs an {@link AndyMarkIMUOrientationOnRobot} for an AndyMark IMU that is mounted at any arbitrary
     * angle on a robot using an {@link Orientation} object.
     * @param rotation The rotation (defined within the Robot Coordinate System, and specified as an
     *                 {@link Orientation}) that would need to be applied in order to rotate an AndyMark IMU from
     *                 having its logo facing up and the I2C port facing forward, to its actual orientation on the
     *                 robot.
     */
    public AndyMarkIMUOrientationOnRobot(Orientation rotation) {
        this(Quaternion.fromMatrix(rotation.getRotationMatrix(), 0));
    }

    /**
     * Constructs an {@link AndyMarkIMUOrientationOnRobot} for an AndyMark IMU that is mounted at any arbitrary
     * angle on a robot using a {@link Quaternion} object.
     * @param rotation The rotation (defined within the Robot Coordinate System, and specified as a
     *                 {@link Quaternion}) that would need to be applied in order to rotate an AndyMark IMU from
     *                 having its logo facing up and the I2C port facing forward, to its actual orientation on the robot.
     */
    public AndyMarkIMUOrientationOnRobot(Quaternion rotation) {
        // When an AndyMark IMU has its logo facing up and its I2C port facing forward, the IMU's axes
        // are rotated by -90 degrees around the Z axis compared to the Robot Coordinate System's axes,
        // so we need to apply that rotation to whatever the user has specified.
        Quaternion imuRotation = QuaternionBasedImuHelper.quaternionFromZAxisRotation(-90, AngleUnit.DEGREES);
        rotation = rotation.multiply(imuRotation, 0).normalized();

        // A rotation that will take the IMU from the "default" orientation to its actual
        // orientation when applied in the Robot Coordinate System, will take the IMU from its
        // actual orientation to the "default" orientation when applied in the IMU's coordinate
        // system, which is what the angular velocity transform is!
        this.angularVelocityTransform = new Quaternion(rotation.w, rotation.x, rotation.y, rotation.z, 0);

        // For the BNO055, the Z axis always points upward, just like in the Robot Coordinate System,
        // regardless of the starting orientation (this is accomplished using their internal
        // accelerometers). This means that the only axes that ever need to be remapped are the X and Y
        // axes, which we can do with the Z axis component of the rotation.
        this.robotCoordinateSystemFromPerspectiveOfImu = new Quaternion(rotation.w, 0, 0, rotation.z, 0).normalized();

        // The Z axis rotation will always start out at zero, so we don't want to include that in
        // the rotation offset.
        rotation = rotation.multiply(robotCoordinateSystemFromPerspectiveOfImu.inverse(), 0).normalized();

        // The rotation was provided as the rotation that needs to be applied to an IMU in the
        // "default" orientation to get it to its actual orientation. The offset that will be
        // applied needs to be the opposite (inverse) of that.
        this.imuRotationOffset = rotation.inverse().normalized();
    }

    @Override
    public Quaternion imuCoordinateSystemOrientationFromPerspectiveOfRobot() {
        return robotCoordinateSystemFromPerspectiveOfImu;
    }

    @Override
    public Quaternion imuRotationOffset() {
        return imuRotationOffset;
    }

    @Override
    public Quaternion angularVelocityTransform() {
        return angularVelocityTransform;
    }

    protected static Orientation friendlyApiToOrientation(
            LogoFacingDirection logoFacingDirection,
            I2cPortFacingDirection i2cPortFacingDirection) {
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
        throw new IllegalArgumentException("The specified AndyMark IMU orientation is physically impossible");
    }

    public static Orientation zyxOrientation(double z, double y, double x) {
        return new Orientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES, (float) z, (float) y, (float) x, 0);
    }

    public static Orientation xyzOrientation(double x, double y, double z) {
        return new Orientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, (float) x, (float) y, (float) z, 0);
    }
}

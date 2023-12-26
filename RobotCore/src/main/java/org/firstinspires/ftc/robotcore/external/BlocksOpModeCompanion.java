/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.firstinspires.ftc.robotcore.external;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * An abstract base class that provides access to hardwareMap, telemetry, gamepad1, and gamepad2,
 * in order to assist a novice Java coder who wants to implement some code in Java that can be
 * called from a Blocks OpMode.
 *
 * The use of this class is not required for exporting a method to the Blocks programming
 * environment. See {@link ExportToBlocks} for details on how to export a method to Blocks.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public abstract class BlocksOpModeCompanion {
  /**
   * The currently running Blocks OpMode, as an {@link OpMode}.
   */
  public static OpMode opMode;

  /**
   * The currently running Blocks OpMode, as an {@link LinearOpMode}.
   */
  public static LinearOpMode linearOpMode;

  /**
   * Hardware mappings.
   */
  public static HardwareMap hardwareMap;

  /**
   * The {@link #telemetry} field contains an object in which a user may accumulate data which
   * is to be transmitted to the driver station. This data is automatically transmitted to the
   * driver station on a regular, periodic basis.
   */
  public static Telemetry telemetry;

  /**
   * Gamepad 1
   */
  public static Gamepad gamepad1;

  /**
   * Gamepad 2
   */
  public static Gamepad gamepad2;
}

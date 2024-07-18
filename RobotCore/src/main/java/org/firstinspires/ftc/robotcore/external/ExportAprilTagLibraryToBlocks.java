/*
 * Copyright 2024 Google LLC
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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;

/**
 * {@link ExportAprilTagLibraryToBlocks} indicates that a method that returns an AprilTagLibrary is exported
 * to the Blocks programming environment.
 * This annotation provides a way for the Java coder to specify some UI attributes of the
 * "call Java method" block.
 * The corresponding block will appear in the Blocks toolbox along with the built-in tag libraries.
 *
 * The method must satisfy all of these requirements:
 * <ul>
 * <li> it has the ExportAprilTagLibraryToBlocks annotation
 * <li> it returns an AprilTagLibrary
 * <li> it is public and static
 * <li> it is not abstract
 * <li> it is in a class that is in the org.firstinspires.ftc.teamcode package<br>
 *      or<br>
 *      it is in a class that has the {@link ExportClassToBlocks} annotation
 * </ul>
 *
 * @author lizlooney@google.com (Liz Looney)
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportAprilTagLibraryToBlocks {
  /**
   * The hue of the block.
   */
  int color() default 289;
  /**
   * The heading to be used on the block.
   */
  String heading() default "call";
  /**
   * The comment to be used on the block. If empty, the block will
   * not have a comment by default.
   */
  String comment() default "";

  /**
   * The tooltip to be used on the block. If empty, a tooltip naming the method,
   * its enclosing class, and its return type will be used.
   */
  String tooltip() default "";

  /**
   * The parameter labels to be shown next to each socket on the block. If empty, or if the length
   * of the array does not match the method's number of parameters, all sockets will be labeled
   * with the parameter types. If an individual element is empty, the corresponding socket will be
   * labeled with the parameter type.
   *
   * Parameter labels for parameters whose type is LinearOpMode, OpMode, HardwareMap, Telemetry,
   * and Gamepad are ignored as the block does not have sockets corresponding to these parameters.
   * However, an entry must be present in the array here since the length of the array must match
   * the method's number of parameters.
   */
  String[] parameterLabels() default {};

  /**
   * The parameter default values to be used for each socket on the block.
   * Similar to the parameter labels, the length of the array must match the method's number of
   * parameters.
   *
   * The values are strings, which will be parsed to the actual type of the parameter.
   *
   * Parameter values for parameters whose type is LinearOpMode, OpMode, HardwareMap, Telemetry,
   * and Gamepad are ignored as the block does not have sockets corresponding to these parameters.
   * However, an entry must be present in the array here since the length of the array must match
   * the method's number of parameters.
   */
  String[] parameterDefaultValues() default {};
}

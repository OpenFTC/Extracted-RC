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

package com.google.blocks.ftcrobotcontroller.util;

import org.firstinspires.ftc.robotcore.external.tfod.TfodParameters;

/**
 * A class that provides constants related to the current game.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public class CurrentGame {
  // TODO(lizlooney): This file should be updated when the FTC game changes.
  // Also, the following other files should be updated:
  // * lib/RobotCore/src/main/java/org/firstinspires/ftc/robotcore/external/tfod/TfodParameters.java

  public static final String CURRENT_GAME_NAME = "CENTERSTAGE";

  public static final String CURRENT_GAME_NAME_NO_SPACES = "CENTERSTAGE";

  public static final String TFOD_MODEL_ASSET = TfodParameters.CurrentGame.MODEL_ASSET;
  public static final String[] TFOD_LABELS = TfodParameters.CurrentGame.LABELS;

  private CurrentGame() {
    // No instantiation allowed.
  }
}

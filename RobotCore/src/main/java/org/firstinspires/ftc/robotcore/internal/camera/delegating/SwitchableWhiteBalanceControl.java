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

package org.firstinspires.ftc.robotcore.internal.camera.delegating;

import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.WhiteBalanceControl;

class SwitchableWhiteBalanceControl extends SwitchableCameraControl<WhiteBalanceControl> implements WhiteBalanceControl {
  private static final int UNKNOWN_TEMPERATURE = -1;

  SwitchableWhiteBalanceControl() {
    super(WhiteBalanceControl.class);
  }

  @Override
  public int getWhiteBalanceTemperature() {
    WhiteBalanceControl whiteBalanceControl = getCameraControl();
    return (whiteBalanceControl != null) ? whiteBalanceControl.getWhiteBalanceTemperature() : UNKNOWN_TEMPERATURE;
  }

  @Override
  public boolean setWhiteBalanceTemperature(int temperature) {
    WhiteBalanceControl whiteBalanceControl = getCameraControl();
    return (whiteBalanceControl != null) ? whiteBalanceControl.setWhiteBalanceTemperature(temperature) : false;
  }

  @Override
  public Mode getMode() {
    WhiteBalanceControl whiteBalanceControl = getCameraControl();
    return (whiteBalanceControl != null) ? whiteBalanceControl.getMode() : Mode.UNKNOWN;
  }

  @Override
  public boolean setMode(Mode mode) {
    WhiteBalanceControl whiteBalanceControl = getCameraControl();
    return (whiteBalanceControl != null) ? whiteBalanceControl.setMode(mode) : false;
  }

  @Override
  public int getMinWhiteBalanceTemperature() {
    WhiteBalanceControl whiteBalanceControl = getCameraControl();
    return (whiteBalanceControl != null) ? whiteBalanceControl.getMinWhiteBalanceTemperature() : UNKNOWN_TEMPERATURE;
  }

  @Override
  public int getMaxWhiteBalanceTemperature() {
    WhiteBalanceControl whiteBalanceControl = getCameraControl();
    return (whiteBalanceControl != null) ? whiteBalanceControl.getMaxWhiteBalanceTemperature() : UNKNOWN_TEMPERATURE;
  }
}

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

import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;

class SwitchableGainControl extends SwitchableCameraControl<GainControl> implements GainControl {
  private static final int UNKNOWN_GAIN = -1;

  SwitchableGainControl() {
    super(GainControl.class);
  }

  @Override
  public int getMinGain() {
    GainControl gainControl = getCameraControl();
    return (gainControl != null) ? gainControl.getMinGain() : UNKNOWN_GAIN;
  }

  @Override
  public int getMaxGain() {
    GainControl gainControl = getCameraControl();
    return (gainControl != null) ? gainControl.getMaxGain() : UNKNOWN_GAIN;
  }

  @Override
  public int getGain() {
    GainControl gainControl = getCameraControl();
    return (gainControl != null) ? gainControl.getGain() : UNKNOWN_GAIN;
  }

  @Override
  public boolean setGain(int gain) {
    GainControl gainControl = getCameraControl();
    return (gainControl != null) ? gainControl.setGain(gain) : false;
  }
}

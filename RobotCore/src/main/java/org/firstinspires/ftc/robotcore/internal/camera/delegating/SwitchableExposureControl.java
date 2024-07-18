/*
Copyright (c) 2018 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
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
package org.firstinspires.ftc.robotcore.internal.camera.delegating;

import java.util.concurrent.TimeUnit;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.internal.collections.MutableReference;

class SwitchableExposureControl extends SwitchableCameraControl<ExposureControl> implements ExposureControl {
  private static final int UNKNOWN_EXPOSURE = -1;

  SwitchableExposureControl() {
    super(ExposureControl.class);
  }

  @Override
  public Mode getMode() {
    ExposureControl exposureControl = getCameraControl();
    return (exposureControl != null) ? exposureControl.getMode() : Mode.Unknown;
  }

  @Override
  public boolean setMode(Mode mode) {
    ExposureControl exposureControl = getCameraControl();
    return (exposureControl != null) ? exposureControl.setMode(mode) : false;
  }

  @Override
  public boolean isModeSupported(Mode mode) {
    ExposureControl exposureControl = getCameraControl();
    return (exposureControl != null) ? exposureControl.isModeSupported(mode) : false;
  }

  @Override
  public long getMinExposure(TimeUnit resultUnit) {
    ExposureControl exposureControl = getCameraControl();
    return (exposureControl != null) ? exposureControl.getMinExposure(resultUnit) : UNKNOWN_EXPOSURE;
  }

  @Override
  public long getMaxExposure(TimeUnit resultUnit) {
    ExposureControl exposureControl = getCameraControl();
    return (exposureControl != null) ? exposureControl.getMaxExposure(resultUnit) : UNKNOWN_EXPOSURE;
  }

  @Override
  public long getExposure(TimeUnit resultUnit) {
    ExposureControl exposureControl = getCameraControl();
    return (exposureControl != null) ? exposureControl.getExposure(resultUnit) : UNKNOWN_EXPOSURE;
  }

  @Override
  public long getCachedExposure(final TimeUnit resultUnit, final MutableReference<Boolean> refreshed, final long permittedStaleness, final TimeUnit permittedStnit) {
    ExposureControl exposureControl = getCameraControl();
    return (exposureControl != null) ? exposureControl.getCachedExposure(resultUnit, refreshed, permittedStaleness, permittedStnit) : UNKNOWN_EXPOSURE;
  }

  @Override
  public boolean setExposure(long duration, TimeUnit durationUnit) {
    ExposureControl exposureControl = getCameraControl();
    return (exposureControl != null) ? exposureControl.setExposure(duration, durationUnit) : false;
  }

  @Override
  public boolean isExposureSupported() {
    ExposureControl exposureControl = getCameraControl();
    return (exposureControl != null) ? exposureControl.isExposureSupported() : false;
  }

  @Override
  public boolean getAePriority() {
    ExposureControl exposureControl = getCameraControl();
    return (exposureControl != null) ? exposureControl.getAePriority() : false;
  }

  @Override
  public boolean setAePriority(boolean priority) {
    ExposureControl exposureControl = getCameraControl();
    return (exposureControl != null) ? exposureControl.setAePriority(priority) : false;
  }
}

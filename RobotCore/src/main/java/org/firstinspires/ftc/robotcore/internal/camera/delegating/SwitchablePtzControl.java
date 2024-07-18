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

import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.PtzControl;

class SwitchablePtzControl extends SwitchableCameraControl<PtzControl> implements PtzControl {
  private static final int UNKNOWN_ZOOM = -1;

  SwitchablePtzControl() {
    super(PtzControl.class);
  }

  @Override
  public PanTiltHolder getPanTilt() {
    PtzControl ptzControl = getCameraControl();
    return (ptzControl != null) ? ptzControl.getPanTilt() : new PanTiltHolder();
  }

  @Override
  public boolean setPanTilt(PanTiltHolder panTiltHolder) {
    PtzControl ptzControl = getCameraControl();
    return (ptzControl != null) ? ptzControl.setPanTilt(panTiltHolder) : false;
  }

  @Override
  public PanTiltHolder getMinPanTilt() {
    PtzControl ptzControl = getCameraControl();
    return (ptzControl != null) ? ptzControl.getMinPanTilt() : new PanTiltHolder();
  }

  @Override
  public PanTiltHolder getMaxPanTilt() {
    PtzControl ptzControl = getCameraControl();
    return (ptzControl != null) ? ptzControl.getMaxPanTilt() : new PanTiltHolder();
  }
 
  @Override
  public int getZoom() {
    PtzControl ptzControl = getCameraControl();
    return (ptzControl != null) ? ptzControl.getZoom() : UNKNOWN_ZOOM;
  }

  @Override
  public boolean setZoom(int zoom) {
    PtzControl ptzControl = getCameraControl();
    return (ptzControl != null) ? ptzControl.setZoom(zoom) : false;
  }

  @Override
  public int getMinZoom() {
    PtzControl ptzControl = getCameraControl();
    return (ptzControl != null) ? ptzControl.getMinZoom() : UNKNOWN_ZOOM;
  }

  @Override
  public int getMaxZoom() {
    PtzControl ptzControl = getCameraControl();
    return (ptzControl != null) ? ptzControl.getMaxZoom() : UNKNOWN_ZOOM;
  }
}

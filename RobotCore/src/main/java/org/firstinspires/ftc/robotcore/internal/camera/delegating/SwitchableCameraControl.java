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

import androidx.annotation.Nullable;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.CameraControl;

abstract class SwitchableCameraControl<T extends CameraControl> implements DelegatingCameraControl {
  private final Class<T> controlType;
  private final Object lockCamera = new Object();
  protected Camera camera;

  protected SwitchableCameraControl(Class<T> controlType) {
    this.controlType = controlType;
  }

  @Override
  public void onCameraChanged(@Nullable Camera camera) {
    synchronized (lockCamera) {
      this.camera = camera;
    }
  }

  protected T getCameraControl() {
    synchronized (lockCamera) {
      return (camera != null) ? camera.getControl(controlType) : null;
    }
  }
}

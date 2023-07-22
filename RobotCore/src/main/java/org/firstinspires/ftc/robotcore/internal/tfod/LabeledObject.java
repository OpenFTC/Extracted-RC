/*
 * Copyright 2022 Google LLC
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

package org.firstinspires.ftc.robotcore.internal.tfod;

import android.graphics.RectF;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import org.firstinspires.ftc.robotcore.internal.system.Misc;


/**
 * Represents an object with a label, confidence, and location (left, top, right, bottom).
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class LabeledObject {

  final @NonNull String label;
  final int color;
  final float confidence;
  final ZoomHelper zoomHelper;
  final CoordinateSystem coordinateSystem;
  final float left;
  final float top;
  final float right;
  final float bottom;

  enum CoordinateSystem {
    TRACKER,
    ZOOM_AREA,
    CAMERA,
  }

  LabeledObject(@NonNull String label, int color, float confidence,
      ZoomHelper zoomHelper, CoordinateSystem coordinateSystem,
      float left, float top, float right, float bottom) {
    this.label = label;
    this.color = color;
    this.confidence = confidence;
    this.zoomHelper = zoomHelper;
    this.coordinateSystem = coordinateSystem;
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  LabeledObject(@NonNull LabeledObject other,
      CoordinateSystem coordinateSystem, float left, float top, float right, float bottom) {
    this(other.label, other.color, other.confidence, other.zoomHelper, coordinateSystem, left, top, right, bottom);
  }

  LabeledObject(@NonNull LabeledObject other, int color) {
    this(other.label, color, other.confidence, other.zoomHelper, other.coordinateSystem, other.left, other.top, other.right, other.bottom);
  }

  RectF getLocation() {
    return new RectF(left, top, right, bottom);
  }

  float width() {
    return right - left;
  }

  float height() {
    return bottom - top;
  }

  RectF getScaledLocation(float scaleBmpPxToCanvasPx) {
    return new RectF(left * scaleBmpPxToCanvasPx, top * scaleBmpPxToCanvasPx,
        right * scaleBmpPxToCanvasPx, bottom * scaleBmpPxToCanvasPx);
  }

  String getText() {
    return !TextUtils.isEmpty(label)
        ? String.format("%s %.2f", label, confidence)
        : String.format("%.2f", confidence);
  }

  void checkCoordinateSystem(CoordinateSystem expected) {
    if (coordinateSystem != expected) {
      throw new RuntimeException("Expected coordinateSystem to be " + expected + " but it was " + coordinateSystem);
    }
  }

  LabeledObject convertToCamera() {
    checkCoordinateSystem(CoordinateSystem.ZOOM_AREA);
    int dx = zoomHelper.left();
    int dy = zoomHelper.top();
    return new LabeledObject(label, color, confidence, zoomHelper, CoordinateSystem.CAMERA,
        left + dx, top + dy, right + dx, bottom + dy);
  }

  @Override
  public String toString() {
    return "LabelObject " + label + " " + ((int) confidence) + " " +
        coordinateSystem + ": "  + ((int) left) + ", " + ((int) top) + " " + ((int) width()) + " x " + ((int) height());
  }
}

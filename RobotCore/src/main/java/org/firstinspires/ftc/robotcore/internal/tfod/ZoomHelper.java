/*
 * Copyright (C) 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.firstinspires.ftc.robotcore.internal.tfod;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import java.util.Objects;

class ZoomHelper {
  private static final Paint paint = new Paint();
  static {
    paint.setColor(Color.WHITE);
    paint.setAlpha(128);
    paint.setStyle(Style.FILL);
  }

  private final double magnification;
  private final Rect zoomArea;

  ZoomHelper(double magnification, double modelAspectRatio, int frameWidth, int frameHeight) {
    Zoom.validateArguments(magnification);
    this.magnification = magnification;
    zoomArea = Zoom.getZoomArea(magnification, modelAspectRatio, frameWidth, frameHeight);
  }

  int left() {
    return zoomArea.left;
  }

  int top() {
    return zoomArea.top;
  }

  int right() {
    return zoomArea.right;
  }

  int bottom() {
    return zoomArea.bottom;
  }

  int width() {
    return zoomArea.width();
  }

  int height() {
    return zoomArea.height();
  }

  boolean hasZoomChanged(Zoom zoom) {
    return !areEqual(magnification, zoom.magnification);
  }

  static boolean areEqual(double a, double b) {
    return Math.abs(a - b) <= 0.0001;
  }

  void blurAroundZoomArea(Canvas canvas) {
    canvas.save();
    // TODO(lizlooney); with API 26, we should use clipOutRect instead.
    canvas.clipRect(zoomArea.left, zoomArea.top, zoomArea.right, zoomArea.bottom,
        Region.Op.DIFFERENCE);
    canvas.drawPaint(paint);
    canvas.restore();
  }

  void blurAroundZoomArea(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx) {
    float left = zoomArea.left * scaleBmpPxToCanvasPx;
    float top = zoomArea.top * scaleBmpPxToCanvasPx;
    float right = zoomArea.right * scaleBmpPxToCanvasPx;
    float bottom = zoomArea.bottom * scaleBmpPxToCanvasPx;
    canvas.drawRect(0, 0, onscreenWidth, top, paint); // above the zoom area (from 0 to onscreenWidth)
    canvas.drawRect(0, bottom, onscreenWidth, onscreenHeight, paint); // below the zoom area (from 0 to onscreenWidth)
    canvas.drawRect(0, top, left, bottom, paint); // left of the zoom area (from top to bottom)
    canvas.drawRect(right, top, onscreenWidth, bottom, paint); // right of the zoom area (from top to bottom)
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ZoomHelper other = (ZoomHelper) o;
    return areEqual(magnification, other.magnification) &&
        zoomArea.equals(other.zoomArea);
  }

  @Override
  public int hashCode() {
    return Objects.hash(magnification, zoomArea);
  }
}

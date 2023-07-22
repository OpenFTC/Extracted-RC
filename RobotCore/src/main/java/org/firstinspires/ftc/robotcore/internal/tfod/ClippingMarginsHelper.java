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
import android.graphics.Region;

class ClippingMarginsHelper {
  private static final Paint paint = new Paint(); // Used to paint clipped edges.
  static {
    paint.setColor(Color.BLACK);
    paint.setStyle(Paint.Style.FILL);
  }

  private final int left;
  private final int top;
  private final int right;
  private final int bottom;

  ClippingMarginsHelper(int left, int top, int right, int bottom, int frameWidth, int frameHeight) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  boolean haveClippingMarginsChanged(ClippingMargins clippingMargins) {
    return left != clippingMargins.left ||
        top != clippingMargins.top ||
        right != clippingMargins.right ||
        bottom != clippingMargins.bottom;
  }

  void fillClippingMargins(Canvas canvas) {
    canvas.save();
    // TODO(lizlooney); with API 26, we should use clipOutRect instead.
    canvas.clipRect(left, top, canvas.getWidth() - right, canvas.getHeight() - bottom,
        Region.Op.DIFFERENCE);
    canvas.drawPaint(paint);
    canvas.restore();
  }

  void fillClippingMargins(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx) {
    float left = this.left * scaleBmpPxToCanvasPx;
    float top = this.top * scaleBmpPxToCanvasPx;
    float right = this.right * scaleBmpPxToCanvasPx;
    float bottom = this.bottom * scaleBmpPxToCanvasPx;
    canvas.drawRect(0, 0, onscreenWidth, top, paint); // top
    canvas.drawRect(0, onscreenHeight - bottom, onscreenWidth, onscreenHeight, paint); // bottom
    canvas.drawRect(0, top, left, onscreenHeight - bottom, paint); // left (not overlapping top or bottom)
    canvas.drawRect(onscreenWidth - right, top, onscreenWidth, onscreenHeight - bottom, paint); // right (not overlapping top or bottom)
  }
}

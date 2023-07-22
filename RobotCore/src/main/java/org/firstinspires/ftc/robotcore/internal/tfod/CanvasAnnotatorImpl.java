/*
 * Copyright 2023 Google LLC
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

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.tfod.CanvasAnnotator;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;


/**
 * CanvasAnnotatorImpl draws the recognized objects onto a canvas.
 */
class CanvasAnnotatorImpl implements CanvasAnnotator {
  private static final Paint trackerBoxPaint = new Paint();
  static {
    trackerBoxPaint.setColor(Color.RED);
    trackerBoxPaint.setStyle(Style.STROKE);
    trackerBoxPaint.setStrokeWidth(12.0f);
    trackerBoxPaint.setStrokeCap(Cap.ROUND);
    trackerBoxPaint.setStrokeJoin(Join.ROUND);
    trackerBoxPaint.setStrokeMiter(100);
  }
  private static final Paint boxPaint = new Paint(); // Used to draw recognitions without tracker
  static {
    boxPaint.setColor(Color.RED);
    boxPaint.setStyle(Style.STROKE);
    boxPaint.setStrokeWidth(12.0f);
  }
  private static final BorderedText borderedText = new BorderedText(60);

  private final ClippingMarginsHelper clippingMarginsHelper;
  private final ZoomHelper zoomHelper;
  private final List<LabeledObject> labeledObjects;
  private final boolean usingObjectTracker;

  CanvasAnnotatorImpl(ClippingMarginsHelper clippingMarginsHelper, ZoomHelper zoomHelper, List<LabeledObject> labeledObjects, boolean usingObjectTracker) {
    this.clippingMarginsHelper = clippingMarginsHelper;
    this.zoomHelper = zoomHelper;
    this.labeledObjects = labeledObjects;
    this.usingObjectTracker = usingObjectTracker;
  }

  @Override
  public void draw(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity) {
    // TODO(Liz Looney, Michael Hoogasian): considering scaling the text size based on the screen
    // density (scaleCanvasDensity).

    clippingMarginsHelper.fillClippingMargins(canvas, onscreenWidth, onscreenHeight, scaleBmpPxToCanvasPx);
    zoomHelper.blurAroundZoomArea(canvas, onscreenWidth, onscreenHeight, scaleBmpPxToCanvasPx);

    if (usingObjectTracker) {
      for (LabeledObject labeledObject : labeledObjects) {
        labeledObject.checkCoordinateSystem(LabeledObject.CoordinateSystem.CAMERA);
        RectF location = labeledObject.getScaledLocation(scaleBmpPxToCanvasPx);
        trackerBoxPaint.setColor(labeledObject.color);
        float cornerSize = Math.min(location.width(), location.height()) / 8.0f;
        canvas.drawRoundRect(location, cornerSize, cornerSize, trackerBoxPaint);
        borderedText.drawText(canvas, location.left + cornerSize, location.bottom, labeledObject.getText());
      }
    } else {
      for (LabeledObject labeledObject : labeledObjects) {
        labeledObject.checkCoordinateSystem(LabeledObject.CoordinateSystem.CAMERA);
        RectF location = labeledObject.getScaledLocation(scaleBmpPxToCanvasPx);
        boxPaint.setColor(labeledObject.color);
        canvas.drawRect(location, boxPaint);
        borderedText.drawText(canvas, location.left, location.bottom, labeledObject.getText());
      }
    }
  }
}

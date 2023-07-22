/*
 * Copyright 2021 Google LLC
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

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Log;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.tfod.CameraInformation;
import org.firstinspires.ftc.robotcore.external.tfod.TfodParameters;
import org.firstinspires.ftc.robotcore.internal.tfod.LabeledObject.CoordinateSystem;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.vision.detector.Detection;
import org.tensorflow.lite.task.vision.detector.ObjectDetector;
import org.tensorflow.lite.task.vision.detector.ObjectDetector.ObjectDetectorOptions;

/**
 * Subclass of TfodFrameManager that uses {@link ObjectDetector} to support TensorFlow 2 object
 * detection models.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class TfodFrameManager2 extends TfodFrameManager {
  TfodFrameManager2(MappedByteBuffer modelData, TfodParameters params,
      CameraInformation cameraInformation, float minResultConfidence,
      ClippingMargins clippingMargins, Zoom zoom, ResultsCallback resultsCallback) {
    super(modelData, params, cameraInformation, minResultConfidence, clippingMargins, zoom, resultsCallback);
  }

  @Override
  protected RecognizerPipeline createRecognizerPipeline(MappedByteBuffer modelData, ZoomHelper zoomHelper, Bitmap zoomBitmap) {
    return new RecognizerPipeline2(modelData, zoomHelper, zoomBitmap);
  }

  private class RecognizerPipeline2 extends RecognizerPipeline {
    private final ObjectDetector objectDetector;

    private RecognizerPipeline2(MappedByteBuffer modelData, ZoomHelper zoomHelper, Bitmap zoomBitmap) {
      super(zoomHelper, zoomBitmap);

      // Create the ObjectDetector.
      ObjectDetectorOptions options = ObjectDetectorOptions.builder()
          .setMaxResults(params.maxNumRecognitions)
          .setNumThreads(params.numDetectorThreads)
          .build();
      objectDetector = ObjectDetector.createFromBufferAndOptions(modelData, options);
    }

    @Override
    protected void processFrame(long frameTimeNanos) {
      TensorImage tensorImage = createTensorImage();
      List<Detection> detections = objectDetector.detect(tensorImage);
      List<LabeledObject> labeledObjects = postProcessDetections(detections);
      onResultsFromRecognizerPipeline(frameTimeNanos, labeledObjects, zoomBitmap);
    }

    private TensorImage createTensorImage() {
      updateBitmapForTfod();
      return TensorImage.fromBitmap(bitmapForTfod);
    }

    private List<LabeledObject> postProcessDetections(List<Detection> detections) {
      List<LabeledObject> labeledObjects = new ArrayList<>();
      for (Detection detection : detections) {
        for (Category category : detection.getCategories()) {
          float detectionScore = category.getScore();
          if (detectionScore < minResultConfidence) {
            continue;
          }

          int detectedClass = category.getIndex();
          if (detectedClass < 0 || detectedClass >= params.modelLabels.size()) {
            Log.w("RecognizerPipeline2.postProcessDetections",
                "got a detection with an invalid class: " + detectedClass);
            continue;
          }

          // Convert the box to zoom area coordinates.
          RectF detectionBox = new RectF();
          tfodToZoomAreaMatrix.mapRect(detectionBox, detection.getBoundingBox());

          LabeledObject labeledObject = new LabeledObject(
              params.modelLabels.get(detectedClass), boxColor, detectionScore,
              zoomHelper, CoordinateSystem.ZOOM_AREA,
              detectionBox.left, detectionBox.top, detectionBox.right, detectionBox.bottom);
          labeledObjects.add(labeledObject);
        }
      }
      return labeledObjects;
    }
  }
}

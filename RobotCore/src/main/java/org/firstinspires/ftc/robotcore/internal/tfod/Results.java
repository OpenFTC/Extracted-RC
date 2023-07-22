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

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.tfod.CameraInformation;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/**
 * Keep frame times and recognitions together in a single class.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public class Results {

  private final long frameTimeNanos;
  private final List<Recognition> recognitions;

  public Results(long frameTimeNanos) {
    this.frameTimeNanos = frameTimeNanos;
    recognitions = new ArrayList<>();
  }

  public Results(CameraInformation cameraInformation, long frameTimeNanos, @NonNull List<LabeledObject> labeledObjects) {
    this.frameTimeNanos = frameTimeNanos;

    recognitions = new ArrayList<>();
    for (LabeledObject labeledObject : labeledObjects) {
      recognitions.add(new RecognitionImpl(cameraInformation, labeledObject.label,
          labeledObject.confidence, labeledObject.getLocation()));
    }

    // The recognitions need to be in sorted order, decreasing by confidence.
    Collections.sort(recognitions, new Comparator<Recognition>() {
      @Override
      public int compare(Recognition a, Recognition b) {
        return Float.compare(b.getConfidence(), a.getConfidence());
      }
    });
  }

  public List<Recognition> getRecognitions() {
    return recognitions;
  }

  public long getFrameTimeNanos() {
    return frameTimeNanos;
  }
}

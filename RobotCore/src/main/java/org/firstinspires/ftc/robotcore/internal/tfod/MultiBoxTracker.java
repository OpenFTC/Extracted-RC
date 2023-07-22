/* Copyright 2016 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package org.firstinspires.ftc.robotcore.internal.tfod;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.ftcresearch.tfod.tracking.ObjectTracker;
import com.google.ftcresearch.tfod.tracking.ObjectTracker.TrackedObject;
import com.google.ftcresearch.tfod.util.Size;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.firstinspires.ftc.robotcore.external.tfod.TfodParameters;
import org.firstinspires.ftc.robotcore.internal.tfod.LabeledObject.CoordinateSystem;

/**
 * A tracker wrapping ObjectTracker that also handles non-max suppression and matching existing
 * objects to new detections.
 */
public class MultiBoxTracker {
  private static final String TAG = "MultiBoxTracker";

  private static final int[] COLORS = {
    Color.BLUE,
    Color.RED,
    Color.GREEN,
    Color.YELLOW,
    Color.CYAN,
    Color.MAGENTA,
    Color.WHITE,
    Color.parseColor("#55FF55"),
    Color.parseColor("#FFA500"),
    Color.parseColor("#FF8888"),
    Color.parseColor("#AAAAFF"),
    Color.parseColor("#FFFFAA"),
    Color.parseColor("#55AAAA"),
    Color.parseColor("#AA33AA"),
    Color.parseColor("#0D0068")
  };

  private final TfodParameters params;
  private final Size trackerSize;

  private final Queue<Integer> availableColors = new LinkedList<Integer>();

  private final ObjectTracker objectTracker;

  private final List<TrackedRecognition> trackedRecognitions = new LinkedList<TrackedRecognition>();

  public MultiBoxTracker(TfodParameters params, Size trackerSize) {
    this.params = params;
    this.trackerSize = trackerSize;

    for (int color : COLORS) {
      availableColors.add(color);
    }

    ObjectTracker.clearInstance();
    objectTracker = ObjectTracker.getInstance(trackerSize.width, trackerSize.height, trackerSize.width /* rowStride */, true);
    if (objectTracker == null) {
      Log.e(TAG, "Object tracking support not found.");
    }
  }

  synchronized List<LabeledObject> getLabeledObjects() {
    List<LabeledObject> labeledObjects = new ArrayList<>();
    for (TrackedRecognition trackedRecognition : trackedRecognitions) {
      RectF location = trackedRecognition.getCurrentPosition();
      labeledObjects.add(new LabeledObject(trackedRecognition.labeledObject,
          CoordinateSystem.TRACKER,
          location.left, location.top, location.right, location.bottom));
    }
    return labeledObjects;
  }

  public synchronized List<LabeledObject> onFrame(long timestamp, byte[] frame) {
    if (objectTracker != null) {
      // Pass the frame to the object tracker.
      objectTracker.nextFrame(frame, null, timestamp, null, true);

      // Clean up any objects not worth tracking any more.
      LinkedList<TrackedRecognition> copyList = new LinkedList<TrackedRecognition>(trackedRecognitions);
      for (TrackedRecognition trackedRecognition : copyList) {
        TrackedObject trackedObject = trackedRecognition.trackedObject;
        float correlation = trackedObject.getCurrentCorrelation();
        if (correlation < params.trackerMinCorrelation) {
          trackedObject.stopTracking();
          trackedRecognitions.remove(trackedRecognition);

          availableColors.add(trackedRecognition.color);
        }
      }
    }

    return getLabeledObjects();
  }

  public synchronized List<LabeledObject> onResultsFromRecognizer(long timestamp,
      List<LabeledObject> labeledObjectsFromRecognizer, byte[] frame) {
    List<LabeledObject> labeledObjects = new LinkedList<>();
    for (LabeledObject labeledObject : labeledObjectsFromRecognizer) {
      if (labeledObject.width() < params.trackerMinSize ||
          labeledObject.height() < params.trackerMinSize) {
        // Degenerate rectangle!
        continue;
      }

      labeledObjects.add(labeledObject);
    }

    if (objectTracker == null) {
      // No tracking support. Just create TrackedRecognitions from the LabeledObjects.
      trackedRecognitions.clear();
      for (LabeledObject labeledObject : labeledObjects) {
        int color = COLORS[trackedRecognitions.size()];
        TrackedRecognition trackedRecognition = new TrackedRecognition(labeledObject, color, null);
        trackedRecognitions.add(trackedRecognition);

        if (trackedRecognitions.size() >= COLORS.length) {
          break;
        }
      }
    } else {
      for (LabeledObject labeledObject : labeledObjects) {
        handleDetection(frame, timestamp, labeledObject);
      }
    }

    return getLabeledObjects();
  }

  private void handleDetection(byte[] frame, long timestamp, LabeledObject labeledObject) {
    labeledObject.checkCoordinateSystem(CoordinateSystem.TRACKER);
    TrackedObject potentialTrackedObject =
        objectTracker.trackObject(labeledObject.getLocation(), timestamp, frame);

    if (potentialTrackedObject.getCurrentCorrelation() < params.trackerMarginalCorrelation) {
      potentialTrackedObject.stopTracking();
      return;
    }

    List<TrackedRecognition> removeList = new LinkedList<TrackedRecognition>();

    float maxIntersect = 0.0f;

    // This is the current tracked object whose color we will take. If left null we'll take the
    // first one from the color queue.
    TrackedRecognition recogToReplace = null;

    // Look for intersections that will be overridden by this object or an intersection that would
    // prevent this one from being placed.
    for (TrackedRecognition trackedRecognition : trackedRecognitions) {
      RectF a = trackedRecognition.getCurrentPosition();
      RectF b = getTrackedPosition(potentialTrackedObject);
      RectF intersection = new RectF();
      boolean intersects = intersection.setIntersect(a, b);

      float intersectArea = intersection.width() * intersection.height();
      float totalArea = a.width() * a.height() + b.width() * b.height() - intersectArea;
      float intersectOverUnion = intersectArea / totalArea;

      // If there is an intersection with this currently tracked box above the maximum overlap
      // percentage allowed, either the new recognition needs to be dismissed or the old
      // recognition needs to be removed and possibly replaced with the new one.
      if (intersects && intersectOverUnion > params.trackerMaxOverlap) {
        if (labeledObject.confidence < trackedRecognition.labeledObject.confidence &&
            trackedRecognition.trackedObject.getCurrentCorrelation() > params.trackerMarginalCorrelation) {
          // If track for the existing object is still going strong and the detection score was
          // good, reject this new object.
          potentialTrackedObject.stopTracking();
          return;
        } else {
          removeList.add(trackedRecognition);

          // Let the previously tracked object with max intersection amount donate its color to
          // the new object.
          if (intersectOverUnion > maxIntersect) {
            maxIntersect = intersectOverUnion;
            recogToReplace = trackedRecognition;
          }
        }
      }
    }

    // If we're already tracking the max object and no intersections were found to bump off,
    // pick the worst current tracked object to remove, if it's also worse than this labeled
    // object.
    if (availableColors.isEmpty() && removeList.isEmpty()) {
      for (TrackedRecognition candidate : trackedRecognitions) {
        if (candidate.labeledObject.confidence < labeledObject.confidence) {
          if (recogToReplace == null ||
              candidate.labeledObject.confidence < recogToReplace.labeledObject.confidence) {
            // Save it so that we use this color for the new object.
            recogToReplace = candidate;
          }
        }
      }
      if (recogToReplace != null) {
        removeList.add(recogToReplace);
      }
    }

    // Remove everything that got intersected.
    for (TrackedRecognition trackedRecognition : removeList) {
      trackedRecognition.trackedObject.stopTracking();
      trackedRecognitions.remove(trackedRecognition);
      if (trackedRecognition != recogToReplace) {
        availableColors.add(trackedRecognition.color);
      }
    }

    if (recogToReplace == null && availableColors.isEmpty()) {
      // No room to track this object, aborting.
      potentialTrackedObject.stopTracking();
      return;
    }

    // We can track this object.
    // Use the color from a replaced object before taking one from the color queue.
    int color = (recogToReplace != null)
        ? recogToReplace.color
        : availableColors.poll();
    TrackedRecognition trackedRecognition = new TrackedRecognition(labeledObject, color, potentialTrackedObject);
    trackedRecognitions.add(trackedRecognition);
  }

  private static RectF getTrackedPosition(TrackedObject trackedObject) {
    return new RectF(trackedObject.getTrackedPositionInPreviewFrame());
  }

  private static class TrackedRecognition {
    private final @NonNull LabeledObject labeledObject;
    private final int color;
    private final @Nullable TrackedObject trackedObject;

    private TrackedRecognition(@NonNull LabeledObject labeledObject, int color,
        @Nullable TrackedObject trackedObject) {
      this.labeledObject = new LabeledObject(labeledObject, color);
      this.color = color;
      this.trackedObject = trackedObject;
    }

    RectF getCurrentPosition() {
      if (trackedObject != null) {
        return getTrackedPosition(trackedObject);
      }
      return labeledObject.getLocation();
    }
  }
}

/*
 * Copyright 2018 Google LLC
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

import static org.firstinspires.ftc.robotcore.internal.system.AppUtil.TFLITE_MODELS_DIR;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import androidx.annotation.NonNull;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import org.firstinspires.ftc.robotcore.external.tfod.CameraInformation;
import org.firstinspires.ftc.robotcore.external.tfod.FrameGenerator;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.TfodParameters;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class to convert object detection and tracking system into a simple interface.
 *
 * <p>TFObjectDetector makes it easy to detect and track objects in real time. After initialization,
 * clients simply call getRecognitions() as often as they wish to get the recognitions corresponding
 * to the most recent frame which has been processed. Recognitions contain information about the
 * location, class, and detection confidence of each particular object.
 *
 * <p>Advanced users may wish to tune the performance of the TFObjectDetector by changing parameters
 * away from the defaults in {@link TfodParameters}. Not all parameters will make a measurable impact
 * on performance.
 *
 * @author Vasu Agrawal
 * @author lizlooney@google.com (Liz Looney)
 */
public class TFObjectDetectorImpl implements TFObjectDetector, OpModeManagerNotifier.Notifications,
    ResultsCallback {

  private final AppUtil appUtil = AppUtil.getInstance();

  private final ClippingMargins requestedClippingMargins = new ClippingMargins();
  private final ClippingMargins adjustedClippingMargins = new ClippingMargins();
  private final Zoom zoom = new Zoom(1.0);

  private final TfodParameters params;
  private final FrameGenerator frameGenerator;

  private volatile MappedByteBuffer modelData;
  private volatile CameraInformation cameraInformation;
  private volatile float minResultConfidence = 0.75f;

  private final Object frameManagerLock = new Object();
  private TfodFrameManager frameManager;

  private final Object resultsLock = new Object();
  private Results results; // Do not access directly, use getResults or getUpdatedResults.
  private long lastReturnedFrameTime = 0;

  private OpModeManagerImpl opModeManager;
  private final AtomicBoolean shutdownDone = new AtomicBoolean();


  public TFObjectDetectorImpl(TfodParameters params, FrameGenerator frameGenerator) {

    this.params = params;

    Activity activity = appUtil.getRootActivity();
    opModeManager = OpModeManagerImpl.getOpModeManagerOfActivity(activity);
    if (opModeManager != null) {
      opModeManager.registerListener(this);
    }

    this.frameGenerator = frameGenerator;

    // Initialize the results to something non-null.
    synchronized (resultsLock) {
      results = new Results(System.nanoTime());
    }

    loadModel();
  }

  private void loadModel() {
    if (params.modelAssetName != null) {
      try {
        AssetManager assetManager = AppUtil.getDefContext().getAssets();
        AssetFileDescriptor afd = assetManager.openFd(params.modelAssetName);
        try (FileInputStream fis = afd.createInputStream()) {
          loadModelData(fis, afd.getStartOffset(), afd.getDeclaredLength());
        }
      } catch (IOException e) {
        throw new RuntimeException("Loading model from asset failed", e);
      }
    } else {
      try {
        // Try relative file first.
        File file = new File(TFLITE_MODELS_DIR, params.modelFileName);
        if (!file.exists()) {
          file = new File(params.modelFileName);
        }

        try (FileInputStream fis = new FileInputStream(file)) {
          loadModelData(fis, 0, file.length());
        }
      } catch (IOException e) {
        throw new RuntimeException("Loading model from file failed", e);
      }
    }
  }

  private void loadModelData(FileInputStream fileInputStream, long startOffset, long declaredLength) throws IOException {
    // Load the model.
    modelData = fileInputStream.getChannel()
        .map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
  }

  private TfodFrameManager newTfodFrameManager() {
    MappedByteBuffer modelData = this.modelData;
    this.modelData = null;

    // Get the camera information.
    cameraInformation = frameGenerator.getCameraInformation();
    adjustClippingMargins(cameraInformation.rotation);

    ResultsCallback resultsCallback = this;

    return params.isModelTensorFlow2
        ? new TfodFrameManager2(modelData, params, cameraInformation, minResultConfidence, adjustedClippingMargins, zoom, resultsCallback)
        : new TfodFrameManager1(modelData, params, cameraInformation, minResultConfidence, adjustedClippingMargins, zoom, resultsCallback);
  }

  // ResultsCallback

  @Override
  public void onResults(Results results) {
    synchronized (resultsLock) {
      this.results = results;
    }
  }

  /**
   * Activates this TFObjectDetector so it starts recognizing objects.
   */
  @Override
  public void activate() {
    synchronized (frameManagerLock) {
      if (frameManager == null) {
        // Create a TfodFrameManager, which handles feeding tasks to the executor. Each task
        // consists of processing a single camera frame, passing it through the model, and
        // returning a list of recognitions.
        frameManager = newTfodFrameManager();
        // Attach our frame consumer from the frame generator.
        frameGenerator.setFrameConsumer(frameManager.getFrameConsumer());
      }
    }

    synchronized (frameManagerLock) {
      if (frameManager != null) {
        frameManager.activate();
      }
    }
  }

  /**
   * Deactivates this TFObjectDetector so it stops recognizing objects.
   */
  @Override
  public void deactivate() {
    synchronized (frameManagerLock) {
      if (frameManager != null) {
        frameManager.deactivate();
      }
    }
  }

  @Override
  public void setMinResultConfidence(float minResultConfidence) {
    if (Float.isNaN(minResultConfidence)) {
      throw new IllegalArgumentException("minResultConfidence cannot be NaN");
    }
    this.minResultConfidence = minResultConfidence;

    synchronized (frameManagerLock) {
      if (frameManager != null) {
        frameManager.setMinResultConfidence(minResultConfidence);
      }
    }
  }

  @Override
  public void setClippingMargins(int left, int top, int right, int bottom) {
    synchronized (requestedClippingMargins) {
      requestedClippingMargins.left = left;
      requestedClippingMargins.top = top;
      requestedClippingMargins.right = right;
      requestedClippingMargins.bottom = bottom;
    }
    if (cameraInformation != null) {
      adjustClippingMargins(cameraInformation.rotation);
    }
  }

  private void adjustClippingMargins(int rotation) {
    int left, top, right, bottom;
    synchronized (requestedClippingMargins) {
      left = requestedClippingMargins.left;
      top = requestedClippingMargins.top;
      right = requestedClippingMargins.right;
      bottom = requestedClippingMargins.bottom;
    }
    synchronized (adjustedClippingMargins) {
      switch (rotation) {
        default:
          throw new IllegalStateException("rotation must be 0, 90, 180, or 270.");
        case 0:
          adjustedClippingMargins.left = left;
          adjustedClippingMargins.top = top;
          adjustedClippingMargins.right = right;
          adjustedClippingMargins.bottom = bottom;
          break;
        case 90:
          adjustedClippingMargins.left = bottom;
          adjustedClippingMargins.top = left;
          adjustedClippingMargins.right = top;
          adjustedClippingMargins.bottom = right;
          break;
        case 180:
          adjustedClippingMargins.left = right;
          adjustedClippingMargins.top = bottom;
          adjustedClippingMargins.right = left;
          adjustedClippingMargins.bottom = top;
          break;
        case 270:
          adjustedClippingMargins.left = top;
          adjustedClippingMargins.top = right;
          adjustedClippingMargins.right = bottom;
          adjustedClippingMargins.bottom = left;
          break;
      }
    }
  }

  @Override
  public void setZoom(double magnification) {
    zoom.setMagnification(magnification);
  }

  private @NonNull Results getResults() {
    synchronized (resultsLock) {
      return results;
    }
  }

  /**
   * Return a new Results object or null if a new one isn't available.
   *
   * If a new Results object has arrived since the last time this method was called, it will be
   * returned. Otherwise, null will be returned.
   *
   * Note that this method still takes a lock internally, and thus calling this method too
   * frequently may degrade performance of the detector.
   *
   * @return A new frame if one is available, null otherwise.
   */
  private Results getUpdatedResults() {
    synchronized (resultsLock) {
      // We can do this safely because we know the results can never be null after the
      // constructor has happened.
      if (results.getFrameTimeNanos() > lastReturnedFrameTime) {
        lastReturnedFrameTime = results.getFrameTimeNanos();
        return results;
      }
    }

    return null;
  }

  private static List<Recognition> makeRecognitionsList(@NonNull Results results) {
    return new ArrayList<Recognition>(results.getRecognitions());
  }

  @Override
  public List<Recognition> getUpdatedRecognitions() {
    Results updatedResults = getUpdatedResults();
    if (updatedResults == null) {
      return null;
    }
    return makeRecognitionsList(updatedResults);
  }

  @Override
  public List<Recognition> getRecognitions() {
    return makeRecognitionsList(getResults());
  }

  /**
   * Perform whatever cleanup is necessary to release all acquired resources.
   */
  @Override
  public void shutdown() {
    if (shutdownDone.getAndSet(true)) {
      return;
    }

    // Detach our frame consumer from the frame generator.
    frameGenerator.setFrameConsumer(null);

    synchronized (frameManagerLock) {
      if (frameManager != null) {
        frameManager.deactivate();
        frameManager.shutdown();
      }
    }
  }

  //  OpModeManagerNotifier.Notifications
  @Override
  public void onOpModePreInit(OpMode opMode) {
  }

  @Override
  public void onOpModePreStart(OpMode opMode) {
  }

  @Override
  public void onOpModePostStop(OpMode opMode) {
    shutdown();

    if (opModeManager != null) {
      opModeManager.unregisterListener(this);
      opModeManager = null;
    }
  }
}

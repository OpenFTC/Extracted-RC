/**
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

package com.google.blocks.ftcrobotcontroller.runtime;

import static com.google.blocks.ftcrobotcontroller.util.CurrentGame.TFOD_CURRENT_GAME_NAME_NO_SPACES;

import android.webkit.JavascriptInterface;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaBase;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.Tfod;
import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;

/**
 * A class that provides JavaScript access to TensorFlow Object Detection.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
final class TfodAccess extends Access {
  private final Tfod tfod;

  TfodAccess(BlocksOpMode blocksOpMode, String identifier, HardwareMap hardwareMap) {
    super(blocksOpMode, identifier, "TensorFlowObjectDetection");
    this.tfod = new Tfod();
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "useDefaultModel")
  public void useDefaultModel() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".useDefaultModelFor" + TFOD_CURRENT_GAME_NAME_NO_SPACES);
      tfod.useDefaultModel();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "useModelFromAsset")
  public void useModelFromAsset(String assetName, String jsonLabels,
      boolean isModelTensorFlow2, boolean isModelQuantized, int inputSize) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".useModelFromAsset");
      String[] labels = SimpleGson.getInstance().fromJson(jsonLabels, String[].class);
      try {
        tfod.useModelFromAsset(assetName, labels, isModelTensorFlow2, isModelQuantized, inputSize);
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "useModelFromFile")
  public void useModelFromFile(String fileName, String jsonLabels,
      boolean isModelTensorFlow2, boolean isModelQuantized, int inputSize) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".useModelFromFile");
      String[] labels = SimpleGson.getInstance().fromJson(jsonLabels, String[].class);
      try {
        tfod.useModelFromFile(fileName, labels, isModelTensorFlow2, isModelQuantized, inputSize);
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "initialize")
  public void initialize(VuforiaBaseAccess vuforiaBaseAccess,
      float minimumConfidence, boolean useObjectTracker, boolean enableCameraMonitoring) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".initialize");
      VuforiaBase vuforiaBase = vuforiaBaseAccess.getVuforiaBase();
      if (vuforiaBase != null) {
        try {
          tfod.initialize(vuforiaBase, minimumConfidence, useObjectTracker, enableCameraMonitoring);
        } catch (IllegalStateException e) {
          reportWarning(e.getMessage());
        }
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "initialize")
  public void initializeWithMoreArgs(VuforiaBaseAccess vuforiaBaseAccess,
      float minimumConfidence, boolean useObjectTracker, boolean enableCameraMonitoring,
      int numInterpreterThreads, int numExecutorThreads,
      int maxNumDetections, int timingBufferSize, double maxFrameRate,
      float trackerMaxOverlap, float trackerMinSize,
      float trackerMarginalCorrelation, float trackerMinCorrelation) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".initialize");
      VuforiaBase vuforiaBase = vuforiaBaseAccess.getVuforiaBase();
      if (vuforiaBase != null) {
        try {
          tfod.initialize(vuforiaBase, minimumConfidence, useObjectTracker,
              enableCameraMonitoring, numInterpreterThreads, numExecutorThreads, maxNumDetections,
              timingBufferSize, maxFrameRate, trackerMaxOverlap, trackerMinSize,
              trackerMarginalCorrelation, trackerMinCorrelation);
        } catch (IllegalStateException e) {
          reportWarning(e.getMessage());
        }
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "activate")
  public void activate() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".activate");
      try {
        tfod.activate();
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "deactivate")
  public void deactivate() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".deactivate");
      try {
        tfod.deactivate();
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "setClippingMargins")
  public void setClippingMargins(int left, int top, int right, int bottom) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setClippingMargins");
      try {
        tfod.setClippingMargins(left, top, right, bottom);
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "setZoom")
  public void setZoom(double magnification, double aspectRatio) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setZoom");
      try {
        tfod.setZoom(magnification, aspectRatio);
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "getRecognitions")
  public String getRecognitions() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getRecognitions");
      try {
        return toJson(tfod.getRecognitions());
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      }
      return "[]";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  private static String toJson(List<Recognition> recognitions) {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    String delimiter = "";
    for (Recognition recognition : recognitions) {
      sb.append(delimiter).append(toJson(recognition));
      delimiter = ",";
    }
    sb.append("]");
    return sb.toString();
  }

  private static String toJson(Recognition recognition) {
    return "{ \"Label\":\"" + recognition.getLabel() + "\"" +
        ", \"Confidence\":" + recognition.getConfidence() +
        ", \"Left\":" + recognition.getLeft() +
        ", \"Right\":" + recognition.getRight() +
        ", \"Top\":" + recognition.getTop() +
        ", \"Bottom\":" + recognition.getBottom() +
        ", \"Width\":" + recognition.getWidth() +
        ", \"Height\":" + recognition.getHeight() +
        ", \"ImageWidth\":" + recognition.getImageWidth() +
        ", \"ImageHeight\":" + recognition.getImageHeight() +
        ", \"estimateAngleToObject\":" + recognition.estimateAngleToObject(AngleUnit.RADIANS) +
        " }";
  }

  // legacy blocks

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "useModelFromAsset")
  public void setModelFromAssetLegacy(String assetName, String jsonLabels) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".useModelFromAsset");
      String[] labels = SimpleGson.getInstance().fromJson(jsonLabels, String[].class);
      try {
        tfod.useModelFromAsset(assetName, labels);
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "useModelFromFile")
  public void setModelFromFileLegacy(String fileName, String jsonLabels) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".useModelFromFile");
      String[] labels = SimpleGson.getInstance().fromJson(jsonLabels, String[].class);
      try {
        tfod.useModelFromFile(fileName, labels);
      } catch (IllegalStateException e) {
        reportWarning(e.getMessage());
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "initialize")
  public void initializeWithIsModelTensorFlow2Legacy(
      VuforiaBaseAccess vuforiaBaseAccess,
      float minimumConfidence, boolean useObjectTracker, boolean enableCameraMonitoring,
      boolean isModelTensorFlow2) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".initialize");
      VuforiaBase vuforiaBase = vuforiaBaseAccess.getVuforiaBase();
      if (vuforiaBase != null) {
        try {
          tfod.initialize(vuforiaBase,
              minimumConfidence, useObjectTracker, enableCameraMonitoring,
              isModelTensorFlow2);
        } catch (IllegalStateException e) {
          reportWarning(e.getMessage());
        }
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Tfod.class, methodName = "initialize")
  public void initializeWithAllArgsLegacy(VuforiaBaseAccess vuforiaBaseAccess,
      float minimumConfidence, boolean useObjectTracker, boolean enableCameraMonitoring,
      boolean isModelTensorFlow2, boolean isModelQuantized, int inputSize,
      int numInterpreterThreads, int numExecutorThreads,
      int maxNumDetections, int timingBufferSize, double maxFrameRate,
      float trackerMaxOverlap, float trackerMinSize,
      float trackerMarginalCorrelation, float trackerMinCorrelation) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".initialize");
      VuforiaBase vuforiaBase = vuforiaBaseAccess.getVuforiaBase();
      if (vuforiaBase != null) {
        try {
          tfod.initialize(vuforiaBase,
              minimumConfidence, useObjectTracker, enableCameraMonitoring,
              isModelTensorFlow2, isModelQuantized, inputSize,
              numInterpreterThreads, numExecutorThreads, maxNumDetections,
              timingBufferSize, maxFrameRate, trackerMaxOverlap, trackerMinSize,
              trackerMarginalCorrelation, trackerMinCorrelation);
        } catch (IllegalStateException e) {
          reportWarning(e.getMessage());
        }
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

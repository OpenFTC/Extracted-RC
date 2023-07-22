/*
 * Copyright (c) 2023 FIRST
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.google.blocks.ftcrobotcontroller.runtime;

import android.webkit.JavascriptInterface;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

/**
 * A class that provides JavaScript access to {@link TfodProcessor}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class TensorFlowAccess extends Access {

  TensorFlowAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, ""); // blocks have various first names.
  }

  private TfodProcessor.Builder checkTfodProcessorBuilder(Object tfodProcessorBuilderArg) {
    return checkArg(tfodProcessorBuilderArg, TfodProcessor.Builder.class, "tfodProcessorBuilder");
  }

  private TfodProcessor checkTfodProcessor(Object tfodProcessorArg) {
    return checkArg(tfodProcessorArg, TfodProcessor.class, "tfodProcessor");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = TfodProcessor.Builder.class, constructor = true)
  public TfodProcessor.Builder createBuilder() {
    try {
      startBlockExecution(BlockType.CREATE, "TfodProcessor.Builder", "");
      return new TfodProcessor.Builder();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = TfodProcessor.Builder.class, methodName = "setModelAssetName")
  public void setModelAssetName(Object tfodProcessorBuilderArg, String modelAssetName) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setModelAssetName");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setModelAssetName(modelAssetName);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setModelFileName")
  public void setModelFileName(Object tfodProcessorBuilderArg, String modelFileName) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setModelFileName");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setModelFileName(modelFileName);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setModelLabels")
  public void setModelLabels(Object tfodProcessorBuilderArg, String jsonLabels) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setModelLabels");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      String[] labels = SimpleGson.getInstance().fromJson(jsonLabels, String[].class);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setModelLabels(labels);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setIsModelTensorFlow2")
  public void setIsModelTensorFlow2(Object tfodProcessorBuilderArg, boolean isModelTensorFlow2) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setIsModelTensorFlow2");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setIsModelTensorFlow2(isModelTensorFlow2);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setIsModelQuantized")
  public void setIsModelQuantized(Object tfodProcessorBuilderArg, boolean isModelQuantized) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setIsModelQuantized");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setIsModelQuantized(isModelQuantized);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setModelInputSize")
  public void setModelInputSize(Object tfodProcessorBuilderArg, int modelInputSize) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setModelInputSize");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setModelInputSize(modelInputSize);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setModelAspectRatio")
  public void setModelAspectRatio(Object tfodProcessorBuilderArg, double modelAspectRatio) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setModelAspectRatio");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setModelAspectRatio(modelAspectRatio);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setNumDetectorThreads")
  public void setNumDetectorThreads(Object tfodProcessorBuilderArg, int numDetectorThreads) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setNumDetectorThreads");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setNumDetectorThreads(numDetectorThreads);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setNumExecutorThreads")
  public void setNumExecutorThreads(Object tfodProcessorBuilderArg, int numExecutorThreads) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setNumExecutorThreads");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setNumExecutorThreads(numExecutorThreads);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setMaxNumRecognitions")
  public void setMaxNumRecognitions(Object tfodProcessorBuilderArg, int maxNumRecognitions) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setMaxNumRecognitions");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setMaxNumRecognitions(maxNumRecognitions);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setUseObjectTracker")
  public void setUseObjectTracker(Object tfodProcessorBuilderArg, boolean useObjectTracker) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setUseObjectTracker");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setUseObjectTracker(useObjectTracker);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setTrackerMaxOverlap")
  public void setTrackerMaxOverlap(Object tfodProcessorBuilderArg, float trackerMaxOverlap) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setTrackerMaxOverlap");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setTrackerMaxOverlap(trackerMaxOverlap);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setTrackerMinSize")
  public void setTrackerMinSize(Object tfodProcessorBuilderArg, float trackerMinSize) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setTrackerMinSize");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setTrackerMinSize(trackerMinSize);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setTrackerMarginalCorrelation")
  public void setTrackerMarginalCorrelation(Object tfodProcessorBuilderArg, float trackerMarginalCorrelation) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setTrackerMarginalCorrelation");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setTrackerMarginalCorrelation(trackerMarginalCorrelation);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "setTrackerMinCorrelation")
  public void setTrackerMinCorrelation(Object tfodProcessorBuilderArg, float trackerMinCorrelation) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".setTrackerMinCorrelation");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        tfodProcessorBuilder.setTrackerMinCorrelation(trackerMinCorrelation);
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
  @Block(classes = TfodProcessor.Builder.class, methodName = "build")
  public TfodProcessor build(Object tfodProcessorBuilderArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor.Builder", ".build");
      TfodProcessor.Builder tfodProcessorBuilder = checkTfodProcessorBuilder(tfodProcessorBuilderArg);
      if (tfodProcessorBuilder != null) {
        return tfodProcessorBuilder.build();
      }
      return null;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = TfodProcessor.class, methodName = "easyCreateWithDefaults")
  public TfodProcessor easyCreateWithDefaults() {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor", ".easyCreateWithDefaults");
      return TfodProcessor.easyCreateWithDefaults();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = TfodProcessor.class, methodName = "setMinResultConfidence")
  public void setMinResultConfidence(Object tfodProcessorArg, float minResultConfidence) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor", ".setMinResultConfidence");
      TfodProcessor tfodProcessor = checkTfodProcessor(tfodProcessorArg);
      if (tfodProcessor != null) {
        tfodProcessor.setMinResultConfidence(minResultConfidence);
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
  @Block(classes = TfodProcessor.class, methodName = "setClippingMargins")
  public void setClippingMargins(Object tfodProcessorArg, int left, int top, int right, int bottom) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor", ".setClippingMargins");
      TfodProcessor tfodProcessor = checkTfodProcessor(tfodProcessorArg);
      if (tfodProcessor != null) {
        tfodProcessor.setClippingMargins(left, top, right, bottom);
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
  @Block(classes = TfodProcessor.class, methodName = "setZoom")
  public void setZoom(Object tfodProcessorArg, double magnification) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor", ".setZoom");
      TfodProcessor tfodProcessor = checkTfodProcessor(tfodProcessorArg);
      if (tfodProcessor != null) {
        tfodProcessor.setZoom(magnification);
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
  @Block(classes = TfodProcessor.class, methodName = "getRecognitions")
  public String getRecognitions(Object tfodProcessorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor", ".getRecognitions");
      TfodProcessor tfodProcessor = checkTfodProcessor(tfodProcessorArg);
      if (tfodProcessor != null) {
        return toJson(tfodProcessor.getRecognitions());
      }
      return "[]";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = TfodProcessor.class, methodName = "getFreshRecognitions")
  public String getFreshRecognitions(Object tfodProcessorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "TfodProcessor", ".getFreshRecognitions");
      TfodProcessor tfodProcessor = checkTfodProcessor(tfodProcessorArg);
      if (tfodProcessor != null) {
        return toJson(tfodProcessor.getFreshRecognitions());
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
    if (recognitions == null) {
      return "null";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    String delimiter = "";
    for (Recognition recognition : recognitions) {
      sb.append(delimiter).append(toJson(recognition));
      delimiter = ", ";
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
}

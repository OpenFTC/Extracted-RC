/*
 * Copyright 2024 Google LLC
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

import android.webkit.JavascriptInterface;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor.Builder;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor.Result;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor.Swatch;

/**
 * A class that provides JavaScript access to {@link PredominantColorProcessor}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class PredominantColorAccess extends Access {

  PredominantColorAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, ""); // blocks have various first names.
  }

  private Builder checkPredominantColorProcessorBuilder(Object predominantColorProcessorBuilderArg) {
    return checkArg(predominantColorProcessorBuilderArg, Builder.class, "predominantColorProcessorBuilder");
  }

  private PredominantColorProcessor checkPredominantColorProcessor(Object predominantColorProcessorArg) {
    return checkArg(predominantColorProcessorArg, PredominantColorProcessor.class, "predominantColorProcessor");
  }

  private Swatch checkSwatch(String swatchString) {
    return checkArg(swatchString, Swatch.class, "swatch");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Builder.class, constructor = true)
  public Builder createPredominantColorProcessorBuilder() {
    try {
      startBlockExecution(BlockType.CREATE, "PredominantColorProcessor.Builder", "");
      return new Builder();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Builder.class, methodName = "setRoi")
  public void setRoi(Object predominantColorProcessorBuilderArg, Object imageRegionArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "PredominantColorProcessor.Builder", ".setRoi");
      Builder predominantColorProcessorBuilder = checkPredominantColorProcessorBuilder(predominantColorProcessorBuilderArg);
      ImageRegion imageRegion = checkImageRegion(imageRegionArg);
      if (predominantColorProcessorBuilder != null && imageRegion != null) {
        predominantColorProcessorBuilder.setRoi(imageRegion);
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
  @Block(classes = Builder.class, methodName = "setSwatches")
  public void setSwatches(Object predominantColorProcessorBuilderArg, String json) {
    try {
      startBlockExecution(BlockType.FUNCTION, "PredominantColorProcessor.Builder", ".setSwatches");
      Builder predominantColorProcessorBuilder = checkPredominantColorProcessorBuilder(predominantColorProcessorBuilderArg);
      if (predominantColorProcessorBuilder != null) {
        String[] swatchStrings = fromJson(json, String[].class);
        Swatch[] swatches = new Swatch[swatchStrings.length];
        for (int i = 0; i < swatches.length; i++) {
          swatches[i] = checkSwatch(swatchStrings[i]);
        }
        predominantColorProcessorBuilder.setSwatches(swatches);
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
  @Block(classes = Builder.class, methodName = "build")
  public PredominantColorProcessor buildPredominantColorProcessor(Object predominantColorProcessorBuilderArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "PredominantColorProcessor.Builder", ".build");
      Builder predominantColorProcessorBuilder = checkPredominantColorProcessorBuilder(predominantColorProcessorBuilderArg);
      if (predominantColorProcessorBuilder != null) {
        return predominantColorProcessorBuilder.build();
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
  @Block(classes = PredominantColorProcessor.class, methodName = "getAnalysis")
  public String getAnalysis(Object predominantColorProcessorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "PredominantColorProcessor", ".getAnalysis");
      PredominantColorProcessor predominantColorProcessor = checkPredominantColorProcessor(predominantColorProcessorArg);
      if (predominantColorProcessor != null) {
        return toJson(predominantColorProcessor.getAnalysis());
      }
      return null;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

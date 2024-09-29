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

import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ColorSpace;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.opencv.core.Scalar;

/**
 * A class that provides JavaScript access to some classes in the
 * org.firstinspires.ftc.vision.opencv package.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class OpencvAccess extends Access {

  OpencvAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, ""); // blocks have various first names.
  }

  private ColorSpace checkColorSpace(String colorSpaceString) {
    return checkArg(colorSpaceString, ColorSpace.class, "colorSpace");
  }

  private Scalar checkScalar(Object scalarArg, String socketName) {
    return checkArg(scalarArg, Scalar.class, socketName);
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = ColorRange.class, fieldName = {"BLUE", "RED", "YELLOW", "GREEN"})
  public ColorRange colorRange(String color) {
    try {
      startBlockExecution(BlockType.GETTER, "ColorRange", "." + color);
      if (color.equals("BLUE")) {
        return ColorRange.BLUE;
      } else if (color.equals("RED")) {
        return ColorRange.RED;
      } else if (color.equals("YELLOW")) {
        return ColorRange.YELLOW;
      } else if (color.equals("GREEN")) {
        return ColorRange.GREEN;
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
  @Block(classes = ColorRange.class, constructor = true)
  public ColorRange createColorRange(String colorSpaceString, Object minArg, Object maxArg) {
    try {
      startBlockExecution(BlockType.CREATE, "ColorRange", "");
      ColorSpace colorSpace = checkColorSpace(colorSpaceString);
      Scalar min = checkScalar(minArg, "min");
      Scalar max = checkScalar(maxArg, "max");
      if (colorSpace != null && min != null && max != null) {
        return new ColorRange(colorSpace, min, max);
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
  @Block(classes = ImageRegion.class, methodName = "asImageCoordinates")
  public ImageRegion asImageCoordinates(int left, int top, int right, int bottom) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ImageRegion", ".asImageCoordinates");
      return ImageRegion.asImageCoordinates(left, top, right, bottom);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = ImageRegion.class, methodName = "asUnityCenterCoordinates")
  public ImageRegion asUnityCenterCoordinates(double left, double top, double right, double bottom) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ImageRegion", ".asUnityCenterCoordinates");
      return ImageRegion.asUnityCenterCoordinates(left, top, right, bottom);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = ImageRegion.class, methodName = "entireFrame")
  public ImageRegion entireFrame() {
    try {
      startBlockExecution(BlockType.FUNCTION, "ImageRegion", ".entireFrame");
      return ImageRegion.entireFrame();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Scalar.class, constructor = true)
  public Scalar createScalar_with3(double v0, double v1, double v2) {
    try {
      startBlockExecution(BlockType.CREATE, "Scalar", "");
      return new Scalar(v0, v1, v2);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

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
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SortOrder;
import java.util.List;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor.Blob;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor.BlobCriteria;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor.BlobFilter;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor.BlobSort;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor.Builder;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor.ContourMode;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor.Util;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;

/**
 * A class that provides JavaScript access to {@link ColorBlobLocatorProcessor}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class ColorBlobLocatorAccess extends Access {

  ColorBlobLocatorAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, ""); // blocks have various first names.
  }

  private Builder checkColorBlobLocatorProcessorBuilder(Object colorBlobLocatorProcessorBuilderArg) {
    return checkArg(colorBlobLocatorProcessorBuilderArg, Builder.class, "colorBlobLocatorProcessorBuilder");
  }

  private ColorRange checkColorRange(Object colorRangeArg) {
    return checkArg(colorRangeArg, ColorRange.class, "colorRange");
  }

  private ContourMode checkContourMode(String contourModeString) {
    return checkArg(contourModeString, ContourMode.class, "contourMode");
  }

  private ColorBlobLocatorProcessor checkColorBlobLocatorProcessor(Object colorBlobLocatorProcessorArg) {
    return checkArg(colorBlobLocatorProcessorArg, ColorBlobLocatorProcessor.class, "colorBlobLocatorProcessor");
  }

  private BlobCriteria checkBlobCriteria(String criteriaString) {
    return checkArg(criteriaString, BlobCriteria.class, "criteria");
  }

  private BlobFilter checkBlobFilter(Object filterArg) {
    return checkArg(filterArg, BlobFilter.class, "filter");
  }

  private BlobSort checkBlobSort(Object sortArg) {
    return checkArg(sortArg, BlobSort.class, "sort");
  }

  private SortOrder checkSortOrder(String sortOrderString) {
    return checkArg(sortOrderString, SortOrder.class, "sortOrder");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Builder.class, constructor = true)
  public Builder createColorBlobLocatorProcessorBuilder() {
    try {
      startBlockExecution(BlockType.CREATE, "ColorBlobLocatorProcessor.Builder", "");
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
  @Block(classes = Builder.class, methodName = "setDrawContours")
  public void setDrawContours(Object colorBlobLocatorProcessorBuilderArg, boolean drawContours) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor.Builder", ".setDrawContours");
      Builder colorBlobLocatorProcessorBuilder = checkColorBlobLocatorProcessorBuilder(colorBlobLocatorProcessorBuilderArg);
      if (colorBlobLocatorProcessorBuilder != null) {
        colorBlobLocatorProcessorBuilder.setDrawContours(drawContours);
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
  @Block(classes = Builder.class, methodName = "setBoxFitColor")
  public void setBoxFitColor(Object colorBlobLocatorProcessorBuilderArg, int color) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor.Builder", ".setBoxFitColor");
      Builder colorBlobLocatorProcessorBuilder = checkColorBlobLocatorProcessorBuilder(colorBlobLocatorProcessorBuilderArg);
      if (colorBlobLocatorProcessorBuilder != null) {
        colorBlobLocatorProcessorBuilder.setBoxFitColor(color);
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
  @Block(classes = Builder.class, methodName = "setRoiColor")
  public void setRoiColor(Object colorBlobLocatorProcessorBuilderArg, int color) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor.Builder", ".setRoiColor");
      Builder colorBlobLocatorProcessorBuilder = checkColorBlobLocatorProcessorBuilder(colorBlobLocatorProcessorBuilderArg);
      if (colorBlobLocatorProcessorBuilder != null) {
        colorBlobLocatorProcessorBuilder.setRoiColor(color);
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
  @Block(classes = Builder.class, methodName = "setContourColor")
  public void setContourColor(Object colorBlobLocatorProcessorBuilderArg, int color) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor.Builder", ".setContourColor");
      Builder colorBlobLocatorProcessorBuilder = checkColorBlobLocatorProcessorBuilder(colorBlobLocatorProcessorBuilderArg);
      if (colorBlobLocatorProcessorBuilder != null) {
        colorBlobLocatorProcessorBuilder.setContourColor(color);
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
  @Block(classes = Builder.class, methodName = "setTargetColorRange")
  public void setTargetColorRange(Object colorBlobLocatorProcessorBuilderArg, Object colorRangeArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor.Builder", ".setTargetColorRange");
      Builder colorBlobLocatorProcessorBuilder = checkColorBlobLocatorProcessorBuilder(colorBlobLocatorProcessorBuilderArg);
      ColorRange colorRange = checkColorRange(colorRangeArg);
      if (colorBlobLocatorProcessorBuilder != null && colorRange != null) {
        colorBlobLocatorProcessorBuilder.setTargetColorRange(colorRange);
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
  @Block(classes = Builder.class, methodName = "setContourMode")
  public void setContourMode(Object colorBlobLocatorProcessorBuilderArg, String contourModeString) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor.Builder", ".setContourMode");
      Builder colorBlobLocatorProcessorBuilder = checkColorBlobLocatorProcessorBuilder(colorBlobLocatorProcessorBuilderArg);
      ContourMode contourMode = checkContourMode(contourModeString);
      if (colorBlobLocatorProcessorBuilder != null && contourMode != null) {
        colorBlobLocatorProcessorBuilder.setContourMode(contourMode);
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
  @Block(classes = Builder.class, methodName = "setRoi")
  public void setRoi(Object colorBlobLocatorProcessorBuilderArg, Object imageRegionArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor.Builder", ".setRoi");
      Builder colorBlobLocatorProcessorBuilder = checkColorBlobLocatorProcessorBuilder(colorBlobLocatorProcessorBuilderArg);
      ImageRegion imageRegion = checkImageRegion(imageRegionArg);
      if (colorBlobLocatorProcessorBuilder != null && imageRegion != null) {
        colorBlobLocatorProcessorBuilder.setRoi(imageRegion);
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
  @Block(classes = Builder.class, methodName = "setBlurSize")
  public void setBlurSize(Object colorBlobLocatorProcessorBuilderArg, int blurSize) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor.Builder", ".setBlurSize");
      Builder colorBlobLocatorProcessorBuilder = checkColorBlobLocatorProcessorBuilder(colorBlobLocatorProcessorBuilderArg);
      if (colorBlobLocatorProcessorBuilder != null) {
        colorBlobLocatorProcessorBuilder.setBlurSize(blurSize);
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
  @Block(classes = Builder.class, methodName = "setErodeSize")
  public void setErodeSize(Object colorBlobLocatorProcessorBuilderArg, int erodeSize) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor.Builder", ".setErodeSize");
      Builder colorBlobLocatorProcessorBuilder = checkColorBlobLocatorProcessorBuilder(colorBlobLocatorProcessorBuilderArg);
      if (colorBlobLocatorProcessorBuilder != null) {
        colorBlobLocatorProcessorBuilder.setErodeSize(erodeSize);
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
  @Block(classes = Builder.class, methodName = "setDilateSize")
  public void setDilateSize(Object colorBlobLocatorProcessorBuilderArg, int dilateSize) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor.Builder", ".setDilateSize");
      Builder colorBlobLocatorProcessorBuilder = checkColorBlobLocatorProcessorBuilder(colorBlobLocatorProcessorBuilderArg);
      if (colorBlobLocatorProcessorBuilder != null) {
        colorBlobLocatorProcessorBuilder.setDilateSize(dilateSize);
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
  public ColorBlobLocatorProcessor buildColorBlobLocatorProcessor(Object colorBlobLocatorProcessorBuilderArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor.Builder", ".build");
      Builder colorBlobLocatorProcessorBuilder = checkColorBlobLocatorProcessorBuilder(colorBlobLocatorProcessorBuilderArg);
      if (colorBlobLocatorProcessorBuilder != null) {
        return colorBlobLocatorProcessorBuilder.build();
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
  @Block(classes = BlobFilter.class, constructor = true)
  public BlobFilter createColorBlobLocatorProcessorBlobFilter(
      String criteriaString, double minValue, double maxValue) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      BlobCriteria criteria = checkBlobCriteria(criteriaString);
      if (criteria != null) {
        return new BlobFilter(criteria, minValue, maxValue);
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
  @Block(classes = ColorBlobLocatorProcessor.class, methodName = "addFilter")
  public void addFilter(Object colorBlobLocatorProcessorArg, Object filterArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor", ".addFilter");
      ColorBlobLocatorProcessor colorBlobLocatorProcessor = checkColorBlobLocatorProcessor(colorBlobLocatorProcessorArg);
      BlobFilter filter = checkBlobFilter(filterArg);
      if (colorBlobLocatorProcessor != null && filter != null) {
        colorBlobLocatorProcessor.addFilter(filter);
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
  @Block(classes = ColorBlobLocatorProcessor.class, methodName = "removeFilter")
  public void removeFilter(Object colorBlobLocatorProcessorArg, Object filterArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor", ".removeFilter");
      ColorBlobLocatorProcessor colorBlobLocatorProcessor = checkColorBlobLocatorProcessor(colorBlobLocatorProcessorArg);
      BlobFilter filter = checkBlobFilter(filterArg);
      if (colorBlobLocatorProcessor != null && filter != null) {
        colorBlobLocatorProcessor.removeFilter(filter);
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
  @Block(classes = ColorBlobLocatorProcessor.class, methodName = "removeAllFilters")
  public void removeAllFilters(Object colorBlobLocatorProcessorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor", ".removeAllFilters");
      ColorBlobLocatorProcessor colorBlobLocatorProcessor = checkColorBlobLocatorProcessor(colorBlobLocatorProcessorArg);
      if (colorBlobLocatorProcessor != null) {
        colorBlobLocatorProcessor.removeAllFilters();
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
  @Block(classes = BlobSort.class, constructor = true)
  public BlobSort createColorBlobLocatorProcessorBlobSort(
      String criteriaString, String sortOrderString) {
    try {
      startBlockExecution(BlockType.CREATE, "");
      BlobCriteria criteria = checkBlobCriteria(criteriaString);
      SortOrder sortOrder = checkSortOrder(sortOrderString);
      if (criteria != null && sortOrder != null) {
        return new BlobSort(criteria, sortOrder);
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
  @Block(classes = ColorBlobLocatorProcessor.class, methodName = "setSort")
  public void setSort(Object colorBlobLocatorProcessorArg, Object sortArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor", ".setSort");
      ColorBlobLocatorProcessor colorBlobLocatorProcessor = checkColorBlobLocatorProcessor(colorBlobLocatorProcessorArg);
      BlobSort sort = checkBlobSort(sortArg);
      if (colorBlobLocatorProcessor != null && sort != null) {
        colorBlobLocatorProcessor.setSort(sort);
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
  @Block(classes = ColorBlobLocatorProcessor.class, methodName = "getBlobs")
  public String getBlobs(Object colorBlobLocatorProcessorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "ColorBlobLocatorProcessor", ".getBlobs");
      ColorBlobLocatorProcessor colorBlobLocatorProcessor = checkColorBlobLocatorProcessor(colorBlobLocatorProcessorArg);
      if (colorBlobLocatorProcessor != null) {
        return blobsToJson(colorBlobLocatorProcessor.getBlobs());
      }
      return null;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  private static String blobsToJson(List<Blob> blobs) {
    StringBuilder json = new StringBuilder();
    json.append("[");
    String delimiter = "";
    for (Blob blob : blobs) {
      double density = 0;
      try {
        density = blob.getDensity();
      } catch (Exception e) {
      }
      double aspectRatio = 0;
      try {
        aspectRatio = blob.getAspectRatio();
      } catch (Exception e) {
      }
      json.append(delimiter)
          .append("{")
          .append("\"ContourPoints\":").append(toJson(blob.getContourPoints())) // Point[] [{"x":0.0,"y":0.0},,...]
          .append(",")
          .append("\"ContourArea\":").append(blob.getContourArea()) // int
          .append(",")
          .append("\"Density\":").append(fixDouble(density)) // double
          .append(",")
          .append("\"AspectRatio\":").append(fixDouble(aspectRatio)) // double
          .append(",")
          .append("\"BoxFit\":").append(rotatedRectToJson(blob.getBoxFit()))
          .append("}");
      delimiter = ",";
    }
    json.append("]");
    return json.toString();
  }

  private static String rotatedRectToJson(RotatedRect rotatedRect) {
    // RotatedRect {"angle":0,"center":{"x":0.0,"y":0.0},"size":{"width":0.0,"height":0.0}}
    String originalJson = toJson(rotatedRect);
    if (!originalJson.endsWith("}")) {
      RobotLog.ww("ColorBlobLocatorAccess", "Unexpected: result from toJson(RotatedRect) does not end with '}'!");
      return originalJson;
    }

    Point[] points = new Point[4];
    rotatedRect.points(points);

    String json = new StringBuilder()
        .append(originalJson.substring(0, originalJson.length() - 1))
        .append(",")
        .append("\"boundingRect\":").append(toJson(rotatedRect.boundingRect()))
        .append(",")
        .append("\"points\":").append(toJson(points))
        .append("}")
        .toString();
    return json;
  }

  private static double fixDouble(double v) {
    if (Double.isFinite(v)) {
      return v;
    }
    return 0;
  }
}

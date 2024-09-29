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

package com.google.blocks.ftcrobotcontroller.runtime;

import android.webkit.JavascriptInterface;

import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.GeneralMatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagMetadata;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseRaw;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor.PoseSolver;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor.TagFamily;

/**
 * A class that provides JavaScript access to {@link AprilTagProcessor}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class AprilTagAccess extends Access {

  AprilTagAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, ""); // blocks have various first names.
  }

  private AprilTagProcessor.Builder checkAprilTagProcessorBuilder(Object aprilTagProcessorBuilderArg) {
    return checkArg(aprilTagProcessorBuilderArg, AprilTagProcessor.Builder.class, "aprilTagProcessorBuilder");
  }

  private AprilTagProcessor checkAprilTagProcessor(Object aprilTagProcessorArg) {
    return checkArg(aprilTagProcessorArg, AprilTagProcessor.class, "aprilTagProcessor");
  }

  private AprilTagLibrary.Builder checkAprilTagLibraryBuilder(Object aprilTagLibraryBuilderArg) {
    return checkArg(aprilTagLibraryBuilderArg, AprilTagLibrary.Builder.class, "aprilTagLibraryBuilder");
  }

  private AprilTagLibrary checkAprilTagLibrary(Object aprilTagLibraryArg) {
    return checkArg(aprilTagLibraryArg, AprilTagLibrary.class, "aprilTagLibrary");
  }

  private TagFamily checkTagFamily(String tagFamilyString) {
    return checkArg(tagFamilyString, TagFamily.class, "tagFamily");
  }

  private PoseSolver checkPoseSolver(String poseSolverString) {
    return checkArg(poseSolverString, PoseSolver.class, "poseSolver");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AprilTagProcessor.Builder.class, constructor = true)
  public AprilTagProcessor.Builder createAprilTagProcessorBuilder() {
    try {
      startBlockExecution(BlockType.CREATE, "AprilTagProcessor.Builder", "");
      return new AprilTagProcessor.Builder();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AprilTagProcessor.Builder.class, methodName = "setCameraPose")
  public void setCameraPose(Object aprilTagProcessorBuilderArg, Object positionArg, Object yawPitchRollAnglesArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor.Builder", ".setCameraPose");
      AprilTagProcessor.Builder aprilTagProcessorBuilder = checkAprilTagProcessorBuilder(aprilTagProcessorBuilderArg);
      Position position = checkPosition(positionArg);
      YawPitchRollAngles yawPitchRollAngles = checkYawPitchRollAngles(yawPitchRollAnglesArg);
      if (aprilTagProcessorBuilder != null && position != null && yawPitchRollAngles != null) {
        aprilTagProcessorBuilder.setCameraPose(position, yawPitchRollAngles);
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
  @Block(classes = AprilTagProcessor.Builder.class, methodName = "setLensIntrinsics")
  public void setLensIntrinsics(Object aprilTagProcessorBuilderArg, double fx, double fy, double cx, double cy) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor.Builder", ".setLensIntrinsics");
      AprilTagProcessor.Builder aprilTagProcessorBuilder = checkAprilTagProcessorBuilder(aprilTagProcessorBuilderArg);
      if (aprilTagProcessorBuilder != null) {
        aprilTagProcessorBuilder.setLensIntrinsics(fx, fy, cx, cy);
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
  @Block(classes = AprilTagProcessor.Builder.class, methodName = "setSuppressCalibrationWarnings")
  public void setSuppressCalibrationWarnings(Object aprilTagProcessorBuilderArg, boolean suppressCalibrationWarnings) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor.Builder", ".setSuppressCalibrationWarnings");
      AprilTagProcessor.Builder aprilTagProcessorBuilder = checkAprilTagProcessorBuilder(aprilTagProcessorBuilderArg);
      if (aprilTagProcessorBuilder != null) {
        aprilTagProcessorBuilder.setSuppressCalibrationWarnings(suppressCalibrationWarnings);
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
  @Block(classes = AprilTagProcessor.Builder.class, methodName = "setTagFamily")
  public void setTagFamily(Object aprilTagProcessorBuilderArg, String tagFamilyString) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor.Builder", ".setTagFamily");
      AprilTagProcessor.Builder aprilTagProcessorBuilder = checkAprilTagProcessorBuilder(aprilTagProcessorBuilderArg);
      TagFamily tagFamily = checkTagFamily(tagFamilyString);
      if (aprilTagProcessorBuilder != null && tagFamily != null) {
        aprilTagProcessorBuilder.setTagFamily(tagFamily);
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
  @Block(classes = AprilTagProcessor.Builder.class, methodName = "setTagLibrary")
  public void setTagLibrary(Object aprilTagProcessorBuilderArg, Object aprilTagLibraryArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor.Builder", ".setTagLibrary");
      AprilTagProcessor.Builder aprilTagProcessorBuilder = checkAprilTagProcessorBuilder(aprilTagProcessorBuilderArg);
      AprilTagLibrary aprilTagLibrary = checkAprilTagLibrary(aprilTagLibraryArg);
      if (aprilTagProcessorBuilder != null && aprilTagLibrary != null) {
        aprilTagProcessorBuilder.setTagLibrary(aprilTagLibrary);
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
  @Block(classes = AprilTagProcessor.Builder.class, methodName = "setOutputUnits")
  public void setOutputUnits(Object aprilTagProcessorBuilderArg, String distanceUnitString, String angleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor.Builder", ".setOutputUnits");
      AprilTagProcessor.Builder aprilTagProcessorBuilder = checkAprilTagProcessorBuilder(aprilTagProcessorBuilderArg);
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (aprilTagProcessorBuilder != null && distanceUnit != null && angleUnit != null) {
        aprilTagProcessorBuilder.setOutputUnits(distanceUnit, angleUnit);
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
  @Block(classes = AprilTagProcessor.Builder.class, methodName = "setDrawAxes")
  public void setDrawAxes(Object aprilTagProcessorBuilderArg, boolean drawAxes) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor.Builder", ".setDrawAxes");
      AprilTagProcessor.Builder aprilTagProcessorBuilder = checkAprilTagProcessorBuilder(aprilTagProcessorBuilderArg);
      if (aprilTagProcessorBuilder != null) {
        aprilTagProcessorBuilder.setDrawAxes(drawAxes);
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
  @Block(classes = AprilTagProcessor.Builder.class, methodName = "setDrawCubeProjection")
  public void setDrawCubeProjection(Object aprilTagProcessorBuilderArg, boolean drawCube) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor.Builder", ".setDrawCubeProjection");
      AprilTagProcessor.Builder aprilTagProcessorBuilder = checkAprilTagProcessorBuilder(aprilTagProcessorBuilderArg);
      if (aprilTagProcessorBuilder != null) {
        aprilTagProcessorBuilder.setDrawCubeProjection(drawCube);
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
  @Block(classes = AprilTagProcessor.Builder.class, methodName = "setDrawTagOutline")
  public void setDrawTagOutline(Object aprilTagProcessorBuilderArg, boolean drawOutline) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor.Builder", ".setDrawTagOutline");
      AprilTagProcessor.Builder aprilTagProcessorBuilder = checkAprilTagProcessorBuilder(aprilTagProcessorBuilderArg);
      if (aprilTagProcessorBuilder != null) {
        aprilTagProcessorBuilder.setDrawTagOutline(drawOutline);
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
  @Block(classes = AprilTagProcessor.Builder.class, methodName = "setDrawTagID")
  public void setDrawTagID(Object aprilTagProcessorBuilderArg, boolean drawTagID) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor.Builder", ".setDrawTagID");
      AprilTagProcessor.Builder aprilTagProcessorBuilder = checkAprilTagProcessorBuilder(aprilTagProcessorBuilderArg);
      if (aprilTagProcessorBuilder != null) {
        aprilTagProcessorBuilder.setDrawTagID(drawTagID);
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
  @Block(classes = AprilTagProcessor.Builder.class, methodName = "setNumThreads")
  public void setNumThreads(Object aprilTagProcessorBuilderArg, int numThreads) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor.Builder", ".setNumThreads");
      AprilTagProcessor.Builder aprilTagProcessorBuilder = checkAprilTagProcessorBuilder(aprilTagProcessorBuilderArg);
      if (aprilTagProcessorBuilder != null) {
        aprilTagProcessorBuilder.setNumThreads(numThreads);
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
  @Block(classes = AprilTagProcessor.Builder.class, methodName = "build")
  public AprilTagProcessor buildAprilTagProcessor(Object aprilTagProcessorBuilderArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor.Builder", ".build");
      AprilTagProcessor.Builder aprilTagProcessorBuilder = checkAprilTagProcessorBuilder(aprilTagProcessorBuilderArg);
      if (aprilTagProcessorBuilder != null) {
        return aprilTagProcessorBuilder.build();
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
  @Block(classes = AprilTagProcessor.class, methodName = "easyCreateWithDefaults")
  public AprilTagProcessor easyCreateWithDefaults() {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor", ".easyCreateWithDefaults");
      return AprilTagProcessor.easyCreateWithDefaults();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AprilTagProcessor.class, methodName = "setDecimation")
  public void setDecimation(Object aprilTagProcessorArg, float decimation) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor", ".setDecimation");
      AprilTagProcessor aprilTagProcessor = checkAprilTagProcessor(aprilTagProcessorArg);
      if (aprilTagProcessor != null) {
        aprilTagProcessor.setDecimation(decimation);
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
  @Block(classes = AprilTagProcessor.class, methodName = "setPoseSolver")
  public void setPoseSolver(Object aprilTagProcessorArg, String poseSolverString) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor", ".setPoseSolver");
      AprilTagProcessor aprilTagProcessor = checkAprilTagProcessor(aprilTagProcessorArg);
      PoseSolver poseSolver = checkPoseSolver(poseSolverString);
      if (aprilTagProcessor != null && poseSolver != null) {
        aprilTagProcessor.setPoseSolver(poseSolver);
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
  @Block(classes = AprilTagProcessor.class, methodName = "getPerTagAvgPoseSolveTime")
  public int getPerTagAvgPoseSolveTime(Object aprilTagProcessorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor", ".getPerTagAvgPoseSolveTime");
      AprilTagProcessor aprilTagProcessor = checkAprilTagProcessor(aprilTagProcessorArg);
      if (aprilTagProcessor != null) {
        return aprilTagProcessor.getPerTagAvgPoseSolveTime();
      }
      return 0;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AprilTagProcessor.class, methodName = "getDetections")
  public String getDetections(Object aprilTagProcessorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor", ".getDetections");
      AprilTagProcessor aprilTagProcessor = checkAprilTagProcessor(aprilTagProcessorArg);
      if (aprilTagProcessor != null) {
        return toJson(aprilTagProcessor.getDetections());
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
  @Block(classes = AprilTagProcessor.class, methodName = "getFreshDetections")
  public String getFreshDetections(Object aprilTagProcessorArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagProcessor", ".getFreshDetections");
      AprilTagProcessor aprilTagProcessor = checkAprilTagProcessor(aprilTagProcessorArg);
      if (aprilTagProcessor != null) {
        return toJson(aprilTagProcessor.getFreshDetections());
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
  @Block(classes = AprilTagGameDatabase.class, methodName = "getCurrentGameTagLibrary")
  public AprilTagLibrary getCurrentGameTagLibrary() {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagGameDatabase", ".getCurrentGameTagLibrary");
      return AprilTagGameDatabase.getCurrentGameTagLibrary();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AprilTagGameDatabase.class, methodName = "getCenterStageTagLibrary")
  public AprilTagLibrary getCenterStageTagLibrary() {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagGameDatabase", ".getCenterStageTagLibrary");
      return AprilTagGameDatabase.getCenterStageTagLibrary();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AprilTagGameDatabase.class, methodName = "getIntoTheDeepTagLibrary")
  public AprilTagLibrary getIntoTheDeepTagLibrary() {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagGameDatabase", ".getIntoTheDeepTagLibrary");
      return AprilTagGameDatabase.getIntoTheDeepTagLibrary();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AprilTagGameDatabase.class, methodName = "getSampleTagLibrary")
  public AprilTagLibrary getSampleTagLibrary() {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagGameDatabase", ".getSampleTagLibrary");
      return AprilTagGameDatabase.getSampleTagLibrary();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AprilTagLibrary.Builder.class, constructor = true)
  public AprilTagLibrary.Builder createAprilTagLibraryBuilder() {
    try {
      startBlockExecution(BlockType.CREATE, "AprilTagLibrary.Builder", "");
      return new AprilTagLibrary.Builder();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AprilTagLibrary.Builder.class, methodName = "setAllowOverwrite")
  public void setAllowOverwrite(Object aprilTagLibraryBuilderArg, boolean allowOverwrite) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagLibrary.Builder", ".setAllowOverwrite");
      AprilTagLibrary.Builder aprilTagLibraryBuilder = checkAprilTagLibraryBuilder(aprilTagLibraryBuilderArg);
      if (aprilTagLibraryBuilder != null) {
        aprilTagLibraryBuilder.setAllowOverwrite(allowOverwrite);
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
  @Block(classes = AprilTagLibrary.Builder.class, methodName = "addTag")
  public void addTag_WithMetadata(Object aprilTagLibraryBuilderArg, String aprilTagMetadataString) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagLibrary.Builder", ".addTag");
      AprilTagLibrary.Builder aprilTagLibraryBuilder = checkAprilTagLibraryBuilder(aprilTagLibraryBuilderArg);
      if (aprilTagLibraryBuilder != null) {
        AprilTagMetadata aprilTagMetadata = fromJson(aprilTagMetadataString, AprilTagMetadata.class);
        aprilTagLibraryBuilder.addTag(aprilTagMetadata);
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
  @Block(classes = AprilTagLibrary.Builder.class, methodName = "addTag")
  public void addTag(Object aprilTagLibraryBuilderArg, int id, String name, double tagSize, Object fieldPositionArg, String distanceUnitString, Object fieldOrientationArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagLibrary.Builder", ".addTag");
      AprilTagLibrary.Builder aprilTagLibraryBuilder = checkAprilTagLibraryBuilder(aprilTagLibraryBuilderArg);
      VectorF fieldPosition = checkVectorF(fieldPositionArg);
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      Quaternion fieldOrientation = checkQuaternion(fieldOrientationArg);
      if (aprilTagLibraryBuilder != null && fieldPosition != null  && distanceUnit != null && fieldOrientation != null) {
        aprilTagLibraryBuilder.addTag(id, name, tagSize, fieldPosition, distanceUnit, fieldOrientation);
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
  @Block(classes = AprilTagLibrary.Builder.class, methodName = "addTag")
  public void addTag_withoutPoseInfo(Object aprilTagLibraryBuilderArg, int id, String name, double tagSize, String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagLibrary.Builder", ".addTag");
      AprilTagLibrary.Builder aprilTagLibraryBuilder = checkAprilTagLibraryBuilder(aprilTagLibraryBuilderArg);
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (aprilTagLibraryBuilder != null && distanceUnit != null) {
        aprilTagLibraryBuilder.addTag(id, name, tagSize, distanceUnit);
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
  @Block(classes = AprilTagLibrary.Builder.class, methodName = "addTags")
  public void addTags(Object aprilTagLibraryBuilderArg, Object aprilTagLibraryArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagLibrary.Builder", ".addTags");
      AprilTagLibrary.Builder aprilTagLibraryBuilder = checkAprilTagLibraryBuilder(aprilTagLibraryBuilderArg);
      AprilTagLibrary aprilTagLibrary = checkAprilTagLibrary(aprilTagLibraryArg);
      if (aprilTagLibraryBuilder != null && aprilTagLibrary != null) {
        aprilTagLibraryBuilder.addTags(aprilTagLibrary);
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
  @Block(classes = AprilTagLibrary.Builder.class, methodName = "build")
  public AprilTagLibrary buildAprilTagLibrary(Object aprilTagLibraryBuilderArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagLibrary.Builder", ".build");
      AprilTagLibrary.Builder aprilTagLibraryBuilder = checkAprilTagLibraryBuilder(aprilTagLibraryBuilderArg);
      if (aprilTagLibraryBuilder != null) {
        return aprilTagLibraryBuilder.build();
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
  @Block(classes = AprilTagLibrary.class, methodName = "lookupTag")
  public String lookupTag(Object aprilTagLibraryArg, int id) {
    try {
      startBlockExecution(BlockType.FUNCTION, "AprilTagLibrary", ".lookupTag");
      AprilTagLibrary aprilTagLibrary = checkAprilTagLibrary(aprilTagLibraryArg);
      if (aprilTagLibrary != null) {
        AprilTagMetadata aprilTagMetadata = aprilTagLibrary.lookupTag(id);
        return toJson(aprilTagMetadata);
      }
      return "null";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AprilTagMetadata.class, constructor = true)
  public String createMetadata(int id, String name, double tagSize, Object fieldPositionArg, String distanceUnitString, Object fieldOrientationArg) {
    try {
      startBlockExecution(BlockType.CREATE, "AprilTagMetadata", "");
      VectorF fieldPosition = checkVectorF(fieldPositionArg);
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      Quaternion fieldOrientation = checkQuaternion(fieldOrientationArg);
      if (fieldPosition != null  && distanceUnit != null && fieldOrientation != null) {
        AprilTagMetadata aprilTagMetadata = new AprilTagMetadata(
            id, name, tagSize, fieldPosition, distanceUnit, fieldOrientation);
        return toJson(aprilTagMetadata);
      }
      return "null";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = AprilTagMetadata.class, constructor = true)
  public String createMetadata_withoutPoseInfo(int id, String name, double tagSize, String distanceUnitString) {
    try {
      startBlockExecution(BlockType.CREATE, "AprilTagMetadata", "");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (distanceUnit != null) {
        AprilTagMetadata aprilTagMetadata = new AprilTagMetadata(
            id, name, tagSize, distanceUnit);
        return toJson(aprilTagMetadata);
      }
      return "null";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  // createVectorF is called if blocks passes AprilTagMetadata.fieldPosition to a java function.
  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(exclusiveToBlocks = true)
  public VectorF createVectorF(String blockTypeString, String blockFirstName, String blockLastName, String json) {
    try {
      startBlockExecution(BlockType.valueOf(blockTypeString), blockFirstName, blockLastName);
      return fromJson(json, VectorF.class);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  // createQuaternion is called if blocks passes AprilTagMetadata.fieldOrientation to a java function.
  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(exclusiveToBlocks = true)
  public Quaternion createQuaternion(String blockTypeString, String blockFirstName, String blockLastName, String json) {
    try {
      startBlockExecution(BlockType.valueOf(blockTypeString), blockFirstName, blockLastName);
      return fromJson(json, Quaternion.class);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  // createAprilTagPoseFtc is called if blocks passes AprilTagDetection.ftcPose to a java function.
  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(exclusiveToBlocks = true)
  public AprilTagPoseFtc createAprilTagPoseFtc(String blockTypeString, String blockFirstName, String blockLastName, String json) {
    try {
      startBlockExecution(BlockType.valueOf(blockTypeString), blockFirstName, blockLastName);
      return fromJson(json, AprilTagPoseFtc.class);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  // createAprilTagPoseRaw is called if blocks passes AprilTagDetection.rawPose to a java function.
  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(exclusiveToBlocks = true)
  public AprilTagPoseRaw createAprilTagPoseRaw(String blockTypeString, String blockFirstName, String blockLastName, String json) {
    try {
      startBlockExecution(BlockType.valueOf(blockTypeString), blockFirstName, blockLastName);
      return fromJson(json, AprilTagPoseRaw.class);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  // createAprilTagPoseRobot is called if blocks passes AprilTagDetection.robotPose to a java function.
  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(exclusiveToBlocks = true)
  public Pose3D createAprilTagPoseRobot(String blockTypeString, String blockFirstName, String blockLastName, String json) {
    try {
      startBlockExecution(BlockType.valueOf(blockTypeString), blockFirstName, blockLastName);
      return fromJson(json, Pose3D.class);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  // createMatrixF is called if blocks passes AprilTagDetection.rawPose.R to a java function.
  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(exclusiveToBlocks = true)
  public MatrixF createMatrixF(String blockTypeString, String blockFirstName, String blockLastName, String json) {
    try {
      startBlockExecution(BlockType.valueOf(blockTypeString), blockFirstName, blockLastName);
      return fromJson(json, GeneralMatrixF.class);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

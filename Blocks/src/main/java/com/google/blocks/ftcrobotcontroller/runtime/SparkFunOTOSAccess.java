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
import com.google.blocks.ftcrobotcontroller.hardware.HardwareItem;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS.Pose2D;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS.SignalProcessConfig;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS.Status;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS.Version;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;

/**
 * A class that provides JavaScript access to {@link SparkFunOTOS}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class SparkFunOTOSAccess extends HardwareAccess<SparkFunOTOS> {
  private final SparkFunOTOS sparkFunOTOS;

  SparkFunOTOSAccess(BlocksOpMode blocksOpMode, HardwareItem hardwareItem, HardwareMap hardwareMap) {
    super(blocksOpMode, hardwareItem, hardwareMap, SparkFunOTOS.class);
    this.sparkFunOTOS = hardwareDevice;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getImuCalibrationProgress")
  public int getImuCalibrationProgress() {
    try {
      startBlockExecution(BlockType.GETTER, ".ImuCalibrationProgress");
      return sparkFunOTOS.getImuCalibrationProgress();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getLinearScalar")
  public double getLinearScalar() {
    try {
      startBlockExecution(BlockType.GETTER, ".LinearScalar");
      return sparkFunOTOS.getLinearScalar();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "setLinearScalar")
  public boolean setLinearScalar(double scalar) {
    try {
      startBlockExecution(BlockType.SETTER, ".LinearScalar");
      return sparkFunOTOS.setLinearScalar(scalar);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getAngularScalar")
  public double getAngularScalar() {
    try {
      startBlockExecution(BlockType.GETTER, ".AngularScalar");
      return sparkFunOTOS.getAngularScalar();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "setAngularScalar")
  public boolean setAngularScalar(double scalar) {
    try {
      startBlockExecution(BlockType.SETTER, ".AngularScalar");
      return sparkFunOTOS.setAngularScalar(scalar);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getLinearUnit")
  public String getLinearUnit() {
    try {
      startBlockExecution(BlockType.GETTER, ".LinearUnit");
      DistanceUnit distanceUnit = sparkFunOTOS.getLinearUnit();
      if (distanceUnit != null) {
        return distanceUnit.toString();
      }
      return "";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "setLinearUnit")
  public void setLinearUnit(String distanceUnitString) {
    try {
      startBlockExecution(BlockType.SETTER, ".LinearUnit");
      DistanceUnit distanceUnit = checkArg(distanceUnitString, DistanceUnit.class, "");
      if (distanceUnit != null) {
        sparkFunOTOS.setLinearUnit(distanceUnit);
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
  @Block(classes = {SparkFunOTOS.class}, methodName = "getAngularUnit")
  public String getAngularUnit() {
    try {
      startBlockExecution(BlockType.GETTER, ".AngularUnit");
      AngleUnit angleUnit = sparkFunOTOS.getAngularUnit();
      if (angleUnit != null) {
        return angleUnit.toString();
      }
      return "";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "setAngularUnit")
  public void setAngularUnit(String angleUnitString) {
    try {
      startBlockExecution(BlockType.SETTER, ".AngularUnit");
      AngleUnit angleUnit = checkArg(angleUnitString, AngleUnit.class, "");
      if (angleUnit != null) {
        sparkFunOTOS.setAngularUnit(angleUnit);
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
  @Block(classes = {SparkFunOTOS.class}, methodName = "getStatus")
  public String getStatus() {
    try {
      startBlockExecution(BlockType.GETTER, ".Status");
      return SimpleGson.getInstance().toJson(sparkFunOTOS.getStatus());
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getOffset")
  public String getOffset() {
    try {
      startBlockExecution(BlockType.GETTER, ".Offset");
      return SimpleGson.getInstance().toJson(sparkFunOTOS.getOffset());
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "setOffset")
  public void setOffset(String json) {
    try {
      startBlockExecution(BlockType.SETTER, ".Offset");
      Pose2D pose = SimpleGson.getInstance().fromJson(json, Pose2D.class);
      sparkFunOTOS.setOffset(pose);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getPosition")
  public String getPosition() {
    try {
      startBlockExecution(BlockType.GETTER, ".Position");
      return SimpleGson.getInstance().toJson(sparkFunOTOS.getPosition());
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "setPosition")
  public void setPosition(String json) {
    try {
      startBlockExecution(BlockType.SETTER, ".Position");
      Pose2D pose = SimpleGson.getInstance().fromJson(json, Pose2D.class);
      sparkFunOTOS.setPosition(pose);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getAcceleration")
  public String getAcceleration() {
    try {
      startBlockExecution(BlockType.GETTER, ".Acceleration");
      return SimpleGson.getInstance().toJson(sparkFunOTOS.getAcceleration());
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getVelocity")
  public String getVelocity() {
    try {
      startBlockExecution(BlockType.GETTER, ".Velocity");
      return SimpleGson.getInstance().toJson(sparkFunOTOS.getVelocity());
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getPositionStdDev")
  public String getPositionStdDev() {
    try {
      startBlockExecution(BlockType.GETTER, ".PositionStdDev");
      return SimpleGson.getInstance().toJson(sparkFunOTOS.getPositionStdDev());
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getAccelerationStdDev")
  public String getAccelerationStdDev() {
    try {
      startBlockExecution(BlockType.GETTER, ".AccelerationStdDev");
      return SimpleGson.getInstance().toJson(sparkFunOTOS.getAccelerationStdDev());
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getVelocityStdDev")
  public String getVelocityStdDev() {
    try {
      startBlockExecution(BlockType.GETTER, ".VelocityStdDev");
      return SimpleGson.getInstance().toJson(sparkFunOTOS.getVelocityStdDev());
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getSignalProcessConfig")
  public String getSignalProcessConfig() {
    try {
      startBlockExecution(BlockType.GETTER, ".SignalProcessConfig");
      return SimpleGson.getInstance().toJson(sparkFunOTOS.getSignalProcessConfig());
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "setSignalProcessConfig")
  public void setSignalProcessConfig(String json) {
    try {
      startBlockExecution(BlockType.SETTER, ".SignalProcessConfig");
      SignalProcessConfig signalProcessConfig = SimpleGson.getInstance().fromJson(json, SignalProcessConfig.class);
      sparkFunOTOS.setSignalProcessConfig(signalProcessConfig);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getPosVelAcc")
  public String getPosVelAcc(String json1, String json2, String json3) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getPosVelAcc");
      // Make sure that the 3 arguments are valid JSON for Pose2D objects.
      SimpleGson.getInstance().fromJson(json1, Pose2D.class);
      SimpleGson.getInstance().fromJson(json2, Pose2D.class);
      SimpleGson.getInstance().fromJson(json3, Pose2D.class);
      Pose2D[] posVelAcc = new Pose2D[3];
      for (int i = 0; i < posVelAcc.length; i++) {
        posVelAcc[i] = new Pose2D();
      }
      sparkFunOTOS.getPosVelAcc(posVelAcc[0], posVelAcc[1], posVelAcc[2]);
      return SimpleGson.getInstance().toJson(posVelAcc);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getPosVelAccStdDev")
  public String getPosVelAccStdDev(String json1, String json2, String json3) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getPosVelAccStdDev");
      // Make sure that the 3 arguments are valid JSON for Pose2D objects.
      SimpleGson.getInstance().fromJson(json1, Pose2D.class);
      SimpleGson.getInstance().fromJson(json2, Pose2D.class);
      SimpleGson.getInstance().fromJson(json3, Pose2D.class);
      Pose2D[] posVelAccStdDev = new Pose2D[3];
      for (int i = 0; i < posVelAccStdDev.length; i++) {
        posVelAccStdDev[i] = new Pose2D();
      }
      sparkFunOTOS.getPosVelAccStdDev(posVelAccStdDev[0], posVelAccStdDev[1], posVelAccStdDev[2]);
      return SimpleGson.getInstance().toJson(posVelAccStdDev);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getPosVelAccAndStdDev")
  public String getPosVelAccAndStdDev(String json1, String json2, String json3, String json4, String json5, String json6) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getPosVelAccAndStdDev");
      // Make sure that the 6 arguments are valid JSON for Pose2D objects.
      SimpleGson.getInstance().fromJson(json1, Pose2D.class);
      SimpleGson.getInstance().fromJson(json2, Pose2D.class);
      SimpleGson.getInstance().fromJson(json3, Pose2D.class);
      SimpleGson.getInstance().fromJson(json4, Pose2D.class);
      SimpleGson.getInstance().fromJson(json5, Pose2D.class);
      SimpleGson.getInstance().fromJson(json6, Pose2D.class);
      Pose2D[] posVelAccAndStdDev = new Pose2D[6];
      for (int i = 0; i < posVelAccAndStdDev.length; i++) {
        posVelAccAndStdDev[i] = new Pose2D();
      }
      sparkFunOTOS.getPosVelAccAndStdDev(posVelAccAndStdDev[0], posVelAccAndStdDev[1], posVelAccAndStdDev[2], posVelAccAndStdDev[3], posVelAccAndStdDev[4], posVelAccAndStdDev[5]);
      return SimpleGson.getInstance().toJson(posVelAccAndStdDev);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "getVersionInfo")
  public String getVersionInfo(String json1, String json2) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getVersionInfo");
      // Make sure that the 3 arguments are valid JSON for Version objects.
      SimpleGson.getInstance().fromJson(json1, Version.class);
      SimpleGson.getInstance().fromJson(json2, Version.class);
      Version[] versionInfo = new Version[2];
      for (int i = 0; i < versionInfo.length; i++) {
        versionInfo[i] = new Version();
      }
      sparkFunOTOS.getVersionInfo(versionInfo[0], versionInfo[1]);
      return SimpleGson.getInstance().toJson(versionInfo);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "resetTracking")
  public void resetTracking() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".resetTracking");
      sparkFunOTOS.resetTracking();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "begin")
  public boolean begin() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".begin");
      return sparkFunOTOS.begin();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "calibrateImu")
  public boolean calibrateImu() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".calibrateImu");
      return sparkFunOTOS.calibrateImu();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "calibrateImu")
  public boolean calibrateImu_withArgs(int numSamples, boolean waitUntilDone) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".calibrateImu");
      return sparkFunOTOS.calibrateImu(numSamples, waitUntilDone);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "isConnected")
  public boolean isConnected() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".isConnected");
      return sparkFunOTOS.isConnected();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {SparkFunOTOS.class}, methodName = "selfTest")
  public boolean selfTest() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".selfTest");
      return sparkFunOTOS.selfTest();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

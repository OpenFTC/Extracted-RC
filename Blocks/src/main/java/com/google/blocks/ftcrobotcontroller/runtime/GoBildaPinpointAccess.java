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
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver.DeviceStatus;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver.EncoderDirection;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver.GoBildaOdometryPods;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver.ReadData;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;

/**
 * A class that provides JavaScript access to {@link GoBildaPinpointDriver}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class GoBildaPinpointAccess extends HardwareAccess<GoBildaPinpointDriver> {
  private final GoBildaPinpointDriver goBildaPinpoint;

  GoBildaPinpointAccess(BlocksOpMode blocksOpMode, HardwareItem hardwareItem, HardwareMap hardwareMap) {
    super(blocksOpMode, hardwareItem, hardwareMap, GoBildaPinpointDriver.class);
    this.goBildaPinpoint = hardwareDevice;
  }

  private GoBildaOdometryPods checkGoBildaOdometryPods(String goBildaOdometryPodsString) {
    return checkArg(goBildaOdometryPodsString, GoBildaOdometryPods.class, "pods");
  }

  private EncoderDirection checkEncoderDirection(String encoderDirectionString, String socketName) {
    return checkArg(encoderDirectionString, EncoderDirection.class, socketName);
  }

  private ReadData checkReadData(String readDataString) {
    return checkArg(readDataString, ReadData.class, "readData");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "setYawScalar")
  public void setYawScalar(double yawOffset) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setYawScalar");
      goBildaPinpoint.setYawScalar(yawOffset);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getYawScalar")
  public float getYawScalar() {
    try {
      startBlockExecution(BlockType.GETTER, ".YawScalar");
      return goBildaPinpoint.getYawScalar();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getDeviceID")
  public int getDeviceID() {
    try {
      startBlockExecution(BlockType.GETTER, ".DeviceID");
      return goBildaPinpoint.getDeviceID();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getDeviceVersion")
  public int getDeviceVersion() {
    try {
      startBlockExecution(BlockType.GETTER, ".DeviceVersion");
      return goBildaPinpoint.getDeviceVersion();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getLoopTime")
  public int getLoopTime() {
    try {
      startBlockExecution(BlockType.GETTER, ".LoopTime");
      return goBildaPinpoint.getLoopTime();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getEncoderX")
  public int getEncoderX() {
    try {
      startBlockExecution(BlockType.GETTER, ".EncoderX");
      return goBildaPinpoint.getEncoderX();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getEncoderY")
  public int getEncoderY() {
    try {
      startBlockExecution(BlockType.GETTER, ".EncoderY");
      return goBildaPinpoint.getEncoderY();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }


  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getFrequency")
  public double getFrequency() {
    try {
      startBlockExecution(BlockType.GETTER, ".Frequency");
      return goBildaPinpoint.getFrequency();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getDeviceStatus")
  public String getDeviceStatus() {
    try {
      startBlockExecution(BlockType.GETTER, ".DeviceStatus");
      DeviceStatus deviceStatus = goBildaPinpoint.getDeviceStatus();
      if (deviceStatus != null) {
        return deviceStatus.toString();
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getPosition")
  public Pose2D getPosition() {
    try {
      startBlockExecution(BlockType.GETTER, ".Position");
      return goBildaPinpoint.getPosition();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "update")
  public void update() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".update");
      goBildaPinpoint.update();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "update")
  public void update_withReadData(String readDataString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".update");
      ReadData readData = checkReadData(readDataString);
      if (readData != null) {
        goBildaPinpoint.update(readData);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "setOffsets")
  public void setOffsets(double xOffset, double yOffset, String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setOffsets");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (distanceUnit != null) {
        goBildaPinpoint.setOffsets(xOffset, yOffset, distanceUnit);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "recalibrateIMU")
  public void recalibrateIMU() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".recalibrateIMU");
      goBildaPinpoint.recalibrateIMU();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "resetPosAndIMU")
  public void resetPosAndIMU() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".resetPosAndIMU");
      goBildaPinpoint.resetPosAndIMU();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "setEncoderDirections")
  public void setEncoderDirections(String xEncoderString, String yEncoderString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setEncoderDirections");
      EncoderDirection xEncoder = checkEncoderDirection(xEncoderString, "xEncoder");
      EncoderDirection yEncoder = checkEncoderDirection(yEncoderString, "yEncoder");
      if (xEncoder != null && yEncoder != null) {
        goBildaPinpoint.setEncoderDirections(xEncoder, yEncoder);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "setEncoderResolution")
  public void setEncoderResolution_withPods(String podsString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setEncoderResolution");
      GoBildaOdometryPods pods = checkGoBildaOdometryPods(podsString);
      if (pods != null) {
        goBildaPinpoint.setEncoderResolution(pods);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "setEncoderResolution")
  public void setEncoderResolution_withTicks(double ticksPerUnit, String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setEncoderResolution");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (distanceUnit != null) {
        goBildaPinpoint.setEncoderResolution(ticksPerUnit, distanceUnit);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "setPosition")
  public void setPosition(Object pose2DArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setPosition");
      Pose2D pose2D = checkPose2D(pose2DArg, "position");
      if (pose2D != null) {
        goBildaPinpoint.setPosition(pose2D);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "setPosX")
  public void setPosX(double posX, String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setPosX");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (distanceUnit != null) {
        goBildaPinpoint.setPosX(posX, distanceUnit);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "setPosY")
  public void setPosY(double posY, String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setPosY");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (distanceUnit != null) {
        goBildaPinpoint.setPosY(posY, distanceUnit);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "setHeading")
  public void setHeading(double heading, String angleUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setHeading");
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (angleUnit != null) {
        goBildaPinpoint.setHeading(heading, angleUnit);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getPosX")
  public double getPosX(String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getPosX");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (distanceUnit != null) {
        return goBildaPinpoint.getPosX(distanceUnit);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getPosY")
  public double getPosY(String distanceUnitString) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getPosY");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (distanceUnit != null) {
        return goBildaPinpoint.getPosY(distanceUnit);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getHeading")
  public double getHeading_withAngleUnit(String angleUnitString) {
    try {
      startBlockExecution(BlockType.GETTER, ".Heading");
      AngleUnit angleUnit = checkAngleUnit(angleUnitString);
      if (angleUnit != null) {
        return goBildaPinpoint.getHeading(angleUnit);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getHeading")
  public double getHeading_withUnnormalizedAngleUnit(String unnormalizedAngleUnitString) {
    try {
      startBlockExecution(BlockType.GETTER, ".Heading");
      UnnormalizedAngleUnit unnormalizedAngleUnit = checkUnnormalizedAngleUnit(unnormalizedAngleUnitString);
      if (unnormalizedAngleUnit != null) {
        return goBildaPinpoint.getHeading(unnormalizedAngleUnit);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getVelX")
  public double getVelX(String distanceUnitString) {
    try {
      startBlockExecution(BlockType.GETTER, ".VelX");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (distanceUnit != null) {
        return goBildaPinpoint.getVelX(distanceUnit);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getVelY")
  public double getVelY(String distanceUnitString) {
    try {
      startBlockExecution(BlockType.GETTER, ".VelY");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (distanceUnit != null) {
        return goBildaPinpoint.getVelY(distanceUnit);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getHeadingVelocity")
  public double getHeadingVelocity_withUnnormalizedAngleUnit(String unnormalizedAngleUnitString) {
    try {
      startBlockExecution(BlockType.GETTER, ".HeadingVelocity");
      UnnormalizedAngleUnit unnormalizedAngleUnit = checkUnnormalizedAngleUnit(unnormalizedAngleUnitString);
      if (unnormalizedAngleUnit != null) {
        return goBildaPinpoint.getHeadingVelocity(unnormalizedAngleUnit);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getXOffset")
  public float getXOffset(String distanceUnitString) {
    try {
      startBlockExecution(BlockType.GETTER, ".XOffset");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (distanceUnit != null) {
        return goBildaPinpoint.getXOffset(distanceUnit);
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
  @Block(classes = {GoBildaPinpointDriver.class}, methodName = "getYOffset")
  public float getYOffset(String distanceUnitString) {
    try {
      startBlockExecution(BlockType.GETTER, ".YOffset");
      DistanceUnit distanceUnit = checkDistanceUnit(distanceUnitString);
      if (distanceUnit != null) {
        return goBildaPinpoint.getYOffset(distanceUnit);
      }
      return 0;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}

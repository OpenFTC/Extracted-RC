/*
 * Copyright 2016 Google LLC
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

package com.google.blocks.ftcrobotcontroller.hardware;

import com.qualcomm.ftccommon.configuration.RobotConfigFile;
import com.qualcomm.ftccommon.configuration.RobotConfigFileManager;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.hardware.configuration.LynxModuleConfiguration;
import com.qualcomm.robotcore.hardware.configuration.ReadXMLFileHandler;
import com.qualcomm.robotcore.hardware.configuration.ServoHubConfiguration;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.xmlpull.v1.XmlPullParser;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

/**
 * A class that maps the supported hardware types to lists of specific hardware items.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public class HardwareItemMap {
  private final SortedMap<HardwareType, List<HardwareItem>> map =
      new TreeMap<HardwareType, List<HardwareItem>>();

  private final Set<DeviceConfiguration> devices = new HashSet<DeviceConfiguration>();

  /**
   * Creates a new {@link HardwareItemMap} with the supported hardware items in the active configuration.
   */
  public static HardwareItemMap newHardwareItemMap() {
    OpModeManagerImpl opModeManagerImpl = OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().getRootActivity());
    if (opModeManagerImpl != null) {
      HardwareMap hardwareMap = opModeManagerImpl.getHardwareMap();
      if (hardwareMap != null) {
        return newHardwareItemMap(hardwareMap);
      }
    }

    try {
      RobotConfigFileManager robotConfigFileManager = new RobotConfigFileManager();
      RobotConfigFile activeConfig = robotConfigFileManager.getActiveConfig();
      XmlPullParser pullParser = activeConfig.getXml();
      try {
        return new HardwareItemMap(pullParser);
      } finally {
        // TODO: it would be good to close the file behind the pull parser, rather than waiting for the finalizer
      }
    } catch (Exception e) {
      RobotLog.logStackTrace(e);
      return new HardwareItemMap();
    }
  }

  /**
   * Creates a new {@link HardwareItemMap} with the supported hardware items in the given
   * {@link HardwareMap}.
   */
  public static HardwareItemMap newHardwareItemMap(HardwareMap hardwareMap) {
    return new HardwareItemMap(hardwareMap);
  }

  /**
   * Constructs a {@link HardwareItemMap}.
   */
  // visible for testing
  HardwareItemMap() {
  }

  /**
   * Constructs a {@link HardwareItemMap} with the supported hardware items in the given
   * configuration {@link XmlPullParser}.
   */
  private HardwareItemMap(XmlPullParser pullParser) {
    try {
      ReadXMLFileHandler readXMLFileHandler = new ReadXMLFileHandler();
      for (ControllerConfiguration controllerConfiguration : readXMLFileHandler.parse(pullParser)) {
        addDevice(controllerConfiguration);
      }
    } catch (RobotCoreException e) {
      RobotLog.logStackTrace(e);
    }
  }

  /**
   * Constructs a {@link HardwareItemMap} with the supported hardware items in the given
   * configuration {@link Reader}.
   */
  // visible for testing
  HardwareItemMap(Reader reader) {
    try {
      ReadXMLFileHandler readXMLFileHandler = new ReadXMLFileHandler();
      for (ControllerConfiguration controllerConfiguration : readXMLFileHandler.parse(reader)) {
        addDevice(controllerConfiguration);
      }
    } catch (RobotCoreException e) {
      RobotLog.logStackTrace(e);
    }
  }

  /**
   * Constructs a {@link HardwareItemMap} with the supported hardware items in the given
   * {@link HardwareMap}.
   */
  private HardwareItemMap(HardwareMap hardwareMap) {
    for (HardwareType hardwareType : HardwareType.values()) {
      SortedSet<String> deviceNames = hardwareMap.getAllNames(hardwareType.deviceType);
      for (String deviceName : deviceNames) {
        addHardwareItem(hardwareType, deviceName);
      }
    }
  }

  /**
   * Adds the given {@link ControllerConfiguration}, as well as any devices belonging to the
   * controller, to the given HardwareItemMap.
   */
  private void addController(ControllerConfiguration<? extends DeviceConfiguration> controllerConfiguration) {
    for (DeviceConfiguration deviceConfiguration : controllerConfiguration.getDevices()) {
      addDevice(deviceConfiguration);
    }
    if (controllerConfiguration instanceof LynxModuleConfiguration) {
      LynxModuleConfiguration lynxModuleConfiguration =
          (LynxModuleConfiguration) controllerConfiguration;
      for (DeviceConfiguration deviceConfiguration : lynxModuleConfiguration.getServos()) {
        addDevice(deviceConfiguration);
      }
      for (DeviceConfiguration deviceConfiguration : lynxModuleConfiguration.getMotors()) {
        addDevice(deviceConfiguration);
      }
      for (DeviceConfiguration deviceConfiguration : lynxModuleConfiguration.getAnalogInputs()) {
        addDevice(deviceConfiguration);
      }
      for (DeviceConfiguration deviceConfiguration : lynxModuleConfiguration.getPwmOutputs()) {
        addDevice(deviceConfiguration);
      }
      for (DeviceConfiguration deviceConfiguration : lynxModuleConfiguration.getI2cDevices()) {
        addDevice(deviceConfiguration);
      }
      for (DeviceConfiguration deviceConfiguration : lynxModuleConfiguration.getDigitalDevices()) {
        addDevice(deviceConfiguration);
      }
    } else if (controllerConfiguration instanceof ServoHubConfiguration) {
      ServoHubConfiguration servoHubConfiguration =
              (ServoHubConfiguration) controllerConfiguration;

      for (DeviceConfiguration deviceConfiguration : servoHubConfiguration.getServos()) {
        addDevice(deviceConfiguration);
      }
    }
  }

  /**
   * Adds the given {@link DeviceConfiguration} to the given HardwareItemMap.
   */
  private void addDevice(DeviceConfiguration deviceConfiguration) {
    // Use a set to prevent duplicates. Duplicates can occur if a controller returns the same
    // devices in getDevices() as it does in getMotors, getServos, etc.
    if (devices.add(deviceConfiguration)) {
      if (deviceConfiguration.isEnabled()) {
        for (HardwareType hardwareType : HardwareUtil.getHardwareTypes(deviceConfiguration)) {
          addHardwareItem(hardwareType, deviceConfiguration.getName());
        }
        if (deviceConfiguration instanceof ControllerConfiguration) {
          addController((ControllerConfiguration<? extends DeviceConfiguration>) deviceConfiguration);
        }
      }
    }
  }

  /**
   * Adds a {@link HardwareItem} to the given HardwareItemMap.
   */
  private void addHardwareItem(HardwareType hardwareType, String deviceName) {
    if (deviceName.isEmpty()) {
      RobotLog.w("Blocks cannot support a hardware device (" +
          hardwareType.deviceType.getSimpleName() + ") whose name is empty.");
      return;
    }
    List<HardwareItem> hardwareItemList = map.get(hardwareType);
    if (hardwareItemList == null) {
      hardwareItemList = new ArrayList<HardwareItem>();
      map.put(hardwareType, hardwareItemList);
    }
    // paranoia: avoid theoretically possible exact duplicates
    for (HardwareItem item : hardwareItemList) {
      if (item.deviceName.equals(deviceName)) {
        return; // we already have this item
      }
    }
    HardwareItem hardwareItem = new HardwareItem(hardwareType, deviceName);
    hardwareItemList.add(hardwareItem);
  }

  /**
   * Returns the number of {@link HardwareType}s stored in this HardwareItemMap.
   */
  public int getHardwareTypeCount() {
    return map.size();
  }

  /**
   * Returns true of this HardwareItemMap contains the given {@link HardwareType}.
   */
  public boolean contains(HardwareType hardwareType) {
    return map.containsKey(hardwareType);
  }

  /**
   * Returns a list of {@link HardwareItem}s for the given {@link HardwareType}, sorted by device
   * name.
   */
  public List<HardwareItem> getHardwareItems(HardwareType hardwareType) {
    List<HardwareItem> list = new ArrayList<HardwareItem>();
    if (map.containsKey(hardwareType)) {
      for (HardwareItem hardwareItem : map.get(hardwareType)) {
        list.add(hardwareItem);
      }
    }
    Collections.sort(list, new Comparator<HardwareItem>() {
      @Override public int compare(HardwareItem a, HardwareItem b) {
        return a.deviceName.compareTo(b.deviceName);
      }
    });
    return Collections.unmodifiableList(list);
  }

  /**
   * Returns a list of all {@link HardwareItem}s in this HardwareItemMap, sorted by identifiers.
   */
  public Iterable<HardwareItem> getAllHardwareItems() {
    List<HardwareItem> list = new ArrayList<HardwareItem>();
    for (List<HardwareItem> hardwareItems : map.values()) {
      list.addAll(hardwareItems);
    }
    Collections.sort(list, new Comparator<HardwareItem>() {
      @Override public int compare(HardwareItem a, HardwareItem b) {
        return a.identifier.compareTo(b.identifier);
      }
    });
    return Collections.unmodifiableList(list);
  }

  /**
   * Returns a set of {@link HardwareType}s in this HardwareItemMap.
   */
  public Set<HardwareType> getHardwareTypes() {
    return Collections.unmodifiableSet(map.keySet());
  }

  // java.lang.Object methods

  @Override
  public boolean equals(Object o) {
    if (o instanceof HardwareItemMap) {
      HardwareItemMap that = (HardwareItemMap) o;
      return this.map.equals(that.map);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return map.hashCode();
  }
}

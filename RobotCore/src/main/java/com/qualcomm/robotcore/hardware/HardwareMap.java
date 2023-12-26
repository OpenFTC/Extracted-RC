/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.robotcore.hardware;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.R;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
import com.qualcomm.robotcore.util.Device;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HardwareMap provides a means of retrieving runtime HardwareDevice instances according to the
 * names with which the corresponding physical devices were associated during robot configuration.
 *
 * <p>A HardwareMap also contains an associated application context in which it was instantiated.
 * Through their {@link com.qualcomm.robotcore.eventloop.opmode.OpMode#hardwareMap hardwareMap}, this
 * provides access to a {@link Context} for OpModes, as such an appropriate instance is needed
 * by various system APIs.</p>
 *
 * Retrieving devices from a HardwareMap will initialize them if they have not already been
 * initialized, which may take some time. As a result, you should ONLY access a HardwareMap from
 * the Init phase of your OpMode.
 */
@SuppressWarnings("WeakerAccess")
public class HardwareMap implements Iterable<HardwareDevice> {

  //------------------------------------------------------------------------------------------------
  // State
  //------------------------------------------------------------------------------------------------

  public DeviceMapping<DcMotorController>     dcMotorController     = new DeviceMapping<DcMotorController>(DcMotorController.class);
  public DeviceMapping<DcMotor>               dcMotor               = new DeviceMapping<DcMotor>(DcMotor.class);

  public DeviceMapping<ServoController>       servoController       = new DeviceMapping<ServoController>(ServoController.class);
  public DeviceMapping<Servo>                 servo                 = new DeviceMapping<Servo>(Servo.class);
  public DeviceMapping<CRServo>               crservo               = new DeviceMapping<CRServo>(CRServo.class);

  public DeviceMapping<TouchSensorMultiplexer> touchSensorMultiplexer = new DeviceMapping<TouchSensorMultiplexer>(TouchSensorMultiplexer.class);

  public DeviceMapping<AnalogInput>           analogInput           = new DeviceMapping<AnalogInput>(AnalogInput.class);
  public DeviceMapping<DigitalChannel>        digitalChannel        = new DeviceMapping<DigitalChannel>(DigitalChannel.class);
  public DeviceMapping<OpticalDistanceSensor> opticalDistanceSensor = new DeviceMapping<OpticalDistanceSensor>(OpticalDistanceSensor.class);
  public DeviceMapping<TouchSensor>           touchSensor           = new DeviceMapping<TouchSensor>(TouchSensor.class);
  public DeviceMapping<PWMOutput>             pwmOutput             = new DeviceMapping<PWMOutput>(PWMOutput.class);
  public DeviceMapping<I2cDevice>             i2cDevice             = new DeviceMapping<I2cDevice>(I2cDevice.class);
  public DeviceMapping<I2cDeviceSynch>        i2cDeviceSynch        = new DeviceMapping<I2cDeviceSynch>(I2cDeviceSynch.class);
  public DeviceMapping<ColorSensor>           colorSensor           = new DeviceMapping<ColorSensor>(ColorSensor.class);
  public DeviceMapping<LED>                   led                   = new DeviceMapping<LED>(LED.class);

  public DeviceMapping<AccelerationSensor>    accelerationSensor    = new DeviceMapping<AccelerationSensor>(AccelerationSensor.class);
  public DeviceMapping<CompassSensor>         compassSensor         = new DeviceMapping<CompassSensor>(CompassSensor.class);
  public DeviceMapping<GyroSensor>            gyroSensor            = new DeviceMapping<GyroSensor>(GyroSensor.class);
  public DeviceMapping<IrSeekerSensor>        irSeekerSensor        = new DeviceMapping<IrSeekerSensor>(IrSeekerSensor.class);
  public DeviceMapping<LightSensor>           lightSensor           = new DeviceMapping<LightSensor>(LightSensor.class);
  public DeviceMapping<UltrasonicSensor>      ultrasonicSensor      = new DeviceMapping<UltrasonicSensor>(UltrasonicSensor.class);
  public DeviceMapping<VoltageSensor>         voltageSensor         = new DeviceMapping<VoltageSensor>(VoltageSensor.class);

  protected Map<String, List<HardwareDevice>> allDevicesMap         = new HashMap<String, List<HardwareDevice>>();
  protected List<HardwareDevice>              allDevicesList        = null;   // cache for iteration
  protected Map<HardwareDevice, Set<String>>  deviceNames           = new HashMap<HardwareDevice, Set<String>>();
  protected Map<SerialNumber, HardwareDevice> serialNumberMap       = new HashMap<SerialNumber, HardwareDevice>();
  // We need a List to allow for multiple configuration entries that have the same name.
  protected Map<String, List<DeviceInstancesFromSingleConfigEntry>> devicesWithMultipleDriversMap = new HashMap<>();
  // WE MUST hold on to a reference to this so that it doesn't get garbage-collected before the HardwareMap.
  private final OpModeManagerNotifier.Notifications opModeNotifications;

  public final List<DeviceMapping<? extends HardwareDevice>> allDeviceMappings;

  public final Context appContext;
  protected final Object lock = new Object();

  private static final String TAG = "HardwareMap";
  @SuppressWarnings("rawtypes") // We have to use the raw type here as far as I (Noah) can tell
  private static final Class<I2cDeviceSynchDevice> i2cDriverBaseClass = I2cDeviceSynchDevice.class;

  //------------------------------------------------------------------------------------------------
  // Construction
  //------------------------------------------------------------------------------------------------

  public HardwareMap(Context appContext, OpModeManagerNotifier notifier) {
    this.appContext = appContext;

    this.allDeviceMappings = new ArrayList<DeviceMapping<? extends HardwareDevice>>(30);  // 30 is approximate

    this.allDeviceMappings.add(this.dcMotorController);
    this.allDeviceMappings.add(this.dcMotor);

    this.allDeviceMappings.add(this.servoController);
    this.allDeviceMappings.add(this.servo);
    this.allDeviceMappings.add(this.crservo);

    this.allDeviceMappings.add(this.touchSensorMultiplexer);

    this.allDeviceMappings.add(this.analogInput);
    this.allDeviceMappings.add(this.digitalChannel);
    this.allDeviceMappings.add(this.opticalDistanceSensor);
    this.allDeviceMappings.add(this.touchSensor);
    this.allDeviceMappings.add(this.pwmOutput);
    this.allDeviceMappings.add(this.i2cDevice);
    this.allDeviceMappings.add(this.i2cDeviceSynch);
    this.allDeviceMappings.add(this.colorSensor);
    this.allDeviceMappings.add(this.led);

    this.allDeviceMappings.add(this.accelerationSensor);
    this.allDeviceMappings.add(this.compassSensor);
    this.allDeviceMappings.add(this.gyroSensor);
    this.allDeviceMappings.add(this.irSeekerSensor);
    this.allDeviceMappings.add(this.lightSensor);
    this.allDeviceMappings.add(this.ultrasonicSensor);
    this.allDeviceMappings.add(this.voltageSensor);

    opModeNotifications = new OpModeManagerNotifier.Notifications() {
      @Override public void onOpModePreInit(OpMode opMode) {
        synchronized (lock) {
          RobotLog.dd(TAG, "Clearing which device instances have been retrieved");
          for (List<DeviceInstancesFromSingleConfigEntry> configEntries: devicesWithMultipleDriversMap.values()) {
            for (DeviceInstancesFromSingleConfigEntry configEntryDriverInstances: configEntries) {
              for (DeviceInstanceHolder deviceInstanceHolder : configEntryDriverInstances.deviceInstanceHolders) {
                deviceInstanceHolder.hasBeenRetrieved = false;
              }
            }
          }
        }
      }
      @Override public void onOpModePreStart(OpMode opMode) { }
      @Override public void onOpModePostStop(OpMode opMode) { }
    };

    // notifier should ONLY be null for a HardwareMap that won't be used by user code.
    if (notifier != null) {
      notifier.registerListener(opModeNotifications);
    }
  }

  //------------------------------------------------------------------------------------------------
  // Retrieval
  //------------------------------------------------------------------------------------------------

  /**
   * Retrieves the (first) device with the indicated name which is also an instance of the
   * indicated class or interface. If no such device is found, an exception is thrown. Example:
   *
   * <pre>
   *    DcMotor motorLeft = hardwareMap.get(DcMotor.class, "motorLeft");
   *    ColorSensor colorSensor = hardwareMap.get(ColorSensor.class, "myColorSensor");
   * </pre>
   *
   * If the device has not already been initialized, calling this method will initialize it, which
   * may take some time. As a result, you should ONLY call this method during the Init phase of your
   * OpMode.
   *
   * @param classOrInterface  the class or interface indicating the type of the device object to be retrieved
   * @param deviceName        the name of the device object to be retrieved
   * @return a device with the indicated name which is an instance of the indicated class or interface
   * @see #get(String)
   * @see #getAll(Class)
   * @see com.qualcomm.robotcore.hardware.HardwareMap.DeviceMapping#get(String)
   * @see #tryGet(Class, String)
   */
  public <T> T get(Class<? extends T> classOrInterface, String deviceName) {
    synchronized (lock) {
      deviceName = deviceName.trim();
      T result = tryGet(classOrInterface, deviceName);
      if (result==null) throw new IllegalArgumentException(String.format("Unable to find a hardware device with name \"%s\" and type %s", deviceName, classOrInterface.getSimpleName()));
      return result;
      }
  }

  /**
   * Retrieves the (first) device with the indicated name which is also an instance of the
   * indicated class or interface. If no such device is found, null is returned.
   * <p>
   * This is not commonly used; {@link #get} is the usual method for retrieving items from
   * the map.
   * <p>
   * If the device has not already been initialized, calling this method will initialize it, which
   * may take some time. As a result, you should ONLY call this method during the Init phase of your
   * OpMode.
   *
   * @see #get(Class, String)
   */
  public @Nullable <T> T tryGet(Class<? extends T> classOrInterface, String deviceName) {
    synchronized (lock) {
      deviceName = deviceName.trim();
      List<HardwareDevice> list = allDevicesMap.get(deviceName);
      @Nullable T result = null;

      if (list != null) {
        for (HardwareDevice device : list) {
          if (classOrInterface.isInstance(device)) {
            initializeDeviceIfNecessary(device);
            result = classOrInterface.cast(device);
            break;
          }
        }
      }

      // Show a warning if the user tried to get the BNO055 IMU when a BHI260 IMU is configured on
      // a Control Hub (Expansion Hubs never have a BHI260)
      if (Device.isRevControlHub() && result == null &&
              (classOrInterface.getSimpleName().contains("BNO055") ||
                      classOrInterface.getSimpleName().contains("LynxEmbeddedIMU"))) {
        // Unfortunately, we can't check which IMU is physically present from RobotCore.
        // Instead, we'll just check if the hardware map contains a BHI260 IMU, but no BNO055 IMU.
        boolean foundBno055 = false;
        boolean foundBhi260 = false;
        for (HardwareDevice device : this) {
          String className = device.getClass().getSimpleName();
          if (className.contains("BHI260")) {
            foundBhi260 = true;
          } else if (className.contains("BNO055") || className.equals("LynxEmbeddedIMU")) {
            foundBno055 = true;
          }
        }

        if (foundBhi260 && !foundBno055) {
          RobotLog.addGlobalWarningMessage("You attempted to use the BNO055 interface when " +
                  "only a BHI260AP IMU is configured. This Control Hub contains a BHI260AP IMU, " +
                  "and you need to update your code to use the IMU interface rather than the BNO055 interface.");
        }
      }

      // Show a warning if the user previously retrieved an instance of a different driver for the
      // same physical hardware device
      if (result != null && devicesWithMultipleDriversMap.containsKey(deviceName)) {
        for (DeviceInstancesFromSingleConfigEntry configEntry: devicesWithMultipleDriversMap.get(deviceName)) {
          if (configEntry.warnIfOtherDriverHasBeenRetrieved((HardwareDevice) result, deviceName)) {
            break;
          }
        }
      }

      return result;
    }
  }

  /**
   * (Advanced) Returns the device with the indicated {@link SerialNumber}, if it exists,
   * cast to the indicated class or interface; otherwise, null.
   * <p>
   * If the device has not already been initialized, calling this method will initialize it, which
   * may take some time. As a result, you should ONLY call this method during the Init phase of your
   * OpMode.
   */
  public @Nullable <T> T get(Class<? extends T> classOrInterface, SerialNumber serialNumber) {
    synchronized (lock) {
      HardwareDevice device = serialNumberMap.get(serialNumber);
      if (device != null) {
        if (classOrInterface.isInstance(device)) {
          initializeDeviceIfNecessary(device);
          return classOrInterface.cast(device);
        }
      }
      return null;
    }
  }

  /**
   * Returns the (first) device with the indicated name. If no such device is found, an exception is
   * thrown. If the found device is an I2C device, it will be initialized at this time if it has not
   * already been initialized, which for some devices may take a second or more.
   * <p>
   * Note that the compile-time type of the return value of this method is {@link HardwareDevice},
   * which is usually not what is desired in user code. Thus, the programmer usually casts the
   * return type to the target type that the programmer knows the returned value to be:
   *
   * <pre>
   *    DcMotor motorLeft = (DcMotor)hardwareMap.get("motorLeft");
   *    ColorSensor colorSensor = (ColorSensor)hardwareMap.get("myColorSensor");
   * </pre>
   *
   * If the device has not already been initialized, calling this method will initialize it, which
   * may take some time. As a result, you should ONLY call this method during the Init phase of your
   * OpMode.
   *
   * @param deviceName  the name of the device object to be retrieved
   * @return a device with the indicated name.
   * @see #get(Class, String)
   * @see com.qualcomm.robotcore.hardware.HardwareMap.DeviceMapping#get(String)
   */
  public HardwareDevice get(String deviceName) {
    synchronized (lock) {
      deviceName = deviceName.trim();
      List<HardwareDevice> list = allDevicesMap.get(deviceName);
      if (list != null) {
        for (HardwareDevice device : list) {
          initializeDeviceIfNecessary(device);
          return device;
        }
      }
      throw new IllegalArgumentException(String.format("Unable to find a hardware device with name \"%s\"", deviceName));
    }
  }

  /**
   * Returns all the devices which are instances of the indicated class or interface. Any I2C
   * devices that are found will be initialized at this time if they have not already been
   * initialized, which for some devices may take a second or more.
   * <p>
   * Any matching devices that have not already been initialized wil be initialized now, which may
   * take some time. As a result, you should ONLY call this method during the Init phase of your
   * OpMode.
   *
   * @param classOrInterface the class or interface indicating the type of the device object to be retrieved
   * @return all the devices registered in the map which are instances of classOrInterface
   * @see #get(Class, String)
   */
  public <T> List<T> getAll(Class<? extends T> classOrInterface) {
    synchronized (lock) {
      List<T> result = new LinkedList<T>();
      for (HardwareDevice device : unsafeIterable()) {
        if (classOrInterface.isInstance(device)) {
          initializeDeviceIfNecessary(device);
          result.add(classOrInterface.cast(device));
        }
      }
      return result;
    }
  }


  /**
   * Returns all the names of all the devices which are instances of the indicated class or interface.
   *
   * @param classOrInterface the class or interface indicating the type of the device object to be retrieved
   * @return all the names of all the devices registered in the map which are instances of classOrInterface
   * @see #getAll(Class)
   */
  public SortedSet<String> getAllNames(Class<? extends HardwareDevice> classOrInterface) {
    synchronized (lock) {
      rebuildDeviceNamesIfNecessary();
      SortedSet<String> result = new TreeSet<>();
      for (HardwareDevice device : unsafeIterable()) {
        if (classOrInterface.isInstance(device)) {
          Set<String> names = deviceNames.get(device);
          if (names != null) {
            result.addAll(names);
          }
        }
      }
      return result;
    }
  }

  /**
   * Puts a device in the overall map without having it also reside in a type-specific DeviceMapping.
   * @param deviceName the name by which the device is to be known (case sensitive)
   * @param device     the device to be stored by that name
   */
  public void put(String deviceName, HardwareDevice device) {
    internalPut(null, deviceName, device);
  }

  /**
   * (Advanced) Puts multiple {@link HardwareDevice} instances for the same configuration entry in
   * the overall map without having them also reside in a type-specific DeviceMapping.
   *
   * @param deviceName      the name by which the device is to be known (case sensitive)
   * @param deviceInstances the HardwareDevice instances to be stored by that name
   */
  public void put(String deviceName, List<HardwareDevice> deviceInstances) {
    synchronized (lock) {
      for (HardwareDevice device: deviceInstances) {
        internalPut(null, deviceName, device);
      }

      List<DeviceInstancesFromSingleConfigEntry> configEntries = devicesWithMultipleDriversMap.get(deviceName);
      if (configEntries == null) {
        configEntries = new ArrayList<>(1);
        devicesWithMultipleDriversMap.put(deviceName, configEntries);
      }
      configEntries.add(new DeviceInstancesFromSingleConfigEntry(deviceInstances));
    }
  }

  /**
   * (Advanced) Puts a device in the overall map without having it also reside in a type-specific DeviceMapping.
   *
   * @param serialNumber the {@link SerialNumber} of the device
   * @param deviceName   the name by which the device is to be known (case sensitive)
   * @param device       the device to be stored by that name
   */
  public void put(@NonNull SerialNumber serialNumber, @NonNull String deviceName, HardwareDevice device) {
    Assert.assertNotNull(serialNumber);
    internalPut(serialNumber, deviceName, device);
  }

  protected void internalPut(@Nullable SerialNumber serialNumber, @NonNull String deviceName, HardwareDevice device) {
    synchronized (lock) {
      deviceName = deviceName.trim();
      List<HardwareDevice> list = allDevicesMap.get(deviceName);
      if (list == null) {
        list = new ArrayList<HardwareDevice>(1);
        allDevicesMap.put(deviceName, list);
      }
      if (!list.contains(device)) {
        allDevicesList = null;
        list.add(device);
      }
      if (serialNumber != null) {
        serialNumberMap.put(serialNumber, device);
      }
      rebuildDeviceNamesIfNecessary();
      recordDeviceName(deviceName, device);
    }
  }

  /**
   * (Advanced) Removes a device from the overall map, if present. If the device is also present in a
   * DeviceMapping, then the device should be removed using {@link DeviceMapping#remove}
   * instead of calling this method.
   *
   * <p>This is normally called only by code in the SDK itself, not by user code.</p>
   *
   * @param deviceName  the name of the device to remove
   * @param device      the device to remove under that name
   * @return whether a device was removed or not
   */
  public boolean remove(String deviceName, HardwareDevice device) {
    return remove(null, deviceName, device);
  }

  /**
   * (Advanced) Removes a device from the overall map, if present. If the device is also present in a
   * DeviceMapping, then the device should be removed using {@link DeviceMapping#remove}
   * instead of calling this method.
   *
   * <p>This is normally called only by code in the SDK itself, not by user code.</p>
   *
   * @param serialNumber (optional) the serial number of the device
   * @param deviceName  the name of the device to remove
   * @param device      the device to remove under that name
   * @return whether a device was removed or not
   */
  public boolean remove(@Nullable SerialNumber serialNumber, String deviceName, HardwareDevice device) {
    synchronized (lock) {
      deviceName = deviceName.trim();
      List<HardwareDevice> list = allDevicesMap.get(deviceName);
      if (list != null) {
        list.remove(device);
        if (list.isEmpty()) {
          allDevicesMap.remove(deviceName);
        }
        allDevicesList = null;
        deviceNames = null;
        if (serialNumber != null) {
          serialNumberMap.remove(serialNumber);
        }
        return true;
      }
      return false;
    }
  }

  /**
   * Returns all the names by which the device is known. Virtually always, there is but
   * a single name.
   *
   * @param device the device whose names are desired.
   * @return the set of names by which that device is known
   */
  public @NonNull Set<String> getNamesOf(HardwareDevice device) {
    synchronized (lock) {
      rebuildDeviceNamesIfNecessary();
      Set<String> result = this.deviceNames.get(device);
      if (result==null) {
        result = new HashSet<String>();
      }
      return result;
    }
  }

  protected void recordDeviceName(String deviceName, HardwareDevice device) {
    deviceName = deviceName.trim();
    Set<String> names = this.deviceNames.get(device);
    if (names==null) {
      names = new HashSet<String>();
      this.deviceNames.put(device,names);
    }
    names.add(deviceName);
  }

  protected void rebuildDeviceNamesIfNecessary() {
    if (this.deviceNames == null) {
      this.deviceNames = new ConcurrentHashMap<HardwareDevice, Set<String>>();
      for (Map.Entry<String, List<HardwareDevice>> pair : allDevicesMap.entrySet()) {
        for (HardwareDevice device : pair.getValue()) {
          recordDeviceName(pair.getKey(), device);
        }
      }
    }
  }

  private void buildAllDevicesList() {
    if (allDevicesList == null) {
      Set<HardwareDevice> set = new HashSet<HardwareDevice>();
      for (String key : allDevicesMap.keySet()) {
        set.addAll(allDevicesMap.get(key));
      }
      allDevicesList = new ArrayList<HardwareDevice>(set);
    }
  }

  /**
   * @return the number of unique device objects currently found in this HardwareMap.
   * @see #iterator()
   */
  public int size() {
    synchronized (lock) {
      buildAllDevicesList();
      return allDevicesList.size();
    }
  }

  /**
   * Returns an iterator of all the devices in the HardwareMap. This function will initialize ALL
   * devices in the HardwareMap if they are not initialized already, so try to avoid using it
   * (whether directly or indirectly by treating HardwareMap as an Iterable).
   *
   * @return an iterator of all the devices in the HardwareMap.
   * @see #size()
   */
  @Override
  public @NonNull Iterator<HardwareDevice> iterator() {
    RobotLog.ww(TAG, new RuntimeException(), "HardwareMap iterator was used, which blindly initializes all uninitialized devices");
    synchronized (lock) {
      buildAllDevicesList();
      initializeMultipleDevicesIfNecessary(allDevicesList);
      return new ArrayList<>(allDevicesList).iterator(); // make copy for locking reasons
    }
  }

  /**
   * Returns an Iterable for all the devices in the HardwareMap. This function will NOT ensure that
   * all devices have been initialized, so this is NOT recommended for use by end users.
   *
   * @return an Iterable for all the devices in the HardwareMap.
   */
  public @NonNull Iterable<HardwareDevice> unsafeIterable() {
    return new Iterable<HardwareDevice>() {
      @NonNull @Override public Iterator<HardwareDevice> iterator() {
        synchronized (lock) {
          buildAllDevicesList();
          return new ArrayList<>(allDevicesList).iterator(); // make copy for locking reasons
        }
      }
    };
  }

  /**
   * Call this on every HardwareDevice that is being fetched from the HardwareMap, in case it is a
   * device that is lazily-initialized. As of June 2022, only I2C devices fall in this category, but
   * do not assume that will always be true.
   */
  private void initializeDeviceIfNecessary(HardwareDevice device) {
    if (i2cDriverBaseClass.isAssignableFrom(device.getClass())) {
      i2cDriverBaseClass.cast(device).initializeIfNecessary();
    }
  }

  private void initializeMultipleDevicesIfNecessary(Iterable<? extends HardwareDevice> devices) {
    for (HardwareDevice device: devices) {
      initializeDeviceIfNecessary(device);
    }
  }

  //------------------------------------------------------------------------------------------------
  // Types
  //------------------------------------------------------------------------------------------------

  /**
   * A DeviceMapping contains a subcollection of the devices registered in a {@link HardwareMap}
   * comprised of all the devices of a particular device type.
   * <p>
   * Retrieving devices from a DeviceMapping will initialize them if they have not already been
   * initialized, which may take some time. As a result, you should ONLY access a DeviceMapping from
   * the Init phase of your OpMode.
   *
   * @param <DEVICE_TYPE>
   * @see com.qualcomm.robotcore.hardware.HardwareMap.DeviceMapping#get(String)
   * @see #get(String)
   */
  public class DeviceMapping<DEVICE_TYPE extends HardwareDevice> implements Iterable<DEVICE_TYPE> {
    private final Map <String, DEVICE_TYPE> map = new HashMap<String, DEVICE_TYPE>();
    private final Class<DEVICE_TYPE> deviceTypeClass;

    public DeviceMapping(Class<DEVICE_TYPE> deviceTypeClass) {
      this.deviceTypeClass = deviceTypeClass;
    }

    /** Returns the runtime device type for this mapping */
    public Class<DEVICE_TYPE> getDeviceTypeClass() {
      return this.deviceTypeClass;
    }

    /** A small utility that assists in keeping the Java generics type system happy */
    public DEVICE_TYPE cast(Object obj) {
      return this.deviceTypeClass.cast(obj);
    }

    /**
     * Retrieves the device in this DeviceMapping with the indicated name. If no such device is
     * found, an exception is thrown.
     * <p>
     * If the device has not already been initialized, calling this method will initialize it, which
     * may take some time. As a result, you should ONLY call this method during the Init phase of
     * your OpMode.
     *
     * @param deviceName the name of the device object to be retrieved
     * @return a device with the indicated name
     */
    public DEVICE_TYPE get(String deviceName) {
      synchronized (lock) {
        deviceName = deviceName.trim();
        DEVICE_TYPE device = map.get(deviceName);
        if (device == null) {
          String msg = String.format("Unable to find a hardware device with the name \"%s\"", deviceName);
          throw new IllegalArgumentException(msg);
        }
        initializeDeviceIfNecessary(device);
        return device;
      }
    }

    /**
     * Registers a new device in this DeviceMapping under the indicated name. Any existing device
     * with this name in this DeviceMapping is removed. The new device is also added to the
     * overall collection in the overall map itself. Note that this method is normally called
     * only by code in the SDK itself, not by user code.
     *
     * @param deviceName  the name by which the new device is to be known (case sensitive)
     * @param device      the new device to be named
     * @see HardwareMap#put(String, HardwareDevice)
     */
    public void put(String deviceName, DEVICE_TYPE device) {
      internalPut(null, deviceName, device);
    }

    /**
     * (Advanced) Registers a new device in this DeviceMapping under the indicated name. Any existing device
     * with this name in this DeviceMapping is removed. The new device is also added to the
     * overall collection in the overall map itself. Note that this method is normally called
     * only by code in the SDK itself, not by user code.
     *
     * @param serialNumber the serial number of the device
     * @param deviceName  the name by which the new device is to be known (case sensitive)
     * @param device      the new device to be named
     * @see HardwareMap#put(String, HardwareDevice)
     */
    public void put(@NonNull SerialNumber serialNumber, String deviceName, DEVICE_TYPE device) {
      internalPut(serialNumber, deviceName, device);
    }

    protected void internalPut(@Nullable SerialNumber serialNumber, String deviceName, DEVICE_TYPE device) {
      synchronized (lock) {
        // remove whitespace at start & end
        deviceName = deviceName.trim();

        // Remove any existing device with that name
        remove(serialNumber, deviceName);

        // Remember the new device in the overall list
        HardwareMap.this.internalPut(serialNumber, deviceName, device);

        // Remember the new device here locally, too
        putLocal(deviceName, device);
      }
    }

    public void putLocal(String deviceName, DEVICE_TYPE device) {
      synchronized (lock) {
        deviceName = deviceName.trim();
        map.put(deviceName, device);
      }
    }

    /**
     * Returns whether a device of the indicated name is contained within this mapping
     * @param deviceName the name sought
     * @return whether a device of the indicated name is contained within this mapping
     */
    public boolean contains(String deviceName) {
      synchronized (lock) {
        deviceName = deviceName.trim();
        return map.containsKey(deviceName);
      }
    }

    /**
     * (Advanced) Removes the device with the indicated name (if any) from this DeviceMapping. The device
     * is also removed under that name in the overall map itself. Note that this method is normally
     * called only by code in the SDK itself, not by user code.
     *
     * @param deviceName  the name of the device to remove.
     * @return            whether any modifications were made to this DeviceMapping
     * @see HardwareMap#remove
     */
     public boolean remove(String deviceName) {
        return remove(null, deviceName);
     }
    /**
     * (Advanced) Removes the device with the indicated name (if any) from this DeviceMapping. The device
     * is also removed under that name in the overall map itself. Note that this method is normally
     * called only by code in the SDK itself, not by user code.
     *
     * @param serialNumber (optional) the serial number of the device to remove
     * @param deviceName  the name of the device to remove.
     * @return            whether any modifications were made to this DeviceMapping
     * @see HardwareMap#remove
     */
     public boolean remove(@Nullable SerialNumber serialNumber, String deviceName) {
      synchronized (lock) {
        deviceName = deviceName.trim();
        HardwareDevice device = map.remove(deviceName);
        if (device != null) {
          HardwareMap.this.remove(serialNumber, deviceName, device);
          return true;
        }
        return false;
      }
    }

    /**
     * Returns an iterator over all the devices in this DeviceMapping. This will initialize any
     * un-initialized devices in the DeviceMapping, so you should ONLY call it during the Init phase
     * of your OpMode.
     *
     * @return an iterator over all the devices in this DeviceMapping.
     */
    @Override public @NonNull Iterator<DEVICE_TYPE> iterator() {
      synchronized (lock) {
        initializeMultipleDevicesIfNecessary(map.values());
        return new ArrayList<>(map.values()).iterator();
      }
    }

    /**
     * Returns a collection of all the (name, device) pairs in this DeviceMapping. This will
     * initialize any un-initialized devices in the DeviceMapping, so you should ONLY call it during
     * the Init phase of your OpMode.
     *
     * @return a collection of all the (name, device) pairs in this DeviceMapping.
     */
    public Set<Map.Entry<String, DEVICE_TYPE>> entrySet() {
      synchronized (lock) {
        initializeMultipleDevicesIfNecessary(map.values());
        return new HashSet<>(map.entrySet());
      }
    }

    /**
     * @return the number of devices currently in this DeviceMapping
     */
    public int size() {
      synchronized (lock) {
        return map.size();
      }
    }
  }

  //------------------------------------------------------------------------------------------------
  // Utility
  //------------------------------------------------------------------------------------------------

  private static final String LOG_FORMAT = "%-50s %-30s %s";

  public void logDevices() {
    RobotLog.i("========= Device Information ===================================================");
    RobotLog.i(String.format(LOG_FORMAT, "Type", "Name", "Connection"));

    for (Map.Entry<String, List<HardwareDevice>> entry : allDevicesMap.entrySet()) {
      List<HardwareDevice> list = entry.getValue();
      for (HardwareDevice d : list) {
        String conn = d.getConnectionInfo();
        String name = entry.getKey();
        String type = d.getDeviceName();
        RobotLog.i(String.format(LOG_FORMAT, type, name, conn));
      }
    }
  }

  protected static class DeviceInstanceHolder {
    boolean hasBeenRetrieved = false;
    final HardwareDevice instance;

    protected DeviceInstanceHolder(HardwareDevice instance) {
      this.instance = instance;
    }
  }

  /**
   * ALL device instances in this data structure come from the SAME configuration entry. Each
   * instance was created from a different driver class.
   */
  protected static class DeviceInstancesFromSingleConfigEntry {
    final List<DeviceInstanceHolder> deviceInstanceHolders;

    protected DeviceInstancesFromSingleConfigEntry(List<HardwareDevice> driverInstances) {
      this.deviceInstanceHolders = new ArrayList<>(driverInstances.size());
      for (HardwareDevice driverInstance: driverInstances) {
        deviceInstanceHolders.add(new DeviceInstanceHolder(driverInstance));
      }
    }

    public boolean warnIfOtherDriverHasBeenRetrieved(HardwareDevice hardwareDevice, String name) {
      boolean containsProvidedInstance = false;
      boolean anotherInstanceWasRetrieved = false;
      for (DeviceInstanceHolder instanceHolder: deviceInstanceHolders) {
        if (instanceHolder.instance == hardwareDevice) {
          containsProvidedInstance = true;
          instanceHolder.hasBeenRetrieved = true;
        } else if (instanceHolder.hasBeenRetrieved) {
          anotherInstanceWasRetrieved = true;
        }
      }

      if (containsProvidedInstance && anotherInstanceWasRetrieved) {
        RobotLog.addGlobalWarningMessage(AppUtil.getDefContext().getString(R.string.warningRetrievedMultipleDeviceInstances, name));
        return true;
      } else {
        return false;
      }
    }
  }
}

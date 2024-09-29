/*
 * Copyright (c) 2014, 2015 Qualcomm Technologies Inc
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * (subject to the limitations in the disclaimer below) provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of Qualcomm Technologies Inc nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.qualcomm.hardware;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.hardware.adafruit.AdafruitI2cColorSensor;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxUsbDeviceImpl;
import com.qualcomm.hardware.lynx.commands.core.LynxFirmwareVersionManager;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cIrSeekerSensorV3;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsTouchSensor;
import com.qualcomm.hardware.modernrobotics.comm.ModernRoboticsUsbUtil;
import com.qualcomm.robotcore.eventloop.SyncdDevice;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.AnalogInputController;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorImpl;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DeviceManager;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImplOnSimple;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.PWMOutput;
import com.qualcomm.robotcore.hardware.PWMOutputController;
import com.qualcomm.robotcore.hardware.PWMOutputImpl;
import com.qualcomm.robotcore.hardware.RobotCoreLynxModule;
import com.qualcomm.robotcore.hardware.RobotCoreLynxUsbDevice;
import com.qualcomm.robotcore.hardware.ScannedDevices;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.AnalogSensorConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.DigitalIoDeviceConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.I2cDeviceConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.ServoConfigurationType;
import com.qualcomm.robotcore.hardware.usb.RobotUsbDevice;
import com.qualcomm.robotcore.hardware.usb.RobotUsbDeviceImplBase;
import com.qualcomm.robotcore.hardware.usb.RobotUsbManager;
import com.qualcomm.robotcore.hardware.usb.RobotUsbManagerCombining;
import com.qualcomm.robotcore.hardware.usb.RobotUsbModule;
import com.qualcomm.robotcore.hardware.usb.ftdi.RobotUsbManagerFtdi;
import com.qualcomm.robotcore.hardware.usb.serial.RobotUsbManagerTty;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;
import com.qualcomm.robotcore.util.ThreadPool;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraManager;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.CameraManagerInternal;
import org.firstinspires.ftc.robotcore.internal.hardware.UserNameable;
import org.firstinspires.ftc.robotcore.internal.hardware.usb.ArmableUsbDevice;
import org.firstinspires.ftc.robotcore.internal.usb.EthernetOverUsbSerialNumber;
import org.firstinspires.ftc.robotcore.internal.usb.VendorProductSerialNumber;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Scan for, and create instances of, hardware devices
 */
@SuppressWarnings("WeakerAccess")
public class HardwareDeviceManager implements DeviceManager {

  //------------------------------------------------------------------------------------------------
  // State
  //------------------------------------------------------------------------------------------------

  public static final String TAG = "HardwareDeviceManager";
  public static final String TAG_USB_SCAN = "USBScan";

  public  final static Object     scanDevicesLock = new Object();

  private       RobotUsbManager   usbManager;
  private final SyncdDevice.Manager manager;
  private final Context           context;

  //------------------------------------------------------------------------------------------------
  // Construction
  //------------------------------------------------------------------------------------------------

  /**
   * HardwareDeviceManager constructor
   * @param context Context of current Android app
   * @param manager event loop manager
   */
   @SuppressWarnings("ConstantConditions")
   // TODO(Noah): Remove context parameter
   // TODO(Noah): Evaluate conditions under which manager can safely be null
   public HardwareDeviceManager(Context context, SyncdDevice.Manager manager) {
    this.context = context;
    this.manager = manager;
    this.usbManager = createUsbManager();
  }

  public static RobotUsbManager createUsbManager() {
    RobotUsbManager usbManager = new RobotUsbManagerFtdi();
    if (LynxConstants.isRevControlHub()) {
        RobotUsbManagerCombining combiner = new RobotUsbManagerCombining();
        combiner.addManager(usbManager);
        combiner.addManager(new RobotUsbManagerTty());
        usbManager = combiner;
    }
    return usbManager;
  }

  //------------------------------------------------------------------------------------------------
  // Scanning
  //------------------------------------------------------------------------------------------------

  /**
   * Returns a map from serial number to UsbDeviceType
   */
  @Override
  public ScannedDevices scanForUsbDevices() throws RobotCoreException {
    synchronized (scanDevicesLock) {
      long start = System.nanoTime();
      final ScannedDevices deviceMap = new ScannedDevices();

      // Enquire of the system as to what is actually out there right now
      List<SerialNumber> serialNumbers = usbManager.scanForDevices();
      int devCount = serialNumbers.size();

      RobotLog.vv(TAG_USB_SCAN, "device count=%d", devCount);
      if (devCount > 0) {
        // Open all the USB devices attached to the robot controller. We do this in parallel so as to minimize latency to the user.
        // 2018.06.20: latency less significant now; parallelism might no longer be justified
        ExecutorService executorService = ThreadPool.newFixedThreadPool(devCount, "hw mgr usb scan");
        final ConcurrentHashMap<SerialNumber, RobotUsbDevice> newlyFoundDevices = new ConcurrentHashMap<SerialNumber, RobotUsbDevice>();
        try {
          for (final SerialNumber serialNumber : serialNumbers) {
            executorService.execute(new Runnable() {
              @Override public void run() {
                  try {
                    RobotLog.vv(TAG_USB_SCAN, "opening %s...", serialNumber);

                    // It turns out that ModernRoboticsUsbUtil.openRobotUsbDevice doesn't contain any
                    // logic that is specific to ModernRobotics, but rather is generic, and so
                    // can be used even on Lynx devices (for discovery purposes)
                    RobotUsbDevice device = ModernRoboticsUsbUtil.openRobotUsbDevice(false, usbManager, serialNumber);
                    newlyFoundDevices.put(serialNumber, device);
                    //
                  } catch (Exception e) {
                    RobotLog.vv(TAG_USB_SCAN, "%s(%s) exception while opening %s", e.getClass().getSimpleName(), e.getMessage(), serialNumber);
                  } finally {
                    RobotLog.vv(TAG_USB_SCAN, "... done opening %s", serialNumber);
                  }

                }
              }
            );
          }
          // Wait for all those opens to finish
          executorService.shutdown();
          ThreadPool.awaitTerminationOrExitApplication(executorService, 30, TimeUnit.SECONDS, "USB Scanning Service", "internal error");

          // Having opened everything, determine the type of each
          for (Map.Entry<SerialNumber,RobotUsbDevice> pair : newlyFoundDevices.entrySet()) {
            determineDeviceType(pair.getValue(), pair.getKey(), deviceMap);
          }

          // Also consider devices that are already open: though perhaps / likely enumerated above
          // they probably can't be opened a second time and thus weren't included.
          for (RobotUsbDevice existingDevice : RobotUsbDeviceImplBase.getExtantDevices()) {
            SerialNumber serialNumber = existingDevice.getSerialNumber();
            if (!newlyFoundDevices.containsKey(serialNumber)) {
              UsbDeviceType deviceType = existingDevice.getDeviceType();
              if (deviceType != UsbDeviceType.FTDI_USB_UNKNOWN_DEVICE) {
                RobotLog.vv(TAG_USB_SCAN, "added extant device %s type=%s", serialNumber, deviceType.toString());
                deviceMap.put(serialNumber, deviceType);
              }
            }
          }

        } finally {
          // On the way out, be sure to close all.
          for (Map.Entry<SerialNumber,RobotUsbDevice> pair : newlyFoundDevices.entrySet()) {
            RobotLog.vv(TAG_USB_SCAN, "closing %s", pair.getKey());
            pair.getValue().close();
          }
        }
      }

      // Also include any cameras we might find
      scanForWebcams(deviceMap);

      // Yes, yes I know, these are ethernet interfaces, but they plug into a usb port and there's no
      // other place to really plug into the scan for devices architecture.
      scanForEthernetOverUsbDevices(deviceMap);

      long end = System.nanoTime();
      RobotLog.vv(TAG_USB_SCAN, "scanForUsbDevices() took %dms count=%d", (int)((end-start) / ElapsedTime.MILLIS_IN_NANO), deviceMap.size());
      return deviceMap;
    }
  }

  Integer countVidPid(Map<Pair<Integer,Integer>, Integer> map, VendorProductSerialNumber vendorProduct) {
    Pair<Integer,Integer> pair = new Pair<>(vendorProduct.getVendorId(), vendorProduct.getProductId());
    Integer count = map.get(pair);
    if (count != null) {
      return count;
    }
    return 0;
  }

  void addVidPid(Map<Pair<Integer,Integer>, Integer> map, VendorProductSerialNumber vendorProduct, int delta) {
    int count = countVidPid(map, vendorProduct);
    Pair<Integer,Integer> pair = new Pair<>(vendorProduct.getVendorId(), vendorProduct.getProductId());
    map.put(pair, count + delta);
  }

  protected void scanForEthernetOverUsbDevices(ScannedDevices scannedDevices) {
    synchronized (scanDevicesLock) {
      try {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
          NetworkInterface networkInterface = interfaces.nextElement();
          // Filter out loopback and wireless interfaces
          if (networkInterface.getName().startsWith("eth")) {
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
              InetAddress address = addresses.nextElement();
              if (address.getAddress().length == 4) {
                RobotLog.dd(TAG, "IP Address: " + address.getHostAddress());
                scannedDevices.put(EthernetOverUsbSerialNumber.fromIpAddress(address.getHostAddress(), networkInterface.getName()), UsbDeviceType.ETHERNET_DEVICE);
              }
            }
          }
        }
      } catch (SocketException e) {
        RobotLog.e(TAG, "Error getting network interfaces", e);
      }
    }
  }

  protected void scanForWebcams(ScannedDevices scannedDevices) {
    synchronized (scanDevicesLock) {
      CameraManager cameraManager = ClassFactory.getInstance().getCameraManager();
      List<WebcamName> webcams = cameraManager.getAllWebcams();

      /** Inventory our webcams */
      Map<Pair<Integer,Integer>, Integer> vidpidConnections = new HashMap<>();
      Map<Pair<Integer,Integer>, Integer> vidpidConnectionless = new HashMap<>();
      for (WebcamName webcamName : webcams) {
        SerialNumber serialNumber = webcamName.getSerialNumber();
        if (serialNumber.isVendorProduct()) {
          VendorProductSerialNumber vendorProduct = (VendorProductSerialNumber)serialNumber;
          if (TextUtils.isEmpty(vendorProduct.getConnectionPath())) {
            addVidPid(vidpidConnectionless, vendorProduct, 1);
          } else {
            addVidPid(vidpidConnections, vendorProduct, 1);
          }
        }
      }

      /** Do we have any unique {@link VendorProductSerialNumber}s? For each such unique guys, forget about the connection path.
       * Instead, use a variation that will serve as a 'wildcard' matching the (vid,pid) pair on *any* connection. */
      for (WebcamName webcamName : webcams) {
        SerialNumber serialNumber = webcamName.getSerialNumber();
        if (serialNumber.isVendorProduct()) {
          VendorProductSerialNumber vendorProduct = (VendorProductSerialNumber)serialNumber;

          // If there's more than one connectionless with the same vid/pid, we don't know which is which, so omit
          // This will only happen on Nougat and beyond, where we can't read sysfs.
          int count = countVidPid(vidpidConnectionless, vendorProduct);
          if (count > 1) {
            RobotLog.ee(TAG, "%d serialnumless webcams w/o connection info; ignoring", count, vendorProduct);
            continue;
          }

          if (countVidPid(vidpidConnectionless, vendorProduct) == 0 && countVidPid(vidpidConnections, vendorProduct) == 1) {
            // We don't need the connection to keep track of this guy, as he's unambignous. So let him move around.
            serialNumber = SerialNumber.fromVidPid(vendorProduct.getVendorId(), vendorProduct.getProductId(), "");
          }
        }
        RobotLog.vv(TAG, "scanned webcam serial=%s", serialNumber);
        scannedDevices.put(serialNumber, UsbDeviceType.WEBCAM);
      }
    }
  }

  void determineDeviceType(RobotUsbDevice dev, SerialNumber serialNumber, ScannedDevices deviceMap) {

    // Have we seen this guy before? A given serial number won't change its device type, so
    // we can use use a cached value if we have it
    UsbDeviceType usbDeviceType = RobotUsbDeviceImplBase.getDeviceType(serialNumber);

    // If not, then open the indicated device by serial number in order to determine its device type
    if (usbDeviceType == UsbDeviceType.UNKNOWN_DEVICE) {
      RobotUsbDevice.USBIdentifiers ids = dev.getUsbIdentifiers();
      if (ids.isLynxDevice()) {
        RobotLog.vv(TAG_USB_SCAN, "%s is a lynx device", serialNumber);
        usbDeviceType = getLynxDeviceType(dev);
      } else {
        // we can't figure this guy out; ignore
        return;
      }
    }

    // Record the type in the device map
    deviceMap.put(serialNumber, usbDeviceType);
  }

  UsbDeviceType getLynxDeviceType(RobotUsbDevice dev) {
    UsbDeviceType usbDeviceType = UsbDeviceType.LYNX_USB_DEVICE;
    dev.setDeviceType(usbDeviceType);
    return usbDeviceType;
  }

  //------------------------------------------------------------------------------------------------
  // Creation
  //------------------------------------------------------------------------------------------------

  /**
   * Note: unlike other creation methods, creating a Lynx USB device will succeed even if
   * the device is already open (in which case it will return a new delegate to the existing
   * instance).
   */
  @Override public RobotCoreLynxUsbDevice createLynxUsbDevice(final SerialNumber serialNumber, @Nullable String name) throws RobotCoreException, InterruptedException {
    HardwareFactory.noteSerialNumberType(context, serialNumber, context.getString(R.string.moduleDisplayNameLynxUsbDevice));
    RobotLog.v("Creating %s", HardwareFactory.getDeviceDisplayName(context, serialNumber));

    return LynxUsbDeviceImpl.findOrCreateAndArm(context, serialNumber, manager, usbManager);
  }


  @Override
  public DcMotor createDcMotor(DcMotorController controller, int portNumber, @NonNull MotorConfigurationType motorType, String name) {
    return new DcMotorImpl(controller, portNumber, DcMotor.Direction.FORWARD, motorType);
  }
  @Override
  public DcMotor createDcMotorEx(DcMotorController controller, int portNumber, @NonNull MotorConfigurationType motorType, String name) {
    return new DcMotorImplEx(controller, portNumber, DcMotor.Direction.FORWARD, motorType);
  }

  @Override
  public Servo createServoEx(ServoControllerEx controller, int portNumber, String name, ServoConfigurationType servoType) {
    return new ServoImplEx(controller, portNumber, Servo.Direction.FORWARD, servoType);
  }

  @Override
  public CRServo createCRServoEx(ServoControllerEx controller, int portNumber, String name, ServoConfigurationType servoType) {
    return new CRServoImplEx(controller, portNumber, DcMotor.Direction.FORWARD, servoType);
  }

  @Override
  public List<HardwareDevice> createCustomServoDeviceInstances(ServoControllerEx controller, int portNumber, ServoConfigurationType servoConfigurationType) {
    return servoConfigurationType.createInstances(controller, portNumber);
  }

  @Override
  public @Nullable WebcamName createWebcamName(final SerialNumber serialNumber, String name) throws RobotCoreException, InterruptedException {
    HardwareFactory.noteSerialNumberType(context, serialNumber, context.getString(R.string.moduleDisplayNameWebcam));
    RobotLog.v("Creating %s", HardwareFactory.getDeviceDisplayName(context, serialNumber));

    ArmableUsbDevice.OpenRobotUsbDevice openWebcam = new ArmableUsbDevice.OpenRobotUsbDevice() {
      @Nullable @Override public RobotUsbDevice open() throws RobotCoreException {
        CameraManagerInternal cameraManager = (CameraManagerInternal) ClassFactory.getInstance().getCameraManager();
        if (cameraManager.isWebcamAttached(serialNumber)) {
        } else {
          RobotLog.logAndThrow("Unable to find webcam with serial number " + serialNumber);
        }
        return null; // we don't need an actual RobotUsbDevice, just needed to do the attachment check.
      }
    };
    CameraManagerInternal cameraManager = (CameraManagerInternal) ClassFactory.getInstance().getCameraManager();
    WebcamName webcamName = cameraManager.webcamNameFromSerialNumber(serialNumber, openWebcam, manager);
    if (webcamName instanceof UserNameable) {
      ((UserNameable)webcamName).setUserName(name);
    }
    ((RobotUsbModule)webcamName).armOrPretend();  // Force it to check for presence on USB
    return webcamName;
  }

  /* (non-Javadoc)
   * @see com.qualcomm.hardware.DeviceManager#createDigitalTouchSensor(com.qualcomm.robotcore.hardware.LegacyModule, int)
   */
  @Override
  public TouchSensor createMRDigitalTouchSensor(DigitalChannelController digitalChannelController, int physicalPort, String name) {
    RobotLog.v("Creating Modern Robotics digital Touch Sensor - Port: " + physicalPort);
    return new ModernRoboticsTouchSensor(digitalChannelController, physicalPort);
  }

  @Override
  public IrSeekerSensor createMRI2cIrSeekerSensorV3(RobotCoreLynxModule lynxModule, DeviceConfiguration.I2cChannel channel, String name) {
    RobotLog.v("Creating Modern Robotics I2C IR Seeker Sensor V3 - mod=%d bus=%d", lynxModule.getModuleAddress(), channel.channel);
    return new ModernRoboticsI2cIrSeekerSensorV3(createI2cDeviceSynch(lynxModule, channel, name), true);
  }

  @Override
  public List<HardwareDevice> createAnalogSensorInstances(AnalogInputController controller, int channel, AnalogSensorConfigurationType type) {
    RobotLog.v("Creating Analog Sensor - Type: " + type.getName() + " - Port: " + channel);
    return type.createInstances(controller, channel);
  }

  @Override
  public List<HardwareDevice> createDigitalDeviceInstances(DigitalChannelController controller, int channel, DigitalIoDeviceConfigurationType type) {
    RobotLog.v("Creating Digital Channel Device - Type: " + type.getName() + " - Port: " + channel);
    return type.createInstances(controller, channel);
  }

  @Override
  public PWMOutput createPwmOutputDevice(PWMOutputController controller, int channel, String name) {
    RobotLog.v("Creating PWM Output Device - Port: " + channel);
    return new PWMOutputImpl(controller, channel);
  }

  @Override
  public List<HardwareDevice> createI2cDeviceInstances(final RobotCoreLynxModule lynxModule, final DeviceConfiguration.I2cChannel bus, final I2cDeviceConfigurationType type, final String name) {
    RobotLog.v("Creating user sensor %s - on Lynx module=%d bus=%d", type.getName(), lynxModule.getModuleAddress(), bus.channel);
    return type.createInstances(
            () -> createI2cDeviceSynchSimple(lynxModule, bus, name));
  }

  @Override
  public HardwareDevice createLimelight3A(SerialNumber serialNumber, @Nullable String name, InetAddress ipAddress) {
    RobotLog.v("Creating Limelight3A named %s at %s", name, serialNumber.getString());
    return new Limelight3A(serialNumber, name, ipAddress);
  }

  @Override
  public ColorSensor createAdafruitI2cColorSensor(RobotCoreLynxModule lynxModule, DeviceConfiguration.I2cChannel channel, String name) {
    RobotLog.v("Creating Adafruit Color Sensor (Lynx) - mod=%d bus=%d", lynxModule.getModuleAddress(), channel.channel);
    return new AdafruitI2cColorSensor(createI2cDeviceSynchSimple(lynxModule, channel, name), true);
  }

  @Override
  public ColorSensor createLynxColorRangeSensor(RobotCoreLynxModule lynxModule, DeviceConfiguration.I2cChannel channel, String name) {
    RobotLog.v("Creating Lynx Color/Range Sensor - mod=%d bus=%d", lynxModule.getModuleAddress(), channel.channel);
    return new LynxI2cColorRangeSensor(createI2cDeviceSynchSimple(lynxModule, channel, name), true);
  }

  @Override
  public ColorSensor createModernRoboticsI2cColorSensor(RobotCoreLynxModule lynxModule, DeviceConfiguration.I2cChannel channel, String name) {
    RobotLog.v("Creating Modern Robotics I2C Color Sensor - mod=%d bus=%d", lynxModule.getModuleAddress(), channel.channel);
    return new ModernRoboticsI2cColorSensor(createI2cDeviceSynch(lynxModule, channel, name), true);
  }

  @Override
  public GyroSensor createModernRoboticsI2cGyroSensor(RobotCoreLynxModule lynxModule, DeviceConfiguration.I2cChannel channel, String name) {
    RobotLog.v("Creating Modern Robotics I2C Gyro Sensor - mod=%d bus=%d", lynxModule.getModuleAddress(), channel.channel);
    return new ModernRoboticsI2cGyro(createI2cDeviceSynch(lynxModule, channel, name), true);
  }

  @Override
  public LED createLED(DigitalChannelController controller, int channel, String name) {
    RobotLog.v("Creating LED - Port: " + channel);
    return new LED(controller, channel);
  }

  @Override
  public I2cDeviceSynch createI2cDeviceSynch(RobotCoreLynxModule lynxModule, DeviceConfiguration.I2cChannel channel, String name) {
    RobotLog.v("Creating I2cDeviceSynch (Lynx) - mod=%d bus=%d", lynxModule.getModuleAddress(), channel.channel);
    I2cDeviceSynchSimple i2cDeviceSynchSimple = createI2cDeviceSynchSimple(lynxModule, channel, name);
    I2cDeviceSynch result = new I2cDeviceSynchImplOnSimple(i2cDeviceSynchSimple, true);
    return result;
  }

  //------------------------------------------------------------------------------------------------
  // Utility
  //------------------------------------------------------------------------------------------------

  protected I2cDeviceSynchSimple createI2cDeviceSynchSimple(RobotCoreLynxModule lynxModule, DeviceConfiguration.I2cChannel channel, String name) {
    I2cDeviceSynchSimple result = LynxFirmwareVersionManager.createLynxI2cDeviceSynch(context, (LynxModule)lynxModule, channel.channel);
    result.setUserConfiguredName(name);
    return result;
  }

  private RobotUsbDevice.FirmwareVersion getModernRoboticsFirmwareVersion(byte[] modernRoboticsDeviceHeader) {
    return new RobotUsbDevice.FirmwareVersion(modernRoboticsDeviceHeader[0]);
  }
}

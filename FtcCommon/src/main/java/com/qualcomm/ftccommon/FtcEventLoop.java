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

/*
 * Copyright (c) 2016 Molly Nicholas
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
 * Neither the name of Molly Nicholas nor the names of its contributors may be used to
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

package com.qualcomm.ftccommon;

import android.app.Activity;
import android.hardware.usb.UsbDevice;

import com.qualcomm.ftccommon.configuration.RobotConfigFile;
import com.qualcomm.ftccommon.configuration.RobotConfigFileManager;
import com.qualcomm.ftccommon.configuration.RobotConfigMap;
import com.qualcomm.ftccommon.configuration.USBScanManager;
import com.qualcomm.hardware.HardwareFactory;
import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.lynx.LynxI2cDeviceSynch;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.LynxModuleWarningManager;
import com.qualcomm.hardware.lynx.LynxUsbDevice;
import com.qualcomm.hardware.lynx.commands.core.LynxFirmwareVersionManager;
import com.qualcomm.robotcore.eventloop.EventLoopManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.EmbeddedControlHubModule;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cWarningManager;
import com.qualcomm.robotcore.hardware.LynxModuleImuType;
import com.qualcomm.robotcore.hardware.configuration.ConfigurationTypeManager;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.hardware.configuration.ReadXMLFileHandler;
import com.qualcomm.robotcore.hardware.configuration.Utility;
import com.qualcomm.robotcore.hardware.usb.RobotArmingStateNotifier;
import com.qualcomm.robotcore.hardware.usb.RobotUsbModule;
import com.qualcomm.robotcore.robocol.Command;
import com.qualcomm.robotcore.robocol.TelemetryMessage;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.ManualControlOpMode;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.internal.camera.CameraManagerInternal;
import org.firstinspires.ftc.robotcore.internal.ftdi.FtDevice;
import org.firstinspires.ftc.robotcore.internal.ftdi.FtDeviceManager;
import org.firstinspires.ftc.robotcore.internal.hardware.CachedLynxModulesInfo;
import org.firstinspires.ftc.robotcore.internal.network.CallbackResult;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Main event loop that controls the robot
 */
@SuppressWarnings("WeakerAccess")
public class FtcEventLoop extends FtcEventLoopBase {

  //------------------------------------------------------------------------------------------------
  // State
  //------------------------------------------------------------------------------------------------

  private static volatile boolean hasInitBeenCalled = false;

  protected final  Utility                 utility;
  protected final  OpModeManagerImpl       opModeManager;
  protected UsbModuleAttachmentHandler     usbModuleAttachmentHandler;
  protected final  Map<String,Long>        recentlyAttachedUsbDevices;  // serialNumber -> when to attach
  protected final  AtomicReference<OpMode> opModeStopRequested;

  //------------------------------------------------------------------------------------------------
  // Construction
  //------------------------------------------------------------------------------------------------

  public FtcEventLoop(HardwareFactory hardwareFactory, OpModeRegister userOpmodeRegister, UpdateUI.Callback callback, Activity activityContext) {
    super(hardwareFactory, userOpmodeRegister, callback, activityContext);
    this.opModeManager              = createOpModeManager(activityContext);
    this.usbModuleAttachmentHandler = new DefaultUsbModuleAttachmentHandler();
    this.recentlyAttachedUsbDevices = new ConcurrentHashMap<String,Long>();
    this.opModeStopRequested        = new AtomicReference<OpMode>();
    this.utility                    = new Utility(activityContext);
  }

  protected static OpModeManagerImpl createOpModeManager(Activity activityContext) {
    // The HardwareMap created here is just a placeholder and won't be exposed to OpModes, so it's
    // OK to pass in null for the notifier parameter.
    return new OpModeManagerImpl(activityContext, new HardwareMap(activityContext, null));
  }

  //------------------------------------------------------------------------------------------------
  // Accessors
  //------------------------------------------------------------------------------------------------

  public OpModeManagerImpl getOpModeManager() {
    return opModeManager;
  }

  public UsbModuleAttachmentHandler getUsbModuleAttachmentHandler() {
    return this.usbModuleAttachmentHandler;
  }

  public void setUsbModuleAttachmentHandler(UsbModuleAttachmentHandler handler) {
    this.usbModuleAttachmentHandler = handler;
  }

  //------------------------------------------------------------------------------------------------
  // Core Event Loop
  //------------------------------------------------------------------------------------------------

  /**
   * Init method
   * <p>
   * This code will run when the robot first starts up. Place any initialization code in this
   * method.
   * <p>
   * It is important to save a copy of the event loop manager from this method, as that is how
   * you'll get access to the gamepad.
   * <p>
   * If an Exception is thrown then the event loop manager will not start the robot.
   * <p>
   * Caller synchronizes: called on RobotSetupRunnable.run() thread
   */
  @Override
  public void init(EventLoopManager eventLoopManager) throws RobotCoreException, InterruptedException {
    boolean appJustLaunched = !hasInitBeenCalled;
    hasInitBeenCalled = true;

    RobotLog.ii(TAG, "======= INIT START =======");
    super.init(eventLoopManager);
    opModeManager.init(eventLoopManager);
    registeredOpModes.registerAllOpModes(userOpmodeRegister);
    sendActiveConfig();
    ConfigurationTypeManager.getInstance().sendUserDeviceTypes();
    sendOpModeList();
    ManualControlOpMode.setEventLoopManager(eventLoopManager);

    ftcEventLoopHandler.init(eventLoopManager);

    LynxUsbDevice temporaryEmbeddedLynxUsb = null;
    try { // Block that uses temporaryEmbeddedLynxUsb, which will be closed in the associated finally block
      if (LynxConstants.isRevControlHub()) {
        // Block that attempts to access the Control Hub's embedded module. If communication with
        // such is failing, we want to catch the exception locally. That error condition is not bad
        // enough to warrant not running the event loop (and thereby triggering the Control Hub OS watchdog).
        try {
          temporaryEmbeddedLynxUsb = ensureEmbeddedControlHubModuleIsSetUp();
          final int chAddress = LynxConstants.CH_EMBEDDED_MODULE_ADDRESS;

          // Mark the IMU type of the Control Hub for use with configurations that are missing
          // the embedded <LynxModule> entry.
          final AtomicReference<LynxModuleImuType> imuTypeRef = new AtomicReference<>();
          temporaryEmbeddedLynxUsb.performSystemOperationOnParentModule(chAddress, module -> {
              LynxModuleImuType imuType = module.getImuType();
              imuTypeRef.set(imuType);
              EmbeddedControlHubModule.setImuType(imuType);
          }, 200, TimeUnit.MILLISECONDS);

          if (appJustLaunched && imuTypeRef.get() == LynxModuleImuType.BHI260) {
            try {
              // Flash the BHI260 IMU firmware if necessary
              temporaryEmbeddedLynxUsb.performSystemOperationOnParentModule(chAddress, module -> {
                LynxI2cDeviceSynch tempImuI2cClient = LynxFirmwareVersionManager.createLynxI2cDeviceSynch(AppUtil.getDefContext(), module, 0);
                try { BHI260IMU.flashFirmwareIfNecessary(tempImuI2cClient); }
                finally { tempImuI2cClient.close(); }
              }, 60, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
              RobotLog.ee(TAG, e, "Timeout expired while flashing BHI260AP IMU firmware");
            }
          }
        } catch (RobotCoreException | TimeoutException e) {
          RobotLog.ee(TAG, e, "Error communicating with internal Control Hub LynxModule");
        }
      }

      HardwareMap hardwareMap = ftcEventLoopHandler.getHardwareMap(opModeManager);

      opModeManager.setHardwareMap(hardwareMap);
      hardwareMap.logDevices();
      CachedLynxModulesInfo.setLynxModulesInfo(compileLynxModulesInfo(hardwareMap));
      LynxModuleWarningManager.getInstance().init(opModeManager, hardwareMap);
      I2cWarningManager.clearI2cWarnings();
    } finally {
      if (temporaryEmbeddedLynxUsb != null) {
        // For performance, we wait until now to close this, so that another delegate will be created before we close this one.
        temporaryEmbeddedLynxUsb.close();
      }
    }

    RobotLog.ii(TAG, "======= INIT FINISH =======");
  }

  /**
   * Loop method, this will be called repeatedly while the robot is running.
   * <p>
   * @see com.qualcomm.robotcore.eventloop.EventLoop#loop()
   * <p>
   * Caller synchronizes: called on EventLoopRunnable.run() thread.
   */
  @Override
  public void loop() {
    super.loop();
    // Atomically capture the OpMode to stop, if any
    OpMode opModeToStop = opModeStopRequested.getAndSet(null);
    if (opModeToStop != null) {
      processOpModeStopRequest(opModeToStop);
    }

    checkForChangedOpModes();

    ftcEventLoopHandler.displayGamePadInfo(opModeManager.getActiveOpModeName());
    Gamepad[] opModeGamepads = ftcEventLoopHandler.getOpModeGamepads();
    Gamepad latestGamepad1Data = ftcEventLoopHandler.getLatestGamepad1Data();
    Gamepad latestGamepad2Data = ftcEventLoopHandler.getLatestGamepad2Data();
    ftcEventLoopHandler.gamepadEffects();
    opModeManager.runActiveOpMode(opModeGamepads, latestGamepad1Data, latestGamepad2Data);
  }

  @Override
  public void refreshUserTelemetry(TelemetryMessage telemetry, double sInterval) {
    ftcEventLoopHandler.refreshUserTelemetry(telemetry, sInterval);
  }

  /**
   * Teardown method
   * <p>
   * This method will be called when the robot is being shut down. This method should stop the robot. There will be no more changes to write
   * to the hardware after this method is called.
   * <p>
   * If an exception is thrown, then the event loop manager will attempt to shut down the robot
   * without the benefit of this method.
   * <p>
   * @see com.qualcomm.robotcore.eventloop.EventLoop#teardown()
   * <p>
   * Caller synchronizes: called on EventLoopRunnable.run() thread.
   */
  @Override
  public void teardown() throws RobotCoreException, InterruptedException {
    RobotLog.ii(TAG, "======= TEARDOWN =======");

    // Stopping the active OpMode will send failsafe commands to all REV Hubs. Do this
    // BEFORE calling super.teardown(), as that will close the REV Hubs.
    opModeManager.stopActiveOpMode();
    opModeManager.teardown();

    super.teardown();

    RobotLog.ii(TAG, "======= TEARDOWN COMPLETE =======");
  }

  /**
   * If the driver station sends over a command, it will be routed to this method. You can choose
   * what to do with this command, or you can just ignore it completely.
   * <p>
   * Called on RecvRunnable.run() thread. Method is thread safe, non-interfering
   */
  @Override
  public CallbackResult processCommand(Command command) throws InterruptedException, RobotCoreException {
    ftcEventLoopHandler.sendBatteryInfo();

    CallbackResult result = super.processCommand(command);
    if (!result.stopDispatch()) {
      CallbackResult localResult = CallbackResult.HANDLED;

      String name = command.getName();
      String extra = command.getExtra();

      if (name.equals(CommandList.CMD_INIT_OP_MODE)) {
        handleCommandInitOpMode(extra);
      } else if (name.equals(CommandList.CMD_RUN_OP_MODE)) {
        handleCommandRunOpMode(extra);
      } else if (name.equals(CommandList.CMD_SET_MATCH_NUMBER)) {
        handleCommandSetMatchNumber(extra);
      } else {
        localResult = CallbackResult.NOT_HANDLED;
      }
      if (localResult==CallbackResult.HANDLED) {
        result = localResult;
      }
    }
    return result;
  }

  /**
   * The driver station is requesting our OpMode list/ UI state. Build up an appropriately-delimited
   * list and send it back to them. Also take this opportunity to forcibly refresh their error/warning
   * state: the OpMode list is only infrequently requested (so sending another datagram isn't
   * a traffic burden) and it's requested just after a driver station reconnects after a disconnect
   * (so doing the refresh now is probably an opportune thing to do).
   */
  protected void sendOpModeList() {
    super.sendOpModeList();

    EventLoopManager manager = ftcEventLoopHandler.getEventLoopManager();
    if (manager != null) manager.refreshSystemTelemetryNow(); // null check is paranoia, need isn't verified
  }

  /*
   * handleCommandSetMatchNumber
   *
   * Cache the match number in the opMode manager.
   */
  protected void handleCommandSetMatchNumber(String extra) {
    try {
      opModeManager.setMatchNumber(Integer.parseInt(extra));
    } catch (NumberFormatException e) {
      RobotLog.logStackTrace(e);
    }
  }

  protected void handleCommandInitOpMode(String extra) {
    String newOpMode = ftcEventLoopHandler.getOpMode(extra);
    opModeManager.initOpMode(newOpMode);
  }

  protected void handleCommandRunOpMode(String extra) {
    // Make sure we're in the OpMode that the DS thinks we are
    String newOpMode = ftcEventLoopHandler.getOpMode(extra);
    if (!opModeManager.getActiveOpModeName().equals(newOpMode)) {
      opModeManager.initOpMode(newOpMode);
    }
    opModeManager.startActiveOpMode();
  }

  @Override
  public void requestOpModeStop(OpMode opModeToStopIfActive) {
    // Note that this may be called from any thread, including an OpMode's loop() thread.
    opModeStopRequested.set(opModeToStopIfActive);
  }

  private void processOpModeStopRequest(OpMode opModeToStop) {
    // Called on the event loop thread. If the currently active OpMode is the one indicated,
    // we are to stop it and set up the default.

    if (opModeToStop != null && this.opModeManager.getActiveOpMode() == opModeToStop) {

      RobotLog.ii(TAG, "auto-stopping OpMode '%s'", this.opModeManager.getActiveOpModeName());

      // Call stop on the currently active OpMode and init the default one
      this.opModeManager.stopActiveOpMode();
    }
  }


  //------------------------------------------------------------------------------------------------
  // Usb connection management
  //------------------------------------------------------------------------------------------------

  /**
   * Deal with the fact that a UsbDevice has recently attached to the system
   * @param usbDevice
   */
  @Override public void onUsbDeviceAttached(UsbDevice usbDevice) {
    // Find out who it is.
    SerialNumber serialNumber = getSerialNumberOfUsbDevice(usbDevice);

    // Remember whoever it was for later
    if (serialNumber != null) {
      pendUsbDeviceAttachment(serialNumber, 0, TimeUnit.MILLISECONDS);
    }
    else {
      // We don't actually understand under what conditions we'll be unable to open an
      // FT_Device for which we're actually receiving a change notification: who else
      // would have it open (for example)? We'd like to do something more, but don't
      // have an idea of what that would look like.
      //
      // 2018.06.01: It is suspected that the serial number being returned as null was
      // a consequence of a race between USB attachment notifications here and in FtDeviceManager.
      RobotLog.ee(TAG, "ignoring: unable get serial number of attached UsbDevice vendor=0x%04x, product=0x%04x device=0x%04x name=%s",
              usbDevice.getVendorId(), usbDevice.getProductId(), usbDevice.getDeviceId(), usbDevice.getDeviceName());
    }
  }

  protected SerialNumber getSerialNumberOfUsbDevice(UsbDevice usbDevice) {
    SerialNumber serialNumber = null;

    serialNumber = SerialNumber.fromStringOrNull(usbDevice.getSerialNumber());

    if (serialNumber==null) {
      // Don't need this branch any more, but left in for now to preserve code paths. Remove after further testing.
      FtDevice ftDevice = null;
      try {
        FtDeviceManager manager = FtDeviceManager.getInstance(); // note: we're not supposed to close this
        ftDevice = manager.openByUsbDevice(usbDevice);
        if (ftDevice != null) {
          serialNumber = SerialNumber.fromStringOrNull(ftDevice.getDeviceInfo().serialNumber);
        }
      } catch (RuntimeException e) {  // paranoia
        // ignored
      } finally {
        if (ftDevice != null)
          ftDevice.close();
      }
    }

    if (serialNumber==null) { // devices that simply lack a serial number
      try {
        CameraManagerInternal cameraManagerInternal = (CameraManagerInternal) ClassFactory.getInstance().getCameraManager();
        serialNumber = cameraManagerInternal.getRealOrVendorProductSerialNumber(usbDevice);
      } catch (RuntimeException e) {
        // ignore
      }
    }

    return serialNumber;
  }

  @Override public void pendUsbDeviceAttachment(SerialNumber serialNumber, long time, TimeUnit unit) {
    long nsDeadline = time==0L ? 0L : System.nanoTime() + unit.toNanos(time);
    this.recentlyAttachedUsbDevices.put(serialNumber.getString(), nsDeadline);
  }

  /**
   * Process any usb devices that might have recently attached.
   * Called on the event loop thread.
   */
  @Override public void processedRecentlyAttachedUsbDevices() throws RobotCoreException, InterruptedException {

    // Snarf a copy of the set of serial numbers
    Set<String> serialNumbersToProcess = new HashSet<String>();
    long now = System.nanoTime();

    for (Map.Entry<String,Long> pair : this.recentlyAttachedUsbDevices.entrySet()) {
      if (pair.getValue() <= now) {
        serialNumbersToProcess.add(pair.getKey());
        this.recentlyAttachedUsbDevices.remove(pair.getKey());
      }
    }

    // If we have a handler, and there's something to handle, then handle it
    if (this.usbModuleAttachmentHandler != null && !serialNumbersToProcess.isEmpty()) {

      // Find all the UsbModules in the current hardware map
      List<RobotUsbModule> modules = this.ftcEventLoopHandler.getHardwareMap(opModeManager).getAll(RobotUsbModule.class);

      // For each serial number, find the module with that serial number and ask the handler to deal with it
      for (String serialNumberString : new ArrayList<>(serialNumbersToProcess)) {
        SerialNumber serialNumberAttached = SerialNumber.fromString(serialNumberString);
        boolean found = false;
        for (RobotUsbModule module : modules) {
          if (serialNumberAttached.matches(module.getSerialNumber())) {
            if (module.getArmingState() != RobotArmingStateNotifier.ARMINGSTATE.ARMED) {
              serialNumbersToProcess.remove(serialNumberString);
              handleUsbModuleAttach(module);
              found = true;
              break;
            }
          }
        }
        if (!found) {
          RobotLog.vv(TAG, "processedRecentlyAttachedUsbDevices(): %s not in hwmap; ignoring", serialNumberAttached);
        }
      }
    }
  }

  @Override
  public void handleUsbModuleDetach(RobotUsbModule module) throws RobotCoreException, InterruptedException {
  // Called on the event loop thread
    UsbModuleAttachmentHandler handler = this.usbModuleAttachmentHandler;
    if (handler != null)
      handler.handleUsbModuleDetach(module);
  }

  public void handleUsbModuleAttach(RobotUsbModule module) throws RobotCoreException, InterruptedException {
  // Called on the event loop thread
    UsbModuleAttachmentHandler handler = this.usbModuleAttachmentHandler;
    if (handler != null)
      handler.handleUsbModuleAttach(module);
  }

  /**
   * The caller of this method MUST close the returned LynxUsbDevice (if one is returned).
   *
   * We return it instead of closing it ourselves to avoid performing the expensive arming process
   * more than necessary.
   */
  private LynxUsbDevice ensureEmbeddedControlHubModuleIsSetUp() throws RobotCoreException, InterruptedException {
    RobotLog.vv(TAG, "Ensuring that the embedded Control Hub module is set up correctly");
    LynxUsbDevice embeddedLynxUsb = (LynxUsbDevice) USBScanManager.getInstance().getDeviceManager().createLynxUsbDevice(SerialNumber.createEmbedded(), null);
    boolean justChangedControlHubAddress = embeddedLynxUsb.setupControlHubEmbeddedModule();
    if (justChangedControlHubAddress) {
      updateEditableConfigFilesWithNewControlHubAddress();

      // Since we just changed the embedded module's address, we need to power-cycle and re-initialize our communications with it
      embeddedLynxUsb.close();
      embeddedLynxUsb = (LynxUsbDevice) USBScanManager.getInstance().getDeviceManager().createLynxUsbDevice(SerialNumber.createEmbedded(), null);
    }
    return embeddedLynxUsb;
  }

  private void updateEditableConfigFilesWithNewControlHubAddress() throws RobotCoreException {
    // Save the new embedded address to all editable config files. This primarily helps with backwards-compatibility.
    RobotLog.vv(TAG, "We just auto-changed the Control Hub's address. Now auto-updating configuration files.");
    ReadXMLFileHandler xmlReader = new ReadXMLFileHandler(USBScanManager.getInstance().getDeviceManager());
    RobotConfigFileManager configFileManager = new RobotConfigFileManager();
    for (RobotConfigFile configFile : configFileManager.getXMLFiles()) {
      if (!configFile.isReadOnly()) {
        // The embedded parent address will be read as 173, regardless of what the XML says (see LynxUsbDeviceConfiguration).
        // That means we can turn right around and re-serialize it with no additional processing.
        RobotLog.vv(TAG, "Updating \"%s\" config file", configFile.getName());
        try {
          RobotConfigMap deserializedConfig = new RobotConfigMap(xmlReader.parse(configFile.getXml()));
          String reserializedConfig = configFileManager.toXml(deserializedConfig);
          configFileManager.writeToFile(configFile, false, reserializedConfig);
        } catch (IOException | XmlPullParserException | RuntimeException e) {
          RobotLog.ee(TAG, e, String.format(Locale.ENGLISH, "Failed to auto-update config file %s after automatically changing embedded Control Hub module address. This is OK.", configFile.getName()));
          // It's not the end of the world if this fails, as the Control Hub's address will be read as 173 regardless of what the XML says.
        }
      }
    }
  }

  private List<CachedLynxModulesInfo.LynxModuleInfo> compileLynxModulesInfo(HardwareMap hardwareMap) {
    List<CachedLynxModulesInfo.LynxModuleInfo> result = new ArrayList<>();
    List<LynxModule> lynxModules = hardwareMap.getAll(LynxModule.class);

    for (LynxModule module: lynxModules) {
      String moduleName;
      try {
        moduleName = hardwareMap.getNamesOf(module).iterator().next();
      } catch (RuntimeException e) { // Protects against empty iterator
        moduleName = "Expansion Hub " + module.getModuleAddress();
      }

      String rawFirmwareVersionString = module.getNullableFirmwareVersionString();
      LynxModuleImuType imuType = module.getImuType();

      result.add(new CachedLynxModulesInfo.LynxModuleInfo(moduleName, rawFirmwareVersionString, module.getSerialNumber().toString(), module.getModuleAddress(), imuType));
    }

    // Sort alphabetically by name
    Collections.sort(result, new Comparator<CachedLynxModulesInfo.LynxModuleInfo>() {
      @Override
      public int compare(CachedLynxModulesInfo.LynxModuleInfo o1, CachedLynxModulesInfo.LynxModuleInfo o2) {
        return o1.name.compareTo(o2.name);
      }
    });

    return Collections.unmodifiableList(result);
  }

  public class DefaultUsbModuleAttachmentHandler implements UsbModuleAttachmentHandler {

    @Override
    public void handleUsbModuleAttach(RobotUsbModule module) throws RobotCoreException, InterruptedException {

      String id = nameOfUsbModule(module);

      RobotLog.ii(TAG, "vv===== MODULE ATTACH: disarm %s=====vv", id);
      module.disarm();

      RobotLog.ii(TAG, "======= MODULE ATTACH: arm or pretend %s=======", id);
      module.armOrPretend();

      RobotLog.ii(TAG, "^^===== MODULE ATTACH: complete %s=====^^", id);
    }

    @Override
    public void handleUsbModuleDetach(RobotUsbModule module) throws RobotCoreException, InterruptedException {
    // This provides the default policy for dealing with hardware modules that have experienced
    // abnormal termination because they, e.g., had their USB cable disconnected.

      String id = nameOfUsbModule(module);

      RobotLog.ii(TAG, "vv===== MODULE DETACH RECOVERY: disarm %s=====vv", id);

      // First thing we do is disarm the module. That will put it in a nice clean state.
      module.disarm();

      RobotLog.ii(TAG, "======= MODULE DETACH RECOVERY: pretend %s=======", id);

      // Next, to make it appear more normal and functional to its clients, we make it pretend
      module.pretend();

      RobotLog.ii(TAG, "^^===== MODULE DETACH RECOVERY: complete %s=====^^", id);
    }

    String nameOfUsbModule(RobotUsbModule module) {
      return HardwareFactory.getDeviceDisplayName(activityContext, module.getSerialNumber());
    }
  }
}

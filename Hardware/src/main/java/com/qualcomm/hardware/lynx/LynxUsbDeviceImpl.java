/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.qualcomm.hardware.lynx;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.hardware.HardwareFactory;
import com.qualcomm.hardware.R;
import com.qualcomm.hardware.lynx.commands.LynxDatagram;
import com.qualcomm.hardware.lynx.commands.LynxMessage;
import com.qualcomm.hardware.lynx.commands.standard.LynxDiscoveryCommand;
import com.qualcomm.hardware.lynx.commands.standard.LynxDiscoveryResponse;
import com.qualcomm.robotcore.eventloop.SyncdDevice;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DeviceManager;
import com.qualcomm.robotcore.hardware.LynxModuleDescription;
import com.qualcomm.robotcore.hardware.LynxModuleImuType;
import com.qualcomm.robotcore.hardware.LynxModuleMeta;
import com.qualcomm.robotcore.hardware.LynxModuleMetaList;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.hardware.usb.RobotUsbDevice;
import com.qualcomm.robotcore.hardware.usb.RobotUsbManager;
import com.qualcomm.robotcore.hardware.usb.RobotUsbModule;
import com.qualcomm.robotcore.hardware.usb.ftdi.RobotUsbDeviceFtdi;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.IncludedFirmwareFileInfo;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;
import com.qualcomm.robotcore.util.ThreadPool;
import com.qualcomm.robotcore.util.TypeConversion;
import com.qualcomm.robotcore.util.Util;
import com.qualcomm.robotcore.util.WeakReferenceSet;

import org.firstinspires.ftc.robotcore.external.Consumer;
import org.firstinspires.ftc.robotcore.internal.hardware.TimeWindow;
import org.firstinspires.ftc.robotcore.internal.hardware.android.AndroidBoard;
import org.firstinspires.ftc.robotcore.internal.hardware.usb.ArmableUsbDevice;
import org.firstinspires.ftc.robotcore.internal.network.RobotCoreCommandList;
import org.firstinspires.ftc.robotcore.internal.system.AppAliveNotifier;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.Assert;
import org.firstinspires.ftc.robotcore.internal.ui.ProgressParameters;
import org.firstinspires.ftc.robotcore.internal.usb.exception.RobotUsbDeviceClosedException;
import org.firstinspires.ftc.robotcore.internal.usb.exception.RobotUsbException;
import org.firstinspires.ftc.robotcore.internal.usb.exception.RobotUsbFTDIException;
import org.firstinspires.ftc.robotcore.internal.usb.exception.RobotUsbUnspecifiedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * {@link LynxUsbDeviceImpl} controls the USB communication to one or more Lynx Modules.
 * It polls for incoming traffic and sorts same according to the module address involved.
 */
@SuppressWarnings("WeakerAccess")
public class LynxUsbDeviceImpl extends ArmableUsbDevice implements LynxUsbDevice
    {
    //----------------------------------------------------------------------------------------------
    // Debugging
    //----------------------------------------------------------------------------------------------

    public static final String TAG = "LynxUsb";
    @Override protected String getTag() { return TAG; }

    public static boolean DEBUG_LOG_MESSAGES          = false;
    public static boolean DEBUG_LOG_DATAGRAMS         = false;
    public static boolean DEBUG_LOG_DATAGRAMS_FINISH  = false;
    public static boolean DEBUG_LOG_DATAGRAMS_LOCK    = false;

    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    protected static final WeakReferenceSet<LynxUsbDeviceImpl>  extantDevices = new WeakReferenceSet<LynxUsbDeviceImpl>();
    protected static final LynxCommExceptionHandler             exceptionHandler = new LynxCommExceptionHandler(TAG);

    protected final ConcurrentHashMap<Integer, LynxModule>  knownModules;               // module address -> module
    protected final ConcurrentHashMap<Integer, LynxModule>  knownModulesChanging;       // module address -> module
    protected final ConcurrentHashMap<Integer, LynxModuleMeta> discoveredModules;       // like knownModules, but different
    protected final ConcurrentHashMap<Integer, String>      missingModules;             // module address -> module name
    protected final MessageKeyedLock                        networkTransmissionLock;
    protected       ExecutorService                         incomingDatagramPoller;
    protected       boolean                                 resetAttempted;
    protected       boolean                                 hasShutdownAbnormally;
    protected       boolean                                 isSystemSynthetic;
    protected       boolean                                 isEngaged;
    protected       boolean                                 wasPollingWhenEngaged;
    protected final Object                                  engageLock = new Object();  // must hold to access isEngaged
    protected final Object                                  sysOpStartStopLock = new Object(); // Held before and after sysop runs
    protected final Set<Object>                             runningSysOpTrackers =  new HashSet<>(); // Guarded by sysOpStartStopLock
    protected final LynxFirmwareUpdater                     lynxFirmwareUpdater = new LynxFirmwareUpdater(this);

    // The lynx hw schematic puts the reset and prog lines on particular pins, CBUS0 and CBUS1 respectively
    protected final static int cbusNReset           = 0x01;
    protected final static int cbusNProg            = 0x02;
    protected final static int cbusMask             = cbusNReset | cbusNProg;
    protected final static int cbusNeitherAsserted  = cbusNReset | cbusNProg;
    protected final static int cbusBothAsserted     = 0;
    protected final static int cbusProgAsserted     = cbusNReset;
    protected final static int cbusResetAsserted    = cbusNProg;

    protected final static int msNetworkTransmissionLockAcquisitionTimeMax = 500;
    protected final static int msCbusWiggle         = 75;       // more of a guess than anything
    protected final static int msResetRecovery      = 200;      // more of a guess than anything

    protected final static String SEPARATOR         = " / ";

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    public static ArmableUsbDevice.OpenRobotUsbDevice createUsbOpener(RobotUsbManager usbManager, SerialNumber serialNumber)
        {
        return () ->
            {
            RobotUsbDevice dev = null;
            try
                {
                dev = LynxUsbUtil.openUsbDevice(true, usbManager, serialNumber);
                if (!dev.getUsbIdentifiers().isLynxDevice())
                    {
                    dev.close();
                    String msg = String.format(Locale.US, "Supposed Lynx USB device (serial number %s) does not have expected IDs", serialNumber);
                    RobotLog.ee(TAG, msg);
                    throw new RobotCoreException(msg);
                    }
                dev.setDeviceType(DeviceManager.UsbDeviceType.LYNX_USB_DEVICE);
                }
            catch (RobotCoreException | RuntimeException e)
                {
                if (dev != null) dev.close(); // avoid leakage of open FT_Devices
                throw e;
                }
            return dev;
            };
        }

    /** Use {@link #findOrCreateAndArm} instead */
    protected LynxUsbDeviceImpl(final Context context, final SerialNumber serialNumber, @Nullable SyncdDevice.Manager manager, RobotUsbManager usbManager)
        {
        super(context, serialNumber, manager, createUsbOpener(usbManager, serialNumber));
        this.incomingDatagramPoller  = null;
        this.knownModules            = new ConcurrentHashMap<Integer, LynxModule>();
        this.knownModulesChanging    = new ConcurrentHashMap<Integer, LynxModule>();
        this.discoveredModules       = new ConcurrentHashMap<Integer, LynxModuleMeta>();
        this.missingModules          = new ConcurrentHashMap<Integer, String>();
        this.networkTransmissionLock = new MessageKeyedLock("lynx xmit lock", msNetworkTransmissionLockAcquisitionTimeMax);
        this.resetAttempted          = false;
        this.hasShutdownAbnormally   = false;
        this.isSystemSynthetic       = false;
        this.isEngaged               = true;
        this.wasPollingWhenEngaged   = true;

        // findOrCreateAndArm will lock this for us
        extantDevices.add(this);

        finishConstruction();
        }

    /**
     * Either finds an already-open device with the indicated serial number and returns same, as-is,
     * or creates a new device and arms or pretends it, as the case may be. We wrap with delegates
     * so that we can do reference counting (allowing multiple opens of the same device) while at
     * the same time maintaining the semantic that close() must be idempotent on a given instance.
     */
    public static LynxUsbDevice findOrCreateAndArm(final Context context,
                                                   final SerialNumber serialNumber,
                                                   @Nullable SyncdDevice.Manager manager,
                                                   RobotUsbManager robotUsbManager) throws RobotCoreException, InterruptedException
        {
        synchronized (extantDevices)
            {
            for (LynxUsbDeviceImpl device : extantDevices)
                {
                if (device.getSerialNumber().equals(serialNumber) && /*paranoia*/device.getArmingState() != ARMINGSTATE.CLOSED)
                    {
                    device.addRef(); // new delegate must own another reference
                    RobotLog.vv(TAG, "using existing [%s]: 0x%08x", serialNumber, device.hashCode());
                    return new LynxUsbDeviceDelegate(device);
                    }
                }

            LynxUsbDeviceImpl newDevice = new LynxUsbDeviceImpl(context, serialNumber, manager, robotUsbManager); // has ref count of one
            RobotLog.vv(TAG, "creating new [%s]: 0x%08x", serialNumber, newDevice.hashCode());
            newDevice.armOrPretend();
            return new LynxUsbDeviceDelegate(newDevice);
            }
        }

    @Override public LynxUsbDeviceImpl getDelegationTarget()
        {
        return this;
        }

    @Override public boolean isSystemSynthetic()
        {
        return this.isSystemSynthetic;
        }

    @Override public void setSystemSynthetic(boolean systemSynthetic)
        {
        this.isSystemSynthetic = systemSynthetic;
        }

    //----------------------------------------------------------------------------------------------
    // HardwareDevice
    //----------------------------------------------------------------------------------------------

    @Override protected void doClose()
        {
        // Take the extantDevices lock before the arming lock so as to preserve lock order. If
        // extantDevices and armingLock are both to be acquired, then the former MUST be done first.
        synchronized (extantDevices)
            {
            super.doClose();

            // Once we're closed, there's no reason for anyone to find us any more.
            extantDevices.remove(this);
            }
        }

    @Override public Manufacturer getManufacturer()
        {
        return Manufacturer.Lynx;
        }

    @Override
    public String getDeviceName()
        {
        return context.getString(R.string.moduleDisplayNameLynxUsbDevice);
        }

    @Override
    public String getConnectionInfo()
        {
        return "USB " + getSerialNumber();
        }

    @Override
    public void resetDeviceConfigurationForOpMode()
        {
        }

    @Override
    public int getVersion()
        {
        return 1;
        }

    //----------------------------------------------------------------------------------------------
    // SyncdDevice
    //----------------------------------------------------------------------------------------------

    @Override
    public ShutdownReason getShutdownReason()
        {
        RobotUsbDevice robotUsbDevice = this.robotUsbDevice;
        return this.hasShutdownAbnormally || robotUsbDevice==null || !robotUsbDevice.isOpen()
                ? ShutdownReason.ABNORMAL
                : ShutdownReason.NORMAL;
        }

    protected boolean hasShutdownAbnormally()
        {
        return getShutdownReason() != ShutdownReason.NORMAL;
        }

    @Override
    public void setOwner(RobotUsbModule owner)
        {
        // ignored
        }

    @Override
    public RobotUsbModule getOwner()
        {
        return this;
        }

    //----------------------------------------------------------------------------------------------
    // Arming and disarming
    //----------------------------------------------------------------------------------------------

    @Override public synchronized void engage()
        {
        synchronized (engageLock)
            {
            if (!this.isEngaged)
                {
                if (wasPollingWhenEngaged && isArmed())
                    {
                    startPollingForIncomingDatagrams();
                    }
                for (LynxModule module : getKnownModules())
                    {
                    module.engage();
                    }
                this.isEngaged = true;
                }
            }
        }

    @Override public synchronized void disengage()
        {
        synchronized (engageLock)
            {
            if (this.isEngaged)
                {
                this.isEngaged = false;
                for (LynxModule module : getKnownModules())
                    {
                    module.disengage();
                    }
                this.wasPollingWhenEngaged = stopPollingForIncomingDatagrams();
                }
            }
        }

    @Override public synchronized boolean isEngaged()
        {
        synchronized (engageLock)
            {
            return this.isEngaged;
            }
        }

    @Override
    protected void doPretend()
        {
        // Nothing to do: see transmit()
        RobotLog.vv(TAG, "doPretend() serial=%s", serialNumber);
        }

    @Override
    protected void armDevice(RobotUsbDevice device) throws RobotCoreException, InterruptedException
        {
        synchronized (armingLock)
            {
            RobotLog.vv(TAG, "armDevice() serial=%s...", serialNumber);

            Assert.assertTrue(device != null);
            this.robotUsbDevice = device;

            // Issue a hardware reset to the device. This is just a good housekeeping practice, as
            // it guarantees that the device is in a pristine, known state. This might help, for
            // example, to recover from synchronization errors.
            //
            // It also will kick the device out of firmware update mode if it happens to be in same.
            //
            // That all said, resetting will obliterate any in-memory state maintained by the lynx
            // firmware, some of which, like motor modes etc, are hard to recreate. If we could come
            // up with a simple enough (re)initialization strategy in which user code could participate,
            // we might reasonably *always* reset, but for now we make sure we do it at least once
            // but not thereafter.
            //
            if (!resetAttempted)
                {
                resetAttempted = true;
                resetDevice(this.robotUsbDevice);
                }

            this.hasShutdownAbnormally = false;
            if (syncdDeviceManager!=null) syncdDeviceManager.registerSyncdDevice(this);
            resetNetworkTransmissionLock();
            startPollingForIncomingDatagrams();
            pingAndQueryKnownInterfaces();
            startRegularPinging();

            RobotLog.vv(TAG, "...done armDevice()");
            }
        }

    @Override
    protected void disarmDevice() throws InterruptedException
        {
        synchronized (armingLock)
            {
            RobotLog.vv(TAG, "disarmDevice() serial=%s...", serialNumber);

            // Note: new transmissions are not accepted because we're not in the armed state
            Assert.assertFalse(this.isArmedOrArming());

            pretendFinishExtantCommands();
            abandonUnfinishedCommands();

            stopRegularPinging();
            stopPollingForIncomingDatagrams();
            if (robotUsbDevice != null)
                {
                robotUsbDevice.close();
                robotUsbDevice = null;
                }

            resetNetworkTransmissionLock();
            if (syncdDeviceManager!=null) syncdDeviceManager.unregisterSyncdDevice(this);

            RobotLog.vv(TAG, "...done disarmDevice()");
            }
        }

    @Override protected void doCloseFromArmed() throws RobotCoreException, InterruptedException
        {
        failSafe();
        closeModules();
        super.doCloseFromArmed();
        }

    @Override protected void doCloseFromOther() throws RobotCoreException, InterruptedException
        {
        closeModules();
        super.doCloseFromOther();
        }

    protected void closeModules()
        {
        // We're about to wait for any currently-running system operations to finish. That should be fine, but since the
        // operation may take some time (such as flashing the BHI260AP firmware), it's important to put the modules into
        // a safe state before waiting.
        for (LynxModule module : getKnownModules())
            {
            try
                {
                module.failSafe();
                }
            catch (RobotCoreException | LynxNackException e)
                {
                RobotLog.ee(TAG, e, "Failed to put module into failsafe mode before closing");
                }
            catch (InterruptedException e)
                {
                Thread.currentThread().interrupt();
                }
            }

        synchronized (sysOpStartStopLock)
            {
            try
                {
                // Wait for all running system operations to finish
                // Do NOT wait for all modules to have systemOperationCounter set to 0, as we only want to wait
                // for currently-in-progress operations to finish, and systemOperationCounter can merely
                // indicate that a piece of code wants to keep the module open for now
                while (runningSysOpTrackers.size() > 0)
                    {
                    // wait() relinquishes the lock and waits for notify() to be called on the lock
                    sysOpStartStopLock.wait();
                    }

                // All system operations have now finished
                for (LynxModule module : getKnownModules())
                    {
                    module.close();
                    }
                }
            catch (InterruptedException e)
                {
                Thread.currentThread().interrupt();
                }
            }
        }

    //----------------------------------------------------------------------------------------------
    // Operations
    //----------------------------------------------------------------------------------------------

    @Override public void failSafe()
        {
        for (LynxModule module : getKnownModules())
            {
            try {
                if (module.isUserModule())
                    {
                    module.failSafe();
                    }
                }
            catch (RobotCoreException|LynxNackException|InterruptedException e)
                {
                exceptionHandler.handleException(e);
                }
            }
        }

    //----------------------------------------------------------------------------------------------
    // Operations
    //----------------------------------------------------------------------------------------------

    protected Collection<LynxModule> getKnownModules()
        {
        synchronized (this.knownModules)
            {
            return this.knownModules.values();
            }
        }

    protected LynxModule findKnownModule(int moduleAddress)
        {
        synchronized (this.knownModules)
            {
            LynxModule result = this.knownModules.get(moduleAddress);
            if (result == null)
                {
                // See if it's a module whose address is changing
                result = this.knownModulesChanging.get(moduleAddress);
                }
            return result;
            }
        }

    /**
     * getAllModuleFirmwareVersions
     *
     * @return A list of firmware versions for all known modules in the form of "id / version"
     */
    public List<String> getAllModuleFirmwareVersions()
        {
        List<String> versions = new ArrayList<>();

        for (LynxModule module : this.getKnownModules())
            {
            module.getFirmwareVersionString();
            versions.add(module.getModuleAddress() + SEPARATOR + module.getFirmwareVersionString());
            }

        return versions;
        }

    public void changeModuleAddress(LynxModule module, int newAddress, Runnable runnable)
        {
        int oldAddress = module.getModuleAddress();
        if (newAddress != oldAddress)
            {
            synchronized (knownModules)
                {
                this.knownModulesChanging.put(newAddress, module);
                }
            runnable.run();
            synchronized (this.knownModules)
                {
                this.knownModules.put(newAddress, module);
                this.knownModules.remove(oldAddress);
                this.knownModulesChanging.remove(newAddress);
                }
            }
        }

    @Override public void noteMissingModule(int moduleAddress, String moduleName)
        {
        this.missingModules.put(moduleAddress, moduleName);
        RobotLog.ee(TAG, "module #%d did not connect at startup: skip adding its hardware items to the hardwareMap", moduleAddress);
        }

    /** For lynx modules, in addition to reporting arming issues, we also need to report
     * any issues having to do with configured modules which are absent. However, we avoid
     * being *too* verbose, choosing instead to prioritize our messages. */
    @Override protected String composeGlobalWarning()
        {
        List<String> warnings = new ArrayList<String>();
        //
        String armingWarning = super.composeGlobalWarning();
        warnings.add(armingWarning);
        //
        if (armingWarning.isEmpty()) // no point warning about missing pieces if the whole is missing in the first place
            {
            for (String moduleName : this.missingModules.values())
                {
                warnings.add(AppUtil.getDefContext().getString(R.string.errorExpansionHubIsMissing, moduleName));
                }

            for (LynxModule module : this.getKnownModules())
                {
                List<String> moduleWarnings = module.getGlobalWarnings();
                warnings.addAll(moduleWarnings);
                }
            }
        //
        return RobotLog.combineGlobalWarnings(warnings);
        }

    /**
     * Returns a LynxModule instance that is equivalent to (but not guaranteed to be the same as)
     * the one passed in as a parameter, which is registered with this LynxUsbDevice.
     * <p>
     * After calling this method, you MUST use the return value, not the instance you passed in as a
     * parameter.
     *
     * @throws RobotCoreException if trying to communicate with this module was unsuccessful.
     */
    @Override public LynxModule getOrAddModule(LynxModuleDescription moduleDescription) throws InterruptedException, RobotCoreException
        {
        // Avoid potential race condition where performSystemOperationOnConnectedModule() closes a module that this
        // method just promoted to be a user module.
        synchronized (sysOpStartStopLock)
            {
            int moduleAddress = moduleDescription.address;
            RobotLog.vv(TAG, "addConfiguredModule() module#=%d", moduleAddress);
            boolean added = false;
            LynxModule module;

            synchronized (this.knownModules)
                {
                if (!this.knownModules.containsKey(moduleAddress))
                    {
                    module = new LynxModule(this, moduleAddress, moduleDescription.isParent, moduleDescription.isUserModule);
                    if (moduleDescription.isSystemSynthetic) { module.setSystemSynthetic(true); }
                    this.knownModules.put(moduleAddress, module);
                    added = true;
                    }
                else
                    {
                    module = knownModules.get(moduleAddress);
                    RobotLog.vv(TAG, "addConfiguredModule() module#=%d: already exists", moduleAddress);

                    //noinspection ConstantConditions
                    if (moduleDescription.isUserModule && !module.isUserModule())
                        {
                        // The caller of this method is trying to set up a user module, but the
                        // currently-registered module is a non-user module, so we convert the
                        // registered module into a user module.
                        RobotLog.vv(TAG, "Converting module #%d to a user module", module.getModuleAddress());
                        module.setUserModule(true);
                        }

                    //noinspection ConstantConditions
                    if (moduleDescription.isUserModule && moduleDescription.isSystemSynthetic && !module.isSystemSynthetic())
                        {
                        // The caller of this method is trying to set up a user module that was added
                        // implicitly, rather than explicitly stated in the XML configuration. Add that
                        // property to the registered module.
                        module.setSystemSynthetic(true);
                        }

                    //noinspection ConstantConditions
                    if (moduleDescription.isParent != module.isParent())
                        {
                        RobotLog.ww(TAG, "addConfiguredModule(): The active configuration file may be incorrect about whether Expansion Hub %d is the parent", module.getModuleAddress());
                        }
                    }
                }

            if (added)
                {
                try {
                    module.pingAndQueryKnownInterfacesAndEtc();
                    }
                catch (RobotCoreException|InterruptedException|RuntimeException e)
                    {
                    // If we don't full add, then we don't add at all
                    RobotLog.logExceptionHeader(TAG, e, "addConfiguredModule() module#=%d", module.getModuleAddress());
                    RobotLog.ee(TAG, "Unable to communicate with REV Hub #%d at robot startup. A Robot Restart will be required to use this hub.", module.getModuleAddress());
                    module.close();
                    synchronized (this.knownModules)
                        {
                        this.knownModules.remove(module.getModuleAddress());
                        }
                    throw e;
                    }
                }

            return module;
            }
        }

    /** Should ONLY be called by LynxModule.close() */
    @Override public void removeConfiguredModule(LynxModule module)
        {
        synchronized (this.knownModules)
            {
            // A module with address 0 was only used for discovery, and was not added to knownModules
            if (module.getModuleAddress() != 0 && this.knownModules.remove(module.getModuleAddress()) == null)
                {
                RobotLog.ee(TAG, "removeConfiguredModule(): mod#=%d wasn't there", module.getModuleAddress());
                }
            }
        }

    /**
     * The operation will run on a background thread, but this method will not return until the operation has completed.
     * <p>
     * If operation is null, this method effectively just verifies that we can communicate with the parent
     * module, throwing an exception if we cannot.
     */
    @Override public void performSystemOperationOnParentModule(int parentAddress,
                                                               @Nullable Consumer<LynxModule> operation,
                                                               int timeout,
                                                               TimeUnit timeoutUnit)
            throws RobotCoreException, InterruptedException, TimeoutException
        {
        performSystemOperationOnConnectedModule(parentAddress, parentAddress, operation, timeout, timeoutUnit);
        }

    /**
     * The operation will run on a background thread, but this method will not return until the operation has completed.
     * <p>
     * If operation is null, this method effectively just verifies that we can communicate with the specified
     * module, throwing an exception if we cannot.
     */
    @Override public void performSystemOperationOnConnectedModule(int moduleAddress,
                                                                  int parentAddress,
                                                                  @Nullable Consumer<LynxModule> operation,
                                                                  int timeout,
                                                                  TimeUnit timeoutUnit)
            throws RobotCoreException, InterruptedException, TimeoutException
        {
        LynxModuleDescription parentModuleDescription = new LynxModuleDescription.Builder(parentAddress, true).build();
        LynxModuleDescription moduleDescription = new LynxModuleDescription.Builder(moduleAddress, false).build();

        LynxModule parentModule = getOrAddModule(parentModuleDescription);
        LynxModule module;
        if (moduleAddress == parentAddress)
            {
            module = parentModule;
            }
        else
            {
            module = getOrAddModule(moduleDescription);
            }

        internalPerformSysOp(module, parentModule, operation, timeout, timeoutUnit);
        }

    protected void internalPerformSysOp(LynxModule module,
                                        LynxModule parentModule,
                                        @Nullable Consumer<LynxModule> operation,
                                        int timeout,
                                        TimeUnit timeoutUnit)
            throws RobotCoreException, InterruptedException, TimeoutException
        {
        final Object sysOpTracker = new Object();

        synchronized (sysOpStartStopLock)
            {
            try {
                // Make sure we can currently communicate with this module (will throw an exception if not)
                module.ping(false);
                }
            catch (LynxNackException e)
                {
                throw new RobotCoreException("Received NACK when pinging LynxModule: " + e.getNack());
                }
            parentModule.systemOperationCounter++;
            module.systemOperationCounter++;

            runningSysOpTrackers.add(sysOpTracker);
            }

        Future<?> operationFuture = null;
        try
            {
            if (operation != null)
                {
                // Run on a background thread so that we can move on if the timeout expires
                final LynxModule finalModule = module; // Final so that it's usable from the background thread
                operationFuture = ThreadPool.getDefault().submit(() -> operation.accept(finalModule));
                operationFuture.get(timeout, timeoutUnit);
                }
            }
        catch (TimeoutException e)
            {
            // Interrupt the background thread and rethrow
            operationFuture.cancel(true);
            throw e;
            }
        catch (ExecutionException executionException)
            {
            // Get the exception that was thrown from the background thread
            Throwable cause = executionException.getCause();

            // Throw the exception on this thread. If it's a runtime exception or an exception that this method is
            // listed as throwing, we can cast it and throw it directly. Otherwise, we must wrap it in a RuntimeException.
            // InterruptedException is a special case.
            if (cause instanceof InterruptedException)
                {
                // We must NOT rethrow this exception, because that would indicate that *this* thread is interrupted,
                // which is not the case.
                throw new RobotCoreException("Background thread was interrupted while performing system operation", cause);
                }
            if (cause instanceof RobotCoreException)
                {
                throw (RobotCoreException) cause;
                }
            else if (cause instanceof RuntimeException)
                {
                throw (RuntimeException) cause;
                }
            else if (cause instanceof TimeoutException)
                {
                throw (TimeoutException) cause;
                }
            }
        finally
            {
            synchronized (sysOpStartStopLock)
                {
                runningSysOpTrackers.remove(sysOpTracker);
                // In case this LynxUsbDevice is in the process of trying to close, but is waiting on this operation to
                // finish, notify that thread via the runningSysOpTrackers lock object.
                sysOpStartStopLock.notifyAll();

                parentModule.systemOperationCounter--;
                internalCloseLynxModuleIfUnused(parentModule);

                module.systemOperationCounter--;
                internalCloseLynxModuleIfUnused(module);
                }
            }
        }

    protected void internalCloseLynxModuleIfUnused(LynxModule lynxModule)
        {
        synchronized (sysOpStartStopLock)
            {
            if (findKnownModule(lynxModule.getModuleAddress()) != lynxModule)
                {
                throw new RuntimeException("An unaffiliated module was passed in to internalCloseLynxModuleIfAppropriate()");
                }

            // Check that systemOperationCounter has a legal value
            if (lynxModule.systemOperationCounter < 0)
                {
                RobotLog.ee(TAG, "systemOperationCounter for module %d was below 0", lynxModule.getModuleAddress());
                }

            // Close the module if it's not a user-visible module and there are no system operation handles on it
            if (!lynxModule.isUserModule && lynxModule.systemOperationCounter <= 0)
                {
                lynxModule.close();
                }
            }
        }

    /**
     * Open a {@link SystemOperationHandle} that will allow you to keep the LynxModule open for an unbounded length of time.
     * <p>
     * You can stop forcing the LynxModule to stay open by calling {@link SystemOperationHandle#close()}.
     * <p>
     * You can perform system operations on the module by calling {@link SystemOperationHandle#performSystemOperation(Consumer, int, TimeUnit)}.
     * <p>
     * This handle will continue working even if the module's address is changed.
     */
    public SystemOperationHandle keepConnectedModuleAliveForSystemOperations(int moduleAddress, int parentAddress)
            throws RobotCoreException, InterruptedException
        {
        LynxModuleDescription parentModuleDescription = new LynxModuleDescription.Builder(parentAddress, true).build();
        LynxModuleDescription moduleDescription = new LynxModuleDescription.Builder(moduleAddress, false).build();

        LynxModule parentModule = getOrAddModule(parentModuleDescription);
        LynxModule module;
        if (moduleAddress == parentAddress)
            {
            module = parentModule;
            }
        else
            {
            module = getOrAddModule(moduleDescription);
            }

        // This constructor will continue the setup process
        return new SystemOperationHandle(this, module, parentModule);
        }

    /**
     * Discover all the lynx modules that are accessible through this Lynx USB device. One of these
     * will be the 'parent', which is the one directly USB connected. The others are accessible
     * over the RS485 bus.
     *
     * @param checkForImus Whether we should check if each discovered module has an onboard BNO055
     *                     IMU, and if parents have an onboard BHI260 IMU
     */
    @Override public LynxModuleMetaList discoverModules(boolean checkForImus) throws RobotCoreException, InterruptedException
        {
        RobotLog.vv(TAG, "lynx discovery beginning for device " + serialNumber);

        // TODO(Noah): ALWAYS call this method through USBScanManager, as modules will be missed if this method is called while discovery is already ongoing
        // Initialize our set of known modules and send out discovery requests
        this.discoveredModules.clear();

        // Make ourselves a fake module so that we can (mostly) use the normal transmission infrastructure
        LynxModule fakeModule = new LynxModule(this, 0/*ignored in discovery*/, false, false);
        try {
            // Make a discovery command and send it out
            LynxDiscoveryCommand discoveryCommand = new LynxDiscoveryCommand(fakeModule);
            discoveryCommand.send();

            // Wait an interval sufficient so as to guarantee that we'll see all the replies
            // that are there to see. See description of timing in LynxDiscoveryCommand.

            long nsPerModuleInterval = 3 * ElapsedTime.MILLIS_IN_NANO;
            int  maxNumberOfModules  = LynxConstants.MAX_MODULES_DISCOVER;
            long nsPacketTimeMax     = 50 * ElapsedTime.MILLIS_IN_NANO;     // "entire packet must be received within 50ms from the first Frame Byte"
            long nsSlop              = 200 * ElapsedTime.MILLIS_IN_NANO;    // should be oodles
            long nsWait = nsPerModuleInterval * maxNumberOfModules + nsPacketTimeMax + nsSlop;

            long msWait = (nsWait / ElapsedTime.MILLIS_IN_NANO);
            //noinspection ConstantConditions
            nsWait = nsWait - msWait * ElapsedTime.MILLIS_IN_NANO;
            RobotLog.vv(TAG, "discovery waiting %dms and %dns", msWait, nsWait);
            Thread.sleep(msWait, (int) nsWait);
            RobotLog.vv(TAG, "discovery waiting complete: #modules=%d", discoveredModules.size());

            RobotLog.vv(TAG, "Checking type of discovered modules");
            LynxModuleMeta[] parents = discoveredModules.values().stream()
                    .filter(LynxModuleMeta::isParent)
                    .toArray(LynxModuleMeta[]::new);
            if (parents.length > 0)
                {
                LynxModuleMeta parentInfo = parents[0];
                for (final LynxModuleMeta moduleMeta: discoveredModules.values())
                    {
                    try
                        {
                        performSystemOperationOnConnectedModule(
                                moduleMeta.getModuleAddress(),
                                parentInfo.getModuleAddress(),
                                module -> {
                                    final int revProductNumber = module.getRevProductNumber();
                                    moduleMeta.setRevProductNumber(revProductNumber);
                                    if (checkForImus)
                                        {
                                        moduleMeta.setImuType(module.getImuType());
                                        }
                                    else
                                        {
                                        moduleMeta.setImuType(LynxModuleImuType.UNKNOWN);
                                        }
                                },
                                250,
                                TimeUnit.MILLISECONDS);
                        }
                    catch (TimeoutException e)
                        {
                        RobotLog.ee(TAG, e, "Timeout expired while getting IMU type");
                        }
                    }
                }
            else
                {
                RobotLog.ee(TAG, "Unable to check for onboard IMUs as the parent module was not found");
                }
            }
        catch (LynxNackException e)
            {
            throw e.wrap();
            }
        finally
            {
            // Tidy up
            fakeModule.close();
            }

        LynxModuleMetaList result = new LynxModuleMetaList(serialNumber, discoveredModules.values());
        RobotLog.vv(TAG, "...lynx discovery completed");
        return result;
        }

    /**
     * Verifies that we can communicate with the Control Hub's embedded module, and that its address
     * is set to {@link LynxConstants#CH_EMBEDDED_MODULE_ADDRESS}. If we can't communicate with it,
     * an attempt will be made to flash new firmware to it. If its address is incorrect, it will be
     * set to the correct value.
     *
     * @return true if the Control Hub's embedded parent module address was changed
     */
    @Override public boolean setupControlHubEmbeddedModule() throws InterruptedException, RobotCoreException
        {
        if (!getSerialNumber().isEmbedded())
            {
            RobotLog.ww(TAG, "setupControlHubEmbeddedModule() called on non-embedded USB device");
            return false;
            }

        try
            {
            performSystemOperationOnParentModule(LynxConstants.CH_EMBEDDED_MODULE_ADDRESS, null, 250, TimeUnit.MILLISECONDS);

            // performSystemOperationOnConnectedModule will throw a checked exception if we were
            // unable to communicate with module 173. Therefore, if we reach this point, we know it
            // has successfully been pinged. Theoretically, it could be a downstream hub, but sadly
            // there's no way to be sure without doing Discovery, which we would like to avoid
            // because it is slow. We detect when a downstream hub is configured with address 173
            // elsewhere, and show an error. For now, we just assume that it's the embedded hub, and
            // exit.
            RobotLog.vv(TAG, "Verified that the embedded Control Hub module has the correct address");
            return false;
            }
        catch (RobotCoreException | TimeoutException e)
            {
            // TimeoutException should never actually get thrown here because we don't pass in an operation
            return handleEmbeddedModuleNotFoundAtExpectedAddress();
            }
        }

    /**
     * @return true if the Control Hub's embedded parent module address was changed
     */
    private boolean handleEmbeddedModuleNotFoundAtExpectedAddress() throws RobotCoreException, InterruptedException
        {
        RobotLog.ww(TAG, "Unable to find embedded Control Hub module at address %d. Attempting to resolve automatically.", LynxConstants.CH_EMBEDDED_MODULE_ADDRESS);
        LynxModuleMeta discoveredParentModule = discoverModules(false).getParent();
        if (discoveredParentModule == null)
            {
            RobotLog.ee(TAG, "Unable to communicate with internal Expansion Hub. Attempting to re-flash firmware.");
            autoReflashControlHubFirmware();
            discoveredParentModule = discoverModules(false).getParent();
            if (discoveredParentModule == null)
                {
                // We still can't see the embedded module
                RobotLog.setGlobalErrorMsg(AppUtil.getDefContext().getString(R.string.controlHubNotAbleToCommunicateWithInternalHub));
                // The module address was not changed, so we return false;
                return false;
                }
            else
                {
                RobotLog.ii(TAG, "Successfully un-bricked the Control Hub's embedded module");
                if (discoveredParentModule.getModuleAddress() == LynxConstants.CH_EMBEDDED_MODULE_ADDRESS)
                    {
                    RobotLog.ii(TAG, "The embedded module already has the correct address");
                    return false;
                    }
                }
            }

        try
            {
            setControlHubModuleAddress(discoveredParentModule);
            return true;
            }
        catch (TimeoutException e)
            {
            RobotLog.ee(TAG, e, "Timeout expired while setting Control Hub module address");
            return false;
            }
        }

    private void autoReflashControlHubFirmware()
        {
        updateFirmware(IncludedFirmwareFileInfo.FW_IMAGE, "autoFirmwareUpdate", new Consumer<ProgressParameters>()
            {
            @Override public void accept(ProgressParameters value)
                {
                // Make sure that the CH OS watchdog doesn't trip while we're in the middle of the firmware update
                AppAliveNotifier.getInstance().notifyAppAlive();
                }
            });
        resetDevice(robotUsbDevice);
        }

    private void setControlHubModuleAddress(LynxModuleMeta discoveredParentModule) throws InterruptedException, RobotCoreException, TimeoutException
        {
        int oldParentModuleAddress = discoveredParentModule.getModuleAddress();
        RobotLog.vv(TAG, "Found embedded module at address %d", oldParentModuleAddress);

        performSystemOperationOnParentModule(oldParentModuleAddress, parentModule ->
            {
            RobotLog.vv(TAG, "Setting embedded module address to %d", LynxConstants.CH_EMBEDDED_MODULE_ADDRESS);
            parentModule.setNewModuleAddress(LynxConstants.CH_EMBEDDED_MODULE_ADDRESS);
            }, 250, TimeUnit.MILLISECONDS);
        }

    protected void onLynxDiscoveryResponseReceived(LynxDatagram datagram)
        {
        LynxDiscoveryResponse incomingResponse = new LynxDiscoveryResponse();
        incomingResponse.setSerialization(datagram);
        incomingResponse.loadFromSerialization();

        RobotLog.vv(TAG, "onLynxDiscoveryResponseReceived()... module#=%d isParent=%s", incomingResponse.getDiscoveredModuleAddress(), Boolean.toString(incomingResponse.isParent()));
        try {
            // Be paranoid about duplicates
            synchronized (this.discoveredModules)
                {
                if (!this.discoveredModules.containsKey(datagram.getSourceModuleAddress()))
                    {
                    RobotLog.vv(TAG, "discovered lynx module#=%d isParent=%s", incomingResponse.getDiscoveredModuleAddress(), Boolean.toString(incomingResponse.isParent()));
                    LynxModuleMeta meta = new LynxModuleMeta(incomingResponse.getDiscoveredModuleAddress(), incomingResponse.isParent());
                    discoveredModules.put(meta.getModuleAddress(), meta);
                    }
                }
            }
        finally
            {
            RobotLog.vv(TAG, "...onLynxDiscoveryResponseReceived()");
            }
        }

    protected void pingAndQueryKnownInterfaces() throws RobotCoreException, InterruptedException
        {
        // Be sure to do the parent first so that it locks in
        for (LynxModule module : getKnownModules())
            {
            if (module.isParent())
                {
                // Do our main work here
                module.pingAndQueryKnownInterfacesAndEtc();
                }
            }
        for (LynxModule module : getKnownModules())
            {
            if (!module.isParent())
                {
                module.pingAndQueryKnownInterfacesAndEtc();
                }
            }
        }

    //----------------------------------------------------------------------------------------------
    // Transmitting and receiving
    //----------------------------------------------------------------------------------------------

    @Override public void lockNetworkLockAcquisitions()
        {
        this.networkTransmissionLock.lockAcquisitions();
        }

    @Override public void setThrowOnNetworkLockAcquisition(boolean shouldThrow)
        {
        this.networkTransmissionLock.throwOnLockAcquisitions(shouldThrow);
        }

    protected void resetNetworkTransmissionLock() throws InterruptedException
        {
        this.networkTransmissionLock.reset();
        }

    /* Because there is no collision management for the RS485 child bus, it is ill-advised for the
     * Host to concurrently expect data from multiple RS485 child bus siblings (waiting for
     * acknowledgement or requested data).
     */
    @Override public void acquireNetworkTransmissionLock(@NonNull LynxMessage message) throws InterruptedException
        {
        this.networkTransmissionLock.acquire(message);
        }

    @Override public void releaseNetworkTransmissionLock(@NonNull LynxMessage message) throws InterruptedException
        {
        this.networkTransmissionLock.release(message);
        }

    protected void startPollingForIncomingDatagrams()
        {
        if (incomingDatagramPoller == null)
            {
            incomingDatagramPoller = ThreadPool.newSingleThreadExecutor("lynx dg poller");
            incomingDatagramPoller.execute(new IncomingDatagramPoller());
            }
        }

    protected boolean stopPollingForIncomingDatagrams()
        {
        boolean wasEngaged = incomingDatagramPoller != null;

        // Make sure the FTDI layer interrupts reads etc
        RobotUsbDevice robotUsbDevice = this.robotUsbDevice;
        if (robotUsbDevice != null) robotUsbDevice.requestReadInterrupt(true);

        if (incomingDatagramPoller != null)
            {
            RobotLog.vv(TAG, "shutting down incoming datagrams");
            incomingDatagramPoller.shutdownNow();
            ThreadPool.awaitTerminationOrExitApplication(incomingDatagramPoller, 5, TimeUnit.SECONDS, "Lynx incoming datagram poller", "internal error");
            incomingDatagramPoller = null;
            }

        // Clear the forced interrupt so we can re-engage later, maybe // TODO review
        if (robotUsbDevice != null) robotUsbDevice.requestReadInterrupt(false);

        return wasEngaged;
        }

    protected void startRegularPinging()
        {
        for (LynxModule module : getKnownModules())
            {
            module.startPingTimer();
            }
        }
    void stopRegularPinging()
        {
        for (LynxModule module : getKnownModules())
            {
            module.stopPingTimer(true);
            }
        }

    @Override public void transmit(LynxMessage message) throws InterruptedException
    // Note that this might be called on ANY thread.
        {
        synchronized (engageLock)
            {
            if (this.isArmedOrArming() && !this.hasShutdownAbnormally() && isEngaged)
                {
                LynxDatagram datagram = message.getSerialization();
                /*
                 * {@link LynxModule#finishedWithMessage()} might have nulled the serialization
                 */
                if (datagram != null)
                    {
                    if (DEBUG_LOG_DATAGRAMS || DEBUG_LOG_MESSAGES)
                        {
                        RobotLog.vv(TAG, "xmit'ing: mod=%d cmd=0x%02x(%s) msg#=%d ref#=%d ", message.getModuleAddress(), message.getCommandNumber(), message.getClass().getSimpleName(), message.getMessageNumber(), message.getReferenceNumber());
                        }

                    byte[] bytes = datagram.toByteArray();

                    try {
                        // If robotUsbDevice is null, we'll just catch and handle the RuntimeException
                        //noinspection ConstantConditions
                        this.robotUsbDevice.write(bytes);
                        }
                    catch (RobotUsbException|RuntimeException e)    // RuntimeException is just paranoia
                        {
                        // For now, at least, we're brutal: we don't quarter ANY usb transmission errors
                        // before giving up and shutting things down. In the wake of future experience, it
                        // might later be reasonable to reconsider this decision.
                        shutdownAbnormally();
                        //
                        RobotLog.ee(TAG, e, "exception thrown in LynxUsbDevice.transmit");
                        //
                        return;
                        }

                    long now = System.nanoTime();
                    message.setNanotimeLastTransmit(now);

                    // "The keep alive must be sent at least every 2500 milliseconds. The Controller Module
                    // will perform the actions specified in Fail Safe (7F05) if it fails to receive a timely
                    // Keep Alive". Other messages will do the trick, too.
                    //
                    message.resetModulePingTimer();
                    }
                else
                    {
                    message.onPretendTransmit();
                    }
                }
            else
                {
                message.onPretendTransmit();
                }
            }

        // Do this last so as to make LynxModule.retransmitDatagrams() interlock more robust
        message.noteHasBeenTransmitted();
        }

    protected void shutdownAbnormally()
        {
        this.hasShutdownAbnormally = true;
        RobotUsbDevice robotUsbDevice = this.robotUsbDevice;
        boolean attached = robotUsbDevice != null && robotUsbDevice.isAttached();
        String format = context.getString(attached ? R.string.warningProblemCommunicatingWithUSBDevice : R.string.warningUSBDeviceDetached);
        setGlobalWarning(String.format(format, HardwareFactory.getDeviceDisplayName(context, serialNumber)));
        }

    protected void pretendFinishExtantCommands() throws InterruptedException
        {
        for (LynxModule module : getKnownModules())
            {
            module.pretendFinishExtantCommands();
            }
        }

    protected void abandonUnfinishedCommands()
        {
        for (LynxModule module : getKnownModules())
            {
            module.abandonUnfinishedCommands();
            }
        }

    class IncomingDatagramPoller implements Runnable
        {
        boolean stopRequested  = false;
        byte[]  scratch        = new byte[2];
        byte[]  prefix         = new byte[4];
        boolean isSynchronized = false;

        @Override public void run()
            {
            ThreadPool.logThreadLifeCycle("lynx incoming datagrams", new Runnable()
                {
                @Override public void run()
                    {
                    // Boost the thread priority in the hopes of receiving data more quickly
                    Thread.currentThread().setPriority(Thread.NORM_PRIORITY+1);

                    while (!stopRequested && !Thread.currentThread().isInterrupted() && !hasShutdownAbnormally())
                        {
                        // Did we get a datagram?
                        LynxDatagram datagram = pollForIncomingDatagram();
                        if (datagram != null)
                            {
                            // Discovery is a special case
                            if (datagram.getPacketId() == LynxDiscoveryResponse.getStandardCommandNumber())
                                {
                                onLynxDiscoveryResponseReceived(datagram);
                                }
                            else
                                {
                                // Tell the corresponding module, if it's for something we know about
                                LynxModule module = findKnownModule(datagram.getSourceModuleAddress());
                                if (module != null)
                                    {
                                    module.onIncomingDatagramReceived(datagram);
                                    }
                                }
                            }
                        }
                    }
                });
            }

        void readIncomingBytes(byte[] buffer, int cbToRead, @Nullable TimeWindow timeWindow) throws InterruptedException, RobotUsbException
            {
            // We specify an essentially infinite read timeout waiting for the next packet to come in
            long msReadTimeout = Integer.MAX_VALUE;
            RobotUsbDevice robotUsbDevice = LynxUsbDeviceImpl.this.robotUsbDevice;

            if (robotUsbDevice == null)
                {
                RobotLog.ee(TAG, "readIncomingBytes() robotUsbDevice was null");
                throw new RobotUsbUnspecifiedException("readIncomingBytes() robotUsbDevice was null");
                }

            int cbRead = robotUsbDevice.read(buffer, 0, cbToRead, msReadTimeout, timeWindow);
            //noinspection StatementWithEmptyBody
            if (cbRead == cbToRead)
                {
                // We got all the data we came for. Just return gracefully
                }
            else if (cbRead == 0)
                {
                // Couldn't read the data in the time allotted. Because we allot
                // an infinite amount of time, that means that an interrupt occurred, but
                // one that was eaten. Re-signal the interrupt.
                RobotLog.ee(TAG, "readIncomingBytes() cbToRead=%d cbRead=%d: throwing InterruptedException", cbToRead, cbRead);
                throw new InterruptedException("interrupt during robotUsbDevice.read()");
                }
            else
                {
                RobotLog.ee(TAG, "readIncomingBytes() cbToRead=%d cbRead=%d: throwing RobotCoreException", cbToRead, cbRead);
                throw new RobotUsbUnspecifiedException("readIncomingBytes() cbToRead=%d cbRead=%d", cbToRead, cbRead);
                }
            }

        byte readSingleByte(byte[] buffer) throws InterruptedException, RobotUsbException
            {
            readIncomingBytes(buffer, 1, null);
            return buffer[0];
            }

        LynxDatagram pollForIncomingDatagram()
            {
            while (!stopRequested && !Thread.currentThread().isInterrupted() && !hasShutdownAbnormally())
                {
                try {
                    if (!isSynchronized)
                        {
                        // Synchronize by looking for the first framing byte
                        if (readSingleByte(scratch) != LynxDatagram.frameBytes[0])
                            {
                            continue;
                            }

                        // Having found the first, if we don't next see the second, then go back to looking for the first
                        if (readSingleByte(scratch) != LynxDatagram.frameBytes[1])
                            {
                            continue;
                            }

                        // Read the packet length
                        readIncomingBytes(scratch, 2, null);

                        // Assemble the prefix
                        System.arraycopy(LynxDatagram.frameBytes, 0, prefix, 0, 2);
                        System.arraycopy(scratch,                 0, prefix, 2, 2);

                        // We think we are in sync. Next time, just try the faster path
                        RobotLog.vv(TAG, "synchronization gained: serial=%s", serialNumber);
                        isSynchronized = true;
                        }
                    else
                        {
                        // Read the prefix in fewer read calls for better performance
                        readIncomingBytes(prefix, 4, null);

                        // If we're not in sync, then go back to the slow way
                        if (!LynxDatagram.beginsWithFraming(prefix))
                            {
                            RobotLog.vv(TAG, "synchronization lost: serial=%s", serialNumber);
                            isSynchronized = false;
                            continue;
                            }
                        }

                    // Compute the packet length, allocate a buffer for the suffix, and read same
                    int cbPacketLength = TypeConversion.unsignedShortToInt(TypeConversion.byteArrayToShort(prefix, 2, LynxDatagram.LYNX_ENDIAN));
                    int cbSuffix = cbPacketLength - LynxDatagram.cbFrameBytesAndPacketLength;
                    byte[] suffix = new byte[cbSuffix];
                    TimeWindow payloadTimeWindow = new TimeWindow();
                    readIncomingBytes(suffix, cbSuffix, payloadTimeWindow);

                    // Parse the message structure of the datagram
                    byte[] completePacket = Util.concatenateByteArrays(prefix, suffix);
                    LynxDatagram datagram = new LynxDatagram();
                    datagram.setPayloadTimeWindow(payloadTimeWindow);
                    datagram.fromByteArray(completePacket);
                    if (datagram.isChecksumValid())
                        {
                        if (DEBUG_LOG_DATAGRAMS)
                            {
                            RobotLog.vv(TAG, "rec'd: mod=%d cmd=0x%02x msg#=%d ref#=%d ", datagram.getSourceModuleAddress(), datagram.getPacketId(), datagram.getMessageNumber(), datagram.getReferenceNumber());
                            }
                        return datagram;
                        }
                    else
                        {
                        // Invalid checksum. The Lynx specification indicates we are simply to ignore.
                        RobotLog.ee(TAG, "invalid checksum received; message ignored");
                        }
                    }
                catch (RobotUsbFTDIException|RobotUsbDeviceClosedException|RuntimeException e)  // RuntimeException is just paranoia
                    {
                    RobotLog.vv(TAG, "device closed in incoming datagram loop");
                    shutdownAbnormally();
                    RobotUsbDevice robotUsbDevice = LynxUsbDeviceImpl.this.robotUsbDevice;
                    if (robotUsbDevice != null)
                        {
                        robotUsbDevice.close();
                        }

                    // We're not going to be receiving any more incoming messages, so get out of whatever we've got
                    try {
                        pretendFinishExtantCommands();
                        }
                    catch (InterruptedException ignored)
                        {
                        stopRequested = true;
                        }
                    }
                catch (RobotUsbException|RobotCoreException e)
                    {
                    // Something disorderly happened. We'll choose to ignore it, and trust to
                    // retransmission logic to fix
                    RobotLog.vv(TAG, e, "exception thrown in incoming datagram loop; ignored");
                    }
                catch (InterruptedException e)
                    {
                    stopRequested = true;
                    }
                }

            return null;
            }
        }

    //----------------------------------------------------------------------------------------------
    // Firmware control
    //----------------------------------------------------------------------------------------------

    /** A simple, cursory test to see whether we can get access to the cbus FTDI functionality.
     * Currently not very robust */
    protected static RobotUsbDeviceFtdi accessCBus(RobotUsbDevice robotUsbDevice)
        {
        if (robotUsbDevice instanceof RobotUsbDeviceFtdi)
            {
            RobotUsbDeviceFtdi deviceFtdi = (RobotUsbDeviceFtdi)robotUsbDevice;
            if (deviceFtdi.supportsCbusBitbang())
                {
                return deviceFtdi;
                }
            }
        RobotLog.ee(TAG, "accessCBus() unexpectedly failed; ignoring");
        return null;
        }

    /**
     * Issues a hardware reset to the lynx module.
     */
    public static void resetDevice(@Nullable RobotUsbDevice robotUsbDevice)
        {
        if (robotUsbDevice == null)
            {
            RobotLog.ww(TAG, "resetDevice() called with null robotUsbDevice");
            return;
            }

        RobotLog.vv(TAG, "resetDevice() serial=%s", robotUsbDevice.getSerialNumber());

        int msDelay = msCbusWiggle;
        try {
            if (LynxConstants.isEmbeddedSerialNumber(robotUsbDevice.getSerialNumber()))
                {
                boolean prevState = AndroidBoard.getInstance().getAndroidBoardIsPresentPin().getState();
                RobotLog.vv(LynxModule.TAG, "resetting embedded usb device: isPresent: was=%s", prevState);

                // Make sure we're 'present'. Our reset pin won't operate unless we are
                if (!prevState)
                    {
                    AndroidBoard.getInstance().getAndroidBoardIsPresentPin().setState(true);
                    Thread.sleep(msDelay);
                    }

                AndroidBoard.getInstance().getLynxModuleResetPin().setState(true);
                Thread.sleep(msDelay);

                AndroidBoard.getInstance().getLynxModuleResetPin().setState(false);
                Thread.sleep(msDelay);
                }
            else
                {
                RobotUsbDeviceFtdi deviceFtdi = accessCBus(robotUsbDevice);
                if (deviceFtdi != null)
                    {
                    // Initialize with both lines deasserted
                    deviceFtdi.cbus_setup(cbusMask, cbusNeitherAsserted);
                    Thread.sleep(msDelay);

                    // Assert reset
                    deviceFtdi.cbus_write(cbusResetAsserted);
                    Thread.sleep(msDelay);

                    // Deassert reset
                    deviceFtdi.cbus_write(cbusNeitherAsserted);
                    }
                }

            // give the board a chance to recover
            Thread.sleep(msResetRecovery); // totally a finger in the wind
            }
        catch (InterruptedException|RobotUsbException e)
            {
            exceptionHandler.handleException(e);
            }
        }

    @Override
    public RobotCoreCommandList.LynxFirmwareUpdateResp updateFirmware(final RobotCoreCommandList.FWImage image, String requestId, Consumer<ProgressParameters> progressConsumer)
        {
        return lynxFirmwareUpdater.updateFirmware(image, requestId, progressConsumer);
        }
    }

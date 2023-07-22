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

package com.qualcomm.robotcore.eventloop.opmode;

import android.app.Activity;
import android.content.Context;
import android.os.Debug;
import android.util.Log;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.R;
import com.qualcomm.robotcore.eventloop.EventLoopManager;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.RobotCoreLynxController;
import com.qualcomm.robotcore.hardware.RobotCoreLynxModule;
import com.qualcomm.robotcore.hardware.RobotCoreLynxUsbDevice;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.robocol.Command;
import com.qualcomm.robotcore.robocol.TelemetryMessage;
import com.qualcomm.robotcore.robot.RobotState;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.ThreadPool;
import com.qualcomm.robotcore.util.WeakReferenceSet;

import org.firstinspires.ftc.robotcore.internal.network.NetworkConnectionHandler;
import org.firstinspires.ftc.robotcore.internal.network.RobotCoreCommandList;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeServices;
import org.firstinspires.ftc.robotcore.internal.opmode.RegisteredOpModes;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.GamepadUser;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * {@link OpModeManagerImpl} is the owner of the concept of a 'current' opmode.
 */
@SuppressWarnings("unused,WeakerAccess")
public class OpModeManagerImpl implements OpModeServices, OpModeManagerNotifier {

  //------------------------------------------------------------------------------------------------
  // Types
  //------------------------------------------------------------------------------------------------

  class OpModeStateTransition {
    OpModeMeta  queuedOpModeMetadata              = null;
    Boolean     opModeSwapNeeded                  = null;
    Boolean     callToInitNeeded                  = null;
    Boolean     gamepadResetNeeded                = null;
    Boolean     telemetryClearNeeded              = null;
    Boolean     callToStartNeeded                 = null;
    Boolean onlyTransitionIfDefaultOpModeIsRunning = null;

    void apply() {
      if (onlyTransitionIfDefaultOpModeIsRunning != null) {
        if (onlyTransitionIfDefaultOpModeIsRunning && !getActiveOpModeName().equals(DEFAULT_OP_MODE_NAME)) {
          return;
        }
      }

      // We never clear state here; that's done in runActiveOpMode()
      if (queuedOpModeMetadata != null) OpModeManagerImpl.this.queuedOpModeMetadata = queuedOpModeMetadata;
      if (opModeSwapNeeded != null)     OpModeManagerImpl.this.opModeSwapNeeded     = opModeSwapNeeded;
      if (callToInitNeeded != null)     OpModeManagerImpl.this.callToInitNeeded     = callToInitNeeded;
      if (gamepadResetNeeded != null)   OpModeManagerImpl.this.gamepadResetNeeded   = gamepadResetNeeded;
      if (telemetryClearNeeded != null) OpModeManagerImpl.this.telemetryClearNeeded = telemetryClearNeeded;
      if (callToStartNeeded != null)    OpModeManagerImpl.this.callToStartNeeded    = callToStartNeeded;
    }

    OpModeStateTransition copy() {
      OpModeStateTransition result = new OpModeStateTransition();
      result.queuedOpModeMetadata = this.queuedOpModeMetadata;
      result.opModeSwapNeeded = this.opModeSwapNeeded;
      result.callToInitNeeded = this.callToInitNeeded;
      result.gamepadResetNeeded = this.gamepadResetNeeded;
      result.telemetryClearNeeded = this.telemetryClearNeeded;
      result.callToStartNeeded = this.callToStartNeeded;
      result.onlyTransitionIfDefaultOpModeIsRunning = this.onlyTransitionIfDefaultOpModeIsRunning;
      return result;
    }
  }

  //-----------------------------------------------------------------------------------------------
  // Static state and methods
  //-----------------------------------------------------------------------------------------------

  protected static int              matchNumber                     = 0;
  protected static volatile boolean preventDangerousHardwareAccess  = false;

  public static boolean shouldPreventDangerousHardwareAccess() {
    return preventDangerousHardwareAccess;
  }

  //------------------------------------------------------------------------------------------------
  // State
  //------------------------------------------------------------------------------------------------

  public static final String TAG = "OpModeManager";

  public static final String DEFAULT_OP_MODE_NAME    = OpModeManager.DEFAULT_OP_MODE_NAME;

  protected enum OpModeState { INIT, LOOPING }

  protected Context               context;
  protected String                activeOpModeName     = DEFAULT_OP_MODE_NAME;
  protected @Nullable OpModeInternal activeOpMode      = null;
  protected OpModeMeta            queuedOpModeMetadata = RegisteredOpModes.DEFAULT_OP_MODE_METADATA;
  protected HardwareMap           hardwareMap          = null;
  protected EventLoopManager      eventLoopManager     = null;
  protected final WeakReferenceSet<OpModeManagerNotifier.Notifications> listeners = new WeakReferenceSet<OpModeManagerNotifier.Notifications>();
  protected OpModeStuckCodeMonitor stuckMonitor        = null;
  protected boolean               peerWasConnected     = NetworkConnectionHandler.getInstance().isPeerConnected();

  protected OpModeState           opModeState          = OpModeState.INIT;
  protected boolean               opModeSwapNeeded     = false;
  protected boolean               callToInitNeeded     = false;
  protected boolean               callToStartNeeded    = false;
  protected boolean               gamepadResetNeeded   = false;
  protected boolean               telemetryClearNeeded = false;
  protected AtomicReference<OpModeStateTransition> nextOpModeState = new AtomicReference<OpModeStateTransition>(null);

  protected static final WeakHashMap<Activity,OpModeManagerImpl> mapActivityToOpModeManager = new WeakHashMap<Activity,OpModeManagerImpl>();

  //------------------------------------------------------------------------------------------------
  // Construction
  //------------------------------------------------------------------------------------------------

  // Called on FtcRobotControllerService thread
  public OpModeManagerImpl(Activity activity, HardwareMap hardwareMap) {
    this.hardwareMap = hardwareMap;

    // switch to the default op mode
    initOpMode(DEFAULT_OP_MODE_NAME);

    this.context = activity;
    synchronized (mapActivityToOpModeManager) {
      mapActivityToOpModeManager.put(activity, this);
      }

  }

  public static OpModeManagerImpl getOpModeManagerOfActivity(Activity activity) {
    synchronized (mapActivityToOpModeManager) {
      return mapActivityToOpModeManager.get(activity);
      }
    }

  // called from the RobotSetupRunnable.run thread
  public void init(EventLoopManager eventLoopManager) {
    this.stuckMonitor = new OpModeStuckCodeMonitor();
    this.eventLoopManager = eventLoopManager;
  }

  public void teardown() {
    this.stuckMonitor.shutdown();
  }

  //------------------------------------------------------------------------------------------------
  // Notifications
  //------------------------------------------------------------------------------------------------

  @Override
  public @Nullable OpMode registerListener(OpModeManagerNotifier.Notifications listener) {
    synchronized (this.listeners) {
      this.listeners.add(listener);
      return (OpMode) this.activeOpMode;
    }
  }

  @Override
  public void unregisterListener(OpModeManagerNotifier.Notifications listener) {
    synchronized (this.listeners) {
      this.listeners.remove(listener);
    }
  }

  protected void setActiveOpMode(OpMode opMode, String activeOpModeName) {
    synchronized (this.listeners) {
      this.activeOpMode = opMode;
      this.activeOpModeName = activeOpModeName;
    }
  }

  //------------------------------------------------------------------------------------------------
  // Accessors
  //------------------------------------------------------------------------------------------------

  // called from the RobotSetupRunnable.run thread
  public void setHardwareMap(HardwareMap hardwareMap) {
    this.hardwareMap = hardwareMap;
  }

  public HardwareMap getHardwareMap() {
    return hardwareMap;
  }

  public RobotState getRobotState() {
    if (eventLoopManager != null) {
      return eventLoopManager.state;
    }
    return RobotState.UNKNOWN;
  }

  //------------------------------------------------------------------------------------------------
  // OpMode management
  //------------------------------------------------------------------------------------------------

  // called on DS receive thread, event loop thread
  public String getActiveOpModeName() { return activeOpModeName; }

  // called on the event loop thread
  public OpMode getActiveOpMode() {
    return (OpMode) activeOpMode;
  }

  protected void doMatchLoggingWork(String opModeName, boolean isSystemOpMode) {
    if (isSystemOpMode) {
      RobotLog.stopMatchLogging();
    } else {
      try {
        RobotLog.startMatchLogging(context, opModeName, matchNumber);
      } catch (RobotCoreException e) {
        RobotLog.ee(TAG, "Could not start match logging");
        e.printStackTrace();
      }
    }
  }

  public void setMatchNumber(int matchNumber) {
    OpModeManagerImpl.matchNumber = matchNumber;
  }

  public void initOpMode(String opModeName) {
    initOpMode(opModeName, false);
  }

  // May be called from any thread
  public void initOpMode(String opModeName, boolean onlyInitIfDefaultIsRunning) {
    boolean isDefaultOpMode = opModeName.equals(DEFAULT_OP_MODE_NAME);
    preventDangerousHardwareAccess = isDefaultOpMode;

    // This may get called before the Op Modes have been registered, so let's special-case the
    // default Op Mode, since that one MUST work
    OpModeMeta meta;
    if (isDefaultOpMode) { meta = RegisteredOpModes.DEFAULT_OP_MODE_METADATA; }
    else { meta = RegisteredOpModes.getInstance().getOpModeMetadata(opModeName); }

    if (meta == null) {
      RobotLog.ee(TAG, "initOpMode(): Was unable to find metadata for Op Mode %s", opModeName);
      return;
    }

    boolean isSystem = meta.flavor == OpModeMeta.Flavor.SYSTEM;

    OpModeStateTransition newState = new OpModeStateTransition();
    newState.queuedOpModeMetadata = meta;
    newState.opModeSwapNeeded = true;
    newState.callToInitNeeded = true;
    newState.gamepadResetNeeded = true;
    newState.telemetryClearNeeded = !isDefaultOpMode;  // no semantic need to clear if we're just stopping
    newState.callToStartNeeded = isSystem; // System Op Modes should auto-transition to Run mode
    newState.onlyTransitionIfDefaultOpModeIsRunning = onlyInitIfDefaultIsRunning;

    // We *insist* on becoming the new state
    nextOpModeState.set(newState);
  }

  // called on DS receive thread
  public void startActiveOpMode() {
    // We're happy to modify an existing (init?) state to then do a start
    OpModeStateTransition existingState = null;
    for (;;) {
      OpModeStateTransition newState;
      if (existingState != null) {
        newState = existingState.copy();
      } else {
        newState = new OpModeStateTransition();
      }
      newState.callToStartNeeded = true;
      if (nextOpModeState.compareAndSet(existingState, newState))
        break;
      Thread.yield();
      existingState = nextOpModeState.get();
    }
  }

  // called on the event loop thread
  public void stopActiveOpMode() {
    callActiveOpModeStop();
    RobotLog.stopMatchLogging();
    initOpMode(DEFAULT_OP_MODE_NAME);
  }

  // called on the event loop thread
  public void runActiveOpMode(Gamepad[] opModeGamepads, Gamepad latestGamepad1Data, Gamepad latestGamepad2Data) {

    // Apply a state transition if one is pending
    OpModeStateTransition transition = nextOpModeState.getAndSet(null);
    if (transition != null)
      transition.apply(); // Sets up the transition to happen later in this method (runActiveOpMode)

    if (activeOpMode != null) {
      activeOpMode.gamepad1 = opModeGamepads[0];
      activeOpMode.gamepad2 = opModeGamepads[1];

      if (!latestGamepad1Data.equals(activeOpMode.previousGamepad1Data) ||
              !latestGamepad2Data.equals(activeOpMode.previousGamepad2Data)) {
        activeOpMode.newGamepadDataAvailable(latestGamepad1Data, latestGamepad2Data);
        activeOpMode.previousGamepad1Data = latestGamepad1Data;
        activeOpMode.previousGamepad2Data = latestGamepad2Data;
      }
    }


    // Robustly ensure that gamepad state from previous Op Modes doesn't
    // leak into new Op Modes.
    if (gamepadResetNeeded) {
      opModeGamepads[0].reset();
      opModeGamepads[1].reset();

      // So basically the only reason that this exists is so that rumble effects will work
      // if no stick or button has been touched on the gamepad yet. The thing is, sending
      // a rumble effect relies on the 'user' field of the gamepad having been set, but that
      // was just nuked in the reset above. And since the Driver Station doesn't transmit gamepads
      // when they are idle, the 'user' field will stay unset until the user touches the gamepad.
      // Hence, any rumble commands would be sent with an uninitialized user, and thus would not
      // work. And we can't just make the DS retransmit the gamepads when it issues a start OpMode
      // command, either: since we're using UDP we have no guarantee of command delivery order.
      // So instead we just have this little workaround...
      opModeGamepads[0].setUserForEffects(GamepadUser.ONE.id);
      opModeGamepads[1].setUserForEffects(GamepadUser.TWO.id);
      gamepadResetNeeded = false;
    }

    if (telemetryClearNeeded && this.eventLoopManager != null) {
      // We clear telemetry once 'init' is pressed in order to
      // ensure that stale telemetry from previous OpMode runs is
      // no longer (confusingly) on the screen.
      TelemetryMessage telemetry = new TelemetryMessage();
      telemetry.addData("\0", "");
      this.eventLoopManager.sendTelemetryData(telemetry);
      telemetryClearNeeded = false;

      // Similarly, we clear the global error/warning in order to
      // ensure that stale messages from previous OpMode runs are
      // no longer (confusingly) on the screen.
      RobotLog.clearGlobalErrorMsg();
      RobotLog.clearGlobalWarningMsg();
    }

    if (opModeSwapNeeded) {
      callActiveOpModeStop();
      boolean swapSucceeded = performOpModeSwap();
      if (swapSucceeded) {
        opModeSwapNeeded = false;
      } else {
        failedToSwapOpMode();
        // GET OUT OF DODGE. failedToSwapOpMode() just set up a new OpMode transition, that NEEDS to
        // be applied before any of the rest of this method runs.
        return;
      }
    }

    if (callToInitNeeded && activeOpMode != null) {
      activeOpMode.gamepad1    = opModeGamepads[0];
      activeOpMode.gamepad2    = opModeGamepads[1];
      activeOpMode.hardwareMap = hardwareMap;
      activeOpMode.internalOpModeServices = this;

      boolean initializingDefaultOpMode = activeOpMode instanceof DefaultOpMode;

      preventDangerousHardwareAccess = initializingDefaultOpMode;

      // The point about resetting the hardware is to have it in the same state
      // every time for the *user's* code so that they can simplify their initialization
      // logic. There's no point in bothering / spending the time for the default opmode.
      if (!initializingDefaultOpMode) {
        resetHardwareForOpMode();
      }

      ((OpMode) activeOpMode).resetRuntime();
      callActiveOpModeInit();
      opModeState = OpModeState.INIT;
      callToInitNeeded = false;
      NetworkConnectionHandler.getInstance().sendCommand(new Command(RobotCoreCommandList.CMD_NOTIFY_INIT_OP_MODE, activeOpModeName)); // send *truth* to DS
    }

    else if (callToStartNeeded) {
      callActiveOpModeStart();
      opModeState = OpModeState.LOOPING;
      callToStartNeeded = false;
      NetworkConnectionHandler.getInstance().sendCommand(new Command(RobotCoreCommandList.CMD_NOTIFY_RUN_OP_MODE, activeOpModeName)); // send *truth* to DS
    }

    else if (opModeState == OpModeState.INIT || opModeState == OpModeState.LOOPING) {
      checkOnActiveOpMode();

      // If the Driver Station just now connected, make sure it knows the status of the current Op Mode
      boolean peerIsConnected = NetworkConnectionHandler.getInstance().isPeerConnected();
      if (peerIsConnected && !peerWasConnected) {
        String command;
        if (opModeState == OpModeState.INIT) {
          command = RobotCoreCommandList.CMD_NOTIFY_INIT_OP_MODE;
        } else {
          // opModeState must be LOOPING
          command = RobotCoreCommandList.CMD_NOTIFY_RUN_OP_MODE;
        }
        NetworkConnectionHandler.getInstance().sendCommand(new Command(command, activeOpModeName));
      }

      peerWasConnected = peerIsConnected;
    }
  }

  // resets the hardware to the state expected at the start of an opmode
  protected void resetHardwareForOpMode() {
    // First reset all instances of LynxModule and LynxController, so that all HardwareDevice
    // classes that use a LynxController subclass get the final say
    Set<HardwareDevice> devicesToBeResetFirst = new HashSet<>();
    devicesToBeResetFirst.addAll(hardwareMap.getAll(RobotCoreLynxModule.class));
    devicesToBeResetFirst.addAll(hardwareMap.getAll(RobotCoreLynxController.class));

    for (HardwareDevice device: devicesToBeResetFirst) {
      device.resetDeviceConfigurationForOpMode();
    }

    for (HardwareDevice device: hardwareMap.unsafeIterable()) {
      if (!devicesToBeResetFirst.contains(device)) {
        device.resetDeviceConfigurationForOpMode();
      }
    }
  }

  /** @return {@code true} if the Op Mode swap succeeded */
  private boolean performOpModeSwap() {
    String newOpModeName = queuedOpModeMetadata.name;
    RobotLog.i("Attempting to switch to op mode " + newOpModeName);

    OpMode opMode = RegisteredOpModes.getInstance().getOpMode(newOpModeName);
    if (opMode != null) {
      setActiveOpMode(opMode, newOpModeName);
      doMatchLoggingWork(newOpModeName, queuedOpModeMetadata.flavor == OpModeMeta.Flavor.SYSTEM);
      return true;
    } else {
      return false;
    }
  }

  private void failedToSwapOpMode() {
    RobotLog.ee(TAG, "Unable to start op mode " + queuedOpModeMetadata.name);
    initOpMode(DEFAULT_OP_MODE_NAME);
  }

  protected void callActiveOpModeStop() {
    if (activeOpMode == null) { return; }
    preventDangerousHardwareAccess = true;

    // Attempt to put all REV Hubs in a safe state
    for (RobotCoreLynxModule lynxModule : this.hardwareMap.getAll(RobotCoreLynxModule.class)) {
      lynxModule.attemptFailSafeAndIgnoreErrors();
    }

    try {
      detectStuck(OpModeInternal.MS_BEFORE_FORCE_STOP_AFTER_STOP_REQUESTED, "stop()", new Runnable() {
        @Override public void run() {
          activeOpMode.internalStop();
        }});
    } catch (ForceStopException e) {
      //We're already stopping, nothing to do
    }

    synchronized (this.listeners) {
      for (OpModeManagerNotifier.Notifications listener : this.listeners) {
        listener.onOpModePostStop((OpMode) activeOpMode);
      }
    }
    for (HardwareDevice device : this.hardwareMap.unsafeIterable()) {
      if (device instanceof OpModeManagerNotifier.Notifications) {
        ((OpModeManagerNotifier.Notifications)device).onOpModePostStop((OpMode) activeOpMode);
      }
    }
  }

  protected void detectStuck(int msTimeout, String method, Runnable runnable) {
    detectStuck(msTimeout, method, runnable, false);
  }

  protected void detectStuck(int msTimeout, String method, Runnable runnable, boolean resetDebuggerCheck) {
    stuckMonitor.startMonitoring(msTimeout, method, resetDebuggerCheck);
    try {
      runnable.run();
    } finally {
      stuckMonitor.stopMonitoring();

      // We need to wait for the monitoring sequence to fully conclude,
      // in order to avoid possibly crashing any code that might run
      // after a force stop, before the throw flag on the network lock
      // has been retracted.
      try {
        stuckMonitor.acquired.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
      }
    }
  }

  public static class ForceStopException extends RuntimeException {}

  /** A utility class that detects infinite loops in user code */
  protected class OpModeStuckCodeMonitor {
    ExecutorService executorService = ThreadPool.newSingleThreadExecutor("OpModeStuckCodeMonitor");
    Semaphore       stopped         = new Semaphore(0);
    CountDownLatch  acquired        = null;
    boolean         debuggerDetected = false;
    int             msTimeout;
    String          method;

    public void startMonitoring(int msTimeout, String method, boolean resetDebuggerCheck) {
      // Wait for any previous monitoring to drain
      if (acquired != null) {
        try { acquired.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
      }
      this.msTimeout = msTimeout;
      this.method = method;
      stopped.drainPermits();
      acquired = new CountDownLatch(1);
      executorService.execute(new Runner());
      if (resetDebuggerCheck) {
        debuggerDetected = false;
      }
    }

    public void stopMonitoring() {
      stopped.release();
    }

    public void shutdown() {
      executorService.shutdownNow();
    }

    protected boolean checkForDebugger() {
      // Once we see a debugger, we disable timeout checking for the remainder of the OpMode
      // in order to be sure to avoid premature termination of the app.
      debuggerDetected = debuggerDetected || Debug.isDebuggerConnected();
      return debuggerDetected;
    }

    protected class Runner implements Runnable {

      final static String msgForceStoppedCommon = "User OpMode was stuck in %s, but was able to be force stopped without restarting the app. ";
      final static String msgForceStoppedPopupIterative = msgForceStoppedCommon + "It appears this was an iterative OpMode; make sure you aren't using your own loops.";
      final static String msgForceStoppedPopupLinear    = msgForceStoppedCommon + "It appears this was a linear OpMode; make sure you are calling opModeIsActive() in any loops.";

      @Override public void run() {
        boolean errorWasSet = false;
        try {
          // We won't bother timing if a debugger is attached because single stepping
          // etc in a debugger can take an arbitrarily long amount of time.
          if (checkForDebugger()) {
            return;
          }

          if (!stopped.tryAcquire(msTimeout, TimeUnit.MILLISECONDS)) {

            // Prepare for final 100ms phase where we try to force stop at bus lock level
            for (RobotCoreLynxUsbDevice dev : hardwareMap.getAll(RobotCoreLynxUsbDevice.class)) {
              dev.setThrowOnNetworkLockAcquisition(true);
            }

            // Final 100ms chance for exit before lethal injection
            if (stopped.tryAcquire(100, TimeUnit.MILLISECONDS)) {
              // Woot, we got 'em! Notify the user they dun goofed in their code
              String msgForUser;

              if(activeOpMode instanceof LinearOpMode) {
                msgForUser = msgForceStoppedPopupLinear;
              }
              else { // iterative
                msgForUser = msgForceStoppedPopupIterative;
              }

              AppUtil.getInstance().showAlertDialog(UILocation.BOTH, "OpMode Force-Stopped", String.format(msgForUser, method));

              // Clear this flag so we don't crash the default opmode
              for (RobotCoreLynxUsbDevice dev : hardwareMap.getAll(RobotCoreLynxUsbDevice.class)) {
                dev.setThrowOnNetworkLockAcquisition(false);
              }

              // We're all done here!
              return;
            }

            // Well shoot. That didn't work. On to the lethal injection, then. But, we need to clear
            // this flag to allow for the last ditch effort failsafe below
            for (RobotCoreLynxUsbDevice dev : hardwareMap.getAll(RobotCoreLynxUsbDevice.class)) {
              dev.setThrowOnNetworkLockAcquisition(false);
            }

            String message = String.format(context.getString(R.string.errorOpModeStuck), activeOpModeName, method);
            errorWasSet = RobotLog.setGlobalErrorMsg(message);
            RobotLog.e(message);

            try
            {
              /*
               * We make a last ditch effort to put any Lynx modules into failsafe
               * mode before we restart the app. This has the effect of the modules
               * entering failsafe mode up to 2500ms (though in reality >3000ms has
               * been observed) before they otherwise would as induced by the no-comms
               * timeout.
               */

              final CountDownLatch lastDitchEffortFailsafeDone = new CountDownLatch(1);

              new Thread(new Runnable() {
                @Override
                public void run() {
                  for (RobotCoreLynxUsbDevice dev : hardwareMap.getAll(RobotCoreLynxUsbDevice.class)) {
                    /*
                     * First, we lock network lock acquisitions. This has the effect
                     * of blocking any other threads from being able to acquire the network
                     * lock, and thus preventing anyone else from being able to send commands
                     * to the module behind our back.
                     *
                     * Once that's done (and that may take a bit, since there may be other
                     * guys queued ahead of us), we send failsafe commands to all the modules
                     * attached to this LynxUsbDevice (well, it does that internally, all we
                     * have to do is call failsafe())
                     *
                     * Then, notice that we DO NOT unlock network lock acquisitions. If we
                     * did, the rogue OpMode could just get right back in there and send, say,
                     * another setPower command after we *JUST* put the module into failsafe
                     * mode!
                     */
                    dev.lockNetworkLockAcquisitions();
                    dev.failSafe();
                  }
                  lastDitchEffortFailsafeDone.countDown();
                }
              }).start();

              /*
               * We only wait 250ms before proceeding with the restart anyway. That way if
               * for some reason the transmission code is in deadlock (or some other condition
               * has happened which would cause the the above to hang) we won't HANG the code
               * that's supposed to restart the app BECAUSE something hung :)
               */
              if(lastDitchEffortFailsafeDone.await(250, TimeUnit.MILLISECONDS))
              {
                RobotLog.e("Successfully sent failsafe commands to Lynx modules before app restart");
              }
              else
              {
                RobotLog.e("Timed out while sending failsafe commands to Lynx modules before app restart");
              }
            }
            /* Paranoia (in honor of Bob :D) */
            catch (Exception ignored){}

            /*
             * We are giving the Robot Controller a lethal injection, try to help its operator figure out why.
             */
            RobotLog.e("Begin thread dump");
            Map<Thread, StackTraceElement[]> traces = Thread.getAllStackTraces();
            for (Map.Entry<Thread, StackTraceElement[]> entry : traces.entrySet()) {
              RobotLog.logStackTrace(entry.getKey(), entry.getValue());
            }

            // Wait a touch for message to be seen
            AppUtil.getInstance().showToast(UILocation.BOTH, String.format(context.getString(R.string.toastOpModeStuck), method));
            Thread.sleep(1000);

            // Restart
            AppUtil.getInstance().restartApp(-1);
          }
        } catch (InterruptedException e) {
          // Shutdown complete, return
          if (errorWasSet) RobotLog.clearGlobalErrorMsg();
        } finally {
          acquired.countDown();
        }
      }
    }
  }

  protected void callActiveOpModeInit() {
    if (activeOpMode == null) { return; }
    synchronized (this.listeners) {
      for (OpModeManagerNotifier.Notifications listener : this.listeners) {
        listener.onOpModePreInit((OpMode) activeOpMode);
      }
    }
    for (HardwareDevice device : this.hardwareMap.unsafeIterable()) {
      if (device instanceof OpModeManagerNotifier.Notifications) {
        ((OpModeManagerNotifier.Notifications)device).onOpModePreInit((OpMode) activeOpMode);
      }
    }

    activeOpMode.internalInit();
  }

  protected void callActiveOpModeStart() {
    if (activeOpMode == null) { return; }
    synchronized (this.listeners) {
      for (OpModeManagerNotifier.Notifications listener : this.listeners) {
        listener.onOpModePreStart((OpMode) activeOpMode);
      }
    }
    for (HardwareDevice device : this.hardwareMap.unsafeIterable()) {
      if (device instanceof OpModeManagerNotifier.Notifications) {
        ((OpModeManagerNotifier.Notifications)device).onOpModePreStart((OpMode) activeOpMode);
      }
    }

    // Check for any exceptions that occurred during the init phase
    try {
      activeOpMode.internalThrowOpModeExceptionIfPresent();
    } catch (ForceStopException e) {
        /*
         * OpMode ran away during the init phase, but we were able to force stop him.
         * Get out of dodge with a switch to the StopRobot OpMode.
         *
         * (We also now use ForceStopException to support {@link OpMode#terminateOpModeNow()})
         */
      initOpMode(DEFAULT_OP_MODE_NAME);
    } catch (Exception e) {
      initOpMode(DEFAULT_OP_MODE_NAME);
      handleUserCodeException(e);
    }

    // Actually put the OpMode into the Start phase
    activeOpMode.internalStart();
  }

  protected void checkOnActiveOpMode() {
    if (activeOpMode == null) { return; }
    try {
      activeOpMode.internalThrowOpModeExceptionIfPresent();
    } catch (ForceStopException e) {
      /*
       * OpMode ran away, but we were able to force stop him.
       * Get out of dodge with a switch to the StopRobot OpMode.
       *
       * (We also now use ForceStopException to support {@link OpMode#terminateOpModeNow()})
       */
      initOpMode(DEFAULT_OP_MODE_NAME);
    } catch (Exception e) {
      initOpMode(DEFAULT_OP_MODE_NAME);
      handleUserCodeException(e);
    }

    activeOpMode.internalOnEventLoopIteration();
  }

  protected void handleUserCodeException(Exception e) {
    RobotLog.ee(TAG, e, "User code threw an uncaught exception");
    handleSendStacktrace(e);
  }

  protected void handleSendStacktrace(Exception e) {
    // Get the trace as a string
    String stacktrace = Log.getStackTraceString(e);
    String[] lines = stacktrace.split("\n");
    StringBuilder builder = new StringBuilder();

    // Truncate at 15 lines
    for(int i = 0; i < Math.min(lines.length, 15); i++) {
      builder.append(lines[i]).append("\n");
    }

    // Send it off to the DS
    NetworkConnectionHandler.getInstance().sendCommand(new Command(RobotCoreCommandList.CMD_SHOW_STACKTRACE, builder.toString()));
  }

  //------------------------------------------------------------------------------------------------
  // OpModeServices
  //------------------------------------------------------------------------------------------------

  /** For the use of {@link TelemetryImpl}. */
  public static void updateTelemetryNow(OpMode opMode, TelemetryMessage telemetry) {
    opMode.internalUpdateTelemetryNow(telemetry);
  }

  @Override public void refreshUserTelemetry(TelemetryMessage telemetry, double sInterval) {
    this.eventLoopManager.getEventLoop().refreshUserTelemetry(telemetry, sInterval);
  }

  /**
   * Requests that an OpMode be stopped.
   * @see OpMode#requestOpModeStop()
   */
  @Override public void requestOpModeStop(OpMode opModeToStopIfActive) {
    // We have two basic concerns: (a) is the indicated opMode the active one, and (b) we might
    // here be running on literally any thread, including the loop() thread or a linear OpMode's
    // thread.
    this.eventLoopManager.getEventLoop().requestOpModeStop(opModeToStopIfActive);
    }

  //------------------------------------------------------------------------------------------------
  // Default OpMode
  //------------------------------------------------------------------------------------------------

  /**
   * {@link DefaultOpMode} is the opmode that the system runs when no user opmode is active.
   * Note that it's not necessarily the case that this opmode runs when a user opmode stops: there
   * are situations in which we can transition directly for one user opmode to another.
   */
  @SuppressWarnings("WeakerAccess")
  public static class DefaultOpMode extends OpMode {

    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    private static final long SAFE_WAIT_NANOS = 100 * ElapsedTime.MILLIS_IN_NANO;  //  100 mSec = 100,000,000 nSec

    private long nanoNextSafe;
    private boolean firstTimeRun = true;
    private ElapsedTime blinkerTimer = new ElapsedTime();

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    public DefaultOpMode() {
      firstTimeRun = true;
    }

    //----------------------------------------------------------------------------------------------
    // Loop operations
    //----------------------------------------------------------------------------------------------

    @Override
    public void init() {
      startSafe();
      telemetry.addData("Status", "Robot is stopping");
    }

    @Override
    public void init_loop() {
      staySafe();
      telemetry.addData("Status", "Robot is stopped");
    }

    @Override
    public void loop() {
      staySafe();
      telemetry.addData("Status", "Robot is stopped");
    }

    @Override
    public void stop() {
      // take no action
    }

    private boolean isLynxDevice(HardwareDevice device) {
      return device.getManufacturer() == HardwareDevice.Manufacturer.Lynx;
    }
    private boolean isLynxDevice(Object o) {
      return isLynxDevice((HardwareDevice)o);
    }

    /***
     * Initiate the robot safe mode by setting motors off (including CR Servos)
     */
    private void startSafe()  {

      // Set all motor powers to zero. The implementation here will also stop any CRServos.
      for (DcMotorSimple motor : hardwareMap.getAll(DcMotorSimple.class)) {
        // Avoid enabling servos if they are already zero power
        if (motor.getPower() != 0)
          motor.setPower(0);
      }

      // Determine how long to wait before we send disables
      // First time this is run after starting the app should be very short to avoid a glitch
      if (firstTimeRun) {
        firstTimeRun = false;
        nanoNextSafe = System.nanoTime();
        blinkerTimer.reset();
      }
      else
        nanoNextSafe = System.nanoTime() + SAFE_WAIT_NANOS;
    }

    /***
     *  Maintain a safe robot by periodically setting all output devices to safe state
     */
    private void staySafe() {
      // periodically set:
      //  DcMotor run mode to something reasonable
      //  Servos to disabled
      //  LEDs to off

      if (System.nanoTime() > nanoNextSafe) {

        // shutdown all lynx devices. that's all we need to do for these devices
        for (RobotCoreLynxUsbDevice device : hardwareMap.getAll(RobotCoreLynxUsbDevice.class)) {
          device.failSafe();
        }

        // power down the servos
        for (ServoController servoController : hardwareMap.getAll(ServoController.class)) {
          if (!isLynxDevice(servoController)) {
            servoController.pwmDisable();
          }
        }

        // Set motors to safe state
        for (DcMotor dcMotor : hardwareMap.getAll(DcMotor.class)) {
          if (!isLynxDevice(dcMotor)) {
            dcMotor.setPower(0.0);
            dcMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
          }
        }

        // turn of light sensors
        for (LightSensor light : hardwareMap.getAll(LightSensor.class)) {
          light.enableLed(false);
        }

        // Restart the safe timer
        nanoNextSafe = System.nanoTime() + SAFE_WAIT_NANOS;

        // This is temporary
        /*if (blinkerTimer.seconds() > 10) {
          for (Blinker blinker : hardwareMap.getAll(Blinker.class)) {
              RobotLog.vv(TAG, "resetting blinker: %s", blinker);
              blinker.setPattern(blinker.getPattern());
            }
          blinkerTimer.reset();
        }*/
      }
    }
  }
}

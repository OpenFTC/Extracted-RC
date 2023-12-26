package com.qualcomm.robotcore.eventloop.opmode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryInternal;
import org.firstinspires.ftc.robotcore.internal.system.Assert;

/**
 * Base class for user defined linear operation modes (linear OpModes).
 * <p>
 * This class derives from {@link OpMode}, but you are not able to
 * override the methods defined in OpMode.
 */
@SuppressWarnings("unused")
public abstract class LinearOpMode extends OpMode {

  //------------------------------------------------------------------------------------------------
  // State
  //------------------------------------------------------------------------------------------------

  private volatile boolean  userMethodReturned = false;
  private volatile boolean  userMonitoredForStart = false;
  private final Object      runningNotifier = new Object();

  //------------------------------------------------------------------------------------------------
  // Construction
  //------------------------------------------------------------------------------------------------

  /**
   * LinearOpMode constructor
   */
  public LinearOpMode() {
  }

  //------------------------------------------------------------------------------------------------
  // Operations
  //------------------------------------------------------------------------------------------------

  /**
   * Override this method and place your code here.
   * <p>
   * Please do not catch {@link InterruptedException}s that are thrown in your OpMode
   * unless you are doing it to perform some brief cleanup, in which case you must exit
   * immediately afterward. Once the OpMode has been told to stop, your ability to
   * control hardware will be limited.
   *
   * @throws InterruptedException When the OpMode is stopped while calling a method
   *                              that can throw {@link InterruptedException}
   */
  abstract public void runOpMode() throws InterruptedException;

  /**
   * Pauses until the play button has been pressed (or until the current thread
   * gets interrupted, which typically indicates that the OpMode has been stopped).
   */
  public void waitForStart() {
    while (!isStarted()) {
      synchronized (runningNotifier) {
        try {
          runningNotifier.wait();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          return;
        }
      }
    }
  }

  /**
   * Puts the current thread to sleep for a bit as it has nothing better to do. This allows other
   * threads in the system to run.
   *
   * <p>One can use this method when you have nothing better to do in your code as you await state
   * managed by other threads to change. Calling idle() is entirely optional: it just helps make
   * the system a little more responsive and a little more efficient.</p>
   *
   * @see #opModeIsActive()
   */
  public final void idle() {
    // Otherwise, yield back our thread scheduling quantum and give other threads at
    // our priority level a chance to run
    Thread.yield();
  }

  /**
   * Sleeps for the given amount of milliseconds, or until the thread is interrupted (which usually
   * indicates that the OpMode has been stopped).
   * <p>This is simple shorthand for {@link Thread#sleep(long) sleep()}, but it does not throw {@link InterruptedException}.</p>
   *
   * @param milliseconds amount of time to sleep, in milliseconds
   * @see Thread#sleep(long)
   */
  public final void sleep(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Determine whether this OpMode is in the Run phase (meaning it has been started and not yet told
   * to stop). For safety reasons, the Run phase is the only time that the robot should move freely,
   * so if this method returns {@code false}, the robot should not make any significant movements.
   *
   * <p>If this method returns false after {@link #waitForStart()} has previously been called, you
   * should break out of any loops and allow the OpMode to exit at its earliest convenience.</p>
   *
   * <p>Note that this method calls {@link #idle()} internally.</p>
   *
   * @return Whether the OpMode is in the Run phase and the robot is allowed to move freely.
   * @see #runOpMode()
   * @see #isStarted()
   * @see #isStopRequested()
   */
  public final boolean opModeIsActive() {
    boolean isActive = !this.isStopRequested() && this.isStarted();
    if (isActive) {
      idle();
    }
    return isActive;
  }

  /**
   * Determine whether this OpMode is still in the Init phase (indicating that the play button has
   * not been pressed and the OpMode has not been stopped).
   * <p>
   * Can be used to break out of an Init loop when false is returned.
   *
   * @return Whether the OpMode is currently in the Init phase.
   */
  public final boolean opModeInInit() {
    return !isStarted() && !isStopRequested();
  }

  /**
   * Determine if the OpMode has been started (the play button has been pressed).
   *
   * <p>To avoid difficult-to-debug deadlocks, this method will also return
   * {@code true} if the current thread has been interrupted (which typically
   * indicates that the OpMode has been told to stop), even if the play
   * button has not been pressed.</p>
   *
   * @return Whether this opMode has been started or not
   * @see #opModeIsActive()
   * @see #isStopRequested()
   */
  public final boolean isStarted() {

    /*
     * What we're looking for here is that the user polled until the
     * the start condition was occurred.
     */
    if(isStarted) userMonitoredForStart = true;

    return this.isStarted || Thread.currentThread().isInterrupted();
  }

  /**
   * Determine whether the OpMode has been asked to stop.
   *
   * <p>If this method returns false, you should break out of any loops
   * and allow the OpMode to exit at its earliest convenience.</p>
   *
   * @return Whether the OpMode has been asked to stop
   * @see #opModeIsActive()
   * @see #isStarted()
   */
  public final boolean isStopRequested() {
    return this.stopRequested || Thread.currentThread().isInterrupted();
  }

  /** This method may not be overridden by linear OpModes */
  @Override final public void init() { }
  /** This method may not be overridden by linear OpModes */
  @Override final public void init_loop() { }
  /** This method may not be overridden by linear OpModes */
  @Override final public void start() { }
  /** This method may not be overridden by linear OpModes */
  @Override final public void loop() { }
  /** This method may not be overridden by linear OpModes */
  @Override final public void stop() { }

  //----------------------------------------------------------------------------------------------
  // OpModeInternal hooks (LinearOpMode MUST override ALL of them to not inherit behavior from OpMode)
  //----------------------------------------------------------------------------------------------

  // Package-private, called on the OpModeThread when the OpMode is initialized
  @Override
  final void internalRunOpMode() throws InterruptedException {
    // Do NOT call super.internalRunOpMode().

    // We need to reset these fields because BlocksOpMode (which is a subclass of LinearOpMode)
    // instances are re-used.
    userMethodReturned = false;
    userMonitoredForStart = false;

    runOpMode();
    userMethodReturned = true;
    RobotLog.d("User runOpModeMethod exited");
    requestOpModeStop();
  }

  // Package-private, called on the main event loop thread
  @Override
  final void internalOnStart() {
    synchronized (runningNotifier) {
      runningNotifier.notifyAll();
    }
  }

  // Package-private, called on the main event loop thread
  @Override
  final void internalOnEventLoopIteration() {
    time = getRuntime();

    synchronized (runningNotifier) {
      runningNotifier.notifyAll();
    }

    if (telemetry instanceof TelemetryInternal) {
      ((TelemetryInternal)telemetry).tryUpdateIfDirty();
    }
  }

  // Package-private, called on the main event loop thread
  @Override
  final void internalOnStopRequested() {
    /*
     * Is it ending because it simply... ended (e.g. end of auto), or
     * because the user failed to monitor for the start condition?
     *
     * We must check userMethodReturned, because if it didn't return,
     * but also !userMonitoredForStart, that means the opmode was aborted
     * during init. We don't want to show a warning in that case.
     */
    if(!userMonitoredForStart && userMethodReturned) {
      RobotLog.addGlobalWarningMessage("The OpMode which was just initialized ended prematurely as a result of not monitoring for the start condition. Did you forget to call waitForStart()?");
    }

    // executorService being null would indicate that this method was called on an OpMode that had not yet been initialized
    Assert.assertTrue(executorService != null);

    // For LinearOpMode, the OpMode thread needs to be interrupted, as the user may have called a
    // long-running method that can only be stopped early by interrupting the thread.
    executorService.shutdownNow();
  }

  @Override
  void newGamepadDataAvailable(Gamepad latestGamepad1Data, Gamepad latestGamepad2Data) {
    // For LinearOpMode, we want the new gamepad data to be available to the user ASAP
    // We copy the data instead of replacing the gamepad instances because the gamepad instances
    // may contain queued effect data.
    gamepad1.copy(latestGamepad1Data);
    gamepad2.copy(latestGamepad2Data);
  }
}

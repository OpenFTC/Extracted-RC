package com.qualcomm.robotcore.eventloop.opmode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryInternal;
import org.firstinspires.ftc.robotcore.internal.system.Assert;

/**
 * Base class for user defined linear operation modes (op modes).
 * <p>
 * This class derives from OpMode, but you should not override the methods from
 * OpMode.
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

  public LinearOpMode() {
  }

  //------------------------------------------------------------------------------------------------
  // Operations
  //------------------------------------------------------------------------------------------------

  /**
   * Override this method and place your code here.
   * <p>
   * Please do not swallow the InterruptedException, as it is used in cases
   * where the op mode needs to be terminated early.
   * @throws InterruptedException
   */
  abstract public void runOpMode() throws InterruptedException;

  /**
   * Pauses the Linear Op Mode until start has been pressed or until the current thread
   * is interrupted.
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
   * Sleeps for the given amount of milliseconds, or until the thread is interrupted. This is
   * simple shorthand for the operating-system-provided {@link Thread#sleep(long) sleep()} method.
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
   * Answer as to whether this opMode is active and the robot should continue onwards. If the
   * opMode is not active, the OpMode should terminate at its earliest convenience.
   *
   * <p>Note that internally this method calls {@link #idle()}</p>
   *
   * @return whether the OpMode is currently active. If this returns false, you should
   *         break out of the loop in your {@link #runOpMode()} method and return to its caller.
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
   * Can be used to break out of an Init loop when false is returned. Touching
   * Start or Stop will return false.
   *
   * @return Whether the OpMode is currently in Init. A return of false can exit
   *         an Init loop and proceed with the next action.
   */
  public final boolean opModeInInit() {
    return !isStarted() && !isStopRequested();
  }

  /**
   * Has the opMode been started?
   *
   * @return whether this opMode has been started or not
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
   * Has the the stopping of the opMode been requested?
   *
   * @return whether stopping opMode has been requested or not
   * @see #opModeIsActive()
   * @see #isStarted()
   */
  public final boolean isStopRequested() {
    return this.stopRequested || Thread.currentThread().isInterrupted();
  }

  // Prevent the non-Linear OpMode methods from being overridden
  @Override final public void init() { }
  @Override final public void init_loop() { }
  @Override final public void start() { }
  @Override final public void loop() { }
  @Override final public void stop() { }

  //----------------------------------------------------------------------------------------------
  // OpModeInternal hooks (LinearOpMode MUST override ALL of them to not inherit behavior from OpMode)
  //----------------------------------------------------------------------------------------------

  // Package-private, called on the OpModeThread when the Op Mode is initialized
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

    // executorService being null would indicate that this method was called on an Op Mode that had not yet been initialized
    Assert.assertTrue(executorService != null);

    // For LinearOpMode, the Op Mode thread needs to be interrupted, as the user may have called a
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

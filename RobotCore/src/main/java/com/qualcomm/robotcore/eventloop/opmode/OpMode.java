/* Copyright (c) 2014 Qualcomm Technologies Inc

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

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.robocol.TelemetryMessage;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.TimeUnit;

/**
 * Base class for user-defined iterative operation modes (OpModes).
 * <p>
 * The OpMode name should be unique. It will be the name displayed on the driver station. If
 * multiple OpModes have the same name, only one will be available.
 */
public abstract class OpMode extends OpModeInternal {
  /**
   * The number of seconds this OpMode has been running. This is
   * updated before every call to loop().
   * <p>
   * OpModes that extend {@link LinearOpMode} should call {@link #getRuntime()} instead.
   */
  public volatile double time = 0.0;
  // time is volatile because LinearOpMode updates this on the main event loop thread instead of
  // the OpMode thread

  // internal time tracking
  private volatile long startTime = 0; // in nanoseconds

  // Latest gamepad data (used to update gamepad1 and gamepad2 in between user code callbacks)
  private volatile Gamepad latestGamepad1Data = new Gamepad();
  private volatile Gamepad latestGamepad2Data = new Gamepad();

  /**
   * OpMode constructor
   */
  public OpMode() {
    startTime = System.nanoTime();
  }

  /**
   * User-defined init method
   * <p>
   * This method will be called once, when the INIT button is pressed.
   */
  abstract public void init();

  /**
   * User-defined init_loop method
   * <p>
   * This method will be called repeatedly during the period between when
   * the init button is pressed and when the play button is pressed (or the
   * OpMode is stopped).
   * <p>
   * This method is optional. By default, this method takes no action.
   */
  public void init_loop() {};

  /**
   * User-defined start method
   * <p>
   * This method will be called once, when the play button is pressed.
   * <p>
   * This method is optional. By default, this method takes no action.
   * <p>
   * Example usage: Starting another thread.
   */
  public void start() {};

  /**
   * User-defined loop method
   * <p>
   * This method will be called repeatedly during the period between when
   * the play button is pressed and when the OpMode is stopped.
   */
  abstract public void loop();

  /**
   * User-defined stop method
   * <p>
   * This method will be called once, when this OpMode is stopped.
   * <p>
   * Your ability to control hardware from this method will be limited.
   * <p>
   * This method is optional. By default, this method takes no action.
   */
  public void stop() {};

  /**
   * Immediately stops execution of the calling OpMode and transitions to the STOP state.
   * No further code in the OpMode will execute once this has been called.
   */
  public final void terminateOpModeNow() {
    throw new OpModeManagerImpl.ForceStopException();
  }

  /**
   * Gets the number of seconds this OpMode has been running.
   * <p>
   * This method has sub-millisecond accuracy.
   *
   * @return number of seconds this OpMode has been running
   */
  public double getRuntime() {
    final double NANOSECONDS_PER_SECOND = TimeUnit.SECONDS.toNanos(1);
    return (System.nanoTime() - startTime) / NANOSECONDS_PER_SECOND;
  }

  /**
   * Resets the runtime to zero.
   */
  public void resetRuntime() {
    startTime = System.nanoTime();
  }

  //----------------------------------------------------------------------------------------------
  // Telemetry management
  //----------------------------------------------------------------------------------------------

  /**
   * Refreshes the user's telemetry on the driver station with the contents of the provided telemetry
   * object if a nominal amount of time has passed since the last telemetry transmission. Once
   * transmitted, the contents of the telemetry object are (by default) cleared.
   *
   * @param telemetry the telemetry data to transmit
   * @see #telemetry
   * @see Telemetry#update()
   */
  public void updateTelemetry(Telemetry telemetry) {
    telemetry.update();
  }

  //----------------------------------------------------------------------------------------------
  // Safety Management
  //----------------------------------------------------------------------------------------------

  /** OpModes no longer have a time limit for init() */
  @Deprecated public int msStuckDetectInit     = 5000;
  /** OpModes no longer have a time limit for init_loop()  */
  @Deprecated public int msStuckDetectInitLoop = 5000;
  /** OpModes no longer have a time limit for start()  */
  @Deprecated public int msStuckDetectStart    = 5000;
  /** OpModes no longer have a time limit for loop() */
  @Deprecated public int msStuckDetectLoop     = 5000;

  //----------------------------------------------------------------------------------------------
  // OpModeInternal hooks
  //----------------------------------------------------------------------------------------------

  // Package-private, called on the OpModeThread when the OpMode is initialized
  // Doesn't actually throw InterruptedException, but the LinearOpMode version does
  @Override
  void internalRunOpMode() throws InterruptedException {
    // If user code in an iterative OpMode throws an exception at any point, no further callback
    // methods will be called because this function will immediately exit.

    // Until we delete the deprecated hooks entirely, we keep calling them.
    internalPreInit();

    init();

    while (!isStarted && !stopRequested) {
      internalPreUserCode();
      init_loop();
      internalPostUserCode();
      // Until we delete the deprecated hooks entirely, we keep calling them.
      internalPostInitLoop();

      //noinspection BusyWait
      Thread.sleep(1);
    }

    if (isStarted) {
      internalPreUserCode();
      start();
      internalPostUserCode();

      while (!stopRequested) {
        internalPreUserCode();
        loop();
        internalPostUserCode();
        // Until we delete the deprecated hooks entirely, we keep calling them.
        internalPostLoop();

        //noinspection BusyWait
        Thread.sleep(1);
      }
    }


    internalPreUserCode();
    stop();
    internalPostUserCode();
  }

  @Override
  void newGamepadDataAvailable(Gamepad latestGamepad1Data, Gamepad latestGamepad2Data) {
    // Save the gamepad data for use after the current user method finishes running
    this.latestGamepad1Data = latestGamepad1Data;
    this.latestGamepad2Data = latestGamepad2Data;
  }

  //----------------------------------------------------------------------------------------------
  // Internal
  //----------------------------------------------------------------------------------------------

  /**
   * This is an internal SDK method, not intended for use by user opmodes.
   *
   * @param telemetry the telemetry data to transmit
   * @see #telemetry
   * @see Telemetry#update()
   */
  public final void internalUpdateTelemetryNow(TelemetryMessage telemetry) {
    this.internalOpModeServices.refreshUserTelemetry(telemetry, 0);
  }

  private void internalPreUserCode() {
    time = getRuntime();
    // We copy the gamepad data instead of replacing the gamepad instances because the gamepad
    // instances may contain queued effect data.
    gamepad1.copy(latestGamepad1Data);
    gamepad2.copy(latestGamepad2Data);
  }

  private void internalPostUserCode() {
    telemetry.update();
  }

  @Deprecated public void internalPreInit() { }
  @Deprecated public void internalPostInitLoop() { }
  @Deprecated public void internalPostLoop() { }
}

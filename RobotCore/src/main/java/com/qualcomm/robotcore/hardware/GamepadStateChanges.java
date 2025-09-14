/*
Copyright (c) 2024 Miriam Sinton-Remes

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of FIRST nor the names of its contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.qualcomm.robotcore.hardware;

/**
 * Monitors changes to buttons on a Gamepad.
 * <p>
 * wasPressed() will return true if the button was pressed since the last call
 * of that method.
 * <p>
 * wasReleased() will return ture if the button was released since the last call
 * of that method.
 * <p>
 * @see Gamepad
 */
class GamepadStateChanges {
    protected ButtonStateMonitor dpadUp = new ButtonStateMonitor();
    protected ButtonStateMonitor dpadDown = new ButtonStateMonitor();
    protected ButtonStateMonitor dpadLeft = new ButtonStateMonitor();
    protected ButtonStateMonitor dpadRight = new ButtonStateMonitor();
    protected ButtonStateMonitor a = new ButtonStateMonitor();
    protected ButtonStateMonitor b = new ButtonStateMonitor();
    protected ButtonStateMonitor x = new ButtonStateMonitor();
    protected ButtonStateMonitor y = new ButtonStateMonitor();
    protected ButtonStateMonitor guide = new ButtonStateMonitor();
    protected ButtonStateMonitor start = new ButtonStateMonitor();
    protected ButtonStateMonitor back = new ButtonStateMonitor();
    protected ButtonStateMonitor leftBumper = new ButtonStateMonitor();
    protected ButtonStateMonitor rightBumper = new ButtonStateMonitor();
    protected ButtonStateMonitor leftStickButton = new ButtonStateMonitor();
    protected ButtonStateMonitor rightStickButton = new ButtonStateMonitor();
    protected ButtonStateMonitor circle = new ButtonStateMonitor();
    protected ButtonStateMonitor cross = new ButtonStateMonitor();
    protected ButtonStateMonitor triangle = new ButtonStateMonitor();
    protected ButtonStateMonitor square = new ButtonStateMonitor();
    protected ButtonStateMonitor share = new ButtonStateMonitor();
    protected ButtonStateMonitor options = new ButtonStateMonitor();
    protected ButtonStateMonitor touchpad = new ButtonStateMonitor();
    protected ButtonStateMonitor ps = new ButtonStateMonitor();

    protected class ButtonStateMonitor {
        private boolean lastPressed = false;
        private boolean pressNotify = false;
        private boolean releaseNotify = false;

        private void update(boolean nowPressed) {
            if (!lastPressed && nowPressed) {
                pressNotify = true;
            }
            if (lastPressed && !nowPressed) {
                releaseNotify = true;
            }
            lastPressed = nowPressed;
        }

        protected boolean wasPressed() {
            boolean pressed = pressNotify;
            pressNotify = false;
            return pressed;
        }

        protected boolean wasReleased() {
            boolean released = releaseNotify;
            releaseNotify = false;
            return released;
        }
    }

    protected void updateAllButtons(Gamepad gamepad) {
        dpadUp.update(gamepad.dpad_up);
        dpadDown.update(gamepad.dpad_down);
        dpadLeft.update(gamepad.dpad_left);
        dpadRight.update(gamepad.dpad_right);
        a.update(gamepad.a);
        b.update(gamepad.b);
        x.update(gamepad.x);
        y.update(gamepad.y);
        guide.update(gamepad.guide);
        start.update(gamepad.start);
        back.update(gamepad.back);
        leftBumper.update(gamepad.left_bumper);
        rightBumper.update(gamepad.right_bumper);
        leftStickButton.update(gamepad.left_stick_button);
        rightStickButton.update(gamepad.right_stick_button);
        circle.update(gamepad.circle);
        cross.update(gamepad.cross);
        triangle.update(gamepad.triangle);
        square.update(gamepad.square);
        share.update(gamepad.share);
        options.update(gamepad.options);
        touchpad.update(gamepad.touchpad);
        ps.update(gamepad.ps);
    }
}

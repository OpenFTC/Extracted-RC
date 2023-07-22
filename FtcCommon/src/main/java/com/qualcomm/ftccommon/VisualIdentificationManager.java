/*
Copyright (c) 2023 REV Robotics

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of REV Robotics nor the names of its contributors may be used to
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
package com.qualcomm.ftccommon;

import com.qualcomm.ftccommon.configuration.USBScanManager;
import com.qualcomm.hardware.lynx.LynxUsbDevice;
import com.qualcomm.hardware.lynx.LynxUsbDevice.SystemOperationHandle;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;

import org.firstinspires.ftc.robotcore.internal.network.NetworkConnectionHandler;
import org.firstinspires.ftc.robotcore.internal.network.PeerStatusCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class VisualIdentificationManager {
    private static final VisualIdentificationManager instance = new VisualIdentificationManager();
    private static final String TAG = "VisualIDManager";

    public static VisualIdentificationManager getInstance() { return instance; }

    private final Map<SerialNumber, SystemOperationHandle> identificationsInProgressMap = new HashMap<>();

    // Private constructor to enforce singleton
    private VisualIdentificationManager() {
        NetworkConnectionHandler.getInstance().registerPeerStatusCallback(new PeerStatusCallback() {
            @Override
            public void onPeerConnected() { /* ignore */ }

            @Override
            public void onPeerDisconnected() {
                // Stop visually identifying all devices, since we no longer expect the DS to tell us to stop
                synchronized (VisualIdentificationManager.this) {
                    RobotLog.vv(TAG, "Driver Station disconnected. Stopping all visual identifications.");
                    for (SystemOperationHandle handle: identificationsInProgressMap.values()) {
                        try {
                            handle.performSystemOperation(module -> module.visuallyIdentify(false), 200, TimeUnit.MILLISECONDS);
                        } catch (RobotCoreException | TimeoutException e) {
                            RobotLog.ee(TAG, e, "Failed to stop visual identification when DS disconnected");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        handle.close();
                    }
                    identificationsInProgressMap.clear();
                }
            }
        });
    }

    public synchronized void handleCommandVisuallyIdentify(CommandList.CmdVisuallyIdentify command) {
        final SerialNumber serial = command.usbSerialNumber;
        final int parentAddress = command.parentModuleAddress;
        final int address = command.moduleAddress;
        final boolean shouldIdentify = command.shouldIdentify;
        RobotLog.vv(TAG, "Setting visual identification status for (serial=%s parentAddress=%d address=%d) to %b", serial, parentAddress, address, shouldIdentify);

        SystemOperationHandle sysopHandle = identificationsInProgressMap.get(serial);

        try {
            if (sysopHandle == null) {
                LynxUsbDevice lynxUsb = (LynxUsbDevice) USBScanManager.getInstance().getDeviceManager().createLynxUsbDevice(serial.getScannableDeviceSerialNumber(), null);
                sysopHandle = lynxUsb.keepConnectedModuleAliveForSystemOperations(command.moduleAddress, command.parentModuleAddress);
            }

            sysopHandle.performSystemOperation(module -> module.visuallyIdentify(shouldIdentify), 200, TimeUnit.MILLISECONDS);
        } catch (RobotCoreException | InterruptedException | TimeoutException e) {
            RobotLog.ee(TAG, e, "Failed to visually identify REV Hub");
        }

        if (shouldIdentify) {
            if (sysopHandle != null) { identificationsInProgressMap.put(serial, sysopHandle); }
        } else {
            identificationsInProgressMap.remove(serial);
            if (sysopHandle != null) { sysopHandle.close(); }
        }
    }
}

/*
Copyright (c) 2017 Robert Atkinson

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.hardware.lynx.commands.LynxMessage;
import com.qualcomm.robotcore.eventloop.SyncdDevice;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Engagable;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.LynxModuleDescription;
import com.qualcomm.robotcore.hardware.LynxModuleMetaList;
import com.qualcomm.robotcore.hardware.RobotCoreLynxUsbDevice;
import com.qualcomm.robotcore.hardware.usb.RobotUsbDevice;
import com.qualcomm.robotcore.hardware.usb.RobotUsbModule;
import com.qualcomm.robotcore.util.GlobalWarningSource;

import com.qualcomm.robotcore.util.SerialNumber;
import org.firstinspires.ftc.robotcore.external.Consumer;
import org.firstinspires.ftc.robotcore.internal.network.RobotCoreCommandList;
import org.firstinspires.ftc.robotcore.internal.ui.ProgressParameters;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * The working interface to Lynx USB Devices. Separating out the interface like this allows
 * us to create delegators where we need to.
 */
public interface LynxUsbDevice extends RobotUsbModule, GlobalWarningSource, RobotCoreLynxUsbDevice, HardwareDevice, SyncdDevice, Engagable
    {
    class SystemOperationHandle
        {
        protected final LynxUsbDeviceImpl lynxUsb;
        protected final LynxModule module;
        protected final LynxModule parentModule;
        protected volatile boolean closed = false;

        /**
         * ONLY to be called by {@link LynxUsbDeviceImpl#keepConnectedModuleAliveForSystemOperations(int, int)}
         */
        protected SystemOperationHandle(LynxUsbDeviceImpl lynxUsb, LynxModule module, LynxModule parentModule)
            {
            this.lynxUsb = lynxUsb;
            this.module = module;
            this.parentModule = parentModule;

            synchronized (lynxUsb.sysOpStartStopLock)
                {
                // Incrementing these outside of the context of a specific system operation has the effect of keeping
                // the modules open even if no system operations are in progress (unless the LynxUsbDeviceImpl itself
                // is closed). The modules will be allowed to close again after close() is called on this handle
                parentModule.systemOperationCounter++;
                module.systemOperationCounter++;
                }
            }

        /**
         * The operation will run on a background thread, but this method will not return until the operation has completed.
         * <p>
         * If operation is null, this method effectively just verifies that we can communicate with the specified
         * module, throwing an exception if we cannot.
         * <p>
         * If you care about the command getting completed, specify a timeout of at least 120ms or so, since the command
         * will get resent if necessary after 100ms.
         */
        public void performSystemOperation(@Nullable Consumer<LynxModule> operation, int timeout, TimeUnit timeoutUnit)
                throws RobotCoreException, InterruptedException, TimeoutException
            {
            if (closed) { throw new RuntimeException("Attempted to perform system operation on closed handle"); }
            lynxUsb.internalPerformSysOp(module, parentModule, operation, timeout, timeoutUnit);
            }

        /**
         * Allow the module associated with this handle (and its parent) to close if there are no other users of it
         */
        public void close()
            {
            closed = true;
            synchronized (lynxUsb.sysOpStartStopLock)
                {
                parentModule.systemOperationCounter--;
                lynxUsb.internalCloseLynxModuleIfUnused(parentModule);

                module.systemOperationCounter--;
                lynxUsb.internalCloseLynxModuleIfUnused(module);
                }
            }

        public SerialNumber getLynxModuleSerialNumber()
            {
            return module.getModuleSerialNumber();
            }

        public boolean wrapsModule(LynxModule module)
            {
            return module == this.module;
            }

        public boolean wrapsSameModule(SystemOperationHandle otherHandle)
            {
            return otherHandle.module == this.module;
            }
        }

    RobotUsbDevice getRobotUsbDevice();

    boolean isSystemSynthetic();

    void setSystemSynthetic(boolean systemSynthetic);

    void failSafe();

    void changeModuleAddress(LynxModule module, int oldAddress, Runnable runnable);

    LynxModule getOrAddModule(LynxModuleDescription moduleDescription) throws RobotCoreException, InterruptedException;

    /** Should ONLY be called by LynxModule.close() */
    void removeConfiguredModule(LynxModule module);

    void noteMissingModule(int moduleAddress, String moduleName);

    /**
     * The operation will run on a background thread, but this method will not return until the operation has completed.
     * <p>
     * If operation is null, this method effectively just verifies that we can communicate with the parent
     * module, throwing an exception if we cannot.
     * <p>
     * If you care about the command getting completed, specify a timeout of at least 120ms or so, since the command
     * will get resent if necessary after 100ms.
     */
    void performSystemOperationOnParentModule(int parentAddress,
                                              @Nullable Consumer<LynxModule> operation,
                                              int timeout,
                                              TimeUnit timeoutUnit)
            throws RobotCoreException, InterruptedException, TimeoutException;

    /**
     * The operation will run on a background thread, but this method will not return until the operation has completed.
     * <p>
     * If operation is null, this method effectively just verifies that we can communicate with the specified
     * module, throwing an exception if we cannot.
     * <p>
     * If you care about the command getting completed, specify a timeout of at least 120ms or so, since the command
     * will get resent if necessary after 100ms.
     */
    void performSystemOperationOnConnectedModule(int moduleAddress,
                                                 int parentAddress,
                                                 @Nullable Consumer<LynxModule> operation,
                                                 int timeout,
                                                 TimeUnit timeoutUnit)
            throws RobotCoreException, InterruptedException, TimeoutException;

    /**
     * Open a {@link SystemOperationHandle} that will allow you to keep the LynxModule open for an unbounded length of time.
     * <p>
     * You can stop forcing the LynxModule to stay open by calling {@link SystemOperationHandle#close()}.
     * <p>
     * You can perform system operations on the module by calling {@link SystemOperationHandle#performSystemOperation(Consumer, int, TimeUnit)}.
     * <p>
     * This handle will continue working even if the module's address is changed.
     */
    SystemOperationHandle keepConnectedModuleAliveForSystemOperations(int moduleAddress, int parentAddress)
            throws RobotCoreException, InterruptedException;

    LynxModuleMetaList discoverModules(boolean checkForImus) throws RobotCoreException, InterruptedException;

    void acquireNetworkTransmissionLock(@NonNull LynxMessage message) throws InterruptedException;

    void releaseNetworkTransmissionLock(@NonNull LynxMessage message) throws InterruptedException;

    void transmit(LynxMessage message) throws InterruptedException;

    boolean setupControlHubEmbeddedModule() throws InterruptedException, RobotCoreException;

    LynxUsbDeviceImpl getDelegationTarget();

    RobotCoreCommandList.LynxFirmwareUpdateResp updateFirmware(RobotCoreCommandList.FWImage image, String requestId, Consumer<ProgressParameters> progressConsumer);
    }

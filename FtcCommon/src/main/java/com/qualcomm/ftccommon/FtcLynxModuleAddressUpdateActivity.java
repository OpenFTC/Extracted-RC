/*
Copyright (c) 2017-2023 Robert Atkinson, REV Robotics

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
package com.qualcomm.ftccommon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.qualcomm.ftccommon.configuration.EditActivity;
import com.qualcomm.ftccommon.configuration.USBScanManager;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DeviceManager;
import com.qualcomm.robotcore.hardware.LynxModuleMeta;
import com.qualcomm.robotcore.hardware.LynxModuleMetaList;
import com.qualcomm.robotcore.hardware.ScannedDevices;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.robocol.Command;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SerialNumber;
import com.qualcomm.robotcore.util.ThreadPool;

import org.firstinspires.ftc.robotcore.internal.network.CallbackResult;
import org.firstinspires.ftc.robotcore.internal.network.NetworkConnectionHandler;
import org.firstinspires.ftc.robotcore.internal.network.RecvLoopRunnable;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.Assert;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * {@link FtcLynxModuleAddressUpdateActivity} provides a means by which users can update
 * the (persistently stored) address of a Lynx Module
 */
@SuppressWarnings("WeakerAccess")
public class FtcLynxModuleAddressUpdateActivity extends EditActivity
    {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public static final String TAG = "FtcLynxModuleAddressUpdateActivity";
    @Override public String getTag() { return TAG; }

    protected final NetworkConnectionHandler            networkConnectionHandler = NetworkConnectionHandler.getInstance();

    protected final int                                 msResponseWait           = 10000;   // finding addresses can be slow

    protected List<PortalInfo>                          portals                  = new ArrayList<>();
    protected final DisplayedPortalList                 displayedPortalList      = new DisplayedPortalList();

    //----------------------------------------------------------------------------------------------
    // Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftc_lynx_address_update);

        final Button applyButton = findViewById(R.id.applyButton);
        applyButton.setVisibility(View.VISIBLE);
        }

    @Override
    protected void onStart()
        {
        super.onStart();
        loadExpansionHubs();
        }

    @Override protected void onDestroy()
        {
        super.onDestroy();
        }

    //----------------------------------------------------------------------------------------------
    // Change management
    //----------------------------------------------------------------------------------------------

    protected void loadExpansionHubs()
        {
        final Runnable displayHubsOnUiThread = () ->
            {
            displayedPortalList.initialize(portals);

            TextView noDevicesNotice = findViewById(R.id.noDevicesNotice);
            if (portals.isEmpty())
                {
                noDevicesNotice.setVisibility(View.VISIBLE);
                }
            else
                {
                noDevicesNotice.setVisibility(View.GONE);
                }
            };

        AppUtil.getInstance().showWaitCursor(
                getString(R.string.dialogMessagePleaseWait),
                () -> portals = getPortalsInfo(),
                displayHubsOnUiThread);
        }

    protected static class PortalInfo
        {
        public final SerialNumber serialNumber;
        public final int parentAddress;
        public final List<Integer> childAddresses;

        public PortalInfo(SerialNumber serialNumber, int parentAddress, List<Integer> childAddresses)
            {
            this.serialNumber = serialNumber;
            this.parentAddress = parentAddress;
            this.childAddresses = childAddresses;
            }
        }


    protected class DisplayedPortalList
        {
        protected ViewGroup portalList;
        protected final List<DisplayedPortal> displayedPortals = new ArrayList<>();

        public void initialize(List<PortalInfo> portals)
            {
            portalList = findViewById(R.id.portalList);
            portalList.removeAllViews();

            displayedPortals.clear();

            for (PortalInfo portal : portals)
                {
                this.add(portal);
                }
            }

        public boolean isDirty()
            {
            for (final DisplayedPortal displayedPortal : displayedPortals)
                {
                if (displayedPortal.isDirty())
                    {
                    return true;
                    }
                }
            return false;
            }

        protected void add(PortalInfo portal)
            {
            ViewGroup portalView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.lynx_portal_configure_addresses, null);
            portalList.addView(portalView);

            DisplayedPortal displayedPortal = new DisplayedPortal(portalView);
            displayedPortal.initialize(portal);
            displayedPortals.add(displayedPortal);
            }
        }

    protected class DisplayedPortal
        {
        protected final Map<Integer, Integer> originalToNewAddressMap = new HashMap<>();
        protected final ViewGroup portalView;
        protected static final int lastModuleAddressChoice = LynxConstants.MAX_UNRESERVED_MODULE_ADDRESS;
        protected final List<DisplayedModule> displayedModules = new ArrayList<>();
        protected SerialNumber serialNumber;
        protected int originalParentAddress;

        protected DisplayedPortal(ViewGroup portalView)
            {
            this.portalView = portalView;
            }

        public void initialize(PortalInfo portalInfo)
            {
            this.serialNumber = portalInfo.serialNumber;
            this.originalParentAddress = portalInfo.parentAddress;
            TextView portalNameView = portalView.findViewById(R.id.portalSerialText);

            if (serialNumber.isEmbedded())
                {
                portalNameView.setText(getString(R.string.lynx_address_control_hub_portal));
                }
            else
                {
                portalNameView.setText(getString(R.string.lynx_address_format_expansion_hub_portal_serial, serialNumber));
                // Only add the parent for USB-connected Expansion Hub Portals
                this.add(originalParentAddress);
                }

            for (int childAddress : portalInfo.childAddresses)
                {
                this.add(childAddress);
                }
            updateAvailableAddresses();
            }

        public void changeAddress(int originalAddress, int newAddress)
            {
            RobotLog.vv(TAG, "Selected address change on portal %s from %d to %d", serialNumber, originalAddress, newAddress);
            originalToNewAddressMap.put(originalAddress, newAddress);
            updateAvailableAddresses();
            }

        public boolean isDirty()
            {
            for (Map.Entry<Integer, Integer> moduleEntry : originalToNewAddressMap.entrySet())
                {
                final int originalAddress = moduleEntry.getKey();
                final int newAddress = moduleEntry.getValue();
                if (originalAddress != newAddress)
                    {
                    return true;
                    }
                }
            return false;
            }

        protected void updateAvailableAddresses()
            {
            List<AddressAndDisplayName> availableAddresses = new ArrayList<>(lastModuleAddressChoice);
            for (int potentialAddress = 0; potentialAddress <= lastModuleAddressChoice; potentialAddress++)
                {
                // Make sure that none of the modules connected via this portal have their new address set to this number
                // Address 0 (no change) is always available in the list
                if (potentialAddress == 0 || !originalToNewAddressMap.containsValue(potentialAddress))
                    {
                    availableAddresses.add(new AddressAndDisplayName(potentialAddress));
                    }
                }

            for (DisplayedModule displayedModule: displayedModules)
                {
                displayedModule.setAvailableAddresses(availableAddresses);
                }
            }

        protected void add(int moduleAddress)
            {
            originalToNewAddressMap.put(moduleAddress, moduleAddress);

            View moduleView = LayoutInflater.from(context).inflate(R.layout.lynx_module_configure_address, null);
            portalView.addView(moduleView);

            DisplayedModule displayedModule = new DisplayedModule(moduleView, this, moduleAddress);
            displayedModule.initialize();
            displayedModules.add(displayedModule);
            }
        }

    protected class DisplayedModule
        {
        protected final int originalAddress;
        protected final ArrayAdapter<AddressAndDisplayName> availableAddressesAdapter;
        protected final DisplayedPortal portal;
        protected final View view;
        protected final Spinner spinner;

        public DisplayedModule(View view, DisplayedPortal portal, int originalAddress)
            {
            Assert.assertTrue(originalAddress != 0);
            this.originalAddress = originalAddress;
            this.portal = portal;
            this.view = view;
            this.availableAddressesAdapter = new ArrayAdapter<>(
                    FtcLynxModuleAddressUpdateActivity.this,
                    android.R.layout.simple_spinner_item);
            this.availableAddressesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.spinner = view.findViewById(R.id.spinnerChooseAddress);
            this.spinner.setAdapter(availableAddressesAdapter);
            }

        public void initialize()
            {
            final TextView addressView = view.findViewById(R.id.moduleAddressText);
            addressView.setText(getString(R.string.lynx_address_format_module_address, originalAddress));

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                @Override public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
                    {
                    AddressAndDisplayName item = (AddressAndDisplayName) adapterView.getItemAtPosition(position);
                    int newAddress = item.address;
                    if (newAddress == originalAddress)
                        {
                        selectNoChange();
                        }
                    else if (newAddress == 0)
                        {
                        newAddress = originalAddress;
                        }
                    portal.changeAddress(originalAddress, newAddress);
                    }

                @Override public void onNothingSelected(AdapterView<?> adapterView)
                    {
                    }
                });
            }

        public void setAvailableAddresses(List<AddressAndDisplayName> availableAddresses)
            {
            int selectedAddress = portal.originalToNewAddressMap.get(originalAddress);
            final AddressAndDisplayName currentlySelectedAddressAndName = new AddressAndDisplayName(selectedAddress);
            availableAddressesAdapter.clear();
            availableAddressesAdapter.addAll(availableAddresses);
            // The currently-selected ("new") address also needs to be available
            availableAddressesAdapter.add(currentlySelectedAddressAndName);
            availableAddressesAdapter.sort(Comparator.comparingInt(o -> o.address));

            // We just changed which addresses are available, which may have changed the position of
            // our selected address
            if (selectedAddress == originalAddress)
                {
                // Select "<No change>"
                spinner.setSelection(0);
                }
            else
                {
                spinner.setSelection(availableAddressesAdapter.getPosition(currentlySelectedAddressAndName));
                }
            }

        protected void selectNoChange()
            {
            spinner.setSelection(0);
            }
        }

    protected class AddressAndDisplayName
        {
        public final String displayName;
        public final int address;

        public AddressAndDisplayName(int address)
            {
            this.address = address;
            this.displayName = address == 0
                    ? getString(R.string.lynx_address_format_no_change)
                    : getString(R.string.lynx_address_format_new_module_address, address);
            }

        @Override public String toString()
            {
            return this.displayName;
            }
        }


    //----------------------------------------------------------------------------------------------
    // Updating
    //----------------------------------------------------------------------------------------------

    DialogInterface.OnClickListener doNothingAndCloseListener = new DialogInterface.OnClickListener()
        {
        public void onClick(DialogInterface dialog, int button)
            {
            // Do nothing. Dialog will dismiss itself upon return.
            }
        };

    public void onApplyButtonPressed(View view)
        {
        RobotLog.vv(TAG, "onApplyButtonPressed()");

        final Runnable waitForChangeToFinish = () ->
            {
            final CountDownLatch changesAppliedLatch = new CountDownLatch(1);
            networkConnectionHandler.pushReceiveLoopCallback(new RecvLoopRunnable.DegenerateCallback()
                {
                @Override
                public CallbackResult commandEvent(Command command) throws RobotCoreException
                    {
                    if (command.getName().equals(CommandList.CMD_LYNX_ADDRESS_CHANGE_FINISHED))
                        {
                        changesAppliedLatch.countDown();
                        return CallbackResult.HANDLED;
                        }
                    return super.commandEvent(command);
                    }
                });
            try
                {
                changesAppliedLatch.await();
                }
            catch (InterruptedException e)
                {
                Thread.currentThread().interrupt();
                }
            };

        final boolean changesBeingApplied = beginApplyingChanges();

        if (changesBeingApplied)
            {
            // Display a wait cursor until the changes have been applied, and then refresh the list
            AppUtil.getInstance().showWaitCursor(
                    getString(R.string.dialogMessagePleaseWait),
                    waitForChangeToFinish,
                    this::loadExpansionHubs);
            }
        else
            {
            // No changes are being applied, so it's safe to refresh the list immediately
            loadExpansionHubs();
            }
        }

    public void onDoneButtonPressed(View view)
        {
        RobotLog.vv(TAG, "onDoneButtonPressed()");
        beginApplyingChanges();
        finishOk();
        }

    public void onCancelButtonPressed(View view)
        {
        RobotLog.vv(TAG, "onCancelButtonPressed()");
        doBackOrCancel();
        }

    @Override public void onBackPressed()
        {
        RobotLog.vv(TAG, "onBackPressed()");
        doBackOrCancel();
        }

    protected boolean beginApplyingChanges()
        {
        ArrayList<CommandList.LynxAddressChangeRequest.AddressChange> modulesToChange = new ArrayList<>();
        for (DisplayedPortal displayedPortal : displayedPortalList.displayedPortals)
            {
            int portalParentAddress = displayedPortal.originalParentAddress;
            for (Map.Entry<Integer, Integer> moduleAddressesEntry : displayedPortal.originalToNewAddressMap.entrySet())
                {
                final int originalAddress = moduleAddressesEntry.getKey();
                final int newAddress = moduleAddressesEntry.getValue();
                if (originalAddress != newAddress)
                    {
                    Assert.assertTrue(originalAddress != 0);
                    Assert.assertTrue(newAddress != 0);
                    CommandList.LynxAddressChangeRequest.AddressChange addressChange = new CommandList.LynxAddressChangeRequest.AddressChange();
                    addressChange.serialNumber = displayedPortal.serialNumber;
                    addressChange.parentAddress = portalParentAddress;
                    addressChange.oldAddress = originalAddress;
                    addressChange.newAddress = newAddress;
                    modulesToChange.add(addressChange);

                    // If we just added an address change for the parent module, future address
                    // changes need to use the updated parent module address.
                    if (originalAddress == portalParentAddress)
                        {
                        portalParentAddress = newAddress;
                        }
                    }
                }
            }

        if (displayedPortalList.displayedPortals.size() > 0)
            {
            if (modulesToChange.size() > 0)
                {
                CommandList.LynxAddressChangeRequest request = new CommandList.LynxAddressChangeRequest();
                request.modulesToChange = modulesToChange;
                sendOrInject(new Command(CommandList.CMD_LYNX_ADDRESS_CHANGE, request.serialize()));
                return true;
                }
            else
                {
                AppUtil.getInstance().showToast(UILocation.BOTH, getString(R.string.toastLynxAddressChangeNothingToDo));
                }
            }
        return false;
        }

    protected void doBackOrCancel()
        {
        if (displayedPortalList.isDirty())
            {
            DialogInterface.OnClickListener exitWithoutSavingButtonListener = new DialogInterface.OnClickListener()
                {
                @Override public void onClick(DialogInterface dialog, int which)
                    {
                    finishCancel();
                    }
                };

            AlertDialog.Builder builder = utility.buildBuilder(getString(R.string.saveChangesTitle), getString(R.string.saveChangesMessageScreen));
            builder.setPositiveButton(R.string.buttonExitWithoutSaving, exitWithoutSavingButtonListener);
            builder.setNegativeButton(R.string.buttonNameCancel, doNothingAndCloseListener);
            builder.show();
            }
        else
            {
            finishCancel();
            }
        }

    //----------------------------------------------------------------------------------------------
    // Networking
    //----------------------------------------------------------------------------------------------

    protected List<PortalInfo> getPortalsInfo()
        {
        final USBScanManager usbScanManager = USBScanManager.getInstance();
        final List<PortalInfo> result = new ArrayList<>();
        try
            {
            ScannedDevices usbDevices = usbScanManager.startDeviceScanIfNecessary().await(msResponseWait);
            if (usbDevices != null)
                {
                final Map<SerialNumber, ThreadPool.SingletonResult<LynxModuleMetaList>> discoveryResults = new HashMap<>();

                // Start module discovery on each Lynx USB device (portal), without waiting for the results to come in
                for (Map.Entry<SerialNumber, DeviceManager.UsbDeviceType> serialAndDeviceType : usbDevices.entrySet())
                    {
                    final SerialNumber serialNumber = serialAndDeviceType.getKey();
                    final DeviceManager.UsbDeviceType usbDeviceType = serialAndDeviceType.getValue();

                    if (usbDeviceType == DeviceManager.UsbDeviceType.LYNX_USB_DEVICE)
                        {
                        discoveryResults.put(serialNumber, usbScanManager.startLynxModuleEnumerationIfNecessary(serialNumber));
                        }
                    }

                // Wait for the discovery results to come in
                for (Map.Entry<SerialNumber, ThreadPool.SingletonResult<LynxModuleMetaList>> discoveryResult : discoveryResults.entrySet())
                    {
                    final SerialNumber serialNumber = discoveryResult.getKey();
                    final LynxModuleMetaList discoveredHubs = discoveryResult.getValue().await(msResponseWait);
                    if (discoveredHubs != null)
                        {
                        int parentAddress = 0;
                        final List<Integer> childAddresses = new ArrayList<>();
                        for (LynxModuleMeta moduleMeta : discoveredHubs)
                            {
                            final int address = moduleMeta.getModuleAddress();
                            if (moduleMeta.isParent())
                                {
                                parentAddress = address;
                                }
                            else
                                {
                                childAddresses.add(address);
                                }
                            }

                        // Add any portals with connected Expansion Hubs
                        // For Control Hubs, this means that children modules must be found
                        if (childAddresses.size() > 0 || (parentAddress != 0 && !serialNumber.isEmbedded()))
                            {
                            result.add(new PortalInfo(serialNumber, parentAddress, childAddresses));
                            }
                        }
                    }
                }
            }
        catch (InterruptedException e)
            {
            Thread.currentThread().interrupt();
            }

        RobotLog.vv(TAG, "found %d REV Hub Portals", result.size());
        return result;
        }
    }

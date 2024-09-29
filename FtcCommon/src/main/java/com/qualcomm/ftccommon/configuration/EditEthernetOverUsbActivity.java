/*
Copyright (c) 2024 FIRST

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
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.qualcomm.ftccommon.configuration;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.qualcomm.ftccommon.R;
import com.qualcomm.robotcore.hardware.configuration.EthernetOverUsbConfiguration;
import com.qualcomm.robotcore.hardware.configuration.WebcamConfiguration;
import com.qualcomm.robotcore.util.RobotLog;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class EditEthernetOverUsbActivity extends EditActivity {

    EditText textDeviceName;
    EditText ipAddressInput;
    public static final RequestCode requestCode = RequestCode.EDIT_ETHERNET_OVER_USB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ethernet_device);

        textDeviceName = (EditText) findViewById(R.id.ethernetDeviceName);

        EditParameters parameters = EditParameters.fromIntent(this, getIntent());
        deserialize(parameters);

        textDeviceName.addTextChangedListener(new SetNameTextWatcher(controllerConfiguration));
        textDeviceName.setText(controllerConfiguration.getName());

        ipAddressInput = (EditText) findViewById(R.id.ethernetDeviceIpAddress);
        ipAddressInput.setText(((EthernetOverUsbConfiguration)controllerConfiguration).getIpAddress().getHostAddress());
        ipAddressInput.setFilters(new InputFilter[] { new IpAddressInputFilter() });

        ipAddressInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action required
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No action required
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidIPAddress(s.toString())) {
                    // Display an error message
                    // ...

                }
            }

            private boolean isValidIPAddress(String ipAddress) {
                String regex = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(?:\\.|$)){4}$";
                Pattern pattern = Pattern.compile(regex);
                return pattern.matcher(ipAddress).matches();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCodeValue, int resultCode, Intent data)
    {
        logActivityResult(requestCodeValue, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            EditParameters parameters = EditParameters.fromIntent(this, data);
            RequestCode requestCode = RequestCode.fromValue(requestCodeValue);

            if (requestCode == EditSwapUsbDevices.requestCode)
            {
            }
            else if (requestCode == EditEthernetOverUsbActivity.requestCode)
            {
                EthernetOverUsbConfiguration newModule = (EthernetOverUsbConfiguration) parameters.getConfiguration();
                if (newModule != null)
                {
                }
            }

            currentCfgFile.markDirty();
            robotConfigFileManager.setActiveConfig(currentCfgFile);
        }
    }

    public void onDoneButtonPressed(View v)
    {
        finishOk();
    }

    @Override protected void finishOk()
    {
        String ipAddress = ipAddressInput.getText().toString();
        controllerConfiguration.setName(textDeviceName.getText().toString());
        try {
            ((EthernetOverUsbConfiguration)controllerConfiguration).setIpAddress(InetAddress.getByName(ipAddress));
        } catch (UnknownHostException e) {
            RobotLog.ee(TAG, "Ignoring ip address change, invalid ip address: %s", ipAddress);
        }
        finishOk(new EditParameters(this, controllerConfiguration, getRobotConfigMap()));
    }

    public void onCancelButtonPressed(View v)
    {
        finishCancel();
    }
}

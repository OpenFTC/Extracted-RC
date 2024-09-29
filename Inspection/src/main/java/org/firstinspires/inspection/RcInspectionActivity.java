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
package org.firstinspires.inspection;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.configuration.ControllerConfiguration;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.hardware.configuration.LynxModuleConfiguration;
import com.qualcomm.robotcore.hardware.configuration.ReadXMLFileHandler;
import com.qualcomm.robotcore.hardware.configuration.ServoHubConfiguration;
import com.qualcomm.robotcore.hardware.configuration.WebcamConfiguration;
import com.qualcomm.robotcore.robocol.Command;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.network.CallbackResult;
import org.firstinspires.ftc.robotcore.internal.network.NetworkConnectionHandler;
import org.firstinspires.ftc.robotcore.internal.network.RecvLoopRunnable;
import org.firstinspires.ftc.robotcore.internal.network.RobotCoreCommandList;
import org.firstinspires.ftc.robotcore.internal.usb.VendorProductSerialNumber;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;

import nl.minvws.encoding.Base45;

public class RcInspectionActivity extends InspectionActivity
    {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    private InspectionState rcInspectionState;
    private List<OpModeMeta> opModes;
    private List<ControllerConfiguration> controllerList = new ArrayList<>();
    private ArrayList<Integer> gamepads;
    private Boolean advancedGamepadFeatures;
    private View topBar;

    public static final String GAMEPAD_KEY = "gamepads";
    public static final String ADVANCED_FEATURES_KEY = "advancedFeatures";

    private static Gson qrGson;
    static
        {
        GsonBuilder builder = new GsonBuilder();
        JsonSerializer<Boolean> booleanSerializer = new JsonSerializer<Boolean>()
            {
            @Override
            public JsonElement serialize(Boolean b, Type type, JsonSerializationContext jsonSerializationContext)
                {
                // map booleans to [0,1] to save space
                return new JsonPrimitive(b != null && b ? 1 : 0);
                }
            };
        builder.registerTypeAdapter(Boolean.class, booleanSerializer);
        builder.registerTypeAdapter(boolean.class, booleanSerializer);
        builder.addSerializationExclusionStrategy(new ExclusionStrategy()
            {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes)
                {
                return fieldAttributes.getAnnotation(QrExclude.class) != null;
                }

            @Override
            public boolean shouldSkipClass(Class<?> aClass)
                {
                return false;
                }
            });

        JsonSerializer<InspectionProperty> inspectionPropertyJsonSerializer = new JsonSerializer<InspectionProperty>()
            {
                @Override
                public JsonElement serialize(InspectionProperty p, Type type, JsonSerializationContext jsonSerializationContext)
                {
                    // Throw away everything but the boolean and then map it to [0,1] to save space
                    return new JsonPrimitive(p != null && p.valid ? 1 : 0);
                }
            };
        builder.registerTypeAdapter(InspectionProperty.class, inspectionPropertyJsonSerializer);

        qrGson = builder.create();
        }

    final RecvLoopRunnable.RecvLoopCallback recvLoopCallback = new RecvLoopRunnable.DegenerateCallback()
        {
        @Override public CallbackResult commandEvent(Command command) throws RobotCoreException
            {
            if (remoteConfigure)
                {
                switch (command.getName())
                    {
                    case RobotCoreCommandList.CMD_REQUEST_INSPECTION_REPORT_RESP: {
                        final InspectionState rcState = InspectionState.deserialize(command.getExtra());
                        rcInspectionState = rcState;
                        AppUtil.getInstance().runOnUiThread(new Runnable()
                            {
                            @Override public void run()
                                {
                                refresh(rcState);
                                }
                            });
                        refreshQr();
                        return CallbackResult.HANDLED;
                        }

                    case RobotCoreCommandList.CMD_NOTIFY_OP_MODE_LIST:
                        opModes = SimpleGson.getInstance().fromJson(command.getExtra(), new TypeToken<Collection<OpModeMeta>>(){}.getType());
                        refreshQr();
                        return CallbackResult.HANDLED_CONTINUE;

                    case RobotCoreCommandList.CMD_NOTIFY_ACTIVE_CONFIGURATION:
                        NetworkConnectionHandler.getInstance().sendCommand(new Command(RobotCoreCommandList.CMD_REQUEST_PARTICULAR_CONFIGURATION, command.getExtra()));
                        return CallbackResult.HANDLED_CONTINUE;

                    case RobotCoreCommandList.CMD_REQUEST_PARTICULAR_CONFIGURATION_RESP:
                        ReadXMLFileHandler parser = new ReadXMLFileHandler();
                        controllerList = parser.parse(new StringReader(command.getExtra()));
                        refreshQr();
                        return CallbackResult.HANDLED_CONTINUE;

                    }
                }
            return CallbackResult.NOT_HANDLED;
            }
        };

    //----------------------------------------------------------------------------------------------
    // Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        topBar = findViewById(R.id.top_bar);
        NetworkConnectionHandler.getInstance().pushReceiveLoopCallback(recvLoopCallback);
        NetworkConnectionHandler.getInstance().sendCommand(new Command(RobotCoreCommandList.CMD_REQUEST_OP_MODE_LIST));
        NetworkConnectionHandler.getInstance().sendCommand(new Command(RobotCoreCommandList.CMD_REQUEST_ACTIVE_CONFIG));
        Intent intent = getIntent();
        if (intent != null)
            {
            if (intent.hasExtra(GAMEPAD_KEY))
                {
                gamepads = intent.getIntegerArrayListExtra(GAMEPAD_KEY);
                }
            if (intent.hasExtra(ADVANCED_FEATURES_KEY))
                {
                advancedGamepadFeatures = intent.getBooleanExtra(ADVANCED_FEATURES_KEY, false);
                }
            }
        }

    @Override protected void onDestroy()
        {
        super.onDestroy();
        NetworkConnectionHandler.getInstance().removeReceiveLoopCallback(recvLoopCallback);
        }

    //----------------------------------------------------------------------------------------------
    // Operations
    //----------------------------------------------------------------------------------------------

    @Override protected void refresh()
        {
        if (remoteConfigure)
            {
            NetworkConnectionHandler.getInstance().sendCommand(new Command(RobotCoreCommandList.CMD_REQUEST_INSPECTION_REPORT));
            }
        else
            {
            super.refresh();
            }
        }

    @Override protected boolean inspectingRobotController()
        {
        return true;
        }

    @Override
    protected boolean inspectingRemoteDevice()
        {
        return remoteConfigure;
        }

    @Override protected int getMenu()
        {
        // We only ever remotely configure an RC, so if we're doing a remote configure, then we
        // know we need to use the RC remote menu
        return remoteConfigure ? R.menu.inspection_menu_rc_remote : R.menu.inspection_menu_rcds_local;
        }

    private void refreshQr()
        {
        // Check that we have all the data needed.
        if (rcInspectionState == null || opModes == null) // haven't received all the data yet
            {
            return;
            }

        final QrData qrData = new QrData();
        qrData.dsInspectionState = new InspectionState();
        qrData.dsInspectionState.initializeLocal();
        qrData.rcInspectionState = rcInspectionState;
        // Add gamepad data
        for (int i = 0; gamepads != null && i < gamepads.size(); i+=2) // null check just in case we didnt get the data in the bundle for some reason.
            {
            qrData.gamepads.add(new GamepadInspection(gamepads.get(i), gamepads.get(i + 1)));
            }
        qrData.surveyData = getSurveyData();
        qrData.validSurvey = controllerList != null && !controllerList.isEmpty();
        qrData.rcValidation = getValidation(qrData.rcInspectionState, true);
        qrData.dsValidation = getValidation(qrData.dsInspectionState, false);
        Bitmap bitmap = null;
        try
            {
            // GSON -> ZLIB -> Base 45 -> QR
            String data = qrGson.toJson(qrData);
            Deflater compressor = new Deflater();
            compressor.setInput(data.getBytes());
            compressor.finish();
            byte[] buffer = new byte[data.length()];
            int len = compressor.deflate(buffer);
            compressor.finish();
            byte[] trimmed = new byte[len];
            System.arraycopy(buffer, 0, trimmed, 0, len);
            String base45 = Base45.getEncoder().encodeToString(trimmed);

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.encodeBitmap(base45, BarcodeFormat.QR_CODE, 512, 512);
            }
        catch (WriterException e)
            {
            RobotLog.ee(TAG, e.getMessage());
            }
        final Bitmap finalBitmap = bitmap;
        AppUtil.getInstance().runOnUiThread(new Runnable()
            {
            @Override public void run()
                {
                refresh(qrData.rcInspectionState);
                autoInspectQr.setImageBitmap(finalBitmap);
                if (!qrData.validSurvey || qrData.gamepads.isEmpty())
                    {
                    invalidQr.setVisibility(View.VISIBLE);
                    invalidQr.setText(qrData.validSurvey ? R.string.qrNoGamepad : R.string.qrInvalid);
                    }
                else
                    {
                    invalidQr.setVisibility(View.GONE);
                    }
                // fill image size to min(width/height)
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                Configuration config = getResources().getConfiguration();
                ViewGroup.LayoutParams params = autoInspectQr.getLayoutParams();
                int heightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, config.screenHeightDp, displayMetrics);
                int widthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, config.screenWidthDp, displayMetrics);
                int topBarPx = topBar.getLayoutParams().height;
                params.height = Math.min(heightPx - topBarPx, widthPx) - 10; // small buffer
                autoInspectQr.setLayoutParams(params);
                }
            });
        }


    @Override
    public void onConfigurationChanged(Configuration newConfig)
        {
        super.onConfigurationChanged(newConfig);
        refreshQr();
        }

    /**
     * Generates the SurveyData object. The SurveyData object will be untouched by the scoring system
     * so can be whatever form & contents desired. This object & format significantly affect QR data size.
     */
    private SurveyData getSurveyData()
        {
        if (opModes == null)
            {
            return null;
            }
        SurveyData surveyData = new SurveyData();
        for (ControllerConfiguration cc : controllerList)
            {
            addControllerToSurvey(surveyData, cc);
            }

        for (OpModeMeta opMode : opModes)
            {
            addItemToCount(surveyData.getOpModeFlavors(), opMode.flavor);
            addItemToCount(surveyData.getOpModeSources(), opMode.source);
            }

        if (advancedGamepadFeatures != null)
            {
            surveyData.setAdvancedGamepadFeatures(advancedGamepadFeatures);
            }
        return surveyData;
        }

    /**
     * Helper function for Survey data generation
     */
    private <T> void addItemToCount(Map<T, Integer> map, T k)
        {
        if (map.containsKey(k))
            {
            map.put(k, map.get(k) + 1);
            }
        else
            {
            map.put(k, 1);
            }
        }

    private void addDevicesToSurvey(SurveyData surveyData, List<? extends DeviceConfiguration> devices)
        {
        for (DeviceConfiguration device : devices)
            {
            if (!device.isEnabled())
                {
                continue;
                }
            if (device instanceof ControllerConfiguration)
                {
                addControllerToSurvey(surveyData, (ControllerConfiguration<?>) device);
                }
            else
                {
                addItemToCount(surveyData.getHardwareData(), device.getConfigurationType().getName());
                }
            }
        }

    private void addControllerToSurvey(SurveyData surveyData, ControllerConfiguration<?> controller)
        {
        if (!controller.isEnabled()) { return; }
        if (controller instanceof WebcamConfiguration)
            {
            WebcamConfiguration webcam = (WebcamConfiguration) controller;
            if (webcam.getSerialNumber().isVendorProduct()) // try to count specific webcam type
                {
                VendorProductSerialNumber sn = (VendorProductSerialNumber) webcam.getSerialNumber();
                addItemToCount(surveyData.getHardwareData(), "webcam_" + sn.getVendorId() + "_" + sn.getProductId());
                }
            else
                {
                addItemToCount(surveyData.getHardwareData(), "webcam");
                }
            return;
            }
        addItemToCount(surveyData.getHardwareData(), controller.getName());
        addDevicesToSurvey(surveyData, controller.getDevices());
        if (controller instanceof LynxModuleConfiguration)
            {   // LynxModuleConfiguration has its own set of List<DeviceConfiguration>s separate from getDevices()
            LynxModuleConfiguration lynx = (LynxModuleConfiguration) controller;
            addDevicesToSurvey(surveyData, lynx.getMotors());
            addDevicesToSurvey(surveyData, lynx.getPwmOutputs());
            addDevicesToSurvey(surveyData, lynx.getServos());
            addDevicesToSurvey(surveyData, lynx.getAnalogInputs());
            addDevicesToSurvey(surveyData, lynx.getDigitalDevices());
            addDevicesToSurvey(surveyData, lynx.getI2cDevices());
            }
        else if (controller instanceof ServoHubConfiguration)
            {
            ServoHubConfiguration servoHub = (ServoHubConfiguration) controller;
            addDevicesToSurvey(surveyData, servoHub.getServos());
            }
        }

    }


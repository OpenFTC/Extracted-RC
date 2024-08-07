/*
 * Copyright (c) 2018 David Sargent
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of David Sargent nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.onbotjava;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.util.Arrays;
import java.util.List;

public class OnBotJavaWebInterfaceManager {
    private static final List<String> packagesToAutocomplete = Arrays.asList(
            "android/util",
            "org/firstinspires/ftc/ftccommon/external",
            "org/firstinspires/ftc/robotcore/external",
            "org/firstinspires/ftc/vision",
            "com/qualcomm/robotcore/eventloop/opmode",
            "com/qualcomm/robotcore/hardware",
            "com/qualcomm/robotcore/robot",
            "com/qualcomm/robotcore/util",
            "com/qualcomm/hardware",
            "java/io",
            "java/lang",
            "java/math",
            "java/text",
            "java/util"
    );

    private final BuildMonitor buildMonitor;
    private final OnBotJavaBroadcastManager broadcastManager;
    private final Gson gson;
    private final SharedPreferences sharedPrefs;
    private final EditorSettings editorSettings;
    private static OnBotJavaWebInterfaceManager instance;

    private OnBotJavaWebInterfaceManager() {
        gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        sharedPrefs = AppUtil.getDefContext().getSharedPreferences(OnBotJavaProgrammingMode.class.getName(), Context.MODE_PRIVATE);
        editorSettings = new EditorSettings(sharedPrefs);
        broadcastManager = new OnBotJavaBroadcastManager();
        buildMonitor = new BuildMonitor(broadcastManager);
    }

    public static OnBotJavaWebInterfaceManager instance() {
        if (instance == null) instance = new OnBotJavaWebInterfaceManager();

        return instance;
    }

    public EditorSettings editorSettings() {
        return editorSettings;
    }

    public BuildMonitor buildMonitor() {
        return buildMonitor;
    }

    public OnBotJavaBroadcastManager broadcastManager() {
        return broadcastManager;
    }

    public Gson gson() {
        return gson;
    }

    public SharedPreferences sharedPrefs() {
        return sharedPrefs;
    }

    public static List<String> packagesToAutocomplete() {
        return packagesToAutocomplete;
    }
}

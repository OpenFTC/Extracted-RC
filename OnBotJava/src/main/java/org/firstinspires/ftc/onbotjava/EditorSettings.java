/*
 * Copyright (c) 2018-2021 David Sargent, REV Robotics
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

import android.content.SharedPreferences;

import com.android.tools.r8.graph.S;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class EditorSettings {
    public final Setting<Boolean> autocompleteEnabledSetting = new Setting<>("autocompleteEnabled", Boolean.class, true);
    public final Setting<Boolean> autocompleteForceEnabledSetting = new Setting<>("autocompleteForceEnabled", Boolean.class, false);
    public final Setting<List> autocompletePackagesSetting = new Setting<List>("autocompletePackages", List.class, OnBotJavaWebInterfaceManager.packagesToAutocomplete());
    public final Setting<Boolean> autoImportEnabledSetting = new Setting<>("autoImportEnabled", Boolean.class, true);
    public final Setting<String> defaultPackageSetting = new Setting<>("defaultPackage", String.class, "org.firstinspires.ftc.teamcode");
    public final Setting<String> fontSetting = new Setting<>("font", String.class, "Source Code Pro");
    public final Setting<Integer> fontSizeSetting = new Setting<>("fontSize", Integer.class, 16);
    public final Setting<String> keybindingSetting = new Setting<>("keybinding", String.class, "OnBotJava");
    public final Setting<Boolean> showInvisibleCharsSetting = new Setting<>("invisibleChars", Boolean.class, false);
    public final Setting<Boolean> showPrintMarginSetting = new Setting<>("printMargin", Boolean.class, true);
    public final Setting<Boolean> softWrapSetting = new Setting<>("softWrap", Boolean.class, false);
    public final Setting<Integer> spacesToTabSetting = new Setting<>("spacesToTab", Integer.class, 4);
    public final Setting<String> themeSetting = new Setting<>("theme", String.class, "chrome");
    public final Setting<Boolean> useNewOnBotJavaWorker = new Setting<>("useNewOnBotJavaWorker", Boolean.class, true);
    public final Setting<String> whitespaceSetting = new Setting<>("whitespace", String.class, "space");
    public final Setting<Integer> settingEpoch = new Setting<>("settingEpoch", Integer.class, 0);
    private final Map<String, Setting<?>> settings;

    private EditorSettings() {
        HashMap<String, Setting<?>> settings = new HashMap<>();
        settings.put(autocompleteEnabledSetting.name, autocompleteEnabledSetting);
        settings.put(autocompleteForceEnabledSetting.name, autocompleteForceEnabledSetting);
        settings.put(autocompletePackagesSetting.name, autocompletePackagesSetting);
        settings.put(autoImportEnabledSetting.name, autoImportEnabledSetting);
        settings.put(defaultPackageSetting.name, defaultPackageSetting);
        settings.put(fontSetting.name, fontSetting);
        settings.put(fontSizeSetting.name, fontSizeSetting);
        settings.put(keybindingSetting.name, keybindingSetting);
        settings.put(showInvisibleCharsSetting.name, showInvisibleCharsSetting);
        settings.put(showPrintMarginSetting.name, showPrintMarginSetting);
        settings.put(softWrapSetting.name, softWrapSetting);
        settings.put(spacesToTabSetting.name, spacesToTabSetting);
        settings.put(themeSetting.name, themeSetting);
        settings.put(useNewOnBotJavaWorker.name, useNewOnBotJavaWorker);
        settings.put(whitespaceSetting.name, whitespaceSetting);
        settings.put(settingEpoch.name, settingEpoch);
        this.settings = Collections.unmodifiableMap(settings);
    }

    private EditorSettings(Map<String, Object> settingValuesMap) {
        // Populate the settings map with the default values
        this();

        // Replace the default values with the corresponding values from the provided map
        for (Map.Entry<String, Object> settingValueEntry: settingValuesMap.entrySet()) {
            Setting<?> setting = settings.get(settingValueEntry.getKey());
            setting.setWithUnknownType(settingValueEntry.getValue()); // performs type checking & conversion at runtime
        }
        autocompletePackagesSetting.set(OnBotJavaWebInterfaceManager.packagesToAutocomplete());
        upgradeOnBotJavaSettings(null);
    }

    private void upgradeOnBotJavaSettings(SharedPreferences.Editor editor) {
        if (settingEpoch.value == 0) {
            useNewOnBotJavaWorker.set(true);
            settingEpoch.set(1);

            if (editor != null) {
                persistValue(editor, useNewOnBotJavaWorker.name);
                persistValue(editor, settingEpoch.name);
            }
        }
    }

    EditorSettings(SharedPreferences preferences) {
        // Populate the settings map with the default values
        this();

        // Synchronize with SharedPreferences
        final SharedPreferences.Editor edit = preferences.edit();
        final Map<String, ?> prefMap = preferences.getAll();
        for (String key : settings.keySet()) {
            if (prefMap.containsKey(key)) {
                Setting setting = settings.get(key);
                setting.setWithUnknownType(prefMap.get(key));
            } else {
                persistValue(edit, key);
            }
        }

        autocompletePackagesSetting.set(OnBotJavaWebInterfaceManager.packagesToAutocomplete());
        upgradeOnBotJavaSettings(edit);
        edit.apply();
    }

    private static EditorSettings fromJson(String json) {
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        //noinspection unchecked
        return new EditorSettings((Map<String, Object>) OnBotJavaWebInterfaceManager.instance().gson().fromJson(json, type));
    }

    private void persistValue(SharedPreferences.Editor edit, String key) {
        Object obj = settings.get(key).get();
        if (obj instanceof Integer) {
            edit.putInt(key, (Integer) obj);
        } else if (obj instanceof String) {
            edit.putString(key, (String) obj);
        } else if (obj instanceof Boolean) {
            edit.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            edit.putFloat(key, (Float) obj);
        } else if (obj instanceof Double) {
            edit.putFloat(key, ((Double) obj).floatValue());
        } else {
            edit.putString(key, SimpleGson.getInstance().toJson(obj));
        }
    }

    public void updateFromJson(String json) {
        EditorSettings newEditorSettings = fromJson(json);
        update(newEditorSettings, OnBotJavaWebInterfaceManager.instance().sharedPrefs());
    }

    // Marked final so that we don't have to worry about registering subtypes with Gson
    public static final class Setting<T> {
        final String name;
        final Class<T> type;
        private T value;

        private Setting(String name, Class<T> type, T defaultValue) {
            this.name = name;
            this.type = type;
            this.value = defaultValue;
        }

        public T get() {
            return value;
        }

        public final void set(T value) {
            setWithUnknownType(value);
        }

        // For use when type erasure renders set() unusable
        public void setWithUnknownType(Object value) {
            try {
                this.value = safelyConvertFromObject(value, this.type);
            } catch (RuntimeException e) {
                RobotLog.ee(EditorSettings.class.getName(), e, "Setting %s not updated.", this.name);
            }
        }

        @SuppressWarnings("unchecked")
        private static <T> T safelyConvertFromObject(Object value, Class<T> type) {
            // If the object already has the desired type, we're golden
            if (type.isInstance(value)) {
                return (T) value;
            }

            Exception gsonException = null;
            // If the object is a string, it may be encoded as JSON
            if (value instanceof String) {
                Class gsonTargetType = type;

                // Gson will also convert a string containing a number into an Integer or a Double.
                // This is extremely useful, because in the past, values that started out as
                // integers would end up persisted to SharedPreferences as a string, because the
                // Javascript frontend would include .0 at the end of the number.
                //
                // However, it's important not to tell Gson to expect an Integer, because that would
                // cause Gson to throw an exception if the number after the decimal point was not 0.
                // Considering that it's possible for the user to manually input any number
                // (including ones with nonzero fractional components) into the settings interface,
                // that could be problematic.
                //
                // To work around this, if the type of this Setting is Integer, we tell Gson to look
                // for a Double value, and then replace the value parameter with the result, instead
                // of returning it. Then, the next section of code will convert the Double to an
                // Integer.
                boolean convertingToInt = (type == Integer.class);
                if (convertingToInt) { gsonTargetType = Double.class; }

                try {
                    Object gsonResult = SimpleGson.getInstance().fromJson((String) value, gsonTargetType);
                    if (convertingToInt) {
                        value = gsonResult;
                    } else {
                        return (T) gsonResult;
                    }
                } catch (JsonSyntaxException e) {
                    gsonException = e;
                }
            }

            // The Javascript frontend always sends numbers with a decimal place, even if that is .0
            // Therefore, if we want to store an Integer, we must make sure to cleanly convert
            // Doubles and Floats as well.
            // This part must come after the part where we use Gson (see above comment).
            if (type == Integer.class && value instanceof Double) {
                int intValue = ((Double) value).intValue();
                return (T) Integer.valueOf(intValue);
            }
            if (type == Integer.class && value instanceof Float) {
                int intValue = ((Float) value).intValue();
                return (T) Integer.valueOf(((Float) value).intValue());
            }

            // We also should handle converting between Doubles and Floats
            if (type == Double.class && value instanceof Float) {
                double doubleValue = ((Float) value).doubleValue();
                return (T) Double.valueOf(doubleValue);
            }
            if (type == Float.class && value instanceof Double) {
                float floatValue = ((Double) value).floatValue();
                return (T) Float.valueOf(floatValue);
            }

            throw new IllegalArgumentException(String.format("Unable to convert %s to %s.", value.getClass().getSimpleName(), type.getSimpleName()), gsonException);
        }
    }

    /**
     * @param newSettings The new settings to apply
     * @param preferences a SharedPreferences instance, where the new settings will be persisted
     */
    @SuppressWarnings({"unchecked"})
    private void update(EditorSettings newSettings, SharedPreferences preferences) {
        final SharedPreferences.Editor editor = preferences.edit();
        for (Map.Entry<String, Setting<?>> newEntry : newSettings.settings.entrySet()) {
            String key = newEntry.getKey();
            Object newValue = newEntry.getValue().get();
            Setting existingSetting = this.settings.get(key);
            existingSetting.set(newValue);
            persistValue(editor, key);
        }
        editor.apply();
    }

    private void trim(SharedPreferences preferences) {
        final Map<String, ?> prefMap = preferences.getAll();
        final SharedPreferences.Editor edit = preferences.edit();
        for (String key : prefMap.keySet()) {
            if (!settings.containsKey(key)) edit.remove(key);
        }
        edit.apply();
    }

    public void resetToDefaults() {
        SharedPreferences preferences = OnBotJavaWebInterfaceManager.instance().sharedPrefs();
        update(new EditorSettings(), preferences);
        trim(preferences);
    }

    public String toJSON() {
        return settingsSerializer.toJson(this.settings);
    }

    private static class SettingTypeAdapter extends TypeAdapter<Setting> {

        @Override public void write(JsonWriter jsonWriter, Setting setting) throws IOException {
            if (setting == null) {
                jsonWriter.nullValue();
                return;
            }
            handleSetting(setting, jsonWriter);
        }

        private <T> void handleSetting(Setting<T> setting, JsonWriter jsonWriter) throws IOException {
            T value = setting.get();
            TypeAdapter<T> valueTypeAdapter = settingsSerializer.getAdapter(setting.type);
            valueTypeAdapter.write(jsonWriter, value);
        }

        @Override public Setting read(JsonReader jsonReader) throws IOException {
            throw new IllegalStateException("The settingsSerializer only supports serializing a Settings instance, not deserializing one.");
        }
    }

    private static final Gson settingsSerializer = new GsonBuilder()
            .registerTypeAdapter(Setting.class, new SettingTypeAdapter())
            .create();
}

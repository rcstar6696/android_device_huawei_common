/*
* Copyright (C) 2018 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package org.carbonrom.settings.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.preference.PreferenceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;

public class Startup extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent bootintent) {

        context.startService(new Intent(context, ScreenStateService.class));

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SystemProperties.set(DeviceSettings.FPNAV_ENABLED_PROP, sharedPrefs.getBoolean(DeviceSettings.KEY_FP_GESTURES, false) ? "1" : "0");
        DisplayModeControl.mExtTouchScreen.hwTsSetCoverMode(sharedPrefs.getBoolean(DeviceSettings.KEY_HIGH_TOUCH, false));
        DisplayModeControl.sDisplayEngineService.setBootComplete(true);
        
        long packedColor = Color.pack(Integer.parseInt(Utils.getPreference(context, DeviceSettings.COLOUR_TEMP_RGB_KEY,
                String.valueOf(0xFFFFFF))));
        DisplayModeControl.mHwPowerManager.nativeUpdateRgbGamma(Color.red(packedColor), Color.green(packedColor), Color.blue(packedColor));

        DisplayModeControl.setMode(Integer.parseInt(Utils.getPreference(context, DeviceSettings.COLOUR_PROFILES_KEY, "0")));
        DisplayModeControl.mHwPowerManager.nativeSetColorTemperature(Integer.parseInt(Utils.getPreference(context, DeviceSettings.COLOUR_TEMP_KEY, "128")));
    }
}

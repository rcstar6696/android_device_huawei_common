/*
* Copyright (C) 2016 The OmniROM Project
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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.TwoStatePreference;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.util.Log;

public class DeviceSettings extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    
    public static final String KEY_FP_GESTURES = "fp_gestures_key";
    public static String FPNAV_ENABLED_PROP = "sys.fpnav.enabled";
    
    public static final String KEY_HIGH_TOUCH = "high_touch_key";
    public static final String HIGH_TOUCH_MODE = "/sys/touchscreen/touch_glove";
    
    public static final String SLIDER_DEFAULT_VALUE = "4,2,0";

    public static final String COLOUR_PROFILES_KEY = "colour_profiles_key";
    public static final String COLOUR_TEMP_KEY = "colour_temp_key";
    public static final String COLOUR_TEMP_RGB_KEY = "colour_temp_rgb_key";

    private TwoStatePreference mFpGestures;
    private TwoStatePreference mHighTouch;
    private ListPreference mColourProfiles;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.main, rootKey);

        mFpGestures = (TwoStatePreference) findPreference(KEY_FP_GESTURES);
        mFpGestures.setChecked(SystemProperties.get(FPNAV_ENABLED_PROP, "0").equals("1"));

        mHighTouch = (TwoStatePreference) findPreference(KEY_HIGH_TOUCH);
        mHighTouch.setChecked(Utils.getFileValueAsBoolean(HIGH_TOUCH_MODE, false));

        mColourProfiles = (ListPreference) findPreference(COLOUR_PROFILES_KEY);
        mColourProfiles.setOnPreferenceChangeListener(this);
        int index = Integer.parseInt(Utils.getPreference(getContext(), DeviceSettings.COLOUR_PROFILES_KEY, "0"));
        mColourProfiles.setValueIndex(index);
        mColourProfiles.setSummary(mColourProfiles.getEntries()[index]);

    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference == mFpGestures) {

            SystemProperties.set(FPNAV_ENABLED_PROP, mFpGestures.isChecked() ? "1" : "0");

            return true;
        }
        else if (preference == mHighTouch) {
            DisplayModeControl.mExtTouchScreen.hwTsSetCoverMode(mHighTouch.isChecked());
            return true;
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mColourProfiles) {
            String value = (String) newValue;
            int colourPofile = Integer.valueOf(value);
            //setSliderAction(0, colourPofile);
            DisplayModeControl.setMode(colourPofile);

            // 2 : COLOR_ENHANCEMENT (1 : Eye Comfort)
            DisplayModeControl.sHwSmartDisplayService.nativeSetSmartDisplay(2, colourPofile);

            Utils.writePreference(getContext(), COLOUR_PROFILES_KEY, value);

            int valueIndex = mColourProfiles.findIndexOfValue(value);
            mColourProfiles.setSummary(mColourProfiles.getEntries()[valueIndex]);
        }
        return true;
    }
}

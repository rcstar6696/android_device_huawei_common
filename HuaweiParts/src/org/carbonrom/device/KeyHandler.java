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

import android.app.ActivityManagerNative;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.IAudioService;
import android.media.AudioManager;
import android.media.session.MediaSessionLegacyHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.provider.Settings.Global;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.HapticFeedbackConstants;
import android.view.WindowManagerGlobal;
import android.view.WindowManagerPolicy;

import com.android.internal.os.DeviceKeyHandler;
import com.android.internal.util.ArrayUtils;
import com.android.internal.statusbar.IStatusBarService;

public class KeyHandler implements DeviceKeyHandler {

    private static final String TAG = "KeyHandler";
    private static final boolean DEBUG = true;
    private static final boolean DEBUG_SENSOR = false;

    protected static final int GESTURE_REQUEST = 1;
    private static final int GESTURE_WAKELOCK_DURATION = 2000;


    private static final String DOZE_INTENT = "com.android.systemui.doze.pulse";

    private static final int FP_GESTURE_SWIPE_LEFT = 106;
    private static final int FP_GESTURE_SWIPE_RIGHT = 105;
    private static final int FP_GESTURE_LONG_PRESS = 28;
    
    private static final int FP_GESTURE_TAP = 174;
    
    private static final int[] sSupportedGestures = new int[]{
        FP_GESTURE_SWIPE_LEFT,
        FP_GESTURE_SWIPE_RIGHT,
        FP_GESTURE_LONG_PRESS,
        FP_GESTURE_TAP
    };


    protected final Context mContext;
    private final PowerManager mPowerManager;
    private EventHandler mEventHandler;
    private WakeLock mGestureWakeLock;
    private Handler mHandler = new Handler();
    private static boolean mButtonDisabled;
    private final NotificationManager mNoMan;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private boolean mFPcheck;
    private boolean mDispOn;
    private WindowManagerPolicy mPolicy;
    private boolean isFpgesture;


    private BroadcastReceiver mScreenStateReceiver = new BroadcastReceiver() {
         @Override
         public void onReceive(Context context, Intent intent) {
             if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                 mDispOn = true;
                 onDisplayOn();
             } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                 mDispOn = false;
                 onDisplayOff();
             }
         }
    };

    public KeyHandler(Context context) {
        mContext = context;
        mDispOn = true;
        mEventHandler = new EventHandler();
        mPowerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        mGestureWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "GestureWakeLock");
        mNoMan = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mContext.registerReceiver(mScreenStateReceiver, screenStateFilter);
    }

    private class EventHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
        }
    }
    
    public void handleNavbarToggle(boolean enabled) {
    }

    @Override
    public KeyEvent handleKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {
            return event;
        }
        isFpgesture = false;

        if (DEBUG) Log.i(TAG, "nav_code=" + event.getScanCode());
        int fpcode = event.getScanCode();
        mFPcheck = canHandleKeyEvent(event);
        String value = getGestureValueForFPScanCode(fpcode);
        if (mFPcheck && mDispOn){
            isFpgesture = true;
        }
        return isFpgesture ? null : event;
    }

  @Override
    public boolean canHandleKeyEvent(KeyEvent event) {
        if (mButtonDisabled) {
            return false;
        }
        return true;
    }

    private void onDisplayOn() {
        if (DEBUG) Log.i(TAG, "Display on");

    }

    private void onDisplayOff() {
        if (DEBUG) Log.i(TAG, "Display off");

    }

    private int getSliderAction(int position) {
        return 0;
    }

    private void doHandleSliderAction(int position) {
        int action = getSliderAction(position);
    }

  

    private boolean launchSpecialActions(String value) {
        return false;
    }

    private String getGestureValueForFPScanCode(int scanCode) {
        return null;
    }

    private boolean areSystemNavigationKeysEnabled() {
        return Settings.Secure.getIntForUser(mContext.getContentResolver(),
                Settings.Secure.SYSTEM_NAVIGATION_KEYS_ENABLED, 0, UserHandle.USER_CURRENT) == 1;
    }

    IStatusBarService getStatusBarService() {
        return IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
    }

    protected static Sensor getSensor(SensorManager sm, String type) {
        for (Sensor sensor : sm.getSensorList(Sensor.TYPE_ALL)) {
            if (type.equals(sensor.getStringType())) {
                return sensor;
            }
        }
        return null;
    }
}

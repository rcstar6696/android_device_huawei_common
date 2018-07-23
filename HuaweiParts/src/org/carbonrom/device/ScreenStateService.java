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

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/**
 * This is a service registers a broadcast receiver to listen for screen on/off events.
 * It is a very unfortunate service that must exist because we can't register for screen on/off
 * in the manifest.
 */

public class ScreenStateService extends Service {

  private BroadcastReceiver mScreenStateBroadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
        DisplayModeControl.sDisplayEngineService.enablePowerMode(true);
      } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
        DisplayModeControl.sDisplayEngineService.enablePowerMode(false);
      }
    }
  };


  @Override
  public void onCreate() {
    super.onCreate();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(Intent.ACTION_SCREEN_ON);
    intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
    registerReceiver(mScreenStateBroadcastReceiver, intentFilter);
  }

  @Override
  public void onDestroy() {
    unregisterReceiver(mScreenStateBroadcastReceiver);
    super.onDestroy();
  }

  @Override
  public IBinder onBind(Intent intent) {
    // Nothing should bind to this service
    return null;
  }
}

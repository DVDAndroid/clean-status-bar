package com.emmaguy.cleanstatusbar.xposed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.emmaguy.cleanstatusbar.CleanStatusBarService;

/**
 * Created by dvdandroid
 */
public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean moduleEnabled = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("xposed_integration",
                false);

        if (intent.getAction().equals(XposedMod.ACTION_COLOR_CHANGED) && CleanStatusBarService.isRunning() && moduleEnabled) {
            context.startService(
                    new Intent(context, CleanStatusBarService.class)
                            .putExtra("color", intent.getIntExtra("color", Color.BLACK)));
        }
    }

}
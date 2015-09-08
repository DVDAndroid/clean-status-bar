package com.emmaguy.cleanstatusbar.xposed;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by dvdandroid
 */
public class XposedMod implements IXposedHookZygoteInit {

    public static final String ACTION_COLOR_CHANGED = "com.emmaguy.cleanstatusbar.COLOR_CHANGED";

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {

        boolean isLollipop = Build.VERSION.SDK_INT >= 21;

        if (!isLollipop) {
            return;
        }

        findAndHookMethod(Activity.class, "performResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param)
                    throws Throwable {
                Object currentObj = param.thisObject;
                Activity currentActivity;
                if (currentObj instanceof Activity) {
                    currentActivity = (Activity) currentObj;
                } else {
                    return;
                }

                int color = currentActivity.getWindow().getStatusBarColor();

                if (color == 0)
                    color = Color.BLACK;

                currentActivity.sendBroadcast(new Intent(ACTION_COLOR_CHANGED)
                        .putExtra("color", color));
            }
        });
    }

}
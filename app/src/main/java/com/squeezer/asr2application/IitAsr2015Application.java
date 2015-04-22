package com.squeezer.asr2application;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.StrictMode;

public class IitAsr2015Application extends Application {


    private static final boolean DEVELOPER_MODE = true;

    public void onCreate(){
        if (DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
        super.onCreate();
    }

    public static boolean isDebuggable(Context ctx) {
        boolean debuggable = false;

        PackageManager packageManager = ctx.getPackageManager();
        try {
            ApplicationInfo appinfo = packageManager.getApplicationInfo(
                    ctx.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags &= ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
            /* debuggable variable will remain false */
        }

        return debuggable;
    }
}

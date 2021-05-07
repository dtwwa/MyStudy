package com.animation_study;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

public class CrashApplication extends Application {
    public static Context context;
    public static int screenWidth = 0;
    public static int screenHeight = 0;
    public static WindowManager wm;
    public static float screenProportion = 0f;

    @Override
    public void onCreate() {
        super.onCreate();
        wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
        screenProportion = screenHeight * 1.0f / screenWidth;
        context = getApplicationContext();
        CrashHandler.getInstance().init(this);

    }
}
package com.indianic;

import android.app.Application;

/**
 * <p>Application class for define things which use till application running.</p>
 */
public class AndroidApp extends Application {

    private static AndroidApp AppInstance;

    public static AndroidApp getInstance() {
        return AppInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

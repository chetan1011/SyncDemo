package com.indianic;

import android.app.Application;

/**
 * <p>Application class for define things which use till application running.</p>
 */
public class AndroidApp extends Application {

    private static AndroidApp androidApp;

    public static AndroidApp getInstance() {
        return androidApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        androidApp = this;
    }
}

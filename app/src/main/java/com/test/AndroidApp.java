package com.test;

import android.app.Application;
import android.content.Intent;

import com.test.database.DatabaseHelper;
import com.test.service.OfflineUserSyncOnServer;

/**
 * <p>Application class for define things which use till application running.</p>
 */
public class AndroidApp extends Application {

    private static AndroidApp androidApp;
    private DatabaseHelper dbHelper;

    public static AndroidApp getInstance() {
        return androidApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        androidApp = this;
        dbHelper = new DatabaseHelper(this);
        dbHelper.openDataBase();

    }

    /**
     * Call when application is close
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (androidApp != null) {
            androidApp = null;
        }
    }
}

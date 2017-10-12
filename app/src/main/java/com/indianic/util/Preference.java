package com.indianic.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.indianic.R;

/**
 * Preference class to use SharedPreference class through out application. Use this class to store or retrieve data from SharedPreference.
 */
public class Preference {
    /**
     * Preference key for userId
     */
    private final String USER_ID = "USER_ID";
    private final String USER_IS_LOGIN = "USER_IS_LOGIN";

    /**
     * Shared Preference instance
     */
    private SharedPreferences sharedPreferences;


    public Preference(Context context) {
        sharedPreferences = (SharedPreferences) context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public String getString(final String key) {
        return sharedPreferences.getString(key, "");
    }

    public boolean getBoolean(final String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putBoolean(final String key, final boolean b) {
        sharedPreferences.edit().putBoolean(key, b).apply();
    }

    public void putString(final String key, final String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }


    /**
     * Clears the all Shared Preference data
     */
    public void clearAllPreferenceData() {
        sharedPreferences.edit().clear().apply();
    }
}

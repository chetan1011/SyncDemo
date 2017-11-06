package com.indianic.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.indianic.AndroidApp;
import com.indianic.R;

/**
 * <p>
 * Purpose of this class is to save data in preference and retrieve values from preference throughout the lifecycle of application
 * This class is hold methods for storing and retrieving values from preference.
 * </p>
 */
public class Preference {
    private static Preference preference;

    /**
     * Preference key for userId
     */
    private final String PREFERENCE_USER_ID = "USER_ID";
    private final String PREFERENCE_USER_IS_LOGIN = "USER_IS_LOGIN";
    final String PREFERENCE_LANG_ID = "LANG_ID";


    private SharedPreferences sharedPreferences;

    private Preference() {
        sharedPreferences = AndroidApp.getInstance().getSharedPreferences(AndroidApp.getInstance().getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    /**
     * @return the {@link SharedPreferences} object that will be used to save values in the application preference
     */
    public static Preference getInstance() {
        if (preference == null) {
            preference = new Preference();
        }
        return preference;
    }

    /**
     * Stores the {@link String} value in the preference
     *
     * @param key   {@link String} parameter for the key for the values in preference
     * @param value {@link String} parameter for the value to be stored in preference
     */
    public void savePreferenceData(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Stores the {@link Boolean} value in the preference
     *
     * @param key   {@link String} parameter for the key for the values in preference
     * @param value {@link Boolean} parameter for the value to be stored in preference
     */
    public void savePreferenceData(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Stores the {@link Integer} value in the preference
     *
     * @param key   {@link String} parameter for the key for the values in preference
     * @param value {@link Integer} parameter for the value to be stored in preference
     */
    public void savePreferenceData(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Stores the {@link Long} value in the preference
     *
     * @param key   {@link String} parameter for the key for the values in preference
     * @param value {@link Long} parameter for the value to be stored in preference
     */
    public void savePreferenceData(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * clearAllPreferenceData : it will clear all data from preference
     */
    public void clearAllPreferenceData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

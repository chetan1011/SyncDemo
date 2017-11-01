package com.indianic.customview;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

class FontCache {
    private static final Hashtable<String, Typeface> FONT_CACHE = new Hashtable<>();

    public static Typeface get(final String name, final Context mContext) {
        Typeface mTypeface = FONT_CACHE.get(name);
        if (mTypeface == null) {
            try {
                mTypeface = Typeface.createFromAsset(mContext.getAssets(), name);
            } catch (Exception e) {
                return null;
            }
            FONT_CACHE.put(name, mTypeface);
        }
        return mTypeface;
    }
}
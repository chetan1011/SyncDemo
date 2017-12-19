package com.indianic.util;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.indianic.R;
import com.indianic.activity.HomeActivity;

import java.util.ArrayList;

/**
 * Manages shortcut menu options on long press of app icon in Mobile device.
 * <p>
 * This feature is available from Android 7.1 and above.
 * <p>
 * Due to unavailability of Android 7.1 device the below methods are commented because this function is tested into Android Emulators only.
 * We can deploy this feature by uncommenting below methods & after proper testing in real Android devices.
 */
public class AppShortcutManager {

    public final static String INTENT_KEY_APP_SHORTCUT_MENU = "INTENT_KEY_APP_SHORTCUT_MENU";

    /**
     * //This will be used as a intent value for specific menu, specify the meaning full unique id for each of the shortcut menu item.
     */
    public final static String MENU_1 = "MENU_1";
    public final static String MENU_2 = "MENU_2";
    public final static String MENU_3 = "MENU_3";


    /**
     * Sets the app shortcut.
     */
    public static void setShortcutMenu(final Context context, final Class<? extends Activity> activityToOpen) {

        if (context == null || activityToOpen == null) {
            throw new IllegalArgumentException("null argument is not allowed.");
        }

        /*
         * Position of the shortcut menu item
         */
        final int RANK_1 = 1;
        final int RANK_2 = 2;
        final int RANK_3 = 3;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {

            final ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);

            final ArrayList<ShortcutInfo> list = new ArrayList<>();

            final ShortcutInfo shortcutCart = createShortcut(context, RANK_1, MENU_1, "Replace_this_with_menu_lable_1", R.drawable.ic_nav_cart, activityToOpen);
            final ShortcutInfo shortcutAccount = createShortcut(context, RANK_2, MENU_2, "Replace_this_with_menu_lable_2", R.drawable.ic_nav_search, activityToOpen);
            final ShortcutInfo shortcutOrders = createShortcut(context, RANK_3, MENU_3, "Replace_this_with_menu_lable_3", R.drawable.ic_nav_slider, activityToOpen);

            list.add(shortcutAccount);
            list.add(shortcutCart);
            list.add(shortcutOrders);

            if (shortcutManager != null) {
                shortcutManager.removeAllDynamicShortcuts();
                shortcutManager.setDynamicShortcuts(list);
            }
        }
    }

    /**
     * Removes all the app shortcut.
     */
    public static void removeAllShortcutMenu(final Context context) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
                final ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);

                if (shortcutManager != null) {
                    shortcutManager.removeAllDynamicShortcuts();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @NonNull
    private static ShortcutInfo createShortcut(final Context context, int i, final String id, final String label, final int resourceIcon, final Class<? extends Activity> activityToOpen) {
        final Intent intent = new Intent(context, activityToOpen);
        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(INTENT_KEY_APP_SHORTCUT_MENU, id);

        final ShortcutInfo.Builder builder = new ShortcutInfo.Builder(context, id);
        builder.setShortLabel(label);
        builder.setLongLabel(label);
        builder.setIcon(Icon.createWithResource(context, resourceIcon));
        builder.setRank(i);
        builder.setIntent(intent);

        return builder.build();
    }
}

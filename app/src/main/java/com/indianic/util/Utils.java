package com.indianic.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.indianic.R;

import java.io.File;
import java.util.Locale;


/**
 * Performs common utility operations.
 */
public class Utils {

    /**
     * checks the GPS is enable or not
     *
     * @param activity    object required for get SystemService
     * @param showMessage if true will show enable GPS alert with got to settings option otherwise check silently
     * @return true if location enabled otherwise false
     */
    public static boolean checkLocationAccess(final Activity activity, boolean showMessage) {
        if (activity != null && !activity.isFinishing()) {
            final LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isNetworkEnabled) {
                return true;
            } else if (isGpsEnabled) {
                return true;
            }

            if (showMessage) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                dialogBuilder.setTitle(activity.getString(R.string.app_name));
                dialogBuilder.setCancelable(false);
                dialogBuilder.setMessage(activity.getString(R.string.alert_check_gps));

                dialogBuilder.setPositiveButton(activity.getString(R.string.settings), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });

                dialogBuilder.setNegativeButton(activity.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface mDialog, int id) {
                        mDialog.dismiss();
                    }
                });
                dialogBuilder.show();

                return false;
            }
        }
        return false;
    }


    /**
     * Validates the Email Id
     *
     * @param emailId email id to be verified
     * @return true valid email id, false invalid emailid
     */
    public static boolean isValidEmailId(final String emailId) {
        return !TextUtils.isEmpty(emailId) && Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
    }

    /**
     * Validates the Url
     *
     * @param url email id to be verified
     * @return true valid email id, false invalid emailid
     */
    public static boolean isValidUrl(final String url) {
        return !TextUtils.isEmpty(url) && Patterns.WEB_URL.matcher(url).matches();
    }


    /**
     * Hide the soft keyboard from screen for edit text only
     *
     * @param context context of current visible activity
     * @param view    clicked view
     */
    public static void hideSoftKeyBoard(final Context context, final View view) {
        try {
            final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * Show soft keyboard on auto focus of edittext
     *
     * @param context context of current visible activity
     * @param view    focused view
     */
    public static void showKeyboard(final Context context, final View view) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * Returns android secure id.
     */
    @SuppressLint("HardwareIds")
    public static String getDeviceId(final Context context) {
        try {
            String deviceId = null;
            // 1 android ID - unreliable
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            if (deviceId == null) {
                // 2 compute DEVICE ID
                deviceId = "35" + // we make this look like a valid IMEI
                        Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
                        + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10; // 13
            }
            return deviceId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * Call snack bar which will disapper in 3-5 seconds
     *
     * @return Snack bar
     */
    public static Snackbar showSnackBar(final Context context, final View view, final String message, final String defaultMessage, final String actionLabel, final View.OnClickListener clickListener) {
        if (view == null) {
            return null;
        }

        final Snackbar snackbar = Snackbar.make(view, TextUtils.isEmpty(message) ? defaultMessage : message, Snackbar.LENGTH_LONG);
        final View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(ContextCompat.getColor(context, isError ? android.R.color.holo_red_dark : android.R.color.holo_green_dark));
        if (!TextUtils.isEmpty(actionLabel)) {
            snackbar.setAction(actionLabel, clickListener);
        }
        final TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.show();
        return snackbar;
    }

    /**
     * Returns the device's DisplayMetrics
     */
    public static DisplayMetrics getDeviceMetrics(final Activity activity) {
        final DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * Save Language specific to application using ConfigLocale
     *
     * @param languageCode code of language which want to set
     */
    public static void saveLanguageSetting(final Context context, String languageCode) {
        if (!languageCode.equalsIgnoreCase("en") && !languageCode.equalsIgnoreCase("it")) {
            languageCode = "en";
        }

        final Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        final Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        Preference.getInstance().setData(Preference.PREFERENCE_LANG_ID, languageCode.toUpperCase());
    }

    /**
     * to get file uri as per OS version check for pre Marshmallow uri also
     *
     * @param context context of current visible activity
     * @param file    file which Uri whants
     * @return file Uri
     */
    public static Uri getUri(final Context context, final File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (context != null) {
                return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file);
            } else {
                return Uri.fromFile(file);
            }
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * Launch web page into third party app.
     */
    public static void openWebPage(final Activity activity, final String url) {
        if (activity != null && !TextUtils.isEmpty(url)) {
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
            }
        }
    }

    /**
     * set formatted Html text
     *
     * @param formattedString formatted string
     * @return spanned text
     */
    public static Spanned setFormattedHtmlString(final String formattedString) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(formattedString, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(formattedString);
        }
    }


    /**
     * Performs the share intent.
     */
    public static void shareItem(final Activity activity, final String url, final String chooserTitle) {
        if (!TextUtils.isEmpty(url) && activity != null) {
            final Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, url);
            sendIntent.setType("text/plain");
            activity.startActivity(Intent.createChooser(sendIntent, chooserTitle));
        }
    }


    /**
     * Cancels the running async task.
     */
    public static boolean cancelAsyncTask(final AsyncTask asyncTask) {
        if (asyncTask != null && asyncTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            asyncTask.cancel(true);
        }
        return false;
    }

    /**
     * checks the device has camera or not
     *
     * @param mActivity object required for get package manager
     * @return true if camera is available otherwise false
     */
    public static boolean isCamera(Activity mActivity) {
        if (mActivity != null && !mActivity.isFinishing()) {
            PackageManager packageManager = mActivity.getPackageManager();

            return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
        } else {
            return false;
        }
    }
//    /**
//     * Returns true if 80% scrolling is done.
//     */
//    private static boolean needLoadMore(int lastVisibleItemPosition, int totalItemCount) {
//        final int max = (int) (totalItemCount * 0.8);
//        return (lastVisibleItemPosition) >= max && lastVisibleItemPosition >= 0;
//    }
//
//    /**
//     * Returns true if it need to load more items.
//     */
//    public static boolean needLoadMore(final GridLayoutManager layoutManager) {
//        int totalItemCount = layoutManager.getItemCount();
//        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//
//        return needLoadMore(lastVisibleItemPosition, totalItemCount);
//    }
//
//    /**
//     * Returns true if it need to load more items.
//     */
//    public static boolean needLoadMore(final LinearLayoutManager layoutManager) {
//        int totalItemCount = layoutManager.getItemCount();
//        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//
//        return needLoadMore(lastVisibleItemPosition, totalItemCount);
//    }
}

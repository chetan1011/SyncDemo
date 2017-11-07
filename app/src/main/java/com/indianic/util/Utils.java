package com.indianic.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.indianic.R;


/**
 * Performs common utility operations.
 */
public class Utils {


    /*
     * Checks the Network availability.
     *
     * @return true if internet available, false otherwise
     */
//    public boolean isNetworkAvailable(Context context) {
//        if (context != null) {
//            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            if (connectivityManager != null) {
//                final NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
//                if (activeNetwork != null) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    /**
     * Checks the Network availability.
     *
     * @return true if internet available, false otherwise
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (mConnectivityManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    final Network[] allNetworks = mConnectivityManager.getAllNetworks();

                    for (Network network : allNetworks) {
                        final NetworkInfo networkInfo = mConnectivityManager.getNetworkInfo(network);
                        if (networkInfo != null && networkInfo.isConnected()) {
                            return true;
                        }
                    }

                } else {
                    boolean wifiNetworkConnected = false;
                    boolean mobileNetworkConnected = false;

                    final NetworkInfo mobileInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    final NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                    if (mobileInfo != null) {
                        mobileNetworkConnected = mobileInfo.isConnected();
                    }

                    if (wifiInfo != null) {
                        wifiNetworkConnected = wifiInfo.isConnected();
                    }

                    return (mobileNetworkConnected || wifiNetworkConnected);
                }
            }
        }
        return false;
    }

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
                final AlertDialog.Builder mDialog = new AlertDialog.Builder(activity);
                mDialog.setTitle(activity.getString(R.string.app_name));
                mDialog.setCancelable(false);
                mDialog.setMessage(activity.getString(R.string.alert_check_gps));

                mDialog.setPositiveButton(activity.getString(R.string.settings), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface mDialog, int id) {
                        mDialog.dismiss();
                        activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });

                mDialog.setNegativeButton(activity.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface mDialog, int id) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();

                return false;
            }
        }
        return false;
    }


    /**
     * Displays alert dialog to show common messages.
     *
     * @param title   Title of the Dialog : Application's Name
     * @param message Message to be shown in the Dialog displayed
     * @param context Context of the Application, where the Dialog needs to be displayed
     */
    public static void displayDialog(final Context context, final String title, final String message) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);

        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        if (!((Activity) context).isFinishing()) {
            alertDialog.show();
        }
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
    public static Snackbar showSnackBar(final Context context, final View view, final boolean isError, final String message, final String defaultMessage) {
        if (view == null) {
            return null;
        }

        final Snackbar snackbar = Snackbar.make(view, TextUtils.isEmpty(message) ? defaultMessage : message, Snackbar.LENGTH_LONG);
        final View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, isError ? android.R.color.holo_red_dark : android.R.color.holo_green_dark));
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
     * Returns true if 80% scrolling is done.
     */
    private static boolean needLoadMore(int lastVisibleItemPosition, int totalItemCount) {
        final int max = (int) (totalItemCount * 0.8);
        return (lastVisibleItemPosition) >= max && lastVisibleItemPosition >= 0;
    }

    /**
     * Returns true if it need to load more items.
     */
    public static boolean needLoadMore(final GridLayoutManager layoutManager) {
        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        return needLoadMore(lastVisibleItemPosition, totalItemCount);
    }

    /**
     * Returns true if it need to load more items.
     */
    public static boolean needLoadMore(final LinearLayoutManager layoutManager) {
        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        return needLoadMore(lastVisibleItemPosition, totalItemCount);
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

}

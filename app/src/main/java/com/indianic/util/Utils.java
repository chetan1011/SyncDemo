package com.indianic.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


/**
 * Performs common utility operations.
 */
public class Utils {

    /**
     * Checks the Network availability.
     *
     * @return true if internet available, false otherwise
     */

    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager != null) {
                final NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    return true;
                }
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
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
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

        final Snackbar snackbar = Snackbar.make(view, isNullString(message) ? defaultMessage : message, Snackbar.LENGTH_LONG);
        final View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, isError ? android.R.color.holo_red_dark : android.R.color.holo_green_dark));
        final TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.show();
        return snackbar;
    }

    /**
     * Called to check permission(In Android M and above versions only)
     *
     * @param permission, which we need to pass
     * @return true, if permission is granted else false
     */
    public static boolean checkForPermission(final Context context, final String permission) {
        int result = ContextCompat.checkSelfPermission(context, permission);
        //If permission is granted then it returns 0 as result
        return result == PackageManager.PERMISSION_GRANTED;
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

    /**
     * Returns true if string is null.
     */
    public static boolean isNullString(final String string) {
        return TextUtils.isEmpty(string) || string.equalsIgnoreCase("null");
    }

    /**
     * Sets view height.
     */
    public static void setViewHeight(final Activity context, final View imageView) {
        final ViewGroup.LayoutParams params = imageView.getLayoutParams();

        final int deviceWidth = getDeviceMetrics(context).widthPixels;
        //Dividing with the 1.777 is to maintain 16:9 aspect ratio.
        params.height = (int) (deviceWidth / 1.777); //left, top, right, bottom

        imageView.setLayoutParams(params);
    }

    /**
     * Inject the css style to HTML string.
     */
    public static String getFormattedHtml(final String html) {
        final String prefix = "<html><head><style type=\"text/css\">@font-face {font-family: Muli;src: url(\"file:///android_asset/Fonts/Muli-Regular.ttf\")}body {margin: 0; padding:0;background-color:white;font-family: Muli;font-size: 12px;line-height: 180%;}</style></head><body>";

        final String pas = "</body></html>";
        return prefix + html + pas;
    }

    /*
     * Set view color according to rate.
     */
    /*public static void setRatingTextViewColor(final Context context, final View view, final double ratings) {
        final GradientDrawable drawable = (GradientDrawable) view.getBackground();
        if (drawable != null) {

            if (ratings == 0) {
                drawable.setColor(ContextCompat.getColor(context, R.color.colorTextFiledLabel));
            } else if (ratings > 0 && ratings < 3) {
                drawable.setColor(ContextCompat.getColor(context, R.color.colorRed));
            } else if (ratings >= 3 && ratings < 4) {
                drawable.setColor(ContextCompat.getColor(context, R.color.colorOrangeLight));
            } else {
                drawable.setColor(ContextCompat.getColor(context, R.color.colorGreenLight));
            }
        }
    }*/

    /**
     * ^ : start of string
     * [ : beginning of character group
     * a-z : any lowercase letter
     * A-Z : any uppercase letter
     * 0-9 : any digit
     * _ : underscore
     * ] : end of character group
     * : zero or more of the given characters
     * $ : end of string
     */

    public static boolean isAlphaNumeric(String string) {
        String pattern = "^([0-9]+[a-zA-Z]+|[a-zA-Z]+[0-9]+)[0-9a-zA-Z]*$";
        return string.matches(pattern);
    }

    /**
     * Launches the Google Play Store App if available.
     */
    public static void launchGooglePlayStore(final Activity activity) {
        if (activity == null) {
            return;
        }
        final String appPackageName = activity.getPackageName(); // getPackageName() from Context or Activity object
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}

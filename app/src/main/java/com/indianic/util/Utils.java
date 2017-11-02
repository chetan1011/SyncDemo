package com.indianic.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.indianic.R;
import com.indianic.customview.OnClickListenerWrapper;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Performs common utility operations.
 */
public class Utils {

    private static Utils ourInstance = new Utils();

    private Utils() {
    }

    public static Utils getInstance() {
        return ourInstance;
    }

    /**
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
    public boolean isNetworkAvailable(Context context) {
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
     * @param mActivity   object required for get SystemService
     * @param showMessage if true will show enable GPS alert with got to settings option otherwise check silently
     * @return true if location enabled otherwise false
     */
    public boolean checkLocationAccess(final Activity mActivity, boolean showMessage) {
        if (mActivity != null && !mActivity.isFinishing()) {
            final LocationManager locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isNetworkEnabled) {
                return true;
            } else if (isGpsEnabled) {
                return true;
            }

            if (showMessage) {
                final AlertDialog.Builder mDialog = new AlertDialog.Builder(mActivity);
                mDialog.setTitle(mActivity.getString(R.string.app_name));
                mDialog.setCancelable(false);
                mDialog.setMessage(mActivity.getString(R.string.alert_check_gps));

                mDialog.setPositiveButton(mActivity.getString(R.string.settings), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface mDialog, int id) {
                        mDialog.dismiss();
                        mActivity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });

                mDialog.setNegativeButton(mActivity.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
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
    public void displayDialog(final Context context, final String title, final String message) {

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
    public boolean isValidEmailId(final String emailId) {
        return !TextUtils.isEmpty(emailId) && Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
    }

    /**
     * Validates the Url
     *
     * @param url email id to be verified
     * @return true valid email id, false invalid emailid
     */
    public boolean isValidUrl(final String url) {
        return !TextUtils.isEmpty(url) && Patterns.WEB_URL.matcher(url).matches();
    }


    /**
     * Hide the soft keyboard from screen for edit text only
     *
     * @param context context of current visible activity
     * @param view    clicked view
     */
    public void hideSoftKeyBoard(final Context context, final View view) {
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
    public void showKeyboard(final Context context, final View view) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Returns android secure id.
     */
    @SuppressLint("HardwareIds")
    public String getDeviceId(final Context context) {
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
    public Snackbar showSnackBar(final Context context, final View view, final boolean isError, final String message, final String defaultMessage) {
        if (view == null) {
            return null;
        }

        final Snackbar snackbar = Snackbar.make(view, isNullString(message) ? defaultMessage : message, Snackbar.LENGTH_LONG);
        final View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, isError ? android.R.color.holo_red_dark : android.R.color.holo_green_dark));
        final TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
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
    public boolean checkForPermission(final Context context, final String permission) {
        int result = ContextCompat.checkSelfPermission(context, permission);
        //If permission is granted then it returns 0 as result
        return result == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * checks the device has camera or not
     *
     * @param mActivity object required for get package manager
     * @return true if camera is available otherwise false
     */
    public boolean isCamera(Activity mActivity) {
        if (mActivity != null && !mActivity.isFinishing()) {
            PackageManager packageManager = mActivity.getPackageManager();

            return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
        } else {
            return false;
        }
    }

    /**
     * Returns the device's DisplayMetrics
     */
    public DisplayMetrics getDeviceMetrics(final Activity activity) {
        final DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * Save Language specific to application using ConfigLocale
     *
     * @param languageCode code of language which want to set
     */
    public void saveLanguageSetting(Context mContext, String languageCode) {
        if (!languageCode.equalsIgnoreCase("en") && !languageCode.equalsIgnoreCase("it")) {
            languageCode = "en";
        }

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        final Configuration config = new Configuration();
        config.locale = locale;
        mContext.getResources().updateConfiguration(config, mContext.getResources().getDisplayMetrics());
        Preference.getInstance().savePreferenceData(Preference.getInstance().PREFERENCE_LANG_ID, languageCode.toUpperCase());
    }

    /**
     * to get file uri as per OS version check for pre Marshmallow uri also
     *
     * @param mContext context of current visible activity
     * @param mFile    file which Uri whants
     * @return file Uri
     */
    public Uri getUri(final Context mContext, final File mFile) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (mContext != null) {
                return FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".fileprovider", mFile);
            } else {
                return Uri.fromFile(mFile);
            }
        } else {
            return Uri.fromFile(mFile);
        }
    }

    /**
     * Launch web page into third party app.
     */
    public void openWebPage(final Activity activity, final String url) {
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
    public Spanned setFormattedHtmlString(final String formattedString) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(formattedString, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(formattedString);
        }
    }

    /**
     * formats date and time in desired format.
     *
     * @param oldDate   {@link String} object that convert into new format
     * @param oldFormat {@link String} format from which oldDate will convert
     * @param newFormat {@link String} format in which oldDate will convert
     * @return {@link String} object of new formatted date.
     */
    public String formatDateTime(String oldDate, String oldFormat, String newFormat) {
        String newTime = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(oldFormat, Locale.US);
            Date newDate = sdf.parse(oldDate);
            sdf.applyPattern(newFormat);
            newTime = sdf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newTime;
    }

    /**
     * Performs the share intent.
     */
    public void shareItem(final Activity activity, final String url, final String chooserTitle) {
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
    private boolean needLoadMore(int lastVisibleItemPosition, int totalItemCount) {
        final int max = (int) (totalItemCount * 0.8);
        return (lastVisibleItemPosition) >= max && lastVisibleItemPosition >= 0;
    }

    /**
     * Returns true if it need to load more items.
     */
    public boolean needLoadMore(final GridLayoutManager layoutManager) {
        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        return needLoadMore(lastVisibleItemPosition, totalItemCount);
    }

    /**
     * Returns true if it need to load more items.
     */
    public boolean needLoadMore(final LinearLayoutManager layoutManager) {
        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        return needLoadMore(lastVisibleItemPosition, totalItemCount);
    }

    /**
     * Cancels the running async task.
     */
    public boolean cancelAsyncTask(final AsyncTask asyncTask) {
        if (asyncTask != null && asyncTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            asyncTask.cancel(true);
        }
        return false;
    }

    /**
     * Returns true if string is null.
     */
    public boolean isNullString(final String string) {
        return TextUtils.isEmpty(string) || string.equalsIgnoreCase("null");
    }

    /**
     * Sets view height.
     */
    public void setViewHeight(final Activity context, final View imageView) {
        final ViewGroup.LayoutParams params = imageView.getLayoutParams();

        final int deviceWidth = getDeviceMetrics(context).widthPixels;
        //Dividing with the 1.777 is to maintain 16:9 aspect ratio.
        params.height = (int) (deviceWidth / 1.777); //left, top, right, bottom

        imageView.setLayoutParams(params);
    }

    /**
     * Inject the css style to HTML string.
     */
    public String getFormattedHtml(final String html) {
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

    public boolean isAlphaNumeric(String string) {
        String pattern = "^([0-9]+[a-zA-Z]+|[a-zA-Z]+[0-9]+)[0-9a-zA-Z]*$";
        return string.matches(pattern);
    }

    /**
     * Launches the Google Play Store App if available.
     */
    public void launchGooglePlayStore(final Activity activity) {
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

    /**
     * Custom view as app theme common dialog
     *
     * @param mContext              Activity context
     * @param title                 Title text
     * @param msg                   Message text
     * @param strPositiveBtn        Positive button text
     * @param strNegativeBtn        Negative button text
     * @param isTitle               Show title if true otherwise not
     * @param isNegativeBtn         Show negative if true otherwise not
     * @param mDialogButtonListener Listener for positive and negative buttons of dialog
     */
    public void displayDialog(final Context mContext, final String title, String msg, final String strPositiveBtn,
                              final String strNegativeBtn, final boolean isTitle, final boolean isNegativeBtn, final DialogButtonListener mDialogButtonListener) {
        if (mContext != null && !((Activity) mContext).isFinishing()) {
            if (TextUtils.isEmpty(msg)) {
                msg = mContext.getString(R.string.alert_some_error);
            }
            final Dialog mDialog = new Dialog(mContext, R.style.DialogCommon);
            final WindowManager.LayoutParams wlmp = mDialog.getWindow().getAttributes();
            wlmp.gravity = Gravity.CENTER;
            wlmp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlmp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mDialog.getWindow().setAttributes(wlmp);
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.dialog_custom_common);

            final TextView tvTitle = mDialog.findViewById(R.id.common_dialog_tvTitle);
            final TextView tvMsg = mDialog.findViewById(R.id.common_dialog_tvMsg);
            final TextView btnPositive = mDialog.findViewById(R.id.common_dialog_btnPositive);
            final TextView btnNegative = mDialog.findViewById(R.id.common_dialog_btnNegative);

            tvTitle.setText(title.trim());
            tvMsg.setText(setFormattedHtmlString(msg.trim()));

            if (isTitle) {
                tvTitle.setVisibility(View.VISIBLE);
            } else {
                tvTitle.setVisibility(View.GONE);
            }

            btnPositive.setOnClickListener(new OnClickListenerWrapper(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mDialogButtonListener.onPositiveButtonClicked();
                }
            }));

            if (!TextUtils.isEmpty(strPositiveBtn.trim())) {
                btnPositive.setText(strPositiveBtn.trim());
            }

            if (isNegativeBtn) {
                btnNegative.setVisibility(View.VISIBLE);

                btnNegative.setOnClickListener(new OnClickListenerWrapper(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        mDialogButtonListener.onNegativeButtonClicked();
                    }
                }));
                if (!TextUtils.isEmpty(strNegativeBtn.trim())) {
                    btnNegative.setText(strNegativeBtn.trim());
                }
            } else {
                btnNegative.setVisibility(View.GONE);
            }
            mDialog.show();
        }
    }

    public interface DialogButtonListener {
        void onPositiveButtonClicked();

        void onNegativeButtonClicked();
    }
}

package com.test.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.test.R;


/**
 * Created by B.S on 15/04/16.
 * Purpose of this Class is to display different dialog in application for example : Progress Dialog, Information Dialog
 */
public class DisplayDialog {
    private static DisplayDialog ourInstance;
    /**
     * A Progress Dialog object
     */
    private static ProgressDialog mDialog;

    private DisplayDialog() {
    }

    public static DisplayDialog getInstance() {
        if (ourInstance == null) {
            ourInstance = new DisplayDialog();
        }
        return ourInstance;
    }
    //    private static ProgressDialog mDialog;

    /**
     * Displays the progress dialog on activity.
     * This method will generate progress dialog and displays it on screen if its not currently showing,
     * If the progressbar dialog already been showing than it will not generate new dialog and return old generated dialog.
     *
     * @param mContext     requires to create ProgressDialog in Application
     * @param message      displays the message on Progress Dialog
     * @param isCancelable Set cancelable property of progress dialog
     * @return Returns the object of Progress dialog that currently generated or previously generated.
     */
    public ProgressDialog showProgressDialog(final Context mContext, final String message, boolean isCancelable) {
        if (mDialog == null) {
            mDialog = new ProgressDialog(mContext, R.style.progress_bar_style);
        }
        if (!mDialog.isShowing()) {
            mDialog = new ProgressDialog(mContext, R.style.progress_bar_style);
            mDialog.show();
        }
        mDialog.setCancelable(isCancelable);
        mDialog.setCanceledOnTouchOutside(false);
        //mDialog.setMessage(message);
        return mDialog;
    }


    /**
     * Dismiss Progress dialog if it is showing
     */
    public void dismissProgressDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


}

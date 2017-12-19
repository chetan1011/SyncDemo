package com.test.webservice.okhttp;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Base class for all API class manages common functions.
 */
public abstract class BaseWSHandler {

    protected final String TAG = this.getClass().getSimpleName();


    protected boolean isSuccess;
    protected int code;
    protected String message = "";


    /**
     * Returns the API message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the API code.
     */
    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

}

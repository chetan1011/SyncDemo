package com.test.webservice.okhttp;

import android.content.Context;

import com.test.R;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * WSAddUser class created
 * This webservice class is for check the add user.
 */

public class WSAddUser {
    private Context mContext;
    private int code;
    private String message;

    public WSAddUser(final Context mContext) {
        this.mContext = mContext;
        message = mContext.getString(R.string.something_went_wrong_msg);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void executeService(String userModel) {
        final String url = WSConstants.BASE_URL + WSConstants.API_METHOD_ADDUSER;
        final String response;
        response = WSUtils.callHttpPost(mContext, url, generateRequest(userModel).toString());
        parseResponse(response);

    }

    private RequestBody generateRequest(String userModel) {

        final FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        formEncodingBuilder.add(WSConstants.API_PARAM_DATA, userModel);
        return formEncodingBuilder.build();
    }

    private void parseResponse(final String response) {
        if (response != null && response.trim().length() > 0) {
            final JSONObject mainObject;
            try {
                mainObject = new JSONObject(response);
                code = mainObject.optInt("error_code");
                message = mainObject.optString("message");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

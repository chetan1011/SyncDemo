package com.test.webservice.okhttp;

import android.content.Context;

import com.test.R;
import com.test.model.UserModel;
import com.test.util.Preference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * WSUserList class created
 * This webservice class is for check the user lists
 */

public class WSUserList {
    private Context mContext;
    private int code;
    private String message;
    private UserModel userListModel;
    private ArrayList<UserModel> userListModelArrayList;

    public WSUserList(final Context mContext) {
        this.mContext = mContext;
        message = mContext.getString(R.string.something_went_wrong_msg);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<UserModel> executeService() {
        final String url = WSConstants.BASE_URL + WSConstants.API_METHOD_USERLIST;
        final String response;
        response = WSUtils.callHttpPost(mContext, url, generateRequest().toString());
        return parseResponse(response);

    }

    private RequestBody generateRequest() {

        final FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        formEncodingBuilder.add(WSConstants.API_PARAM_TIMESTAMP, Preference.getInstance().getData("lasttimestamp", ""));
        return formEncodingBuilder.build();
    }

    private ArrayList<UserModel> parseResponse(final String response) {
        userListModelArrayList = new ArrayList<>();
        if (response != null && response.trim().length() > 0) {
            final JSONObject mainObject;
            try {
                mainObject = new JSONObject(response);
                code = mainObject.optInt("error_code");
                message = mainObject.optString("message");
                JSONArray jDataArray = mainObject.getJSONArray("brand_list");
                if (jDataArray != null && jDataArray.length() > 0) {
                    for (int i = 0; i < jDataArray.length(); i++) {
                        JSONObject jsonObject = jDataArray.getJSONObject(i);
                        userListModel = new UserModel();
                        userListModel.setId(jsonObject.optString("id"));
                        userListModel.setDesc(jsonObject.optString("description"));
                        userListModel.setName(jsonObject.optString("name"));
                        userListModel.setCreatedAt(jsonObject.optString("created_at"));
                        userListModelArrayList.add(userListModel);


                    }
                }

                return userListModelArrayList;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

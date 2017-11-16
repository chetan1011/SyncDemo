package com.indianic.webservice;


import android.content.Context;
import android.support.annotation.NonNull;

import com.indianic.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Provides the different methods to call the webservice.
 */
public class WSUtils {

    /**
     * Below mentioned urls are for OkHttp guidance
     * <p>
     * https://guides.codepath.com/android/Using-OkHttp
     * https://github.com/square/okhttp/wiki/Recipes
     */

    private final static long CONNECTION_TIME_OUT = 60;

    private final static long READ_TIME_OUT = 60;

    private final static String TAG = WSUtils.class.getSimpleName();

    public static String callHttpGet(final Context context, final String url) {
        try {
            final OkHttpClient okHttpClient = getOkHttpClient();

            final Request.Builder builder = new Request.Builder();
            builder.url(url);

            //* Add authentication header if required *//*
//            builder.addHeader("Authorization", "bearer 896b21d1-52c1-4a24-a5b2-819f813fa0ff");
//            builder.addHeader("Authorization", "bearer 896b21d1-52c1-4a24-a5b2-819f813fa0ff");

            final Response response;
            response = okHttpClient.newCall(builder.build()).execute();

            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            return getNetworkError(context);
        }
    }

    public static String callHttpPost(final Context context, final String url, final String jsonString) {
        try {

            final OkHttpClient client = new OkHttpClient();
            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            final RequestBody jsonBody = RequestBody.create(JSON, jsonString);

            final Request request = new Request.Builder().url(url).post(jsonBody).build();

            Response response = client.newCall(request).execute();

            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            return getNetworkError(context);
        }
    }

    static String callHttpPostCancellable(final Context context, final String url, final String jsonString, final HttpCallListener listener) {
        try {

            final OkHttpClient client = new OkHttpClient();
            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            final RequestBody jsonBody = RequestBody.create(JSON, jsonString);

            final Request request = new Request.Builder()
                    .url(url)
                    .post(jsonBody)
                    .build();

            final Call call = client.newCall(request);
            if (listener != null) {
                listener.onNewCall(call);
            }
            Response response = call.execute();


            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            return getNetworkError(context);
        }
    }


    static String callHttpMultiPart(final Context context, final String url, final RequestBody requestBody) {
        try {
            final OkHttpClient client = getOkHttpClient();
            /* You can add multipart params into RequestBody as shown in below commented example code */

//            final String IMGUR_CLIENT_ID = "...";
//            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//            final RequestBody requestBody = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM)
//                    .addFormDataPart("title", "Square Logo")
//                    .addFormDataPart("image", "logo-square.png",
//                            RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
//                    .build();

            final Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            final Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            //            Log.e(TAG, "Response ---: " + responseString);

//            Log.e(TAG, "Response Time in milliseconds : " + (l1 - l));

            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            return getNetworkError(context);
        }
    }

    @NonNull
    private static OkHttpClient getOkHttpClient() {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient().newBuilder();
        okHttpBuilder.connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
        return okHttpBuilder.build();
    }

    private static String getNetworkError(final Context context) {
        try {
            final JSONObject object = new JSONObject();
            object.put("isSuccess", WSConstants.STATUS_FAIL);
            object.put("code", WSConstants.STATUS_NETWORK_ERROR);

            if (context != null) {
                object.put("message", context.getString(R.string.alert_network_error));
            } else {
                object.put("message", "Network error! Try after sometime.");
            }
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public interface HttpCallListener {
        void onNewCall(final Call call);
    }
    /*
     *  Do not delete below items
     *  Below items can be use in future to implement basic authentication in API
     */


    /*private final static String AUTHORIZED_USER_NAME = "username";
    private final static String AUTHORIZED_PASSWORD = "password";
    private final static String AUTHORIZATION = "Authorization";
    private final static String CREDENTIAL = Credentials.basic(AUTHORIZED_USER_NAME, AUTHORIZED_PASSWORD);*/

    /*
        Do not delete below commented methods as they cann be used in future.
        Below methods can be used in case of particular API call
     */
   /* public static void callHttpGet(final Context context, final String url) {
        try {
            final OkHttpClient okHttpClient = getOkHttpClient();

            final Request.Builder builder = new Request.Builder();
            builder.url(url);

            *//* Add authentication header if required *//*
            //builder.header(AUTHORIZATION, CREDENTIAL);

            final Response response;
            response = okHttpClient.newCall(builder.build()).execute();

            final String responseString = response.body().string();

//            Log.e(TAG, "Response : " + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void callHttpPost(final Context context, final String url, final RequestBody requestBody) {
        try {
            final OkHttpClient client = getOkHttpClient();
            *//* You can add params into RequestBody as shown in below commented example code *//*

            // final RequestBody formBody = new FormBody.Builder()
            //  .add("key", "value")
            //  .build();

            final Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            final Response response = client.newCall(request).execute();
            final String responseString = response.body().string();
//            Log.e(TAG, "Response : " + responseString);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }*/
}

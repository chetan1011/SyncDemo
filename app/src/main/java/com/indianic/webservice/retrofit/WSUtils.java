package com.indianic.webservice.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Webservice utility class.
 */

public class WSUtils {

    private static WSCallback wsCallback;

    /**
     * Static method to to get api client instance
     *
     * @return ApiCallback instance
     */
    public static WSCallback getClient() {

        try {
            if (wsCallback == null) {

                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.connectTimeout(WSConstants.WS_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
                httpClient.readTimeout(WSConstants.WS_READ_TIMEOUT, TimeUnit.SECONDS);

                /**
                 *  interceptor for printing logs of request and response
                 */
//                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//                httpClient.addInterceptor(logging);  // <-- this is the important line!

                Retrofit client = new Retrofit.Builder()
                        .baseUrl(WSConstants.WS_BASE_URL)
                        .client(httpClient.build())
//                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                wsCallback = client.create(WSCallback.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wsCallback;
    }

    @NonNull
    public static MultipartBody.Part getFileMultiPart(final Context context, final String partName, final File file) {

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

}
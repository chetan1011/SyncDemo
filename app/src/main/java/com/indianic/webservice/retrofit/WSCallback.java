package com.indianic.webservice.retrofit;


import org.json.JSONArray;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Retrofit callback methods.
 */

public interface WSCallback {

    @GET(WSConstants.WS_METHOD_USERS_LOGIN)
    Call<ResponseBody> login(@Query(WSConstants.WS_PARAM_EMAIL) String email, @Query(WSConstants.WS_PARAM_PASSWORD) String password);

    @GET(WSConstants.WS_METHOD_FORGOT_PASSWORD)
    Call<ResponseBody> forgotPassword(@Query(WSConstants.WS_PARAM_EMAIL) String email);


    @Multipart
    @POST(WSConstants.WS_METHOD_UPDATE_USERS_PROFILE)
    Call<ResponseBody> updateUserProfile(@Query(WSConstants.WS_PARAM_USERS_ID) String userId, @Query(WSConstants.WS_PARAM_FIRST_NAME) String firstName, @Query(WSConstants.WS_PARAM_LAST_NAME) String lastName, @Query(WSConstants.WS_PARAM_PHONE_NUMBER) String phoneNumber, @Query(WSConstants.WS_PARAM_SALUTATION) String salutation, @Part MultipartBody.Part profileImage, @Query(WSConstants.WS_PARAM_IS_FILE) String isFile);

    @POST(WSConstants.WS_METHOD_UPDATE_USERS_PROFILE)
    Call<ResponseBody> updateUserProfile(@Query(WSConstants.WS_PARAM_USERS_ID) String userId, @Query(WSConstants.WS_PARAM_FIRST_NAME) String firstName, @Query(WSConstants.WS_PARAM_LAST_NAME) String lastName, @Query(WSConstants.WS_PARAM_PHONE_NUMBER) String phoneNumber, @Query(WSConstants.WS_PARAM_SALUTATION) String salutation, @Query(WSConstants.WS_PARAM_IS_FILE) String isFile, @Query(WSConstants.WS_PARAM_PROFILE_IMAGE) String profileImage);

}

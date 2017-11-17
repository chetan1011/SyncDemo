package com.indianic.webservice.okhttp;

/**
 * Constants variable for web service call and response parsing.
 */
public class WSConstants {
    /*
     * API URL : QA
     */
    public static final String BASE_URL = "API BASE URL";

    public static final int STATUS_SUCCESS = 1;
    static final int STATUS_FAIL = 0;
    static final int STATUS_NETWORK_ERROR = 201;

    public static final int LIST_DATA_LIMIT = 10;

    public static final int DEFAULT_PAGE = 1;

    static final int HOME_SCREEN_DATA_LIMIT = 5;
    static final String DEVICE_TYPE_ANDROID = "Android";


    public static final String API_SUCCESS = "success";
    public static final String API_CODE = "statusCode";
    public static final String API_MESSAGE = "message";
    public static final String API_DATA = "data";
    static final String API_METHOD = "method";
    static final String API_PARAMS = "params";

}

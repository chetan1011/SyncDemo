package com.test.webservice.okhttp;

/**
 * Constants variable for web service call and response parsing.
 */
public class WSConstants {
    /*
     * API URL : QA
     */
    public static final String BASE_URL = "http://appsdata2.cloudapp.net/demo/androidApi/";

    public static final int STATUS_SUCCESS = 1;
    static final int STATUS_FAIL = 0;
    static final int STATUS_NETWORK_ERROR = 201;

    static final String API_METHOD_USERLIST = "list.php";
    static final String API_METHOD_ADDUSER = "insert.php";


    public static final String API_PARAM_NAME = "name";
    public static final String API_PARAM_DESCRIPTION = "description";
    public static final String API_PARAM_TIMESTAMP = "timestamp";
    public static final String API_PARAM_BRAND = "brand";
    public static final String API_PARAM_DATA = "data";
}

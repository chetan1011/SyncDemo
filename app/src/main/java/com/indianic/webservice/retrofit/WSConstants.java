package com.indianic.webservice.retrofit;

/**
 * Common webservice constants.
 */

public class WSConstants {

    /**
     * Webservice url
     */
    static final String WS_BASE_URL = "http://your_baseurl";

    /**
     * Webservice methods
     */
    static final String WS_METHOD_USERS_LOGIN = "users_login";
    static final String WS_METHOD_FORGOT_PASSWORD = "forgot_password";
    static final String WS_METHOD_UPDATE_USERS_PROFILE = "update_users_profile";

    /**
     * Webservice parameters
     */
    static final String WS_PARAM_USERS_ID = "users_id";
    static final String WS_PARAM_IS_FILE = "is_file";
    static final String WS_PARAM_EMAIL = "email";
    static final String WS_PARAM_PASSWORD = "password";
    static final String WS_PARAM_FIRST_NAME = "first_name";
    static final String WS_PARAM_LAST_NAME = "last_name";
    static final String WS_PARAM_PHONE_NUMBER = "phone_number";
    static final String WS_PARAM_SALUTATION = "salutation";

    static final String WS_PARAM_PROFILE_IMAGE = "profile_image";

    public static final String WS_STATUS_SUCCESS = "1";
    public static final String WS_STATUS_FAIL = "0";

    public static final String WS_RESPONSE_DATA = "data";
    public static final String WS_RESPONSE_SETTINGS = "settings";

    static final int WS_CONNECTION_TIMEOUT = 60;
    static final int WS_READ_TIMEOUT = 60;

}

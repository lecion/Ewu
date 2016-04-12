package com.yliec.ewu.net;

/**
 * Created by Lecion on 11/30/15.
 */
public interface Api {
    String VAL_APPID = "1018e36c7e246b78d2fd9d6ddfe63269";
    String VAL_REST = "8d1f934037f118c2af2b43d211415491";

    String BASE_URL = "http://192.168.1.100:3000/api/";

    public interface SORT_TYPE {
        int POP = 1;
        int TIME = 2;
        int PRICE = 3;
    }

    int LIMIT = 5;
}

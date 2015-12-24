package com.yliec.ewu.net;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Lecion on 12/2/15.
 */
public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originReq = chain.request();
        originReq.newBuilder()
                .addHeader("x-access-token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiNTY3OTJkMThmZGVlMGFlNzBkNDM4YTAwIiwiaWF0IjoxNDUwODY2ODExLCJleHAiOjE0NTM0NTg4MTF9.YI63AXtIGPc5FHiPl_csWVNFZStAwKkTeB_Qab8jgoU")
                .build();
        return chain.proceed(originReq);
    }


}

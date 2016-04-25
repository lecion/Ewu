package com.yliec.ewu.net.interceptor;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;
import com.yliec.ewu.app.common.TokenProvider;
import com.yliec.lsword.compat.util.L;

import java.io.IOException;

/**
 * Created by Lecion on 4/25/16.
 */
public class AuthInterceptor implements Interceptor {

    TokenProvider mTokenProvider;
    private String TAG = getClass().getSimpleName();

    public void setTokenProvider(TokenProvider tokenProvider) {
        mTokenProvider = tokenProvider;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (mTokenProvider != null) {
            L.d(TAG, "mToken " + mTokenProvider.getToken());
            return chain.proceed(chain.request().newBuilder().addHeader("x-access-token", mTokenProvider.getToken()).build());
        }
        return chain.proceed(chain.request());
    }
}

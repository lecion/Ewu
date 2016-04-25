package com.yliec.ewu.net.interceptor;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yliec.lsword.compat.util.L;

import java.io.IOException;

/**
 * Created by Lecion on 4/25/16.
 */
public class LogInterceptor implements Interceptor {

    private final String TAG = getClass().getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();

        L.d(TAG, String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        L.d(TAG, String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;

    }
}

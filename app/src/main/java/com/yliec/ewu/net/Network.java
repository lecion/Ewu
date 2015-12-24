package com.yliec.ewu.net;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Lecion on 11/30/15.
 */
public class Network {

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static Retrofit authRetrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new AuthInterceptor());
        client.networkInterceptors().add(new LogInterceptor());
        return client;
    }

    static class LogInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();

            System.out.println(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            System.out.println(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;

        }
    }

    static class AuthInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder().addHeader("x-access-token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiNTY3OTJkMThmZGVlMGFlNzBkNDM4YTAwIiwiaWF0IjoxNDUwODY5NDMyLCJleHAiOjE0NTM0NjE0MzJ9.c9acvH8LHi0HT9u8SGrTNzvNUun8-5ISVVNCrLIynQs").build();
            return chain.proceed(request);
        }
    }
}

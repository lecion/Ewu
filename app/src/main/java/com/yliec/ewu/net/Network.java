package com.yliec.ewu.net;

import com.squareup.okhttp.OkHttpClient;
import com.yliec.ewu.net.interceptor.AuthInterceptor;
import com.yliec.ewu.net.interceptor.LogInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Lecion on 11/30/15.
 */
public class Network {

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    private static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new AuthInterceptor());
        client.networkInterceptors().add(new LogInterceptor());
        return client;
    }
}
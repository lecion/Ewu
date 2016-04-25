package com.yliec.ewu.app.base;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;
import com.yliec.ewu.net.Api;
import com.yliec.ewu.net.interceptor.AuthInterceptor;
import com.yliec.ewu.net.interceptor.LogInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Lecion on 12/30/15.
 */
public abstract class BaseModel<API, M extends BaseModel> {
    private API mService;

//    private Retrofit.Builder mBuilder;

    private AuthInterceptor mAuthInterceptor;

    public BaseModel() {
        mAuthInterceptor = new AuthInterceptor();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getClient())
                .build();

//        mBuilder = new Retrofit.Builder()
//                .baseUrl(Api.BASE_URL)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create());
        mService = retrofit.create(getApiClass());
    }

    @NonNull
    private OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(mAuthInterceptor);
        client.networkInterceptors().add(new LogInterceptor());
        return client;
    }

    public M setToken(final String token) {
        mAuthInterceptor.setTokenProvider(() -> token);
        return (M) this;
    }

    public API getService() {
//        return mBuilder.client(getClient()).build().create(getApiClass());
        return mService;
    }

//    public API getAuthService() {
//        return mBuilder.client(getAuthClient()).build().create(getApiClass());
//    }
//
//    private OkHttpClient getAuthClient() {
//        OkHttpClient client = new OkHttpClient();
//        client.networkInterceptors().add(mAuthInterceptor);
//        client.networkInterceptors().add(new LogInterceptor());
//        return client;
//    }

    protected abstract Class<API> getApiClass();

}

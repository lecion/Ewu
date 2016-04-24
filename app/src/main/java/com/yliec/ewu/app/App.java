package com.yliec.ewu.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.pwittchen.prefser.library.Prefser;
import com.yliec.ewu.app.common.C;
import com.yliec.ewu.di.component.ApiComponent;
import com.yliec.ewu.di.component.AppComponent;
import com.yliec.ewu.di.component.DaggerApiComponent;
import com.yliec.ewu.di.component.DaggerAppComponent;
import com.yliec.ewu.di.module.ApiModule;
import com.yliec.ewu.di.module.AppModule;

import javax.inject.Inject;

/**
 * Created by Lecion on 4/11/16.
 */
public class App extends Application {
    private AppComponent mAppComponent;
    private ApiComponent mApiComponent;
    @Inject
    Prefser mPrefser;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
        setupComponent();
        getAppComponent().inject(this);
    }

    private void setupComponent() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        mApiComponent = DaggerApiComponent.builder().appModule(new AppModule(this)).apiModule(new ApiModule()).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public String getToken() {
        if (isLogin()) {
            return mPrefser.get(C.PRE_TOKEN, String.class, null);
        }
        return null;
    }

    public boolean isLogin() {
        return mPrefser.get(C.PRE_IS_LOGIN, Boolean.class, false);
    }

    public void logout() {
        mPrefser.remove(C.PRE_IS_LOGIN);
        mPrefser.remove(C.PRE_TOKEN);
    }
}

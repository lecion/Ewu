package com.yliec.ewu.app;

import android.app.Application;

import com.yliec.ewu.di.ApiComponent;
import com.yliec.ewu.di.ApiModule;
import com.yliec.ewu.di.AppComponent;
import com.yliec.ewu.di.AppModule;
import com.yliec.ewu.di.DaggerApiComponent;
import com.yliec.ewu.di.DaggerAppComponent;

/**
 * Created by Lecion on 4/11/16.
 */
public class App extends Application {
    private AppComponent mAppComponent;
    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        setupComponent();
    }

    private void setupComponent() {
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        mApiComponent = DaggerApiComponent.builder().apiModule(new ApiModule()).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }
}

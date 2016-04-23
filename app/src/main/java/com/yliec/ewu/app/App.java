package com.yliec.ewu.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yliec.ewu.di.component.ApiComponent;
import com.yliec.ewu.di.component.AppComponent;
import com.yliec.ewu.di.component.DaggerApiComponent;
import com.yliec.ewu.di.component.DaggerAppComponent;
import com.yliec.ewu.di.module.ApiModule;
import com.yliec.ewu.di.module.AppModule;

/**
 * Created by Lecion on 4/11/16.
 */
public class App extends Application {
    private AppComponent mAppComponent;
    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
        setupComponent();
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
}

package com.yliec.ewu.di.module;

import android.content.ContentResolver;

import com.yliec.ewu.app.App;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lecion on 4/11/16.
 */
@Module
public class AppModule {
    private App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    public App provideApplication() {
        return mApp;
    }

    @Provides
    public ContentResolver provideContentProvider() {
        return mApp.getContentResolver();
    }
}

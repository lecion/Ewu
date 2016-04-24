package com.yliec.ewu.di.module;

import android.content.ContentResolver;
import android.content.Context;

import com.github.pwittchen.prefser.library.Prefser;
import com.yliec.ewu.app.App;

import javax.inject.Singleton;

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
    @Singleton
    public Context provideApplication() {
        return mApp;
    }

    @Provides
    public ContentResolver provideContentProvider() {
        return mApp.getContentResolver();
    }

    @Singleton
    @Provides
    public Prefser providerPrefser(Context context) {
        return new Prefser(context);
    }
}

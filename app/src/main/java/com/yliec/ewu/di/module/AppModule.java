package com.yliec.ewu.di.module;

import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;

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

    @Provides
    @Singleton
    public AccountManager provideAccountManager(Context context) {
        return AccountManager.get(context);
    }
}

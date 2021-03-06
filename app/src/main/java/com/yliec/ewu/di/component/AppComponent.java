package com.yliec.ewu.di.component;

import com.yliec.ewu.app.App;
import com.yliec.ewu.di.module.AppModule;
import com.yliec.ewu.model.LocalImageModel;
import com.yliec.ewu.module.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Lecion on 4/11/16.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
//    void inject(MainFragment mainFragment);

    //    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity);

    void inject(LocalImageModel localImageModel);

    void inject(App app);
}

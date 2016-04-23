package com.yliec.ewu.di.component;

import com.yliec.ewu.di.module.ApiModule;
import com.yliec.ewu.module.login.LoginPresenter;
import com.yliec.ewu.module.main.MainPresenter;
import com.yliec.ewu.module.publish.AlbumPresenter;

import dagger.Component;

/**
 * Created by Lecion on 4/12/16.
 */
@Component(modules = ApiModule.class)
public interface ApiComponent {
    void inject(MainPresenter mainPresenter);

    void inject(AlbumPresenter albumPresenter);

    void inject(LoginPresenter loginPresenter);
}

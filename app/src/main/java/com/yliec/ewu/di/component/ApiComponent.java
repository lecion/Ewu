package com.yliec.ewu.di.component;

import com.yliec.ewu.di.module.ApiModule;
import com.yliec.ewu.module.detail.DetailPresenter;
import com.yliec.ewu.module.login.LoginPresenter;
import com.yliec.ewu.module.main.MainPresenter;
import com.yliec.ewu.module.publish.AlbumPresenter;
import com.yliec.ewu.module.publish.PublishPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Lecion on 4/12/16.
 */
@Singleton
@Component(modules = ApiModule.class)
public interface ApiComponent {
    void inject(MainPresenter mainPresenter);

    void inject(AlbumPresenter albumPresenter);

    void inject(LoginPresenter loginPresenter);

    void inject(PublishPresenter publishPresenter);

    void inject(DetailPresenter DetailPresenter);
}

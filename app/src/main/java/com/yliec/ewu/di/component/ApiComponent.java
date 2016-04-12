package com.yliec.ewu.di.component;

import com.yliec.ewu.di.module.ApiModule;
import com.yliec.ewu.module.main.MainPresenter;

import dagger.Component;

/**
 * Created by Lecion on 4/12/16.
 */
@Component(modules = ApiModule.class)
public interface ApiComponent {
    void inject(MainPresenter mainPresenter);
}

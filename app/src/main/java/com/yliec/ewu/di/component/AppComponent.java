package com.yliec.ewu.di.component;

import com.yliec.ewu.di.module.AppModule;
import com.yliec.ewu.module.publish.AlbumPresenter;

import dagger.Component;

/**
 * Created by Lecion on 4/11/16.
 */
@Component(modules = {AppModule.class})
public interface AppComponent {
//    void inject(MainFragment mainFragment);

//    void inject(MainActivity mainActivity);
    void inject(AlbumPresenter albumPresenter);
}

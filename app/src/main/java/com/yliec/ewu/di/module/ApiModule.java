package com.yliec.ewu.di.module;

import com.yliec.ewu.model.GoodsModel;
import com.yliec.ewu.model.LocalImageModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lecion on 4/11/16.
 */
@Module(includes = AppModule.class)
public class ApiModule {

    @Provides
    GoodsModel provideGoodsModel() {
        return new GoodsModel();
    }

    @Provides
    LocalImageModel provideLocalImageModel() {
        return new LocalImageModel();
    }
}

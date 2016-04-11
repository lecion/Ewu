package com.yliec.ewu.di;

import com.yliec.ewu.model.GoodsModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lecion on 4/11/16.
 */
@Module
public class ApiModule {

    @Provides
    GoodsModel provideGoodsModel() {
        return new GoodsModel();
    }
}

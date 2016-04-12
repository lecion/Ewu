package com.yliec.ewu.model;

import com.yliec.ewu.api.GoodsApi;
import com.yliec.ewu.api.entity.GoodsEntity;
import com.yliec.ewu.app.base.BaseModel;

import rx.Observable;

/**
 * Created by Lecion on 4/11/16.
 */
public class GoodsModel extends BaseModel<GoodsApi, GoodsModel> {
    @Override
    protected Class<GoodsApi> getApiClass() {
        return GoodsApi.class;
    }

    public Observable<GoodsEntity.GoodsList> getGoodsList() {
        return getService().getGoodsList();
    }

    public Observable<GoodsEntity.AGoods> getGoodsById(String id) {
        return getService().getGoods(id);
    }
}

package com.yliec.ewu.model;

import com.yliec.ewu.api.GoodsApi;
import com.yliec.ewu.api.entity.GoodsEntity;
import com.yliec.ewu.app.base.BaseModel;
import com.yliec.ewu.net.Api;

import rx.Observable;

/**
 * Created by Lecion on 4/11/16.
 */
public class GoodsModel extends BaseModel<GoodsApi, GoodsModel> {
    @Override
    protected Class<GoodsApi> getApiClass() {
        return GoodsApi.class;
    }

    public Observable<GoodsEntity.GoodsList> getGoodsList(int page, int sortType) {
        return getService().getGoodsList(page, Api.LIMIT, sortType, 0);
    }

    public Observable<GoodsEntity.GoodsList> getGoodsListByPop(int page) {
        return this.getGoodsList(page, Api.SORT_TYPE.POP);
    }

    public Observable<GoodsEntity.GoodsList> getGoodsListByTime(int page) {
        return this.getGoodsList(page, Api.SORT_TYPE.TIME);
    }

    public Observable<GoodsEntity.GoodsList> getGoodsListByPrice(int page) {
        return this.getGoodsList(page, Api.SORT_TYPE.PRICE);
    }

    public Observable<GoodsEntity.AGoods> getGoodsById(String id) {
        return getService().getGoods(id);
    }
}

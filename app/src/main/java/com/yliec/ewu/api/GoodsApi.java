package com.yliec.ewu.api;

import com.yliec.ewu.api.entity.GoodsEntity;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Lecion on 4/11/16.
 */
public interface GoodsApi {

    @GET("Goods")
    Observable<GoodsEntity.GoodsList> getGoodsList();

    @GET("Goods/{goods}")
    Observable<GoodsEntity.AGoods> getGoods(@Path("goods") String goodsId);
}

package com.yliec.ewu.net;

import com.yliec.ewu.model.Goods;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Lecion on 11/30/15.
 */
public interface Api {
    String VAL_APPID = "1018e36c7e246b78d2fd9d6ddfe63269";
    String VAL_REST = "8d1f934037f118c2af2b43d211415491";

    String BASE_URL = "http://localhost:3000/api/";

    interface GoodsApi {

        @GET("Goods")
        Call<List<Goods>> getGoodsList();

        @GET("Goods/{goods}")
        Call<Goods> getGoods(@Path("goods") String goodsId);
    }
}

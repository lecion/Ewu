package com.yliec.ewu.module.detail;

import android.os.Bundle;

import com.yliec.ewu.app.common.SchedulerTransformer;
import com.yliec.ewu.model.GoodsModel;
import com.yliec.lsword.compat.util.L;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;

/**
 * Created by Lecion on 4/25/16.
 */
public class DetailPresenter extends RxPresenter<DetailActivity> {
    public static final int REQUEST_GOODS = 1;
    private String TAG = getClass().getSimpleName();
    @Inject
    GoodsModel mGoodsModel;
    String mGoodsId;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        restartableLatestCache(REQUEST_GOODS
                , () -> mGoodsModel.getGoodsById(mGoodsId)
                        .compose(new SchedulerTransformer<>())
                , (detailActivity, aGoods) -> {
                    L.d(TAG, "response " + aGoods);
                    if (aGoods.getStatus().getCode() == 0) {
                        detailActivity.onGoods(aGoods.getData());
                    }
                }
                , (detailActivity1, throwable) -> {
                    L.d(TAG, "err " + throwable.getMessage());
                });
    }

    public void getGoods(String goodsId) {
        mGoodsId = goodsId;
        start(REQUEST_GOODS);
    }

}

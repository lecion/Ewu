package com.yliec.ewu.module.main;

import android.os.Bundle;

import com.yliec.ewu.app.common.SchedulerTransformer;
import com.yliec.ewu.model.GoodsModel;
import com.yliec.ewu.net.Api;
import com.yliec.lsword.compat.util.L;

import java.util.HashMap;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Lecion on 4/11/16.
 */
public class MainPresenter extends RxPresenter<MainFragment> {
    private String TAG = getClass().getSimpleName();

    private static final int REQUEST_POP_ID = 1;
    private static final int REQUEST_TIME_ID = 2;
    private static final int REQUEST_PRICE_ID = 3;

    private static final HashMap<Integer, Integer> requests = new HashMap<>();

    static {
        requests.put(Api.SORT_TYPE.POP, REQUEST_POP_ID);
        requests.put(Api.SORT_TYPE.TIME, REQUEST_TIME_ID);
        requests.put(Api.SORT_TYPE.PRICE, REQUEST_PRICE_ID);
    }

    @Inject
    GoodsModel mGoodsModel;

    protected int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        L.d(TAG, "onCreate");
        restartableLatestCache(REQUEST_POP_ID, () -> mGoodsModel.getGoodsListByPop(pageIndex)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(goodsList -> goodsList.getData())
                        .compose(new SchedulerTransformer<>()),
                (mainFragment, goodsList) -> mainFragment.onChangeItems(goodsList, pageIndex),
                (mainFragment, throwable) ->
                        L.d(TAG, "err " + throwable.getMessage())
        );

        restartableLatestCache(REQUEST_TIME_ID, () -> mGoodsModel.getGoodsListByTime(pageIndex)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(goodsList -> goodsList.getData())
                        .compose(new SchedulerTransformer<>()),
                (mainFragment, goodsList) -> mainFragment.onChangeItems(goodsList, pageIndex),
                (mainFragment, throwable) ->
                        L.d(TAG, "err " + throwable.getMessage())
        );

        restartableLatestCache(REQUEST_PRICE_ID, () -> mGoodsModel.getGoodsListByPrice(pageIndex)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(goodsList -> goodsList.getData())
                        .compose(new SchedulerTransformer<>()),
                (mainFragment, goodsList) -> mainFragment.onChangeItems(goodsList, pageIndex),
                (mainFragment, throwable) ->
                        L.d(TAG, "err " + throwable.getMessage())
        );

    }

    public void refresh(int sortType) {
        pageIndex = 1;
        start(requests.get(sortType));
    }

    public void loadMore(int sortType) {
        pageIndex++;
        start(requests.get(sortType));
    }
}

package com.yliec.ewu.module.main;

import android.os.Bundle;

import com.yliec.ewu.app.common.SchedulerTransformer;
import com.yliec.ewu.model.GoodsModel;
import com.yliec.lsword.compat.util.L;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Lecion on 4/11/16.
 */
public class MainPresenter extends RxPresenter<MainFragment> {
    private String TAG = getClass().getSimpleName();

    private static final int REQUEST_ID = 1;

    @Inject
    GoodsModel mGoodsModel;

    protected int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        L.d(TAG, "onCreate");
        restartableLatestCache(REQUEST_ID, () -> mGoodsModel.getGoodsListByPop(pageIndex)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(goodsList -> goodsList.getData())
                        .compose(new SchedulerTransformer<>()),
                (mainFragment, goodsList) -> mainFragment.onChangeItems(goodsList, pageIndex),
                (mainFragment, throwable) ->
                        L.d(TAG, "err " + throwable.getMessage())
        );
    }

    public void refresh() {
        pageIndex = 1;
        start(REQUEST_ID);
    }

    public void loadMore() {
        pageIndex++;
        start(REQUEST_ID);
    }
}

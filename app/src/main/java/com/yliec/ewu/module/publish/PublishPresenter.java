package com.yliec.ewu.module.publish;

import android.os.Bundle;
import android.widget.Toast;

import com.yliec.ewu.api.entity.ResEntity;
import com.yliec.ewu.api.entity.element.PostGoods;
import com.yliec.ewu.app.base.BasePresenter;
import com.yliec.ewu.app.common.SchedulerTransformer;
import com.yliec.ewu.model.GoodsModel;
import com.yliec.lsword.compat.util.L;

import javax.inject.Inject;

import rx.functions.Action2;

/**
 * Created by Lecion on 4/14/16.
 */
public class PublishPresenter extends BasePresenter<PublishActivity> {
    public static final int REQUEST_ADD_GOODS = 1;
    private final String TAG = getClass().getSimpleName();

    @Inject
    GoodsModel mGoodsModel;

    private PostGoods mGoods;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableLatestCache(REQUEST_ADD_GOODS, () -> mGoodsModel.addGoods(mGoods).compose(new SchedulerTransformer<>())
                , new Action2<PublishActivity, ResEntity>() {
                    @Override
                    public void call(PublishActivity publishActivity, ResEntity resEntity) {
                        Toast.makeText(publishActivity, "成功", Toast.LENGTH_LONG).show();
                        L.d(TAG, "GOodsId " + resEntity.getData().getId());
                    }
                }, new Action2<PublishActivity, Throwable>() {
                    @Override
                    public void call(PublishActivity publishActivity, Throwable throwable) {
                        L.d(TAG, "失败 " + throwable.getMessage());
                    }
                });
    }

    public void publish(PostGoods goods) {
        //TODO 发布商品逻辑
        mGoods = goods;
        L.d(TAG, "publish " + goods);
        start(REQUEST_ADD_GOODS);
    }
}

package com.yliec.ewu.module.publish;

import android.os.Bundle;
import android.widget.Toast;

import com.yliec.ewu.api.entity.element.PostGoods;
import com.yliec.ewu.app.base.BasePresenter;
import com.yliec.ewu.app.common.SchedulerTransformer;
import com.yliec.ewu.model.GoodsModel;
import com.yliec.lsword.compat.util.L;

import javax.inject.Inject;

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
                , (publishActivity, resEntity) -> {
                    Toast.makeText(publishActivity, "成功", Toast.LENGTH_LONG).show();
                    L.d(TAG, "GOodsId " + resEntity.getData().getId());
                }, (publishActivity, throwable) -> {
                    L.d(TAG, "失败 " + throwable.getMessage());
                    publishActivity.showErrMsg("商品发布失败，请稍后重试");
                });
    }

    public void publish(PostGoods goods) {
        mGoods = goods;
        L.d(TAG, "publish " + goods);
        start(REQUEST_ADD_GOODS);
    }
}

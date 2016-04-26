package com.yliec.ewu.module.detail;

import android.os.Bundle;

import com.yliec.ewu.api.entity.element.PostReply;
import com.yliec.ewu.api.entity.element.Reply;
import com.yliec.ewu.app.App;
import com.yliec.ewu.app.common.SchedulerTransformer;
import com.yliec.ewu.model.GoodsModel;
import com.yliec.ewu.model.ReplyModel;
import com.yliec.lsword.compat.util.L;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;

/**
 * Created by Lecion on 4/25/16.
 */
public class DetailPresenter extends RxPresenter<DetailActivity> {
    public static final int REQUEST_GOODS = 1;
    public static final int REQUEST_SEND_REPLY = 2;
    private String TAG = getClass().getSimpleName();
    @Inject
    GoodsModel mGoodsModel;

    @Inject
    ReplyModel mReplyModel;

    @Inject
    App mApp;

    String mGoodsId;

    private PostReply mPostReply;

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

        restartableLatestCache(REQUEST_SEND_REPLY
                , () -> mReplyModel.setToken(mApp.getToken())
                        .addReply(mGoodsId, mPostReply)
                        .compose(new SchedulerTransformer<>())
                , (detailActivity, replyEntity) -> {
                    L.d(TAG, "response " + replyEntity);
                    if (replyEntity.getStatus().getCode() == 0) {
                        detailActivity.onReply(replyEntity.getData());
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

    public void reply(String content, Reply toReply) {
        PostReply postReply = new PostReply();
        postReply.setContent(content);
        if (toReply != null) {
            postReply.setReply_id(toReply.getId());
        }
        mPostReply = postReply;
        start(REQUEST_SEND_REPLY);
    }
}

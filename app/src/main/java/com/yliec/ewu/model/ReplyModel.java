package com.yliec.ewu.model;

import com.yliec.ewu.api.ReplyApi;
import com.yliec.ewu.api.entity.ReplyEntity;
import com.yliec.ewu.api.entity.element.PostReply;
import com.yliec.ewu.app.base.BaseModel;

import rx.Observable;

/**
 * Created by Lecion on 4/11/16.
 */
public class ReplyModel extends BaseModel<ReplyApi, ReplyModel> {
    @Override
    protected Class<ReplyApi> getApiClass() {
        return ReplyApi.class;
    }

    public Observable<ReplyEntity> addReply(String goodsId, PostReply postReply) {
        return getService().addReply(goodsId, postReply);
    }
}

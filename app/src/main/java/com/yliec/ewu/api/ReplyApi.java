package com.yliec.ewu.api;

import com.yliec.ewu.api.entity.ReplyEntity;
import com.yliec.ewu.api.entity.element.PostReply;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Lecion on 4/11/16.
 */
public interface ReplyApi {

    @POST("goods/{goodsId}/reply")
    Observable<ReplyEntity> addReply(@Path("goodsId") String goodsId, @Body PostReply postReply);
}

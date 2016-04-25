package com.yliec.ewu.model;

import com.yliec.ewu.api.ReplyApi;
import com.yliec.ewu.app.base.BaseModel;

/**
 * Created by Lecion on 4/11/16.
 */
public class ReplyModel extends BaseModel<ReplyApi, ReplyModel> {
    @Override
    protected Class<ReplyApi> getApiClass() {
        return ReplyApi.class;
    }


}

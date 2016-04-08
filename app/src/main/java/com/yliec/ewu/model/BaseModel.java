package com.yliec.ewu.model;

import com.yliec.ewu.net.Network;

/**
 * Created by Lecion on 12/30/15.
 */
public abstract class BaseModel<Api, M extends BaseModel> {
    protected Api mService;

    public BaseModel() {
        this.mService = Network.retrofit.create(getApiClass());
    }
    public Api getService() {
        return mService;
    }

    protected abstract Class<Api> getApiClass();

}

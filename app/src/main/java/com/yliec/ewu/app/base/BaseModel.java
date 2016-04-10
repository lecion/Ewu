package com.yliec.ewu.app.base;

import com.yliec.ewu.net.Network;

/**
 * Created by Lecion on 12/30/15.
 */
public abstract class BaseModel<API, M extends BaseModel> {
    protected API mService;

    public BaseModel() {
        this.mService = Network.retrofit.create(getApiClass());
    }

    public API getService() {
        return mService;
    }

    protected abstract Class<API> getApiClass();

}

package com.yliec.ewu.model;

import com.yliec.ewu.api.AuthApi;
import com.yliec.ewu.api.entity.TokenEntity;
import com.yliec.ewu.api.entity.element.AuthUser;
import com.yliec.ewu.app.base.BaseModel;

import rx.Observable;

/**
 * Created by Lecion on 4/11/16.
 */
public class AuthModel extends BaseModel<AuthApi, AuthModel> {
    @Override
    protected Class<AuthApi> getApiClass() {
        return AuthApi.class;
    }

    public Observable<TokenEntity> getToken(String username, String password) {
        return getService().getToken(new AuthUser(username, password));
    }
}

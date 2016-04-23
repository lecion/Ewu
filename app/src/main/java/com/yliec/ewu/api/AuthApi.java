package com.yliec.ewu.api;

import com.yliec.ewu.api.entity.TokenEntity;
import com.yliec.ewu.api.entity.element.AuthUser;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Lecion on 12/23/15.
 */
public interface AuthApi {
    @POST("auth")
    Observable<TokenEntity> getToken(@Body AuthUser user);
}
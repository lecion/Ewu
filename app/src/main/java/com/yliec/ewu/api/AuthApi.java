package com.yliec.ewu.api;

import com.yliec.ewu.api.entity.TokenEntity;
import com.yliec.ewu.api.entity.element.RequestUser;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Lecion on 12/23/15.
 */
public interface AuthApi {
    @POST("auth")
    Call<TokenEntity> getToken(@Body RequestUser user);
}
package com.yliec.ewu.api;

import com.yliec.ewu.api.entity.UserEntity;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Lecion on 12/23/15.
 */
public interface UserApi {
    @GET("users/{username}")
    Call<UserEntity.AUser> getUserInfo(@Path("username") String username);

    @GET("users")
    Call<UserEntity.Users> getUsers();
}

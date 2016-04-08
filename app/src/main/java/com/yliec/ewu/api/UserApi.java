package com.yliec.ewu.api;

import com.yliec.ewu.api.entity.UserEntity;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Lecion on 12/23/15.
 */
public interface UserApi {
    @GET("user/{username}")
    Call<UserEntity.AUser> getUserInfo(@Path("username") String username);

    @GET("users")
    Call<UserEntity.Users> getUsers();

    @GET("users/{username}")
    Observable<UserEntity.AUser> getUserInfoRx(@Path("username") String username);

    @GET("users")
    Observable<UserEntity.Users> getUsersRx();
}

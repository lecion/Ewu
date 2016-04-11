package com.yliec.ewu;

import com.yliec.ewu.api.AuthApi;
import com.yliec.ewu.api.GoodsApi;
import com.yliec.ewu.api.UserApi;
import com.yliec.ewu.api.entity.GoodsEntity;
import com.yliec.ewu.api.entity.TokenEntity;
import com.yliec.ewu.api.entity.UserEntity;
import com.yliec.ewu.api.entity.element.AuthUser;
import com.yliec.ewu.api.entity.element.User;
import com.yliec.ewu.net.Network;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;

/**
 * Created by Lecion on 11/30/15.
 */
public class ApiHub {
    public void goodsList() {
        GoodsApi goodsApi = Network.retrofit.create(GoodsApi.class);
        Observable<GoodsEntity.GoodsList> call = goodsApi.getGoodsList();
        call.subscribe(goodsList -> {
            System.out.println(goodsList.getData().toString());
        });
    }

    public void getGoods(String id) {
        GoodsApi goodsApi = Network.retrofit.create(GoodsApi.class);
        Observable<GoodsEntity.AGoods> call = goodsApi.getGoods(id);
        call.subscribe(aGoods -> {
            System.out.println(aGoods.getData().toString());
        });
    }

    public void getUserInfo(String name) {
        UserApi userApi = Network.retrofit.create(UserApi.class);
        Call<UserEntity.AUser> call = userApi.getUserInfo(name);
        call.enqueue(new Callback<UserEntity.AUser>() {
            @Override
            public void onResponse(Response<UserEntity.AUser> response, Retrofit retrofit) {
                System.out.println(response.body().getData().getMeta());
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.toString());
            }
        });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void getUserInfoRx(String name) {
        UserApi userApi = Network.retrofit.create(UserApi.class);
        Observable<UserEntity.AUser> observable = userApi.getUserInfoRx(name);
        observable.subscribe(aUser -> {
            System.out.println("getUserInfo by Rx way: " + aUser);
        });

    }

    public void getUsers() {
        UserApi userApi = Network.retrofit.create(UserApi.class);
        Call<UserEntity.Users> call = userApi.getUsers();
        try {
            Response response = call.execute();
            call.cancel();
            UserEntity.Users users = (UserEntity.Users) response.body();
            for (User user : users.getData()) {
                System.out.println(user.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getToken(AuthUser user) {
        AuthApi authApi = Network.retrofit.create(AuthApi.class);
        Call<TokenEntity> call = authApi.getToken(user);
        try {
            Response response = call.execute();
            TokenEntity tokenEntity = (TokenEntity) response.body();

            System.out.println(tokenEntity.getData().getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

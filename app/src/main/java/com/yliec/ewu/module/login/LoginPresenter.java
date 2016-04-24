package com.yliec.ewu.module.login;

import android.os.Bundle;
import android.widget.Toast;

import com.github.pwittchen.prefser.library.Prefser;
import com.yliec.ewu.api.entity.TokenEntity;
import com.yliec.ewu.app.common.C;
import com.yliec.ewu.app.common.SchedulerTransformer;
import com.yliec.ewu.model.AuthModel;
import com.yliec.lsword.compat.util.L;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;
import rx.functions.Action2;
import rx.schedulers.Schedulers;

/**
 * Created by Lecion on 4/11/16.
 */
public class LoginPresenter extends RxPresenter<LoginActivity> {
    public static final int REQUEST_TOKEN = 1;

    private String mUsername;

    private String mPassword;

    private String TAG = getClass().getSimpleName();

    @Inject
    AuthModel mAuthModel;

    @Inject
    Prefser mPrefser;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableLatestCache(REQUEST_TOKEN,
                () -> mAuthModel.getToken(mUsername, mPassword)
                        .subscribeOn(Schedulers.io())
                        .compose(new SchedulerTransformer<>()),
                new Action2<LoginActivity, TokenEntity>() {
                    @Override
                    public void call(LoginActivity loginActivity, TokenEntity tokenEntity) {
                        L.d(TAG, "token: " + tokenEntity);

                        if (tokenEntity.getStatus().getCode() != 0) {
                            // 错误
                            loginActivity.showErrMsg(tokenEntity.getStatus().getMsg());
                        } else {
                            loginActivity.loginSuccess();
                            mPrefser.put(C.PRE_IS_LOGIN, true);
                            mPrefser.put(C.PRE_TOKEN, tokenEntity.getData().getToken());
                        }
                    }
                }, new Action2<LoginActivity, Throwable>() {
                    @Override
                    public void call(LoginActivity loginActivity, Throwable throwable) {
                        Toast.makeText(loginActivity, "获取token失败 ", Toast.LENGTH_LONG).show();
                        L.d(TAG, "error: " + throwable);

                        loginActivity.showErrMsg("登录失败，请检查网络连接");
                    }
                });
    }

    public void login(String username, String password) {
        mUsername = username;
        mPassword = password;
        start(REQUEST_TOKEN);
    }
}

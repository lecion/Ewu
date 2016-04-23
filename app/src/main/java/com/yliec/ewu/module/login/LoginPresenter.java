package com.yliec.ewu.module.login;

import android.os.Handler;

import nucleus.presenter.RxPresenter;

/**
 * Created by Lecion on 4/11/16.
 */
public class LoginPresenter extends RxPresenter<LoginActivity> {


    private String TAG = getClass().getSimpleName();

    private Handler mHandler = new Handler();

    public void login(String username, String password) {
        getView().showLoginProgress();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().hideLoginProgress();
            }
        }, 3000);
    }
}

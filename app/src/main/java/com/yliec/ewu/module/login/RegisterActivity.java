package com.yliec.ewu.module.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yliec.ewu.R;
import com.yliec.ewu.app.base.BaseActivity;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(RegisterPresenter.class)
public class RegisterActivity extends BaseActivity<RegisterPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void injectPrensenter() {
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }
}

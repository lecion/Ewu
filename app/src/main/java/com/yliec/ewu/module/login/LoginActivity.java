package com.yliec.ewu.module.login;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.yliec.ewu.R;
import com.yliec.ewu.app.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BaseActivity<LoginPresenter> {

    @Bind(R.id.btn_register)
    Button mRegister;

    @Bind(R.id.btn_login)
    Button mLogin;

    @Bind(R.id.et_username)
    EditText mEtUsername;

    @Bind(R.id.et_password)
    EditText mEtPassword;

    @Bind(R.id.pb_login)
    ProgressBar mPbLogin;

    @Inject
    AccountManager mAccountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void injectPrensenter() {
        getAppComponent().inject(this);
        getApiComponent().inject(getPresenter());
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @OnClick(R.id.btn_register)
    public void openRegister() {
        Intent i = RegisterActivity.getCallingIntent(this);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.btn_login)
    public void doLogin() {
        boolean validate = true;
        String username = mEtUsername.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            mEtUsername.setError("用户名不能为空");
            validate = false;
        }
        if (TextUtils.isEmpty(password)) {
            mEtPassword.setError("密码不能为空");
            validate = false;
        }
        if (validate) {
            showLoginProgress();
            getPresenter().login(username, password);
        }
    }

    public void showLoginProgress() {
        mPbLogin.setVisibility(View.VISIBLE);
    }

    public void hideLoginProgress() {
        mPbLogin.setVisibility(View.INVISIBLE);
    }
}

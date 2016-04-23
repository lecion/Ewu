package com.yliec.ewu.module.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yliec.ewu.R;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}

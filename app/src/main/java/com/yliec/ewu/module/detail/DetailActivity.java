package com.yliec.ewu.module.detail;

import android.os.Bundle;

import com.yliec.ewu.R;
import com.yliec.ewu.app.base.BaseActivity;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(DetailPresenter.class)
public class DetailActivity extends BaseActivity<DetailPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected void injectPrensenter() {
        getApp().getApiComponent().inject(getPresenter());
    }

}

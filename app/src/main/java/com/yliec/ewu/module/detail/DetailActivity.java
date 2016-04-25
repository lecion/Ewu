package com.yliec.ewu.module.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yliec.ewu.R;
import com.yliec.ewu.app.base.BaseActivity;

import nucleus.factory.RequiresPresenter;

@RequiresPresenter(DetailPresenter.class)
public class DetailActivity extends BaseActivity<DetailPresenter> {
    private String mGoodsId;
    public static final String GOODS_ID = "goods_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent();
        laodData();
    }

    private void laodData() {
        getPresenter().getGoods(mGoodsId);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        mGoodsId = intent.getStringExtra(GOODS_ID);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    protected void injectPrensenter() {
        getApp().getApiComponent().inject(getPresenter());
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, DetailActivity.class);
    }

}

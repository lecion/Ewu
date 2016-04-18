package com.yliec.ewu.module.publish;

import android.os.Bundle;

import com.yliec.ewu.app.base.BasePresenter;

/**
 * Created by Lecion on 4/14/16.
 */
public class PublishPresenter extends BasePresenter<AlbumActivity> {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

    }


    @Override
    protected void onTakeView(AlbumActivity albumActivity) {
        super.onTakeView(albumActivity);
    }

    public void publish() {
        //TODO 发布商品逻辑
    }
}

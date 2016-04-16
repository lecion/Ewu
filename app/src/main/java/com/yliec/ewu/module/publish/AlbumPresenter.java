package com.yliec.ewu.module.publish;

import android.os.Bundle;

import com.yliec.ewu.app.base.BasePresenter;
import com.yliec.ewu.model.LocalImageModel;
import com.yliec.lsword.compat.util.L;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Lecion on 4/14/16.
 */
public class AlbumPresenter extends BasePresenter<AlbumActivity> {
    public static final int REQUEST_IMAGES_ID = 1;
    private final String TAG = getClass().getSimpleName();
    @Inject
    LocalImageModel mLocalImageModel;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableLatestCache(REQUEST_IMAGES_ID,
                () -> mLocalImageModel.getLocalImages().observeOn(AndroidSchedulers.mainThread()),
                AlbumActivity::onItemChange,
                AlbumActivity::onNetError
        );
    }

    public void loadPhotos() {
        long startTime = System.currentTimeMillis();
        start(REQUEST_IMAGES_ID);
        long endTime = System.currentTimeMillis();
        L.d(TAG, "use time: " + (endTime - startTime) + " ms");
    }

    @Override
    protected void onTakeView(AlbumActivity albumActivity) {
        super.onTakeView(albumActivity);
        albumActivity.injectModle(mLocalImageModel);
    }

}

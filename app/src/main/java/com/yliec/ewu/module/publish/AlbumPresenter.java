package com.yliec.ewu.module.publish;

import android.os.Bundle;

import com.yliec.ewu.api.entity.element.ImageBucket;
import com.yliec.ewu.app.base.BasePresenter;
import com.yliec.ewu.model.LocalImageModel;
import com.yliec.lsword.compat.util.L;

import java.util.HashMap;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Lecion on 4/14/16.
 */
public class AlbumPresenter extends BasePresenter<AlbumActivity> {
    private final String TAG = getClass().getSimpleName();
    @Inject
    LocalImageModel mLocalImageModel;

    private HashMap<String, ImageBucket> bucketList = new HashMap<>();
    private HashMap<String, String> thumbnailList = new HashMap<>();
    private AlbumActivity mAlbumActivity = null;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    public void loadPhotos() {
        long startTime = System.currentTimeMillis();
        mLocalImageModel.getLocalImages()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageList -> {
                    mAlbumActivity.onItemChange(imageList);
                }, throwable -> {
                    mAlbumActivity.onNetError(throwable);
                });
        long endTime = System.currentTimeMillis();
        L.d(TAG, "use time: " + (endTime - startTime) + " ms");
    }

    @Override
    protected void onTakeView(AlbumActivity albumActivity) {
        super.onTakeView(albumActivity);
        mAlbumActivity = albumActivity;
        mAlbumActivity.injectModle(mLocalImageModel);
    }

}

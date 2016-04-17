package com.yliec.ewu.module.publish;

import android.os.Bundle;

import com.yliec.ewu.app.base.BasePresenter;
import com.yliec.ewu.model.LocalImageModel;

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
        start(REQUEST_IMAGES_ID);
//        mLocalImageModel.getImageBucketList().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<ImageBucket>>() {
//            @Override
//            public void call(List<ImageBucket> imageBuckets) {
//                L.d(TAG, "getImageBucketList: " + imageBuckets.size());
//                for (int i = 0; i < imageBuckets.size(); i++) {
//                    ImageBucket bucket = imageBuckets.get(i);
//                    L.d(TAG, "getImageBucketList: bucket( "+i+" ) " + bucket);
//                }
//            }
//        });
    }

    @Override
    protected void onTakeView(AlbumActivity albumActivity) {
        super.onTakeView(albumActivity);
        albumActivity.injectModle(mLocalImageModel);
    }

}

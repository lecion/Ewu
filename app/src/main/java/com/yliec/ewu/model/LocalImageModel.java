package com.yliec.ewu.model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore.Images.Media;

import com.yliec.ewu.api.entity.element.ImageBucket;
import com.yliec.ewu.api.entity.element.LocalImage;
import com.yliec.lsword.compat.util.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Lecion on 4/16/16.
 */
public class LocalImageModel {
    @Inject
    ContentResolver mResolver;

    private HashMap<String, ImageBucket> bucketList = new HashMap<>();
    private List<LocalImage> mLocalImages = new ArrayList<>();
    private final String TAG = getClass().getSimpleName();

    public Observable<List<LocalImage>> getLocalImages() {
        String columns[] = new String[]{
                Media._ID,
                Media.BUCKET_ID,
                Media.DATA,
                Media.DISPLAY_NAME,
                Media.TITLE,
                Media.SIZE,
                Media.BUCKET_DISPLAY_NAME
        };
        return Observable.create(new Observable.OnSubscribe<Cursor>() {
            @Override
            public void call(Subscriber<? super Cursor> subscriber) {
                L.d(TAG, "not initialized ");
                L.d(TAG, "currentThread: " + Thread.currentThread().getName());
                Cursor cur = mResolver.query(Media.EXTERNAL_CONTENT_URI, columns, null, null,
                        null);
                subscriber.onNext(cur);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).map(cursor -> {
            getImagesFromCursor(cursor);
            L.d(TAG, "mLocalImages: " + mLocalImages);
            return mLocalImages;
        });
    }

    private void getImagesFromCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            int photoIDIndex = cursor.getColumnIndexOrThrow(Media._ID);
            int photoPathIndex = cursor.getColumnIndexOrThrow(Media.DATA);
            int photoNameIndex = cursor.getColumnIndexOrThrow(Media.DISPLAY_NAME);
            int photoTitleIndex = cursor.getColumnIndexOrThrow(Media.TITLE);
            int photoSizeIndex = cursor.getColumnIndexOrThrow(Media.SIZE);
            int bucketDisplayNameIndex = cursor.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME);
            int bucketIdIndex = cursor.getColumnIndexOrThrow(Media.BUCKET_ID);

            do {
                String _id = cursor.getString(photoIDIndex);
                String name = cursor.getString(photoNameIndex);
                String path = cursor.getString(photoPathIndex);
                String title = cursor.getString(photoTitleIndex);
                String size = cursor.getString(photoSizeIndex);
                String bucketName = cursor.getString(bucketDisplayNameIndex);
                String bucketId = cursor.getString(bucketIdIndex);

//                L.d(_id + " bucket: "
//                        + bucketName + ", bucketId: " + bucketId + " name:" + name + " path:" + path
//                        + " title: " + title + " size: " + size);
                ImageBucket bucket = bucketList.get(bucketId);
                if (bucket == null) {
                    bucket = new ImageBucket();
                    bucketList.put(bucketId, bucket);
                    bucket.imageList = new ArrayList<LocalImage>();
                    bucket.bucketName = bucketName;
                }
                bucket.count++;
                LocalImage imageItem = new LocalImage();
                imageItem.imageId = _id;
                imageItem.imagePath = path;
                bucket.imageList.add(imageItem);

            } while (cursor.moveToNext());

            Iterator<Map.Entry<String, ImageBucket>> it = bucketList.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<String, ImageBucket> entry = it.next();
                ImageBucket bucket = entry.getValue();
                for (int i = 0; i < bucket.imageList.size(); i++) {
                    LocalImage image = bucket.imageList.get(i);
                    mLocalImages.add(image);
                }
            }
        }
    }

}

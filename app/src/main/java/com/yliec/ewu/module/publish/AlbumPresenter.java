package com.yliec.ewu.module.publish;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;

import com.yliec.ewu.api.entity.element.ImageBucket;
import com.yliec.ewu.api.entity.element.LocalImage;
import com.yliec.lsword.compat.util.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;

/**
 * Created by Lecion on 4/14/16.
 */
public class AlbumPresenter extends RxPresenter<AlbumActivity> {
    private final String TAG = getClass().getSimpleName();
    @Inject
    ContentResolver mResolver;
    private HashMap<String, ImageBucket> bucketList = new HashMap<>();
    private HashMap<String, String> thumbnailList = new HashMap<>();
    private AlbumActivity mAlbumActivity = null;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    public void loadPhotos() {
        long startTime = System.currentTimeMillis();
//        getThumbnail();
        loadBucketImage();

        Iterator<Map.Entry<String, ImageBucket>> it = bucketList.entrySet().iterator();
        List<LocalImage> tmpList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<String, ImageBucket> entry = it.next();
            ImageBucket bucket = entry.getValue();
//            L.d(TAG, entry.getKey() + ", " + bucket.bucketName + ", "
//                    + bucket.count + " ---------- ");
            for (int i = 0; i < bucket.imageList.size(); i++) {
                LocalImage image = bucket.imageList.get(i);
//                L.d(TAG, "----- " + image.imageId + ", " + image.imagePath
//                        + ", " + image.thumbnailPath);
                tmpList.add(image);
            }

        }

        L.d(TAG, "mAlbumActivity: " + mAlbumActivity);
        if (mAlbumActivity != null) {
            mAlbumActivity.onItemChange(tmpList);

        }
        long endTime = System.currentTimeMillis();
        L.d(TAG, "use time: " + (endTime - startTime) + " ms");
    }

    @Override
    protected void onTakeView(AlbumActivity albumActivity) {
        super.onTakeView(albumActivity);
        mAlbumActivity = albumActivity;
    }

    private void loadBucketImage() {
        String columns[] = new String[]{Media._ID, Media.BUCKET_ID,
                Media.PICASA_ID, Media.DATA, Media.DISPLAY_NAME, Media.TITLE,
                Media.SIZE, Media.BUCKET_DISPLAY_NAME};
        Cursor cur = mResolver.query(Media.EXTERNAL_CONTENT_URI, columns, null, null,
                null);
        if (cur.moveToFirst()) {
            int photoIDIndex = cur.getColumnIndexOrThrow(Media._ID);
            int photoPathIndex = cur.getColumnIndexOrThrow(Media.DATA);
            int photoNameIndex = cur.getColumnIndexOrThrow(Media.DISPLAY_NAME);
            int photoTitleIndex = cur.getColumnIndexOrThrow(Media.TITLE);
            int photoSizeIndex = cur.getColumnIndexOrThrow(Media.SIZE);
            int bucketDisplayNameIndex = cur
                    .getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME);
            int bucketIdIndex = cur.getColumnIndexOrThrow(Media.BUCKET_ID);
            int picasaIdIndex = cur.getColumnIndexOrThrow(Media.PICASA_ID);
            int totalNum = cur.getCount();

            do {
                String _id = cur.getString(photoIDIndex);
                String name = cur.getString(photoNameIndex);
                String path = cur.getString(photoPathIndex);
                String title = cur.getString(photoTitleIndex);
                String size = cur.getString(photoSizeIndex);
                String bucketName = cur.getString(bucketDisplayNameIndex);
                String bucketId = cur.getString(bucketIdIndex);

//                L.d(_id + " bucket: "
//                        + bucketName +", bucketId: " + bucketId + " name:" + name + " path:" + path
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
//                imageItem.thumbnailPath = thumbnailList.get(_id);
                bucket.imageList.add(imageItem);

            } while (cur.moveToNext());
            L.d(TAG, bucketList.size() + "");
        }
    }

    private void getThumbnail() {
        String[] projection = {Thumbnails._ID, Thumbnails.IMAGE_ID, Thumbnails.DATA};
        Cursor cursor = mResolver.query(Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);
        if (cursor.moveToFirst()) {
            int _id;
            int imageId;
            String imagePath;

            int _idIndex = cursor.getColumnIndex(Thumbnails._ID);
            int imageIdIndex = cursor.getColumnIndex(Thumbnails.IMAGE_ID);
            int dataIndex = cursor.getColumnIndex(Thumbnails.DATA);

            do {
                _id = cursor.getInt(_idIndex);
                imageId = cursor.getInt(imageIdIndex);
                imagePath = cursor.getString(dataIndex);
                L.d(_id + " " + imageId + " " + imagePath);
                thumbnailList.put(String.valueOf(imageId), imagePath);
            } while (cursor.moveToNext());
        }
    }
}

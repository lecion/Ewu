package com.yliec.ewu.module.publish;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Images.Thumbnails;

import com.yliec.lsword.compat.util.L;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;

/**
 * Created by Lecion on 4/14/16.
 */
public class AlbumPresenter extends RxPresenter<AlbumActivity> {
    @Inject
    ContentResolver mResolver;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    public void loadPhotos() {
        getThumbnail();
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
            } while (cursor.moveToNext());
        }
    }
}

package com.yliec.ewu.api.entity.element;

import java.util.List;

/**
 * Created by Lecion on 4/16/16.
 */
public class ImageBucket {
    public int count = 0;
    public String bucketName;
    public List<LocalImage> imageList;

    @Override
    public String toString() {
        return "ImageBucket{" +
                "count=" + count +
                ", bucketName='" + bucketName + '\'' +
                ", imageList=" + imageList +
                '}';
    }
}

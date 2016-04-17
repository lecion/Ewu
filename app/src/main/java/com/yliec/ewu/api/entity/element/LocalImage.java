package com.yliec.ewu.api.entity.element;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lecion on 4/16/16.
 */
public class LocalImage implements Parcelable {
    public String imageId;
    public String thumbnailPath;
    public String imagePath;
    public boolean isSelected = false;

    public LocalImage() {

    }

    protected LocalImage(Parcel in) {
        imageId = in.readString();
        thumbnailPath = in.readString();
        imagePath = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<LocalImage> CREATOR = new Creator<LocalImage>() {
        @Override
        public LocalImage createFromParcel(Parcel in) {
            return new LocalImage(in);
        }

        @Override
        public LocalImage[] newArray(int size) {
            return new LocalImage[size];
        }
    };

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }



    @Override
    public String toString() {
        return "LocalImage{" +
                "imageId='" + imageId + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageId);
        dest.writeString(thumbnailPath);
        dest.writeString(imagePath);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }


}

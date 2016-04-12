package com.yliec.ewu.api.entity.element;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lecion on 11/30/15.
 */
public class Goods implements Parcelable {
    @SerializedName("_id")
    private String objectId;

    private String name;

    @SerializedName("sale_price")
    private double price;

    private String detail;

    private int userId;

    @SerializedName("seller")
    @Expose
    private User seller;

    @SerializedName("category")
    @Expose
    private Category category;

    private int status;

    private String createdAt;

    private String updatedAt;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.objectId);
        dest.writeString(this.name);
        dest.writeDouble(this.price);
        dest.writeString(this.detail);
        dest.writeInt(this.userId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dest.writeTypedObject(this.category, 0);
        }
        dest.writeInt(this.status);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public Goods() {
    }

    protected Goods(Parcel in) {
        this.objectId = in.readString();
        this.name = in.readString();
        this.price = in.readDouble();
        this.detail = in.readString();
        this.userId = in.readInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.category = in.readTypedObject(Category.CREATOR);
        }
        this.status = in.readInt();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        public Goods createFromParcel(Parcel source) {
            return new Goods(source);
        }

        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    @Override
    public String toString() {
        return "Goods{" +
                "objectId='" + objectId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", detail='" + detail + '\'' +
                ", userId=" + userId +
                ", seller=" + seller +
                ", category=" + category +
                ", status=" + status +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}

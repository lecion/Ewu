
package com.yliec.ewu.api.entity.element;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("updateAt")
    @Expose
    private String updateAt;
    @SerializedName("createAt")
    @Expose
    private String createAt;

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "updateAt='" + updateAt + '\'' +
                ", createAt='" + createAt + '\'' +
                '}';
    }
}

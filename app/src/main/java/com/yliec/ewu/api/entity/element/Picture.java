package com.yliec.ewu.api.entity.element;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lecion on 4/13/16.
 */
public class Picture {
    @SerializedName("_id")
    String id;

    String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

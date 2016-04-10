package com.yliec.ewu.api.entity.element;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lecion on 4/10/16.
 */
public class Goods {
    @SerializedName("_id")
    @Expose
    private String id;

    private String name;

}

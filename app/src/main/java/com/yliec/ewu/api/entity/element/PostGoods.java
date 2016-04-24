package com.yliec.ewu.api.entity.element;

import java.util.ArrayList;

/**
 * Created by Lecion on 11/30/15.
 */
public class PostGoods {
    private String name;

    private Float sale_price;


    private String detail;

    private String category;

    private ArrayList<String> pictures;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getSale_price() {
        return sale_price;
    }

    public void setSale_price(Float sale_price) {
        this.sale_price = sale_price;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "PostGoods{" +
                "name='" + name + '\'' +
                ", sale_price=" + sale_price +
                ", detail='" + detail + '\'' +
                ", category='" + category + '\'' +
                ", pictures=" + pictures +
                '}';
    }

    public double getPrice() {
        return sale_price;
    }

    public void setPrice(Float price) {
        this.sale_price = price;
    }
}

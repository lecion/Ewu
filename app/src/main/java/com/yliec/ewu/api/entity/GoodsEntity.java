package com.yliec.ewu.api.entity;

import com.yliec.ewu.api.entity.element.Goods;

import java.util.List;

/**
 * Created by Lecion on 4/11/16.
 */
public class GoodsEntity {
    public class AGoods extends BaseEntity<Goods> {
    }

    public class GoodsList extends BaseEntity<List<Goods>> {
    }
}

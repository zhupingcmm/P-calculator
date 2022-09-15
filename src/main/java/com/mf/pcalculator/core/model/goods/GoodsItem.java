package com.mf.pcalculator.core.model.goods;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mf.pcalculator.core.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Data
@AllArgsConstructor
public class GoodsItem extends GoodsInfo implements Serializable, Cloneable, Comparable<GoodsItem> {

    private Long calculateId;

    private Map<String, Object> extra = Maps.newHashMap();

    public GoodsItem(long calculateId, GoodsInfo goodsInfo) {

        this.calculateId = calculateId;
        this.setGoodId(goodsInfo.getGoodId());
        this.setCategoryIds(goodsInfo.getCategoryIds());
        this.setName(goodsInfo.getName());
        this.setSkuId(goodsInfo.getSkuId());
        this.setSalePrice(goodsInfo.getSalePrice());
    }


    public static <T extends GoodsItem> List<T> generateItems(GoodsInfo goodsInfo, IdGenerator idGenerator, Consumer<T> consumer){
        if (goodsInfo == null) {
            return Lists.newArrayList();
        }

        List<T> goodItems = Lists.newArrayList();
        for (int i = 0; i < goodsInfo.getNum(); i++) {
            T item = (T) new GoodsItem(idGenerator.nextId(), goodsInfo);
            consumer.accept(item);
            goodItems.add(item);
        }
        return goodItems;
    }

    @Override
    protected GoodsInfo clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(GoodsItem o) {
        return this.getCalculateId().compareTo(o.getCalculateId());
    }
}

package com.mf.pcalculator.core.model.goods;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfo implements Serializable, Cloneable {
    private Long goodId;

    private Long skuId;

    private List<Long> categoryIds;

    private int num;

    private Long salePrice;

    private String name;

    private Map<String, Object> goodsExtra = Maps.newHashMap();




    @Override
    protected GoodsInfo clone() throws CloneNotSupportedException {
        return (GoodsInfo) super.clone();
    }
}

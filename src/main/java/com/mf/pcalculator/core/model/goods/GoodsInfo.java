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
    /**
     * product id
     */
    private Long goodId;

    /**
     * sku id
     */

    private Long skuId;

    /**
     * product category ids
     */
    private List<Long> categoryIds;

    /**
     * product number
     */
    private int num;

    /**
     * product sale price
     */
    private Long salePrice;

    /**
     * product name
     */

    private String name;

    /**
     * product extra name
     */

    private Map<String, Object> goodsExtra = Maps.newHashMap();

    @Override
    protected GoodsInfo clone() throws CloneNotSupportedException {
        return (GoodsInfo) super.clone();
    }
}

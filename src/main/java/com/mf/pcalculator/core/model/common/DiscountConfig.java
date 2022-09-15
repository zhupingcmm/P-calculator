package com.mf.pcalculator.core.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountConfig implements Serializable {

    private int calculateGroup;

    /**
     * 商品限制(0 -所有商品可以参与计算 1- 指定某些商品参与 2-指定某些商品不参与
     */
    private int goodsType;

    private int itemType;

    private List<Long> itemIds;

    private Map<String, Object> extra;
}

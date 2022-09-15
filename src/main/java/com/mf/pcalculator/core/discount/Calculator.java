package com.mf.pcalculator.core.discount;

import com.mf.pcalculator.core.model.common.DiscountContext;
import com.mf.pcalculator.core.model.common.DiscountWrapper;
import com.mf.pcalculator.core.model.goods.GoodsItem;

import java.util.Map;

public interface Calculator <T extends GoodsItem>{

    long calcWrap(DiscountContext<T> context, DiscountWrapper discountWrapper, Map<Long, T> records, byte idx, int i);
}

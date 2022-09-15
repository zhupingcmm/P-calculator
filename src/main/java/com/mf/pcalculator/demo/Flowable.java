package com.mf.pcalculator.demo;

import com.mf.pcalculator.core.model.common.DiscountContext;
import com.mf.pcalculator.core.model.goods.GoodsItem;
import com.mf.pcalculator.core.permutation.Permutation;

import java.util.List;

public class Flowable extends Permutation<GoodsItem> {

    @Override
    public void resetContext(DiscountContext<GoodsItem> context) {

    }

    @Override
    public boolean enableOptimize(List<Byte> a) {
        return false;
    }
}

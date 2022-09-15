package com.mf.pcalculator.demo.calc;

import com.mf.pcalculator.core.discount.impl.AbstractCalculator;
import com.mf.pcalculator.core.model.common.CalcStage;
import com.mf.pcalculator.core.model.common.DiscountContext;
import com.mf.pcalculator.core.model.common.DiscountWrapper;
import com.mf.pcalculator.core.model.goods.GoodsItem;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component("manjian")
public class ManjianCalc extends AbstractCalculator<GoodsItem> {
    @Override
    public long calc(DiscountContext<GoodsItem> context, DiscountWrapper discountWrapper, Map<Long, GoodsItem> records, long prevStagePrice, CalcStage curStage) {
        return 500;
    }
}

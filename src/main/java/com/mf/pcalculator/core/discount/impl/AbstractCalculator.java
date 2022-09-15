package com.mf.pcalculator.core.discount.impl;

import com.mf.pcalculator.core.discount.Calculator;
import com.mf.pcalculator.core.model.common.CalcResult;
import com.mf.pcalculator.core.model.common.CalcStage;
import com.mf.pcalculator.core.model.common.DiscountContext;
import com.mf.pcalculator.core.model.common.DiscountWrapper;
import com.mf.pcalculator.core.model.goods.GoodsItem;

import java.util.Map;

public abstract class AbstractCalculator <T extends GoodsItem> implements Calculator<T> {
    @Override
    public long calcWrap(DiscountContext<T> context, DiscountWrapper discountWrapper, Map<Long, T> records, byte idx, int i) {

        CalcStage stage = new CalcStage();

        CalcResult result = context.getCalcResult();
        long price = result.getCurPrice();
        stage.setBeforeCalcPrice(price);
        price = calc(context, discountWrapper, records, price, stage);
        if (price < 0) return price;
        stage.setAfterCalcPrice(price);
        stage.setIndex(idx);
        stage.setType(discountWrapper.getType());
        result.setCurPrice(price);
        if (stage.getBeforeCalcPrice() > stage.getAfterCalcPrice()){
            result.getCurStages()[i] = stage;
        }
        return price;
    }

    public abstract  long calc(DiscountContext<T> context, DiscountWrapper discountWrapper, Map<Long,T> records, long prevStagePrice, CalcStage curStage);
}

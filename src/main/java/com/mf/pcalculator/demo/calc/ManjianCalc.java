package com.mf.pcalculator.demo.calc;

import com.mf.pcalculator.core.constant.Constant;
import com.mf.pcalculator.core.discount.impl.AbstractCalculator;
import com.mf.pcalculator.core.model.common.CalcStage;
import com.mf.pcalculator.core.model.common.DiscountContext;
import com.mf.pcalculator.core.model.common.DiscountWrapper;
import com.mf.pcalculator.core.model.goods.GoodsItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component("manjian")
public class ManjianCalc extends AbstractCalculator<GoodsItem> {
    @Override
    public long calc(DiscountContext<GoodsItem> context, DiscountWrapper discountWrapper, Map<Long, GoodsItem> records, long prevStagePrice, CalcStage curStage) {
        List<GoodsItem> items = context.getDiscountItemGroup().get(discountWrapper.getId());
        long total = items.stream().mapToLong(x -> (long) x.getExtra().get(Constant.UPDATE_ABLE_PRICE)).sum();

        // 满100 减 20
        long discount = (total / 100) * 20;

        //均摊
        // todo 均摊算出来的结果可能不准确
        for (GoodsItem item : items) {
            long price = (long) item.getExtra().get(Constant.UPDATE_ABLE_PRICE);
            item.getExtra().put(Constant.UPDATE_ABLE_PRICE, price -  discount / items.size());
        }
        return prevStagePrice - discount;
    }
}

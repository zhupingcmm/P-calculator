package com.mf.pcalculator.core.model.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mf.pcalculator.core.model.goods.GoodsItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class DiscountContext <T extends GoodsItem> implements Serializable {

    private long originalPrice;

    private CalcResult calcResult;

    private long cost;

    private List<T> goodsItems;


    private List<DiscountWrapper> discountWrappers;


    private Map<String, List<T>> discountItemGroup;


    /**
     * 记录享受过优惠的单品，key是calculateId
     */
    private Map<Long,T> records;

    /**
     * 存储预计算的结果
     */
    private Map<String,Object> preCompute;

    /**
     * 扩展字段
     */
    private Map<String,Object> extra;

    public static <T extends GoodsItem> DiscountContext<T> create(long originalPrice, List<T> goodsItems, List<DiscountWrapper> discountWrappers){

        DiscountContext<T> c = new DiscountContext<>();

        c.setOriginalPrice(originalPrice);
        c.setRecords(Maps.newHashMap());
        c.setDiscountItemGroup(Maps.newHashMap());
        c.setCalcResult(CalcResult.create(discountWrappers.size()));
        c.calcResult.setFinalPrice(originalPrice);
        c.setGoodsItems(goodsItems);
        c.setDiscountWrappers(discountWrappers);
        c.setPreCompute(Maps.newHashMap());

        for (DiscountWrapper wrapper : discountWrappers) {
            initDiscount(c, wrapper);
        }

        return c;


    }

    private static <T extends GoodsItem> void initDiscount (DiscountContext<T> ctx, DiscountWrapper discountWrapper){

        DiscountConfig config = discountWrapper.getDiscountConfig();
        List<T> list = Lists.newArrayList(ctx.getGoodsItems());

        if (0 == config.getGoodsType()){
            list = list.stream().sorted().collect(Collectors.toList());
            ctx.discountItemGroup.put(discountWrapper.getId(), list);
        }

    }


}

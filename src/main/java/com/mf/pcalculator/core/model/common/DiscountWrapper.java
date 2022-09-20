package com.mf.pcalculator.core.model.common;


import lombok.Data;
import java.io.Serializable;

@Data
public class DiscountWrapper extends Item implements Serializable {

    /**
     * the coupon of name
     */
    private String name;

    /**
     * if must use coupon
     */
    private boolean mustUse;

    /**
     * the config of coupon
     */

    private DiscountConfig discountConfig;

    public static DiscountWrapper of (String type, String id, String name, boolean mustUse, DiscountConfig discountConfig){
        DiscountWrapper discountWrapper = new DiscountWrapper();
        discountWrapper.setId(id);
        discountWrapper.setType(type);
        discountWrapper.setName(name);
        discountWrapper.setMustUse(mustUse);
        discountWrapper.setDiscountConfig(discountConfig);
        return discountWrapper;
    }
}

package com.mf.pcalculator.core.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CalcResult {

    /**
     * 最优实付金额
     */

    private long finalPrice;

    /**
     * 最优优惠券计算过程
     */
    private CalcStage[] stages;

    /**
     * 当前序列计算的实付金额
     */

    private transient long curFinalPrice;

    /**
     * 当前序列优惠计算过程
     */
    private transient CalcStage [] curStages;

    /**
     *  实时计算价格
     */
    @JsonIgnore
    private transient long curPrice;

    public static CalcResult create(int n) {
        CalcResult c = new CalcResult();
        c.setStages(new CalcStage[n]);
        c.setCurStages(new CalcStage[n]);
        return c;
    }
}

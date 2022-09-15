package com.mf.pcalculator.core.permutation;

import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mf.pcalculator.core.aware.CalculatorRouter;
import com.mf.pcalculator.core.discount.Calculator;
import com.mf.pcalculator.core.model.common.CalcResult;
import com.mf.pcalculator.core.model.common.DiscountContext;
import com.mf.pcalculator.core.model.common.DiscountWrapper;
import com.mf.pcalculator.core.model.goods.GoodsItem;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Permutation<T extends GoodsItem> {
    public final static int SUPPORTED_SIZE = 7;

    private final Set<Byte> mustUseSet = Sets.newHashSet();

    private final static Map<Integer, Collection<List<Byte>>> PERMUTATIONS = Maps.newHashMap();

    static{
        //前置计算 1-SUPPORTEDSIZE 之间所有排列组合
        for(byte i=1;i<=SUPPORTED_SIZE;i++){
            PERMUTATIONS.put((int)i, Collections2.permutations(IntStream.range(0,i).boxed()
                    .map(x->(byte)x.intValue()).collect(Collectors.toList())));
        }
    }


    private DiscountContext<T> context;

    private CalculatorRouter calculatorRouter;

    public Permutation<T> build(CalculatorRouter calculatorRouter){
        this.calculatorRouter = calculatorRouter;
        return this;
    }

    public void perm(DiscountContext<T> context) {
        this.context = context;
        int size = context.getDiscountWrappers().size();
        if (size == 0) return;

        Collection<List<Byte>> list= PERMUTATIONS.get(size);
        for (List<Byte> a : list) {
            boolean isBetter = executeCalc(a);
            if (isBetter) {
                updateRecord(context.getCalcResult());
            }
        }
    }

    private void updateRecord(CalcResult result) {
        result.setFinalPrice(result.getCurFinalPrice());

        System.arraycopy(result.getCurStages(), 0, result.getStages(), 0, result.getStages().length);
    }
    private boolean executeCalc(List<Byte> a){

        for (int i = 0; i < a.size(); i++) {
            DiscountWrapper wrapper = context.getDiscountWrappers().get(a.get(i));
            Calculator <T> calculator = calculatorRouter.getService(wrapper.getType());
            if (!calcInner(calculator,wrapper,a,i)) {
                return false;
            }

        }

        long curPrice = context.getCalcResult().getCurPrice();

        context.getCalcResult().setFinalPrice(curPrice);
        return curPrice < context.getCalcResult().getCurFinalPrice();
    }

    private boolean calcInner(Calculator<T> calculator, DiscountWrapper wrapper, List<Byte> a, int i) {
        long price = calculator.calcWrap(context, wrapper, context.getRecords(), a.get(i), i);

        if (price < 0) return false;
        if(i<a.size()-1) {
            int order = wrapper.getDiscountConfig().getCalculateGroup();
            int nextOrder = context.getDiscountWrappers().get(a.get(i+1)).getDiscountConfig().getCalculateGroup();
            if(order>nextOrder){
                //优先级不符合则跳出
                return false;
            }
        }
        return true;
    }

    private void loadMustUseDiscount(){
        for (int i = 0; i < context.getDiscountWrappers().size(); i++) {
            if (context.getDiscountWrappers().get(i).isMustUse()){
                this.mustUseSet.add((byte) i);
            }
        }
    }

}

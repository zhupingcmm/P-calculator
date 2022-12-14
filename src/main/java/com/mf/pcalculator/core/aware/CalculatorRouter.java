package com.mf.pcalculator.core.aware;

import com.mf.pcalculator.core.discount.Calculator;
import com.mf.pcalculator.core.model.goods.GoodsItem;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CalculatorRouter<T extends GoodsItem> implements ApplicationContextAware {
    @Getter
    private Map<String, Calculator> map;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        map = applicationContext.getBeansOfType(Calculator.class);
    }

    public Calculator<T> getService(String key) {
        return map.get(key);
    }

}

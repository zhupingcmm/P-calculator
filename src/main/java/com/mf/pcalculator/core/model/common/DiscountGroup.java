package com.mf.pcalculator.core.model.common;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountGroup {
    private String relation;

    private List<Item> items;

    public List<Item> filterItems(Map<String, Map<String, DiscountWrapper>> inMap) {

        if (CollectionUtils.isEmpty(items) || MapUtils.isEmpty(inMap)) {
            return null;
        }

        List<Item> itemsCopy = Lists.newArrayList(items);
        Iterator<Item> it = itemsCopy.iterator();

        while (it.hasNext()) {
            Item item = it.next();

            if (!inMap.containsKey(item.getType())) {
                it.remove();
                continue;
            }

            if (StringUtils.isNotBlank(item.getId())) {
                Map<String, DiscountWrapper> m = inMap.get(item.getType());
                if(MapUtils.isNotEmpty(m)) {
                    if (!m.containsKey(item.getId())) {
                        it.remove();
                    }
                }
            }


        }
        return itemsCopy;
    }
}

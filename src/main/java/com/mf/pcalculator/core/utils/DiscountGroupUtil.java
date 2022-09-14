package com.mf.pcalculator.core.utils;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.AtomicLongMap;
import com.mf.pcalculator.core.model.common.DiscountGroup;
import com.mf.pcalculator.core.model.common.DiscountWrapper;
import com.mf.pcalculator.core.model.common.Item;
import com.mf.pcalculator.core.permutation.Permutation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class DiscountGroupUtil {

    public static List<Pair<Set<DiscountWrapper>, Set<DiscountWrapper>>> transform(List<List<DiscountGroup>> groups, Map<String, Map<String,DiscountWrapper>> inMap) {
        List<Pair<Set<DiscountWrapper>, Set<DiscountWrapper>>> resultList = Lists.newArrayList();
        List<List<Item>> list = mergeGroups(groups, inMap);
        if (CollectionUtils.isEmpty(list)) return Lists.newArrayList();

        for (List<Item> items : list) {
            Set<DiscountWrapper> discountWrappers =items.stream().map(x -> {
                // todo 这段代码有点重复，觉得可以删除
                if (inMap.containsKey(x.getType())) {
                    Map<String, DiscountWrapper> m = inMap.get((x.getType()));
                    if (m.containsKey(x.getId())) {
                        return m.get(x.getId());
                    }
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toSet());

            if (CollectionUtils.isNotEmpty(discountWrappers) && discountWrappers.size() >= 2) {
                if (discountWrappers.size() > Permutation.SUPPORTED_SIZE) {
                    // todo
                } else {
                    resultList.add(Pair.of(discountWrappers, Sets.newHashSet()));
                }

            }

        }



        return resultList.stream().sorted(Comparator.comparing(x -> x.getLeft().size(), Collections.reverseOrder())).collect(Collectors.toList());

    }

    private static List<List<Item>> mergeGroups(List<List<DiscountGroup>> groups, Map<String, Map<String,DiscountWrapper>> inMap){
        if (CollectionUtils.isEmpty(groups) || MapUtils.isEmpty(inMap)) {
            return null;
        }

        // 最终接收结果
        List<List<Item>> resultList = Lists.newArrayList();

        AtomicLongMap<String> ctx = AtomicLongMap.create();

        //索引，存放可能需要删除的key
        Map<String, Integer> idxMap = Maps.newHashMap();

        for (List<DiscountGroup> group : groups) {
            mergeGroup(group,inMap,ctx,idxMap,resultList);
        }

        Map<String, Long> map = ctx.asMap();

        List<Integer> orderedList = Lists.newArrayList();


        for (Map.Entry<String, Long> e : map.entrySet()) {
            Integer idx = idxMap.get(e.getKey());
            if (idx == null && e.getValue() > 1) {
                orderedList.add(idxMap.get(e.getKey()));
            }
        }
        orderedList.sort(Collections.reverseOrder());
        //从后往前删除，否则索引会出问题
        for(Integer i:orderedList){
            resultList.remove(i.intValue());
        }
        return resultList.stream().filter(CollectionUtils::isNotEmpty).collect(Collectors.toList());
    }

    private static void mergeGroup(List<DiscountGroup> groups, Map<String, Map<String, DiscountWrapper>> inMap, AtomicLongMap<String> ctx, Map<String, Integer> idxMap, List<List<Item>> resultList) {
        if (CollectionUtils.isEmpty(groups)) return;

        List<Item> xList = groups.get(0).filterItems(inMap);

        if(CollectionUtils.isEmpty(xList)) return;

        if (groups.size() == 1) {
            String key = uniqueKey(xList);
            ctx.incrementAndGet(key);
            idxMap.put(key, resultList.size());
            resultList.add(xList);
        }
    }

    private static String uniqueKey(List<Item> list) {
        return list.stream().map(i->i.getType() + i.getId()).sorted().collect(Collectors.joining());
    }


}

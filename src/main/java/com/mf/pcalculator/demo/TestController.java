package com.mf.pcalculator.demo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mf.pcalculator.core.constant.Constant;
import com.mf.pcalculator.core.enums.GroupRelation;
import com.mf.pcalculator.core.model.common.DiscountConfig;
import com.mf.pcalculator.core.model.common.DiscountGroup;
import com.mf.pcalculator.core.model.common.DiscountWrapper;
import com.mf.pcalculator.core.model.common.Item;
import com.mf.pcalculator.core.model.goods.GoodsInfo;
import com.mf.pcalculator.core.model.goods.GoodsItem;
import com.mf.pcalculator.core.utils.DiscountGroupUtil;
import com.mf.pcalculator.core.utils.IdGenerator;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class TestController {

    @RequestMapping("test")
    @ResponseBody
    public Object test(){
        // mock 商品
        List<GoodsItem> items = mockItems();
        // mock 组关系并转化共享组
        List<Pair<Set<DiscountWrapper>, Set<DiscountWrapper>>> pairs = transform(mockGroups());

        return "zp";
    }


    private List<GoodsItem> mockItems (){
        IdGenerator idGenerator = IdGenerator.getInstance();
        GoodsInfo goodsInfo = new GoodsInfo(1001L,2001L,null,4,20 * 100L,"产品1",null);
        GoodsInfo goodsInfo2 = new GoodsInfo(1001L,2002L,null,2,10 * 100L,"产品1",null);

        List<GoodsItem> items = GoodsItem.generateItems(goodsInfo, idGenerator, x->x.getExtra().put(Constant.UPDATE_ABLE_PRICE, x.getSalePrice()));
        items.addAll(GoodsItem.generateItems(goodsInfo2, idGenerator, x->x.getExtra().put(Constant.UPDATE_ABLE_PRICE, x.getSalePrice())));
        return items;
    }

    private List<List<DiscountGroup>> mockGroups () {
        List<List<DiscountGroup>> groups = Lists.newArrayList();
        DiscountGroup discountGroup = DiscountGroup.builder()
                .relation(GroupRelation.SHARE.getType())
                .items(Lists.newArrayList(new Item("zhekou","1"),new Item("manjian","2"),new Item("manzeng","3")))
                .build();
        groups.add(Lists.newArrayList(discountGroup));
        return groups;
    }

    private List<Pair<Set<DiscountWrapper>, Set<DiscountWrapper>>> transform(List<List<DiscountGroup>> groups) {
        List<DiscountWrapper> wrapperList = Lists.newArrayList(
                DiscountWrapper.of("zhekou", "1", "折扣", false, new DiscountConfig()),
                DiscountWrapper.of("manjian", "2", "满减", false, new DiscountConfig())
        );


       Map<String, Map<String, DiscountWrapper>> inMap = wrapperList.stream().collect(Collectors.toMap(DiscountWrapper::getType, x -> ImmutableMap.of(x.getId(), x)));

       return DiscountGroupUtil.transform(groups, inMap);
    }



}

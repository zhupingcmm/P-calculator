package com.mf.pcalculator.demo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mf.pcalculator.core.aware.CalculatorRouter;
import com.mf.pcalculator.core.constant.Constant;
import com.mf.pcalculator.core.enums.GroupRelation;
import com.mf.pcalculator.core.model.common.*;
import com.mf.pcalculator.core.model.goods.GoodsInfo;
import com.mf.pcalculator.core.model.goods.GoodsItem;
import com.mf.pcalculator.core.utils.DiscountGroupUtil;
import com.mf.pcalculator.core.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class TestController {

    private final CalculatorRouter calculatorRouter;

    @RequestMapping("test")
    @ResponseBody
    public Object test(){
        // mock products
        List<GoodsItem> items = mockItems();
        // mock 组关系并转化共享组
        List<Pair<Set<DiscountWrapper>, Set<DiscountWrapper>>> pairs = transform(mockGroups());

        // 全局最优计算过程
        List<CalcStage> globalStages = Lists.newArrayList();
        int count = 0;
        long totalPrice = items.stream().mapToLong(GoodsInfo::getSalePrice).sum();

        long globalPrice = totalPrice;

        Flowable flowable = (Flowable) new Flowable().build(calculatorRouter);


        for (Pair<Set<DiscountWrapper>, Set<DiscountWrapper>> pair : pairs) {
            List<DiscountWrapper> wrappers = Lists.newArrayList(pair.getLeft());

            DiscountContext<GoodsItem> ctx = DiscountContext.create(totalPrice, Lists.newArrayList(items), wrappers);
            flowable.perm(ctx);
            if (ctx.getCalcResult().getFinalPrice() < globalPrice) {
                globalPrice = ctx.getCalcResult().getFinalPrice();
                globalStages = Arrays.asList(ctx.getCalcResult().getStages());
            }
        }


        return Pair.of(globalPrice, globalStages);
    }


    private List<GoodsItem> mockItems (){
        IdGenerator idGenerator = IdGenerator.getInstance();
        GoodsInfo goodsInfo = new GoodsInfo(1001L,2001L,null,4,20 * 100L,"product one",null);
        GoodsInfo goodsInfo2 = new GoodsInfo(1001L,2002L,null,2,10 * 100L,"product two",null);

        List<GoodsItem> items = GoodsItem.generateItems(goodsInfo, idGenerator, x->x.getGoodsExtra().put(Constant.UPDATE_ABLE_PRICE, x.getSalePrice()));
        items.addAll(GoodsItem.generateItems(goodsInfo2, idGenerator, x->x.getGoodsExtra().put(Constant.UPDATE_ABLE_PRICE, x.getSalePrice())));
        return items;
    }

    private List<List<DiscountGroup>> mockGroups () {
        List<List<DiscountGroup>> groups = Lists.newArrayList();
        DiscountGroup discountGroup = DiscountGroup.builder()
                .relation(GroupRelation.SHARE.getType())
                .items(Lists.newArrayList(new Item("discount","1"),new Item("fullReduction","2"),new Item("fullIncrease","3")))
                .build();
        groups.add(Lists.newArrayList(discountGroup));
        return groups;
    }

    private List<Pair<Set<DiscountWrapper>, Set<DiscountWrapper>>> transform(List<List<DiscountGroup>> groups) {
        List<DiscountWrapper> wrapperList = Lists.newArrayList(
                DiscountWrapper.of("discount", "1", "full reduction", false, new DiscountConfig()),
                DiscountWrapper.of("fullReduction", "2", "full reduction", false, new DiscountConfig())
        );


       Map<String, Map<String, DiscountWrapper>> inMap = wrapperList.stream().collect(Collectors.toMap(DiscountWrapper::getType, x -> ImmutableMap.of(x.getId(), x)));

       return DiscountGroupUtil.transform(groups, inMap);
    }



}

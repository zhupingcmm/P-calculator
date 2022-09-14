package com.mf.pcalculator.core.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountConfig implements Serializable {

    private int calculateGroup;

    private int goodsType;

    private int itemType;

    private List<Long> itemIds;

    private Map<String, Object> extra;
}

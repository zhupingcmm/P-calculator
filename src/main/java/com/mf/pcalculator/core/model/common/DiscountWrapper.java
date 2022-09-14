package com.mf.pcalculator.core.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountWrapper implements Serializable {

    private String type;

    private String id;

    private String name;

    private boolean mustUse;

    private DiscountConfig discountConfig;
}

package com.mf.pcalculator.core.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {

    /**
     * the coupon of type
     */
    private String type;
    /**
     * the coupon of id
     */
    private String id;
}

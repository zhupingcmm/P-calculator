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
     * 优惠券类型
     */
    private String type;
    /**
     * 优惠券id
     */
    private String id;
}

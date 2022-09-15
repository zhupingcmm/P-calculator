package com.mf.pcalculator.core.model.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class CalcStage implements Serializable {

    private Long beforeCalcPrice;

    private Long afterCalcPrice;

    private String type;

    private byte index;

    private Map<String, Object> extra;

}

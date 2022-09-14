package com.mf.pcalculator.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum GroupRelation {
    SHARE("share"),
    EXCLUDE("exclude");

    @Getter
    @Setter
    private String type;

}

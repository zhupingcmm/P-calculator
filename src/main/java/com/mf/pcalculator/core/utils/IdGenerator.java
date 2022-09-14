package com.mf.pcalculator.core.utils;

public class IdGenerator {
    public static IdGenerator getInstance() {
        return new IdGenerator();
    }
    private long currentId = 1;

    public long nextId() {
        return currentId++;
    }
}

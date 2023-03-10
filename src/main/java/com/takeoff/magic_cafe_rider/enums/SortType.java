package com.takeoff.magic_cafe_rider.enums;

import java.util.HashMap;
import java.util.Map;

public enum SortType {
    COMPLETE_COUNT(0, "인기 정렬"),
    NEW(1, "최신 정렬");

    public final int id;
    public final String label;

    private static final Map<Integer, SortType> BY_ID = new HashMap<>();

    static {
        for (SortType e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    SortType(int id, String label) {
        this.id = id;
        this.label = label;
    }
}

package com.takeoff.magic_cafe_rider.enums;

import java.util.HashMap;
import java.util.Map;

public enum SearchType {
    NONE(0, "모두"),
    NAME(1, "이름검색"),
    AREA(2, "지역검색"),
    JOB(3, "타입검색");

    public final int id;
    public final String label;

    private static final Map<Integer, SearchType> BY_ID = new HashMap<>();

    static {
        for (SearchType e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    SearchType(int id, String label) {
        this.id = id;
        this.label = label;
    }
}

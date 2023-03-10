package com.takeoff.magic_cafe_rider.enums;

import java.util.HashMap;
import java.util.Map;

public enum FootType {
    NORMAL(0, "일반"),
    EVENT(1, "이벤트"),
    COMPLETED(2, "퀘스트 완료");

    public final int id;
    public final String label;

    private static final Map<Integer, FootType> BY_ID = new HashMap<>();

    static {
        for (FootType e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    FootType(int id, String label) {
        this.id = id;
        this.label = label;
    }
}

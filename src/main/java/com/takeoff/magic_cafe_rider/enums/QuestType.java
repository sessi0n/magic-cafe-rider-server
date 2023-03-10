package com.takeoff.magic_cafe_rider.enums;

import java.util.HashMap;
import java.util.Map;

public enum QuestType {
    CAFE(0, "카페"),
    FOOD(1, "음식점"),
    CAMPING(2, "캠핑"),
    POINT(3, "포인트");

    public final int id;
    public final String label;

    private static final Map<Integer, QuestType> BY_ID = new HashMap<>();

    static {
        for (QuestType e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    QuestType(int id, String label) {
        this.id = id;
        this.label = label;
    }
}

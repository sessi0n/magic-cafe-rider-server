package com.takeoff.magic_cafe_rider.enums;

import java.util.HashMap;
import java.util.Map;

public enum NpcType {
    STORE(0, "잡화점"),
    BRAND(1, "브랜드샵"),
    ENGINE(2, "센터"),
    WEBSTORE(3, "웹스토어"),
    FOOD(4, "맛집"),
    CAMPING(5, "캠핑"),
    ACADEMY(6, "아카데미"),
    ASSIST(7, "어시스트");

    public final int id;
    public final String label;

    private static final Map<Integer, NpcType> BY_ID = new HashMap<>();

    static {
        for (NpcType e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    NpcType(int id, String label) {
        this.id = id;
        this.label = label;
    }
}

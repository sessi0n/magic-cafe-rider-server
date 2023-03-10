package com.takeoff.magic_cafe_rider.enums;

import java.util.HashMap;
import java.util.Map;

public enum NpcTabType {
    WITH_BIKE(0, "바이크관련"),
    NON_BIKE(1, "ETC");

    public final int id;
    public final String label;

    private static final Map<Integer, NpcTabType> BY_ID = new HashMap<>();

    static {
        for (NpcTabType e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    NpcTabType(int id, String label) {
        this.id = id;
        this.label = label;
    }
}

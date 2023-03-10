package com.takeoff.magic_cafe_rider.enums;

import java.util.HashMap;
import java.util.Map;

public enum MarkerType {
    QUEST(0, "QUEST 마커"),
    NPC(1, "NPC 마커");

    public final int id;
    public final String label;

    private static final Map<Integer, MarkerType> BY_ID = new HashMap<>();

    static {
        for (MarkerType e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    MarkerType(int id, String label) {
        this.id = id;
        this.label = label;
    }
}

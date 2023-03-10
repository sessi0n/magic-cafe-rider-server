package com.takeoff.magic_cafe_rider.enums;

import java.util.HashMap;
import java.util.Map;

public enum FriendState {
    REQUEST(0, "요청중"),
    RESPONSE(1, "요청받음"),
    PAIRING(2, "상호 등록"),
    UNPAIR(3, "비활성화");

    public final int id;
    public final String label;

    private static final Map<Integer, FriendState> BY_ID = new HashMap<>();

    static {
        for (FriendState e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    FriendState(int id, String label) {
        this.id = id;
        this.label = label;
    }
}

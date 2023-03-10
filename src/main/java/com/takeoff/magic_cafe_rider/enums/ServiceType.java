package com.takeoff.magic_cafe_rider.enums;

import java.util.HashMap;
import java.util.Map;

public enum ServiceType {
    NONE(0, "unknown"),
    GOOGLE(1, "구글 로그인"),
    APPLE(2, "애플 로그인"),
    KAKAO(3, "카카오 로그인");

    public final int id;
    public final String label;

    private static final Map<Integer, ServiceType> BY_ID = new HashMap<>();

    static {
        for (ServiceType e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    ServiceType(int id, String label) {
        this.id = id;
        this.label = label;
    }
}

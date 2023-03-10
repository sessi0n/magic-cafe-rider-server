package com.takeoff.magic_cafe_rider.enums;

import java.util.HashMap;
import java.util.Map;

public enum QuestDataReqType {
    REGISTER(0, "등록한 퀘스트"),
    ACCEPTED(1, "수락한 퀘스트"),
    COMPLETED(2, "완료한 퀘스트");

    public final int id;
    public final String label;

    private static final Map<Integer, QuestDataReqType> BY_ID = new HashMap<>();

    static {
        for (QuestDataReqType e : values()) {
            BY_ID.put(e.id, e);
        }
    }

    QuestDataReqType(int id, String label) {
        this.id = id;
        this.label = label;
    }
}

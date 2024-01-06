package com.umc.BareuniBE.global.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AlarmType {

    COMMENT(0, "COMMENT"),
    COMMENT_COMMENT(1, "COMMENT_COMMENT"),
    HOT_COMMUNITY(2, "HOT_COMMUNITY");

    private final Integer value;
    private final String alarmType;


    @JsonValue
    public String getAlarmType() {
        return this.alarmType;
    }
}

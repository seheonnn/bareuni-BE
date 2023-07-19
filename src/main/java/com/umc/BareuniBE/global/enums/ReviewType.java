package com.umc.BareuniBE.global.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReviewType {
    BAD(0, "나쁨"),
    NORMAL(1, "보통"),
    GOOD(2, "좋음");

    private final Integer value;
    private final String review;

//    @JsonValue
//    public String getGender() {
//        return this.review;
//    }
}

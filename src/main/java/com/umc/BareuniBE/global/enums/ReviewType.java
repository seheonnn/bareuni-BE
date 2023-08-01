package com.umc.BareuniBE.global.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReviewType {
    BAD(0, "BAD"),
    NORMAL(1, "NORMAL"),
    GOOD(2, "GOOD");

    private final Integer value;
    private final String review;

    @JsonValue
    public String getReview() {
        return this.review;
    }
}

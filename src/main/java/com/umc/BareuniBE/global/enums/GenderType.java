package com.umc.BareuniBE.global.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GenderType {
    MALE(0, "남성"),
    FEMALE(1, "여성");

    private final Integer value;
    private final String gender;


//    @JsonValue
//    public String getGender() {
//        return this.gender;
//    }
}

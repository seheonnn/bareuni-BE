package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.global.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserUpdateReq {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class MyUpdateReq {
        private String nickname;
        private String name;
        private GenderType gender;
        private Long age;
        private boolean ortho;
    }

}

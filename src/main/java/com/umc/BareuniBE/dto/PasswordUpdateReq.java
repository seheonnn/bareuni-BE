package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.global.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PasswordUpdateReq {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class MyPasswordUpdateReq {
        private String currentPassword;
        private String newPassword;
        private String confirmPassword;
    }
}

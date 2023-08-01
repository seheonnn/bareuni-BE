package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserReq {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserJoinReq extends User { // 회원가입 요청
        private User user;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserLoginReq extends User { // 로그인 요청
        private String email;
        private String password;
    }
    // 이메일, 비밀번호,,,,,,,,,,,,



}

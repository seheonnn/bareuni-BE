package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class UserReq {

    @Getter
    @Setter
    public static class UserJoinRequestWrapper { // 회원가입 요청
        private MultipartFile file;
        private UserJoinReq userJoinReq;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserJoinReq {
        private String email;
        private Long age;
        private String password;
        private String nickname;

        private GenderType gender;
        private String provider;
        private boolean ortho;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserLoginReq { // 로그인 요청
        private String email;
        private String password;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserProfileReq {
        private String email;
        private String nickname;
        private GenderType gender;
        private Long age;
        private boolean ortho;
        private String provider;
        //private String profile;
    }
}

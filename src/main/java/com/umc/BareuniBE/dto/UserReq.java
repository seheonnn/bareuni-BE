package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.enums.GenderType;
import com.umc.BareuniBE.global.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserReq {

    @Getter
    @Setter
    public static class UserJoinReq extends User { // 회원가입 요청
       //private User user;
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CheckEmailReq {
        private String email;
    }
}

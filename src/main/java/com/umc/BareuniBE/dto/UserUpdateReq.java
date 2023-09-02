package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.global.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class UserUpdateReq {

    @Getter
    @Setter
    public static class UpdateRequestWrapper { // 회원정보 수정 요청
        private MultipartFile file;
        private UserUpdateReq.MyUpdateReq MyUpdateReq;

    }
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

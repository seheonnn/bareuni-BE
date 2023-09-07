package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.QUser;
import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserRes {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserJoinRes {
        private User user;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class SocialLoginRes {
        private User user;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserSummary {
        private Long userIdx;
        private String nickName;
        private String profile;

        public UserSummary(User user) {
            this.userIdx = user.getUserIdx();
            this.nickName = user.getNickname();
            this.profile = user.getProfile();
        }

        public UserSummary(QUser user) {
            this.userIdx = Long.parseLong(String.valueOf(user.userIdx));
            this.nickName = String.valueOf(user.nickname);
            this.profile = String.valueOf(user.profile);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserInfo {
        private String email;
        private String nickname;
        private GenderType gender;
        private Long age;
        private boolean ortho;
        private String profile;
    }


}

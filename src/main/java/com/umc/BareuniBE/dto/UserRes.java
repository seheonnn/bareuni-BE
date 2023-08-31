package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.User;
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
    }
}

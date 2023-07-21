package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.Community;
import com.umc.BareuniBE.entities.User;
import lombok.*;

import java.time.LocalDateTime;

public class CommunityRes {
//    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommunityDetailRes extends Community{
        private Community community;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommunityListRes {


        private Object communityIdx;

        private Object createdAt;

        private Object updatedAt;

        private User user;

        private Object content;
        private Object like;

    }
}

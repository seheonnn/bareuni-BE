package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.Community;
import lombok.*;

public class CommunityRes {
//    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommunityDetailRes {
        private Community community;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommunityListRes extends Community{
        private int like;

    }
}

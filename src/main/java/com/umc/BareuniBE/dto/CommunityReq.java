package com.umc.BareuniBE.dto;

import lombok.*;

public class CommunityReq {
//    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommunityCreateReq {
        private Long userIdx;/**/
        private String content;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommentCreateReq {
        private Long userIdx;
        private String comment;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommentDeleteReq {
        private Long userIdx;
    }

}

package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.Comment;
import com.umc.BareuniBE.entities.Community;
import com.umc.BareuniBE.entities.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class CommunityRes {
    //    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommunityCreateRes {
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommunityDetailRes {


        private Long communityIdx;

        private User user;
       
        private String content;
      
        private List<CommentSummary> commentList;


    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommentSummary {

        private String nickname;
        private String comment;
        private LocalDateTime commentCreatedAt;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommentCreateRes {
        private Comment comment;
    }
}


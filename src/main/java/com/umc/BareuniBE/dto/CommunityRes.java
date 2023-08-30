package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.Comment;
import com.umc.BareuniBE.entities.Community;
import com.umc.BareuniBE.entities.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class CommunityRes {
//    @Builder
//    @NoArgsConstructor
//    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommunityCreateRes {
        private Long communityIdx;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String content;
        private UserRes.UserSummary user;

    public CommunityCreateRes(Community community) {
        this.communityIdx = community.getCommunityIdx();
        this.createdAt = community.getCreatedAt();
        this.updatedAt = community.getUpdatedAt();
        this.content = community.getContent();
        this.user = new UserRes.UserSummary(community.getUser());
    }
}

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommunityListRes {


        private Object communityIdx;

        private Object createdAt;

        private Object updatedAt;

        private UserRes.UserSummary user;

        private Object content;
        private Object like;


        public void setUser(User user) {
            this.user = new UserRes.UserSummary(user);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommunityDetailRes {


        private Long communityIdx;

        private UserRes.UserSummary user;
       
        private String content;
      
        private List<CommentSummary> commentList;

        public CommunityDetailRes(Community community, List<CommentSummary> commentSummaryList) {
            this.communityIdx = community.getCommunityIdx();
            this.user = new UserRes.UserSummary(community.getUser());
            this.content = community.getContent();
            this.commentList = commentSummaryList;
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommentSummary {
        private Long communityIdx;
        private UserRes.UserSummary user;
        private String comment;
        private LocalDateTime commentCreatedAt;

        public CommentSummary(Comment comment) {
            this.communityIdx = comment.getCommunity().getCommunityIdx();
            this.user = new UserRes.UserSummary(comment.getUser());
            this.comment = comment.getComment();
            this.commentCreatedAt = comment.getCreatedAt();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class BestCommunityListRes {
        private Object communityIdx;
        private Object createdAt;
        private Object updatedAt;
        private Object content;
        private Object likeCnt;
    }
}


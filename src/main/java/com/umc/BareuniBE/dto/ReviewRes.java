package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.Review;
import com.umc.BareuniBE.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewRes {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ReviewCreateRes {
        private Review review;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class ReviewListRes {
        private Long reviewIdx;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private UserRes.UserSummary user;
        private String content;
        private int totalScore;
        private boolean receipt;
        private List<String> images;

        public ReviewListRes(Review review, User user) {
            this.reviewIdx = review.getReviewIdx();
            this.createdAt = review.getCreatedAt();
            this.updatedAt = review.getUpdatedAt();
            this.user = new UserRes.UserSummary(user);
            this.content = review.getContent();
            this.totalScore = review.getTotalScore();
            this.receipt = review.isReceipt();
            this.images = List.of(review.getImages().split(","));
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class ReviewStatisticsRes {
        private Long reviewIdx;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private User user;
        private String content;
        private int totalScore;
        private boolean receipt;
        private List<String> images;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class ReviewDetailRes {
        private Long reviewIdx;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private User user;
        private String content;
        private int totalScore;
        private boolean receipt;
        private Long payment;
        private List<String> images;
    }

}

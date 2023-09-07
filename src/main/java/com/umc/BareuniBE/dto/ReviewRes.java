package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.Review;
import com.umc.BareuniBE.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
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
            this.images = (review.getImages() != null) ? List.of(review.getImages().split(",")) : Collections.emptyList();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class ReviewSearchListRes {
        private Long reviewIdx;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private UserRes.UserSummary user;
        private String content;
        private int totalScore;
        private boolean receipt;
        private List<String> images;

        public ReviewSearchListRes(Review review, User user) {
            this.reviewIdx = review.getReviewIdx();
            this.createdAt = review.getCreatedAt();
            this.updatedAt = review.getUpdatedAt();
            this.user = new UserRes.UserSummary(user);
            this.content = review.getContent();
            this.totalScore = review.getTotalScore();
            this.receipt = review.isReceipt();
            this.images = (review.getImages() != null) ? List.of(review.getImages().split(",")) : Collections.emptyList();
        }
    }

    @Getter @Setter
    public static class ReviewObjectListRes {
        private Object reviewIdx;
        private Object createdAt;
        private Object updatedAt;
        private UserRes.UserSummary user;
        private Object content;
        private Object totalScore;
        private Object receipt;
        private List<String> images;

        public ReviewObjectListRes(Review review, User user) {
            this.reviewIdx = review.getReviewIdx();
            this.createdAt = review.getCreatedAt();
            this.updatedAt = review.getUpdatedAt();
            this.user = new UserRes.UserSummary(user);
            this.content = review.getContent();
            this.totalScore = review.getTotalScore();
            this.receipt = review.isReceipt();
            this.images = (review.getImages() != null) ? List.of(review.getImages().split(",")) : Collections.emptyList();
        }
    }

    @Getter @Setter
    public static class ReviewStatisticsRes {
        private Long reviewIdx;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private UserRes.UserSummary user;
        private String content;
        private int totalScore;
        private boolean receipt;
        private List<String> images;

        public ReviewStatisticsRes(Review review) {
            this.reviewIdx = review.getReviewIdx();
            this.createdAt = review.getCreatedAt();
            this.updatedAt = review.getUpdatedAt();
            this.user = new UserRes.UserSummary(review.getUser());
            this.content = review.getContent();
            this.totalScore = review.getTotalScore();
            this.receipt = review.isReceipt();
            this.images = (review.getImages() != null) ? List.of(review.getImages().split(",")) : Collections.emptyList();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class ReviewDetailRes {
        private Long reviewIdx;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private UserRes.UserSummary user;
        private String content;
        private int totalScore;
        private boolean receipt;
        private Long payment;
        private List<String> images;

        public ReviewDetailRes(Review review) {
            this.reviewIdx = review.getReviewIdx();
            this.createdAt = review.getCreatedAt();
            this.updatedAt = review.getUpdatedAt();
            this.user = new UserRes.UserSummary(review.getUser());
            this.content = review.getContent();
            this.totalScore = review.getTotalScore();
            this.receipt = review.isReceipt();
            this.payment = review.getPayment();
            this.images = (review.getImages() != null) ? List.of(review.getImages().split(",")) : Collections.emptyList();
        }
    }

}

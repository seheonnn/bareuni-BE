package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.global.enums.ReviewType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ReviewReq {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ReviewCreateReq {
        private Long userIdx;
        private Long hospitalIdx;
        private int totalScore;
        private ReviewType treatmentScore;
        private ReviewType serviceScore;
        private ReviewType equipmentScore;
        private String content;
        private Long payment;
        private boolean receipt;
    }


}

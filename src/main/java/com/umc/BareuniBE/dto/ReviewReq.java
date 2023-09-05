package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.global.enums.ReviewType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.umc.BareuniBE.global.enums.ReviewType.GOOD;

public class ReviewReq {

    @Getter
    @Setter
    public static class ReviewCreateWrapper {
        private List<MultipartFile> files;
        private ReviewCreateReq reviewCreateReq;
    }
//    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ReviewCreateReq {
        private Long hospitalIdx;
        private int totalScore;
        private ReviewType treatmentScore;
        private ReviewType serviceScore;
        private ReviewType equipmentScore;
        private String content;
        private Long payment;
        private boolean receipt;

        public ReviewCreateReq() {
            this.hospitalIdx = 3L;
            this.totalScore = 5;
            this.treatmentScore = GOOD;
            this.serviceScore = GOOD;
            this.equipmentScore = GOOD;
            this.content = "contentcontentcontentcontent";
            this.payment = 10000L;
            this.receipt = false;
        }
    }


}

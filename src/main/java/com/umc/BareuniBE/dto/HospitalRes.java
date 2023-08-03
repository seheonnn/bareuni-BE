package com.umc.BareuniBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class HospitalRes {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class HospitalScrapCreateRes {
        private Long scrapIdx;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class HospitalSummaryListRes {
        private Object hospitalIdx;
        private Object hosName;
        private Object address;
        private Object score;
        private Object reviewCnt;
        private Object summary;
        // private String[] images; // 일단 이미지 없이 진행
    }
}

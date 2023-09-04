package com.umc.BareuniBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
        private Long hospitalIdx;
        private String hosName;
        private String address;
        private Double score;
        private Long reviewCnt;
        private String summary;
        private String images;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class HospitalListRes {
        private Object hospital_idx;
        private Object hospital_name;

        private Object summary;

        private Object address;

        private List<String> images;

        private Object reviewAVG;

        public HospitalListRes(Object[] object) {
            this.hospital_idx = object[0];
            this.hospital_name = object[1];
            this.summary = object[2];
            this.address = object[3];
            this.images = List.of(object[4].toString().split(","));
            this.reviewAVG = object[5];
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class HospitalDetailRes {
        private Long hospitalIdx;
        private String hosName;
        private boolean bookable;
        private String[] closedDay;
        private boolean todayClosed;
        private String address;
        private String keywords;
        private String openTime;
        private String lunchTime;
        private String content;
        private String telephone;
        private List<String> images;

        private Double totalScore;
        private Integer reviewCnt;
        private String treatment;
        private Double treatmentRate;
        private String service;
        private Double serviceRate;
        private String equipment;
        private Double equipmentRate;
    }

    public interface HospitalDetailResult {
        Long getHospital_idx();
        String getHospital_name();
        String getTelephone();
        String getKeywords();
        String getOpen_time();
        String getClosed_day();
        String getLunch_time();
        String getContent();
        String getAddress();
        String getImages();
        boolean getBookable();
        String getTreatment();
        Double getTreatment_ratio();
        String getService();
        Double getService_ratio();
        String getEquipment();
        Double getEquipment_ratio();
        Double getTotal_score();
        Integer getReview_cnt();
    }
}

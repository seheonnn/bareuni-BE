package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.User;
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
        private Long hospitalIdx;
        private String hosName;
        private String address;
        private Double score;
        private Long reviewCnt;
        private String summary;
        private String image;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class HospitalListRes {
        private Object scrapIdx;

        private Object createdAt;

        private Object updatedAt;

        private User user;

        private Object hospital;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class HospitalDetailRes {
        private Long hospitalIdx;
        private String hosName;
        private boolean bookable;
        private String closedDay;
        private boolean todayClosed;
        private String address;
        private String keywords;
        private String openTime;
        private String lunchTime;
        private String content;
        private String telephone;
        private String image;

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
        String getImage();
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

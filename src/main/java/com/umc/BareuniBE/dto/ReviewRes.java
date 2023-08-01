package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ReviewRes {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ReviewListRes {


        private Object reviewIdx;
        private Object createdAt;
        private Object updatedAt;
        private User user;
        private Object content;
        private Object equipmentScore;
        private Object receipt;
        private Object serviceScore;
        private Object payment;
        private Object totalScore;
        private Object treatmentScore;
        private Object hospital;

    }
}

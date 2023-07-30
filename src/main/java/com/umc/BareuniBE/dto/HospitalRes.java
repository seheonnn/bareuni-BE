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
    public static class HospitalListRes {
        private Object scrapIdx;

        private Object createdAt;

        private Object updatedAt;

        private User user;

        private Object hospital;

    }
}

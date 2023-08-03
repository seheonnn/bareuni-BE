package com.umc.BareuniBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class HospitalReq {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class HospitalScrapCreateReq {
        private Long userIdx;
    }
}
package com.umc.BareuniBE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class BookingReq {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class BookingCreateReq {
        private Long userIdx; // 예약을 요청하는 사용자의 ID
        private Long hospitalIdx; // 선택한 치과의 ID (병원 ID)
        private String method; // 예약 방식 (전화 예약 또는 인앱 예약)
        private LocalDateTime bookingDate; // 예약 날짜
    }
}

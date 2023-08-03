package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.Booking;
import com.umc.BareuniBE.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BookingRes {


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class BookingCreateRes{
        private Booking booking;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class BookingListRes {


        private Object bookingIdx;

        private Object createdAt;

        private Object updatedAt;

        private User user;

        private Object hospital;

        private Object method;

        private Object bookingDate;

    }
}

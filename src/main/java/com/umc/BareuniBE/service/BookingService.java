package com.umc.BareuniBE.service;

import com.umc.BareuniBE.dto.BookingReq;
import com.umc.BareuniBE.dto.BookingRes;
import com.umc.BareuniBE.entities.Booking;
import com.umc.BareuniBE.entities.Hospital;
import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.repository.BookingRepository;
import com.umc.BareuniBE.repository.HospitalRepository;
import com.umc.BareuniBE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.umc.BareuniBE.global.BaseResponseStatus.HOSPITAL_EMPTY_ID;
import static com.umc.BareuniBE.global.BaseResponseStatus.USERS_EMPTY_USER_ID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;

    private final BookingRepository bookingRepository;

    public BookingRes.BookingCreateRes createBooking(BookingReq.BookingCreateReq request) throws BaseException {
        User user = userRepository.findById(request.getUserIdx())
                .orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));
        Hospital hospital = hospitalRepository.findById(request.getHospitalIdx())
                .orElseThrow(() -> new BaseException(HOSPITAL_EMPTY_ID));

        Booking newBooking = Booking.builder()
                .user(user)
                .hospital(hospital)
                .method(request.getMethod())
                .bookingDate(request.getBookingDate())
                .build();

        Booking booking = bookingRepository.saveAndFlush(newBooking);
        return new BookingRes.BookingCreateRes(booking);
    }
}

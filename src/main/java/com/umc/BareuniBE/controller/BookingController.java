package com.umc.BareuniBE.controller;


import com.umc.BareuniBE.dto.BookingReq;
import com.umc.BareuniBE.dto.BookingRes;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    //예약 생성
    @PostMapping("")
    public BaseResponse<BookingRes.BookingCreateRes> createBooking(@RequestBody BookingReq.BookingCreateReq request) throws BaseException {
        return new BaseResponse<>(bookingService.createBooking(request));
    }

}

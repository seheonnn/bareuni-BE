//package com.umc.BareuniBE.controller;
//
//
//import com.umc.BareuniBE.dto.BookingReq;
//import com.umc.BareuniBE.dto.BookingRes;
//import com.umc.BareuniBE.global.BaseException;
//import com.umc.BareuniBE.global.BaseResponse;
//import com.umc.BareuniBE.service.BookingService;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/booking")
//public class BookingController {
//    private final BookingService bookingService;
//
//    // 예약 생성
//    @ApiOperation(value = "예약 생성", notes = "ex) http://localhost:8080/booking\n\n" +
//            "{\n\n" +
//            "  \"bookingDate\": \"2023-08-03T10:28:20.116Z\",\n\n" +
//            "  \"hospitalIdx\": 1,\n\n" +
//            "  \"method\": \"병원에서 전화오면 예약할게요\",\n\n" +
//            "  \"userIdx\": 1\n\n" +
//            "}")
//    @PostMapping("")
//    public BaseResponse<BookingRes.BookingCreateRes> createBooking(@RequestBody BookingReq.BookingCreateReq request) throws BaseException {
//        return new BaseResponse<>(bookingService.createBooking(request));
//    }
//
//}

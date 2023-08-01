package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.BookingRes;
import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.dto.HospitalRes;
import com.umc.BareuniBE.dto.ReviewRes;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;

    @Autowired
    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    // 작성한 글 목록 조회 (최신순)
    @GetMapping("/community/{userId}")
    public BaseResponse<List<CommunityRes.CommunityListRes>> getMyCommunityList(
            @PathVariable Long userId,
            @PageableDefault(page = 0, size = 10, sort = "created_at", direction = Sort.Direction.DESC) Pageable page
    ) throws BaseException {
        return new BaseResponse<>(mypageService.getMyCommunityList(userId, page));
    }

    // 치과 저장 목록 조회 (최신순)
    @GetMapping("/scrap/{userId}")
    public BaseResponse<List<HospitalRes.HospitalListRes>> getMyHospitalList(
            @PathVariable Long userId,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable page
    ) throws BaseException {
        return new BaseResponse<>(mypageService.getMyHospitalList(userId, page));
    }

    // 작성한 리뷰 목록 조회 (최신순)
    @GetMapping("/reviews/{userId}")
    public BaseResponse<List<ReviewRes.ReviewListRes>> getMyReviewList(
            @PathVariable Long userId,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable page
    ) throws BaseException {
        return new BaseResponse<>(mypageService.getMyReviewList(userId, page));
    }

    // 예약 내역 조회 (다가오는 예약 날짜 순?)
    @GetMapping("/booking/{userId}")
    public BaseResponse<List<BookingRes.BookingListRes>> getMyBookingList(
            @PathVariable Long userId,
            @PageableDefault(page = 0, size = 10, sort = "bookingDate", direction = Sort.Direction.ASC) Pageable page
    ) throws BaseException {
        return new BaseResponse<>(mypageService.getMyBookingList(userId, page));
    }
}
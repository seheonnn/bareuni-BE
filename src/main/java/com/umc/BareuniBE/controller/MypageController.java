package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.*;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.service.MypageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "작성한 글 목록 조회(최신순)", notes = "ex) http://localhost:8080/mypage/community/1?page=0&size=10&sort=created_at,desc")
    @GetMapping("/community/{userId}")
    public BaseResponse<List<CommunityRes.CommunityListRes>> getMyCommunityList(
            @PathVariable Long userId,
            @PageableDefault(page = 0, size = 10, sort = "created_at", direction = Sort.Direction.DESC) Pageable page
    ) throws BaseException {
        return new BaseResponse<>(mypageService.getMyCommunityList(userId, page));
    }

    // 치과 저장 목록 조회 (최신순)
    @ApiOperation(value = "치과 저장 목록(스크랩) 조회 (최신순)", notes = "ex) http://localhost:8080/mypage/scrap/1?page=0&size=10&sort=createdAt,desc\n\n")
    @GetMapping("/scrap/{userId}")
    public BaseResponse<List<HospitalRes.HospitalListRes>> getMyHospitalList(
            @PathVariable Long userId,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable page
    ) throws BaseException {
        return new BaseResponse<>(mypageService.getMyHospitalList(userId, page));
    }

    // 작성한 리뷰 목록 조회 (최신순)
    @ApiOperation(value = "작성한 리뷰 목록 조회 (최신순)", notes = "ex) http://localhost:8080/mypage/reviews/1?page=0&size=10&sort=createdAt,desc\n\n")
    @GetMapping("/reviews/{userId}")
    public BaseResponse<List<ReviewRes.ReviewListRes>> getMyReviewList(
            @PathVariable Long userId,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable page
    ) throws BaseException {
        return new BaseResponse<>(mypageService.getMyReviewList(userId, page));
    }

    // 예약 내역 조회 (다가오는 예약 날짜 순?)
    @ApiOperation(value = "예약 내역 조회", notes = "ex) http://localhost:8080/mypage/booking/1?page=0&size=10&sort=bookingDate,desc")
    @GetMapping("/booking/{userId}")
    public BaseResponse<List<BookingRes.BookingListRes>> getMyBookingList(
            @PathVariable Long userId,
            @PageableDefault(page = 0, size = 10, sort = "bookingDate", direction = Sort.Direction.ASC) Pageable page
    ) throws BaseException {
        return new BaseResponse<>(mypageService.getMyBookingList(userId, page));
    }

    // 회원 정보 수정 (닉네임, 이름, 성별, 연령대, 교정 여부)
    @ApiOperation(value = "회원 정보 수정 (닉네임, 이름, 성별, 연령대, 교정 여부)", notes = "ex) http://localhost:8080/mypage/users/1\n\n" +
            "{\n\n" +
            "\"age\": 0,\n\n" +
            "\"gender\": \"FEMALE\",\n\n" +
            "\"name\": \"이바른\",\n\n" +
            "\"nickname\": \"바른이바른이22\",\n\n" +
            "\"ortho\": true\n\n" +
            "}")
    @PatchMapping("/users/{userId}")
    public BaseResponse<String> userUpdate(
            @PathVariable Long userId,
            @RequestBody UserUpdateReq.MyUpdateReq myUpdateReq
    ) throws BaseException {
        return new BaseResponse<>(mypageService.userUpdate(userId, myUpdateReq));
    }
}
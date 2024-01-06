package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.ReviewReq;
import com.umc.BareuniBE.dto.ReviewRes;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.global.enums.GenderType;
import com.umc.BareuniBE.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @ApiOperation(value = "리뷰 작성", notes = "ex) http://localhost:8080/reviews\n\n" +
            "{\n\n" +
            "  \"content\": \"리뷰 내용입니다.\",\n\n" +
            "  \"equipmentScore\": \"BAD\",\n\n" +
            "  \"hospitalIdx\": 1,\n\n" +
            "  \"payment\": 50000,\n\n" +
            "  \"receipt\": true,\n\n" +
            "  \"serviceScore\": \"BAD\",\n\n" +
            "  \"totalScore\": 5,\n\n" +
            "  \"treatmentScore\": \"BAD\",\n\n" +
            "  \"userIdx\": 1\n\n" +
            "}")
    @PostMapping("")
    public BaseResponse<ReviewRes.ReviewListRes> createReview(
            @ModelAttribute ReviewReq.ReviewCreateWrapper reviewCreateWrapper,
            HttpServletRequest request
    ) throws BaseException {
        return new BaseResponse<>(reviewService.createReview(reviewCreateWrapper.getFiles(), reviewCreateWrapper.getReviewCreateReq(), request));
    }


    // 리뷰 조회
    @ApiOperation(value = "리뷰 조회", notes = "ex) http://localhost:8080/reviews?page=0&size=2&sort=created_at,desc")
    @GetMapping("/{hospitalIdx}")
    public BaseResponse<List<ReviewRes.ReviewListRes>> getReviewList(@PathVariable Long hospitalIdx, @PageableDefault(page = 0, size = 2) Pageable page) {
        List<ReviewRes.ReviewListRes> reviewListRes = reviewService.getReviewList(hospitalIdx, page);
        return new BaseResponse<>(reviewListRes);
    }

    // 리뷰 통계 조회
    @ApiOperation(value = "리뷰 통계 조회", notes = "ex) http://localhost:8080/reviews/statistics?receipt=true&gender=MALE&page=0&size=2&sort=createdAt,desc")
    @GetMapping("/statistics/{hospitalIdx}")
    public BaseResponse<List<ReviewRes.ReviewStatisticsRes>> getReviewStatisticsList(
            @PathVariable Long hospitalIdx,
            @RequestParam(required = false) boolean receipt,
            @RequestParam(required = false) GenderType gender,
            @PageableDefault(page = 0, size = 2, sort = "createdAt", direction = Sort.Direction.DESC) Pageable page
    ) {
        List<ReviewRes.ReviewStatisticsRes> reviewStatisticsList = reviewService.getReviewStatisticsList(hospitalIdx, receipt, gender, page);

        return new BaseResponse<>(reviewStatisticsList);
    }

    // 리뷰 상세 조회
    @ApiOperation(value = "리뷰 상세 조회", notes = "ex) http://localhost:8080/reviews/1")
    @GetMapping("/detail/{reviewIdx}")
    public BaseResponse<ReviewRes.ReviewDetailRes> getReviewDetail( @PathVariable Long reviewIdx) throws BaseException {
        return new BaseResponse<>(reviewService.getReviewDetail(reviewIdx));
    }

    // 검색 - 치과 리뷰
    @ApiOperation(value = "검색 - 치과 리뷰", notes = "ex) http://localhost:8080/reviews/search?keyword=아쉽")
    @GetMapping("/search")
    public BaseResponse<List<ReviewRes.ReviewSearchListRes>> searchReview (
            @RequestParam(value = "keyword") String keyword
    ) throws BaseException {
        return new BaseResponse<>(reviewService.searchReview(keyword));
    }
}


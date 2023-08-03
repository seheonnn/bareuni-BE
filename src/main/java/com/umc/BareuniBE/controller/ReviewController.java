package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.ReviewReq;
import com.umc.BareuniBE.dto.ReviewRes;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.global.enums.GenderType;
import com.umc.BareuniBE.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 작성
    @PostMapping("")
    public BaseResponse<ReviewRes.ReviewCreateRes> createReview(@RequestBody ReviewReq.ReviewCreateReq request) throws BaseException {
        return new BaseResponse<>(reviewService.createReview(request));
    }


    //리뷰 조회
    @GetMapping("")
    public BaseResponse<Page<ReviewRes.ReviewListRes>> getReviewList(@PageableDefault(page = 0, size = 2) Pageable page) {
        Page<ReviewRes.ReviewListRes> reviewListRes = reviewService.getReviewList(page);

        return new BaseResponse<>(reviewListRes);
    }

    //리뷰 통계 조회
    @GetMapping("/statistics")
    public BaseResponse<List<ReviewRes.ReviewStatisticsRes>> getReviewStatisticsList(
            @RequestParam boolean receipt,
            @RequestParam GenderType gender,
            @PageableDefault(page = 0, size = 2, sort = "created_at", direction = Sort.Direction.DESC) Pageable page
    ) {
        List<ReviewRes.ReviewStatisticsRes> reviewStatisticsList = reviewService.getReviewStatisticsList(receipt, gender, page);

        return new BaseResponse<>(reviewStatisticsList);
    }

    //리뷰 상세 조회
    @GetMapping("/{reviewIdx}")
    public BaseResponse<ReviewRes.ReviewDetailRes> getReviewDetail( @PathVariable Long reviewIdx) throws BaseException {
        return new BaseResponse<>(reviewService.getReviewDetail(reviewIdx));
    }
}


package com.umc.BareuniBE.service;

import com.umc.BareuniBE.dto.ReviewReq;
import com.umc.BareuniBE.dto.ReviewRes;
import com.umc.BareuniBE.entities.Hospital;
import com.umc.BareuniBE.entities.Review;
import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.enums.GenderType;
import com.umc.BareuniBE.repository.HospitalRepository;
import com.umc.BareuniBE.repository.ReviewRepository;
import com.umc.BareuniBE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.umc.BareuniBE.global.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    private final HospitalRepository hospitalRepository;

    private final UploadService uploadService;

    public ReviewRes.ReviewCreateRes createReview(List<MultipartFile> files, ReviewReq.ReviewCreateReq request) throws BaseException {
        List<String> imagesUrl = uploadService.uploadImages(files);
        String imagesString = String.join(",", imagesUrl);

        User user = userRepository.findById(request.getUserIdx())
                .orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));

        Hospital hospital = hospitalRepository.findById(request.getHospitalIdx())
                .orElseThrow(() -> new BaseException(HOSPITAL_EMPTY_ID));

        Review newReview = Review.builder()
                .user(user)
                .hospital(hospital)
                .totalScore(request.getTotalScore())
                .treatmentScore(request.getTreatmentScore())
                .serviceScore(request.getServiceScore())
                .equipmentScore(request.getEquipmentScore())
                .content(request.getContent())
                .payment(request.getPayment())
                .receipt(request.isReceipt())
                .images(imagesString)
                .build();

        Review review = reviewRepository.saveAndFlush(newReview);
        return new ReviewRes.ReviewCreateRes(review);
    }


    public Page<ReviewRes.ReviewListRes> getReviewList(Pageable page){
        Page<Review> reviews = reviewRepository.findAll(PageRequest.of(page.getPageNumber(), page.getPageSize()));

        return reviews.map(review -> {
                    ReviewRes.ReviewListRes reviewListRes = new ReviewRes.ReviewListRes();
                    reviewListRes.setReviewIdx(review.getReviewIdx());
                    reviewListRes.setCreatedAt(review.getCreatedAt());
                    reviewListRes.setUpdatedAt(review.getUpdatedAt());
                    reviewListRes.setUser(review.getUser());
                    reviewListRes.setContent(review.getContent());
                    reviewListRes.setTotalScore(review.getTotalScore());
                    reviewListRes.setReceipt(review.isReceipt());

                    return reviewListRes;
                });
    }


    public List<ReviewRes.ReviewStatisticsRes> getReviewStatisticsList(boolean receipt, GenderType gender, Pageable page) {
        List<Review> reviews = reviewRepository.findByReceiptAndUserGender(receipt, gender, PageRequest.of(page.getPageNumber(), page.getPageSize(), page.getSort()));

        return reviews.stream()
                .map(review -> {
                    ReviewRes.ReviewStatisticsRes reviewStatisticsRes = new ReviewRes.ReviewStatisticsRes();
                    reviewStatisticsRes.setReviewIdx(review.getReviewIdx());
                    reviewStatisticsRes.setCreatedAt(review.getCreatedAt());
                    reviewStatisticsRes.setUpdatedAt(review.getUpdatedAt());
                    reviewStatisticsRes.setUser(review.getUser());
                    reviewStatisticsRes.setContent(review.getContent());
                    reviewStatisticsRes.setTotalScore(review.getTotalScore());
                    reviewStatisticsRes.setReceipt(review.isReceipt());

                    return reviewStatisticsRes;
                })
                .collect(Collectors.toList());
    }

    public ReviewRes.ReviewDetailRes getReviewDetail(Long reviewIdx) throws BaseException {
        Review review = reviewRepository.findById(reviewIdx)
                .orElseThrow(() -> new BaseException(REVIEW_EMPTY_ID));

        ReviewRes.ReviewDetailRes reviewDetailRes = new ReviewRes.ReviewDetailRes();
        reviewDetailRes.setReviewIdx(review.getReviewIdx());
        reviewDetailRes.setCreatedAt(review.getCreatedAt());
        reviewDetailRes.setUpdatedAt(review.getUpdatedAt());
        reviewDetailRes.setUser(review.getUser());
        reviewDetailRes.setContent(review.getContent());
        reviewDetailRes.setTotalScore(review.getTotalScore());
        reviewDetailRes.setPayment(review.getPayment());
        reviewDetailRes.setReceipt(review.isReceipt());

        return reviewDetailRes;
    }

    public List<ReviewRes.ReviewListRes> searchReview(String keyword) throws BaseException {
        List<ReviewRes.ReviewListRes> reviewList = reviewRepository.searchReview(keyword);

        if (reviewList.isEmpty()) {
            throw new BaseException(EMPTY_SEARCH_KEYWORD);
        }

        return reviewList;
    }
}

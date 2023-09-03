package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.dto.ReviewRes;

import java.util.List;

public interface ReviewRepositoryCustom {
    List<ReviewRes.ReviewSearchListRes> searchReview(String keyword);
}


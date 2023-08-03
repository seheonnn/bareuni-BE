package com.umc.BareuniBE.repository.querydsl;

import com.umc.BareuniBE.dto.HospitalRes;

import java.util.List;

public interface HospitalRepositoryCustom {
    List<HospitalRes.HospitalSummaryListRes> findRecommendHospital(String[] areaList);
}

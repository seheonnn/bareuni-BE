package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.dto.HospitalRes;

import java.util.List;

public interface HospitalRepositoryCustom {
    List<HospitalRes.HospitalSummaryListRes> findRecommendHospital(String[] areaList);

    List<HospitalRes.HospitalSummaryListRes> searchHospital(String keyword);

    List<HospitalRes.HospitalSummaryListRes> findNearHospital(String address1, String address2);
}

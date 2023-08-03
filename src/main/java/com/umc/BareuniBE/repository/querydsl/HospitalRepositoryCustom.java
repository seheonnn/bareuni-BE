package com.umc.BareuniBE.repository.querydsl;

import com.umc.BareuniBE.dto.HospitalRes;
import com.umc.BareuniBE.entities.Hospital;

import java.util.List;

public interface HospitalRepositoryCustom {
    List<HospitalRes.HospitalListRes> findRecommendHospital(String[] areaList);
}

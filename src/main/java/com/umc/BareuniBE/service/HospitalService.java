package com.umc.BareuniBE.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.dto.HospitalReq;
import com.umc.BareuniBE.dto.HospitalRes;
import com.umc.BareuniBE.entities.Hospital;
import com.umc.BareuniBE.entities.Scrap;
import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.repository.HospitalRepository;
import com.umc.BareuniBE.repository.ScrapRepository;
import com.umc.BareuniBE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.umc.BareuniBE.entities.QHospital.hospital;
import static com.umc.BareuniBE.global.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class HospitalService {

    @PersistenceContext
    private EntityManager em;
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;

    // 홈 - 후기가 좋은 치과 목록 조회
    public List<HospitalRes.HospitalListRes> getBestHospitalList() {
        List<Object[]> hospitals = hospitalRepository.findBestHospital();

        return hospitals.stream()
                .map(hospitalData -> {
                    HospitalRes.HospitalListRes hospitalListRes = new HospitalRes.HospitalListRes();
                    hospitalListRes.setHospitalIdx(hospitalData[0]);
                    hospitalListRes.setAddress(hospitalData[1]);
                    hospitalListRes.setHosName(hospitalData[2]);
                    hospitalListRes.setScore(hospitalData[3]);
                    hospitalListRes.setReviewCnt(hospitalData[4]);

                    return hospitalListRes;
                })
                .collect(Collectors.toList());
    }

    // 치과정보 탭 - 추천 치과 목록 조회
    public List<HospitalRes.HospitalListRes> getRecommendHospitalList(String[] areaList) {
        List<HospitalRes.HospitalListRes> resList = hospitalRepository.findRecommendHospital(areaList);
        return resList;
    }

    // 스크랩 추가
    public HospitalRes.HospitalScrapCreateRes createScrap(HospitalReq.HospitalScrapCreateReq request, Long hospitalIdx) throws BaseException {
        User user = userRepository.findById(request.getUserIdx())
                .orElseThrow(() ->  new BaseException(USERS_EMPTY_USER_ID));

        Hospital hospital = hospitalRepository.findById(hospitalIdx)
                .orElseThrow(() -> new BaseException(HOSPITAL_EMPTY_HOSPITAL_ID));

        Scrap newScrap = Scrap.builder()
                .user(user)
                .hospital(hospital)
                .build();

        Scrap scrap = scrapRepository.save(newScrap);
        return new HospitalRes.HospitalScrapCreateRes(scrap.getScrapIdx());
    }


}

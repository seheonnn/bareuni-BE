package com.umc.BareuniBE.service;

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
    public List<HospitalRes.HospitalSummaryListRes> getBestHospitalList() {
        List<Object[]> hospitals = hospitalRepository.findBestHospital();

        return hospitals.stream()
                .map(hospitalData -> {
                    HospitalRes.HospitalSummaryListRes hospitalSummaryListRes = new HospitalRes.HospitalSummaryListRes();
                    hospitalSummaryListRes.setHospitalIdx(hospitalData[0]);
                    hospitalSummaryListRes.setAddress(hospitalData[1]);
                    hospitalSummaryListRes.setHosName(hospitalData[2]);
                    hospitalSummaryListRes.setScore(hospitalData[3]);
                    hospitalSummaryListRes.setReviewCnt(hospitalData[4]);

                    return hospitalSummaryListRes;
                })
                .collect(Collectors.toList());
    }

    // 치과정보 탭 - 추천 치과 목록 조회
    public List<HospitalRes.HospitalSummaryListRes> getRecommendHospitalList(String[] areaList) {
        List<HospitalRes.HospitalSummaryListRes> resList = hospitalRepository.findRecommendHospital(areaList);
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

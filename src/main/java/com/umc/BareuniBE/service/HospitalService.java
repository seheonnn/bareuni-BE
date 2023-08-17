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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

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
    public HospitalRes.HospitalScrapCreateRes createScrap(HospitalReq.HospitalScrapReq request, Long hospitalIdx) throws BaseException {
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

    // 치과 정보글 상세 조회
    public HospitalRes.HospitalDetailRes getHospitalDetail(Long hospitalIdx) {
        HospitalRes.HospitalDetailResult result = hospitalRepository.findHospitalDetailById(hospitalIdx);

        HospitalRes.HospitalDetailRes hospitalDetailRes = new HospitalRes.HospitalDetailRes();
        hospitalDetailRes.setHospitalIdx(result.getHospital_idx());
        hospitalDetailRes.setHosName(result.getHospital_name());
        hospitalDetailRes.setBookable(result.getBookable());
        hospitalDetailRes.setClosedDay(result.getClosed_day());
        hospitalDetailRes.setAddress(result.getAddress());
        hospitalDetailRes.setKeywords(result.getKeywords());
        hospitalDetailRes.setOpenTime(result.getOpen_time());
        hospitalDetailRes.setLunchTime(result.getLunch_time());
        hospitalDetailRes.setContent(result.getContent());
        hospitalDetailRes.setTelephone(result.getTelephone());

        // 오늘 휴무일인지 아닌지 여부
        GregorianCalendar calendar_new = new GregorianCalendar();
        int week = calendar_new.get(Calendar.DAY_OF_WEEK);
        String[] weekname = {"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};
        boolean todayClosed = false;
        if (weekname[week].equals(result.getClosed_day())) {
            todayClosed = true;
        }
        hospitalDetailRes.setTodayClosed(todayClosed);

        hospitalDetailRes.setTotalScore(result.getTotal_score());
        hospitalDetailRes.setReviewCnt(result.getReview_cnt());

        hospitalDetailRes.setTreatment(result.getTreatment());
        hospitalDetailRes.setTreatmentRate(result.getTreatment_ratio());
        hospitalDetailRes.setService(result.getService());
        hospitalDetailRes.setServiceRate(result.getService_ratio());
        hospitalDetailRes.setEquipment(result.getEquipment());
        hospitalDetailRes.setEquipmentRate(result.getEquipment_ratio());

        return hospitalDetailRes;
    }

    // 스크랩 삭제
    public String deleteScrap(HospitalReq.HospitalScrapReq request, Long hospitalIdx, Long scrapIdx) throws BaseException {
        User user = userRepository.findById(request.getUserIdx())
                .orElseThrow(() ->  new BaseException(USERS_EMPTY_USER_ID));

        Hospital hospital = hospitalRepository.findById(hospitalIdx)
                .orElseThrow(() -> new BaseException(HOSPITAL_EMPTY_HOSPITAL_ID));

        Scrap scrap = scrapRepository.findById(scrapIdx)
                .orElseThrow(() -> new BaseException(SCRAP_EMPTY_SCRAP_ID));

        if (scrap.getUser() != user || scrap.getHospital() != hospital) {
            throw new BaseException(SCRAP_DELETE_AUTHORIZED_ERROR);
        }

        scrapRepository.delete(scrap);

        return "스크랩 삭제 성공!";
    }

    // 검색 - 치과
    public List<HospitalRes.HospitalSummaryListRes> searchHospital(String keyword) throws BaseException {
        List<HospitalRes.HospitalSummaryListRes> hospitalsList = hospitalRepository.searchHospital(keyword);

        if (hospitalsList.isEmpty()) {
            throw new BaseException(EMPTY_SEARCH_KEYWORD);
        }

        return hospitalsList;
    }
}

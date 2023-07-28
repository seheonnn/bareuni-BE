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

import static com.umc.BareuniBE.global.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;

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

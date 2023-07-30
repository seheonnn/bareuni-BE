package com.umc.BareuniBE.service;

import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.dto.HospitalRes;
import com.umc.BareuniBE.entities.Hospital;
import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.repository.CommunityRepository;
import com.umc.BareuniBE.repository.HospitalRepository;
import com.umc.BareuniBE.repository.ScrapRepository;
import com.umc.BareuniBE.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import static com.umc.BareuniBE.global.BaseResponseStatus.USERS_EMPTY_USER_ID;

@Service
public class MypageService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;

    @Autowired
    public MypageService(CommunityRepository communityRepository, UserRepository userRepository, ScrapRepository scrapRepository){
        this.communityRepository = communityRepository;
        this.scrapRepository = scrapRepository;
        this.userRepository = userRepository;
}

    // 작성한 글 목록 조회 (최신순)
    public List<CommunityRes.CommunityListRes> getMyCommunityList(Long userId, Pageable page) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));
        List<Object[]> communities = communityRepository.findAllCommunityByUserOrderByCreatedAtDesc(user, page);

        return communities.stream()
                .map(communityData -> {
                    CommunityRes.CommunityListRes communityRes = new CommunityRes.CommunityListRes();
                    communityRes.setCommunityIdx(communityData[0]);
                    communityRes.setCreatedAt( communityData[1]);
                    communityRes.setUpdatedAt( communityData[2]);
                    communityRes.setContent( communityData[3]);
                    communityRes.setUser(userRepository.findById(((BigInteger) communityData[4]).longValue()).orElse(null));
                    communityRes.setLike(communityData[5]);

                    return communityRes;
                })
                .collect(Collectors.toList());
    }

    // 치과 저장 목록 조회
    public List<HospitalRes.HospitalListRes> getMyHospitalList(Long userId, Pageable page) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));

        List<Object[]> scrap = scrapRepository.findHospitalsByUserId(user, page);

        return scrap.stream()
                .map(hospitalData -> {
                    HospitalRes.HospitalListRes hospitalRes = new HospitalRes.HospitalListRes();
                    hospitalRes.setScrapIdx(hospitalData[0]);
                    hospitalRes.setCreatedAt(hospitalData[1]);
                    hospitalRes.setUpdatedAt( hospitalData[2]);
                    hospitalRes.setUser(userRepository.findById(((Long) hospitalData[3]).longValue()).orElse(null));
                    hospitalRes.setHospital( hospitalData[4]);

                    return hospitalRes;
                })
                .collect(Collectors.toList());
    }
}

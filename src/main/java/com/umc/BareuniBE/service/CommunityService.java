package com.umc.BareuniBE.service;

import com.umc.BareuniBE.dto.CommunityReq;
import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.entities.Community;
import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.repository.CommunityRepository;
import com.umc.BareuniBE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.umc.BareuniBE.global.BaseResponseStatus.USERS_EMPTY_USER_ID;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    public CommunityRes.CommunityCreateRes createCommunity(CommunityReq.CommunityCreateReq request) throws BaseException {
        User user = userRepository.findById(request.getUserIdx())
                .orElseThrow(() ->  new BaseException(USERS_EMPTY_USER_ID));

        Community newCommunity = Community.builder()
                .user(user)
                .content(request.getContent())
                .build();
        Community community = communityRepository.saveAndFlush(newCommunity);
        return new CommunityRes.CommunityCreateRes(community);
    }

}

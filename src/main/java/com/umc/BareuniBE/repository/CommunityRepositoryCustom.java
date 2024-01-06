package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.entities.User;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface CommunityRepositoryCustom {
    List<CommunityRes.CommunityListRes> searchCommunity(String keyword);
    List<CommunityRes.CommunityListRes> getCommunityList(String sort);
    List<CommunityRes.CommunityListRes> getMyCommunityList(User user, Pageable pageable);
}


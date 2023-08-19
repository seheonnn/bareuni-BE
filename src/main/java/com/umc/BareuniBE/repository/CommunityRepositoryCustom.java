package com.umc.BareuniBE.repository;

import com.umc.BareuniBE.dto.CommunityRes;

import java.util.List;

public interface CommunityRepositoryCustom {
    List<CommunityRes.CommunityListRes> searchCommunity(String keyword);
}


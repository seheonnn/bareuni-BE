package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.CommunityReq;
import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.entities.Community;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    // 커뮤니티 글 작성
    @PostMapping("")
    public BaseResponse<CommunityRes.CommunityDetailRes> createCommunity(@RequestBody CommunityReq.CommunityCreateReq request) throws BaseException {
        return new BaseResponse<>(communityService.createCommunity(request));
    }

    // 커뮤니티 글 조회 (최신순, 좋아요순)
    @GetMapping("")
    public BaseResponse<List<CommunityRes.CommunityListRes>> getCommunityList(
            @PageableDefault(page = 0, size = 3, sort = "created_at", direction = Sort.Direction.DESC) Pageable page
    ) {
        return new BaseResponse<>(communityService.getCommunityList(page));
    }
}

package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.CommunityReq;
import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("")
    public BaseResponse<CommunityRes.CommunityCreateRes> createCommunity(@RequestBody CommunityReq.CommunityCreateReq request) throws BaseException {
        return new BaseResponse<>(communityService.createCommunity(request));
    }
}

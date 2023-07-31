package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.CommunityReq;
import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.service.CommunityService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    // 커뮤니티 글 작성
    @PostMapping("")
    public BaseResponse<CommunityRes.CommunityCreateRes> createCommunity(@RequestBody CommunityReq.CommunityCreateReq request) throws BaseException {
        return new BaseResponse<>(communityService.createCommunity(request));
    }

    // 커뮤니티 글 조회 (최신순, 좋아요순)
    @ApiOperation(value = "커뮤니티 글 조회 (최신순, 좋아요순)", notes = "ex1) http://localhost:8080/community?page=0&size=3&sort=created_at,desc\n\n ex2) http://localhost:8080/community?page=0&size=3&sort=likeCnt,desc")
    @GetMapping("")
    public BaseResponse<List<CommunityRes.CommunityListRes>> getCommunityList(
            @PageableDefault(page = 0, size = 3, sort = "created_at", direction = Sort.Direction.DESC) Pageable page
    ) {
        return new BaseResponse<>(communityService.getCommunityList(page));
    }

    // 커뮤니티 글 상세 조회
    @GetMapping("/{communityIdx}")
    public BaseResponse<CommunityRes.CommunityDetailRes> getCommunityDetails( @PathVariable Long communityIdx ) throws BaseException {
        return new BaseResponse<>(communityService.getCommunityDetails(communityIdx));
    }

    // 커뮤니티 글 수정
    @PatchMapping("/{communityIdx}")
    public BaseResponse<CommunityRes.CommunityCreateRes> updateCommunity(@PathVariable Long communityIdx, @RequestBody CommunityReq.CommunityCreateReq request) throws BaseException {
        return new BaseResponse<>(communityService.updateCommunity(communityIdx, request));
    }

    // 커뮤니티 글 삭제
    @DeleteMapping("/{communityIdx}")
    public BaseResponse<String> deleteCommunity(@PathVariable Long communityIdx, @RequestBody Map<String, Long> requestBody) throws BaseException {
        return new BaseResponse<>(communityService.deleteCommunity(communityIdx, requestBody.get("userIdx")));
    }

    // 커뮤니티 좋아요
    @PostMapping("/{communityIdx}/like")
    public BaseResponse<String> likeToggle(@PathVariable Long communityIdx, @RequestBody Map<String, Long> requestBody) throws BaseException {
        return new BaseResponse<>(communityService.likeToggle(requestBody.get("userIdx"), communityIdx));
    }
}

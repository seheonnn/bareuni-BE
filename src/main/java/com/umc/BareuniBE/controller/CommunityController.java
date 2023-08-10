package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.CommunityReq;
import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.service.CommunityService;

import lombok.RequiredArgsConstructor;

import io.swagger.annotations.ApiOperation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    // 커뮤니티 글 작성
    @ApiOperation(value = "커뮤니티 글 작성", notes = "ex) http://localhost:8080/community\n\n" +
            "{\n" +
            "\n" +
            "\"userIdx\":1,\n" +
            "\n" +
            "\"content\":\"커뮤니티 글입니다.\"\n" +
            "\n" +
            "}")
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
    @ApiOperation(value = "커뮤니티 글 상세 조회", notes = "ex) http://localhost:8080/community/1")
    @GetMapping("/{communityIdx}")
    public BaseResponse<CommunityRes.CommunityDetailRes> getCommunityDetails( @PathVariable Long communityIdx ) throws BaseException {
        return new BaseResponse<>(communityService.getCommunityDetails(communityIdx));
    }

    // 커뮤니티 글 수정
    @ApiOperation(value = "커뮤니티 글 수정", notes = "ex) http://localhost:8080/community/1\n\n" +
            "{\n\n" +
            "\"userIdx\":1,\n\n" +
            "\"content\":\"커뮤니티 글 수정입니다.\"\n\n" +
            "}")
    @PatchMapping("/{communityIdx}")
    public BaseResponse<CommunityRes.CommunityCreateRes> updateCommunity(@PathVariable Long communityIdx, @RequestBody CommunityReq.CommunityCreateReq request) throws BaseException {
        return new BaseResponse<>(communityService.updateCommunity(communityIdx, request));
    }

    // 커뮤니티 글 삭제
    @ApiOperation(value = "커뮤니티 글 삭제", notes = "ex) http://localhost:8080/community/1\n\n" +
            "{\n\n" +
            "\"userIdx\":1\n\n" +
            "}")
    @DeleteMapping("/{communityIdx}")
    public BaseResponse<String> deleteCommunity(@PathVariable Long communityIdx, @RequestBody Map<String, Long> requestBody) throws BaseException {
        return new BaseResponse<>(communityService.deleteCommunity(communityIdx, requestBody.get("userIdx")));
    }

    // 커뮤니티 글 좋아요
    @ApiOperation(value = "커뮤니티 글 좋아요", notes = "ex) http://localhost:8080/community/1/like\n\n" +
            "{\n\n" +
            "\"userIdx\":1\n\n" +
            "}")
    @PostMapping("/{communityIdx}/like")
    public BaseResponse<String> likeToggle(@PathVariable Long communityIdx, @RequestBody Map<String, Long> requestBody) throws BaseException {
        return new BaseResponse<>(communityService.likeToggle(requestBody.get("userIdx"), communityIdx));
    }

    // 커뮤니티 댓글 작성
    @ApiOperation(value = "커뮤니티 댓글 작성", notes = "ex) http://localhost:8080/community/1/comment\n\n" +
            "{\n\n" +
            "\"userIdx\":1,\n\n" +
            "\"comment\":\"커뮤니티 댓글입니다.\"\n\n" +
            "}")
    @PostMapping("/{communityIdx}/comment")
    public BaseResponse<CommunityRes.CommentCreateRes> createComment(@PathVariable Long communityIdx, @RequestBody CommunityReq.CommentCreateReq request) throws BaseException {
        return new BaseResponse<>(communityService.createComment(communityIdx, request));
    }

    // 커뮤니티 댓글 삭제
    @ApiOperation(value = "커뮤니티 댓글 삭제", notes = "ex) http://localhost:8080/community/comment/1\n\n" +
            "{\n\n" +
            "\"userIdx\":1\n\n" +
            "}")
    @DeleteMapping("/comment/{commentIdx}")
    public BaseResponse<String> deleteComment(@PathVariable Long commentIdx, @RequestBody CommunityReq.CommentDeleteReq request) throws BaseException {
        return new BaseResponse<>(communityService.deleteComment(commentIdx, request));
    }

    // 홈 - 커뮤니티 인기글 조회
    @ApiOperation(value = "홈 - 커뮤니티 인기글 조회", notes = "ex) http://localhost:8080/community/best")
    @GetMapping("/best")
    public BaseResponse<List<CommunityRes.BestCommunityListRes>> getBestCommunityList(
    ) {
        return new BaseResponse<>(communityService.getBestCommunityList());
    }

}

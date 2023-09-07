package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.CommunityReq;
import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.global.BaseResponseStatus;
import com.umc.BareuniBE.global.enums.AlarmType;
import com.umc.BareuniBE.service.CommunityService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;
    private final AlarmController alarmController;

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
    public BaseResponse<CommunityRes.CommunityCreateRes> createCommunity(@RequestBody CommunityReq.CommunityCreateReq communityCreateReq, HttpServletRequest request) throws BaseException {
        CommunityRes.CommunityCreateRes res = communityService.createCommunity(communityCreateReq, request);
        alarmController.subscribe(res.getCommunityIdx()); // 이 커뮤니티 글에 대해 알림 구독
        return new BaseResponse<>(res);
    }

    // 커뮤니티 글 조회 (최신순, 좋아요순)
    @ApiOperation(value = "커뮤니티 글 조회 (최신순, 좋아요순)", notes = "ex1) http://localhost:8080/community?page=0&size=3&sort=created_at,desc\n\n ex2) http://localhost:8080/community?page=0&size=3&sort=likeCnt,desc")
    @GetMapping("")
    public BaseResponse<List<CommunityRes.CommunityListRes>> getCommunityList(
//            @PageableDefault(page = 0, size = 3, sort = "created_at", direction = Sort.Direction.DESC) Pageable page,
            @RequestParam(value = "sort", required = false, defaultValue = "created_at") String sort
    ) throws BaseException {
        return new BaseResponse<>(communityService.getCommunityList(sort));
    }


    // 커뮤니티 글 상세 조회
    @ApiOperation(value = "커뮤니티 글 상세 조회", notes = "ex) http://localhost:8080/community/1")
    @GetMapping("/{communityIdx}")
    public BaseResponse<CommunityRes.CommunityDetailRes> getCommunityDetails( @PathVariable Long communityIdx, HttpServletRequest request ) throws BaseException {
        return new BaseResponse<>(communityService.getCommunityDetails(communityIdx, request));
    }

    // 커뮤니티 글 수정
    @ApiOperation(value = "커뮤니티 글 수정", notes = "ex) http://localhost:8080/community/1\n\n" +
            "{\n\n" +
            "\"userIdx\":1,\n\n" +
            "\"content\":\"커뮤니티 글 수정입니다.\"\n\n" +
            "}")
    @PatchMapping("/{communityIdx}")
    public BaseResponse<CommunityRes.CommunityCreateRes> updateCommunity(@PathVariable Long communityIdx, @RequestBody CommunityReq.CommunityCreateReq communityCreateReq, HttpServletRequest request) throws BaseException {
        return new BaseResponse<>(communityService.updateCommunity(communityIdx, communityCreateReq, request));
    }

    // 커뮤니티 글 삭제
    @ApiOperation(value = "커뮤니티 글 삭제", notes = "ex) http://localhost:8080/community/1\n\n" +
            "{\n\n" +
            "\"userIdx\":1\n\n" +
            "}")
    @DeleteMapping("/{communityIdx}")
    public BaseResponse<String> deleteCommunity(@PathVariable Long communityIdx, HttpServletRequest request) throws BaseException {
        return new BaseResponse<>(communityService.deleteCommunity(communityIdx, request));
    }

    // 커뮤니티 글 좋아요
    @ApiOperation(value = "커뮤니티 글 좋아요", notes = "ex) http://localhost:8080/community/1/like\n\n" +
            "{\n\n" +
            "\"userIdx\":1\n\n" +
            "}")
    @PostMapping("/{communityIdx}/like")
    public BaseResponse<String> likeToggle(@PathVariable Long communityIdx, HttpServletRequest request) throws BaseException {
        return new BaseResponse<>(communityService.likeToggle(communityIdx, request));
    }

    // 커뮤니티 댓글 작성
    @ApiOperation(value = "커뮤니티 댓글 작성", notes = "ex) http://localhost:8080/community/1/comment\n\n" +
            "{\n\n" +
            "\"userIdx\":1,\n\n" +
            "\"comment\":\"커뮤니티 댓글입니다.\"\n\n" +
            "}")
    @PostMapping("/{communityIdx}/comment")
    public BaseResponse<CommunityRes.CommentSummary> createComment(@PathVariable Long communityIdx, @RequestBody CommunityReq.CommentCreateReq commentCreateReq, HttpServletRequest request) throws BaseException {
        CommunityRes.CommentSummary res = communityService.createComment(communityIdx, commentCreateReq, request); // 댓글 작성

        Long communityWriterIdx = communityService.getCommunityDetails(communityIdx, request).getUser().getUserIdx();
        alarmController.sendData(AlarmType.COMMENT, communityIdx, res.getComment(), communityWriterIdx); // 글 작성자에게 알림 생성

        return new BaseResponse<>(res);
    }

    // 커뮤니티 댓글 삭제
    @ApiOperation(value = "커뮤니티 댓글 삭제", notes = "ex) http://localhost:8080/community/comment/1\n\n" +
            "{\n\n" +
            "\"userIdx\":1\n\n" +
            "}")
    @DeleteMapping("/comment/{commentIdx}")
    public BaseResponse<BaseResponseStatus> deleteComment(@PathVariable Long commentIdx, HttpServletRequest request) throws BaseException {
        return new BaseResponse<>(communityService.deleteComment(commentIdx, request));
    }

    // 홈 - 커뮤니티 인기글 조회
    @ApiOperation(value = "홈 - 커뮤니티 인기글 조회", notes = "ex) http://localhost:8080/community/best")
    @GetMapping("/best")
    public BaseResponse<List<CommunityRes.BestCommunityListRes>> getBestCommunityList(
    ) {
        return new BaseResponse<>(communityService.getBestCommunityList());
    }

    // 검색 - 커뮤니티
    @ApiOperation(value = "검색 - 커뮤니티", notes = "ex) http://localhost:8080/search?keyword=바른이")
    @GetMapping("/search")
    public BaseResponse<List<CommunityRes.CommunityListRes>> searchCommunity (
            @RequestParam(value = "keyword") String keyword
    ) throws BaseException {
        return new BaseResponse<>(communityService.searchCommunity(keyword));
    }
}

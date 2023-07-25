package com.umc.BareuniBE.service;

import com.umc.BareuniBE.dto.CommunityReq;
import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.entities.Comment;
import com.umc.BareuniBE.entities.Community;
import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.repository.CommentRepository;
import com.umc.BareuniBE.repository.CommunityRepository;
import com.umc.BareuniBE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.umc.BareuniBE.global.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;

    private static final int PAGE_POST_COUNT = 10; // 한 페이지 당 게시글 수

    public CommunityRes.CommunityCreateRes createCommunity(CommunityReq.CommunityCreateReq request) throws BaseException {
        User user = userRepository.findById(request.getUserIdx())
                .orElseThrow(() ->  new BaseException(USERS_EMPTY_USER_ID));

        Community newCommunity = Community.builder()
                .user(user)
                .content(request.getContent())
                .build();

        return new CommunityRes.CommunityCreateRes(communityRepository.saveAndFlush(newCommunity));
    }

    public List<CommunityRes.CommunityListRes> getCommunityList(Pageable page) {
        List<Object[]> communities = communityRepository.findAllCommunity_Pagination(PageRequest.of(page.getPageNumber(), page.getPageSize(), page.getSort()));
        return communities.stream()
                .map(communityData -> {
                    CommunityRes.CommunityListRes communityRes = new CommunityRes.CommunityListRes();
                    communityRes.setCommunityIdx(communityData[0]);
                    communityRes.setCreatedAt(communityData[1]);
                    communityRes.setUpdatedAt(communityData[2]);
                    communityRes.setContent(communityData[3]);
                    communityRes.setUser(userRepository.findById(((BigInteger)communityData[4]).longValue()).orElse(null));
                    communityRes.setLike(communityData[5]);

                    return communityRes;
                })
                .collect(Collectors.toList());
    }

    public CommunityRes.CommunityDetailRes getCommunityDetails(Long communityIdx) throws BaseException {
        Community community = communityRepository.findById(communityIdx)
                .orElseThrow(() -> new BaseException(COMMUNITY_EMPTY_COMMUNITY_ID));

        log.info(String.valueOf(community));
        List<Comment> comments = commentRepository.findAllByCommunity(community);
        List<CommunityRes.CommentSummary> commentList = comments.stream()
                .map(comment -> {
                    CommunityRes.CommentSummary commentSummary = new CommunityRes.CommentSummary();
                    commentSummary.setNickname(comment.getUser().getNickname());
                    commentSummary.setComment(comment.getComment());
                    commentSummary.setCommentCreatedAt(comment.getCreatedAt());
                    return commentSummary;
                })
                .collect(Collectors.toList());
        return new CommunityRes.CommunityDetailRes(community.getCommunityIdx(), community.getUser(), commentList);
    }

    public CommunityRes.CommunityCreateRes updateCommunity(Long communityIdx, CommunityReq.CommunityCreateReq request) throws BaseException {
        // 해당 글 유저
        Community community = communityRepository.findById(communityIdx)
                .orElseThrow(() -> new BaseException(COMMUNITY_EMPTY_COMMUNITY_ID));
        // request 로 받은 유저
        User user = userRepository.findById(request.getUserIdx())
                .orElseThrow(() ->  new BaseException(USERS_EMPTY_USER_ID));


        if (community.getUser() != user)
            throw new BaseException(UPDATE_AUTHORIZED_ERROR);


        community.setContent(request.getContent());

        return new CommunityRes.CommunityCreateRes(communityRepository.saveAndFlush(community));
    }


}

package com.umc.BareuniBE.service;

import com.umc.BareuniBE.config.security.JwtTokenProvider;
import com.umc.BareuniBE.dto.CommunityReq;
import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.entities.Comment;
import com.umc.BareuniBE.entities.Community;
import com.umc.BareuniBE.entities.LikeEntity;
import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponseStatus;
import com.umc.BareuniBE.repository.CommentRepository;
import com.umc.BareuniBE.repository.CommunityRepository;
import com.umc.BareuniBE.repository.LikeRepository;
import com.umc.BareuniBE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import static com.umc.BareuniBE.global.BaseResponseStatus.USERS_EMPTY_USER_ID;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.umc.BareuniBE.global.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class  CommunityService {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public CommunityRes.CommunityCreateRes createCommunity(CommunityReq.CommunityCreateReq communityCreateReq, HttpServletRequest request) throws BaseException {
        log.info(String.valueOf(jwtTokenProvider.getCurrentUser(request)));
        User user = userRepository.findById(jwtTokenProvider.getCurrentUser(request))
                .orElseThrow(() ->  new BaseException(USERS_EMPTY_USER_ID));

        Community newCommunity = Community.builder()
                .user(user)
                .content(communityCreateReq.getContent())
                .build();

        return new CommunityRes.CommunityCreateRes(communityRepository.saveAndFlush(newCommunity));
    }

//    public List<CommunityRes.CommunityListRes> getCommunityList(Pageable page, HttpServletRequest request) throws BaseException {
    public List<CommunityRes.CommunityListRes> getCommunityList(String sort) throws BaseException {
//        List<Object[]> communities = communityRepository.findAllCommunity_Pagination(PageRequest.of(page.getPageNumber(), page.getPageSize(), page.getSort()));

        List<Object[]> communities = communityRepository.findAllCommunity_custom(sort);

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

    public CommunityRes.CommunityDetailRes getCommunityDetails(Long communityIdx, HttpServletRequest request) throws BaseException {
        jwtTokenProvider.getCurrentUser(request);

        Community community = communityRepository.findById(communityIdx)
                .orElseThrow(() -> new BaseException(COMMUNITY_EMPTY_ID));

        List<Comment> comments = commentRepository.findAllByCommunity(community);
        List<CommunityRes.CommentSummary> commentList = comments.stream()
                .map(comment -> {
                    return new CommunityRes.CommentSummary(comment);
                })
                .collect(Collectors.toList());
        return new CommunityRes.CommunityDetailRes(community, commentList);
    }

    public CommunityRes.CommunityCreateRes updateCommunity(Long communityIdx, CommunityReq.CommunityCreateReq communityCreateReq, HttpServletRequest request) throws BaseException {
        // 해당 글 유저
        Community community = communityRepository.findById(communityIdx)
                .orElseThrow(() -> new BaseException(COMMUNITY_EMPTY_ID));
        // request 로 받은 유저

        Long currentUserIdx = jwtTokenProvider.getCurrentUser(request);
        User user = userRepository.findById(currentUserIdx)
                .orElseThrow(() ->  new BaseException(USERS_EMPTY_USER_ID));


        if (community.getUser() != user)
            throw new BaseException(UPDATE_AUTHORIZED_ERROR);


        community.setContent(communityCreateReq.getContent());
        community.setUpdatedAt(LocalDateTime.now());

        return new CommunityRes.CommunityCreateRes(communityRepository.saveAndFlush(community));
    }

    public BaseResponseStatus deleteCommunity(Long communityIdx, HttpServletRequest request) throws BaseException {
        // 해당 글 유저
        Community community = communityRepository.findById(communityIdx)
                .orElseThrow(() -> new BaseException(COMMUNITY_EMPTY_ID));
        // request 로 받은 유저
        User user = userRepository.findById(jwtTokenProvider.getCurrentUser(request))
                .orElseThrow(() ->  new BaseException(USERS_EMPTY_USER_ID));


        if (community.getUser() != user)
            throw new BaseException(UPDATE_AUTHORIZED_ERROR);

        communityRepository.delete(community);
        return SUCCESS;
    }

    public BaseResponseStatus likeToggle(Long communityIdx, HttpServletRequest request) throws BaseException {

        User user = userRepository.findById(jwtTokenProvider.getCurrentUser(request))
                .orElseThrow(() ->  new BaseException(USERS_EMPTY_USER_ID));
        Community community = communityRepository.findById(communityIdx)
                .orElseThrow(() -> new BaseException(COMMUNITY_EMPTY_ID));


        Optional<LikeEntity> likeRelation = likeRepository.findByUserAndCommunity(user, community);

        if (likeRelation.isPresent()) {
            likeRepository.delete(likeRelation.get());
            return CANCELED_LIKE;
        }
        else {
            likeRepository.saveAndFlush(new LikeEntity(user, community));
            return SUCCESS_LIKE;
        }

    }

    public CommunityRes.CommentSummary createComment (Long communityIdx, CommunityReq.CommentCreateReq commentCreateReq, HttpServletRequest request) throws BaseException {
        User user = userRepository.findById(jwtTokenProvider.getCurrentUser(request))
                .orElseThrow(() ->  new BaseException(USERS_EMPTY_USER_ID));

        Community community = communityRepository.findById(communityIdx)
                .orElseThrow(() -> new BaseException(COMMUNITY_EMPTY_ID));

        Comment newComment = Comment.builder()
                .user(user)
                .community(community)
                .comment(commentCreateReq.getComment())
                .build();

        return new CommunityRes.CommentSummary(commentRepository.saveAndFlush(newComment));
    }

    public BaseResponseStatus deleteComment (Long commentIdx, HttpServletRequest request) throws BaseException {
        User user = userRepository.findById(jwtTokenProvider.getCurrentUser(request))
                .orElseThrow(() ->  new BaseException(USERS_EMPTY_USER_ID));

        Comment comment = commentRepository.findById(commentIdx)
                .orElseThrow(() -> new BaseException(Comment_EMPTY_ID));

        if (comment.getUser() != user)
            throw new BaseException(UPDATE_AUTHORIZED_ERROR);

        commentRepository.delete(comment);

        return SUCCESS;
    }

    public List<CommunityRes.BestCommunityListRes> getBestCommunityList() {
        List<Object[]> communities = communityRepository.getBestCommunityList();

        return communities.stream()
                .map(communityData -> {
                    CommunityRes.BestCommunityListRes bestCommunityListRes = new CommunityRes.BestCommunityListRes();
                    bestCommunityListRes.setCommunityIdx(communityData[0]);
                    bestCommunityListRes.setCreatedAt(communityData[1]);
                    bestCommunityListRes.setUpdatedAt(communityData[2]);
                    bestCommunityListRes.setContent(communityData[3]);
                    bestCommunityListRes.setLikeCnt(communityData[5]);

                    return bestCommunityListRes;
                })
                .collect(Collectors.toList());
    }

    public List<CommunityRes.CommunityListRes> searchCommunity(String keyword) throws BaseException {
        List<CommunityRes.CommunityListRes> communityList = communityRepository.searchCommunity(keyword);

        if (communityList.isEmpty()) {
            throw new BaseException(EMPTY_SEARCH_KEYWORD);
        }

        return communityList;
    }
}

package com.umc.BareuniBE.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.dto.UserRes;
import com.umc.BareuniBE.entities.User;

import lombok.RequiredArgsConstructor;

import org.hibernate.internal.util.NullnessHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.umc.BareuniBE.entities.QCommunity.community;
import static com.umc.BareuniBE.entities.QLikeEntity.likeEntity;
import static org.hibernate.internal.util.NullnessHelper.*;

@Repository
@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommunityRes.CommunityListRes> searchCommunity(String keyword) {
        List<CommunityRes.CommunityListRes> communities = queryFactory
                .select(
                        Projections.constructor(CommunityRes.CommunityListRes.class,
                                community.communityIdx,
                                community.createdAt,
                                community.updatedAt,
                                Projections.constructor(UserRes.UserSummary.class,
                                        community.user.userIdx, community.user.nickname, community.user.profile),
                                community.content,
                                likeEntity.community.count().as("like")
                        )
                )
                .from(community, community)
                .leftJoin(likeEntity).on(community.communityIdx.eq(likeEntity.community.communityIdx))
                .groupBy(community.communityIdx)
                .where(community.content.contains(keyword))
                .orderBy(community.createdAt.asc())
                .fetch();

        return communities;
    }

    @Override
    public List<CommunityRes.CommunityListRes> getCommunityList(String sort) {
        List<CommunityRes.CommunityListRes> communities = queryFactory
            .select(
                Projections.constructor(CommunityRes.CommunityListRes.class,
                    community.communityIdx,
                    community.createdAt,
                    community.updatedAt,
                    Projections.constructor(UserRes.UserSummary.class,
                        community.user.userIdx,
                        community.user.nickname,
                        community.user.profile),
                    community.content,
                    likeEntity.community.count().coalesce(0L).as("like")
                )
            )
            .from(community)
            .leftJoin(likeEntity).on(community.communityIdx.eq(likeEntity.community.communityIdx))
            .groupBy(community.communityIdx)
            .orderBy(getOrderByExpression(sort))
            .fetch();

        return communities;
    }

    @Override
    public List<CommunityRes.CommunityListRes> getMyCommunityList(User user, Pageable page) {
        List<CommunityRes.CommunityListRes> communities = queryFactory
            .select(
                Projections.constructor(CommunityRes.CommunityListRes.class,
                    community.communityIdx,
                    community.createdAt,
                    community.updatedAt,
                    Projections.constructor(UserRes.UserSummary.class,
                        community.user.userIdx,
                        community.user.nickname,
                        community.user.profile),
                    community.content,
                    likeEntity.community.count().coalesce(0L).as("like")
                )
            )
            .from(community)
            .leftJoin(likeEntity).on(community.communityIdx.eq(likeEntity.community.communityIdx))
            .where(community.user.eq(user))
            .groupBy(community.communityIdx)
            .orderBy(community.createdAt.desc())
            .fetch();

        return communities;
    }

    private OrderSpecifier<?> getOrderByExpression(String sort) {
        switch (sort) {
            case "created":
                return community.createdAt.desc();
            case "like":
                return likeEntity.community.count().desc();
            default:
                return community.createdAt.desc();
        }
    }
}

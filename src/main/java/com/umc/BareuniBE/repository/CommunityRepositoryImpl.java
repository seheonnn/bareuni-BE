package com.umc.BareuniBE.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.BareuniBE.dto.CommunityRes;
import com.umc.BareuniBE.dto.UserRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.umc.BareuniBE.entities.QCommunity.community;
import static com.umc.BareuniBE.entities.QLikeEntity.likeEntity;

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
                                Projections.bean(UserRes.UserSummary.class,
                                        community.user),
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
}

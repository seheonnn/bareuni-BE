package com.umc.BareuniBE.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.BareuniBE.dto.ReviewRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.umc.BareuniBE.entities.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReviewRes.ReviewSearchListRes> searchReview(String keyword) {
        List<ReviewRes.ReviewSearchListRes> reviews = queryFactory
                .select(
                        Projections.constructor(ReviewRes.ReviewSearchListRes.class,
                                review,
                                review.user)
                )
                .from(review, review)
                .where(review.content.contains(keyword))
                .orderBy(review.createdAt.asc())
                .fetch();

        return reviews;
    }
}

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
    public List<ReviewRes.ReviewListRes> searchReview(String keyword) {
        List<ReviewRes.ReviewListRes> reviews = queryFactory
                .select(
                        Projections.constructor(ReviewRes.ReviewListRes.class,
                                review.reviewIdx,
                                review.createdAt,
                                review.updatedAt,
                                review.user,
                                review.content,
                                review.totalScore,
                                review.receipt
                        )
                )
                .from(review, review)
                .where(review.content.contains(keyword))
                .orderBy(review.createdAt.asc())
                .fetch();

        return reviews;
    }
}

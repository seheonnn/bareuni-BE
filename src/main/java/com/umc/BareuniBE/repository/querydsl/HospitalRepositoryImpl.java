package com.umc.BareuniBE.repository.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.BareuniBE.dto.HospitalRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.umc.BareuniBE.entities.QHospital.hospital;
import static com.umc.BareuniBE.entities.QReview.review;

@Repository
@RequiredArgsConstructor
public class HospitalRepositoryImpl implements HospitalRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<HospitalRes.HospitalSummaryListRes> findRecommendHospital(String[] areaList) {
        BooleanBuilder builder = new BooleanBuilder();
        for (String area : areaList) {
            builder.or(hospital.address.contains(area));
        }

        List<HospitalRes.HospitalSummaryListRes> hospitals = queryFactory
                .select(
                        Projections.constructor(HospitalRes.HospitalSummaryListRes.class,
                                hospital.hospitalIdx.as("hospitalIdx"),
                                hospital.hospitalName.as("hosName"),
                                hospital.address.as("address"),
                                review.totalScore.avg().as("totalScore"),
                                review.count().as("reviewCnt"),
                                hospital.summary.as("summary")
                        )
                )
                .from(review, review)
                .join(review.hospital, hospital)
                .where(builder)
                .groupBy(hospital.hospitalIdx)
                .orderBy(review.totalScore.desc())
                .fetch();

        return hospitals;
    }
}

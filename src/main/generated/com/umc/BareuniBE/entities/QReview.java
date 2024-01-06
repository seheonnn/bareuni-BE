package com.umc.BareuniBE.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = -1890715416L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final com.umc.BareuniBE.global.QBaseEntity _super = new com.umc.BareuniBE.global.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.umc.BareuniBE.global.enums.ReviewType> equipmentScore = createEnum("equipmentScore", com.umc.BareuniBE.global.enums.ReviewType.class);

    public final QHospital hospital;

    public final StringPath images = createString("images");

    public final NumberPath<Long> payment = createNumber("payment", Long.class);

    public final BooleanPath receipt = createBoolean("receipt");

    public final NumberPath<Long> reviewIdx = createNumber("reviewIdx", Long.class);

    public final EnumPath<com.umc.BareuniBE.global.enums.ReviewType> serviceScore = createEnum("serviceScore", com.umc.BareuniBE.global.enums.ReviewType.class);

    public final NumberPath<Integer> totalScore = createNumber("totalScore", Integer.class);

    public final EnumPath<com.umc.BareuniBE.global.enums.ReviewType> treatmentScore = createEnum("treatmentScore", com.umc.BareuniBE.global.enums.ReviewType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hospital = inits.isInitialized("hospital") ? new QHospital(forProperty("hospital")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}


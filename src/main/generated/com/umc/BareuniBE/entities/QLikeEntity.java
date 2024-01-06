package com.umc.BareuniBE.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeEntity is a Querydsl query type for LikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeEntity extends EntityPathBase<LikeEntity> {

    private static final long serialVersionUID = -360256534L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeEntity likeEntity = new QLikeEntity("likeEntity");

    public final com.umc.BareuniBE.global.QBaseEntity _super = new com.umc.BareuniBE.global.QBaseEntity(this);

    public final QCommunity community;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> likeIdx = createNumber("likeIdx", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QLikeEntity(String variable) {
        this(LikeEntity.class, forVariable(variable), INITS);
    }

    public QLikeEntity(Path<? extends LikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeEntity(PathMetadata metadata, PathInits inits) {
        this(LikeEntity.class, metadata, inits);
    }

    public QLikeEntity(Class<? extends LikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.community = inits.isInitialized("community") ? new QCommunity(forProperty("community"), inits.get("community")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}


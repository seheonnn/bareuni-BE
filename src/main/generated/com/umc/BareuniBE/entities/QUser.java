package com.umc.BareuniBE.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1154936485L;

    public static final QUser user = new QUser("user");

    public final com.umc.BareuniBE.global.QBaseEntity _super = new com.umc.BareuniBE.global.QBaseEntity(this);

    public final NumberPath<Long> age = createNumber("age", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final EnumPath<com.umc.BareuniBE.global.enums.GenderType> gender = createEnum("gender", com.umc.BareuniBE.global.enums.GenderType.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final BooleanPath ortho = createBoolean("ortho");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath profile = createString("profile");

    public final StringPath provider = createString("provider");

    public final EnumPath<com.umc.BareuniBE.global.enums.RoleType> role = createEnum("role", com.umc.BareuniBE.global.enums.RoleType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userIdx = createNumber("userIdx", Long.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}


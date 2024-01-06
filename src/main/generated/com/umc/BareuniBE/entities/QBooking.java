package com.umc.BareuniBE.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBooking is a Querydsl query type for Booking
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBooking extends EntityPathBase<Booking> {

    private static final long serialVersionUID = 482097353L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBooking booking = new QBooking("booking");

    public final com.umc.BareuniBE.global.QBaseEntity _super = new com.umc.BareuniBE.global.QBaseEntity(this);

    public final DateTimePath<java.time.LocalDateTime> bookingDate = createDateTime("bookingDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> bookingIdx = createNumber("bookingIdx", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QHospital hospital;

    public final StringPath method = createString("method");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QBooking(String variable) {
        this(Booking.class, forVariable(variable), INITS);
    }

    public QBooking(Path<? extends Booking> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBooking(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBooking(PathMetadata metadata, PathInits inits) {
        this(Booking.class, metadata, inits);
    }

    public QBooking(Class<? extends Booking> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hospital = inits.isInitialized("hospital") ? new QHospital(forProperty("hospital")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}


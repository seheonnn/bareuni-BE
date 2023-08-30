package com.umc.BareuniBE.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHospital is a Querydsl query type for Hospital
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHospital extends EntityPathBase<Hospital> {

    private static final long serialVersionUID = -248783926L;

    public static final QHospital hospital = new QHospital("hospital");

    public final com.umc.BareuniBE.global.QBaseEntity _super = new com.umc.BareuniBE.global.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final BooleanPath bookable = createBoolean("bookable");

    public final ListPath<Booking, QBooking> bookings = this.<Booking, QBooking>createList("bookings", Booking.class, QBooking.class, PathInits.DIRECT2);

    public final StringPath closedDay = createString("closedDay");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> hospitalIdx = createNumber("hospitalIdx", Long.class);

    public final StringPath hospitalName = createString("hospitalName");

    public final StringPath image = createString("image");

    public final StringPath keywords = createString("keywords");

    public final StringPath lunchTime = createString("lunchTime");

    public final StringPath openTime = createString("openTime");

    public final ListPath<Review, QReview> reviews = this.<Review, QReview>createList("reviews", Review.class, QReview.class, PathInits.DIRECT2);

    public final StringPath summary = createString("summary");

    public final StringPath telephone = createString("telephone");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QHospital(String variable) {
        super(Hospital.class, forVariable(variable));
    }

    public QHospital(Path<? extends Hospital> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHospital(PathMetadata metadata) {
        super(Hospital.class, metadata);
    }

}


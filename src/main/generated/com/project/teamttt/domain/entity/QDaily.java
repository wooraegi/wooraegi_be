package com.project.teamttt.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDaily is a Querydsl query type for Daily
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDaily extends EntityPathBase<Daily> {

    private static final long serialVersionUID = -369194017L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDaily daily = new QDaily("daily");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final NumberPath<Long> dailyId = createNumber("dailyId", Long.class);

    public final BooleanPath isPublic = createBoolean("isPublic");

    public final QMember member;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public QDaily(String variable) {
        this(Daily.class, forVariable(variable), INITS);
    }

    public QDaily(Path<? extends Daily> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDaily(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDaily(PathMetadata metadata, PathInits inits) {
        this(Daily.class, metadata, inits);
    }

    public QDaily(Class<? extends Daily> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}


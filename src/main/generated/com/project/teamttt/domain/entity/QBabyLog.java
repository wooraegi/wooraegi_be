package com.project.teamttt.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBabyLog is a Querydsl query type for BabyLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBabyLog extends EntityPathBase<BabyLog> {

    private static final long serialVersionUID = -94289196L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBabyLog babyLog = new QBabyLog("babyLog");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBaby baby;

    public final NumberPath<Long> babyLogId = createNumber("babyLogId", Long.class);

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public QBabyLog(String variable) {
        this(BabyLog.class, forVariable(variable), INITS);
    }

    public QBabyLog(Path<? extends BabyLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBabyLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBabyLog(PathMetadata metadata, PathInits inits) {
        this(BabyLog.class, metadata, inits);
    }

    public QBabyLog(Class<? extends BabyLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.baby = inits.isInitialized("baby") ? new QBaby(forProperty("baby"), inits.get("baby")) : null;
    }

}


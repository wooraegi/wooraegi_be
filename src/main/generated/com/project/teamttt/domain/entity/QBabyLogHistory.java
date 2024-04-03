package com.project.teamttt.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBabyLogHistory is a Querydsl query type for BabyLogHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBabyLogHistory extends EntityPathBase<BabyLogHistory> {

    private static final long serialVersionUID = 1963074336L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBabyLogHistory babyLogHistory = new QBabyLogHistory("babyLogHistory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBaby baby;

    public final NumberPath<Long> babyLogHistoryId = createNumber("babyLogHistoryId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final BooleanPath isChecked = createBoolean("isChecked");

    public final DateTimePath<java.util.Date> logDate = createDateTime("logDate", java.util.Date.class);

    public final StringPath todoName = createString("todoName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public QBabyLogHistory(String variable) {
        this(BabyLogHistory.class, forVariable(variable), INITS);
    }

    public QBabyLogHistory(Path<? extends BabyLogHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBabyLogHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBabyLogHistory(PathMetadata metadata, PathInits inits) {
        this(BabyLogHistory.class, metadata, inits);
    }

    public QBabyLogHistory(Class<? extends BabyLogHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.baby = inits.isInitialized("baby") ? new QBaby(forProperty("baby"), inits.get("baby")) : null;
    }

}


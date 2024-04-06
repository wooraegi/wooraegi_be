package com.project.teamttt.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBabyLogItem is a Querydsl query type for BabyLogItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBabyLogItem extends EntityPathBase<BabyLogItem> {

    private static final long serialVersionUID = -1883330553L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBabyLogItem babyLogItem = new QBabyLogItem("babyLogItem");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBabyLog babyLog;

    public final NumberPath<Long> babyLogItemId = createNumber("babyLogItemId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final StringPath todoName = createString("todoName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public QBabyLogItem(String variable) {
        this(BabyLogItem.class, forVariable(variable), INITS);
    }

    public QBabyLogItem(Path<? extends BabyLogItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBabyLogItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBabyLogItem(PathMetadata metadata, PathInits inits) {
        this(BabyLogItem.class, metadata, inits);
    }

    public QBabyLogItem(Class<? extends BabyLogItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.babyLog = inits.isInitialized("babyLog") ? new QBabyLog(forProperty("babyLog"), inits.get("babyLog")) : null;
    }

}


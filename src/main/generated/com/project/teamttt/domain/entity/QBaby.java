package com.project.teamttt.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBaby is a Querydsl query type for Baby
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBaby extends EntityPathBase<Baby> {

    private static final long serialVersionUID = -1813084592L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBaby baby = new QBaby("baby");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath animalType = createString("animalType");

    public final NumberPath<Long> babyId = createNumber("babyId", Long.class);

    public final StringPath babyName = createString("babyName");

    public final StringPath birth = createString("birth");

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final BooleanPath isPublic = createBoolean("isPublic");

    public final QMember member;

    public final StringPath nickname = createString("nickname");

    public final StringPath reminder = createString("reminder");

    public final StringPath sex = createString("sex");

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public QBaby(String variable) {
        this(Baby.class, forVariable(variable), INITS);
    }

    public QBaby(Path<? extends Baby> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBaby(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBaby(PathMetadata metadata, PathInits inits) {
        this(Baby.class, metadata, inits);
    }

    public QBaby(Class<? extends Baby> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}


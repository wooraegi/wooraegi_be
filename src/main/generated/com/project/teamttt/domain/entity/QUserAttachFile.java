package com.project.teamttt.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAttachFile is a Querydsl query type for UserAttachFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAttachFile extends EntityPathBase<UserAttachFile> {

    private static final long serialVersionUID = -827399034L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAttachFile userAttachFile = new QUserAttachFile("userAttachFile");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> attachFileId = createNumber("attachFileId", Long.class);

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final StringPath fileUrl = createString("fileUrl");

    public final BooleanPath isUsed = createBoolean("isUsed");

    public final QMember member;

    public final StringPath refId = createString("refId");

    public final StringPath refType = createString("refType");

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public QUserAttachFile(String variable) {
        this(UserAttachFile.class, forVariable(variable), INITS);
    }

    public QUserAttachFile(Path<? extends UserAttachFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAttachFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAttachFile(PathMetadata metadata, PathInits inits) {
        this(UserAttachFile.class, metadata, inits);
    }

    public QUserAttachFile(Class<? extends UserAttachFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}


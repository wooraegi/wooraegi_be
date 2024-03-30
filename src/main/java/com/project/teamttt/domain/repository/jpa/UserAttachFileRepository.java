package com.project.teamttt.domain.repository.jpa;

import com.project.teamttt.domain.entity.UserAttachFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAttachFileRepository extends JpaRepository<UserAttachFile, Long> {
    List<UserAttachFile> findByRefId(String refId);
    UserAttachFile findByRefIdAndIsUsedTrue(String refId);
    Long deleteByAttachFileId (Long attachFileId);

}

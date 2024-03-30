package com.project.teamttt.domain.repository.jpa;

import com.project.teamttt.domain.entity.BabyLog;
import com.project.teamttt.domain.entity.BabyLogItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BabyLogItemRepository extends JpaRepository<BabyLogItem, Long> {
    List<BabyLogItem> findByBabyLog(BabyLog babyLog);
}

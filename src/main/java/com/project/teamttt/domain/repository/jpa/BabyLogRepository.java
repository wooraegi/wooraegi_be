package com.project.teamttt.domain.repository.jpa;

import com.project.teamttt.domain.entity.Baby;
import com.project.teamttt.domain.entity.BabyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BabyLogRepository extends JpaRepository<BabyLog, Long> {

    @Query("SELECT bl FROM BabyLog bl WHERE bl.baby = :baby AND bl.createdAt = (SELECT MAX(bl2.createdAt) FROM BabyLog bl2 WHERE bl2.baby = :baby)")
    BabyLog findMostRecentByBaby(Baby baby);

}

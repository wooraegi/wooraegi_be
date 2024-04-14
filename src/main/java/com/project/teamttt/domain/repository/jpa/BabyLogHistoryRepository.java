package com.project.teamttt.domain.repository.jpa;

import com.project.teamttt.domain.entity.Baby;
import com.project.teamttt.domain.entity.BabyLogHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BabyLogHistoryRepository extends JpaRepository<BabyLogHistory, Long> {

    Long countByLogDateAndBaby(Date logDate, Baby baby);

    Long deleteByBabyAndLogDate(Baby baby, Date logDate);

    Long deleteByBabyLogHistoryId(Long babyLogHistoryId);

    List<BabyLogHistory> findAllByBaby(Baby baby);

    List<BabyLogHistory> findByBabyAndLogDate(Baby baby, Date logDate);

}


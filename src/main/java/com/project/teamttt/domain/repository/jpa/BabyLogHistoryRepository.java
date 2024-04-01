package com.project.teamttt.domain.repository.jpa;

import com.project.teamttt.domain.entity.Baby;
import com.project.teamttt.domain.entity.BabyLogHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BabyLogHistoryRepository extends JpaRepository<BabyLogHistory, Long> {

    Long countByLogDate(Date logDate);

    Long deleteByBabyAndLogDate(Baby baby, Date logDate);

    List<BabyLogHistory> findAllByBaby(Baby baby);
}


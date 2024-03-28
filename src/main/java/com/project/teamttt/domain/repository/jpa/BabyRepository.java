package com.project.teamttt.domain.repository.jpa;

import com.project.teamttt.domain.entity.Baby;
import com.project.teamttt.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BabyRepository extends JpaRepository<Baby, Long> {

    Baby findByBabyId(Long babyId);

    Long deleteByBabyId(Long babyId);

    List<Baby> findByMember(Member member);
}

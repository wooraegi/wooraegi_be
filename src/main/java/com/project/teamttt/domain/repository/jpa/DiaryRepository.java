package com.project.teamttt.domain.repository.jpa;

import com.project.teamttt.domain.entity.Diary;
import com.project.teamttt.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    Diary findByDiaryId(Long diaryId);

    Long deleteByDiaryId(Long diaryId);

    List<Diary> findByMember(Member member);
}

package com.project.teamttt.domain.service;

import com.project.teamttt.api.diary.dto.DiaryRequestDto;
import com.project.teamttt.domain.entity.Diary;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.repository.jpa.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class DiaryDomainService {
        private final DiaryRepository diaryRepository;

        public Diary save(DiaryRequestDto.RequestCreate requestCreate, Member member) {
            return diaryRepository.save(
                    Diary.builder()
                            .member(member)
                            .title(requestCreate.getTitle())
                            .content(requestCreate.getContent())
                            .isPublic(requestCreate.getIsPublic())
                            .build()
            );
        }

        public Diary save(DiaryRequestDto.RequestUpdate RequestUpdate, Member member) {
            return diaryRepository.save(
                    Diary.builder()
                            .member(member)
                            .diaryId(RequestUpdate.getDiaryId())
                            .title(RequestUpdate.getTitle())
                            .content(RequestUpdate.getContent())
                            .isPublic(RequestUpdate.getIsPublic())
                            .build()
            );
        }

        public Diary findByDiaryId(Long diaryId) {return diaryRepository.findByDiaryId(diaryId);
        }

        public Boolean deleteByDiaryId(Long diaryId) {
            if(diaryRepository.deleteByDiaryId(diaryId) > 0){
                return true;
            }
            return false;
        }

        public List<Diary> getDiaryListByMemberId(Long memberId)
        {
            return diaryRepository.findByMemberId(memberId);
        }
    }



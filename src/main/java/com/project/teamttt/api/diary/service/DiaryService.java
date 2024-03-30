package com.project.teamttt.api.diary.service;

import com.project.teamttt.api.diary.dto.DiaryRequestDto;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.Diary;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.service.DiaryDomainService;
import com.project.teamttt.domain.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryDomainService diaryDomainService;
    private final MemberDomainService memberDomainService;


    @Transactional
    public ResponseDto<String> createDiary(DiaryRequestDto.RequestCreate requestCreate) {
        Long memberId = requestCreate.getMemberId();
        Member member = memberDomainService.findByMemberId(memberId);
        try {
            diaryDomainService.save(requestCreate, member);
            return new ResponseDto<>(true, "SUCCESS CREATE DIARY", null);
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO CREATE DIARY: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ResponseDto<String> updateDiary(DiaryRequestDto.RequestUpdate requestUpdate) {
        try {
            Long memberIdByLogin = requestUpdate.getMemberId();
            Member member = memberDomainService.findByMemberId(memberIdByLogin);

            Diary diary = diaryDomainService.findByDiaryId(requestUpdate.getDiaryId());
            Long memberIdByDiary = diary.getMember().getMemberId();


            if(memberIdByLogin.equals(memberIdByDiary)){

                diaryDomainService.save(requestUpdate, member);
                return new ResponseDto<>(true, "SUCCESS UPDATE DIARY", null);

            }else {
                return new ResponseDto<>(false, "MEMBER ID IS DIFFERENT", null);
            }
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO UPDATE DIARY: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ResponseDto<String> deleteDiary(Long diaryId, Long memberId) {
        try {

            Diary diary = diaryDomainService.findByDiaryId(diaryId);
            Long memberIdByDiary = diary.getMember().getMemberId();

            if(memberId.equals(memberIdByDiary)){
                diaryDomainService.deleteByDiaryId(diaryId);
                return new ResponseDto<>(true, "SUCCESS DELETE DIARY", null);

            }else {
                return new ResponseDto<>(false, "MEMBER ID IS DIFFERENT", null);
            }
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO DELETE DIARY: " + e.getMessage(), null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<DiaryRequestDto.ResponseDiary>> getDiaryList(Long memberId) {
        try {
            List<DiaryRequestDto.ResponseDiary> diaryList = diaryDomainService.getDiaryListByMemberId(memberId)
                    .stream()
                    .map(DiaryRequestDto.ResponseDiary::of)
                    .collect(Collectors.toList());

            return new ResponseDto<>(true, "SUCCESS GET DIARYLIST", diaryList);

        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO GET DIARYLIST: " + e.getMessage(), null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<DiaryRequestDto.ResponseDiary> getDiaryById(Long diaryId) {
        try {
            // 특정 다이어리 아이디로 다이어리를 조회
            Diary diary = diaryDomainService.findByDiaryId(diaryId);
            // 다이어리가 존재하지 않는 경우 에러 응답 반환
            if (diary == null) {
                return new ResponseDto<>(false, "Diary not found for id: " + diaryId, null);
            }
            // 다이어리를 ResponseDto로 변환하여 반환
            DiaryRequestDto.ResponseDiary responseDiary = DiaryRequestDto.ResponseDiary.of(diary);
            responseDiary.setMemberId(diary.getMember().getMemberId());
            return new ResponseDto<>(true, "SUCCESS GET DIARY", responseDiary);
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO GET DIARY: " + e.getMessage(), null);
        }
    }
}
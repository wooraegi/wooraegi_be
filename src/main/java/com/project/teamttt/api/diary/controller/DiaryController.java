package com.project.teamttt.api.diary.controller;


import com.project.teamttt.api.diary.dto.DiaryRequestDto;
import com.project.teamttt.api.diary.service.DiaryService;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.teamttt.endpoint.DiaryEndPoint.*;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    /**
     * @param requestCreate
     * @return ResponseEntity<String>
     */
    @PostMapping(DIALY_CREATE)
    public ResponseEntity<String> saveDiary(@RequestBody DiaryRequestDto.RequestCreate requestCreate) {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }
        requestCreate.setMemberId(memberId);
        ResponseDto<String> response = diaryService.createDiary(requestCreate);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }

    /**
     * @param RequestUpdate
     * @return ResponseEntity<String>
     */
    @PostMapping(DIALY_UPDATE)
    public ResponseEntity<String> updateDiary(@RequestBody DiaryRequestDto.RequestUpdate RequestUpdate) {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }
        RequestUpdate.setMemberId(memberId);
        ResponseDto<String> response = diaryService.updateDiary(RequestUpdate);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }

    /**
     * @param diaryId
     * @return ResponseEntity<String>
     */
    @DeleteMapping(DIALY_DELETE)
    public ResponseEntity<String> deleteDiary(@RequestParam Long diaryId) {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }

        ResponseDto<String> response = diaryService.deleteDiary(diaryId, memberId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }

    /**
     * @return ResponseEntity<ResponseDto < List < Diary>>>
     */
    @GetMapping(DIALY_LIST)
    public ResponseEntity<ResponseDto<List<DiaryRequestDto.ResponseDiary>>> getDiaryList() {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }

        ResponseDto<List<DiaryRequestDto.ResponseDiary>> response = diaryService.getDiaryList(memberId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }
    }
    @GetMapping(DIARY_GET_BY_ID)
    public ResponseEntity<ResponseDto<DiaryRequestDto.ResponseDiary>> getDiaryById(@RequestParam Long diaryId) {
        ResponseDto<DiaryRequestDto.ResponseDiary> response = diaryService.getDiaryById(diaryId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
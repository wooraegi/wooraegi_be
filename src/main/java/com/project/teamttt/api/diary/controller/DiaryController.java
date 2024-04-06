package com.project.teamttt.api.diary.controller;


import com.project.teamttt.api.diary.dto.DiaryRequestDto;
import com.project.teamttt.api.diary.service.DiaryService;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.Member;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.project.teamttt.endpoint.DiaryEndPoint.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "4. DIARY API", description = "반려동물 다이어리 관련 api입니다.")
public class DiaryController {

    private final DiaryService diaryService;

    /**
     * @param requestCreate
     * @return ResponseEntity<String>
     */
    @PostMapping(value = DIALY_CREATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveDiary(@RequestPart @Valid DiaryRequestDto.RequestCreate requestCreate, @RequestPart(value = "imageFileList", required = false) List<MultipartFile> imageFileList) {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }
        requestCreate.setMemberId(memberId);
        ResponseDto<String> response = diaryService.createDiary(requestCreate, imageFileList);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }

    /**
     * @param requestUpdate
     * @return ResponseEntity<String>
     */
    @PostMapping(DIALY_UPDATE)
    public ResponseEntity<String> updateDiary(@RequestPart @Valid DiaryRequestDto.RequestUpdate requestUpdate, @RequestPart(value = "imageFileList", required = false) List<MultipartFile> imageFileList) {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }
        requestUpdate.setMemberId(memberId);
        ResponseDto<String> response = diaryService.updateDiary(requestUpdate, imageFileList);

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
    public ResponseEntity<ResponseDto<DiaryRequestDto.ResponseDiaryDetail>> getDiaryById(@RequestParam Long diaryId) {
        ResponseDto<DiaryRequestDto.ResponseDiaryDetail> response = diaryService.getDiaryById(diaryId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
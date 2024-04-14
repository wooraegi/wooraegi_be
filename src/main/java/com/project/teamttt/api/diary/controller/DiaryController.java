package com.project.teamttt.api.diary.controller;


import com.project.teamttt.api.diary.dto.DiaryRequestDto;
import com.project.teamttt.api.diary.service.DiaryService;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "4.DIARY API", description = "반려동물 다이어리 관련 api입니다.")
public class DiaryController {

    private final DiaryService diaryService;

    /**
     * @param requestCreate, image
     * @return ResponseEntity<String>
     */
    @Operation(summary = "반려동물 다이어리 등록",
            description = "자신의 반려동물의 다이어리를 등록하는 API" +
                    "\n### * 반려동물 등록 요청 예시" +
                    "\n {\n" +
                    "\n \"memberId\" : \"1\",\n" +
                    "\n \"title\" : \"몽쉘이의 소풍\",\n" +
                    "\n \"content\" : \"오늘도 즐거운 산책을 했다, 우리 몽쉘이는 항상 귀엽다\",\n" +
                    "\n \"isPublic\" : true\n" +
                    "\n }"

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "다이어리 등록에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "다이어리 등록에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
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
     * @param requestUpdate, image
     * @return ResponseEntity<String>
     */
    @Operation(summary = "다이어리 수정",
            description = "멤버가 등록한 다이어리을 수정하는 API" +
                    "\n### * 반려동물 수정 요청 예시" +
                    "\n {\n" +
                    "\n \"diaryId\" : 1, //다이어리 등록을 진행 후 id값을 입력해주세요\n" +
                    "\n \"title\" : \"몽쉘이의 소풍, 병원 방문\",\n" +
                    "\n \"content\" : \"오늘도 즐거운 산책을 했다, 우리 몽쉘이는 항상 귀엽다.\",\n" +
                    "\n \"isPublic\" : false\n" +
                    "\n }"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "다이어리 수정에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "다이어리 수정에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping(value = DIALY_UPDATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

    @Operation(summary = "다이어리 삭제", description = "멤버가 등록한 다이어리을 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "다이어리 삭제에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "다이어리 삭제에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
    @Parameters({
            @Parameter(name = "diaryId", description = "삭제할 다이어리 아이디", in = ParameterIn.QUERY, required = true),
    })
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
    @Operation(summary = "다이어리 리스트 조회", description = "멤버가 등록한 다이어리 리스트를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "다이어리 리스트 조회에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "다이어리 리스트 조회에 실패했습니다.", content = @Content(mediaType = "application/json"))
    })
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

    /**
     * @param diaryId
     * @return ResponseEntity<String>
     */
    @Operation(summary = "다이어리 상세페이지", description = "멤버가 등록한 다이어리을 상세페이지를 가져오는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "다이어리를 가져왔습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "다이어리를 가져오지 못했습니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "diaryId", description = "선택하는 다이어리 아이디", in = ParameterIn.QUERY, required = true),
    })

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
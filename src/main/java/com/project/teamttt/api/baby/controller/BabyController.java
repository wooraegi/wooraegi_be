package com.project.teamttt.api.baby.controller;

import com.project.teamttt.api.baby.dto.BabyRequestDto;
import com.project.teamttt.api.baby.service.BabyService;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;

import static com.project.teamttt.endpoint.BabyEndPoint.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "BABY API", description = "반려동물 관련 api입니다.")
public class BabyController {

    private final BabyService babyService;

    /**
     *
     * @param babyRequestDto, image
     * @return ResponseEntity<String>
     */
    @Operation(summary = "반려동물 등록",
            description = "멤버가 자신의 반려동물을 등록하는 API" +
                    "\n### * 반려동물 등록 요청 예시" +
                    "\n {\n" +
                    "\n \"babyName\" : \"냐옹이\",\n" +
                    "\n \"birth\" : \"2012.06.23\",\n" +
                    "\n \"sex\" : \"F\",\n" +
                    "\n \"animalType\" : \"CAT\",\n" +
                    "\n \"nickname\" : \"말랑이\",\n" +
                    "\n \"reminder\" : \"아침 영양제 잊지않기\",\n" +
                    "\n \"isPublic\" : false\n" +
                    "\n }"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "반려동물 등록에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "반려동물 등록에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping(value = BABY_CREATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveBaby(@RequestPart @Valid BabyRequestDto babyRequestDto, @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }
        babyRequestDto.setMemberId(memberId);
        ResponseDto<String> response = babyService.createBaby(babyRequestDto, imageFile);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }

    /**
     *
     * @param requestUpdate, image
     * @return ResponseEntity<String>
     */
    @Operation(summary = "반려동물 수정",
            description = "멤버가 등록한 반려동물을 수정하는 API" +
                    "\n### * 반려동물 수정 요청 예시" +
                    "\n {\n" +
                    "\n \"babyId\" : 1, //반려동물 등록을 진행 후 id값을 입력해주세요\n" +
                    "\n \"babyName\" : \"냐옹이\",\n" +
                    "\n \"birth\" : \"2024.06.23\",\n" +
                    "\n \"sex\" : \"M\",\n" +
                    "\n \"animalType\" : \"CAT\",\n" +
                    "\n \"nickname\" : \"나비\",\n" +
                    "\n \"reminder\" : \"하루 산책 2번\",\n" +
                    "\n \"isPublic\" : true\n" +
                    "\n }"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "반려동물 수정에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "반려동물 수정에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping(value = BABY_UPDATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateBaby(@RequestPart @Valid BabyRequestDto.RequestUpdate requestUpdate, @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }
        requestUpdate.setMemberId(memberId);
        ResponseDto<String> response = babyService.updateBaby(requestUpdate, imageFile);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }

    /**
     *
     * @param babyId
     * @return ResponseEntity<String>
     */
    @Operation(summary = "반려동물 삭제", description = "멤버가 등록한 반려동물을 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "반려동물 삭제에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "반려동물 삭제에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
    @Parameters({
            @Parameter(name = "babyId", description = "삭제할 반려동물의 아이디", in = ParameterIn.QUERY, required = true),
    })
    @DeleteMapping(BABY_DELETE)
    public ResponseEntity<String> deleteBaby(@RequestParam Long babyId) {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }

        ResponseDto<String> response = babyService.deleteBaby(babyId, memberId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }

    /**
     *
     * @return ResponseEntity<ResponseDto<List<Baby>>>
     */
    @Operation(summary = "반려동물 리스트 조회", description = "멤버가 등록한 반려동물 리스트를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "반려동물 리스트 조회에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "반려동물 리스트 조회에 실패했습니다.", content = @Content(mediaType = "application/json"))
    })
    @GetMapping(BABY_LIST)
    public ResponseEntity<ResponseDto<List<BabyRequestDto.ResponseBaby>>> getBabyList() {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }

        ResponseDto<List<BabyRequestDto.ResponseBaby>> response = babyService.getBabyList(memberId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}

package com.project.teamttt.api.profile.controller;

import com.project.teamttt.api.member.service.MemberService;
import com.project.teamttt.api.profile.dto.ProfileRequestDto;
import com.project.teamttt.api.profile.service.ProfileService;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.project.teamttt.endpoint.ProfileEndPoint.PROFILE_DETAIL;
import static com.project.teamttt.endpoint.ProfileEndPoint.PROFILE_UPDATE;

@RestController
@RequiredArgsConstructor
@Tag(name = "PROFILE API", description = "멤버 프로필 관련 api입니다.")
public class ProfileController {

    private final ProfileService profileService;
    private final MemberService memberService;

    /**
     *
     * @return ResponseEntity<ResponseDto<ProfileRequestDto.ResponseDetail>>
     */
    @Operation(summary = "프로필 조회", description = "맴버 프로필 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 조회에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "프로필 조회에 실패했습니다.", content = @Content(mediaType = "application/json"))
    })
    @GetMapping(PROFILE_DETAIL)
    public ResponseEntity<ResponseDto<ProfileRequestDto.ResponseDetail>> getProfile() {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }

        ResponseDto<ProfileRequestDto.ResponseDetail> response = profileService.findMemberByMemberId(memberId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     *
     * @param nickname
     * @return ResponseEntity<String>
     */
    @Operation(summary = "프로필 수정", description = "맴버 프로필 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 수정에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "프로필 수정에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
    @Parameters({
            @Parameter(name = "nickname", description = "wooraegi에서 사용할 멤버의 이름", in = ParameterIn.QUERY, required = true)
    })
    @PostMapping(PROFILE_UPDATE)
    public ResponseEntity<String> updateMember(@RequestParam String nickname) {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }

        ResponseDto<String> response = memberService.updateMember(memberId, nickname);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }
}

package com.project.teamttt.api.profile.controller;

import com.project.teamttt.api.member.service.MemberService;
import com.project.teamttt.api.profile.dto.ProfileRequestDto;
import com.project.teamttt.api.profile.service.ProfileService;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.Member;
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
public class ProfileController {

    private final ProfileService profileService;
    private final MemberService memberService;

    /**
     *
     * @return ResponseEntity<ResponseDto<ProfileRequestDto.ResponseDetail>>
     */
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
    @PostMapping(PROFILE_UPDATE)
    public ResponseEntity<String> updateBaby(@RequestParam String nickname) {
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

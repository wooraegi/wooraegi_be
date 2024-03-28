package com.project.teamttt.api.baby.controller;

import com.project.teamttt.api.baby.dto.BabyRequestDto;
import com.project.teamttt.api.baby.service.BabyService;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.teamttt.endpoint.BabyEndPoint.*;

@RestController
@RequiredArgsConstructor
public class BabyController {

    private final BabyService babyService;

    /**
     *
     * @param requestCreate
     * @return ResponseEntity<String>
     */
    @PostMapping(BABY_CREATE)
    public ResponseEntity<String> saveBaby(@RequestBody BabyRequestDto.RequestCreate requestCreate) {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }
        requestCreate.setMemberId(memberId);
        ResponseDto<String> response = babyService.createBaby(requestCreate);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }

    /**
     *
     * @param RequestUpdate
     * @return ResponseEntity<String>
     */
    @PostMapping(BABY_UPDATE)
    public ResponseEntity<String> updateBaby(@RequestBody BabyRequestDto.RequestUpdate RequestUpdate) {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }
        RequestUpdate.setMemberId(memberId);
        ResponseDto<String> response = babyService.updateBaby(RequestUpdate);

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

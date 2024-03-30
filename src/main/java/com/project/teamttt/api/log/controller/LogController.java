package com.project.teamttt.api.log.controller;


import com.project.teamttt.api.log.service.LogService;
import com.project.teamttt.api.log.dto.LogRequestDto;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.teamttt.endpoint.LogEndPoint.LOG_ITEM_CREATE;
import static com.project.teamttt.endpoint.LogEndPoint.LOG_ITEM_LIST;

@RestController
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;

    /**
     *
     * @param requestCreate
     * @return ResponseEntity<String>
     */
    @PostMapping(LOG_ITEM_CREATE)
    public ResponseEntity<String> saveLogItem(@RequestBody LogRequestDto.RequestCreate requestCreate) {
        Long memberId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Member member = (Member) authentication.getPrincipal();
            memberId = member.getMemberId();
        }

        ResponseDto<String> response = logService.saveLogItem(memberId, requestCreate);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }

    /**
     * @param babyId
     * @return ResponseEntity<ResponseDto<List<BabyLogItem>>>
     */
    @GetMapping(LOG_ITEM_LIST)
    public ResponseEntity<ResponseDto<List<String>>> getLogItemList(@RequestParam Long babyId) {

        ResponseDto<List<String>> response = logService.getLogItemList(babyId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}

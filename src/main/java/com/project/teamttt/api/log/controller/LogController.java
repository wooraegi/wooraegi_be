package com.project.teamttt.api.log.controller;

import com.project.teamttt.api.log.dto.LogHistoryDto;
import com.project.teamttt.api.log.service.LogService;
import com.project.teamttt.api.log.dto.LogRequestDto;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.BabyLogHistory;
import com.project.teamttt.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.project.teamttt.endpoint.LogEndPoint.*;

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
    public ResponseEntity<ResponseDto<List<String>>> getLogList(@RequestParam Long babyId) {

        ResponseDto<List<String>> response = logService.getLogItemList(babyId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    /**
     *
     * @param requestCreateLogHistory
     * @return ResponseEntity<String>
     */
    @PostMapping(LOG_CREATE)
    public ResponseEntity<String> saveLogHistory(@RequestBody List<LogHistoryDto.RequestCreateLogHistory> requestCreateLogHistory) {

        ResponseDto<String> response = logService.saveLogHistory(requestCreateLogHistory);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }

    /**
     *
     * @param requestUpdateLogHistory
     * @return ResponseEntity<String>
     */
    @PostMapping(LOG_UPDATE)
    public ResponseEntity<String> updateLog(@RequestBody LogHistoryDto.UpdateLogHistory requestUpdateLogHistory) {

        ResponseDto<String> response = logService.updateLog(requestUpdateLogHistory);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
    }

    /**
     * @param babyId, @param logDate
     * @return ResponseEntity<String>
     */
    @DeleteMapping(LOG_DELETE)
    public ResponseEntity<String> deleteLog(@RequestParam Long babyId,@RequestParam String logDate) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = dateFormat.parse(logDate);
        ResponseDto<String> response = logService.deleteLog(babyId, date);

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
    @GetMapping(LOG_LIST)
    public ResponseEntity<ResponseDto<Map<String, List<LogHistoryDto.ResponseBabyLogHistory>>>> getLogHistoryList(@RequestParam Long babyId) {

        ResponseDto<Map<String, List<LogHistoryDto.ResponseBabyLogHistory>>> response = logService.getLogHistoryList(babyId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}

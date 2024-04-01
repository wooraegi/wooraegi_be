package com.project.teamttt.api.log.controller;

import com.project.teamttt.api.log.dto.LogHistoryDto;
import com.project.teamttt.api.log.service.LogService;
import com.project.teamttt.api.log.dto.LogRequestDto;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.BabyLogHistory;
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
@Tag(name = "LOG API", description = "데일리 로그 관련 api입니다.")
public class LogController {
    private final LogService logService;

    /**
     *
     * @param requestCreate
     * @return ResponseEntity<String>
     */
    @Operation(summary = "로그 아이템 저장",
            description = "데일리 로그 아이템 설정값을 저장하는 API" +
                    "\n### * 로그 아이템 저장 요청 예시" +
                    "\n {\n" +
                    "\n \"babyId\" : 1,\n" +
                    "\n \"todoNameList\" : [\"아침밥\",\"영양제\",\"오전산책\",\"오후산책\",\"저녁밥\"]\n" +
                    "\n }"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그 아이템 저장에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "로그 아이템 저장에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
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
    @Operation(summary = "로그 아이템 리스트 조회", description = "가장 최근에 저장한 로그 아이템 리스트를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그 아이템 리스트 조회에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "로그 아이템 리스트 조회에 실패했습니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "babyId", description = "로그 아이템 조회할 반려동물의 아이디", in = ParameterIn.QUERY, required = true),
    })
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

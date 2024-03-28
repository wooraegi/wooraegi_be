package com.project.teamttt.api.daily.controller;


import com.project.teamttt.api.daily.dto.DailyRequestDto;
import com.project.teamttt.api.daily.service.DailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import static com.project.teamttt.endpoint.DailyEndPoint.DAILY_CREATE;

@RestController
@RequiredArgsConstructor
public class DailyController {

    private final DailyService dailyService;

    /**
     * @param
     * @return Post 정보 저장 성공시 String  "Success" 메세지
     */
    @PostMapping(DAILY_CREATE)
    public String dailyCreate(@RequestBody DailyRequestDto.DailyCreate dailyCreate) throws IOException {
        return dailyService.dailyCreate(dailyCreate);
    }
}
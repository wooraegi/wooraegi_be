package com.project.teamttt.api.diary.controller;


import com.project.teamttt.api.diary.dto.DiaryRequestDto;
import com.project.teamttt.api.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import static com.project.teamttt.endpoint.DiaryEndPoint.DAILY_CREATE;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    /**
     * @param
     * @return Post 정보 저장 성공시 String  "Success" 메세지
     */
    @PostMapping(DAILY_CREATE)
    public String dailyCreate(@RequestBody DiaryRequestDto.DiaryCreate diaryCreate) throws IOException {
        return diaryService.diaryCreate(diaryCreate);
    }
}
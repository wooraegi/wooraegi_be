package com.project.teamttt.api.log.service;

import com.project.teamttt.api.log.dto.LogRequestDto;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.Baby;
import com.project.teamttt.domain.entity.BabyLog;
import com.project.teamttt.domain.entity.BabyLogItem;
import com.project.teamttt.domain.service.BabyDomainService;
import com.project.teamttt.domain.service.LogDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {
    private final BabyDomainService babyDomainService;
    private final LogDomainService logDomainService;

    @Transactional
    public ResponseDto<String> saveLogItem(Long memberId, LogRequestDto.RequestCreate requestCreate) {
        try {
            boolean containsEmptyString = requestCreate.getTodoNameList().stream().anyMatch(String::isEmpty);

            if(containsEmptyString){
                return new ResponseDto<>(false, "TO DO NAME LIST contains null value", null);
            }
            Baby baby = babyDomainService.findByBabyId(requestCreate.getBabyId());
            if(memberId.equals(baby.getMember().getMemberId())){

                BabyLog babyLog = logDomainService.save(createRequestCreateLog(baby));

                List<String> todoNameList = requestCreate.getTodoNameList();
                for(String todoNameOne : todoNameList) {
                    logDomainService.save(createRequestCreateLogItem(todoNameOne, babyLog));
                }

            }else{
                return new ResponseDto<>(false, "MEMBER ID IS DIFFERENT", null);
            }

            return new ResponseDto<>(true, "SUCCESS SAVE LOG ITEM", null);
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO SAVE LOG ITEM: " + e.getMessage(), null);
        }
    }

    private LogRequestDto.RequestCreateLog createRequestCreateLog(Baby baby) {
        return LogRequestDto.RequestCreateLog.builder()
                .baby(baby)
                .build();
    }

    private LogRequestDto.RequestCreateLogItem createRequestCreateLogItem(String todoNameOne, BabyLog babyLog) {
            return LogRequestDto.RequestCreateLogItem.builder()
                    .babyLog(babyLog)
                    .todoName(todoNameOne)
                    .build();

    }


    @Transactional(readOnly = true)
    public ResponseDto<List<String>> getLogItemList(Long babyId) {
        try {
            Baby baby = babyDomainService.findByBabyId(babyId);
            BabyLog babyLog = logDomainService.getBabyLogByBabyId(baby);

            List<BabyLogItem> babyLogItemList = logDomainService.getBabyLogItemLIst(babyLog);

            List<String> LogItemList = new ArrayList<>();
            for (BabyLogItem babyLogItem : babyLogItemList) {
                LogItemList.add(babyLogItem.getTodoName());
            }
            return new ResponseDto<>(true, "SUCCESS GET LOG ITEM LIST", LogItemList);
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO GET LOG ITEM LIST: " + e.getMessage(), null);
        }
    }
}

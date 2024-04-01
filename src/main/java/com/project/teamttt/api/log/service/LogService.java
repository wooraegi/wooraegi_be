package com.project.teamttt.api.log.service;

import com.project.teamttt.api.diary.dto.DiaryRequestDto;
import com.project.teamttt.api.log.dto.LogHistoryDto;
import com.project.teamttt.api.log.dto.LogRequestDto;
import com.project.teamttt.api.util.ResponseDto;
import com.project.teamttt.domain.entity.*;
import com.project.teamttt.domain.repository.jpa.BabyLogHistoryRepository;
import com.project.teamttt.domain.service.BabyDomainService;
import com.project.teamttt.domain.service.LogDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogService {
    private final BabyDomainService babyDomainService;
    private final LogDomainService logDomainService;
    private final BabyLogHistoryRepository babyLogHistoryRepository;

    @Transactional
    public ResponseDto<String> saveLogItem(Long memberId, LogRequestDto.RequestCreate requestCreate) {
        try {
            boolean containsEmptyString = requestCreate.getTodoNameList().stream().anyMatch(String::isEmpty);

            if (containsEmptyString) {
                return new ResponseDto<>(false, "TO DO NAME LIST contains null value", null);
            }
            Baby baby = babyDomainService.findByBabyId(requestCreate.getBabyId());
            if (memberId.equals(baby.getMember().getMemberId())) {

                BabyLog babyLog = logDomainService.save(createRequestCreateLog(baby));

                List<String> todoNameList = requestCreate.getTodoNameList();
                for (String todoNameOne : todoNameList) {
                    logDomainService.save(createRequestCreateLogItem(todoNameOne, babyLog));
                }

            } else {
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

    @Transactional
    public ResponseDto<String> saveLogHistory(List<LogHistoryDto.RequestCreateLogHistory> requestCreateLogHistory) {
        try {
            Long babyId = requestCreateLogHistory.get(0).getBabyId();
            Baby baby = babyDomainService.findByBabyId(babyId);

            Date logDate = requestCreateLogHistory.get(0).getLogDate();
            Long count = babyLogHistoryRepository.countByLogDate(logDate);
            if (count >= 7) {
                return new ResponseDto<>(false, "Failed to save log item: Maximum log items for the same date exceeded (7 or fewer items allowed)", null);
            }

            for (LogHistoryDto.RequestCreateLogHistory logHistory : requestCreateLogHistory) {
                logDomainService.save(logHistory, baby);
            }
            return new ResponseDto<>(true, "SUCCESS SAVE LOG ITEM", null);
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO SAVE LOG ITEM: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ResponseDto<String> updateLog(LogHistoryDto.UpdateLogHistory requestUpdateLogHistory) {
        try {
            Long BabyId = requestUpdateLogHistory.getBabyId();
            Baby baby = babyDomainService.findByBabyId(BabyId);

            logDomainService.save(requestUpdateLogHistory, baby);

            return new ResponseDto<>(true, "SUCCESS UPDATE LOGHISTORY", null);
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO UPDATE LOGHISTORY: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ResponseDto<String> deleteLog(Long BabyId, Date logDate) {
        try {

            Baby baby = babyDomainService.findByBabyId(BabyId);

          Long deleteCount = logDomainService.deleteByBabyIdAndLogDate(baby, logDate);
            if(deleteCount > 0 ){
                return new ResponseDto<>(true, "SUCCESS DELETE LOG HISTORY", null);
            } else {
                return new ResponseDto<>(false, "Failed to delete log history: No corresponding records found", null);
            }
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO DELETE LOG HISTORY: " + e.getMessage(), null);
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<Map<String, List<LogHistoryDto.ResponseBabyLogHistory>>> getLogHistoryList(Long babyId) {
        try {
            Baby baby = babyDomainService.findByBabyId(babyId);

            List<LogHistoryDto.ResponseBabyLogHistory> babyLogHistory = logDomainService.getBabyLogHistory(baby)
                    .stream()
                    .map(LogHistoryDto.ResponseBabyLogHistory::of)
                    .collect(Collectors.toList());

            Map<String, List<LogHistoryDto.ResponseBabyLogHistory>> responseList = new HashMap<>();
            Map<String, List<LogHistoryDto.ResponseBabyLogHistory>> groupedList = new HashMap<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = "";
            for (LogHistoryDto.ResponseBabyLogHistory log : babyLogHistory) {
                Date logDate = log.getLogDate();
                formattedDate = dateFormat.format(logDate);

                if (!groupedList.containsKey(formattedDate)) {
                    groupedList.put(formattedDate, new ArrayList<>());
                }
                groupedList.get(formattedDate).add(log);
            }

            responseList.putAll(groupedList);

            return new ResponseDto<>(true, "SUCCESS GET BABY LOG HISTORY LIST", responseList);
        } catch (Exception e) {
            return new ResponseDto<>(false, "FAILED TO GET BABY LOG HISTORY LIST: " + e.getMessage(), null);
        }
    }
}
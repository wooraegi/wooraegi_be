package com.project.teamttt.domain.service;

import com.project.teamttt.api.log.dto.LogHistoryDto;
import com.project.teamttt.api.log.dto.LogRequestDto;
import com.project.teamttt.domain.entity.*;
import com.project.teamttt.domain.repository.jpa.BabyLogHistoryRepository;
import com.project.teamttt.domain.repository.jpa.BabyLogItemRepository;
import com.project.teamttt.domain.repository.jpa.BabyLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogDomainService {
    private final BabyLogRepository babyLogRepository;
    private final BabyLogItemRepository babyLogItemRepository;
    private final BabyLogHistoryRepository babyLogHistoryRepository;

    public BabyLog save(LogRequestDto.RequestCreateLog requestCreateLog) {
        return babyLogRepository.save(
                BabyLog.builder()
                        .baby(requestCreateLog.getBaby())
                        .build()
        );
    }

    public BabyLogItem save(LogRequestDto.RequestCreateLogItem requestCreateLogItem) {
        return babyLogItemRepository.save(
                BabyLogItem.builder()
                        .babyLog(requestCreateLogItem.getBabyLog())
                        .todoName(requestCreateLogItem.getTodoName())
                        .build()
        );
    }

    public BabyLog getBabyLogByBabyId(Baby baby) {
        return babyLogRepository.findMostRecentByBaby(baby);
    }

    public List<BabyLogItem> getBabyLogItemLIst(BabyLog babyLog) {
        return babyLogItemRepository.findByBabyLog(babyLog);
    }

    public BabyLogHistory save(LogHistoryDto.RequestCreateLogHistory logHistory, Baby baby) {
        return babyLogHistoryRepository.save(
                BabyLogHistory.builder()
                        .todoName(logHistory.getTodoName())
                        .isChecked(logHistory.getIsChecked())
                        .logDate(logHistory.getLogDate())
                        .baby(baby)
                        .build()
        );
    }
    public BabyLogHistory save(LogHistoryDto.UpdateLogHistory requestUpdateLogHistory, Baby baby) {
        return babyLogHistoryRepository.save(
                BabyLogHistory.builder()
                        .todoName(requestUpdateLogHistory.getTodoName())
                        .isChecked(requestUpdateLogHistory.getIsChecked())
                        .logDate(requestUpdateLogHistory.getLogDate())
                        .baby(baby)
                        .babyLogHistoryId(requestUpdateLogHistory.getLogHistoryId())
                        .build()
        );
    }
    public Long deleteByBabyIdAndLogDate(Baby baby, Date logDate) {
        return babyLogHistoryRepository.deleteByBabyAndLogDate(baby, logDate);
    }
    public List<BabyLogHistory> getBabyLogHistory(Baby baby) {
        return babyLogHistoryRepository.findAllByBaby(baby);
    }
    public Long delete (Long babyLogHistoryId) {
        return babyLogHistoryRepository.deleteByBabyLogHistoryId(babyLogHistoryId);
    }

    public List<BabyLogHistory> findByLogDateAndBaby(Baby baby, Date logDate){ return babyLogHistoryRepository.findByBabyAndLogDate(baby, logDate);}
}

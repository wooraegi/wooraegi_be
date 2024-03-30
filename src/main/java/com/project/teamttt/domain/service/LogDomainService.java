package com.project.teamttt.domain.service;

import com.project.teamttt.api.log.dto.LogRequestDto;
import com.project.teamttt.domain.entity.Baby;
import com.project.teamttt.domain.entity.BabyLog;
import com.project.teamttt.domain.entity.BabyLogItem;
import com.project.teamttt.domain.repository.jpa.BabyLogItemRepository;
import com.project.teamttt.domain.repository.jpa.BabyLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogDomainService {
    private final BabyLogRepository babyLogRepository;
    private final BabyLogItemRepository babyLogItemRepository;

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
}

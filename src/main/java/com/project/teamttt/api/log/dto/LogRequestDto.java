package com.project.teamttt.api.log.dto;

import com.project.teamttt.domain.entity.Baby;
import com.project.teamttt.domain.entity.BabyLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

public class LogRequestDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class RequestCreate {

        /**
         * 베이비 id
         */
        private Long babyId;

        /**
         * 베이비 로그 id
         */
        private Long babyLogId;

        /**
         * 로그 투두 이름
         */
        private List<String> todoNameList;

    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class RequestCreateLog {

        /**
         * 베이비 id
         */
        private Baby baby;

    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class RequestCreateLogItem {

        /**
         * 베이비 로그 id
         */
        private BabyLog babyLog;

        /**
         * 로그 투두 이름
         */
        private String todoName;

    }


}

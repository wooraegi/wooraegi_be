package com.project.teamttt.api.log.dto;

import com.project.teamttt.api.diary.dto.DiaryRequestDto;
import com.project.teamttt.domain.entity.Baby;
import com.project.teamttt.domain.entity.BabyLogHistory;
import com.project.teamttt.domain.entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

public class LogHistoryDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class RequestCreateLogHistory {

        /**
         * 로그 투두 이름 (리스트로 변경)
         */
        private String todoName;

        /**
         * 로그 체크 여부
         */
        private Boolean isChecked;

        /**
         * 사용자가 지정한 로그 날짜
         */
        private Date logDate;

        /**
         * 반려동물 ID
         */
        private Long babyId;
    }
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class UpdateLogHistory {

        /**
         * 반려동물 ID
         */
        private Long babyId;
        /**
         * 히스토리 아이디
         */
        private Long logHistoryId;
        /**
         * 로그 투두 이름 (리스트로 변경)
         */
        private String todoName;

        /**
         * 로그 체크 여부
         */
        private Boolean isChecked;

        /**
         * 사용자가 지정한 로그 날짜
         */
        private Date logDate;
    }
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class ResponseBabyLogHistory {

        /**
         * 반려동물 ID
         */
        private Long babyId;
        /**
         * 히스토리 아이디
         */
        private Long logHistoryId;
        /**
         * 로그 투두 이름 (리스트로 변경)
         */
        private String todoName;

        /**
         * 로그 체크 여부
         */
        private Boolean isChecked;

        /**
         * 사용자가 지정한 로그 날짜
         */
        private Date logDate;

        public static LogHistoryDto.ResponseBabyLogHistory of(BabyLogHistory babyLogHistory) {
            return ResponseBabyLogHistory.builder()
                    .babyId(babyLogHistory.getBaby().getBabyId())
                    .logHistoryId(babyLogHistory.getBabyLogHistoryId())
                    .todoName(babyLogHistory.getTodoName())
                    .isChecked(babyLogHistory.getIsChecked())
                    .logDate(babyLogHistory.getLogDate())
                    .build();
        }
    }
}


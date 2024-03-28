package com.project.teamttt.api.daily.dto;

import com.project.teamttt.domain.entity.Member;
import jakarta.persistence.*;
import lombok.*;



public class DailyRequestDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor

    public static class DailyCreate {

        /**
         * 데일리 아이디
         */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long dailyId;

        /**
         * 데일리 제목
         */
        private String title;

        /**
         * 데일리 내용
         */
        private String content;

        /**
         * 데일리 공개 여부
         */
        private Boolean isPublic;

    }
}

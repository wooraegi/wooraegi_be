package com.project.teamttt.api.diary.dto;

import jakarta.persistence.*;
import lombok.*;



public class DiaryRequestDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor

    public static class DiaryCreate {

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

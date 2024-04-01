package com.project.teamttt.api.diary.dto;

import com.project.teamttt.domain.entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.List;


public class DiaryRequestDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class RequestCreate {


        /**
         * 해당 다이어리를 작성한 맴버 아이디
         */
        private Long memberId;

        /**
         * 다이어리 제목
         */
        private String title;

        /**
         * 다이어리 내용
         */
        private String content;

        /**
         * 다이어리 공개 여부
         */
        private Boolean isPublic;

        /**
         * 다이어리 생성일
         */
        private OffsetDateTime createdAt;


        public RequestCreate() {
            this.isPublic = true;

        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class RequestUpdate {


        /**
         * 다이어리 아이디
         */
        private Long diaryId;
        /**
         * 해당 다이어리를 작성한 맴버 아이디
         */
        private Long memberId;

        /**
         * 다이어리 제목
         */
        private String title;

        /**
         * 다이어리 내용
         */
        private String content;

        /**
         * 다이어리 공개 여부
         */
        private Boolean isPublic;

        /**
         * 다이어리 수정일
         */
        private OffsetDateTime updatedAt;


        public RequestUpdate() {
            this.isPublic = true;

        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class ResponseDiary {

        /**
         * 다이어리 아이디
         */
        private Long diaryId;
        /**
         * 해당 다이어리를 작성한 맴버 아이디
         */
        private Long memberId;

        /**
         * 다이어리 제목
         */
        private String title;

        /**
         * 다이어리 내용
         */
        private String content;

        /**
         * 다이어리 공개 여부
         */
        private Boolean isPublic;


        public static ResponseDiary of(Diary diary) {
            return ResponseDiary.builder()
                    .diaryId(diary.getDiaryId())
                    .title(diary.getTitle())
                    .content(diary.getContent())
                    .isPublic(diary.getIsPublic())
                    .memberId(diary.getMember().getMemberId())
                    .build();
        }
    }

        @Getter
        @Setter
        @Builder
        @AllArgsConstructor
        public static class RequestGet {
            /**
             * 다이어리 아이디
             */
            private Long diaryId;

            public static RequestGet of(Long diaryId) {
                return RequestGet.builder()
                        .diaryId(diaryId)
                        .build();
            }
        }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class ResponseDiaryDetail{

        /**
         * 다이어리 아이디
         */
        private Long diaryId;
        /**
         * 해당 다이어리를 작성한 맴버 아이디
         */
        private Long memberId;

        /**
         * 다이어리 제목
         */
        private String title;

        /**
         * 다이어리 내용
         */
        private String content;

        /**
         * 다이어리 공개 여부
         */
        private Boolean isPublic;

        /**
         * 다이어리 첨부파일 urlList
         */
        private List<String> fileUrlList;


        public static DiaryRequestDto.ResponseDiaryDetail of(Diary diary, List<String> fileUrlList) {
            return DiaryRequestDto.ResponseDiaryDetail.builder()
                    .diaryId(diary.getDiaryId())
                    .title(diary.getTitle())
                    .content(diary.getContent())
                    .isPublic(diary.getIsPublic())
                    .memberId(diary.getMember().getMemberId())
                    .fileUrlList(fileUrlList)
                    .build();
        }
    }
}
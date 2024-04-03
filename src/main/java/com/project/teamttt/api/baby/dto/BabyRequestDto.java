package com.project.teamttt.api.baby.dto;

import com.project.teamttt.domain.entity.Baby;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BabyRequestDto {

        /**
         * 반려동물 이름
         */
        private String babyName;

        /**
         * 생일
         */
        private String birth;

        /**
         * 성별
         */
        private String sex;

        /**
         * 반려동물 종류
         */
        private String animalType;

        /**
         * 별명
         */
        private String nickname;

        /**
         * 메모 사항
         */
        private String reminder;

        /**
         * 공개 여부
         */
        private Boolean isPublic;

        /**
         * 해당 반려동물을 등록한 멤버 아이디
         */
        private Long memberId;

        public BabyRequestDto() {
            this.isPublic = true;
        }


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class RequestUpdate {

        /**
         * 반려동물 아이디
         */
        private Long babyId;

        /**
         * 반려동물 이름
         */
        private String babyName;

        /**
         * 생일
         */
        private String birth;

        /**
         * 성별
         */
        private String sex;

        /**
         * 반려동물 종류
         */
        private String animalType;

        /**
         * 별명
         */
        private String nickname;

        /**
         * 메모 사항
         */
        private String reminder;

        /**
         * 공개 여부
         */
        private Boolean isPublic;

        /**
         * 반려동물 수정일
         */
        private OffsetDateTime updatedAt;

        /**
         * 해당 반려동물을 등록한 멤버 아이디
         */
        private Long memberId;

        public RequestUpdate() {
            this.isPublic = true;
        }

    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class ResponseBaby {

        /**
         * 반려동물 아이디
         */
        private Long babyId;

        /**
         * 반려동물 이름
         */
        private String babyName;

        /**
         * 생일
         */
        private String birth;

        /**
         * 성별
         */
        private String sex;

        /**
         * 반려동물 종류
         */
        private String animalType;

        /**
         * 별명
         */
        private String nickname;

        /**
         * 메모 사항
         */
        private String reminder;

        /**
         * 공개 여부
         */
        private Boolean isPublic;

        /**
         * 반려동물 프로필 사진 url
         */
        private String fileUrl;

        public static ResponseBaby of(Baby baby, String fileUrl) {
            return ResponseBaby.builder()
                    .babyId(baby.getBabyId())
                    .babyName(baby.getBabyName())
                    .birth(baby.getBirth())
                    .sex(baby.getSex())
                    .animalType(baby.getAnimalType())
                    .nickname(baby.getNickname())
                    .reminder(baby.getReminder())
                    .isPublic(baby.getIsPublic())
                    .fileUrl(fileUrl)
                    .build();
        }
    }
}

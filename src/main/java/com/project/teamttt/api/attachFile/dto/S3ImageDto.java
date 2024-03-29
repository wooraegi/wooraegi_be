package com.project.teamttt.api.attachFile.dto;

import com.project.teamttt.domain.entity.UserAttachFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public class S3ImageDto {
//    @Getter
//    @Setter
//    @Builder
//    @AllArgsConstructor
//    public static class RequestCreate {
//
//        /**
//         * 참조하는 id 종류 - baby_id, dialy_id, member_id
//         */
//        private String refId;
//
//        /**
//         * 사용되는 카테고리 종류 - MY_PROFILE, BABY_PROFILE, DIARY
//         */
//        private String refType;
//
//        /**
//         * 첨부파일 url
//         */
//        private String fileUrl;
//
//        /**
//         * 첨부파일 현재 사용 여부
//         */
//        private Boolean isUsed;
//
//        /**
//         * 해당 첨부파일을 등록한 멤버 아이디
//         */
//        private Long memberId;
//
//        public RequestCreate() {
//            this.isUsed = true;
//        }
//
//        public static S3ImageDto.RequestCreate of(UserAttachFile userAttachFile) {
//            return RequestCreate.builder()
//                    .refId(userAttachFile.getRefId())
//                    .refType(userAttachFile.getRefType())
//                    .fileUrl(userAttachFile.getFileUrl())
//                    .isUsed(userAttachFile.getIsUsed())
//                    .memberId(userAttachFile.getMember().getMemberId())
//                    .build();
//        }
//    }
}

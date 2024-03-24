package com.project.teamttt.api.member.dto;

import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.entity.Role;
import com.project.teamttt.api.util.RandomNickName;
import lombok.*;

import java.time.OffsetDateTime;

public class MemberRequestDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class RequestCreate {

        /**
         * 멤버 이메일
         */
        private String email;

        /**
         * 멤버 비밀번호
         */
        private String password;

        /**
         * 멤버 이름
         */
        private String nickname;

        /**
         * 소셜 = 'MEMBER'
         */
        private String social;

        /**
         * 권한
         */
        private Role role;

        /**
         * 멤버 생성일
         */
        private OffsetDateTime createdAt;

        public RequestCreate() {
            this.role = Role.ROLE_USER;
            this.nickname = RandomNickName.generateRandomNickname();
            this.social = "MEMBER";
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ResponseMember {

        /**
         * 멤버 아이디
         */
        private Long memberId;

        /**
         * 멤버 이름
         */
        private String nickname;

        /**
         * jwt 토큰
         */
        private String token;

        public static ResponseMember of(Member member, String token) {
            return ResponseMember.builder()
                    .memberId(member.getMemberId())
                    .nickname(member.getNickname())
                    .token(token)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class AuthenticationRequest {

        /**
         * 멤버 이메일
         */
        private String email;

        /**
         * 멤버 비밀번호
         */
        private String password;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class AuthenticationResponse {

        /**
         * 토큰
         */
        private String token;

        /**
         * 멤버 정보
         */
        private Member member;

        public static AuthenticationResponse of(Member member, String token) {
            return AuthenticationResponse.builder()
                    .member(member)
                    .token(token)
                    .build();
        }
    }
}

package com.project.teamttt.api.util;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
public class AuthTokenDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ResponseAuthToken{

        /**
         * 엑세스 토큰
         */
        private String access_token;

        /**
         * 엑세스 토큰의 남은 수명
         */
        private String expires_in;

        /**
         * 리스레쉬 토큰
         */
        private String refresh_token;

        /**
         * 토큰에 부여된 권한 범위
         */
        private String scope;

        /**
         * 반환된 토큰 유형(Bearer 고정)
         */
        private String token_type;

        /**
         * jwt 토큰
         */
        private String id_token;


        public static AuthTokenDto.ResponseAuthToken of(Map<String, String> authTokenMap) {
            return ResponseAuthToken.builder()
                    .access_token(authTokenMap.get("access_token"))
                    .id_token(authTokenMap.get("jwt_token"))
                    .build();
        }
    }
}
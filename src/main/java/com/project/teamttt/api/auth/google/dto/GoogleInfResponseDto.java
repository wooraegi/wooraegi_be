package com.project.teamttt.api.auth.google.dto;


import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GoogleInfResponseDto{

    /**
     * 멤버 로그인 종류
     */
    private String social;

    /**
     * 멤버 이메일
     */
    private String email;

}
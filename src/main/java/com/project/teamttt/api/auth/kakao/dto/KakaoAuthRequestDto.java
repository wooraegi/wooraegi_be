package com.project.teamttt.api.auth.kakao.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KakaoAuthRequestDto {

    private String email;
    private String nickname;
}

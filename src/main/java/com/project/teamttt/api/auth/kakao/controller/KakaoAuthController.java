package com.project.teamttt.api.auth.kakao.controller;

import com.project.teamttt.api.auth.kakao.dto.KakaoAuthRequestDto;
import com.project.teamttt.api.auth.kakao.service.KakaoAuthService;
import com.project.teamttt.endpoint.KakaoEndPoint;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;

    @GetMapping("/login/oauth2/code/kakao")
    public KakaoAuthRequestDto callback(HttpServletRequest request) throws Exception {
        return kakaoAuthService.getKakaoInfo(request.getParameter("code"));

    }
}
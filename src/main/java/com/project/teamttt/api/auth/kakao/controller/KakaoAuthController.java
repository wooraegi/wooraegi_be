package com.project.teamttt.api.auth.kakao.controller;

import com.project.teamttt.api.auth.kakao.service.KakaoAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.teamttt.endpoint.AuthEndPoint.KAKAO_USER_CREATE;


@RestController
@RequiredArgsConstructor
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;

    @GetMapping(KAKAO_USER_CREATE)
    public String callback(HttpServletRequest request) throws Exception {
       return kakaoAuthService.getKakaoInfo(request.getParameter("code"));
    }
}
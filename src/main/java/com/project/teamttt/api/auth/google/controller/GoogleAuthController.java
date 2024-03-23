package com.project.teamttt.api.auth.google.controller;

import com.project.teamttt.api.auth.google.service.GoogleAuthService;
import com.project.teamttt.endpoint.GoogleEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class GoogleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService;

    @PostMapping(GoogleEndPoint.GOOGLE_ROOT)
    public String loginUrlGoogle() {
        return googleAuthService.getLoginUrl();
        // 로그인 URL 반환
    }

    @GetMapping(GoogleEndPoint.GOOGLE_USER_CREATE)
    public String loginGoogle(@RequestParam(value = "code") String authCode) {
        return googleAuthService.loginGoogle(authCode);
        // 로그인 처리
    }

}

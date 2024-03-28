package com.project.teamttt.api.auth.google.controller;

import com.project.teamttt.api.auth.google.service.GoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static com.project.teamttt.endpoint.AuthEndPoint.GOOGLE_ROOT;
import static com.project.teamttt.endpoint.AuthEndPoint.GOOGLE_USER_CREATE;

@RestController
@CrossOrigin("*")
public class GoogleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService;

    @PostMapping(GOOGLE_ROOT)
    public String loginUrlGoogle() {
        return googleAuthService.getLoginUrl();
        // 로그인 URL 반환
    }

    @GetMapping(GOOGLE_USER_CREATE)
    public String loginGoogle(@RequestParam(value = "code") String authCode) {
        return googleAuthService.loginGoogle(authCode);
    }

}

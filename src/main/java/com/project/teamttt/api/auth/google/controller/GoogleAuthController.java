package com.project.teamttt.api.auth.google.controller;

import com.project.teamttt.api.auth.google.service.GoogleAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

import static com.project.teamttt.endpoint.AuthEndPoint.*;

@RestController
@CrossOrigin("*")
@Tag(name = "GOOGLE API", description = "구글 로그인 관련 api입니다.")
public class GoogleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService;
    /**
     *
     * @return redirect URI
     */
    @Operation(summary = "구글 uri", description = "구글 소셜 로그인 uri를 생성하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "uri 생성에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "uri 생성에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping(GOOGLE_CREATE_URI)
    public String loginUrlGoogle() {
        return googleAuthService.getLoginUrl();
        // 로그인 URL 반환
    }
    /**
     *
     * @param authCode
     * @return String
     */
    @Operation(summary = "구글 로그인",
            description = "구글 소셜 로그인 API" +
                    "\n### * 구글 로그인 요청 순서" +
                    "\n 1. 구글 uri 생성 api 먼저 진행\n" +
                    "\n 2. 응답받은 uri를 주소창에 입력\n" +
                    "\n 3. 구글 로그인 진행\n" +
                    "\n 4. 응답받은 토큰값을 스웨거 상단 Authorize에 입력\n" +
                    "\n 5. 로그인 성공\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구글 로그인에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "구글 로그인에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping(GOOGLE_CALLBACK)
    public String loginGoogle(@RequestParam(value = "code") String authCode) {
        return googleAuthService.loginGoogle(authCode);
    }

}

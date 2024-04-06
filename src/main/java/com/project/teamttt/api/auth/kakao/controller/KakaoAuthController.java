package com.project.teamttt.api.auth.kakao.controller;

import com.project.teamttt.api.auth.kakao.service.KakaoAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.teamttt.endpoint.AuthEndPoint.KAKAO_CALLBACK;

@RestController
@RequiredArgsConstructor
@Tag(name = "2.KAKAO API", description = "카카오 로그인 관련 api입니다.")
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;

    /**
     *
     * @param request
     * @return String
     */
    @Operation(summary = "카카오 로그인",
            description = "카카오 소셜 로그인 API" +
                    "\n### * 카카오 로그인 요청 순서" +
                    "\n 1. 주소창에 https://kauth.kakao.com/oauth/authorize?client_id=65df1cfa1ecf4e16aaa4809b8f8e4770&redirect_uri=http://localhost:8080/login/oauth2/code/kakao&response_type=code&state= 입력\n" +
                    "\n 2. 카카오 로그인 진행\n" +
                    "\n 3. 응답받은 토큰값을 스웨거 상단 Authorize에 입력\n" +
                    "\n 4. 로그인 성공\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카카오 로그인에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "카카오 로그인에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping(KAKAO_CALLBACK)
    public String callback(HttpServletRequest request) throws Exception {
       return kakaoAuthService.getKakaoInfo(request.getParameter("code"));
    }
}
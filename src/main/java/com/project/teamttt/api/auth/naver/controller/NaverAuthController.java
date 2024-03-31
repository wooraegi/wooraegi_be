package com.project.teamttt.api.auth.naver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.teamttt.api.auth.naver.service.NaverAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

import static com.project.teamttt.endpoint.AuthEndPoint.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "NAVER API", description = "네이버 로그인 관련 api입니다.")
public class NaverAuthController {

    private final NaverAuthService naverAuthService;

    /**
     *
     * @return redirect URI
     * @throws UnsupportedEncodingException
     */
    @Operation(summary = "네이버 uri", description = "네이버 소셜 로그인 uri를 생성하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "uri 생성에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "uri 생성에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping(NAVER_CREATE_URI)
    public ResponseEntity<?> createNaverUri() throws UnsupportedEncodingException {
        try{
            String url = naverAuthService.createNaverURL();

            return new ResponseEntity<>(url, HttpStatus.OK);
        }catch (UnsupportedEncodingException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param code, state
     * @return ResponseEntity<String>
     */
    @Operation(summary = "네이버 로그인",
            description = "네이버 소셜 로그인 API" +
                    "\n### * 네이버 로그인 요청 순서" +
                    "\n 1. 네이버 uri 생성 api 먼저 진행\n" +
                    "\n 2. 응답받은 uri를 주소창에 입력\n" +
                    "\n 3. 네이버 로그인 진행\n" +
                    "\n 4. 응답받은 토큰값을 스웨거 상단 Authorize에 입력\n" +
                    "\n 5. 로그인 성공\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "네이버 로그인에 성공했습니다.", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "네이버 로그인에 실패했습니다.", content = @Content(mediaType = "text/plain"))
    })
    @GetMapping(NAVER_CALLBACK)
    public ResponseEntity<String> loginNaver(@RequestParam("code") String code, @RequestParam("state") String state) throws JsonProcessingException {
        String token = naverAuthService.loginNaver(code, state);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILED NAVER CALLBACK");
        } else {
            return ResponseEntity.ok().body(token);
        }
    }


}

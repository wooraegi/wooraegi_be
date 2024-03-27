package com.project.teamttt.api.auth.naver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.teamttt.api.auth.naver.service.NaverAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

import static com.project.teamttt.endpoint.AuthEndPoint.NAVER_CALLBACK;
import static com.project.teamttt.endpoint.AuthEndPoint.NAVER_CREATE;


@RestController
@RequiredArgsConstructor
public class NaverAuthController {

    private final NaverAuthService naverAuthService;

    /**
     *
     * @return redirect URI
     * @throws UnsupportedEncodingException
     */
    @GetMapping(NAVER_CREATE)
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

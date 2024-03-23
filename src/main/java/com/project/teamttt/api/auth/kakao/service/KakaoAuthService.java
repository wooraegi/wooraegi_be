package com.project.teamttt.api.auth.kakao.service;

import com.project.teamttt.api.auth.kakao.dto.KakaoAuthRequestDto;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoAuthService {

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;

    @Value("${kakao.redirect.url}")
    private String kakaoRedirectUrl;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
    private final static String KAKAO_API_URI = "https://kapi.kakao.com";

    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/oauth/authorize"
                + "?client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUrl
                + "&response_type=code";
    }

    public KakaoAuthRequestDto getKakaoInfo(String code) throws Exception {
        if (code == null) throw new Exception("Failed to get authorization code");

        String accessToken = "";
        String refreshToken = "";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", kakaoClientId);
            params.add("client_secret", kakaoClientSecret);
            params.add("code", code);
            params.add("redirect_uri", kakaoRedirectUrl);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = new RestTemplate().postForEntity(
                    KAKAO_AUTH_URI + "/oauth/token",
                    request,
                    String.class
            );

            if (response.getBody() != null) {
                JSONObject jsonObj = new JSONObject(response.getBody());

                accessToken = jsonObj.getString("access_token");
                refreshToken = jsonObj.getString("refresh_token");
            } else {
                throw new Exception("API call returned null response body");
            }
        } catch (Exception e) {
            throw new Exception("API call failed", e);
        }

        return getUserInfoWithToken(accessToken);
    }

    private KakaoAuthRequestDto getUserInfoWithToken(String accessToken) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = new RestTemplate().exchange(
                KAKAO_API_URI + "/v2/user/me",
                HttpMethod.POST,
                request,
                String.class
        );

        if (response.getBody() != null) {
            JSONObject jsonObj = new JSONObject(response.getBody());
            JSONObject account = jsonObj.getJSONObject("kakao_account");
            JSONObject profile = account.getJSONObject("profile");

            long id = jsonObj.getLong("id");
            String email = account.getString("email");
            String nickname = profile.getString("nickname");

            return KakaoAuthRequestDto.builder()
                    .email(email)
                    .nickname(nickname)
                    .build();
        } else {
            throw new Exception("API call returned null response body");
        }
    }
}

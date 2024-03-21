package com.project.teamttt.api.auth.google.service;

import com.project.teamttt.api.auth.google.dto.GoogleInfResponseDto;
import com.project.teamttt.api.auth.google.dto.GoogleRequestDto;
import com.project.teamttt.api.auth.google.dto.GoogleResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleAuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.client.secret}")
    private String googleClienSecret;

    @Value("${google.provider.resource-url}")
    private String googleResourceUrl;

    public String getLoginUrl() {
        String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
                + "&redirect_uri=http://localhost:8080/google/oauth2/callback&response_type=code&scope=email%20profile%20openid&access_type=offline";
        return reqUrl;
    }

    public String loginGoogle(String authCode) {
        GoogleRequestDto googleOAuthRequestParam = GoogleRequestDto
                .builder()
                .clientId(googleClientId)
                .clientSecret(googleClienSecret)
                .code(authCode)
                .redirectUri("http://localhost:8080/google/oauth2/callback")
                .grantType("authorization_code").build();
        ResponseEntity<GoogleResponseDto> resultEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                googleOAuthRequestParam, GoogleResponseDto.class);
        String jwtToken = resultEntity.getBody().getId_token();
        Map<String, String> map = new HashMap<>();
        map.put("id_token", jwtToken);
        ResponseEntity<GoogleInfResponseDto> resultEntity2 = restTemplate.postForEntity(googleResourceUrl,
                map, GoogleInfResponseDto.class);
        String email = resultEntity2.getBody().getEmail();
        return email;
    }
}

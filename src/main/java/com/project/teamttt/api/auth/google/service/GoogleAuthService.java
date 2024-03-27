package com.project.teamttt.api.auth.google.service;

import com.project.teamttt.api.auth.google.dto.GoogleInfResponseDto;
import com.project.teamttt.api.auth.google.dto.GoogleRequestDto;
import com.project.teamttt.api.util.AuthTokenDto;
import com.project.teamttt.api.util.RandomNickName;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.repository.jpa.MemberRepository;
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
    private MemberRepository memberRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")

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
                .clientSecret(googleClientSecret)
                .code(authCode)
                .redirectUri("http://localhost:8080/google/oauth2/callback")
                .grantType("authorization_code").build();
        ResponseEntity<AuthTokenDto.ResponseAuthToken> resultEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                googleOAuthRequestParam, AuthTokenDto.ResponseAuthToken.class);
        String jwtToken = resultEntity.getBody().getJwt_token();
        Map<String, String> map = new HashMap<>();
        map.put("id_token", jwtToken);
        ResponseEntity<GoogleInfResponseDto> resultEntity2 = restTemplate.postForEntity(googleResourceUrl,
                map, GoogleInfResponseDto.class);
        String email = resultEntity2.getBody().getEmail();
        String social = "GOOGLE";
        String randomNickname = RandomNickName.generateRandomNickname();

        Member member = new Member();
        member.setEmail(email);
        member.setSocial(social);
        member.setNickname(randomNickname); // 랜덤 닉네임 설정
        memberRepository.save(member);

        return "login success";
    }
}

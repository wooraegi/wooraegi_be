package com.project.teamttt.api.auth.google.service;

import com.project.teamttt.api.auth.google.dto.GoogleInfResponseDto;
import com.project.teamttt.api.auth.google.dto.GoogleRequestDto;
import com.project.teamttt.api.member.dto.MemberRequestDto;
import com.project.teamttt.api.util.AuthTokenDto;
import com.project.teamttt.config.JwtConfig;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.repository.jpa.MemberRepository;
import com.project.teamttt.domain.service.MemberDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class GoogleAuthService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MemberDomainService memberDomainService;

    @Autowired
    private JwtConfig jwtConfig;

    @Value("${spring.security.oauth2.client.registration.google.client.id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client.secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.client.redirect-uri}")
    private String googleRedirectUri;

    @Value("${spring.security.oauth2.client.registration.google.provider.token_uri}")
    private String googleTokenUri;

    @Value("${spring.security.oauth2.client.registration.google.provider.resource_uri}")
    private String googleResourceUri;


    public String getLoginUrl() {
        String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
                + "&redirect_uri=" + googleRedirectUri + "&response_type=code&scope=email%20profile%20openid&access_type=offline";
        return reqUrl;
    }

    public String loginGoogle(String authCode) {
        GoogleRequestDto googleOAuthRequestParam = GoogleRequestDto
                .builder()
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .code(authCode)
                .redirectUri(googleRedirectUri)
                .grantType("authorization_code").build();
        ResponseEntity<AuthTokenDto.ResponseAuthToken> resultEntity = restTemplate.postForEntity(googleTokenUri,
                googleOAuthRequestParam, AuthTokenDto.ResponseAuthToken.class);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("access_token", resultEntity.getBody().getAccess_token());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + resultEntity.getBody().getAccess_token());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<GoogleInfResponseDto> resultEntity2 = restTemplate.postForEntity(googleResourceUri, requestEntity, GoogleInfResponseDto.class);

        Member memberDetail;
        Optional<Member> existingMemberOptional = memberDomainService.findByEmail(resultEntity2.getBody().getEmail());
        if (existingMemberOptional.isPresent()) {

            memberDetail = existingMemberOptional.get();

        } else {
            MemberRequestDto.RequestCreateMember requestCreate = new MemberRequestDto.RequestCreateMember();
            requestCreate.setEmail(resultEntity2.getBody().getEmail());
            requestCreate.setSocial("GOOGLE");

            memberDetail = memberDomainService.save(requestCreate);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetail.getEmail(), memberDetail.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtConfig.generateToken(memberDetail, Duration.ofHours(2));
        String refreshToken = jwtConfig.createRefreshToken(memberDetail);

        return accessToken;
    }

}

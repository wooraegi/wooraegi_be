package com.project.teamttt.api.auth.naver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.teamttt.api.member.dto.MemberRequestDto;
import com.project.teamttt.api.util.AuthTokenDto;
import com.project.teamttt.config.JwtConfig;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.service.MemberDomainService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NaverAuthService {
    private final JwtConfig jwtConfig;
    private final MemberDomainService memberDomainService;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;
    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String naverRedirectUri;
    public String createNaverURL () throws UnsupportedEncodingException {
        StringBuffer url = new StringBuffer();

        //String redirectURI = URLEncoder.encode(naverRedirectUri, "UTF-8");
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString();

        url.append("https://nid.naver.com/oauth2.0/authorize?response_type=code");
        url.append("&client_id=" + naverClientId);
        url.append("&state=" + state);
        url.append("&redirect_uri=" + naverRedirectUri);
        url.append("&client_secret=" + naverClientSecret);

        return url.toString();
    }

    public String loginNaver (String code, String state) throws JsonProcessingException {
        RestTemplate token_rt = new RestTemplate();

        HttpHeaders naverTokenRequestHeadres = new HttpHeaders();
        naverTokenRequestHeadres.add("Content-type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverClientId);
        params.add("client_secret", naverClientSecret);
        params.add("code", code);
        params.add("state", state);

        HttpEntity<MultiValueMap<String, String>> naverTokenRequest =
                new HttpEntity<>(params, naverTokenRequestHeadres);

        ResponseEntity<String> oauthTokenResponse = token_rt.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                naverTokenRequest,
                String.class
        );
        String responseBody = oauthTokenResponse.getBody();
        ObjectMapper responseMapper = new ObjectMapper();
        Map<String, String> authTokenMap = responseMapper.readValue(responseBody, new TypeReference<Map<String,String>>(){});

        AuthTokenDto.ResponseAuthToken.of(authTokenMap);


        RestTemplate profile_rt = new RestTemplate();
        HttpHeaders userDetailReqHeaders = new HttpHeaders();
        userDetailReqHeaders.add("Authorization", "Bearer " + authTokenMap.get("access_token"));
        userDetailReqHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
        HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(userDetailReqHeaders);

        ResponseEntity<String> userDetailResponse = profile_rt.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                naverProfileRequest,
                String.class
        );
        String responseUserDetail= userDetailResponse.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseUserDetail);
        String email = jsonNode.get("response").get("email").asText();

        Member memberDetail;

        Optional<Member> existingMemberOptional = memberDomainService.findByEmail(email);
        if (existingMemberOptional.isPresent()) {

            memberDetail = existingMemberOptional.get();

        } else {
            MemberRequestDto.RequestCreateMember requestCreate = new MemberRequestDto.RequestCreateMember();
            requestCreate.setEmail(email);
            requestCreate.setSocial("NAVER");

            memberDetail = memberDomainService.save(requestCreate);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetail.getEmail(), memberDetail.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtConfig.generateToken(memberDetail, Duration.ofHours(2));
        String refreshToken = jwtConfig.createRefreshToken(memberDetail);

        return accessToken;
    }
}

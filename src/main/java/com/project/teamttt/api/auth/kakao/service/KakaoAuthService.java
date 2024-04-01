package com.project.teamttt.api.auth.kakao.service;

import com.project.teamttt.api.auth.kakao.dto.KakaoAuthRequestDto;
import com.project.teamttt.api.member.dto.MemberRequestDto;
import com.project.teamttt.config.JwtConfig;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.api.util.RandomNickName;
import com.project.teamttt.domain.service.MemberDomainService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUrl;

    @Autowired
    private MemberDomainService memberDomainService;
    @Autowired
    private JwtConfig jwtConfig;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
    private final static String KAKAO_API_URI = "https://kapi.kakao.com";

    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/oauth/authorize"
                + "?client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUrl
                + "&response_type=code";
    }

    public String getKakaoInfo(String code) throws Exception {
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
                    KAKAO_AUTH_URI + "/oauth/token", request,
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

    public String getUserInfoWithToken(String accessToken) throws Exception {
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

            String email = account.getString("email");
            String nickname = profile.getString("nickname");

            if (nickname == null || nickname.isEmpty()) {
                nickname = RandomNickName.generateRandomNickname();
            }


            KakaoAuthRequestDto kakaoAuthRequestDto = KakaoAuthRequestDto.builder()
                    .email(email)
                    .nickname(nickname)
                    .build();

            return saveMemberFromKakaoInfo(kakaoAuthRequestDto);
        } else {
            throw new Exception("API call returned null response body");
        }
    }

    @Transactional
    public String saveMemberFromKakaoInfo(KakaoAuthRequestDto kakaoAuthRequestDto) throws Exception {
        String email = kakaoAuthRequestDto.getEmail();

        Member memberDetail;
        Optional<Member> existingMemberOptional = memberDomainService.findByEmail(email);
        if (existingMemberOptional.isPresent()) {

            memberDetail = existingMemberOptional.get();

        } else {
            MemberRequestDto.RequestCreateMember requestCreate = new MemberRequestDto.RequestCreateMember();
            requestCreate.setEmail(email);
            requestCreate.setSocial("KAKAO");

            memberDetail = memberDomainService.save(requestCreate);

        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetail.getEmail(), memberDetail.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtConfig.generateToken(memberDetail, Duration.ofHours(2));
        String refreshToken = jwtConfig.createRefreshToken(memberDetail);

        return accessToken;
    }
}
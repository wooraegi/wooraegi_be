package com.project.teamttt.api.auth.google.service;

import com.project.teamttt.api.auth.google.dto.GoogleInfResponseDto;
import com.project.teamttt.api.auth.google.dto.GoogleRequestDto;
import com.project.teamttt.api.auth.google.dto.GoogleResponseDto;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.repository.jpa.MemberRepository;
import com.project.teamttt.util.RandomNickName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@Service
public class GoogleAuthService extends DefaultOAuth2UserService {

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
        ResponseEntity<GoogleResponseDto> resultEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                googleOAuthRequestParam, GoogleResponseDto.class);
        String jwtToken = resultEntity.getBody().getId_token();
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

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User = " + oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if (registrationId.equals("google")) {
            GoogleInfResponseDto oAuth2Response = new GoogleInfResponseDto(oAuth2User.getAttributes());
            OAuth2User defaultOAuth2User = new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), // 권한 설정
                    oAuth2User.getAttributes(), // 속성
                    "sub" // 기본 ID 속성
            );
            return defaultOAuth2User;
        }

        return oAuth2User;
    }
}

package com.project.teamttt.api.member.service;

import com.project.teamttt.config.JwtConfig;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final JwtConfig jwtConfig;
    private final RefreshTokenService refreshTokenService;
    private final MemberDomainService memberDomainService;

    public String createNewAccessToken(String refreshToken) {
        if(!jwtConfig.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long memberId = refreshTokenService.findByRefreshToken(refreshToken).getMemberId();
        Member member = memberDomainService.findByMemberId(memberId);

        return jwtConfig.generateToken(member, Duration.ofHours(2));
    }
}

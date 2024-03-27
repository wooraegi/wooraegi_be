package com.project.teamttt.api.member.service;

import com.project.teamttt.domain.entity.RefreshToken;
import com.project.teamttt.domain.repository.jpa.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }

    public RefreshToken saveRefreshToken(Long memberId, String refreshToken) {
        RefreshToken newRefreshToken = new RefreshToken(memberId, refreshToken);
        return refreshTokenRepository.save(newRefreshToken);
    }

    public RefreshToken updateRefreshToken(RefreshToken refreshToken, String newRefreshToken) {
        return refreshTokenRepository.save(refreshToken.update(newRefreshToken));
    }
}

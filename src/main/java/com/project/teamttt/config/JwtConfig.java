package com.project.teamttt.config;


import com.project.teamttt.api.member.service.RefreshTokenService;
import com.project.teamttt.domain.entity.Member;
import com.project.teamttt.domain.service.MemberDomainService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.time.Duration;

@Component
@RequiredArgsConstructor
@Configuration
public class JwtConfig {
    private final Logger logger = LoggerFactory.getLogger(JwtConfig.class);
    public final String AUTHORIZATION_HEADER = "Authorization";
    public final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret_key}")
    private String jwtSecretKey;
    private final RefreshTokenService refreshTokenService;
    private final MemberDomainService memberDomainService;

    private Key getSignKey(String secretKey){
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), member);
    }

    private String makeToken(Date expiry, Member member) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim("email", Base64.getEncoder().encodeToString(member.getEmail().getBytes()))
                .claim("id", member.getMemberId())
                .signWith(getSignKey(jwtSecretKey),SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        String sub = getClaims(token).getSubject();

        Member member = memberDomainService.loadUserByUsername(sub);
        return new UsernamePasswordAuthenticationToken(
                member, token, member.getAuthorities());
    }


    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String createNewAccessToken(String refreshToken) {
        if(!this.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long memberId = refreshTokenService.findByRefreshToken(refreshToken).getMemberId();
        Member member = memberDomainService.findByMemberId(memberId);

        return this.generateToken(member, Duration.ofHours(2));
    }

    public String createRefreshToken(Member member) {
        return this.generateToken(member, Duration.ofDays(30));
    }
}



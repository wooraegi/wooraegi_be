package com.project.teamttt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.teamttt.api.member.dto.MemberRequestDto;
import com.project.teamttt.domain.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.Duration;

import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtConfig jwtConfig;

    public JwtAuthenticationFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!request.getMethod().equals("POST")) {
                throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            }
            MemberRequestDto.AuthenticationRequest requestDto = new ObjectMapper().readValue(request.getInputStream(), MemberRequestDto.AuthenticationRequest.class);


            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.authenticated(
                    requestDto.getEmail(),
                    requestDto.getPassword(),
                    null
            );
            setDetails(request, authRequest);

            return getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            logger.error("IOException occurred during authentication attempt: {}");
            throw new RuntimeException("Error occurred during authentication attempt: " + e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {
        Member member = ((Member) authResult.getPrincipal());

        String token = jwtConfig.generateToken(member, Duration.ofMinutes(30));
        response.addHeader(jwtConfig.AUTHORIZATION_HEADER, token);

        chain.doFilter(request, response);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}
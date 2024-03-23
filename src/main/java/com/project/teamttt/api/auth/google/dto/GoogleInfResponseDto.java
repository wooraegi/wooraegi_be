package com.project.teamttt.api.auth.google.dto;

import com.project.teamttt.api.auth.OAuth2ResponseDto;
import com.project.teamttt.api.member.dto.MemberRequestDto;
import com.project.teamttt.domain.entity.Member;
import lombok.*;

import java.util.Map;

@Data
public class GoogleInfResponseDto implements OAuth2ResponseDto{
    private final Map<String, Object> attribute;
    public GoogleInfResponseDto(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    private String social;

    @Override
    public String getProvider() {
        return null;
    }

    @Override
    public String getProviderId() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
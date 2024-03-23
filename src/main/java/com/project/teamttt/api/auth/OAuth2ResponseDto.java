package com.project.teamttt.api.auth;

public interface OAuth2ResponseDto {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
}

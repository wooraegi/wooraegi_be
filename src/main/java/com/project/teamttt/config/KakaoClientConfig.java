package com.project.teamttt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kakao.client")
public class KakaoClientConfig {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}

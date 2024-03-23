package com.project.teamttt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.client.provider.google.provider")
public class GoogleProviderConfig {
    private String loginUrl;
    private String authUrl;
    private String tokenUrl;
    private String resourceUri;
}

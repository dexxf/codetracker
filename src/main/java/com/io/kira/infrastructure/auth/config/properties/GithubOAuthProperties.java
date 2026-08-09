package com.io.kira.infrastructure.auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "github")
public record GithubOAuthProperties(
        String authorizationUrl,
        String clientId,
        String clientSecret,
        String redirectUri,
        String scope,
        boolean allowSignup,
        boolean promptConsent
) {
}
package com.io.codetracker.infrastructure.auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.cookie.jwt")
public record JwtCookieProperties(
        boolean secure,
        boolean httpOnly,
        String domain,
        String path,
        String sameSite
) {
}
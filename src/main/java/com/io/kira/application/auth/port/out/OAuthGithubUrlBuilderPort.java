package com.io.kira.application.auth.port.out;

public interface OAuthGithubUrlBuilderPort {
    String buildUrl(String state);
}

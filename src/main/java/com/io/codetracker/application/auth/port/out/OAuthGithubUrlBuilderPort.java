package com.io.codetracker.application.auth.port.out;

public interface OAuthGithubUrlBuilderPort {
    String buildUrl(String state);
}

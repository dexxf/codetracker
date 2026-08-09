package com.io.kira.adapter.auth.out.service;

import com.io.kira.application.auth.port.out.OAuthGithubUrlBuilderPort;
import com.io.kira.infrastructure.auth.config.properties.GithubOAuthProperties;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class OAuthGithubUrlBuilderService implements OAuthGithubUrlBuilderPort {


    private final GithubOAuthProperties githubOAuthProperties;

    public OAuthGithubUrlBuilderService(GithubOAuthProperties githubOAuthProperties) {
        this.githubOAuthProperties = githubOAuthProperties;
    }

    @Override
    public String buildUrl(String state) {
        try {
            URI authUrl = new URIBuilder(githubOAuthProperties.authorizationUrl())
                    .addParameter("client_id", githubOAuthProperties.clientId())
                    .addParameter("redirect_uri", githubOAuthProperties.redirectUri())
                    .addParameter("scope", githubOAuthProperties.scope())
                    .addParameter("state", state)
                    .addParameter("allow_signup", String.valueOf(githubOAuthProperties.allowSignup()))
                    .addParameter("prompt", githubOAuthProperties.promptConsent() ? "consent" : "none")
                    .build();

            return authUrl.toString();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Failed to build GitHub OAuth authorize URL", e);
        }
    }
}
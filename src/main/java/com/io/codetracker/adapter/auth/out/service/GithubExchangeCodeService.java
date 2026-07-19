package com.io.codetracker.adapter.auth.out.service;

import com.io.codetracker.adapter.auth.out.dto.response.GithubCodeExchangeResponse;
import com.io.codetracker.application.auth.error.GithubExchangeCodeError;
import com.io.codetracker.application.auth.port.out.GithubExchangeCodePort;
import com.io.codetracker.application.auth.result.GithubExchangeCodeResult;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.infrastructure.auth.config.properties.GithubOAuthProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
public final class GithubExchangeCodeService implements GithubExchangeCodePort {

    private final RestClient restClient;
    private final GithubOAuthProperties githubOAuthProperties;

    public GithubExchangeCodeService(RestClient restClient, GithubOAuthProperties githubOAuthProperties) {
        this.restClient = restClient;
        this.githubOAuthProperties = githubOAuthProperties;
    }

    @Override
    public Result<GithubExchangeCodeResult, GithubExchangeCodeError> exchange(String code) {
        if (code == null || code.isBlank()) {
            return Result.fail(GithubExchangeCodeError.MISSING_CODE);
        }

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", githubOAuthProperties.clientId());
        form.add("client_secret", githubOAuthProperties.clientSecret());
        form.add("code", code);
        form.add("redirect_uri", githubOAuthProperties.redirectUri());

        GithubCodeExchangeResponse tokenResponse;
        try {
            tokenResponse = restClient.post()
                    .uri("https://github.com/login/oauth/access_token")
                    .headers(headers -> {
                        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
                    })
                    .body(form)
                    .retrieve()
                    .body(GithubCodeExchangeResponse.class);
        } catch (RestClientException e) {
            return Result.fail(GithubExchangeCodeError.TOKEN_EXCHANGE_FAILED);
        }

        if (tokenResponse == null) {
            return Result.fail(GithubExchangeCodeError.TOKEN_EXCHANGE_FAILED);
        }

        if (tokenResponse.access_token() == null || tokenResponse.access_token().isBlank()) {
            return Result.fail(GithubExchangeCodeError.NO_ACCESS_TOKEN_RECEIVED);
        }

        GithubExchangeCodeResult result = new GithubExchangeCodeResult(
                tokenResponse.access_token(),
                tokenResponse.token_type(),
                tokenResponse.scope()
        );

        return Result.ok(result);
    }
}
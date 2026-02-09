package com.io.codetracker.adapter.auth.out.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubTokenResult(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("token_type") String tokenType,
    @JsonProperty("scope") String scope
) {}

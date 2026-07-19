package com.io.codetracker.adapter.auth.out.dto.response;

public record GithubCodeExchangeResponse(
        String access_token,
        String token_type,
        String scope,
        String error,
        String error_description
) {
}
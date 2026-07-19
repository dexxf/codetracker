package com.io.codetracker.adapter.auth.out.dto.response;

public record GithubEmailResponse(
    String email,
    boolean primary,
    boolean verified,
    String visibility
) {}
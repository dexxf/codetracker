package com.io.codetracker.adapter.auth.out.github.result;

public record GithubEmailResult(
    String email,
    boolean primary,
    boolean verified,
    String visibility
) {}
package com.io.codetracker.adapter.auth.out.github.dto;

public record GithubEmailDTO(
    String email,
    boolean primary,
    boolean verified,
    String visibility
) {}
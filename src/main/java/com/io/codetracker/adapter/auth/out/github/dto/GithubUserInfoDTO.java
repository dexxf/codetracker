package com.io.codetracker.adapter.auth.out.github.dto;

public record GithubUserInfoDTO(
    Long id,
    String login,
    String repos_url,
    String name,
    String email,
    String avatar_url
) { 

}

package com.io.codetracker.application.auth.result;

public record GithubFetchUserInfoResult(
    Long id,
    String login,
    String repos_url,
    String name,
    String email,
    String avatar_url
) { 

}

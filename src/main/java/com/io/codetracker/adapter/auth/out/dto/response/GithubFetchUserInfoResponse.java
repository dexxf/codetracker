package com.io.codetracker.adapter.auth.out.dto.response;

public record GithubFetchUserInfoResponse(    Long id,
                                              String login,
                                              String repos_url,
                                              String name,
                                              String email,
                                              String avatar_url) {
}

package com.io.kira.application.github.result;

public record GithubRepositoryOwnerData(
        long ownerId,
        String nodeId,
        String login,
        String name,
        String type,
        String avatarUrl,
        String htmlUrl,
        String apiUrl,
        boolean siteAdmin
) {
}

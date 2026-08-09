package com.io.kira.application.github.result;

public record GithubRepositoryLicenseData(
        long licenseId,
        String nodeId,
        String key,
        String name,
        String spdxId,
        String description,
        String htmlUrl
) {
}

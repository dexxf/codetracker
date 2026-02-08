package com.io.codetracker.adapter.auth.out.persistence.mapper;

import com.io.codetracker.domain.auth.entity.GithubAccount;
import com.io.codetracker.infrastructure.auth.persistence.entity.GithubAccountEntity;

public final class GithubAccountMapper {

    private GithubAccountMapper() {
    }

    public static GithubAccount toDomain(GithubAccountEntity entity) {
        if (entity == null) {
            return null;
        }

        return new GithubAccount(
                entity.getGithubAccountId(),
                entity.getAuthId(),
                entity.getGithubId(),
                entity.getAccessToken()
        );
    }

    public static GithubAccountEntity toEntity(GithubAccount domain) {
        if (domain == null) {
            return null;
        }

        return new GithubAccountEntity(
                domain.getGithubAccountId(),
                domain.getAuthId(),
                domain.getGithubId(),
                domain.getAccessToken()
        );
    }

}

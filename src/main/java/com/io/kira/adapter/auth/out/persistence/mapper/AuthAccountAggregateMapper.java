package com.io.kira.adapter.auth.out.persistence.mapper;

import com.io.kira.domain.auth.aggregate.AuthAccountAggregate;
import com.io.kira.domain.auth.entity.Auth;
import com.io.kira.domain.auth.entity.GithubAccount;
import com.io.kira.domain.auth.valueobject.Email;
import com.io.kira.domain.auth.valueobject.HashedPassword;
import com.io.kira.infrastructure.auth.persistence.entity.AuthEntity;
import com.io.kira.infrastructure.auth.persistence.entity.GithubAccountEntity;

public final class AuthAccountAggregateMapper {

    private AuthAccountAggregateMapper() {
    }
    public static AuthEntity toEntity(AuthAccountAggregate aggregate) {
        Auth auth = aggregate.auth();

        AuthEntity authEntity = AuthEntity.builder()
                .id(auth.getAuthId())
                .userId(auth.getUserId())
                .email(auth.getEmail().getValue())
                .username(auth.getUsername())
                .password(auth.getPassword() == null
                        ? null
                        : auth.getPassword().getValue())
                .createdAt(auth.getCreatedAt())
                .status(auth.getStatus())
                .role(auth.getRole())
                .build();

        GithubAccount github = aggregate.githubAccount();

        if (github != null) {
            GithubAccountEntity githubEntity = new GithubAccountEntity(
                    github.getId(),
                    authEntity,
                    github.getGithubId(),
                    github.getAccessToken()
            );

            authEntity.linkGithubAccount(githubEntity);
        }

        return authEntity;
    }

    public static AuthAccountAggregate toDomain(AuthEntity entity) {
        Auth auth = Auth.reconstitute(
                entity.getId(),
                entity.getUserId(),
                entity.getEmail() == null ? null : Email.of(entity.getEmail()).data(),
                entity.getUsername(),
                entity.getPassword() == null ? null : HashedPassword.of(entity.getPassword()).data(),
                entity.getRole(),
                entity.getStatus(),
                entity.getCreatedAt()
        );

        GithubAccountEntity githubEntity = entity.getGithubAccountEntity();
        GithubAccount githubAccount = githubEntity == null
                ? null
                : new GithubAccount(entity.getId(), githubEntity.getGithubId(), githubEntity.getAccessToken());

        return new AuthAccountAggregate(auth, githubAccount);
    }
}

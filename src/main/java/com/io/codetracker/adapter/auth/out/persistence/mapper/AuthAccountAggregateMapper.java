package com.io.codetracker.adapter.auth.out.persistence.mapper;

import com.io.codetracker.domain.auth.aggregate.AuthAccountAggregate;
import com.io.codetracker.domain.auth.entity.Auth;
import com.io.codetracker.domain.auth.entity.GithubAccount;
import com.io.codetracker.domain.auth.valueobject.Email;
import com.io.codetracker.domain.auth.valueobject.HashedPassword;
import com.io.codetracker.infrastructure.auth.persistence.entity.AuthEntity;
import com.io.codetracker.infrastructure.auth.persistence.entity.GithubAccountEntity;

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
                entity.getEmail() == null ? null :
                        Email.of(entity.getEmail()).data(),
                entity.getUsername(),
                entity.getPassword() == null ? null
                        : HashedPassword.of(entity.getPassword()).data(),
                entity.getRole(),
                entity.getStatus(),
                entity.getCreatedAt()
        );

        return new AuthAccountAggregate(auth, new GithubAccount(entity.getId(), entity.getGithubAccountEntity().getGithubId(), entity.getGithubAccountEntity().getAccessToken()));
    }
}

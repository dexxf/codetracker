package com.io.codetracker.infrastructure.auth.factory;

import com.io.codetracker.domain.auth.aggregate.AuthAccountAggregate;
import com.io.codetracker.domain.auth.entity.Auth;
import com.io.codetracker.domain.auth.entity.GithubAccount;
import com.io.codetracker.domain.auth.valueobject.Email;
import com.io.codetracker.domain.auth.valueobject.Roles;
import org.springframework.stereotype.Component;

import com.io.codetracker.domain.auth.factory.AuthAccountAggregateFactory;

import java.util.UUID;


@Component
public class DefaultAuthAccountAggregateFactory implements AuthAccountAggregateFactory {


    @Override
    public AuthAccountAggregate create(UUID userId, Email email, String username, Roles role, Long githubId, String accessToken) {
        UUID authId = UUID.randomUUID();

        Auth auth = Auth.createOAuth(authId,userId,email,username,role);

        GithubAccount githubAccount = new GithubAccount(authId.toString(),githubId,accessToken);
        return new AuthAccountAggregate(auth,githubAccount);
    }
}


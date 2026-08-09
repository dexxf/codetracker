package com.io.kira.infrastructure.auth.factory;

import com.io.kira.domain.auth.aggregate.AuthAccountAggregate;
import com.io.kira.domain.auth.entity.Auth;
import com.io.kira.domain.auth.entity.GithubAccount;
import com.io.kira.domain.auth.valueobject.Email;
import com.io.kira.domain.auth.valueobject.Roles;
import org.springframework.stereotype.Component;

import com.io.kira.domain.auth.factory.AuthAccountAggregateFactory;

import java.util.UUID;


@Component
public class DefaultAuthAccountAggregateFactory implements AuthAccountAggregateFactory {


    @Override
    public AuthAccountAggregate create(UUID userId, Email email, String username, Roles role, Long githubId, String accessToken) {
        UUID authId = UUID.randomUUID();

        Auth auth = Auth.createOAuth(authId,userId,email,username,role);

        GithubAccount githubAccount = new GithubAccount(authId,githubId,accessToken);
        return new AuthAccountAggregate(auth,githubAccount);
    }
}


package com.io.codetracker.infrastructure.auth.factory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.io.codetracker.common.id.IDGenerator;
import com.io.codetracker.domain.auth.entity.GithubAccount;
import com.io.codetracker.domain.auth.factory.GithubAccountFactory;

@Component
public class DefaultGithubAccountFactory implements GithubAccountFactory{
   
    private final IDGenerator idGenerator;

    public DefaultGithubAccountFactory (@Qualifier("githubAccountIDGenerator") IDGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public GithubAccount create(String authId, Long githubId, String accessToken) {
        return new GithubAccount(idGenerator.generate(), authId, githubId, accessToken);
    }

}

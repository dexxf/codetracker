package com.io.codetracker.domain.auth.factory;



import java.util.UUID;
import com.io.codetracker.domain.auth.aggregate.AuthAccountAggregate;
import com.io.codetracker.domain.auth.valueobject.Email;
import com.io.codetracker.domain.auth.valueobject.Roles;



public interface AuthAccountAggregateFactory {

    AuthAccountAggregate create(UUID userId, Email email, String username, Roles role, Long githubId, String accessToken);
}


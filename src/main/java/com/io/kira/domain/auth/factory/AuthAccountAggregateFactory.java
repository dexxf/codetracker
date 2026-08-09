package com.io.kira.domain.auth.factory;



import java.util.UUID;
import com.io.kira.domain.auth.aggregate.AuthAccountAggregate;
import com.io.kira.domain.auth.valueobject.Email;
import com.io.kira.domain.auth.valueobject.Roles;



public interface AuthAccountAggregateFactory {

    AuthAccountAggregate create(UUID userId, Email email, String username, Roles role, Long githubId, String accessToken);
}


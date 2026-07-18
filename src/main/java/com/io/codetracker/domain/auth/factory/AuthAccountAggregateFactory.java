package com.io.codetracker.domain.auth.factory;


import com.io.codetracker.domain.auth.aggregate.AuthAccountAggregate;
import com.io.codetracker.domain.auth.valueobject.Email;
import com.io.codetracker.domain.auth.valueobject.Roles;



public interface AuthAccountAggregateFactory {
    AuthAccountAggregate create(String userId, Email email, String username, Roles role, Long githubId, String accessToken);
}

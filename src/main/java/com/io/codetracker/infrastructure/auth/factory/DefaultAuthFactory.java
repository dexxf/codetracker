package com.io.codetracker.infrastructure.auth.factory;

import com.io.codetracker.common.id.IDGenerator;
import com.io.codetracker.domain.auth.entity.Auth;
import com.io.codetracker.domain.auth.factory.AuthFactory;
import com.io.codetracker.domain.auth.valueobject.Email;
import com.io.codetracker.domain.auth.valueobject.HashedPassword;
import com.io.codetracker.domain.auth.valueobject.Roles;
import com.io.codetracker.domain.auth.valueobject.Status;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DefaultAuthFactory implements AuthFactory {

    private final IDGenerator idGenerator;

    public DefaultAuthFactory (@Qualifier("authIDGenerator") IDGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public Auth create(String userId, Email email, String username, HashedPassword password, Roles role) {
        return new Auth(idGenerator.generate(),userId, email,username,password, role,Status.INACTIVE,LocalDateTime.now());
    }
}
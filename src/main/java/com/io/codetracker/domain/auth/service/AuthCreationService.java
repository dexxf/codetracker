package com.io.codetracker.domain.auth.service;

import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.entity.Auth;
import com.io.codetracker.domain.auth.factory.AuthFactory;
import com.io.codetracker.domain.auth.repository.AuthDomainRepository;
import com.io.codetracker.domain.auth.result.AuthCreationResult;
import com.io.codetracker.domain.auth.result.EmailResult;
import com.io.codetracker.domain.auth.valueobject.Email;
import com.io.codetracker.domain.auth.valueobject.Roles;


public final class AuthCreationService {

    private final AuthFactory factory;
    private final AuthDomainRepository repository;

    public AuthCreationService (AuthFactory factory, AuthDomainRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

       public Result<Auth, AuthCreationResult> createAuthWithOAuth(String userId, String email,String username, String role) {
        if (repository.existsByUsername(username)) {
            return Result.fail(AuthCreationResult.USERNAME_TAKEN);
        }
        
        Result<Email, EmailResult> emailResult = Email.of(email);
        if (!emailResult.success()) {
            return switch (emailResult.error()) {
                case EMAIL_EMPTY -> Result.fail(AuthCreationResult.EMPTY_EMAIL);
                case INVALID_EMAIL_FORMAT -> Result.fail(AuthCreationResult.INVALID_EMAIL_FORMAT);
            };
        }
        
        Roles selectedRole;
        try {
            selectedRole = Roles.valueOf(role);
        } catch (IllegalArgumentException e) {
            return Result.fail(AuthCreationResult.INVALID_ROLE);
        }
        
        Auth auth = factory.create(userId, emailResult.data(), username, null, selectedRole);
        return Result.ok(auth);
    }
}

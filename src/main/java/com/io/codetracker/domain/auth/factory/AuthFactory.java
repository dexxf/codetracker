package com.io.codetracker.domain.auth.factory;

import com.io.codetracker.domain.auth.entity.Auth;
import com.io.codetracker.domain.auth.valueobject.Email;
import com.io.codetracker.domain.auth.valueobject.HashedPassword;
import com.io.codetracker.domain.auth.valueobject.Roles;


public interface AuthFactory {
    Auth create(String userId, Email email, String username, HashedPassword password, Roles role);
}

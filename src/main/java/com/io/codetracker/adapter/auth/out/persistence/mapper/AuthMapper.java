package com.io.codetracker.adapter.auth.out.persistence.mapper;

import com.io.codetracker.domain.auth.entity.Auth;
import com.io.codetracker.domain.auth.valueobject.Email;
import com.io.codetracker.domain.auth.valueobject.HashedPassword;
import com.io.codetracker.infrastructure.auth.persistence.entity.AuthEntity;

public class AuthMapper {
    
    public static Auth toDomain(AuthEntity entity) {
        Email email = Email.of(entity.getEmail()).data();
        
        HashedPassword password = null;
        if (entity.getPassword() != null) {
            password = HashedPassword.of(entity.getPassword()).data();
        }
        
        return new Auth(
            entity.getAuthId(),
            entity.getUserId(),
            email,
            entity.getUsername(),
            password,
            entity.getRole(),
            entity.getStatus(),
            entity.getCreatedAt()
        );
    }

    public static AuthEntity toEntity(Auth auth) {
        String passwordValue = null;
        if (auth.getPassword() != null) {
            passwordValue = auth.getPassword().getValue();
        }
        
        return new AuthEntity(
            auth.getAuthId(),
            auth.getUserId(),
            auth.getEmail().getValue(),
            auth.getUsername(),
            passwordValue,
            auth.getCreatedAt(),
            auth.getStatus(),
            auth.getRole()
        );
    }

}

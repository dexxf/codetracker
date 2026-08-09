package com.io.kira.adapter.auth.out.persistence.mapper;

import com.io.kira.domain.auth.entity.Auth;
import com.io.kira.domain.auth.valueobject.Email;
import com.io.kira.domain.auth.valueobject.HashedPassword;
import com.io.kira.infrastructure.auth.persistence.entity.AuthEntity;

public class AuthMapper {
    
    public static Auth toDomain(AuthEntity entity) {
        Email email = Email.of(entity.getEmail()).data();
        
        HashedPassword password = null;
        if (entity.getPassword() != null) {
            password = HashedPassword.of(entity.getPassword()).data();
        }
        
        return Auth.reconstitute(
            entity.getId(),
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

        return AuthEntity.builder()
                .id(auth.getAuthId())
                .userId(auth.getUserId())
                .email(auth.getEmail().getValue())
                .username(auth.getUsername())
                .password(passwordValue)
                .createdAt(auth.getCreatedAt())
                .status(auth.getStatus())
                .role(auth.getRole())
                .build();
    }

}

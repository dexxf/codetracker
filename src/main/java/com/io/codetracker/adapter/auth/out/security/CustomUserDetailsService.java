package com.io.codetracker.adapter.auth.out.security;

import com.io.codetracker.adapter.auth.out.persistence.mapper.AuthMapper;
import com.io.codetracker.infrastructure.auth.persistence.entity.AuthEntity;
import com.io.codetracker.infrastructure.auth.persistence.repository.JpaAuthRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final JpaAuthRepository repository;

    public CustomUserDetailsService (JpaAuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String authId) throws UsernameNotFoundException {
        UUID parsedAuthId;
        try {
            parsedAuthId = UUID.fromString(authId);
        } catch (IllegalArgumentException e) {
            throw new UsernameNotFoundException("Invalid auth id: " + authId, e);
        }

        AuthEntity entity = repository.findById(parsedAuthId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + authId));

        return new AuthPrincipal(AuthMapper.toDomain(entity));
    }
}

package com.io.codetracker.adapter.auth.out.security;

import com.io.codetracker.adapter.auth.out.persistence.mapper.AuthMapper;
import com.io.codetracker.adapter.auth.out.persistence.repository.JpaAuthRepository;
import com.io.codetracker.infrastructure.auth.persistence.entity.AuthEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final JpaAuthRepository repository;

    public CustomUserDetailsService (JpaAuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String authId) throws UsernameNotFoundException {
        AuthEntity entity = repository.findById(authId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + authId));

        return new AuthPrincipal(AuthMapper.toDomain(entity));
    }
}

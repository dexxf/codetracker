package com.io.codetracker.adapter.auth.out.security;

import com.io.codetracker.domain.auth.entity.Auth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public final class AuthPrincipal implements UserDetails {

    private final Auth auth;

    public AuthPrincipal(Auth auth) {
        this.auth = auth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority( "ROLE_"+ auth.getRole().name()));
    }

    @Override
    public String getPassword() {
        return auth.getPassword().getValue();
    }

    @Override
    public String getUsername() {
        return auth.getAuthId();
    }

    public String getUserId() {
        return auth.getUserId();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

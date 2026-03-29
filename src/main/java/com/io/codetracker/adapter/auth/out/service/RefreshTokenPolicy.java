package com.io.codetracker.adapter.auth.out.service;

import com.io.codetracker.domain.auth.service.RefreshTokenLifetimePolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RefreshTokenPolicy implements RefreshTokenLifetimePolicy {

    private final int lifetimeHours;

    public RefreshTokenPolicy(@Value("${refresh.token.lifetime.hour}") int lifetimeHours){
        this.lifetimeHours = lifetimeHours;
    }

    @Override
    public LocalDateTime issueExpirationFromNow() {
        return LocalDateTime.now().plusHours(lifetimeHours);
    }
}

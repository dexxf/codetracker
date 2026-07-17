package com.io.codetracker.adapter.auth.out.service;

import com.io.codetracker.application.auth.port.out.RefreshTokenLifetimePolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class RefreshTokenPolicy implements RefreshTokenLifetimePolicy {

    private final int lifetimeHours;

    public RefreshTokenPolicy(@Value("${refresh.token.lifetime.hour}") int lifetimeHours){
        this.lifetimeHours = lifetimeHours;
    }

    @Override
    public Instant issueExpirationFromNow() {
        return Instant.now().plus(Duration.ofHours(lifetimeHours));
    }
}

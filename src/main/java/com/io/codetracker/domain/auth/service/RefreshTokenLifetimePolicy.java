package com.io.codetracker.domain.auth.service;

import java.time.Instant;

public interface RefreshTokenLifetimePolicy {
    Instant issueExpirationFromNow();
}

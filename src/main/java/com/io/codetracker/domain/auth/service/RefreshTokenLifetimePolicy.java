package com.io.codetracker.domain.auth.service;

import java.time.LocalDateTime;

public interface RefreshTokenLifetimePolicy {
    LocalDateTime issueExpirationFromNow();
}

package com.io.codetracker.application.auth.port.out;

import java.time.Instant;

public interface RefreshTokenLifetimePolicy {
    Instant issueExpirationFromNow();
}

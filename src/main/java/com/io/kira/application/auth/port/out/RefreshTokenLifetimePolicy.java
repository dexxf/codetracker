package com.io.kira.application.auth.port.out;

import java.time.Instant;

public interface RefreshTokenLifetimePolicy {
    Instant issueExpirationFromNow();
}

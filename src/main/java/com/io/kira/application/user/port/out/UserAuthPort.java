package com.io.kira.application.user.port.out;

import java.util.UUID;

public interface UserAuthPort {
    void changeStatusActiveByUserId(UUID userId);
}


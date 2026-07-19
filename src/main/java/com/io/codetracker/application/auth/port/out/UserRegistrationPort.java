package com.io.codetracker.application.auth.port.out;

import java.util.UUID;

public interface UserRegistrationPort {
    UUID createShallowUser();
}

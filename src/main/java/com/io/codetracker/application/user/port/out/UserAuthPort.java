package com.io.codetracker.application.user.port.out;

import java.util.Optional;

public interface UserAuthPort {
    void markUserAsFullyInitialized(String userId);
    Optional<String> getUserIdByAuthId(String authId);
}

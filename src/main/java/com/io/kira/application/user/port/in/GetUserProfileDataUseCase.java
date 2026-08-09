package com.io.kira.application.user.port.in;


import java.util.UUID;
import com.io.kira.application.user.result.UserData;

import java.util.Optional;

public interface GetUserProfileDataUseCase {
    Optional<UserData> getProfileData(UUID userId);
}


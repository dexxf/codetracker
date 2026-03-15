package com.io.codetracker.application.user.port.in;

import com.io.codetracker.application.user.result.UserData;

import java.util.Optional;

public interface GetUserProfileDataUseCase {
    Optional<UserData> getProfileData(String userId);
}

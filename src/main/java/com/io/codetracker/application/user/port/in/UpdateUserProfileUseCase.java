package com.io.codetracker.application.user.port.in;


import java.util.UUID;
import com.io.codetracker.application.user.command.UserProfileCommand;
import com.io.codetracker.application.user.error.UserProfileError;
import com.io.codetracker.application.user.result.UserData;
import com.io.codetracker.common.result.Result;

import java.util.List;

public interface UpdateUserProfileUseCase {
    Result<UserData, List<UserProfileError>> updateProfile(UUID userId, UserProfileCommand command);
}


package com.io.kira.application.user.port.in;


import java.util.UUID;
import com.io.kira.application.user.command.UserProfileCommand;
import com.io.kira.application.user.error.UserProfileError;
import com.io.kira.application.user.result.UserData;
import com.io.kira.common.result.Result;

import java.util.List;

public interface UpdateUserProfileUseCase {
    Result<UserData, List<UserProfileError>> updateProfile(UUID userId, UserProfileCommand command);
}


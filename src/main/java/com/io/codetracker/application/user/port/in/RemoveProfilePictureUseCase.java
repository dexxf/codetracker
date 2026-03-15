package com.io.codetracker.application.user.port.in;

import com.io.codetracker.application.user.result.ProfilePictureResult;

public interface RemoveProfilePictureUseCase {
    ProfilePictureResult removeProfilePicture(String userId);
}

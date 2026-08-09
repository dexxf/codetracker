package com.io.kira.application.user.port.in;


import java.util.UUID;
import com.io.kira.application.user.result.ProfilePictureResult;

public interface RemoveProfilePictureUseCase {
    ProfilePictureResult removeProfilePicture(UUID userId);
}


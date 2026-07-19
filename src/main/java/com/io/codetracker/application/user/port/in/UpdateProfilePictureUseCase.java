package com.io.codetracker.application.user.port.in;


import java.util.UUID;
import com.io.codetracker.application.user.result.ProfilePictureResult;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateProfilePictureUseCase {
    ProfilePictureResult updateProfilePicture(UUID userId, MultipartFile imgByte);
}


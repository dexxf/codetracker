package com.io.codetracker.application.user.port.in;

import com.io.codetracker.application.user.result.ProfilePictureResult;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateProfilePictureUseCase {
    ProfilePictureResult updateProfilePicture(String userId, MultipartFile imgByte);
}

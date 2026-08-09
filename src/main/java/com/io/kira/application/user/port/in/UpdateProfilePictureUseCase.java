package com.io.kira.application.user.port.in;


import java.util.UUID;
import com.io.kira.application.user.result.ProfilePictureResult;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateProfilePictureUseCase {
    ProfilePictureResult updateProfilePicture(UUID userId, MultipartFile imgByte);
}


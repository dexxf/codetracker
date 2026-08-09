package com.io.kira.application.user.service;


import java.util.UUID;
import com.io.kira.application.user.port.in.RemoveProfilePictureUseCase;
import com.io.kira.application.user.port.in.UpdateProfilePictureUseCase;
import com.io.kira.application.user.port.out.CloudinaryPort;
import com.io.kira.application.user.port.out.UserAppRepository;
import com.io.kira.application.user.result.ProfilePictureResult;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ProfilePictureService implements RemoveProfilePictureUseCase, UpdateProfilePictureUseCase {

    private final CloudinaryPort cloudinaryPort;
    private final UserAppRepository userAppRepository;

    @Transactional
    public ProfilePictureResult removeProfilePicture(UUID userId) {
        try {
            cloudinaryPort.deleteImageByPublicId(userId.toString());
            int rowsAffected = userAppRepository.updateProfileUrlByUserId(userId, null);

            return switch (rowsAffected) {
                case 1 -> ProfilePictureResult.SUCCESS;
                case 0 -> ProfilePictureResult.USER_NOT_FOUND;
                default -> ProfilePictureResult.MULTIPLE_ROWS_AFFECTED;
            };
        } catch (IOException e) {
            return ProfilePictureResult.MODIFICATION_FAILED;
        }
    }

    @Transactional
    public ProfilePictureResult updateProfilePicture(UUID userId, MultipartFile imgByte) {
        try {
            String imageUrl = cloudinaryPort.uploadProfilePicture(imgByte.getBytes(), userId.toString());
            int rowsAffected = userAppRepository.updateProfileUrlByUserId(userId, imageUrl);

            return switch (rowsAffected) {
                case 1 -> ProfilePictureResult.SUCCESS;
                case 0 -> ProfilePictureResult.USER_NOT_FOUND;
                default -> ProfilePictureResult.MULTIPLE_ROWS_AFFECTED;
            };

        } catch (IOException e) {
            return ProfilePictureResult.MODIFICATION_FAILED;
        }
    }
}


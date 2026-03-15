package com.io.codetracker.application.user.service;

import com.io.codetracker.adapter.user.in.dto.response.UpdateProfilePictureResponse;
import com.io.codetracker.application.user.port.out.CloudinaryPort;
import com.io.codetracker.application.user.port.out.UserAppRepository;
import com.io.codetracker.application.user.result.RemoveProfilePictureResult;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ProfilePictureService {

    private final CloudinaryPort cloudinaryPort;
    private final UserAppRepository userAppRepository;

    @Transactional
    public RemoveProfilePictureResult removeProfilePicture(String userId) {
        try {
            cloudinaryPort.deleteImageByPublicId(userId);
            int rowsAffected = userAppRepository.updateProfileUrlByUserId(userId, null);

            return switch (rowsAffected) {
                case 1 -> RemoveProfilePictureResult.SUCCESS;
                case 0 -> RemoveProfilePictureResult.USER_NOT_FOUND;
                default -> RemoveProfilePictureResult.MULTIPLE_ROWS_AFFECTED;
            };
        } catch (IOException e) {
            return RemoveProfilePictureResult.DELETE_FAILED;
        }
    }

    @Transactional
    public UpdateProfilePictureResponse updateProfilePicture(String userId, MultipartFile imgByte) {
        try {
            String imageUrl = cloudinaryPort.uploadProfilePicture(imgByte.getBytes(),userId);
            int rowsAffected = userAppRepository.updateProfileUrlByUserId(userId, imageUrl);
            if (rowsAffected == 1) {
                return UpdateProfilePictureResponse.success(imageUrl, "Successfully updated Profile Picture.");
            } else if (rowsAffected == 0) {
                return UpdateProfilePictureResponse.failure("User not found or no profile picture to update.");
            } else {
                return UpdateProfilePictureResponse.failure("Unexpected error: multiple rows affected.");
            }
        } catch (IOException e) {
            return UpdateProfilePictureResponse.failure("error updating profile picture.");
        }
    }
}

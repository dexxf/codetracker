package com.io.codetracker.application.user.service;

import com.io.codetracker.application.user.port.in.response.DeleteProfilePictureResponse;
import com.io.codetracker.application.user.port.in.response.UpdateProfilePictureResponse;
import com.io.codetracker.application.user.port.out.CloudinaryPort;
import com.io.codetracker.application.user.port.out.UserAppRepository;
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
    public DeleteProfilePictureResponse removeProfilePicture(String userId) {
        try {
            cloudinaryPort.deleteImageByPublicId(userId);
            int rowsAffected = userAppRepository.updateProfileUrlByUserId(userId, null);
            if (rowsAffected == 1) {
                return DeleteProfilePictureResponse.success("Profile picture removed successfully.");
            } else if (rowsAffected == 0) {
                return DeleteProfilePictureResponse.failure("User not found or no profile picture to remove.");
            } else {
                return DeleteProfilePictureResponse.failure("Unexpected error: multiple rows affected.");
            }
        } catch (IOException e) {
            return DeleteProfilePictureResponse.failure("Failed to delete profile picture: " + e.getMessage());
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

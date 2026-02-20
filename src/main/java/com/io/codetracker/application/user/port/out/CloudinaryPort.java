package com.io.codetracker.application.user.port.out;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryPort {
    String uploadProfilePicture(MultipartFile file, String publicId) throws IOException;
    void deleteImageByPublicId(String publicId) throws IOException;
}
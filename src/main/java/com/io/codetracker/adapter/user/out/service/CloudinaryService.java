package com.io.codetracker.adapter.user.out.service;

import com.cloudinary.Cloudinary;
import com.io.codetracker.application.user.port.out.CloudinaryPort;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService implements CloudinaryPort{

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadProfilePicture(MultipartFile file, String publicId) throws IOException {
        return uploadImage(file, "user-profile", publicId);
    }

    @Override
    public void deleteImageByPublicId(String publicId) throws IOException{
        cloudinary.uploader().destroy(publicId, Map.of());
    }

    public String uploadImage(MultipartFile file, String folder, String publicId) throws IOException {
        Map<String, Object> options = new HashMap<>();

        if (folder != null && !folder.isBlank()) {
            options.put("folder", folder);
        }
        if (publicId != null && !publicId.isBlank()) {
            options.put("public_id", publicId);
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(file.getBytes(), options);
        return (String) uploadResult.get("secure_url");
    }
}
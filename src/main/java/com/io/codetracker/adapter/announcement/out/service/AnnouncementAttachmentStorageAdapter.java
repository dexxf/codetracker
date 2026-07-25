package com.io.codetracker.adapter.announcement.out.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.io.codetracker.application.announcement.port.out.AnnouncementAttachmentStoragePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AnnouncementAttachmentStorageAdapter implements AnnouncementAttachmentStoragePort {

    private final Cloudinary cloudinary;

    @Override
    public String upload(byte[] content, UUID classroomId, UUID attachmentId) throws IOException {

        Map<String, Object> options = ObjectUtils.asMap(
                "public_id", attachmentId.toString(),
                "folder", classroomId.toString(),
                "resource_type", "auto",
                "overwrite", false
        );

        Map uploadResult = cloudinary.uploader().upload(content, options);

        return (String) uploadResult.get("secure_url");
    }

    @Override
    public void delete(UUID classroomId, String publicId) throws IOException {
        String fullPublicId = classroomId + "/" + publicId;
        cloudinary.uploader().destroy(fullPublicId, ObjectUtils.emptyMap());
    }
}
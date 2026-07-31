package com.io.codetracker.adapter.announcement.out.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.io.codetracker.application.announcement.port.out.AnnouncementAttachmentStoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class AnnouncementAttachmentStorageAdapter implements AnnouncementAttachmentStoragePort {

    private final Cloudinary cloudinary;
    private final String announcementFolderPath;

    public AnnouncementAttachmentStorageAdapter(
            Cloudinary cloudinary,
            @Value("${cloudinary.announcement.attachment.folder-path}") String announcementFolderPath) {
        this.cloudinary = cloudinary;
        this.announcementFolderPath = announcementFolderPath;
    }

    @Override
    public UploadedAttachment upload(byte[] content, UUID classroomId, UUID attachmentId) throws IOException {

        Map<String, Object> options = ObjectUtils.asMap(
                "public_id", attachmentId.toString(),
                "folder", announcementFolderPath + classroomId.toString(),
                "resource_type", "auto",
                "overwrite", false
        );

        Map<String, Object> uploadResult = cloudinary.uploader().upload(content, options);

        return new UploadedAttachment(
                (String) uploadResult.get("secure_url"),
                (String) uploadResult.get("resource_type")
        );
    }

    @Override
    public void delete(UUID classroomId, UUID publicId, String resourceType) throws IOException {
        String fullPublicId = announcementFolderPath + classroomId + "/" + publicId;
        cloudinary.uploader().destroy(fullPublicId, ObjectUtils.asMap("resource_type", resourceType, "type", "upload","invalidate", true));
    }
}
package com.io.kira.application.announcement.port.out;

import java.io.IOException;
import java.util.UUID;

public interface AnnouncementAttachmentStoragePort {
    UploadedAttachment upload(byte[] content, UUID classroomId, UUID attachmentId) throws IOException;
    void delete(UUID classroomId, UUID publicId, String resourceType) throws IOException;

    record UploadedAttachment(String url, String resourceType) { }
}

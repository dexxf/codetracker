package com.io.codetracker.application.announcement.port.out;

import java.io.IOException;
import java.util.UUID;

public interface AnnouncementAttachmentStoragePort {
    String upload(byte[] content, UUID classroomId, UUID attachmentId) throws IOException;
    void delete(UUID classroomId, String publicId) throws IOException;
}

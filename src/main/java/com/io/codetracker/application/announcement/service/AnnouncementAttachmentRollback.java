package com.io.codetracker.application.announcement.service;

import com.io.codetracker.application.announcement.port.out.AnnouncementAttachmentStoragePort;
import com.io.codetracker.domain.announcement.entity.AnnouncementAttachment;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

@Slf4j
public final class AnnouncementAttachmentRollback {

    private AnnouncementAttachmentRollback() {
    }

    public static void deleteUploaded(
            AnnouncementAttachmentStoragePort attachmentStorage,
            UUID classroomId,
            Collection<AnnouncementAttachment> uploadedAttachments
    ) {
        for (AnnouncementAttachment attachment : uploadedAttachments) {
            try {
                attachmentStorage.delete(classroomId, attachment.attachmentId(), attachment.resourceType());
            } catch (IOException ex) {
                log.warn("Failed to rollback attachment {} in classroom {}", attachment.attachmentId(), classroomId, ex);
            }
        }
    }
}
